package com.sinosoft.sys.platform.power.service.spring;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.sinosoft.app.common.util.HqlRulesUtil;
import com.sinosoft.sys.platform.company.service.facade.CompanyService;
import com.sinosoft.sys.platform.power.model.SaaCompany;
import com.sinosoft.sys.platform.power.model.SaaUser;
import com.sinosoft.sys.platform.power.service.facade.SaaPowerHelpService;
import com.sinosoft.sys.platform.power.service.facade.SaaPowerService;
import com.sinosoft.sys.platform.power.service.facade.SaaUserService;
import com.sinosoft.sys.platform.power.util.IConstants;

public class SaaUserServiceSpringImpl extends GenericDaoHibernate<SaaUser, String> implements SaaUserService {
	private SaaPowerHelpService saaPowerHelpService;
	private SaaPowerService saaPowerService;
	private CompanyService companyService;

	/*
	 * linsiming-wb 2011-8-15
	 */
	@Override
	public Page queryUserTranslateCode(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		Page page = super.find(queryRule, pageNo, pageSize);
		return page;
	}

	public void queryUserJSP(String userCode, String comCode, String saaGradeCode, String userCodeOperate) {
		HttpServletResponse response = ServletActionContext.getResponse();
		String userCodesSql = "select saaUserGrade.userCode from SaaUserGrade saaUserGrade where saaUserGrade.saaGrade.id=" + Long.parseLong(saaGradeCode);
		List<String> userCodesByGrade = super.findByHql(userCodesSql);
		if (userCodesByGrade.size() > 1) {
			String userCodes = saaPowerHelpService.removeDuplicateWithOrder(userCodesByGrade);

			String competence = saaPowerService.addPower(userCodeOperate, IConstants.SAA_USERPOWER_POWERFULLCOPY, "", "saaUser.comCode", "", "", "");
			response.setContentType("text/xml;charset=utf-8");
			response.setHeader("Cache-Control", "no-cache");
			String hql = "select saaUser from SaaUser saaUser " + "where 1=1 and " + competence + " and " + "saaUser.userCode in(" + userCodes + ")" + " and saaUser.userCode<>'"
					+ userCode + "'";
			List<SaaUser> saaUserList = super.findByHql(hql);
			String xml_start = "<selects>";
			String xml_end = "</selects>";
			String xml = "";
			for (SaaUser saaUser : saaUserList) {
				xml += "<select><value>" + saaUser.getUserCode() + "</value><text>" + saaUser.getUserCode() + "-" + saaUser.getUserName() + "</text></select>";
			}
			String last_xml = xml_start + xml + xml_end;
			try {
				response.getWriter().write(last_xml);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public List<SaaUser> findSaaUserSameComList(String userCode, String userCodeOperate) {
		String competence = saaPowerService.addPower(userCodeOperate, IConstants.SAA, "", "saaUser.comCode", "", "", "");
		String hql = "select saaUser from SaaUser saaUser where " + "saaUser.userCode <> ? and saaUser.userCode <> '0000000000' "
				+ "and saaUser.comCode=(select user.comCode from SaaUser user where user.userCode=?) " + "and " + competence;
		return super.findByHql(hql, userCode, userCode);
	}

	public void queryUserJSPByUserCode(String userCode, String userCodeOperate) {
		List<SaaUser> saaUserList = new ArrayList<SaaUser>(0);
		saaUserList = this.findSaaUserSameComList(userCode, userCodeOperate);
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/xml;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		String xml_start = "<selects>";
		String xml_end = "</selects>";
		String xml = "";
		for (SaaUser saaUser : saaUserList) {
			xml += "<select><value>" + saaUser.getUserCode() + "</value><text>" + saaUser.getUserCode() + "-" + saaUser.getUserName() + "</text></select>";
		}
		String last_xml = xml_start + xml + xml_end;
		try {
			response.getWriter().write(last_xml);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public SaaUser findSaaUserByUserCode(String userCode, String operUserCode) {
		List<SaaUser> saaUserList = new ArrayList<SaaUser>(0);
		if (userCode != null) {
			String hql = "select saaUser from SaaUser saaUser where saaUser.userCode=?" + " and "
					+ saaPowerService.addPower(operUserCode, IConstants.EWPS_SYSTEM_SAA_GRADE, "", "saaUser.comCode", "", "", "");
			saaUserList = super.findByHql(hql, userCode);
		}
		if (saaUserList.size() != 0) {
			return saaUserList.get(0);
		} else {
			return null;
		}

	}

	public SaaUser findSaaUserByUserCode(String userCode) {
		List<SaaUser> saaUserList = new ArrayList<SaaUser>(0);
		if (userCode != null) {
			String hql = "select saaUser from SaaUser saaUser where saaUser.userCode=?";
			saaUserList = super.findByHql(hql, userCode);
		}
		if (saaUserList.size() != 0) {
			return saaUserList.get(0);
		} else {
			return null;
		}

	}

	public Page getUserList(SaaUser saaUser, int pageNo, int pageSize, String userCodeOperate) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from SaaUser saaUser where 1=1 and saaUser.userCode <> '00000000'");
		if (!userCodeOperate.equals("00000000")) {
			String comCodeSql = saaPowerService.addPower(userCodeOperate, IConstants.SAA, "", "saaUser.comCode", "", "", "");
			hql.append(" and saaUser.userCode<>'" + userCodeOperate + "'");
			hql.append(" and " + comCodeSql);
		}
		String comCName = saaUser.getComCName();
		if (comCName != null && !"".equals(comCName)) {
			String comCodeStr = "";
			QueryRule rule = QueryRule.getInstance();
			rule.addLike("comCName", comCName);
			List<SaaCompany> companyList = companyService.getCompany(rule);
			for (SaaCompany itemCompany : companyList) {
				comCodeStr += ",'" + itemCompany.getComCode() + "'";
			}
			hql.append(" and saaUser.comCode in (" + comCodeStr.substring(1) + ")");
		}
		hql.append(" and saaUser.validStatus='1'");
		HqlRulesUtil hqlRulesUtil = new HqlRulesUtil();
		hqlRulesUtil.addLike("saaUser.userCode", saaUser.getUserCode());
		hqlRulesUtil.addLike("saaUser.comCode", saaUser.getComCode());
		hqlRulesUtil.addLike("saaUser.userName", saaUser.getUserName());

		if (hqlRulesUtil.getHql().trim().length() != 0) {
			hql.append(" and ").append(hqlRulesUtil.getHql());
		}
		hql.append(" order by comCode Asc,userCode Asc");
		Page page = super.findByHqlNoLimit(hql.toString(), pageNo, pageSize);
		List<SaaUser> saaUserLists = page.getResult();
		for (SaaUser user : saaUserLists) {
			SaaCompany saaCompany = companyService.getPrpDcompanyByComCode(user.getComCode());
			if(saaCompany!=null){
				user.setComCName(saaCompany.getComCName());
			}else{
				user.setComCName("");
			}
			
		}
		return new Page(0, page.getTotalCount(), pageSize,saaUserLists);
//		return page;
	}

	public Page getAgengUserList(SaaUser saaUser, int pageNo, int pageSize, String userCodeOperate) {

		String agentComHql = "select company.comCode from SaaCompany company where 1=1 and company.agentInsCom is not null";
		if (!userCodeOperate.equals("00000000")) {
			agentComHql += " and  " + saaPowerService.addPower(userCodeOperate, IConstants.SAA_AGENTPOWER_USERQUERY, "", "company.comCode", "", "", "");
		}
		List<String> agentComListPower = super.findByHql(agentComHql);
		String agentComs = saaPowerHelpService.removeDuplicateWithOrder(agentComListPower);
		StringBuffer hql = new StringBuffer();
		hql.append(" from SaaUser saaUser where 1=1 and saaUser.userCode <> '0000000000'");
		if (!userCodeOperate.equals("00000000")) {
			// String comCodeSql=saaPowerService.addPower(userCodeOperate,
			// IConstants.SAA_AGENTPOWER_USERQUERY, "", "saaUser.comCode", "",
			// "");
			hql.append(" and saaUser.userCode<>'" + userCodeOperate + "'");
		}
		hql.append(" and saaUser.comCode in(" + agentComs + ")");
		hql.append(" and saaUser.validStatus='1'");
		System.out.println(hql.toString());
		HqlRulesUtil hqlRulesUtil = new HqlRulesUtil();
		hqlRulesUtil.addLike("saaUser.userCode", saaUser.getUserCode());
		hqlRulesUtil.addLike("saaUser.comCode", saaUser.getComCode());
		hqlRulesUtil.addLike("saaUser.userName", saaUser.getUserName());
		if (hqlRulesUtil.getHql().trim().length() != 0) {
			hql.append(" and ").append(hqlRulesUtil.getHql());
		}
//		Page page = findByHql(hql.toString(), pageNo, pageSize);
		return null;
	}

	// 根据人员代码数组查询人员组合
	@Override
	public List<SaaUser> findSaaUserListByCodeArray(String[] userCodeArray) {
		Criteria crit = super.getSession().createCriteria(new SaaUser().getClass());
		List<String> list = Arrays.asList(userCodeArray);
		crit.add(Restrictions.eq("validStatus", "1"));
		crit.add(Restrictions.in("userCode", list));
		return crit.list();
	}
//查询某一处室下的所有人员
	@Override
	public List<SaaUser> findSaaUserByComcode(String comCode) {
		String hql = "from SaaUser where comCode = ? and validStatus = ?";
		return super.findByHql(hql, comCode, "1");
	}
	@Override
	public List<SaaUser> findSaaUserListByCodeList(List<String> userCodeList) {
		if(userCodeList==null||userCodeList.isEmpty()){
			return null;
		}
		Criteria crit = super.getSession().createCriteria(new SaaUser().getClass());
		crit.add(Restrictions.in("userCode", userCodeList));
		crit.add(Restrictions.eq("validStatus", "1"));
		return crit.list();
	}

	
	public List<SaaUser> findByRule(QueryRule queryRule){
		return super.find(queryRule);
	}
	/*------------------------属性setter getter 注入方法------------------------*/

	public void setSaaPowerHelpService(SaaPowerHelpService saaPowerHelpService) {
		this.saaPowerHelpService = saaPowerHelpService;
	}

	public void setSaaPowerService(SaaPowerService saaPowerService) {
		this.saaPowerService = saaPowerService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

}
