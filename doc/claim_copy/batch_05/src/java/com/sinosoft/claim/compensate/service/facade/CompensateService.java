package com.sinosoft.claim.compensate.service.facade;

import ins.framework.common.Page;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sinosoft.claim.compensate.vo.CompensateDto;
import com.sinosoft.claim.compensate.vo.CompensateFeeDto;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLcompelMedical;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLloss;
import com.sinosoft.claim.schema.model.PrpLpersonLoss;
import com.sinosoft.claim.schema.model.PrpLverifyLoss;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.workflow.vo.JbpmDto;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;

/**
 * 
 * @Description 车险理算实赔业务处理接口
 * @author 中科软
 */
public interface CompensateService {

	/**
	 * 保存实赔
	 * @param CompensateDto：实赔对象DTO
	 * @throws Exception
	 */
	public void save(CompensateDto compensateDto) throws Exception;
	/**
	 * 保存实赔带工作流
	 * @param CompensateDto：实赔对象DTO
	 * @throws Exception
	 */
	public void save(Boolean isSumbitUndwrt,CompensateDto compensateDto,WorkFlowDto workFlowDto) throws Exception;
	/**
	 * 删除实赔
	 * @param compensateNo：实赔号
	 * @throws Exception
	 */
	public void delete(String compensateNo) throws Exception;
	/**
	 * 获得实赔信息
	 * @param  compensateNo：实赔号
	 * @return 实赔对象
	 * @throws Exception
	 */
	public CompensateDto findByPrimaryKey(String compensateNo) throws Exception;
	/**
	 * 获得实赔信息
	 * @param  compensateNo：实赔号
	 * @param  caseType 特殊赔案标志
	 * @return 实赔对象
	 * @throws Exception 
	 */
	public CompensateDto findByPrimaryKey(String compensateNo,String caseType) throws Exception;
	/**
	 * 判断实赔号是否存在
	 * @param compensateNo:实赔号
	 * @return 是/否
	 * @throws Exception
	 */
	public boolean isExist(String compensateNo) throws Exception;
	/**
	 * 获得实赔信息
	 * @param  conditions：查询条件
	 * @return 实赔对象
	 * @throws Exception
	 */
	
	public List<PrpLcompensate> findByConditions(String conditions) throws Exception;
	/** 
	 * 获得实赔信息
	 * @param  conditions：查询条件 - 从PrpLcompensate查询
	 * @return 实赔对象
	 * @throws Exception
	 */ 
	
	public Page findByConditions(String conditions,int pageNo,int pageSize) throws Exception;
	/**
	 * 获得实赔标的信息
	 * @param  conditions：查询条件
	 * @return 列表
	 * @throws Exception
	 */
	
	public List<PrpLloss> findLossByConditions(String conditions) throws Exception;
	/**
	 * 获得实赔人员信息
	 * @param  conditions：查询条件
	 * @return 列表
	 * @throws Exception
	 */
	
	public List<PrpLpersonLoss> findPersonLossByConditions(String conditions) throws Exception;
	/**
	 * 获得查勘查询信息
	 * @param  conditions：查询条件
	 */
	
	public List<PrpLcompensate> findByQueryConditions(String conditions) throws Exception;
	/**
	 * 获得实赔查询信息
	 * @param  conditions：查询条件 - 联表查
	 * @return 实赔列表
	 * @throws Exception
	 */
	
	public List<PrpLcompensate> findByApproveConditions(String conditions) throws Exception;
	/**
	 * 复核实赔
     * @param compensateNo：赔款计算书号码
     * @param userCode：复核员代码
     * @param underWriteFlag：核赔标志
	 * @throws Exception
	 */
	public void approve(String compensateNo,String userCode,String underWriteFlag) throws Exception;
	/**
	 * 查询特别约定,赔偿限额/免赔额信息
	 * @param CompensateDto：实赔对象DTO
	 * @throws Exception 
	 */
	public CompensateDto findByAppendInformation(CompensateDto compensateDto) throws Exception;
	/**
	 * 根据赔案号得到已决赔款
	 * @param claimNo 赔案号
	 * @author 中科软 
	 * @return 
	 * @throws Exception
	 */
	public CompensateFeeDto findCompensateFeeByClaimNo(String claimNo) throws Exception;
	/**
	 * 计算书退回定损
	 * @param CompensateDto：实赔对象DTO
	 * @throws Exception
	 */
	public void backToCerta(String claimNo,PrpLverifyLoss prpLverifyLoss,WorkFlowDto workFlowDto) throws Exception;
	/**
	 * 按条件查询多条数据
	 * @Description: prplcompensate表,prplregist表和prplclaimstatus表中查询多条数据
	 * @author 中科软
	 * @date Feb 26, 2013 3:19:59 PM
	 * @param conditions
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page findPageByConditions(String conditions,int pageNo,int pageSize);
	/**
	 * @Description: 判断理算是否可以注销
	 * @author 中科软
	 * @date Feb 25, 2013 6:34:43 PM
	 * @param businessNo
	 * @param conditions
	 * @return
	 * @throws Exception
	 */
	public boolean isRejectByConditions(String businessNo,String conditions) throws Exception;
	
