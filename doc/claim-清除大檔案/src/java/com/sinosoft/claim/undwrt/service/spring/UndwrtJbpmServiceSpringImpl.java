package com.sinosoft.claim.undwrt.service.spring;

import ins.framework.dao.GenericDaoHibernate;


import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.claim.undwrt.service.facade.UndwrtJbpmService;
import com.sinosoft.claim.undwrt.util.CommonDealSubmitViewHelper;
import com.sinosoft.one.bpm.aspect.ProcessTask;
import com.sinosoft.one.bpm.aspect.TaskParam;
import com.sinosoft.one.bpm.aspect.TaskParams;
import com.sinosoft.sysframework.reference.AppConfig;

public class UndwrtJbpmServiceSpringImpl  extends GenericDaoHibernate implements UndwrtJbpmService{
	
	private CommonDealSubmitViewHelper commonDealSubmitViewHelper;
	private PrpLcompensateService prpLcompensateService;
	
	/* (non-Javadoc)
	 * 核赔通过後保存
	 * @see com.sinosoft.claim.undwrt.service.facade.UndwrtJbpmService#submitTaskBpm(java.lang.String, java.lang.String, java.util.List, javax.servlet.http.HttpServletRequest)
	 */
//	@ProcessTask(userId="veric",businessBeanOffset=0)
//	@TaskParams(taskParams={@TaskParam(key="nodeType", paramValueBeanOffset=1)})
//	@Variable(name = "nodeList", variableValueBeanOffset=2,scope=VariableScope.PROCESSINSTANCE,processId="claim_05",businessBeanOffset=0)
//	public int submitTaskBpm(String businessNo,String nodeType,List nodeList,HttpServletRequest req) throws Exception{
//		return commonDealSubmitViewHelper.submitTask(req);
//	}
	
	/**
	 * 保存核赔节点，带jbpm工作流信息
	 * @param businessNo
	 * @param nodeType
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@ProcessTask(processIdBeanOffset=2,userId="veric",businessBeanOffset=0)
	@TaskParams(taskParams={@TaskParam(key="nodeType", paramValueBeanOffset=1)})
	public int submitTaskBpm(String businessNo,String nodeType,String processId,HttpServletRequest req) throws Exception{
		return commonDealSubmitViewHelper.submitTask(req);
	}

	/**
	 * 保存特殊赔案节点，带jbpm工作流信息
	 * @param nodeType
	 * @param businessNo
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@ProcessTask(processIdBeanOffset=2,userId="speci_veric",businessBeanOffset=1)
	@TaskParams(taskParams={@TaskParam(key="nodeType", paramValueBeanOffset=0)})
	public int submitTaskSpeciBpm(String nodeType,String businessNo,String processId,HttpServletRequest req) throws Exception{
		return commonDealSubmitViewHelper.submitTask(req);
	}
	
	/**
	 * 核赔通过後获取jbpm下一个节点名称
	 * @param businessNo
	 * @return
	 * @throws Exception
	 */
	public String getJbpmNextNode(String businessNo) throws Exception{
		 //???判断是不是自动结案(从 appconfig+计算书的最终标志)，如果是做passVericAndCloseFlow();
	     String autoEndCaseFlag = AppConfig.get("sysconst.AutoEndCase");
		 PrpLcompensate prpLcompensate = prpLcompensateService.findPrpLcompensate(businessNo);
		 String nodeType = "endca";
		if ((businessNo.substring(0,1).equals("3")|| businessNo.substring(0,1).equals("C")|| businessNo.substring(0,1).equals("D"))&&
				"1".equals(autoEndCaseFlag) && prpLcompensate!=null&&
				("1".equals(prpLcompensate.getFinallyFlag())||
						"2".equals(prpLcompensate.getFinallyFlag()))) {
			nodeType = "end";
		}
		return nodeType;
	}
	
	public int submitTask(HttpServletRequest req)throws Exception{
		return commonDealSubmitViewHelper.submitTask(req);
	}
	
	//mantis：CLM0241，處理人員： DP0713 ，需求單編號：強制任意批次核賠功能新增
	public int submitHeapTask(HttpServletRequest req)throws Exception{
		return commonDealSubmitViewHelper.submitHeapTask(req);
	}
	
	public CommonDealSubmitViewHelper getCommonDealSubmitViewHelper() {
		return commonDealSubmitViewHelper;
	}

	public void setCommonDealSubmitViewHelper(CommonDealSubmitViewHelper commonDealSubmitViewHelper) {
		this.commonDealSubmitViewHelper = commonDealSubmitViewHelper;
	}

	public PrpLcompensateService getPrpLcompensateService() {
		return prpLcompensateService;
	}

	public void setPrpLcompensateService(PrpLcompensateService prpLcompensateService) {
		this.prpLcompensateService = prpLcompensateService;
	}

}
