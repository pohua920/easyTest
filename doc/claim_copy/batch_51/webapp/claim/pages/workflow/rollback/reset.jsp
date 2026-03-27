<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="ins.framework.common.ServiceFactory"%>
<%@ page import="com.sinosoft.claim.common.service.facade.CommonService"%>
<%@ page import="com.sinosoft.claim.workflow.service.facade.WorkFlowService"%>
<%@ page import="com.sinosoft.claim.schema.model.SwfLog"%>
<%@ page import="java.util.*"%>
<%@ page import="com.sinosoft.one.bpm.util.JbpmAPIUtil"%>
<%
	String flowID = request.getParameter("flowID");
	String logNo = request.getParameter("logNo");
	Long taskId = -1l;
	String message = "";//處理訊息
	String f = "0";//狀態 1，恢復成功
	System.err.println("恢復處理參數2：" + "{ \"flowID\" : \"" + flowID + "\" , \"logNo\" : \"" + logNo + "\" }");
	if (flowID == null || flowID.trim().length() == 0 || logNo == null || logNo.trim().length() == 0) {
		message = "參數不正確！";
	} else {
		WorkFlowService workFlowService = (WorkFlowService) ServiceFactory.getService("workFlowService");
		SwfLog swfLog = workFlowService.findByPrimaryKey(flowID, Integer.parseInt(logNo));
		if(swfLog == null){
			message = "未能查詢到工作流任務！";
		}else{
			String nodeStatus = swfLog.getNodeStatus();
			if("4".equals(nodeStatus)){
				message = "該節點任務已處理！";
			} else if("5".equals(nodeStatus)){
				message = "該節點任務已退回！";
			} else if("6".equals(nodeStatus)){
				message = "該節點任務已註銷！";
			} else {
				CommonService commonService = (CommonService) ServiceFactory.getService("commonService");
				String statements = "select 0 from swfpathlog where flowID = '"+swfLog.getId().getFlowID()+"' and startnodeno = "+ logNo;
				List<?> list = commonService.findByStatements(statements);
				if(list!=null && !list.isEmpty() && !"sched".equals(swfLog.getNodeType()) && !"compe".startsWith(swfLog.getNodeType())){//代表工作流無後續節點
					message = "該節點任務已流轉，且存在後續節點任務！";
				} else {
					String processId = swfLog.getProcessId();
					String businessId = swfLog.getBusinessId();
					String actorId = swfLog.getActorId();
					if(processId == null || processId.trim().length() == 0 || businessId == null || businessId.trim().length() == 0|| actorId == null || actorId.trim().length() == 0){
						message = "該節點任務未關聯JBPM工作流！";
					} else {
						// 查詢當前處理的jbpm任務
						statements = " select task.id ,task.status from task , processinstanceboinfo t where task.processinstanceid = t.processinstanceid and " + " t.businessid = '" + businessId
								+ "' and t.status = '1' and ( task.actualowner_id = '" + actorId + "' or task.createdby_id = '" + actorId + "' ) ";
						list = commonService.findByStatements(statements);
						if (list == null || list.isEmpty()) {
							message = "未能查詢到JBPM任務！";
						} else {
							boolean check = true;
							Iterator<?> it = list.iterator();
							while(it.hasNext()){
								Object[] obj = (Object[])it.next();
								String status = String.valueOf(obj[1]);
								if(org.jbpm.task.Status.Reserved.name().equals(status)){//存在 可以處理的任務，則表示無需恢復
									check = false;
									break;
								} else {
									taskId = Long.parseLong(obj[0].toString());//
								}
							}
							if (check) {//業務未處理，JBPM已處理，可恢復
								try {
									System.err.println("恢復處理參數2：" + "{ \"processId\" : " + processId + " , \"businessId\" : " + businessId + " , \"actorId\" : " + actorId + " , \"taskId\" : " + taskId + "}");
									JbpmAPIUtil.rollbackTask(processId, businessId, actorId, taskId);
									f = "1";
								} catch (Exception e) {
									e.printStackTrace();
									message = "恢復出現異常，請查看LOG日誌！";
								}
							} else {
								message = "該節點JBPM任務尚未流轉，無需恢復！";
							}
						}
					}
				}
			}
		}
	}
	System.err.println("恢復處理結果：" + "{\"status\":\"" + f + "\",\"message\":\"" + message + "\"}");
	response.getWriter().print("{\"status\":\"" + f + "\",\"message\":\"" + message + "\"}");
%>