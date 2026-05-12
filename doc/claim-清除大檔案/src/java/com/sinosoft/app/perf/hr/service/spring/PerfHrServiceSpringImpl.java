package com.sinosoft.app.perf.hr.service.spring;

import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import com.sinosoft.app.perf.hr.model.HrCompany;
import com.sinosoft.app.perf.hr.model.HrDepartment;
import com.sinosoft.app.perf.hr.model.HrUser;
import com.sinosoft.app.perf.hr.service.facade.PerfHrService;

public class PerfHrServiceSpringImpl extends GenericDaoHibernate<HrCompany, String> implements PerfHrService{

    /**
     * 同步sun数据库里的内容到perfdb里
     * @author 中科软
     * @param hql
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Page findHrCompanyPage(String hql, int pageNo, int pageSize) {
       return null;
    }
    
    /**
     * 查询人力机构数据
     * @author 中科软
     * @return List<HrCompany>
     */
    @SuppressWarnings("unchecked")
	public List<HrCompany> findHrCompany(QueryRule queryRule) {
        return super.find(HrCompany.class,queryRule);
    }
    
    /**
     * 查询人力部门数据
     * @author 中科软
     * @return List<HrDepartment>
     */
    @SuppressWarnings("unchecked")
	public List<HrDepartment> findHrDepartment(QueryRule queryRule) {
        return super.find(HrDepartment.class, queryRule);
    }
    
    @Override
    public long getCount(String hql) {
        return super.getCount(hql);
    }



	/* 
	 * linsiming-wb 
	 * 2011-8-22
	 */
	@Override
	public Page findHrUserPage(String hql, int pageNo, int pageSize)
			throws Exception {
		return null;
	}

    /**
     * 查询人力员工数据
     * @author 中科软
     * @return List<HrUser>
     */
	@SuppressWarnings("unchecked")
	public List<HrUser> findHrUser(QueryRule queryRule){
		return super.find(HrUser.class, queryRule);
	}
}
