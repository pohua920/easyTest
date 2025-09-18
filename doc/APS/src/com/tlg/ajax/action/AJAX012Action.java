package com.tlg.ajax.action;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.tlg.aps.bs.firFubonRenewalService.FirFubonMaintainService;
import com.tlg.aps.bs.firVerifyService.FirVerifyDatasService;
import com.tlg.aps.vo.Aps034FbDetailVo;
import com.tlg.aps.vo.Aps034genFileVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.service.FirAgtrnBatchDtlService;
import com.tlg.prpins.service.FirAgtrnTmpFbService;
import com.tlg.util.BaseAction;
import com.tlg.util.JsonUtil;
import com.tlg.util.Result;

/** mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業 */
@SuppressWarnings("serial")
public class AJAX012Action extends BaseAction implements Serializable {
	private String result;
	private String batchNo;
	private int fixNrec;//鎖定筆數
	private int sfNrec;//預計剔退筆數
	private Aps034FbDetailVo fbDetailVo;
	private String checkType;
	
	private FirAgtrnBatchDtlService firAgtrnBatchDtlService;
	private FirAgtrnTmpFbService firAgtrnTmpFbService;
	private FirFubonMaintainService firFubonMaintainService;
	private FirVerifyDatasService firVerifyDatasService;
	
	//富邦續件產檔大保單、續保明細檔時，要先比對批次號核心出單筆數及鎖定比數-剔退比數是否相符
	public String compareDataCount() {
		try {
			Map<String,String> params = new HashMap<>();
			params.put("batchNo", batchNo);
			int prec = firAgtrnBatchDtlService.countCoreInsured(params);
			
			
			boolean compare = true;
			String bseq = "";
			Map<String,Object> map = new LinkedHashMap<>();
			int bprec = fixNrec - sfNrec;
			
			if(bprec != prec) {
				Result searchResult = firAgtrnBatchDtlService.findCoreNotInsuredData(params);
				compare = false;
				if(searchResult.getResObject()!=null) {
					Aps034genFileVo genFileVo = (Aps034genFileVo) searchResult.getResObject();
					bseq = genFileVo.getBseq();
				}
			}
			map.put("bseq", bseq);
			map.put("compare", compare);
			map.put("prec", prec);
			map.put("bprec", bprec);
			
			result = JsonUtil.getJSONString(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}
	
	//產生差異檔時，先查詢有無資料
	public String ckeckDiffData() {
		try {
			boolean haveData = true;
			
			Map<String,String> params = new HashMap<>();
			params.put("batchNo", batchNo);
			Result searchResult = firAgtrnTmpFbService.findFbDiffFile(params);

			if(searchResult.getResObject() == null) {
				haveData = false;
			}
			
			Map<String,Object> map = new LinkedHashMap<>();
			map.put("haveData", haveData);
			
			result = JsonUtil.getJSONString(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}
	
	//整批鎖定時，先查詢需鎖定之資料
	public String countFixData() throws SystemException, Exception {
		try {
			Map<String, String> params = new HashMap<>();
			params.put("batchNo", batchNo);
			params.put("fixUserNull", "Y");

			boolean haveFixData = true;
			int fixCount = firAgtrnBatchDtlService.countFirAgtrnBatchDtl(params);
			if (fixCount == 0) {
				haveFixData = false;
			}
			
			Map<String, Object> map = new LinkedHashMap<>();
			map.put("haveFixData", haveFixData);
			map.put("fixCount", fixCount);

			result = JsonUtil.getJSONString(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}
	
	public String verifyBasicData() throws Exception {
		try {
			Map<String, Object> resultMap = firFubonMaintainService.basicDataCheck(fbDetailVo, getUserInfo().getUserId(), checkType);
			result = JsonUtil.getJSONString(resultMap);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}
	
	public String checkStructureLeval(){
		Map<String,Object> map = new LinkedHashMap<>();
		String wallmaterial = fbDetailVo.getWallmaterial();
		String roofmaterial = fbDetailVo.getRoofmaterial();
		String startdate = fbDetailVo.getStartdate().replace("/", "");
		int floorNum = Integer.parseInt(fbDetailVo.getSumfloors());
		String structure = "";
			Map<String, String> structMap;
			try {
				structMap = firVerifyDatasService.findPrpdPropStructByParams(wallmaterial, roofmaterial, startdate, floorNum);
				if(!structMap.isEmpty()) {
					structure = structMap.get("structureno");
					map.put("structure", structure);
					//mantis：FIR0587，處理人員：DP0713，需求單編號：FIR0587，-APS富邦續保作業-修改作業建築等級說明調整
					//mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業
					map.put("structureText", fbDetailVo.getWallname()+fbDetailVo.getRoofname()+floorNum+"層樓"+(structMap.get("structureText")));
				}else {
					map.put("errMsg","查無建物等級，無法計算保費;");
				}
				result = JsonUtil.getJSONString(map);
			} catch (SystemException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return Action.SUCCESS;
	}
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public FirAgtrnBatchDtlService getFirAgtrnBatchDtlService() {
		return firAgtrnBatchDtlService;
	}

	public void setFirAgtrnBatchDtlService(FirAgtrnBatchDtlService firAgtrnBatchDtlService) {
		this.firAgtrnBatchDtlService = firAgtrnBatchDtlService;
	}

	public FirAgtrnTmpFbService getFirAgtrnTmpFbService() {
		return firAgtrnTmpFbService;
	}

	public void setFirAgtrnTmpFbService(FirAgtrnTmpFbService firAgtrnTmpFbService) {
		this.firAgtrnTmpFbService = firAgtrnTmpFbService;
	}

	public FirFubonMaintainService getFirFubonMaintainService() {
		return firFubonMaintainService;
	}

	public void setFirFubonMaintainService(FirFubonMaintainService firFubonMaintainService) {
		this.firFubonMaintainService = firFubonMaintainService;
	}

	public FirVerifyDatasService getFirVerifyDatasService() {
		return firVerifyDatasService;
	}

	public void setFirVerifyDatasService(FirVerifyDatasService firVerifyDatasService) {
		this.firVerifyDatasService = firVerifyDatasService;
	}

	public int getFixNrec() {
		return fixNrec;
	}

	public void setFixNrec(int fixNrec) {
		this.fixNrec = fixNrec;
	}

	public int getSfNrec() {
		return sfNrec;
	}

	public void setSfNrec(int sfNrec) {
		this.sfNrec = sfNrec;
	}

	public Aps034FbDetailVo getFbDetailVo() {
		return fbDetailVo;
	}

	public void setFbDetailVo(Aps034FbDetailVo fbDetailVo) {
		this.fbDetailVo = fbDetailVo;
	}

	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}
}