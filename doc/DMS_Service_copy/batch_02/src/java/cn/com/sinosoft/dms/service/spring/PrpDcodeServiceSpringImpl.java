package cn.com.sinosoft.dms.service.spring;

import ins.framework.common.Page;
import ins.framework.common.ServiceFactory;
import ins.framework.dao.GenericDaoHibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.sinosoft.common.model.InputBean;
import cn.com.sinosoft.dms.model.PrpDcode;
import cn.com.sinosoft.dms.model.PrpDcodeId;
import cn.com.sinosoft.dms.model.PrpDnewCode;
import cn.com.sinosoft.dms.model.PrpDnewCodeCom;
import cn.com.sinosoft.dms.model.PrpDnewCodeId;
import cn.com.sinosoft.dms.service.facade.CheckSameKeyService;
import cn.com.sinosoft.dms.service.facade.PrpDcodeService;
import cn.com.sinosoft.ims.log.model.UtiISyncLog;
import cn.com.sinosoft.ims.log.service.facade.UtiISyncLogService;
import cn.com.sinosoft.ims.sync.HDMessageProducer;
import cn.com.sinosoft.ims.sync.SyncConstants;
import cn.com.sinosoft.ims.util.ReadProperties;
import cn.com.sinosoft.saa.util.HqlRulesUtil;

