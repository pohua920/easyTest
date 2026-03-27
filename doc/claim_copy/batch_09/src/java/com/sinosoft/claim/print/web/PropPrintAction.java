package com.sinosoft.claim.print.web;

import ins.framework.web.Struts2Action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JREmptyDataSource;

import com.opensymphony.xwork2.Preparable;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.print.util.PropPrintViewHelper;
import com.sinosoft.claim.print.vo.PropClaimApplicationFormObject;
import com.sinosoft.claim.print.vo.PropClaimDisposeReportObject;
import com.sinosoft.claim.print.vo.PropCoinsCompesateObject;
import com.sinosoft.claim.print.vo.PropCompensateObject;
import com.sinosoft.claim.print.vo.PropGeneralClaimObject;
import com.sinosoft.claim.print.vo.PropLossListObject;
import com.sinosoft.claim.print.vo.PropPaymentAcceptanceObject;
import com.sinosoft.claim.print.vo.PropPrpinsClaimInformationObject;
import com.sinosoft.claim.print.vo.PropRegistReportObject;
import com.sinosoft.claim.print.vo.PropRemittanceFormObject;
import com.sinosoft.claim.print.vo.PropRemnantObject;
import com.sinosoft.claim.print.vo.PropReplevyReportObject;
import com.sinosoft.sysframework.common.util.DataUtils;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * 火險列印處理
 * @author 中科軟
 * 
 */
public class PropPrintAction extends Struts2Action implements Preparable{
	private static final long serialVersionUID = 1L;
	/** 报表jasper文件路径*/
	private String path;
	/** 报表列印  数据对象*/
	private List<Object> resultList = new ArrayList<Object>();
	/** 传递的参数*/
	private Map<String, Object> param = new HashMap<String, Object>();
	/** 收集 火险列印 所需数据*/
	private PropPrintViewHelper propPrintViewHelper; 
	
	@Override
	public void prepare() throws Exception {
		path = super.getRequest().getSession().getServletContext().getRealPath("")+"/printReport/";
		param.put("IMGPATH", this.path + "image/logo.png");
		param.put("SUBREPORT_DIR", this.path + "Prop/");
	}
	
	/**
	 * 火險追償計算書
	 * @return
	 * @throws UserException 
	 */
	public String printPropReplevyReport() throws UserException{
		HttpServletRequest request = super.getRequest();
		String compensateNo = request.getParameter("compensateNo");
		if("".equals(DataUtils.dbNullToEmpty(compensateNo))){
			throw new UserException(1, 3, "請輸入計算書號碼！","");
		}
		try{
			PropReplevyReportObject propReplevyReportObject = this.propPrintViewHelper.findPropReplevyReportObjectByCompensateNo(compensateNo.trim());
			this.resultList.add(propReplevyReportObject);
		}catch(Exception e){
			e.printStackTrace();
			throw new UserException(1, 3, "請輸入正確的計算書號碼！","");
		}
		return SUCCESS;
	}
	
	/**
	 * 火險出險報告
	 * @return
	 * @throws UserException 
	 */
	public String printPropRegistReport() throws UserException{
		HttpServletRequest request = super.getRequest();
		String registNo = request.getParameter("registNo");
		if("".equals(DataUtils.dbNullToEmpty(registNo))){
			throw new UserException(1, 3, "請輸入備案號碼！","");
		}
		try{
			PropRegistReportObject propRegistReportObject = this.propPrintViewHelper.findPropRegistReportObjectByRegistNo(registNo.trim());
			this.resultList.add(propRegistReportObject);
		}catch(Exception e){
			e.printStackTrace();
			throw new UserException(1, 3, "請輸入正確的備案號碼！","");
		}
		return SUCCESS;
	}
	
	/**
	 * 非水代查勘委託書
	 * @return
	 * @throws UserException 
	 */
	public String printPropGeneralClaim() throws UserException{
		HttpServletRequest request = super.getRequest();
		String registNo = request.getParameter("registNo");
		if("".equals(DataUtils.dbNullToEmpty(registNo))){
			throw new UserException(1, 3, "請輸入備案號碼！","");
		}
		try{
			PropGeneralClaimObject propGeneralClaimObject = this.propPrintViewHelper.findPropGeneralClaimObjectByRegistNo(registNo.trim());
			this.resultList.add(propGeneralClaimObject);
		}catch(Exception e){
			e.printStackTrace();
			throw new UserException(1, 3, "請輸入正確的備案號碼！","");
		}
		return SUCCESS;
	}
	
