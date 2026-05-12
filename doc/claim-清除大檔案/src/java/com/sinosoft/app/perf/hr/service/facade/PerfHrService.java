package com.sinosoft.app.perf.hr.service.facade;

import java.util.List;

import com.sinosoft.app.perf.hr.model.HrCompany;
import com.sinosoft.app.perf.hr.model.HrDepartment;
import com.sinosoft.app.perf.hr.model.HrUser;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

public interface PerfHrService {
    /**
     * 同步sun数据库里的内容到perfdb里
     * @author 中科软
     * @param hql
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Page findHrCompanyPage(String hql, int pageNo, int pageSize);

    /**
     * 同步sun数据库里User数据内容到perfdb里
     * @author 中科软 
     * @Date 2011-8-22
     * @param hql
     * @param pageNo
     * @param pageSize
     * @return
     * @throws Exception
     */
    public Page findHrUserPage(String hql,int pageNo,int pageSize) throws Exception;
    
    /**
     * 获得记录条数
     * @author 中科软
     * @param hql
     * @return
     */
    public long getCount(String hql);
    
    /**
     * 查询人力机构数据
     * @author 中科软
     * @return List<HrCompany>
     */
	public List<HrCompany> findHrCompany(QueryRule queryRule);
	
    /**
     * 查询人力部门数据
     * @author 中科软
     * @return List<HrDepartment>
     */
	public List<HrDepartment> findHrDepartment(QueryRule queryRule);
	
    /**
     * 查询人力员工数据
     * @author 中科软
     * @return List<HrUser>
     */
	public List<HrUser> findHrUser(QueryRule queryRule);
}
