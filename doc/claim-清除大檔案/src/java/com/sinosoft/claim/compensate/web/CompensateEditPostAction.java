package com.sinosoft.claim.compensate.web;

import java.io.File;
//mantis：CLM0126，處理人員：DP0713，需求單編號：受款人ID檢核 
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
// mantis：CLM0219，處理人員：DP0714，新核心-理算暫存功能異常(未處理理算)
//mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單 START
import ins.framework.common.DateTime;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.math.BigDecimal;
import java.util.Calendar;
import com.sinosoft.one.util.date.DateUtils;
import com.tlg.commons.util.api.rest.blockChain.enums.EnumApplicantsTypes;

import com.sinosoft.claim.schema.model.ClaimCompulsoryApplicant;
import com.sinosoft.claim.schema.model.ClaimCompulsoryApplicantId;
import com.sinosoft.claim.schema.model.ClaimCompulsoryApportion;
import com.sinosoft.claim.schema.model.ClaimCompulsoryApportionId;
import com.sinosoft.claim.schema.model.ClaimCompulsoryCase;
import com.sinosoft.claim.schema.model.ClaimCompulsoryCaseId;
import com.sinosoft.claim.schema.model.ClaimCompulsoryCharges;
import com.sinosoft.claim.schema.model.ClaimCompulsoryChargesId;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpLpersonLoss;
import com.sinosoft.claim.schema.model.PrpLthirdParty;
import com.sinosoft.claim.schema.model.VehicleClaimApiLog;
import com.sinosoft.claim.schema.model.VehicleClaimApiLogId;
import com.sinosoft.claim.common.util.EndorseViewHelper;
import com.sinosoft.claim.schema.service.facade.PrpLthirdPartyService;
import com.sinosoft.claim.schema.service.facade.VehicleClaimApiLogService;
import com.sinosoft.claim.schema.service.facade.ClaimCompulsoryApplicantService;
import com.sinosoft.claim.schema.service.facade.ClaimCompulsoryApportionService;
import com.sinosoft.claim.schema.service.facade.ClaimCompulsoryCaseService;
import com.sinosoft.claim.schema.service.facade.ClaimCompulsoryChargesService;
import com.sinosoft.claim.schema.service.facade.ClaimCompulsoryStatePricesService;
//mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單 END
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ins.framework.common.QueryRule;
import ins.framework.common.ServiceFactory;
import ins.framework.web.Struts2Action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//mantis：CLM0126，處理人員：DP0713，需求單編號：受款人ID檢核
import javax.servlet.http.HttpSession;

//mantis：CLM0126，處理人員：DP0713，需求單編號：受款人ID檢核
import org.apache.commons.lang.xwork.ArrayUtils;
import org.apache.struts2.views.jasperreports.ValueStackDataSource;

import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.json.JSONObject;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;
import com.sinosoft.app.common.util.StringUtil;
import com.sinosoft.claim.common.service.facade.BillService;
import com.sinosoft.claim.common.service.facade.CodeService;
//mantis：CLM0126，處理人員：DP0713，需求單編號：受款人ID檢核
import com.sinosoft.claim.common.service.facade.PrpDuserService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.ProcessTokenException;
import com.sinosoft.claim.common.util.StringUtils;
import com.sinosoft.claim.compensate.service.facade.CompensateService;
import com.sinosoft.claim.compensate.util.AccidentCompensateViewHelper;
import com.sinosoft.claim.compensate.util.SunnyCompensateViewHelper;
import com.sinosoft.claim.compensate.vo.CompensateDto;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.email.service.facade.EmailService;
import com.sinosoft.claim.email.vo.Attachment;
import com.sinosoft.claim.email.vo.EmailDto;
import com.sinosoft.claim.print.util.AcciPrintViewHelper;
import com.sinosoft.claim.print.util.GAAPrintViewHelper;
import com.sinosoft.claim.print.util.LiabPrintViewHelper;
import com.sinosoft.claim.print.util.PropPrintViewHelper;
import com.sinosoft.claim.print.util.ShipPrintViewHelper;
import com.sinosoft.claim.print.vo.AcciPrintObject;
import com.sinosoft.claim.print.vo.GAACompensateObject;
import com.sinosoft.claim.print.vo.LiabCompensateObject;
import com.sinosoft.claim.print.vo.PropCompensateObject;
import com.sinosoft.claim.print.vo.ShipCompensateObject;
import com.sinosoft.claim.regist.service.facade.RegistService;
//mantis：CLM0126，處理人員：DP0713，需求單編號：受款人ID檢核
import com.sinosoft.claim.schema.model.PrpDuser;
import com.sinosoft.claim.schema.model.PrpLcarInsurance;
//mantis：CLM0126，處理人員：DP0713，需求單編號：受款人ID檢核
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLclaimLoss;
import com.sinosoft.claim.schema.model.PrpLcompensate;
// mantis：CLM0219，處理人員：DP0714，新核心-理算暫存功能異常(未處理理算)
import com.sinosoft.claim.schema.model.PrpLloss;
//mantis：CLM0126，處理人員：DP0713，需求單編號：受款人ID檢核
import com.sinosoft.claim.schema.model.PrpLpayObjectInfo;
import com.sinosoft.claim.schema.model.PrpLregist;
//mantis：CLM0126，處理人員：DP0713，需求單編號：受款人ID檢核
import com.sinosoft.claim.schema.model.PrpLuser;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.service.facade.PrpLcarInsuranceService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimLossService;
//mantis：CLM0126，處理人員：DP0713，需求單編號：受款人ID檢核  START
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.claim.schema.service.facade.PrpLregistService;
import com.sinosoft.claim.schema.service.facade.PrpLuserService;
//mantis：CLM0126，處理人員：DP0713，需求單編號：受款人ID檢核 END
import com.sinosoft.claim.schema.service.facade.PrpLrecaseService;
import com.sinosoft.claim.schema.service.facade.SwfLogService;
import com.sinosoft.claim.specailCase.service.facade.GeneralClaimService;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.util.BusinessViewHelper;
import com.sinosoft.claim.workflow.util.JbpmBusinessViewHelper;
import com.sinosoft.claim.workflow.util.WorkFlowViewHelper;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;
import com.sinosoft.sysframework.common.util.DataUtils;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * 分发HTTP Post 车险理赔实赔编辑界面
 * <p>
 * Title: 车险理赔实赔编辑界面信息
 * </p>
 * <p>
 * Description: 车险理赔实赔编辑界面信息
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Company: sinosoft.com.cn
 * </p>
 * @author 中科软
 * @version 1.0
 */
public class CompensateEditPostAction extends Struts2Action {

	/**
	 * @Fields serialVersionUID:
	 */
	private static final long serialVersionUID = 1L;

	/**理算服务*/
	private CompensateService compensateService;
	/**理算数据收集*/
	private SunnyCompensateViewHelper sunnyCompensateViewHelper;
	/**非车险数据收集*/
	private AccidentCompensateViewHelper accidentCompensateViewHelper;
	/**报案服务*/
	private RegistService registService;
	/**代码翻译服务*/
	private CodeService codeService;
	/**通赔服务*/
	private GeneralClaimService generalClaimService;
	/**重开赔案服务*/
	private PrpLrecaseService prpLrecaseService;
	/**单号生成服务*/
	private BillService billService;
	/**工作流数据收集*/
	private WorkFlowViewHelper workFlowViewHelper;
	/**工作流日志服务*/
	private SwfLogService swfLogService;
	/**工作流处理服务*/
	private WorkFlowService workFlowService;
	//mantis：CLM0126，處理人員：DP0713，需求單編號：受款人ID檢核 START
	private PrpLclaimService prpLclaimService;
	private PrpLcompensateService prpLcompensateService;
	private PrpLregistService prpLregistService;
	//mantis：CLM0126，處理人員：DP0713，需求單編號：受款人ID檢核 END
	private JbpmBusinessViewHelper jbpmBusinessViewHelper;
	private EmailService emailService;
	private ShipPrintViewHelper shipPrintViewHelper;
	private GAAPrintViewHelper gaaPrintViewHelper;
	private LiabPrintViewHelper liabPrintViewHelper;
	private PropPrintViewHelper propPrintViewHelper;
	/** 伤害险列印帮助类   */
	private AcciPrintViewHelper acciPrintViewHelper;
	private PrpLcarInsuranceService prpLcarInsuranceService;
	private BusinessViewHelper businessViewHelper;
	/**車體險訊息服務*/
	//mantis：CLM0126，處理人員：DP0713，需求單編號：受款人ID檢核
	private PrpLuserService prpLuserService;
	//mantis：CLM0126，處理人員：DP0713，需求單編號：受款人ID檢核
	private PrpDuserService prpDuserService;

