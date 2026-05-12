package com.sinosoft.claim.print.web;

import ins.framework.web.Struts2Action;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import com.opensymphony.xwork2.Preparable;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.print.util.LiabPrintViewHelper;
import com.sinosoft.claim.print.vo.LiabCardAppendObject;
import com.sinosoft.claim.print.vo.LiabCardComplexObject;
import com.sinosoft.claim.print.vo.LiabCardObject;
import com.sinosoft.claim.print.vo.LiabClaimApplicationObject;
import com.sinosoft.claim.print.vo.LiabCommissionedObject;
import com.sinosoft.claim.print.vo.LiabCompensateObject;
import com.sinosoft.claim.print.vo.LiabContractObject;
import com.sinosoft.claim.print.vo.LiabInvestigativeObject;
import com.sinosoft.claim.print.vo.LiabNotificationObject;
import com.sinosoft.claim.print.vo.LiabReceiptObject;
import com.sinosoft.claim.print.vo.LiabReconciliationObject;
import com.sinosoft.claim.print.vo.LiabRemittanceObject;
import com.sinosoft.claim.print.vo.LiabRemnantObject;
import com.sinosoft.claim.print.vo.LiabRevocationObject;
import com.sinosoft.claim.print.vo.LiabSingleNoteObject;
import com.sinosoft.sysframework.common.util.DataUtils;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * 責任險列印處理
 * @author 中科軟
 */
public class LiabPrintAction extends Struts2Action implements Preparable {
	private static final long serialVersionUID = 1L;
	/** 报表jasper文件路径 */
	private String path;
	/** 报表列印 数据对象 */
	private List<Object> resultList = new ArrayList<Object>();
	/** 传递的参数 */
	private Map<String, Object> param = new HashMap<String, Object>();
	/** 收集 責任险列印 所需数据 */
	private LiabPrintViewHelper liabPrintViewHelper;

	@Override
	public void prepare() throws Exception {
		path = super.getRequest().getSession().getServletContext().getRealPath("") + "/printReport/";
		param.put("IMGPATH", this.path + "image/logo.png");
		param.put("SUBREPORT_DIR", this.path + "Liab/");
	}

	/**
	 * 理賠申請書
	 * @return
	 * @throws UserException
	 */
	public String printLiabClaimApplication() throws UserException {
		HttpServletRequest request = super.getRequest();
		String claimNo = request.getParameter("claimNo");
		if ("".equals(DataUtils.dbNullToEmpty(claimNo))) {
			throw new UserException(1, 3, "請輸入賠案號碼！", "");
		}
		try {
			LiabClaimApplicationObject liabClaimApplicationObject = this.liabPrintViewHelper.findLiabClaimApplicationObjectByClaimNo(claimNo.trim());
			this.resultList.add(liabClaimApplicationObject);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException(1, 3, "請輸入正確的賠案號碼！", "");
		}
		return SUCCESS;
	}

