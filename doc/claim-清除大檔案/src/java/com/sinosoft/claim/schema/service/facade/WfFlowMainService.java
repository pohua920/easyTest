package com.sinosoft.claim.schema.service.facade;

/**
 * 工作流主表接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.WfFlowMain;


public interface WfFlowMainService {

    /**
     * 插入一条数据
     * @param wfFlowMain wfFlowMain
     * @throws Exception
     */
    public void insert(WfFlowMain wfFlowMain) throws Exception;

    /**
     * 按主键删除一条数据
     * @param flowID 工作流号
     * @throws Exception
     */
    public void delete(String flowID) throws Exception;

    /**
     * 按条件删除数据
     * @param conditions 删除条件
     * @throws Exception
     */
    public void deleteByQueryRule(QueryRule queryRule) throws Exception;

    /**
     * 按主键更新一条数据(主键本身无法变更)
     * @param wfFlowMain wfFlowMain
     * @throws Exception
     */
    public void update(WfFlowMain wfFlowMain) throws Exception;

    /**
     * 按主键查找一条数据
     * @param flowID 工作流号
     * @return wfFlowMain wfFlowMain
     * @throws Exception
     */
    public WfFlowMain findByPrimaryKey(String flowID) throws Exception;

    /**
     * 按条件查询多条数据
     * @param conditions 查询条件
     * @param pageNo 页号
     * @param rowsPerPage 每页的行数
     * @return PageRecord 查询的一页的结果
     * @throws Exception
     */
    public Page findByQueryRule(QueryRule queryRule,int pageNo,int rowsPerPage) throws Exception;

    /**
     * 按条件查询多条数据
     * @param conditions 查询条件
     * @return Collection 包含wfFlowMain的集合
     * @throws Exception
     */
    public List<WfFlowMain> findByQueryRule(QueryRule queryRule) throws Exception;

    /**
     * 查询满足模糊查询条件的记录数
     * @param conditions 模糊查询条件
     * @return 满足模糊查询条件的记录数
     * @throws Exception
     */
    public int getCount(QueryRule queryRule) throws Exception;
    
    
}
