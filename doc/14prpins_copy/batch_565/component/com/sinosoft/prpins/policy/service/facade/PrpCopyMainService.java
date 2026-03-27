package com.sinosoft.prpins.policy.service.facade;

import java.util.List;
import ins.framework.common.Page;

import com.sinosoft.common.schema.model.PrpCPmain;
//mantis：EGN0105，處理人員：DP0713，需求單編號：新增批單共保會簽頁及共保收據保費顯示調整(含責任險) 
import com.sinosoft.common.schema.model.PrpCopyCoinsDetail;
import com.sinosoft.common.schema.model.PrpCopyMain;
import com.sinosoft.common.util.IConstants;
/**
 * 備份主服務
 * @author Sinosoft
 */
public interface PrpCopyMainService {
	
	/**
	 * 查詢備份表數據
	 * @param applyNo 申請號
	 * @return PrpCPmain PrpCPmain對象
	 */
	public PrpCPmain getPrpCopyMainByApplyNo1(String applyNo);
	/**
	 * 查詢備份表數據
	 * @param applyNo 申請號
	 * @return PrpCPmain PrpCPmain對象
	 */
	public PrpCopyMain getPrpCopyMainByApplyNo(String applyNo);
	/**
	 * 刪除PrpCopyMain信息
	 * @param applyNo 批單申請號
	 */
	public void deletePrpCopyMainByApplyNo(String applyNo);
	/**
	 * 查詢PrpCPmain對象集合
	 * @param conditions 條件
	 * @return List PrpCPmain對象集合
	 */
	public List<PrpCPmain> getDataByConditions1(String conditions);
	/**
	 * 查詢PrpCopyMain對象集合
	 * @param conditions 條件
	 * @return List PrpCopyMain對象集合
	 */
	public List<PrpCopyMain> getDataByConditions(String conditions);
	/**
	 * 查詢PrpCopyMain對象集合
	 * @param policyNo 保單號
	 * @return List PrpCopyMain對象集合
	 */
	public List<PrpCopyMain> getPrpCopyMainByPolicyNo(String policyNo);
	
	/**
	 * 查詢通知船名批改的PrpCopyMain對象集合
	 * @param policyNo 保單號
	 * @return List PrpCopyMain對象集合
	 */
	public List<PrpCopyMain> getGPrpCopyMainByPolicyNo(String policyNo,String endorType);
	
	/**
	 * 查詢PrpCPmain對象
	 * @param applyNo 申請號
	 * @param policyNo 保單號
	 * @return PrpCPmain PrpCPmain對象
	 */
	public PrpCPmain getPreviousPrpCopyMain1(String applyNo, String policyNo);
	/**
	 * 查詢PrpCopyMain對象
	 * @param applyNo 申請號
	 * @param policyNo 保單號
	 * @return PrpCopyMain PrpCopyMain對象
	 */
	public PrpCopyMain getPreviousPrpCopyMain(String applyNo, String policyNo);
	/**
	 * 查詢PrpCopyMain對象,查詢原始保單數據
	 * @param policyNo 保單號
	 * @return PrpCopyMain PrpCopyMain對象
	 */
	public PrpCopyMain getFirstPrpCopyMain(String policyNo);
	/**
	 * 查詢PrpCopyMain對象
	 * @param certiNo 單證號
	 * @param endorseTimes 批改次數
	 * @return PrpCopyMain PrpCopyMain對象
	 */
	public PrpCopyMain getDataByPolicyNoAndEndorseTimes(String certiNo, Integer endorseTimes);
	/**
	 * 查詢PrpCopyMain對象
	 * @param policyNo 保單號
	 * @return PrpCopyMain PrpCopyMain對象
	 */
	public PrpCopyMain getDataByPolicyNoForCopyMain(String policyNo) ;
	/**
	 * 查詢PrpCopyMain對象
	 * @param applyNo 申請號
	 * @return PrpCopyMain PrpCopyMain對象
	 */
	public PrpCopyMain getDataByPolicyNoForCopyMainP(String applyNo);
	/**
	 * 更新PrpCopyMain對象
	 * @param prpCopyMain PrpCopyMain對象
	 */
	 public void updatePrpCopyMain(PrpCopyMain prpCopyMain);

	/**
	 * 查詢最大序列號
	 * @param applyNo 申請號
	 * @return Integer 最大序列號
	 * @throws Exception
	 */
	public Integer getMaxEngageSerial(String applyNo) throws Exception;
	//add by bh054 mantis5719:新增原始保單列印查詢 20171106 start
	/**
	 * 查詢PrpCopyMain對象集合
	 * @param policyNo 保單號
	 * @param endorseTimes 屬性批改次數
	 * @return List PrpCopyMain對象集合
	 */
	public Page getPrpCopyMainByPolicyNoAndEndorseTimes(String policyNo,String policyNoSign, String userCode,String riskcode, int ipageNo, int ipageSize);
	//add by bh054 mantis5719:新增原始保單列印查詢 20171106 end
	
	/**
	 * mantis：EGN0105，處理人員：DP0713，需求單編號：新增批單共保會簽頁及共保收據保費顯示調整(含責任險) 
	 * 查詢PrpCopyCoinsDetail對象集合
	 * @param policyNo
	 * @param endorseNo
	 * @return
	 */
	public List<PrpCopyCoinsDetail> getDataByPolicyNoAndEndorseNoForCopyCoinsDetail(String policyNo,String endorseNo);
}
