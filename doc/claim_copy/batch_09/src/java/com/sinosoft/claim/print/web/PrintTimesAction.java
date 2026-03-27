package com.sinosoft.claim.print.web;

import ins.framework.web.Struts2Action;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

import com.opensymphony.xwork2.Preparable;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.print.util.PropPrintViewHelper;
import com.sinosoft.claim.print.vo.JasperPrintObject;
import com.sinosoft.claim.print.vo.PropPaymentAcceptanceObject;
import com.sinosoft.sysframework.common.util.DataUtils;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * 多次列印處理
 * @author 中科軟
 *
 */
public class PrintTimesAction extends Struts2Action implements Preparable{
	private static final long serialVersionUID = 1L;
	/** 报表jasper文件路径*/
	private String path;
	/** 收集 火险列印 所需数据*/
	private PropPrintViewHelper propPrintViewHelper;

	@Override
	public void prepare() throws Exception {
		path = super.getRequest().getSession().getServletContext().getRealPath("")+"/printReport/";
	}
	/**
	 * 循环打印jasperPrintObject,实现 多张报表打印多次
	 * @param jasperPrintObjectList
	 * @throws Exception
	 */
	@SuppressWarnings({ "deprecation", "unchecked", "rawtypes" })
	private void exportReportToPdf(List<JasperPrintObject> jasperPrintObjectList) throws Exception{
		List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
		JasperReport jasperReport = null;
		JasperPrint jasperPrint = null;
		JRBeanCollectionDataSource dataSource = null;
		for(JasperPrintObject jasperPrintObject : jasperPrintObjectList){
 			for(Iterator it = jasperPrintObject.getResultList().iterator();it.hasNext();){
				jasperReport = (JasperReport) JRLoader.loadObject(jasperPrintObject.getPath());
				ArrayList tempList = new ArrayList();
				tempList.add(it.next());
				dataSource = new JRBeanCollectionDataSource(tempList);
				jasperPrint = JasperFillManager.fillReport(jasperReport,jasperPrintObject.getParameters(),dataSource);
				jasperPrintList.add(jasperPrint);
			}
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    JRPdfExporter exporter = new JRPdfExporter();
	    exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST,jasperPrintList);
	    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
	    exporter.exportReport();
	    byte[] bytes= baos.toByteArray();
	    getResponse().setContentType("application/pdf");
		getResponse().setContentLength(bytes.length);
		ServletOutputStream ouputStream = getResponse().getOutputStream();
		try{
			ouputStream.write(bytes, 0, bytes.length);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			ouputStream.flush();
			ouputStream.close();
		}
	}
	/**
	 * 火災保險賠款接受書(此方法已不用，但可以作為多次打印的例子)
	 * @return
	 * @throws  UserException ,Exception 
	 */
//	public void printTimesPropPaymentAcceptance() throws UserException,Exception{
//		HttpServletRequest request = super.getRequest();
//		String compensateNo = request.getParameter("compensateNo");
//		if(CommonUtils.isEmpty(compensateNo)){
//			throw new UserException(1, 3, "請輸入計算書號碼！","");
//		}
//		Map<String,Object> parameters = new HashMap<String, Object>();
//		parameters.put("IMGPATH", this.path + "image/logo.jpg");
//		parameters.put("SUBREPORT_DIR", this.path + "Prop/");
//		List<PropPaymentAcceptanceObject> paymentAcceptanceObjectList = null;
//		//暂未收集完全数据
//		try{
//			paymentAcceptanceObjectList = this.propPrintViewHelper.findPropPaymentAcceptanceObjectListByCompensateNo(compensateNo.trim());
//			List<JasperPrintObject> jasperPrintObjectList = new ArrayList<JasperPrintObject>();
//			JasperPrintObject jasperPrintObject = new JasperPrintObject();
//			jasperPrintObject.setPath(this.path + "Prop/PropPaymentAcceptance.jasper");
//			jasperPrintObject.setParameters(parameters);
//			jasperPrintObject.setResultList(paymentAcceptanceObjectList);
//			jasperPrintObjectList.add(jasperPrintObject);
//			exportReportToPdf(jasperPrintObjectList);
//		}catch (Exception e){
//			e.printStackTrace();
//			throw new UserException(1, 3, "請輸入正確的計算書號碼！","");
//		}
//	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public PropPrintViewHelper getPropPrintViewHelper() {
		return propPrintViewHelper;
	}
	public void setPropPrintViewHelper(PropPrintViewHelper propPrintViewHelper) {
		this.propPrintViewHelper = propPrintViewHelper;
	}
	
}
