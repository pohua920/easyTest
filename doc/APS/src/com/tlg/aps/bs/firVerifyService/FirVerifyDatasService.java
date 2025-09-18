/**
 * mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業
 * 火險相關檢核Service
 * 包含欄位驗證、webService
 */
package com.tlg.aps.bs.firVerifyService;

import java.util.Date;
import java.util.Map;

import com.tlg.aps.vo.Aps016DetailVo;
import com.tlg.aps.vo.FirAddressCheckVo;
import com.tlg.aps.vo.FirAddressRuleObj;
import com.tlg.aps.vo.FirAmountWsParamVo;
import com.tlg.aps.vo.FirEqFundQueryVo;
import com.tlg.aps.vo.FirPremWsParamVo;
import com.tlg.aps.vo.FirVerifyVo;
import com.tlg.aps.vo.RuleReponseVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirPremcalcTmp;

public interface FirVerifyDatasService {
	
	public boolean checkIsExistInsured(String orderseq) throws SystemException, Exception;
	
	public Map<String,String> checkExtracomcode(String branchNo, String businessnature) throws SystemException, Exception;
	
	public String findPrpdNewCode(String codetype,String codecode) throws SystemException, Exception;
	
	public Map<String,String> verifyID(String id) throws Exception;
	
	public Map<String,String> findPrpdPropStructByParams(String wallno, String roofno, String startdate,int sumfloors) throws SystemException, Exception;

	public FirEqFundQueryVo queryDoubleIns(FirEqFundQueryVo firEqFundQueryVo) throws Exception;
	
	public FirVerifyVo queryDoubleInsVerify(FirEqFundQueryVo firEqFundQueryVo);
	
	public FirPremcalcTmp firAmountCal(FirAmountWsParamVo firWsParamVo) throws Exception;
	
	public FirAddressCheckVo checkAddress(FirAddressCheckVo firAddressCheckVo) throws Exception;
	
	public FirVerifyVo addressVerify(FirAddressCheckVo firAddressCheckVo);
	
	public FirPremcalcTmp firPremCal(FirPremWsParamVo firPremWsParamVo) throws Exception;
	
	public Map<String, String> findPrpyddagentByParams(String agentid, String businesssource) throws SystemException, Exception;
	
	public Map findFirAgtrnAs400Data(Map<String,String> params) throws SystemException, Exception;

	public RuleReponseVo firAddressRule(FirAddressRuleObj firAddressRuleObj) throws Exception;	
	
	//mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業
	/*mantis：FIR0657，處理人員：BJ085，需求單編號：FIR0657 住火_配合造價表調整重算外銀續保保額  start*/
	public Map findFirAgtrnCoreData(Aps016DetailVo aps016DetailVo, String userId) throws Exception;
	
	public String recalAmount(String amountF, Date startdate) throws SystemException, Exception;
	/*mantis：FIR0657，處理人員：BJ085，需求單編號：FIR0657 住火_配合造價表調整重算外銀續保保額  end*/
}

