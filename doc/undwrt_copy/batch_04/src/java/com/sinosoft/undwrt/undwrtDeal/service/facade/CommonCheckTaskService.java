package com.sinosoft.undwrt.undwrtDeal.service.facade;

import java.util.List;
import java.util.Map;//mantis： LIA0348，處理人員：DP0706，需求單編號：LIA0348_稽核議題處理

import com.sinosoft.common.schema.model.PrpTmain;
import com.sinosoft.undwrt.undwrtDeal.vo.PolicyAbstractInfoVo;
import com.sinosoft.undwrt.undwrtDeal.vo.ZHInfoVo;

// TODO: Auto-generated Javadoc
/**
 * 核保服務接口類.
 */
public interface CommonCheckTaskService {

	/**
	 * 獲取保書簡要訊息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @return 保書信息類
	 * @throws Exception
	 *             異常
	 */
	public PolicyAbstractInfoVo getPolicyAbstractInfo(String businessNo)
			throws Exception;

	/**
	 * 獲取立案號.
	 * 
	 * @param busiNo
	 *            業務號
	 * @param busiType
	 *            業務類型
	 * @return 立案號
	 * @throws Exception
	 *             異常
	 */
	public String getClaimNo(String busiNo, String busiType) throws Exception;

	/**
	 * 獲取報案號.
	 * 
	 * @param claimNo
	 *            立案號
	 * @return 報案號
	 * @throws Exception
	 *             異常
	 */
	public String getRegistNo(String claimNo) throws Exception;
	
	public List<ZHInfoVo> getZHInfoVolist (String businessType, String businessNo) throws Exception;
	
	//mantis： CAR0051 ，處理人員： David ，需求單編號： CAR0051 start  原因  業務人員失效減核問題-核保系統檢核
	public boolean checkValidsTatus(PrpTmain prpTmain);
	
	public boolean checkPrpduser(String userCode) ;
	//mantis： CAR0051 ，處理人員： David ，需求單編號： CAR0051   end
	
	/**
	 * mantis： LIA0348，處理人員：DP0706，需求單編號：LIA0348_稽核議題處理
	 * 查詢AR險館藏品保險期間/參展品保險期間/運送品保險期間
	 */
	public Map<String, Object> queryARStartDate(String businessNo);
}
