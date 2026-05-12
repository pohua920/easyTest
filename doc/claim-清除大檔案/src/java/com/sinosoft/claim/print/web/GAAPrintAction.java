package com.sinosoft.claim.print.web;

import ins.framework.web.Struts2Action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JREmptyDataSource;

import com.opensymphony.xwork2.Preparable;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.print.util.GAAPrintViewHelper;
import com.sinosoft.claim.print.vo.GAAClaimApplicationObject;
import com.sinosoft.claim.print.vo.GAACommissionedObject;
import com.sinosoft.claim.print.vo.GAACompensateObject;
import com.sinosoft.claim.print.vo.GAAContractObject;
import com.sinosoft.claim.print.vo.GAAInvestigativeObject;
import com.sinosoft.claim.print.vo.GAANotificationObject;
import com.sinosoft.claim.print.vo.GAAReceiptObject;
import com.sinosoft.claim.print.vo.GAAReconciliationObject;
import com.sinosoft.claim.print.vo.GAAReinsCompensateObject;
import com.sinosoft.claim.print.vo.GAARemittanceObject;
import com.sinosoft.claim.print.vo.GAARemnantObject;
import com.sinosoft.claim.print.vo.GAARevocationObject;
import com.sinosoft.sysframework.common.util.DataUtils;
import com.sinosoft.sysframework.exceptionlog.UserException;

/***
 * 工程险打印类
 * @author 中科软
 */
public class GAAPrintAction extends Struts2Action implements Preparable {
	private static final long serialVersionUID = 1L;
	/** 报表jasper文件路径 */
	private String path;
	/** 报表列印 数据对象 */
	private List<Object> resultList = new ArrayList<Object>();
	/** 传递的参数 */
	private Map<String, Object> param = new HashMap<String, Object>();
	/** 收集 火险列印 所需数据 */
	private GAAPrintViewHelper gaaPrintViewHelper;

	public void prepare() throws Exception {
		path = super.getRequest().getSession().getServletContext().getRealPath("") + "/printReport/";
		param.put("IMGPATH", this.path + "image/logo.png");
		param.put("SUBREPORT_DIR", this.path + "GAA/");
	}
	//mantis：CLM0072 ，處理人員：BK007 蘇哲，需求單編號：CLM0072.工程險追償理算書 start
	/**
	 * 追償計算書
	 * @return
	 * @throws UserException 
	 */
	public String printGAAReplevyReport() throws UserException{
		HttpServletRequest request = super.getRequest();
		String compensateNo = request.getParameter("compensateNo");
		if("".equals(DataUtils.dbNullToEmpty(compensateNo))){
			throw new UserException(1, 3, "請輸入計算書號碼！","");
		}
		try{
			this.resultList.add(this.gaaPrintViewHelper.findGAAReplevyReportObjectByCompensateNo(compensateNo.trim()));
		}catch(Exception e){
			e.printStackTrace();
			throw new UserException(1, 3, "請輸入正確的計算書號碼！","");
		}
		return SUCCESS;
	}
	//mantis：CLM0072 ，處理人員：BK007 蘇哲，需求單編號：CLM0072.工程險追償理算書 end
	
	/***
	 * 理賠申請書
	 * @return
	 * @throws Exception
	 */
	public String printGAAClaimApplication() throws Exception {
		HttpServletRequest request = super.getRequest();
		String claimNo = request.getParameter("claimNo");
		if("".equals(DataUtils.dbNullToEmpty(claimNo))){
			throw new UserException(1, 3, "請輸入賠案號碼！","");
		}
		try {
			GAAClaimApplicationObject gaaClaimApplicationObject = this.gaaPrintViewHelper.findGAAClaimApplicationObjectByClaimNo(claimNo);
			this.resultList.add(gaaClaimApplicationObject);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException(1, 3, "列印錯誤", "請輸入正確的賠案號碼！");
		}
		return SUCCESS;
	}

	/***
	 * 匯款同意書
	 * @return
	 * @throws Exception
	 */
	public String printGAARemittance() throws Exception {
		HttpServletRequest request = super.getRequest();
		String compensateNo = request.getParameter("compensateNo");
		if("".equals(DataUtils.dbNullToEmpty(compensateNo))){
			throw new UserException(1, 3, "請輸入賠款計算書號碼！","");
		}
		try {
			GAARemittanceObject gaaRemittanceObject = this.gaaPrintViewHelper.findGAARemittanceObjectByCompensateNo(compensateNo);
			this.resultList.add(gaaRemittanceObject);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException(1, 3, "列印錯誤", "請輸入正確的賠款計算書號碼！");
		}
		return SUCCESS;
	}

