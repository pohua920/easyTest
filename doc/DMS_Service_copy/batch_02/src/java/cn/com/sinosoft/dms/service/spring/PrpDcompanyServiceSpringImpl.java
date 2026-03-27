package cn.com.sinosoft.dms.service.spring;

import ins.framework.common.Page;
import ins.framework.common.ServiceFactory;
import ins.framework.dao.GenericDaoHibernate;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.sinosoft.common.model.InputBean;
import cn.com.sinosoft.dms.model.PrpDcompany;
import cn.com.sinosoft.dms.model.PrpDcompanyExpansion;
import cn.com.sinosoft.dms.service.facade.CheckSameKeyService;
import cn.com.sinosoft.dms.service.facade.PrpDcompanyService;
import cn.com.sinosoft.ims.log.model.UtiISyncLog;
import cn.com.sinosoft.ims.log.service.facade.UtiISyncLogService;
import cn.com.sinosoft.ims.sync.HDMessageProducer;
import cn.com.sinosoft.ims.sync.SyncConstants;
import cn.com.sinosoft.ims.user.vo.UtiIUserVO;
import cn.com.sinosoft.ims.util.IConstants;
import cn.com.sinosoft.ims.util.ReadProperties;
import cn.com.sinosoft.saa.util.HqlRulesUtil;

import com.sinosoft.bpsdriver.service.facade.SaaAPIService;
import com.sinosoft.bpsdriver.service.spring.SaaAPIServiceImpl;

