package com.sinosoft.claim.schema.service.facade;

/**
 * 人员级别设置表接口
 * @author 中科软
 */
import java.util.List;

import com.sinosoft.claim.schema.model.UtiUwLevel;


public interface UtiUwLevelService {
	
	/**
	 * 删除满足条件的记录
	 */
	public void deleteByConditions(String conditions2) throws Exception;
	/**
	 * 根据查询对象获取人员级别设置信息  的集合
	 * @param queryRule 查询对象
	 * @return 包含的  人员级别设置信息的集合
	 */
	public List<UtiUwLevel> findGroupByConditions(String conditions) throws Exception;
	/**
	 * 保存人员级别设置信息
	 * @param list:保存人员级别设置信息
	 */
	public void insertAll(List<UtiUwLevel> list) throws Exception;
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param conditions 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的人员级别设置页面信息
	 */
	public List<UtiUwLevel> findByConditions(String conditionsLevel) throws Exception;
	/**
	 * 返回满足条件的数量
	 */
	public int getCount(String conditions)throws Exception;
	public UtiUwLevel findByPrimaryKeyAndValidStatus(String userCode, String calComCode, String riskCode, int modelNo, int nodeNo, String uwType)throws Exception;
}
