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

import net.sf.json.JSONObject;

import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.schema.model.PrpLbank;
import com.sinosoft.claim.schema.service.facade.PrpLbankService;
import com.sinosoft.sysframework.common.util.DataUtils;

/**
 * 银行自动填充功能
 * @author 中科软
 *
 */
public class AutoBankAction extends Struts2Action {

	private static final long serialVersionUID = 1L;
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
	
	private PrpLbank prpLbank;
	/**
	 * 总分行信息自动带出处理
	 * @return
	 * @throws Exception
	 */
	public String autoBank() throws Exception {
		HttpServletRequest request = this.getRequest();
		String orginEncoding = request.getCharacterEncoding();
		request.setCharacterEncoding("UTF-8");
		String bankName = request.getParameter("bankName");
		String upperBankName = request.getParameter("upperBankName");
		request.setCharacterEncoding(orginEncoding);
		HttpServletResponse response = this.getResponse();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/HTML");
		String result = "";
		try {
			long count = prpLbankService.findCount(bankCode, bankName,bankLevel,upperBankCode,upperBankName);
			int pageNo = 1;
			int pageSize = 60;
			if (count > 10) {
				if (bankCode != null && !"".equals(bankCode)) {
					for (int i = bankCode.length(); i > 0; i--) {
						if (i * pageSize <= count) {
							pageNo = i;
							break;
						}
					}
				} else if (bankName != null && !"".equals(bankName)) {
					for (int i = bankName.length(); i > 0; i--) {
						if (i * pageSize <= count) {
							pageNo = i;
							break;
						}
					}
				} else if (bankCode != null && !"".equals(bankCode) && bankName != null && !"".equals(bankName)) {
					for (int i = bankCode.length() + bankName.length(); i > 0; i--) {
						if (i * pageSize <= count) {
							pageNo = i;
							break;
						}
					}
				}
			}
			if (pageNo < 1) {
				pageNo = 1;
			}
			List<PrpLbank> list  = prpLbankService.findBank(bankCode, bankName,bankLevel,upperBankCode,upperBankName, pageNo, pageSize);
			StringBuffer sb = new StringBuffer("[");
			if (list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					PrpLbank prpLbank = list.get(i);
					sb.append("{'bankCode':'" + prpLbank.getId().getBankCode() + "','bankName':'" + prpLbank.getBankCName() + "','upperBankCode':'"+prpLbank.getId().getUpperBankCode()+"','upperBankName':'"+DataUtils.dbNullToEmpty(prpLbank.getUpperBankCName())+"','bankShortName':'"+DataUtils.dbNullToEmpty(prpLbank.getBankShortName())+"'},");
				}
				result = sb.substring(0, sb.length() - 1) + "]";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.err.println(result);
		response.getWriter().print(result);
		return NONE;
	}

	/**
	 * 银行信息检测
	 * @return
	 * @throws Exception
	 */
	public String verificationBank() throws Exception {
		HttpServletResponse response = this.getResponse();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/HTML");
		long count = prpLbankService.getCount(bankCode, bankName,bankLevel);
		if (count > 0) {
			response.getWriter().print("true");
		} else {
			response.getWriter().print("false");
		}
		return NONE;
	}
	
	public String bankBeforeEdit() throws Exception{
		HttpServletRequest request = super.getRequest();
		String editType = request.getParameter("editType");
		String bankLevel = request.getParameter("bankLevel");
		String bankCode = request.getParameter("bankCode");
		String upperBankCode = request.getParameter("upperBankCode");
		PrpLbank prpLbank = new PrpLbank();
		if("ADD".equals(editType)){
			prpLbank.setValidstatus("1");
			prpLbank.setBankLevel(bankLevel);
			if("2".equals(bankLevel)){//新增分行
				PrpLbank upper = this.prpLbankService.findPrpLbank(bankCode, upperBankCode);
				prpLbank.getId().setUpperBankCode(upperBankCode);
				prpLbank.setUpperBankCName(upper.getBankCName());
			}
		} else if("EDIT".equals(editType)){
			prpLbank = this.prpLbankService.findPrpLbank(bankCode, upperBankCode);
			prpLbank.setOrigBankCode(prpLbank.getId().getBankCode());
		}
		request.setAttribute("prpLbank", prpLbank);
		return SUCCESS;
	}
	
	public String saveBank() throws IOException{
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		response.setContentType("text/html;charset=GBK");
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			boolean check = true;
			String editType = request.getParameter("editType");
			if("ADD".equals(editType)){
				if("1".equals(prpLbank.getBankLevel())){//新增總行
					prpLbank.getId().setBankCode(prpLbank.getId().getUpperBankCode());
					prpLbank.setBankCName(URLDecoder.decode(prpLbank.getUpperBankCName(),"UTF-8"));
					prpLbank.setBankShortName(URLDecoder.decode(prpLbank.getBankShortName(),"UTF-8"));
					prpLbank.setUpperBankCName(URLDecoder.decode(prpLbank.getUpperBankCName(),"UTF-8"));

				}else{
					prpLbank.getId().setBankCode(prpLbank.getId().getBankCode());
					prpLbank.setBankCName(URLDecoder.decode(prpLbank.getBankCName(),"UTF-8"));
					prpLbank.setBankShortName(URLDecoder.decode(prpLbank.getBankShortName(),"UTF-8"));
					prpLbank.setUpperBankCName(URLDecoder.decode(prpLbank.getUpperBankCName(),"UTF-8"));
				}
				PrpLbank upper = this.prpLbankService.findPrpLbank(prpLbank.getId().getBankCode());
				if(upper != null){
					jsonMap.put("msg", "該抵押權人代碼已存在！");
				} else {
					
					this.prpLbankService.save(prpLbank);
				}
				prpLbank.setOrigBankCode(prpLbank.getId().getBankCode());
			} else if("EDIT".equals(editType)){//修改
				boolean changeBankCode = false;
				if("1".equals(prpLbank.getBankLevel())){//修改總行
					prpLbank.getId().setBankCode(prpLbank.getId().getUpperBankCode());
					prpLbank.setBankCName(URLDecoder.decode(prpLbank.getUpperBankCName(),"UTF-8"));
					prpLbank.setBankShortName(URLDecoder.decode(prpLbank.getBankShortName(),"UTF-8"));
					prpLbank.setUpperBankCName(URLDecoder.decode(prpLbank.getUpperBankCName(),"UTF-8"));
					changeBankCode = !prpLbank.getId().getUpperBankCode().equals(prpLbank.getOrigBankCode());//是否修改了總行代碼
					QueryRule queryRule = QueryRule.getInstance();
					queryRule.addEqual("id.upperBankCode", prpLbank.getId().getUpperBankCode());
					if("0".equals(prpLbank.getValidstatus())){
						List<PrpLbank> prpLbanks = this.prpLbankService.findPrpLbank(queryRule);
						for (Iterator iterator = prpLbanks.iterator(); iterator
								.hasNext();) {
							PrpLbank prpLbank = (PrpLbank) iterator.next();
							prpLbank.setValidstatus("0");
							this.prpLbankService.updatePrpLbank(prpLbank);
						}
					}
				} else if("2".equals(prpLbank.getBankLevel())){//修改分行
					String upperBankValidStatus = this.prpLbankService.findPrpLbank(prpLbank.getId().getUpperBankCode()).getValidstatus();
					String validStatus = this.prpLbankService.findPrpLbank(prpLbank.getId().getBankCode()).getValidstatus();
					prpLbank.getId().setBankCode(prpLbank.getId().getBankCode());
					prpLbank.setBankCName(URLDecoder.decode(prpLbank.getBankCName(),"UTF-8"));
					prpLbank.setBankShortName(URLDecoder.decode(prpLbank.getBankShortName(),"UTF-8"));
					prpLbank.setUpperBankCName(URLDecoder.decode(prpLbank.getUpperBankCName(),"UTF-8"));
					changeBankCode = !prpLbank.getId().getBankCode().equals(prpLbank.getOrigBankCode());//是否修改了分行代碼
					if("0".equals(upperBankValidStatus)&&"0".equals(validStatus)){
						check = false;
						jsonMap.put("msg", "總行代碼狀態為無效、分行代碼不能設置為有效！請先修改總行代碼狀態為有效，再修改分行代碼");
					}
				}
				
				if(changeBankCode){//修改了總分行代碼
					PrpLbank upper = this.prpLbankService.findPrpLbank(prpLbank.getId().getBankCode());
					if(upper != null){
						check = false;
						jsonMap.put("msg", "該抵押權人代碼已存在！");
					}
				}
				if(check){
					this.prpLbankService.updatePrpLbank(prpLbank);
					prpLbank.setOrigBankCode(prpLbank.getId().getBankCode());
				}
			} else if("DELETE".equals(editType)){//刪除
				String bankLevel = this.prpLbankService.findPrpLbank(prpLbank.getId().getBankCode()).getBankLevel();
				if("1".equals(bankLevel)){
					QueryRule queryRule = QueryRule.getInstance();
					queryRule.addEqual("id.upperBankCode", prpLbank.getId().getUpperBankCode());
					List<PrpLbank> prpLbanks = this.prpLbankService.findPrpLbank(queryRule);
					for (Iterator iterator = prpLbanks.iterator(); iterator
							.hasNext();) {
						PrpLbank prpLbank = (PrpLbank) iterator.next();
						prpLbank.setValidstatus("0");
						this.prpLbankService.updatePrpLbank(prpLbank);
					}
				}else{
					prpLbank.setValidstatus("0");
					this.prpLbankService.updatePrpLbank(prpLbank.getId().getBankCode(), prpLbank.getValidstatus());
				}
			} else if("RESUME".equals(editType)){//恢復
				String upperBankValidStatus = this.prpLbankService.findPrpLbank(prpLbank.getId().getUpperBankCode()).getValidstatus();
				if("0".equals(upperBankValidStatus)&&"2".equals(this.prpLbankService.findPrpLbank(prpLbank.getId().getBankCode()).getBankLevel())){
					jsonMap.put("msg", "總行代碼狀態為無效、分行代碼不能設置為有效！");
				}else{
					prpLbank.setValidstatus("1");
					this.prpLbankService.updatePrpLbank(prpLbank.getId().getBankCode(), prpLbank.getValidstatus());
				}
				/*prpLbank.setValidstatus("1");
				this.prpLbankService.updatePrpLbank(prpLbank.getId().getBankCode(), prpLbank.getValidstatus());*/
			}
			jsonMap.put("prpLbank", prpLbank);
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
	public String bankQuery() throws Exception {
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
			queryStatements = (String) session.getAttribute("bankQueryStatements");
			params = (Object[]) session.getAttribute("bankQueryParams");
		} else {
			List<Object> paramList = new ArrayList<Object>();
			queryStatements = this.getQuerySql(paramList);
			params = new Object[paramList.size()];
			paramList.toArray(params);
			session.setAttribute("bankQueryStatements", queryStatements);
			session.setAttribute("bankQueryParams", params);
		}
		Page page = this.prpLbankService.findPrpLbank(queryStatements, params, pageNo, pageSize);
		return page;
	}
	
	private String getQuerySql(List<Object> paramList) throws Exception {
		HttpServletRequest request = super.getRequest();
		String upperBankCodeSign = DataUtils.emptyToNull(request.getParameter("queryUpperBankCodeSign"));
		String upperBankCode = DataUtils.emptyToNull(request.getParameter("queryUpperBankCode"));
		String upperBankCNameSign = DataUtils.emptyToNull(request.getParameter("queryUpperBankCNameSign"));
		String upperBankCName = URLDecoder.decode(request.getParameter("queryUpperBankCName") , "UTF-8");
		String bankCodeSign = DataUtils.emptyToNull(request.getParameter("queryBankCodeSign"));
		String bankCode = DataUtils.emptyToNull(request.getParameter("queryBankCode"));
		String bankCNameSign = DataUtils.emptyToNull(request.getParameter("queryBankCNameSign"));
		String bankCName = URLDecoder.decode(request.getParameter("queryBankCName"),"UTF-8");
		String bankLevel = DataUtils.emptyToNull(request.getParameter("queryBankLevel"));
		String validstatus = DataUtils.emptyToNull(request.getParameter("queryValidstatus"));
		StringBuffer conditions = new StringBuffer("");
		if (!CommonUtils.isEmpty(validstatus)) {
			conditions.append(" and ").append(CommonUtils.getCondition("=", "validstatus", validstatus, paramList, String.class));
		}
		if (!CommonUtils.isEmpty(bankLevel)) {
			conditions.append(" and ").append(CommonUtils.getCondition("=", "bankLevel", bankLevel, paramList, String.class));
		}
		if (!CommonUtils.isEmpty(upperBankCode)) {
			conditions.append(" and ").append(CommonUtils.getCondition(upperBankCodeSign, "upperBankCode", upperBankCode, paramList, String.class));
		}
		if (!CommonUtils.isEmpty(upperBankCName)) {
			conditions.append(" and ").append(CommonUtils.getCondition(upperBankCNameSign, "upperBankCName", upperBankCName, paramList, String.class));
		}
		if (!CommonUtils.isEmpty(bankCode)) {
			conditions.append(" and ").append(CommonUtils.getCondition(bankCodeSign, "bankCode", bankCode, paramList, String.class));
		}
		if (!CommonUtils.isEmpty(bankCName)) {
			conditions.append(" and ").append(CommonUtils.getCondition(bankCNameSign, "bankCName", bankCName, paramList, String.class));
		}
		// 設置查詢SQL
		StringBuffer statements = new StringBuffer(" 1 = 1 ");
		statements.append(conditions);
		statements.append(" ORDER  BY upperBankCode ASC , bankCode ASC ");
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

	public PrpLbank getPrpLbank() {
		return prpLbank;
	}

	public void setPrpLbank(PrpLbank prpLbank) {
		this.prpLbank = prpLbank;
	}
	
}
