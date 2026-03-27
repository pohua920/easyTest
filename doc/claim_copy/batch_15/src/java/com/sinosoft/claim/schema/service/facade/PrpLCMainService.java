package com.sinosoft.claim.schema.service.facade;
/**
 * 代赔保单接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLCMain;
import com.sinosoft.claim.schema.model.PrpLCMainId;

public interface PrpLCMainService {
	
	/**
	 * 保存代赔保单信息
	 * @param prpLCMain ：传入的代赔保单
	 */
	public void save(PrpLCMain prpLCMain) throws Exception;
	
	/**
	 * 代赔保单信息
	 * @param list  :传入的代赔保单信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLCMain> list) throws Exception;
	
	/**
	 * 删除代赔保单信息
	 * @param prpLCMainId ：传入的代赔保单编号
	 */
	public void delete(PrpLCMainId prpLCMainId) throws Exception;

	/**
	 * 更新代赔保单信息
	 * @param prpLCMain :传入需要更新的代赔保单
	 */
	public void update(PrpLCMain prpLCMain) throws Exception;

	/**
	 * 根据代赔保单编号查询出代赔保单信息
	 * @param prpLCMainId ：传入的代赔保单编号
	 * @return 返回代赔保单
	 */
	public PrpLCMain findPrpLCMain(PrpLCMainId prpLCMainId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的代赔保单页面信息
	 */
	public Page findPrpLCMain(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取代赔保单页面信息  的列表
	 * @param queryRule 查询对象
	 * @return 包含的 代赔保单页面信息 的列表
	 */
	public List<PrpLCMain> findPrpLCMain(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据代赔保单编号查询出代赔保单信息
	 * @param certiNo ：传入的代赔保单编号
	 * @return 返回代赔保单
	 */
	public PrpLCMain findPrpLCMain(String certiNo) throws Exception;
}
