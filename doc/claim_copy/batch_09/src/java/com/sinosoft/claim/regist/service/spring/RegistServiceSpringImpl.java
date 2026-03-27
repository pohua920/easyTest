package com.sinosoft.claim.regist.service.spring;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;//mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案
import java.util.Random;
import java.util.TreeSet;

import javax.servlet.http.HttpSession;//mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案

import org.hibernate.Session;

//mantis：CLM0282、CLM9009 ，處理人員： DP0713 ，需求單編號：多元理賠備案_非車全險種
import com.sinosoft.app.webservice.server.schema.model.regist.vo.ClaimExternalRiskSourceVo;
import com.sinosoft.app.webservice.server.schema.model.regist.vo.ClaimExternalSourceVo;//mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案
import com.sinosoft.claim.check.vo.AcciCheckDto;
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.common.vo.CaseRelateNodeDto;
import com.sinosoft.claim.compensate.vo.CompensateFeeDto;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.regist.service.facade.RegistService;
import com.sinosoft.claim.regist.vo.RegistClaimInfoDto;
import com.sinosoft.claim.regist.vo.RegistDto;
//mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案 START
import com.sinosoft.claim.email.service.facade.EmailService;
import com.sinosoft.claim.email.vo.EmailDto;
import com.sinosoft.claim.schema.model.PrpCmain;
//mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案 END
import com.sinosoft.claim.schema.model.PrpLacciCheck;
import com.sinosoft.claim.schema.model.PrpLacciCheckText;
import com.sinosoft.claim.schema.model.PrpLcallCenter;
import com.sinosoft.claim.schema.model.PrpLcheckId;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLclaimStatusId;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLextId;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.model.PrpLregistExt;
import com.sinosoft.claim.schema.model.PrpLregistText;
import com.sinosoft.claim.schema.model.PrpLthirdCarLoss;
import com.sinosoft.claim.schema.service.facade.PrpCmainService;
import com.sinosoft.claim.schema.service.facade.PrpLacciCheckService;
import com.sinosoft.claim.schema.service.facade.PrpLacciCheckTextService;
import com.sinosoft.claim.schema.service.facade.PrpLacciPersonService;
import com.sinosoft.claim.schema.service.facade.PrpLcallCenterService;
import com.sinosoft.claim.schema.service.facade.PrpLcheckService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimStatusService;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.claim.schema.service.facade.PrpLdriverService;
import com.sinosoft.claim.schema.service.facade.PrpLextService;
import com.sinosoft.claim.schema.service.facade.PrpLpersonTraceService;
import com.sinosoft.claim.schema.service.facade.PrpLregistExtService;
import com.sinosoft.claim.schema.service.facade.PrpLregistLogService;
import com.sinosoft.claim.schema.service.facade.PrpLregistService;
import com.sinosoft.claim.schema.service.facade.PrpLregistTextService;
import com.sinosoft.claim.schema.service.facade.PrpLrelatePersonService;
import com.sinosoft.claim.schema.service.facade.PrpLscheduleItemService;
import com.sinosoft.claim.schema.service.facade.PrpLscheduleMainWFService;
import com.sinosoft.claim.schema.service.facade.PrpLthirdCarLossService;
import com.sinosoft.claim.schema.service.facade.PrpLthirdPartyService;
import com.sinosoft.claim.schema.service.facade.PrpLthirdPropService;
import com.sinosoft.claim.schema.service.facade.PrpLverifyLossService;
import com.sinosoft.claim.schema.service.facade.PrplregistrpolicyService;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;
import com.sinosoft.one.bpm.aspect.ProcessTask;
import com.sinosoft.one.bpm.aspect.StartProcess;
import com.sinosoft.one.bpm.aspect.TaskParam;
import com.sinosoft.one.bpm.aspect.TaskParams;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.common.util.DataUtils;
import com.sinosoft.sysframework.common.util.StringUtils;

/**
 * 报案数据库管理对象
 * <p>
 * Title: 车险理赔报案数据管理
 * </p>
 * <p>
 * Description: 车险理赔报案数据管理
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * @author 中科软
 * @version 1.0
 */
public class RegistServiceSpringImpl extends GenericDaoHibernate<RegistDto, String> implements RegistService {
	/** 报案service */
	private PrpLregistService prpLregistService;
	/** 赔案保单关联service */
	private PrplregistrpolicyService prpLregistrpolicyService;
	/** 理赔车辆service */
	private PrpLthirdPartyService prpLthirdPartyService;
	/** 车险驾驶员service */
	private PrpLdriverService prpLdriverService;
	/** 报案文字service */
	private PrpLregistTextService prpLregistTextService;
	/** 人伤跟踪service */
	private PrpLacciPersonService prpLacciPersonService;
	/** 备注摘要service */
	private PrpLextService prpLextService;
	/** 损失部位service */
	private PrpLthirdCarLossService prpLthirdCarLossService;
	/** 财产损失部位service */
	private PrpLthirdPropService prpLthirdPropService;
	/** 人伤跟踪service */
	private PrpLpersonTraceService prpLpersonTraceService;
	/** 报案信息补充说明service */
	private PrpLregistExtService prpLregistExtService;
	/** 调度任务/查勘任务service */
	private PrpLscheduleMainWFService prpLscheduleMainWFService;
	/** 调度任务标的service */
	private PrpLscheduleItemService prpLscheduleItemService;
	/** 联系人service */
	private PrpLrelatePersonService prpLrelatePersonService;
	/** 呼叫中心service */
	private PrpLcallCenterService prpLcallCenterService;
	/** 理赔节点状态service */
	private PrpLclaimStatusService prpLclaimStatusService;
	/** 立案信息service */
	private PrpLclaimService prpLclaimService;
	/** 报案修改轨迹信息service */
	private PrpLregistLogService prpLregistLogService;
	/** 赔款计算书信息service */
	private PrpLcompensateService prpLcompensateService;
	/** 定核损信息service */
	private PrpLverifyLossService prpLverifyLossService;
	/** 查勘/代查勘service */
	private PrpLcheckService prpLcheckService;
	/** 代码翻译service */
	private CodeService codeService;
	/** 保单基本信息service */
	private PrpCmainService prpCmainService;
	/** 工作流处理service */
	private WorkFlowService workFlowService;
	private PrpLacciCheckService prpLacciCheckService;
	private PrpLacciCheckTextService prpLacciCheckTextService;
	
	/** mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案 **/
	private EmailService emailService;

	public PrpCmainService getPrpCmainService() {
		return prpCmainService;
	}

	public void setPrpCmainService(PrpCmainService prpCmainService) {
		this.prpCmainService = prpCmainService;
	}

