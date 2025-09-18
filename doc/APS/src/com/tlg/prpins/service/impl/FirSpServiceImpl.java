package com.tlg.prpins.service.impl;

import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirSpDao;
import com.tlg.prpins.service.FirSpService;
import com.tlg.util.Constants;
import com.tlg.util.Message;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirSpServiceImpl implements FirSpService{

	private FirSpDao firSpDao;

	@Override
	public int runSpRptCtbcCtf(Map<String, Object> params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.PARAMETER_ERROR));
		}
		
		firSpDao.runSpRptCtbcCtf(params);
		
		int returnValue = 0;
		if(params.containsKey("outResult")) {
			returnValue = (Integer)params.get("outResult");
		}
		
		return returnValue;
	}
	
	@Override
	public String runSpFirGetBatchno(Map<String, Object> params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.PARAMETER_ERROR));
		}
		
		firSpDao.runSpFirGetBatchno(params);
		
		String returnValue = "";
		if(params.containsKey("outBatchno")) {
			returnValue = (String)params.get("outBatchno");
		}
		
		return returnValue;
	}
	
	@Override
	public int runSpFirCtbcR7(Map<String, Object> params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.PARAMETER_ERROR));
		}
		
		firSpDao.runSpFirCtbcR7(params);
		
		int returnValue = 0;
		if(params.containsKey("outResult")) {
			returnValue = (Integer)params.get("outResult");
		}
		
		return returnValue;
	}

	/* mantis：FIR0265，處理人員：BJ085，需求單編號：FIR0265 板信受理檔產生排程 start */
	@Override
	public int runSpFirAgtBop01(Map<String, Object> params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.PARAMETER_ERROR));
		}
		firSpDao.runSpFirAgtBop01(params);
		int returnValue = 0;
		if(params.containsKey("outResult")) {
			returnValue = (Integer)params.get("outResult");
		}
		return returnValue;
	}
	/* mantis：FIR0265，處理人員：BJ085，需求單編號：FIR0265 板信受理檔產生排程 end */

	/* mantis：FIR0271，處理人員：BJ085，需求單編號：FIR0271 板信保單檔產生作業-排程作業 start */
	@Override
	public int runSpFirAgtBop02(Map<String, Object> params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.PARAMETER_ERROR));
		}
		firSpDao.runSpFirAgtBop02(params);
		int returnValue = 0;
		if(params.containsKey("outResult")) {
			returnValue = (Integer)params.get("outResult");
		}
		return returnValue;
	}
	/* mantis：FIR0271，處理人員：BJ085，需求單編號：FIR0271 板信保單檔產生作業-排程作業 end */

	/* mantis：FIR0494，處理人員：CC009，需求單編號：FIR0494_板信回饋檔產生排程規格_新版 START */
	@Override
	public int runSpFirAgtBop03(Map<String, Object> params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.PARAMETER_ERROR));
		}
		firSpDao.runSpFirAgtBop03(params);
		int returnValue = 0;
		if(params.containsKey("outResult")) {
			returnValue = (Integer)params.get("outResult");
		}
		return returnValue;
	}
	
	@Override
	public int runSpFirAgtBop04(Map<String, Object> params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.PARAMETER_ERROR));
		}
		firSpDao.runSpFirAgtBop04(params);
		int returnValue = 0;
		if(params.containsKey("outResult")) {
			returnValue = (Integer)params.get("outResult");
		}
		return returnValue;
	}
	/* mantis：FIR0494，處理人員：CC009，需求單編號：FIR0494_板信回饋檔產生排程規格_新版 END */

	/**
	 * mantis：FIR0680，處理人員：DP0714，住火_元大回饋檔產生排程規格 
	 */
	@Override
	public int runSpFirAgtYcb02(Map<String, Object> params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.PARAMETER_ERROR));
		}
		firSpDao.runSpFirAgtYcb02(params);
		int returnValue = 0;
		if(params.containsKey("outResult")) {
			returnValue = (Integer)params.get("outResult");
		}
		return returnValue;
	}

	/* mantis：FIR0314，處理人員：BJ085，需求單編號：FIR0314 台銀保經-APS新件要保檔產生排程 start */
	@Override
	public int runSpFirAgtBotAp(Map<String, Object> params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.PARAMETER_ERROR));
		}
		firSpDao.runSpFirAgtBotAp(params);
		int returnValue = 0;
		if(params.containsKey("outResult")) {
			returnValue = (Integer)params.get("outResult");
		}
		return returnValue;
	}
	/* mantis：FIR0314，處理人員：BJ085，需求單編號：FIR0314 台銀保經-APS新件要保檔產生排程 end */
	
	/* mantis：FIR0315，處理人員：BJ085，需求單編號：FIR0315 台銀保經-APS保單檔產生排程 start */
	@Override
	public int runSpFirAgtBotFh(Map<String, Object> params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.PARAMETER_ERROR));
		}
		firSpDao.runSpFirAgtBotFh(params);
		int returnValue = 0;
		if(params.containsKey("outResult")) {
			returnValue = (Integer)params.get("outResult");
		}
		return returnValue;
	}
	/* mantis：FIR0315，處理人員：BJ085，需求單編號：FIR0315 台銀保經-APS保單檔產生排程 end */
	
	/* mantis：FIR0436，處理人員：BJ085，需求單編號：FIR0436 住火-APS保單通訊資料產生及下載作業批次作業 start */
	@Override
	public int runSpPins01GenCdata(Map<String, Object> params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.PARAMETER_ERROR));
		}
		firSpDao.runSpPins01GenCdata(params);
		int returnValue = 0;
		if(params.containsKey("outResult")) {
			returnValue = (Integer)params.get("outResult");
		}
		return returnValue;
	}
	/* mantis：FIR0436，處理人員：BJ085，需求單編號：FIR0436 住火-APS保單通訊資料產生及下載作業批次作業 end*/
	
	/* mantis：FIR0546，處理人員：BJ085，需求單編號：FIR0546 火險_聯邦新件_APS回饋檔產生排程 start */
	@Override
	public int runSpFirAgtUb02(Map<String, Object> params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.PARAMETER_ERROR));
		}
		firSpDao.runSpFirAgtUb02(params);
		int returnValue = 0;
		if(params.containsKey("outResult")) {
			returnValue = (Integer)params.get("outResult");
		}
		return returnValue;
	}
	/* mantis：FIR0546，處理人員：BJ085，需求單編號：FIR0546 火險_聯邦新件_APS回饋檔產生排程 end */
	
	/*mantis：FIR0570，處理人員：BJ085，需求單編號：FIR0570 住火_APS每月應續件產生排程 start*/
	@Override
	public int runSpFirRenewList(Map<String, Object> params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.PARAMETER_ERROR));
		}
		firSpDao.runSpFirRenewList(params);
		int returnValue = 0;
		if(params.containsKey("outResult")) {
			returnValue = (Integer)params.get("outResult");
		}
		return returnValue;
	}
	
	@Override
	public int runSpFirRenewListUpdate(Map<String, Object> params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.PARAMETER_ERROR));
		}
		firSpDao.runSpFirRenewListUpdate(params);
		int returnValue = 0;
		if(params.containsKey("outResult")) {
			returnValue = (Integer)params.get("outResult");
		}
		return returnValue;
	}
	/*mantis：FIR0570，處理人員：BJ085，需求單編號：FIR0570 住火_APS每月應續件產生排程 end*/
	
	/* mantis：FIR0579，處理人員：BJ085，需求單編號：FIR0579 保發中心-住火保批資料產生排程規格 start*/
	@Override
	public int runSpFirBatchTiiP(Map<String, Object> params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.PARAMETER_ERROR));
		}
		firSpDao.runSpFirBatchTiiP(params);
		int returnValue = 0;
		if(params.containsKey("outResult")) {
			returnValue = (Integer)params.get("outResult");
		}
		return returnValue;
	}

	@Override
	public int runSpFirBatchTiiE(Map<String, Object> params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.PARAMETER_ERROR));
		}
		firSpDao.runSpFirBatchTiiE(params);
		int returnValue = 0;
		if(params.containsKey("outResult")) {
			returnValue = (Integer)params.get("outResult");
		}
		return returnValue;
	}

	@Override
	public int runSpFirBatchTiiD(Map<String, Object> params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.PARAMETER_ERROR));
		}
		firSpDao.runSpFirBatchTiiD(params);
		int returnValue = 0;
		if(params.containsKey("outResult")) {
			returnValue = (Integer)params.get("outResult");
		}
		return returnValue;
	}
	/* mantis：FIR0579，處理人員：BJ085，需求單編號：FIR0579 保發中心-住火保批資料產生排程規格 end*/
	
	/** mantis：FIR0620，處理人員：CD094，需求單編號：FIR0620 住火-台銀續保作業  **/
	@Override
	public int runSpFirAgtrnBot(Map<String, Object> params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.PARAMETER_ERROR));
		}
		firSpDao.runSpFirAgtrnBot(params);
		int returnValue = 0;
		if(params.containsKey("outResult")) {
			returnValue = (Integer)params.get("outResult");
		}
		return returnValue;
	}
	/** mantis：FIR0620，處理人員：CD094，需求單編號：FIR0620 住火-台銀續保作業  **/
	@Override
	public void runSpProcFpolicyInterinfo(Map<String, Object> params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.PARAMETER_ERROR));
		}
		firSpDao.runSpProcFpolicyInterinfo(params);
	}
	
	/** mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業 */
	@Override
	public int runSpFirAgtrnBotTemp(Map<String, Object> params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.PARAMETER_ERROR));
		}
		firSpDao.runSpFirAgtrnBotTemp(params);
		int returnValue = 0;
		if(params.containsKey("outResult")) {
			returnValue = (Integer)params.get("outResult");
		}
		return returnValue;
	}
	
	/**
	 * mantis：FIR0667，處理人員：DP0706，需求單編號：FIR0667 住火_元大續保作業_續件資料產生排程
	 */
	@Override
	public int runSpFirAgtrnYcb(Map<String, Object> params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.PARAMETER_ERROR));
		}
		firSpDao.runSpFirAgtrnYcb(params);
		int returnValue = 0;
		if(params.containsKey("outResult")) {
			returnValue = (Integer)params.get("outResult");
		}
		return returnValue;
	}
	
	public FirSpDao getFirSpDao() {
		return firSpDao;
	}

	public void setFirSpDao(FirSpDao firSpDao) {
		this.firSpDao = firSpDao;
	}
	
	/**
	 * FIR0690、FIR0693	 CF048住火	住火_APS_元大續保作業Ph2_N+1_產生保單副本&N+1產生出單明細檔 
	 */
	@Override
	public int runSpFirAgtYcbGenFile(Map<String, Object> params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.PARAMETER_ERROR));
		}
		firSpDao.runSpFirAgtYcbGenFile(params);
		int returnValue = 0;
		if(params.containsKey("outResult")) {
			returnValue = (Integer)params.get("outResult");
		}
		return returnValue;
	}
}
