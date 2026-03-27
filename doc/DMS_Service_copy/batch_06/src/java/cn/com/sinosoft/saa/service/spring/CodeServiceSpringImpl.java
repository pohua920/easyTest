package cn.com.sinosoft.saa.service.spring;

import ins.framework.cache.CacheManager;
import ins.framework.common.Page;
import ins.framework.common.ServiceFactory;
import ins.framework.dao.GenericDaoHibernate;
import ins.framework.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import com.sinosoft.bpsdriver.service.facade.SaaAPIService;
import com.sinosoft.bpsdriver.service.spring.SaaAPIServiceImpl;

import cn.com.sinosoft.ims.util.IConstants;
import cn.com.sinosoft.saa.service.facade.CodeService;
import cn.com.sinosoft.saa.vo.Code;

public class CodeServiceSpringImpl extends GenericDaoHibernate implements
		CodeService {

	private static CacheManager cacheManager = CacheManager.getIntance("Code");

	/**
	 * �������
	 * 
	 * @param codeType
	 *            ��������
	 * @param codeCode
	 *            ����
	 * @param language
	 *            ����(C:����/E:Ӣ��)
	 * @return ������� changed by hualimin ȥ��try catch ����BusinessException 2009-8-9
	 */
	@SuppressWarnings("unchecked")
	public String translateCode(String systemCode, String codeType,
			String codeCode, String codeFlag, String language) {

		String codeName;
		if (codeCode == null) {
			return "";
		}
		/** ��ʹ��cacheManager start part1 of 2 */
		// String key = cacheManager.generateCacheKey("translateCode",
		// codeType, codeCode, language);
		// Object result = cacheManager.getCache(key);
		// if (result != null) {
		// return (String) result;
		// }
		/** ��ʹ��cacheManager end */
		codeName = null;
		StringBuilder buffer = new StringBuilder();
		if (codeCode.indexOf(",") > -1) {
			String[] codes = StringUtils.split(codeCode, ",");
			for (String code : codes) {
				String hql = generateTranslateHql(codeType, code.trim(),codeFlag,
						language);
				List nameList = this.findByHql(hql, code.trim());
				if (nameList.size() > 0) {
					codeName = nameList.get(0) + "";
					codeName = codeName.trim();
				}
				buffer.append(codeName);
				if (!code.equals(codes[codes.length - 1])) {
					buffer.append(",");
				}
			}
			codeName = buffer.toString();
		} else {
			String hql = generateTranslateHql(codeType, codeCode, codeFlag,language);
			List nameList = this.findByHql(hql, codeCode);
			if (nameList.size() > 0) {
				codeName = nameList.get(0) + "";
				codeName = codeName.trim();
			}
		}
		if (codeName == null || "".equals(codeName) || "null".equals(codeName)) {
			codeName = codeCode;
			// BusinessException be = new
			// BusinessException(ServiceInfoConst.ERROR_CODE_NULL,
			// ServiceInfoConst.ERROR_MESSAGE_NULL);
			// throw be;
		}
		/** ��ʹ��cacheManager start part2 of 2 */
		// cacheManager.putCache(key, codeName);
		/** ��ʹ��cacheManager end */
		return codeName;
	}

	public String translateNameToCode(String systemCode, String codeType, String codeCName,String codeEName) {
		String codeCode = null;
		String hql = generateTranslateNameToCodeHql(codeType, codeCName,codeEName);
		List codeList = this.findByHql(hql, codeCName, codeEName);
		if (codeList.size() > 0) {
			codeCode = codeList.get(0) + "";
			codeCode = codeCode.trim();
		}
		if (codeCode == null || "".equals(codeCode) || "null".equals(codeCode)) {
			codeCode = "";
		}
		return codeCode;
	}
	
	/**
	 * �������<br>
	 * ֧�ֵĴ��������У�<br>
	 * 
	 * @param codeType
	 *            ��������
	 * @param riskCode
	 *            ���ִ���
	 * @param language
	 *            ����(C:����/E:Ӣ��)
	 * @return ����Map
	 */
	/*
	 * @SuppressWarnings("unchecked") public Map<String, String>
	 * listCodes(String codeType, String riskCode, String language) { String key
	 * = cacheManager.generateCacheKey("listCodes", codeType, riskCode,
	 * language); Object result = cacheManager.getCache(key); if (result !=
	 * null) { return (Map<String, String>) result; }
	 * 
	 * TreeMap<String, String> map = new TreeMap<String, String>(); String hql =
	 * generateListHql(codeType, riskCode, language, "asc");
	 * 
	 * List list = this.findByHql(hql); for (int i = 0; i < list.size(); i++) {
	 * Object[] arrValue = (Object[]) list.get(i); String code = (arrValue[0] +
	 * "").trim(); String name = (arrValue[1] + "").trim(); map.put(code, name);
	 * } cacheManager.putCache(key, map); return map; }
	 */

	/**
	 * �������<br>
	 * ֧�ֵĴ��������У�<br>
	 * 
	 * @param codeType
	 *            ��������
	 * @param riskCode
	 *            ���ִ���
	 * @param language
	 *            ����(C:����/E:Ӣ��)
	 * @param otherCondition
	 *            ��������
	 * @return ����Map
	 */
	/*
	 * @SuppressWarnings("unchecked") public Map<String, String>
	 * listCodes(String codeType, String riskCode, String language, String
	 * otherCondition) {
	 * 
	 * String key = cacheManager.generateCacheKey("listCodes", codeType,
	 * riskCode, language, otherCondition); Object result =
	 * cacheManager.getCache(key); if (result != null) { return (Map<String,
	 * String>) result; }
	 * 
	 * TreeMap<String, String> map = new TreeMap<String, String>(); String hql =
	 * generateListHql(codeType, riskCode, language, ""); if (otherCondition !=
	 * null && !otherCondition.trim().equals("")) { hql = hql + " and " +
	 * otherCondition; }
	 * 
	 * List list = this.findByHql(hql); for (int i = 0; i < list.size(); i++) {
	 * Object[] arrValue = (Object[]) list.get(i); String code = (arrValue[0] +
	 * "").trim(); String name = (arrValue[1] + "").trim(); map.put(code, name);
	 * } cacheManager.putCache(key, map); return map; }
	 */

	/**
	 * �������<br>
	 * ֧�ֵĴ��������У�<br>
	 * 
	 * @param codeType
	 *            ��������
	 * @param riskCode
	 *            ���ִ���
	 * @param language
	 *            ����(C:����/E:Ӣ��)
	 * @return ����List
	 */
	public List<Code> listCodeList(String codeType, String riskCode,
			String language) {
		List<Code> codes = this.listOrderCodeList(codeType, riskCode, language,
				"");
		return codes;
	}

	/**
	 * �������<br>
	 * ֧�ֵĴ��������У�<br>
	 * 
	 * @param codeType
	 *            ��������
	 * @param riskCode
	 *            ���ִ���
	 * @param language
	 *            ����(C:����/E:Ӣ��)
	 * @param order
	 *            ����(asc:����/desc:����)
	 * @return ����List
	 */
	@SuppressWarnings("unchecked")
	public List<Code> listOrderCodeList(String codeType, String riskCode,
			String language, String order) {
		String key = cacheManager.generateCacheKey("listOrderCodeList",
				codeType, riskCode, language, order);
		Object result = cacheManager.getCache(key);
		if (result != null) {
			return (List<Code>) result;
		}

		List<Code> codes = new ArrayList<Code>();
		String hql = generateListHql(codeType, riskCode, language, order);
		List list = this.findByHql(hql);
		for (int i = 0; i < list.size(); i++) {
			Object[] arrValue = (Object[]) list.get(i);
			String code = (arrValue[0] + "").trim();
			String name = (arrValue[1] + "").trim();
			codes.add(new Code(code, name));
		}
		cacheManager.putCache(key, codes);
		return codes;
	}

	/**
	 * ��ȡ���������б�
	 * 
	 * @param riskCode
	 *            ���ִ���
	 * @param clauseType
	 *            �������
	 * @return �������������б�
	 */
	public List<Code> findDeductCodes(String riskCode, String clauseType) {

		String key = cacheManager.generateCacheKey("findDeductCodes", riskCode,
				clauseType);
		Object result = cacheManager.getCache(key);
		if (result != null) {
			return (List<Code>) result;
		}

		List<Code> codeList = new ArrayList<Code>();
		String hql = "select distinct a.id.deductCondCode,a.deductCondName from PrpDdeductCond a where a.id.riskCode=? and a.id.clauseType=?";
		List deductList = this.findByHql(hql, riskCode, clauseType);
		for (int i = 0; i < deductList.size(); i++) {
			Object[] objs = (Object[]) deductList.get(i);
			String code = objs[0] + "";
			String name = objs[1] + "";
			codeList.add(new Code(code, name));
		}

		cacheManager.putCache(key, codeList);
		return codeList;

	}

	/**
	 * ���HQL
	 * 
	 * @param codeType
	 *            ��������
	 * @param codeCode
	 *            ����
	 * @param riskCode
	 *            ���ִ���
	 * @param language
	 *            ����(C:����/E:Ӣ��)
	 * @return HQL���
	 */
	private String generateTranslateHql(String codeType, String codeCode,String codeFlag,
			String language) {
		String hql = null;
		 if (codeType.equals(USERCODE)) {
			 hql = "select a.userName from UtiIUser a where a.userCode = ?";
		 } 
		/**��ѯ���*/
		else if (codeType.equals(COMCODE)) {
			if (language.equals("E")) {
				hql = "select a.comEName from PrpDcompany a where a.comCode = ?";
			} else {
				hql = "select a.comCName from PrpDcompany a where a.comCode = ?";
			}
		}
		/**��ѯPrpDagent��*/
		else if (codeType.equals(AGENTCODE)) {
			hql = "select a.agentName from PrpDagent a where a.id.agentCode=?";
		} 
		/**��ѯPrpDrisk��*/
		else if (codeType.equals(RISKCODE)) {
			if (language.equals("E")) {
				hql = "select a.riskEName from PrpDrisk a where a.riskCode = ?";
			} else {
				hql = "select a.riskCName from PrpDrisk a where a.riskCode = ?";
			}
		}
		/**��ѯPrpDtype*/
		else if (codeType.equals(TYPECODE)) {
			if (language.equals("E")) {
				hql = "select a.codeType from PrpDtype a where a.codeType = ?";
			} else {
				hql = "select a.codeTypeCName from PrpDtype a where a.codeType = ?";
			}
		}
		/**��ѯPrpDclass*/
		else if (codeType.equals(CLASSCODE)) {
			if (language.equals("E")) {
				hql = "select a.classEName from PrpDclass a where a.classCode = ?";
			} else {
				hql = "select a.classCName from PrpDclass a where a.classCode = ?";
			}
		}
		
		// else if (codeType.equals(KINDCODE)) {
		// if (language.equals("E")) {
		// hql =
		// "select a.kindename from PrpDkind a where a.id.riskCode in('0000', '"
		// + riskCode + "') and a.id.kindCode = ?";
		// } else {
		// hql =
		// "select a.kindCName from PrpDkind a where a.id.riskCode in('0000', '"
		// + riskCode + "') and a.id.kindCode = ?";
		// }
		// }
		// else if (codeType.equals(ITEMCODE)) {
		// if (language.equals("E")) {
		// hql =
		// "select a.itemename from PrpDitem a where a.id.riskCode in('0000', '"
		// + riskCode + "') and a.id.itemCode = ?";
		// } else {
		// hql =
		// "select a.itemcname from PrpDitem a where a.id.riskCode in('0000', '"
		// + riskCode + "') and a.id.itemCode = ?";
		// }
		// }
		else if (codeType.equals(CURRENCYCODE)) {
			if (language.equals("E")) {
				hql = "select a.currencyEName from PrpDcurrency a where a.currencyCode = ?";
			} else {
				hql = "select a.currencyCName from PrpDcurrency a where a.currencyCode = ?";
			}
		} 
//		else if (codeType.equals(CUSTOMERCODE)) {
//			if (language.equals("E")) {
//				hql = "select a.customerEName from PrpDcustomer a where a.customerCode = ?";
//			} else {
//				hql = "select a.customerCName from PrpDcustomer a where a.customerCode = ?";
//			}
//		}
		// else if (codeType.equals(LIMITCODE)) {
		// if (language.equals("E")) {
		// hql =
		// "select a.limitEName from PrpDlimit a where a.id.riskCode in('0000', '"
		// + riskCode + "') and a.id.limitCode = ?";
		// } else {
		// hql =
		// "select a.limitCName from PrpDlimit a where a.id.riskCode in('0000', '"
		// + riskCode + "') and a.id.limitCode = ?";
		// }
		// }
//		else if (codeType.equals(TASKCODE)) {
//			if (language.equals("E")) {
//				hql = "select a.taskEName from SaaTask a where a.taskCode = ? and (a.taskCode like ? or a.taskEName like ?)";
//			} else {
//				hql = "select a.taskCName from SaaTask a where a.taskCode = ? and (a.taskCode like ? or a.taskEName like ?)";
//			}
//		}
		/**��ѯPrpDcode��*/
		else {
			//������0����Ϊ���´���
			if (!"0".equals(codeFlag)) {
				if (language.equals("E")) {
					hql = "select a.codeEName from PrpDnewCode a where a.id.codeType='"
							+ codeType + "' and a.id.codeCode = ?";
				} else if (language.equals("EF")){//add by yjm (币别英语全称)
					hql = "select a.oldCodeCode from PrpDnewCode a where a.id.codeType='"
							+ codeType + "' and a.id.codeCode = ?";
					System.out.println("========================================="+hql+"======================================");
				} else {
					hql = "select a.codeCName from PrpDnewCode a where a.id.codeType='"
							+ codeType + "' and a.id.codeCode = ?";
				}
			} else {
				if (language.equals("E")) {
					hql = "select a.codeEName from PrpDcode a where a.id.codeType='"
						+ codeType + "' and a.id.codeCode = ?";
				} else {
					hql = "select a.codeCName from PrpDcode a where a.id.codeType='"
						+ codeType + "' and a.id.codeCode = ?";
				}
			}
		}
		return hql;
	}
	/**
	 * ���HQL
	 * 
	 * @param codeType
	 *            ��������
	 * @param codeCode
	 *            ����
	 * @param riskCode
	 *            ���ִ���
	 * @param language
	 *            ����(C:����/E:Ӣ��)
	 * @return HQL���
	 */
	private String generateTranslateNameToCodeHql(String codeType, String codeCName, String codeEName) {
		String hql = null;
		/**��ѯ���*/
		if (codeType.equals(COMCODE)) {
				hql = "select a.comCode from PrpDcompany a where a.comCName = ? or a.comEName = ?";
		}
		/**��ѯPrpDagent��*/
//		else if (codeType.equals(AGENTCODE)) {
//			hql = "select a.agentName from PrpDagent a where a.id.agentCode=?";
//		} 
//		/**��ѯPrpDrisk��*/
//		else if (codeType.equals(RISKCODE)) {
//				hql = "select a.riskEName from PrpDrisk a where a.riskCode = ?";
//				hql = "select a.riskCName from PrpDrisk a where a.riskCode = ?";
//		}
		/**��ѯPrpDtype*/
//		else if (codeType.equals(TYPECODE)) {
//				hql = "select a.codeType from PrpDtype a where a.codeType = ?";
//				hql = "select a.codeTypeCName from PrpDtype a where a.codeType = ?";
//		}
		/**��ѯPrpDclass*/
//		else if (codeType.equals(CLASSCODE)) {
//				hql = "select a.classEName from PrpDclass a where a.classCode = ?";
//				hql = "select a.classCName from PrpDclass a where a.classCode = ?";
//		}
		
		// else if (codeType.equals(KINDCODE)) {
		// if (language.equals("E")) {
		// hql =
		// "select a.kindename from PrpDkind a where a.id.riskCode in('0000', '"
		// + riskCode + "') and a.id.kindCode = ?";
		// } else {
		// hql =
		// "select a.kindCName from PrpDkind a where a.id.riskCode in('0000', '"
		// + riskCode + "') and a.id.kindCode = ?";
		// }
		// }
		// else if (codeType.equals(ITEMCODE)) {
		// if (language.equals("E")) {
		// hql =
		// "select a.itemename from PrpDitem a where a.id.riskCode in('0000', '"
		// + riskCode + "') and a.id.itemCode = ?";
		// } else {
		// hql =
		// "select a.itemcname from PrpDitem a where a.id.riskCode in('0000', '"
		// + riskCode + "') and a.id.itemCode = ?";
		// }
		// }
//		else if (codeType.equals(CURRENCYCODE)) {
//			if (language.equals("E")) {
//				hql = "select a.currencyEName from PrpDcurrency a where a.currencyCode = ?";
//			} else {
//				hql = "select a.currencyCName from PrpDcurrency a where a.currencyCode = ?";
//			}
//		} 
//		else if (codeType.equals(CUSTOMERCODE)) {
//			if (language.equals("E")) {
//				hql = "select a.customerEName from PrpDcustomer a where a.customerCode = ?";
//			} else {
//				hql = "select a.customerCName from PrpDcustomer a where a.customerCode = ?";
//			}
//		}
		// else if (codeType.equals(LIMITCODE)) {
		// if (language.equals("E")) {
		// hql =
		// "select a.limitEName from PrpDlimit a where a.id.riskCode in('0000', '"
		// + riskCode + "') and a.id.limitCode = ?";
		// } else {
		// hql =
		// "select a.limitCName from PrpDlimit a where a.id.riskCode in('0000', '"
		// + riskCode + "') and a.id.limitCode = ?";
		// }
		// }
//		else if (codeType.equals(TASKCODE)) {
//			if (language.equals("E")) {
//				hql = "select a.taskEName from SaaTask a where a.taskCode = ? and (a.taskCode like ? or a.taskEName like ?)";
//			} else {
//				hql = "select a.taskCName from SaaTask a where a.taskCode = ? and (a.taskCode like ? or a.taskEName like ?)";
//			}
//		}
		/**��ѯPrpDcode��*/
		else {
				hql = "select a.id.codeCode from PrpDcode a where a.id.codeType='"
					+ codeType + "' and (a.codeCName = ? or a.codeEName = ?)";
		}
		return hql;
	}

	/**
	 * ���HQL
	 * 
	 * @param codeType
	 *            ��������
	 * @param riskCode
	 *            ���ִ���
	 * @param language
	 *            ����(C:����/E:Ӣ��)
	 * @return HQL���
	 */
	private String generateListHql(String codeType, String riskCode,
			String language, String order) {
		String hql = null;
		// ** һ�������ౣ���������Ҫ�Ȳ�����ִ���*//*
		String[] riskCodeStr = StringUtils.split(riskCode, ",");
		if (riskCodeStr.length > 1) {
			String code = "";
			for (int i = 0; i < riskCodeStr.length; i++) {
				code += "'" + riskCodeStr[i] + "',";
			}
			riskCode = code.substring(1, (code.length() - 2));
		}
		// ��ʼƴsql
		if (codeType.equals(USERCODE)) {
			hql = "select a.userCode,a.userName from UtiIUser a where a.validStatus='1'";
		}
		/*
		 * else if (codeType.equals(COMCODE)) { if (language.equals("E")) { hql
		 * =
		 * "select a.comCode,a.comEName from PrpDcompany a where a.validStatus='1'"
		 * ; } else { hql =
		 * "select a.comCode,a.comCName from PrpDcompany a where a.validStatus='1'"
		 * ; }
		 * 
		 * if (order != null && (order.equalsIgnoreCase("asc") || order
		 * .equalsIgnoreCase("desc"))) { hql = hql + " order by 1 " + order; }
		 */
		return hql;
	}


	public String codeTypeTranslate(String systemCode, String codeType) {
		String codeCName = "";

		String hql = "select codeTypeDesc from PrpDtype a where a.codeType='"
				+ codeType +"'";
		logger.debug(hql);
		List nameList = this.findByHql(hql);
		if (nameList.size() > 0) {
			codeCName = nameList.get(0) + "";
			codeCName = codeCName.trim();
		}
		if (codeCName == "") {// ���codeCName���򷵻�codeType
			codeCName = codeType;
		}
		return codeCName;
	}

	/**
	 * �õ�������
	 * 
	 */
	public Long getCountCompany() {
		List<Long> size = this
				.findByHql("select count(a.comCode) from PrpDcompany a where a.validStatus='1' ");
		return size.get(0);
	}

	public String findFactoryType(String hql) {
		List<String> repairFactoryTypeList = super.findByHql(hql);
		String repairFactoryType = "";
		if (repairFactoryTypeList != null && repairFactoryTypeList.size() > 0) {
			repairFactoryType = repairFactoryTypeList.get(0);
		}
		return repairFactoryType;
	}
	public Page listCodeSelect(String codeType, String riskCode,
			String language, String matches, int pageNo, int pageSize,
			String userCode, String typeParam, String extraCond) throws Exception {
		String key = cacheManager.generateCacheKey("listCodeSelect", codeType,
				riskCode, language, matches, pageNo, pageSize, typeParam,
				extraCond);
		cacheManager.clearAllCacheManager();
		Object result = cacheManager.getCache(key);
		if ((Page) result != null) {
			return (Page) result;
		}

		Page page = null;
		if (USERTREE.equals(codeType) || GROUPTREE.equals(codeType)
				|| POWERUSER.equals(codeType)) {
			// donothing
		} else {
			String hql = generateCodeSelectHql(codeType, riskCode, language,
					"asc", matches, userCode, typeParam, extraCond);
			if("ComCode".equals(codeType)){
				page = this.findByHql(hql, pageNo, pageSize, matches, matches);
			}
		}
		cacheManager.putCache(key, page);
		return page;
	}
	//addPower�������Ʋ�ѯ��������֮�ڵ���ݣ�
	public  String addPower(String userCode) throws Exception{
		SaaAPIService saaAPIService = new SaaAPIServiceImpl();
		String condition =  saaAPIService.addPower(IConstants.SVRCODE, userCode, IConstants.SEARCH_COMCODE, IConstants.PRPDCOMPANY_BM, "", "");
		if(!"".equals(condition))
			return condition;
		else
			return " 1 != 1";
	}
	/**
	 * 
	 * @param codeType
	 * @param riskCode
	 * @param language
	 * @param order
	 * @param matches
	 * @param typeParam
	 * @return
	 * @throws Exception 
	 */
	private String generateCodeSelectHql(String codeType, String riskCode,
			String language, String order, String matches, String userCode,
			String typeParam, String extraCond) throws Exception {
		// cacheManager.clearAllCacheManager();
		StringBuffer hql = new StringBuffer();
		String[] riskCodeStr = StringUtils.split(riskCode, ",");
		if (riskCodeStr.length > 1) {
			String code = "";
			for (int i = 0; i < riskCodeStr.length; i++) {
				code += "'" + riskCodeStr[i] + "',";
			}
			riskCode = code.substring(1, (code.length() - 2));
		}
		if (codeType.equals("ComCode")) {
			hql.append(" select prpDcompany.comCode,prpDcompany.comCName from PrpDcompany prpDcompany where prpDcompany.validStatus='1' and (prpDcompany.comCode like ? or prpDcompany.comCName like ?) and ");
			String con = addPower(userCode);
			hql.append(con);
			}
		return hql.toString();
	}
}