	/**
	 * 报案保存方法
	 * @param registDto 报案对象
	 * @throws SQLException
	 * @throws Exception
	 * @return 无
	 */
	public void save(RegistDto registDto) throws SQLException, Exception {
		boolean flag = prpLregistService.isExist(registDto.getPrpLregist().getRegistNo());

		if (flag) {
			this.deleteSubInfo(registDto);
		}
		if (registDto.getPrpLregist() == null) {
			throw new Exception("數據異常");
		}
		prpLregistService.saveOrUpdate(registDto.getPrpLregist());
		// 首先删除原来的相关数据
		// 强三 -----start
		if (registDto.getPrpLRegistRPolicyList() != null) {
			prpLregistrpolicyService.saveOrUpdate(registDto.getPrpLRegistRPolicyList());
		}
		// 强三 -----end

		if (registDto.getPrpLthirdPartyList() != null) {
			prpLthirdPartyService.saveOrUpdate(registDto.getPrpLthirdPartyList());
		}
		if (registDto.getPrpLdriverList() != null) {
			prpLdriverService.saveOrUpdate(registDto.getPrpLdriverList());
		}
		if (registDto.getPrpLregistTextList() != null) {
			prpLregistTextService.saveOrUpdate(registDto.getPrpLregistTextList());
		}

		// 原因：添加呈报信息
		if (registDto.getPrpLregistTextList2() != null) {
			prpLregistTextService.saveOrUpdate(registDto.getPrpLregistTextList2());
		}

		// 原因：添加出险人员信息
		if (registDto.getPrpLacciPerson() != null) {
			prpLacciPersonService.saveOrUpdate(registDto.getPrpLacciPerson());
		}

		if (registDto.getPrpLext() != null) {
			prpLextService.saveOrUpdate(registDto.getPrpLext());
		}

		if (registDto.getPrpLthirdCarLossList() != null) {
			prpLthirdCarLossService.saveOrUpdate(registDto.getPrpLthirdCarLossList());
		}
		if (registDto.getPrpLthirdPropList() != null) {
			prpLthirdPropService.saveOrUpdate(registDto.getPrpLthirdPropList());
		}

		if (registDto.getPrpLpersonTraceList() != null) {
			prpLpersonTraceService.saveOrUpdate(registDto.getPrpLpersonTraceList());
		}

		if (registDto.getPrpLregistExtList() != null) {
			prpLregistExtService.saveOrUpdate(registDto.getPrpLregistExtList());
		}

		// 暂存不保存调度信息
		if ("4".equals(registDto.getPrpLclaimStatus().getStatus())) {
			if (registDto.getPrpLscheduleMainWF() != null) {
				prpLscheduleMainWFService.saveOrUpdate(registDto.getPrpLscheduleMainWF());
			}
			if (registDto.getPrpLscheduleItemList() != null) {
				prpLscheduleItemService.saveAndDelete(registDto.getPrpLscheduleItemList());
			}
		}

		if (registDto.getPrpLrelatePersonList() != null) {
			prpLrelatePersonService.saveOrUpdate(registDto.getPrpLrelatePersonList());
		}

		// 进行状态的改变
		if (registDto.getPrpLcallCenter() != null) {
			prpLcallCenterService.saveOrUpdate(registDto.getPrpLcallCenter());
		}
		updateClaimStatus(registDto);

	}

	/**
	 * 变更立案的操作状态的方法
	 * @param claimDto 立案对象
	 * @throws SQLException
	 * @throws Exception
	 * @return 无
	 */
	public void updateClaimStatus(RegistDto registDto) throws SQLException, Exception {
		if (registDto.getPrpLclaimStatus() != null) {
			prpLclaimStatusService.deleteByRegistNo(registDto.getPrpLclaimStatus().getId().getBusinessNo(), "regis");
			prpLclaimStatusService.saveOrUpdate(registDto.getPrpLclaimStatus());
		}
	}

	/**
	 * 报案查询方法
	 * @param registDto 报案对象
	 * @throws SQLException
	 * @throws Exception
	 * @return 无
	 */
	public RegistDto findByPrimaryKey(String registNo) throws SQLException, Exception {
		RegistDto registDto = new RegistDto();
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.registNo", StringUtils.rightTrim(registNo));
		QueryRule queryRule1 = QueryRule.getInstance();
		queryRule1.addEqual("id.registNo", StringUtils.rightTrim(registNo));
		queryRule1.addAscOrder("id.serialNo");
		registDto.setPrpLregist(this.prpLregistService.findPrpLregist(registNo));
		registDto.setPrpLext(this.prpLextService.findPrpLext(new PrpLextId(registNo, "03")));
		registDto.setPrpLthirdPartyList(this.prpLthirdPartyService.findPrpLthirdParty(queryRule1));
		registDto.setPrpLdriverList(this.prpLdriverService.findPrpLdriver(queryRule));
		registDto.setPrpLthirdCarLossList(this.prpLthirdCarLossService.findPrpLthirdCarLoss(queryRule));
		registDto.setPrpLthirdPropList(this.prpLthirdPropService.findPrpLthirdProp(queryRule));
		registDto.setPrpLpersonTraceList(this.prpLpersonTraceService.findPrpLpersonTrace(queryRule));
		registDto.setPrpLregistExtList(this.prpLregistExtService.findPrpLregistExt(queryRule));
		QueryRule queryRuleText = QueryRule.getInstance();
		queryRuleText.addEqual("id.registNo", StringUtils.rightTrim(registNo));
		queryRuleText.addAscOrder("id.textType");
		queryRuleText.addAscOrder("id.lineNo");
		registDto.setPrpLregistTextList(this.prpLregistTextService.findPrpLregistText(queryRuleText));
		registDto.setPrpLrelatePersonList(this.prpLrelatePersonService.findPrpLrelatePerson(queryRule));
		QueryRule queryRule2 = QueryRule.getInstance();
		queryRule2.addEqual("id.registNo", StringUtils.rightTrim(registNo));
		queryRule2.addEqual("id.scheduleID", 1);
		registDto.setPrpLscheduleMainWF(this.prpLscheduleMainWFService.findPrpLscheduleMainWF(1, registNo));
		registDto.setPrpLscheduleItemList(this.prpLscheduleItemService.findPrpLscheduleItem(queryRule2));
		registDto.setPrpLclaimStatus(this.prpLclaimStatusService.findPrpLclaimStatus(new PrpLclaimStatusId(registNo, "regis", 0)));// 不知道为啥之前是"claim"
		// 原因：向registDto中增加出险人员信息表 PrpLacciPersoDto
		registDto.setPrpLacciPerson(this.prpLacciPersonService.findPrpLacciPerson(registNo));
		// 强三关联查询
		QueryRule queryRule3 = QueryRule.getInstance();
		queryRule3.addEqual("id.registNo", StringUtils.rightTrim(registNo));
		queryRule3.addEqual("validStatus", "1");
		registDto.setPrpLRegistRPolicyList(prpLregistrpolicyService.findPrplregistrpolicy(queryRule3));
		registDto.setPrpLext(this.prpLextService.findPrpLext(new PrpLextId(registNo, "01")));

		// 原因：添加调查信息
		// 由於没有生成acciCheck实体类，所以暂时注掉，後期在改
		AcciCheckDto acciCheckDto = new AcciCheckDto();
		// new DBPrpLacciCheck(dbManager).findByRegistNoMaxTimes(registNo);
		int time = prpLacciCheckService.findByRegistNoMaxTimes(registNo);
		// ArrayList acciCheckDtoList = (ArrayList) new
		// DBPrpLacciCheck(dbManager).findByConditions(conditions);
		QueryRule queryRule4 = QueryRule.getInstance();
		queryRule4.addEqual("registNo", registNo);
		queryRule4.addEqual("times", time);
		List<PrpLacciCheck> acciCheckDtoList = prpLacciCheckService.findPrpLacciCheck(queryRule4);
		if (acciCheckDtoList != null && acciCheckDtoList.size() > 0) {
			acciCheckDto.setPrpLacciCheck(acciCheckDtoList.get(0));
			// acciCheckDto.setPrpLacciCheckTextList((ArrayList)
			// new DBPrpLacciCheckText(dbManager).findByConditions("CheckNo = '"
			// + acciCheckDto.getPrpLacciCheck().getCheckNo() + "'"));
			QueryRule queryRule5 = QueryRule.getInstance();
			queryRule5.addEqual("id.checkNo", acciCheckDto.getPrpLacciCheck().getCheckNo());
			List<PrpLacciCheckText> prpLacciCheckTextlist = prpLacciCheckTextService.findPrpLacciCheckText(queryRule5);
			acciCheckDto.setPrpLacciCheckTextList(prpLacciCheckTextlist);
			// acciCheckDto.setPrpLregistDto(new
			// DBPrpLregist(dbManager).findByPrimaryKey(registNo));
			acciCheckDto.setPrpLregist(prpLregistService.findPrpLregist(registNo));
		}
		registDto.setAcciCheckDto(acciCheckDto);
		return registDto;
	}

