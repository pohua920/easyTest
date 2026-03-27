/*
 * @(#)CommonDealSubmitAction.java	Feb 19, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.undwrt.web;

import ins.framework.web.Struts2Action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JasperRunManager;

import org.apache.struts2.views.jasperreports.ValueStackDataSource;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.ProcessTokenException;
import com.sinosoft.claim.email.service.facade.EmailService;
import com.sinosoft.claim.email.vo.Attachment;
import com.sinosoft.claim.email.vo.EmailDto;
import com.sinosoft.claim.print.util.PropPrintViewHelper;
import com.sinosoft.claim.print.vo.PropCompensateObject;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.claim.schema.service.facade.PrpLemailLogService;
import com.sinosoft.claim.schema.service.facade.PrpLrecaseService;
import com.sinosoft.claim.schema.service.facade.PrplregistrpolicyService;
import com.sinosoft.claim.schema.service.facade.SwfNodeService;
import com.sinosoft.claim.schema.service.facade.WfLogService;
import com.sinosoft.claim.sms.util.SmsViewHelper;
import com.sinosoft.claim.undwrt.service.facade.UndwrtJbpmService;
import com.sinosoft.claim.undwrt.util.WfLogQueryViewHelper;//mantis：CLM0241，處理人員： DP0713 ，需求單編號：強制任意批次核賠功能新增
import com.sinosoft.claim.workflow.util.JbpmBusinessViewHelper;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description
 */
@SuppressWarnings("serial")
public class CommonDealSubmitAction extends Struts2Action {

