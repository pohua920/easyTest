package com.tlg.prpins.dao;

import java.util.Map;

public interface FirSpDao {
	
	public void runSpRptCtbcCtf(Map<String,Object> params)throws Exception;
	
	public void runSpFirGetBatchno(Map<String,Object> params)throws Exception;

	public void runSpFirCtbcR7(Map<String,Object> params)throws Exception;

	//mantis：FIR0265，處理人員：BJ085，需求單編號：FIR0265 板信受理檔產生排程
	public void runSpFirAgtBop01(Map<String,Object> params)throws Exception;

	// mantis：FIR0271，處理人員：BJ085，需求單編號：FIR0271 板信保單檔產生作業-排程作業
	public void runSpFirAgtBop02(Map<String,Object> params)throws Exception;
	
	//mantis：FIR0494，處理人員：CC009，需求單編號：FIR0494_板信回饋檔產生排程規格_新版
	public void runSpFirAgtBop03(Map<String,Object> params)throws Exception;
	
	//mantis：FIR0494，處理人員：CC009，需求單編號：FIR0494_板信回饋檔產生排程規格_新版
	public void runSpFirAgtBop04(Map<String,Object> params)throws Exception;
	
	// mantis：FIR0314，處理人員：BJ085，需求單編號：FIR0314 台銀保經-APS新件要保檔產生排程
	public void runSpFirAgtBotAp(Map<String,Object> params)throws Exception;
	
	// mantis：FIR0315，處理人員：BJ085，需求單編號：FIR0315 台銀保經-APS保單檔產生排程 
	public void runSpFirAgtBotFh(Map<String,Object> params)throws Exception;
	
	// mantis：FIR0436，處理人員：BJ085，需求單編號：FIR0436 住火-APS保單通訊資料產生及下載作業批次作業 
	public void runSpPins01GenCdata(Map<String,Object> params)throws Exception;
	
	//mantis：FIR0546，處理人員：BJ085，需求單編號：FIR0546 火險_聯邦新件_APS回饋檔產生排程
	public void runSpFirAgtUb02(Map<String,Object> params)throws Exception;
	
	/*mantis：FIR0570，處理人員：BJ085，需求單編號：FIR0570 住火_APS每月應續件產生排程 start*/
	public void runSpFirRenewList(Map<String,Object> params)throws Exception;
	
	public void runSpFirRenewListUpdate(Map<String,Object> params)throws Exception;
	/*mantis：FIR0570，處理人員：BJ085，需求單編號：FIR0570 住火_APS每月應續件產生排程 end*/
	
	/* mantis：FIR0579，處理人員：BJ085，需求單編號：FIR0579 保發中心-住火保批資料產生排程規格 start*/
	public void runSpFirBatchTiiP(Map<String,Object> params)throws Exception;
	
	public void runSpFirBatchTiiE(Map<String,Object> params)throws Exception;
	
	public void runSpFirBatchTiiD(Map<String,Object> params)throws Exception;
	/* mantis：FIR0579，處理人員：BJ085，需求單編號：FIR0579 保發中心-住火保批資料產生排程規格 end*/
	
	/** mantis：FIR0620，處理人員：CD094，需求單編號：FIR0620 住火-台銀續保作業  **/
	public void runSpFirAgtrnBot(Map<String,Object> params)throws Exception;

	/** mantis：FIR0635，處理人員：CD094，需求單編號：FIR0635_住火_新核心保單轉入中介檔異常處理排程  **/
	public void runSpProcFpolicyInterinfo(Map<String,Object> params)throws Exception;
	
	//mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業
	public void runSpFirAgtrnBotTemp(Map<String,Object> params)throws Exception;
	
	/** mantis：FIR0667，處理人員：DP0706，需求單編號：FIR0667_住火_元大續保作業_AS400資料匯入  */
	public void runSpFirAgtrnYcb(Map<String,Object> params)throws Exception;

	// mantis：FIR0680，處理人員：DP0714，住火_元大回饋檔產生排程規格
	public void runSpFirAgtYcb02(Map<String, Object> params) throws Exception;
	
	//FIR0690、FIR0693	 CF048住火	住火_APS_元大續保作業Ph2_N+1_產生保單副本&N+1產生出單明細檔 
	public void runSpFirAgtYcbGenFile(Map<String, Object> params) throws Exception;
}