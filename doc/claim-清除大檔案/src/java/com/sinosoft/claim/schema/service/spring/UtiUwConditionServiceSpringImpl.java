package com.sinosoft.claim.schema.service.spring;

/**
 * 核保核赔因子设置表接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;

import com.sinosoft.claim.common.service.facade.PrpDcodeRiskService;
import com.sinosoft.claim.common.service.facade.PrpDcompanyService;
import com.sinosoft.claim.common.service.facade.PrpDtypeService;
import com.sinosoft.claim.common.service.facade.PrpDuserService;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpDclass;
import com.sinosoft.claim.schema.model.PrpDcode;
import com.sinosoft.claim.schema.model.PrpDcodeRisk;
import com.sinosoft.claim.schema.model.PrpDcompany;
import com.sinosoft.claim.schema.model.PrpDuser;
import com.sinosoft.claim.schema.model.SwfModelMain;
import com.sinosoft.claim.schema.model.UtiUwCondition;
import com.sinosoft.claim.schema.model.UtiUwConditionId;
import com.sinosoft.claim.schema.model.UtiUwFactor;
import com.sinosoft.claim.schema.model.UtiUwLevel;
import com.sinosoft.claim.schema.model.UtiUwUserCondition;
import com.sinosoft.claim.schema.service.facade.PrpDclassService;
import com.sinosoft.claim.schema.service.facade.PrpDcodeService;
import com.sinosoft.claim.schema.service.facade.SwfModelMainService;
import com.sinosoft.claim.schema.service.facade.UtiUwComboFactorService;
import com.sinosoft.claim.schema.service.facade.UtiUwConditionService;
import com.sinosoft.claim.schema.service.facade.UtiUwFactorService;
import com.sinosoft.claim.schema.service.facade.UtiUwLevelService;
import com.sinosoft.claim.schema.service.facade.UtiUwUserConditionService;
import com.sinosoft.claim.undwrt.vo.UtiUwEnumFactor;
import com.sinosoft.sysframework.common.util.SqlUtils;

public class UtiUwConditionServiceSpringImpl extends GenericDaoHibernate<UtiUwCondition, UtiUwConditionId> implements UtiUwConditionService {
	private UtiUwUserConditionService utiUwUserConditionService;
	private UtiUwLevelService utiUwLevelService;
	private UtiUwFactorService utiUwFactorService;
	private PrpDcodeService prpDcodeService;
	private PrpDcodeRiskService prpDcodeRiskService;
	private UtiUwComboFactorService utiUwComboFactorService;
	private PrpDtypeService prpDtypeService;
	private PrpDuserService prpDuserService;
	private PrpDcompanyService prpDcompanyService;
	private PrpDclassService prpDclassService;
	private SwfModelMainService SwfModelMainService;

	@Override
	public void delete(UtiUwCondition conditionDto, String actionType) throws Exception {
		String classCode = conditionDto.getId().getClassCode();
		StringBuffer conditions = new StringBuffer("1=1");
		conditions.append(SqlUtils.convertString("ComCode", conditionDto.getId().getComCode()));
		conditions.append(SqlUtils.convertNumber("ModelNo", String.valueOf(conditionDto.getId().getModelNo())));
		if (actionType.startsWith("update") || actionType.startsWith("insert"))
			conditions.append(SqlUtils.convertNumber("NodeNo", String.valueOf(conditionDto.getId().getNodeNo())));
		conditions.append(SqlUtils.convertString("ClassCode", classCode));
		conditions.append(SqlUtils.convertString("RiskCode", conditionDto.getId().getRiskCode()));
		conditions.append(SqlUtils.convertString("UwType", conditionDto.getId().getUwType()));
		StringBuffer buffer = new StringBuffer(100);
		buffer.append("DELETE FROM UtiUwCondition WHERE ");
		buffer.append(conditions);
		super.getSession().createSQLQuery(buffer.toString()).executeUpdate();
		StringBuffer conditions1 = new StringBuffer("1=1");
		conditions1.append(SqlUtils.convertString("ComCode", conditionDto.getId().getComCode()));
		conditions1.append(SqlUtils.convertNumber("ModelNo", String.valueOf(conditionDto.getId().getModelNo())));
		conditions1.append(SqlUtils.convertString("ClassCode", classCode));
		conditions1.append(SqlUtils.convertString("RiskCode", conditionDto.getId().getRiskCode()));
		conditions1.append(SqlUtils.convertString("UwType", conditionDto.getId().getUwType()));
		this.utiUwUserConditionService.deleteByConditions(conditions1.toString());
		StringBuffer conditions2 = new StringBuffer("1=1");
		conditions2.append(SqlUtils.convertString("UwType", conditionDto.getId().getUwType()));
		conditions2.append(SqlUtils.convertString("ComCode", conditionDto.getId().getComCode()));
		conditions2.append(SqlUtils.convertString("RiskCode", conditionDto.getId().getRiskCode()));
		conditions2.append(SqlUtils.convertNumber("ModelNo", String.valueOf(conditionDto.getId().getModelNo())));
		this.utiUwLevelService.deleteByConditions(conditions2.toString());
	}

	@Override
	public Page findOverviewByConditions(String conditions, int pageNo, int rowsPerPage) throws Exception {
		StringBuffer statement = new StringBuffer(200);
		statement.append("SELECT ");
		statement.append("ComCode,ModelNo,UwType,ClassCode,Remark,CreateTime,ValidStatus ");
		statement.append("FROM UtiUwCondition WHERE ");
		statement.append(conditions);
		statement.append(" GROUP BY ");
		statement.append("ComCode,ModelNo,UwType,ClassCode,Remark,CreateTime,ValidStatus");
		Session session = super.getSession();
		List<?> tempList = HibernateUtils.findbySql(session, statement.toString(), pageNo, rowsPerPage);
		List<UtiUwCondition> resultList = new ArrayList<UtiUwCondition>();
		UtiUwCondition utiUwCondition = null;
		for (int i = 0; i < tempList.size(); i++) {
			Object[] object = (Object[]) tempList.get(i);
			utiUwCondition = new UtiUwCondition();
			utiUwCondition.getId().setComCode(String.valueOf(object[0]));
			utiUwCondition.getId().setModelNo(new Integer(String.valueOf(object[1])));
			utiUwCondition.getId().setUwType(String.valueOf(object[2]));
			utiUwCondition.getId().setClassCode(String.valueOf(object[3]));
			utiUwCondition.setRemark(String.valueOf(object[4]));
			utiUwCondition.setCreateTime(String.valueOf(object[5]));
			utiUwCondition.setValidStatus(String.valueOf(object[6]));
			PrpDcompany companyDto = this.prpDcompanyService.findByPrimaryKey(String.valueOf(object[0]));
			if (companyDto != null) {
				utiUwCondition.setComName(companyDto.getComCName());
			}
			SwfModelMain modelMainDto = this.SwfModelMainService.findSwfModelMain(new Integer(String.valueOf(object[1])));
			utiUwCondition.setModelName(modelMainDto.getModelName());
			PrpDcode prpDcodeDto = this.prpDcodeService.findByPrimaryKey("UwType", String.valueOf(object[2]));
			utiUwCondition.setUwTypeName(prpDcodeDto.getCodeCName());
			PrpDclass prpDclassDto = this.prpDclassService.findPrpDclass(String.valueOf(object[3]));
			utiUwCondition.setClassName(prpDclassDto.getClassName());
			utiUwCondition.setValidStatusName(utiUwCondition.getValidStatus().equals("1") ? "有效" : "註銷");
			StringBuffer condition = new StringBuffer("1=1");
			condition.append(SqlUtils.convertString("ComCode", String.valueOf(object[0])));
			condition.append(SqlUtils.convertString("ModelNo", "" + new Integer(String.valueOf(object[1]))));
			condition.append(SqlUtils.convertString("UwType", String.valueOf(object[2])));
			condition.append(SqlUtils.convertString("ClassCode", String.valueOf(object[3])));
			condition.append(SqlUtils.convertString("CreateTime", String.valueOf(object[5])));
			List<String> riskList = findRiskCodeByConditions(condition.toString());
			String riskCodeString = "";
			for (int j = 0; j < riskList.size(); j++) {
				riskCodeString = riskCodeString + (String) riskList.get(j) + " ";
			}
			utiUwCondition.getId().setRiskCode(riskCodeString.substring(0, riskCodeString.length() - 1));
			resultList.add(utiUwCondition);
		}
		return new Page((pageNo - 1) * rowsPerPage, HibernateUtils.getCountbySql(session, statement.toString()), rowsPerPage, resultList);
	}

	public void prepareInsertValidate(UtiUwCondition conditionDto) throws Exception {
		String uwType = conditionDto.getId().getUwType();
		String comCode = conditionDto.getId().getComCode();
		String classCode = conditionDto.getId().getClassCode();
		String riskCodeAry[] = conditionDto.getId().getRiskCode().split(",");
		for (int j = 0; j < riskCodeAry.length; j++) {
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.uwType", uwType);
			queryRule.addEqual("id.comCode", comCode);
			queryRule.addEqual("id.classCode", classCode);
			queryRule.addEqual("id.riskCode", riskCodeAry[j]);
			List<?> collection = super.find(queryRule);
			if (collection.size() > 0) {
				String message = "\u5DF2\u7ECF\u4E3A\u5BA1\u6838\u7C7B\u578B\uFF1A" + uwType + "\uFF0C\u5BA1\u6838\u90E8\u95E8\uFF1A" + comCode + "\uFF0C\u9669\u7C7B\uFF1A" + classCode + "\uFF0C\u9669\u79CD\uFF1A" + riskCodeAry[j]
						+ " \u914D\u7F6E\u4E86\u53CC\u6838\u6761\u4EF6\u3002";
				throw new Exception(message);
			}
		}
	}

	@Override
	public void update(UtiUwCondition conditionDto, String oldRiskCode) throws Exception {
		StringBuffer conditions = new StringBuffer("1=1");
		conditions.append(SqlUtils.convertString("UwType", conditionDto.getId().getUwType()));
		conditions.append(SqlUtils.convertNumber("ModelNo", String.valueOf(conditionDto.getId().getModelNo())));
		conditions.append(SqlUtils.convertString("ComCode", conditionDto.getId().getComCode()));
		conditions.append(SqlUtils.convertString("ClassCode", conditionDto.getId().getClassCode()));
		conditions.append(SqlUtils.convertString("RiskCode", oldRiskCode));
		conditions.append(SqlUtils.convertString("CreateTime", conditionDto.getCreateTime()));
		List<UtiUwCondition> conditionList = findGroupByConditions(conditions.toString());
		List<UtiUwUserCondition> userConditionList = this.utiUwUserConditionService.findGroupByConditions(conditions.toString());
		deleteByConditions(conditions.toString());
		this.utiUwUserConditionService.deleteByConditions(conditions.toString());
		StringBuffer conditions2 = new StringBuffer("1=1");
		conditions2.append(SqlUtils.convertString("UwType", conditionDto.getId().getUwType()));
		conditions2.append(SqlUtils.convertString("ComCode", conditionDto.getId().getComCode()));
		conditions2.append(SqlUtils.convertNumber("ModelNo", String.valueOf(conditionDto.getId().getModelNo())));
		conditions2.append(SqlUtils.convertString("RiskCode", oldRiskCode));
		List<UtiUwLevel> levelList = this.utiUwLevelService.findGroupByConditions(conditions2.toString());
		this.utiUwLevelService.deleteByConditions(conditions2.toString());
		// String remark = conditionDto.getRemark();
		// String riskArray[] = conditionDto.getId().getRiskCode().split(",");

		insertAll(conditionList);
		this.utiUwLevelService.insertAll(levelList);
		this.utiUwUserConditionService.insertAll(userConditionList);
	}

	private void insertAll(List<?> list) {
		if (list != null && list.size() > 0) {
			Session session = super.getSession();
			for (int i = 0; i < list.size(); i++) {
				session.saveOrUpdate((UtiUwCondition) list.get(i));
			}
		}
	}

	public List<UtiUwCondition> findGroupByConditions(String conditions) throws Exception {
		StringBuffer statement = new StringBuffer(200);
		statement.append("SELECT ");
		statement.append("ComCode,ModelNo,NodeNo,RiskCategoryCode,UwType,ClassCode,FactorCode,FactorValueNo,FactorValue,Remark,CreateTime,ValidStatus,RiskCode ");
		statement.append("FROM UtiUwCondition WHERE ");
		statement.append(conditions);
		statement.append(" GROUP BY ");
		statement.append("ComCode,ModelNo,NodeNo,RiskCategoryCode,UwType,ClassCode,FactorCode,FactorValueNo,FactorValue,Remark,CreateTime,ValidStatus,RiskCode");
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		List<?> tempList = HibernateUtils.findbySql(session, statement.toString(), 0, 0);
		List<UtiUwCondition> resultList = new ArrayList<UtiUwCondition>();
		UtiUwCondition utiUwCondition = null;
		for (int i = 0; i < tempList.size(); i++) {
			Object[] object = (Object[]) tempList.get(i);
			utiUwCondition = new UtiUwCondition();
			UtiUwConditionId utiUwConditionId = new UtiUwConditionId();
			utiUwConditionId.setComCode((String) object[0]);
			utiUwConditionId.setModelNo(new Integer(String.valueOf(object[1])));
			utiUwConditionId.setNodeNo(new Integer(String.valueOf(object[2])));
			utiUwCondition.setRiskCategoryCode((String) object[3]);
			utiUwConditionId.setUwType((String) object[4]);
			utiUwConditionId.setClassCode((String) object[5]);
			utiUwConditionId.setFactorCode((String) object[6]);
			utiUwConditionId.setFactorValueNo(new Integer(String.valueOf(object[7])));
			utiUwCondition.setFactorValue((String) object[8]);
			utiUwCondition.setRemark((String) object[9]);
			utiUwCondition.setCreateTime((String) object[10]);
			utiUwCondition.setValidStatus((String) object[11]);
			utiUwConditionId.setRiskCode((String) object[12]);
			utiUwCondition.setId(utiUwConditionId);
			resultList.add(utiUwCondition);
		}
		return resultList;
	}

	public void deleteByConditions(String conditions) throws Exception {
		StringBuffer buffer = new StringBuffer(100);
		buffer.append("DELETE FROM UtiUwCondition WHERE ");
		buffer.append(conditions);
		super.getSession().createSQLQuery(buffer.toString()).executeUpdate();
	}

	public List<?> getComboFactors(UtiUwCondition conditionDto, int flag) throws Exception {
		StringBuffer conditions = new StringBuffer("1=1");
		conditions.append(SqlUtils.convertString("UwType", conditionDto.getId().getUwType()));
		conditions.append(SqlUtils.convertString("ClassCode", conditionDto.getId().getClassCode()));
		conditions.append(SqlUtils.convertString("MultiSelectFlag", "C"));
		conditions.append(SqlUtils.convertString("ValidStatus", "1"));
		List<UtiUwFactor> factorList = (List<UtiUwFactor>) this.utiUwFactorService.findByConditions(conditions.toString(), 0, 0);
		return factorList;
	}

	public List<?> getEnumFactors(UtiUwCondition conditionDto, int flag) throws Exception {
		List<UtiUwFactor> enumFactorList = new ArrayList<UtiUwFactor>();
		StringBuffer conditions = new StringBuffer("1=1");
		conditions.append(SqlUtils.convertString("UwType", conditionDto.getId().getUwType()));
		conditions.append(SqlUtils.convertString("ClassCode", conditionDto.getId().getClassCode()));
		conditions.append(SqlUtils.convertString("MultiSelectFlag", "E"));
		conditions.append(SqlUtils.convertString("ValidStatus", "1"));
		List<UtiUwFactor> factorList = (List<UtiUwFactor>) this.utiUwFactorService.findByConditions(conditions.toString(), 0, 0);
		for (int i = 0; i < factorList.size(); ++i) {
			UtiUwFactor factorDto = (UtiUwFactor) factorList.get(i);
			conditions = new StringBuffer("1=1");
			conditions.append(SqlUtils.convertString("UwType", conditionDto.getId().getUwType()));
			conditions.append(SqlUtils.convertString("ClassCode", conditionDto.getId().getClassCode()));
			conditions.append(SqlUtils.convertString("RiskCode", conditionDto.getId().getRiskCode()));
			conditions.append(SqlUtils.convertString("ComCode", conditionDto.getId().getComCode()));
			conditions.append(SqlUtils.convertNumber("ModelNo", String.valueOf(conditionDto.getId().getModelNo())));
			conditions.append(SqlUtils.convertNumber("NodeNo", conditionDto.getId().getNodeNo().toString()));
			conditions.append(SqlUtils.convertString("FactorCode", factorDto.getId().getFactorCode()));
			List<?> nodeValueList = findFactorValueByConditions(conditions.toString());
			String nodeValue = (nodeValueList.size() > 0) ? ((UtiUwCondition) nodeValueList.get(0)).getFactorValue() : "";
			String userValue = "";
			if (flag == 1) {
				conditions.append(SqlUtils.convertString("UserCode", conditionDto.getUserCode()));
				List<?> userValueList = this.utiUwUserConditionService.findFactorValueByConditions(conditions.toString());
				userValue = (userValueList.size() > 0) ? ((UtiUwUserCondition) userValueList.get(0)).getFactorValue() : "";
			}
			String baseValue = (userValue.length() == 0) ? nodeValue : userValue;
			String codeType = factorDto.getId().getFactorCode();
			conditions = new StringBuffer("1=1");
			conditions.append(SqlUtils.convertString("CodeType", codeType));
			conditions.append(SqlUtils.convertString("RiskCode", conditionDto.getId().getRiskCode()));
			conditions.append(" Order By CodeCode");
			List<PrpDcodeRisk> codeRiskList = (List<PrpDcodeRisk>) this.prpDcodeRiskService.findByConditions(conditions.toString());
			List<UtiUwEnumFactor> enumCodeList = new ArrayList<UtiUwEnumFactor>();
			for (int j = 0; j < codeRiskList.size(); j++) {
				PrpDcodeRisk prpDcodeRiskDto = (PrpDcodeRisk) codeRiskList.get(j);
				String codeCode = prpDcodeRiskDto.getId().getCodeCode();
				PrpDcode prpDcodeDto = this.prpDcodeService.findByPrimaryKey(codeType, codeCode);
				String checked = (StringUtils.contains(baseValue, codeCode)) ? "checked" : "";
				UtiUwEnumFactor enumFactorDto = new UtiUwEnumFactor();
				enumFactorDto.setCodeCode(prpDcodeDto.getId().getCodeCode());
				enumFactorDto.setCodeName(prpDcodeDto.getCodeCName());
				enumFactorDto.setChecked(checked);
				enumCodeList.add(enumFactorDto);
			}
			factorDto.setEnumCodeList(enumCodeList);
			enumFactorList.add(factorDto);
		}
		return enumFactorList;
	}

	public List<UtiUwFactor> getSimpleFactors(UtiUwCondition conditionDto, int flag) throws Exception {
		List<UtiUwFactor> simpleFactorList = new ArrayList<UtiUwFactor>();
		StringBuffer conditions = new StringBuffer("1=1");
		conditions.append(SqlUtils.convertString("UwType", conditionDto.getId().getUwType()));
		conditions.append(SqlUtils.convertString("ClassCode", conditionDto.getId().getClassCode()));
		if (!"Z".equals(conditionDto.getId().getUwType())) {
			conditions.append(SqlUtils.convertString("MultiSelectFlag", "S"));
		}
		conditions.append(SqlUtils.convertString("ValidStatus", "1"));
		List<UtiUwFactor> factorList = (List<UtiUwFactor>) this.utiUwFactorService.findByConditions(conditions.toString(), 0, 0);
		for (int i = 0; i < factorList.size(); ++i) {
			UtiUwFactor factorDto = (UtiUwFactor) factorList.get(i);
			PrpDcode prpDcodeDto = this.prpDcodeService.findByPrimaryKey("UwFactorAttr", factorDto.getFactorAttr());
			factorDto.setFactorName(prpDcodeDto.getCodeCName());
			conditions = new StringBuffer("1=1");
			conditions.append(SqlUtils.convertString("UwType", conditionDto.getId().getUwType()));
			conditions.append(SqlUtils.convertString("ClassCode", conditionDto.getId().getClassCode()));
			conditions.append(SqlUtils.convertString("RiskCode", conditionDto.getId().getRiskCode()));
			conditions.append(SqlUtils.convertString("ComCode", conditionDto.getId().getComCode()));
			conditions.append(SqlUtils.convertNumber("ModelNo", String.valueOf(conditionDto.getId().getModelNo())));
			conditions.append(SqlUtils.convertNumber("NodeNo", conditionDto.getId().getNodeNo().toString()));
			conditions.append(SqlUtils.convertString("FactorCode", factorDto.getId().getFactorCode()));
			List<?> nodeValueList = findFactorValueByConditions(conditions.toString());
			String nodeValue = (nodeValueList.size() > 0) ? ((UtiUwCondition) nodeValueList.get(0)).getFactorValue() : "";
			factorDto.setNodeValue(nodeValue);
			if (flag == 1) {
				conditions.append(SqlUtils.convertString("UserCode", conditionDto.getUserCode()));
				List<?> userValueList = this.utiUwUserConditionService.findFactorValueByConditions(conditions.toString());
				String userValue = (userValueList.size() > 0) ? ((UtiUwUserCondition) userValueList.get(0)).getFactorValue() : "";
				userValue = (userValue.length() == 0) ? nodeValue : userValue;
				factorDto.setUserValue(userValue);
			}
			simpleFactorList.add(factorDto);
		}
		return simpleFactorList;
	}

	public List<?> findFactorValueByConditions(String conditions) throws Exception {
		StringBuffer statement = new StringBuffer(200);
		statement.append("SELECT FactorCode,FactorValue,FactorValueNo FROM UtiUwCondition WHERE ");
		statement.append(conditions);
		statement.append(" GROUP BY FactorCode,FactorValue,FactorValueNo Order By FactorValueNo");
		UtiUwCondition utiUwCondition = null;
		ArrayList<UtiUwCondition> resultList = new ArrayList<UtiUwCondition>();
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		List<?> tempList = HibernateUtils.findbySql(session, statement.toString(), 0, 0);
		for (int i = 0; i < tempList.size(); i++) {
			Object[] object = (Object[]) tempList.get(i);// 每行记录不在是一个对象
			// 而是一个数组
			utiUwCondition = new UtiUwCondition();
			utiUwCondition.getId().setFactorCode(String.valueOf(object[0]));
			utiUwCondition.setFactorValue(String.valueOf(object[1]));
			utiUwCondition.getId().setFactorValueNo(new Integer(String.valueOf(object[2])));
			resultList.add(utiUwCondition);
		}
		return resultList;
	}

	public List<UtiUwLevel> getUtiUwLevel(UtiUwCondition condition) throws Exception {
		StringBuffer conditions = new StringBuffer("1=1");
		conditions.append(SqlUtils.convertString("UwType", condition.getId().getUwType()));
		conditions.append(SqlUtils.convertString("ComCode", condition.getId().getComCode()));
		conditions.append(SqlUtils.convertString("RiskCode", condition.getId().getRiskCode()));
		conditions.append(SqlUtils.convertNumber("ModelNo", String.valueOf(condition.getId().getModelNo())));
		conditions.append(SqlUtils.convertNumber("NodeNo", String.valueOf(condition.getId().getNodeNo())));
		List<UtiUwLevel> utiUwLevelUserList = utiUwLevelService.findGroupByConditions(conditions.toString());
		for (int i = 0; i < utiUwLevelUserList.size(); i++) {
			UtiUwLevel utiUwLevel = utiUwLevelUserList.get(i);
			PrpDuser prpDuser = prpDuserService.findPrpDuser(utiUwLevel.getId().getUserCode());
			PrpDcompany prpDcompany = prpDcompanyService.findByPrimaryKey(prpDuser.getComCode());
			utiUwLevel.setUserName(prpDuser.getUserName());
			utiUwLevel.setUserComCode(prpDcompany.getComCode());
			utiUwLevel.setUserComName(prpDcompany.getComCName());
		}
		return utiUwLevelUserList;
	}

	public List<UtiUwCondition> findByConditions(String statement) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(statement);
		return super.find(queryRule);
	}

	public long getCount(String statement) throws Exception {
		String sql = "select count(1) from utiUwCondition where " + statement;
		return HibernateUtils.getCountbyCountSql(super.getSession(), sql);
	}

	public boolean findCountByConditions(String paramString1, int paramInt1, int paramInt2, String paramString2, String paramString3) throws Exception {
		int i = 1;
		int j = 0;
		String str = "Select count(*) From UtiUwCondition Where COMCODE = ? AND MODELNO = ? AND NODENO = ? AND RISKCODE = ? AND UWTYPE = ? AND ValidStatus = ?";
		List<?> list = super.getSession().createSQLQuery(str).setString(i++, paramString1).setInteger(i++, paramInt2).setString(i++, paramString2).setString(i++, paramString3).setString(i++, "1").list();
		if (list != null && list.size() > 0) {
			j = ((Number) list.get(0)).intValue();
		}
		if (j > 0) {
			return true;
		}
		return false;
	}

	public void updateUtiUwCondition(UtiUwCondition condition, String[] simpleFactorCode, String[] simpleFactorValue, String[] enFactorCode, String[] enCheckbox, String[] comboFactorCode, String[] comboFactorCols, String[] comboCodeType,
			String[] comboCodeCode, String[] comboFactorValue, String[] comboFactorDefaultValue, int flag, String actionType) throws Exception {
		String classCode = condition.getId().getClassCode();
		StringBuffer conditions = new StringBuffer("1=1");
		conditions.append(SqlUtils.convertString("ComCode", condition.getId().getComCode()));
		conditions.append(SqlUtils.convertNumber("ModelNo", String.valueOf(condition.getId().getModelNo())));
		if ((actionType.startsWith("update")) || (actionType.startsWith("insert"))) {
			conditions.append(SqlUtils.convertNumber("NodeNo", String.valueOf(condition.getId().getNodeNo())));
		}
		conditions.append(SqlUtils.convertString("ClassCode", classCode));
		conditions.append(SqlUtils.convertString("RiskCode", condition.getId().getRiskCode()));
		conditions.append(SqlUtils.convertString("UwType", condition.getId().getUwType()));
		String deleteSql = "DELETE FROM UtiUwCondition WHERE " + conditions.toString();
		HibernateUtils.executeSql(super.getSession(), deleteSql);
		this.insertSimpleFactors(condition, simpleFactorCode, simpleFactorValue, flag);
		this.insertEnumFactors(condition, enFactorCode, enCheckbox, flag);
		this.insertComboFactors(condition, comboFactorCode, comboFactorCols, comboCodeType, comboCodeCode, comboFactorValue, comboFactorDefaultValue, flag);
	}

	public void insertComboFactors(UtiUwCondition condition, String[] comboFactorCode, String[] comboFactorCols, String[] comboCodeType, String[] comboCodeCode, String[] comboFactorValue, String[] comboFactorExampleValue, int flag) throws Exception {
		if (comboFactorCode == null) {
			return;
		}

		String className = flag == 1 ? "com.sinosoft.claim.schema.model.UtiUwUserCondition" : "com.sinosoft.claim.schema.model.UtiUwCondition";
		String[] riskCodeAry = condition.getId().getRiskCode().split(",");
		List<String> factorCodeList = new ArrayList<String>();
		for (int i = 0; i < comboFactorCode.length; i++) {
			if (factorCodeList.contains(comboFactorCode[i]))
				continue;
			factorCodeList.add(comboFactorCode[i]);
		}

		List<Object> conditionList = new ArrayList<Object>();
		for (int i = 0; i < factorCodeList.size(); i++) {
			String factorCode = (String) factorCodeList.get(i);
			int columnCount = 0;
			for (int j = 0; j < comboFactorCols.length; j++) {
				if (!comboFactorCols[j].startsWith(factorCode))
					continue;
				columnCount = Integer.parseInt(comboFactorCols[j].split(",")[1]);
			}

			int cellCount = 0;
			List<String> codeValueList = new ArrayList<String>();
			for (int j = 0; j < comboFactorCode.length; j++) {
				if (!comboFactorCode[j].equals(factorCode))
					continue;
				cellCount++;
				if (StringUtils.trimToEmpty(comboCodeCode[j]).length() <= 0)
					continue;
				codeValueList.add(comboCodeCode[j]);
			}

			int rowCount = (cellCount - columnCount) / columnCount;

			List<String> codeTypeList = new ArrayList<String>();
			for (int j = 0; j < comboCodeType.length; j++) {
				if (!comboCodeType[j].startsWith(factorCode))
					continue;
				String codeType = comboCodeType[j].split(",")[1];
				if (codeTypeList.contains(codeType))
					continue;
				codeTypeList.add(codeType);
			}

			List<String> factorValueList = new ArrayList<String>();
			for (int j = 0; j < comboFactorExampleValue.length; j++) {
				if (!comboFactorExampleValue[j].startsWith(factorCode))
					continue;
				factorValueList.add(comboFactorValue[j]);
			}

			for (int j = 0; j < rowCount; j++) {
				String conditionValue = "";
				for (int k = 0; k < columnCount; k++) {
					String codeType = (String) codeTypeList.get(k);
					String codeValue = (String) codeValueList.get(j * columnCount + k);
					conditionValue = conditionValue + codeType + ":" + codeValue + ";";
				}
				String factorValue = StringUtils.trimToEmpty((String) factorValueList.get(j + 1));
				conditionValue = conditionValue + factorValue;

				for (int k = 0; k < riskCodeAry.length; k++) {
					Object insertDto = Class.forName(className).newInstance();
					PropertyUtils.setProperty(insertDto, "id.comCode", condition.getId().getComCode());
					PropertyUtils.setProperty(insertDto, "id.modelNo", condition.getId().getModelNo());
					PropertyUtils.setProperty(insertDto, "id.nodeNo", new Integer(condition.getId().getNodeNo()));
					PropertyUtils.setProperty(insertDto, "id.riskCode", riskCodeAry[k]);
					if (flag == 1) {
						PropertyUtils.setProperty(insertDto, "classCode", condition.getId().getClassCode());
					} else {
						PropertyUtils.setProperty(insertDto, "id.classCode", condition.getId().getClassCode());
					}
					PropertyUtils.setProperty(insertDto, "id.classCode", condition.getId().getClassCode());
					PropertyUtils.setProperty(insertDto, "id.uwType", condition.getId().getUwType());
					PropertyUtils.setProperty(insertDto, "id.factorCode", factorCode);
					PropertyUtils.setProperty(insertDto, "id.factorValueNo", new Integer(j + 1));
					PropertyUtils.setProperty(insertDto, "createTime", condition.getCreateTime());
					PrpDclass prpDclass = prpDclassService.findPrpDclass(condition.getId().getClassCode());
					PropertyUtils.setProperty(insertDto, "riskCategoryCode", prpDclass.getRiskCategory());
					PropertyUtils.setProperty(insertDto, "factorValue", conditionValue);
					PropertyUtils.setProperty(insertDto, "remark", condition.getRemark());
					PropertyUtils.setProperty(insertDto, "validStatus", condition.getValidStatus());
					if (flag == 1) {
						PropertyUtils.setProperty(insertDto, "id.userCode", condition.getUserCode());
					}

					conditionList.add(insertDto);
				}
			}
		}
		if (flag == 0) {
			this.insertAll(conditionList);
		} else if (flag == 1) {
			utiUwUserConditionService.insertAll(conditionList);
		}
	}

	public void insertEnumFactors(UtiUwCondition condition, String[] enumFactorCode, String[] enumCheckbox, int flag) throws Exception {
		if (enumFactorCode == null) {
			return;
		}

		String className = flag == 1 ? "com.sinosoft.claim.schema.model.UtiUwUserCondition" : "com.sinosoft.claim.schema.model.UtiUwCondition";
		List<Object> conditionList = new ArrayList<Object>();
		String[] riskCodeAry = condition.getId().getRiskCode().split(",");
		for (int i = 0; i < enumFactorCode.length; i++) {
			String factorCode = enumFactorCode[i];
			List<String> enumCodeList = new ArrayList<String>();
			for (int j = 0; j < enumCheckbox.length; j++) {
				if (!enumCheckbox[j].startsWith(factorCode))
					continue;
				enumCodeList.add(enumCheckbox[j]);
			}

			if (enumCodeList.size() <= 0)
				continue;
			String factorValue = ArrayUtils.toString(enumCodeList.toArray(), "");
			factorValue = factorValue.replaceAll(factorCode + ",", "");
			factorValue = factorValue.substring(1, factorValue.length() - 1);
			for (int j = 0; j < riskCodeAry.length; j++) {
				Object insertDto = Class.forName(className).newInstance();
				PropertyUtils.setProperty(insertDto, "id.comCode", condition.getId().getComCode());
				PropertyUtils.setProperty(insertDto, "id.modelNo", condition.getId().getModelNo());
				PropertyUtils.setProperty(insertDto, "id.nodeNo", new Integer(condition.getId().getNodeNo()));
				PropertyUtils.setProperty(insertDto, "id.riskCode", riskCodeAry[j]);
				if (flag == 1) {
					PropertyUtils.setProperty(insertDto, "classCode", condition.getId().getClassCode());
				} else {
					PropertyUtils.setProperty(insertDto, "id.classCode", condition.getId().getClassCode());
				}
				PropertyUtils.setProperty(insertDto, "id.uwType", condition.getId().getUwType());
				PropertyUtils.setProperty(insertDto, "id.factorCode", factorCode);
				PropertyUtils.setProperty(insertDto, "id.factorValueNo", new Integer(1));
				PropertyUtils.setProperty(insertDto, "createTime", condition.getCreateTime());
				PrpDclass prpDclass = prpDclassService.findPrpDclass(condition.getId().getClassCode());
				PropertyUtils.setProperty(insertDto, "riskCategoryCode", prpDclass.getRiskCategory());
				PropertyUtils.setProperty(insertDto, "factorValue", factorValue);
				PropertyUtils.setProperty(insertDto, "remark", condition.getRemark());
				PropertyUtils.setProperty(insertDto, "validStatus", condition.getValidStatus());
				if (flag == 1) {
					PropertyUtils.setProperty(insertDto, "id.userCode", condition.getUserCode());
				}

				conditionList.add(insertDto);
			}
		}

		if (flag == 0) {
			this.insertAll(conditionList);
		} else if (flag == 1) {
			utiUwUserConditionService.insertAll(conditionList);
		}
	}

	public void insertSimpleFactors(UtiUwCondition condition, String[] factorCode, String[] factorValue, int flag) throws Exception {
		String className = flag == 1 ? "com.sinosoft.claim.schema.model.UtiUwUserCondition" : "com.sinosoft.claim.schema.model.UtiUwCondition";
		String[] riskCodeAry = condition.getId().getRiskCode().split(",");
		List<Object> conditionList = new ArrayList<Object>();
		for (int j = 0; j < riskCodeAry.length; j++) {
			for (int i = 0; i < factorCode.length; i++) {
				Object insertDto = Class.forName(className).newInstance();
				PropertyUtils.setProperty(insertDto, "id.comCode", condition.getId().getComCode());
				PropertyUtils.setProperty(insertDto, "id.modelNo", condition.getId().getModelNo());
				PropertyUtils.setProperty(insertDto, "id.nodeNo", condition.getId().getNodeNo());
				PropertyUtils.setProperty(insertDto, "id.riskCode", riskCodeAry[j]);
				if (flag == 1) {
					PropertyUtils.setProperty(insertDto, "classCode", condition.getId().getClassCode());
				} else {
					PropertyUtils.setProperty(insertDto, "id.classCode", condition.getId().getClassCode());
				}
				PropertyUtils.setProperty(insertDto, "id.uwType", condition.getId().getUwType());
				PropertyUtils.setProperty(insertDto, "id.factorCode", factorCode[i]);
				PropertyUtils.setProperty(insertDto, "factorValueNo", new Integer(1));
				PropertyUtils.setProperty(insertDto, "createTime", condition.getCreateTime());
				PrpDclass prpDclass = prpDclassService.findPrpDclass(condition.getId().getClassCode());
				PropertyUtils.setProperty(insertDto, "riskCategoryCode", prpDclass.getRiskCategory());
				PropertyUtils.setProperty(insertDto, "factorValue", factorValue[i]);
				PropertyUtils.setProperty(insertDto, "remark", condition.getRemark());
				PropertyUtils.setProperty(insertDto, "validStatus", condition.getValidStatus());
				if (flag == 1) {
					PropertyUtils.setProperty(insertDto, "id.userCode", condition.getUserCode());
				}
				conditionList.add(insertDto);
			}
		}
		if (flag == 0) {
			this.insertAll(conditionList);
		} else if (flag == 1) {
			utiUwUserConditionService.insertAll(conditionList);
		}
	}

	public List<String> findRiskCodeByConditions(String conditions) throws Exception {
		StringBuffer statement = new StringBuffer(200);
		statement.append("SELECT RiskCode FROM UtiUwCondition WHERE ");
		statement.append(conditions);
		statement.append(" GROUP BY RiskCode ORDER BY RiskCode");
		List<?> listTemp = HibernateUtils.findbySql(super.getSession(), statement.toString());
		List<String> list = new ArrayList<String>();
		Iterator<?> it = listTemp.iterator();
		while (it.hasNext()) {
			String riskCode = (String) it.next();
			list.add(riskCode);
		}
		return list;
	}

	public UtiUwCondition findByPrimaryKey(String paramString1, int paramInt1, int paramInt2, String paramString2, String paramString3, String paramString4, int paramInt3) throws Exception {
		String sql = "Select * From UtiUwCondition Where COMCODE = '" + paramString1 + "' AND MODELNO = " + paramInt1 + " AND NODENO = " + paramInt2 + " AND RISKCODE = '" + paramString2 + "' AND UWTYPE = '" + paramString3 + "' AND FactorCode = '"
				+ paramString4 + "' AND FactorValueNo= " + paramInt3;
		List<?> list = HibernateUtils.findbySql(super.getSession(), sql, UtiUwCondition.class);
		UtiUwCondition utiUwCondition = null;
		if (list.size() > 0) {
			utiUwCondition = (UtiUwCondition) list.get(0);
		}
		return utiUwCondition;
	}

	public UtiUwLevelService getUtiUwLevelService() {
		return utiUwLevelService;
	}

	public void setUtiUwLevelService(UtiUwLevelService utiUwLevelService) {
		this.utiUwLevelService = utiUwLevelService;
	}

	public UtiUwUserConditionService getUtiUwUserConditionService() {
		return utiUwUserConditionService;
	}

	public void setUtiUwUserConditionService(UtiUwUserConditionService utiUwUserConditionService) {
		this.utiUwUserConditionService = utiUwUserConditionService;
	}

	public UtiUwFactorService getUtiUwFactorService() {
		return utiUwFactorService;
	}

	public void setUtiUwFactorService(UtiUwFactorService utiUwFactorService) {
		this.utiUwFactorService = utiUwFactorService;
	}

	public PrpDcodeService getPrpDcodeService() {
		return prpDcodeService;
	}

	public void setPrpDcodeService(PrpDcodeService prpDcodeService) {
		this.prpDcodeService = prpDcodeService;
	}

	public PrpDcodeRiskService getPrpDcodeRiskService() {
		return prpDcodeRiskService;
	}

	public void setPrpDcodeRiskService(PrpDcodeRiskService prpDcodeRiskService) {
		this.prpDcodeRiskService = prpDcodeRiskService;
	}

	public UtiUwComboFactorService getUtiUwComboFactorService() {
		return utiUwComboFactorService;
	}

	public void setUtiUwComboFactorService(UtiUwComboFactorService utiUwComboFactorService) {
		this.utiUwComboFactorService = utiUwComboFactorService;
	}

	public PrpDtypeService getPrpDtypeService() {
		return prpDtypeService;
	}

	public void setPrpDtypeService(PrpDtypeService prpDtypeService) {
		this.prpDtypeService = prpDtypeService;
	}

	public PrpDuserService getPrpDuserService() {
		return prpDuserService;
	}

	public void setPrpDuserService(PrpDuserService prpDuserService) {
		this.prpDuserService = prpDuserService;
	}

	public PrpDcompanyService getPrpDcompanyService() {
		return prpDcompanyService;
	}

	public void setPrpDcompanyService(PrpDcompanyService prpDcompanyService) {
		this.prpDcompanyService = prpDcompanyService;
	}

	public PrpDclassService getPrpDclassService() {
		return prpDclassService;
	}

	public void setPrpDclassService(PrpDclassService prpDclassService) {
		this.prpDclassService = prpDclassService;
	}

	public SwfModelMainService getSwfModelMainService() {
		return SwfModelMainService;
	}

	public void setSwfModelMainService(SwfModelMainService swfModelMainService) {
		SwfModelMainService = swfModelMainService;
	}

}
