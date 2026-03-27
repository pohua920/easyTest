/*
 * @(#)ReplevyServiceSpringImpl.java	Mar 11, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.replevy.service.spring;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;
import ins.framework.utils.DataUtils;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.sinosoft.claim.audit.vo.ReplevyRuleCondition;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.compensate.service.facade.CompensateService;
import com.sinosoft.claim.compensate.vo.CompensateDto;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.payment.util.PayMentServiceManager;
import com.sinosoft.claim.replevy.service.facade.ReplevyService;
import com.sinosoft.claim.replevy.util.ReplevyViewHelper;
import com.sinosoft.claim.replevy.vo.ReplevyUndwrtDto;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLctext;
import com.sinosoft.claim.schema.model.PrpLintfProcess;
import com.sinosoft.claim.schema.model.PrpLloss;
import com.sinosoft.claim.schema.model.PrpLreplevy;
import com.sinosoft.claim.schema.model.PrpLreplevyId;
import com.sinosoft.claim.schema.model.Prplreplevyhistory;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.model.SwfNotion;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.claim.schema.service.facade.PrpLctextService;
import com.sinosoft.claim.schema.service.facade.PrpLintfProcessService;
import com.sinosoft.claim.schema.service.facade.PrpLlossService;
import com.sinosoft.claim.schema.service.facade.PrplreplevyhistoryService;
import com.sinosoft.claim.schema.service.facade.SwfNotionService;
import com.sinosoft.claim.schema.service.facade.UtiUserGradeService;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.vo.JbpmDto;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;
import com.sinosoft.one.bpm.util.JbpmAPIUtil;
import com.sinosoft.one.rule.service.facade.DroolsRuleService;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description 
 */
public class ReplevyServiceSpringImpl extends GenericDaoHibernate<PrpLreplevy, PrpLreplevyId> implements ReplevyService {
	/**系统交互处理信息接口*/
	private PrpLintfProcessService prpLintfProcessService;
	/**系统交互处理信息接口*/
	private CompensateService compensateService;
	/**追偿数据收集*/
	private ReplevyViewHelper replevyViewHelper;
	
	private PrpLcompensateService prpLcompensateService;
	
	private PrpLclaimService prpLclaimService;

	private PrplreplevyhistoryService prpLreplevyhistoryService;

	private CodeService codeService;

	private PrpLctextService prpLctextService;
	
	private WorkFlowService workFlowService;
	
	private SwfNotionService swfNotionService;
	
	private PrpLlossService prpLlossService;
	
	private DroolsRuleService droolsRuleService;
	