public class PrpDcompanyServiceSpringImpl extends
		GenericDaoHibernate<PrpDcompany, String> implements PrpDcompanyService {
	private int cont = 0;
	private static Log logger = LogFactory
			.getLog(PrpDcompanyServiceSpringImpl.class);

	/**
	 * 获得所有prpDcompany
	 * */
	@SuppressWarnings("unchecked")
	public List<PrpDcompany> getPrpDcompanyList() {
		String hql = " from PrpDcompany prpDcompany ";
		List<PrpDcompany> prpDcompanyList = super.findByHql(hql, null);
		return prpDcompanyList;
	}

	public void deletePrpDcompany(String comCode) {
		super.deleteByPK(comCode);
	}

	public PrpDcompany getPrpDcompany(String comCode) {
		return super.get(comCode);
	}

	public PrpDcompany getPrpDcompany1(String comCode) {
		String hql = "from PrpDcompany prpDcompany where prpDcompany.comCode=? and validStatus=1";
		List list = new ArrayList();
		list = super.findByHql(hql, comCode);
		if (list.size() != 0) {
			return (PrpDcompany) list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 生成级别 生成grade字段 插入机构表。
	 * 
	 * 因为目前设计只能增加下级机构所以增加操作时不需要考虑更改其他机构的upperPath字段。
	 * */
	public void insertPrpDcompany(PrpDcompany prpDcompany,String userCode) {
		/**
		 * 生成级别字段数据，*！只有在数据库中comlevel和上级代码的upperPath数据正确时才能生成！* start 2009-10-28
		 */
		String comCode = prpDcompany.getComCode();
		String upperComCode = prpDcompany.getUpperComCode();
		String upperPath = generateupperPath(comCode, upperComCode);
		prpDcompany.setUpperPath(upperPath);
		/** 生成级别字段数据，*！只有在数据库中comlevel和上级代码的upperPath数据正确时才能生成！* end */
		String[] level = upperPath.split(",");
		int comLevel = level.length;
		prpDcompany.setComLevel(new BigDecimal(comLevel));
		super.save(prpDcompany);
		String utilityflag = ReadProperties.getString("utilityflag");
		if(utilityflag.equals(SyncConstants.UtilityFlag)){
			PrpDcompanyExpansion prpDcompanyExpansion = new PrpDcompanyExpansion();
			prpDcompanyExpansion.setComCode(prpDcompany.getComCode());
			prpDcompanyExpansion.setComCodeCIRC(prpDcompany.getComCodeCIRC());
			prpDcompanyExpansion.setLicenseNo(prpDcompany.getLicenseNo());
			prpDcompanyExpansion.setEmail(prpDcompany.getEmail());
			prpDcompanyExpansion.setRemark(prpDcompany.getRemark());
			try {
				addMessageToOldPrpDcompany(prpDcompany);
				addMessageToOldPrpDcompanyExpansion(prpDcompanyExpansion);
			} catch (Exception e) {
				e.printStackTrace();		
			}
		}	
		// JMS
		String syncflag = ReadProperties.getString("syncflag");
		if(syncflag.equals(SyncConstants.SyncFlag)){
			CheckSameKeyService checkSameKeyService = (CheckSameKeyService) ServiceFactory
	        .getService("checkSameKeyService");// 获得Spring管理的bean
			UtiISyncLogService utiISyncLogService = (UtiISyncLogService) ServiceFactory
			.getService("utiISyncLogService");// 获得Spring管理的bean
			 String onlineCom = ReadProperties.getString("onlineCom");
			 String[] strOnlineCom = onlineCom.split(",");
			 List<UtiISyncLog> utiISyncLogList = new ArrayList<UtiISyncLog>();
			 UtiISyncLog utiISyncLog = null;
			 Long id = checkSameKeyService.getMaxId("UtiISyncLog", "id");
			 for (String comCodes : strOnlineCom) {	
				 utiISyncLog = new UtiISyncLog();
				 utiISyncLog.setId(id);
				 utiISyncLog.setClassName(SyncConstants.RequestFlag_PrpDcompanyMaintain);
				 utiISyncLog.setDestComCode(comCodes);
				 utiISyncLog.setEditType(SyncConstants.RequestFlag_EditTypeAdd);
				 utiISyncLog.setOperUserCode(userCode);
				 utiISyncLog.setReplayTimes(0);
				 utiISyncLog.setSendDate(new Date());
				 utiISyncLog.setLastSendDate(new Date());
				 utiISyncLog.setStrKey("comCode = '" + prpDcompany.getComCode() + "'");
				 utiISyncLogList.add(utiISyncLog);
				 id++;
			}
			 utiISyncLogService.insertAllUtiISyncLog(utiISyncLogList);
			 if (utiISyncLogList.size() > 0) {
				HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
				.getService("messageProducer");// 获得Spring管理的bean
				InputBean inputBean = new InputBean();
				inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDcompanyMaintain);
				 inputBean.setUtiISyncLogList(utiISyncLogList);
				 inputBean.setPrpDcompany(prpDcompany);
				 inputBean.setDestComCode(SyncConstants.DestComCode_Pub);
				 messageProducer.send(inputBean);
			}	
		}
	}

	/**
	 *被修改的机构有下级机构 则需要更新 *所有* 其下级机构的upperPath字段
	 **/
	public void updatePrpDcompany(PrpDcompany prpDcompany,String userCode) {
		/**
		 * 生成级别字段数据，*！只有在数据库中comlevel和上级代码的upperPath数据正确时才能生成！* start 2009-10-28
		 */
		String comCode = prpDcompany.getComCode();
		String upperComCode = prpDcompany.getUpperComCode();
		String upperPath = generateupperPath(comCode, upperComCode);
		prpDcompany.setUpperPath(upperPath);
		String[] level = upperPath.split(",");
		int comLevel = level.length;
		prpDcompany.setComLevel(new BigDecimal(comLevel));
		super.update(prpDcompany);
		String utilityflag = ReadProperties.getString("utilityflag");
		if(utilityflag.equals(SyncConstants.UtilityFlag)){
			PrpDcompanyExpansion prpDcompanyExpansion = new PrpDcompanyExpansion();
			prpDcompanyExpansion.setComCode(prpDcompany.getComCode());
			prpDcompanyExpansion.setComCodeCIRC(prpDcompany.getComCodeCIRC());
			prpDcompanyExpansion.setLicenseNo(prpDcompany.getLicenseNo());
			prpDcompanyExpansion.setEmail(prpDcompany.getEmail());
			prpDcompanyExpansion.setRemark(prpDcompany.getRemark());
			
		try {
			updateMessageToOldPrpDcompany(prpDcompany);
			updateMessageToOldPrpDcompanyExpansion(prpDcompanyExpansion);
		} catch (Exception e) {
			e.printStackTrace();
			}		
		}
		//updateSubCom(prpDcompany,userCode);// 更新所有下级机构
		// JMS
		String syncflag = ReadProperties.getString("syncflag");
		if(syncflag.equals("1")){
			CheckSameKeyService checkSameKeyService = (CheckSameKeyService) ServiceFactory
		       .getService("checkSameKeyService");// 获得Spring管理的bean
			UtiISyncLogService utiISyncLogService = (UtiISyncLogService) ServiceFactory
			.getService("utiISyncLogService");// 获得Spring管理的bean
			 String onlineCom = ReadProperties.getString("onlineCom");
			 String[] strOnlineCom = onlineCom.split(",");
			 List<UtiISyncLog> utiISyncLogList = new ArrayList<UtiISyncLog>();
			 UtiISyncLog utiISyncLog = null;
			 Long id = checkSameKeyService.getMaxId("UtiISyncLog", "id");
			 for (String comCodes : strOnlineCom) {	
				 utiISyncLog = new UtiISyncLog();
				 utiISyncLog.setId(id);
				 utiISyncLog.setClassName(SyncConstants.RequestFlag_PrpDcompanyMaintain);
				 utiISyncLog.setDestComCode(comCodes);
				 utiISyncLog.setEditType(SyncConstants.RequestFlag_EditTypeModify);
				 utiISyncLog.setOperUserCode(userCode);
				 utiISyncLog.setReplayTimes(0);
				 utiISyncLog.setSendDate(new Date());
				 utiISyncLog.setLastSendDate(new Date());
				 utiISyncLog.setStrKey("comCode = '" + prpDcompany.getComCode() + "'");
				 utiISyncLogList.add(utiISyncLog);
				 id++;
			}
			 utiISyncLogService.insertAllUtiISyncLog(utiISyncLogList);
			 if (utiISyncLogList.size() > 0) {
				HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
				.getService("messageProducer");// 获得Spring管理的bean
				InputBean inputBean = new InputBean();
				inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDcompanyMaintain);
				 inputBean.setUtiISyncLogList(utiISyncLogList);
				 inputBean.setPrpDcompany(prpDcompany);
				 inputBean.setDestComCode(SyncConstants.DestComCode_Pub);
				 messageProducer.send(inputBean);
			}				
			/** 生成级别字段数据，*！只有在数据库中comlevel和上级代码的upperPath数据正确时才能生成！* end */
		}
	}

	/**
	 * 2009-11-8 hualimin
	 * 
	 * @param prpDcompany
	 *            更新当前机构的所有下级机构的upperPath字段
	 */
	private void updateSubCom(PrpDcompany prpDcompany,String userCode) {
		if (prpDcompany == null) {
			return;
		}
		HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
		.getService("messageProducer");// 获得Spring管理的bean
		InputBean inputBean = null;
		 CheckSameKeyService checkSameKeyService = (CheckSameKeyService) ServiceFactory
	        .getService("checkSameKeyService");// 获得Spring管理的bean
		 UtiISyncLogService utiISyncLogService = (UtiISyncLogService) ServiceFactory
		 .getService("utiISyncLogService");// 获得Spring管理的bean
		String validStatus = prpDcompany.getValidStatus();
		String comCode = prpDcompany.getComCode();
		String upperPath = prpDcompany.getUpperPath();
		List list = getSubCode(comCode);// 获得直接下级机构,所以不可能是总公司，不需要判断总公司情况。
		for (int i = 0; i < list.size(); i++) {
			PrpDcompany currCompany = (PrpDcompany) list.get(i);
			if ("0".equals(validStatus)) {
				currCompany.setValidStatus("0");// 如果有效状态为0则将所有下级机构的有效状态都置为0
			}
			String currUpperPath = upperPath + "," + currCompany.getComCode();
			currCompany.setUpperPath(currUpperPath);
			String[] level = currUpperPath.split(",");
			int comLevel = level.length+1;
			currCompany.setComLevel(new BigDecimal(comLevel));
			super.update(currCompany);
			
			// JMS
			String syncflag = ReadProperties.getString("syncflag");
			if(syncflag.equals(SyncConstants.SyncFlag)){
				String onlineCom = ReadProperties.getString("onlineCom");
				 String[] strOnlineCom = onlineCom.split(",");
				 List<UtiISyncLog> utiISyncLogList = new ArrayList<UtiISyncLog>();
				 UtiISyncLog utiISyncLog = null;
				 Long id = checkSameKeyService.getMaxId("UtiISyncLog", "id");
				 for (String comCodes : strOnlineCom) {	
					 utiISyncLog = new UtiISyncLog();
					 utiISyncLog.setId(id);
					 utiISyncLog.setClassName(SyncConstants.RequestFlag_PrpDcompanyMaintain);
					 utiISyncLog.setDestComCode(comCodes);
					 utiISyncLog.setEditType(SyncConstants.RequestFlag_EditTypeModify);
					 utiISyncLog.setOperUserCode(userCode);
					 utiISyncLog.setReplayTimes(0);
					 utiISyncLog.setSendDate(new Date());
					 utiISyncLog.setLastSendDate(new Date());
					 utiISyncLog.setStrKey("comCode = '" + prpDcompany.getComCode() + "'");
					 utiISyncLogList.add(utiISyncLog);
					 id++;
				}
				 utiISyncLogService.insertAllUtiISyncLog(utiISyncLogList);
				 if (utiISyncLogList.size() > 0) {
					inputBean = new InputBean();
					inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDbankMaintain);
					 inputBean.setUtiISyncLogList(utiISyncLogList);
					 inputBean.setPrpDcompany(prpDcompany);
					 inputBean.setDestComCode(SyncConstants.DestComCode_Pub);
					 messageProducer.send(inputBean);
				}		
			}
			updateSubCom(currCompany,userCode);// 递归更新所有下级机构
		}
	}

	/**
	 * 分页查询
	 * */
	public Page getPrpDcompanyList(PrpDcompany prpDcompany, int pageNo,
			int pageSize) {
		StringBuffer hql = new StringBuffer();
		String nodeCompanyCode = prpDcompany.getFlag();//此flag是用户点击后设置到此字段，仅为传值用
														// ！

		hql.append(" from PrpDcompany prpDcompany ");
		HqlRulesUtil hqlRules = new HqlRulesUtil();
		hqlRules.addLike("prpDcompany.comCode", prpDcompany.getComCode());
		hqlRules.addLike("prpDcompany.comCName", prpDcompany.getComCName());
		if (hqlRules.getHql().trim() != null
				&& !hqlRules.getHql().trim().equals("")) {// 如果用户在查询框输入条件时
			hql.append("where " + hqlRules.getHql());
		}
		logger.debug("!!!!!!!!!!!!" + hql.toString());
		Page page = findByHql(hql.toString(), pageNo, pageSize);
		return page;
	}

	/**
	 * 获得所有上级代码comcode，和comCName的map 有效率问题，数据量大时不可行！！！
	 * */
	public Map<String, String> upCodeMap(String comCode) {
		List list = findAllUpperCompany(comCode);
		Map<String, String> map = new HashMap<String, String>();
		map.put("", "请选择");
		for (int i = 0; i < list.size(); i++) {
			PrpDcompany prpDcompany = (PrpDcompany) list.get(i);
			map.put(prpDcompany.getComCode(), prpDcompany.getComCName());
		}
		return map;
	}

	/**
	 * 得到所有上级代码 在保持prpDcompany和prpDcompanyGrade表数据一致的情况下，本方法可用， 否则可能出错！
	 * */
	@SuppressWarnings("unchecked")
	private List findAllUpperCompany(String comCode) {
		StringBuffer hql = new StringBuffer();
		
		/***************使用upperPath字段获得上级机构*************start********/
		StringBuffer comCodes = new StringBuffer();
		String upComCodes = getAllUpperComCodes(comCode); // 获得所有上级机构，包括当前机构
		if (upComCodes != "" && upComCodes != null) {
			comCodes.append("('").append(upComCodes.replace(",", "','"))
					.append("')");
		} else {
			comCodes.append("('')");
		}
		hql.append("from PrpDcompany a where a.comCode in ").append(comCodes).append(" and a.comCode <> '").append(comCode).append("'");
		List<PrpDcompany> list = super.findByHql(hql.toString());
		/**************使用upperPath字段获得上级机构**************end *****/
		
//		--------------使用prpDcompanyGrade获得上级机构--------start--------------
//		hql
//				.append("from PrpDcompany prpDcompany where prpDcompany.comCode not in (select prpDcompanyGrade.id.subComCode from PrpDcompanyGrade prpDcompanyGrade where prpDcompanyGrade.id.comCode=?)");
//
//		List<PrpDcompany> list = super.findByHql(hql.toString(), comCode);
//		---------------使用prpDcompanyGrade获得上级机构-----------end-----
		return list;
	}

	// ---------------同步更新grade表------------------
	@SuppressWarnings("unchecked")
	// private void generatePrpDcompanyGradeInner(PrpDcompany
	// currNode,PrpDcompany prpDcompany){//生成所有直接上下级关系的prpDcompanyGrade 返回直接下级代码
	//
	// String upCode = prpDcompany.getComCode();
	// List list = getSubCode1(upCode);//根据company表获得直接下级代码
	// for (int i = 0; i < list.size(); i++) {
	// PrpDcompany newCompany = (PrpDcompany) list.get(i);
	// insertGrade(currNode,newCompany);//插入数据
	// cont++;
	// logger.debug("【当前代码："+currNode.getComCode()+"||"+currNode.getComCName()+
	// "】【下级代码："+newCompany.getComCode()+"||"+newCompany.getComCName()+"】");
	// logger.debug("当前插入数量"+cont);
	// generatePrpDcompanyGradeInner(currNode,newCompany);//設置級別 并且获得直接下级代码
	// }
	// }
	//	
	// ----------
	public void generatePrpDcompanyGrade(PrpDcompany prpDcompany) {

		// String upCode = prpDcompany.getComCode();
		// if("00000000".equals(prpDcompany.getComCode())){
		// super.getSession().getTransaction().begin();//事物控制提交在每生成一个节点数据提交一次
		// generatePrpDcompanyGradeInner(prpDcompany,prpDcompany);
		// super.getSession().getTransaction().commit();
		// }
		// List list = getSubCode1(upCode);//根据company表获得直接下级代码
		// for (int i = 0; i < list.size(); i++) {
		// PrpDcompany newCompany = (PrpDcompany) list.get(i);
		// super.getSession().getTransaction().begin();
		// generatePrpDcompanyGradeInner(newCompany,newCompany);
		// generatePrpDcompanyGrade (newCompany);//設置級別 并且获得直接下级代码
		// super.getSession().getTransaction().commit();//事物控制提交在每生成一个节点数据提交一次
		// }

		/** 下面是分别生成机构表级别字段1-8级的数据 目前机构级别最多到7级 */
		// generateGradeSt(1);
		// generateGradeSt(2);
		// generateGradeSt(3);
		// generateGradeSt(4);
		// generateGradeSt(5);
		// generateGradeSt(6);
		// generateGradeSt(7);
		// generateGradeSt(8);
	}

	// -------
	/**
	 * 插入prpDcompanyGrade表(废弃，关于PrpDcompanyGrade表全部废弃。)
	 * */
	public void insertGrade(PrpDcompany prpDcompany, PrpDcompany newCompany) {
		// PrpDcompanyGradeService
		// prpDcompanyGradeService=(PrpDcompanyGradeService)
		// applicationContext.getBean("prpDcompanyGradeService");
		// prpDcompanyGradeService.insertGrade(prpDcompany,newCompany);
	}

	/********************************* 生成prpDcompany表upperPath字段****start *************/
	/**
	 * 生成prpDcompany表中的级别upperPath字段数据
	 * 
	 * @param upperComCode
	 *            当前机构的上级机构代码字段值 uppergrade 当前机构的上级机构的upperPath字段值
	 * @return 当前机构的upperPath字段值
	 */
	private String generateupperPath(String comCode, String upperComCode) {
		String uppergrade = "";
		StringBuffer hql = new StringBuffer();
		StringBuffer grade = new StringBuffer();
		hql.append("select upperPath from PrpDcompany where comCode = '");
		hql.append(upperComCode);
		hql.append("'");
		List<String> upperPaths = super.findByHql(hql.toString());
		if (upperPaths.size() > 0) {
			uppergrade = upperPaths.get(0);
		}
		grade.append(uppergrade);
		// TODO 判断代码是不是总公司代码
		//modify by duanfa start 20110726 总公司改为31000000
		//if (!"00000000".equals(comCode)) {
		if (!"31000000".equals(comCode)) {
		//modify by duanfa end 20110726
			grade.append(",");
			grade.append(comCode);
		}
		return grade.toString();
	}

	/**
	 * 向数据库中添加数据
	 * */
	private void insertprpDcompany(PrpDcompany prpDcompany) {
		String upperPath = generateupperPath(prpDcompany.getComCode(),
				prpDcompany.getUpperComCode());
		prpDcompany.setUpperPath(upperPath);
		super.save(prpDcompany);
	}

	public void generateGradeSt(int level) {
		StringBuffer hql = new StringBuffer();
		hql.append("from PrpDcompany where comlevel = " + level);
		List<PrpDcompany> companyList = super.findByHql(hql.toString());
		for (int i = 0; i < companyList.size(); i++) {
			insertprpDcompany(companyList.get(i));
		}
	}

	/************************** 生成prpDcompany表upperPath字段****end *************/
	/**
	 * 获得直接下级公司信息
	 * */
	public List<PrpDcompany> getSubCode(String upCode) {
		if (upCode == null) {
			List list = new ArrayList();
			return list;
		}
		StringBuffer hql = new StringBuffer();
		hql
				.append("from PrpDcompany prpDcompany where prpDcompany.upperComCode=? and prpDcompany.upperComCode!=prpDcompany.comCode");
		List<PrpDcompany> list = findByHql(hql.toString(), upCode);
		return list;
	}

	/**
	 * 获得直接下级公司信息 添加validStatus控制
	 * */
	public List<PrpDcompany> getSubCode1(String upCode) {
		if (upCode == null)
			return null;
		StringBuffer hql = new StringBuffer();
		hql
				.append("from PrpDcompany prpDcompany where prpDcompany.upperComCode=? and prpDcompany.upperComCode!=prpDcompany.comCode and validStatus=1");
		List<PrpDcompany> list = findByHql(hql.toString(), upCode);
		return list;
	}
	public List getSubSystemListByParentId(String parentId,String userCode)throws Exception{
		List<UtiIUserVO> utiIUserVOList = new ArrayList<UtiIUserVO>(0);
		StringBuffer hql = new StringBuffer();
		String con = addPower(userCode);
		hql.append(" select prpDcompany from PrpDcompany prpDcompany where prpDcompany.upperComCode = ? and prpDcompany.upperComCode!=prpDcompany.comCode and ");
		hql.append(con);
		List<PrpDcompany> prpdCompanyList = super.findByHql(hql.toString(), parentId);
		UtiIUserVO utiIUserVO = null;
		PrpDcompany company = null;
		for (int i = 0; i < prpdCompanyList.size(); i++) {
			company = prpdCompanyList.get(i);
			utiIUserVO = new UtiIUserVO();
			utiIUserVO.setComCode(company.getComCode());
			utiIUserVO.setComCName(company.getComCName());
			utiIUserVO.setUpperComCode(company.getUpperComCode());
			utiIUserVOList.add(utiIUserVO);
		}
		return utiIUserVOList;
	}

	/**
	 * 获得所有下级公司信息 2009.7.31 changed by ain 将返回comcode改为返回Prpdcompany
	 * 增加validStatus 条件
	 */
	public List<PrpDcompany> getAllSubCompany(String comCode) {
		logger.debug("▲getAllSubComCode");
		List<PrpDcompany> list = new ArrayList();
		StringBuffer hql = new StringBuffer();
		/************* 通过upperPath获得商机机构*************start *************/
		String upperPath = getAllUpperComCodes(comCode); // 获得所有上级机构，包括当前机构
		if("".equals(upperPath)){
			return list;
		}
		hql.append("from PrpDcompany o where o.upperPath like '").append(
				upperPath)
				.append("%' and o.validStatus = 1 and o.comCode <> '").append(
						comCode).append("'");
		list = findByHql(hql.toString());
		/**************** 通过upperPath获得商机机构************end *****/
//--------------------------------------------------------------------
		/** 使用prpDcompanyGrade表查询直接下级机构方法***********start */
		// hql.append(
		// "from PrpDcompany o where o.comCode in (select t.id.subComCode from PrpDcompanyGrade t where t.id.comCode = ?  and validStatus= ?)"
		// );//使用prpDcompanyGrade表查询直接下级机构方法
		// list = findByHql(hql.toString(), comCode
		// ,"1");//使用prpDcompanyGrade表查询直接下级机构方法
		/** 使用prpDcompanyGrade表查询直接下级机构方法***********end */
		return list;
	}

	/**
	 * 获得所有上级机构，包括当前机构 
	 * 
	 * @param comCode
	 *            当前机构代码
	 * @return
	 */
	private String getAllUpperComCodes(String comCode) {
		List list = new ArrayList();
		StringBuffer hql = new StringBuffer();
		String codes = "";
		hql.append("select upperPath from PrpDcompany where comCode = '")
				.append(comCode).append("'");
		list = super.findByHql(hql.toString());
		if (list.size() > 0) {
			codes = (String) list.get(0);
		}
		return codes;
	}

	/**
	 * 获得当前机构的级别
	 * */
	public int getLv(PrpDcompany prpDcompany) {
		int i = 1;
		while (getUpprpDcompany(prpDcompany) != null) {
			i++;
			PrpDcompany temp = getUpprpDcompany(prpDcompany);
			prpDcompany = temp;
		}
		return i;
	}

	/**
	 * 获得直接上级公司信息,通过参数prpDcompany获得上级代码的comcode 如果为顶级机构则返回null
	 * */
	@SuppressWarnings("unchecked")
	public PrpDcompany getUpprpDcompany(PrpDcompany prpDcompany) {
		StringBuffer hql = new StringBuffer();
		if (prpDcompany.getUpperComCode() != null
				&& !prpDcompany.getUpperComCode().equals(
						prpDcompany.getComCode())) {
			PrpDcompany uperCompany = super.get(prpDcompany.getUpperComCode());
			return uperCompany;
		} else {
			return null;
		}
	}

	// ------------------同步更新grade表---------------
	/** 是否存在用户 存在 返回false */
	public boolean isHadUser(String comCode) {
		/** 屏蔽 删除机构操作时 判断机构下是否存在用户信息 等用户开发新接口后使用 start 2009-10-28 */
		// String hql = "from UtiIUser u where u.comCode = ?";
		// List list = super.findByHql(hql, comCode);
		// if(list.size()> 0){
		// return false;
		// }
		/** 屏蔽 删除机构操作时 判断机构下是否存在用户信息 等开发新接口后使用 end 2009-10-28 */
		return true;
	}
	
	public  String addPower(String userCode) throws Exception{
		SaaAPIService saaAPIService = new SaaAPIServiceImpl();
		String condition =  saaAPIService.addPower(IConstants.SVRCODE, userCode, IConstants.SEARCH_COMCODE, IConstants.PRPDCOMPANY_BM, "", "");
		if(!"".equals(condition))
			return condition;
		else
			return " 1 != 1";
	}
	public void prpDcompanyMessageProcess(PrpDcompany prpDcompany)throws Exception{
		if (prpDcompany != null) {
			try {				
				super.save(prpDcompany);
				String utilityflag = ReadProperties.getString("utilityflag");
				if(utilityflag.equals(SyncConstants.UtilityFlag)){
					PrpDcompanyExpansion prpDcompanyExpansion = new PrpDcompanyExpansion();
					prpDcompanyExpansion.setComCode(prpDcompany.getComCode());
					prpDcompanyExpansion.setComCodeCIRC(prpDcompany.getComCodeCIRC());
					prpDcompanyExpansion.setLicenseNo(prpDcompany.getLicenseNo());
					prpDcompanyExpansion.setEmail(prpDcompany.getEmail());
					prpDcompanyExpansion.setRemark(prpDcompany.getRemark());
					addMessageToOldPrpDcompany(prpDcompany);//向二代库中的prpdcompany表中添加信息 by wanghaibo
					addMessageToOldPrpDcompanyExpansion(prpDcompanyExpansion);//向二代库中的prpdcompanyexpansion表中添加信息 by wanghaibo2010-7-15
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception(e.getMessage());
			}
		}
	}
	/**
	 * 在增加下级机构的同时，把相应数据同步到工具库的prpdcompany表中
	 * 原有JDBC方式从system.properties文件获得url,user,password。
	 * 修改为使用JDNI获得weblogic中配置的数据源方式 modfiy by wanghaibo 2010-12-13
	 * Class.forName("com.informix.jdbc.IfxDriver").newInstance(); 
					String url = ReadProperties.getString("prpDcompanyUrl");
					String user = ReadProperties.getString("dbuser");
					String password = ReadProperties.getString("dbpassword");
					conn= DriverManager.getConnection(url,user,password);
	  修改为
	  ctx = new InitialContext(); 
		    DataSource ds = (DataSource)ctx.lookup("piccutilityDataSource");//JNDI名 
		    conn = ds.getConnection();
	 */
  	public  void addMessageToOldPrpDcompany(PrpDcompany prpDcompany)throws Exception{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Context ctx = null;
		try {
			ctx = new InitialContext(); 
		    DataSource ds = (DataSource)ctx.lookup("piccutilityDataSource");//JNDI名 
		    conn = ds.getConnection(); 
			String sql_query = "select * from prpdcompany where comcode = '" + prpDcompany.getComCode() + "';";
			ps = conn.prepareStatement(sql_query);
			rs = ps.executeQuery();
			if (!rs.next()) {
				String sql = "insert into prpdcompany(comcode,comcname,comename,addresscname,addressename,postcode,phonenumber,faxnumber,uppercomcode,insurername,comtype,manager,accountant,remark,newcomcode,validstatus,acntunit,articlecode,flag) " +
				"values ('" + prpDcompany.getComCode()+ "','" + prpDcompany.getComCName() + "','" + prpDcompany.getComEName() + "','" + prpDcompany.getAddressCName()+ "','" + prpDcompany.getAddressEName() + "','" + prpDcompany.getPostCode() + "','" + 
				prpDcompany.getPhoneNumber() + "','" + prpDcompany.getFaxNumber()+ "','"+ prpDcompany.getUpperComCode()+"','" + prpDcompany.getInsurerName()+"','"+prpDcompany.getComType()+"','"+prpDcompany.getManager()+"','"+prpDcompany.getAccountant()
				+"','"+prpDcompany.getRemark()+"','"+prpDcompany.getNewComCode()+"','"+prpDcompany.getValidStatus()+"','"+prpDcompany.getAcntUnit()+"','"+prpDcompany.getArticleCode()+"','"+prpDcompany.getFlag()+"');";				
				ps = conn.prepareStatement(sql);
				int num = ps.executeUpdate();
				if (num > 0) {
					logger.info("插入数据库成功！");
				}else {
					logger.info("插入数据库失败！");
				}
			}
		} catch (Exception e) {
//			e.printStackTrace();
			throw new Exception("没有配置同步二代工具库数据源,请确认！");
		}finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
				if (ctx!= null){
					ctx.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
				throw new Exception(e2.getMessage());
			}
		}
	}
		/**
		 * 在增加下级机构的同时，把相应数据同步到工具库的prpdcompanyexpansion表中
		 */
		public  void addMessageToOldPrpDcompanyExpansion(PrpDcompanyExpansion prpDcompanyExpansion)throws Exception{
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			Context ctx = null;
			try {
				ctx = new InitialContext(); 
			    DataSource ds = (DataSource)ctx.lookup("piccutilityDataSource");//JNDI名 
			    conn = ds.getConnection();
				String sql_query = "select * from prpdcompanyexpansion where comcode = '" + prpDcompanyExpansion.getComCode() + "';";
				ps = conn.prepareStatement(sql_query);
				rs = ps.executeQuery();
				if (!rs.next()) {
					String sql = "insert into prpdcompanyexpansion(comcode,comcodecirc,licenseno,email,remark) " +
					"values ('" + prpDcompanyExpansion.getComCode()+ "','" + prpDcompanyExpansion.getComCodeCIRC() + "','" + prpDcompanyExpansion.getLicenseNo()+ "','" + prpDcompanyExpansion.getEmail()+ "','" + prpDcompanyExpansion.getRemark()+"');";
					ps = conn.prepareStatement(sql);
					int num = ps.executeUpdate();
					if (num > 0) {
						logger.info("插入数据库成功！");
					}else {
						logger.info("插入数据库失败！");
					}
				}else {
					logger.info("机构代码：" + prpDcompanyExpansion.getComCode() + " 在工具库中已经存在！");
				}
			} catch (Exception e) {
//				e.printStackTrace();
				throw new Exception("没有配置同步二代工具库数据源,请确认！");
			}finally {
				try {
					if (ps != null) {
						ps.close();
					}
					if (conn != null) {
						conn.close();
					}
					if (ctx!= null){
						ctx.close();
					}
				} catch (Exception e2) {
					e2.printStackTrace();
					throw new Exception(e2.getMessage());
				}		
			}
		}
			/**
			 * 在修改下级机构的同时，把相应数据同步到工具库的prpdcompany表中
			 */
			public void updateMessageToOldPrpDcompany(PrpDcompany prpDcompany)throws Exception{
			Connection conn = null;
			PreparedStatement ps = null;
			Context ctx = null;
			try {
				ctx = new InitialContext(); 
			    DataSource ds = (DataSource)ctx.lookup("piccutilityDataSource");//JNDI名 
			    conn = ds.getConnection();
				String sql_query = " update prpdcompany set comcname = '" + prpDcompany.getComCName() + "',comename = '" + prpDcompany.getComEName() + "',addresscname = '" + prpDcompany.getAddressCName()+ "',addressename = '" + prpDcompany.getAddressEName() + "',postcode= '" + prpDcompany.getPostCode() + "',phonenumber = '" + 
				prpDcompany.getPhoneNumber() + "',faxnumber = '" + prpDcompany.getFaxNumber()+ "',uppercomcode ='" + prpDcompany.getUpperComCode()+ "',insurername = '" + prpDcompany.getInsurerName()+ "',comtype ='" + prpDcompany.getComType()+ "' ,manager = '" +prpDcompany.getManager()+ "',accountant = '" +prpDcompany.getAccountant() + "',remark = '" +
				prpDcompany.getRemark()+ "',newcomcode = '" +prpDcompany.getNewComCode()+ "',validstatus ='" +prpDcompany.getValidStatus()+ "' ,acntunit = '" +prpDcompany.getAcntUnit()+ "',articlecode = '" +prpDcompany.getArticleCode()+ "',flag = '" +prpDcompany.getFlag()+ "' where comcode = '" +prpDcompany.getComCode()+"'" ;
				ps = conn.prepareStatement(sql_query);
				int num = ps.executeUpdate();
				if (num > 0) {
					logger.info("修改数据成功！");
				}else {
					logger.info("修改数据失败！");
				}
			} catch (Exception e) {
//				e.printStackTrace();
				throw new Exception("没有配置同步二代工具库数据源,请确认！");
			}finally {
				try {
					if (ps != null) {
						ps.close();
					}
					if (conn != null) {
						conn.close();
					}
					if (ctx!= null){
						ctx.close();
					}
				} catch (Exception e2) {
					e2.printStackTrace();
					throw new Exception(e2.getMessage());
				}		
			}
		}
		/**
		 * 在修改下级机构的同时，把相应数据同步到工具库的prpdcompanyexpansion表中
		 */	
			public void updateMessageToOldPrpDcompanyExpansion(PrpDcompanyExpansion prpDcompanyExpansion)throws Exception{
				Connection conn = null;
				PreparedStatement ps = null;
				Context ctx = null;
				try {
					ctx = new InitialContext(); 
				    DataSource ds = (DataSource)ctx.lookup("piccutilityDataSource");//JNDI名 
				    conn = ds.getConnection();
					String sql_query = " update prpdcompanyexpansion set comcode = '" + prpDcompanyExpansion.getComCode() + "',comcodecirc = '" + prpDcompanyExpansion.getComCodeCIRC()+ "',licenseno = '" + prpDcompanyExpansion.getLicenseNo()+ "',email = '" + prpDcompanyExpansion.getEmail()+ "',remark= '" + prpDcompanyExpansion.getRemark()+"' where comcode = '" +prpDcompanyExpansion.getComCode()+"'" ;
					ps = conn.prepareStatement(sql_query);
					int num = ps.executeUpdate();
					if (num > 0) {
						logger.info("修改数据成功！");
					}else {
						logger.info("修改数据失败！");
					}
				} catch (Exception e) {
//					e.printStackTrace();
					throw new Exception("没有配置同步二代工具库数据源,请确认！");
				}finally {
					try {
						if (ps != null) {
							ps.close();
						}
						if (conn != null) {
							conn.close();
						}
						if (ctx!= null){
							ctx.close();
						}
					} catch (Exception e2) {
						e2.printStackTrace();
						throw new Exception(e2.getMessage());
					}		
				}
			}		
     }