	//mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單 START
	private VehicleClaimApiLogService vehicleClaimApiLogService;
	private ClaimCompulsoryApplicantService claimCompulsoryApplicantService;
	private ClaimCompulsoryApportionService claimCompulsoryApportionService;
	private ClaimCompulsoryCaseService claimCompulsoryCaseService;
	private ClaimCompulsoryChargesService claimCompulsoryChargesService;
	private ClaimCompulsoryStatePricesService claimCompulsoryStatePricesService;
	/** 结案数据收集 */
	private EndorseViewHelper endorseViewHelper;
	private PrpLthirdPartyService prpLthirdPartyService;
	//mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單 END
	/**
	 * 理算处理暂存、提交
	 * @return
	 * @throws Exception
	 */
	public String compensateEditPost() throws Exception {
		this.clearErrorsAndMessages();
		HttpServletRequest request = super.getRequest();
		try {
			// 程序思路:
			// ---------------------------------------------------
			// 如果是第一次保存，只要能将状态变成正在处理就行了。。
			// 其他就是在正在处理的状态栏里进行处理了。
			// ---------------------------------------------------
			String swfLogFlowID = request.getParameter("swfLogFlowID"); // 工作流号码
			String swfLogLogNo = request.getParameter("swfLogLogNo"); // 工作流logno
			// 清除实赔结点的操作人
			int LogNo = Integer.parseInt(swfLogLogNo);
			SwfLog swfLogDto = this.getWorkFlowService().findNodeByPrimaryKey(swfLogFlowID, LogNo);
			if (swfLogDto != null) {
				if (swfLogDto.getNodeType().equals("compe")) {
					swfLogDto.setHandlerCode("");
					swfLogDto.setHandlerName("");
				}
				this.getWorkFlowService().updateFlow(swfLogDto);
			} else {
				throw new UserException(1, 3, "0000", getText("prompt.compensate.workFlowNodeNotExist"));//該節點工作流實例不存在！
			}
			int newCompensate = -1;
			// 默认不需要重新生成赔款计算书,後来决定不需要用工作流保存每个计算书的状态
//			UserDto user = (UserDto) request.getSession().getAttribute("user");
			String riskCodeTemp = request.getParameter("prpLcompensateRiskCode");
			// 将立案号，提交类型定义变量 begin
			String claimNo = request.getParameter("prpLcompensateClaimNo");
			String buttonSaveType = request.getParameter("buttonSaveType");
			// 将立案号定义变量 end
//			String comCode = request.getParameter("prpLcompensateComCode");
			String riskCode = riskCodeTemp;
			request.setAttribute("riskCode", riskCode);//
			// reason: 防止重复提交
			String strRiskType = this.getCodeService().translateRiskCodetoRiskType(riskCode);
			// 业务操作
			// 取赔款计算书号
			String compensateNo = request.getParameter("prpLcompensateCompensateNo");
			String compFlag = this.compensateService.getCompFlagByConditions(claimNo);
			String[] flags = compFlag.split("-");
			if (flags.length > 0 && "1".equals(flags[0])) {
				if ("".equals(DataUtils.dbNullToEmpty(compensateNo))) {
					//該立案存在未核賠通過的理算書，請不要重複提交！
					throw new UserException(1, 3, "理算", getText("prompt.compensate.multipleCompensatePagesVeric"));
				}
				int backCount = this.getSwfLogService().getCount("keyin='" + claimNo + "' and nodetype='compp' and nodestatus in ('2','3')");
				if (flags.length > 1 && backCount < Integer.parseInt(flags[1]) && !"2".equals(buttonSaveType)) {
					throw new Exception(getText("prompt.compensate.compensatePagesNotVeric"));//該立案存在未核賠通過的理算書，不允許任務提交！
				} else if (flags.length > 1 && backCount > 0 && "2".equals(buttonSaveType)) {
					int backCountZanCun = this.getSwfLogService().getCount("keyin='" + claimNo + "' and nodetype='compp' and nodestatus in ('2')");
					if (backCountZanCun == 1 && backCount == 1) {

					} else {
						throw new UserException(1, 3, "理算", getText("prompt.compensate.backCannotHold"));//核賠駁回的理算任務，不允許暫存，請調整後提交！
					}
				}
			}
			if ("".equals(DataUtils.dbNullToEmpty(compensateNo))) {
				String tableName = "prplcompensate";
				String prpLcompensatePolicyNo = request.getParameter("prpLcompensatePolicyNo");
				Map<String,Object> infoMap = new HashMap<String,Object>();
				String chargeType = request.getParameter("chargeType");
				infoMap.put("policyNo",prpLcompensatePolicyNo);
				infoMap.put("chargeType",chargeType);
				compensateNo = billService.getNoByPolciyYear(tableName, claimNo,infoMap);
				newCompensate = 1; // 生成赔款计算书 这种情况由於是多任务处理，无论何时，都需要新插节点
			}
			request.setAttribute("compensateNo", compensateNo);
			// 用viewHelper整理界面输入
			CompensateDto compensateDto = null;
			if ("D".equals(strRiskType)) {
				compensateDto = this.sunnyCompensateViewHelper.viewToDto(request);
			} else {
				if ("E".equals(strRiskType)) {
					compensateDto = this.accidentCompensateViewHelper.viewToDtoForAccident(request);
				} else {
					compensateDto = this.accidentCompensateViewHelper.viewToDto(request);
				}
			}

			//mantis：CLM0126，處理人員：DP0713，需求單編號：受款人ID檢核 START
			if ("4".equals(DataUtils.dbNullToEmpty(request.getParameter("buttonSaveType")))) {//2暫存 4提交
				String message = checkPayuserCode();
				if(message!=""){
					throw new UserException(1, 3, "理算", message);
				}
			}
			//mantis：CLM0126，處理人員：DP0713，需求單編號：受款人ID檢核 END
			
			
			PrpLcompensate prpLcompensate = compensateDto.getPrpLcompensate();
			// 异常测试
			if (DataUtils.emptyToNull(compensateNo) == null) {
				//計算書號碼產生錯誤，不能儲存計算書
				throw new UserException(1, 3, "理算", getText("prompt.compensate.compensatePagesError"));
				// 必须抛出错误，没有计算书号码。
			}
			

			//mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單(打3.5區塊鏈) START
			System.out.println("policyVo.visaCode="+request.getParameter("policyVo.visaCode"));
			if("4".equals(DataUtils.dbNullToEmpty(request.getParameter("buttonSaveType")))
				&& null!=prpLcompensate.getIsCompulsoryBchainClaim()
				&& prpLcompensate.getIsCompulsoryBchainClaim().equals("N")){//==N 頁面會查詢3.10
				
				PrpLclaim prpLclaim = compensateDto.getPrpLclaim();
				String policyNo = prpLclaim.getPolicyNo();
				String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
				String damageHour = prpLclaim.getDamageStartHour();
				PrpCmain prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate, damageHour);
//				prpCmain.getPrintNo()+prpCmain.getVisaCode()

				String registNo = prpLclaim.getRegistNo();
				QueryRule queryRule2 = QueryRule.getInstance().addEqual("id.registNo", registNo).addEqual("insureCarFlag","1");
				List<PrpLthirdParty> prpLthirdPartyList = this.prpLthirdPartyService.findPrpLthirdParty(queryRule2);
				
				for(int i=0;i<compensateDto.getPrpLpersonLossList().size();i++){
					PrpLpersonLoss prpLpersonLoss = compensateDto.getPrpLpersonLossList().get(i);
					// 格式化流水號為3位數，不足補0
			        String sequenceStr = String.format("%03d",i+1);
			        
			        //==VehicleClaimApiLog 
					VehicleClaimApiLog vehicleClaimApiLog = null;
					
					QueryRule queryRule = QueryRule.getInstance();
					queryRule.addEqual("compensateNo", compensateNo);
					queryRule.addEqual("status", "PENDING");
					queryRule.addEqual("apiCode", "API 3.5");//CREATE
					List<VehicleClaimApiLog> vcalList = vehicleClaimApiLogService.findVehicleClaimApiLog(queryRule);
					if(null!=vcalList && vcalList.size()>0){
						vehicleClaimApiLog = vcalList.get(0);
					}else{
						vehicleClaimApiLog = new VehicleClaimApiLog();
						VehicleClaimApiLogId vehicleClaimApiLogId = new VehicleClaimApiLogId();
				        // 取得當前時間
				        Date currentDate = new Date();
				        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				        // 格式化日期
				        String formattedDate = sdf.format(currentDate);
				        
						vehicleClaimApiLogId.setLogId(formattedDate+sequenceStr);//編碼規則：西元年+月+日+時+分+秒+流水碼三碼
						vehicleClaimApiLog.setId(vehicleClaimApiLogId);
						vehicleClaimApiLog.setCompensateNo(compensateNo);
					}
					vehicleClaimApiLog.setCreateTime(new Date());
					
					vehicleClaimApiLog.setUnderWriteFlag("9");//待審核。審通過後才能進排程
					vehicleClaimApiLog.setStatus("PENDING");//PENDING+api code(API 號碼)=排程

					//--hitTime
					String format = "yyyy-MM-dd'T'HH:mm";
					ThreadLocal<SimpleDateFormat> local = new ThreadLocal<>();
					SimpleDateFormat dateFormat = local.get();
			        if (dateFormat == null) {
			            dateFormat = new SimpleDateFormat(format);
			            local.set(dateFormat);
			        }
			        
			        Calendar calendar = Calendar.getInstance();
			        calendar.setTime(prpLcompensate.getDamageStartDate());
			        calendar.set(Calendar.HOUR_OF_DAY, StringUtils.isNotBlank(prpLcompensate.getDamageStartHour())?Integer.parseInt(prpLcompensate.getDamageStartHour()):0);
			        calendar.set(Calendar.MINUTE, 0);
			        calendar.set(Calendar.SECOND, 0);
			        calendar.set(Calendar.MILLISECOND, 0);
			        
			        Date hitDate = calendar.getTime();
			        vehicleClaimApiLog.setHitTime(dateFormat.format(hitDate));
			        vehicleClaimApiLog.setUserCode(prpLcompensate.getHandlerCode());
			        
					vehicleClaimApiLog.setPersonName(prpLpersonLoss.getPersonName());
					vehicleClaimApiLog.setIdentifyNumber(prpLpersonLoss.getIdentifyNumber());
					vehicleClaimApiLog.setIdNumberType(prpLpersonLoss.getIdNumberType());
					vehicleClaimApiLog.setCarNo(prpLpersonLoss.getFamilyName());
					vehicleClaimApiLog.setClaimNo(prpLcompensate.getClaimNo());
					vehicleClaimApiLog.setPolicyNo(prpLcompensate.getPolicyNo());
					vehicleClaimApiLog.setApiUrl("192.168");
					vehicleClaimApiLog.setApiCode("API 3.5");
					vehicleClaimApiLog.setHttpMethod("GET");
					
					
					//==DATE
					Date claimCompulsoryDate = new Date();
					
					//==未分類   		
					
					//====claimCompulsoryCase 
						ClaimCompulsoryCase claimCompulsoryCase = new ClaimCompulsoryCase();
						ClaimCompulsoryCaseId claimCompulsoryCaseId = new ClaimCompulsoryCaseId();
						claimCompulsoryCaseId.setoId(vehicleClaimApiLog.getId().getLogId());
						claimCompulsoryCase.setId(claimCompulsoryCaseId);
						claimCompulsoryCase.setJobOid(new BigDecimal(vehicleClaimApiLog.getId().getLogId()));
						
						//hitTime
						claimCompulsoryCase.setHitTime(dateFormat.format(hitDate));//受害人資料-出險日期 (case-hit_time) > 出險時間 (prplcaim.DAMAGESTARTDATE)
						
						//'1':'本車上乘客',==>EnumApplicantsTypes.THIS_CAR_PASSENGER
						//'3':'車外人員',==>EnumApplicantsTypes.PEDESTRIAN
						//'4':'對方車上乘客',==>EnumApplicantsTypes.PASSENGER
						//'5':'對方車上駕駛',==>EnumApplicantsTypes.DRIVER
						//'6':'本車上駕駛' ==>NAN(不該選到這項)
						switch (prpLpersonLoss.getRideSituation()) {
				            case "1":
				            	claimCompulsoryCase.setApplicantType(EnumApplicantsTypes.THIS_CAR_PASSENGER.getValue());
				                break;
				            case "3":
				            	claimCompulsoryCase.setApplicantType(EnumApplicantsTypes.PEDESTRIAN.getValue());
				                break;
				            case "4":
				            	claimCompulsoryCase.setApplicantType(EnumApplicantsTypes.PASSENGER.getValue());
				                break;
				            case "5":
				            	claimCompulsoryCase.setApplicantType(EnumApplicantsTypes.DRIVER.getValue());
				                break;
				        }
						claimCompulsoryCase.setCaseNumber(prpLcompensate.getClaimNo());//賠案編號 (case -case_number) > 賠案號碼(prplcompensate.claimno)
						
						claimCompulsoryCase.setIcreate(prpLcompensate.getHandlerCode());
						claimCompulsoryCase.setDcreate(claimCompulsoryDate);
						claimCompulsoryCaseService.save(claimCompulsoryCase);//SAVE
						
					//====claimCompulsoryApplicant
						ClaimCompulsoryApplicant claimCompulsoryApplicant = new ClaimCompulsoryApplicant();
						ClaimCompulsoryApplicantId claimCompulsoryApplicantId = new ClaimCompulsoryApplicantId();
						claimCompulsoryApplicantId.setoId(vehicleClaimApiLog.getId().getLogId());
						claimCompulsoryApplicant.setId(claimCompulsoryApplicantId);
						claimCompulsoryApplicant.setJobOid(new BigDecimal(vehicleClaimApiLog.getId().getLogId()));
						
						claimCompulsoryApplicant.setApplicantIdNumberType(prpLpersonLoss.getIdNumberType());
						claimCompulsoryApplicant.setApplicantIdNumber(prpLpersonLoss.getIdentifyNumber());//受害人資料-身分證號碼(applicant-applicant_id_number) > 強制險受害人訊息-身分證號 (prplpersonloss. IDENTIFYNUMBER)
						
						if(null!=prpLpersonLoss.getBirthday()){
							SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					        String dateStr = sdf.format(prpLpersonLoss.getBirthday());//受害人資料-出生年月日(民國)(applicant- applicant_birthday) > 強制險受害人訊息-出身年份(prplpersonloss.Birthday)
							claimCompulsoryApplicant.setApplicantBirthday(Integer.parseInt(dateStr.substring(0,4),10)+dateStr.substring(4));
						}
						claimCompulsoryApplicant.setApplicantName(prpLpersonLoss.getPersonName());//受害人資料-姓名(applicant- applicant_name) > 強制險受害人訊息-人員姓名(prplpersonloss. PERSONNAME)

						claimCompulsoryApplicant.setIcreate(prpLcompensate.getHandlerCode());
						claimCompulsoryApplicant.setDcreate(claimCompulsoryDate);
						claimCompulsoryApplicantService.save(claimCompulsoryApplicant);//SAVE
						
					//====claimCompulsoryApportion
						ClaimCompulsoryApportion claimCompulsoryApportion = new ClaimCompulsoryApportion();
						ClaimCompulsoryApportionId claimCompulsoryApportionId = new ClaimCompulsoryApportionId();
						claimCompulsoryApportionId.setoId(vehicleClaimApiLog.getId().getLogId());
						claimCompulsoryApportion.setId(claimCompulsoryApportionId);
						claimCompulsoryApportion.setJobOid(new BigDecimal(vehicleClaimApiLog.getId().getLogId()));
						
						claimCompulsoryApportion.setInsuranceCarNumber(prpLpersonLoss.getFamilyName());//X賠付公司資料-牌照號碼(apportion-insurance_car_number) > 牌照號碼(prpLclaim.LicenseNo)
						claimCompulsoryApportion.setDriverName(prpLcompensate.getInsuredName());//X賠付公司資料-駕駛姓名(apportion-driver_name) > 被保險人(prplclaim.InsuredName)
						claimCompulsoryApportion.setLossCity(prpLcompensate.getDamageCode());//X賠付公司資料-出險地區(apportion-loss_city) > 出險地區 (prplclaim. DAMAGEAREACODE)
						//***
						claimCompulsoryApportion.setInsuranceNumber(prpCmain.getPrintNo());//賠付公司資料-強制險證號(apportion-insurance_number) >　強制險保險證號碼 (PRPCOPYMAIN. PRINTNO)
//						policyVo.setVisaCode(prpCmain.getVisaCode());//強制險 保險證號 1 的來源
//			    		policyVo.setVisaSerialNo(prpCmain.getPrintNo().substring(prpCmain.getVisaCode().length()));//強制險 保險證號 2	 的來源
						//[缺]賠付公司資料-肇事路段 prpLcompensate.getDamageAddress()

						claimCompulsoryApportion.setIcreate(prpLcompensate.getHandlerCode());
						claimCompulsoryApportion.setDcreate(claimCompulsoryDate);
						claimCompulsoryApportionService.save(claimCompulsoryApportion);//SAVE
						
					//====claimCompulsoryCharges
						ClaimCompulsoryCharges claimCompulsoryCharges = new ClaimCompulsoryCharges();
						ClaimCompulsoryChargesId claimCompulsoryChargesId = new ClaimCompulsoryChargesId();
						claimCompulsoryChargesId.setoId(vehicleClaimApiLog.getId().getLogId());
						claimCompulsoryCharges.setId(claimCompulsoryChargesId);
						claimCompulsoryCharges.setJobOid(new BigDecimal(vehicleClaimApiLog.getId().getLogId()));
						
						claimCompulsoryCharges.setMspId("MSP18");//攤賠資料-賠付公司(compulsory_charges-mspid) > 承保公司代號prpLthirdParty. INSURECOMCODE、prpLthirdParty. INSURECOMNAME)
						claimCompulsoryCharges.setInsuranceCarNumber(prpLpersonLoss.getFamilyName());//攤賠資料-牌照號碼(compulsory_charges-insurance_car_number) > 牌照號碼(prpLthirdParty.LICENSENO)
						claimCompulsoryCharges.setInsuranceNumber(prpLcompensate.getPolicyNo());//攤賠資料-強制險證號(compulsory_charges-insurance_number) > 強制保險證號(prpLthirdParty.INSURANCENO)

//						claimCompulsoryCharges.setVehicleType(vehicleType);//攤賠資料-車輛種類(compulsory_charges-vehicle_type) >車輛種類(prpLthirdParty.CARKINDCODE)
//						claimCompulsoryCharges.setVehiclePayloadCapacity(vehiclePayloadCapacity);//攤賠資料-乘載限制(compulsory_charges- vehicle_payload_capacity)> 承載數量(prpLthirdParty.CARRYINGNUMBER)
						if(null!=prpLthirdPartyList && prpLthirdPartyList.size()>0)
							for(PrpLthirdParty thirdParty:prpLthirdPartyList){
								if(thirdParty.getLicenseNo().equals(prpLpersonLoss.getFamilyName())){
									claimCompulsoryCharges.setVehicleType(thirdParty.getCarKindCode());
									claimCompulsoryCharges.setVehiclePayloadCapacity(String.valueOf(thirdParty.getCarryingNumber()));
									claimCompulsoryCharges.setVehiclePayloadCapacityUnit(thirdParty.getCarryingUnit());
									break;
								}
							}
						
						claimCompulsoryCharges.setIcreate(prpLcompensate.getHandlerCode());
						claimCompulsoryCharges.setDcreate(claimCompulsoryDate);
						claimCompulsoryChargesService.save(claimCompulsoryCharges);//SAVE
					
					vehicleClaimApiLogService.save(vehicleClaimApiLog);
				}
			}
//			if(1==1){
//				throw new Exception("不給過");
//			}
			//mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單 END
			
			WorkFlowDto workFlowDto = null;
			String actorId = request.getParameter("swfLogActorId");
			if (WorkFlowDto.isWorkflowswitch() && DataUtils.emptyToNull(DataUtils.dbNullToEmpty(actorId)) != null) {
				workFlowDto = this.getJbpmBusinessViewHelper().getJbpmWorkFlowDto(super.getRequest(), newCompensate != 1, newCompensate == 1, null, null, compensateNo, compensateNo, claimNo, null);
			} else {
//				workFlowDto = this.getWorkFlowDto(newCompensate, prpLcompensate);
	             workFlowDto = this.businessViewHelper.getWorkFlowDto(super.getRequest(), newCompensate != 1, newCompensate == 1, null, null, compensateNo, compensateNo, claimNo, null);
			}

			// mantis：CLM0219，處理人員：DP0714，新核心-理算暫存功能異常(未處理理算) -- start
			if (compensateDto.getPrpLlossList()!=null) {
				Iterator<PrpLloss> iter = compensateDto.getPrpLlossList().iterator();
				while (iter.hasNext()) {
					PrpLloss prpLloss = iter.next();
				    if (StringUtils.isBlank(prpLloss.getKindCode())) {
				    	iter.remove();
				     }
				}
			}
			// mantis：CLM0219，處理人員：DP0714，新核心-理算暫存功能異常(未處理理算) -- end

			if (workFlowViewHelper.checkDealDto(workFlowDto)) {
				// 因暂时不能与工作流共享事务，所以单独处理。暂存也是一次提交，多任务类型的节点特殊处理
				if ("4".equals(request.getParameter("buttonSaveType"))) {
					this.compensateService.save(true, compensateDto, workFlowDto);
					//提交需要开启核赔工作流
//					this.getJbpmBusinessViewHelper().saveWorkFlow(compensateService,"save", workFlowDto, new Boolean(true),compensateDto);
				} else {
					this.compensateService.save(false, compensateDto, workFlowDto);
//					this.getJbpmBusinessViewHelper().saveBusiness(compensateService,"save", workFlowDto, new Boolean(false),compensateDto);
				}
				// 无责垫付案件标示回写报案主表prplregist
				String advanceType = request.getParameter("prplregistAdvance");
				if (!"".equals(DataUtils.dbNullToEmpty(advanceType))) {
					String registNo = this.getCodeService().translateBusinessCode(claimNo, false);
					PrpLregist prpLregist = this.registService.findByPrimaryKeyForPrpLRegist(registNo);
					prpLregist.setAdvanceType(advanceType);
					this.registService.updatePrpLRegist(prpLregist);
//					user.setUserMessage(compensateNo);
				}
			} else {
				throw new UserException(1, 3, "理算", getText("prompt.compensate.workFlowDataError"));//工作流流程數據整理錯誤，不能儲存計算書
			}
			request.setAttribute("prpLcompensate", prpLcompensate);
			super.clearErrorsAndMessages();
			if ("4".equals(DataUtils.dbNullToEmpty(request.getParameter("buttonSaveType")))) {
				super.addActionMessage(super.getText("prompt.compensate.submit"));
			} else {
				super.addActionMessage(super.getText("prompt.compensate.save"));
			}
			super.addActionMessage(super.getText("db.prpLcompensate.compensateNo"));
			super.addActionMessage(compensateNo);
			if ("4".equals(DataUtils.dbNullToEmpty(request.getParameter("buttonSaveType")))) {
				String configCode = this.getCodeService().translateRiskCodetoConfigCode(riskCode);
				//承保險種為OH-船體險、AV-航空險、FV-漁船險，時理算提交會提示：此險種有批減，請通知承保端處理”
				if("RISKCODE_YAV".equals(configCode)||"RISKCODE_YFV".equals(configCode)||"RISKCODE_YOH".equals(configCode)){
					super.addActionMessage("此險種有批減，請通知承保端處理");
				}
				if ("1".equals(prpLcompensate.getInformReinsFlag()) && ("G".equals(strRiskType) || "Y".equals(strRiskType) || "Z".equals(strRiskType)|| "Q".equals(strRiskType)|| "E".equals(strRiskType))) {
					try {
						this.sendMail(prpLcompensate, strRiskType);
						super.addActionMessage("郵件：再保攤賠通知函發送成功！");
					} catch (Exception e) {
						super.addActionMessage("郵件：再保攤賠通知函發送失败！");
					}
				}
			}
			String printFlag = request.getParameter("prpLcompensatePrintFlag");
			if ("1".equals(DataUtils.dbNullToEmpty(printFlag))) {
				super.getResponse().sendRedirect("/claim/ClaimPrint.do?printType=Compensate&CompensateNo=" + compensateNo);
			}
			request.setAttribute("compensateNo", compensateNo);
			request.setAttribute("compPrintType", "compPrintType");

			// mantis：CLM0218，處理人員：DP0714，新核心-理算暫存功能異常 -- start
			if (StringUtils.isNotBlank(compensateNo)) {
				String conditions = "flowid = '" + swfLogFlowID + "' and nodetype = 'compp' and nodestatus = '2'";
				List<SwfLog> swfLogs = this.getWorkFlowService().findNodesByConditions(conditions);
				if (swfLogs.size()==1) {
					swfLogs.get(0).setKeyOut(compensateNo);
					this.getWorkFlowService().updateFlow(swfLogs.get(0));
				}
			}
			// mantis：CLM0218，處理人員：DP0714，新核心-理算暫存功能異常 -- end
		} catch (ProcessTokenException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}
	
