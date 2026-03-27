package com.sinosoft.claim.print.web;

import ins.framework.web.Struts2Action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JREmptyDataSource;

import com.opensymphony.xwork2.Preparable;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.print.util.ShipPrintViewHelper;
import com.sinosoft.claim.print.vo.CargoClaimApplicationObject;
import com.sinosoft.claim.print.vo.CargoCommissionedObject;
import com.sinosoft.claim.print.vo.CargoSubrogationObject;
import com.sinosoft.claim.print.vo.CargoTransferObject;
import com.sinosoft.claim.print.vo.ShipClaimApplicationObject;
import com.sinosoft.claim.print.vo.ShipCommissionedObject;
import com.sinosoft.claim.print.vo.ShipCompensateObject;
import com.sinosoft.claim.print.vo.ShipContractObject;
import com.sinosoft.claim.print.vo.ShipReceiptObject;
import com.sinosoft.claim.print.vo.ShipReconciliationObject;
import com.sinosoft.claim.print.vo.ShipRemittanceObject;
import com.sinosoft.claim.print.vo.ShipRemnantObject;
import com.sinosoft.claim.print.vo.ShipRevocationObject;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.sysframework.common.util.DataUtils;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * 火險列印處理
 * @author 中科軟
 * 
 */
public class ShipPrintAction extends Struts2Action implements Preparable{
	private static final long serialVersionUID = 1L;
	/** 报表jasper文件路径*/
	private String path;
	/** 报表列印  数据对象*/
	private List<Object> resultList = new ArrayList<Object>();
	/** 传递的参数*/
	private Map<String, Object> param = new HashMap<String, Object>();
	/** 收集列印 所需数据*/
	private ShipPrintViewHelper shipPrintViewHelper;
	/** 理算 */
	private PrpLcompensateService prpLcompensateService;

	private CodeService codeService;
	
	@Override
	public void prepare() throws Exception {
		path = super.getRequest().getSession().getServletContext().getRealPath("")+"/printReport/";
		param.put("IMGPATH", this.path + "image/logo.png");
		param.put("SUBREPORT_DIR", this.path + "Ship/");
	}
	
	/**
	 * 貨物運輸險索賠函
	 * @return
	 * @throws UserException 
	 */
	public String printCargoClaimApplication() throws UserException{	
		HttpServletRequest request = super.getRequest();
		String policyNo = request.getParameter("policyNo");
		if("".equals(DataUtils.dbNullToEmpty(policyNo))){
			throw new UserException(1, 3, "請輸入保單號碼！","");
		}
		try{
			CargoClaimApplicationObject cargoClaimApplicationObject = this.shipPrintViewHelper.findCargoClaimApplicationObjectByPolicyNo(policyNo.trim());
			this.resultList.add(cargoClaimApplicationObject);
		}catch(Exception e){
			e.printStackTrace();
			throw new UserException(1, 3, "請輸入正確的保單號碼！","");
		}
		return SUCCESS;
	}
	
	/**
	 * 貨物運輸險委託公證申請單
	 * @return
	 * @throws UserException 
	 */
	public String printCargoCommissioned() throws UserException{
		HttpServletRequest request = super.getRequest();
		String registNo = request.getParameter("registNo");
		if("".equals(DataUtils.dbNullToEmpty(registNo))){
			throw new UserException(1, 3, "請輸入備案號碼！","");
		}
		try{
			CargoCommissionedObject cargoCommissionedObject = this.shipPrintViewHelper.findCargoCommissionedObjectByRegistNo(registNo.trim());
			this.resultList.add(cargoCommissionedObject);
			resultList.add(new JREmptyDataSource());
		}catch(Exception e){
			e.printStackTrace();
			throw new UserException(1, 3, "請輸入正確的備案號碼！","");
		}
		return SUCCESS;
	}
	/**
	 * 貨物運輸險代位追償權利書
	 * @return
	 * @throws UserException 
	 */
	public String printCargoSubrogation() throws UserException{
		HttpServletRequest request = super.getRequest();
		String claimNo = request.getParameter("claimNo");
		if("".equals(DataUtils.dbNullToEmpty(claimNo))){
			throw new UserException(1, 3, "請輸入賠案號碼！","");
		}
		try{
			CargoSubrogationObject cargoSubrogationObject = this.shipPrintViewHelper.findCargoSubrogationObjectByClaimNo(claimNo.trim());
			this.resultList.add(cargoSubrogationObject);
		}catch(Exception e){
			e.printStackTrace();
			throw new UserException(1, 3, "請輸入正確的賠案號碼！","");
		}
		return SUCCESS;
	}
	
