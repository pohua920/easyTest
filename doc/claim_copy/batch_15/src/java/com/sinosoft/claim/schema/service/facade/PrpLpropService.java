package com.sinosoft.claim.schema.service.facade;
/**
 * 财产核定损明细清单接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLprop;
import com.sinosoft.claim.schema.model.PrpLpropId;

public interface PrpLpropService {
	
	/**
	 * 保存财产核定损明细清单信息
	 * @param prpLprop ：传入的财产核定损明细清单
	 */
	public void save(PrpLprop prpLprop) throws Exception;
	
	/**
	 * 财产核定损明细清单信息
	 * @param list  :传入的财产核定损明细清单信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLprop> list) throws Exception;
	
	/**
	 * 删除财产核定损明细清单信息
	 * @param prpLpropId ：传入的财产核定损明细清单编号
	 */
	public void delete(PrpLpropId prpLpropId) throws Exception;

	/**
	 * 更新财产核定损明细清单信息
	 * @param prpLprop :传入需要更新的财产核定损明细清单
	 */
	public void update(PrpLprop prpLprop) throws Exception;

	/**
	 * 根据财产核定损明细清单编号查询出财产核定损明细清单信息
	 * @param prpLpropId ：传入的财产核定损明细清单编号
	 * @return 返回财产核定损明细清单
	 */
	public PrpLprop findPrpLprop(PrpLpropId prpLpropId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的财产核定损明细清单页面信息
	 */
	public Page findPrpLprop(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取财产核定损明细清单信息  的集合
	 * @param queryRule 查询对象
	 * @return 包含的  财产核定损明细清单信息的集合
	 */
	public List<PrpLprop> findPrpLprop(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据财产核定损明细清单编号查询出财产核定损明细清单信息
	 * @param certiNo ：传入的财产核定损明细清单编号
	 * @return 返回财产核定损明细清单
	 */
	public PrpLprop findPrpLprop(String certiNo) throws Exception;
}