	/***
	 * 匯款同意書
	 * @return
	 * @throws UserException
	 */
	public String printPropRemittanceForm() throws UserException{
		HttpServletRequest request = super.getRequest();
		String compensateNo = request.getParameter("compensateNo");
		if("".equals(DataUtils.dbNullToEmpty(compensateNo))){
			throw new UserException(1, 3, "請輸入計算書號碼！","");
		}
		try{
			PropRemittanceFormObject propRemittanceFormObject = this.propPrintViewHelper.findPropRemittanceFormObjectByCompensateNo(compensateNo.trim());
			this.resultList.add(propRemittanceFormObject);
		}catch(Exception e){
			e.printStackTrace();
			throw new UserException(1, 3, "請輸入正確的計算書號碼！","");
		}
		return SUCCESS;
	}
	
	/**
	 * 理賠申請書
	 * @return
	 * @throws UserException
	 */
	public String printPropClaimApplicationForm() throws UserException{
		HttpServletRequest request = super.getRequest();
		String claimNo = request.getParameter("claimNo");
		if("".equals(DataUtils.dbNullToEmpty(claimNo))){
			throw new UserException(1, 3, "請輸入賠案號碼！","");
		}
		try{
			PropClaimApplicationFormObject propClaimApplicationFormObject = this.propPrintViewHelper.findPropClaimApplicationFormObjectByClaimNo(claimNo.trim());
			this.resultList.add(propClaimApplicationFormObject);
		}catch(Exception e){
			e.printStackTrace();
			throw new UserException(1, 3, "請輸入正確的賠案號碼！","");
		}
		return SUCCESS;
	}
	
	/***
	 * 理賠處理報告
	 * @return
	 * @throws UserException
	 */
	public String printPropClaimDisposeReport() throws UserException{
		HttpServletRequest request = super.getRequest();
		String claimNo = request.getParameter("claimNo");
		if("".equals(DataUtils.dbNullToEmpty(claimNo))){
			throw new UserException(1, 3, "請輸入賠案號碼！","");
		}
		try{
			PropClaimDisposeReportObject propClaimDisposeReportObject = this.propPrintViewHelper.findPropClaimDisposeReportObjectByClaimNo(claimNo.trim());
			this.resultList.add(propClaimDisposeReportObject);
		}catch(Exception e){
			e.printStackTrace();
			throw new UserException(1, 3, "請輸入正確的賠案號碼！","");
		}
		return SUCCESS;
	}
	
	/**
	 * 聯共保計算書
	 * @return
	 * @throws UserException
	 */
	public String printPropCoinsCompensate() throws UserException{
		HttpServletRequest request = super.getRequest();
		String compensateNo = request.getParameter("compensateNo");
		if("".equals(DataUtils.dbNullToEmpty(compensateNo))){
			throw new UserException(1, 3, "請輸入計算書號碼！","");
		}
		try{
			PropCoinsCompesateObject propCoinsCompesateObject = this.propPrintViewHelper.findPropCoinsCompesateObjectByCompensateNo(compensateNo.trim());
			this.resultList.add(propCoinsCompesateObject);
		}catch(Exception e){
			e.printStackTrace();
			throw new UserException(1, 3, "請輸入正確的計算書號碼！","");
		}
		
		return SUCCESS;
	}
	
	/***
	 * 火險賠款計算書
	 * @return
	 * @throws UserException
	 */
	public String printPropClaimCompensateReport() throws UserException{
		HttpServletRequest request = super.getRequest();
		String compensateNo = request.getParameter("compensateNo");
		if("".equals(DataUtils.dbNullToEmpty(compensateNo))){
			throw new UserException(1, 3, "請輸入計算書號碼！","");
		}
		try{
			PropCompensateObject propCompensateObject = this.propPrintViewHelper.findPropClaimCompensateReportObjectByCompensateNo(compensateNo.trim());
			param.put("DISPLAYFLAG", false);
			if(propCompensateObject.getCompensateSubreport2Object().size() > 0){
				param.put("DISPLAYFLAG", true);
			}
			this.resultList.add(propCompensateObject);
		}catch(Exception e){
			e.printStackTrace();
			throw new UserException(1, 3, "請輸入正確的計算書號碼！","");
		}
		return SUCCESS;
	}
	
