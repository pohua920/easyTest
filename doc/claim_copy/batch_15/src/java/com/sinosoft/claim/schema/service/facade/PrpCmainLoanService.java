package com.sinosoft.claim.schema.service.facade;

/**
 * 贷款保险保单信息接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import java.util.List;
import com.sinosoft.claim.schema.model.PrpCmainLoan;
import com.sinosoft.claim.schema.model.PrpCmainLoanId;

public interface PrpCmainLoanService {

	/**
	 * 保存贷款保险保单信息
	 * @param prpLcheck ：传入的贷款保险保单信息
	 */
	public void save(PrpCmainLoan prpCmainLoan) throws Exception;

	/**
	 * 贷款保险保单信息
	 * @param list :传入的贷款保险保单信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpCmainLoan> list) throws Exception;

	/**
	 * 删除贷款保险保单信息
	 * @param prpCmainLoanId ：传入的贷款保险保单信息编号
	 */
	public void delete(PrpCmainLoanId prpCmainLoanId) throws Exception;

	/**
	 * 更新贷款保险保单信息
	 * @param prpCmainLoan :传入需要更新的贷款保险保单信息
	 */
	public void update(PrpCmainLoan prpCmainLoan) throws Exception;

	/**
	 * 根据贷款保险保单信息编号查询出贷款保险保单信息
	 * @param prpCmainLoanId ：传入的贷款保险保单信息编号
	 * @return 返回贷款保险保单信息
	 */
	public PrpCmainLoan findPrpCmainLoan(PrpCmainLoanId prpCmainLoanId) throws Exception;

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的贷款保险保单信息页面信息
	 */
	public Page findPrpCmainLoan(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取贷款保险保单信息的列表
	 * @param queryRule 查询对象
	 * @return 包含的贷款保险保单信息的列表
	 */
	public List<PrpCmainLoan> findPrpCmainLoan(QueryRule queryRule) throws Exception;
}
