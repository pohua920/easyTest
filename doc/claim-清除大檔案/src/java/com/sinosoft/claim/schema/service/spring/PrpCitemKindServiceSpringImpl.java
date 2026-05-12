package com.sinosoft.claim.schema.service.spring;

/**
 * PRPCITEMKIND信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;
import ins.framework.utils.DataUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Session;

import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.ConstantsCollection;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpCitemKind;
import com.sinosoft.claim.schema.model.PrpCitemKindId;
import com.sinosoft.claim.schema.model.PrpClimit;
import com.sinosoft.claim.schema.service.facade.PrpCitemKindService;
import com.sinosoft.claim.schema.service.facade.PrpClimitService;

public class PrpCitemKindServiceSpringImpl extends GenericDaoHibernate<PrpCitemKind, PrpCitemKindId> implements PrpCitemKindService {
	/** 限额服务 */
	private PrpClimitService prpClimitService;
	/** 数据服务 */
	private CodeService codeService;

	@Override
	public void save(PrpCitemKind prpCitemKind) throws Exception {
		logger.info("保存PRPCITEMKIND信息");
		super.save(prpCitemKind);

	}

	@Override
	public void save(List<PrpCitemKind> list) throws Exception {
		logger.info("保存PRPCITEMKIND信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpCitemKindId prpCitemKindId) throws Exception {
		logger.info("删除PRPCITEMKIND信息编号为" + prpCitemKindId + "的PRPCITEMKIND信息");
		super.deleteByPK(PrpCitemKind.class, prpCitemKindId);
	}

	@Override
	public PrpCitemKind findPrpCitemKind(PrpCitemKindId prpCitemKindId) throws Exception {
		logger.info("查询PRPCITEMKIND信息编号为" + prpCitemKindId + "的PRPCITEMKIND信息");
		return super.get(PrpCitemKind.class, prpCitemKindId);
	}
	/**
	 * 不建议使用
	 */
	@Override
	public Page findPrpCitemKind(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取PRPCITEMKIND信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpCitemKind> findPrpCitemKind(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	/**
	 * 根据PRPCITEMKIND编号查询出PRPCITEMKIND信息
	 * @param certiNo ：传入的PRPCITEMKIND编号
	 * @return 返回PRPCITEMKIND
	 */
	public PrpCitemKind findPrpCitemKind(String certiNo) throws Exception {
		PrpCitemKind prpCitemKind = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.policyNo", certiNo);
		List<PrpCitemKind> resultList = super.find(queryRule);
		if (resultList != null && resultList.size() > 0) {
			prpCitemKind = resultList.get(0);
		}
		return prpCitemKind;
	}

	/**
	 * 按条件查询多条数据
	 * @param conditions 查询条件
	 * @param pageNo 页号
	 * @param rowsPerPage 每页的行数
	 * @return Collection
	 * @throws Exception
	 */
	public List<PrpCitemKind> findByConditionsDistinct(String conditions, int pageNo, int rowsPerPage) throws Exception {
		if (conditions.length() <= 0) {
			conditions = "1 = 1";
		}
		String statement = "SELECT DISTINCT(familyno),familyName, kindCode,kindName,amount,itemCode,unitAmount,flag,itemKindNo,itemDetailName,itemName,VALUE" + " FROM PRPCITEMKIND WHERE " + conditions;
		List<PrpCitemKind> resultList = new ArrayList<PrpCitemKind>();
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		List<?> tempList = HibernateUtils.findbySql(session, statement, pageNo,rowsPerPage);
		PrpCitemKind prpCitemKind = null;
		for (int i = 0; i < tempList.size(); i++) {
			Object[] object = (Object[]) tempList.get(i);
			prpCitemKind = new PrpCitemKind();
			prpCitemKind.setFamilyNo(DataUtils.getInteger(object[0]));
			prpCitemKind.setFamilyName(DataUtils.getString(object[1]));
			prpCitemKind.setKindCode(DataUtils.getString(object[2]));
			prpCitemKind.setKindName(DataUtils.getString(object[3]));
			prpCitemKind.setAmount(DataUtils.getDouble(object[4]));
			prpCitemKind.setItemCode(DataUtils.getString(object[5]));
			prpCitemKind.setUnitAmount(DataUtils.getDouble(object[6]));
			prpCitemKind.setFlag(DataUtils.getString(object[7]));
			prpCitemKind.getId().setItemKindNo(DataUtils.getInteger(object[8]));
			prpCitemKind.setItemDetailName(DataUtils.getString(object[9]));
			prpCitemKind.setItemName(DataUtils.getString(object[10]));
			prpCitemKind.setValue(DataUtils.getDouble(object[11]));
			resultList.add(prpCitemKind);
		}
		return resultList;
	}

	@Override
	public Page findKindCodeAndNameByConditionsDistinct(String conditions, int pageNo, int rowsPerPage) throws Exception {
		if (DataUtils.emptyToNull(conditions)==null) {
			conditions = "1 = 1";
		}
		String statement = "SELECT DISTINCT(KINDCODE),KINDNAME,familyno,ITEMKINDNO,amount,VALUE,itemCode,itemName,itemDetailName FROM PRPCITEMKIND WHERE " + conditions;
		Page page = HibernateUtils.findPagebySql(super.getSession(), statement, pageNo, rowsPerPage);
		List<?> result = page.getResult();
		List<PrpCitemKind> resultList = new ArrayList<PrpCitemKind>();
		if (result!=null && !result.isEmpty()) {
			Object[] object = null;
			PrpCitemKind prpCitemKind = null;
			for (Iterator<?> it = result.iterator(); it.hasNext();) {
				object = (Object[])it.next();
				prpCitemKind = new PrpCitemKind();
				prpCitemKind.setKindCode((String)object[0]);
				prpCitemKind.setKindName((String)object[1]);
				prpCitemKind.setFamilyNo(DataUtils.getInteger(object[2]));
				prpCitemKind.getId().setItemKindNo(DataUtils.getInteger(object[3]));
				prpCitemKind.setAmount(DataUtils.getDouble(object[4]));
				prpCitemKind.setValue(DataUtils.getDouble(object[5]));
				prpCitemKind.setItemCode((String)object[6]);
				prpCitemKind.setItemName((String)object[7]);
				prpCitemKind.setItemDetailName((String)object[8]);
				resultList.add(prpCitemKind);
			}
		}
		return new Page(page.getStart(), page.getTotalCount(), page.getPageSize(), resultList);
	}

	@Override
	public Page findByPage(String conditions, int pageNo, int rowsPerPage) throws Exception {
		if(DataUtils.emptyToNull(conditions)==null){
			conditions = " 1=1 ";
		}
		String sql = "select * from PrpCitemKind where " + conditions;
		return HibernateUtils.findPagebySql(super.getSession(), sql, pageNo, rowsPerPage,PrpCitemKind.class);
	}
	@Override
	public List<PrpCitemKind> findByConditions(String conditions) {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		return super.find(queryRule);
	}
	
	@Override
	public List<PrpCitemKind> generateVirtualKind(PrpCitemKind prpCitemKind) throws Exception {
		List<PrpCitemKind> resultList = new ArrayList<PrpCitemKind>();
		if(prpCitemKind!=null) {
			String strRiskType = codeService.translateRiskCodetoRiskType(prpCitemKind.getRiskCode());
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.policyNo", prpCitemKind.getId().getPolicyNo());
			queryRule.addEqual("id.limitNo", prpCitemKind.getId().getItemKindNo());
			List<PrpClimit> tempList = new ArrayList<PrpClimit>();
			tempList = prpClimitService.findPrpClimit(queryRule);
			if(!CommonUtils.isEmpty(tempList)) {
				PrpCitemKind tempPrpCitemKind = null;
				PrpClimit prpClimit = null;
				for(int i = 0;i<tempList.size();i++) {
					tempPrpCitemKind = new PrpCitemKind();
					prpClimit = tempList.get(i);
					PropertyUtils.copyProperties(tempPrpCitemKind, prpCitemKind);
					//mantis：CLM0128，處理人員：DP0713，需求單編號：新核心-藝術品AR立案錯誤問題 START
					if(!CommonUtils.isEmpty(tempPrpCitemKind.getPerimeter()) || !CommonUtils.isEmpty(tempPrpCitemKind.getThickness()) &&
							tempPrpCitemKind.getRiskCode().equals(ConstantCodes.RISKCODE_AR)){
						if(tempPrpCitemKind.getPerimeter().equals("1")){
							tempPrpCitemKind.setItemName("手動輸入");
						}else if(tempPrpCitemKind.getPerimeter().equals("2")){
							tempPrpCitemKind.setItemName("含於上項");
							tempPrpCitemKind.setAmount(0.0);
						}else if(tempPrpCitemKind.getPerimeter().equals("2")){
							tempPrpCitemKind.setItemName("不保");
							tempPrpCitemKind.setAmount(0.0);
						}
					}else{
						tempPrpCitemKind.setItemCode(prpClimit.getId().getLimitType());
						tempPrpCitemKind.setItemName(codeService.translateLimitType(prpClimit.getId().getLimitType(), true));
						tempPrpCitemKind.setAmount(prpClimit.getLimitFee());
					}
					//mantis：CLM0128，處理人員：DP0713，需求單編號：新核心-藝術品AR立案錯誤問題 END
					resultList.add(tempPrpCitemKind);
				}
			} else if (ConstantCodes.CLASSCODE_G.equals(strRiskType)||ConstantCodes.CLASSCODE_Q.equals(strRiskType)){//工程险虚拟险种拆分
				//火险第三人责任险
				Double tempAmount = 0d;
				PrpCitemKind tempPrpCitemKind = null;
				for(String fieldName:ConstantCodes.LIMIT_FIELD) {
					tempAmount = (Double)PropertyUtils.getProperty(prpCitemKind, fieldName);
					if (tempAmount>0) {
						tempPrpCitemKind = new PrpCitemKind();
						PropertyUtils.copyProperties(tempPrpCitemKind, prpCitemKind);
						tempPrpCitemKind.setItemCode(ConstantsCollection.limitTypeList.get(fieldName));
						tempPrpCitemKind.setItemName(ConstantsCollection.limitTypeNameList.get(tempPrpCitemKind.getItemCode()));
						tempPrpCitemKind.setAmount(tempAmount);
						resultList.add(tempPrpCitemKind);
					}
				}
			}
		}
		return resultList;
	}

	public PrpClimitService getPrpClimitService() {
		return prpClimitService;
	}

	public void setPrpClimitService(PrpClimitService prpClimitService) {
		this.prpClimitService = prpClimitService;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

}
