package com.sinosoft.claim.common.web;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.web.Struts2Action;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import net.sf.json.JSONObject;

import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpLUserLog;
import com.sinosoft.claim.schema.model.PrpLUserLogId;
import com.sinosoft.claim.schema.model.PrpLbank;
import com.sinosoft.claim.schema.model.PrpLuser;
import com.sinosoft.claim.schema.service.facade.PrpLUserLogService;
import com.sinosoft.claim.schema.service.facade.PrpLbankService;
import com.sinosoft.claim.schema.service.facade.PrpLuserService;
import com.sinosoft.sysframework.common.util.DataUtils;

/**
 *mantis：CLM0125，處理人員：DP0713，需求單編號：新功能理賠人員資料維護 
 */
public class ClaimEmployeeAction extends Struts2Action {

	private static final long serialVersionUID = 1L;
	/** 理賠使用者接口 */
	private PrpLuserService prpLuserService;
	/** 理賠使用者接口 */
	private PrpLUserLogService prpLUserLogService;
	/** 银行表接口 */
	private PrpLbankService prpLbankService;
	/** 银行代码 */
	private String bankCode;
	/** 银行名称 */
	private String bankName;
	/** 银行级别 */
	private String bankLevel;
	/** 上级银行代码 */
	private String upperBankCode;
	/** 上级银行名称 */
	private String upperBankName;
	
	private PrpLuser prpLuser;
	
	public String employeeBeforeEdit() throws Exception{
		HttpServletRequest request = super.getRequest();
		String editType = request.getParameter("editType");
		String id = request.getParameter("id");
		String userCode = request.getParameter("userCode");
		String updateRec = "";
		PrpLuser prpLuser = new PrpLuser();
		prpLuser.setUserFlag("1");//新增時候預設為1 有效
		if("EDIT".equals(editType)){
			prpLuser = this.prpLuserService.findPrpLuserByUserCode(userCode);
			updateRec = "";//使用者填寫
		}
		request.setAttribute("updateRec", updateRec);
		request.setAttribute("prpLuser", prpLuser);
		return SUCCESS;
	}
	
	public String saveEmployee() throws IOException{
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		response.setContentType("text/html;charset=GBK");
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		UserDto user = (UserDto) request.getSession().getAttribute("user");
		try {			
			PrpLUserLog prpLUserLog = new PrpLUserLog();
			PrpLUserLogId prpLUserLogId=new PrpLUserLogId();
			
			boolean check = false;
			String editType = request.getParameter("editType");
			String updateRec = request.getParameter("param.updateRec");
			int count = this.prpLuserService.countPrpLuserByUserCode(prpLuser.getUserCode());
			if(("ADDED".equals(editType) || "ADD".equals(editType)) && count!=0){
				jsonMap.put("msg", "帳號已經存在！");
				check = false;
			}else if("ADD".equals(editType)){
				prpLuser.setUserName(URLDecoder.decode(prpLuser.getUserName(),"UTF-8"));
				prpLuser.setWorkPlaceNm(URLDecoder.decode(prpLuser.getWorkPlaceNm(),"UTF-8"));
				prpLuser.setCreateUser(user.getUserCode());
				prpLuser.setCreateTime(new Date());
				this.prpLuserService.save(prpLuser);
				check = true;
				
			}else if("EDIT".equals(editType)){
				prpLuser.setUserName(URLDecoder.decode(prpLuser.getUserName(),"UTF-8"));
				prpLuser.setWorkPlaceNm(URLDecoder.decode(prpLuser.getWorkPlaceNm(),"UTF-8"));
				if(StringUtils.isBlank(updateRec)){
					jsonMap.put("msg", "【修改原因備註】必須輸入！");
				}else{
					//Date過不了URL\"editType=EDIT&prpLuser.createUser=AA000&prpLuser.createTime=2013-07-01+11:20:22.374489&prpLuser.userCode=...
					//+這符號導致AJAX失效 改用QUERY查出該筆時間寫回
					Date uDate = this.prpLuserService.getRecordDateByUserCode(prpLuser.getUserCode());
					prpLuser.setCreateUser(prpLuser.getCreateUser());
					prpLuser.setCreateTime(uDate);
					prpLuser.setUpdateUser(user.getUserCode());
					prpLuser.setUpdateTime(new Date());
					this.prpLuserService.save(prpLuser);
					check=true;
				}
			}
			
			if(check){
				prpLUserLogId.setOid(this.prpLUserLogService.getMax()+1);
				prpLUserLogId.setUserCode(prpLuser.getUserCode());
				prpLUserLog.setId(prpLUserLogId);
				prpLUserLog.setComcode(prpLuser.getComcode());
				prpLUserLog.setWorkPlaceNm(prpLuser.getWorkPlaceNm());
				if(null!=prpLuser.getFeeQuota() && !prpLuser.getFeeQuota().equals("")){					
					prpLUserLog.setFeeQuota(prpLuser.getFeeQuota().intValue());
				}
				prpLUserLog.setCreatUser(user.getUserCode());
				prpLUserLog.setCreatTime(new Date());
				prpLUserLog.setUpdateRec(URLDecoder.decode(updateRec,"UTF-8"));
				this.prpLUserLogService.save(prpLUserLog);
			}
			
			jsonMap.put("prpLuser", prpLuser);
		} catch (Exception e) {
			e.printStackTrace();
			jsonMap.put("msg", "保存資料失敗！" + e.getMessage());
		}
		response.getWriter().write(JSONObject.fromObject(jsonMap).toString());
		return NONE;
	}
	
