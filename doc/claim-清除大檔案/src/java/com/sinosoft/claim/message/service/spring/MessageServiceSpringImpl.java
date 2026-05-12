package com.sinosoft.claim.message.service.spring;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.sinosoft.claim.message.send.MsgSenderThread;
import com.sinosoft.claim.message.service.facade.MessageService;
import com.sinosoft.claim.message.vo.MsgSenderDto;
import com.sinosoft.claim.schema.model.WfLog;
import com.sinosoft.sysframework.reference.AppConfig;
import com.sinosoft.sysframework.reference.DBManager;

/**
 * <p>
 * Title: 即时消息
 * </p>
 * <p>
 * Description: 即时消息
 * </p>
 * @author 中科软
 * @version
 */
public class MessageServiceSpringImpl implements MessageService{
	/**
	 * 發送消息方法
	 * @param userCode 發送用戶
	 * @param content 發送內容
	 * @throws Exception 
	 */
	@Override
	public List<String> getUsersOfNode(WfLog wfLog,WfLog wfLogOld) throws Exception{
		DBManager dbManager = new DBManager();
		dbManager.open(AppConfig.get("sysconst.DBJNDI"));
		ArrayList<String> list = new ArrayList<String>();
		String sql = "";
		if("C".equals(wfLog.getBusinessType()) || "Y".equals(wfLog.getBusinessType())){
			if(wfLog.getNodeNo()==1){
				list.add(wfLog.getHandlerCode().trim());
				return list;
			}else if(wfLog.getNodeNo()==11){
				sql = "select  distinct usercode from utiusergrade where comcode in ('"+wfLog.getComCode()+"','00',(select uppercomcode from prpdcompany where comcode='"+wfLog.getComCode()+"')) and gradecode in ('003','005')";
			}else{
				sql = "select  distinct usercode from utiuwlevel" + 
			    	     " where nodeno= '" + wfLog.getNodeNo() + "'" +
			    	     "and uwType='"+wfLog.getBusinessType()+"' ";
			}
		}
		//System.out.println("查询语句-------------------------"+sql);
		ResultSet resultSet = null;
		resultSet =  dbManager.executeQuery(sql);
		while (resultSet.next()) {
			String s = resultSet.getString("usercode");
			list.add(s);
		}
		return list;
	}
	/**
	 * 發送消息方法
	 * @param wfLog 新工作流節點
	 * @param wflogOld 舊工作流節點
	 * @throws Exception 
	 */
	public void send(WfLog wfLog,WfLog wflogOld){
		try{
			List<String> users = getUsersOfNode(wfLog,wflogOld);
			if(users == null || users.size() == 0){
				//System.out.println("即时通讯--------该节点没有人员");
				//users.add("AG215");
			}
			if(users == null || users.size() == 0){
				return;
			}
			MsgSenderDto msgSenderDto = new MsgSenderDto();
			msgSenderDto.setBusinessNo(wfLog.getBusinessNo());
			msgSenderDto.setBusinessType(wfLog.getBusinessType());
			msgSenderDto.setSendUserCode(wflogOld.getOperatorCode());
			msgSenderDto.setSendUserName(wflogOld.getOperatorName());
			msgSenderDto.setTaskLink(getHrefLink(wfLog));
			msgSenderDto.setRcverUser(users);
			msgSenderDto.setRiskCode(wfLog.getRiskCode());
			if (wfLog.getNodeNo() == 12) {
				msgSenderDto.setUpDownFlag("pass");
			} else if (wflogOld.getNodeNo() == 1) {
				msgSenderDto.setUpDownFlag("UC");
			} else {
				if (wfLog.getNodeNo() > wflogOld.getNodeNo()) {
					msgSenderDto.setUpDownFlag("up");
				} else {
					msgSenderDto.setUpDownFlag("down");
				}
			}
			
			MsgSenderThread msgSender = new MsgSenderThread(msgSenderDto);
			//System.out.println("即时通讯--消息发送线程开始启动");
			msgSender.start();
			//System.out.println("即时通讯--消息发送线程启动成功");

		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 獲取消息連接方法
	 * @param wflog 工作流節點
	 * @throws Exception 
	 */
	public String getHrefLink(WfLog wflog){
		StringBuffer buff = new StringBuffer();
		if (wflog.getNodeNo() == 1 && "T".equals(wflog.getBusinessType())) {
			buff.append("/prpins/policy/updateProposal.do?");
			buff.append("bizNo=" + wflog.getBusinessNo());
			buff.append("&editType=PO_EDIT");
			buff.append("&riskCode="+wflog.getRiskCode());
		} else if(wflog.getNodeNo() == 12 && "T".equals(wflog.getBusinessType())){
			buff.append("/prpins/endorse/browseEndorse.do?");
			buff.append("&editType=PO_VIEW&visaFlag=0");
		} else if (wflog.getNodeNo() == 12 && "E".equals(wflog.getBusinessType())) {
			buff.append("/prpins/endorse/browseEndorse.do?");
			buff.append("applyNo=" + wflog.getBusinessNo());
		} else if(wflog.getNodeNo() == 1 && "E".equals(wflog.getBusinessType())){
			buff.append("/prpins/endorse/browseEndorse.do?");
			buff.append("applyNo=" + wflog.getBusinessNo());
		}else if((wflog.getNodeNo()==1||wflog.getNodeNo()==12) && ("C".equals(wflog.getBusinessType())||"Y".equals(wflog.getBusinessType()))){
			buff.append("/claim/compensate/compensateFinishQueryList.do?");
			buff.append("prpLcompensateCompensateNo="+wflog.getBusinessNo());
			buff.append("&swfLogFlowID="+wflog.getRelateFlowId());
			buff.append("&swfLogLogNo="+wflog.getRelateLogNo());
			buff.append("&status="+wflog.getNodeStatus());
			buff.append("&riskCode="+wflog.getRiskCode());
			buff.append("&editType=EDIT");
			buff.append("&nodeType=compp");
			buff.append("&businessNo="+wflog.getBusinessNo());
			buff.append("&keyIn="+wflog.getClaimNo());
			buff.append("&policyNo="+wflog.getPolicyNo());
			buff.append("&modelNo="+wflog.getModelNo());
			buff.append("&nodeNo="+wflog.getNodeNo());
		}else {
			if ("T".equals(wflog.getBusinessType())) {
				buff.append("/undwrt/taskCheck/commonCheckTask.do?");
				buff.append("iFlowID=" + wflog.getId().getFlowId());
				buff.append("&iLogNo=" + wflog.getId().getLogNo());
				buff.append("&iBusinessNo=" + wflog.getBusinessNo());
				buff.append("&iBusinessType=" + wflog.getBusinessType());
				buff.append("&iContractNo=" + wflog.getContractNo());
				buff.append("&iPackageID=" + wflog.getPackageId());
				buff.append("&iModelNo=" + wflog.getModelNo());
				buff.append("&iNodeNo=" + wflog.getNodeNo());
				buff.append("&iFlowStatus=" + wflog.getFlowStatus());
				buff.append("&iDeptCode=" + wflog.getDeptCode());
				buff.append("&iFlowInTime=" + wflog.getFlowInTime());
				buff.append("&iNodeStatus=" + wflog.getNodeStatus());
				buff.append("&iRiskCode=" + wflog.getRiskCode());
				buff.append("&iClassCode=" + wflog.getClassCode());
				buff.append("&handType=11");
				buff.append("&EditType=deal");
			}
			if ("C".equals(wflog.getBusinessType())
					|| "Y".equals(wflog.getBusinessType())) {
				buff.append("/claim/CommonCheckTask.do?");
				buff.append("iFlowID=" + wflog.getId().getFlowId());
				buff.append("&iLogNo=" + wflog.getId().getLogNo());
				buff.append("&iBusinessNo=" + wflog.getBusinessNo());
				buff.append("&iBusinessType=" + wflog.getBusinessType());
				buff.append("&iContractNo=" + wflog.getContractNo());
				buff.append("&iPackageID=" + wflog.getPackageId());
				buff.append("&iModelNo=" + wflog.getModelNo());
				buff.append("&iNodeNo=" + wflog.getNodeNo());
				buff.append("&iFlowStatus=" + wflog.getFlowStatus());
				buff.append("&iDeptCode=" + wflog.getDeptCode());
				buff.append("&iFlowInTime=" + wflog.getFlowInTime());
				buff.append("&iNodeStatus=" + wflog.getNodeStatus());
				buff.append("&iRiskCode=" + wflog.getRiskCode());
				buff.append("&iClassCode=" + wflog.getClassCode());
				buff.append("&HandType=22");
				buff.append("&EditType=deal");
			}
		}
		return buff.toString();
	}
}

