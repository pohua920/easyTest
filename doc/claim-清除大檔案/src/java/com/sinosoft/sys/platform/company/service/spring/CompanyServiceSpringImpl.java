package com.sinosoft.sys.platform.company.service.spring;

import ins.framework.cache.CacheManager;
import ins.framework.cache.CacheService;
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;

import cn.com.sinosoft.saa.service.facade.PowerBean;

import com.sinosoft.app.common.CodeConstants;
import com.sinosoft.app.common.model.PerfCode;
import com.sinosoft.app.common.service.facade.PerfCodeService;
import com.sinosoft.app.common.util.HqlRulesUtil;
import com.sinosoft.app.common.util.StringUtil;
import com.sinosoft.app.perf.hr.service.facade.PerfHrService;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.sys.platform.common.TreeNode;
import com.sinosoft.sys.platform.company.service.facade.CompanyService;
import com.sinosoft.sys.platform.power.model.SaaCompany;
import com.sinosoft.sys.platform.power.service.facade.SaaPowerService;
import com.sinosoft.sys.platform.power.service.facade.SaaTaskService;
import com.sinosoft.sys.platform.power.service.facade.SaaUserGradeService;
import com.sinosoft.sys.platform.power.service.facade.SaaUserService;
import com.sinosoft.sys.platform.power.util.IConstants;

public class CompanyServiceSpringImpl extends GenericDaoHibernate<SaaCompany, String> implements CompanyService {
	private PerfHrService perfHrService;
	protected SaaUserService saaUserService;
	private SaaPowerService saaPowerService;
	private SaaTaskService saaTaskService = null;
	private PerfCodeService perfCodeService;
	private SaaUserGradeService saaUserGradeService;
	private static CacheService cacheManager = CacheManager.getInstance("Company");

	public void setPerfHrService(PerfHrService perfHrService) {
		this.perfHrService = perfHrService;
	}

	public void setSaaPowerService(SaaPowerService saaPowerService) {
		this.saaPowerService = saaPowerService;
	}

	public void setPerfCodeService(PerfCodeService perfCodeService) {
		this.perfCodeService = perfCodeService;
	}

	public SaaTaskService getSaaTaskService() {
		return saaTaskService;
	}

	public void setSaaTaskService(SaaTaskService saaTaskService) {
		this.saaTaskService = saaTaskService;
	}

	public void setSaaUserGradeService(SaaUserGradeService saaUserGradeService) {
		this.saaUserGradeService = saaUserGradeService;
	}

	public List<SaaCompany> listMatchesCompany(String comMatches) {

		String matches = comMatches;
		if (matches.indexOf("%") == -1) {
			matches = matches + "%";
		}
		String hql = "from SaaCompany a where a.comCode like ? or a.comCName like ?";
		List<SaaCompany> list = this.findByHql(hql, matches, matches);

		return list;
	}

	public List<SaaCompany> getMatchesCompany(String comMatches) {
		String hql = "from SaaCompany a where a.comCode like ? or a.comCName like ?";
		List<SaaCompany> list = this.findByHql(hql, comMatches, comMatches);

		return list;
	}

	/**
	 * 由险种代码查询出险种名称（addBy raoguangpu）
	 */
	public String findComCNameByComCode(String comCode) {
		SaaCompany prpDcompany;
		QueryRule queryRule = QueryRule.getInstance();// QueryRule类实例
		queryRule.addEqual("comCode", comCode);
		prpDcompany = super.findUnique(queryRule);
		if (prpDcompany != null) {
			return prpDcompany.getComCName();
		} else {
			return null;
		}
	}

	/**
	 * 得到当前归属
	 * 
	 * @param userCode
	 * @return
	 */
	public String getCompanyCode(String userCode) {
		String hql = "select user.prpDcompany.comCode from SaaUser user where user.userCode = ?";
		return (String) this.findByHql(hql, userCode).get(0);
	}

	/**
	 * 得到当前机构的下属机构
	 * 
	 * @param userCode
	 * @return
	 */
	public String getSubCompanyCode(String comCode) {
		StringBuffer buffer = new StringBuffer();
		String hql = "select company.comCode from SaaCompany company where company.upperComCode = ?";
		List<String> list = this.findByHql(hql, comCode);
		for (String str : list) {
			buffer.append(str);
			if (!str.equals(list.get(list.size() - 1))) {
				buffer.append(",");
			}
		}
		return buffer.toString();
	}

	/**
	 * 得到当前机构的下属机构
	 * 
	 * @param comCode
	 * @return add by zoulijuan 20120427
	 */
	@SuppressWarnings("unchecked")
	public List<SaaCompany> getSubCompanyCodeList(String comCode) {
		List<SaaCompany> list = new ArrayList<SaaCompany>();
		String hql = "select company from SaaCompany company where company.upperComCode = ? and (company.comType=? or company.comType=?)";
		list = this.findByHql(hql, comCode, new String("04"), new String("05"));
		String hql1 = "select company from SaaCompany company where company.upperComCode = ? and company.comType='03' and company.virtualFlag = '3'";
		List<SaaCompany> list1 = this.findByHql(hql1, comCode);
		if (list1 != null) {
			list.addAll(list1);
		}
		if (list.size() != 0) {
			return list;
		} else {
			return null;
		}
	}

	/**
	 * 得到当前机构及下属机构
	 * 
	 * @param userCode
	 * @return
	 */
	public String getCompanyCodeAndSubCompanyCode(String userCode) {
		StringBuffer buffer = new StringBuffer();
		String comCode = this.getCompanyCode(userCode);
		String subComCode = this.getSubCompanyCode(comCode);
		buffer.append(comCode);
		if (!subComCode.equals("")) {
			buffer.append(",");
			buffer.append(subComCode);
		}
		return buffer.toString();
	}

	/**
	 * 得到有权限的机构（报表）
	 * 
	 * @param userCode
	 * @return
	 */

	public String getPermitCompany(String userCode, String taskCode) {
		StringBuffer comCodeBuffer = new StringBuffer();
		// 业务权限配置
		List<String> permitCompanyCodeTemp = this.listPermitCompanyCodes(userCode, taskCode);
		for (String str : permitCompanyCodeTemp) {
			comCodeBuffer.append(",'");
			comCodeBuffer.append(str);
			comCodeBuffer.append("'");
		}
		if (comCodeBuffer.length() > 0)
			return "'" + comCodeBuffer.substring(1) + "'";
		else
			return "''''";
	}

	/**
	 * 得到有权限的机构(用'分隔)
	 * 
	 * @param userCode
	 * @return
	 */