public class PrpDcodeServiceSpringImpl extends
		GenericDaoHibernate<PrpDnewCode, PrpDnewCodeId> implements PrpDcodeService {

	public void deleteByPK(PrpDnewCodeId PK) {
		super.delete(PK);
	}
	/**
	 * 删除多条数据时有可能更改list中将要删除代码的代码结构 ，会影响吗？？
	 * */
	public void deleteAll(List list){
		if(list!=null&&list.size()!=0){
			for(int i=0;i<list.size();i++){
				try {
					deletePrpDcode((PrpDnewCode) list.get(i));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	@SuppressWarnings("unchecked")
	public void deletePrpDcode(PrpDnewCode prpDcode) throws Exception {
		// ----------update reference
		// code-------如果删除一条数据，prpDcode表中对应的代码级别中有这条记录的也要做更新
		// 。(上提一个级别)-------------
		/**
		 **** 去掉此代码   不适用此逻辑 start  2010-1-13****************************
		StringBuffer hql = new StringBuffer();
		hql
				.append(" from PrpDcode prpDcode where (prpDcode.codeCode1=? or prpDcode.codeCode2=? or prpDcode.codeCode3=? or prpDcode.codeCode4=? or prpDcode.codeCode5=?) and prpDcode.id.codeType=?");
		List<PrpDcode> list = super.findByHql(hql.toString(), prpDcode.getId()
				.getCodeCode(), prpDcode.getId().getCodeCode(), prpDcode
				.getId().getCodeCode(), prpDcode.getId().getCodeCode(),
				prpDcode.getId().getCodeCode(), prpDcode.getId().getCodeType());
		Iterator iterator = list.iterator();
		
		while (iterator.hasNext()) {
			PrpDcode temp = new PrpDcode();
			PrpDcode changeprpDcode = (PrpDcode) iterator.next();
			int changelv = getlv(changeprpDcode, prpDcode.getId()
					.getCodeCode());
			if (changelv == 1) {
				temp.setCodeCode1(changeprpDcode.getCodeCode2());
				temp.setCodeCode2(changeprpDcode.getCodeCode3());
				temp.setCodeCode3(changeprpDcode.getCodeCode4());
				temp.setCodeCode4(changeprpDcode.getCodeCode5());
				temp.setCodeCode5("");
			} else if (changelv == 2) {
				temp.setCodeCode1(changeprpDcode.getCodeCode1());
				temp.setCodeCode2(changeprpDcode.getCodeCode3());
				temp.setCodeCode3(changeprpDcode.getCodeCode4());
				temp.setCodeCode4(changeprpDcode.getCodeCode5());
				temp.setCodeCode5("");
			} else if (changelv == 3) {
				temp.setCodeCode1(changeprpDcode.getCodeCode1());
				temp.setCodeCode2(changeprpDcode.getCodeCode2());
				temp.setCodeCode3(changeprpDcode.getCodeCode4());
				temp.setCodeCode4(changeprpDcode.getCodeCode5());
				temp.setCodeCode5("");
			} else if (changelv == 4) {
				temp.setCodeCode1(changeprpDcode.getCodeCode1());
				temp.setCodeCode2(changeprpDcode.getCodeCode2());
				temp.setCodeCode3(changeprpDcode.getCodeCode3());
				temp.setCodeCode4(changeprpDcode.getCodeCode5());
				temp.setCodeCode5("");
			} else if (changelv == 5) {
				temp.setCodeCode1(changeprpDcode.getCodeCode1());
				temp.setCodeCode2(changeprpDcode.getCodeCode2());
				temp.setCodeCode3(changeprpDcode.getCodeCode3());
				temp.setCodeCode4(changeprpDcode.getCodeCode4());
				temp.setCodeCode5("");
			}
			changeprpDcode.setCodeCode1(temp.getCodeCode1());
			changeprpDcode.setCodeCode2(temp.getCodeCode2());
			changeprpDcode.setCodeCode3(temp.getCodeCode3());
			changeprpDcode.setCodeCode4(temp.getCodeCode4());
			changeprpDcode.setCodeCode5(temp.getCodeCode5());

			super.update(changeprpDcode);

			// ----------delete reference code--------------------
		}
		* ************************不适用此逻辑 end  2010-1-13***********/
		super.delete(prpDcode);
	}

	public Page getPrpDcodeList(PrpDnewCode prpDcode,int pageNo, int pageSize){
		StringBuffer hql = new StringBuffer();
		hql.append(" from PrpDnewCode prpDcode where 1=1");
		HqlRulesUtil hqlRules = new HqlRulesUtil();
		hqlRules.addEqual("prpDcode.id.codeType", prpDcode.getId().getCodeType());
		hqlRules
				.addLike("prpDcode.id.codeCode", prpDcode.getId().getCodeCode());
		hqlRules
				.addLike("prpDcode.codeCName", prpDcode.getCodeCName());
		if (hqlRules.getHql().trim() != null
				&& !hqlRules.getHql().trim().equals("")) {
			hql.append("and " + hqlRules.getHql());
		}
		logger.debug("!!!!!!!!!!!!" + hql.toString());
		Page page = findByHql(hql.toString(), pageNo, pageSize);
		return page;
	}

	public void insertPrpDcode(PrpDnewCode prpDcode,String userCode) {
		super.save(prpDcode);
		PrpDcode prpdcode = new PrpDcode();// 增加PrpDnewCode的同时，保存PrpDcode到总公司库majordmsdb中start...
		PrpDcodeId prpdcodeId = new PrpDcodeId();
		prpdcodeId.setCodeType(prpDcode.getId().getCodeType());
		prpdcodeId.setCodeCode(prpDcode.getId().getCodeCode());
		prpdcode.setId(prpdcodeId);
		prpdcode.setCodeCName(prpDcode.getCodeCName());
		prpdcode.setCodeEName(prpDcode.getCodeEName());
		prpdcode.setNewCodeCode(prpDcode.getNewCodeCode());
		prpdcode.setValidStatus(prpDcode.getValidStatus());
		prpdcode.setFlag(prpDcode.getFlag());
		super.save(prpdcode); //end...
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
			 for (String comCode : strOnlineCom) {	
				 utiISyncLog = new UtiISyncLog();
				 utiISyncLog.setId(id);
				 utiISyncLog.setClassName(SyncConstants.RequestFlag_PrpDcodeMaintain);
				 utiISyncLog.setDestComCode(comCode);
				 utiISyncLog.setEditType(SyncConstants.RequestFlag_EditTypeAdd);
				 utiISyncLog.setOperUserCode(userCode);
				 utiISyncLog.setReplayTimes(0);
				 utiISyncLog.setSendDate(new Date());
				 utiISyncLog.setLastSendDate(new Date());
				 utiISyncLog.setStrKey("id.codeType = '" + prpDcode.getId().getCodeType() + "' and id.codeCode = '" + prpDcode.getId().getCodeCode() + "'");
				 utiISyncLogList.add(utiISyncLog);
				 id++;
			}
			 utiISyncLogService.insertAllUtiISyncLog(utiISyncLogList);
			 if (utiISyncLogList.size() > 0) {
				HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
				.getService("messageProducer");// 获得Spring管理的bean
				InputBean inputBean = new InputBean();
				inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDcodeMaintain);
				inputBean.setUtiISyncLogList(utiISyncLogList);
				inputBean.setPrpDcode(prpDcode);
				inputBean.setDestComCode(SyncConstants.DestComCode_Pub);
				 messageProducer.send(inputBean);
			}
		}
	}
	public void insertPrpDnewCodeCom(PrpDnewCodeCom prpDnewCodeCom, String userCode) {
		super.save(prpDnewCodeCom);
		//JMS 
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
			 for (String comCode : strOnlineCom) {	
				 utiISyncLog = new UtiISyncLog();
				 utiISyncLog.setId(id);
				 utiISyncLog.setClassName(SyncConstants.RequestFlag_PrpDnewCodeComMaintain);
				 utiISyncLog.setDestComCode(comCode);
				 utiISyncLog.setEditType(SyncConstants.RequestFlag_EditTypeAdd);
				 utiISyncLog.setOperUserCode(userCode);
				 utiISyncLog.setReplayTimes(0);
				 utiISyncLog.setSendDate(new Date());
				 utiISyncLog.setLastSendDate(new Date());
				 utiISyncLog.setStrKey("id.comCode = '" + prpDnewCodeCom.getId().getComCode() 
						 +"' and id.codeType = '" + prpDnewCodeCom.getId().getCodeType()
						 +"' and id.codeCode = '"+prpDnewCodeCom.getId().getCodeCode()
						 +"'");
				 utiISyncLogList.add(utiISyncLog);
				 id++;
			}
			 utiISyncLogService.insertAllUtiISyncLog(utiISyncLogList);
			 if (utiISyncLogList.size() > 0) {
				HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
				.getService("messageProducer");// 获得Spring管理的bean
				InputBean inputBean = new InputBean();
				inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDnewCodeComMaintain);
				inputBean.setUtiISyncLogList(utiISyncLogList);
				inputBean.setPrpDnewCodeCom(prpDnewCodeCom);
				inputBean.setDestComCode(prpDnewCodeCom.getId().getComCode());
				 messageProducer.send(inputBean);
			}
		}
	}
	/**
	 * 插入prpDcode upcode是上级代码的codeCode
	 * 
	 * @throws Exception
	 * */
	public void insertPrpDcode(PrpDnewCode prpDcode, String upcode,String userCode)
			throws Exception {
//		PrpDnewCode newPrpDcode = setLevels(prpDcode, upcode);
		super.save(prpDcode);
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
			 for (String comCode : strOnlineCom) {	
				 utiISyncLog = new UtiISyncLog();
				 utiISyncLog.setId(id);
				 utiISyncLog.setClassName(SyncConstants.RequestFlag_PrpDcodeMaintain);
				 utiISyncLog.setDestComCode(comCode);
				 utiISyncLog.setEditType(SyncConstants.RequestFlag_EditTypeAdd);
				 utiISyncLog.setOperUserCode(userCode);
				 utiISyncLog.setReplayTimes(0);
				 utiISyncLog.setSendDate(new Date());
				 utiISyncLog.setLastSendDate(new Date());
				 utiISyncLog.setStrKey("id.codeType = '" + prpDcode.getId().getCodeType() + "' and id.codeCode = '" + prpDcode.getId().getCodeCode() + "'");
				 utiISyncLogList.add(utiISyncLog);
				 id++;
			}
			 utiISyncLogService.insertAllUtiISyncLog(utiISyncLogList);
			 if (utiISyncLogList.size() > 0) {
				HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
				.getService("messageProducer");// 获得Spring管理的bean
				InputBean inputBean = new InputBean();
				inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDcodeMaintain);
				 inputBean.setUtiISyncLogList(utiISyncLogList);
				 inputBean.setPrpDcode(prpDcode);
				 inputBean.setDestComCode(SyncConstants.DestComCode_Pub);
				 messageProducer.send(inputBean);
			}
		}
	}

	public void updatePrpDcode(PrpDnewCode prpDcode,String userCode) {
		super.update(prpDcode);
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
			 for (String comCode : strOnlineCom) {	
				 utiISyncLog = new UtiISyncLog();
				 utiISyncLog.setId(id);
				 utiISyncLog.setClassName(SyncConstants.RequestFlag_PrpDcodeMaintain);
				 utiISyncLog.setDestComCode(comCode);
				 utiISyncLog.setEditType(SyncConstants.RequestFlag_EditTypeModify);
				 utiISyncLog.setOperUserCode(userCode);
				 utiISyncLog.setReplayTimes(0);
				 utiISyncLog.setSendDate(new Date());
				 utiISyncLog.setLastSendDate(new Date());
				 utiISyncLog.setStrKey("id.codeType = '" + prpDcode.getId().getCodeType() + "' and id.codeCode = '" + prpDcode.getId().getCodeCode() + "'");
				 utiISyncLogList.add(utiISyncLog);
				 id++;
			}
			 utiISyncLogService.insertAllUtiISyncLog(utiISyncLogList);
			 if (utiISyncLogList.size() > 0) {
				HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
				.getService("messageProducer");// 获得Spring管理的bean
				InputBean inputBean = new InputBean();
				inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDcodeMaintain);
				 inputBean.setUtiISyncLogList(utiISyncLogList);
				 inputBean.setPrpDcode(prpDcode);
				 inputBean.setDestComCode(SyncConstants.DestComCode_Pub);
				 messageProducer.send(inputBean);
			}
		}
	}

	/**
	 * 更新prpDcode upcode是上级代码的codeCode
	 * 
	 * @throws Exception
	 * */
	@SuppressWarnings("unchecked")
	public void updatePrpDcode(PrpDnewCode prpDcode, String upcode,String userCode)
			throws Exception {
		// TODO 修改prpDcode表时，代码级别的逻辑需要变更
		HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
		.getService("messageProducer");// 获得Spring管理的bean
		InputBean inputBean = null;
		 CheckSameKeyService checkSameKeyService = (CheckSameKeyService) ServiceFactory
	       .getService("checkSameKeyService");// 获得Spring管理的bean		
		 UtiISyncLogService utiISyncLogService = (UtiISyncLogService) ServiceFactory
		 .getService("utiISyncLogService");// 获得Spring管理的bean
//		prpDcode = setLevels(prpDcode, upcode);// 将代码级别set到prpDcode中
		// ----update reference code---update之前需要prpDcode中有当前要更新的代码级别-----
//		StringBuffer hql = new StringBuffer();
//		hql
//				.append(" from PrpDnewCode prpDcode where (prpDcode.codeCode1=? or prpDcode.codeCode2=? or prpDcode.codeCode3=? or prpDcode.codeCode4=? or prpDcode.codeCode5=?) and prpDcode.id.codeType=? and prpDcode.id.codeCode!=?");
//		List<PrpDnewCode> list = super.findByHql(hql.toString(), prpDcode.getId()
//				.getCodeCode(), prpDcode.getId().getCodeCode(), prpDcode
//				.getId().getCodeCode(), prpDcode.getId().getCodeCode(),
//				prpDcode.getId().getCodeCode(), prpDcode.getId().getCodeType(),
//				prpDcode.getId().getCodeCode());

//		int currentlv = getlv(prpDcode, prpDcode.getId().getCodeCode());
//		Iterator iterator = list.iterator();
		UtiISyncLog utiISyncLog = null;
//		while (iterator.hasNext()) {
//			PrpDnewCode changeprpDcode = (PrpDnewCode) iterator.next();
//			int changeLv = getlv(changeprpDcode, prpDcode.getId()
//					.getCodeCode());
//			PrpDnewCode temp = new PrpDnewCode();
//			if (currentlv == 1) {
//				temp.setCodeCode1(prpDcode.getCodeCode1());
//				if (changeLv == 1) {
//					temp.setCodeCode2(changeprpDcode.getCodeCode2());
//					temp.setCodeCode3(changeprpDcode.getCodeCode3());
//					temp.setCodeCode4(changeprpDcode.getCodeCode4());
//					temp.setCodeCode5(changeprpDcode.getCodeCode5());
//				} else if (changeLv == 2) {
//					temp.setCodeCode2(changeprpDcode.getCodeCode3());
//					temp.setCodeCode3(changeprpDcode.getCodeCode4());
//					temp.setCodeCode4(changeprpDcode.getCodeCode5());
//					temp.setCodeCode5("");
//				} else if (changeLv == 3) {
//					temp.setCodeCode2(changeprpDcode.getCodeCode4());
//					temp.setCodeCode3(changeprpDcode.getCodeCode5());
//					temp.setCodeCode4("");
//					temp.setCodeCode5("");
//				} else if (changeLv == 4) {
//					temp.setCodeCode2(changeprpDcode.getCodeCode5());
//					temp.setCodeCode3("");
//					temp.setCodeCode4("");
//					temp.setCodeCode5("");
//				} else if (changeLv == 5) {
//					temp.setCodeCode2("");
//					temp.setCodeCode3("");
//					temp.setCodeCode4("");
//					temp.setCodeCode5("");
//				} else {
//					throw new BusinessException("最多只有五级代码！", false);
//				}
//			}
//			if (currentlv == 2) {
//				temp.setCodeCode1(prpDcode.getCodeCode1());
//				temp.setCodeCode2(prpDcode.getCodeCode2());
//				if (changeLv == 1) {
//					temp.setCodeCode3(changeprpDcode.getCodeCode2());
//					temp.setCodeCode4(changeprpDcode.getCodeCode3());
//					temp.setCodeCode5(changeprpDcode.getCodeCode4());
//				} else if (changeLv == 2) {
//					temp.setCodeCode3(changeprpDcode.getCodeCode3());
//					temp.setCodeCode4(changeprpDcode.getCodeCode4());
//					temp.setCodeCode5(changeprpDcode.getCodeCode5());
//				} else if (changeLv == 3) {
//					temp.setCodeCode3(changeprpDcode.getCodeCode4());
//					temp.setCodeCode4(changeprpDcode.getCodeCode5());
//					temp.setCodeCode5("");
//				} else if (changeLv == 4) {
//					temp.setCodeCode3(changeprpDcode.getCodeCode5());
//					temp.setCodeCode4("");
//					temp.setCodeCode5("");
//				} else if (changeLv == 5) {
//					temp.setCodeCode3("");
//					temp.setCodeCode4("");
//					temp.setCodeCode5("");
//				} else {
//					throw new BusinessException("最多只有五级代码！", false);
//				}
//			}
//
//			if (currentlv == 3) {
//				temp.setCodeCode1(prpDcode.getCodeCode1());
//				temp.setCodeCode2(prpDcode.getCodeCode2());
//				temp.setCodeCode3(prpDcode.getCodeCode3());
//				if (changeLv == 1) {
//					temp.setCodeCode4(changeprpDcode.getCodeCode2());
//					temp.setCodeCode5(changeprpDcode.getCodeCode3());
//				} else if (changeLv == 2) {
//					temp.setCodeCode4(changeprpDcode.getCodeCode3());
//					temp.setCodeCode5(changeprpDcode.getCodeCode4());
//				} else if (changeLv == 3) {
//					temp.setCodeCode4(changeprpDcode.getCodeCode4());
//					temp.setCodeCode5(changeprpDcode.getCodeCode5());
//				} else if (changeLv == 4) {
//					temp.setCodeCode4(changeprpDcode.getCodeCode5());
//					temp.setCodeCode5("");
//				} else if (changeLv == 5) {
//					temp.setCodeCode4("");
//					temp.setCodeCode5("");
//				} else {
//					throw new BusinessException("最多只有五级代码！", false);
//				}
//			}
//
//			if (currentlv == 4) {
//				temp.setCodeCode1(prpDcode.getCodeCode1());
//				temp.setCodeCode2(prpDcode.getCodeCode2());
//				temp.setCodeCode3(prpDcode.getCodeCode3());
//				temp.setCodeCode4(prpDcode.getCodeCode4());
//				if (changeLv == 1) {
//					temp.setCodeCode5(changeprpDcode.getCodeCode2());
//				} else if (changeLv == 2) {
//					temp.setCodeCode5(changeprpDcode.getCodeCode3());
//				} else if (changeLv == 3) {
//					temp.setCodeCode5(changeprpDcode.getCodeCode4());
//				} else if (changeLv == 4) {
//					temp.setCodeCode5(changeprpDcode.getCodeCode5());
//				} else if (changeLv == 5) {
//					temp.setCodeCode5("");
//				} else {
//					throw new BusinessException("最多只有五级代码！", false);
//				}
//			}
//
//			if (currentlv == 5) {
//				temp.setCodeCode1(prpDcode.getCodeCode1());
//				temp.setCodeCode2(prpDcode.getCodeCode2());
//				temp.setCodeCode3(prpDcode.getCodeCode3());
//				temp.setCodeCode4(prpDcode.getCodeCode4());
//				temp.setCodeCode5(prpDcode.getCodeCode5());
//			}
//			changeprpDcode.setCodeCode1(temp.getCodeCode1());
//			changeprpDcode.setCodeCode2(temp.getCodeCode2());
//			changeprpDcode.setCodeCode3(temp.getCodeCode3());
//			changeprpDcode.setCodeCode4(temp.getCodeCode4());
//			changeprpDcode.setCodeCode5(temp.getCodeCode5());
//			super.update(changeprpDcode);
//			
//			inputBean = new InputBean();
//			inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDcodeMaintain);
//			inputBean.setPrpDcode(changeprpDcode);
//			messageProducer.send(inputBean);
//			// ------------------update reference code--------------
//		}
		super.update(prpDcode);
		String syncflag = ReadProperties.getString("syncflag");
		if(syncflag.equals(SyncConstants.SyncFlag)){
			String onlineCom = ReadProperties.getString("onlineCom");
			String[] strOnlineCom = onlineCom.split(",");
			List<UtiISyncLog> utiISyncLogList = new ArrayList<UtiISyncLog>();			
			Long id = checkSameKeyService.getMaxId("UtiISyncLog", "id");
			for (String comCode : strOnlineCom) {
				utiISyncLog = new UtiISyncLog();
				utiISyncLog.setId(id);
				utiISyncLog
						.setClassName(SyncConstants.RequestFlag_PrpDcodeMaintain);
				utiISyncLog.setDestComCode(comCode);
				utiISyncLog.setEditType(SyncConstants.RequestFlag_EditTypeModify);
				utiISyncLog.setOperUserCode(userCode);
				utiISyncLog.setReplayTimes(0);
				utiISyncLog.setSendDate(new Date());
				utiISyncLog.setLastSendDate(new Date());
				utiISyncLog.setStrKey("id.codeType = '"
						+ prpDcode.getId().getCodeType() + "' and id.codeCode = '"
						+ prpDcode.getId().getCodeCode() + "'");
				utiISyncLogList.add(utiISyncLog);
				id++;
			}
			utiISyncLogService.insertAllUtiISyncLog(utiISyncLogList);
			if (utiISyncLogList.size() > 0) {
				inputBean = new InputBean();
				inputBean
						.setRequestFlag(SyncConstants.RequestFlag_PrpDcodeMaintain);
				inputBean.setUtiISyncLogList(utiISyncLogList);
				inputBean.setPrpDcode(prpDcode);
				inputBean.setDestComCode(SyncConstants.DestComCode_Pub);
				messageProducer.send(inputBean);
			}
		}
	}

	public PrpDnewCode findByPrimaryKey(PrpDnewCodeId prpDcodeId) {
		return super.get(prpDcodeId);
	}
	public PrpDnewCode findByPrimaryKey1(PrpDnewCodeId prpDcodeId) {
		String hql="from PrpDnewCode prpDcode where prpDcode.id.codeType=? and prpDcode.id.codeCode=? and validStatus=1";
		List list = new ArrayList();
		list =  super.findByHql(hql, prpDcodeId.getCodeType(),prpDcodeId.getCodeCode());
		if(list.size()!=0){
			return (PrpDnewCode) list.get(0);
		}else{
			return null;
		}
	}

	/**
	 * 获得上级代码 upcode是上级代码的codeCode
	 * */
	public PrpDnewCode getUpcode(String upcode, String codeType) {
		PrpDnewCodeId id = new PrpDnewCodeId();
		id.setCodeCode(upcode);
		id.setCodeType(codeType);
		PrpDnewCode upprpDcode = super.get(id);
		return upprpDcode;
	}

	/**
	 * 获得代码级别codeCode1,codeCode2...codeCode5 并且set到prpDcode中
	 * 
	 * @throws Exception
	 * 
	 * */