	/***
	 * @return
	 * @throws Exception
	 */
	public String employeeQuery() throws Exception {
		HttpServletRequest request = super.getRequest();
		Page page = this.query();
		// queryType json 本次請求返回json，
		request.setAttribute("currentPageNo", page.getCurrentPageNo());
		request.setAttribute("currentPageSize", page.getPageSize());
		request.setAttribute("totalPageCount", page.getTotalPageCount());
		request.setAttribute("pageStart", page.getStart());
		request.setAttribute("resultList", page.getResult());
		request.setAttribute("page", page);
		return SUCCESS;
	}

	private Page query() throws Exception {
		HttpServletRequest request = super.getRequest();
		HttpSession session = request.getSession();
		String queryStatements = "";
		Object[] params = null;
		// 查詢類型，querynew 新查詢 querycontinue 翻頁
		String queryType = request.getParameter("queryType");
		String queryPageNo = request.getParameter("pageNo");
		String queryPageSize = request.getParameter("rowsPerPage");
		pageNo = Integer.parseInt(CommonUtils.isEmpty(queryPageNo) ? "1" : queryPageNo);
		pageSize = Integer.parseInt(CommonUtils.isEmpty(queryPageSize) ? "10" : queryPageSize);
		if ("querycontinue".equals(queryType)) {// 翻頁查詢
			queryStatements = (String) session.getAttribute("employeeQueryStatements");
			params = (Object[]) session.getAttribute("employeeQueryParams");
		} else {
			List<Object> paramList = new ArrayList<Object>();
			queryStatements = this.getQuerySql(paramList);
			params = new Object[paramList.size()];
			paramList.toArray(params);
			session.setAttribute("employeeQueryStatements", queryStatements);
			session.setAttribute("employeeQueryParams", params);
		}
		Page page = this.prpLuserService.findPrpLuser(queryStatements, params, pageNo, pageSize);
		return page;
	}
	
	private String getQuerySql(List<Object> paramList) throws Exception {
		HttpServletRequest request = super.getRequest();
		StringBuffer conditions = new StringBuffer("");
		String userNameSign = DataUtils.emptyToNull(request.getParameter("queryUserNameSign"));
		String userName = URLDecoder.decode(request.getParameter("queryUserName") , "UTF-8");
		if (!CommonUtils.isEmpty(userName)) {
			conditions.append(" and ").append(CommonUtils.getCondition(userNameSign, "userName", userName, paramList, String.class));
		}
		String userCodeSign = DataUtils.emptyToNull(request.getParameter("queryUserCodeSign"));
		String userCode = DataUtils.emptyToNull(request.getParameter("queryUserCode"));
		if (!CommonUtils.isEmpty(userCode)) {
			conditions.append(" and ").append(CommonUtils.getCondition(userCodeSign, "userCode", userCode, paramList, String.class));
		}
		String workPlaceNmSign = DataUtils.emptyToNull(request.getParameter("queryWorkPlaceNmSign"));
		String workPlaceNm = URLDecoder.decode(request.getParameter("queryWorkPlaceNm") , "UTF-8");
		if (!CommonUtils.isEmpty(workPlaceNm)) {
			conditions.append(" and ").append(CommonUtils.getCondition(workPlaceNmSign, "workPlaceNm", workPlaceNm, paramList, String.class));
		}
		String comcodeSign = DataUtils.emptyToNull(request.getParameter("queryComcodeSign"));
		String comcode = DataUtils.emptyToNull(request.getParameter("queryComcode"));
		if (!CommonUtils.isEmpty(comcode)) {
			conditions.append(" and ").append(CommonUtils.getCondition(comcodeSign, "comcode", comcode, paramList, String.class));
		}
		String feeQuotaSign = DataUtils.emptyToNull(request.getParameter("queryFeeQuotaSign"));
		String feeQuota = DataUtils.emptyToNull(request.getParameter("queryFeeQuota"));
		if (!CommonUtils.isEmpty(feeQuota)) {
			conditions.append(" and ").append(CommonUtils.getCondition(feeQuotaSign, "feeQuota", feeQuota, paramList, String.class));
		}
		String userFlag = DataUtils.emptyToNull(request.getParameter("queryUserFlag"));
		if (!CommonUtils.isEmpty(userFlag)) {
			conditions.append(" and ").append(CommonUtils.getCondition("=", "userFlag", userFlag, paramList, String.class));
		}
		// 設置查詢SQL
		StringBuffer statements = new StringBuffer(" 1 = 1 ");
		statements.append(conditions);
		//statements.append(" ORDER  BY upperBankCode ASC , bankCode ASC ");
		return statements.toString();
	}
	
	public PrpLbankService getPrpLbankService() {
		return prpLbankService;
	}

	public void setPrpLbankService(PrpLbankService prpLbankService) {
		this.prpLbankService = prpLbankService;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankLevel() {
		return bankLevel;
	}

	public void setBankLevel(String bankLevel) {
		this.bankLevel = bankLevel;
	}

	public String getUpperBankCode() {
		return upperBankCode;
	}

	public void setUpperBankCode(String upperBankCode) {
		this.upperBankCode = upperBankCode;
	}

	public String getUpperBankName() {
		return upperBankName;
	}

	public void setUpperBankName(String upperBankName) {
		this.upperBankName = upperBankName;
	}

	public PrpLuserService getPrpLuserService() {
		return prpLuserService;
	}

	public void setPrpLuserService(PrpLuserService prpLuserService) {
		this.prpLuserService = prpLuserService;
	}

	public PrpLuser getPrpLuser() {
		return prpLuser;
	}

	public void setPrpLuser(PrpLuser prpLuser) {
		this.prpLuser = prpLuser;
	}

	public PrpLUserLogService getPrpLUserLogService() {
		return prpLUserLogService;
	}

	public void setPrpLUserLogService(PrpLUserLogService prpLUserLogService) {
		this.prpLUserLogService = prpLUserLogService;
	}
	
}
