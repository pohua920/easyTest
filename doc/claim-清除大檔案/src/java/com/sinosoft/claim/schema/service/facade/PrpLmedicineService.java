package com.sinosoft.claim.schema.service.facade;
/**
 * 雇员医药费清单接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLmedicine;
import com.sinosoft.claim.schema.model.PrpLmedicineId;

public interface PrpLmedicineService {
	
	/**
	 * 保存雇员医药费清单信息
	 * @param prpLmedicine ：传入的雇员医药费清单
	 */
	public void save(PrpLmedicine prpLmedicine) throws Exception;
	
	/**
	 * 雇员医药费清单信息
	 * @param list  :传入的雇员医药费清单信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLmedicine> list) throws Exception;
	
	/**
	 * 删除雇员医药费清单信息
	 * @param prpLmedicineId ：传入的雇员医药费清单编号
	 */
	public void delete(PrpLmedicineId prpLmedicineId) throws Exception;

	/**
	 * 更新雇员医药费清单信息
	 * @param prpLmedicine :传入需要更新的雇员医药费清单
	 */
	public void update(PrpLmedicine prpLmedicine) throws Exception;

	/**
	 * 根据雇员医药费清单编号查询出雇员医药费清单信息
	 * @param prpLmedicineId ：传入的雇员医药费清单编号
	 * @return 返回雇员医药费清单
	 */
	public PrpLmedicine findPrpLmedicine(PrpLmedicineId prpLmedicineId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的雇员医药费清单页面信息
	 */
	public Page findPrpLmedicine(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取  雇员医药费清单页面信息的集合
	 * @param queryRule 查询对象
	 * @return 包含的雇员医药费清单页面信息  的集合
	 */
	public List<PrpLmedicine> findPrpLmedicine(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据雇员医药费清单编号查询出雇员医药费清单信息
	 * @param certiNo ：传入的雇员医药费清单编号
	 * @return 返回雇员医药费清单
	 */
	public PrpLmedicine findPrpLmedicine(String certiNo) throws Exception;
}
