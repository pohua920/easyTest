package com.sinosoft.claim.common.web;

import ins.framework.common.Page;
import ins.framework.web.Struts2Action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import com.sinosoft.claim.common.util.ClaimQueryViewHelper;
import com.sinosoft.claim.common.util.CommonUtils;

public class ClaimQueryAction extends Struts2Action {
	private static Logger logger = Logger.getLogger(ClaimQueryAction.class);
	private static final long serialVersionUID = 1L;
	private ClaimQueryViewHelper claimQueryViewHelper;

	/***
	 * 應追償未追償查詢
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String claimReplevyQuery() {
		try {
			HttpServletRequest request = super.getRequest();
			Page page = this.claimQueryViewHelper.claimReplevyQuery(request, pageNo, pageSize);
			String[] args = { "makecom", "comcode","claimno", "policyno", "insuredname", "licenseno", "claimhandlername", "damagestartdate", "claimdate", "endcasedate", "replevydate", "replevyhandlername", "replevyamount", "inputdate" };
			request.setAttribute("page", new Page(page.getStart(), page.getTotalCount(), page.getPageSize(), null));
			request.setAttribute("resultList", this.setData(page.getResult(), args));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return SUCCESS;
	}

	/***
	 * 已追償明細查詢
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String claimRepleviedQuery() {
		try {
			HttpServletRequest request = super.getRequest();
			Page page = this.claimQueryViewHelper.claimRepleviedQuery(request, pageNo, pageSize);
			String[] args = { "makecom", "comcode","claimno", "policyno", "insuredname", "licenseno", "claimhandlername", "damagestartdate", "claimdate", "sumloss", "sumthispaid", "sumnodutyfee", "inputdate", "endflag" };
			request.setAttribute("page", new Page(page.getStart(), page.getTotalCount(), page.getPageSize(), null));
			request.setAttribute("resultList", this.setData(page.getResult(), args));
			request.setAttribute("replevyStatusArray", request.getParameterValues("replevyStatus"));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return SUCCESS;
	}

	/***
	 * 未決賠案明細查詢
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String claimOutstandingQuery() {
		try {
			HttpServletRequest request = super.getRequest();
			Page page = this.claimQueryViewHelper.claimOutstandingQuery(request, pageNo, pageSize);
			// 展示列的名稱。請按前端列順序指定
			String[] args = { "makecom","comcode", "channeltype", "businessnature", "businessnaturename", "claimno", "policyno", "insuredname", "insuredcode", "licenseno", "damagestartdate", "claimdate", "claimlossdate", "kindcode", "sumkindpay", "endflag",
					"endcasedate", "handlername", "handler1name" };
			request.setAttribute("page", new Page(page.getStart(), page.getTotalCount(), page.getPageSize(), null));
			request.setAttribute("resultList", this.setData(page.getResult(), args));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return SUCCESS;
	}

	/**
	 * 已決賠案明細查詢
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String claimSettledQuery() {
		try {
			HttpServletRequest request = super.getRequest();
			Page page = this.claimQueryViewHelper.claimSettledQuery(request, pageNo, pageSize);
			// 展示列的名稱。請按前端列順序指定
			String[] args = { "makecom","comcode","channeltype", "businessnature", "businessnaturename", "claimno", "policyno", "insuredname", "licenseno", "damagestartdate", "claimdate", "kindcode", "sumclaim", "sumkindpay", "sumkindfee",
					"underwriteenddate", "handlername", "handler1name" };
			request.setAttribute("page", new Page(page.getStart(), page.getTotalCount(), page.getPageSize(), null));
			request.setAttribute("resultList", this.setData(page.getResult(), args));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return SUCCESS;
	}

	private List<Map<String, Object>> setData(List<Map<String, Object>> resultList, String[] args) {
		List<Map<String, Object>> data = null;
		// 對數據進行預處理，保留需要的字段和順序
		if (!CommonUtils.isEmpty(resultList)) {
			data = new ArrayList<Map<String, Object>>();
			Map<String, Object> obj = null;
			int length = args.length;
			for (Map<String, Object> map : resultList) {
				obj = new LinkedHashMap<String, Object>();
				for (int index = 0; index < length; index++) {
					obj.put(args[index], map.get(args[index].toUpperCase()));
				}
				data.add(obj);
			}
		}
		return data;
	}

	public void writeJSONDataFromMap(Page page, String args[]) {
		try {
			Assert.notEmpty(args);
			List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> list = page.getResult();
			int size = args.length;
			Map<String, Object> dataMap;
			Object value;
			for (Map<String, Object> element : list) {
				dataMap = new HashMap<String, Object>(size);
				for (int i = 0; i < size; i++) {
					value = element.get(args[i].toUpperCase());
					Object retObject = value;
					if (value instanceof Date) {
						retObject = new Timestamp(((Date) value).getTime());
					}
					dataMap.put(args[i], retObject);
				}
				dataList.add(dataMap);
			}
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("recordsReturned", page.getPageSize());
			jsonObject.put("startIndex", page.getStart());
			jsonObject.put("totalRecords", page.getTotalCount());
			jsonObject.put("data", JSONArray.fromObject(dataList));
			System.err.println("data : " + JSONArray.fromObject(dataList));
			jsonObject.put("message", page.getMessage());
			renderJSON(jsonObject.toString());
		} catch (Exception e) {
			writeJSONMsg(e.getMessage());
		}
	}

	public ClaimQueryViewHelper getClaimQueryViewHelper() {
		return claimQueryViewHelper;
	}

	public void setClaimQueryViewHelper(ClaimQueryViewHelper claimQueryViewHelper) {
		this.claimQueryViewHelper = claimQueryViewHelper;
	}

}