	public String getPermitCompanys(String userCode, String taskCode) {
		if (userCode.equals(CodeConstants.TOP_USERCODE)) {
			return "ALL";
		}
		StringBuffer comCodeBuffer = new StringBuffer();
		List<String> permitCompanyCodes = this.listPermitCompanyCodes(userCode, taskCode);

		// 业务权限配置
		// if (permitCompanyCodes.size() > 0) {
		for (String str : permitCompanyCodes) {
			comCodeBuffer.append(",'");
			comCodeBuffer.append(str);
			comCodeBuffer.append("'");
		}
		// } else {
		// throw new BusinessException("没有得到用户[" + userCode + "]执行["
		// + taskCode + "]的相关权限机构，请确认用户业务权限配置正确！", false);
		// }
		if (comCodeBuffer.length() > 0) {
			return comCodeBuffer.substring(1);
		} else
			return "";
	}

	/**
	 * 得到有权限的机构
	 * 
	 * @param userCode
	 * @return
	 */

	public String getPermitCompanyCode(String userCode, String taskCode) {
		if (userCode.equals(CodeConstants.TOP_USERCODE)) {
			return "ALL";
		}
		StringBuffer comCodeBuffer = new StringBuffer();
		List<String> permitCompanyCodes = this.listPermitCompanyCodes(userCode, taskCode);

		// 业务权限配置
		// if (permitCompanyCodes.size() > 0) {
		for (String str : permitCompanyCodes) {
			comCodeBuffer.append(",");
			comCodeBuffer.append(str);
		}
		// } else {
		// throw new BusinessException("没有得到用户[" + userCode + "]执行["
		// + taskCode + "]的相关权限机构，请确认用户业务权限配置正确！", false);
		// }

		if (comCodeBuffer.length() > 0) {
			return comCodeBuffer.substring(1);
		} else
			return "";
	}

	/**
	 * 得到有权限的机构
	 * 
	 * @param userCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SaaCompany> listPermitCompany(String userCode, String taskCode) {
		List<String> permitCompanyCodes = PowerBean.getPowerComList(userCode, taskCode);
		List<SaaCompany> permitPrpDcompanys = new ArrayList<SaaCompany>();

		for (String comCode : permitCompanyCodes) {
			permitPrpDcompanys.add(this.getPrpDcompanyByComCode(comCode));
		}
		return permitPrpDcompanys;
	}

	/**
	 * 得到有权限的机构代码列表
	 * 
	 * @param userCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected List<String> listPermitCompanyCodes(String userCode, String taskCode) {
		// 业务权限配置
		String key = cacheManager.generateCacheKey("listPermitCompanyCodes", userCode, taskCode);
		Object result = cacheManager.getCache(key);
		if (result != null) {
			return (List<String>) result;
		}
		Set<String> permitComSet = new HashSet<String>(0);
		Set<String> exceptComSet = new HashSet<String>(0);

		Long taskId = saaTaskService.getTaskIdByTaskCode(taskCode);
		List<String> permitComCodes = this.findByHql("select userPermitCompany.comCode" + " from SaaUserPermitCompany userPermitCompany"
				+ " where userPermitCompany.saaUserPower.userCode=? and userPermitCompany.saaUserPower.taskId=? " + " and userPermitCompany.saaUserPower.validInd='1'"
				+ " and (userPermitCompany.saaUserPower.flag='0'" + "  or (userPermitCompany.saaUserPower.flag='1'"
				+ "     and current_date>=userPermitCompany.saaUserPower.startTime and current_date<=userPermitCompany.saaUserPower.endTime)" + ")", userCode, taskId);

		List<String> exceptComCodes = this.findByHql("select userExceptCompany.comCode" + " from SaaUserExceptCompany userExceptCompany"
				+ " where userExceptCompany.saaUserPower.userCode=? and userExceptCompany.saaUserPower.taskId=? " + " and userExceptCompany.saaUserPower.validInd='1'"
				+ " and (userExceptCompany.saaUserPower.flag='0'" + "  or (userExceptCompany.saaUserPower.flag='1'"
				+ "     and current_date>=userExceptCompany.saaUserPower.startTime and current_date<=userExceptCompany.saaUserPower.endTime)" + ")", userCode, taskId);
		permitComSet.addAll(permitComCodes);
		exceptComSet.addAll(exceptComCodes);

		StringBuffer permitComStr = new StringBuffer();
		StringBuffer exceptComStr = new StringBuffer();
		for (String comCode : permitComSet) {
			permitComStr.append(",'" + comCode + "'");
		}
		for (String comCode : exceptComSet) {
			exceptComStr.append(",'" + comCode + "'");
		}

		String hql = "select distinct prpDcompanyGrade.id.subComCode" + " from PrpDcompanyGrade prpDcompanyGrade" + " where 1=1";
		if (permitComStr.length() > 0) {
			hql += " and prpDcompanyGrade.id.upperComCode in(" + permitComStr.substring(1) + ")";
		} else {
			hql += " and 1=2";
		}
		if (exceptComStr.length() > 0) {
			hql += " and not exists (select 1 from PrpDcompanyGrade prpDcompanyGrade2" + " where prpDcompanyGrade2.id.subComCode=prpDcompanyGrade.id.subComCode"
					+ " and prpDcompanyGrade2.id.upperComCode in(" + exceptComStr.substring(1) + "))";
		}

		List permitCompanyCodes = super.findByHql(hql);

		cacheManager.putCache(key, permitCompanyCodes);

		return permitCompanyCodes;
	}

	/**
	 * 得到岗位下的机构代码列表
	 * 
	 * @param userCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SaaCompany> listGradePermitCompanyCodes(String userCode, String query) {
		String key = cacheManager.generateCacheKey("listTaskPermitCompanyCodes", userCode, query);
		Object result = cacheManager.getCache(key);
		if (result != null) {
			return (List<SaaCompany>) result;
		}
		StringBuffer buffer = new StringBuffer(500);
		buffer.append("select saaCompany from SaaCompany saaCompany where validStatus='1' ");
		if (query != null && query.length() > 0) {
			buffer.append(" and (comCode like '%" + query + "%' or comCName like '%" + query + "%') ");
		}
		if (!"00000000".equals(userCode)) {
			String comCodeSql = saaPowerService.addPower(userCode, IConstants.EWPS_SYSTEM_SAA_POWER, "", "comCode", "", "", "");
			buffer.append(" and " + comCodeSql);
		}
		buffer.append(" order by comCode");
		List<SaaCompany> permitCompanyCodes = super.findByHql(buffer.toString());
		cacheManager.putCache(key, permitCompanyCodes);
		return permitCompanyCodes;
	}

	/**
	 * 得到岗位下的机构代码列表
	 * 
	 * @param userCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SaaCompany> listGradePermitCompanyCodes(String userCode, String query, String type) {
		String key = cacheManager.generateCacheKey("listTaskPermitCompanyCodes", userCode, query, type);
		Object result = cacheManager.getCache(key);
		if (result != null) {
			return (List<SaaCompany>) result;
		}
		StringBuffer buffer = new StringBuffer(500);
		buffer.append("select saaCompany from SaaCompany saaCompany where validStatus='1' ");
		if (query != null && query.length() > 0) {
			buffer.append(" and (comCode like '%" + query + "%' or comCName like '%" + query + "%') ");
		}
		if (type != null && type.length() > 0) {
			buffer.append(" and comType = '" + type + "'");
		}
		if (!"00000000".equals(userCode)) {
			String comCodeSql = saaPowerService.addPower(userCode, IConstants.EWPS_SYSTEM_SAA_POWER, "", "comCode", "", "", "");
			buffer.append(" and " + comCodeSql);
		}
		buffer.append(" order by comCode");
		List<SaaCompany> permitCompanyCodes = super.findByHql(buffer.toString());
		cacheManager.putCache(key, permitCompanyCodes);
		return permitCompanyCodes;
	}

	public List<SaaCompany> listGradePermitCompanyCodes(String userCode, String query, String type, String gradeId) {
		String key = cacheManager.generateCacheKey("listTaskPermitCompanyCodes", userCode, query, type, gradeId);
		Object result = cacheManager.getCache(key);
		if (result != null) {
			return (List<SaaCompany>) result;
		}

//		QueryRule queryRule = QueryRule.getInstance();
		StringBuffer sql = new StringBuffer("select * from SAA_COMPANY where ");
		sql.append(" (comCode like '%" + query + "%' or comCName like '%" + query + "%') and virtualflag <> '3'");
		if (type != null && type.length() > 0) {
//			queryRule.addEqual("comType", type);
			sql.append(" and comType='"+type+"'");
		} else {
			if ("2".equals(gradeId) || "3".equals(gradeId) || "4".equals(gradeId)) {
//				queryRule.addEqual("comType", "03");
				sql.append(" and comType='03'");
			} else if ("5".equals(gradeId) || "6".equals(gradeId)) {
				sql.append(" and comType in ('04','05')");
//				queryRule.addSql(sql);
			}
		}
		if ("7".equals(gradeId) || "8".equals(gradeId)) {
//			queryRule.addEqual("comLevel", "05");
			sql.append(" and comLevel='05'");
		}
		
		if (!saaUserGradeService.isSuperManager(userCode)) {
			String comCodeSql = saaPowerService.addPower(userCode, IConstants.EWPS_SYSTEM_SAA_POWER, "", "comCode", "", "", "");
			if (comCodeSql.contains("1=1") || userCode.equals("00000000")) {
				List<String> companyLists = new ArrayList<String>();
				QueryRule rule = QueryRule.getInstance();
				rule.addEqual("validStatus", "1");
				rule.addEqual("comType", "03");
				rule.addEqual("upperComCode", "9999999998");
				List<SaaCompany> saaCompanyList = this.getCompany(rule);
				if (saaCompanyList != null) {
					for (SaaCompany itemCompany : saaCompanyList) {
						companyLists.add(itemCompany.getComCode());
					}
				}
				String comCodes = StringUtil.listTostring(companyLists);
				sql.append(" and 1=1 start with comCode in (" + comCodes + ") connect by prior comCode = upperComCode");
			} else {
				sql.append(comCodeSql);
			}
		}
//		queryRule.addAscOrder("comCode");
		sql.append(" order by comCode");
		List<SaaCompany> permitCompanyCodes = (List<SaaCompany>) HibernateUtils.findbySql(super.getSession(), sql.toString(), SaaCompany.class);
		cacheManager.putCache(key, permitCompanyCodes);
		return permitCompanyCodes;
	}

	/**
	 * 根据机构以代码查询出一条对象
	 * 
	 * @param comCode
	 * @return
	 */
	public SaaCompany getPrpDcompanyByComCode(String comCode) {
		SaaCompany prpDcompany = new SaaCompany();
		prpDcompany = this.findUnique("comCode", comCode);
		return prpDcompany;
	}