	/***
	 * 賠款同意書暨領款收據
	 * @return
	 * @throws Exception
	 */
	public String printGAAReceipt() throws Exception {
		HttpServletRequest request = super.getRequest();
		String claimNo = request.getParameter("claimNo");
		if("".equals(DataUtils.dbNullToEmpty(claimNo))){
			throw new UserException(1, 3, "請輸入賠案號碼！","");
		}
		try {
			GAAReceiptObject gaaReceiptObject = this.gaaPrintViewHelper.findGAAReceiptObjectByClaimNo(claimNo);
			this.resultList.add(gaaReceiptObject);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException(1, 3, "列印錯誤", "請輸入正確的賠案號碼！");
		}
		return SUCCESS;
	}

	/***
	 * 賠委託公證申請單
	 * @return
	 * @throws Exception
	 */
	public String printGAACommissioned() throws Exception {
		HttpServletRequest request = super.getRequest();
		HttpSession session =  super.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		String claimNo = request.getParameter("claimNo");
		if("".equals(DataUtils.dbNullToEmpty(claimNo))){
			throw new UserException(1, 3, "請輸入賠案號碼！","");
		}
		try {
			GAACommissionedObject gaaCommissionedObject = this.gaaPrintViewHelper.findGAACommissionedObjectByClaimNo(claimNo,user.getComName(),user.getUserName());
			this.resultList.add(gaaCommissionedObject);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException(1, 3, "列印錯誤", "請輸入正確的賠案號碼！");
		}
		return SUCCESS;
	}

	/***
	 * 債權讓與契約暨通知書
	 * @return
	 * @throws Exception
	 */
	public String printGAAContract() throws Exception {
		try {
			HttpServletRequest request = super.getRequest();
			String claimNo = request.getParameter("claimNo");
			if("".equals(DataUtils.dbNullToEmpty(claimNo))){
				throw new UserException(1, 3, "請輸入賠案號碼！","");
			}
			GAAContractObject gaaContractObject = this.gaaPrintViewHelper.findGAAGAAContractObjectByClaimNo(claimNo);
			this.resultList.add(gaaContractObject);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException(1, 3, "列印錯誤", "請輸入正確的賠案號碼！");
		}
		return SUCCESS;
	}

	/***
	 * 撤銷申請理賠同意書
	 * @return
	 * @throws Exception
	 */
	public String printGAARevocation() throws Exception {
		HttpServletRequest request = super.getRequest();
		String claimNo = request.getParameter("claimNo");
		if("".equals(DataUtils.dbNullToEmpty(claimNo))){
			throw new UserException(1, 3, "請輸入賠案號碼！","");
		}
		try {
			GAARevocationObject gaaRevocationObject = this.gaaPrintViewHelper.findGAARevocationObjectByClaimNo(claimNo);
			this.resultList.add(gaaRevocationObject);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException(1, 3, "列印錯誤", "請輸入正確的賠案號碼！");
		}
		return SUCCESS;
	}

	/***
	 * 補件通知函
	 * @return
	 * @throws Exception
	 */
	public String printGAANotification() throws Exception {
		HttpServletRequest request = super.getRequest();
		String claimNo = request.getParameter("claimNo");
		if("".equals(DataUtils.dbNullToEmpty(claimNo))){
			throw new UserException(1, 3, "請輸入賠案號碼！","");
		}
		try {
			GAANotificationObject gaaNotificationObject = this.gaaPrintViewHelper.findGAANotificationObjectByClaimNo(claimNo);
			this.resultList.add(gaaNotificationObject);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException(1, 3, "列印錯誤", "請輸入正確的賠案號碼！");
		}
		return SUCCESS;
	}

	/***
	 * 查案單
	 * @return
	 * @throws Exception
	 */
	public String printGAAInvestigative() throws Exception {
		HttpServletRequest request = super.getRequest();
		String claimNo = request.getParameter("claimNo");
		if("".equals(DataUtils.dbNullToEmpty(claimNo))){
			throw new UserException(1, 3, "請輸入賠案號碼！","");
		}
		try {
			GAAInvestigativeObject gaaInvestigativeObject =  this.gaaPrintViewHelper.findGAAInvestigativeObjectByClaimNo(claimNo);
			this.resultList.add(gaaInvestigativeObject);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException(1, 3, "列印錯誤", "請輸入正確的賠案號碼！");
		}
		return SUCCESS;
	}