//	private PrpDnewCode setLevels(PrpDnewCode prpDcode, String upcode)
//			throws Exception {
//		int uplv = 0;
//		int currlv = 0;
//		PrpDnewCode upprpDcode = getUpcode(upcode, prpDcode.getId().getCodeType());
////		PrpDcode newprpDcode = super.get(prpDcode.getId());
//		currlv = getlv(prpDcode, prpDcode.getId().getCodeCode());// 获得当前代码级别
//		PrpDnewCode temp = new PrpDnewCode();
//		if (upcode == null || upcode.equals("")) {// 如果upcode上级代码为空（用户每输入上级代码，
//			// 则此代码为一级代码）
//			temp.setCodeCode1(prpDcode.getId().getCodeCode());
//			temp.setCodeCode2("");
//			temp.setCodeCode3("");
//			temp.setCodeCode4("");
//			temp.setCodeCode5("");
//		} else {
//			uplv = getlv(upprpDcode, upcode);// 获得上级代码的级别
//			if (uplv == 1) {
//				temp.setCodeCode1(upprpDcode.getCodeCode1());
//				temp.setCodeCode2(prpDcode.getId().getCodeCode());
//				temp.setCodeCode3("");
//				temp.setCodeCode4("");
//				temp.setCodeCode5("");
//			} else if (uplv == 2) {
//				temp.setCodeCode1(upprpDcode.getCodeCode1());
//				temp.setCodeCode2(upprpDcode.getCodeCode2());
//				temp.setCodeCode3(prpDcode.getId().getCodeCode());
//				temp.setCodeCode4("");
//				temp.setCodeCode5("");
//			} else if (uplv == 3) {
//				temp.setCodeCode1(upprpDcode.getCodeCode1());
//				temp.setCodeCode2(upprpDcode.getCodeCode2());
//				temp.setCodeCode3(upprpDcode.getCodeCode3());
//				temp.setCodeCode4(prpDcode.getId().getCodeCode());
//				temp.setCodeCode5("");
//			} else if (uplv == 4) {
//				temp.setCodeCode1(upprpDcode.getCodeCode1());
//				temp.setCodeCode2(upprpDcode.getCodeCode2());
//				temp.setCodeCode3(upprpDcode.getCodeCode3());
//				temp.setCodeCode4(upprpDcode.getCodeCode4());
//				temp.setCodeCode5(prpDcode.getId().getCodeCode());
//			} else if (uplv == 5) {
//				throw new BusinessException("目前只支持5级代码！", false);
//			}
//		}
//		prpDcode.setCodeCode1(temp.getCodeCode1());
//		prpDcode.setCodeCode2(temp.getCodeCode2());
//		prpDcode.setCodeCode3(temp.getCodeCode3());
//		prpDcode.setCodeCode4(temp.getCodeCode4());
//		prpDcode.setCodeCode5(temp.getCodeCode5());
//		return prpDcode;
//	}

	/**
	 * 获得代码级别 1：一级代码；2：二级代码...5：五级代码 ；-1：else
	 * 
	 * @throws Exception
	 * */