	/**
	 * 得到当前机构的下属机构
	 * 
	 * @param prpDcompanys
	 * @return
	 */
	public List<SaaCompany> listSubPrpDcompany(List<SaaCompany> prpDcompanys) {
		List<SaaCompany> retPrpDcompanys = new java.util.ArrayList<SaaCompany>();
		String hql = "from SaaCompany company where company.upperComCode = ?";
		for (SaaCompany prpDcompany : prpDcompanys) {
			List<SaaCompany> list = this.findByHql(hql, prpDcompany.getComCode());
			retPrpDcompanys.addAll(list);
		}
		return retPrpDcompanys;
	}

	/**
	 * 省内机构
	 * 
	 * @param userComCode
	 * @param level
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SaaCompany> listLocalCompany(String userComCode, int level) {
		String key = cacheManager.generateCacheKey("listLocalCompany", userComCode, level);
		Object result = cacheManager.getCache(key);
		if (result != null) {
			return (List<SaaCompany>) result;
		}
		List<SaaCompany> retPrpDcompanys = new java.util.ArrayList<SaaCompany>();
		String comCode = userComCode;
		SaaCompany prpDcompany = null;
		while (comCode != null) {
			prpDcompany = this.findUnique(SaaCompany.class, "comCode", comCode);
			if (prpDcompany == null) {
				break;
			}
			if (prpDcompany.getUpperComCode().equals("00000000")) {
				break;
			}
			comCode = prpDcompany.getUpperComCode();
		}

		List<SaaCompany> prpDcompanys = new java.util.ArrayList<SaaCompany>();
		prpDcompanys.add(prpDcompany);

		// 如果是单列市 不需要查找下属市级机构 prpDcompany.getFlag().substring(2, 3).equals("7")
		// 直辖市/计划单列市
		// if(prpDcompany != null && prpDcompany.getFlag().substring(2,
		// 3).equals("7")){

		// retPrpDcompanys.add(prpDcompany);

		// }else{
		for (int i = 0; i < level; i++) {
			prpDcompanys = listSubPrpDcompany(prpDcompanys);
			retPrpDcompanys.addAll(prpDcompanys);
		}
		// }

		cacheManager.putCache(key, retPrpDcompanys);
		return retPrpDcompanys;
	}

	/**
	 * 省间机构
	 * 
	 * @param userComCode
	 * @param level
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SaaCompany> listRemoteCompany(String userComCode, int level) {
		String key = cacheManager.generateCacheKey("listRemoteCompany", userComCode, level);
		Object result = cacheManager.getCache(key);
		if (result != null) {
			return (List<SaaCompany>) result;
		}
		List<SaaCompany> retPrpDcompanys = new java.util.ArrayList<SaaCompany>();
		String comCode = userComCode;
		SaaCompany prpDcompany = null;
		while (comCode != null) {
			prpDcompany = this.findUnique(SaaCompany.class, "comCode", comCode);
			if (prpDcompany == null) {
				break;
			}
			if (prpDcompany.getUpperComCode().equals("00000000")) {
				break;
			}
			comCode = prpDcompany.getUpperComCode();
		}

		List<SaaCompany> prpDcompanys = findByHql("from SaaCompany company where company.upperComCode='00000000' and company.comCode<>?", comCode);
		retPrpDcompanys.addAll(prpDcompanys);

		for (int i = 0; i < level; i++) {
			prpDcompanys = listSubPrpDcompany(prpDcompanys);
			retPrpDcompanys.addAll(prpDcompanys);
		}

		cacheManager.putCache(key, retPrpDcompanys);
		return retPrpDcompanys;
	}

	// 通过查询条件查询功能代码--------
	public Page findByPrpDcompany(SaaCompany prpDcompany, int pageNo, int pageSize) {
		HqlRulesUtil hqlRulesUtil = new HqlRulesUtil();
		hqlRulesUtil.addLike("prpDcompany.comCode", prpDcompany.getComCode());
		hqlRulesUtil.addLike("prpDcompany.upperComCode", prpDcompany.getUpperComCode());
		hqlRulesUtil.addLike("prpDcompany.addressCName", prpDcompany.getAddressCName());
		hqlRulesUtil.addLike("prpDcompany.postCode", prpDcompany.getPostCode());
		hqlRulesUtil.addLike("prpDcompany.faxNumber", prpDcompany.getFaxNumber());
		hqlRulesUtil.addLike("prpDcompany.addressEName", prpDcompany.getAddressEName());
		StringBuffer hql = new StringBuffer();
		hql.append(" from SaaCompany prpDcompany");
		if (hqlRulesUtil.getHql().trim().length() != 0) {
			hql.append(" where  ").append(hqlRulesUtil.getHql());
		}
		logger.debug(hql);
//		Page page = findByHql(hql.toString(), pageNo, pageSize);

		return null;
	}

	// 添加功能代码
	public void addPrpDcompany(SaaCompany prpDcompany) {
		prpDcompany.setValidStatus("1");
		super.save(prpDcompany);
	}

	// 修改功能代码
	public void changePrpDcompany(SaaCompany prpDcompany) {
		super.update(prpDcompany);
	}

	// 通过id删除功能代码
	public void deletePrpDcompany(String ComCode) {
		super.deleteByPK(ComCode);

	}

	/**
	 * 得到一条prpDcompany记录
	 * 
	 * @param map
	 * @return prpDcompany
	 */
	public SaaCompany getPrpDcompany(Map<String, Object> map) {
		return super.findUnique(map);
	}

