package com.sinosoft.claim.schema.service.facade;
/**
 * 损失部位接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLthirdCarLoss;
import com.sinosoft.claim.schema.model.PrpLthirdCarLossId;

public interface PrpLthirdCarLossService {
	
	/**
	 * 保存损失部位信息
	 * @param prpLthirdCarLoss ：传入的损失部位
	 */
	public void save(PrpLthirdCarLoss prpLthirdCarLoss) throws Exception;
	
	/**
	 * 保存损失部位信息
	 * @param list:保存损失部位信息
	 */
	public void save(List<PrpLthirdCarLoss> list) throws Exception;
	
	/**
	 * 删除损失部位信息
	 * @param prpLthirdCarLossId ：传入的损失部位编号
	 */
	public void delete(PrpLthirdCarLossId prpLthirdCarLossId) throws Exception;

	/**
	 * 更新损失部位信息
	 * @param prpLthirdCarLoss :传入需要更新的损失部位
	 */
	public void update(PrpLthirdCarLoss prpLthirdCarLoss) throws Exception;

	/**
	 * 根据损失部位编号查询出损失部位信息
	 * @param prpLthirdCarLossId ：传入的损失部位编号
	 * @return 返回损失部位
	 */
	public PrpLthirdCarLoss findPrpLthirdCarLoss(PrpLthirdCarLossId prpLthirdCarLossId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的损失部位页面信息
	 */
	public Page findPrpLthirdCarLoss(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取损失部位信息  的集合
	 * @param queryRule 查询对象
	 * @return 包含的  损失部位信息的集合
	 */
	public List<PrpLthirdCarLoss> findPrpLthirdCarLoss(QueryRule queryRule) throws Exception;
	/**
	 * @param registNo
	 * @return
	 * @throws Exception
	 * 根据报案号查询所有信息
	 */
	public List<PrpLthirdCarLoss> findByRegistNo(String registNo)throws Exception;
	/**
	 * @param registNo
	 * @throws Exception
	 * 根据报案号删除信息
	 */
	public void deleteByRegistNo(String registNo) throws Exception;
	/**
	 * 保存损失部位信息
	 * @param list:保存损失部位信息
	 */
	public void saveOrUpdate(List<PrpLthirdCarLoss> list) throws Exception;
	/**
	 * 保存损失部位信息
	 * @param list:保存损失部位信息
	 */
	public void saveOrUpdate(PrpLthirdCarLoss prpLthirdCarLoss) throws Exception;

	public void insertAll(List<PrpLthirdCarLoss> prpLthirdCarLossList);
}