//	private int getlv(PrpDnewCode prpDcode, String code) throws Exception {
//		if (prpDcode == null || prpDcode.getCodeCode1() == null) {
//			return 1;
//		} else if (prpDcode.getCodeCode1().equals(code)
//				|| prpDcode.getCodeCode1().equals("")) {// codeCode1是1或者为空为一级代码
//			return 1;
//		} else if (prpDcode.getCodeCode2().equals(code)) {
//			return 2;
//		} else if (prpDcode.getCodeCode3().equals(code)) {
//			return 3;
//		} else if (prpDcode.getCodeCode4().equals(code)) {
//			return 4;
//		} else if (prpDcode.getCodeCode5().equals(code)) {
//			return 5;
//		} else {
//			throw new BusinessException("目前只支持5级代码！", false);// 五级代码以上设计的数据库表不支持抛出异常。
//		}
//	}

	@SuppressWarnings("unchecked")
	public List<PrpDnewCode> findByHql(String string, String codeType) {
		return super.findByHql(string, codeType);
	}

	/**
	 * 返回key：代码，value：代码名称 的map集合
	 * */
	public Map<String, String> upCodeMap(String codeType) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from PrpDnewCode prpDcode where 1=1 ");
		HqlRulesUtil hqlRules = new HqlRulesUtil();
		hqlRules.addEqual("prpDcode.id.codeType", codeType);
		if (hqlRules.getHql().trim() != null
				&& !hqlRules.getHql().trim().equals("")) {
			hql.append("and " + hqlRules.getHql());
		}
		logger.debug("!!!!!!!!!!!!" + hql.toString());
		List list = super.findByHql(hql.toString());
		Map<String, String> map = new HashMap<String, String>();
		map.put("", "请选择");
		for (int i = 0; i < list.size(); i++) {
			PrpDnewCode prpDcode = (PrpDnewCode) list.get(i);
			map.put(prpDcode.getId().getCodeCode(), prpDcode.getCodeCName());
		}
		return map;
	}

	/**
	 * 获得上级代码的codeCode
	 * 
	 * @throws Exception
	 * */
	public String getuplevel(PrpDnewCodeId prpDcodeId) throws Exception {
//		String hql = "from PrpDnewCode prpDcode where prpDcode.id.codeCode = ? and prpDcode.id.codeType = ?";
		String hql = "from PrpDnewCode prpDcode where prpDcode.id.codeCode = ? and prpDcode.id.codeType = ?";
		List<PrpDnewCode> list = super.findByHql(hql, prpDcodeId.getCodeCode(),prpDcodeId.getCodeType());
		PrpDnewCode prpDcode = null;
		if(list.size() > 0){
			prpDcode = list.get(0);
			return prpDcode.getUpperCode();
//			if (prpDcode.getCodeCode1() == null
//					|| prpDcode.getCodeCode1().equals("")
//					|| prpDcode.getCodeCode1().equals(prpDcodeId.getCodeCode())) {
//				return "";
//			} else if (prpDcode.getCodeCode2()==null||prpDcode.getCodeCode2().equals(prpDcodeId.getCodeCode())) {
//				return prpDcode.getCodeCode1();
//			} else if (prpDcode.getCodeCode3()==null||prpDcode.getCodeCode3().equals(prpDcodeId.getCodeCode())) {
//				return prpDcode.getCodeCode2();
//			} else if (prpDcode.getCodeCode4()==null||prpDcode.getCodeCode4().equals(prpDcodeId.getCodeCode())) {
//				return prpDcode.getCodeCode3();
//			} else if (prpDcode.getCodeCode5()==null||prpDcode.getCodeCode5().equals(prpDcodeId.getCodeCode())) {
//				return prpDcode.getCodeCode4();
//			} else {
//				throw new BusinessException("目前只支持5级代码！", false);
//			}
		}else{
			return null;
		}
	}
	
	//获得所有直接下级代码 changed by hualimin 2009-8-9
	public List<PrpDnewCode> getSubCode(String codeType, String codeCode){
		String hql = "from PrpDnewCode prpDcode where prpDcode.upperCode = ? and prpDcode.id.codeType = ?";
		List<PrpDnewCode> list = super.findByHql(hql, codeCode,codeType);
//		if(list.size() > 0){
//			PrpDnewCode prpDcode = list.get(0);
//			if(codeCode.equals(prpDcode.getCodeCode1())){
//				hql = "from PrpDnewCode prpDcode where prpDcode.id.codeCode <> ? and prpDcode.codeCode1 = ? and prpDcode.id.codeType = ? and (prpDcode.codeCode2!='' or prpDcode.codeCode2!=null) and (prpDcode.codeCode3='' or prpDcode.codeCode3=null) and validStatus=1";
//				list = super.findByHql(hql,codeCode,codeCode,codeType);
//				return list;
//			}else if(codeCode.equals(prpDcode.getCodeCode2())){
//				hql = "from PrpDnewCode prpDcode where prpDcode.id.codeCode <> ? prpDcode.codeCode2 = ? and prpDcode.id.codeType = ? and (prpDcode.codeCode4='' or prpDcode.codeCode4=null) and validStatus=1";
//				list = super.findByHql(hql,codeCode,codeCode,codeType);
//				return list;
//			}else if(codeCode.equals(prpDcode.getCodeCode3())){
//				hql = "from PrpDnewCode prpDcode where prpDcode.id.codeCode <> ? prpDcode.codeCode3 = ? and prpDcode.id.codeType = ? and (prpDcode.codeCode4!='' or prpDcode.codeCode4!=null) and (prpDcode.codeCode5='' or prpDcode.codecode5=null) and validStatus=1";
//				list = super.findByHql(hql,codeCode,codeCode,codeType);
//				return list;
//			}else if(codeCode.equals(prpDcode.getCodeCode4())){
//				hql = "from PrpDnewCode prpDcode where prpDcode.id.codeCode <> ? prpDcode.codeCode4 = ? and prpDcode.id.codeType = ? and (prpDcode.codeCode5!='' or prpDcode.codeCode5!=null)) and validStatus=1";
//				list = super.findByHql(hql,codeCode,codeCode,codeType);
//				return list;
//			}
//		}
		return list;
	}

	public List codeList(String codeType) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from PrpDnewCode prpDcode where prpDcode.id.codeType = ? ");

		List<PrpDnewCode> list = super.findByHql(hql.toString(),codeType);
		return list;
	}
