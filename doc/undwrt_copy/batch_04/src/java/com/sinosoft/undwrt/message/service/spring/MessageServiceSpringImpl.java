package com.sinosoft.undwrt.message.service.spring;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.sinosoft.sysframework.reference.AppConfig;
import com.sinosoft.sysframework.reference.DBManager;
import com.sinosoft.undwrt.message.model.MsgSenderDto;
import com.sinosoft.undwrt.message.send.MsgSenderThread;
import com.sinosoft.undwrt.message.service.facade.MessageService;
import com.sinosoft.undwrt.undwrtBase.model.WfLog;

/**
 * 即時通訊實現類.
 */
public class MessageServiceSpringImpl implements MessageService {
	
	/**
	 * 獲取用戶代碼列表.
	 * 
	 * @param wfLog
	 *            工作流日誌
	 * @param wfLogOld
	 *            工作流日誌
	 * @return 滿足條件的用戶代碼集合
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.message.service.facade.MessageService#getUsersOfNode(com.sinosoft.undwrt.undwrtBase.model.WfLog,
	 *      com.sinosoft.undwrt.undwrtBase.model.WfLog)
	 */
	@Override
	public ArrayList<String> getUsersOfNode(WfLog wfLog, WfLog wfLogOld)
			 {
		ArrayList<String> list = new ArrayList<String>();
		String sql = "";
		if (wfLog.getNodeNo() == 12 || wfLog.getNodeNo() == 1) {
			sql = "select  distinct usercode from utiusergrade"
					+ " where comcode = '" + wfLog.getComCode() + "' "
					+ "and gradecode in ('115','116','117','118')";
		} else{
			sql = "select  distinct usercode from utiuwlevel"
					+ " where nodeno= '" + wfLog.getNodeNo() + "' "
					+ "and uwType='T' and comcode in ('"+wfLog.getComCode()+"','00',(select uppercomcode from prpdcompany where comcode='"+wfLog.getComCode()+"'))";
		}
		System.out.println("查询语句-------------------------" + sql);
		ResultSet resultSet = null;
		DBManager dbManager = new DBManager();
		try {
		dbManager.open(AppConfig.get("sysconst.UNDWRTDATASOURCE"));
		resultSet = dbManager.executeQuery(sql);
		while (resultSet.next()) {
			String s = resultSet.getString("usercode");
			list.add(s);
		}
		}
	  catch (Exception e) {
			e.printStackTrace();
		} 
		finally
		{
			try {
				dbManager.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * 發送即時訊息.
	 * 
	 * @param wfLog
	 *            工作流日誌
	 * @param wflogOld
	 *            工作流日誌
	 * @see com.sinosoft.undwrt.message.service.facade.MessageService#send(com.sinosoft.undwrt.undwrtBase.model.WfLog,
	 *      com.sinosoft.undwrt.undwrtBase.model.WfLog)
	 */
	public void send(WfLog wfLog, WfLog wflogOld) {
		try {
			ArrayList<String> users = getUsersOfNode(wfLog, wflogOld);
			if (users == null || users.size() == 0) {
				System.out.println("即时通讯--------该节点没有人员");
				//users.add("AG215");
			}
			if (users == null || users.size() == 0) {
				return;
			}
			MsgSenderDto msgSenderDto = new MsgSenderDto();
			msgSenderDto.setBusinessNo(wfLog.getBusinessNo());
			msgSenderDto.setSendUserCode(wflogOld.getOperatorCode());
			msgSenderDto.setSendUserName(wflogOld.getOperatorName());
			msgSenderDto.setTaskLink(getHrefLink(wfLog));
			msgSenderDto.setRcverUser(users);
			msgSenderDto.setBusinessType(wfLog.getBusinessType());
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
			System.out.println("即时通讯--消息发送线程开始启动");
			msgSender.start();
			System.out.println("即时通讯--消息发送线程启动成功");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 獲取請求鏈接地址.
	 * 
	 * @param wflog
	 *            工作流日誌
	 * @return 請求鏈接地址
	 */
	public String getHrefLink(WfLog wflog) {
		StringBuffer buff = new StringBuffer();
		if (wflog.getNodeNo() == 1 && "T".equals(wflog.getBusinessType())) {
			buff.append("/prpins/policy/updateProposal.do?");
			buff.append("bizNo=" + wflog.getBusinessNo());
			buff.append("&editType=PO_EDIT");
			buff.append("&riskCode="+wflog.getRiskCode());
		} else if(wflog.getNodeNo() == 12 && "T".equals(wflog.getBusinessType())){
			buff.append("/prpins/policy/browseProposal.do?");
			buff.append("&proposalNo="+wflog.getBusinessNo());
			buff.append("&editType=PO_VIEW&visaFlag=0");
		} else if (wflog.getNodeNo() == 12 && "E".equals(wflog.getBusinessType())) {
			buff.append("/prpins/endorse/browseEndorse.do?");
			buff.append("applyNo=" + wflog.getBusinessNo());
		} else if(wflog.getNodeNo() == 1 && "E".equals(wflog.getBusinessType())){
			buff.append("/prpins/endorse/browseEndorse.do?");
			buff.append("applyNo=" + wflog.getBusinessNo());
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
				buff.append("&HandType=11");
				buff.append("&EditType=deal");
				buff.append("&handType=11");
				buff.append("&editType=deal");
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
			if ("E".equals(wflog.getBusinessType())) {
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
				buff.append("&HandType=11");
				buff.append("&EditType=deal");
				buff.append("&handType=11");
				buff.append("&editType=deal");
			}
		}
		return buff.toString();
	}
}
