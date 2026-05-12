package com.sinosoft.claim.schema.service.facade;
/**
 * WfLog表的附属表接口
 * @author 中科软
 */
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.WfLogExt;
import com.sinosoft.claim.schema.model.WfLogExtId;


public interface WfLogExtService {
    
    /**
     * 按条件删除数据
     * @param conditions 查询条件
     * @return 删除的行数
     * @throws Exception
     */
    public void deleteByQueryRule(QueryRule queryRule)
            throws Exception;
    
    /**
     * 采用批方式插入多条数据
     * @param collection collection
     * @throws Exception
     */
    public void insertAll(List<WfLogExt> collection) throws Exception;
	/**
	 * 根据查询对象获取 WfLog表的附属表信息 的集合
	 * @param queryRule 查询对象
	 * @return 包含的WfLog表的附属表信息  的集合
	 */
    public List<WfLogExt> getWfLogExtList(QueryRule queryRule);
    
	/**
	 * 更新WfLog表的附属表信息
	 * @param wfLogExt :传入需要更新的WfLog表的附属表
	 */
	public void update(WfLogExt wfLogExt) throws Exception;
	/**
	 * 删除WfLog表的附属表信息
	 * @param wfLogExtId ：传入的WfLog表的附属表编号
	 */
	public void delete(WfLogExtId wfLogExtId) throws Exception;
	/**
	 * 保存WfLog表的附属表信息
	 * @param wfLogExt ：传入的WfLog表的附属表
	 */
	public void save(WfLogExt wfLogExt) throws Exception;
	
	/**
	 * 保存WfLog表的附属表信息
	 * @param list:保存WfLog表的附属表信息
	 */
	public void save(List<WfLogExt> list) throws Exception;
	/**
	 * 根据WfLog表的附属表编号查询出WfLog表的附属表信息
	 * @param wfLogExtId ：传入的WfLog表的附属表编号
	 * @return 返回WfLog表的附属表
	 */
	public WfLogExt findWfLogExt(WfLogExtId wfLogExtId) throws Exception;
	
}
