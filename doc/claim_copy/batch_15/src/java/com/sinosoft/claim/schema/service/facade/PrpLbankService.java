package com.sinosoft.claim.schema.service.facade;

/**
 * 银行信息表接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import java.util.List;
import com.sinosoft.claim.schema.model.PrpLbank;
import com.sinosoft.claim.schema.model.PrpLbankId;

public interface PrpLbankService {

	/**
	 * 保存银行信息
	 * @param prpLbank ：传入的银行信息
	 */
	public void save(PrpLbank prpLbank) throws Exception;

	/**
	 * 银行信息
	 * @param list :传入的银行信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLbank> list) throws Exception;

	/**
	 * 删除银行信息
	 * @param prpLbankId ：传入的银行代号
	 */
	public void delete(PrpLbankId prpLbankId) throws Exception;

	/**
	 * 更新银行信息
	 * @param prpLbank :传入需要更新的银行
	 */
	public void updatePrpLbank(PrpLbank prpLbank) throws Exception;

	/**
	 * 根据银行编号查询出银行信息
	 * @param prpLbankId ：传入的银行编号
	 * @return 返回银行
	 */
	public PrpLbank findPrpLbank(String bankCode , String upperBankCode) throws Exception;
	/**
	 * 根据银行编号查询出银行信息
	 * @param bankCode ：传入的银行编号
	 * @return 返回银行
	 */
	public PrpLbank findPrpLbank(String bankCode ) throws Exception;

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的银行页面信息
	 */
	public Page findPrpLbank(QueryRule queryRule, int pageNo, int pageSize) throws Exception;

	/**根据查询对象获取银行信息  的集合
	 * @param queryRule
	 * @return
	 * @throws Exception
	 */
	public List<PrpLbank> findPrpLbank(QueryRule queryRule) throws Exception;
	/**
	 * @param bankCode
	 * @param bankName
	 * @return
	 * @throws Exception
	 * 更具名称和编码查询银行
	 */
	public List<PrpLbank> findBank(String bankCode,String bankName,String bankLevel,String upperBankCode,String upperBankName, int pageNo, int pageSize) throws Exception ;
	/**
	 * @param bankCode
	 * @param bankName
	 * @return
	 * @throws Exception
	 * 更具名称和编码查询银行的条数，精确查询
	 */
	public long getCount(String bankCode,String bankName,String bankLevel)throws Exception;
	/**
	 * @param bankCode
	 * @param bankName
	 * @return
	 * @throws Exception
	 * 更具名称和编码查询银行的条数,模糊查询
	 */
	public long findCount(String bankCode,String bankName,String bankLevel,String upperBankCode,String upperBankName)throws Exception;
	
	/***
	 * 分頁查詢抵押權人資料
	 * @param statements
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page findPrpLbank(String statements, Object[] params, int pageNo, int pageSize);
	/***
	 * 刪除或恢復
	 * @param bankCode
	 * @param validstatus
	 * @throws Exception
	 */
	public void updatePrpLbank(String bankCode , String validstatus) throws Exception ;
}
