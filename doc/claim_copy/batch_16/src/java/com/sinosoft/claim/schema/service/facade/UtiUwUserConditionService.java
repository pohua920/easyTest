package com.sinosoft.claim.schema.service.facade;
/**
 * 人员核保核赔条件表接口
 * @author 中科软
 */
import java.util.List;

import com.sinosoft.claim.schema.model.UtiUwUserCondition;


public interface UtiUwUserConditionService {
	/**
	 * 删除人员核保核赔条件信息
	 * @param conditions ：传入的查询条件
	 */
    public void deleteByConditions(String conditions)
    throws Exception;
	/**
	 * 根据查询对象获取人员核保核赔条件信息  的集合
	 * @param conditions 查询对象
	 * @return 包含的  人员核保核赔条件信息的集合
	 */
    public List<UtiUwUserCondition> findGroupByConditions(String conditions)
    throws Exception;
	/**
	 * 保存人员核保核赔条件信息
	 * @param list:保存人员核保核赔条件信息
	 */
    public void insertAll(List<?> list)
    throws Exception;
	/**
	 * 根据查询对象获取人员核保核赔条件信息  的集合
	 * @param conditions 查询对象
	 * @return 包含的  人员核保核赔条件信息的集合
	 */
    public List<UtiUwUserCondition> findFactorValueByConditions(String conditions) throws Exception;
	/**
	 * 根据查询对象获取人员核保核赔条件信息  的集合
	 * @param sql 查询语句
	 * @return 包含的  人员核保核赔条件信息的集合
	 */
    public List<UtiUwUserCondition> findByConditions(String sql)throws Exception;
}