	/**
	 * 信用卡不便險理賠申請書
	 * @return
	 * @throws UserException
	 */
	public String printLiabCard() throws UserException {
		// 模板文件路径
		String path = super.getRequest().getSession().getServletContext().getRealPath("") + "/printReport/Liab";
		HttpServletRequest request = super.getRequest();
		String claimNo = request.getParameter("claimNo");
		ServletOutputStream ouputStream = null;
		if ("".equals(DataUtils.dbNullToEmpty(claimNo))) {
			throw new UserException(1, 3, "請輸入賠案號碼！", "");
		}
		try {
			LiabCardObject liabCardObject = this.liabPrintViewHelper.findLiabCardObjectByClaimNo(claimNo.trim());
			this.resultList.add(liabCardObject);
			// 列印
			JasperPrint jasperPrint = JasperFillManager.fillReport(path + "/LiabCard.jasper", param, new JRBeanCollectionDataSource(this.resultList));
			JasperPrint jasperPrint2 = JasperFillManager.fillReport(path + "/LiabCard_01.jasper", param, new JRBeanCollectionDataSource(this.resultList));
			List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
			jasperPrintList.add(jasperPrint);
			jasperPrintList.add(jasperPrint2);

			// 获取输出字节流
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, byteArrayOutputStream);
			exporter.exportReport();
			byte[] bytes = byteArrayOutputStream.toByteArray();
			getResponse().setContentType("application/pdf");
			getResponse().setContentLength(bytes.length);
			ouputStream = getResponse().getOutputStream();
			ouputStream.write(bytes, 0, bytes.length);
			ouputStream.flush();
			ouputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException(1, 3, "請輸入正確的賠案號碼！", "");
		} finally {
			if (ouputStream != null)
				try {
					ouputStream.close();
				} catch (IOException e) {
					System.out.println("close:" + e.toString());
				}
		}
		return NONE;
	}

	/**
	 * 信用卡附加旅平險理賠申請書
	 * @return
	 * @throws UserException
	 */
	public String printLiabCardAppend() throws UserException {
		// 模板文件路径
		String path = super.getRequest().getSession().getServletContext().getRealPath("") + "/printReport/Liab";
		HttpServletRequest request = super.getRequest();
		String claimNo = request.getParameter("claimNo");
		ServletOutputStream ouputStream = null;
		if ("".equals(DataUtils.dbNullToEmpty(claimNo))) {
			throw new UserException(1, 3, "請輸入賠案號碼！", "");
		}
		try {
			LiabCardAppendObject liabCardAppendObject = this.liabPrintViewHelper.findLiabCardAppendObjectByClaimNo(claimNo.trim());
			this.resultList.add(liabCardAppendObject);
			// 列印
			JasperPrint jasperPrint = JasperFillManager.fillReport(path + "/LiabCardAppend.jasper", param, new JRBeanCollectionDataSource(this.resultList));
			JasperPrint jasperPrint2 = JasperFillManager.fillReport(path + "/LiabCardAppend_01.jasper", param, new JRBeanCollectionDataSource(this.resultList));
			List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
			jasperPrintList.add(jasperPrint);
			jasperPrintList.add(jasperPrint2);

			// 获取输出字节流
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, byteArrayOutputStream);
			exporter.exportReport();
			byte[] bytes = byteArrayOutputStream.toByteArray();
			getResponse().setContentType("application/pdf");
			getResponse().setContentLength(bytes.length);
			ouputStream = getResponse().getOutputStream();
			ouputStream.write(bytes, 0, bytes.length);
			ouputStream.flush();
			ouputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException(1, 3, "請輸入正確的賠案號碼！", "");
		}
		return SUCCESS;
	}

	/**
	 * 信用卡綜合保險全球購物理賠申請書
	 * @return
	 * @throws UserException
	 */
	public String printLiabCardComplex() throws UserException {
		// 模板文件路径
		String path = super.getRequest().getSession().getServletContext().getRealPath("") + "/printReport/Liab";
		HttpServletRequest request = super.getRequest();
		String claimNo = request.getParameter("claimNo");
		ServletOutputStream ouputStream = null;
		if ("".equals(DataUtils.dbNullToEmpty(claimNo))) {
			throw new UserException(1, 3, "請輸入賠案號碼！", "");
		}
		try {
			LiabCardComplexObject liabCardComplexObject = this.liabPrintViewHelper.findLiabCardComplexObjectByClaimNo(claimNo.trim());
			this.resultList.add(liabCardComplexObject);
			// 列印
			JasperPrint jasperPrint = JasperFillManager.fillReport(path + "/LiabCardComplex.jasper", param, new JRBeanCollectionDataSource(this.resultList));
			JasperPrint jasperPrint2 = JasperFillManager.fillReport(path + "/LiabCardComplex_01.jasper", param, new JRBeanCollectionDataSource(this.resultList));
			JasperPrint jasperPrint3 = JasperFillManager.fillReport(path + "/LiabCardComplex_02.jasper", param, new JRBeanCollectionDataSource(this.resultList));
			List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
			jasperPrintList.add(jasperPrint);
			jasperPrintList.add(jasperPrint2);
			jasperPrintList.add(jasperPrint3);

			// 获取输出字节流
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, byteArrayOutputStream);
			exporter.exportReport();
			byte[] bytes = byteArrayOutputStream.toByteArray();
			getResponse().setContentType("application/pdf");
			getResponse().setContentLength(bytes.length);
			ouputStream = getResponse().getOutputStream();
			ouputStream.write(bytes, 0, bytes.length);
			ouputStream.flush();
			ouputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException(1, 3, "請輸入正確的賠案號碼！", "");
		}
		return SUCCESS;
	}

	/***
	 * 匯款同意書
	 * @return
	 * @throws Exception
	 */
	public String printLiabRemittance() throws Exception {
		HttpServletRequest request = super.getRequest();
		String compensateNo = request.getParameter("compensateNo");
		if ("".equals(DataUtils.dbNullToEmpty(compensateNo))) {
			throw new UserException(1, 3, "請輸入賠款計算書號碼！", "");
		}
		try {
			LiabRemittanceObject liabRemittanceObject = this.liabPrintViewHelper.findLiabRemittanceObjectByCompensateNo(compensateNo);
			this.resultList.add(liabRemittanceObject);
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
	public String printLiabReceipt() throws Exception {
		HttpServletRequest request = super.getRequest();
		String compensateNo = request.getParameter("compensateNo");
		if ("".equals(DataUtils.dbNullToEmpty(compensateNo))) {
			throw new UserException(1, 3, "請輸入賠款計算書號碼！", "");
		}
		try {
			LiabReceiptObject liabReceiptObject = this.liabPrintViewHelper.findLiabReceiptObjectByClaimNo(compensateNo);
			this.resultList.add(liabReceiptObject);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException(1, 3, "列印錯誤", "請輸入賠款計算書號碼！");
		}
		return SUCCESS;
	}

	/***
	 * 賠委託公證申請單
	 * @return
	 * @throws Exception
	 */
	public String printLiabCommissioned() throws Exception {
		HttpServletRequest request = super.getRequest();
		HttpSession session = super.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		String claimNo = request.getParameter("claimNo");
		if ("".equals(DataUtils.dbNullToEmpty(claimNo))) {
			throw new UserException(1, 3, "請輸入賠案號碼！", "");
		}
		try {
			LiabCommissionedObject liabCommissionedObject = this.liabPrintViewHelper.findLiabCommissionedObjectByClaimNo(claimNo, user.getComName(), user.getUserName());
			this.resultList.add(liabCommissionedObject);
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
	public String printLiabContract() throws Exception {
		try {
			HttpServletRequest request = super.getRequest();
			String claimNo = request.getParameter("claimNo");
			if ("".equals(DataUtils.dbNullToEmpty(claimNo))) {
				throw new UserException(1, 3, "請輸入賠案號碼！", "");
			}
			LiabContractObject liabContractObject = this.liabPrintViewHelper.findLiabContractObjectByClaimNo(claimNo);
			this.resultList.add(liabContractObject);
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
	public String printLiabRevocation() throws Exception {
		HttpServletRequest request = super.getRequest();
		String claimNo = request.getParameter("claimNo");
		if ("".equals(DataUtils.dbNullToEmpty(claimNo))) {
			throw new UserException(1, 3, "請輸入賠案號碼！", "");
		}
		try {
			LiabRevocationObject liabRevocationObject = this.liabPrintViewHelper.findLiabRevocationObjectByClaimNo(claimNo);
			this.resultList.add(liabRevocationObject);
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
	public String printLiabNotification() throws Exception {
		HttpServletRequest request = super.getRequest();
		String claimNo = request.getParameter("claimNo");
		if ("".equals(DataUtils.dbNullToEmpty(claimNo))) {
			throw new UserException(1, 3, "請輸入賠案號碼！", "");
		}
		try {
			LiabNotificationObject liabNotificationObject = this.liabPrintViewHelper.findLiabNotificationObjectByClaimNo(claimNo);
			this.resultList.add(liabNotificationObject);
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
	public String printLiabInvestigative() throws Exception {
		HttpServletRequest request = super.getRequest();
		String claimNo = request.getParameter("claimNo");
		if ("".equals(DataUtils.dbNullToEmpty(claimNo))) {
			throw new UserException(1, 3, "請輸入賠案號碼！", "");
		}
		try {
			LiabInvestigativeObject liabInvestigativeObject = this.liabPrintViewHelper.findLiabInvestigativeObjectByClaimNo(claimNo);
			this.resultList.add(liabInvestigativeObject);
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
	public String printLiabReconciliation() throws Exception {
		HttpServletRequest request = super.getRequest();
		String claimNo = request.getParameter("claimNo");
		if ("".equals(DataUtils.dbNullToEmpty(claimNo))) {
			throw new UserException(1, 3, "請輸入賠案號碼！", "");
		}
		try {
			LiabReconciliationObject liabReconciliationObject = this.liabPrintViewHelper.findLiabReconciliationObjectByClaimNo(claimNo);
			this.resultList.add(liabReconciliationObject);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException(1, 3, "列印錯誤", "請輸入正確的賠案號碼！");
		}
		return SUCCESS;
	}

	/**
	 * 旅行業責任保險理賠照會單
	 * @return
	 * @throws UserException
	 */
	public String printLiabSingleNote() throws UserException {
		HttpServletRequest request = super.getRequest();
		String claimNo = request.getParameter("claimNo");
		if ("".equals(DataUtils.dbNullToEmpty(claimNo))) {
			throw new UserException(1, 3, "請輸入賠案號碼！", "");
		}
		try {
			LiabSingleNoteObject liabSingleNoteObject = this.liabPrintViewHelper.findLiabSingleNoteObjectByClaimNo(claimNo.trim());
			this.resultList.add(liabSingleNoteObject);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException(1, 3, "請輸入正確的賠案號碼！", "");
		}
		return SUCCESS;
	}

	/**
	 * 應備文件
	 * @return
	 */
	public String printLiabDocument() {
		resultList.add(new JREmptyDataSource());
		return SUCCESS;
	}

	/**
	 * 信用卡綜合保險應備文件
	 * @return
	 */
	public String printLiabCardDocument() {
		resultList.add(new JREmptyDataSource());
		return SUCCESS;
	}

	/***
	 * 残余物理算书
	 * @return
	 * @throws Exception
	 */
	public String printLiabRemnant() throws Exception {
		HttpServletRequest request = super.getRequest();
		String compensateNo = request.getParameter("compensateNo");
		if ("".equals(DataUtils.dbNullToEmpty(compensateNo))) {
			throw new UserException(1, 3, "請輸入賠款計算書號碼！", "");
		}
		try {
			LiabRemnantObject liabRemnantObject = this.liabPrintViewHelper.findLiabRemnantObjectByCompensateNo(compensateNo);
			this.resultList.add(liabRemnantObject);
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
	public String printLiabCompensate() throws Exception {
		HttpServletRequest request = super.getRequest();
		String compensateNo = request.getParameter("compensateNo");
		if ("".equals(DataUtils.dbNullToEmpty(compensateNo))) {
			throw new UserException(1, 3, "請輸入賠款計算書號碼！", "");
		}
		try {
			LiabCompensateObject liabCompensateObject = this.liabPrintViewHelper.findLiabCompensateObjectByCompensateNo(compensateNo);
			param.put("DISPLAYFLAG", new Boolean(liabCompensateObject.getCompensateSubreport2Object().size() > 0));
			this.resultList.add(liabCompensateObject);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException(1, 3, "列印錯誤", "請輸入正確的賠款計算書號碼！");
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

	public LiabPrintViewHelper getLiabPrintViewHelper() {
		return liabPrintViewHelper;
	}

	public void setLiabPrintViewHelper(LiabPrintViewHelper liabPrintViewHelper) {
		this.liabPrintViewHelper = liabPrintViewHelper;
	}
}
