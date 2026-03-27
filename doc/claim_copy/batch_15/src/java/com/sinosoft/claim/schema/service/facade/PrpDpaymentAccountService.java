package com.sinosoft.claim.schema.service.facade;
/**
 * 支付帳户信息接口
 * @author 中科软
 */
import ins.framework.common.Page;

import java.util.HashMap;
import java.util.List;

import com.sinosoft.claim.schema.model.PrpDpaymentAccount;

public interface PrpDpaymentAccountService {
	/**
	 * 保存支付帳户信息
	 * @param prpDpaymentAccount ：传入的支付帳户信息
	 */
	public void save(PrpDpaymentAccount prpDpaymentAccount)
			throws Exception;

	/**
	 * 采用批方式插入多条数据
	 * 
	 * @param collection ： 传入的支付帳户信息列表
	 * @throws Exception
	 */
	public void insertAll(List<PrpDpaymentAccount> collection)
			throws Exception;

	/**
	 * 按主键删除一条数据
	 * 
	 * @param accountCode：银行帳号
	 * @throws Exception
	 */
	public void delete(String accountCode) throws Exception;

	/**
	 * 按主键更新一条数据(主键本身无法变更)
	 * 
	 * @param prpdPaymentAccountDto ：传入的支付帳户信息
	 * @throws Exception
	 */
	public void update(PrpDpaymentAccount prpdPaymentAccountDto)
			throws Exception;

	/**
	 * 按主键查找一条数据
	 * 
	 * @param accountCode：银行帳号
	 * @return PrpdPaymentAccount
	 * @throws Exception
	 */
	public PrpDpaymentAccount findByPrimaryKey(String accountCode)
			throws Exception;

	/**
	 * 按条件查询多条数据
	 * 
	 * @param conditions 查询条件
	 * @param pageNo 页号
	 * @param rowsPerPage每页的行数
	 * @return Collection
	 * @throws Exception
	 */
	public List<PrpDpaymentAccount> findByConditions(String conditions,
			int pageNo, int rowsPerPage) throws Exception;

	/**
	 * 按条件查询多条数据
	 * 
	 * @param conditions查询条件
	 * @return Collection
	 * @throws Exception
	 */
	public List<PrpDpaymentAccount> findByConditions(String conditions)
			throws Exception;

	/**
	 * 按条件删除数据
	 * 
	 * @param conditions查询条件
	 * @return 删除的行数
	 * @throws Exception
	 */
	public int deleteByConditions(String conditions) throws Exception;

	/**
	 * 查询满足模糊查询条件的记录数
	 * 
	 * @param conditions查询条件
	 * @return 满足模糊查询条件的记录数
	 * @throws Exception
	 */
	public int getCount(String conditions) throws Exception;
	/**
	 * 保存支付帳户信息
	 * @param hashMap ：传入的包含支付帳户信息的hashmap
	 */
	public PrpDpaymentAccount saveAccount(HashMap<?,?> hashMap) throws Exception;
	
	/**
	 * 按条件查询多条数据
	 * mantis：CLM0075 ，處理人員：BK007  蘇哲，需求單編號：CLM0075.理賠系統-修改或刪除已失效匯款帳戶
	 * @param conditions 查询条件
	 * @param pageNo 页号
	 * @param rowsPerPage每页的行数
	 * @return Collection
	 * @throws Exception
	 */
	public Page findByConditionsForPage(String conditions,
			int pageNo, int rowsPerPage) throws Exception;

}