	/**
	 * mantis：CLM0126，處理人員：DP0713，需求單編號：受款人ID檢核 
	 * AJAX進入點
	 * @return
	 * @throws Exception
	 */
	public String checkPayuserList() throws Exception {
		String message =  "";
		message = checkPayuserCode();

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("message", message);
		super.getResponse().setContentType("text/html; charset=UTF-8");
		super.getResponse().getWriter().write(JSONObject.fromObject(jsonMap).toString());
		return NONE;
	}
	
	/**
	 * mantis：CLM0126，處理人員：DP0713，需求單編號：受款人ID檢核 
	 * 檢核方法 可共用
	 * @return
	 * @throws Exception
	 */
	public String checkPayuserCode() throws Exception {
		String message =  "";
		HttpServletRequest request = super.getRequest();
		
		String claimNo = request.getParameter("prpLcompensateClaimNo");
		String s = request.getParameter("prpLpayObjectInfoUniformNo");

		if ("4".equals(DataUtils.dbNullToEmpty(request.getParameter("buttonSaveType")))) {//2暫存 4提交
			System.out.println("不可為賠付對象的受款人判斷 START");
			String[] prpLpayObjectInfoUniformNoAry = s!=null&&s.length()>0?s.split(","):null;
			if(null!=prpLpayObjectInfoUniformNoAry && prpLpayObjectInfoUniformNoAry.length>0){
				for(String o:prpLpayObjectInfoUniformNoAry){				
					System.out.println("賠付對象:"+o);
				}
			}
			HttpServletRequest httpServletRequest = super.getRequest();
			HttpSession session = httpServletRequest.getSession();
			UserDto user = (UserDto) session.getAttribute("user");//經辦
			PrpDuser prpDuser = prpDuserService.findPrpDuser(user.getUserCode());
			PrpLuser prpLuser = null;

			//[登入prpDuser.getUserCode()]是否在PRPLUSER查找  (X)
			prpLuser = this.getPrpLuserService().findPrpLuserByUserCode(prpDuser.getUserCode());
			if(null!=prpLuser && checkPayuserId(prpLuser.getId())){
				System.out.println("不可為賠付對象的受款人:登入prpDuser.getUserCode():"+prpLuser.getId());
				message=getText("prompt.compensate.cannotSameWithPayuser");//理賠經辦人員不可為賠付對象的受款人！
			}
			//PRPLCLAIM 立案
			PrpLclaim prpLclaim  = this.prpLclaimService.findPrpLclaim(claimNo);

			//PRPLREGIST 備案
//			String prpLcompensatePolicyNo = request.getParameter("prpLcompensatePolicyNo");
			List<PrpLregist> registList = this.getRegistService().findRegistsByPolicyno(prpLclaim.getPolicyNo());
			if(null!=registList && registList.size()>0){
				for(PrpLregist plr:registList){
					prpLuser = this.getPrpLuserService().findPrpLuserByUserCode(plr.getOperatorCode());
					if(null!=prpLuser && checkPayuserId(prpLuser.getId())){
						System.out.println("不可為賠付對象的受款人::PRPLREGIST plr.getOperatorCode()備案:"+prpLuser.getId());
						message=getText("prompt.compensate.cannotSameWithPayuser");//理賠經辦人員不可為賠付對象的受款人！
					}
					prpLuser = this.getPrpLuserService().findPrpLuserByUserCode(plr.getHandlerCode());
					if(null!=prpLuser && checkPayuserId(prpLuser.getId())){
						System.out.println("不可為賠付對象的受款人:PRPLREGIST plr.getHandlerCode()備案:"+prpLuser.getId());
						message=getText("prompt.compensate.cannotSameWithPayuser");//理賠經辦人員不可為賠付對象的受款人！
					}
				}
			}
			//PRPLCLAIM.OPERATORCODE 立案經辦人員代號
			String handlerCode1 = prpLclaim.getHandlerCode();
			prpLuser = this.getPrpLuserService().findPrpLuserByUserCode(handlerCode1);
			if(null!=prpLuser && checkPayuserId(prpLuser.getId())){
				System.out.println("不可為賠付對象的受款人:PRPLCLAIM.OPERATORCODE 立案經辦人員代號:"+prpLuser.getId());
				message=getText("prompt.compensate.cannotSameWithPayuser");//理賠經辦人員不可為賠付對象的受款人！
			}
			//PRPLCLAIM.HANDLERCODE 立案處理人員代號
			String operatorCode1= prpLclaim.getOperatorCode();
			prpLuser = this.getPrpLuserService().findPrpLuserByUserCode(operatorCode1);
			if(null!=prpLuser && checkPayuserId(prpLuser.getId())){
				System.out.println("不可為賠付對象的受款人:PRPLCLAIM.HANDLERCODE 立案處理人員代號:"+prpLuser.getId());
				message=getText("prompt.compensate.cannotSameWithPayuser");//理賠經辦人員不可為賠付對象的受款人！
			}
			
			List<PrpLcompensate> prpLcompensateList = this.prpLcompensateService.findByClaimNo(prpLclaim.getClaimNo());
			//PRPLCOMPENSATE 理算
			PrpLcompensate plc = null;
			if(null!=prpLcompensateList){
				for (int j = 0; j < prpLcompensateList.size(); j++) {
					plc = prpLcompensateList.get(j);
					//PRPLCOMPENSATE.HANDLERCODE 理算處理人員代號
					String handlerCode2 = plc.getHandlerCode();
					prpLuser = this.getPrpLuserService().findPrpLuserByUserCode(handlerCode2);
					if(null!=prpLuser && checkPayuserId(prpLuser.getId())){
						System.out.println("不可為賠付對象的受款人:PRPLCOMPENSATE.HANDLERCODE 理算處理人員代號:"+prpLuser.getId());
						message=getText("prompt.compensate.cannotSameWithPayuser");//理賠經辦人員不可為賠付對象的受款人！
					}
					//PRPLCOMPENSATE.OPERATORCODE 理算經辦人員代號
					String operatorCode2= plc.getOperatorCode();
					prpLuser = this.getPrpLuserService().findPrpLuserByUserCode(operatorCode2);
					if(null!=prpLuser && checkPayuserId(prpLuser.getId())){
						System.out.println("不可為賠付對象的受款人:PRPLCOMPENSATE.OPERATORCODE 理算經辦人員代號:"+prpLuser.getId());
						message=getText("prompt.compensate.cannotSameWithPayuser");//理賠經辦人員不可為賠付對象的受款人！
					}
				}
			}
		}
		System.out.println("不可為賠付對象的受款人判斷 END");
		return message;
	}

