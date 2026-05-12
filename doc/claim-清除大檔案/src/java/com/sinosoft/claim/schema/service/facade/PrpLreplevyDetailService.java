package com.sinosoft.claim.schema.service.facade;
/**
 * 权益转让及追偿信息接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLreplevyDetail;
import com.sinosoft.claim.schema.model.PrpLreplevyDetailId;

public interface PrpLreplevyDetailService {
	
	/**
	 * 保存权益转让及追偿信息
	 * @param prpLreplevyDetail ：传入的权益转让及追偿信息
	 */
	public void save(PrpLreplevyDetail prpLreplevyDetail) throws Exception;
	
	/**
	 * 权益转让及追偿信息
	 * @param list  :传入的权益转让及追偿信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLreplevyDetail> list) throws Exception;
	
	/**
	 * 删除权益转让及追偿信息
	 * @param prpLreplevyDetailId ：传入的权益转让及追偿信息编号
	 */
	public void delete(PrpLreplevyDetailId prpLreplevyDetailId) throws Exception;

	/**
	 * 更新权益转让及追偿信息
	 * @param prpLreplevyDetail :传入需要更新的权益转让及追偿信息
	 */
	public void update(PrpLreplevyDetail prpLreplevyDetail) throws Exception;

	/**
	 * 根据权益转让及追偿信息编号查询出权益转让及追偿信息
	 * @param prpLreplevyDetailId ：传入的权益转让及追偿信息编号
	 * @return 返回权益转让及追偿信息
	 */
	public PrpLreplevyDetail findPrpLreplevyDetail(PrpLreplevyDetailId prpLreplevyDetailId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的权益转让及追偿信息页面信息
	 */
	public Page findPrpLreplevyDetail(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取权益转让及追偿信息  的集合
	 * @param queryRule 查询对象
	 * @return 包含的权益转让及追偿信息 的集合
	 */
	public List<PrpLreplevyDetail> findPrpLreplevyDetail(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据权益转让及追偿信息编号查询出权益转让及追偿信息
	 * @param certiNo ：传入的权益转让及追偿信息编号
	 * @return 返回权益转让及追偿信息
	 */
	public PrpLreplevyDetail findPrpLreplevyDetail(String certiNo) throws Exception;
}
