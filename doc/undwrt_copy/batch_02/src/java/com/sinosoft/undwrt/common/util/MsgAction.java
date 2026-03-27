package com.sinosoft.undwrt.common.util;

import ins.framework.common.QueryRule;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import javax.naming.NamingException;

import com.sinosoft.platform.dto.domain.UtiUserMsgPathDto;
import com.sinosoft.platform.resource.dtofactory.domain.DBUtiUserMsgPath;
import com.sinosoft.prpall.dbsvr.cb.DBPrpCinsured;
import com.sinosoft.prpall.dbsvr.cb.DBPrpCinsuredNature;
import com.sinosoft.prpall.dbsvr.cb.DBPrpCitemCar;
import com.sinosoft.prpall.dto.domain.PrpCmainDto;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpCmain;
import com.sinosoft.prpall.schema.PrpCinsuredNatureSchema;
import com.sinosoft.prpall.schema.PrpCinsuredSchema;
import com.sinosoft.prpall.schema.PrpCitemCarSchema;
import com.sinosoft.prpall.schema.PrpCmainSchema;
import com.sinosoft.sysframework.reference.DBManager;
import com.sinosoft.undwrt.common.util.sms.services.SMsgService;
import com.sinosoft.undwrt.common.util.sms.services.SMsgServiceLocator;
import com.sinosoft.undwrt.common.util.sms.services.SMsg_PortType;
import com.sinosoft.undwrt.undwrtBase.model.WfLog;
import com.sinosoft.undwrt.undwrtBase.service.facade.WfLogService;
import com.sinosoft.utiall.dbsvr.DBPrpDcode;
import com.sinosoft.utiall.dbsvr.DBPrpDcodeRisk;
import com.sinosoft.utiall.dbsvr.DBPrpDrisk;
import com.sinosoft.utiall.schema.PrpDcodeSchema;
import com.sinosoft.utiall.schema.PrpDriskSchema;
import com.sinosoft.utility.SysConfig;
import com.sinosoft.utility.database.DbPool;

/**
 * <p>
 * Title: uwweb
 * </p>
 * <p>
 * Description:消息发送程序
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * .
 * 
 * @author luojing
 * @version 1.0
 */
public class MsgAction
{
	
	
	
