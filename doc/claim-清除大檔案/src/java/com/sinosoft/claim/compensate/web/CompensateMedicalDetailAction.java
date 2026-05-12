package com.sinosoft.claim.compensate.web;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.web.Struts2Action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.PowerService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.DataUtils;
import com.sinosoft.claim.compensate.service.facade.CompensateService;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpDcode;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLcompelMedical;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLpayObjectInfo;
import com.sinosoft.claim.schema.model.PrpLpersonLoss;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.model.SwfLogStore;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLpayObjectInfoService;
import com.sinosoft.claim.schema.service.facade.PrpLregistService;
import com.sinosoft.claim.schema.service.facade.SwfLogService;
import com.sinosoft.claim.schema.service.facade.SwfLogStoreService;
import com.sinosoft.claim.ui.control.action.UIPowerInterface;
import com.sinosoft.sysframework.common.datatype.DateTime;

/**
 * 強制險醫療給付費用
 * @Description
 * @author 中科软
 */
public class CompensateMedicalDetailAction extends Struts2Action {

	/**
	 * @Fields serialVersionUID:
	 */
	private static final long serialVersionUID = 1L;
	private CompensateService compensateService;
	private CodeService codeService;
	private PowerService powerService;
	private PrpLpayObjectInfoService prpLpayObjectInfoService;
	private PrpLregistService prpLregistService;
	private PrpLclaimService prpLclaimService;
	private SwfLogService swfLogService;
	private SwfLogStoreService swfLogStoreService;
	/**
	 * 查找受害人費用收據訊息
	 * @return
	 * @throws Exception
	 */
	public String beforeInsertMedicalDetail() throws Exception {
		HttpServletRequest request = super.getRequest();
		String claimNo = request.getParameter("claimNo");
		String compensateNo = request.getParameter("compensateNo");
		String personNo = request.getParameter("personNo");
		String identifyNumber = request.getParameter("identifyNumber");
		String personName = request.getParameter("personName");
		String actionType = request.getParameter("actionType");
		System.out.println("personName---------"+personName);
		if (CommonUtils.isEmpty(compensateNo)) {
			compensateNo = claimNo; // 没有計算書號碼，則取立案號碼
		}
		PrpLclaim prpLclaim = this.prpLclaimService.findPrpLclaim(claimNo);
		String damageDate = prpLclaim.getDamageStartDate().toString().replace("-", "/");
		PrpLregist prpLregist = this.prpLregistService.findPrpLregist(prpLclaim.getRegistNo());
		
		List<PrpLcompelMedical> prpLcompelMedicalList = this.compensateService.findPrpLcompelMedical(compensateNo, identifyNumber);
		PrpLcompelMedical prpLcompelMedical = null;
		if (CommonUtils.isEmpty(prpLcompelMedicalList)) {
			prpLcompelMedical = new PrpLcompelMedical();
			// 受害人資料還展示上一次保存的
			prpLcompelMedical.getId().setIdentifyNumber(identifyNumber);
			prpLcompelMedical.setPersonName(personName);
		} else {
			prpLcompelMedical = prpLcompelMedicalList.get(0);
		}
		prpLcompelMedical.setPersonNo(Integer.parseInt(personNo));
		request.setAttribute("prpLcompelMedical", prpLcompelMedical);
		request.setAttribute("prpLcompelMedicalList", prpLcompelMedicalList);
		request.setAttribute("damageDate", damageDate);
		String prpLpayObjectInfoPaycodeType = "";
		if("ADD".equals(actionType)||"EDIT".equals(actionType)){
			prpLpayObjectInfoPaycodeType = request.getParameter("prpLpayObjectInfoPaycodeType");
		}else{
			List<PrpLpayObjectInfo> prpLpayObjectInfoList = this.prpLpayObjectInfoService.findPrpLpayObjectInfo(QueryRule.getInstance().addEqual("id.compensateNo", compensateNo));
			prpLpayObjectInfoPaycodeType = prpLpayObjectInfoList.get(0).getPaycodeType();
		}
		request.setAttribute("prpLpayObjectInfoPaycodeType", prpLpayObjectInfoPaycodeType);
		request.setAttribute("reportDate", prpLregist.getReportDate().toString().replace("-", "/"));
		//判斷該賠案是否是一結
		
		String conditions = " compensateNo like 'C"+claimNo+"%' and ( underWriteFlag = '1' or underWriteFlag = '3' ) and mutualCompensateNo is null order by underWriteEndDate asc , times asc ";
		List<PrpLcompensate> compensateList = this.compensateService.findByConditions(conditions);
		PrpLcompelMedical lastLcompelMedical = new PrpLcompelMedical();
		prpLcompelMedicalList = this.compensateService.findLastPrpLcompelMedical(claimNo, identifyNumber,compensateNo);
		if(!CommonUtils.isEmpty(compensateList)){//非一結
			double sumA01 = 0d;
			double sumA021 = 0d;
			double sumA022 = 0d;
			double sumA023 = 0d;
			double sumA024 = 0d;
			double sumA025 = 0d;
			double sumA026 = 0d;
			double sumA029a = 0d;
			double sumA029b = 0d;
			double sumA029c = 0d;
			double sumA029z = 0d;
			double sumA03 = 0d;
			double sumA04 = 0d;
			String healthHospitalize = "";
			if(!CommonUtils.isEmpty(prpLcompelMedicalList)){
				for (PrpLcompelMedical prpLcompelMedical1 : prpLcompelMedicalList) {
					sumA01  += prpLcompelMedical1.getA01() == null ? 0d : prpLcompelMedical1.getA01();
					sumA021 += prpLcompelMedical1.getA021() == null ? 0d : prpLcompelMedical1.getA021();
					sumA022  += prpLcompelMedical1.getA022() == null ? 0d : prpLcompelMedical1.getA022();
					sumA023 += prpLcompelMedical1.getA023() == null ? 0d : prpLcompelMedical1.getA023();
					sumA024  += prpLcompelMedical1.getA024() == null ? 0d : prpLcompelMedical1.getA024();
					sumA025 += prpLcompelMedical1.getA025() == null ? 0d : prpLcompelMedical1.getA025();
					sumA026  += prpLcompelMedical1.getA026() == null ? 0d : prpLcompelMedical1.getA026();
					sumA029a += prpLcompelMedical1.getA029a() == null ? 0d : prpLcompelMedical1.getA029a();
					sumA029b  += prpLcompelMedical1.getA029b() == null ? 0d : prpLcompelMedical1.getA029b();
					sumA029c += prpLcompelMedical1.getA029c() == null ? 0d : prpLcompelMedical1.getA029c();
					sumA029z += prpLcompelMedical1.getA029z() == null ? 0d : prpLcompelMedical1.getA029z();
					sumA03  += prpLcompelMedical1.getA03() == null ? 0d : prpLcompelMedical1.getA03();
					sumA04 += prpLcompelMedical1.getA04() == null ? 0d : prpLcompelMedical1.getA04();
					healthHospitalize = CommonUtils.isEmpty(prpLcompelMedical1.getHealthHospitalize()) ? null : prpLcompelMedical1.getHealthHospitalize();	 
			    }
				lastLcompelMedical.setA01(sumA01);
				lastLcompelMedical.setA021(sumA021);
				lastLcompelMedical.setA022(sumA022);
				lastLcompelMedical.setA023(sumA023);
				lastLcompelMedical.setA024(sumA024);
				lastLcompelMedical.setA025(sumA025);
				lastLcompelMedical.setA026(sumA026);
				lastLcompelMedical.setA029a(sumA029a);
				lastLcompelMedical.setA029b(sumA029b);
				lastLcompelMedical.setA029c(sumA029c);
				lastLcompelMedical.setA029z(sumA029z);
			    lastLcompelMedical.setA03(sumA03);
				lastLcompelMedical.setA04(sumA04);
				lastLcompelMedical.setHealthHospitalize(healthHospitalize);
			}else{
				lastLcompelMedical = new PrpLcompelMedical();
			}
		}
		request.setAttribute("lastPrpLcompelMedical", lastLcompelMedical);
		conditions = " compensateNo ='" + compensateNo + "' and ( underWriteFlag = '1' or underWriteFlag = '3') ";
		String conditions1 = " businessNo ='"+claimNo+"'and nodeType='compe'";
	    List<PrpLcompensate> prpLcompensateList = this.compensateService.findByConditions(conditions);
	    List<SwfLog> swflogList = this.swfLogService.findByConditions(conditions1);
	    String[]  underWriteEndDate = null;
	    if(swflogList.size()>0 || !CommonUtils.isEmpty(swflogList)){
	    	underWriteEndDate = swflogList.get(0).getFlowInTime().split(" ");
	    }else{
	    	QueryRule queryRule = QueryRule.getInstance();
	    	queryRule.addSql(conditions1);
	    	List<SwfLogStore> swflogStoreList = this.swfLogStoreService.findSwfLogStore(queryRule);
	    	underWriteEndDate = swflogStoreList.get(0).getFlowInTime().split(" ");
	    }
		
	    request.setAttribute("underWriteEndDate", underWriteEndDate[0].replace("-", "/"));

		if ("AMEND".equals(actionType) || "SHOW".equals(actionType)) {// 計算書補錄
			if ("AMEND".equals(actionType)) {// 補錄的時候重新設置下受害人名稱
				conditions = " compensateNo = '" + compensateNo + "' and personNo = " + personNo + " and identifyNumber = '" + identifyNumber + "' ";
				List<PrpLpersonLoss> personLossList = compensateService.findPersonLossByConditions(conditions);
				if (!CommonUtils.isEmpty(personLossList)) {
					prpLcompelMedical.setPersonName(personLossList.get(0).getPersonName());
				}
			}
		}
		request.setCharacterEncoding("GBK");
		return SUCCESS;
	}

