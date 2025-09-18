package com.tlg.ajax.action;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.tlg.aps.vo.FirBopRenewalDataVo;
import com.tlg.prpins.service.FirAgtrnBatchDtlService;
import com.tlg.util.BaseAction;
import com.tlg.util.JsonUtil;
import com.tlg.util.Result;

/* mantis：FIR0349，處理人員：BJ085，需求單編號：FIR0349 外銀板信續件扣款前置檔產生作業 */
@SuppressWarnings("serial")
public class AJAX008Action extends BaseAction implements Serializable {
	private FirAgtrnBatchDtlService firAgtrnBatchDtlService;
	private String result;
	private String batchNo;

	public String countPolicyByBatchNo() {
		try {
			Map<String,String> params = new HashMap<>();
			params.put("batchNo", batchNo);
			Result searchResult = firAgtrnBatchDtlService.findPolicyCountByBatchNo(params);
			Map<String,Object> map = new LinkedHashMap<>();
			
			if (null != searchResult.getResObject()) {
				FirBopRenewalDataVo firBopRenewalDataVo = (FirBopRenewalDataVo) searchResult.getResObject();
				map.put("isExist", true);
				map.put("batchNo", firBopRenewalDataVo.getBatchNo());
				map.put("npm", firBopRenewalDataVo.getNpm());
				map.put("ntotal", firBopRenewalDataVo.getNtotal());
			}else{
				map.put("isExist", false);
			}
			result = JsonUtil.getJSONString(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}

	public FirAgtrnBatchDtlService getFirAgtrnBatchDtlService() {
		return firAgtrnBatchDtlService;
	}

	public void setFirAgtrnBatchDtlService(FirAgtrnBatchDtlService firAgtrnBatchDtlService) {
		this.firAgtrnBatchDtlService = firAgtrnBatchDtlService;
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
}