	/**
	 * mantis：CLM0126，處理人員：DP0713，需求單編號：受款人ID檢核 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean checkPayuserId(String id) throws Exception {
		HttpServletRequest request = super.getRequest();
		String s = request.getParameter("prpLpayObjectInfoUniformNo");
		String[] prpLpayObjectInfoUniformNoAry = s!=null&&s.length()>0?s.split(","):null;
		PrpLuser prpLuser = null;
		if(null!=prpLpayObjectInfoUniformNoAry && prpLpayObjectInfoUniformNoAry.length>0){
			for(String prpLpayObjectInfo : prpLpayObjectInfoUniformNoAry){
				prpLuser = this.getPrpLuserService().findPrpLuserById(id);
				//[ 賠付對象]的 統一編號比較 
				if(null!=prpLuser && prpLpayObjectInfo.equals(prpLuser.getId()) && prpLuser.getUserFlag().equals("1") ){
					System.out.println("不可為賠付對象的受款人:中獎人員代碼:"+prpLuser.getId());
					return true;
				}
			}
		}
		return false;
	}
	
	/***
	 * 再保攤賠通知發送
	 * @param businessNo
	 * @param riskType
	 * @return
	 * @throws Exception
	 */
	private void sendMail(PrpLcompensate prpLcompensate,String riskType) throws Exception{
		File file = null;
		try {
			EmailDto email = new EmailDto();
			email.setBusinessNo(prpLcompensate.getCompensateNo());
			email.setSubject("賠案"+prpLcompensate.getClaimNo()+"再保攤賠通知");
			email.setText("客服部寄送");
			String filePath = "";
			String fileName = "";
			/** 报表列印  数据对象*/
			List<Object> resultList = new ArrayList<Object>();
			/** 传递的参数*/
			Map<String, Object> param = new HashMap<String, Object>();
			String path = super.getRequest().getSession().getServletContext().getRealPath("")+"/printReport/";
			param.put("IMGPATH", path + "image/logo.jpg");
			String printFilePath = null;
			if("Y".equals(riskType)){
				String configCode = this.getCodeService().translateRiskCodetoConfigCode(prpLcompensate.getRiskCode());
				if("RISKCODE_YMC".equals(configCode)){
					filePath = "/Ship/CargoCompensate"+prpLcompensate.getCompensateNo()+".pdf";
					printFilePath = "/Ship/CargoCompensate.jasper";
					fileName = "貨物運輸險理算書pdf格式.pdf";
				}else{
					filePath = "/Ship/ShipCompensate"+prpLcompensate.getCompensateNo()+".pdf";;
					printFilePath = "/Ship/ShipCompensate.jasper";
					fileName = "水險理算書pdf格式.pdf";
				}
				
				param.put("SUBREPORT_DIR", path + "Ship/");
				ShipCompensateObject shipCompensateObject = this.shipPrintViewHelper.printShipCompensate(prpLcompensate);
				resultList.add(shipCompensateObject);
			}else if("G".equals(riskType)){
				filePath = "/GAA/GAACompensate"+prpLcompensate.getCompensateNo()+".pdf";;;
				printFilePath = "/GAA/GAACompensate.jasper";
				fileName = "工程險理算書pdf格式.pdf";
				
				param.put("SUBREPORT_DIR", path + "GAA/");
				GAACompensateObject gaaCompensateObject =  this.gaaPrintViewHelper.findGAACompensateObjectByCompensateNo(prpLcompensate.getCompensateNo());
				//判断公證公司是否显示。
				param.put("DISPLAYFLAG", new Boolean(gaaCompensateObject.getCompensateSubreport2Object().size() > 0));
				resultList.add(gaaCompensateObject);
			}else if("Z".equals(riskType)){
				filePath = "/Liab/LiabCompensate"+prpLcompensate.getCompensateNo()+".pdf";;;
				printFilePath = "/Liab/LiabCompensate.jasper";
				fileName = "責任險理算書pdf格式.pdf";
				
				param.put("SUBREPORT_DIR", path + "Liab/");
				LiabCompensateObject liabCompensateObject = this.liabPrintViewHelper.findLiabCompensateObjectByCompensateNo(prpLcompensate.getCompensateNo());
				param.put("DISPLAYFLAG", new Boolean(liabCompensateObject.getCompensateSubreport2Object().size() > 0));
				resultList.add(liabCompensateObject);
			}else if("Q".equals(riskType)){
				filePath = "/Prop/PropCompensate"+prpLcompensate.getCompensateNo()+".pdf";;;
				printFilePath = "/Prop/PropClaimCompensateReport.jasper";
				fileName = "火險理算書pdf格式.pdf";
				
				param.put("SUBREPORT_DIR", path + "Prop/");
				PropCompensateObject propCompensateObject = this.propPrintViewHelper.findPropClaimCompensateReportObjectByCompensateNo(prpLcompensate.getCompensateNo());
				param.put("DISPLAYFLAG", false);
				if(propCompensateObject.getCompensateSubreport2Object().size() > 0){
					param.put("DISPLAYFLAG", true);
				}
				resultList.add(propCompensateObject);
			}else if("E".equals(riskType)){
				filePath = "/Acci/AcciCompensate"+prpLcompensate.getCompensateNo()+".pdf";
				printFilePath = "/Acci/Compensate.jasper";
				fileName = "傷害險理算書pdf格式.pdf";
				
				param.put("SUBREPORT_DIR", path + "Acci/");
				AcciPrintObject acciPrintObject = this.acciPrintViewHelper.printCompensate(param,prpLcompensate.getCompensateNo());
				resultList.add(acciPrintObject);
			}
			
			//打印計算書
			ValueStack stack = ActionContext.getContext().getValueStack();
			stack.set("resultList",resultList);
			ValueStackDataSource stackDataSource = new ValueStackDataSource(stack,"resultList");
			JasperRunManager.runReportToPdfFile(path + printFilePath,path+filePath, param, stackDataSource);
			file = CommonUtils.getWebRootFile("/printReport"+filePath);
			if(file!=null){
				email.getAttachment().add(new Attachment(fileName,file));
			}
			this.getEmailService().mailSend(prpLcompensate.getCompensateNo(), "10003", "01",email);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			if(file!=null&&file.isFile()){
				file.delete();
			}
		}
	}