//	public String getCodeLevel(String systemCode, String codeType,
//			String codeCode) {
//		String hql = "from PrpDnewCode p where p.id.codeType = ? and p.id.codeCode = ?";
//		List<PrpDnewCode> list = super.findByHql(hql,codeType,codeCode);
//		PrpDnewCode prpDcode = null;
//		if(list.size() > 0){
//			prpDcode = list.get(0);
//		}
//		if(prpDcode != null){
//			if(prpDcode.getCodeCode1().equals(codeCode)){
//				return "1";
//			}else if(prpDcode.getCodeCode2().equals(codeCode)){
//				return "2";
//			}else if(prpDcode.getCodeCode3().equals(codeCode)){
//				return "3";
//			}else if(prpDcode.getCodeCode4().equals(codeCode)){
//				return "4";
//			}else if(prpDcode.getCodeCode5().equals(codeCode)){
//				return "5";
//			}
//		}
//		return null;
//	}
	/**
	 *  总颁代码获取清分内容，在branchdmsdb中保存prpdnewcode，同时保存prpdcode。
	 */
	public void prpdCodeMessageProcess(PrpDnewCode prpDcode)throws Exception{
		if (prpDcode != null) {
			try {
				super.save(prpDcode);
				PrpDcode prpdcode = new PrpDcode(); // 增加PrpDnewCode的同时，保存原有的PrpDcode到分公司库branchdmsdb中
				PrpDcodeId prpdcodeId = new PrpDcodeId();
				prpdcodeId.setCodeType(prpDcode.getId().getCodeType());
				prpdcodeId.setCodeCode(prpDcode.getId().getCodeCode());
				prpdcode.setId(prpdcodeId);
				prpdcode.setCodeCName(prpDcode.getCodeCName());
				prpdcode.setCodeEName(prpDcode.getCodeEName());
				prpdcode.setNewCodeCode(prpDcode.getNewCodeCode());
				prpdcode.setValidStatus(prpDcode.getValidStatus());
				prpdcode.setFlag(prpDcode.getFlag());
				super.save(prpdcode);
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception(e.getMessage());
			}
		}
	} 
	
	public void prpDnewCodeComMessageProcess(PrpDnewCodeCom prpDnewCodeCom)throws Exception{
		if (prpDnewCodeCom != null) {
			try {
				PrpDnewCode prpDnewCode = new PrpDnewCode();
				PrpDcode prpDcode = new PrpDcode();
				//转移到prpDnewCode中保存
				PrpDnewCodeId newCodeId = new PrpDnewCodeId();
				newCodeId.setCodeType(prpDnewCodeCom.getId().getCodeType());
				newCodeId.setCodeCode(prpDnewCodeCom.getId().getCodeCode());
				prpDnewCode.setId(newCodeId);
				prpDnewCode.setCodeCName(prpDnewCodeCom.getCodeCName());
				prpDnewCode.setCodeEName(prpDnewCodeCom.getCodeEName());
				prpDnewCode.setUpperCode(prpDnewCodeCom.getUpperCode());
				prpDnewCode.setOldCodeCode(prpDnewCodeCom.getOldCodeCode());
				prpDnewCode.setNewCodeCode(prpDnewCodeCom.getNewCodeCode());
				prpDnewCode.setValidStatus(prpDnewCodeCom.getValidStatus());
				prpDnewCode.setFlag(prpDnewCodeCom.getFlag());
				//转移到prpDcode中保存
				PrpDcodeId codeId = new PrpDcodeId();
				codeId.setCodeType(prpDnewCodeCom.getId().getCodeType());
				codeId.setCodeCode(prpDnewCodeCom.getId().getCodeCode());
				prpDcode.setId(codeId);
				prpDcode.setCodeCName(prpDnewCodeCom.getCodeCName());
				prpDcode.setCodeEName(prpDnewCodeCom.getCodeEName());
				prpDcode.setNewCodeCode(prpDnewCodeCom.getNewCodeCode());
				prpDcode.setValidStatus(prpDnewCodeCom.getValidStatus());
				prpDcode.setFlag(prpDnewCodeCom.getFlag());
				super.save(prpDnewCode);
				super.save(prpDcode);
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception(e.getMessage());
			}
		}
	}
}
