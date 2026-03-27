package com.sinosoft.claim.schema.service.facade;

import ins.framework.common.Page;

import java.sql.SQLException;
import java.util.Collection;

import com.sinosoft.claim.schema.model.PrpDagent;

/**
 * 代理人信息接口
 * @author 中科软
 *
 */
public interface PrpDagentService {

    /**
     * 插入一条数据
     * @param prpDagentDto prpDagentDto
     * @throws Exception
     */
    public void save(PrpDagent prpDagent) throws Exception;

    /**
     * 按主键删除一条数据
     * @param agentCode 代理人代码
     * @throws Exception
     */
    public void delete(String agentCode) throws Exception;

    /**
     * 按条件删除数据
     * @param condtions 删除条件
     * @throws Exception
     */
    public void deleteByConditions(String conditions) throws Exception;

    /**
     * 按主键更新一条数据(主键本身无法变更)
     * @param prpDagent prpDagent
     * @throws Exception
     */
    public void update(PrpDagent prpDagent) throws Exception;
    /**
     * 按主键查找一条数据
     * @param agentCode 代理人代码
     * @return prpDagent prpDagent
     * @throws Exception
     */
    public PrpDagent findPrpDagent(String agentCode) throws Exception;
    
    /**
     * 按条件查询多条数据
     * @param conditions 查询条件
     * @param pageNo 页号
     * @param rowsPerPage 每页的行数
     * @return Collection 包含prpDagent的集合
     * @throws Exception
     */
    public Page findPrpDagent(String conditions,int pageNo,int pageSize) throws Exception;
    /** 根据代理人代码得到代理人姓名
     * @param agentCode 代理人代码
     */
  public String translateAgentName(String agentCode) throws SQLException,Exception;

	public Collection<PrpDagent> findByConditions(String conditions) throws Exception;
	/**
	 * 查询销管系统的讯息
	 * 台壽通路營業人員
	 * 台壽通路營業主管ID,电话
	 * @param sales 用户名称
	 * @param handlerCode
	 * @return
	 * @throws Exception
	 */
	public PrpDagent findSalesPrpDagent(String salesUser,String handlerCode )throws Exception;
}