	/**
	 * 执行消息发送.
	 * 
	 * @param wfLogService
	 *            工作流日誌 service
	 * @param dbManager
	 *            数据库连接
	 * @param wflogDto
	 *            工作流当前节点信息
	 * @param wflogOldDto
	 *            工作流中上一个节点
	 * @param endflag
	 *            是否结束节点的标识
	 * @throws Exception
	 *             the exception
	 */
	public void send(WfLogService wfLogService,DBManager dbManager,WfLog wflogDto,WfLog wflogOldDto,boolean endflag)
			throws Exception {
		Collection userList = new ArrayList();
		GetUsersOfNode dBGetUsersOfNode = new GetUsersOfNode();
		WfLog wflogDtoTmp = wflogDto;

		DBPrpCmain dbPrpCmain =new DBPrpCmain(dbManager);
		DBUtiUserMsgPath dbUtiUserMsgPath = new DBUtiUserMsgPath(dbManager);
		String msgContent = "业务处理信息发送失败！";
		String noType = null;
		String noSucc = null;
		String noWait = null;
		String noWaitModify = null;
		
		//工作流中向上找节点的最高一级
		String msgWFLastNo = com.sinosoft.sysframework.reference.AppConfig.get("sysconst.MSG_WF_LASTNO");
		int iMsWFLastNo = msgWFLastNo==null?6:Integer.parseInt(msgWFLastNo);
		//工作流中向下找节点的最低一级
		String msgWFFirstNo = com.sinosoft.sysframework.reference.AppConfig.get("sysconst.MSG_WF_FIRSTNO");
		int iMsWFFirstNo = msgWFFirstNo==null?2:Integer.parseInt(msgWFFirstNo);
		//工作流向下是按照节点还是按照具体操作员,0是到节点,1是到操作员(缺省)
		String wfowDownNode = com.sinosoft.sysframework.reference.AppConfig.get("sysconst.MSG_WF_DOWN_NODE");
		int iWfowDownNode =wfowDownNode==null?1:Integer.parseInt(wfowDownNode);
		//判断是否采用同步的消息处理机制,0是同步,1是异步,缺省是异步
		String wfissync = com.sinosoft.sysframework.reference.AppConfig.get("sysconst.MSG_IS_SYNC");
		int iWfissync =wfissync==null?1:Integer.parseInt(wfissync);
		
		boolean wflowFlag = false;//工作流流转的标签,false表示反向流程即下发修改,true为正向流程即提交上级
		
		if (wflogDtoTmp.getNodeNo()>wflogOldDto.getNodeNo())
			wflowFlag = true;
		
		if(wflogDtoTmp.getBusinessType().equals("T")){
			noType = "投保单号：";
			noSucc = "核保完成!生成新的保单号:";
			noWait = "等待核保";
			noWaitModify = "等待核保修改";
		}else if(wflogDtoTmp.getBusinessType().equals("E")){
			noType = "批单号：";
			noSucc = "核批完成!";
			noWait = "等待核批";
			noWaitModify = "等待批改修改";
		}else{
			noType = "业务号：";			
			noSucc = "业务处理完成!";
			noWait = "等待业务处理";
			noWaitModify = "等待修改";
		}
		
		

		
		//判断是否是开始或者结束节点
		if(endflag){//结束点直接发送核保通过信息给出单员
			//获取第一个节点，从而得到出单员
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.flowId", wflogDtoTmp.getId().getFlowId());
			queryRule.addEqual("id.logNo", 1);
			wflogDtoTmp = wfLogService.findByPrimaryKey(queryRule);
			
			userList.add(wflogDtoTmp.getOperatorCode());
			String strWherePart = " ProposalNo = '" + wflogDtoTmp.getBusinessNo() + "'";
			ArrayList tmpList = (ArrayList)dbPrpCmain.findByConditions(strWherePart);
			if(tmpList.size()>0)
				noSucc = noSucc + ((PrpCmainDto)(tmpList.toArray())[0]).getPolicyNo();
			else
				noSucc = noSucc + "未生成";
			msgContent = "(" + wflogOldDto.getOperatorName() + ") " + noType + wflogDtoTmp.getBusinessNo() + noSucc;
		}else if(wflogDtoTmp.getNodeNo() == 1){//开始点直接发送核保修改信息给出单员
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.flowId", wflogDtoTmp.getId().getFlowId());
			queryRule.addEqual("id.logNo", 1);
			wflogDtoTmp = wfLogService.findByPrimaryKey(queryRule);
			
			userList.add(wflogDtoTmp.getOperatorCode());
			msgContent =  "(" + wflogOldDto.getOperatorName() + ") " + noType  + wflogDtoTmp.getBusinessNo() + noWaitModify;			
		}else{//除去结束节点和开始节点的其他节点的处理，根据前后两个节点的节点号来判断是下发修改还是提交上级
			int currNode = wflogDtoTmp.getNodeNo();
			if(wflowFlag){//提交上级
				msgContent = "(" + wflogOldDto.getOperatorName() + ") " + noType  + wflogDtoTmp.getBusinessNo() + noWait;
				ArrayList tmpList = (ArrayList)this.getWFlOGByNodeNO(wfLogService,wflogOldDto.getId().getFlowId().trim(), wflogDtoTmp.getNodeNo());
				if(tmpList.size()==0){//判断当前提交节点是否曾经下发修改过,如果是就直接发送消息给具体操作员,如果否则发给所有此节点下的操作员
					//userList = dBGetUsersOfNode.getUsersOfNode(wflogDtoTmp, dbManager);
					userList = this.getUsersOfNode(dBGetUsersOfNode, dbUtiUserMsgPath, dbManager, wflogDto, wflogOldDto);
				}else{
					userList.add(((WfLog)(tmpList.toArray())[0]).getOperatorCode());
				}
			}else{//下发修改,取出wflog中最后一条当前下发节点的记录
				msgContent = "(" + wflogOldDto.getOperatorName() + ") " + noType  + wflogDtoTmp.getBusinessNo() + noWaitModify;
				if(iWfowDownNode ==1){//下发到具体操作员
					ArrayList tmpList = (ArrayList)this.getWFlOGByNodeNO(wfLogService,wflogOldDto.getId().getFlowId().trim(), wflogDtoTmp.getNodeNo());
					if(tmpList.size()>0)
						userList.add(((WfLog)(tmpList.toArray())[0]).getOperatorCode());
				}else{
					//userList = dBGetUsersOfNode.getUsersOfNode(wflogDtoTmp, dbManager);
					userList = this.getUsersOfNode(dBGetUsersOfNode, dbUtiUserMsgPath, dbManager, wflogDto, wflogOldDto);
				}
			}
			//判断当前节点是否没有安排操作人员
			if(userList.size()==0){//如果为空则循环取下一个节点的用户列表，正向流程直到取出用户或者到节点6,反向流程直到取出用户或者到节点2
				while(userList.size() == 0){
					if(wflowFlag && currNode >=iMsWFLastNo) 
						break;
					if(!wflowFlag && currNode <= iMsWFFirstNo)
						break;
					if(wflowFlag){
						wflogDtoTmp.setNodeNo(++currNode);
						ArrayList tmpList = (ArrayList)this.getWFlOGByNodeNO(wfLogService,wflogOldDto.getId().getFlowId().trim(), wflogDtoTmp.getNodeNo());
						if(tmpList.size()==0){//判断当前提交节点是否曾经下发修改过,如果是就直接发送消息给具体操作员,如果否则发给所有此节点下的操作员
							//userList = dBGetUsersOfNode.getUsersOfNode(wflogDtoTmp, dbManager);
							userList = this.getUsersOfNode(dBGetUsersOfNode, dbUtiUserMsgPath, dbManager, wflogDto, wflogOldDto);
						}else{
							userList.add(((WfLog)(tmpList.toArray())[0]).getOperatorCode());
						}
					}else{
						wflogDtoTmp.setNodeNo(--currNode);
						if(iWfowDownNode ==1){//下发到具体操作员
							ArrayList tmpList = (ArrayList)this.getWFlOGByNodeNO(wfLogService,wflogOldDto.getId().getFlowId().trim(), wflogDtoTmp.getNodeNo());
							if(tmpList.size()>0)
								userList.add(((WfLog)(tmpList.toArray())[0]).getOperatorCode());
						}else{
							//userList = dBGetUsersOfNode.getUsersOfNode(wflogDtoTmp, dbManager);
							userList = this.getUsersOfNode(dBGetUsersOfNode, dbUtiUserMsgPath, dbManager, wflogDto, wflogOldDto);
						}
					}
				}				
			}
		}
		try{
			if(iWfissync==0){//判断是采用同步方式还是异步方式
				MsgSender msgSender = new MsgSender();
				msgSender.send(wflogOldDto.getOperatorCode(),userList,msgContent);
			}else{	
				MsgSenderThread msgSenderThread = new MsgSenderThread(wflogOldDto.getOperatorCode(),userList,msgContent);
				msgSenderThread.start();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 执行消息发送.
	 * 
	 * @param wfLogService
	 *            工作流日誌 service
	 * @param flowid
	 *            工作流id
	 * @param nodeNO
	 *            工作流节点号
	 * @return tmpList 　用户列表
	 * @throws Exception
	 *             the exception
	 */
	private Collection getWFlOGByNodeNO(WfLogService wfLogService,String flowid,int nodeNO)
		throws Exception {
//		String strWherePart = " flowid = '" + flowid + "' "
//			+ " And nodeno = '" + nodeNO + "' "
//			+ " order by logno desc";
		
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.flowId", flowid);
//		queryRule.addEqual("nodeNo", nodeNO);
//		queryRule.addDescOrder("id.logNo");
		ArrayList tmpList = new ArrayList();
				//(ArrayList)wfLogService.findByQueryRuleList(queryRule);
		
		for(int i=0;i<tmpList.size();i++){//删除操作员号为空的记录,不知道为什么总会取出一条空记录,因此特别处理
			WfLog wfLogDto = (WfLog)(tmpList.toArray())[i];
			if(wfLogDto.getOperatorCode() == null || wfLogDto.getOperatorCode().trim().equals("")){
				tmpList.remove(i);
			}
		}
		return tmpList;
	}
	
	
	/**
	 * 獲取屬性the sinosoft users from user msg path.
	 * 
	 * @param dbUtiUserMsgPath
	 *            the db uti user msg path
	 * @param sender
	 *            the sender
	 * @return 屬性the sinosoft users from user msg path的值
	 * @throws Exception
	 *             the exception
	 */
	private Collection getUsersFromUserMsgPath(DBUtiUserMsgPath dbUtiUserMsgPath,String sender)
		throws Exception {
		String strWherePart = " sendercode = '" + sender + "' ";
		ArrayList tmpList = (ArrayList)dbUtiUserMsgPath.findByConditions(strWherePart);
		for(int i=0;i<tmpList.size();i++){//删除操作员号为空的记录,不知道为什么总会取出一条空记录,因此特别处理
			UtiUserMsgPathDto utiUserMsgPathDto = (UtiUserMsgPathDto)(tmpList.toArray())[i];
			if(utiUserMsgPathDto.getReceiverCode() == null || utiUserMsgPathDto.getReceiverCode().trim().equals("")){
				tmpList.remove(i);
			}
		}
		return tmpList;
	}
	
	/**
	 * 獲取屬性the sinosoft users of node.
	 * 
	 * @param getUsersOfNode
	 *            the get users of node
	 * @param dbUtiUserMsgPath
	 *            the db uti user msg path
	 * @param dbManager
	 *            the db manager
	 * @param wflogDto
	 *            the wflog dto
	 * @param wflogOldDto
	 *            the wflog old dto
	 * @return 屬性the sinosoft users of node的值
	 * @throws Exception
	 *             the exception
	 */
	private Collection getUsersOfNode(GetUsersOfNode getUsersOfNode,DBUtiUserMsgPath dbUtiUserMsgPath,DBManager dbManager,WfLog wflogDto,WfLog wflogOldDto)
		throws Exception {
		ArrayList tmpList = (ArrayList)this.getUsersFromUserMsgPath(dbUtiUserMsgPath, wflogOldDto.getOperatorCode());
		for(int i=0;i<tmpList.size();i++){//删除操作员号为空的记录,不知道为什么总会取出一条空记录,因此特别处理
			UtiUserMsgPathDto utiUserMsgPathDto = (UtiUserMsgPathDto)(tmpList.toArray())[i];
			if(utiUserMsgPathDto.getReceiverCode() == null || utiUserMsgPathDto.getReceiverCode().trim().equals("")){
				tmpList.remove(i);
			}
		}
		if(tmpList.size()==0)
			return getUsersOfNode.getUsersOfNode(wflogDto, dbManager);
		else
			return tmpList;
	}
	
	/**
	 * Succes insured send.
	 * 
	 * @param policyNOs
	 *            the policy n os
	 */
	private void succesInsuredSend(ArrayList policyNOs){
		//过滤保单号
		ArrayList policylist=new ArrayList();
		Vector schema = null;
		String strSQL, strPolicyNo, strRiskCode,strTable;
		DbPool dbpool = new DbPool();
		for(int i=0;i<policyNOs.size();i++){
			String str=null;
			int beginIdx,endIdx;
			str=(String) policyNOs.get(i);
			if(str.indexOf(" 保单：")>-1){
				beginIdx=str.indexOf(" 保单：");
				endIdx=str.indexOf("</br>");
				policylist.add(str.substring(beginIdx+4, endIdx));
			}
		}
		// 打开数据库，开始事务
		try {
			dbpool.open(SysConfig.getProperty("DDCCDATASOURCE"));
			DBPrpDcode dbPrpDcode = new DBPrpDcode();
			strSQL = "select * from prpdcode where  CodeType = 'MsgPlatform' ORDER BY CodeCode";
			Vector<PrpDcodeSchema> blPrpDcodeschemas  = dbPrpDcode.findByConditions(dbpool,
					strSQL);
			if (blPrpDcodeschemas.size() < 1) {
				return;
			}
			String apiCode = blPrpDcodeschemas.get(0).getCodeEName();
			String loginName = blPrpDcodeschemas.get(1).getCodeEName();
			String loginPwd = blPrpDcodeschemas.get(2).getCodeEName();
			long smID = (long) (Math.random() * 100000000);
			SMsgService service = new SMsgServiceLocator();
			SMsg_PortType client = service.getSMsg();
			for (int i = 0; i < policylist.size(); i++) {
				strPolicyNo = (String) policylist.get(i);
				strRiskCode = strPolicyNo.substring(1, 5);
				//0301,0306,0307,0309和27险种手机号在PrpCinsured表，其他险种在PrpCinsuredNature表
				if(strRiskCode.substring(0, 2).equals("27")||strRiskCode.equals("0301")||strRiskCode.equals("0306")||strRiskCode.equals("0307")||strRiskCode.equals("0309")){
					strTable="PrpCinsuredNature";
				}
				else{
					strTable="PrpCinsured";
				}
				DBPrpDcodeRisk dBPrpDcodeRisk = new DBPrpDcodeRisk();
				strSQL = " SELECT * FROM PrpDcodeRisk WHERE CodeType = 'SuccessInsureSM' and RiskCode = '"
						+ strRiskCode
						+ "' and exists(select 1 from "+strTable+" where PolicyNo='"
						+ strPolicyNo
						+ "' and insuredflag = '2' and Mobile is not null)";
				schema = dBPrpDcodeRisk.findByConditions(dbpool, strSQL);// 需要发短信的险种，并且投保人手机号不为空
				if (schema.size() < 1) {
					continue;
				}
				strSQL = "SELECT * FROM PrpDcode WHERE  CodeType = 'SuccessInsureSM' and CodeCode in (select CodeCode from PrpDcodeRisk where CodeType = 'SuccessInsureSM' and RiskCode ='"
						+ strRiskCode + "') ORDER BY CodeCode";
				 blPrpDcodeschemas = dbPrpDcode.findByConditions(dbpool, strSQL);// 查询到该险种对应的短信内容，目前只是车与非车的区分
				 com.sinosoft.prpall.dbsvr.cb.DBPrpCmain dbPrpCmain = new com.sinosoft.prpall.dbsvr.cb.DBPrpCmain();
				schema = dbPrpCmain.findByPolicyNo(dbpool, strPolicyNo);
				PrpCmainSchema prpCmainSchema = (PrpCmainSchema) schema.get(0);
				String content = null;
				String[] mobiles = new String[1];
			    mobiles[0] =getMobile(dbpool, strRiskCode, strPolicyNo);
				if (blPrpDcodeschemas.size() == 5) {// 非车
					String[] startDate = prpCmainSchema.getStartDate().split(
							"-");
					DBPrpDrisk dbPrpDrisk = new DBPrpDrisk();
					schema = dbPrpDrisk.findByConditions(dbpool,
							"SELECT * FROM PrpDrisk WHERE RiskCode = '"
									+ strRiskCode + "'");
					PrpDriskSchema prpDrisk = (PrpDriskSchema) schema.get(0);
					content = blPrpDcodeschemas.get(0).getCodeCName()
							+ prpCmainSchema.getAppliName()
							+ blPrpDcodeschemas.get(1).getCodeCName()
							+ prpDrisk.getRiskCName()
							+ blPrpDcodeschemas.get(2).getCodeCName()
							+ strPolicyNo
							+ blPrpDcodeschemas.get(3).getCodeCName()
							+ startDate[0] + "年" + startDate[1] + "月"
							+ startDate[2] + "日"
							+ blPrpDcodeschemas.get(4).getCodeCName();
				} else if (blPrpDcodeschemas.size() == 4) {// 车
					strSQL = "SELECT * FROM PrpCitemCar WHERE  PolicyNo='"
							+ strPolicyNo + "'";
					DBPrpCitemCar dbPrpCitemCar = new DBPrpCitemCar();
					schema = dbPrpCitemCar.findByConditions(dbpool, strSQL);
					StringBuffer licenseNos = new StringBuffer("@");
					for (int j = 0; j < schema.size(); j++) {// 取得该保单号下的所有车辆信息
						PrpCitemCarSchema prpCitemCarSchema = (PrpCitemCarSchema) schema
								.get(j);
						licenseNos.append("，"
								+ prpCitemCarSchema.getLicenseNo());
					}
					String[] startDate = prpCmainSchema.getStartDate().split(
							"-");
					content = blPrpDcodeschemas.get(0).getCodeCName()
							+ licenseNos.toString().replace("@，", "")
							+ blPrpDcodeschemas.get(1).getCodeCName()
							+ strPolicyNo
							+ blPrpDcodeschemas.get(2).getCodeCName()
							+ startDate[0] + "年" + startDate[1] + "月"
							+ startDate[2] + "日"
							+ blPrpDcodeschemas.get(3).getCodeCName();
				}
				if (content != null) {
					content = new String(content.getBytes(), "gbk");
				    int result = -1;
				    	result = client.sendSM(apiCode, loginName, loginPwd,mobiles, content, smID);			    	
				    	}
			}
			
		} 
		catch (Exception exception)
		{			
				System.out.println("投保成功发送短信异常关闭数据源失败！");
				exception.printStackTrace();
		}
		 finally 
		 {
		
					try {
						if (dbpool != null) {
							dbpool.close();
						}

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			} 
		}


	//得到各个险种的手机号
	/**
	 * 獲取屬性the sinosoft mobile.
	 * 
	 * @param dbpool
	 *            the dbpool
	 * @param strRiskCode
	 *            the str risk code
	 * @param strPolicyNo
	 *            the str policy no
	 * @return 屬性the sinosoft mobile的值
	 * @throws SQLException
	 *             the sQL exception
	 * @throws NamingException
	 *             the naming exception
	 */
	private String getMobile(DbPool dbpool,String strRiskCode,String strPolicyNo) throws SQLException, NamingException{
		String mobile=null;
		if(strRiskCode.substring(0, 2).equals("27")||strRiskCode.equals("0301")||strRiskCode.equals("0306")||strRiskCode.equals("0307")||strRiskCode.equals("0309")){
			DBPrpCinsuredNature PrpCInquireNature=new DBPrpCinsuredNature();
			PrpCinsuredNatureSchema insureSchema=(PrpCinsuredNatureSchema)PrpCInquireNature.findByConditions(dbpool, "select * from PrpCinsuredNature where  PolicyNo='" +strPolicyNo+"' and insuredflag = '2'").get(0);
		    mobile =insureSchema.getMobile();
		}
		else{
			DBPrpCinsured PrpCInquire=new DBPrpCinsured();
			PrpCinsuredSchema insureSchema=(PrpCinsuredSchema)PrpCInquire.findByConditions(dbpool, "select * from PrpCinsured where  PolicyNo='" +strPolicyNo+"' and insuredflag = '2'").get(0);
		    mobile = insureSchema.getMobile();
		}
		return mobile;
	}
	
	//投保成功，异步发送短信
	/**
	 * Succes insured asynchronous send.
	 * 
	 * @param policyNOs
	 *            the policy n os
	 */
	public void succesInsuredAsynchronousSend(final ArrayList policyNOs) {
		new Thread() {//新建线程
			public void run() {
				succesInsuredSend(policyNOs);
			}
		}.start();
	}

//	public WfLogService getWfLogService() {
//		return wfLogService;
//	}
//
//	public void setWfLogService(WfLogService wfLogService) {
//		this.wfLogService = wfLogService;
//	}
	
	
	
}
