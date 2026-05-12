/************************************************************************
 * Description: 理赔系统代码获取类
 * Author     : 中科软
 * CreateDate : 2013-03-02
 * UpdateLog  : Name           Date         Reason/Content
 *          ------------------------------------------------------------
 *
 ************************************************************************/
package com.sinosoft.claim.common.service.spring;

import ins.framework.cache.CacheManager;
import ins.framework.cache.CacheService;
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;
import ins.framework.utils.StringUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.sinosoft.claim.claim.vo.Code;
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.PrpDcompanyService;
import com.sinosoft.claim.common.service.facade.PrpDcurrencyService;
import com.sinosoft.claim.common.service.facade.PrpDidentifierService;
import com.sinosoft.claim.common.service.facade.PrpDkindService;
import com.sinosoft.claim.common.service.facade.PrpDriskConfigService;
import com.sinosoft.claim.common.service.facade.PrpDriskService;
import com.sinosoft.claim.common.service.facade.PrpDuserService;
import com.sinosoft.claim.common.service.facade.UtiCodeTransferService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpCitemKind;
import com.sinosoft.claim.schema.model.PrpDcode;
import com.sinosoft.claim.schema.model.PrpDcodeId;
import com.sinosoft.claim.schema.model.PrpDcompany;
import com.sinosoft.claim.schema.model.PrpDcurrency;
import com.sinosoft.claim.schema.model.PrpDexch;
import com.sinosoft.claim.schema.model.PrpDidentifier;
import com.sinosoft.claim.schema.model.PrpDkind;
import com.sinosoft.claim.schema.model.PrpDkindId;
import com.sinosoft.claim.schema.model.PrpDlimit;
import com.sinosoft.claim.schema.model.PrpDlimitId;
import com.sinosoft.claim.schema.model.PrpDrisk;
import com.sinosoft.claim.schema.model.PrpDriskConfig;
import com.sinosoft.claim.schema.model.PrpLthirdParty;
import com.sinosoft.claim.schema.model.UtiCodeTransfer;
import com.sinosoft.claim.schema.model.UtiUserGrade;
import com.sinosoft.claim.schema.service.facade.PrpDagentService;
import com.sinosoft.claim.schema.service.facade.PrpDcodeService;
import com.sinosoft.claim.schema.service.facade.PrpDexchService;
import com.sinosoft.claim.schema.service.facade.PrpDlimitService;
import com.sinosoft.claim.schema.service.facade.PrpLthirdPartyService;
import com.sinosoft.claim.schema.service.facade.UtiConfigService;
import com.sinosoft.sysframework.common.util.DataUtils;
import com.sinosoft.sysframework.common.util.SqlUtils;
import com.sun.org.apache.commons.beanutils.PropertyUtils;

@SuppressWarnings("unchecked")
public class CodeServiceSpringImpl extends GenericDaoHibernate<PrpDcode, PrpDcodeId> implements CodeService {
	/** 基础代码service */
	private PrpDcodeService prpDcodeService;
	/** 险种险类代码配置service */
	private UtiCodeTransferService utiCodeTransferService;
	/** 币别配置service */
	private PrpDcurrencyService prpDcurrencyService;
	/** 用户service */
	private PrpDuserService prpDuserService;
	/** 机构service */
	private PrpDcompanyService prpDcompanyService;
	/** 代理人service */
	private PrpDagentService prpDagentService;
	/** 险类service */
	private PrpDriskService prpDriskService;
	/** 检验人代码service */
	private PrpDidentifierService prpDidentifierService;
	/** 险别配置表service */
	private PrpDriskConfigService prpDriskConfigService;
	/** 基础配置表service */
	private UtiConfigService utiConfigService;
	/** 限额表service */
	private PrpDlimitService prpDlimitService;
	/** 险别配置表service */
	private PrpDkindService prpDkindService;
	/** 车辆信息service */
	private PrpLthirdPartyService prpLthirdPartyService;
	/** 每日汇率信息service  */
	private PrpDexchService prpDexchService;

	/**
	 * 初始缓存实例
	 */
	private static CacheService cacheManager = CacheManager.getInstance("code");

	/**
	 * 代码服务<br>
	 * 支持的代码类型有：<br>
	 * @param codeType 代码类型
	 * @param riskCode 险种代码
	 * @param language 语种(C:中文/E:英文)
	 * @param order 排序(asc:升序/desc:降序)
	 * @param matches 匹配字符串
	 * @return 代码List
	 */
	@Override
	public Page listCodeSelect(String codeType, String riskCode, String language, String matches, int pageNo, int pageSize, String userCode, String typeParam, String extraCond) {
		String key = cacheManager.generateCacheKey("listCodeSelect", codeType, riskCode, language, matches, pageNo, pageSize, typeParam, extraCond);
		Object result = cacheManager.getCache(key);
		if ((Page) result != null) {
			return (Page) result;
		}
		Page page = null;
		String hql = generateCodeSelectHql(codeType, riskCode, language, ConstantCodes.SQLLanguage.ASC, matches, userCode, typeParam, extraCond);
		page = this.findByHqlNoLimit(hql, pageNo, pageSize, matches, matches);
		cacheManager.putCache(key, page);
		return page;
	}