	/***
	 * 存儲受害人費用收據訊息
	 * @return
	 * @throws Exception
	 */
	public String insertMedicalDetail() throws Exception {
		HttpServletRequest request = super.getRequest();
		String claimNo = request.getParameter("claimNo");
		String compensateNo = request.getParameter("compensateNo");
		String actionType = request.getParameter("actionType");
		String personNo = request.getParameter("personNo");
		if (CommonUtils.isEmpty(compensateNo)) {
			compensateNo = claimNo; // 没有計算書號碼，則取立案號碼
		}
		String identifyNumber = request.getParameter("identifyNumber");
		HttpServletResponse response = super.getResponse();
		response.setContentType("text/html;charset=GBK");
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			List<PrpLcompelMedical> prpLcompelMedicalList = this.view(compensateNo, identifyNumber);
			this.compensateService.savePrpLcompelMedical(compensateNo, personNo, prpLcompelMedicalList);
			jsonMap.put("success", true);
			if ("ADD".equals(actionType) || "EDIT".equals(actionType)) {// 理算時對收據保存處理後，需要將費用彙總資料帶入理算畫面
				String conditions = " codetype ='PersonFeeType' ";
				conditions += " and codeCode in (select codeCode from prpdcoderisk where codetype ='PersonFeeType' and (riskcode='B01' or riskcode='0000')) ";
				conditions += " AND validstatus='1' order by codecode";
				List<PrpDcode> codeList = this.codeService.findPrpDcodeByConditions(conditions);
				jsonMap.put("codes", codeList);
			} else if ("AMEND".equals(actionType)){
				if(CommonUtils.isEmpty(prpLcompelMedicalList)){
					jsonMap.put("status", "0");
				} else {
					jsonMap.put("status", prpLcompelMedicalList.get(0).getStatus());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonMap.put("msg", "保存資料失敗！" + e.getMessage());
		}
		response.getWriter().write(JSONObject.fromObject(jsonMap).toString());
		return NONE;
	}

	/***
	 * 操作受害人刪除時，該受害人醫療給付費用收據資料的刪除
	 * @return
	 * @throws Exception
	 */
	public String deleteMedicalDetail() throws Exception {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		response.setContentType("text/html;charset=GBK");
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		String claimNo = request.getParameter("claimNo");
		String compensateNo = request.getParameter("compensateNo");
		String identifyNumber = request.getParameter("identifyNumber");
		if (CommonUtils.isEmpty(compensateNo)) {
			compensateNo = claimNo; // 没有計算書號碼，則取立案號碼
		}
		try {
			this.compensateService.deletePrpLcompelMedical(compensateNo, identifyNumber);
			jsonMap.put("success", true);
		} catch (Exception e) {
			jsonMap.put("msg", "刪除資料失敗！");
		}
		response.getWriter().write(JSONObject.fromObject(jsonMap).toString());
		return NONE;
	}

	/***
	 * 理算提交校驗強制險醫療給付費用
	 * @return
	 */
	public String checkCompeMedicalDetail() throws Exception {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		response.setContentType("text/html;charset=GBK");
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		String claimNo = request.getParameter("claimNo");
		String compensateNo = request.getParameter("compensateNo");
		if (CommonUtils.isEmpty(compensateNo)) {
			compensateNo = claimNo; // 没有計算書號碼，則取立案號碼
		}
		try {
			Map<String, PrpLcompelMedical> map = new HashMap<String, PrpLcompelMedical>();
			List<PrpLcompelMedical> prpLcompelMedicalList = this.compensateService.findPrpLcompelMedical(compensateNo);
			if (!CommonUtils.isEmpty(prpLcompelMedicalList)) {
				PrpLcompelMedical prpLcompelMedical = null;
				for (PrpLcompelMedical m : prpLcompelMedicalList) {
					if (map.containsKey(m.getId().getIdentifyNumber())) {// 受害人已存在
						prpLcompelMedical = map.get(m.getId().getIdentifyNumber());
					} else {
						prpLcompelMedical = new PrpLcompelMedical();
						prpLcompelMedical.getId().setIdentifyNumber(m.getId().getIdentifyNumber());
						prpLcompelMedical.setPersonName(m.getPersonName());
					}
					prpLcompelMedical.setA01((prpLcompelMedical.getA01() == null ? 0d : prpLcompelMedical.getA01()) + (m.getA01() == null ? 0d : m.getA01()));
					prpLcompelMedical.setA021((prpLcompelMedical.getA021() == null ? 0d : prpLcompelMedical.getA021()) + (m.getA021() == null ? 0d : m.getA021()));
					prpLcompelMedical.setA022((prpLcompelMedical.getA022() == null ? 0d : prpLcompelMedical.getA022()) + (m.getA022() == null ? 0d : m.getA022()));
					prpLcompelMedical.setA023((prpLcompelMedical.getA023() == null ? 0d : prpLcompelMedical.getA023()) + (m.getA023() == null ? 0d : m.getA023()));
					prpLcompelMedical.setA024((prpLcompelMedical.getA024() == null ? 0d : prpLcompelMedical.getA024()) + (m.getA024() == null ? 0d : m.getA024()));
					prpLcompelMedical.setA025((prpLcompelMedical.getA025() == null ? 0d : prpLcompelMedical.getA025()) + (m.getA025() == null ? 0d : m.getA025()));
					prpLcompelMedical.setA026((prpLcompelMedical.getA026() == null ? 0d : prpLcompelMedical.getA026()) + (m.getA026() == null ? 0d : m.getA026()));
					prpLcompelMedical.setA029((prpLcompelMedical.getA029() == null ? 0d : prpLcompelMedical.getA029()));
					prpLcompelMedical.setA029(prpLcompelMedical.getA029() + (m.getA029a() == null ? 0d : m.getA029a()));
					prpLcompelMedical.setA029(prpLcompelMedical.getA029() + (m.getA029b() == null ? 0d : m.getA029b()));
					prpLcompelMedical.setA029(prpLcompelMedical.getA029() + (m.getA029c() == null ? 0d : m.getA029c()));
					prpLcompelMedical.setA029(prpLcompelMedical.getA029() + (m.getA029z() == null ? 0d : m.getA029z()));
					prpLcompelMedical.setA03((prpLcompelMedical.getA03() == null ? 0d : prpLcompelMedical.getA03()) + (m.getA03() == null ? 0d : m.getA03()));
					prpLcompelMedical.setA04((prpLcompelMedical.getA04() == null ? 0d : prpLcompelMedical.getA04()) + (m.getA04() == null ? 0d : m.getA04()));
					map.put(m.getId().getIdentifyNumber(), prpLcompelMedical);
				}
			}
			List<Map<String, Object>> checkPerson = new ArrayList<Map<String, Object>>();
			List<String> checkPersonIdentifyNumbers = new ArrayList<String>();
			for (PrpLcompelMedical m : map.values()) {
				checkPersonIdentifyNumbers.add(m.getId().getIdentifyNumber());
				Map<String, Object> me = new HashMap<String, Object>();
				me.put("FeeCode", new String[] { "A01", "A021", "A022", "A023", "A024", "A025", "A026", "A029", "A03", "A04" });
				me.put("FeeDefPay", new Double[] { m.getA01(), m.getA021(), m.getA022(), m.getA023(), m.getA024(), m.getA025(), m.getA026(), m.getA029(), m.getA03(), m.getA04() });
				checkPerson.add(me);
			}
			jsonMap.put("checkPersonIdentifyNumbers", checkPersonIdentifyNumbers);
			jsonMap.put("checkPerson", checkPerson);
			jsonMap.put("success", true);
		} catch (Exception e) {
			jsonMap.put("msg", "獲取受害人強制險醫療給付收據資料失敗！");
		}
		response.getWriter().write(JSONObject.fromObject(jsonMap).toString());
		return NONE;
	}

	/***
	 * 提交補錄強制險醫療給付費用時校驗加總是否與計算書一致
	 * @return
	 * @throws Exception
	 */
	public String checkMedicalDetail() throws Exception {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		response.setContentType("text/html;charset=GBK");
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		String compensateNo = request.getParameter("compensateNo");
		String personNo = request.getParameter("personNo");
		String identifyNumber = request.getParameter("identifyNumber");
		StringBuffer statements = new StringBuffer("");
		statements.append(" SELECT ");
		statements.append(" identifyNumber, ");
		statements.append(" SUM(DECODE(liabdetailcode, 'A01', sumrealpay, 0))  A01, ");
		statements.append(" SUM(DECODE(liabdetailcode, 'A021', sumrealpay, 0)) A021, ");
		statements.append(" SUM(DECODE(liabdetailcode, 'A022', sumrealpay, 0)) A022, ");
		statements.append(" SUM(DECODE(liabdetailcode, 'A023', sumrealpay, 0)) A023, ");
		statements.append(" SUM(DECODE(liabdetailcode, 'A024', sumrealpay, 0)) A024, ");
		statements.append(" SUM(DECODE(liabdetailcode, 'A025', sumrealpay, 0)) A025, ");
		statements.append(" SUM(DECODE(liabdetailcode, 'A026', sumrealpay, 0)) A026, ");
		statements.append(" SUM(DECODE(liabdetailcode, 'A029', sumrealpay, 0)) A029, ");
		statements.append(" SUM(DECODE(liabdetailcode, 'A03', sumrealpay, 0))  A03, ");
		statements.append(" SUM(DECODE(liabdetailcode, 'A04', sumrealpay, 0))  A04, ");
		statements.append(" healthpoints, ");
		statements.append(" healthamount ");
		statements.append(" FROM prplpersonloss WHERE riskcode = 'B01' AND compensateno = ? AND personno = ? AND identifyNumber = ? ");
		statements.append(" GROUP BY identifyNumber,healthpoints,healthamount ");
		Object[] params = new Object[] { compensateNo, Integer.parseInt(personNo), identifyNumber };
		try {
			Page page = this.compensateService.findPrpLcompelMedical(statements.toString(), params, 0, 0);
			List<?> resultList = page.getResult();
			if (!CommonUtils.isEmpty(resultList)) {
				Object[] objs = (Object[]) resultList.get(0);
				jsonMap.put("FeeCode", new String[] { "A01", "A021", "A022", "A023", "A024", "A025", "A026", "A029", "A03", "A04" });
				jsonMap.put("FeeRealPay", new Object[] { objs[1], objs[2], objs[3], objs[4], objs[5], objs[6], objs[7], objs[8], objs[9], objs[10] });
				jsonMap.put("HealthPoints", objs[11]);
				jsonMap.put("HealthAmount", objs[12]);
			}
			jsonMap.put("success", true);
		} catch (Exception e) {
			jsonMap.put("msg", "獲取該受害人醫療賠付費用訊息失敗！");
		}
		response.getWriter().write(JSONObject.fromObject(jsonMap).toString());
		return NONE;
	}

	private List<PrpLcompelMedical> view(String compensateNo, String identifyNumber) throws UnsupportedEncodingException {
		List<PrpLcompelMedical> prpLcompelMedicalList = new ArrayList<PrpLcompelMedical>();
		HttpServletRequest request = super.getRequest();
		String personNo = request.getParameter("personNo");
		String personName = URLDecoder.decode(request.getParameter("personName"),"UTF-8");
		String[] serialNo = request.getParameterValues("SerialNo");
		String[] startDate = request.getParameterValues("StartDate");
		String[] a01 = request.getParameterValues("FeeA01");
		String[] a021 = request.getParameterValues("FeeA021");
		String[] a022 = request.getParameterValues("FeeA022");
		String[] a023 = request.getParameterValues("FeeA023");
		String[] a024 = request.getParameterValues("FeeA024");
		String[] a025 = request.getParameterValues("FeeA025");
		String[] a026 = request.getParameterValues("FeeA026");
		String[] a029a = request.getParameterValues("FeeA029a");
		String[] a029b = request.getParameterValues("FeeA029b");
		String[] a029c = request.getParameterValues("FeeA029c");
		String[] a029z = request.getParameterValues("FeeA029z");
		String[] a03 = request.getParameterValues("FeeA03");
		String[] a04 = request.getParameterValues("FeeA04");
		String[] healthHospitalize = request.getParameterValues("healthHospitalize");
		String status = request.getParameter("status");
		if (!CommonUtils.isEmpty(serialNo)) {
			int length = serialNo.length;
			PrpLcompelMedical prpLcompelMedical = null;
			for (int index = 1; index < length; index++) {
				prpLcompelMedical = new PrpLcompelMedical();
				prpLcompelMedical.getId().setCompensateNo(compensateNo);
				prpLcompelMedical.getId().setIdentifyNumber(identifyNumber);
				prpLcompelMedical.getId().setSerialNo(index);
				prpLcompelMedical.setStartDate(CommonUtils.isEmpty(startDate[index]) ? null : new DateTime(startDate[index], DateTime.YEAR_TO_DAY));
				prpLcompelMedical.setA01(CommonUtils.isEmpty(a01[index]) ? null : Double.parseDouble(a01[index]));
				prpLcompelMedical.setA021(CommonUtils.isEmpty(a021[index]) ? null : Double.parseDouble(a021[index]));
				prpLcompelMedical.setA022(CommonUtils.isEmpty(a022[index]) ? null : Double.parseDouble(a022[index]));
				prpLcompelMedical.setA023(CommonUtils.isEmpty(a023[index]) ? null : Double.parseDouble(a023[index]));
				prpLcompelMedical.setA024(CommonUtils.isEmpty(a024[index]) ? null : Double.parseDouble(a024[index]));
				prpLcompelMedical.setA025(CommonUtils.isEmpty(a025[index]) ? null : Double.parseDouble(a025[index]));
				prpLcompelMedical.setA026(CommonUtils.isEmpty(a026[index]) ? null : Double.parseDouble(a026[index]));
				prpLcompelMedical.setA029a(CommonUtils.isEmpty(a029a[index]) ? null : Double.parseDouble(a029a[index]));
				prpLcompelMedical.setA029b(CommonUtils.isEmpty(a029b[index]) ? null : Double.parseDouble(a029b[index]));
				prpLcompelMedical.setA029c(CommonUtils.isEmpty(a029c[index]) ? null : Double.parseDouble(a029c[index]));
				prpLcompelMedical.setA029z(CommonUtils.isEmpty(a029z[index]) ? null : Double.parseDouble(a029z[index]));
				prpLcompelMedical.setA03(CommonUtils.isEmpty(a03[index]) ? null : Double.parseDouble(a03[index]));
				prpLcompelMedical.setA04(CommonUtils.isEmpty(a04[index]) ? null : Double.parseDouble(a04[index]));
				prpLcompelMedical.setHealthHospitalize(CommonUtils.isEmpty(healthHospitalize[index]) ? null : healthHospitalize[index]);
				prpLcompelMedical.setInputDate(DateTime.current());
				prpLcompelMedical.setStatus(status);
				prpLcompelMedical.setPersonNo(Integer.parseInt(personNo));
				prpLcompelMedical.setPersonName(personName);
				prpLcompelMedicalList.add(prpLcompelMedical);
			}
		}
		return prpLcompelMedicalList;
	}

	/***
	 * 強制險醫療給付費用處理查詢
	 * @return
	 * @throws Exception
	 */
	public String medicalDetailQuery() throws Exception {
		HttpServletRequest request = super.getRequest();
		Page page = this.query();
		// queryType json 本次請求返回json，
		request.setAttribute("currentPageNo", page.getCurrentPageNo());
		request.setAttribute("currentPageSize", page.getPageSize());
		request.setAttribute("totalPageCount", page.getTotalPageCount());
		request.setAttribute("pageStart", page.getStart());
		request.setAttribute("resultList", page.getResult());
		request.setAttribute("page", page);
		String queryType = request.getParameter("returnType");
		if("html".equals(queryType)){
			return "nextpage";
		}
		return SUCCESS;
	}

	private Page query() throws Exception {
		HttpServletRequest request = super.getRequest();
		HttpSession session = request.getSession();
		String queryStatements = "";
		Object[] param = null;
		// 查詢類型，querynew 新查詢 querycontinue 翻頁
		String queryType = request.getParameter("queryType");
		String queryPageNo = request.getParameter("pageNo");
		String queryPageSize = request.getParameter("rowsPerPage");
		pageNo = Integer.parseInt(CommonUtils.isEmpty(queryPageNo) ? "1" : queryPageNo);
		pageSize = Integer.parseInt(CommonUtils.isEmpty(queryPageSize) ? "10" : queryPageSize);
		if ("querycontinue".equals(queryType)) {// 翻頁查詢
			queryStatements = (String) session.getAttribute("medicalDetailQueryStatements");
			param = (Object[]) session.getAttribute("medicalDetailQueryParams");
		} else {
			List<Object> paramList = new ArrayList<Object>();
			queryStatements = this.getQuerySql(paramList);
			param = new Object[paramList.size()];
			paramList.toArray(param);
			session.setAttribute("medicalDetailQueryStatements", queryStatements);
			session.setAttribute("medicalDetailQueryParams", param);
		}

		Page page = this.compensateService.findPrpLcompelMedical(queryStatements, param, pageNo, pageSize);
		List<?> resultList = page.getResult();
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		if (!resultList.isEmpty()) {
			Map<String, Object> object = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for (Iterator<?> it = resultList.iterator(); it.hasNext();) {
				Object[] objs = (Object[]) it.next();
				object = new HashMap<String, Object>();
				object.put("policyNo", objs[0]);
				object.put("claimNo", objs[1]);
				object.put("compensateNo", objs[2]);
				object.put("underWriteEndDateMG", CommonUtils.getMGDateStr((Timestamp) objs[3], sdf));
				object.put("underWriteEndDate", new DateTime((Timestamp) objs[3]).toString());
				object.put("personNo", objs[4]);
				object.put("personName", objs[5]);
				object.put("identifyNumber", objs[6]);
				object.put("healthPoints", objs[7]);
				object.put("healthAmount", objs[8]);
				object.put("status", objs[9]);
				object.put("sumRealpay", objs[10]);
				results.add(object);
			}
		}
		return new Page(page.getStart(), page.getTotalCount(), page.getPageSize(), results);
	}

	/***
	 * 強制險醫療給付費用查詢SQL組織
	 * @param request
	 * @param paramList
	 * @return
	 * @throws Exception
	 */
	private String getQuerySql(List<Object> paramList) throws Exception {
		HttpServletRequest request = super.getRequest();
		String compensateNoSign = DataUtils.emptyToNull(request.getParameter("queryCompensateNoSign"));
		String compensateNo = DataUtils.emptyToNull(request.getParameter("queryCompensateNo"));
		String claimNoSign = DataUtils.emptyToNull(request.getParameter("queryClaimNoSign"));
		String claimNo = DataUtils.emptyToNull(request.getParameter("queryClaimNo"));
		String personNameSign = DataUtils.emptyToNull(request.getParameter("queryPersonNameSign"));
		String personName = DataUtils.emptyToNull(request.getParameter("queryPersonName"));
		String identifyNumberSign = DataUtils.emptyToNull(request.getParameter("queryIdentifyNumberSign"));
		String identifyNumber = DataUtils.emptyToNull(request.getParameter("queryIdentifyNumber"));
		String policyNoSign = DataUtils.emptyToNull(request.getParameter("queryPolicyNoSign"));
		String policyNo = DataUtils.emptyToNull(request.getParameter("queryPolicyNo"));
		String endCaseDateStart = DataUtils.emptyToNull(request.getParameter("queryEndCaseDateStart"));
		String endCaseDateEnd = DataUtils.emptyToNull(request.getParameter("queryEndCaseDateEnd"));
		String status = DataUtils.emptyToNull(request.getParameter("queryStatus"));
		StringBuffer conditions = new StringBuffer("");
		if (!CommonUtils.isEmpty(compensateNo)) {
			conditions.append(" and ").append(CommonUtils.getCondition(compensateNoSign, "c.compensateNo", compensateNo, paramList, String.class));
		}
		if (!CommonUtils.isEmpty(claimNo)) {
			conditions.append(" and ").append(CommonUtils.getCondition(claimNoSign, "c.claimNo", claimNo, paramList, String.class));
		}
		if (!CommonUtils.isEmpty(personName)) {
			conditions.append(" and ").append(CommonUtils.getCondition(personNameSign, "l.personName", personName, paramList, String.class));
		}
		if (!CommonUtils.isEmpty(identifyNumber)) {
			conditions.append(" and ").append(CommonUtils.getCondition(identifyNumberSign, "l.identifyNumber", identifyNumber, paramList, String.class));
		}
		if (!CommonUtils.isEmpty(policyNo)) {
			conditions.append(" and ").append(CommonUtils.getCondition(policyNoSign, "c.policyNo", policyNo, paramList, String.class));
		}
		if (!CommonUtils.isEmpty(endCaseDateStart)) {
			conditions.append(" and ").append(CommonUtils.getCondition(">=", "c.underWriteEndDate", endCaseDateStart, paramList, Date.class));
		}
		if (!CommonUtils.isEmpty(endCaseDateEnd)) {
			conditions.append(" and ").append(CommonUtils.getCondition("<=", "c.underWriteEndDate", endCaseDateEnd, paramList, Date.class));
		}
		if (!CommonUtils.isEmpty(status)) {
			if("0".equals(status)){
				conditions.append(" and ").append(" m.status is null ");
			} else {
				conditions.append(" and ").append(CommonUtils.getCondition("=", "m.status", status, paramList, String.class));
			}
		}
		HttpSession session = request.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		conditions.append(uiPowerInterface.addPower(user, "c", "", "makeCom"));// 取當前處理人員的機構權限
		conditions.append(powerService.addRiskPower(user, "c", "claim"));// 取當前人員是否有車險的險種權限
		// 設置查詢SQL
		StringBuffer statements = new StringBuffer("");
		statements.append(" SELECT c.policyno, ");
		statements.append(" c.claimno, ");
		statements.append(" c.compensateno, ");
		statements.append(" c.underwriteenddate, ");
		statements.append(" l.personno, ");
		statements.append(" l.personname, ");
		statements.append(" l.identifynumber, ");
		statements.append(" l.healthpoints, ");
		statements.append(" l.healthamount, ");
		statements.append(" nvl(m.status,'0') status, ");
		statements.append(" SUM(l.sumrealpay) sumrealpay ");
		statements.append(" FROM   prplcompensate c  ");
		statements.append(" INNER  JOIN prplpersonloss l ON c.compensateno = l.compensateno  ");
		statements.append(" LEFT   JOIN prplcompelmedical m ON ( l.compensateno = m.compensateno AND l.identifynumber = m.identifynumber AND m.serialno = 1 ) ");
		statements.append(" WHERE  c.riskcode = 'B01' AND c.mutualcompensateno IS NULL AND c.compensateNo LIKE 'C%' ");
		statements.append(" AND (c.underwriteflag = '1' OR c.underwriteflag = '3') AND l.liabdetailcode LIKE 'A0%' AND l.sumrealpay > 0 ");
		statements.append(conditions);
		statements.append(" GROUP  BY c.policyno, ");
		statements.append(" c.claimno, ");
		statements.append(" c.compensateno, ");
		statements.append(" c.times, ");
		statements.append(" c.underwriteenddate, ");
		statements.append(" l.personname, ");
		statements.append(" l.identifynumber, ");
		statements.append(" l.personno, ");
		statements.append(" l.healthpoints, ");
		statements.append(" l.healthamount, ");
		statements.append(" status  ");
		statements.append(" ORDER  BY underwriteenddate ASC, ");
		statements.append(" personno ASC ");
		return statements.toString();
	}

	/***
	 * 下載導出醫療給付費用收據資料
	 * @return
	 * @throws Exception
	 */
	public String exportMedicalDetail() throws Exception {
		HttpServletRequest request = super.getRequest();
		String[] cbx = request.getParameterValues("cbx");
		int length = cbx == null ? 0 : cbx.length;
		File file = null;
		if (length > 0) {
			FileInputStream fis = null;
			OutputStream out = null;
			String[] claimNo = new String[length], compensateNo = new String[length], personNo = new String[length], identifyNumber = new String[length], underWriteEndDate = new String[length], personName = new String[length];
			for (int i = 0; i < length; i++) {
				String[] strs = cbx[i].split(",");
				claimNo[i] = strs[0];
				compensateNo[i] = strs[1];
				personNo[i] = strs[2];
				identifyNumber[i] = strs[3];
				underWriteEndDate[i] = strs[4];
				personName[i] = strs[5];
			}
			try {
				HttpServletResponse response = super.getResponse();
				String fileName = null;
				if (length == 1) {
					file = this.getXlsFile(claimNo[0], compensateNo[0], personNo[0], identifyNumber[0], underWriteEndDate[0], personName[0]);
					if (file != null) {
						fileName = compensateNo[0].substring(1) + personNo[0] + ".xls";
						response.setContentType("application/ms-excel");
						response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(), "iso-8859-1"));
					}
				} else if (length > 1) {
					file = this.getZipFile(claimNo, compensateNo, personNo, identifyNumber, underWriteEndDate, personName);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
					fileName = sdf.format(new Date()) + String.valueOf((int) (Math.random() * 100.0D) + 1000).substring(1) + ".zip";
					response.setContentType("application/zip");
				}
				if (file != null) {
					response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(), "iso-8859-1"));
					fis = new FileInputStream(file);
					out = response.getOutputStream();
					byte[] bytes = new byte[1024];
					int count = 0;
					while ((count = fis.read(bytes, 0, 1024)) > 0) {
						out.write(bytes, 0, count);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (fis != null) {
						fis.close();
					}
					if (file != null && file.isFile()) {
						file.delete();
					}
					if (out != null) {
						out.flush();
						out.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return NONE;
	}

	/***
	 * 批量下載導出受害人的醫療費用收據資料，返回的是zip壓縮文檔
	 * @param claimNo 
	 * @param compensateNo
	 * @param personNo
	 * @param identifyNumber
	 * @param underWriteEndDate
	 * @param personName
	 * @return
	 * @throws Exception
	 */
	public File getZipFile(String[] claimNo, String[] compensateNo, String[] personNo, String[] identifyNumber, String[] underWriteEndDate, String[] personName) throws Exception {
		HttpServletRequest request = super.getRequest();
		String destPath = request.getSession().getServletContext().getRealPath("/pages/DAA/compensate/compel/xls/" + UUID.randomUUID() + ".zip");
		File zipFile = new File(destPath); // 下载的zip文件
		CheckedOutputStream cos = null;
		ZipOutputStream zos = null;
		try {
			cos = new CheckedOutputStream(new FileOutputStream(zipFile), new CRC32());
			zos = new ZipOutputStream(cos);
			File xlsFile = null;
			ZipEntry xlsEntry = null;
			byte[] bytes = new byte[1024];
			int count = 0;
			BufferedInputStream bis = null;
			for (int index = 0, len = claimNo.length; index < len; index++) {
				xlsFile = this.getXlsFile(claimNo[index], compensateNo[index], personNo[index], identifyNumber[index], underWriteEndDate[index], personName[index]);
				if (xlsFile != null) {
					//被壓縮各項的名稱
					String fname = compensateNo[index].substring(1) + personNo[index] + ".xls";;
					xlsEntry = new ZipEntry(claimNo[index] + File.separator + fname);
					zos.putNextEntry(xlsEntry);
					try {
						bis = new BufferedInputStream(new FileInputStream(xlsFile));
						while ((count = bis.read(bytes, 0, 1024)) > 0) {
							zos.write(bytes, 0, count);
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						if (bis != null) {
							bis.close();
						}
					}
					zos.closeEntry();
					xlsFile.delete();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				zos.flush();
				zos.close();
			} catch (Exception e2) {
				
			}
			try {
				cos.close();
			} catch (Exception e2) {
				
			}
		}
		zipFile.deleteOnExit();
		return zipFile;
	}
	
	/***
	 * 獲得受害人醫療費用收據資料導出的excel文檔
	 * @param claimNo 立案號碼
	 * @param compensateNo 計算書號碼
	 * @param personNo 受害人數別
	 * @param identifyNumber 受害人身分證號碼
	 * @param underWriteEndDate 結案日期
	 * @param personName 受害人名稱
	 * @return
	 * @throws Exception
	 */
	public File getXlsFile(String claimNo, String compensateNo, String personNo, String identifyNumber, String underWriteEndDate, String personName) throws Exception {
		HttpServletRequest request = super.getRequest();
		String tempFilePath = request.getSession().getServletContext().getRealPath("/pages/DAA/compensate/compel/xls");
		// 取模板
		File originalFile = new File(tempFilePath + File.separator + "exportMedicalDetail.xls");
		File destFile = null;
		FileOutputStream fos = null;
		HSSFWorkbook workbook = null;
		try {
			List<PrpLcompelMedical> dataList = this.compensateService.findPrpLcompelMedical(compensateNo, identifyNumber);
			destFile = new File(tempFilePath + File.separator + UUID.randomUUID() + ".xls.temp");
			FileUtils.copyFile(originalFile, destFile);
			destFile.deleteOnExit();
			workbook = new HSSFWorkbook(new FileInputStream(destFile));
			HSSFSheet hssfSheet = workbook.getSheetAt(0);
			HSSFRow hssfRow = hssfSheet.getRow(1);
			CommonUtils.setCellValue(hssfRow.getCell(5), claimNo, 0);// 設置賠案號碼
			CommonUtils.setCellValue(hssfRow.getCell(9), personName, 0);// 受害人姓名
			CommonUtils.setCellValue(hssfRow.getCell(13), identifyNumber, 0);// 受害人身分證號碼
			CommonUtils.setCellValue(hssfRow.getCell(17), new DateTime(underWriteEndDate), 1);// 設置結案日期
			// 設置每行資料的值
			int rowStart = 10;// 正常資料從第11行開始
			if (!CommonUtils.isEmpty(dataList)) {
				for (int row = 0, size = dataList.size(); row < size; row++) {
					PrpLcompelMedical m = dataList.get(row);
					hssfRow = hssfSheet.getRow(row + rowStart);
					CommonUtils.setCellValue(hssfRow.getCell(0), m.getId().getSerialNo(), 2);
					CommonUtils.setCellValue(hssfRow.getCell(1), m.getStartDate(), 1);
					CommonUtils.setCellValue(hssfRow.getCell(2), m.getEndDate(), 1);
					CommonUtils.setCellValue(hssfRow.getCell(2), m.getEndDate(), 1);
					CommonUtils.setCellValue(hssfRow.getCell(3), m.getA01(), 2);
					CommonUtils.setCellValue(hssfRow.getCell(4), m.getA021(), 2);
					CommonUtils.setCellValue(hssfRow.getCell(5), m.getA022(), 2);
					CommonUtils.setCellValue(hssfRow.getCell(6), m.getA023(), 2);
					CommonUtils.setCellValue(hssfRow.getCell(7), m.getA024(), 2);
					CommonUtils.setCellValue(hssfRow.getCell(8), m.getA025(), 2);
					CommonUtils.setCellValue(hssfRow.getCell(9), m.getA026(), 2);
					CommonUtils.setCellValue(hssfRow.getCell(10), m.getA029a(), 2);
					CommonUtils.setCellValue(hssfRow.getCell(11), m.getA029b(), 2);
					CommonUtils.setCellValue(hssfRow.getCell(12), m.getA029c(), 2);
					CommonUtils.setCellValue(hssfRow.getCell(13), m.getA029z(), 2);
					CommonUtils.setCellValue(hssfRow.getCell(14), m.getA03(), 2);
					CommonUtils.setCellValue(hssfRow.getCell(15), m.getA04(), 2);
				}
			}
			// 強制刷新公式計算
			workbook.setForceFormulaRecalculation(true);
			fos = new FileOutputStream(destFile);
			workbook.write(fos);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(fos != null){
					fos.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return destFile;
	}

	public CompensateService getCompensateService() {
		return compensateService;
	}

	public void setCompensateService(CompensateService compensateService) {
		this.compensateService = compensateService;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	public PowerService getPowerService() {
		return powerService;
	}

	public PrpLpayObjectInfoService getPrpLpayObjectInfoService() {
		return prpLpayObjectInfoService;
	}

	public void setPrpLpayObjectInfoService(
			PrpLpayObjectInfoService prpLpayObjectInfoService) {
		this.prpLpayObjectInfoService = prpLpayObjectInfoService;
	}

	public void setPowerService(PowerService powerService) {
		this.powerService = powerService;
	}

	public PrpLregistService getPrpLregistService() {
		return prpLregistService;
	}

	public void setPrpLregistService(PrpLregistService prpLregistService) {
		this.prpLregistService = prpLregistService;
	}

	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}

	public SwfLogService getSwfLogService() {
		return swfLogService;
	}

	public void setSwfLogService(SwfLogService swfLogService) {
		this.swfLogService = swfLogService;
	}

	public SwfLogStoreService getSwfLogStoreService() {
		return swfLogStoreService;
	}

	public void setSwfLogStoreService(SwfLogStoreService swfLogStoreService) {
		this.swfLogStoreService = swfLogStoreService;
	}
}