	/***
	 * 和解書
	 * @return
	 * @throws Exception
	 */
	public String printGAAReconciliation() throws Exception {
		HttpServletRequest request = super.getRequest();
		String claimNo = request.getParameter("claimNo");
		if("".equals(DataUtils.dbNullToEmpty(claimNo))){
			throw new UserException(1, 3, "請輸入賠案號碼！","");
		}
		try {
			GAAReconciliationObject gaaReconciliationObject =  this.gaaPrintViewHelper.findGAAReconciliationObjectByClaimNo(claimNo);
			this.resultList.add(gaaReconciliationObject);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException(1, 3, "列印錯誤", "請輸入正確的賠案號碼！");
		}
		return SUCCESS;
	}

	/***
	 * 残余物理算书
	 * @return
	 * @throws Exception
	 */
	public String printGAARemnant() throws Exception {
		HttpServletRequest request = super.getRequest();
		String compensateNo = request.getParameter("compensateNo");
		if("".equals(DataUtils.dbNullToEmpty(compensateNo))){
			throw new UserException(1, 3, "請輸入賠款計算書號碼！","");
		}
		try {
			GAARemnantObject gaaRemnantObject =  this.gaaPrintViewHelper.findGAARemnantObjectByCompensateNo(compensateNo.trim());
			this.resultList.add(gaaRemnantObject);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException(1, 3, "列印錯誤", "請輸入正確的賠款計算書號碼！");
		}
		return SUCCESS;
	}

	/***
	 * 理賠計算書
	 * @return
	 * @throws Exception
	 */
	public String printGAACompensate() throws Exception {
		HttpServletRequest request = super.getRequest();
		String compensateNo = request.getParameter("compensateNo");
		if("".equals(DataUtils.dbNullToEmpty(compensateNo))){
			throw new UserException(1, 3, "請輸入賠款計算書號碼！","");
		}
		try {
			GAACompensateObject gaaCompensateObject =  this.gaaPrintViewHelper.findGAACompensateObjectByCompensateNo(compensateNo.trim());
			//判断公證公司是否显示。
			param.put("DISPLAYFLAG", new Boolean(gaaCompensateObject.getCompensateSubreport2Object().size() > 0));
			this.resultList.add(gaaCompensateObject);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException(1, 3, "列印錯誤", "請輸入正確的賠款計算書號碼！");
		}
		return SUCCESS;
	}
	/***
	 * 理賠計算書（再保用）
	 * @return
	 * @throws Exception
	 */
	public String printGAAReinsCompensate() throws Exception {
		HttpServletRequest request = super.getRequest();
		String compensateNo = request.getParameter("compensateNo");
		if("".equals(DataUtils.dbNullToEmpty(compensateNo))){
			throw new UserException(1, 3, "請輸入賠款計算書號碼！","");
		}
		try {
			GAAReinsCompensateObject gaaCompensateObject =  this.gaaPrintViewHelper.findGAAReinsCompensateObjectByCompensateNo(compensateNo.trim());
			//判断公證公司是否显示。
			param.put("compensateSubreport", gaaCompensateObject.getCompensateSubreport());
			this.resultList.add(gaaCompensateObject);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException(1, 3, "列印錯誤", "請輸入正確的賠款計算書號碼！");
		}
		return SUCCESS;
	}
	
	public List<Object> getResultList() {
		if (CommonUtils.isEmpty(resultList)) {
			resultList.add(new JREmptyDataSource());
		}
		return resultList;
	}

	public void setResultList(List<Object> resultList) {
		this.resultList = resultList;
	}

	public List<Object> getList() {
		return resultList;
	}

	public void setList(List<Object> list) {
		this.resultList = list;
	}

	public Map<String, Object> getParam() {
		return param;
	}

	public void setParam(Map<String, Object> param) {
		this.param = param;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public GAAPrintViewHelper getGaaPrintViewHelper() {
		return gaaPrintViewHelper;
	}

	public void setGaaPrintViewHelper(GAAPrintViewHelper gaaPrintViewHelper) {
		this.gaaPrintViewHelper = gaaPrintViewHelper;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