	/***
	 * 旧工作流处理理算任务，
	 * @param newCompensate
	 * @param prpLcompensate
	 * @return
	 * @throws Exception
	 */
	private WorkFlowDto getWorkFlowDto(int newCompensate, PrpLcompensate prpLcompensate) throws Exception {
		HttpServletRequest request = super.getRequest();
		String swfLogFlowID = request.getParameter("swfLogFlowID"); // 工作流号码
		String swfLogLogNo = request.getParameter("swfLogLogNo"); // 工作流logno
		String caseType = request.getParameter("prpLcompensateCaseType");
		SwfLog swfLogDtoDealNode = new SwfLog();
		swfLogDtoDealNode.setNewMTask(newCompensate);
		// 子任务的处理 ,单个赔款计算书的处理
		if (!"".equals(DataUtils.dbNullToEmpty(swfLogFlowID)) && !"".equals(DataUtils.dbNullToEmpty(swfLogLogNo))) {
			swfLogDtoDealNode.getId().setFlowID(swfLogFlowID);
			swfLogDtoDealNode.getId().setLogNo(Integer.parseInt(DataUtils.nullToZero(swfLogLogNo)));
		}
		if (newCompensate == 1) {
			swfLogDtoDealNode.setBusinessNo(prpLcompensate.getClaimNo()); // 本节点的查找
			swfLogDtoDealNode.setNextBusinessNo(prpLcompensate.getCompensateNo());
		} else {
			swfLogDtoDealNode.setBusinessNo(prpLcompensate.getCompensateNo()); // 本节点的查找
			swfLogDtoDealNode.setNextBusinessNo(prpLcompensate.getClaimNo());
		}
		swfLogDtoDealNode.setKeyIn(prpLcompensate.getClaimNo());
		swfLogDtoDealNode.setNodeStatus(DataUtils.dbNullToEmpty(request.getParameter("buttonSaveType")));
		swfLogDtoDealNode.setKeyOut(prpLcompensate.getCompensateNo());
		// reason:如果是特殊赔案进行的核赔提交，则需要将businessno转换成计算书号码。
		if (("3".equals(caseType) || "4".equals(caseType) || "6".equals(caseType)) && "4".equals(swfLogDtoDealNode.getNodeStatus())) {
			swfLogDtoDealNode.setNextBusinessNo(prpLcompensate.getCompensateNo());
		}
		UserDto user = (UserDto) request.getSession().getAttribute("user");
		return this.getWorkFlowViewHelper().viewToDto(user, swfLogDtoDealNode);
	}
	