	/**
	 * 貨物運輸險權利轉讓書
	 * @return
	 * @throws UserException 
	 */
	public String printCargoTransfer() throws UserException{
		HttpServletRequest request = super.getRequest();
		String policyNo = request.getParameter("policyNo");
		if("".equals(DataUtils.dbNullToEmpty(policyNo))){
			throw new UserException(1, 3, "請輸入保單號碼！","");
		}
		try{
			CargoTransferObject cargoTransferObject = this.shipPrintViewHelper.findCargoTransferObjectByPolicyNo(policyNo.trim());
			this.resultList.add(cargoTransferObject);
		}catch(Exception e){
			e.printStackTrace();
			throw new UserException(1, 3, "請輸入正確的保單號碼！","");
		}
		return SUCCESS;
	}
	/***
	 * 貨物運輸險賠款理算書
	 * @return
	 * @throws UserException
	 */
	public String printCargoCompensate() throws Exception{
		try{
			this.printShipCompensate();
		}catch(Exception e){
			e.printStackTrace();
			throw new UserException(1, 3, "請輸入正確的貨物運輸險理算書號碼！","");
		}
		return SUCCESS;
	}
	/**
	 * 貨物運輸險追償理算書
	 * @return
	 * @throws Exception 
	 */
	public String printCargoRecovery() throws Exception{
		HttpServletRequest request = super.getRequest();
		String compensateNo = request.getParameter("compensateNo");
		PrpLcompensate prpLcompensate = null;
		if (DataUtils.emptyToNull(compensateNo) != null) {
			String conditions = " compensateNo = '"+compensateNo+"' and exists (select 0 from uticodetransfer u where u.outercode = riskcode and u.riskType = 'Y') ";
			List<PrpLcompensate> list = this.prpLcompensateService.findByConditions(conditions);
			if (list == null || list.isEmpty()) {
				throw new UserException(1, 3, "","請輸入正確的貨物運輸險追償理算書！");
			}
			prpLcompensate = list.get(0);
		}
		try{
			resultList.add(this.shipPrintViewHelper.findCargoRecovery(prpLcompensate));
		}catch(Exception e){
			e.printStackTrace();
			throw new UserException(1, 3,"","請輸入正確的貨物運輸險追償理算書！");
		}
		return SUCCESS;
	}
	/**
	 * 貨物運輸險殘餘物理算書
	 * @return
	 * @throws UserException
	 */
	public String printCargoRemnant() throws UserException{
		HttpServletRequest request = super.getRequest();
		String compensateNo = request.getParameter("compensateNo");
		if("".equals(DataUtils.dbNullToEmpty(compensateNo))){
			throw new UserException(1, 3, "請輸入計算書號碼！","");
		}
		try{
			ShipRemnantObject shipRemnantObject = this.shipPrintViewHelper.findShipRemnantObjectByCompensateNo(compensateNo);
			resultList.add(shipRemnantObject);
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
	public String printShipClaimApplication() throws UserException{
		HttpServletRequest request = super.getRequest();
		String registNo = request.getParameter("registNo");
		if("".equals(DataUtils.dbNullToEmpty(registNo))){
			throw new UserException(1, 3, "請輸入備案號碼！","");
		}
		try{
			ShipClaimApplicationObject shipClaimApplicationObject = this.shipPrintViewHelper.findShipClaimApplicationObjectByRegistNo(registNo.trim());
			this.resultList.add(shipClaimApplicationObject);
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
	public String printShipRemittance() throws UserException{
		HttpServletRequest request = super.getRequest();
		String claimNo = request.getParameter("claimNo");
		if("".equals(DataUtils.dbNullToEmpty(claimNo))){
			throw new UserException(1, 3, "請輸入賠案號碼！","");
		}
		try{
			ShipRemittanceObject shipRemittanceObject = this.shipPrintViewHelper.findShipRemittanceObjectByClaimNo(claimNo);
			this.resultList.add(shipRemittanceObject);
		}catch(Exception e){
			e.printStackTrace();
			throw new UserException(1, 3, "請輸入正確的賠案號碼！","");
		}
		return SUCCESS;
	}
	/***
	 * 賠款同意書暨領款收據
	 * @return
	 * @throws UserException
	 */
	public String printShipReceipt() throws UserException{
		HttpServletRequest request = super.getRequest();
		String claimNo = request.getParameter("claimNo");
		if("".equals(DataUtils.dbNullToEmpty(claimNo))){
			throw new UserException(1, 3, "請輸入賠案號碼！","");
		}
		try{
			ShipReceiptObject shipReceiptObject = this.shipPrintViewHelper.findShipReceiptObjectByClaimNo(claimNo);
			this.resultList.add(shipReceiptObject);
		}catch(Exception e){
			e.printStackTrace();
			throw new UserException(1, 3, "請輸入正確的賠案號碼！","");
		}
		return SUCCESS;
	}
	/**
	 * 委託公證申請單
	 * @return
	 * @throws UserException 
	 */
	public String printShipCommissioned() throws UserException{
		HttpServletRequest request = super.getRequest();
		String registNo = request.getParameter("registNo");
		if("".equals(DataUtils.dbNullToEmpty(registNo))){
			throw new UserException(1, 3, "請輸入備案號碼！","");
		}
		try{
			ShipCommissionedObject shipCommissionedObject = this.shipPrintViewHelper.findShipCommissionedObjectByRegistNo(registNo.trim());
			this.resultList.add(shipCommissionedObject);
		}catch(Exception e){
			e.printStackTrace();
			throw new UserException(1, 3, "請輸入正確的備案號碼！","");
		}
		return SUCCESS;
	}
	/**
	 * 債權讓與契約暨通知書
	 * @return
	 * @throws UserException 
	 */
	public String printShipContract() throws UserException{	
		HttpServletRequest request = super.getRequest();
		String policyNo = request.getParameter("policyNo");
		if("".equals(DataUtils.dbNullToEmpty(policyNo))){
			throw new UserException(1, 3, "請輸入保單號碼！","");
		}
		try{
			ShipContractObject shipContractObject = this.shipPrintViewHelper.findShipContractObjectByPolicyNo(policyNo.trim());
			this.resultList.add(shipContractObject);
		}catch(Exception e){
			e.printStackTrace();
			throw new UserException(1, 3, "請輸入正確的保單號碼！","");
		}
		return SUCCESS;
	}
	/**
	 * 撤銷申請理賠同意書
	 * @return
	 * @throws UserException 
	 */
	public String printShipRevocation() throws UserException{	
		HttpServletRequest request = super.getRequest();
		String policyNo = request.getParameter("policyNo");
		if("".equals(DataUtils.dbNullToEmpty(policyNo))){
			throw new UserException(1, 3, "請輸入保單號碼！","");
		}
		try{
			ShipRevocationObject shipRevocationObject = this.shipPrintViewHelper.findShipRevocationObjectByPolicyNo(policyNo.trim());
			String damageTime = shipRevocationObject.getDamageTime();
			if(DataUtils.emptyToNull(damageTime)!=null){//时间格式YYY-MM-DD
				param.put("MGYEAR", damageTime.substring(0,damageTime.indexOf("-")));
				param.put("MGMONTH", damageTime.substring(damageTime.indexOf("-")+1,damageTime.lastIndexOf("-")));
				param.put("MGDAY", damageTime.substring(damageTime.lastIndexOf("-")+1));
			}
			this.resultList.add(shipRevocationObject);
		}catch(Exception e){
			e.printStackTrace();
			throw new UserException(1, 3, "請輸入正確的保單號碼！","");
		}
		return SUCCESS;
	}
	/**
	 * 賠款理算書
	 * @return
	 * @throws UserException 
	 */
	public String printShipCompensate() throws Exception{
		HttpServletRequest request = super.getRequest();
		String compensateNo = request.getParameter("compensateNo");
		PrpLcompensate prpLcompensate = null;
		if (DataUtils.emptyToNull(compensateNo) != null) {
			String conditions = " compensateNo = '"+compensateNo+"' and exists (select 0 from uticodetransfer u where u.outercode = riskcode and u.riskType = 'Y') ";
			List<PrpLcompensate> list = this.prpLcompensateService.findByConditions(conditions);
			if (list == null || list.isEmpty()) {
				throw new UserException(1, 3, "","請輸入正確的水險理算書號碼！");
			}
			prpLcompensate = list.get(0);
		}
		try{
			ShipCompensateObject shipCompensateObject = this.shipPrintViewHelper.printShipCompensate(prpLcompensate);
			this.resultList.add(shipCompensateObject);
		}catch(Exception e){
			e.printStackTrace();
			throw new UserException(1, 3, "請輸入正確的水險理算書號碼！","");
		}
		return SUCCESS;
	}
	/**
	 * 和解書
	 * @return
	 * @throws UserException 
	 */
	public String printShipReconciliation() throws UserException{
		HttpServletRequest request = super.getRequest();
		String claimNo = request.getParameter("claimNo");
		if("".equals(DataUtils.dbNullToEmpty(claimNo))){
			throw new UserException(1, 3, "請輸入賠案號碼！","");
		}
		try{
			ShipReconciliationObject shipReconciliationObject = this.shipPrintViewHelper.findShipReconciliationObjectByCompensateNo(claimNo.trim());
			this.resultList.add(shipReconciliationObject);
		}catch(Exception e){
			e.printStackTrace();
			throw new UserException(1, 3, "請輸入正確的賠案號碼！","");
		}
		return SUCCESS;
	}
	/**
	 * 追償理算書
	 * @return
	 * @throws Exception 
	 */
	public String printShipRecovery() throws Exception{
		HttpServletRequest request = super.getRequest();
		String compensateNo = request.getParameter("compensateNo");
		PrpLcompensate prpLcompensate = null;
		if (DataUtils.emptyToNull(compensateNo) != null) {
			String conditions = " compensateNo = '"+compensateNo+"' and exists (select 0 from uticodetransfer u where u.outercode = riskcode and u.riskType = 'Y') ";
			List<PrpLcompensate> list = this.prpLcompensateService.findByConditions(conditions);
			if (list == null || list.isEmpty()) {
				throw new UserException(1, 3, "","請輸入正確的水險追償理算書！");
			}
			prpLcompensate = list.get(0);
		}
		try{
			resultList.add(this.shipPrintViewHelper.findCargoRecovery(prpLcompensate));
		}catch(Exception e){
			e.printStackTrace();
			throw new UserException(1, 3,"","請輸入正確的水險追償理算書！");
		}
		return SUCCESS;
	}
	/***
	 * 殘餘物理算書
	 * @return
	 * @throws UserException
	 */
	public String printShipRemnant() throws UserException{
		HttpServletRequest request = super.getRequest();
		String compensateNo = request.getParameter("compensateNo");
		if("".equals(DataUtils.dbNullToEmpty(compensateNo))){
			throw new UserException(1, 3, "請輸入計算書號碼！","");
		}
		try{
			ShipRemnantObject shipRemnantObject = this.shipPrintViewHelper.findShipRemnantObjectByCompensateNo(compensateNo);
			this.resultList.add(shipRemnantObject);
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

	public ShipPrintViewHelper getShipPrintViewHelper() {
		return shipPrintViewHelper;
	}

	public void setShipPrintViewHelper(ShipPrintViewHelper shipPrintViewHelper) {
		this.shipPrintViewHelper = shipPrintViewHelper;
	}

	public PrpLcompensateService getPrpLcompensateService() {
		return prpLcompensateService;
	}

	public void setPrpLcompensateService(PrpLcompensateService prpLcompensateService) {
		this.prpLcompensateService = prpLcompensateService;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}
	
}