	/**处理核赔流程service*/
	private UndwrtJbpmService undwrtJbpmService;
	/**重开赔案service*/
	private PrpLrecaseService prpLrecaseService;
	/**关联报案service*/
	private PrplregistrpolicyService prpLregistrpolicyService;
	/**立案service*/
	private PrpLclaimService prpLclaimService;
	/**理算service*/
	private PrpLcompensateService prpLcompensateService;
	/**邮件服务service*/
	private PrpLemailLogService prpLemailLogService;
	/** 向承保发邮件*/
	private EmailService emailService;
	/**工作流节点service*/
	private SwfNodeService swfNodeService;
	private JbpmBusinessViewHelper jbpmBusinessViewHelper;
	private SmsViewHelper smsViewHelper;
	private WfLogService wfLogService;
	private CodeService codeService;
	private PropPrintViewHelper propPrintViewHelper;
	/**
	 * 任务处理提交
	 * @return
	 * @throws Exception
	 */
	public String commonDealSubmit() throws Exception {
		this.clearErrorsAndMessages();
		String forward = "";
		int flag = 0;
		HttpServletRequest req = this.getRequest();
		String businessNo = req.getParameter("BusinessNo");
		String businessType = req.getParameter("BusinessType");
		PrpLcompensate prplcompensate = prpLcompensateService.findPrpLcompensate(businessNo);
		try {
			// 核赔提交上级，下发修改，审核通过参数传递
			flag = undwrtJbpmService.submitTask(req);
			if (flag < 0) {
				throw new UserException(-98, -1007, this.getClass().getName(), "未知錯誤！請與系統管理員聯系！");
			} else {
				int ModelNo = Integer.parseInt((String) req.getParameter("ModelNo"));
				int NodeNo = Integer.parseInt((String) req.getParameter("selectNodeNo"));
				String endFlag = swfNodeService.findEndFlag(ModelNo, NodeNo);
//				String riskCategory = req.getParameter("riskCategory");
				String riskCode = req.getParameter("strRiskCode");
				String riskType = this.codeService.translateRiskCodetoRiskType(riskCode);
				//审核通过后才发送简讯和email
				if ("1".equals(endFlag)) {
					if ("C".equals(businessType)) {
						if(ConstantCodes.CLASSCODE_Q.equals(riskType) &&  prplcompensate.getSumDutyPaid()>0){
							//火險對於有賠款的核賠任务 才发送邮件
							File file = null;
							try {
								Map<String, Object> emailInfoMap = this.getEmailInfoMap(prplcompensate, req);
								EmailDto email = new EmailDto();
								email.setModel(emailInfoMap);
							
								Map<String, Object> param = new HashMap<String, Object>();
								String path = super.getRequest().getSession().getServletContext().getRealPath("") + "/printReport/";
								param.put("IMGPATH", path + "image/logo.jpg");
								param.put("SUBREPORT_DIR", path + "Prop/");
								param.put("DISPLAYFLAG", false);
								PropCompensateObject propCompensateObject = this.propPrintViewHelper.findPropClaimCompensateReportObjectByCompensateNo(prplcompensate.getCompensateNo());
								if(propCompensateObject.getCompensateSubreport2Object().size() > 0){
									param.put("DISPLAYFLAG", true);
								}
								List<Object> resultList = new ArrayList<Object>();
								resultList.add(propCompensateObject);
								String filePath = "/Prop/" + prplcompensate.getCompensateNo() + "-" + System.currentTimeMillis() + ".pdf";
								String printFilePath = "/Prop/PropClaimCompensateReport.jasper";
								ValueStack stack = ActionContext.getContext().getValueStack();
								stack.set("resultList", resultList);
								ValueStackDataSource stackDataSource = new ValueStackDataSource(stack, "resultList");
								JasperRunManager.runReportToPdfFile(path + printFilePath, path + filePath, param, stackDataSource);
								file = CommonUtils.getWebRootFile("/printReport" + filePath);
								if (file != null) {
									email.getAttachment().add(new Attachment("理賠計算書-" + prplcompensate.getCompensateNo() + ".pdf", file));
								}
								this.getEmailService().mailSend(prplcompensate.getCompensateNo(), "10000", "02", email);
								this.addActionMessage("郵件：理賠減保通知函發送成功！");
							} catch (Exception e) {
								e.printStackTrace();//
								this.addActionMessage("郵件：理賠減保通知函發送失败！");
							} finally {
								if (file != null && file.isFile() && file.exists()) {
									file.delete();
								}
							}
						}
					}
					smsViewHelper.sendSms(businessNo, "veric");
				}
			}
//			Object workFlowDto = MDC.get("workFlowDto");//获取核赔处理设置的工作流对象
//			if(workFlowDto!=null){
//				this.jbpmBusinessViewHelper.saveWorkFlow((WorkFlowDto)workFlowDto);
//			}
			forward = "success";
			this.addActionMessage("任務提交成功！");
			req.removeAttribute("content");
		} catch (UserException usee) {
			forward = "failure";
			HttpSession session = req.getSession();
			session.setAttribute("userException", usee);
			usee.printStackTrace();
		} catch (com.sinosoft.utility.error.UserException usee) {
			forward = "failure";
			HttpSession session = req.getSession();
			session.setAttribute("userException", usee);
		} catch (ProcessTokenException e) {
			throw e;
		} catch (Exception e) {
			UserException userException = new UserException(-98, -1007, this.getClass().getName(), e.getMessage());
			forward = "failure";
			HttpSession session = req.getSession();
			e.printStackTrace();
			session.setAttribute("userException", userException);
		} finally {
//			MDC.remove("workFlowDto");//移除核赔处理设置的工作流对象
		}
		return forward;
	}
	
