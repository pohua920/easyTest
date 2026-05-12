package com.sinosoft.claim.common.web;

import ins.framework.web.Struts2Action;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.json.JSONObject;

import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.Preparable;
import com.sinosoft.claim.common.service.facade.ClaimPrintService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.DataUtils;
import com.sinosoft.claim.common.util.StringUtils;
import com.sinosoft.claim.compensate.service.facade.CompensateService;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLcompelMedical;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLperson;
import com.sinosoft.claim.schema.model.PrpLprepay;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.claim.schema.service.facade.PrpLprepayService;
import com.sinosoft.claim.schema.service.facade.PrpLregistService;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.exceptionlog.UserException;

public class ClaimPrintAction extends Struts2Action implements Preparable{

	//mantis： CLM0046 ，處理人員：BK007 蘇哲，需求單編號：CLM0046強制險醫療明細提醒同一身分證號與日期重複時有提醒與之前重複的賠次
	private static final Logger log = LoggerFactory.getLogger(ClaimPrintAction.class);
	
	private static final long serialVersionUID = 1L;
	/** 打印类型 */
	private String printType;
	/** 报案号码 */
	private String registNo;
	/** action类型 */
	private String actionType;
	/** 路径 */
	private static String path;
	/** 赔案号 */
	private String claimNo;
	/** 计算书号 */
	private String compensateNo;
	/** 保单号 */
	private String policyNo;
	/** 被保险人 */
	private String insuredName; 
	/** 车牌号码 */
	private String licenseNo; 
	/** 预赔号 */
	private String prepayNo;
	/** 立案主表接口 */
	private PrpLclaimService prpLclaimService;
	/** 理算接口 */
	private PrpLcompensateService prpLcompensateService;
	/*
	 * mantis： CLM0046 ，處理人員：BK007 蘇哲，需求單編號：CLM0046強制險醫療明細提醒同一身分證號與日期重複時有提醒與之前重複的賠次
	 * 處理過程：
	 *  PrpLcompensateService 新增 verifyPrpLcompelMedical 方法，提供 verifyMedicalDetail Ajax 使用，驗證同一身分證號與日期重複資訊
	 *  DAACompelPrpLcompelMedical.js 提供前台驗證，有輸入時提醒與送出驗證及提醒
	 *  ClaimPrintAction 列印功能新增備註頁面(prpLcompelMedicalComment.jrxml)及調整 項目渲染時的提示 (prpLcompelMedical_subreport0.jrxml)
	 */
	/** 理算接口 */
	private CompensateService compensateService;
	/*
	 * mantis： CLM0046 ，處理人員：BK007 蘇哲，需求單編號：CLM0046強制險醫療明細提醒同一身分證號與日期重複時有提醒與之前重複的賠次 -end
	 */
	/** 赔案打印接口 */
	private ClaimPrintService claimPrintService;
	/** 报案主表接口 */
	private PrpLregistService prpLregistService;
	/** 预赔主表接口 */
	private PrpLprepayService prpLprepayService;
	@Override
	public void prepare() throws Exception {
		path = super.getRequest().getSession().getServletContext().getRealPath("")+"/printReport/";
	}
	
