package com.sinosoft.sys.platform.user.service.spring;

import ins.framework.cache.CacheManager;
import ins.framework.cache.CacheService;
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;
import ins.framework.exception.BusinessException;
import ins.framework.rule.RuleService;

import ins.platform.sui.ac.vo.CodeCondition;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sinosoft.app.perf.hr.service.facade.PerfHrService;
import com.sinosoft.sys.platform.company.service.facade.CompanyService;
import com.sinosoft.sys.platform.power.model.SaaCompany;
import com.sinosoft.sys.platform.power.model.SaaUser;
import com.sinosoft.sys.platform.power.service.facade.SaaPowerService;
import com.sinosoft.sys.platform.power.util.IConstants;
import com.sinosoft.sys.platform.user.service.facade.UserService;

public class UserServiceSpringImpl extends GenericDaoHibernate<SaaUser, String> implements UserService {

	private static CacheService cacheManager = CacheManager.getInstance("User");
	private static final Log logger = LogFactory.getLog(UserServiceSpringImpl.class);
	private RuleService ruleService;
	private SaaPowerService saaPowerService;
	private PerfHrService perfHrService;
	private CompanyService companyService;

	public void setSaaPowerService(SaaPowerService saaPowerService) {
		this.saaPowerService = saaPowerService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public void setPerfHrService(PerfHrService perfHrService) {
		this.perfHrService = perfHrService;
	}

	public RuleService getRuleService() {
		return ruleService;
	}

	public void setRuleService(RuleService ruleService) {
		this.ruleService = ruleService;
	}

	public SaaUser getUserByUserCode(String userCode) {
		String key = cacheManager.generateCacheKey(new Object[] { "userCode", userCode });
		Object result = cacheManager.getCache(key);
		if (result != null) {
			return (SaaUser) result;
		} else {
			cacheManager.putCache(key, super.get(userCode));
			return (SaaUser) super.get(userCode);
		}
	}

	public SaaUser getUser(String userCode) {
		logger.debug((new StringBuilder("\u83B7\u53D6\u5DE5\u53F7\u4E3A")).append(userCode).append("\u7684\u5458\u5DE5\u4FE1\u606F").toString());
		return (SaaUser) super.get(userCode);
	}

	public Page findUser(QueryRule queryRule, int pageNo, int pageSize) {
		logger.debug("\u83B7\u53D6\u5458\u5DE5\u4FE1\u606F\u5217\u8868");
		return super.find(queryRule, pageNo, pageSize);
	}

	public void delete(String userCode) {
		logger.debug((new StringBuilder("\u522A\u9664\u5DE5\u53F7\u4E3A")).append(userCode).append("\u7684\u5458\u5DE5\u4FE1\u606F").toString());
		super.deleteByPK(userCode);
	}

	public void save(SaaUser prpDuser) {
		logger.debug("\u4FDD\u5B58\u5458\u5DE5\u4FE1\u606F");
		if (prpDuser.getValidStatus() == null || "".equals(prpDuser.getValidStatus())) {
			prpDuser.setValidStatus("1");
		}
		super.save(prpDuser);
	}

	public void update(SaaUser prpDuser) {
		logger.debug((new StringBuilder("\u66F4\u65B0\u5DE5\u53F7\u4E3A")).append(prpDuser.getUserCode()).append("\u5458\u5DE5\u4FE1\u606F").toString());
		if (prpDuser.getValidStatus() == null || "".equals(prpDuser.getValidStatus())) {
			prpDuser.setValidStatus("1");
		}
		super.update(prpDuser);
		System.out.println("===============update===============");
	}

	public void updateNothing() {
		System.out.println("===============updateNothing===============");
	}

	public SaaUser findUserByUserCode(String userCode) {
		return (SaaUser) super.get(userCode);
	}

	public String getComCodeByUserCode(String userCode) {
		if (userCode == null) {
			return "";
		}
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addLike("userCode", userCode);
		SaaUser user = (SaaUser) findUnique(queryRule);
		if (user == null) {
			throw new BusinessException((new StringBuilder("\u6CA1\u6709\u627E\u5230\u5458\u5DE5")).append(userCode)
					.append("\u7684\u4FE1\u606F\uFF0C\u8BF7\u6838\u5B9E\u540E\u5728\u6B64\u67E5\u8BE2").toString(), false);
		}
		// if (user.getPrpDcompany() == null) {
		// throw new BusinessException((new
		// StringBuilder("\u6CA1\u6709\u627E\u5230\u5458\u5DE5")).append(userCode).append(
		// "\u7684\u673A\u6784\u4FE1\u606F\uFF0C\u8BF7\u6838\u5B9E\u540E\u5728\u6B64\u67E5\u8BE2").toString(),
		// false);
		// } else {
		return user.getUserCode();
		// }
	}

	private SaaUser updateNewUserCode(SaaUser prpDuser) {
		try {
			prpDuser = (SaaUser) ruleService.executeRules("user", prpDuser, "/UserRuleApp/UserRule");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prpDuser;
	}

	public void unvalidUser(String userCode) {
		SaaUser user = (SaaUser) get(userCode);
		user.setValidStatus("0");
		save(user);
	}

	public Page findAllUser(String query, String codeType) {
		StringBuilder hql = new StringBuilder();
		hql.append("select prpDuser.userCode,prpDuser.userName from PrpDuser prpDuser where ");
		hql.append(" prpDuser.userCode like '");
		hql.append(query);
		hql.append("'");
		Page page = null;
		try {
			page = findByHql(hql.toString(), 1, 30, new Object[0]);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return page;
	}

	public List listUserCodeSelect(CodeCondition cond) {
		return null;
	}

	public List listUserCodeSelect(List transitionList) {
		StringBuilder hql = new StringBuilder();
		List<Object> userCodeList = new ArrayList<Object>();
		hql.append("select distinct saaUserGrade.userCode ").append(" from SaaUserGrade saaUserGrade ");
		hql.append("where saaUserGrade.saaGrade.id in (");
		hql.append("select id from SaaGrade saaGrade where ");
		String preName = "";
		for (int i = 0; i < transitionList.size(); i++) {
			hql.append("saaGrade.gradeCName like '%");
			preName = ((String) transitionList.get(i)).substring(0, 2);
			hql.append(preName).append("%'");
			if (i < transitionList.size() - 1) {
				hql.append(" or ");
			}
		}

		hql.append(")");
		userCodeList = super.findByHql(hql.toString(), null);
		return userCodeList;
	}

	/**
	 * @author 中科软 自动完成组件 sui:autocomplete
	 *         查询所有userName、userCode
	 */
	@Override
	public List<Object[]> getUser() {
		List<Object[]> userCodeList = new ArrayList<Object[]>();
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("validStatus", "1");
		System.out.println("hello----");
		queryRule.addSql(" rownum < 15");
		List<SaaUser> prpDuserList = super.find(queryRule);
		System.out.println(prpDuserList.size() + "------------");
		if (prpDuserList != null) {
			Object[] obj = null;
			for (SaaUser prpDuser : prpDuserList) {
				obj = new Object[2];
				obj[0] = prpDuser.getUserCode();
				obj[1] = prpDuser.getUserName();
				userCodeList.add(obj);
			}
			return userCodeList;

		}
		return null;
	}

	/*
	 * linsiming-wb 2011-8-22
	 */
	@Override
	public void synchroPrpDuser(SaaUser prpDuser) {
		SaaUser user = super.get(prpDuser.getUserCode());
		if (user == null) {
			super.save(prpDuser);
		} else {
			prpDuser.setPassword(user.getPassword());
			prpDuser.setPasswdExpireDate(user.getPasswdExpireDate());
			prpDuser.setPasswdSetDate(user.getPasswdSetDate());
			prpDuser.setValidStatus(user.getValidStatus());
			super.getHibernateTemplate().merge(prpDuser);
		}
	}

	public void synReverseSaaUser(SaaUser saaUser) throws Exception {
		String hql = "from HrUser where badge = '" + saaUser.getUserCode() + "'";
		long comDataNum = perfHrService.getCount(hql);
		if (comDataNum == 0) {
			saaUser.setValidStatus("0");
			super.getHibernateTemplate().merge(saaUser);
		}
	}

	public boolean UserExist(String userCode, String password) {
		boolean flag = false;
		SaaUser user = super.get(userCode);
		if (password.equals(user.getPassword())) {
			flag = true;
		}
		return flag;
	}

	@Override
	public Page findUser(String userCode, QueryRule queryRule, int pageNo, int pageSize) {
		if (!userCode.equals("00000000")) {
			String comCodeSql = saaPowerService.addPower(userCode, IConstants.EWPS_SYSTEM_USER_USERMANAGER, "", "comCode", "", "", "");
			queryRule.addSql(comCodeSql);
		}
		Page page = super.find(queryRule, pageNo, pageSize);
		List<SaaUser> userLists = page.getResult();
		for (SaaUser user : userLists) {
			SaaCompany saaCompany = companyService.getPrpDcompanyByComCode(user.getComCode());
			if (saaCompany != null) {
				user.setComCName(saaCompany.getComCName());
			} else {
				user.setComCName("");
			}
		}
		return new Page(0, page.getTotalCount(), pageSize,userLists);
	}

	@Override
	public List<SaaUser> getSaaUser(QueryRule rule) {
		return super.find(SaaUser.class, rule);
	}

	@Override
	public Page findUser(String userCode, String taskCode, QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		if (!userCode.equals("00000000")) {
			String comCodeSql = saaPowerService.addPower(userCode, taskCode, "", "comCode", "", "", "");
			queryRule.addSql(comCodeSql);
		}
		Page page = super.find(queryRule, pageNo, pageSize);
		return page;
	}

}
