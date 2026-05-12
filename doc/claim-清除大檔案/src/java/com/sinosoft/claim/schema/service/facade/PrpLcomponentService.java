package com.sinosoft.claim.schema.service.facade;
/**
 * 换件项目清单接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLcomponent;
import com.sinosoft.claim.schema.model.PrpLcomponentId;

public interface PrpLcomponentService {
	
	/**
	 * 保存换件项目清单信息
	 * @param prpLcomponent ：传入的换件项目清单
	 */
	public void save(PrpLcomponent prpLcomponent) throws Exception;
	
	/**
	 * 换件项目清单信息
	 * @param list  :传入的换件项目清单信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLcomponent> list) throws Exception;
	
	/**
	 * 删除换件项目清单信息
	 * @param prpLcomponentId ：传入的换件项目清单编号
	 */
	public void delete(PrpLcomponentId prpLcomponentId) throws Exception;

	/**
	 * 更新换件项目清单信息
	 * @param prpLcomponent :传入需要更新的换件项目清单
	 */
	public void update(PrpLcomponent prpLcomponent) throws Exception;

	/**
	 * 根据换件项目清单编号查询出换件项目清单信息
	 * @param prpLcomponentId ：传入的换件项目清单编号
	 * @return 返回换件项目清单
	 */
	public PrpLcomponent findPrpLcomponent(PrpLcomponentId prpLcomponentId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的换件项目清单页面信息
	 */
	public Page findPrpLcomponent(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取  换件项目清单页面信息的列表
	 * @param queryRule 查询对象
	 * @return 包含的  换件项目清单页面信息的列表
	 */
	public List<PrpLcomponent> findPrpLcomponent(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据换件项目清单编号查询出换件项目清单信息
	 * @param certiNo ：传入的换件项目清单编号
	 * @return 返回换件项目清单
	 */
	public PrpLcomponent findPrpLcomponent(String certiNo) throws Exception;
}
