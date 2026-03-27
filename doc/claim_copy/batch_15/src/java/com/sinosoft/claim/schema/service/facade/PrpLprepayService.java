package com.sinosoft.claim.schema.service.facade;
/**
 * 预赔登记接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLprepay;

public interface PrpLprepayService {
	
	/**
	 * 保存预赔登记信息
	 * @param prpLprepay ：传入的预赔登记
	 */
	public void save(PrpLprepay prpLprepay) throws Exception;
	
	/**
	 * 保存预赔登记信息
	 * @param list:保存预赔登记信息
	 */
	public void save(List<PrpLprepay> list) throws Exception;
	
	/**
	 * 删除预赔登记信息
	 * @param preCompensateNo ：传入的预赔登记编号
	 */
	public void delete(String preCompensateNo) throws Exception;

	/**
	 * 更新预赔登记信息
	 * @param prpLprepay :传入需要更新的预赔登记
	 */
	public void update(PrpLprepay prpLprepay) throws Exception;

	/**
	 * 根据预赔登记编号查询出预赔登记信息
	 * @param preCompensateNo ：传入的预赔登记编号
	 * @return 返回预赔登记
	 */
	public PrpLprepay findPrpLprepay(String preCompensateNo) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的预赔登记页面信息
	 */
	public Page findPrpLprepay(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取 预赔登记信息 的集合
	 * @param queryRule 查询对象
	 * @return 包含的  预赔登记信息的集合
	 */
	public List<PrpLprepay> findPrpLprepay(QueryRule queryRule) throws Exception;
	/***
	 * @Description: 查询满足条件的数据行数
	 * @author 中科软
	 * @date Feb 23, 2013 2:03:15 AM
	 * @param conditions
	 * @return
	 */
	public int getCount(String conditions);
	/**
     * 复核实赔
     * @param preCompensateNo：预赔号码
     * @param userCode：复核员代码
     * @param underWriteFlag：核赔标志
     * @throws Exception
     */
    public void approve(String prepayNo,String userCode,String underWriteFlag) throws Exception;
    /**
     * 按条件从prplprepay,prplclaimstatus表中查询多条数据
     * @author 中科软
     * @date Feb 28, 2013 3:46:43 PM
     * @param conditions
     * @param pageNo
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    public List<PrpLprepay> findByApproveQueryConditions(String conditions,int pageNo,int pageSize) throws Exception;
    /**
	 * @param claimNo
	 * @return
	 * @throws Exception
	 * 根据立案号查询预配信息
	 */
	public List<PrpLprepay> findByClaimNo(String claimNo)throws Exception;
}