	/**
	 * 翻译代码
	 * @param codeType 代码类型
	 * @param codeCode 代码
	 * @param riskCode 险种代码
	 * @param language 语种(C:中文/E:英文)
	 * @return 代码名称
	 */
	@Override
	public String translateCode(String codeType, String codeCode, String riskCode, String language) {

		if (codeCode == null) {
			return "";
		}

		// 准备放入缓存
		String key = cacheManager.generateCacheKey("translateCode", codeType, codeCode, riskCode, language);
		Object result = cacheManager.getCache(key);
		if (result != null) {
			return (String) result;
		}

		String codeName = null;
		StringBuilder buffer = new StringBuilder();
		if (codeCode.indexOf(",") > -1) {
			String[] codes = StringUtils.split(codeCode, ",");
			for (String code : codes) {
				String hql = generateTranslateHql(codeType, code.trim(), riskCode, language);
				List<?> nameList = this.findByHql(hql, code.trim());
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
			String hql = generateTranslateHql(codeType, codeCode, riskCode, language);
			// 处理模板编号查询
			List<?> nameList = null;
			nameList = this.findByHql(hql, codeCode);
			// 处理模板编号查询
			if (nameList.size() > 0) {
				codeName = nameList.get(0) + "";
				codeName = codeName.trim();
			}
		}
		if (codeName == null) {
			codeName = codeCode;
		}

		cacheManager.putCache(key, codeName);
		return codeName;
	}

	/**
	 * 翻译代码
	 * @param codeType 代码类型
	 * @param codeCode 代码
	 * @param language 语种(C:中文/E:英文)
	 * @return 代码名称
	 */
	@Override
	public String translateCode(String codeType, String codeCode, String language) {
		return this.translateCode(codeType, codeCode, "0000", language);
	}

	/**
	 * 翻译机构代码
	 * @param codeCode 代码
	 * @param language 语种(C:中文/E:英文)
	 * @return 代码名称
	 */
	@Override
	public String translateComCode(String codeCode, boolean isChinese) {
		String codeName = ""; // 查询到的名称
		try {
			codeName = this.prpDcompanyService.translateCode(codeCode, isChinese);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return codeName;
	}

	/**
	 * 翻译员工代码
	 * @param codeCode 代码
	 * @param language 语种(C:中文/E:英文)
	 * @return 代码名称
	 */
	@Override
	public String translateUserCode(String codeCode, boolean isChinese) {
		String codeName = ""; // 查询到的名称
		try {
			codeName = this.prpDuserService.translateCode(codeCode, isChinese);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return codeName;
	}

	/**
	 * 翻译险种代码
	 * @param riskCode 险种代码
	 * @param language 语种(C:中文/E:英文)
	 * @return 代码名称
	 * @throws Exception
	 */
	@Override
	public String translateRiskCode(String riskCode, boolean isChinese) throws Exception {
		String codeName = "";
		if (riskCode != null && !"".equals(riskCode)) {
			PrpDrisk prpDrisk = super.get(PrpDrisk.class, riskCode);
			if (prpDrisk != null) {
				if (isChinese) {
					codeName = prpDrisk.getRiskCName();
				} else {
					codeName = prpDrisk.getRiskEName();
				}
			}
		}
		return codeName;
	}
	
	/**
	 * 翻译险种代码
	 * @param riskCode 险种代码
	 * @param language 语种(C:中文/E:英文)
	 * @return 代码名称
	 * @throws Exception
	 */
	@Override
	public String translateLimitType(String typeCode, boolean isChinese) throws Exception {
		String codeName = "";
		if (typeCode != null && !"".equals(typeCode)) {
			String sql = "select codecname,codeename from ccicdms.prpdnewcode where codetype='LimitType' and codecode='" + typeCode + "'";
			List<?> resultList = HibernateUtils.findbySql(super.getSession(), sql);
			if (!CommonUtils.isEmpty(resultList)) {
				Object[] object = (Object[]) resultList.get(0);
				if (isChinese) {
					codeName = DataUtils.dbNullToEmpty((String) object[0]);
				} else {
					codeName = DataUtils.dbNullToEmpty((String) object[1]);
				}
			}
		}
		return codeName;
	}

	/**
	 * 代码服务<br>
	 * 支持的代码类型有：<br>
	 * @param codeType 代码类型
	 * @param riskCode 险种代码
	 * @param language 语种(C:中文/E:英文)
	 * @return 代码Map
	 */
	@Override
	public Map<String, String> listCodes(String codeType, String riskCode, String language) {
		String key = cacheManager.generateCacheKey("listCodes", codeType, riskCode, language);
		Object result = cacheManager.getCache(key);
		if (result != null) {
			return (Map<String, String>) result;
		}

		TreeMap<String, String> map = new TreeMap<String, String>();
		String hql = generateListHql(codeType, riskCode, language, ConstantCodes.SQLLanguage.ASC);

		List<Object[]> list = this.findByHql(hql);
		for (int i = 0; i < list.size(); i++) {
			Object[] arrValue = (Object[]) list.get(i);
			String code = (arrValue[0] + "").trim();
			String name = (arrValue[1] + "").trim();
			map.put(code, name);
		}
		cacheManager.putCache(key, map);
		return map;
	}

	/**
	 * 代码服务<br>
	 * 支持的代码类型有：<br>
	 * @param codeType 代码类型
	 * @param riskCode 险种代码
	 * @param language 语种(C:中文/E:英文)
	 * @return 代码Map
	 */
	@Override
	public Map<String, String> listCodesBySql(String codeType, String riskCode, String language) {
		String key = cacheManager.generateCacheKey("listCodes", codeType, riskCode, language);
		Object result = cacheManager.getCache(key);
		if (result != null) {
			return (Map<String, String>) result;
		}

		TreeMap<String, String> map = new TreeMap<String, String>();
		String sql = generateListHql(codeType, riskCode, language, ConstantCodes.SQLLanguage.ASC);

		List<Object[]> list = this.findBySql(sql);
		for (int i = 0; i < list.size(); i++) {
			Object[] arrValue = (Object[]) list.get(i);
			String code = (arrValue[0] + "").trim();
			String name = (arrValue[1] + "").trim();
			map.put(code, name);
		}
		cacheManager.putCache(key, map);
		return map;
	}

	/**
	 * 代码服务<br>
	 * 支持的代码类型有：<br>
	 * @param codeType 代码类型
	 * @param riskCode 险种代码
	 * @param language 语种(C:中文/E:英文)
	 * @param otherCondition 其它条件
	 * @return 代码Map
	 */
	@Override
	public Map<String, String> listCodes(String codeType, String riskCode, String language, String otherCondition) {

		String key = cacheManager.generateCacheKey("listCodes", codeType, riskCode, language, otherCondition);
		Object result = cacheManager.getCache(key);
		if (result != null) {
			return (Map<String, String>) result;
		}

		TreeMap<String, String> map = new TreeMap<String, String>();
		String hql = generateListHql(codeType, riskCode, language, "");
		if (otherCondition != null && !otherCondition.trim().equals("")) {
			hql = hql + ConstantCodes.SQLLanguage.AND + otherCondition;
		}

		List<Object[]> list = this.findByHql(hql);
		for (int i = 0; i < list.size(); i++) {
			Object[] arrValue = (Object[]) list.get(i);
			String code = (arrValue[0] + "").trim();
			String name = (arrValue[1] + "").trim();
			map.put(code, name);
		}
		cacheManager.putCache(key, map);
		return map;
	}

	/**
	 * 代码服务<br>
	 * 支持的代码类型有：<br>
	 * @param codeType 代码类型
	 * @param riskCode 险种代码
	 * @param language 语种(C:中文/E:英文)
	 * @return 代码List
	 */
	@Override
	public List<Code> listCodeList(String codeType, String riskCode, String language) {
		List<Code> codes = this.listOrderCodeList(codeType, riskCode, language, "");
		return codes;
	}

	/**
	 * 代码服务<br>
	 * 支持的代码类型有：<br>
	 * @param codeType 代码类型
	 * @param riskCode 险种代码
	 * @param language 语种(C:中文/E:英文)
	 * @param order 排序(asc:升序/desc:降序)
	 * @return 代码List
	 */
	@Override
	public List<Code> listOrderCodeList(String codeType, String riskCode, String language, String order) {

		String key = cacheManager.generateCacheKey("listOrderCodeList", codeType, riskCode, language, order);
		Object result = cacheManager.getCache(key);
		if (result != null) {
			return (List<Code>) result;
		}

		List<Code> codes = new ArrayList<Code>();
		String hql = generateListHql(codeType, riskCode, language, order);

		List<Object[]> list = this.findByHql(hql);
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
	 * 代码服务<br>
	 * 支持的代码类型有：<br>
	 * @param codeType 代码类型
	 * @param riskCode 险种代码
	 * @param language 语种(C:中文/E:英文)
	 * @param order 排序(asc:升序/desc:降序)
	 * @return 代码List
	 */
	public List<Code> listOrderCodeListSql(String codeType, String riskCode, String language, String order) {

		String key = cacheManager.generateCacheKey("listOrderCodeList", codeType, riskCode, language, order);
		Object result = cacheManager.getCache(key);
		if (result != null) {
			return (List<Code>) result;
		}

		List<Code> codes = new ArrayList<Code>();
		String hql = generateListHql(codeType, riskCode, language, order);
		List<Object[]> list = this.findBySql(hql);
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
	 * 代码翻译-生成HQL
	 * @param codeType 代码类型
	 * @param codeCode 代码
	 * @param riskCode 险种代码
	 * @param language 语种(C:中文/E:英文)
	 * @return HQL语句
	 */
	private String generateTranslateHql(String codeType, String codeCode, String riskCode, String language) {
		String hql = null;

		// 用户
		if (ConstantCodes.CodeConfig.USERCODE.equals(codeType)) {
			if (ConstantCodes.Language.ENGLISH.equals(language)) {

			} else {
				hql = "select a.userName from PrpDuser a where a.userCode = ?";
			}
			// 机构
		} else if (ConstantCodes.CodeConfig.COMCODE.equals(codeType)) {
			if (ConstantCodes.Language.ENGLISH.equals(language)) {
			} else {
				hql = " select a.comCName from PrpDcompany a where a.comCode = ? ";
			}
		} else {
			if (ConstantCodes.Language.ENGLISH.equals(language)) {
			} else {
				hql = " select a.codeCName from PrpDcode a where a.id.codeCode = ? and a.id.codeType = '" + codeType + "'";
			}
		}
		return hql;
	}

	/**
	 * 下拉列表框初始化-生成HQL
	 * @param codeType 代码类型
	 * @param riskCode 险种代码
	 * @param language 语种(C:中文/E:英文)
	 * @return HQL语句
	 */
	private String generateListHql(String codeType, String riskCode, String language, String order) {
		String hql = null;
		StringBuffer sb = null;

		if (riskCode != null && !"".equals(riskCode.trim())) {
			riskCode = riskCode.replaceAll(",", "','");
		}

		if (ConstantCodes.CodeConfig.COMCODEDOWN.equals(codeType)) { // 向下查询机构
			if (ConstantCodes.Language.ENGLISH.equals(language)) {
			} else if (ConstantCodes.StaticNum.THREE == Integer.parseInt(language)) {
				sb = new StringBuffer();
				sb.append(" select comcode, comcname                                   ");
				sb.append("   from PrpDcompany c                                       ");
				sb.append("  where validstatus = '1'                                   ");
				sb.append("    and comcode in (Select comcode                          ");
				sb.append("                      From PrpDcompany                      ");
				sb.append("                     Start With comcode = '").append(riskCode).append("' ");
				sb.append("                    Connect By Prior comcode = uppercomcode ");
				sb.append("                           And comcode != uppercomcode      ");
				sb.append("                           and comlevel in ('3', 'A'))      ");
				sb.append("    and comlevel in ('3', 'A')                              ");
				sb.append("    and exists                                              ");
				sb.append("  (select 'x'                                               ");
				sb.append("           from PrpDcompany f                               ");
				sb.append("          where f.comcode = c.uppercomcode                  ");
				sb.append("            and f.comlevel = '2')                           ");
				hql = sb.toString();
			} else {
				sb = new StringBuffer();
				sb.append(" select comcode, comcname                                   ");
				sb.append("   from PrpDcompany                                         ");
				sb.append("   where validstatus = '1'                                  ");
				sb.append("   and comcode in (Select comcode                           ");
				sb.append("                      From PrpDcompany                      ");
				sb.append("                     Start With comcode = '").append(riskCode).append("' ");
				sb.append("                    Connect By Prior comcode = uppercomcode ");
				sb.append("                           And comcode != uppercomcode      ");
				sb.append("                           and comlevel = '").append(language).append("') ");
				sb.append("    and comlevel = '").append(language).append("' ");
				hql = sb.toString();
			}
		} else if (ConstantCodes.CodeConfig.COMCODEUP.equals(codeType)) { // 向上查询机构
			if (ConstantCodes.Language.ENGLISH.equals(language)) {
			} else if (ConstantCodes.StaticNum.THREE == Integer.parseInt(language)) {
				sb = new StringBuffer();
				sb.append(" select comcode, comcname                                   ");
				sb.append("   from PrpDcompany                                         ");
				sb.append("   where validstatus = '1'                                  ");
				sb.append("     and comcode in (Select comcode                         ");
				sb.append("                      From PrpDcompany                      ");
				sb.append("                     Start With comcode = '").append(riskCode).append("' ");
				sb.append("                    Connect By Prior uppercomcode = comcode ");
				sb.append("                           And comcode != uppercomcode )    ");
				sb.append("    and comlevel in ('3','A')                               ");
				hql = sb.toString();
			} else {
				sb = new StringBuffer();
				sb.append(" select comcode, comcname                                   ");
				sb.append("   from PrpDcompany                                         ");
				sb.append("   where validstatus = '1'                                  ");
				sb.append("     and comcode in (Select comcode                         ");
				sb.append("                      From PrpDcompany                      ");
				sb.append("                     Start With comcode = '").append(riskCode).append("' ");
				sb.append("                    Connect By Prior uppercomcode = comcode ");
				sb.append("                           And comcode != uppercomcode )    ");
				sb.append("    and comlevel = '").append(language).append("' ");
				hql = sb.toString();
			}
		} else {
		}

		if (order != null && (order.equalsIgnoreCase(ConstantCodes.SQLLanguage.ASC) || order.equalsIgnoreCase(ConstantCodes.SQLLanguage.DESC))) {
			hql = hql + " order by 1 " + order;
		}
		return hql;
	}

	/**
	 * 自动填充功能-生成HQL
	 * @param codeType 类型
	 * @param riskCode 险别
	 * @param language 语言
	 * @param order 排序
	 * @param matches
	 * @param typeParam
	 * @return
	 */
	private String generateCodeSelectHql(String codeType, String riskCode, String language, String order, String matches, String userCode, String typeParam, String extraCond) {
//		if (riskCode != null && !"".equals(riskCode.trim())) {
//			riskCode = riskCode.replaceAll(",", "','");
//		}
		String hql = null;

		if (!(typeParam == null || "null".equals(typeParam) || "".equals(typeParam.trim()))) {
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.typeParam", typeParam);
			queryRule.addEqual("id.codeType", codeType);
			queryRule.addEqual("validstate", ConstantCodes.VALID);
		}
		if (order != null && (order.equalsIgnoreCase(ConstantCodes.SQLLanguage.ASC) || order.equalsIgnoreCase(ConstantCodes.SQLLanguage.DESC))) {
			hql = hql + " order by 1 " + order;
			logger.debug(hql);
		}

		return hql;
	}

	/**
	 * List转换String
	 * @param isConnect 是否需要连接
	 * @param codeType 列表类型
	 * @param isBlankLine 是否有空白行
	 * @param order 列表类型
	 * @return String 列表值
	 */
	public String listToString(boolean isConnect, List<Code> codes, boolean isBlankLine) {
		StringBuffer buffer = new StringBuffer();
		if (codes.size() == 0) {
			return buffer.toString();
		} else {
			buffer.append("{");
			// 是否添加全部
			if (isBlankLine) {
				buffer.append("'ALLDATA':'',");
			}
			for (int i = 0; i < codes.size(); i++) {
				Code code = (Code) codes.get(i);
				buffer.append("'");
				buffer.append(code.getCode());
				buffer.append("':'");
				if (isConnect == true) {
					buffer.append(code.getCode() + "-" + code.getName());
				} else {
					buffer.append(code.getName());
				}
				buffer.append("'");
				if (i != codes.size() - 1) {
					buffer.append(",");
				}
			}
			buffer.append("}");
		}
		return buffer.toString();
	}

	/**
	 * 根据代码类型获取页面Select初始化列表
	 * @param isConnect 是否需要连接
	 * @param codeType 列表类型
	 * @param isBlankLine 是否有空白行
	 * @return String 列表值
	 */
	@Override
	public String getSelectValue(boolean isConnect, String codeType, boolean isBlankLine) {
		List<Code> codes = listOrderCodeList(codeType, null, null, null);
		return listToString(isConnect, codes, isBlankLine);
	}

	/**
	 * 根据代码类型获取页面Select初始化列表
	 * @param isConnect 是否需要连接
	 * @param codeType 列表类型
	 * @param riskCode 险种
	 * @param language 语种(C:中文/E:英文)
	 * @param isBlankLine 是否有空白行
	 * @param order 列表类型
	 * @return String 列表值
	 */
	@Override
	public String getSelectValue(boolean isConnect, String codeType, String riskCode, String language, String order, boolean isBlankLine) {
		List<Code> codes = listOrderCodeList(codeType, riskCode, language, ConstantCodes.SQLLanguage.ASC);
		return listToString(isConnect, codes, isBlankLine);
	}

	/**
	 * 根据代码类型获取页面Select初始化列表
	 * @param codeType 列表类型
	 * @param isBlankLine 是否有空白行
	 * @param isFieldSeparator 是否增加字段分隔符
	 * @return String 列表值
	 */
	@Override
	public String getSelectValue(String codeType, boolean isBlankLine, boolean isFieldSeparator) {
		List<Code> codes = listOrderCodeList(codeType, null, null, null);
		return listToString(codes, isBlankLine, isFieldSeparator);
	}

	/**
	 * 根据代码类型获取页面Select初始化列表
	 * @param codeType 列表类型
	 * @param isBlankLine 是否有空白行
	 * @param order 列表类型
	 * @return String 列表值
	 */
	public String getSelectValue(String codeType, boolean isBlankLine) {

		List<Code> codes = listOrderCodeList(codeType, null, null, null);

		return listToString(codes, isBlankLine);
	}

	/**
	 * 根据代码类型获取页面Select初始化列表
	 * @param codeType 列表类型
	 * @param language 语种(C:中文/E:英文)
	 * @param isBlankLine 是否有空白行
	 * @param order 列表类型
	 * @return String 列表值
	 */
	public String getSelectValue(String codeType, String language, String order, boolean isBlankLine) {

		List<Code> codes = listOrderCodeList(codeType, null, language, ConstantCodes.SQLLanguage.ASC);

		return listToString(codes, isBlankLine);
	}

	/**
	 * 根据代码类型获取页面Select初始化列表
	 * @param codeType 列表类型
	 * @param riskCode 险种
	 * @param language 语种(C:中文/E:英文)
	 * @param isBlankLine 是否有空白行
	 * @param order 列表类型
	 * @return String 列表值
	 */
	public String getSelectValueSql(String codeType, String riskCode, String language, String order, boolean isBlankLine) {

		List<Code> codes = listOrderCodeListSql(codeType, riskCode, language, ConstantCodes.SQLLanguage.ASC);

		return listToString(codes, isBlankLine);
	}

	/**
	 * 根据代码类型获取页面Select初始化列表
	 * @param codeType 列表类型
	 * @param riskCode 险种
	 * @param language 语种(C:中文/E:英文)
	 * @param isBlankLine 是否有空白行
	 * @param order 列表类型
	 * @return String 列表值
	 */
	public String getSelectValue(String codeType, String riskCode, String language, String order, boolean isBlankLine) {

		List<Code> codes = listOrderCodeList(codeType, riskCode, language, ConstantCodes.SQLLanguage.ASC);

		return listToString(codes, isBlankLine);
	}

	/**
	 * List转换String
	 * @param codeType 列表类型
	 * @param isBlankLine 是否有空白行
	 * @param order 列表类型
	 * @return String 列表值
	 */
	public String listToString(List<Code> codes, boolean isBlankLine) {
		StringBuffer buffer = new StringBuffer();
		if (codes.size() == 0) {
			return buffer.toString();
		} else {
			buffer.append("{");
			// 是否添加全部
			if (isBlankLine) {
				buffer.append("'ALLDATA':'',");
			}
			for (int i = 0; i < codes.size(); i++) {
				Code code = (Code) codes.get(i);
				buffer.append("'");
				buffer.append(code.getCode());
				buffer.append("':'");
				buffer.append(code.getCode() + "-" + code.getName());
				buffer.append("'");
				if (i != codes.size() - 1) {
					buffer.append(",");
				}
			}
			buffer.append("}");
		}
		return buffer.toString();
	}

	/**
	 * List转换String
	 * @param codeType 列表类型
	 * @param isBlankLine 是否有空白行
	 * @param isFieldSeparator 是否增加字段分隔符
	 * @return String 列表值
	 */
	public String listToString(List<Code> codes, boolean isBlankLine, boolean isFieldSeparator) {
		if (isFieldSeparator == false) {
			return listToString(codes, isBlankLine);
		}
		StringBuffer buffer = new StringBuffer();
		if (codes.size() == 0) {
			return buffer.toString();
		} else {
			buffer.append("{");
			// 是否添加全部
			if (isBlankLine) {
				buffer.append("'ALLDATA':'',");
			}
			for (int i = 0; i < codes.size(); i++) {
				Code code = (Code) codes.get(i);
				buffer.append("'");
				buffer.append(code.getCode());
				buffer.append(ConstantCodes.CodeConfig.FIELD_SEPARATOR);
				buffer.append(code.getName());
				buffer.append("':'");
				buffer.append(code.getName());
				buffer.append("'");
				if (i != codes.size() - 1) {
					buffer.append(",");
				}
			}
			buffer.append("}");
		}
		return buffer.toString();
	}

	/**
	 * 验证sql条件是否正确
	 * @author 中科软
	 * @param tableName 表名
	 * @param conditionSql sql语句
	 * @return
	 * @throws Exception
	 */
	public String checkConditionSql(String tableName, String conditionSql) throws Exception {
		StringBuffer buffer = new StringBuffer(100);
		String msg = "sql测试成功！";
		buffer.append("select * from (select * from ").append(tableName).append(" where 1=0) where 1=1 ");
		buffer.append(conditionSql);
		try {
			super.getSession().createSQLQuery(buffer.toString()).executeUpdate();
		} catch (Exception e) {
			msg = e.getMessage();
		}
		return msg;
	}

	/**
	 * 根据业务类型及险种查询业务代码
	 * @param codetype：业务类型
	 * @param riskcode：险种代码
	 * @return PrpDcodeDto 代码查询
	 * @throws SQLException
	 * @throws Exception
	 */
	public List<PrpDcode> getCodeType(String codetype, String riskcode) throws Exception {
		String conditions = ""; // 查询条件
		conditions = " codetype = '" + codetype + "' AND validstatus = '1' AND codecode in (SELECT codecode FROM prpdcoderisk WHERE codetype = '" + codetype + "' AND (riskcode = '" + riskcode + "' OR riskcode = '0000'))";
		if ("ImageType".equals(codetype)) {
			conditions = conditions + " order by codecode";
		}

		String statement = "Select CodeType," + " CodeCode," + " CodeCName," + " CodeEName," + " NewCodeCode," + " ValidStatus," + " Flag From PrpDcode Where " + conditions;
		List<PrpDcode> resultList = new ArrayList<PrpDcode>();
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		List<?> tempList = HibernateUtils.findbySql(session, statement, 0, 0);
		PrpDcode prpDcode = null;
		for (int i = 0; i < tempList.size(); i++) {
			Object[] object = (Object[]) tempList.get(i);
			prpDcode = new PrpDcode();
			PrpDcodeId prpDcodeId = new PrpDcodeId();
			prpDcodeId.setCodeType((String) object[0]);
			prpDcodeId.setCodeCode((String) object[1]);
			prpDcode.setCodeCName((String) object[2]);
			prpDcode.setCodeEName((String) object[3]);
			prpDcode.setNewCodeCode((String) object[4]);
			prpDcode.setValidStatus((String) object[5]);
			prpDcode.setFlag((String) object[6]);
			prpDcode.setId(prpDcodeId);
			resultList.add(prpDcode);
		}
		return resultList;
	}
	
	/** mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程  start**/
	/**
	 *
	 * 
	 * @param codetype：业务类型
	 * @param codeCode：
	 * @throws SQLException
	 * @throws Exception
	 */
	public List<PrpDcode> getNewCodeCode(String codetype, String codeCode) throws Exception {
		String conditions = ""; // 查询条件
		conditions = " codetype = '" + codetype + "' AND validstatus = '1' AND codecode = '" + codeCode + "'";


		String statement = "Select CodeType," + " CodeCode," + " CodeCName," + " CodeEName," + " NewCodeCode," + " ValidStatus," + " Flag From ccicdms.PRPDNEWCODE Where " + conditions;
		List<PrpDcode> resultList = new ArrayList<PrpDcode>();
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		List<?> tempList = HibernateUtils.findbySql(session, statement, 0, 0);
		PrpDcode prpDcode = null;
		for (int i = 0; i < tempList.size(); i++) {
			Object[] object = (Object[]) tempList.get(i);
			prpDcode = new PrpDcode();
			PrpDcodeId prpDcodeId = new PrpDcodeId();
			prpDcodeId.setCodeType((String) object[0]);
			prpDcodeId.setCodeCode((String) object[1]);
			prpDcode.setCodeCName((String) object[2]);
			prpDcode.setCodeEName((String) object[3]);
			prpDcode.setNewCodeCode((String) object[4]);
			prpDcode.setValidStatus((String) object[5]);
			prpDcode.setFlag((String) object[6]);
			prpDcode.setId(prpDcodeId);
			resultList.add(prpDcode);
		}
		return resultList;
	}
	/** mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程  end**/
	
	@Override
	public String translateProductCode(String conFigCode) {
		String codeName = "";
		if (conFigCode == null) {
			codeName = "";
		} else {
			UtiCodeTransfer utiCodeTransfer = null;
			try {
				utiCodeTransfer = this.utiCodeTransferService.findByPrimaryKey(conFigCode);
				codeName = utiCodeTransfer.getOuterCode();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return codeName;
	}

	@Override
	public String translateBusinessCode(String businessCode, boolean isSearchClaimNo) {
		String buinessNo = "";
		if (CommonUtils.isEmpty(businessCode)) {
			return buinessNo;
		}
		String statement = null;
		if (isSearchClaimNo) {
			statement = "Select claimno from prplclaim Where registno='" + businessCode + "'";
		} else {
			statement = "Select registno from prplclaim Where claimno='" + businessCode + "'";
		}
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		List<?> tempList = HibernateUtils.findbySql(session, statement, 0, 0);
		for (int i = 0; i < tempList.size(); i++) {
			buinessNo = (String) tempList.get(i);
		}
		return buinessNo;
	}

	/**
	 * 关联报案，一个报案号对应多个立案号
	 * @param businessCode 报案号码或者立案号码
	 * @param isSearchClaimNo 是查询立案号码，还是报案号码
	 * @return 立案号或者报案号
	 */
	public String[] translateBusinessCodes(String businessCode, boolean isSearchClaimNo) throws Exception {
		String buinessNo = "";

		if (CommonUtils.isEmpty(businessCode)) {
			return new String[0];
		}
		String statement = null;
		if (isSearchClaimNo) {
			statement = "Select claimno from prplclaim Where registno='" + businessCode + "'";
		} else {
			statement = "Select registno from prplclaim Where claimno='" + businessCode + "'";
		}
		Session session = super.getSession();
		List<?> tempList = HibernateUtils.findbySql(session, statement, 0, 0);
		String[] businessNos = new String[tempList.size()];
		for (int i = 0; i < tempList.size(); i++) {
			buinessNo = (String) tempList.get(i);
			businessNos[i] = buinessNo;
		}
		return businessNos;
	}

	/**
	 * 根据险种，险别代码得到险别名称
	 * @param riskCode 险别
	 * @param kindCode 险种
	 * @param isChinese 是否中文名称
	 * @return String 返回中午或者英文名称
	 * @throws Exception
	 */
	@Override
	public String translateKindCode(String riskCode, String kindCode, boolean isChinese) {
		String codeName = "";
		try {
			if (!CommonUtils.isEmpty(riskCode) && !CommonUtils.isEmpty(kindCode)) {
				codeName = this.translateCode(riskCode, kindCode, isChinese);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return codeName;
	}

	/**
	 * 根据子险种代码，险种得到子险种名称
	 * @param userCode String
	 * @param isChinese boolean
	 * @throws Exception
	 * @return String
	 */
	public String translateCode(String riskCode, String kindCode, boolean isChinese) throws Exception {
		String codeName = "";
		try {
			if (!CommonUtils.isEmpty(riskCode) && !CommonUtils.isEmpty(kindCode)) {
				PrpDkindId prpDkindId = new PrpDkindId();
				prpDkindId.setKindCode(kindCode);
				prpDkindId.setRiskCode(riskCode);
				PrpDkind prpDkind = null;
				prpDkind = prpDkindService.findPrpDkindById(prpDkindId);
				if (prpDkind != null) {
					if (isChinese) {
						codeName = prpDkind.getKindCName();
					} else {
						codeName = prpDkind.getKindEName();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return codeName;
	}

	@Override
	public String translateRiskCodetoConfigCode(String riskCode) {
		String codeName = "";
		try {
			if (!CommonUtils.isEmpty(riskCode)) {
				List<?> utiCodeTransferList = new ArrayList<Object>();
				try {
					utiCodeTransferList = this.utiCodeTransferService.findByConditions(" outercode='" + riskCode + "'");
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (utiCodeTransferList != null && utiCodeTransferList.size() != 0) {
					UtiCodeTransfer utiCodeTransfer = (UtiCodeTransfer) utiCodeTransferList.get(0);
					codeName = utiCodeTransfer.getConfigCode();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return codeName;
	}

	@Override
	public String translateRiskCodetoRiskType(String riskCode) {
		String codeName = "";
		try {
			if (!CommonUtils.isEmpty(riskCode)) {
				List<UtiCodeTransfer> utiCodeTransferList =  this.utiCodeTransferService.findByConditions(" outercode='" + riskCode + "'");
				if (utiCodeTransferList != null && utiCodeTransferList.size() != 0) {
					UtiCodeTransfer utiCodeTransfer = (UtiCodeTransfer) utiCodeTransferList.get(0);
					codeName = utiCodeTransfer.getRiskType();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return codeName;
	}

	/**
	 * 获得PrpDcode的集合
	 * @param conditions 查询条件
	 * @return 查询的结果集
	 * @throws Exception
	 */
	@Override
	public List<PrpDcode> findPrpDcodeByConditions(String conditions) {
		if (conditions == null || conditions.length() == 0) {
			conditions = "1=1";
		}
		try {
			return this.prpDcodeService.findByConditions(conditions);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String translateCurrencyCode(String currencyCode, boolean isChinese) {
		String codeName = ""; // 查询到的名称
		codeName = this.prpDcurrencyService.translateCode(currencyCode, isChinese);
		return codeName;
	}

	@Override
	public String translateAgentName(String agentCode) {
		String agentName = ""; // 查询到的名称
		try {
			agentName = this.prpDagentService.translateAgentName(agentCode);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return agentName;
	}

	@Override
	public String translateCodeCode(String codeType, String codeCode, boolean isChinese) {
		String codeName = "";
		if (DataUtils.emptyToNull(codeCode) != null && DataUtils.emptyToNull(codeType) != null) {
			PrpDcode prpDcode = null;
			try {
				prpDcode = this.prpDcodeService.findByPrimaryKey(codeType, codeCode);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (prpDcode != null) {
				if (isChinese) {
					codeName = prpDcode.getCodeCName();
				} else {
					codeName = prpDcode.getCodeEName();
				}
			}
		}
		return codeName;
	}

	@Override
	public List<PrpDcode> getCodeTypeCarKind(String codetype, String classCode) {
		String conditions = ""; // 查询条件
		conditions = " codetype = '" + codetype + "' AND validstatus = '1' AND codecode in (SELECT codecode FROM prpdcoderisk WHERE codetype = '" + codetype + "' AND (riskcode like '" + classCode + "%'))";
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		List<PrpDcode> list = super.find(queryRule);
		if ("CarKind".equals(codetype) && list != null) {
			List<PrpDcode> listTemp = new ArrayList<PrpDcode>(list.size());
			PrpDcode temp = null;
			for (int i = 0; i < list.size(); i++) {
				temp = new PrpDcode(list.get(i));
				temp.setCodeCName(temp.getId().getCodeCode() + "-" + temp.getCodeCName());
				listTemp.add(temp);
			}
			list = listTemp;
		}
		return list;
	}

	/**
	 * 查询代码
	 * @param List：查询代码
	 * @throws Exception
	 */
	@Override
	public List<PrpDcode> findByConditions(String codeType, String conditions, int pageNo, int rowsPerPage) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions.toString());
		queryRule.addEqual("id.codeType", codeType);
		return prpDcodeService.findPrpDcode(queryRule, pageNo, rowsPerPage);
	}

	/**
	 * 查询代码
	 * @param List：查询代码
	 * @throws Exception
	 */
	public List<PrpDcode> findByConditions(String conditions) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions.toString());
		return prpDcodeService.findPrpDcode(queryRule);
	}

	/**
	 * 查询免赔条件
	 * @param Collection：查询代码
	 * @throws Exception
	 */
	@Override
	public List<PrpDcode> getDeductCondition(String riskCode) throws Exception {
		String condtions = " codecode in (select codecode from prpdcoderisk where riskcode = '" + riskCode + "' and codetype = 'DeductCond') and ValidStatus='1' and codetype = 'DeductCond'";
		return this.prpDcodeService.findPrpDcode(QueryRule.getInstance().addSql(condtions));
	}

	/**
	 * 根据机构代码查询机构级别
	 * @param comCode：机构代码
	 * @return 机构级别
	 * @throws Exception
	 */
	@Override
	public String getComLevel(String comCode) throws Exception {
		PrpDcompany prpDcompany = new PrpDcompany();
		prpDcompany = this.prpDcompanyService.query(comCode);
		return prpDcompany.getComLevel();
	}

	/**
	 * 根据用户代码，查询用户的显示价格权限
	 * @param userCode：用户代码
	 * @return 用户价格权限
	 * @throws Exception
	 */
	public String getUserShowPriceFlag(String userCode) throws Exception {
		return this.userShowPriceFlagQuery(userCode);
	}

	/**
	 * 根据用户代码，查询用户的显示价格权限
	 * @param userCode：用户代码
	 * @return 用户价格权限
	 * @throws SQLException
	 * @throws Exception
	 */
	public String userShowPriceFlagQuery(String userCode) throws Exception {
		String showPriceFlag = "";
		if (userCode == null) {
			showPriceFlag = "000000";
			return showPriceFlag;
		} else {
			if (userCode.equals("")) {
				showPriceFlag = "000000";
				return showPriceFlag;
			}
		}
		String hql = "Select showpriceFlag from PrpDuserCA Where usercode=?";
		List<?> showpriceFlagList = new ArrayList<Object>();
		showpriceFlagList = this.findByHql(hql, userCode);
		if (showpriceFlagList.size() > 0) {
			showPriceFlag = showpriceFlagList.get(0) + "";
			showPriceFlag = showPriceFlag.trim();
		}
		return showPriceFlag;

	}

	/**
	 * 返回货币名称，代码列表
	 * @return Collection 代码查询
	 * @throws SQLException
	 * @throws Exception
	 */
	@Override
	public List<PrpDcurrency> getCurrencyList() throws Exception {
		return this.prpDcurrencyService.findPrpDcurrency(QueryRule.getInstance().addSql(" 1=1 "));
	}

	/**
	 * 查询PrpdLimit表，赔偿限额专用转换
	 * @author 中科软
	 * @param riskCode 险种
	 * @param limitCode 限额代码
	 * @param isChinese 是否中文
	 * @return
	 * @throws Exception
	 */
	@Override
	public String translateLimit(String riskCode, String limitCode, boolean isChinese) throws Exception {
		String limitName = "";

		if (riskCode == null || limitCode == null) {
			limitName = "";
		} else {
			if (!riskCode.equals("") && !limitCode.equals("")) {
				this.translateLimitQuery(riskCode, limitCode, isChinese);
			}
		}
		return limitName;
	}

	/**
	 * 查询PrpdLimit表，赔偿限额专用转换
	 * @author 中科软
	 * @param riskCode 险种
	 * @param limitCode 限额代码
	 * @param isChinese 是否中文
	 * @return
	 * @throws Exception
	 */
	@Override
	public String translateLimitQuery(String riskCode, String limitCode, boolean isChinese) throws Exception {
		String limitName = "";
		if (riskCode == null || limitCode == null) {
			return limitName;
		} else {
			if (riskCode.equals("") || limitCode.equals("")) {
				return limitName;
			}
		}
		PrpDlimitId id = new PrpDlimitId();
		id.setLimitCode(limitCode);
		id.setRiskCode(riskCode);
		PrpDlimit prpDlimit = prpDlimitService.findPrpDlimitById(id);
		if (prpDlimit != null) {
			limitName = prpDlimit.getLimitCName();
		}

		return limitName;
	}

	/**
	 * 查询本报案的相关车牌号码的列表
	 * @param registNo 报案号码
	 * @return List 代码查询
	 * @throws Exception
	 */
	@Override
	public List<PrpLthirdParty> getLicenseNoList(String registNo) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.registNo", registNo.trim());
		return this.prpLthirdPartyService.findPrpLthirdParty(queryRule);
	}

	/**
	 * 根据险种代码得到险类的代码
	 * @param riskCode 险种
	 * @return 险类
	 * @throws Exception
	 */
	@Override
	public String translateClassCodeByRiskCode(String riskCode) throws Exception {
		String classCode = "";

		if (riskCode != null && !riskCode.equals("")) {
			PrpDrisk prpDrisk = prpDriskService.findPrpDrisk(riskCode);
			if (prpDrisk != null)
				classCode = prpDrisk.getClassCode();
		}

		return classCode;
	}

	/**
	 * 根据险种，险别代码得到计入总保额标志
	 * @param riskCode String
	 * @param kindCode String
	 * @throws Exception
	 * @return String
	 */
	@Override
	public String translateCalculateFlag(String riskCode, String kindCode) throws Exception {
		String codeName = "";

		if (riskCode == null || kindCode == null) {
			codeName = "";
		} else {
			if (!riskCode.equals("") && !kindCode.equals("")) {
				this.translateCalculateFlagQuery(riskCode, kindCode);
			}
		}
		return codeName;
	}

	/**
	 * 根据子险种代码，险种得到查询计入总保额标志
	 * @param userCode String
	 * @param isChinese boolean
	 * @throws Exception
	 * @return String
	 */
	public String translateCalculateFlagQuery(String riskCode, String kindCode) throws Exception {
		String codeName = "";
		try {
			if (riskCode == null || kindCode == null) {
				codeName = "";
				return codeName;
			} else {
				if (riskCode.equals("") || kindCode.equals("")) {
					codeName = "";
					return codeName;
				}
			}
			PrpDkindId prpDkindId = new PrpDkindId();
			prpDkindId.setKindCode(kindCode);
			prpDkindId.setRiskCode(riskCode);
			PrpDkind prpDkind = new PrpDkind();
			prpDkind = prpDkindService.findPrpDkindById(prpDkindId);
			codeName = prpDkind.getCalculateFlag();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return codeName;
	}

	/**
	 * 根据客户代码得到客户姓名
	 * @param agentCode 客户代码
	 */
	@Override
	public String translateCustomerCName(String customerCode) throws Exception {
		String customerCName = "";

		try {
			if (customerCode == null) {
				customerCName = "";
			} else {
				if (!customerCode.equals("")) {
					customerCName = this.prpDagentService.translateAgentName(customerCode);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return customerCName;
	}

	/**
	 * 根据代码对照表转换代码
	 * @param riskCode String
	 * @param kindCode String
	 * @param isChinese boolean
	 * @throws Exception
	 * @return String
	 */
	@Override
	public String getRiskCodebyRiskType(String riskType) throws Exception {
		String condition = "";
		try {
			if (riskType != null) {
				List<UtiCodeTransfer> utiCodeTransferList = new ArrayList<UtiCodeTransfer>();
				utiCodeTransferList = utiCodeTransferService.findByConditions(" risktype='" + riskType + "'");
				if (utiCodeTransferList != null && utiCodeTransferList.size() != 0) {
					UtiCodeTransfer UtiCodeTransfer = (UtiCodeTransfer) utiCodeTransferList.get(0);
					condition = condition + "'" + UtiCodeTransfer.getOuterCode() + "'";
					for (int i = 1; i < utiCodeTransferList.size(); i++) {
						condition = condition + ",'" + ((UtiCodeTransfer) utiCodeTransferList.get(i)).getOuterCode() + "'";
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return condition;
	}

	/**
	 * 根据代码对照表转换代码
	 * @param riskCode String
	 * @param kindCode String
	 * @param isChinese boolean
	 * @throws Exception
	 * @return String
	 */
	@Override
	public String translateRiskCodetoInnerCode(String riskCode) throws Exception {
		String codeName = "";
		try {
			if (riskCode == null) {
				codeName = "";
			} else {
				List<UtiCodeTransfer> utiCodeTransferList = new ArrayList<UtiCodeTransfer>();
				utiCodeTransferList = utiCodeTransferService.findByConditions(" outercode='" + riskCode + "'");
				if (utiCodeTransferList != null && utiCodeTransferList.size() != 0) {
					UtiCodeTransfer UtiCodeTransfer = (UtiCodeTransfer) utiCodeTransferList.get(0);
					codeName = UtiCodeTransfer.getInnerCode();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return codeName;
	}

	/**
	 * 根据国外检验代理人代码转换成国外检验代理人姓名
	 * @param checkAgentCodeSQL 查询语句
	 * @exception throws Exception
	 * @return prpDidentifierDtoList
	 */
	public List<PrpDidentifier> translateCheckAgentCodeToName(String checkAgentCodeSQL) throws Exception {
		List<PrpDidentifier> prpDidentifierList = new ArrayList<PrpDidentifier>();
		try {
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addSql(checkAgentCodeSQL.toString());
			prpDidentifierList = this.prpDidentifierService.findPrpDidentifier(queryRule);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return prpDidentifierList;
	}

	/**
	 * 查询接口:查询某险种,某部门(可选,null为险种配置属性,非null为业务配置属 性),配置代码为configCode的配置项
	 * @param comCode String 部门代码,如果为null,则说明为险种配置属性,如果 为 代码
	 *            值,则说明是业务配置属性.业务配置代码查询时,对部门代码采取上溯 处理,找最近一级部门.
	 * @param riskCode String 险种代码,这个是必要给的
	 * @param configCode String 配置项代码.
	 * @throws Exception 查询异常
	 */
	@Override
	public PrpDriskConfig queryRiskConfig(String comCode, String riskCode, String configCode) throws Exception {
		return this.riskConfigQuery(comCode, riskCode, configCode);
	}

	/**
	 * 根据主键获得PrpDcompany
	 * @param comcode
	 * @return
	 * @throws Exception
	 */
	@Override
	public PrpDcompany findPrpDcompanyByPrimaryKey(String comCode) throws Exception {
		if (comCode == null || comCode.length() == 0) {
			return null;
		} else {
			return prpDcompanyService.findPrpDcompany(comCode);
		}

	}

	/**
	 * 通过一次查询获得某个用户的UtiUserGrade所有结果集
	 * @param userCode
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<UtiUserGrade> findUtiUserGradeListByUserCode(String userCode) throws Exception {
		String hql = "Select u.usercode,u.username,p.comcode,p.comcname,u.gradecode,u.gradename,u.remark,p.flag from utiusergrade ug "
				+ "join prpdcompany p On ug.comcode = p.comcode join utigrade g On ug.gradecode = g.gradecode join prpduser u on ug.usercode = u.usercode " + "where ug.userCode=?";
		List<UtiUserGrade> utiUserGradeList = new ArrayList<UtiUserGrade>();
		utiUserGradeList = this.findByHql(hql);
		return utiUserGradeList;
	}

	/**
	 * 获得某个用户所有分配的机构
	 * @param userCode
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<PrpDcompany> findUserGradeCompanyListByUserCode(String userCode) throws Exception {
		String sqlwhere = "exists (Select comcode From utiusergrade Where usercode='" + userCode + "' and comcode=prpdCompany.comcode)";
		// 经分析以上两句SQL的执行计划是一样的，都使用了索引完成。
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(sqlwhere.toString());
		return prpDcompanyService.findPrpDcompany(queryRule);
	}

	/*
	 * 根据客户代码查询客户类别 @param customerCode：客户代码 @return 客户类别 @throws SQLException
	 * @throws Exception
	 */
	@Override
	public String getCustomerType(String customerCode) throws Exception {
		return "";
	}

	/**
	 * 报案号生成规则调整 规则：机构设置除总公司外，其他取省分机构
	 * @param comCode
	 * @return
	 */
	@Override
	public String getRegistComCode(String comCode) {
		String registComCode = "";
		if ("00".equals(comCode.trim())) {
			registComCode = comCode.trim();
		} else {
			String sqlwhere = "COMCODE IN (Select ComCode from prpdCompany Start With ComCode = '" + comCode.trim() + "' Connect By Prior uppercomCode = comCode and prior ComCode != ComCode and validstatus = '1') " + "AND COMLEVEL ='2'";
			try {
				QueryRule queryRule = QueryRule.getInstance();
				queryRule.addSql(sqlwhere.toString());
				List<PrpDcompany> prpDcompanyList = this.prpDcompanyService.findPrpDcompany(queryRule);
				Iterator<PrpDcompany> iterator = prpDcompanyList.iterator();
				while (iterator.hasNext()) {
					PrpDcompany prpDcompany = (PrpDcompany) iterator.next();
					if ("2".equals(prpDcompany.getComLevel())) {
						registComCode = prpDcompany.getComCode();
						break;
					}
				}
				// 因为机构为大项目部等机构时，其comLevel为"5",直接取机构"0000000000"
				if ("".equals(registComCode) || registComCode == null) {
					registComCode = ConstantCodes.MAINCOMPANYCOMCODE;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return registComCode;
	}

	/**
	 * 查询接口:查询某险种,某部门(可选,null为险种配置属性,非null为业务配置属 性),配置代码为configCode的配置项
	 * @param comCode String 部门代码,如果为null,则说明为险种配置属性,如果 为 代码
	 *            值,则说明是业务配置属性.业务配置代码查询时,对部门代码采取上溯 处理,找最近一级部门.
	 * @param riskCode String 险种代码,这个是必要给的
	 * @param configCode String 配置项代码.
	 * @throws Exception 查询异常
	 */
	@Override
	public PrpDriskConfig riskConfigQuery(String comCode, String riskCode, String configCode) throws Exception {
		List<PrpDriskConfig> prpdRiskConfigList = new ArrayList<PrpDriskConfig>();
		if (comCode == null && riskCode == null || configCode == null) {
			throw new Exception("部门代码或险种代码为空");
		}
		// 查询险种配置属性
		String conditions = "1=1";
		if (comCode == null) {
			conditions += SqlUtils.convertString("riskCode", riskCode);
			if (configCode != null) {
				conditions += SqlUtils.convertString("configCode", configCode);
			}
		} else {
			// 查询业务属性
			conditions += SqlUtils.convertString("riskCode", riskCode);
			conditions += SqlUtils.convertString("comCode", comCode);
			if (configCode != null) {
				conditions += SqlUtils.convertString("configCode", configCode);
			}
		}
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions.toString());
		prpdRiskConfigList = this.prpDriskConfigService.findPrpDriskConfig(queryRule);
		Iterator<PrpDriskConfig> it = prpdRiskConfigList.iterator();
		PrpDriskConfig prpdRiskConfig = new PrpDriskConfig();
		if (it.hasNext()) {
			prpdRiskConfig = (PrpDriskConfig) it.next();
		} else {
			// 没有找到任何结果,这里只对业务配置属性做进一步上溯查询,即comcode != null的情况.
			if (comCode != null) {
				PrpDcompany prpDcompany = prpDcompanyService.findPrpDcompany(comCode);
				// 当此comcode找不到配置项时，找上级机构的配置项。
				if (!prpDcompany.getPrpDcompany().getComCode().equals(comCode)) {
					return queryRiskConfig(prpDcompany.getPrpDcompany().getComCode(), riskCode, configCode);
				}
			}
		}
		return prpdRiskConfig;
	}
	/**
	 * 查询支付币别的内容集
	 * @return
	 */
	public Map<String,String> findPayCurrencyMap() {
		return findPayCurrencyMap(true);
	}
	/**
	 * 查询支付币别的内容集
	 * @return
	 */
	public Map<String,String> findPayCurrencyMap(boolean isChinese) {
		String key = cacheManager.generateCacheKey("findPayCurrencyMap",isChinese);
		Map<String,String> payCurrencyMap = (Map<String, String>) cacheManager.getCache(key);
		if(payCurrencyMap!=null && payCurrencyMap.size()>0){
			return payCurrencyMap;
		}
		payCurrencyMap = new LinkedHashMap<String,String>();
		try {
			List<PrpDcurrency> list = prpDcurrencyService.findPayCurrency();
			for(PrpDcurrency prpDcurrency : list){
				if(isChinese){
					payCurrencyMap.put(prpDcurrency.getCurrencyCode(), prpDcurrency.getCurrencyCName());
				}else{
					payCurrencyMap.put(prpDcurrency.getCurrencyCode(), prpDcurrency.getCurrencyEName());
				}
			}
			cacheManager.putCache(key,payCurrencyMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return payCurrencyMap;
	}
	
	/***
	 * 获取指定本位币 本日的汇率
	 * @param exchDate 日期 （ 为空则默认当日 ）
	 * @param baseCurrency 本位币
	 * @return
	 */
	public List<PrpDexch> findBasePrpDexch(Date exchDate, String baseCurrency){
		return this.prpDexchService.findBasePrpDexch(exchDate, baseCurrency);
	}

	/***
	 * 获取指定目标币别 本日的汇率
	 * @param exchDate 日期 （ 为空则默认当日 ）
	 * @param baseCurrency 目标币别
	 * @return
	 */
	public List<PrpDexch> findExchPrpDexch(Date exchDate, String exchCurrency){
		return this.prpDexchService.findExchPrpDexch(exchDate, exchCurrency);
	}

	
	@Override
	public String translateRiskCode(String riskCode, String language) throws Exception {
		return translateRiskCode(riskCode, true);
	}
	
	/**
	 * 根据行业类别，查询出一级行业和二级行业。
	 * @param jobCode
	 * @param riskCode
	 * @return
	 * @throws Exception
	 */
	public PrpDcode[] translateJobCode(String jobCode,String riskCode) throws Exception{
		PrpDcode[] jobCodes = new PrpDcode[3];
		try {
			if (!"".equals(jobCode) && jobCode != null) {
				String strRiskType = this.translateRiskCodetoRiskType(riskCode);
				String conditions = "codecode='" + jobCode + "' and flag='3' and validstatus='1' and codetype='BusinessSource' AND codeEname like '%," + strRiskType + ",%' ";
				List<PrpDcode> prpDcodeList = prpDcodeService.findByConditions(conditions);
				if (prpDcodeList != null && prpDcodeList.size() > 0) {
					jobCodes[2] = prpDcodeList.get(0);
				}else{
					jobCodes[2] = new PrpDcode();
				}
				String jobCode1 = null;
				String jobCode2 = null;
				if ("Z".equals(strRiskType) || "G".equals(strRiskType) ||"Q".equals(strRiskType)) {// 代码共7位
					jobCode1 = jobCode.substring(0, jobCode.length() - 5);//一级行业代码
					jobCode2 = jobCode.substring(0, jobCode.length());// 二级行业代码
				}else if ("Y".equals(strRiskType)||"E".equals(strRiskType)) {// 代码共4位
					jobCode1 = jobCode.length()>=2 ? jobCode.substring(0, 2) : jobCode;// 一级行业代码
					if(CommonUtils.isEmpty(jobCodes[2].getUpperCode())){
						jobCode2 = jobCode.length()>=4 ? jobCode.substring(0, 4) : jobCode;// 二级行业代码
					}else{
						jobCode2 = jobCodes[2].getUpperCode();// 二级行业代码
					}
				}else {
					jobCode1 = jobCode.substring(0, jobCode.length() - 2);// 一级行业代码
					jobCode2 = jobCode.substring(0, jobCode.length()-1);// 二级行业代码
				}
				conditions = "codecode='" + jobCode1 + "' and flag='1' and validstatus='1' and codetype='BusinessSource' AND codeEname like '%," + strRiskType + ",%' ";
				prpDcodeList = prpDcodeService.findByConditions(conditions);
				if (prpDcodeList != null && prpDcodeList.size() > 0) {
					jobCodes[0] = prpDcodeList.get(0);
				}
				conditions = "codecode='" + jobCode2 + "' and flag='2' and validstatus='1' and codetype='BusinessSource' AND codeEname like '%," + strRiskType + ",%' ";
				prpDcodeList = prpDcodeService.findByConditions(conditions);
				if (prpDcodeList != null && prpDcodeList.size() > 0) {
					jobCodes[1] = prpDcodeList.get(0);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		for(int i=0;i<jobCodes.length;i++){
			if(jobCodes[i]==null){
				jobCodes[i] = new PrpDcode();
			}
		}
		return jobCodes;
	}

	/**
	 *  修正承保存值不规范问题
	 * 获取prpCitemKind的itemCode的值
	 * @param prpCitemKind
	 * @return
	 */
	public String getItemCode(PrpCitemKind prpCitemKind) {
		if(prpCitemKind==null){
			return "";
		}
		String itemCode = null;
		try {
			String key = cacheManager.generateCacheKey("getItemCode",prpCitemKind.getRiskCode(),prpCitemKind.getKindCode());
			List<PrpDriskConfig> list = (List<PrpDriskConfig>) cacheManager.getCache(key);
			if(cacheManager.getCache(key)==null){
				list = prpDriskConfigService.findByConditions("configcode = 'ITEMCODEVALUE' and riskCode='"+prpCitemKind.getRiskCode()+"' and configValue='"+prpCitemKind.getKindCode()+"'");
				cacheManager.putCache(key, list);
			}
			if(!CommonUtils.isEmpty(list)&&!CommonUtils.isEmpty(list.get(0).getConfigValueDesc())){
				String configValue = list.get(0).getConfigValueDesc().trim();
				//			使用sql查询
				if(configValue.toLowerCase().startsWith("select ")){
					SQLQuery sqlQuery = super.getSession().createSQLQuery(configValue);
					int index = configValue.indexOf(":");
					String name = null;
					Object value = null;
					while(index>-1){
						int endIndex = configValue.indexOf(" ",index);
						if(endIndex<0){
							endIndex = configValue.indexOf(")",index);
						}
						if(endIndex<0){
							endIndex = configValue.length();
						}
						name = configValue.substring(index+1, endIndex);
						value = PropertyUtils.getProperty(prpCitemKind,name);
						sqlQuery.setParameter(name,value);
						index = configValue.indexOf(":",index+1);
					}
					List valueList = sqlQuery.list();
					if(!CommonUtils.isEmpty(valueList)&&valueList.get(0)!=null){
						itemCode = String.valueOf(valueList.get(0));
					}
				}else{
					//取prpCitemKind的值
					Object obj = PropertyUtils.getProperty(prpCitemKind,configValue);
					if(obj!=null){
						itemCode = String.valueOf(obj);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(itemCode==null){
			itemCode = prpCitemKind.getItemCode();
		}
		return itemCode!=null ? itemCode : "";
	}
	/**
	 * 修正承保存值不规范问题
	 * 获取prpCitemKind的itemName的值
	 * @param prpCitemKind
	 * @return
	 */
	public String getItemName(PrpCitemKind prpCitemKind) throws Exception {
		if(prpCitemKind==null){
			return "";
		}
		String itemName = null;
		try {
			String key = cacheManager.generateCacheKey("getItemName",prpCitemKind.getRiskCode(),prpCitemKind.getKindCode());
			List<PrpDriskConfig> list = (List<PrpDriskConfig>) cacheManager.getCache(key);
			if(cacheManager.getCache(key)==null){
				list = prpDriskConfigService.findByConditions("configcode = 'ITEMNAMEVALUE' and riskCode='"+prpCitemKind.getRiskCode()+"' and configValue='"+prpCitemKind.getKindCode()+"'");
				cacheManager.putCache(key, list);
			}
			if(!CommonUtils.isEmpty(list)&&!CommonUtils.isEmpty(list.get(0).getConfigValueDesc())){
				String configValue = list.get(0).getConfigValueDesc().trim();
				//			使用sql查询
				if(configValue.toLowerCase().startsWith("select ")){
					SQLQuery sqlQuery = super.getSession().createSQLQuery(configValue);
					int index = configValue.indexOf(":");
					String name = null;
					Object value = null;
					List valueList = null;
					while(index>-1){
						int endIndex = configValue.indexOf(" ",index);
						if(endIndex<0){
							endIndex = configValue.indexOf(")",index);
						}
						if(endIndex<0){
							endIndex = configValue.length();
						}
						name = configValue.substring(index+1, endIndex);
						value = PropertyUtils.getProperty(prpCitemKind,name);
						sqlQuery.setParameter(name,value);
						index = configValue.indexOf(":",index+1);
					}
					valueList = sqlQuery.list();
					if(!CommonUtils.isEmpty(valueList)&&valueList.get(0)!=null){
						itemName = String.valueOf(valueList.get(0));
					}
				}else{
					//取prpCitemKind的值
					Object obj = PropertyUtils.getProperty(prpCitemKind,configValue);
					if(obj!=null){
						itemName = String.valueOf(obj);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(itemName==null){
			itemName = CommonUtils.isEmpty(prpCitemKind.getItemName())? prpCitemKind.getItemDetailName():prpCitemKind.getItemName();
		}
		return itemName!=null ? itemName : "";
	}
	
	@Override
	public List<String> getResponKindCode(int type) {
		String key = cacheManager.generateCacheKey("getResponKindCode", type > 0 ? "1" : "0");
		List<String> list = (List<String>) cacheManager.getCache(key);
		if(cacheManager.getCache(key)==null){
			String statement = "";
			if(type > 0 ){//取車體
				statement = "SELECT distinct kindcode FROM fdkindconfig where (accriskcode = 'A901' or accriskcode = 'A902') ";
			} else {//取責任
				statement = "SELECT distinct kindcode FROM fdkindconfig where (accriskcode = 'A903' or accriskcode = 'A904') ";
			}
			list = super.getSession().createSQLQuery(statement).list();
			if(CommonUtils.isEmpty(list)){
				return new ArrayList<String>();
			}
			cacheManager.putCache(key, list);
		}
		return list;
	}

	public UtiCodeTransferService getUtiCodeTransferService() {
		return utiCodeTransferService;
	}

	public void setUtiCodeTransferService(UtiCodeTransferService utiCodeTransferService) {
		this.utiCodeTransferService = utiCodeTransferService;
	}

	public PrpDcurrencyService getPrpDcurrencyService() {
		return prpDcurrencyService;
	}

	public void setPrpDcurrencyService(PrpDcurrencyService prpDcurrencyService) {
		this.prpDcurrencyService = prpDcurrencyService;
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

	public PrpDagentService getPrpDagentService() {
		return prpDagentService;
	}

	public void setPrpDagentService(PrpDagentService prpDagentService) {
		this.prpDagentService = prpDagentService;
	}

	public PrpDriskService getPrpDriskService() {
		return prpDriskService;
	}

	public void setPrpDriskService(PrpDriskService prpDriskService) {
		this.prpDriskService = prpDriskService;
	}

	public PrpDidentifierService getPrpDidentifierService() {
		return prpDidentifierService;
	}

	public void setPrpDidentifierService(PrpDidentifierService prpDidentifierService) {
		this.prpDidentifierService = prpDidentifierService;
	}

	public UtiConfigService getUtiConfigService() {
		return utiConfigService;
	}

	public void setUtiConfigService(UtiConfigService utiConfigService) {
		this.utiConfigService = utiConfigService;
	}

	public PrpDlimitService getPrpDlimitService() {
		return prpDlimitService;
	}

	public void setPrpDlimitService(PrpDlimitService prpDlimitService) {
		this.prpDlimitService = prpDlimitService;
	}

	public PrpDkindService getPrpDkindService() {
		return prpDkindService;
	}

	public void setPrpDkindService(PrpDkindService prpDkindService) {
		this.prpDkindService = prpDkindService;
	}

	public PrpDcodeService getPrpDcodeService() {
		return prpDcodeService;
	}

	public void setPrpDcodeService(PrpDcodeService prpDcodeService) {
		this.prpDcodeService = prpDcodeService;
	}

	public PrpDriskConfigService getPrpDriskConfigService() {
		return prpDriskConfigService;
	}

	public void setPrpDriskConfigService(PrpDriskConfigService prpDriskConfigService) {
		this.prpDriskConfigService = prpDriskConfigService;
	}

	public PrpLthirdPartyService getPrpLthirdPartyService() {
		return prpLthirdPartyService;
	}

	public void setPrpLthirdPartyService(PrpLthirdPartyService prpLthirdPartyService) {
		this.prpLthirdPartyService = prpLthirdPartyService;
	}

	public PrpDexchService getPrpDexchService() {
		return prpDexchService;
	}

	public void setPrpDexchService(PrpDexchService prpDexchService) {
		this.prpDexchService = prpDexchService;
	}

}
