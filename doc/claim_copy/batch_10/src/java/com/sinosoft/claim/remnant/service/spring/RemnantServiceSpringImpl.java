package com.sinosoft.claim.remnant.service.spring;

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
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.compensate.vo.CompensateDto;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.payment.util.PayMentServiceManager;
import com.sinosoft.claim.remnant.service.facade.RemnantService;
import com.sinosoft.claim.remnant.vo.RemnantDto;
import com.sinosoft.claim.replevy.vo.ReplevyUndwrtDto;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLintfProcess;
import com.sinosoft.claim.schema.model.PrpLpayObjectInfo;
import com.sinosoft.claim.schema.model.PrpLremnant;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.model.SwfNotion;
import com.sinosoft.claim.schema.service.facade.PrpLbuyerService;
import com.sinosoft.claim.schema.service.facade.PrpLchargeService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.claim.schema.service.facade.PrpLctextService;
import com.sinosoft.claim.schema.service.facade.PrpLintfProcessService;
import com.sinosoft.claim.schema.service.facade.PrpLpayObjectInfoService;
import com.sinosoft.claim.schema.service.facade.PrpLremnantService;
import com.sinosoft.claim.schema.service.facade.SwfNotionService;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.vo.JbpmDto;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;
import com.sinosoft.one.bpm.util.JbpmAPIUtil;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.exceptionlog.UserException;

public class RemnantServiceSpringImpl extends GenericDaoHibernate<RemnantDto, String> implements RemnantService {