	/**
	 * 判断理算任务是否可以提交
	 * @author 中科软
	 * @param businessNo
	 * @return
	 * @throws Exception
	 */
	public String getCompFlagByConditions(String businessNo) throws Exception ;
	/***
	 * 实赔删除,删除一个案件的所有计算书
	 * @Description: 实赔删除
	 * @author 中科软
	 * @date Feb 26, 2013 3:49:54 PM
	 * @param claimNo
	 * @throws Exception
	 */
	public void deleteByClaimNo(String claimNo) throws Exception;

	/**
	 * 保存实赔带工作流
	 * 带jbpm工作流
	 */
	public void saveBpm(JbpmDto jbpmDto,boolean isSumbitUndwrt, CompensateDto compensateDto, WorkFlowDto workFlowDto, UserDto user) throws Exception;
	/***
	 * 重开赔案，理算任务保存
	 * @param jbpmDto
	 * @param isSumbitUndwrt
	 * @param compensateDto
	 * @param workFlowDto
	 * @param user
	 * @throws Exception
	 */
	public void saveReCaseBpm(JbpmDto jbpmDto,boolean isSumbitUndwrt, CompensateDto compensateDto, WorkFlowDto workFlowDto, UserDto user) throws Exception;
	/**
	 * 保存实赔带工作流
	 * 带jbpm工作流
	 * 退回到单证节点
	 */
	public void saveBpmCerti(JbpmDto jbpmDto,WorkFlowDto workFlowDto) throws Exception;
	/**
	 * 保存实赔带工作流
	 * 带jbpm工作流
	 * 退回到单证节点
	 */
	public void saveBpmCerta(JbpmDto jbpmDto,String claimNo, WorkFlowDto workFlowDto, PrpLverifyLoss prpLverifyLoss) throws Exception;
	 /**
	  * 查询用户是否有差额赔付权限,没有返回false
     * @param userCode
     * @return
     * @throws Exception
     */
    public String findExceedingPayout(UserDto userDto)throws Exception;
    
    /**
     * 根据立案号获取本案已审核通过的计算书的险别赔付信息
     * @param claimNo 立案号 （任意险）
     * @return Map<String,Double> key：险别；value：赔付金额
     */
    public Map<String,Double> getPastCompePayAmount(String claimNo)throws Exception;
    /**
     * 更具立案号查询是否关联报案，关联报案。另外的案件是否也需要出计算书
     * @param claimNo
     * @return
     * @throws Exception
     */
    public String getRelatedCompe(String claimNo) throws Exception;
    /**
     * 获取赔案已赔付的险别，并組織追償訊息
     * @param claimNo 赔案号码
     * @return
     * @throws Exception
     */
    public List<PrpLloss> getPrpLlossForReplevy(String claimNo) throws Exception;
    
    /***
     * 根據立案號碼查詢該立案的各險別追償上限訊息
     * @param claimNo
     * @return
     * @throws Exception
     */
    public List<PrpLloss> getReplevyInfoByClaim(String claimNo) throws Exception;
    
    /***
     * 根据立案号码获取该案件已核赔通过可互冲的计算书。
     * @param claimNo
     * @return
     * @throws Exception
     */
    public List<String> getMutualCompensateNo(String claimNo) throws Exception;
    /***
     * 获取计算书赔付的险种
     * @param compensateNo
     * @return
     */
    public List<String> getPayRiskCode(String compensateNo);
    
    /***
     * 理算撤銷簡易賠案
     * @param prpLclaim 立案
     * @param currSwfLog 未處理的理算節點工作流
     * @throws Exception 
     */
	public void saveCancelSimpleCase(PrpLclaim prpLclaim, SwfLog currSwfLog) throws Exception;
	