	private UtiUserGradeService utiUserGradeService;
	/**
	 * 計算書審核
	 * @param PrpLcompensate prpLcompensate 待審核計算書
	 * @param infoMap：传入参数
	 * @throws SQLException
	 * @throws Exception
	 */
	private void undwrt(PrpLcompensate prpLcompensate,Map<String,String> infoMap) throws Exception {
		String compensateNo = prpLcompensate.getCompensateNo();
		if(DataUtils.emptyToNull(compensateNo)!=null){
			if(compensateNo.endsWith("00")){//追償登錄修改的審核
				prpLcompensate.setUnderWriteFlag("0");
				prpLcompensateService.saveOrUpdate(prpLcompensate);
			}else{
				//审核通过 - 更新理算书状态
				UserDto user = (UserDto) ActionContext.getContext().getSession().get("user");
				prpLcompensate.setUnderWriteEndDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_SECOND));
				prpLcompensate.setUnderWriteCode(user.getUserCode());
				prpLcompensate.setUnderWriteName(user.getUserName());
				prpLcompensate.setUnderWriteFlag("1");
				prpLcompensate.setUnderWriteDeptCode(user.getComCode());
				prpLcompensateService.saveOrUpdate(prpLcompensate);
				//记录追偿历史信息
				PrpLclaim prpLclaim = this.prpLclaimService.findPrpLclaim(prpLcompensate.getClaimNo());
				Prplreplevyhistory prplreplevyhistory = prpLreplevyhistoryService.findPrplreplevyhistory(compensateNo);
				if (prplreplevyhistory == null) {
					prplreplevyhistory = new Prplreplevyhistory();
				}
				prplreplevyhistory.setBusinessNo(compensateNo);
				prplreplevyhistory.setRegistNo(prpLclaim.getRegistNo());
				prplreplevyhistory.setClaimNo(prpLcompensate.getClaimNo());
				prplreplevyhistory.setPolicyNo(prpLcompensate.getPolicyNo());
				prplreplevyhistory.setRiskCode(prpLcompensate.getRiskCode());
				prplreplevyhistory.setReplevytimes(prpLcompensate.getTimes());
				prplreplevyhistory.setReplevytype(prpLcompensate.getIndemnityDuty());
				prplreplevyhistory.setCurrency(prpLcompensate.getCurrency());
				prplreplevyhistory.setReplevysumpaid(-prpLcompensate.getSumThisPaid());//计算书表存的是负值，此处要正
				prplreplevyhistory.setReplevyfee(-prpLcompensate.getSumNoDutyFee());
				prplreplevyhistory.setOperatorCode(user.getUserCode());
				prplreplevyhistory.setOperatorname(user.getUserName());
				prplreplevyhistory.setReplevytime(prpLcompensate.getStatisticsYM());
				prplreplevyhistory.setReplevyendtime(prpLcompensate.getPreserveDate());
				prplreplevyhistory.setComCode(prpLclaim.getComCode());
				prplreplevyhistory.setComname(this.codeService.translateComCode(prpLclaim.getComCode(), true));
				prplreplevyhistory.setReplevyreason(prpLcompensate.getDutyDescription());
				StringBuffer context = new StringBuffer();
				String sql = " compensateNo = '"+compensateNo+"' and textType = '26' order by lineNo asc";
				List<PrpLctext> prplcTextList = prpLctextService.findPrpLctext(QueryRule.getInstance().addSql(sql));
				for(PrpLctext p : prplcTextList){
					context.append(p.getContext());
				}
				String replevytext = context.toString();
				if (replevytext.length() > 1990) {
					replevytext = replevytext.substring(0, 1990);
				}
				prplreplevyhistory.setReplevytext(replevytext);
				prpLreplevyhistoryService.saveOrUpdate(prplreplevyhistory);
				//回写登录计算书的状态
				PrpLcompensate replevyPrpLcompensate = this.prpLcompensateService.getReplevyPrpLcompensate(prpLcompensate.getClaimNo());
				if(replevyPrpLcompensate!=null){//追償審核通過時，回寫登錄計算書的次數和狀態
					if (!"7".equals(prpLcompensate.getPaySituation())) {// 給付追償情況為費用時，不計追償
						replevyPrpLcompensate.setReplevyTimes(replevyPrpLcompensate.getTimes() + 1);
						replevyPrpLcompensate.setTimes(prpLcompensate.getTimes());
						//第一次追償時，若給付類型為分次，則
						if("4".equals(prpLcompensate.getPaySituation())){
							if(prpLcompensate.getTotalTimes() >= replevyPrpLcompensate.getTotalTimes()
									&& prpLcompensate.getTotalTimes() > prpLcompensate.getReplevyTimes()){
								replevyPrpLcompensate.setPaySituation(prpLcompensate.getPaySituation());
								replevyPrpLcompensate.setTotalTimes(prpLcompensate.getTotalTimes());
							}
						}
					}
					replevyPrpLcompensate.setUnderWriteFlag("0");
					//mantis： CLM0106，處理人員：BK007 蘇哲，需求單編號：CLM0106.新核心-案件賠付速別預設值更改為速件
					replevyPrpLcompensate.setSpeedFlag("N");
					this.prpLcompensateService.saveOrUpdate(replevyPrpLcompensate);
				}
				//送接口
				try {
					PayMentServiceManager.getService().transData("R", compensateNo,infoMap);
				} catch (Exception e) {
					e.printStackTrace();
					throw new UserException(-98, -1149, "計算書號==" + compensateNo, "送接口表數據出錯");
				}
				// 调用收付取数方法
				PrpLintfProcess prpLinrfProcess = new PrpLintfProcess();
				try {
					DateTime thisTime = new DateTime(DateTime.current(), DateTime.YEAR_TO_MILLISECOND);
					prpLinrfProcess.setCertiType("C");
					prpLinrfProcess.setArriveDate(thisTime);
					prpLinrfProcess.setStatus("0");
					String subStr = (1000 + ((int) (Math.random() * 1000))) + "";
					prpLinrfProcess.setBusinessNo(thisTime.getTime() + "" + subStr);
					prpLinrfProcess.setCertiNo(compensateNo);
					PayMentServiceManager.getService().send("C", compensateNo);
					prpLinrfProcess.setLastOperateDate(new DateTime(DateTime.current(), DateTime.YEAR_TO_MILLISECOND));
					prpLinrfProcess.setStatus("1");
				} catch (Exception e) {
					prpLinrfProcess.setLastOperateDate(new DateTime(DateTime.current(), DateTime.YEAR_TO_MILLISECOND));
					String exceptionContext = "";
					if (e instanceof com.sinosoft.sysframework.exceptionlog.UserException) {
						com.sinosoft.sysframework.exceptionlog.UserException sysUserException = (com.sinosoft.sysframework.exceptionlog.UserException) e;
						exceptionContext = sysUserException.getErrorMessage();
					} else if (e instanceof com.sinosoft.utility.error.UserException) {
						com.sinosoft.utility.error.UserException errorUserException = (com.sinosoft.utility.error.UserException) e;
						exceptionContext = errorUserException.getErrorMessage();
					} else {
						exceptionContext = e.getLocalizedMessage();
					}
					prpLinrfProcess.setErrorMessage(exceptionContext);
					e.printStackTrace();
					throw e;
				} finally {
					this.prpLintfProcessService.logForReplevy(prpLinrfProcess);//记录交互讯息
				}
			}
		}

	}

	/**
	 * 保存追偿信息
	 * @param compensateDto
	 * @param workFlowDto
	 * @throws Exception
	 */
	public void saveBpm(CompensateDto compensateDto,WorkFlowDto workFlowDto)throws Exception{
		try {
			// 解決 a different object with the same identifier value was already associated with the session
			// 這麼做需要避免本方法調用時處於其他事務中才可以，否則會導致事務不一致。
			super.getSession().clear();
			//mantis： CLM0106，處理人員：BK007 蘇哲，需求單編號：CLM0106.新核心-案件賠付速別預設值更改為速件
			compensateDto.getPrpLcompensate().setSpeedFlag("N");
			compensateService.save(compensateDto);
			if ((workFlowDto.getCreate()) || (workFlowDto.getUpdate()) || (workFlowDto.getSubmit()) || (workFlowDto.getClose())) {
		        System.out.println("-----replevy----start---");
				this.getWorkFlowService().dealAudit(workFlowDto);
				this.saveSwfNotion(workFlowDto.getCurrSwfLog(), "1");//提交審核
				System.out.println("-----replevy----end---");
			}
		} catch (Exception e) {
			if (workFlowDto.isNewWorkFlow()) {// 处理工作流引擎renw
				JbpmDto jbpmDto = workFlowDto.getJbpmDto();
				if (jbpmDto != null && jbpmDto.getBpmSuccess()) {
					// jbpm事务回滚
					JbpmAPIUtil.rollbackTask(jbpmDto.getProcessId(), jbpmDto.getBusinessId(), jbpmDto.getActorId(), jbpmDto.getTaskId());
					jbpmDto.setBpmSuccess(false);
				}
			}
			throw e;
		}
	}
	/**
	 * 审核通过 (舊數據的審核處理)
	 * @param nodeType
	 * @param compensateNo
	 * @param workFlowDto
	 * @throws Exception
	 */
	public void saveUndwrtBpm(String nodeType,String compensateNo,WorkFlowDto workFlowDto)throws Exception{
		if("end".equals(nodeType)){
			this.saveUndwrtPass(compensateNo, workFlowDto);
		}else if("replevy".equals(nodeType)){//舊數據的駁回修改再次
			this.withdrawalViewToDto(compensateNo);
		}
	}
	
	/***
	 * 查询待审核追偿数据
	 */
	public Page findUndwrtByConditions(String condition, int pageNo, int pageSize) {
		String statements = "select c.compensateNo,c.claimNo,c.policyNo,c.operatorCode,c.inputDate,"
				+ "s.flowID,s.logNo,s.nodeName,s.handlerCode,s.handlerName,s.flowInTime from PrpLcompensate c left join SwfLog s on c.compensateNo = s.businessNo where " + condition;
		Page page = HibernateUtils.findPagebySql(getSession(), statements, pageNo, pageSize);
		List<?> result = page.getResult();
		List<ReplevyUndwrtDto> resultList = new ArrayList<ReplevyUndwrtDto>();
		if (result != null && !result.isEmpty()) {
			ReplevyUndwrtDto replevyUndwrtDto = null;
			PrpLcompensate prpLcompensate = null;
			SwfLog swfLog = null;
			for (Iterator<?> it = result.iterator(); it.hasNext();) {
				Object[] obj = (Object[]) it.next();
				prpLcompensate = new PrpLcompensate();
				prpLcompensate.setCompensateNo(String.valueOf(obj[0]));
				prpLcompensate.setClaimNo(String.valueOf(obj[1]));
				prpLcompensate.setPolicyNo(String.valueOf(obj[2]));
				prpLcompensate.setOperatorCode(String.valueOf(obj[3]));
				prpLcompensate.setInputDate(new Date(((Timestamp)obj[4]).getTime()));
				if(obj[5]!=null){//flowID 
					swfLog = new SwfLog(String.valueOf(obj[5]),((BigDecimal)obj[6]).intValue());
					swfLog.setNodeName(String.valueOf(obj[7]));
					swfLog.setHandlerCode(String.valueOf(obj[8]));
					swfLog.setHandlerName(String.valueOf(obj[9]));
					swfLog.setFlowInTime(String.valueOf(obj[10]));
				}
				replevyUndwrtDto = new ReplevyUndwrtDto(prpLcompensate, swfLog);
				resultList.add(replevyUndwrtDto);
			}
		}
		return new Page(page.getStart(), page.getTotalCount(), page.getPageSize(), resultList);
	}
	
	
	
	/***
	 * 追偿审核驳回修改
	 */
	@Override
	public void saveUndwrtBack(String compensateNo, WorkFlowDto workFlowDto) throws Exception {
		try {
			this.withdrawalViewToDto(compensateNo);
			if ((workFlowDto.getCreate()) || (workFlowDto.getUpdate()) || (workFlowDto.getSubmit()) || (workFlowDto.getClose())) {
				workFlowDto.getJbpmDto().getParamsMap().put("back", true);
				this.getWorkFlowService().dealAudit(workFlowDto);
			}
		} catch (Exception e) {
			if (workFlowDto.isNewWorkFlow()) {// 处理工作流引擎renw
				JbpmDto jbpmDto = workFlowDto.getJbpmDto();
				if (jbpmDto != null && jbpmDto.getBpmSuccess()) {
					// jbpm事务回滚
					JbpmAPIUtil.rollbackTask(jbpmDto.getProcessId(), jbpmDto.getBusinessId(), jbpmDto.getActorId(), jbpmDto.getTaskId());
					jbpmDto.setBpmSuccess(false);
				}
			}
			throw e;
		}
	}
	/***
	 * 追偿审核驳回修改
	 * @param compensateNo
	 * @throws Exception
	 */
	private void withdrawalViewToDto(String compensateNo) throws Exception {
		PrpLcompensate prpLcompensate = this.prpLcompensateService.findPrpLcompensate(compensateNo);
		prpLcompensate.setUnderWriteFlag("2");
		prpLcompensateService.saveOrUpdate(prpLcompensate);
	}
	/***
	 * 追償計算書審核通過
	 */
	@Override
	public void saveUndwrtPass(String compensateNo, WorkFlowDto workFlowDto) throws Exception {
		try {
			this.undwrtViewToDto(compensateNo, workFlowDto.getCurrSwfLog());
			if ((workFlowDto.getCreate()) || (workFlowDto.getUpdate()) || (workFlowDto.getSubmit()) || (workFlowDto.getClose())) {
				workFlowDto.getJbpmDto().getParamsMap().put("pass", true);
				workFlowDto.setClose(true);//審核通過，關閉工作流
				this.getWorkFlowService().dealAudit(workFlowDto);
				this.saveSwfNotion(workFlowDto.getCurrSwfLog(), "2");// 審核通過
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (workFlowDto.isNewWorkFlow()) {// 处理工作流引擎renw
				JbpmDto jbpmDto = workFlowDto.getJbpmDto();
				if (jbpmDto != null && jbpmDto.getBpmSuccess()) {
					// jbpm事务回滚
					JbpmAPIUtil.rollbackTask(jbpmDto.getProcessId(), jbpmDto.getBusinessId(), jbpmDto.getActorId(), jbpmDto.getTaskId());
					jbpmDto.setBpmSuccess(false);
				}
			}
			throw e;
		}
	}
	/***
	 * 追償計算書審核通過
	 * @param compensateNo
	 * @param currSwfLog
	 * @throws Exception
	 */
	private void undwrtViewToDto(String compensateNo,SwfLog currSwfLog) throws Exception {
		UserDto user = (UserDto) ActionContext.getContext().getSession().get("user");
		PrpLcompensate prpLcompensate = this.prpLcompensateService.findPrpLcompensate(compensateNo);
		this.checkPower(user, prpLcompensate,currSwfLog);
		Map<String,String> infoMap = new HashMap<String, String>();
		infoMap.put("comCode", user.getComCode());
		try {
			this.undwrt(prpLcompensate,infoMap);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException(-98, -1149, "計算書號==" + compensateNo, "送接口表數據出錯");
		}
	}
	/***
	 * 校驗審核權限
	 * @param user
	 * @param prpLcompensate
	 * @throws Exception 
	 */
	private void checkPower(UserDto user, PrpLcompensate prpLcompensate, SwfLog currSwfLog) throws Exception {
		// String gradeCode = "";'006'
		// 業管中心科長,'009'部門理賠科長,'010'部門經理,'011'體系主管,'012'總經理,'013'董事長
		// 查询理赔岗位
		String sql = " select distinct gradecode from UtiUserGrade where usercode='" + user.getUserCode() + "'";
		sql += " and comcode = '"+ user.getComCode() +"' and gradecode in ('006','009','010','011','012','013') order by usercode";
		// List<UtiUserGrade> list =
		// this.utiUserGradeService.findUtiUserGrade(QueryRule.getInstance().addSql(conditions));
		List<?> list = HibernateUtils.findbySql(super.getSession(), sql);
		if (list == null || list.isEmpty()) {
			throw new UserException(1, 3, "追償審核", "您無可審核的崗位！");
		} else {
			double sumLoss = 0d;// 法務預估
			// 法務預估
			List<PrpLloss> lossList = this.prpLlossService.findByConditions(" compensateNo = 'R" + prpLcompensate.getClaimNo() + "00' order by serialNo asc ");
			//mantis：CLM0144，處理人員：DP0713，需求單編號：CLM0144，新核心-追償審核流程錯誤問題確認 START
			if(lossList.size()<=0){
				lossList = this.prpLlossService.findByConditions(" compensateNo = 'R" + prpLcompensate.getClaimNo() + "01' order by serialNo asc ");
				if(lossList.size()>0){
					String kindCode = lossList.get(0).getKindCode();
					//將"01"回寫'R"+prpLcompensate.getClaimNo()+"00"的prpLloss、且  只回寫指定kindCode的部分。
					CompensateDto compensateDto = this.replevyViewHelper.autoReplevy(prpLcompensate.getClaimNo(),kindCode);
					this.compensateService.save(compensateDto);
				}
			}
			//mantis：CLM0144，處理人員：DP0713，需求單編號：CLM0144，新核心-追償審核流程錯誤問題確認 END
			for (PrpLloss p : lossList) {
				sumLoss += p.getSumLoss();
			}
			ReplevyRuleCondition condition = new ReplevyRuleCondition();
			condition.setLevel("1");// 无流程数据时，默认当前1级，兼容旧数据
			if (currSwfLog != null) {
				currSwfLog = this.getWorkFlowService().findByPrimaryKey(currSwfLog.getId().getFlowID(), currSwfLog.getId().getLogNo());
				condition.setLevel(String.valueOf(currSwfLog.getNodeNo()));
			}
			if (prpLcompensate.getCompensateNo().endsWith("00")) {// 追償協商審核
				List<PrpLloss> tempList = compensateService.getPrpLlossForReplevy(prpLcompensate.getClaimNo());
				double sumDefPay = 0d;
				for (PrpLloss p : tempList) {
					sumDefPay += p.getSumDefPay();
				}
				condition.setSumRealPay(sumLoss);// 協商規則，該值代表法務預估金額總和
				condition.setSumLoss(sumDefPay);// 協商規則，該值代表賠款金額總和
			} else if ("7".equals(prpLcompensate.getPaySituation())) {
				// 追償給付類型為‘費用’，不需要審核權限,以一万元为限，
				String conditions = " caseType = 'R' And compensateNo like 'R" + prpLcompensate.getClaimNo() + "%' and UnderWriteFlag = '1' Order By times Desc";
				QueryRule queryRule = QueryRule.getInstance().addSql(conditions);
				List<PrpLcompensate> prpLcompensateList = prpLcompensateService.findPrpLcompensate(queryRule);
				double sumNoDutyFee = prpLcompensate.getSumNoDutyFee();
				if (prpLcompensateList != null && prpLcompensateList.size() > 0) {
					for (PrpLcompensate p : prpLcompensateList) {
						sumNoDutyFee += p.getSumNoDutyFee();
					}
				}
				condition.setChargeAmount(sumNoDutyFee);
				condition.setSumRealPay(0);
				condition.setSumLoss(0);
			} else {// 一般追償審核
				double sumRealPay = prpLcompensate.getSumThisPaid();// 實際追償
				// 总期数,,本次追償為分次追償
				if (!"4".equals(prpLcompensate.getPaySituation())) {
					String conditions = " caseType = 'R' And compensateNo like 'R" + prpLcompensate.getClaimNo() + "%' and UnderWriteFlag = '1' Order By times Desc";
					QueryRule queryRule = QueryRule.getInstance().addSql(conditions);
					List<PrpLcompensate> prpLcompensateList = prpLcompensateService.findPrpLcompensate(queryRule);
					if (prpLcompensateList != null && prpLcompensateList.size() > 0) {
						for (PrpLcompensate p : prpLcompensateList) {
							sumRealPay += p.getSumThisPaid();
						}
					}
				} else {
					condition.setTotalTimes(prpLcompensate.getTotalTimes());
				}
				condition.setSumRealPay(sumRealPay);
				condition.setSumLoss(sumLoss);

			}
			for (int i = 0; i < list.size(); i++) {
				condition.getGradeCodes().add(String.valueOf(list.get(i)));
			}
			try {
				droolsRuleService.executeRules("undwrtRuleFlow", "undwrtChangeSet.xml", condition);
				if (!condition.getResult()) {
					throw new UserException(-1, -1, "權限效驗失敗", condition.getResultMessage());
				}
			} catch (Exception e) {
				e.printStackTrace();
				if (e instanceof UserException) {
					throw e;
				}
				throw new UserException(-1, -1, "權限效驗失敗", e.getMessage());
			}
		}
	}
	
	/***
	 * 存儲當前環節意見
	 * @param swfLog
	 * @param swfNotionFlag
	 * @throws Exception
	 */
	private void saveSwfNotion(SwfLog swfLog, String swfNotionFlag) throws Exception {
		if (swfLog!=null && DataUtils.emptyToNull(swfLog.getId().getFlowID())!=null) {
			SwfNotion swfNotion = new SwfNotion();
			swfNotion.getId().setFlowID(swfLog.getId().getFlowID());
			swfNotion.getId().setLogNo(swfLog.getId().getLogNo());
			swfNotion.getId().setLineNo(1);
			swfNotion.setFlag(swfNotionFlag);
			this.getSwfNotionService().save(swfNotion);
		}
	}
	
	public PrpLintfProcessService getPrpLintfProcessService() {
		return prpLintfProcessService;
	}
	public void setPrpLintfProcessService(PrpLintfProcessService prpLintfProcessService) {
		this.prpLintfProcessService = prpLintfProcessService;
	}
	public CompensateService getCompensateService() {
		return compensateService;
	}
	public void setCompensateService(CompensateService compensateService) {
		this.compensateService = compensateService;
	}
	public ReplevyViewHelper getReplevyViewHelper() {
		return replevyViewHelper;
	}
	public void setReplevyViewHelper(ReplevyViewHelper replevyViewHelper) {
		this.replevyViewHelper = replevyViewHelper;
	}
	public PrpLcompensateService getPrpLcompensateService() {
		return prpLcompensateService;
	}
	public void setPrpLcompensateService(PrpLcompensateService prpLcompensateService) {
		this.prpLcompensateService = prpLcompensateService;
	}
	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}
	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}
	public PrplreplevyhistoryService getPrpLreplevyhistoryService() {
		return prpLreplevyhistoryService;
	}
	public void setPrpLreplevyhistoryService(PrplreplevyhistoryService prpLreplevyhistoryService) {
		this.prpLreplevyhistoryService = prpLreplevyhistoryService;
	}
	public CodeService getCodeService() {
		return codeService;
	}
	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}
	public PrpLctextService getPrpLctextService() {
		return prpLctextService;
	}
	public void setPrpLctextService(PrpLctextService prpLctextService) {
		this.prpLctextService = prpLctextService;
	}

	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	public SwfNotionService getSwfNotionService() {
		return swfNotionService;
	}

	public void setSwfNotionService(SwfNotionService swfNotionService) {
		this.swfNotionService = swfNotionService;
	}

	public PrpLlossService getPrpLlossService() {
		return prpLlossService;
	}

	public void setPrpLlossService(PrpLlossService prpLlossService) {
		this.prpLlossService = prpLlossService;
	}

	public DroolsRuleService getDroolsRuleService() {
		return droolsRuleService;
	}

	public void setDroolsRuleService(DroolsRuleService droolsRuleService) {
		this.droolsRuleService = droolsRuleService;
	}

	public UtiUserGradeService getUtiUserGradeService() {
		return utiUserGradeService;
	}

	public void setUtiUserGradeService(UtiUserGradeService utiUserGradeService) {
		this.utiUserGradeService = utiUserGradeService;
	}

}