	/** 计算书Service */
	private PrpLcompensateService prpLcompensateService;
	/** 支付对象Service */
	private PrpLpayObjectInfoService prpLpayObjectInfoService;
	/** 买受人Service */
	private PrpLbuyerService prpLbuyerService;
	/** 残余物Service */
	private PrpLremnantService prpLremnantService;
	/** 立案Service */
	private PrpLclaimService prpLclaimService;
	/** 接口交互记录Service */
	private PrpLintfProcessService prpLintfProcessService;
	/** 费用信息service */
	private PrpLchargeService prpLchargeService;
	/** 理算说明service */
	private PrpLctextService prpLctextService;
	private WorkFlowService workFlowService;
	private SwfNotionService swfNotionService;
	/**残余物大对象保存方法
	 * @param remnantDto 残余物大对象
	 * @throws Exception
	 */
	@Override
	public void save(RemnantDto remnantDto) throws Exception {
		if(remnantDto.getPrpLcompensate() != null){
			String compensateNo = remnantDto.getPrpLcompensate().getCompensateNo();
			// 首先删除原来的相关数据
			this.deleteSubInfo(compensateNo);
			prpLcompensateService.saveOrUpdate(remnantDto.getPrpLcompensate());
			prpLremnantService.saveOrUpdate(remnantDto.getPrpLremnantList());
			prpLpayObjectInfoService.saveOrUpdate(remnantDto.getPrpLpayObjectInfoList());
			prpLbuyerService.saveOrUpdate(remnantDto.getPrpLbuyerList());
			prpLchargeService.save(remnantDto.getPrpLchargeList());
			prpLctextService.save(remnantDto.getPrpLctextList());
		}
	}
	/**
	 * 保存残余物
	 * @param compensateDto
	 * @param workFlowDto
	 * @throws Exception
	 */
	public void saveBpm(RemnantDto remnantDto,WorkFlowDto workFlowDto)throws Exception{
		try {
			//mantis： CLM0106，處理人員：BK007 蘇哲，需求單編號：CLM0106.新核心-案件賠付速別預設值更改為速件 
			remnantDto.getPrpLcompensate().setSpeedFlag("N");
			this.save(remnantDto);
			if ((workFlowDto.getCreate()) || (workFlowDto.getUpdate()) || (workFlowDto.getSubmit()) || (workFlowDto.getClose())) {
				this.getWorkFlowService().dealAudit(workFlowDto);
				this.saveSwfNotion(workFlowDto.getCurrSwfLog(), "1");//提交審核
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
	 * 追償計算書審核通過
	 */
	public void saveUndwrtPass(String compensateNo, WorkFlowDto workFlowDto) throws Exception {
		try {
			UserDto user = (UserDto) ActionContext.getContext().getSession().get("user");
			this.undwrt(user,compensateNo);
			if ((workFlowDto.getCreate()) || (workFlowDto.getUpdate()) || (workFlowDto.getSubmit()) || (workFlowDto.getClose())) {
				workFlowDto.getJbpmDto().getParamsMap().put("pass", true);
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
	/**根据残余物计算书号删除残余物大对象中的数据。
	 * @param compensateNo 残余物计算书号
	 * @throws SQLException
	 * @throws Exception
	 */
	@Override
	public void deleteSubInfo(String compensateNo) throws SQLException, Exception {
		prpLcompensateService.delete(compensateNo);
		prpLpayObjectInfoService.deleteByCompensateNo(compensateNo);
		prpLbuyerService.deleteByCompensateNo(compensateNo);
		prpLremnantService.deleteByCompensateNo(compensateNo);
		prpLchargeService.deleteByCompensateNo(compensateNo);
		prpLctextService.deleteByCompensateNo(compensateNo);
	}

	/**
	 * 残余物审核提交收集页面信息
	 * @param httpServletRequest
	 * @param compensateNo
	 * @throws Exception
	 */
	public void undwrt(UserDto user, String compensateNo) throws Exception {
		RemnantDto remnantDto = this.findByPrimaryKey(compensateNo);
		PrpLcompensate prpLcompensate = remnantDto.getPrpLcompensate();
		prpLcompensate.setUnderWriteEndDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_SECOND));
		prpLcompensate.setUnderWriteCode(user.getUserCode());
		prpLcompensate.setUnderWriteName(user.getUserName());
		prpLcompensate.setUnderWriteFlag("1");
		if(remnantDto.getPrpLremnantList().size()>0){
			String remnants = "1";
			for (PrpLremnant prpLremnant : remnantDto.getPrpLremnantList()) {
				if("0".equals(prpLremnant.getRemnants())){
					remnants = "0";
					break;
				}
			}
			if("1".equals(remnants)){
				prpLcompensate.setRemnants("9");
			}
		}
		prpLcompensateService.saveOrUpdate(prpLcompensate);
		Map<String, String> infoMap = new HashMap<String, String>();
		infoMap.put("comCode", user.getComCode());
		this.undwrtPayMent(compensateNo, infoMap);
	}
	/***
	 * 追偿审核驳回修改
	 */
	public void saveUndwrtBack(String compensateNo, WorkFlowDto workFlowDto) throws Exception {
		try {
			UserDto user = (UserDto) ActionContext.getContext().getSession().get("user");
			this.withdrawal(user,compensateNo);
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
	
	/**残余物审核，数据送收付
	 * @param compensateNo 残余物计算书号
	 * @param infoMap 信息集合，包含用户的comcode
	 * @throws Exception
	 */
	@Override
	public void undwrtPayMent(String compensateNo, Map<String, String> infoMap)throws Exception {
		// 调用收付取数方法
		PrpLintfProcess prpLintfProcess = new PrpLintfProcess();
		DateTime thisTime = new DateTime(DateTime.current(), DateTime.YEAR_TO_MILLISECOND);
		prpLintfProcess.setCertiType("C");
		prpLintfProcess.setArriveDate(thisTime);
		prpLintfProcess.setStatus("0");
		String subStr = (1000 + ((int) (Math.random() * 1000))) + "";
		prpLintfProcess.setBusinessNo(thisTime.getTime() + "" + subStr);
		prpLintfProcess.setCertiNo(compensateNo);
		try {
			PayMentServiceManager.getService().transData("S", compensateNo,infoMap);
			//向收付送数据
			PayMentServiceManager.getService().send("C", compensateNo);
			prpLintfProcess.setLastOperateDate(new DateTime(DateTime.current(), DateTime.YEAR_TO_MILLISECOND));
			prpLintfProcess.setStatus("1");
		} catch (Exception e) {
			e.printStackTrace();
			prpLintfProcess.setLastOperateDate(new DateTime(DateTime.current(), DateTime.YEAR_TO_MILLISECOND));
			String exceptionContext = "";
			if (e instanceof com.sinosoft.sysframework.exceptionlog.UserException) {
				com.sinosoft.sysframework.exceptionlog.UserException sysUserException = (com.sinosoft.sysframework.exceptionlog.UserException) e;
				exceptionContext = sysUserException.getErrorMessage();
			} else if (e instanceof com.sinosoft.utility.error.UserException) {
				com.sinosoft.utility.error.UserException errorUserException = (com.sinosoft.utility.error.UserException) e;
				exceptionContext = errorUserException.getErrorMessage();
			} else {
				exceptionContext = e.getStackTrace().toString().substring(0,20000);
			}
			prpLintfProcess.setErrorMessage(exceptionContext);
			throw new UserException(-98, -1149, "計算書號==" + compensateNo, "送接口表數據出錯");
		}finally{
			prpLintfProcessService.logForReplevy(prpLintfProcess);
		}
		
	}
	/**
	 * 残余物任务退回修改页面数据收集
	 * @param httpServletRequest
	 * @param compensateNo
	 * @throws Exception
	 */
	public void withdrawal(UserDto user, String compensateNo) throws Exception {
		RemnantDto remnantDto = this.findByPrimaryKey(compensateNo);
		PrpLcompensate prpLcompensate = remnantDto.getPrpLcompensate();
		prpLcompensate.setUnderWriteFlag("2");// 打回修改
		prpLcompensateService.saveOrUpdate(prpLcompensate);
	}
	
	/**根据残余物计算书号查找残余物大对象中的数据。
	 * @param compensateNo 残余物计算书号
	 * @return 残余物大对象
	 * @throws SQLException
	 * @throws Exception
	 */
	@Override
	public RemnantDto findByPrimaryKey(String compensateNo) throws SQLException, Exception {
		RemnantDto remnantDto = new RemnantDto();
		PrpLcompensate prpLcompensate = prpLcompensateService.findPrpLcompensate(compensateNo);
		remnantDto.setPrpLcompensate(prpLcompensate);
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.compensateNo", compensateNo);
		queryRule.addAscOrder("id.serialNo");
		List<PrpLpayObjectInfo> prpLpayObjectInfolist = prpLpayObjectInfoService.findPrpLpayObjectInfo(queryRule);
		remnantDto.setPrpLpayObjectInfoList(prpLpayObjectInfolist);
		remnantDto.setPrpLbuyerList(prpLbuyerService.findPrpLbuyer(queryRule));
		remnantDto.setPrpLremnantList(prpLremnantService.findPrpLremnant(queryRule));
		PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(prpLcompensate.getClaimNo());
		remnantDto.setPrpLclaim(prpLclaim);
		remnantDto.setPrpLchargeList(prpLchargeService.findPrpLcharge(queryRule));
		QueryRule queryRule1 = QueryRule.getInstance();
		queryRule1.addEqual("id.compensateNo", compensateNo);
		queryRule1.addEqual("id.textType", "1");
		queryRule1.addAscOrder("id.lineNo");
		remnantDto.setPrpLctextList(prpLctextService.findPrpLctext(queryRule1));
		return remnantDto;
	}
	/***
	 * 查询待审核追偿数据
	 */
	public Page findUndwrtByConditions(String condition, int pageNo, int pageSize) throws Exception{
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
	public PrpLcompensateService getPrpLcompensateService() {
		return prpLcompensateService;
	}

	public void setPrpLcompensateService(PrpLcompensateService prpLcompensateService) {
		this.prpLcompensateService = prpLcompensateService;
	}

	public PrpLbuyerService getPrpLbuyerService() {
		return prpLbuyerService;
	}

	public void setPrpLbuyerService(PrpLbuyerService prpLbuyerService) {
		this.prpLbuyerService = prpLbuyerService;
	}

	public PrpLremnantService getPrpLremnantService() {
		return prpLremnantService;
	}

	public void setPrpLremnantService(PrpLremnantService prpLremnantService) {
		this.prpLremnantService = prpLremnantService;
	}

	public PrpLpayObjectInfoService getPrpLpayObjectInfoService() {
		return prpLpayObjectInfoService;
	}

	public void setPrpLpayObjectInfoService(
			PrpLpayObjectInfoService prpLpayObjectInfoService) {
		this.prpLpayObjectInfoService = prpLpayObjectInfoService;
	}

	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}

	public PrpLintfProcessService getPrpLintfProcessService() {
		return prpLintfProcessService;
	}

	public void setPrpLintfProcessService(
			PrpLintfProcessService prpLintfProcessService) {
		this.prpLintfProcessService = prpLintfProcessService;
	}
	public PrpLchargeService getPrpLchargeService() {
		return prpLchargeService;
	}

	public void setPrpLchargeService(PrpLchargeService prpLchargeService) {
		this.prpLchargeService = prpLchargeService;
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
	
}