	/***
	 * 獲取保單各險別已賠付，已追償
	 * 已賠付key ：C 
	 * 已追償償key ：R 
	 * 賠付追償匯總 ：CLAIM
	 * @param claimNo 賠案號碼
	 * @return
	 */
	public Map<String , Map<String, Double> > getClaimKindCodePay(String claimNo) throws Exception;
	
	/***
	 * 查找強制險計算書受害人的強制險醫療給付費用收據資料
	 * @param compensateNo 強制險計算書號碼
	 * @param identifyNumber 受害人身分證號碼
	 * @return
	 */
	public List<PrpLcompelMedical> findPrpLcompelMedical(String compensateNo, String identifyNumber) throws Exception;

	/***
	 * 查找本計算書 強制險醫療給付費用收據資料
	 * @param compensateNo
	 * @return
	 * @throws Exception
	 */
	public List<PrpLcompelMedical> findPrpLcompelMedical(String compensateNo) throws Exception;
	
	/*
	 * mantis： CLM0046 ，處理人員：BK007 蘇哲，需求單編號：CLM0046強制險醫療明細提醒同一身分證號與日期重複時有提醒與之前重複的賠次
	 * 處理過程：
	 *  PrpLcompensateService 新增 verifyPrpLcompelMedical 方法，提供 verifyMedicalDetail Ajax 使用，驗證同一身分證號與日期重複資訊
	 *  DAACompelPrpLcompelMedical.js 提供前台驗證，有輸入時提醒與送出驗證及提醒
	 *  ClaimPrintAction 列印功能新增備註頁面(prpLcompelMedicalComment.jrxml)及調整 項目渲染時的提示 (prpLcompelMedical_subreport0.jrxml)
	 */
	/***
	 * 驗證是否有重複日期
	 * @param compensateNo
	 * @return
	 * @throws Exception
	 */
	public String verifyPrpLcompelMedical(String identifyNumber, String compensateNo,Integer serialNo, Date startDate) throws Exception;
	public String verifyPrpLcompelMedical(PrpLcompelMedical compelMedical) throws Exception;
	/*
	 * mantis： CLM0046 ，處理人員：BK007 蘇哲，需求單編號：CLM0046強制險醫療明細提醒同一身分證號與日期重複時有提醒與之前重複的賠次 -end
	 */
	
	/***
	 * 保存強制險受害人醫療給付費用收據資料
	 * @param compensateNo 強制險計算書號碼
	 * @param identifyNumber 受害人身分證號碼
	 * @param prpLcompelMedicalList 最新收據資料
	 * @return
	 */
	public void savePrpLcompelMedical(String compensateNo, String identifyNumber, List<PrpLcompelMedical> prpLcompelMedicalList) throws Exception;

	/***
	 * 刪除指定受害人醫療給付費用收據資料
	 * @param compensateNo 強制險計算書號碼
	 * @param identifyNumber 受害人身分證號碼
	 * @return
	 */
	public void deletePrpLcompelMedical(String compensateNo, String identifyNumber) throws Exception;
	
	/***
	 * 分頁查找強制險受害人醫療費用資料
	 * @param statements 查詢SQL
	 * @param param 參數
	 * @param pageNo 
	 * @param pageSize
	 * @return
	 */
	public Page findPrpLcompelMedical(String statements , Object[] param ,int pageNo,int pageSize);
	/**
	 * 獲得上次簽結的強制險受害人醫療費用資料
	 * @param claimNo
	 * @return
	 * @throws Exception
	 */
	public List<PrpLcompelMedical> findLastPrpLcompelMedical(String claimNo,String identifyNumber,String compensateNo) throws Exception;

    //mantis：CLM0072 ，處理人員：BK007 蘇哲，需求單編號：CLM0072.工程險追償理算書 start
    /**
     * 計算書(理算說明/追償說明)
     * @param compensateNo
     * @return
     */
    public String getContextByCompensateNo(String compensateNo);
    
    /**
     * 賠案累計賠款金額
     * 包含已確的所有賠款金額 (理賠+追償+殘餘物)
     * @param claimNo
     * @return
     */
    public BigDecimal getClaimSumPaidByClaimNo(String claimNo);

    /**
     * 賠案累計賠款費用
     * 包含已確的所有賠款金額 (理賠+追償+殘餘物)
     * @param claimNo
     * @return
     */
    public BigDecimal getClaimSumFeeByClaimNo(String claimNo);
    //mantis：CLM0072 ，處理人員：BK007 蘇哲，需求單編號：CLM0072.工程險追償理算書 end
    
}