	/*
	 * 报案查询方法 @param registDto 报案对象 @throws SQLException @throws Exception
	 * @return 无
	 */

	/**
	 * 报案保存注销信息
	 * @param registDto 报案对象
	 * @throws SQLException
	 * @throws Exception
	 * @return 无
	 */
	public void insertRegistCancel(RegistDto registDto) throws SQLException, Exception {
		if (registDto.getPrpLregist() == null) {
			throw new Exception("沒有報案信息");
		}
		String statement = "";
		// 更新报案表信息
		String registNo = "";
		registNo = registDto.getPrpLregist().getRegistNo();
		Session session = super.getSession();
		if (registDto.getPrpLRegistRPolicy() == null) { // 不单独更新关联表的时候，才更新报案的
			this.prpLregistService.update(registDto.getPrpLregist());
			statement = " update prplregistrpolicy set validstatus='0' Where  registNo = '" + StringUtils.rightTrim(registNo) + "'";
			HibernateUtils.executeSql(session, statement);
			// 增加需要将关联表中的信息全部注销掉的过程
		}
		// 保存报案注销原因
		String condition = " registNo = '" + StringUtils.rightTrim(registNo) + "' and texttype='2'";
		statement = " DELETE FROM PrpLregistText Where " + condition;
		HibernateUtils.executeSql(session, statement);
		if (registDto.getPrpLregistTextList() != null) {
			prpLregistTextService.saveOrUpdate(registDto.getPrpLregistTextList());
		}
		// 加入报案关联表的关联
		if (registDto.getPrpLRegistRPolicy() != null) {
			prpLregistrpolicyService.save(registDto.getPrpLRegistRPolicy());
			// 处理如果注销掉的是主报案上的保单号这种情况，需要批量进行保单号码的代换，比较麻烦的。
			if (registDto.getPrpLRegistRPolicy().getId().getPolicyNo().equals(registDto.getPrpLregist().getPolicyNo())) {
				// 1。取出PrpLRegistRPolicy还算有效的PolicyNo进行替换.
				// 替换的表很多。从报案开始。。(报案，调度，查勘，定损，工作流。。等等。。6月22日完成这部分的代码)
			}
		}
	}

	/**
	 * 生成报案修改轨迹
	 * @param fcoClaimNoticeNo
	 * @throws SQLException
	 * @throws Exception
	 */
	public String generateAlterLocus(PrpLregist originalPrpLregist, RegistDto registDto) throws Exception {
		String alterLocus = "";
		PrpLregist newPrpLregist = registDto.getPrpLregist();
		if (!DataUtils.dbNullToEmpty(newPrpLregist.getReportorName()).equals(DataUtils.dbNullToEmpty(originalPrpLregist.getReportorName()))) {
			alterLocus += "報案人姓名：從“" + originalPrpLregist.getReportorName() + "”修改為“" + newPrpLregist.getReportorName() + "”； ";
		}
		if (!DataUtils.dbNullToEmpty(newPrpLregist.getReportorPhoneNumber()).equals(DataUtils.dbNullToEmpty(originalPrpLregist.getReportorPhoneNumber()))) {
			alterLocus += "報案人電話：從“" + originalPrpLregist.getReportorPhoneNumber() + "”修改為“" + newPrpLregist.getReportorPhoneNumber() + "”； ";
		}
		if (!DataUtils.dbNullToEmpty(newPrpLregist.getLinkerName()).equals(DataUtils.dbNullToEmpty(originalPrpLregist.getLinkerName()))) {
			alterLocus += "聯繫人：從“" + originalPrpLregist.getLinkerName() + "”修改為“" + newPrpLregist.getLinkerName() + "”； ";
		}
		if (!DataUtils.dbNullToEmpty(newPrpLregist.getPhoneNumber()).equals(DataUtils.dbNullToEmpty(originalPrpLregist.getPhoneNumber()))) {
			alterLocus += "聯繫人電話：從“" + originalPrpLregist.getPhoneNumber() + "”修改為“" + newPrpLregist.getPhoneNumber() + "”； ";
		}
		if ((!newPrpLregist.getDamageStartDate().equals(originalPrpLregist.getDamageStartDate())) || (!newPrpLregist.getDamageStartHour().equals(originalPrpLregist.getDamageStartHour()))) {
			alterLocus += "出險時間：從“" + originalPrpLregist.getDamageStartDate() + " " + originalPrpLregist.getDamageStartHour() + "”修改为“" + newPrpLregist.getDamageStartDate() + " " + newPrpLregist.getDamageStartHour() + "”； ";
		}
		if (!DataUtils.dbNullToEmpty(newPrpLregist.getDamageName()).equals(DataUtils.dbNullToEmpty(originalPrpLregist.getDamageName()))) {
			alterLocus += "出險原因：從“" + originalPrpLregist.getDamageName() + "”修改為“" + newPrpLregist.getDamageName() + "”； ";
		}
		if (!DataUtils.dbNullToEmpty(newPrpLregist.getDamageAddress()).equals(DataUtils.dbNullToEmpty(originalPrpLregist.getDamageAddress()))) {
			alterLocus += "出險地點：從“" + originalPrpLregist.getDamageAddress() + "”修改為“" + newPrpLregist.getDamageAddress() + "”； ";
		}
		if (!DataUtils.dbNullToEmpty(newPrpLregist.getPhoneNumber()).equals(DataUtils.dbNullToEmpty(originalPrpLregist.getPhoneNumber()))) {
			alterLocus += "聯繫人電話：從“" + originalPrpLregist.getPhoneNumber() + "”修改為“" + newPrpLregist.getPhoneNumber() + "”； ";
		}

		// 出险摘要和附加信息
		String originalRegistContext = "";
		String originalAddInformation = "";
		String newRegistContext = "";
		String newAddInformation = "";
		
		if (registDto.getPrpLregistTextList() != null && registDto.getPrpLregistTextList().size() > 0) {
			for (int i = 0; i < registDto.getPrpLregistTextList().size(); i++) {
				PrpLregistText prpLregistText = (PrpLregistText) registDto.getPrpLregistTextList().get(i);
				newRegistContext += prpLregistText.getContext();
			}
		}
		if (registDto.getPrpLregistTextList2() != null && registDto.getPrpLregistTextList2().size() > 0) {
			for (int i = 0; i < registDto.getPrpLregistTextList2().size(); i++) {
				PrpLregistText prpLregistText = (PrpLregistText) registDto.getPrpLregistTextList2().get(i);
				if ("5".equals(prpLregistText.getId().getTextType())) {
					newAddInformation += prpLregistText.getContext();
				}
			}
		}
		if (!originalRegistContext.equals(newRegistContext)) {
			alterLocus += "出險摘要：從“" + originalRegistContext + "”修改為“" + newRegistContext + "”； ";
		}
		if (!originalAddInformation.equals(newAddInformation)) {
			alterLocus += "附加信息：從“" + originalAddInformation + "”修改為“" + newAddInformation + "”。 ";
		}
		if ("".equals(alterLocus)) {
			alterLocus += "沒有修改任何信息！";
		}
		alterLocus = new DateTime(DateTime.current()) + " 修改人：" + newPrpLregist.getReceiverName() + " ；修改內容：" + alterLocus;
		return alterLocus;
	}