	/**
	 * 批次-批次任务处理提交
	 * mantis：CLM0241，處理人員： DP0713 ，需求單編號：強制任意批次核賠功能新增  -批次任务处理提交
	 * @return
	 * @throws Exception
	 */
	public String commonDealHeapSubmit() throws Exception {
		this.clearErrorsAndMessages();
		String forward = "";
		int flag = 0;
		HttpServletRequest req = this.getRequest();
		String businessNo = req.getParameter("BusinessNo");
		String businessType = req.getParameter("BusinessType");
		
		try {
			// 核赔提交上级，下发修改，审核通过参数传递
			flag = undwrtJbpmService.submitHeapTask(req);
			if (flag < 0) {
				throw new UserException(-98, -1007, this.getClass().getName(), "未知錯誤！請與系統管理員聯系！");
			}
			forward = "success";
			this.addActionMessage("任務提交成功！");
			req.removeAttribute("content");
		} catch (Exception e) {
			UserException userException = new UserException(-98, -1007, this.getClass().getName(), e.getMessage());
			forward = "failure";
			HttpSession session = req.getSession();
			e.printStackTrace();
			session.setAttribute("userException", userException);
		} finally {
//			MDC.remove("workFlowDto");//移除核赔处理设置的工作流对象
		}
		return forward;
	}
	/**
	 * 收集发送邮件的内容
	 * @param claimDto
	 * @return
	 * @throws Exception 
	 */
	private Map<String, Object> getEmailInfoMap(PrpLcompensate prpLcompensate,HttpServletRequest httpServletRequest) throws Exception{
		String claimNo="";
		String policyNo="";
		String insuredName="";
		String compensateNo="";
		Map<String, Object> emailInfoMap = new HashMap<String, Object>();
		claimNo = prpLcompensate.getClaimNo();
		policyNo = prpLcompensate.getPolicyNo();
//		insuredName = prpLcompensate.getInsuredName();
		compensateNo = prpLcompensate.getCompensateNo();
		PrpLclaim prpLclaim = this.prpLclaimService.findPrpLclaim(prpLcompensate.getClaimNo());
		insuredName = prpLclaim.getInsuredName();
		StringBuilder compensateLink = new StringBuilder();
		compensateLink.append("http://");
		compensateLink.append(httpServletRequest.getServerName()+":"+httpServletRequest.getServerPort());//从httpServletRequest中获取服务器地址及端口号
		compensateLink.append("/claim/JRPropPropClaimApplicationForm.do?claimNo="+claimNo);
		emailInfoMap.put("compensateLink",compensateLink);
		emailInfoMap.put("insuredName",insuredName);//被保险人名称
		emailInfoMap.put("businessNo", claimNo);//賠案號碼
		emailInfoMap.put("policyNo", policyNo);//保單號碼
		emailInfoMap.put("compensateNo", compensateNo);//计算书号
		return emailInfoMap;
	}

	public PrpLrecaseService getPrpLrecaseService() {
		return prpLrecaseService;
	}

	public void setPrpLrecaseService(PrpLrecaseService prpLrecaseService) {
		this.prpLrecaseService = prpLrecaseService;
	}

	public PrplregistrpolicyService getPrpLregistrpolicyService() {
		return prpLregistrpolicyService;
	}

	public void setPrpLregistrpolicyService(PrplregistrpolicyService prpLregistrpolicyService) {
		this.prpLregistrpolicyService = prpLregistrpolicyService;
	}

	public UndwrtJbpmService getUndwrtJbpmService() {
		return undwrtJbpmService;
	}

	public void setUndwrtJbpmService(UndwrtJbpmService undwrtJbpmService) {
		this.undwrtJbpmService = undwrtJbpmService;
	}

	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}

	public SwfNodeService getSwfNodeService() {
		return swfNodeService;
	}

	public void setSwfNodeService(SwfNodeService swfNodeService) {
		this.swfNodeService = swfNodeService;
	}
	
	public JbpmBusinessViewHelper getJbpmBusinessViewHelper() {
		return jbpmBusinessViewHelper;
	}

	public void setJbpmBusinessViewHelper(JbpmBusinessViewHelper jbpmBusinessViewHelper) {
		this.jbpmBusinessViewHelper = jbpmBusinessViewHelper;
	}

	public PrpLcompensateService getPrpLcompensateService() {
		return prpLcompensateService;
	}

	public void setPrpLcompensateService(PrpLcompensateService prpLcompensateService) {
		this.prpLcompensateService = prpLcompensateService;
	}

	public PrpLemailLogService getPrpLemailLogService() {
		return prpLemailLogService;
	}

	public void setPrpLemailLogService(PrpLemailLogService prpLemailLogService) {
		this.prpLemailLogService = prpLemailLogService;
	}

	public EmailService getEmailService() {
		return emailService;
	}

	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

	public SmsViewHelper getSmsViewHelper() {
		return smsViewHelper;
	}

	public void setSmsViewHelper(SmsViewHelper smsViewHelper) {
		this.smsViewHelper = smsViewHelper;
	}

	public WfLogService getWfLogService() {
		return wfLogService;
	}

	public void setWfLogService(WfLogService wfLogService) {
		this.wfLogService = wfLogService;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	public PropPrintViewHelper getPropPrintViewHelper() {
		return propPrintViewHelper;
	}

	public void setPropPrintViewHelper(PropPrintViewHelper propPrintViewHelper) {
		this.propPrintViewHelper = propPrintViewHelper;
	}
	
}