	/***
	 * 校驗險別賠付是否超過預估
	 * @return
	 * @throws Exception 
	 */
	public String checkKindPayLimit() throws Exception{
		HttpServletRequest request = getRequest();
		String claimNo = request.getParameter("claimNo");
		String propKind = request.getParameter("propKind");
		String propKindPay = request.getParameter("propKindPay");
		String personKind = request.getParameter("personKind");
		String personKindPay = request.getParameter("personKindPay");
		//本計算書各險別賠付
		Map<String , Double > currCompePay = new HashMap<String , Double >();
		if (!StringUtils.isEmpty(propKind)) {
			String[] propKindArray = StringUtils.split(propKind, ',');
			String[] propKindPayArray = StringUtils.split(propKindPay, ',');
			for (int i = 0, l = propKindArray.length; i < l; i++) {
				setPay(currCompePay, propKindArray[i], Double.parseDouble(propKindPayArray[i]));
			}
		}
		if (!StringUtils.isEmpty(personKind)) {
			String[] personKindArray = StringUtils.split(personKind, ',');
			String[] personKindPayArray = StringUtils.split(personKindPay, ',');
			for (int i = 0, l = personKindArray.length; i < l; i++) {
				setPay(currCompePay, personKindArray[i], Double.parseDouble(personKindPayArray[i]));
			}
		}
		//各險別預估
		Map<String , Double > estimatePay = new HashMap<String , Double >();
		PrpLclaimLossService prpLclaimLossService = (PrpLclaimLossService)ServiceFactory.getService("prpLclaimLossService");
		List<PrpLclaimLoss> claimLossList = prpLclaimLossService.findPrpLclaimLoss(claimNo);
		for (PrpLclaimLoss p : claimLossList) {
			if("P".equals(p.getLossFeeType())){//賠款
				setPay(estimatePay, p.getKindCode(), p.getSumClaim());
			}
		}
		List<String> checkMassage = new ArrayList<String>();
		Map<String , Map<String, Double> > claimKindMap = this.compensateService.getClaimKindCodePay(claimNo);
		//各險別已賠付
		Map<String,Double> passCompePay =  claimKindMap.get("C");
		Map<String,Double> passCompeReplevy =  claimKindMap.get("R");
		DecimalFormat df = new DecimalFormat("#,##0");
		for(Map.Entry<String,Double> entry : currCompePay.entrySet()){
			String key = entry.getKey();
			Double value = entry.getValue();
			if(!estimatePay.containsKey(key)){//險別沒有估損訊息
				checkMassage.add("險別 " + key +" 沒有估損訊息 ！");
			} else {
				Double passpay = passCompePay.get(key) == null ? 0d : passCompePay.get(key);//已賠付；
				Double passReplevy = passCompeReplevy.get(key) == null ? 0d : Math.abs(passCompeReplevy.get(key));//已追償 ；
				Double estpay = estimatePay.get(key);//估損訊息
				if(value > estpay - ( passpay - passReplevy )){//賠付 > 預估 - （ 已賠付- 已追償 ）
					StringBuilder sb = new StringBuilder();
					sb.append("險別 " + key + "：");
					sb.append(" 預估 " + df.format(estpay));
					if(passpay > 0){
						sb.append("，已賠付 " + df.format(passpay));
						if(passReplevy > 0){
							sb.append("（其中已追償 " + df.format(passReplevy)+"）");
						}
					}
					sb.append("，本次賠付 " + df.format(value) + "。");
					sb.append(" 超出預估 ！");
					checkMassage.add(sb.toString());
				}
			}
		}
		if(!checkMassage.isEmpty()){
			StringBuilder sb = new StringBuilder();
			for(String str : checkMassage){
				sb.append(str).append("\n");
			}
			this.writeJSONMsg(sb.toString());
		}
		return NONE;
	}
	/**
	 * 理算提交任意險自付額發票號唯一性校驗
	 * @return
	 */
	public String deductibleInvoiceCheck(){
		HttpServletRequest request = getRequest();
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		String deductibleInvoice = request.getParameter("deductibleInvoice");//自負額發票號
		String compensateNo = request.getParameter("compensateNo");//計算書號
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("deductibleInvoice", deductibleInvoice);
		if(StringUtil.isNotEmpty(compensateNo)){
			queryRule.addNotEqual("id.compensateNo", compensateNo);
		}
		try {
			List<PrpLcarInsurance> prpLcarInsuranceList = prpLcarInsuranceService.findPrpLcarInsurance(queryRule);
			jsonMap.put("count",prpLcarInsuranceList.size());
			HttpServletResponse response = super.getResponse();
			response.setContentType("text/html;charset=GBK");
			response.getWriter().write(JSONObject.fromObject(jsonMap).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NONE;
	}
	private void setPay(Map<String , Double > currCompePay , String kindCode , double payAmount){
		if(currCompePay.containsKey(kindCode)){
			currCompePay.put(kindCode, currCompePay.get(kindCode) + payAmount);
		}else {
			currCompePay.put(kindCode, payAmount);
		}
	}

	public CompensateService getCompensateService() {
		return compensateService;
	}

	public void setCompensateService(CompensateService compensateService) {
		this.compensateService = compensateService;
	}

	public SunnyCompensateViewHelper getSunnyCompensateViewHelper() {
		return sunnyCompensateViewHelper;
	}

	public void setSunnyCompensateViewHelper(SunnyCompensateViewHelper sunnyCompensateViewHelper) {
		this.sunnyCompensateViewHelper = sunnyCompensateViewHelper;
	}

	public AccidentCompensateViewHelper getAccidentCompensateViewHelper() {
		return accidentCompensateViewHelper;
	}

	public void setAccidentCompensateViewHelper(AccidentCompensateViewHelper accidentCompensateViewHelper) {
		this.accidentCompensateViewHelper = accidentCompensateViewHelper;
	}

	public RegistService getRegistService() {
		return registService;
	}

	public void setRegistService(RegistService registService) {
		this.registService = registService;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	public GeneralClaimService getGeneralClaimService() {
		return generalClaimService;
	}

	public void setGeneralClaimService(GeneralClaimService generalClaimService) {
		this.generalClaimService = generalClaimService;
	}

	public PrpLrecaseService getPrpLrecaseService() {
		return prpLrecaseService;
	}

	public void setPrpLrecaseService(PrpLrecaseService prpLrecaseService) {
		this.prpLrecaseService = prpLrecaseService;
	}

	public BillService getBillService() {
		return billService;
	}

	public void setBillService(BillService billService) {
		this.billService = billService;
	}

	public WorkFlowViewHelper getWorkFlowViewHelper() {
		return workFlowViewHelper;
	}

	public void setWorkFlowViewHelper(WorkFlowViewHelper workFlowViewHelper) {
		this.workFlowViewHelper = workFlowViewHelper;
	}

	public SwfLogService getSwfLogService() {
		return swfLogService;
	}

	public void setSwfLogService(SwfLogService swfLogService) {
		this.swfLogService = swfLogService;
	}

	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	public JbpmBusinessViewHelper getJbpmBusinessViewHelper() {
		return jbpmBusinessViewHelper;
	}

	public void setJbpmBusinessViewHelper(JbpmBusinessViewHelper jbpmBusinessViewHelper) {
		this.jbpmBusinessViewHelper = jbpmBusinessViewHelper;
	}

	public EmailService getEmailService() {
		return emailService;
	}

	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

	public ShipPrintViewHelper getShipPrintViewHelper() {
		return shipPrintViewHelper;
	}

	public void setShipPrintViewHelper(ShipPrintViewHelper shipPrintViewHelper) {
		this.shipPrintViewHelper = shipPrintViewHelper;
	}

	public GAAPrintViewHelper getGaaPrintViewHelper() {
		return gaaPrintViewHelper;
	}

	public void setGaaPrintViewHelper(GAAPrintViewHelper gaaPrintViewHelper) {
		this.gaaPrintViewHelper = gaaPrintViewHelper;
	}

	public LiabPrintViewHelper getLiabPrintViewHelper() {
		return liabPrintViewHelper;
	}

	public void setLiabPrintViewHelper(LiabPrintViewHelper liabPrintViewHelper) {
		this.liabPrintViewHelper = liabPrintViewHelper;
	}

	public PropPrintViewHelper getPropPrintViewHelper() {
		return propPrintViewHelper;
	}

	public void setPropPrintViewHelper(PropPrintViewHelper propPrintViewHelper) {
		this.propPrintViewHelper = propPrintViewHelper;
	}

	public AcciPrintViewHelper getAcciPrintViewHelper() {
		return acciPrintViewHelper;
	}

	public void setAcciPrintViewHelper(AcciPrintViewHelper acciPrintViewHelper) {
		this.acciPrintViewHelper = acciPrintViewHelper;
	}
	

	public PrpLcarInsuranceService getPrpLcarInsuranceService() {
		return prpLcarInsuranceService;
	}

	public void setPrpLcarInsuranceService(PrpLcarInsuranceService prpLcarInsuranceService) {
		this.prpLcarInsuranceService = prpLcarInsuranceService;
	}

    public BusinessViewHelper getBusinessViewHelper() {
        return businessViewHelper;
    }

    public void setBusinessViewHelper(BusinessViewHelper businessViewHelper) {
        this.businessViewHelper = businessViewHelper;
    }

  //mantis：CLM0126，處理人員：DP0713，需求單編號：受款人ID檢核 START
	public PrpLuserService getPrpLuserService() {
		return prpLuserService;
	}

	public void setPrpLuserService(PrpLuserService prpLuserService) {
		this.prpLuserService = prpLuserService;
	}

	public PrpDuserService getPrpDuserService() {
		return prpDuserService;
	}

	public void setPrpDuserService(PrpDuserService prpDuserService) {
		this.prpDuserService = prpDuserService;
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
	
	//mantis：CLM0126，處理人員：DP0713，需求單編號：受款人ID檢核 END
	
	//mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單 START
	public VehicleClaimApiLogService getVehicleClaimApiLogService() {
		return vehicleClaimApiLogService;
	}

	public void setVehicleClaimApiLogService(
			VehicleClaimApiLogService vehicleClaimApiLogService) {
		this.vehicleClaimApiLogService = vehicleClaimApiLogService;
	}

	public ClaimCompulsoryApplicantService getClaimCompulsoryApplicantService() {
		return claimCompulsoryApplicantService;
	}

	public void setClaimCompulsoryApplicantService(
			ClaimCompulsoryApplicantService claimCompulsoryApplicantService) {
		this.claimCompulsoryApplicantService = claimCompulsoryApplicantService;
	}

	public ClaimCompulsoryApportionService getClaimCompulsoryApportionService() {
		return claimCompulsoryApportionService;
	}

	public void setClaimCompulsoryApportionService(
			ClaimCompulsoryApportionService claimCompulsoryApportionService) {
		this.claimCompulsoryApportionService = claimCompulsoryApportionService;
	}

	public ClaimCompulsoryCaseService getClaimCompulsoryCaseService() {
		return claimCompulsoryCaseService;
	}

	public void setClaimCompulsoryCaseService(
			ClaimCompulsoryCaseService claimCompulsoryCaseService) {
		this.claimCompulsoryCaseService = claimCompulsoryCaseService;
	}

	public ClaimCompulsoryChargesService getClaimCompulsoryChargesService() {
		return claimCompulsoryChargesService;
	}

	public void setClaimCompulsoryChargesService(
			ClaimCompulsoryChargesService claimCompulsoryChargesService) {
		this.claimCompulsoryChargesService = claimCompulsoryChargesService;
	}

	public ClaimCompulsoryStatePricesService getClaimCompulsoryStatePricesService() {
		return claimCompulsoryStatePricesService;
	}

	public void setClaimCompulsoryStatePricesService(
			ClaimCompulsoryStatePricesService claimCompulsoryStatePricesService) {
		this.claimCompulsoryStatePricesService = claimCompulsoryStatePricesService;
	}

	public EndorseViewHelper getEndorseViewHelper() {
		return endorseViewHelper;
	}

	public void setEndorseViewHelper(EndorseViewHelper endorseViewHelper) {
		this.endorseViewHelper = endorseViewHelper;
	}

	public PrpLthirdPartyService getPrpLthirdPartyService() {
		return prpLthirdPartyService;
	}

	public void setPrpLthirdPartyService(PrpLthirdPartyService prpLthirdPartyService) {
		this.prpLthirdPartyService = prpLthirdPartyService;
	}
	//mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單 END
    
}