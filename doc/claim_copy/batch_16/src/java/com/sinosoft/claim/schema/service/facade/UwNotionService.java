package com.sinosoft.claim.schema.service.facade;
/**
 * 核保核赔处理意见表接口
 * @author 中科软
 */
import ins.framework.common.QueryRule;

import java.sql.SQLException;
import java.util.List;

import com.sinosoft.claim.schema.model.UwNotion;
import com.sinosoft.claim.schema.model.WfLog;



public interface UwNotionService {

	/**
	 * 批量插入
	 * 
	 * @param collection
	 * @throws Exception
	 * @author 中科软
	 */
	public void insertAll(List<UwNotion> uwNotionList) throws Exception ;

	/**
	 * 保存审核意见。
	 * 
	 * @date 2006-07-20
	 * @author 中科软
	 * @throws SQLException
	 */
	public void saveNotion(UwNotion uwNotionDto) throws Exception ;

	/**
	 * 保存任务
	 * 
	 * @param uwNotionDto
	 * @param prpDuserDto
	 * @throws SQLException
	 * @throws Exception
	 * @author 中科软
	 */


	/**
     * 将制单员的说明，插入到UwNotion表中。如果是出单员，则插入出单员意见
     */
    public void insertUwNotionByMakeUser(WfLog wfLog , String iCertiType)
            throws Exception ;
	/**
	 * 按条件查询多条数据
	 * 
	 * @param conditions查询条件
	 * @return Collection 包含uwNotionDto的集合
	 * @throws Exception
	 */
	public List<UwNotion> findByConditions(QueryRule queryRule)
			throws Exception ;
	/**
     * 删除多条数据
     * @return Collection 包含uwNotionDto的集合
     * @throws Exception
     */
    public void deleteList(List<?> list)
            throws Exception ;
	
	/**
	 * 
	 * @param flowID
	 * @return
	 * @throws Exception
	 */
	public String getPreHandleText(String flowID)throws Exception ;
	/**
		将HandleText拆分，组成多个uwNotionDto对象
	 */
	public List<UwNotion> ungroup(UwNotion uwNotionDto);
	/**
	 * 根据查询对象获取核保核赔处理意见的集合
	 * @param conditions 查询对象
	 * @return 包含的核保核赔处理意见信息的集合
	 */
	public List<UwNotion> findByConditions(String conditions) throws Exception;
	


}