	/**
	 * 打印数据有效性检查
	 * @return
	 * @throws Exception
	 */
	public String claimPrintCheck() throws Exception {
		HttpServletResponse response = this.getResponse();
		response.setCharacterEncoding("GBK");
		response.setContentType("text/HTML");
		String result = "";
		try {
			if ("ClaimApplication".equals(printType) || "AccidentPhotoCard".equals(printType) || "CarCase".equals(printType)) {
				PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(claimNo.trim());
				if (prpLclaim != null) {
					result = prpLclaim.getClaimNo();
				}
			} else if ("ClaimStatement".equals(printType) || "ClaimStatementReplevy".equals(printType) || "CompensateReport".equals(printType)) {
				PrpLcompensate prpLcompensate = prpLcompensateService.findPrpLcompensate(compensateNo.trim());
				if (prpLcompensate != null && !prpLcompensate.getCompensateNo().endsWith("00")) {
					result = prpLcompensate.getCompensateNo();
				}
			} else if ("check".equals(printType)) {
				PrpLregist prpLregist = prpLregistService.findPrpLregist(registNo.trim());
				if (prpLregist != null) {
					result = prpLregist.getRegistNo();
				}
			} else if ("BzPay".equals(printType)) {
				PrpLprepay prpLprepay = prpLprepayService.findPrpLprepay(prepayNo.trim());
				if (prpLprepay != null) {
					result = prpLprepay.getPreCompensateNo();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		response.getWriter().print(result);
		return NONE;
	}

	/**
	 * 理赔打印主函数
	 * @return
	 * @throws Exception
	 */
	public void claimPrint() {
		try {
			if (!CommonUtils.isEmpty(printType)) {
				// 查勘
				if (printType.equals("check")) {
					this.checkPrint();
				} else if (printType.equals("LossCarDetail")) {
					this.lossCarPrint();
				} else if (printType.equals("CarClaim")) {
					this.carClaim();
				} else if (printType.equals("BzPay")) {
					this.bzPay();
				} else if (printType.equals("CarCase")) {
					this.carCase();
				} else if (printType.equals("ClaimStatement")) {
					this.claimStatement();
				} else if (printType.equals("ClaimApplication")) {
					this.claimApplication();
				} else if (printType.equals("CompensateReport")) {
					this.compensateReport();
				} else if (printType.equals("Regist")) {
					this.regist();
				} else if (printType.equals("CustomerInterview")) {
					this.customerInterview();
				} else if (printType.equals("AccidentPhotoCard")) {
					this.accidentPhotoCard();
				} else if (printType.equals("ClaimStatementReplevy")) {
					this.claimStatementReplevy();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查勘打印
	 * @throws Exception
	 */
	public void checkPrint() throws Exception {
		Map<String, Object> emptyHashMap = new HashMap<String, Object>();
		emptyHashMap.put("SUBREPORT_DIR", path);
		emptyHashMap.put("REGISTNO", registNo.trim());
		try {
			byte[] bytes = claimPrintService.checkBytes(path, emptyHashMap, registNo.trim());
			printResponse(bytes);
		} catch (Exception exception) {
			throw exception;
		}
	}

	/**
	 * 失竊車輛應備明細表
	 */
	public void lossCarPrint() throws Exception {
		Map<String, Object> emptyHashMap = new HashMap<String, Object>();
		emptyHashMap.put("SUBREPORT_DIR", path);
		try {
			byte[] bytes = claimPrintService.lossCarPrint(path, emptyHashMap);
			printResponse(bytes);
		} catch (Exception exception) {
			throw exception;
		}
	}

	/**
	 * 車險理賠申請所需文件
	 * @throws Exception
	 */
	public void carClaim() throws Exception {
		Map<String, Object> emptyHashMap = new HashMap<String, Object>();
		emptyHashMap.put("SUBREPORT_DIR", path);
		try {
			byte[] bytes = claimPrintService.carClaim(path, emptyHashMap);
			printResponse(bytes);
		} catch (Exception exception) {
			throw exception;
		}
	}

	/**
	 * 強制險現金給付審核表
	 * @throws Exception
	 */
	public void bzPay() throws Exception {
		if (prepayNo != null) {
			prepayNo=prepayNo.trim();
			prepayNo = prepayNo.substring(0, prepayNo.indexOf("Y", 1) == -1 ? prepayNo.length() : prepayNo.indexOf("Y", 1));
		}
		Map<String, Object> emptyHashMap = new HashMap<String, Object>();
		emptyHashMap.put("SUBREPORT_DIR", path);
		emptyHashMap.put("PREPAYNO", prepayNo.trim());
		try {
			byte[] bytes = claimPrintService.bzPay(path, emptyHashMap, prepayNo.trim());
			printResponse(bytes);
		} catch (Exception exception) {
			throw exception;
		}
	}

	/**
	 * 汽 車 險 賠 案 查 證 記 錄 表
	 * @throws Exception
	 */
	public void carCase() throws Exception {
		Map<String, Object> emptyHashMap = new HashMap<String, Object>();
		emptyHashMap.put("SUBREPORT_DIR", path);
		emptyHashMap.put("CLAIMNO", claimNo.trim());
		try {
			byte[] bytes = claimPrintService.carCase(path, emptyHashMap);
			printResponse(bytes);
		} catch (Exception exception) {
			throw exception;
		}
	}

	/**
	 * 汽車險理賠計算書
	 * @throws Exception
	 */
	public void claimStatement() throws Exception {
		if (compensateNo != null) {
			compensateNo = compensateNo.trim();
			compensateNo = compensateNo.substring(0, compensateNo.indexOf("C", 1) == -1 ? compensateNo.length() : compensateNo.indexOf("C", 1));
		}
		Map<String, Object> emptyHashMap = new HashMap<String, Object>();
		// 公司logo
		String imgpath = path + "image/logo.png";
		emptyHashMap.put("IMGPATH", imgpath);
		emptyHashMap.put("SUBREPORT_DIR", path);
		emptyHashMap.put("COMPENSATENO", compensateNo);
		try {
			byte[] bytes = claimPrintService.claimStatement(path, emptyHashMap, compensateNo);
			printResponse(bytes);
		} catch (Exception exception) {
			throw exception;
		}
	}
	
	/***
	 * 汽車險追償計算書列印
	 * @throws Exception 
	 */
	private void claimStatementReplevy() throws Exception {
		if (compensateNo != null) {
			compensateNo = compensateNo.trim();
		}
		if(DataUtils.emptyToNull(compensateNo)!=null){
			String conditions = " compensateNo = '"+compensateNo+"' and exists (select 0 from uticodetransfer u where u.outercode = riskcode and u.riskType = 'D') ";
			List<PrpLcompensate> list = this.prpLcompensateService.findByConditions(conditions);
			if (list == null || list.isEmpty()) {
				throw new UserException(1, 3, "","該追償計算書不存在。請重新錄入。");
			}
		}else{
			throw new UserException(-1, 0, "", "請錄入正確的車險理賠追償計算書");
		}
		Map<String, Object> emptyHashMap = new HashMap<String, Object>();
		// 公司logo
		String imgpath = path + "image/logo.png";
		emptyHashMap.put("IMGPATH", imgpath);
		emptyHashMap.put("SUBREPORT_DIR", path);
		emptyHashMap.put("COMPENSATENO", compensateNo);
		try {
			byte[] bytes = claimPrintService.claimStatementReplevy(path, emptyHashMap, compensateNo);
			printResponse(bytes);
		} catch (Exception exception) {
			throw exception;
		}
	}

	/**
	 * 汽車險理賠申請書
	 * @throws Exception
	 */
	public void claimApplication() throws Exception {
		Map<String, Object> emptyHashMap = new HashMap<String, Object>();
		// 公司logo
		String imgpath = path + "image/logo.png";
		emptyHashMap.put("IMGPATH", imgpath);
		emptyHashMap.put("SUBREPORT_DIR", path);
		emptyHashMap.put("CLAIMNO", claimNo.trim());
		try {
			byte[] bytes = claimPrintService.claimApplication(path, emptyHashMap, claimNo.trim());
			printResponse(bytes);
		} catch (Exception exception) {
			throw exception;
		}
	}

	/**
	 * 理算報告書
	 * @throws Exception
	 */
	public void compensateReport() throws Exception {
		Map<String, Object> emptyHashMap = new HashMap<String, Object>();
		// 公司logo
		String imgpath = path + "image/logo.png";
		emptyHashMap.put("IMGPATH", imgpath);
		emptyHashMap.put("SUBREPORT_DIR", path);
		emptyHashMap.put("COMPENSATENO", compensateNo.trim());
		try {
			byte[] bytes = claimPrintService.compensateReport(path, emptyHashMap, compensateNo.trim());
			printResponse(bytes);
		} catch (Exception exception) {
			throw exception;
		}
	}

	/**
	 * 汽車保險報案記錄（承保理賠資訊）
	 * @throws Exception
	 */
	public void regist() throws Exception {
		Map<String, Object> emptyHashMap = new HashMap<String, Object>();
		// 公司logo
		String imgpath = path + "image/logo.png";
		emptyHashMap.put("IMGPATH", imgpath);
		emptyHashMap.put("SUBREPORT_DIR", path);
		emptyHashMap.put("REGISTNO", registNo.trim());
		try {
			byte[] bytes = claimPrintService.findRegist(path, emptyHashMap, registNo.trim());
			printResponse(bytes);
		} catch (Exception exception) {
			throw exception;
		}
	}

	/**
	 * 失竊車客戶訪談表
	 * @throws Exception
	 */
	public void customerInterview() throws Exception {
		Map<String, Object> emptyHashMap = new HashMap<String, Object>();
		emptyHashMap.put("SUBREPORT_DIR", path);
		try {
			byte[] bytes = claimPrintService.customerInterview(path, emptyHashMap);
			printResponse(bytes);
		} catch (Exception exception) {
			throw exception;
		}
	}

	/**
	 * 事故照片粘貼單
	 * @throws Exception
	 */
	public void accidentPhotoCard() throws Exception {
		Map<String, Object> emptyHashMap = new HashMap<String, Object>();
		emptyHashMap.put("SUBREPORT_DIR", path);
		try {
			byte[] bytes = claimPrintService.accidentPhotoCard(path, emptyHashMap, claimNo.trim());
			printResponse(bytes);
		} catch (Exception exception) {
			throw exception;
		}
	}
	
	/**
	 * 列印公共部分处理
	 * @throws Exception
	 */
	public void printResponse(byte[] bytes) throws Exception {
		ServletOutputStream ouputStream = null;
		try {
			getResponse().setContentType("application/pdf");
			getResponse().setContentLength(bytes.length);
			ouputStream = getResponse().getOutputStream();
			ouputStream.write(bytes, 0, bytes.length);
			ouputStream.flush();
			ouputStream.close();
		} catch (Exception exception) {
			throw exception;
		} finally {
			if (ouputStream != null) {
				ouputStream.close();
			}
		}
	}

	/**
	 * 強制險醫療給付費用明細列印
	 * @throws Exception
	 */
	public String printPrpLcompelMedical() throws Exception {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();

		Map<String, Object> emptyHashMap = new HashMap<String, Object>();
		String claimNo = request.getParameter("claimNo");
		String personName = request.getParameter("personName");
		String identifyNumber = request.getParameter("identifyNumber");
		String claimReceiveDate = request.getParameter("claimReceiveDate");
		String endCaseDate = request.getParameter("endCaseDate");
		emptyHashMap.put("claimNo", claimNo);
		emptyHashMap.put("personName", personName);
		emptyHashMap.put("identifyNumber", identifyNumber);
		emptyHashMap.put("claimReceiveDate", claimReceiveDate);
		emptyHashMap.put("endCaseDate", endCaseDate);
		emptyHashMap.put("SUBREPORT_DIR", path);
		try {
			/*
			 * mantis： CLM0046 ，處理人員：BK007 蘇哲，需求單編號：CLM0046強制險醫療明細提醒同一身分證號與日期重複時有提醒與之前重複的賠次-start
			 * 處理過程：
			 *  PrpLcompensateService 新增 verifyPrpLcompelMedical 方法，提供 verifyMedicalDetail Ajax 使用，驗證同一身分證號與日期重複資訊
			 *  DAACompelPrpLcompelMedical.js 提供前台驗證，有輸入時提醒與送出驗證及提醒
			 *  ClaimPrintAction 列印功能新增備註頁面(prpLcompelMedicalComment.jrxml)及調整 項目渲染時的提示 (prpLcompelMedical_subreport0.jrxml)
			 */
			StringBuilder comment = new StringBuilder();
			List<PrpLperson> personList = this.view(comment);
			List<ArrayList<PrpLperson>> lists = new ArrayList<ArrayList<PrpLperson>>();
			emptyHashMap.put("claimNo", request.getParameter("compensateNo"));
			/*
			 * mantis： CLM0046 ，處理人員：BK007 蘇哲，需求單編號：CLM0046強制險醫療明細提醒同一身分證號與日期重複時有提醒與之前重複的賠次 -end
			 */
			for(int i=0;i<personList.size();i++){
				List<PrpLperson> personList1 = new ArrayList<PrpLperson>();
				personList1.add(personList.get(i));
				if(i>0){
					List<PrpLcompelMedical> prpLcompelMedicals = personList1.get(0).getPrpLcompelMedicalList();
					prpLcompelMedicals.add(new PrpLcompelMedical());
					prpLcompelMedicals.add(new PrpLcompelMedical());
					
					for(int j=prpLcompelMedicals.size()-3;j>=0;j--){
						PrpLcompelMedical prpLcompelMedical = prpLcompelMedicals.get(j);
						prpLcompelMedicals.set(j+2, prpLcompelMedical);
					}
					prpLcompelMedicals.set(0,new PrpLcompelMedical());
					prpLcompelMedicals.set(1,new PrpLcompelMedical());
					personList1.get(0).setPrpLcompelMedicalList(prpLcompelMedicals);
					int s = 30-personList1.get(0).getPrpLcompelMedicalList().size();
					if(personList1.get(0).getPrpLcompelMedicalList().size()<30){
						List<PrpLcompelMedical> prpLcompelMedicalList = new ArrayList<PrpLcompelMedical>(30-personList1.get(0).getPrpLcompelMedicalList().size());
						PrpLcompelMedical prpLcompelMedical = new PrpLcompelMedical();
						for(int j=0;j<s;j++){
							prpLcompelMedicalList.add(j, prpLcompelMedical);
						}
						personList1.get(0).getPrpLcompelMedicalList().addAll(prpLcompelMedicalList);
					}
				}
				
				lists.add(i, (ArrayList)(personList1));
			}
			JRDataSource jrDataSource = null;
			ArrayList<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
			int totalPageNo = lists.size();
			emptyHashMap.put("totalPageNo", totalPageNo+"");
			int pageNo = 1;
			for(ArrayList list : lists){
				jrDataSource = new JRBeanCollectionDataSource(list);
				emptyHashMap.put("pageNo", pageNo+"");
				JasperPrint jasperPrint = JasperFillManager.fillReport(path+"prpLcompelMedical.jasper", emptyHashMap, jrDataSource);
				jasperPrintList.add(jasperPrint);
				pageNo++;
			}
			/*
			 * mantis： CLM0046 ，處理人員：BK007 蘇哲，需求單編號：CLM0046強制險醫療明細提醒同一身分證號與日期重複時有提醒與之前重複的賠次
			 * 處理過程：
			 *  PrpLcompensateService 新增 verifyPrpLcompelMedical 方法，提供 verifyMedicalDetail Ajax 使用，驗證同一身分證號與日期重複資訊
			 *  DAACompelPrpLcompelMedical.js 提供前台驗證，有輸入時提醒與送出驗證及提醒
			 *  ClaimPrintAction 列印功能新增備註頁面(prpLcompelMedicalComment.jrxml)及調整 項目渲染時的提示 (prpLcompelMedical_subreport0.jrxml)
			 */
			if(org.apache.commons.lang.StringUtils.isNotBlank(comment.toString())){
				Map<String,String> parameters = new HashMap<String,String>();
				parameters.put("comment", comment.toString());
				List<Map<String,String>> comments = new ArrayList<Map<String,String>>();
				comments.add(parameters);
				jrDataSource = new JRBeanCollectionDataSource(comments);
				emptyHashMap.put("pageNo", pageNo+"");
				JasperPrint jasperPrint = JasperFillManager.fillReport(path+"prpLcompelMedicalComment.jasper", emptyHashMap,jrDataSource);
				jasperPrintList.add(jasperPrint);
				pageNo++;
			}
			/*
			 * mantis： CLM0046 ，處理人員：BK007 蘇哲，需求單編號：CLM0046強制險醫療明細提醒同一身分證號與日期重複時有提醒與之前重複的賠次 -end
			 */
			ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
			JRPdfExporter exporter = new JRPdfExporter();  
			exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST,jasperPrintList);  
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);  
			exporter.exportReport();  
			byte[] bytes= baos.toByteArray();//得到这个流  
			response.setContentType("application/pdf");  
			response.setContentLength(bytes.length);  
			ServletOutputStream ouputStream = response.getOutputStream();  
			ouputStream.write(bytes, 0, bytes.length);  
			ouputStream.flush();  
			ouputStream.close();
		} catch (Exception exception) {
			throw exception;
		}
	/*	Map<String, Object> jsonMap = new HashMap<String, Object>();
		response.getWriter().write(JSONObject.fromObject(jsonMap).toString());*/
		return NONE;
	}
	/*
	 * mantis： CLM0046 ，處理人員：BK007 蘇哲，需求單編號：CLM0046強制險醫療明細提醒同一身分證號與日期重複時有提醒與之前重複的賠次
	 * 處理過程：
	 *  PrpLcompensateService 新增 verifyPrpLcompelMedical 方法，提供 verifyMedicalDetail Ajax 使用，驗證同一身分證號與日期重複資訊
	 *  DAACompelPrpLcompelMedical.js 提供前台驗證，有輸入時提醒與送出驗證及提醒
	 *  ClaimPrintAction 列印功能新增備註頁面(prpLcompelMedicalComment.jrxml)及調整 項目渲染時的提示 (prpLcompelMedical_subreport0.jrxml)
	 */
	public String verifyCompeMedicalDetail() throws Exception {
		HttpServletResponse response = super.getResponse();
		HttpServletRequest request = super.getRequest();
		response.setContentType("text/html;charset=GBK");
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try{
			String compensateNo = request.getParameter("compensateNo");
			String serialNo = request.getParameter("serialNo");
			String identifyNumber = request.getParameter("identifyNumber");
			String startDate = request.getParameter("startDate");
			Date atStart = DateTimeFormat.forPattern("yyyy/MM/dd").parseDateTime(startDate).toDate();
			String msg = compensateService.verifyPrpLcompelMedical(identifyNumber,compensateNo,Integer.valueOf(serialNo),atStart);
			jsonMap.put("success", true);
			jsonMap.put("msg", msg);
		}catch(Exception e){
			jsonMap.put("success", false);
			jsonMap.put("msg", e.toString());
			jsonMap.put("stack", e.getStackTrace());
		}
		response.getWriter().write(JSONObject.fromObject(jsonMap).toString());
		return NONE;
	}
	private  List<PrpLperson> view(StringBuilder comment) {
		/*
		 * mantis： CLM0046 ，處理人員：BK007 蘇哲，需求單編號：CLM0046強制險醫療明細提醒同一身分證號與日期重複時有提醒與之前重複的賠次 -end
		 */
		List<PrpLcompelMedical> prpLcompelMedicalList = new ArrayList<PrpLcompelMedical>();
		HttpServletRequest request = super.getRequest();
		PrpLcompelMedical prpLcompelMedical = null;
		
		String lastStartDateDisPlay = request.getParameter("lastEndDateDisPlay");
		String lastSumFeeA01 = request.getParameter("lastSumFeeA01");
		String lastSumFeeA021 = request.getParameter("lastSumFeeA021");
		String lastSumFeeA022 = request.getParameter("lastSumFeeA022");
		String lastSumFeeA023 = request.getParameter("lastSumFeeA023");
		String lastSumFeeA024 = request.getParameter("lastSumFeeA024");
		String lastSumFeeA025 = request.getParameter("lastSumFeeA025");
		String lastSumFeeA026 = request.getParameter("lastSumFeeA026");
		String lastSumFeeA029a = request.getParameter("lastSumFeeA029a");
		String lastSumFeeA029b = request.getParameter("lastSumFeeA029b");
		String lastSumFeeA029c = request.getParameter("lastSumFeeA029c");
		String lastSumFeeA029z = request.getParameter("lastSumFeeA029z");
		String lastSumFeeA03 = request.getParameter("lastSumFeeA03");
		String lastSumFeeA04 = request.getParameter("lastSumFeeA04");
		String SumFeeA01234 = request.getParameter("SumFeeA01234");
		prpLcompelMedical = new PrpLcompelMedical();
		prpLcompelMedical.setStartDate(CommonUtils.isEmpty(lastStartDateDisPlay) ? null : new DateTime(lastStartDateDisPlay, DateTime.YEAR_TO_DAY));
		prpLcompelMedical.setA01(CommonUtils.isEmpty(lastSumFeeA01) ? null : Double.parseDouble(lastSumFeeA01));
		prpLcompelMedical.setA021(CommonUtils.isEmpty(lastSumFeeA021) ? null : Double.parseDouble(lastSumFeeA021));
		prpLcompelMedical.setA022(CommonUtils.isEmpty(lastSumFeeA022) ? null : Double.parseDouble(lastSumFeeA022));
		prpLcompelMedical.setA023(CommonUtils.isEmpty(lastSumFeeA023) ? null : Double.parseDouble(lastSumFeeA023));
		prpLcompelMedical.setA024(CommonUtils.isEmpty(lastSumFeeA024) ? null : Double.parseDouble(lastSumFeeA024));
		prpLcompelMedical.setA025(CommonUtils.isEmpty(lastSumFeeA025) ? null : Double.parseDouble(lastSumFeeA025));
		prpLcompelMedical.setA026(CommonUtils.isEmpty(lastSumFeeA026) ? null : Double.parseDouble(lastSumFeeA026));
		prpLcompelMedical.setA029a(CommonUtils.isEmpty(lastSumFeeA029a) ? null : Double.parseDouble(lastSumFeeA029a));
		prpLcompelMedical.setA029b(CommonUtils.isEmpty(lastSumFeeA029b) ? null : Double.parseDouble(lastSumFeeA029b));
		prpLcompelMedical.setA029c(CommonUtils.isEmpty(lastSumFeeA029c) ? null : Double.parseDouble(lastSumFeeA029c));
		prpLcompelMedical.setA029z(CommonUtils.isEmpty(lastSumFeeA029z) ? null : Double.parseDouble(lastSumFeeA029z));
		prpLcompelMedical.setA03(CommonUtils.isEmpty(lastSumFeeA03) ? null : Double.parseDouble(lastSumFeeA03));
		prpLcompelMedical.setA04(CommonUtils.isEmpty(lastSumFeeA04) ? null : Double.parseDouble(lastSumFeeA04));
		prpLcompelMedical.setSumFeeA(CommonUtils.isEmpty(SumFeeA01234) ? null : Double.parseDouble(SumFeeA01234));
		prpLcompelMedicalList.add(prpLcompelMedical);
		
		String StartDateDisPlay = request.getParameter("StartDateDisPlay");
		String SumFeeA01 = request.getParameter("SumFeeA01");
		String SumFeeA021 = request.getParameter("SumFeeA021");
		String SumFeeA022 = request.getParameter("SumFeeA022");
		String SumFeeA023 = request.getParameter("SumFeeA023");
		String SumFeeA024 = request.getParameter("SumFeeA024");
		String SumFeeA025 = request.getParameter("SumFeeA025");
		String SumFeeA026 = request.getParameter("SumFeeA026");
		String SumFeeA029a = request.getParameter("SumFeeA029a");
		String SumFeeA029b = request.getParameter("SumFeeA029b");
		String SumFeeA029c = request.getParameter("SumFeeA029c");
		String SumFeeA029z = request.getParameter("SumFeeA029z");
		String SumFeeA03 = request.getParameter("SumFeeA03");
		String SumFeeA04 = request.getParameter("SumFeeA04");
		String SumFeeA = request.getParameter("SumFeeA");
		prpLcompelMedical = new PrpLcompelMedical();
		prpLcompelMedical.setStartDate(CommonUtils.isEmpty(StartDateDisPlay) ? null : new DateTime(StartDateDisPlay, DateTime.YEAR_TO_DAY));
		prpLcompelMedical.setA01(CommonUtils.isEmpty(SumFeeA01) ? null : Double.parseDouble(SumFeeA01));
		prpLcompelMedical.setA021(CommonUtils.isEmpty(SumFeeA021) ? null : Double.parseDouble(SumFeeA021));
		prpLcompelMedical.setA022(CommonUtils.isEmpty(SumFeeA022) ? null : Double.parseDouble(SumFeeA022));
		prpLcompelMedical.setA023(CommonUtils.isEmpty(SumFeeA023) ? null : Double.parseDouble(SumFeeA023));
		prpLcompelMedical.setA024(CommonUtils.isEmpty(SumFeeA024) ? null : Double.parseDouble(SumFeeA024));
		prpLcompelMedical.setA025(CommonUtils.isEmpty(SumFeeA025) ? null : Double.parseDouble(SumFeeA025));
		prpLcompelMedical.setA026(CommonUtils.isEmpty(SumFeeA026) ? null : Double.parseDouble(SumFeeA026));
		prpLcompelMedical.setA029a(CommonUtils.isEmpty(SumFeeA029a) ? null : Double.parseDouble(SumFeeA029a));
		prpLcompelMedical.setA029b(CommonUtils.isEmpty(SumFeeA029b) ? null : Double.parseDouble(SumFeeA029b));
		prpLcompelMedical.setA029c(CommonUtils.isEmpty(SumFeeA029c) ? null : Double.parseDouble(SumFeeA029c));
		prpLcompelMedical.setA029z(CommonUtils.isEmpty(SumFeeA029z) ? null : Double.parseDouble(SumFeeA029z));
		prpLcompelMedical.setA03(CommonUtils.isEmpty(SumFeeA03) ? null : Double.parseDouble(SumFeeA03));
		prpLcompelMedical.setA04(CommonUtils.isEmpty(SumFeeA04) ? null : Double.parseDouble(SumFeeA04));
		prpLcompelMedical.setSumFeeA(CommonUtils.isEmpty(SumFeeA) ? null : Double.parseDouble(SumFeeA));
		prpLcompelMedicalList.add(prpLcompelMedical);
		
		String[] SerialNo = request.getParameterValues("SerialNo");
		String[] StartDate = request.getParameterValues("StartDate");
		String[] FeeA01 = request.getParameterValues("FeeA01");
		String[] FeeA021 = request.getParameterValues("FeeA021");
		String[] FeeA022 = request.getParameterValues("FeeA022");
		String[] FeeA023 = request.getParameterValues("FeeA023");
		String[] FeeA024 = request.getParameterValues("FeeA024");
		String[] FeeA025 = request.getParameterValues("FeeA025");
		String[] FeeA026 = request.getParameterValues("FeeA026");
		String[] FeeA029a = request.getParameterValues("FeeA029a");
		String[] FeeA029b = request.getParameterValues("FeeA029b");
		String[] FeeA029c = request.getParameterValues("FeeA029c");
		String[] FeeA029z = request.getParameterValues("FeeA029z");
		String[] FeeA03 = request.getParameterValues("FeeA03");
		String[] FeeA04 = request.getParameterValues("FeeA04");
		String[] FeeA = request.getParameterValues("FeeA");
		String[] healthHospitalize = request.getParameterValues("healthHospitalize");
		if(SerialNo.length >0){
			for (int index = 1; index < SerialNo.length; index++) {
					prpLcompelMedical = new PrpLcompelMedical();
					prpLcompelMedical.getId().setSerialNo(index);
					prpLcompelMedical.setStartDate(CommonUtils.isEmpty(StartDate[index]) ? null : new DateTime(StartDate[index], DateTime.YEAR_TO_DAY));
					prpLcompelMedical.setA01(CommonUtils.isEmpty(FeeA01[index]) ? null : Double.parseDouble(FeeA01[index]));
					prpLcompelMedical.setA021(CommonUtils.isEmpty(FeeA021[index]) ? null : Double.parseDouble(FeeA021[index]));
					prpLcompelMedical.setA022(CommonUtils.isEmpty(FeeA022[index]) ? null : Double.parseDouble(FeeA022[index]));
					prpLcompelMedical.setA023(CommonUtils.isEmpty(FeeA023[index]) ? null : Double.parseDouble(FeeA023[index]));
					prpLcompelMedical.setA024(CommonUtils.isEmpty(FeeA024[index]) ? null : Double.parseDouble(FeeA024[index]));
					prpLcompelMedical.setA025(CommonUtils.isEmpty(FeeA025[index]) ? null : Double.parseDouble(FeeA025[index]));
					prpLcompelMedical.setA026(CommonUtils.isEmpty(FeeA026[index]) ? null : Double.parseDouble(FeeA026[index]));
					prpLcompelMedical.setA029a(CommonUtils.isEmpty(FeeA029a[index]) ? null : Double.parseDouble(FeeA029a[index]));
					prpLcompelMedical.setA029b(CommonUtils.isEmpty(FeeA029b[index]) ? null : Double.parseDouble(FeeA029b[index]));
					prpLcompelMedical.setA029c(CommonUtils.isEmpty(FeeA029c[index]) ? null : Double.parseDouble(FeeA029c[index]));
					prpLcompelMedical.setA029z(CommonUtils.isEmpty(FeeA029z[index]) ? null : Double.parseDouble(FeeA029z[index]));
					prpLcompelMedical.setA03(CommonUtils.isEmpty(FeeA03[index]) ? null : Double.parseDouble(FeeA03[index]));
					prpLcompelMedical.setA04(CommonUtils.isEmpty(FeeA04[index]) ? null : Double.parseDouble(FeeA04[index]));
					prpLcompelMedical.setSumFeeA(CommonUtils.isEmpty(FeeA[index]) ? null : Double.parseDouble(FeeA[index]));
					/*
					 * mantis： CLM0046 ，處理人員：BK007 蘇哲，需求單編號：CLM0046強制險醫療明細提醒同一身分證號與日期重複時有提醒與之前重複的賠次-start
					 * 處理過程：
					 *  PrpLcompensateService 新增 verifyPrpLcompelMedical 方法，提供 verifyMedicalDetail Ajax 使用，驗證同一身分證號與日期重複資訊
					 *  DAACompelPrpLcompelMedical.js 提供前台驗證，有輸入時提醒與送出驗證及提醒
					 *  ClaimPrintAction 列印功能新增備註頁面(prpLcompelMedicalComment.jrxml)及調整 項目渲染時的提示 (prpLcompelMedical_subreport0.jrxml)
					 */
					prpLcompelMedical.getId().setIdentifyNumber(request.getParameter("identifyNumber"));
					prpLcompelMedical.getId().setCompensateNo(request.getParameter("compensateNo"));
					try {
						prpLcompelMedical.setvMsg(compensateService.verifyPrpLcompelMedical(prpLcompelMedical));
						if(org.apache.commons.lang.StringUtils.isNotBlank(prpLcompelMedical.getvMsg())){
							comment.append("編號["+prpLcompelMedical.getId().getSerialNo()+"] 在 "+prpLcompelMedical.getvMsg()+" 日期重複\r\n");
						}
					} catch (Exception e) {
						log.error(e.toString(),e);
					}
					/*
					 * mantis： CLM0046 ，處理人員：BK007 蘇哲，需求單編號：CLM0046強制險醫療明細提醒同一身分證號與日期重複時有提醒與之前重複的賠次 -end
					 */
					prpLcompelMedical.setHealthHospitalize(CommonUtils.isEmpty(healthHospitalize[index]) ? null : healthHospitalize[index]);
					prpLcompelMedicalList.add(prpLcompelMedical);
					
				}
		}
		List<PrpLperson> personList = new ArrayList<PrpLperson>();
		List<ArrayList> lists = StringUtils.groupListByQuantity(prpLcompelMedicalList,30);
		for (int i=0;i<lists.size();i++) {
			PrpLperson prpLperson = new PrpLperson();
			prpLperson.setPrpLcompelMedicalList(lists.get(i));
			personList.add(i, prpLperson);
		}
		
		return personList;
	}
	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getPrintType() {
		return printType;
	}

	public void setPrintType(String printType) {
		this.printType = printType;
	}

	public String getRegistNo() {
		return registNo;
	}

	public void setRegistNo(String registNo) {
		this.registNo = registNo;
	}

	public ClaimPrintService getClaimPrintService() {
		return claimPrintService;
	}

	public void setClaimPrintService(ClaimPrintService claimPrintService) {
		this.claimPrintService = claimPrintService;
	}

	public String getClaimNo() {
		return claimNo;
	}

	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}

	public String getCompensateNo() {
		return compensateNo;
	}

	public void setCompensateNo(String compensateNo) {
		this.compensateNo = compensateNo;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getInsuredName() {
		return insuredName;
	}

	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}

	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	public String getPrepayNo() {
		return prepayNo;
	}

	public void setPrepayNo(String prepayNo) {
		this.prepayNo = prepayNo;
	}

	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}

	public PrpLcompensateService getPrpLcompensateService() {
		return prpLcompensateService;
	}

	public void setPrpLcompensateService(PrpLcompensateService prpLcompensateService) {
		this.prpLcompensateService = prpLcompensateService;
	}

	public PrpLregistService getPrpLregistService() {
		return prpLregistService;
	}

	public void setPrpLregistService(PrpLregistService prpLregistService) {
		this.prpLregistService = prpLregistService;
	}

	public PrpLprepayService getPrpLprepayService() {
		return prpLprepayService;
	}

	public void setPrpLprepayService(PrpLprepayService prpLprepayService) {
		this.prpLprepayService = prpLprepayService;
	}
	/*
	 * mantis： CLM0046 ，處理人員：BK007 蘇哲，需求單編號：CLM0046強制險醫療明細提醒同一身分證號與日期重複時有提醒與之前重複的賠次-start
	 * 處理過程：
	 *  PrpLcompensateService 新增 verifyPrpLcompelMedical 方法，提供 verifyMedicalDetail Ajax 使用，驗證同一身分證號與日期重複資訊
	 *  DAACompelPrpLcompelMedical.js 提供前台驗證，有輸入時提醒與送出驗證及提醒
	 *  ClaimPrintAction 列印功能新增備註頁面(prpLcompelMedicalComment.jrxml)及調整 項目渲染時的提示 (prpLcompelMedical_subreport0.jrxml)
	 */
	public CompensateService getCompensateService() {
		return compensateService;
	}

	public void setCompensateService(CompensateService compensateService) {
		this.compensateService = compensateService;
	}
	/*
	 * mantis： CLM0046 ，處理人員：BK007 蘇哲，需求單編號：CLM0046強制險醫療明細提醒同一身分證號與日期重複時有提醒與之前重複的賠次 -end
	 */
}
