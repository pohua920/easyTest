package com.tlg.ajax.action;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.tlg.prpins.entity.MetaAmlResultDetail;
import com.tlg.prpins.service.MetaAmlResultDetailService;
import com.tlg.util.BaseAction;
import com.tlg.util.JsonUtil;
import com.tlg.util.Result;

@SuppressWarnings("serial")
public class AJAX001Action extends BaseAction implements Serializable {
	/* mantis：OTH0065，處理人員：BJ085，需求單編號：OTH0065 建置AML洗錢查詢畫面 start */

	private MetaAmlResultDetailService metaAmlResultDetailService;

	private String result;

	@SuppressWarnings("unchecked")
	public String queryMetaAmlResultDetail() {
		try {
			getPageInfo().getFilter().put("oidMetaAmlResultMain", getRequest().getParameter("oid"));
			getPageInfo().getFilter().put("sortBy", "SCREEN_SEQ");
			getPageInfo().getFilter().put("sortType", "ASC");
			getPageInfo().setCurrentPage(Integer.parseInt(getRequest().getParameter("currentPage")));
			getPageInfo().setPageSize(Integer.parseInt(getRequest().getParameter("pageSize")));
			getPageInfo().setStartRow(Integer.parseInt(getRequest().getParameter("startRow")));
			getPageInfo().setEndRow(Integer.parseInt(getRequest().getParameter("endRow")));
			Result resultSet = metaAmlResultDetailService.findMetaAmlResultDetailByPageInfo(getPageInfo());
			Map<String,Object> map = new LinkedHashMap<>();
			if (null != resultSet.getResObject()) {
				map.put("isExist", "true");
				List<MetaAmlResultDetail> resultList = (List<MetaAmlResultDetail>) resultSet.getResObject();
				map.put("metaAmlResultDetailList", resultList);
			}else{
				map.put("isExist", "false");
			}
			result = JsonUtil.getJSONString(map);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}
	
	public MetaAmlResultDetailService getMetaAmlResultDetailService() {
		return metaAmlResultDetailService;
	}

	public void setMetaAmlResultDetailService(MetaAmlResultDetailService metaAmlResultDetailService) {
		this.metaAmlResultDetailService = metaAmlResultDetailService;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}