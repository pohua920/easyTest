package cn.com.sinosoft.saa.service.spring;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.ArrayList;
import java.util.List;

import cn.com.sinosoft.saa.model.SaaUser;
import cn.com.sinosoft.saa.model.SaaUserInstead;
import cn.com.sinosoft.saa.service.facade.SaaUserInsteadService;

public class SaaUserInsteadServiceSpringImpl extends
		GenericDaoHibernate<SaaUserInstead, String> implements
		SaaUserInsteadService {
	private SaaUserInstead saaUserInstead;

	public SaaUserInstead getInstance() {
		saaUserInstead = new SaaUserInstead();
		return saaUserInstead;
	}

	public void editUserInstead(SaaUserInstead saaUserInstead) {
		String hql = "select saaUserInstead from SaaUserInstead saaUserInstead where saaUserInstead.authUserCode=?";
		List<SaaUserInstead> saaUserInsteadList = super.findByHql(hql,
				saaUserInstead.getAuthUserCode());
		if (saaUserInsteadList != null) {
			super.deleteAll(saaUserInsteadList);
		}
		super.save(saaUserInstead);

	}

	public void updateUserInstead(SaaUserInstead saaUserInstead) {
		super.save(saaUserInstead);
	}

	@SuppressWarnings("unchecked")
	public String checkUserInstead(String userCode) {
		// liyu 查看选择的用户，看其是否已经被授权为代岗，若是，则只能查看接下来的页面，否则则可以编辑接下来的页面
		String hql = "select saaUserInstead from SaaUserInstead saaUserInstead where saaUserInstead.authUserCode=?";
		List<SaaUserInstead> saaUserInsteadList = super
				.findByHql(hql, userCode);
		if (saaUserInsteadList == null || saaUserInsteadList.size() == 0
				|| saaUserInsteadList.get(0).getValidStatus().equals("0")) {
			return "editUserInstead";
		} else {
			return "viewUserInstead";
		}
	}

	@SuppressWarnings("unchecked")
	public SaaUserInstead getUserInsteadByUserCode(String userCode) {
		String hql = "select saaUserInstead from SaaUserInstead saaUserInstead where saaUserInstead.authUserCode=?";
		List<SaaUserInstead> saaUserInsteadList = super
				.findByHql(hql, userCode);
		return saaUserInsteadList.get(0);
	}

	public Page getUserList(SaaUser saaUser, int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		QueryRule queryRule = QueryRule.getInstance();
		if (saaUser.getUserCode() != null && !"".equals(saaUser.getUserCode())) {
			queryRule.addLike("userCode", "%" + saaUser.getUserCode() + "%");
		}
		if (saaUser.getComCode() != null && !"".equals(saaUser.getComCode())) {
			queryRule.addEqual("comCode", saaUser.getComCode());
		}
		if (saaUser.getUserName() != null && !"".equals(saaUser.getUserName())) {
			queryRule.addLike("userName", "%" + saaUser.getUserName() + "%");
		}
		if (saaUser.getValidStatus() != null
				&& !"".equals(saaUser.getValidStatus())) {
			queryRule.addLike("validStatus", saaUser.getValidStatus());
		}
		Page page = super.find(SaaUser.class, queryRule, pageNo, pageSize);
		List<SaaUser> users = page.getResult();
		List<SaaUser> saaUsers = new ArrayList<SaaUser>(0);
		for (SaaUser user : users) {
			SaaUser condition = new SaaUser();
			if (user == null)
				continue;
			condition.setComCode(user.getComCode());
			condition.setUserName(user.getUserName());
			condition.setUserCode(user.getUserCode());
			condition.setValidStatus(user.getValidStatus());
			saaUsers.add(condition);
		}
		Page newPage = new Page(0, page.getTotalCount(), page.getPageSize(),
				saaUsers);
		return newPage;
	}
}