	/**
	 * @param policyNo
	 * @return
	 * @throws Exception 根据保单号查询报案和连信息
	 */
	public List<RegistClaimInfoDto> findByPolicyNo(String policyNo) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("policyNo", policyNo);
		List<PrpLregist> prpLregistList = prpLregistService.findPrpLregist(queryRule);
		List<RegistClaimInfoDto> registClaimDtoList = new ArrayList<RegistClaimInfoDto>();
		RegistClaimInfoDto registClaimInfoDto = null;
		PrpLregist prpLregist = null;
		PrpLcompensate prpLcompensate = null;
		List<PrpLclaim> prpLclaimList = null;
		List<PrpLcompensate> prpLcompensateList = null;
		List<PrpLthirdCarLoss> prpLthirdCarLossList = null;
		for (int i = 0; i < prpLregistList.size(); i++) {
			prpLregist = prpLregistList.get(i);
			registClaimInfoDto = new RegistClaimInfoDto(prpLregist);
			double sumPaidShow = 0D;
			try {
				prpLclaimList = prpLclaimService.findByRegistNo(prpLregist.getRegistNo());
				;
				prpLthirdCarLossList = prpLthirdCarLossService.findByRegistNo(prpLregist.getRegistNo());
				;
			} catch (Exception e) {
				prpLclaimList = null;
			}
			if (prpLthirdCarLossList.size() < 1) {
				registClaimInfoDto.setCompName("");
			} else {
				PrpLthirdCarLoss prpLthirdCar = prpLthirdCarLossList.get(0);
				registClaimInfoDto.setCompName(prpLthirdCar.getCompName());
			}

			if (prpLclaimList == null) {
				prpLclaimList = new ArrayList<PrpLclaim>();
			}

			if (prpLclaimList.size() < 1) {
				registClaimInfoDto.setClaimNo("");
				registClaimInfoDto.setSumClaim(0);
				registClaimInfoDto.setSumPaidShow(0);

				registClaimInfoDto.setStatus("未结案");
				// reasion:增加一个报案注销的判断，如果是注销的情况，就不在已出险次数计算之列了。
				if (prpLregist.getCancelDate() != null && !"".equals(prpLregist.getCancelDate().toString())) {
					registClaimInfoDto.setStatus("已註銷");
				}
			} else {
				PrpLclaim prpLclaim = prpLclaimList.get(0);
				prpLcompensateList = prpLcompensateService.findByClaimNo(prpLclaim.getClaimNo());
				for (int j = 0; j < prpLcompensateList.size(); j++) {
					prpLcompensate = prpLcompensateList.get(j);
					sumPaidShow = sumPaidShow + prpLcompensate.getSumPaid();
				}
				registClaimInfoDto.setClaimNo(prpLclaim.getClaimNo());
				registClaimInfoDto.setSumClaim(prpLclaim.getSumClaim());
				registClaimInfoDto.setSumPaidShow(sumPaidShow);
				// DateTime对象不为null，还需要对equals("")的判断
				if (prpLclaim.getEndCaseDate() != null && !prpLclaim.getEndCaseDate().toString().equals("")) {
					registClaimInfoDto.setStatus("已结案");
				} else {
					registClaimInfoDto.setStatus("未结案");
				}
			}
			registClaimDtoList.add(registClaimInfoDto);
		}
		return registClaimDtoList;
	}

	/**
	 * @param conditions
	 * @param pageNo
	 * @param rowsPerPage
	 * @return
	 * @throws Exception 报案的查询
	 */
	public Page findByQueryConditions(String conditions) throws Exception {
		return this.findByQueryConditions(conditions, 0, 20);
	}

	/**
	 * @param conditions
	 * @param pageNo
	 * @param rowsPerPage
	 * @return
	 * @throws Exception 报案的查询
	 */
	public Page findByQueryConditions(String conditions, String strPageNo, String rowsPerPage) throws Exception {
		int pageNo = 0;
		if (strPageNo != null && !strPageNo.equals("")) {
			pageNo = Integer.parseInt(strPageNo);
		}
		int pageSize = 20;
		if (rowsPerPage != null && !rowsPerPage.equals("")) {
			pageSize = Integer.parseInt(rowsPerPage);
		}
		return this.findByQueryConditions(conditions, pageNo, pageSize);
	}

	/**
	 * @param conditions
	 * @param pageNo
	 * @param rowsPerPage
	 * @return
	 * @throws Exception 报案的查询
	 */
	public Page findByQueryConditions(String conditions, int pageNo, int pageSize) throws Exception {
		String statement = "";
		conditions = conditions.replaceAll("prplregist", "a");
		conditions = conditions.replaceAll("c.policyNo", "prplregist.policyNo");
		// 强三查询
		statement = "Select DISTINCT  prpLregist.RegistNo," + " prpLregist.PolicyNo, " + " prpLregist.ReceiverName, " + " b.Status, " + " b.OperateDate, " + " b.RiskCode, " + " prpLregist.LicenseNo, "
				+ " prpLregist.InsuredName, prpLregist.canceldate," + "prplregist.OperatorCode, " + "prplregist.reportDate" + " From  PrpLClaimStatus b Right JOIN prpLregist ON  prpLregist.RegistNo = b.BusinessNo " + "where b.nodetype='regis' and "
				+ conditions;
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Page page = HibernateUtils.findPagebySql(session, statement, pageNo, pageSize);
		List<PrpLregist> resultList = new ArrayList<PrpLregist>();
		List<?> tempList = page.getResult();
		PrpLregist prpLregist = null;
		Object[] object = null;
		List<?> tempListSub = null;
		for (Iterator<?> it = tempList.iterator(); it.hasNext(); resultList.add(prpLregist)) {
			object = (Object[]) it.next();// 每行记录不在是一个对象 而是一个数组
			prpLregist = new PrpLregist();
			prpLregist.setRegistNo((String) object[0]);
			prpLregist.setPolicyNo((String) object[1]);
			prpLregist.setReceiverName((String) object[2]);
			prpLregist.setStatus((String) object[3]);
			prpLregist.setOperateDate(new Date(((Timestamp) object[4]).getTime()));
			prpLregist.setRiskCode((String) object[5]);
			prpLregist.setLicenseNo((String) object[6]);
			prpLregist.setInsuredName((String) object[7]);
			if (object[8] != null) {
				prpLregist.setCancelDate(new Date(((Timestamp) object[8]).getTime()));
			} else {
				prpLregist.setCancelDate(null);
			}
			prpLregist.setOperatorCode((String) object[9]);
			prpLregist.setOperatorName(codeService.translateCode(ConstantCodes.CodeConfig.USERCODE, (String) object[9], ConstantCodes.Language.CHINESE));
			prpLregist.setReportDate((Date) object[10]);
			// 强三查询
			prpLregist.setRelatepolicyNo(new TreeSet<String>());
			statement = "select PolicyNo from prplregistrpolicy where RegistNo='" + (String) object[0] + "'";
			tempListSub = HibernateUtils.findbySql(session, statement, 0, 0);
			for (Iterator<?> itSub = tempListSub.iterator(); itSub.hasNext();) {
				prpLregist.getRelatepolicyNo().add((String) itSub.next());
			}
		}
		return new Page(page.getStart(), page.getTotalCount(), page.getPageSize(), resultList);
	}

	/** 备案查询 */
	public Page findRegistByConditions(String conditions, int pageNo, int pageSize) {
		String statement = "Select distinct a.RegistNo,a.PolicyNo,b.Status,b.OperateDate,b.RiskCode,a.LicenseNo, "
				+ " a.InsuredName,a.canceldate,b.handlerCode From prpLregist a left join PrpLClaimStatus b ON a.RegistNo = b.BusinessNo where b.nodetype='regis' " + conditions;
		Page page = HibernateUtils.findPagebySql(super.getSession(), statement, pageNo, pageSize);
		List<PrpLregist> resultList = new ArrayList<PrpLregist>();
		List<?> tempList = page.getResult();
		PrpLregist prpLregist = null;
		Object[] object = null;
		List<?> tempListSub = null;
		for (Iterator<?> it = tempList.iterator(); it.hasNext(); resultList.add(prpLregist)) {
			object = (Object[]) it.next();// 每行记录不在是一个对象 而是一个数组
			prpLregist = new PrpLregist();
			prpLregist.setRegistNo((String) object[0]);
			prpLregist.setPolicyNo((String) object[1]);
			prpLregist.setStatus((String) object[2]);
			prpLregist.setOperateDate(new Date(((Timestamp) object[3]).getTime()));
			prpLregist.setRiskCode((String) object[4]);
			prpLregist.setLicenseNo((String) object[5]);
			prpLregist.setInsuredName((String) object[6]);
			if (object[7] != null) {
				prpLregist.setCancelDate(new Date(((Timestamp) object[7]).getTime()));
			} else {
				prpLregist.setCancelDate(null);
			}
			prpLregist.setOperatorCode((String) object[8]);
			prpLregist.setOperatorName(codeService.translateCode(ConstantCodes.CodeConfig.USERCODE, prpLregist.getOperatorCode(), ConstantCodes.Language.CHINESE));
			// 强三查询
			prpLregist.setRelatepolicyNo(new TreeSet<String>());
			statement = "select PolicyNo from prplregistrpolicy where RegistNo='" + (String) object[0] + "'";
			tempListSub = HibernateUtils.findbySql(super.getSession(), statement, 0, 0);
			for (Iterator<?> itSub = tempListSub.iterator(); itSub.hasNext();) {
				prpLregist.getRelatepolicyNo().add((String) itSub.next());
			}
		}
		return new Page(page.getStart(), page.getTotalCount(), page.getPageSize(), resultList);
	}

	/**
	 * @param conditions
	 * @param pageNo
	 * @param rowsPerPage
	 * @return
	 * @throws Exception 查询工作流信息
	 */
	public List<?> getWorkFlowList(String conditions) throws Exception {
		return this.getWorkFlowList(conditions, 0, 20);
	}

	/**
	 * @param conditions
	 * @param pageNo
	 * @param rowsPerPage
	 * @return
	 * @throws Exception 查询工作流信息
	 */
	public List<?> getWorkFlowList(String conditions, int pageNo, int rowsPerPage) throws Exception {
		String statement = "Select DISTINCT a.RegistNo," + "a.PolicyNo, " + "a.ReceiverName, " + "a.RiskCode, " + "a.ReportDate, " + "a.LicenseNo, "
				+ "b.FlowID,b.HandlerName From prpLregist a Right JOIN (Select * from swflog where nodetype='regis') b ON a.RegistNo = b.BusinessNo where" + conditions;
		String countSql = "select count(*) from " + statement;
		long count = 0;
		count = HibernateUtils.getCountbyCountSql(getSession(), countSql);
		List<PrpLregist> collection = new ArrayList<PrpLregist>();
		if (count == 0) {
			return collection;
		}
		List<?> resultSet = HibernateUtils.findbySql(getSession(), statement, pageNo, rowsPerPage);
		PrpLregist prpLregist = null;
		String[] result = null;
		for (int i = 0; i < resultSet.size(); i++) {
			prpLregist = new PrpLregist();
			result = (String[]) resultSet.get(i);
			prpLregist.setRegistNo(result[0]);
			prpLregist.setPolicyNo(result[1]);
			prpLregist.setReceiverName(result[2]);
			prpLregist.setRiskCode(result[3]);
			prpLregist.setReportDate(new DateTime(result[4]));
			prpLregist.setLicenseNo(result[5]);
			prpLregist.setFlowID(result[6]);
			prpLregist.setHandlerName(result[7]);
			collection.add(prpLregist);
		}
		return collection;
	}

	/**
	 * @param registDto
	 * @throws Exception 保存报案信息不带工作流
	 */
	public void save(RegistDto registDto, WorkFlowDto workFlowDto) throws Exception {
		// 创建数据库管理对象
		this.save(registDto);
		if (workFlowDto != null) {
			this.workFlowService.deal(workFlowDto);
		}
	}
	
	/**
	 * mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案
	 * @param registDto
	 * @throws Exception 保存报案信息不带工作流
	 */
	public void save4Ws(RegistDto registDto, WorkFlowDto workFlowDto,HttpSession session) throws Exception {
		// 创建数据库管理对象
		this.save(registDto);
		if (workFlowDto != null) {
			this.workFlowService.deal4Ws(workFlowDto,session);
		}
	}
	
	/***
	 * mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案
	 * @param businessNo
	 * @param riskType
	 * @return
	 * @throws Exception
	 */
	public void sendMail(String[] sendTo,String registNo,ClaimExternalSourceVo vo) throws Exception{
		try {
			EmailDto email = new EmailDto();
			email.setFrom("newims@ctbcins.com");
			email.setTo(sendTo);
			email.setSenderName("多元理賠信件通知");
			email.setSubject("Web RTC備案資料與承保資料有異");
			StringBuffer sb = new StringBuffer();
			sb.append("<table>");
			sb.append("<tr>");
			sb.append("<td>說明：茲通知受理備案資料與部分承保資料有異，請通知辦理更正。</td>");
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("<td>"+vo.getMemo()+"</td>");
			sb.append("</tr>");
			sb.append("</table>");
			email.setText(sb.toString());

			this.getEmailService().mailSendForRtc(registNo,email);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
		}
	}
	
	/***
	 * mantis：CLM0282、CLM9009 ，處理人員： DP0713 ，需求單編號：多元理賠備案_非車全險種
	 * @param businessNo
	 * @param riskType
	 * @return
	 * @throws Exception
	 */
	public void sendMail4Risk(String[] sendTo,String registNo,ClaimExternalRiskSourceVo vo) throws Exception{
		try {
			EmailDto email = new EmailDto();
			email.setFrom("newims@ctbcins.com");
			email.setTo(sendTo);
			email.setSenderName("多元理賠信件通知");
			email.setSubject("Web RTC備案資料與承保資料有異");
			StringBuffer sb = new StringBuffer();
			sb.append("<table>");
			sb.append("<tr>");
			sb.append("<td>說明：茲通知受理備案資料與部分承保資料有異，請通知辦理更正。</td>");
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("<td>"+vo.getMemo()+"</td>");
			sb.append("</tr>");
			sb.append("</table>");
			email.setText(sb.toString());

			this.getEmailService().mailSendForRtc(registNo,email);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
		}
	}

	/**
	 * 创建jbpm的工作流
	 * @param registDto
	 * @param workFlowDto
	 * @throws Exception
	 */
	@StartProcess(processId = "claim_05", businessBeanOffset = 0, businessIdAttributeName = "prpLregist.registNo")
	@ProcessTask(processId = "claim_05", userId = "regis", businessBeanOffset = 0, businessIdAttributeName = "prpLregist.registNo")
	public void saveBpm(RegistDto registDto, WorkFlowDto workFlowDto) throws Exception {
		save(registDto, workFlowDto);
	}

	/**
	 * 创建jbpm的工作流 意见险流程
	 * @param registDto
	 * @param workFlowDto
	 * @throws Exception
	 */
	@StartProcess(processId = "claim_E", businessBeanOffset = 0, businessIdAttributeName = "prpLregist.registNo")
	@ProcessTask(userId = "regis", businessBeanOffset = 0, businessIdAttributeName = "prpLregist.registNo")
	public void saveBpm_E(RegistDto registDto, WorkFlowDto workFlowDto) throws Exception {
		save(registDto, workFlowDto);
	}

	/**
	 * 创建jbpm的工作流 财产险流程
	 * @param registDto
	 * @param workFlowDto
	 * @throws Exception
	 */
	@StartProcess(processId = "claim_Q", businessBeanOffset = 0, businessIdAttributeName = "prpLregist.registNo")
	@ProcessTask(userId = "regis", businessBeanOffset = 0, businessIdAttributeName = "prpLregist.registNo")
	public void saveBpm_Q(RegistDto registDto, WorkFlowDto workFlowDto) throws Exception {
		save(registDto, workFlowDto);
	}

	/**
	 * 报案删除
	 * @param fcoRegistNoticeNo
	 * @throws SQLException
	 * @throws Exception
	 */
	public void deleteSubInfo(RegistDto registDto) throws SQLException, Exception {
		String registNo = registDto.getPrpLregist().getRegistNo();
		prpLregistTextService.deleteByRegistNo(registNo);
		prpLdriverService.deleteByRegistNo(registNo);
		prpLthirdPartyService.deleteByRegistNo(registNo);
		prpLthirdCarLossService.deleteByRegistNo(registNo);
		prpLregistService.delete(registNo);
		prpLrelatePersonService.deleteByRegistNo(registNo);
		prpLregistrpolicyService.deleteByRegistNo(registNo);
		prpLacciPersonService.deleteByRegistNo(registNo, null);

		String strRiskType = this.codeService.translateRiskCodetoRiskType(registDto.getPrpLregist().getRiskCode());
		if(!"Q".equals(strRiskType)){
			//火险立案的时候没有添加财产和人伤信息，不删除
			// 删除人伤跟踪信息
			prpLpersonTraceService.deleteByRegistNo(registNo);
			prpLthirdPropService.deleteByRegistNo(registNo);
		}
		// 暂存不保存调度信息
		if ("4".equals(registDto.getPrpLclaimStatus().getStatus())) {
			if (registDto.getPrpLscheduleMainWF() != null) {
				HibernateUtils.executeSql(super.getSession(), "delete from PrpLscheduleMainWF where registNo='" + registNo + "'");
			}
			// if (registDto.getPrpLscheduleItemList() != null) {
			// prpLscheduleItemService.deleteByRegistNo(StringUtils.rightTrim(registNo));
			// }
		}
	}

	// 二期
	/**
	 * 插入一条数据
	 * @param prpLcallCenterDto prpLcallCenterDto
	 * @throws Exception
	 */
	public void saveCallCenter(RegistDto registDto, List<PrpLregistExt> prpLregistExtList, PrpLcallCenter prpLcallCenter) throws Exception {
		/*
		 * 二期添加修改轨迹表信息begin
		 */
		if (null != registDto) {
			/*
			 * 添加轨迹信息
			 */
			insertLog(registDto);
			/*
			 * 重新插入报案信息
			 */
			this.insertRemark(registDto);
		}

		if (null != prpLregistExtList) {
			prpLregistExtService.save(prpLregistExtList);
		}
		/*
		 * end
		 */
		if (null != prpLcallCenter) {
			prpLcallCenterService.save(prpLcallCenter);
		}

	}

	/**
	 * 修改报案信息保存方法
	 * @param registDto 报案对象
	 * @throws SQLException
	 * @throws Exception
	 * @return 无
	 */
	public void insertRemark(RegistDto registDto) throws SQLException, Exception {
		String registNo = "";
		registNo = registDto.getPrpLregist().getRegistNo();
		// 生成修改轨迹
		PrpLregist prpLregist = prpLregistService.findPrpLregist(registNo);
		String alterLocus = this.generateAlterLocus(prpLregist, registDto);
		registDto.getPrpLregist().setAlterLocus(alterLocus);
		this.save(registDto);
		
		//车险回写更新立案的出险时间、出险原因
		PrpLregist tempPrpLregist = registDto.getPrpLregist();
		List<PrpLclaim> claimList = this.prpLclaimService.findByRegistNo(tempPrpLregist.getRegistNo());
		if(!CommonUtils.isEmpty(claimList)){
			for(PrpLclaim prpLclaim : claimList){
				prpLclaim.setDamageStartDate(tempPrpLregist.getDamageStartDate());
				prpLclaim.setDamageStartHour(tempPrpLregist.getDamageStartHour());
				prpLclaim.setDamageEndDate(tempPrpLregist.getDamageEndDate());
				prpLclaim.setDamageEndHour(tempPrpLregist.getDamageEndHour());
				if(ConstantCodes.RISKCODE_DAZ.equals(prpLclaim.getRiskCode())){
					prpLclaim.setDamageCode(DataUtils.dbNullToEmpty(tempPrpLregist.getDamageCodeBZ()).trim());
					prpLclaim.setDamageName(tempPrpLregist.getDamageNameBZ());
				}else{
					prpLclaim.setDamageCode(DataUtils.dbNullToEmpty(tempPrpLregist.getDamageCode()).trim());
					prpLclaim.setDamageName(tempPrpLregist.getDamageName());
				}
			}
			this.prpLclaimService.saveOrUpdate(claimList);
		}
	}

	/*
	 * 二期添加修改轨迹信息begin
	 */
	/*
	 * 添加报案修改轨迹信息方法。 @param RegistDto registDto DBManager dbManager @return void
	 * @throws Exception
	 */
	public void insertLog(RegistDto registDto) throws SQLException, Exception {
		if (registDto.getPrpLregist() == null) {
			throw new Exception();
		}
		String registNo = registDto.getPrpLregist().getRegistNo();
		String logId = this.getInstanceID();
		prpLregistLogService.save(logId, registNo);
		// 表没有生成，，以後在处理
		// new DBPrpLregistLog(dbManager).insert(LogID, registNo,prpLregistLog);
		// new DBPrpLthirdpartyLog(dbManager).insertAll(LogID, registNo);
		// new DBPrpLdriverLog(dbManager).insertAll(LogID, registNo);
		// new DBPrpLregistTextLog(dbManager).insertAll(LogID, registNo);
		// new DBPrpLextLog(dbManager).insert(LogID, registNo);
		// new DBPrpLthirdCarLossLog(dbManager).insertAll(LogID, registNo);
		// new DBPrpLthirdPropLog(dbManager).insertAll(LogID, registNo);
		// new DBPrpLpersonTraceLog(dbManager).insertAll(LogID, registNo);
		// new DBPrpLrelatePersonLog(dbManager).insert(LogID, registNo);
	}

	/*
	 * 生成轨迹ID方法。 @return String LogID
	 */
	public String getInstanceID() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
		StringBuffer ID = new StringBuffer("L" + String.valueOf(sdf.format(new Date())));
		ID = ID.append((int) (new Random().nextFloat() * 1000));
		return ID.toString();
	}

	@Override
	public CaseRelateNodeDto relateNode(String registNo) throws SQLException, Exception {
		String claimNo = codeService.translateBusinessCode(registNo, true);
		CaseRelateNodeDto caseRelateNodeDto = new CaseRelateNodeDto();
		caseRelateNodeDto.setPrpLregist(this.prpLregistService.findPrpLregist(registNo));
		// caseRelateNodeDto.setPrpCmainDto((new
		// BLPrpCmainFacade()).findByPrimaryKey(caseRelateNodeDto.getPrpLregist().getPolicyNo()));
		caseRelateNodeDto.setPrpCmain(prpCmainService.findByPrimaryKey(caseRelateNodeDto.getPrpLregist().getPolicyNo()));

		caseRelateNodeDto.setPrpLcheck(this.prpLcheckService.findPrpLcheck(new PrpLcheckId(registNo, 1)));
		caseRelateNodeDto.setPrpLverifyLoss(this.prpLverifyLossService.findPrpLverifyLoss(registNo, "1", "certa"));
		caseRelateNodeDto.setPrpLclaim(this.prpLclaimService.findPrpLclaim(claimNo));
		return caseRelateNodeDto;
	}

	/***************************************************************************
	 * 根据主键获取PrpLRegist对象
	 * @param registNo
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	@Override
	public PrpLregist findByPrimaryKeyForPrpLRegist(String registNo) throws SQLException, Exception {
		PrpLregist prpLregist = prpLregistService.findPrpLregist(registNo);
		return prpLregist;
	}

	/***************************************************************************
	 * 更新报案主对象PrpLregist
	 * @param prpLregist
	 * @throws SQLException
	 * @throws Exception
	 */
	@Override
	public void updatePrpLRegist(PrpLregist prpLregist) throws SQLException, Exception {
		prpLregistService.update(prpLregist);
	}

	/**
	 * 保存报案注销信息带工作流处理的过程
	 * @param registDto：自定义报案对象
	 * @param workFlowDto：工作流对象
	 * @throws SQLException
	 * @throws Exception
	 */
	public void saveRegistCancel(RegistDto registDto, WorkFlowDto workFlowDto) throws SQLException, Exception {
		// 创建数据库管理对象
		this.insertRegistCancel(registDto);
		if (workFlowDto != null) {
			this.getWorkFlowService().deal(workFlowDto);
		}
	}

	/**
	 * 保存报案注销信息带工作流处理的过程
	 * @param registDto：自定义报案对象
	 * @param workFlowDto：工作流对象
	 * @throws SQLException
	 * @throws Exception
	 */
	@ProcessTask(processId = "claim_05", userId = "regist_cancel", businessBeanOffset = 0, businessIdAttributeName = "prpLregist.registNo")
	@TaskParams(taskParams = { @TaskParam(key = "nodeType", paramValueBeanOffset = 2) })
	public void saveBpmRegistCancel(RegistDto registDto, WorkFlowDto workFlowDto, String nodeType) throws SQLException, Exception {
		this.saveRegistCancel(registDto, workFlowDto);
	}

	@Override
	public List<PrpLregist> findSamePolicyRegist(String policyNo) throws Exception {
		String sql = " Select registNo,DamageStartDate From Prplregist where  prplregist.policyNo ='" + policyNo + "' order by registNo";
		List<?> resultSet = super.getSession().createSQLQuery(sql).list();
		List<PrpLregist> list = new ArrayList<PrpLregist>();
		if (resultSet != null && !resultSet.isEmpty()) {
			PrpLregist prpLregist = null;
			Object[] object = null;
			for (Iterator<?> it = resultSet.iterator(); it.hasNext(); list.add(prpLregist)) {
				object = (Object[]) it.next();// 每行记录不在是一个对象 而是一个数组
				prpLregist = new PrpLregist();
				prpLregist.setRegistNo((String) object[0]);
				prpLregist.setDamageStartDate(new Date(((Timestamp) object[1]).getTime()));
			}
		}
		return list;
	}

	@Override
	public List<PrpLregist> findRegistsByPolicyno(String policyno) throws SQLException, Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("policyNo", policyno);
		List<PrpLregist> prpLregistList = prpLregistService.findPrpLregist(queryRule);
		return prpLregistList;
	}

	@Override
	public CompensateFeeDto getCompensateFeeByRegistNo(String registNo) throws Exception {
		CompensateFeeDto compensateFeeDto = null;
		// 根据报案号获得相应的立案号
		String claimNo = this.codeService.translateBusinessCode(registNo, true);
		compensateFeeDto = this.prpLcompensateService.findCompensateFeeByClaimNo(claimNo);
		return compensateFeeDto;
	}

	public void updateDamageDate(String registNo, String damageDate, UserDto user) throws Exception {
		PrpLregist prpLregist = prpLregistService.findPrpLregist(registNo);
		String logId = this.getInstanceID();
		prpLregistLogService.save(logId, registNo);
		String alterLocus = "";
		DateTime dateTime = new DateTime(prpLregist.getDamageStartDate());
		if (!damageDate.equals(dateTime.toString())) {
			alterLocus += "出險時間：從“" + dateTime.toString() + "”修改为“" + damageDate + "”； ";
		}
		alterLocus = new DateTime(DateTime.current()) + " 修改人：" + user.getUserName() + " ；修改內容：" + alterLocus;
		prpLregist.setDamageStartDate(CommonUtils.toYearToDayDate(damageDate));
		prpLregist.setAlterLocus(alterLocus);
		this.update(prpLregist);
		PrpLclaim prpLclaim = null;
		List<PrpLclaim> prpLclaimList = prpLclaimService.findByRegistNo(registNo);
		if (!CommonUtils.isEmpty(prpLclaimList)) {
			for (int i = 0; i < prpLclaimList.size(); i++) {
				prpLclaim = prpLclaimList.get(i);
				prpLclaim.setDamageStartDate(new DateTime(damageDate));
				prpLclaimService.update(prpLclaim);
			}
		}

	}

	public PrpLregistService getPrpLregistService() {
		return prpLregistService;
	}

	public void setPrpLregistService(PrpLregistService prpLregistService) {
		this.prpLregistService = prpLregistService;
	}

	public PrplregistrpolicyService getPrpLregistrpolicyService() {
		return prpLregistrpolicyService;
	}

	public void setPrpLregistrpolicyService(PrplregistrpolicyService prpLregistrpolicyService) {
		this.prpLregistrpolicyService = prpLregistrpolicyService;
	}

	public PrpLthirdPartyService getPrpLthirdPartyService() {
		return prpLthirdPartyService;
	}

	public void setPrpLthirdPartyService(PrpLthirdPartyService prpLthirdPartyService) {
		this.prpLthirdPartyService = prpLthirdPartyService;
	}

	public PrpLdriverService getPrpLdriverService() {
		return prpLdriverService;
	}

	public void setPrpLdriverService(PrpLdriverService prpLdriverService) {
		this.prpLdriverService = prpLdriverService;
	}

	public PrpLregistTextService getPrpLregistTextService() {
		return prpLregistTextService;
	}

	public void setPrpLregistTextService(PrpLregistTextService prpLregistTextService) {
		this.prpLregistTextService = prpLregistTextService;
	}

	public PrpLacciPersonService getPrpLacciPersonService() {
		return prpLacciPersonService;
	}

	public void setPrpLacciPersonService(PrpLacciPersonService prpLacciPersonService) {
		this.prpLacciPersonService = prpLacciPersonService;
	}

	public PrpLextService getPrpLextService() {
		return prpLextService;
	}

	public void setPrpLextService(PrpLextService prpLextService) {
		this.prpLextService = prpLextService;
	}

	public PrpLthirdCarLossService getPrpLthirdCarLossService() {
		return prpLthirdCarLossService;
	}

	public void setPrpLthirdCarLossService(PrpLthirdCarLossService prpLthirdCarLossService) {
		this.prpLthirdCarLossService = prpLthirdCarLossService;
	}

	public PrpLthirdPropService getPrpLthirdPropService() {
		return prpLthirdPropService;
	}

	public void setPrpLthirdPropService(PrpLthirdPropService prpLthirdPropService) {
		this.prpLthirdPropService = prpLthirdPropService;
	}

	public PrpLpersonTraceService getPrpLpersonTraceService() {
		return prpLpersonTraceService;
	}

	public void setPrpLpersonTraceService(PrpLpersonTraceService prpLpersonTraceService) {
		this.prpLpersonTraceService = prpLpersonTraceService;
	}

	public PrpLregistExtService getPrpLregistExtService() {
		return prpLregistExtService;
	}

	public void setPrpLregistExtService(PrpLregistExtService prpLregistExtService) {
		this.prpLregistExtService = prpLregistExtService;
	}

	public PrpLscheduleMainWFService getPrpLscheduleMainWFService() {
		return prpLscheduleMainWFService;
	}

	public void setPrpLscheduleMainWFService(PrpLscheduleMainWFService prpLscheduleMainWFService) {
		this.prpLscheduleMainWFService = prpLscheduleMainWFService;
	}

	public PrpLscheduleItemService getPrpLscheduleItemService() {
		return prpLscheduleItemService;
	}

	public void setPrpLscheduleItemService(PrpLscheduleItemService prpLscheduleItemService) {
		this.prpLscheduleItemService = prpLscheduleItemService;
	}

	public PrpLrelatePersonService getPrpLrelatePersonService() {
		return prpLrelatePersonService;
	}

	public void setPrpLrelatePersonService(PrpLrelatePersonService prpLrelatePersonService) {
		this.prpLrelatePersonService = prpLrelatePersonService;
	}

	public PrpLcallCenterService getPrpLcallCenterService() {
		return prpLcallCenterService;
	}

	public void setPrpLcallCenterService(PrpLcallCenterService prpLcallCenterService) {
		this.prpLcallCenterService = prpLcallCenterService;
	}

	public PrpLclaimStatusService getPrpLclaimStatusService() {
		return prpLclaimStatusService;
	}

	public void setPrpLclaimStatusService(PrpLclaimStatusService prpLclaimStatusService) {
		this.prpLclaimStatusService = prpLclaimStatusService;
	}

	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}

	public PrpLregistLogService getPrpLregistLogService() {
		return prpLregistLogService;
	}

	public void setPrpLregistLogService(PrpLregistLogService prpLregistLogService) {
		this.prpLregistLogService = prpLregistLogService;
	}

	public PrpLcompensateService getPrpLcompensateService() {
		return prpLcompensateService;
	}

	public void setPrpLcompensateService(PrpLcompensateService prpLcompensateService) {
		this.prpLcompensateService = prpLcompensateService;
	}

	public PrpLverifyLossService getPrpLverifyLossService() {
		return prpLverifyLossService;
	}

	public void setPrpLverifyLossService(PrpLverifyLossService prpLverifyLossService) {
		this.prpLverifyLossService = prpLverifyLossService;
	}

	public PrpLcheckService getPrpLcheckService() {
		return prpLcheckService;
	}

	public void setPrpLcheckService(PrpLcheckService prpLcheckService) {
		this.prpLcheckService = prpLcheckService;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	public PrpLacciCheckService getPrpLacciCheckService() {
		return prpLacciCheckService;
	}

	public void setPrpLacciCheckService(PrpLacciCheckService prpLacciCheckService) {
		this.prpLacciCheckService = prpLacciCheckService;
	}

	public PrpLacciCheckTextService getPrpLacciCheckTextService() {
		return prpLacciCheckTextService;
	}

	public void setPrpLacciCheckTextService(PrpLacciCheckTextService prpLacciCheckTextService) {
		this.prpLacciCheckTextService = prpLacciCheckTextService;
	}

	//mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案 START
	public EmailService getEmailService() {
		return emailService;
	}

	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}
	//mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案 END
}