	/**
	 * 返回市级机构代码，如果没有返回空串。
	 * 
	 * @param comCode
	 *            机构代码
	 * @return 市级机构代码，如果没有返回空串。
	 */
	public String getCityComCode(String comCode) {
		SaaCompany prpDcompany = null;
		if (comCode != null) {
			prpDcompany = super.get(comCode);
		}
		if (prpDcompany == null) {
			return "";
		}
		// Flag第三位 5:省公司,7:直辖市/计划单列市,4:地市公司,3:科室,2:区县,8:网点
		String flag3 = null;
		while (true) {
			flag3 = null;
			if (prpDcompany.getFlag() != null && prpDcompany.getFlag().length() >= 3) {
				flag3 = prpDcompany.getFlag().substring(2, 3);
			}
			if (flag3 != null && (flag3.equals("4") || flag3.equals("7"))) {
				break;
			}

			if (prpDcompany.getComCode().equals(prpDcompany.getUpperComCode())) {
				return "";
			}
			prpDcompany = super.get(prpDcompany.getUpperComCode());
		}
		return prpDcompany.getComCode();
	}

	public boolean getPrpDcompanyByUpperCode(String upperComCode) {

		String hql = "select count(*) from SaaCompany where UpperComCode=?";
		Object[] values = new Object[] { upperComCode };
		List count = super.findByHql(hql, values);
		Iterator iterator = count.iterator();
		if (iterator.hasNext()) {
			if ((Long) iterator.next() > 0) {

				return true;
			}
		} else {

			return false;
		}
		return false;
	}

