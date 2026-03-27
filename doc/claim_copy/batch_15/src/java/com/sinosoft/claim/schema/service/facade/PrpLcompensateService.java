package com.sinosoft.claim.schema.service.facade;
/**
 * 赔款计算书信息接口
 * @author 中科软
 */

import java.util.List;
import java.util.Map;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.compensate.vo.CompensateFeeDto;
import com.sinosoft.claim.schema.model.PrpCitemKind;
import com.sinosoft.claim.schema.model.PrpLcompensate;

public interface PrpLcompensateService {
	
	/**
	 * 赔款计算书信息
	 * @param PrpLcompensate ：传入的赔款计算书信息
	 */
	public void save(PrpLcompensate prpLcompensate) throws Exception;
	
	/**
	 * 保存赔款计算书信息
	 * @param list  :传入的赔款计算书信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLcompensate> list) throws Exception;
 	/**
	 * @param prpLcompensate
	 * @throws Exception
	 * 保存或者修改，如果对象在数据库中不存在就保存对象，如果存在就更新对象
	 */
	public void saveOrUpdate(PrpLcompensate prpLcompensate) throws Exception;
	
	public void saveOrUpdate(List<PrpLcompensate> prpLcompensateList) throws Exception;
	
	/**
	 * 删除赔款计算书信息
	 * @param policyNo ：传入的赔款计算书信息
	 */
	public void delete(String compensateNo) throws Exception;

	/**
	 * 更新赔款计算书信息信息
	 * @param PrpLcompensate :传入需要更新的赔款计算书信息
	 */
	public void update(PrpLcompensate prpLcompensate) throws Exception;
	public void update(List<PrpLcompensate> prpLcompensateList) throws Exception;
	

	/**
	 * 根据赔款计算书信息编号查询出保单赔款计算书信息
	 * @param policyNo ：传入的赔款计算书信息编号
	 * @return 返回赔款计算书信息
	 */
	public PrpLcompensate findPrpLcompensate(String compensateNo) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的赔款计算书信息页面信息
	 */
	public Page findPrpLcompensate(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取赔款计算书信息  的列表
	 * @param queryRule 查询对象
	 * @return 包含的赔款计算书信息  的列表
	 */
	public List<PrpLcompensate> findPrpLcompensate(QueryRule queryRule) throws Exception;
	/**
	 * @param claimNo
	 * @return
	 * @throws Exception
	 * 根立案号查询计算书
	 */
	public List<PrpLcompensate> findByClaimNo(String claimNo)throws Exception;
	/**
	 * @Description: 查询满足条件的数据行数
	 * @author 中科软
	 * @date Feb 23, 2013 2:03:15 AM
	 * @param conditions
	 * @return
	 */
	public long getCount(String conditions);
    /**
     * 复核实赔
     * @param compensateNo：赔款计算书号码
     * @param userCode：复核员代码
     * @param underWriteFlag：核赔标志
     * @throws Exception
     */
	public void approve(String compensateNo,String userCode,String underWriteFlag)throws Exception;
	/**
	 * 按条件从prplcompensate表,prplregist表和prplclaimstatus表中查询多条数据
	 * @Description: 
	 * @author 中科软
	 * @date Feb 25, 2013 4:44:09 PM
	 * @param conditions
	 * @param pageNo
	 * @param rowsPerPage
	 * @return
	 * @throws Exception
	 */
	public List<PrpLcompensate> findByQueryConditions(String conditions,int pageNo,int rowsPerPage) throws Exception;
	
    /**获取立案号
     * @param conditions
     * @return
     * @throws Exception
     */
    public String getClaimNoConditions(String conditions);
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param conditions 查询条件
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的赔款计算书信息
	 */
    public Page findByConditions(String conditions,int pageNo,int pageSize)throws Exception;
	/**
	 * 根据查询对象获取赔款计算书信息  的列表
	 * @param queryRule 查询对象
	 * @return 包含的赔款计算书信息  的列表
	 */
    public List<PrpLcompensate> findByConditions(String conditions)throws Exception;
    
    public Map<String,Double> getKindSumRealPayMap(String compensateNo) throws Exception;
    /**
     * 根据赔案号得到已决赔款
     * @param claimNo 赔案号
     * @author 中科软 
     * @return
     * @throws Exception
     */
    public CompensateFeeDto findCompensateFeeByClaimNo(String claimNo)throws Exception;
    
    /***
     * 获取历史（非本次）赔付
     * @param policyNo 保单号
     * @param claimNo 本次立案号
     * @return
     */
    public List<Map<String,Double>> getPastCasePay(String policyNo,String claimNo);
    /***
     * 获取本案人伤已赔付（已核赔通过的计算书）
     * @param claimNo
     * @return
     */
    public Map<String,Double> getPastPrpLpersonLossPay(String claimNo);
    /***
     * 获取本案车物损已赔付（已核赔通过的计算书）
     * @param claimNo
     * @return
     */
    public Map<String,Double> getPastPrpLlossPay(String claimNo);
    /***
     * 獲取追償的登錄計算書 尾號為00的那張
     * @param claimNo
     * @return
     */
    public PrpLcompensate getReplevyPrpLcompensate(String claimNo);
    /**
	 * 查询险别的损失
	 * @param compensateNo
	 * @param prpCitemKind
	 * @return
	 */
	public List<Double> findKindSumLoss(String compensateNo, PrpCitemKind prpCitemKind);
	
	/**
	 * 查詢核賠人員審核當月累積已核賠費用
	 * mantis：CLM0150，處理人員：DP0706，需求單編號：.新核心-車資費用人員階級管控
	 * @param uniformno
	 * @return
	 */
	public Double getSumPayAmountThisMonth(String uniformno);
	
	//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3
	/***
     * 获取本案车物损已赔付（已核赔通过的计算书）超額的部分
     * @param claimNo
     * @return
     */
	public Map<String, Double> getPastPrpLlossPayE(String claimNo);

	//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3
	/**
	 * 获取本案已赔付人伤(根据受害人身份证号区分每人) 超額的部分
	 * @param claimNo
	 * @return
	 */
	public Map<String, Double> getPastPrpLpersonLossPayE(String claimNo);
    
}
