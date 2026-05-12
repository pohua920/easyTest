package com.sinosoft.claim.schema.service.facade;
/**
 * 双核因子表接口
 * @author 中科软
 */
import ins.framework.common.Page;

import java.util.List;

import com.sinosoft.claim.schema.model.UtiUwFactor;



public interface UtiUwFactorService {
	/**
	 * 保存双核因子信息
	 * @param prpLthirdCarLoss ：传入的双核因子
	 */
	public void insert(UtiUwFactor utiUwFactorDto) throws Exception;
	/**
	 * 保存双核因子信息
	 * @param list:保存双核因子信息
	 */
	public void insertAll(List<?> collection) throws Exception;
	/**
	 * 删除双核因子信息
	 * @param prpLthirdCarLossId ：传入的双核因子编号
	 */
	public void delete(String uwType, String classCode, String factorCode) throws Exception;
	/**
	 * 更新双核因子信息
	 * @param prpLthirdCarLoss :传入需要更新的双核因子
	 */
	public void update(UtiUwFactor utiUwFactorDto)  throws Exception;
	/**
	 * 根据双核因子编号查询出双核因子信息
	 * @param prpLthirdCarLossId ：传入的双核因子编号
	 * @return 返回双核因子
	 */
	public UtiUwFactor findByPrimaryKey(String uwType, String classCode, String factorCode) throws Exception;
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param conditions 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的双核因子页面信息
	 */
	public List<UtiUwFactor> findByConditions(String conditions, int pageNo, int rowsPerPage);
	/**
	 * 根据查询对象获取双核因子信息  的集合
	 * @param conditions 查询对象
	 * @return 包含的  双核因子信息的集合
	 */
	public List<UtiUwFactor> findByConditions(String conditions) throws Exception;
	/**
	 * 返回删除的数量
	 */
	public int deleteByConditions(String conditions) throws Exception;
	/**
	 * 返回满足条件的数量
	 */
	public int getCount(String conditions)  throws Exception;
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param conditions 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的双核因子页面信息
	 */
	public Page findPageByConditions(String conditions, int pageNo, int rowsPerPage);
}