	/**
	 * 是否同一个市的直接下属机构代码
	 * 
	 * @param comCode
	 *            机构代码（变参）
	 * @return 是同一个市的则返回true，否则返回false
	 */
	public boolean isSameCityComCode(String... comCodes) {
		if (comCodes == null || comCodes.length == 0) {
			return true;
		}
		String cityComCode = getCityComCode(comCodes[0]);
		// 找不到市级机构代码，返回false
		if (cityComCode.equals("")) {
			return false;
		}
		// 如果本身是市级机构则返回false
		if (cityComCode.equals(comCodes[0])) {
			return false;
		}
		for (int i = 1; i < comCodes.length; i++) {
			SaaCompany prpDcompany = super.get(comCodes[i]);
			if (prpDcompany == null) {
				return false;
			}
			if (!prpDcompany.getUpperComCode().equals(cityComCode)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 是否同一个市的直接下属机构代码
	 * 
	 * @param comCode
	 *            机构代码（变参）
	 * @return 是同一个市的则返回true，否则返回false
	 */
	public boolean isSameCityComCode(String comCodes) {
		String[] comCodeArray = comCodes.split(",");
		Map<String, String> map = new HashMap<String, String>(0);
		for (int i = 0; i < comCodeArray.length; i++) {
			String tempComCode = this.getCityComCode(comCodeArray[i].trim());
			if (!map.isEmpty() && !map.containsValue(tempComCode)) {
				return false;
			}
			map.put(comCodeArray[i], tempComCode);
		}

		return true;
	}

	public SaaCompany findPrpDcompanyByComCode(String comCode) {

		SaaCompany prpDcompany;
		QueryRule queryRule = QueryRule.getInstance();// QueryRule类实例
		queryRule.addEqual("comCode", comCode);
		prpDcompany = super.findUnique(queryRule);
		return prpDcompany;
	}

	public void updatePrpDcompany(SaaCompany prpDcompany) {
		super.update(prpDcompany);
	}

	// public Page findPageByPrpDcompany(PrpDcompany prpDcompany, int pageNo,
	// int pageSize) {
	//
	// HqlRulesUtil hqlRulesUtil = new HqlRulesUtil();
	// hqlRulesUtil.addLike("prpDcompany.comCode", prpDcompany.getComCode());
	// hqlRulesUtil.addLike("prpDcompany.upperComCode", prpDcompany
	// .getUpperComCode());
	// hqlRulesUtil.addLike("prpDcompany.addressCName", prpDcompany
	// .getAddressCName());
	// hqlRulesUtil.addLike("prpDcompany.postCode", prpDcompany.getPostCode());
	// hqlRulesUtil.addLike("prpDcompany.faxNumber", prpDcompany
	// .getFaxNumber());
	// hqlRulesUtil.addLike("prpDcompany.addressEName", prpDcompany
	// .getAddressEName());
	// StringBuffer hql = new StringBuffer();
	// hql.append(" from PrpDcompany prpDcompany");
	// if (hqlRulesUtil.getHql().trim().length() != 0) {
	// hql.append(" where  ").append(hqlRulesUtil.getHql());
	// }
	// logger.debug(hql);
	// Page page = findByHql(hql.toString(), pageNo, pageSize);
	// return page;
	// }
	// public Page findPageByPrpDcompany(QueryRule queryRule, int pageNo,
	// int pageSize) {
	// return super.find(queryRule, pageNo, pageSize);
	// }

	/**
	 * 得到当前机构列表的所有下属机构(权限专用接口)
	 * 
	 * @param comCodes
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	private List<String> getSubCompanyCode(List<String> comCodes) {
		List<String> comCodeReturn = new ArrayList<String>(0);
		for (String str : comCodes) {
			comCodeReturn.addAll(this.getSubAllCompanyCode(str));
		}
		return comCodeReturn;
	}

	/**
	 * 得到当前机构列表的所有下属机构
	 * 
	 * @param comCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getSubAllCompanyCode(String comCode) {
		String key = cacheManager.generateCacheKey("subAllCompanyCode", comCode);
		Object result = cacheManager.getCache(key);
		if (result != null) {
			return (List<String>) result;
		}
		List<String> comCodeList = new ArrayList<String>(0);
		// List<String> subComCodeList = this
		// .findByHql(
		// "select company.comCode from PrpDcompany company where
		// company.upperComCode is not company.comCode and
		// company.upperComCode=? ",
		// comCode);
		// for (String str : subComCodeList) {
		// comCodeList.addAll(this.getSubAllCompanyCode(str));
		// }
		// comCodeList.add(comCode);
		//
		TreeNode<String> node = this.getCompanyTree().get(comCode);
		if (node != null) {
			List<TreeNode<String>> children = node.getAllChildren();
			if (children != null && children.size() != 0) {
				for (TreeNode<String> child : children) {
					comCodeList.add(child.getValue());
				}
			}
			comCodeList.add(node.getValue());
		}
		cacheManager.putCache(key, comCodeList);
		return comCodeList;
	}

	public List<String> getSubCompanyCodeList(List<String> comCodes) {
		String key = cacheManager.generateCacheKey("subCompanyCodeList", comCodes);
		Object result = cacheManager.getCache(key);
		if (result != null) {
			return (List<String>) result;
		}
		List<String> comCodeList = this.getSubCompanyCode(comCodes);
		cacheManager.putCache(key, comCodeList);
		return comCodeList;
	}

	/**
	 * 得到当前机构列表的所有下属机构,逗号隔开
	 * 
	 * @param comCode
	 * @return
	 */
	public String getSubCompanyCodeString(String comCode) {
		List<String> comCodeList = this.getSubAllCompanyCode(comCode);
		String comCodes = "";
		for (String str : comCodeList) {
			comCodes = comCodes + "," + str;
		}
		if (comCodes.startsWith(",")) {
			comCodes = comCodes.substring(1, comCodes.length());
		}
		return comCodes;
	}

	/**
	 * 得到当前机构列表的所有下属机构(外部接口)
	 * 
	 * @param comCodes
	 * @return
	 */
	public String getSubCompanyCodes(String comCode) {

		StringBuffer comCodeBuffer = new StringBuffer();
		String comCodeString = "";

		List<String> comCodes = new ArrayList<String>(0);
		comCodes.add(comCode);

		List<String> comCodeList = this.getSubCompanyCode(comCodes);
		for (String str : comCodeList) {
			comCodeBuffer.append(str);
			comCodeBuffer.append(",");
		}
		comCodeString = comCodeBuffer.toString();
		if (!comCodeString.equals("") && comCodeString.endsWith(",")) {
			comCodeString = comCodeString.substring(0, comCodeString.length() - 1);
		}
		return comCodeString;
	}

	/**
	 * 判断当前机构代码是不是市级公司
	 * 
	 * @param comCode
	 * @return
	 */
	private boolean isCityCompany(String comCode) {
		SaaCompany company = this.get(comCode);
		if (company == null) {
			return false;
		}
		String flag3 = "";
		if (company.getFlag() != null && company.getFlag().length() >= 3) {
			flag3 = company.getFlag().substring(2, 3);
		}
		boolean cityFlag = false;
		if (company.getUpperComCode() != null && company.getUpperComCode().equals("00000000") && flag3.equals("7")) {
			cityFlag = true;
		}
		if (flag3 != null && (flag3.equals("4") || cityFlag)) {
			return true;
		}
		return false;
	}

	/**
	 * 递归得到下级的市级公司放入map
	 * 
	 * @param comCode
	 * @return
	 */
	private List<String> subCityCompanyCode(String comCode) {
		List<String> subCityComCodeList = new ArrayList<String>(0);
		List<String> comCodes = this.getSubAllCompanyCode(comCode);
		for (String str : comCodes) {
			if (this.isCityCompany(str)) {
				subCityComCodeList.add(str);
			}
		}
		return subCityComCodeList;
	}

	/**
	 * 得到下级的市级公司
	 * 
	 * @param comCode
	 * @return
	 */
	public String getSubCityCompanyCodes(String comCode) {
		if (this.isCityCompany(comCode)) {
			return comCode;
		}

		StringBuffer comCodeBuffer = new StringBuffer();
		List<String> comCodeList = new ArrayList<String>(0);
		String comCodeString = "";

		comCodeList.addAll(this.subCityCompanyCode(comCode));
		for (String str : comCodeList) {
			comCodeBuffer.append(str);
			comCodeBuffer.append(",");
		}
		comCodeString = comCodeBuffer.toString();
		if (!comCodeString.equals("") && comCodeString.endsWith(",")) {
			comCodeString = comCodeString.substring(0, comCodeString.length() - 1);
		}
		return comCodeString;
	}

	/**
	 * 得到有权的市级公司
	 * 
	 * @param userCode
	 * @param taskCode
	 * @return
	 */
	public List<String> getPermitCityCompanyCodes(String userCode, String taskCode) {
		List<String> cityComCode = new ArrayList<String>(0);
		String[] comCodes = null;
		String comCode = this.getPermitCompanyCode(userCode, taskCode);
		if (comCode.equals("ALL")) {
			comCode = this.getSubCityCompanyCodes(this.getProvinceCode(this.getCompanyCode(userCode)));
		}
		if (comCode.indexOf(",") > -1) {
			comCodes = comCode.split(",");
		} else {
			comCodes = new String[] { comCode };
		}
		for (String str : comCodes) {
			if (isCityCompany(str)) {
				cityComCode.add(str);
			}
		}
		return cityComCode;
	}

	/**
	 * 根据当前机构获取省级机构代码
	 * 
	 * @param comCode
	 * @return
	 */
	public String getProvinceCode(String comCode) {
		String provinceCode = "";
		while (provinceCode.equals("")) {
			List<SaaCompany> prpDcompanys = super.findByHql("from SaaCompany prpDcompany where comCode =?", comCode);
			if (prpDcompanys == null || prpDcompanys.size() == 0) {
				break;
			} else {
				for (int i = 0; i < prpDcompanys.size(); i++) {
					SaaCompany prpDcompany = prpDcompanys.get(i);
					if (prpDcompany.getUpperComCode().equals("00000000")) {
						provinceCode = prpDcompany.getComCode();
						break;
					}
					comCode = prpDcompany.getUpperComCode();
				}
			}
		}

		return provinceCode;
	}

	/**
	 * 判断是否是同一个省中的地市，用於省间通赔
	 */
	private String getProvinceComCode(String comCode) {
		SaaCompany prpDcompany = super.get(comCode);
		if (prpDcompany == null) {
			return "";
		}
		// Flag第三位 5:省公司,7:直辖市/计划单列市,4:地市公司,3:科室,2:区县,8:网点
		String flag3 = null;
		while (true) {
			flag3 = null;
			if (prpDcompany.getFlag() != null && prpDcompany.getFlag().length() >= 3) {
				flag3 = prpDcompany.getFlag().substring(2, 3);
			}
			if (flag3 != null && flag3.equals("5")) {
				break;
			}

			if (prpDcompany.getComCode().equals(prpDcompany.getUpperComCode())) {
				return "";
			}
			if (prpDcompany.getUpperComCode() == null) {
				return comCode;
			} else {
				prpDcompany = super.get(prpDcompany.getUpperComCode());
			}
		}
		return prpDcompany.getComCode();
	}

	/**
	 * 是否是同一个省下面的地市
	 */
	public boolean isSameProvinceComCode(String comCodes) {
		String[] comCodeArray = comCodes.split(",");
		Map<String, String> map = new HashMap<String, String>(0);
		for (int i = 0; i < comCodeArray.length; i++) {
			String tempComCode = this.getProvinceComCode(comCodeArray[i].trim());
			if (!map.isEmpty() && !map.containsValue(tempComCode)) {
				return false;
			}
			map.put(comCodeArray[i], tempComCode);
		}

		return true;
	}

	/**
	 * 省间通赔省内机构查询
	 * 
	 * @param matches
	 * @return
	 */
	public Page listRemoteCompany(String matches) {
		String key = cacheManager.generateCacheKey("listRemoteCompany", matches);
		Object result = cacheManager.getCache(key);
		if (result != null) {
			return (Page) result;
		}
		// 得到当前匹配的省公司代码
		String comCode = "";
		String hql = "select company.comCode,company.flag,company.comCName from SaaCompany company where company.validStatus='1' and  company.upperComCode = '00000000' and ( company.comCode like ? or company.comCName like ? ) order by company.comCode";
		List<Object[]> comCodes = this.findByHql(hql, matches, matches);
		// 针对单列市
		if (comCodes.size() > 0) {
			if (comCodes.get(0) != null && comCodes.get(0)[1].toString().length() > 3 && comCodes.get(0)[1].toString().substring(2, 3).equals("7")) {
				Object[] comCodes1 = { comCodes.get(0)[0].toString(), comCodes.get(0)[2].toString() };
				List<Object[]> cityComCodes = new ArrayList<Object[]>(0);
				cityComCodes.add(comCodes1);
				Page page = new Page(0, cityComCodes.size(), cityComCodes.size(), cityComCodes);
				return page;
			}
			comCode = comCodes.get(0)[0].toString();
		}
		// 得到当前省公司的所有下级机构代码
		String subComCodeHql = "select company.comCode,company.comCName from SaaCompany company where company.upperComCode = ?";
		List<Object[]> subComCodes = this.findByHql(subComCodeHql, comCode);

		// 得到下级的所有市级公司
		List<Object[]> cityComCodes = new ArrayList<Object[]>(0);
		for (Object[] subComCode : subComCodes) {
			if (this.isCityCompany((String) subComCode[0])) {
				cityComCodes.add(subComCode);
			}
		}
		Page page = new Page(0, cityComCodes.size(), cityComCodes.size(), cityComCodes);
		return page;
	}

	/**
	 * 省间通赔省级机构查询
	 * 
	 * @param matches
	 * @return
	 */
	public Page listProvinceCompany(String matches, String userComCode) {
		String key = cacheManager.generateCacheKey("listRemoteCompany", matches);
		Object result = cacheManager.getCache(key);
		if (result != null) {
			return (Page) result;
		}
		String comCodeProvince = getProvinceCode(userComCode);

		// 得到当前匹配的省公司代码
		String hql = "select company.comCode,company.comCName  from SaaCompany company where company.upperComCode='00000000' and( company.comCName like ? or company.comCode like ? ) and company.comCode <> ?";
		List<Object[]> provinceCodes = this.findByHql(hql, matches, matches, comCodeProvince);
		Page page = new Page(0, provinceCodes.size(), provinceCodes.size(), provinceCodes);
		return page;
	}

	/**
	 * 得到当前机构列表的所有下属机构
	 * 
	 * @param comCode
	 * @return
	 */
	public Page getAllSubComCode(String matches, String comCode) {
		String hql = "select company.comCode,company.comCName from SaaCompany company where company.upperComCode is not company.comCode and company.upperComCode=? or company.comCode=?";
		List<Object[]> companys = this.findByHql(hql, comCode, comCode);
		Page page = new Page(0, companys.size(), companys.size(), companys);
		return page;
	}

	/**
	 * 得到当前机构的分公司机构
	 * 
	 * @param comCode
	 * @return
	 */
	public String getBranchComCode(String comCode) {
		StringBuffer strBuffer1 = new StringBuffer();
		strBuffer1.append("select comcode from prpscompany where validstatus = '1' AND comlevel = 1");
		strBuffer1.append(" start with comcode = '" + comCode + "'");
		strBuffer1.append(" Connect By Prior upperComCode = ComCode");
		strBuffer1.append(" AND Prior comCode != comCode");
		SQLQuery query = (SQLQuery) super.getSession().createSQLQuery(strBuffer1.toString());
		List<Object> list = query.list();
		return list.get(0).toString();
	}

	/**
	 * 判断upperComCode 是否是comCode的父节点
	 * 
	 * @param comCode
	 * @param upperComCode
	 * @return
	 */
	public boolean isUpperComCode(String comCode, String upperComCode) {
		if (comCode == null || upperComCode == null || comCode.equals(upperComCode)) {
			return false;
		}
		Map<String, TreeNode<String>> companyTree = getCompanyTree();
		TreeNode<String> node = companyTree.get(comCode);
		TreeNode<String> parent = companyTree.get(upperComCode);

		if (node == null || parent == null) {
			return false;
		}
		if (node.getParent() == parent.getParent()) { // brothers
			return false;
		}
		while (node != null) {
			if (node.getParent() == parent) {
				return true;
			}
			node = node.getParent();
		}
		return false;
	}

	/**
	 * 得到当前机构的所有上级机构列表
	 * 
	 * @param comCode
	 * @return
	 */
	public List<String> getAllUpperCompanyCode(String comCode) {
		List<String> list = new ArrayList<String>();
		Map<String, TreeNode<String>> companyTree = getCompanyTree();
		TreeNode<String> node = companyTree.get(comCode);
		if (node != null) {
			List<TreeNode<String>> parents = node.getAllParent();
			for (TreeNode<String> parent : parents) {
				list.add(parent.getValue());
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	private Map<String, TreeNode<String>> initCompanyTreeCache() {
		Map<String, TreeNode<String>> companyTree = new HashMap<String, TreeNode<String>>();
		List<Object[]> list = super.findByHql("select com.comCode, com.upperComCode from SaaCompany com where com.validStatus='1'");
		for (Object[] str : list) {
			TreeNode<String> node = new TreeNode<String>((String) str[0]);
			companyTree.put(node.getValue(), node);
		}
		for (Object[] str : list) {
			TreeNode<String> node = companyTree.get((String) str[0]);
			TreeNode<String> parent = companyTree.get((String) str[1]);
			if (parent != null && parent != node) {
				node.setParent(parent);
				parent.addChild(node);
			}
		}
		cacheManager.putCache("CompanyTree", companyTree);
		return companyTree;
	}

	@SuppressWarnings("unchecked")
	private Map<String, TreeNode<String>> getCompanyTree() {
		Map<String, TreeNode<String>> treeNodeMap = (Map<String, TreeNode<String>>) cacheManager.getCache("CompanyTree");
		if (treeNodeMap == null) {
			treeNodeMap = initCompanyTreeCache();
		}
		return treeNodeMap;
	}

	/**
	 * 得到当前机构的所有上级机构列表
	 * 
	 * @param comCode
	 * @return
	 */
	public String getUpperCompanyCode(String comCode) {
		List<String> comCodeList = new ArrayList<String>();
		comCodeList = getAllUpperCompanyCode(comCode);
		StringBuffer comCodeBuffer = new StringBuffer();
		for (String str : comCodeList) {
			comCodeBuffer.append(str);
			comCodeBuffer.append(",");
		}
		comCodeBuffer.delete(comCodeBuffer.length() - 1, comCodeBuffer.length());

		return comCodeBuffer.toString();
	}

	public SaaUserService getSaaUserService() {
		return saaUserService;
	}

	public void setSaaUserService(SaaUserService saaUserService) {
		this.saaUserService = saaUserService;
	}

	/**
	 * 得到当前机构的下属机构但不包括本身的串
	 * 
	 * @param comCode
	 * @return
	 */
	public String getSubCompanyCodeStr(String comCode) {
		StringBuffer buffer = new StringBuffer();
		String hql = "select company.comCode from SaaCompany company where company.upperComCode = ? and company.comCode<>?";
		List<String> list = this.findByHql(hql, comCode, comCode);
		for (String str : list) {
			buffer.append(str);
			if (!str.equals(list.get(list.size() - 1))) {
				buffer.append(",");
			}
		}
		return buffer.toString();
	}

	/**
	 * 得到当前机构名称
	 * 
	 * @param comCname
	 * @return
	 */
	@Override
	public String getComCname(String comCode) {
		// String hql = "select comCName from PrpDcompany where comCode = ?";
		String hql = "select comCName from 	SaaCompany where comCode = ?";

		List list = this.findByHql(hql, comCode);
		if (list.size() > 0) {
			return list.get(0).toString();
		}
		return null;
	}

	/**
	 * 通过状态获得公司
	 * 
	 * @author 中科软
	 */
	public List<SaaCompany> getComByValidstatus(String Validstatus) {
		// String hql =
		// "select comCName,comCode from PrpDcompany where validstatus = ?";
		QueryRule rule = QueryRule.getInstance();
		rule.addEqual("validStatus", Validstatus);
		return this.find(rule);
	}

	public List<SaaCompany> getCompany(QueryRule rule) {
		return super.find(SaaCompany.class, rule);
	}

	public Page findCompany(QueryRule queryRule, int pageNo, int pageSize) {
		return super.find(queryRule, pageNo, pageSize);
	}

	/**
	 * 类似於saveorupdate方法 管理功能同步
	 * 
	 * @author 中科软
	 * 
	 */
	public void synchroPrpDcompany(SaaCompany prpDcompany) {
		SaaCompany comData = super.get(prpDcompany.getComCode());
		if (comData == null) {// 新增
			super.save(prpDcompany);
		} else {// 修改
			prpDcompany.setCreateCode(comData.getCreateCode());
			prpDcompany.setCreateTime(comData.getCreateTime());
			prpDcompany.setValidStatus(comData.getValidStatus());
			super.getHibernateTemplate().merge(prpDcompany);
		}
	}

	/**
	 * 管理功能的逆向同步
	 * 
	 * @author 中科软
	 * 
	 */
	public void synReverseSaaCompany(SaaCompany saaCompany) {
		String hql = "";
		if ("01".equals(saaCompany.getComType())) {
			hql = "from HrCompany where CompId = '" + saaCompany.getComCode() + "'";
		} else {
			hql = "from HrDepartment where depId = '" + saaCompany.getComCode() + "'";
		}
		if (!"".equals(hql)) {
			long comDataNum = perfHrService.getCount(hql);
			if (comDataNum == 0) {
				saaCompany.setValidStatus("0");
				super.getHibernateTemplate().merge(saaCompany);
			}
		}
	}

	public Page findCompanyByRule(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		Page page = super.find(queryRule, pageNo, pageSize);
		List<SaaCompany> companyLists = page.getResult();
		List<SaaCompany> saaCompanyLists = new ArrayList<SaaCompany>();
		for (SaaCompany itemCompany : companyLists) {
			SaaCompany saaCompany = new SaaCompany();
			saaCompany.setComCode(itemCompany.getComCode());
			saaCompany.setComCName(itemCompany.getComCName());
			saaCompany.setComLevel(itemCompany.getComLevel());
			saaCompany.setComType(itemCompany.getComType());
			saaCompany.setUpperComCode(itemCompany.getUpperComCode());
			saaCompany.setValidStatus(itemCompany.getValidStatus());

			PerfCode perfCodeT = perfCodeService.findPerfCodeById("ComType", itemCompany.getComType());
			if (null != perfCodeT) {
				saaCompany.setComTypeName(perfCodeT.getCodeCName());
			}
			PerfCode perfCodeL = perfCodeService.findPerfCodeById("ComLevel", itemCompany.getComLevel());
			if (null != perfCodeL) {
				saaCompany.setComLevelName(perfCodeL.getCodeCName());
			}

			saaCompanyLists.add(saaCompany);
			super.evict(saaCompany);
		}
		return new Page(0, page.getTotalCount(), pageSize, saaCompanyLists);
	}

	@Override
	public Page findCompanyByRule(String userCode, String taskCode, QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		if (!userCode.equals("00000000")) {
			String comCodeSql = saaPowerService.addPower(userCode, taskCode, "", "comCode", "", "", "");
			queryRule.addSql(comCodeSql);
		}
		queryRule.addAscOrder("comCode");
		Page page = super.find(queryRule, pageNo, pageSize);
		List<SaaCompany> companyLists = page.getResult();
		List<SaaCompany> saaCompanyLists = new ArrayList<SaaCompany>();
		for (SaaCompany itemCompany : companyLists) {
			SaaCompany saaCompany = new SaaCompany();
			saaCompany.setComCode(itemCompany.getComCode());
			saaCompany.setComCName(itemCompany.getComCName());
			saaCompany.setComLevel(itemCompany.getComLevel());
			saaCompany.setComType(itemCompany.getComType());
			saaCompany.setUpperComCode(itemCompany.getUpperComCode());
			saaCompany.setValidStatus(itemCompany.getValidStatus());

			PerfCode perfCodeT = perfCodeService.findPerfCodeById("ComType", itemCompany.getComType());
			if (null != perfCodeT) {
				saaCompany.setComTypeName(perfCodeT.getCodeCName());
			}
			PerfCode perfCodeL = perfCodeService.findPerfCodeById("ComLevel", itemCompany.getComLevel());
			if (null != perfCodeL) {
				saaCompany.setComLevelName(perfCodeL.getCodeCName());
			}
			
			SaaCompany upperCompany = this.getPrpDcompanyByComCode(itemCompany.getUpperComCode());
			if(upperCompany!=null){
				saaCompany.setUpperComName(upperCompany.getComCName());
			}else{
				saaCompany.setUpperComName("");
			}
			
			saaCompanyLists.add(saaCompany);
			super.evict(saaCompany);
		}
		return new Page(0, page.getTotalCount(), pageSize, saaCompanyLists);
	}

	@Override
	public String getSubCompanyCodeByComCode(String comCode) {
		String comCodes = "";
		List<String> comCodeList = new ArrayList<String>(0);
		SaaCompany saaCompany = this.findPrpDcompanyByComCode(comCode);
		if ("3".equals(saaCompany.getVirtualFlag())) {
			comCodeList = this.getSubAllCompanyCode(saaCompany.getUpperComCode());
		} else {
			comCodeList = this.getSubAllCompanyCode(comCode);
		}
		for (String str : comCodeList) {
			comCodes = comCodes + ",'" + str + "'";
		}
		return comCodes;
	}

	public List<SaaCompany> getSubDepartCodeByComCode(String comCode) {
		List<String> companyLists = new ArrayList<String>();
		QueryRule rule = QueryRule.getInstance();
		rule.addEqual("validStatus", "1");
		//rule.addEqual("comType", "03");
		rule.addNotEqual("comType", "01");
		rule.addEqual("upperComCode", comCode);
		List<SaaCompany> saaCompanyList = this.getCompany(rule);
		if (saaCompanyList != null) {
			for (SaaCompany itemCompany : saaCompanyList) {
				companyLists.add(itemCompany.getComCode());
			}
		}
		String comCodes = StringUtil.listTostring(companyLists);
		rule = QueryRule.getInstance();
		rule.addEqual("validStatus", "1");
		rule.addEqual("comType", "03");
		rule.addAscOrder("comCode");
		if (comCodes != null && !"".equals(comCodes)) {
			rule.addSql("virtualflag <> '3' and 1=1 start with comcode in (" + comCodes + ") connect by prior comCode = upperComCode");
		}
		return this.getCompany(rule);
	}

	@Override
	public List<SaaCompany> findSubCompany(String comCode, String comLevel) {
		List<SaaCompany> saaCompanies = new ArrayList<SaaCompany>();
		String hql = "select company from SaaCompany company where company.upperComCode =? and company.comLevel=?";
		saaCompanies = super.findByHql(hql, comCode, comLevel);
		return saaCompanies;
	}

	public Page transferListToPage(List<SaaCompany> saaCompanyList, int pageNo, int pageSize) throws Exception {
		List<SaaCompany> saaCompanyLists = new ArrayList<SaaCompany>();
		for (SaaCompany itemCompany : saaCompanyList) {
			SaaCompany saaCompany = new SaaCompany();
			saaCompany.setComCode(itemCompany.getComCode());
			saaCompany.setComCName(itemCompany.getComCName());
			saaCompany.setComLevel(itemCompany.getComLevel());
			saaCompany.setComType(itemCompany.getComType());
			saaCompany.setUpperComCode(itemCompany.getUpperComCode());
			saaCompany.setValidStatus(itemCompany.getValidStatus());
			PerfCode perfCodeT = perfCodeService.findPerfCodeById("ComType", itemCompany.getComType());
			if (null != perfCodeT) {
				saaCompany.setComTypeName(perfCodeT.getCodeCName());
			}
			PerfCode perfCodeL = perfCodeService.findPerfCodeById("ComLevel", itemCompany.getComLevel());
			if (null != perfCodeL) {
				saaCompany.setComLevelName(perfCodeL.getCodeCName());
			}
			saaCompanyLists.add(saaCompany);
			super.evict(saaCompany);
		}
		return new Page(0, saaCompanyList.size(), pageSize, saaCompanyLists);
	}

	public List<String> findComCodeByVirtual(String upperComCode) throws Exception {
		List<String> comCodeLists = new ArrayList<String>(0);
		String hql = "from SaaCompany where upperComCode = ? and virtualFlag = ?";
		List<SaaCompany> saaCompanyLists = super.findByHql(hql, upperComCode, "3");
		for (SaaCompany itemCompany : saaCompanyLists) {// 虚拟机构 机构代码是人员代码
			List<SaaCompany> saaCompanyList = saaUserGradeService.findSaaPermitCompanyList(itemCompany.getComCode(), "5");
			for (SaaCompany itemPower : saaCompanyList) {
				comCodeLists.add(itemPower.getComCode());
			}
		}
		return comCodeLists;

	}

	public boolean isExistComCode(String comCode) throws Exception {
		boolean isExist = false;
		SaaCompany saacompanyTemp = this.findPrpDcompanyByComCode(comCode);
		List<String> comCodeLists = this.findComCodeByVirtual(saacompanyTemp.getUpperComCode());
		for (String itemCode : comCodeLists) {
			if (comCode.equals(itemCode)) {
				isExist = true;
				break;
			}
		}
		return isExist;
	}

	public void insertVirtualCompany(SaaCompany saaCompany) {
		super.save(saaCompany);
	}

	public void updateVirtualCompany(SaaCompany saaCompany) {
		super.update(saaCompany);
	}

	@Override
	public int getCompanyAmount(String comCode, int i) {
		String hql="from SaaCompany where upperComCode=? and comLevel=?";
		return (int)super.getCount(hql, comCode,i*10+"");
	}

	@Override
	public List<SaaCompany> findNotInCompany(Set<String> comCodeSet) {
		Criteria crit = super.getSession().createCriteria(new SaaCompany().getClass());
		crit.add(Restrictions.not(Restrictions.in("comCode", comCodeSet)));
		crit.add(Restrictions.eq("comLevel", "05"));
		return crit.list();
	}
}
