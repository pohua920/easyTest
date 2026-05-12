package com.sinosoft.claim.schema.service.facade;
/**
 * 财产损失部位接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLthirdProp;
import com.sinosoft.claim.schema.model.PrpLthirdPropId;

public interface PrpLthirdPropService {
	
	/**
	 * 保存财产损失部位信息
	 * @param prpLthirdProp ：传入的财产损失部位
	 */
	public void save(PrpLthirdProp prpLthirdProp) throws Exception;
	
	/**
	 * 保存财产损失部位信息
	 * @param list:保存财产损失部位信息
	 */
	public void save(List<PrpLthirdProp> list) throws Exception;
	
	/**
	 * 删除财产损失部位信息
	 * @param prpLthirdPropId ：传入的财产损失部位编号
	 */
	public void delete(PrpLthirdPropId prpLthirdPropId) throws Exception;

	/**
	 * 更新财产损失部位信息
	 * @param prpLthirdProp :传入需要更新的财产损失部位
	 */
	public void update(PrpLthirdProp prpLthirdProp) throws Exception;

	/**
	 * 根据财产损失部位编号查询出财产损失部位信息
	 * @param prpLthirdPropId ：传入的财产损失部位编号
	 * @return 返回财产损失部位
	 */
	public PrpLthirdProp findPrpLthirdProp(PrpLthirdPropId prpLthirdPropId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的财产损失部位页面信息
	 */
	public Page findPrpLthirdProp(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取财产损失部位信息  的集合
	 * @param queryRule 查询对象
	 * @return 包含的  财产损失部位信息的集合
	 */
	public List<PrpLthirdProp> findPrpLthirdProp(QueryRule queryRule) throws Exception;
	/**
	 * @param registNo
	 * @throws Exception
	 * 根据报案号删除信息
	 */
	public void deleteByRegistNo(String registNo) throws Exception;
	/**
	 * 保存财产损失部位信息
	 * @param list:保存财产损失部位信息
	 */
	public void saveOrUpdate(List<PrpLthirdProp> list) throws Exception;
	/**
	 * 保存财产损失部位信息
	 * @param list:保存财产损失部位信息
	 */
	public void saveOrUpdate(PrpLthirdProp prpLthirdProp) throws Exception;
	/**
	 * 保存财产损失部位信息
	 * @param list:保存财产损失部位信息集合
	 */
	public void insertAll(List<PrpLthirdProp> prpLthirdPropList);
}