	/***
	 * 火 險 損 失 清 單
	 * @description 保單號 ，備案號 任意正確即可
	 * @return
	 * @throws UserException
	 */
	public String printPropLossList() throws UserException{
		HttpServletRequest request = super.getRequest();
		String registNo = request.getParameter("registNo");
		String policyNo = request.getParameter("policyNo");
		PropLossListObject propLossListObject = null;
		if(!CommonUtils.isEmpty(registNo)){
			try{
				propLossListObject = this.propPrintViewHelper.findPropLossListObjectByRegistNo(registNo);
				this.resultList.add(propLossListObject);
				return SUCCESS;
			}catch(Exception e){
				
			}
		}
		if(!CommonUtils.isEmpty(policyNo)){
			try{
				propLossListObject = this.propPrintViewHelper.findPropLossListObjectByPolicyNo(policyNo);
				this.resultList.add(propLossListObject);
				return SUCCESS;
			}catch(Exception e){
				e.printStackTrace();
				throw new UserException(1, 3, "請輸入正確的保單號碼 或者 備案號碼！","");
			}
		}else{
			throw new UserException(1, 3, "請輸入保單號碼 或者 備案號碼！","");
		}
		
	}
	
	/**
	 * 銀行同意書
	 * @return
	 */
	public String printPropBankAgreement(){
		resultList.add(new JREmptyDataSource());
		return SUCCESS;
	}
	
	/***
	 * 火險承保理賠信息
	 * @return
	 * @throws UserException 
	 */
	public String printPropPrpinsClaimInformation() throws UserException{
		HttpServletRequest request = super.getRequest();
		String registNo = request.getParameter("registNo");
		if("".equals(DataUtils.dbNullToEmpty(registNo))){
			throw new UserException(1, 3, "請輸入備案號碼！","");
		}
		try{
			PropPrpinsClaimInformationObject propPrpinsClaimInformationObject = this.propPrintViewHelper.findpropPrpinsClaimInformationObjectByRegistNo(registNo.trim());
			this.resultList.add(propPrpinsClaimInformationObject);
		}catch(Exception e){
			e.printStackTrace();
			throw new UserException(1, 3, "請輸入正確的備案號碼！","");
		}
		return SUCCESS;
	}
	
	/**
	 * 火災保險賠款接受書
	 * @return
	 * @throws UserException
	 */
	public String printPropPaymentAcceptance() throws UserException{
		HttpServletRequest request = super.getRequest();
		String compensateNo = request.getParameter("compensateNo");
		if(CommonUtils.isEmpty(compensateNo)){
			throw new UserException(1, 3, "請輸入計算書號碼！","");
		}
		try{
			PropPaymentAcceptanceObject propPaymentAcceptanceObject = this.propPrintViewHelper.findPropPaymentAcceptanceObjectByCompensateNo(compensateNo.trim());
			this.resultList.add(propPaymentAcceptanceObject);
		}catch(Exception e){
			e.printStackTrace();
			throw new UserException(1, 3, "請輸入計算書號碼！","");
		}
		return SUCCESS;
	}
	/***
	 * 殘餘物理算書
	 * @return
	 * @throws UserException
	 */
	public String printPropRemnant() throws UserException{
		HttpServletRequest request = super.getRequest();
		String compensateNo = request.getParameter("compensateNo");
		if("".equals(DataUtils.dbNullToEmpty(compensateNo))){
			throw new UserException(1, 3, "請輸入計算書號碼！","");
		}
		try{
			PropRemnantObject propRemnantObject = this.propPrintViewHelper.findPropRemnantObjectByCompensateNo(compensateNo);
			this.resultList.add(propRemnantObject);
			resultList.add(new JREmptyDataSource());
		}catch(Exception e){
			e.printStackTrace();
			throw new UserException(1, 3, "請輸入正確的計算書號碼！","");
		}
		return SUCCESS;
	}
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<Object> getResultList() {
		return resultList;
	}

	public void setResultList(List<Object> resultList) {
		this.resultList = resultList;
	}


	public Map<String, Object> getParam() {
		return param;
	}

	public void setParam(Map<String, Object> param) {
		this.param = param;
	}

	public PropPrintViewHelper getPropPrintViewHelper() {
		return propPrintViewHelper;
	}

	public void setPropPrintViewHelper(PropPrintViewHelper propPrintViewHelper) {
		this.propPrintViewHelper = propPrintViewHelper;
	}
	
}
