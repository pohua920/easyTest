package com.sinosoft.claim.schema.service.facade;

import ins.framework.common.Page;

import java.util.List;

import com.sinosoft.claim.schema.model.WfOtherFees;

/**
 * 其他费用信息接口
 * @author 中科软
 */
public interface WfOtherFeesService {

    /**
     * 插入一条数据
     * @param wfotherfeesDto wfotherfeesDto
     * @throws Exception
     */
    public void insert(WfOtherFees wfotherfees) throws Exception;

    /**
     * 按主键删除一条数据
     * @param businessno BUSINESSNO
     * @param serialno SERIALNO
     * @param lineno LINENO
     * @throws Exception
     */
    public void delete(String businessno,int serialno,int lineno) throws Exception;

    /**
     * 按条件删除数据
     * @param conditions 删除条件
     * @throws Exception
     */
    public void deleteByConditions(String conditions) throws Exception;

    /**
     * 按主键更新一条数据(主键本身无法变更)
     * @param wfotherfeesDto wfotherfeesDto
     * @throws Exception
     */
    public void update(WfOtherFees wfotherfees);

    /**
     * 按主键查找一条数据
     * @param businessno BUSINESSNO
     * @param serialno SERIALNO
     * @param lineno LINENO
     * @return wfotherfeesDto wfotherfeesDto
     * @throws Exception
     */
    public WfOtherFees findByPrimaryKey(String businessno,int serialno,int lineno) throws Exception;

    /**
     * 按条件查询多条数据
     * @param conditions 查询条件
     * @param pageNo 页号
     * @param rowsPerPage 每页的行数
     * @return PageRecord 查询的一页的结果
     * @throws Exception
     */
    public Page findByConditions(String conditions,int pageNo,int rowsPerPage) throws Exception;

    /**
     * 按条件查询多条数据
     * @param conditions 查询条件
     * @return Collection 包含wfotherfeesDto的集合
     * @throws Exception
     */
    public List<?> findByConditions(String conditions) throws Exception;

    /**
     * 查询满足模糊查询条件的记录数
     * @param conditions conditions
     * @return 满足模糊查询条件的记录数
     * @throws Exception
     */
    public int getCount(String conditions) throws Exception;
}
