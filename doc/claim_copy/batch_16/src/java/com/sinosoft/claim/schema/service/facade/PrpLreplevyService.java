package com.sinosoft.claim.schema.service.facade;
/**
 * 权益转让及追偿登记表接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLreplevy;

public interface PrpLreplevyService {
	
	/**
	 * 权益转让及追偿登记信息
	 * @param PrpLreplevy ：传入的权益转让及追偿登记信息
	 */
	public void save(PrpLreplevy prpLreplevy) throws Exception;
	
	/**
	 * 保存权益转让及追偿登记信息
	 * @param list  :传入的权益转让及追偿登记信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLreplevy> list) throws Exception;
	
	/**
	 * 删除权益转让及追偿登记
	 * @param policyNo ：传入的权益转让及追偿登记信息编号
	 */
	public void delete(String policyNo) throws Exception;

	/**
	 * 更新权益转让及追偿登记
	 * @param PrpLreplevy :传入需要更新的权益转让及追偿登记信息
	 */
	public void update(PrpLreplevy prpLreplevy) throws Exception;

	/**
	 * 根据权益转让及追偿登记信息编号查询出权益转让及追偿登记
	 * @param policyNo ：传入的权益转让及追偿登记信息编号
	 * @return 返回权益转让及追偿登记信息
	 */
	public PrpLreplevy findPrpLreplevy(String policyNo) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的权益转让及追偿登记信息页面信息
	 */
	public Page findPrpLreplevy(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取权益转让及追偿登记信息  的集合
	 * @param queryRule 查询对象
	 * @return 包含的权益转让及追偿登记信息  的集合
	 */
	public List<PrpLreplevy> findPrpLreplevy(QueryRule queryRule) throws Exception;
}
