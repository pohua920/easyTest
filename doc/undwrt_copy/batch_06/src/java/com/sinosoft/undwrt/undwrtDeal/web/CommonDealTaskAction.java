package com.sinosoft.undwrt.undwrtDeal.web;

import ins.framework.common.QueryRule;
import ins.framework.web.Struts2Action;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.ccic.service.life.queryall.bean.Request;
import com.sinosoft.platform.dto.domain.PrpDuserDto;
import com.sinosoft.reins.common.service.facade.PDangerUnitService;
import com.sinosoft.reins.common.vo.PrpCDangerUnitVO;
import com.sinosoft.reins.common.vo.PrpPDangerUnitVO;
import com.sinosoft.reins.common.vo.PrpTDangerUnitVO;
import com.sinosoft.reins.interf.service.facade.BLDangerService;
import com.sinosoft.reins.interf.vo.CDangerVO;
import com.sinosoft.reins.interf.vo.PDangerVO;
import com.sinosoft.reins.interf.vo.TDangerVO;
import com.sinosoft.sysframework.common.util.StringUtils;
import com.sinosoft.undwrt.common.model.PrpDcompany;
import com.sinosoft.undwrt.common.model.PrpDuser;
import com.sinosoft.undwrt.common.service.facade.PrpDcompanyService;
import com.sinosoft.undwrt.common.service.facade.PrpDuserService;
import com.sinosoft.undwrt.common.util.Constants;
import com.sinosoft.undwrt.undwrtBase.model.SwfNode;
import com.sinosoft.undwrt.undwrtBase.model.SwfNodeId;
import com.sinosoft.undwrt.undwrtBase.model.SwfPath;
import com.sinosoft.undwrt.undwrtBase.model.SwfPathNew;
import com.sinosoft.undwrt.undwrtBase.model.UtiUwLevel;
import com.sinosoft.undwrt.undwrtBase.model.UwNotion;
import com.sinosoft.undwrt.undwrtBase.model.UwNotionId;
import com.sinosoft.undwrt.undwrtBase.model.WfLog;
import com.sinosoft.undwrt.undwrtBase.service.facade.SwfPathNewService;
import com.sinosoft.undwrt.undwrtBase.service.facade.SwfPathService;
import com.sinosoft.undwrt.undwrtBase.service.facade.UtiUwLevelService;
import com.sinosoft.undwrt.undwrtBase.service.facade.WfLogService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.CommonDangerInfoService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.CommonDealTaskService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.PrpFeedBackService;
import com.sinosoft.undwrt.undwrtDeal.vo.SendTaskVo;
import com.sinosoft.utility.error.UserException;





/**
 * 核保系統處理類.
 */
public class CommonDealTaskAction extends Struts2Action {
	
	/** 屬性處理類型. */
	private String DealType;
	
	/** 屬性工作流號. */
	private String FlowId;
	
	/** 屬性提交路徑. */
	private String SubmitDirection;
	
	/** 屬性工作流日誌接口. */
	private WfLogService wfLogService;
	
	/** 屬性 路徑接口. */
	private SwfPathNewService swfPathNewService;
	
	/** 屬性機構接口. */
	private PrpDcompanyService prpDcompanyService;
	
	/** 屬性核保系統提交任務服務接口. */
	private CommonDealTaskService commonDealTaskService;
	
	/** 屬性工作流路徑定義接口. */
	private SwfPathService swfPathService;
	
	/** 屬性危險單位信息服務接口. */
	private CommonDangerInfoService commonDangerInfoService;
	
	/** 屬性危險單位劃分接口. */
	private PDangerUnitService pDangerUnitService;
	
	/** 屬性危險單位信息接口. */
	private BLDangerService blDangerService;
	
	/** 屬性序號. */
	private String LogNo;
	
	/** 屬性跳轉頁面返回結果. */
	private String content;
	
    /** 屬性編輯類型. */
    private String editType;
	
	/** 屬性處理類型. */
	private String handType;
	
	/** 屬性處理人員代碼. */
	private String OperatorCode;
	
	/** 屬性合約號. */
	private String ContractNo;
	
	/** 屬性包號. */
	private String iPackageID;
	
	/** 屬性工作流狀態. */
	private String iFlowStatus;
	
	/** 屬性處理部門代碼. */
	private String iDeptCode;
	
	/** 屬性流入時間. */
	private String iFlowInTime;
	
	/** 屬性節點狀態. */
	private String iNodeStatus;
	
	/** 屬性節點名稱. */
	private String iNodeName;
	
    /** 屬性模板號. */
    private String ModelNo;
	
    /** 屬性節點號. */
    private String NodeNo;
	
    /** 屬性業務類型. */
    private String BusinessType;
	
    /** 屬性業務號. */
    private String BusinessNo;
	
    /** 屬性險種代碼. */
    private String iRiskCode;
	
    /** 屬性默認標誌位. */
    private String DefaultFlag;
	
    /** 屬性拆分危險單位. */
    private String riskUnitFlag;
	
    /** 屬性強制分保試算標志. */
    private String requiredReins;
	
    /** 屬性處理意見. */
    private String HandleText;
	
    /** 屬性機構代碼. */
    private String comCode;
    
    private String comLevel;
	
    /** 屬性標題. */
    private String handTitle;
	
    /** 屬性編輯標題. */
    private String editTitle;
	
    /** 屬性提交路徑. */
    private List colSubmitList;
	
    /** 屬性回退路徑. */
    private List colBackList;
	
	/** 屬性險類代碼. */
	private String classCode;
	
	/** 屬性選中的節點號. */
	private String selectNodeNo;
	
	/** 核保級別設定接口*/
	private UtiUwLevelService utiUwLevelService;
	
	private PrpFeedBackService prpFeedBackService;
	/** 危險單位號*/
	private String dangerNo;
	private PrpDuserService prpDuserService;
	
	/**  改派任務voList*/
	private List<SendTaskVo> sendTaskVoList = new ArrayList<SendTaskVo>();
	
	
	/**
	 * 处理任务.
	 * 
	 * @return 頁面跳轉結果
	 * @throws UserException
	 *             自定義異常
	 * @throws Exception
	 *             異常
	 */
	public String dealWithTask() throws UserException, Exception
	{
		String forward = "";
		//处理业务类型
		HttpServletRequest req = this.getRequest();
		String handTitle = (String) this.getSession(false).getAttribute("handTitle");
		String logMessage = handTitle+getText("undwrt.action.commonDealSubmit.task");
		String logModule = handTitle+getText("undwrt.action.commonCheckTask.dealWith");
		PrpDuserDto prpDuserDto = (PrpDuserDto) this.getSession(true).getAttribute("user");
		//UICommonDangerInfoAction uiCommonDangerInfoAction = new UICommonDangerInfoAction();
		
		//任务类型 save－保存审批任务 submit－提交审批任务
		try
		{   
			
			//更新或保存当前的危险单位主信息
			if (DealType.equals("saveDangerItem"))
			{   
		    this.saveDangerDetailByDanger(req);  
			forward = "save";
			content= getText("undwrt.action.commonDealTask.riskEstSaveSuccess");
			}
			if (DealType.equals("saveEndorseDangerItem"))
			{   
			//add by wangshizhu 20070228 拆分危险单位程序合并
			this.saveDangerDetailByDanger(req);  
			forward = "save";
			content = getText("undwrt.action.commonDealTask.riskEstSaveSuccess") ;
			}
			
			String flowId =(FlowId!= null ? FlowId.trim() : "");
			HttpSession session = this.getSession();
			//HashMap taskSubmitHashTable = (HashMap)session.getAttribute("TaskSubmitHashTable");
			//TaskDealVo taskDealDto = (TaskDealVo)taskSubmitHashTable.get(flowId);
			HashMap taskDealDto=null;//临时加的，最后需要放开上面两句，删除这行
			if(taskDealDto != null)
			{
			session.setAttribute("taskDealDto", taskDealDto);
			return "asubmittedError";
			}
			
			if (DealType.equals("save"))    //暂存
			{
				
			this.saveTask();
			forward = "save";
			content = "任务保存成功！";
			//LogUtils.info(prpDuserDto, logModule, prpDuserDto.getUserName() + " "+ logMessage+"保存成功：工作流" + req.getParameter("FlowId") + " 序号：" + req.getParameter("LogNo"));
			}
			else if (DealType.equals("cancel"))    //放弃任务
			{
			String nodeStatus = "";
			WfLog wfLogDto = null;
			int logNo = 0;
			String riskCode = "";
			if (LogNo!= null
			&& !LogNo.equals("")) {
			logNo = Integer.parseInt(new DecimalFormat("#").format(Double
			.parseDouble(LogNo)));
			}
			QueryRule queryRule=QueryRule.getInstance();
			queryRule.addEqual("id.flowId", flowId);
			queryRule.addEqual("id.logNo", logNo);
			wfLogDto = wfLogService.findByPrimaryKey(queryRule);
			nodeStatus = wfLogDto.getNodeStatus();
			if ((nodeStatus.equals("2")||nodeStatus.equals("3")) && DealType.equals("cancel")) {
			wfLogDto.setOperatorCode("");
			wfLogDto.setOperatorName("");
			wfLogDto.setDeptCode("");
			wfLogDto.setDeptName("");
			wfLogDto.setNodeStatus("1");
			wfLogService.update(wfLogDto);
			}
			forward = "cancel";
			content = getText("undwrt.action.batchTaskSubmit.taskDropSuccess");
			}
			else if(DealType.equals("send1")){ //改派任务
				
				//先拿到登录人和机构
				String userCode = (String) session.getAttribute("myUserCode");
				String comCode = (String) session.getAttribute("myComCode");
				
				int logNo = Integer.parseInt(new DecimalFormat("#").format(Double.parseDouble(LogNo)));
				
				QueryRule queryRule = QueryRule.getInstance();
				queryRule.addEqual("id.flowId", flowId);
				queryRule.addEqual("id.logNo", logNo);
				WfLog wfLog = wfLogService.findByPrimaryKey(queryRule);
				
				
				//1.得到核保级别
				queryRule = QueryRule.getInstance();
				queryRule.getRuleList().clear();
				queryRule.getQueryRuleList().clear();
				queryRule.addEqual("id.userCode", userCode);
				queryRule.addEqual("id.comCode", comCode);
				queryRule.addEqual("id.validStatus", "1");
				queryRule.addEqual("id.modelNo", wfLog.getModelNo());
				queryRule.addEqual("id.uwType", "T");
				List<UtiUwLevel> utiUwLevelList = new ArrayList<UtiUwLevel>();
				utiUwLevelList = utiUwLevelService.getUtiUwLevelList(queryRule);
				if (null != utiUwLevelList && utiUwLevelList.size() > 0) {
					 NodeNo = String.valueOf(utiUwLevelList.get(0).getId().getNodeNo());
				}
				//2.得到机构级别
				queryRule.getRuleList().clear();
				queryRule.getQueryRuleList().clear();
				queryRule.addEqual("comCode", comCode);
				PrpDcompany prpDcompany=prpDcompanyService.findByPrimaryKey(queryRule);
				if(null != prpDcompany){
					comLevel = prpDcompany.getComLevel();
				}
				//3.得到所有机构
				List<PrpDcompany> prpDcompanyList = new ArrayList<PrpDcompany>();
				queryRule.getRuleList().clear();
				queryRule.getQueryRuleList().clear();
				queryRule.addLessEqual("comLevel", comLevel);
				prpDcompanyList=prpDcompanyService.findByConditions(queryRule);
				List<String> comList=new  ArrayList<String>();
				if(null!=prpDcompanyList && prpDcompanyList.size()>0){
					for(int i=0;i<prpDcompanyList.size();i++){
						comList.add(prpDcompanyList.get(i).getComCode());
					}
				}
				//4.最终得到满足要求核保人员
				List<UtiUwLevel> utiUwLevelList1 = new ArrayList<UtiUwLevel>();
				queryRule.getRuleList().clear();
				queryRule.getQueryRuleList().clear();
				queryRule.addEqual("id.validStatus", "1");
				queryRule.addEqual("id.modelNo", wfLog.getModelNo());
				queryRule.addGreaterEqual("id.nodeNo", Integer.parseInt(NodeNo));
				queryRule.addIn("id.comCode", comList);
				utiUwLevelList1 = utiUwLevelService.getUtiUwLevelList(queryRule);
				if (null != utiUwLevelList1 && utiUwLevelList1.size() > 0) {
					for(int i=0;i<utiUwLevelList1.size();i++){
						SendTaskVo sendTaskVo= new SendTaskVo();
						//核保人代码
						sendTaskVo.setUserCode(utiUwLevelList1.get(i).getId().getUserCode());
						//核保人名称
						PrpDuser prpDuser=prpDuserService.findByPrimaryKey(utiUwLevelList1.get(i).getId().getUserCode());;
						if(null != prpDuser){
							sendTaskVo.setUserName(prpDuser.getUserName());
						}
						//机构代码
						sendTaskVo.setComCode(utiUwLevelList1.get(i).getId().getComCode());
						//机构名称
						queryRule.getRuleList().clear();
						queryRule.getQueryRuleList().clear();
						queryRule.addEqual("comCode", utiUwLevelList1.get(i).getId().getComCode());
						PrpDcompany prpDCompany=prpDcompanyService.findByPrimaryKey(queryRule);
						if(null != prpDCompany){
							sendTaskVo.setComName(prpDCompany.getComCName());
						}
						//核保人级别
						sendTaskVo.setNodeNo(Integer.toString(utiUwLevelList1.get(i).getId().getNodeNo()-1));
						sendTaskVoList.add(sendTaskVo);
					}
				}
				forward = "send1";
			}
			else if(DealType.equals("send2")){//修改任务处理人信息
				HttpServletRequest request = ServletActionContext.getRequest();
				String userComCode=request.getParameter("operatorCode");

					int a=userComCode.indexOf("*");
					//机构代码
					String cCode=userComCode.substring(0, a);
					//员工代码
					String uCode=userComCode.substring(a+1);
					//员工名称
					PrpDuser prpDuser=prpDuserService.findByPrimaryKey(uCode);
					//机构名称
					QueryRule queryRule = QueryRule.getInstance();
					queryRule.getRuleList().clear();
					queryRule.getQueryRuleList().clear();
					queryRule.addEqual("comCode", cCode);
					PrpDcompany prpDcompany=prpDcompanyService.findByPrimaryKey(queryRule);
					
				String nodeStatus = "";
				WfLog wfLogDto = null;
				String flowid=(String)session.getAttribute("FlowIDsend");
				int logno=Integer.parseInt((String)session.getAttribute("LogNosend"));
				queryRule = QueryRule.getInstance();
				queryRule.addEqual("id.flowId", flowid);
				queryRule.addEqual("id.logNo", logno);
				wfLogDto = wfLogService.findByPrimaryKey(queryRule);
				nodeStatus = wfLogDto.getNodeStatus();
				if(("2".equals(nodeStatus)||"3".equals(nodeStatus))&&DealType.equals("send2")){
					wfLogDto.setOperatorCode(uCode);
					wfLogDto.setOperatorName(prpDuser.getUserName());
					wfLogDto.setDeptCode(cCode);
					wfLogDto.setComName(prpDcompany.getComCName());
					wfLogService.update(wfLogDto);
				}
				forward = "send2";
				content = getText("undwrt.action.batchTaskSubmit.taskSendSuccess");
			}
			else if (DealType.equals("submit")) //提交任务
			{
			this.submitTaskBefore(ModelNo,NodeNo,BusinessType,BusinessNo,DefaultFlag,comCode,FlowId,LogNo,iRiskCode,riskUnitFlag,requiredReins,HandleText);
			
			forward = SubmitDirection;
			}
			else if(DealType.equals("refuse")) //拒保
			{
			//uiCommonDealTaskAction.submitRefuse(req);
			forward = "success";
			}
			if (DealType.equals("delete"))
			{
				prpFeedBackService.deletePrpDangerUnitAndItem(BusinessType, BusinessNo, dangerNo);
			}
		}
		catch (Exception e)
		{  
		e.printStackTrace();
		throw e;
		}
		return forward;
	}

/**
 * 提交任務預處理.
 * 
 * @param iModelNo
 *           模板號
 * @param iNodeNo
 *            節點號
 * @param BusinessType
 *            業務類型
 * @param iBusinessNo
 *            業務號
 * @param DefaultFlag
 *            默認標誌位
 * @param iComCode
 *            機構代碼
 * @param iFlowID
 *            工作流號
 * @param iLogNo
 *            序號
 * @param iRiskCode
 *            險種代碼
 * @param riskUnitFlag
 *            拆分危險單位標誌
 * @param requiredReins
 *            強制分保試算標誌
 * @param HandleText
 *            核保處理意見
 * @throws Exception
 *             異常
 */
public void submitTaskBefore(String iModelNo,String iNodeNo,String BusinessType,String iBusinessNo,String DefaultFlag,String iComCode,String iFlowID,String iLogNo,String iRiskCode,String riskUnitFlag,String requiredReins,String HandleText) throws Exception {

	HttpSession session=this.getSession();
    // 保存任务
    int logNO=Integer.parseInt(iLogNo);
    this.saveTask(iRiskCode,riskUnitFlag,requiredReins,HandleText,iFlowID,logNO);
    try {
        // 查询提交路径
//        int modelNo = Integer.parseInt((String) req.getParameter("ModelNo"));
//        int nodeNo = Integer.parseInt((String) req.getParameter("NodeNo"));
//        String businessType = (String) req.getParameter("BusinessType");
//        String businessNo = (String) req.getParameter("BusinessNo");
//        String defaultFlag = (String) req.getParameter("DefaultFlag");
//        String comCode = (String) req.getSession(false).getAttribute("myComCode");
//
//        String flowId = req.getParameter("FlowId");
//        int logNo = Integer.parseInt(req.getParameter("LogNo"));
        
        
        WfLog wfLogDto = new WfLog();
        QueryRule queryRule=QueryRule.getInstance();
        queryRule.addEqual("id.flowId",iFlowID);
        queryRule.addEqual("id.logNo", Integer.parseInt(iLogNo));
        wfLogDto = wfLogService.findByPrimaryKey(queryRule);
        
        /**
         * 规则引擎的路径信息保存在swfpathnew表里面
         * wflog的resultcode=2表示是ILog返回的人工核保
         */
        if("2".equals(wfLogDto.getResultCode())){
        	String[] arrNodeNo;
        	String[] arrNodeName;
        	String strRiskCode = wfLogDto.getRiskCode();//险种代码
        	String strComCode = wfLogDto.getComCode();//归属机构代码
        	SwfPathNew swfPathNewDto = new SwfPathNew();
        	
    		boolean isFind = false;
    		while(!isFind){
    	        QueryRule queryRule1=QueryRule.getInstance();
    	        queryRule.addEqual("id.riskCode",iRiskCode);
    	        queryRule.addEqual("id.comCode", iComCode);
    			swfPathNewDto = swfPathNewService.findByPrimaryKey(queryRule1);
    			//如果没有找到就查找上级
    			if(swfPathNewDto != null){
    				isFind = true;
    			}else{
    				PrpDcompany prpDcompanyDto = new PrpDcompany();
        	        QueryRule queryRule2=QueryRule.getInstance();
        	        queryRule2.addEqual("comCode",iComCode);
    			    prpDcompanyDto = prpDcompanyService.findByPrimaryKey(queryRule2);
    			    //查到总公司还没有数据就抛出
    			    if(!prpDcompanyDto.getComCode().equals(prpDcompanyDto.getUpperClaimComCode())){
    			    	iComCode = prpDcompanyDto.getUpperClaimComCode();
    			    }else{
    			    	throw new UserException(-98, -9999, this.getClass().getName() + ".getNodeNo()", getText("undwrt.action.batchTask.noRoute"));
    			    }
    			}
    		}
    		arrNodeNo = swfPathNewDto.getPath().split(",");
			arrNodeName = swfPathNewDto.getPathDesc().split(",");
        	
			// 提交路径列表  -1是为了去掉审核通过节点
			for(int i=0;i<arrNodeNo.length-1;i++){
				if(Integer.parseInt(iNodeNo)<Integer.parseInt(arrNodeNo[i])){
					SwfPath wfPathDto = new SwfPath();
					SwfNode swfNodeByfkPathNode2 = new SwfNode();
					SwfNodeId swfNodeId = new SwfNodeId();
					//wfPathDto.setEndNodeNo(Integer.parseInt(arrNodeNo[i])); 
					swfNodeId.setNodeNo(Integer.parseInt(arrNodeNo[i]));
					swfNodeByfkPathNode2.setId(swfNodeId);
					wfPathDto.setSwfNodeByfkPathNode2(swfNodeByfkPathNode2);
					wfPathDto.setEndNodeName(arrNodeName[i]);
					colSubmitList.add(wfPathDto);
					break;
				}			
			}
			
			//add by yanglibo 20100114 begin reason: for task-2565 固定编码，暂不进行配置处理，配置处理较复杂(增加从二级C直接提交到二级A的路径)。
			classCode = wfLogDto.getClassCode();//险类代码
			if(Integer.parseInt(iNodeNo)==5){
				if(!("A".equals(classCode)||"B".equals(classCode))){
					SwfPath wfPathKuaJiDto = new SwfPath();
					SwfNode swfNodeByfkPathNode2 = new SwfNode();
					SwfNodeId swfNodeId = new SwfNodeId();
					//wfPathDto.setEndNodeNo(Integer.parseInt(arrNodeNo[i])); 
					swfNodeId.setNodeNo(7);
					swfNodeByfkPathNode2.setId(swfNodeId);
					wfPathKuaJiDto.setSwfNodeByfkPathNode2(swfNodeByfkPathNode2);
					wfPathKuaJiDto.setEndNodeName(getText("undwrt.action.commonDealTask.underwriteSecondary"));
					colSubmitList.add(wfPathKuaJiDto);
				}
			}
			//add by yanglibo 20100114 end
			
			// 回退路径
			for(int i=(arrNodeNo.length-1);i>=0;i--){
				if(Integer.parseInt(iNodeNo)>Integer.parseInt(arrNodeNo[i])){
					WfLog wfPathDto = new WfLog();
					wfPathDto.setNodeNo(Integer.parseInt(arrNodeNo[i]));
					wfPathDto.setNodeName(arrNodeName[i]);
					colBackList.add(wfPathDto);
				}			
			}
        }else {
			// 提交路径列表
        	int modelNo=Integer.parseInt(iModelNo);
        	int nodeNo=Integer.parseInt(iNodeNo);
        	
        	//核保提交上級到審核人員級別的上一級 begin
        	PrpDuserDto prpDuser = (PrpDuserDto) session.getAttribute("user");
        	queryRule = QueryRule.getInstance();
        	queryRule.addEqual("id.uwType", Constants.UWTYPE_T);
    		queryRule.addEqual("id.validStatus", "1");
    		queryRule.addEqual("id.userCode", prpDuser.getUserCode());
    		queryRule.addEqual("id.comCode", prpDuser.getLoginComCode());
    		queryRule.addEqual("id.modelNo", modelNo);
    		Collection<UtiUwLevel> utiUwLevelCollection = utiUwLevelService.getUtiUwLevelList(queryRule);
    		
    		if(null != utiUwLevelCollection && utiUwLevelCollection.iterator().hasNext()){
    			nodeNo = utiUwLevelCollection.iterator().next().getId().getNodeNo();
    		}
    		if("SubmitSuperior".equals(SubmitDirection))
    		{
    			colSubmitList =swfPathService.getPathes(modelNo, nodeNo, nodeNo + 1, BusinessType, iBusinessNo, DefaultFlag,iComCode);
    		}
			//核保提交上級到審核人員級別的上一級 end
			
			// 回退路径
			int logNo=Integer.parseInt(iLogNo);
			colBackList = (List) wfLogService.getBackList(iFlowID,logNo, nodeNo);
			
		}
        
        session.setAttribute("submitList", colSubmitList);
        session.setAttribute("submitBackList",colBackList);
        
    } catch (Exception e) {
        e.printStackTrace();
        throw e;
    }
}
	
	/**
	 * 保存任務.
	 * 
	 * @param riskCode
	 *            險種代碼
	 * @param riskUnitFlag
	 *            拆分危險單位標誌
	 * @param requiredReins
	 *            強制分保試算標誌
	 * @param handleText
	 *            核保處理意見
	 * @param flowId
	 *            工作流號
	 * @param logNo
	 *            序號
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	private void saveTask(String riskCode, String riskUnitFlag,
					String requiredReins, String handleText, String flowId,
					int logNo) throws SQLException, Exception {
			//        if (riskUnitFlag.equals("1")) // 需要拆分危险单位的险种处理
			//        {
			//            this.saveDangerDetail(req); // 保存默认的一条危险单位信息(在点暂存时调用)
			//        } else if (requiredReins.equals("1")) // 不拆分危险单位但强制分保试算的险种
			//        {
			//            this.saveDangerDetailNoDangerUnit(req);
			//        }
			        // 保存审批意见，更新工作流日志表中处理部门、处理人员代码及名称、处理时间、节点状态(3)。
			         HandleText = StringUtils.replace(HandleText,
			                "'", "''");
			        if (HandleText == null) {
			            HandleText = "";
			        }
			        UwNotionId uwId=new UwNotionId();
			        uwId.setFlowId(flowId);
			        uwId.setLogNo(logNo);
			        UwNotion uwNotionDto = new UwNotion();
			        uwNotionDto.setId(uwId);
			        uwNotionDto.setHandleText(HandleText);
			
			        PrpDuserDto prpDuserDto = (PrpDuserDto) this.getSession(true)
			                .getAttribute("user");
			        // 将HandleText拆分成多条 变成多个uwNotionDto对象批量插入uwNotion表
			        commonDealTaskService.saveTask(uwNotionDto, prpDuserDto);
	
}
	
	/**
	 * 保存任務.
	 * 
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	private void saveTask() throws SQLException, Exception {
	//        if (riskUnitFlag.equals("1")) // 需要拆分危险单位的险种处理
	//        {
	//            this.saveDangerDetail(req); // 保存默认的一条危险单位信息(在点暂存时调用)
	//        } else if (requiredReins.equals("1")) // 不拆分危险单位但强制分保试算的险种
	//        {
	//            this.saveDangerDetailNoDangerUnit(req);
	//        }
	        // 保存审批意见，更新工作流日志表中处理部门、处理人员代码及名称、处理时间、节点状态(3)。
	         HandleText = StringUtils.replace(HandleText,
	                "'", "''");
	        if (HandleText == null) {
	            HandleText = "";
	        }
	        UwNotionId uwId=new UwNotionId();
	        uwId.setFlowId(FlowId);
	        uwId.setLogNo(Integer.parseInt(LogNo));
	        UwNotion uwNotionDto = new UwNotion();
	        uwNotionDto.setId(uwId);
	        uwNotionDto.setHandleText(HandleText);
	
	        PrpDuserDto prpDuserDto = (PrpDuserDto) this.getSession(true)
	                .getAttribute("user");
	        // 将HandleText拆分成多条 变成多个uwNotionDto对象批量插入uwNotion表
	        commonDealTaskService.saveTask(uwNotionDto, prpDuserDto);

}
	  
  	/**
	 * 保存危險單位信息.
	 * 
	 * @param req
	 *            請求對象
	 * @throws Exception
	 *             異常
	 */
  	public void saveDangerDetailByDanger(HttpServletRequest req)
	    throws Exception {
	Collection dangerUnitDtoList = new ArrayList();
	Collection dangerItemDtoList = new ArrayList();
	Collection dangerTotDtoList = new ArrayList();
	Collection dangerPlanDtoList = new ArrayList();
	Collection dangerCoinsDtoList = new ArrayList();
	Collection dangerRiskDtoList = new ArrayList();
	String businessType = req.getParameter("hiBusinessType");
	String businessNo = req.getParameter("businessNo");
	String dangerNo = req.getParameter("hiDangerNo");
	String policyNo = req.getParameter("policyNo");
	String riskCode = req.getParameter("riskCode");
	double sumAmount = 0d;
	double chgAmount = 0d;

	dangerUnitDtoList = commonDangerInfoService.getDangerUnit(req);
	dangerItemDtoList = commonDangerInfoService.getDangerItemList(req);
	dangerTotDtoList = commonDangerInfoService.getDangerTotList(businessType, businessNo,
		dangerUnitDtoList);
	dangerPlanDtoList = commonDangerInfoService.getDangerPlanList(businessType, businessNo,
		dangerUnitDtoList);
	dangerCoinsDtoList = commonDangerInfoService.getDangerCoinsList(businessType, businessNo,
		policyNo, dangerUnitDtoList);
	dangerRiskDtoList = (Collection)blDangerService.getDangerRiskList(businessType,
		businessNo, dangerNo);

	if (businessType.equals("T")) // ???
	{
	    TDangerVO tDangerDto = new TDangerVO();
	    tDangerDto
		    .setPrpTDangerUnitVO((PrpTDangerUnitVO) dangerUnitDtoList
			    .iterator().next());
	    tDangerDto.setPrpTDangerItemVOList(dangerItemDtoList);
	    tDangerDto.setPrpTDangerTotVOList(dangerTotDtoList);
	    tDangerDto.setPrpTDangerPlanVOList(dangerPlanDtoList);
	    tDangerDto.setPrpTDangerCoinsVOList(dangerCoinsDtoList);
	    tDangerDto.setPrpTDangerRiskVOList(dangerRiskDtoList);

	    blDangerService.saveTDangerUnit(tDangerDto);
	} else if (businessType.equals("P")) // ??
	{
	    CDangerVO cDangerDto = new CDangerVO();
	    cDangerDto
		    .setPrpCDangerUnitVO((PrpCDangerUnitVO ) dangerUnitDtoList
			    .iterator().next());
	    cDangerDto.setPrpCDangerItemVOList(dangerItemDtoList);
	    cDangerDto.setPrpCDangerTotVOList(dangerTotDtoList);
	    cDangerDto.setPrpCDangerPlanVOList(dangerPlanDtoList);
	    cDangerDto.setPrpCDangerCoinsVOList(dangerCoinsDtoList);
	    cDangerDto.setPrpCDangerRiskVOList(dangerRiskDtoList);

	    blDangerService.saveCDangerUnit(cDangerDto);
	} else if (businessType.equals("E")) // ??
	{
	    PDangerVO pDangerDto = new PDangerVO();
	    pDangerDto
		    .setPrpPDangerUnitVO((PrpPDangerUnitVO) dangerUnitDtoList
			    .iterator().next());
	    pDangerDto.setPrpPDangerItemVOList(dangerItemDtoList);
	    pDangerDto.setPrpPDangerTotVOList(dangerTotDtoList);
	    pDangerDto.setPrpPDangerPlanVOList(dangerPlanDtoList);
	    pDangerDto.setPrpPDangerCoinsVOList(dangerCoinsDtoList);
	    pDangerDto.setPrpPDangerRiskVOList(dangerRiskDtoList);

	    blDangerService.savePDangerUnit(pDangerDto);
	}
  }
	
	
	
	
	

	/**
	 * 獲取屬性處理類型.
	 * 
	 * @return 屬性處理類型的值
	 */
	public String getDealType() {
		return DealType;
	}



	/**
	 * 設置屬性處理類型.
	 * 
	 * @param dealType
	 *            待設置的處理類型的值
	 */
	public void setDealType(String dealType) {
		this.DealType = dealType;
	}




	/**
	 * 獲取屬性提交路徑.
	 * 
	 * @return 屬性提交路徑的值
	 */
	public String getSubmitDirection() {
		return SubmitDirection;
	}



	/**
	 * 設置屬性提交路徑.
	 * 
	 * @param submitDirection
	 *            待設置的提交路徑的值
	 */
	public void setSubmitDirection(String submitDirection) {
		this.SubmitDirection = submitDirection;
	}



	/**
	 * 獲取屬性工作流日誌接口.
	 * 
	 * @return 屬性工作流日誌接口的值
	 */
	public WfLogService getWfLogService() {
		return wfLogService;
	}



	/**
	 * 設置屬性工作流日誌接口.
	 * 
	 * @param wfLogService
	 *            待設置的工作流日誌接口的值
	 */
	public void setWfLogService(WfLogService wfLogService) {
		this.wfLogService = wfLogService;
	}
	
	/**
	 * 獲取屬性序號.
	 * 
	 * @return 屬性序號的值
	 */
	public String getLogNo() {
		return LogNo;
	}

	/**
	 * 設置屬性序號.
	 * 
	 * @param logNo
	 *            待設置的序號的值
	 */
	public void setLogNo(String logNo) {
		this.LogNo = logNo;
	}

	/**
	 * 獲取屬性業務號.
	 * 
	 * @return 屬性業務號的值
	 */
	public String getBusinessNo() {
		return BusinessNo;
	}

	/**
	 * 設置屬性業務號.
	 * 
	 * @param businessNo
	 *            待設置的業務號的值
	 */
	public void setBusinessNo(String businessNo) {
		this.BusinessNo = businessNo;
	}

	/**
	 * 獲取屬性跳轉頁面返回結果.
	 * 
	 * @return 屬性跳轉頁面返回結果的值
	 */
	public String getContent() {
		return content;
	}



	/**
	 * 設置屬性跳轉頁面返回結果.
	 * 
	 * @param content
	 *            待設置的跳轉頁面返回結果的值
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 獲取屬性編輯類型.
	 * 
	 * @return 屬性編輯類型的值
	 */
	public String getEditType() {
		return editType;
	}

	/**
	 * 設置屬性編輯類型.
	 * 
	 * @param editType
	 *            待設置的編輯類型的值
	 */
	public void setEditType(String editType) {
		this.editType = editType;
	}

	/**
	 * 獲取屬性處理類型.
	 * 
	 * @return 屬性處理類型的值
	 */
	public String getHandType() {
		return handType;
	}

	/**
	 * 設置屬性處理類型.
	 * 
	 * @param handType
	 *            待設置的處理類型的值
	 */
	public void setHandType(String handType) {
		this.handType = handType;
	}


	/**
	 * 獲取屬性工作流號.
	 * 
	 * @return 屬性工作流號的值
	 */
	public String getFlowId() {
		return FlowId;
	}

	/**
	 * 設置屬性工作流號.
	 * 
	 * @param flowId
	 *            待設置的工作流號的值
	 */
	public void setFlowId(String flowId) {
		FlowId = flowId;
	}

	/**
	 * 獲取屬性模板號.
	 * 
	 * @return 屬性模板號的值
	 */
	public String getModelNo() {
		return ModelNo;
	}

	/**
	 * 設置屬性模板號.
	 * 
	 * @param modelNo
	 *            待設置的模板號的值
	 */
	public void setModelNo(String modelNo) {
		ModelNo = modelNo;
	}

	/**
	 * 獲取屬性節點號.
	 * 
	 * @return 屬性節點號的值
	 */
	public String getNodeNo() {
		return NodeNo;
	}

	/**
	 * 設置屬性節點號.
	 * 
	 * @param nodeNo
	 *            待設置的節點號的值
	 */
	public void setNodeNo(String nodeNo) {
		NodeNo = nodeNo;
	}

	/**
	 * 獲取屬性業務類型.
	 * 
	 * @return 屬性業務類型的值
	 */
	public String getBusinessType() {
		return BusinessType;
	}

	/**
	 * 設置屬性業務類型.
	 * 
	 * @param businessType
	 *            待設置的業務類型的值
	 */
	public void setBusinessType(String businessType) {
		this.BusinessType = businessType;
	}
	
	/**
	 * 獲取屬性險種代碼.
	 * 
	 * @return 屬性險種代碼的值
	 */
	public String getiRiskCode() {
		return iRiskCode;
	}

	/**
	 * 設置屬性險種代碼.
	 * 
	 * @param iRiskCode
	 *            待設置的險種代碼的值
	 */
	public void setiRiskCode(String iRiskCode) {
		this.iRiskCode = iRiskCode;
	}

	/**
	 * 獲取屬性默認標誌位.
	 * 
	 * @return 屬性默認標誌位的值
	 */
	public String getDefaultFlag() {
		return DefaultFlag;
	}

	/**
	 * 設置屬性默認標誌位.
	 * 
	 * @param defaultFlag
	 *            待設置的默認標誌位的值
	 */
	public void setDefaultFlag(String defaultFlag) {
		this.DefaultFlag = defaultFlag;
	}

	/**
	 * 獲取屬性拆分危險單位.
	 * 
	 * @return 屬性拆分危險單位的值
	 */
	public String getRiskUnitFlag() {
		return riskUnitFlag;
	}

	/**
	 * 設置屬性拆分危險單位.
	 * 
	 * @param riskUnitFlag
	 *            待設置的拆分危險單位的值
	 */
	public void setRiskUnitFlag(String riskUnitFlag) {
		this.riskUnitFlag = riskUnitFlag;
	}

	/**
	 * 獲取屬性強制分保試算標志.
	 * 
	 * @return 屬性強制分保試算標志的值
	 */
	public String getRequiredReins() {
		return requiredReins;
	}

	/**
	 * 設置屬性強制分保試算標志.
	 * 
	 * @param requiredReins
	 *            待設置的強制分保試算標志的值
	 */
	public void setRequiredReins(String requiredReins) {
		this.requiredReins = requiredReins;
	}

	/**
	 * 獲取屬性處理意見.
	 * 
	 * @return 屬性處理意見的值
	 */
	public String getHandleText() {
		return HandleText;
	}

	/**
	 * 設置屬性處理意見.
	 * 
	 * @param handleText
	 *            待設置的處理意見的值
	 */
	public void setHandleText(String handleText) {
		this.HandleText = handleText;
	}
	
	/**
	 * 獲取屬性機構代碼.
	 * 
	 * @return 屬性機構代碼的值
	 */
	public String getComCode() {
		return comCode;
	}

	/**
	 * 設置屬性機構代碼.
	 * 
	 * @param comCode
	 *            待設置的機構代碼的值
	 */
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	public String getComLevel() {
		return comLevel;
	}

	public void setComLevel(String comLevel) {
		this.comLevel = comLevel;
	}

	/**
	 * 獲取屬性 路徑接口.
	 * 
	 * @return 屬性 路徑接口的值
	 */
	public SwfPathNewService getSwfPathNewService() {
		return swfPathNewService;
	}

	/**
	 * 設置屬性 路徑接口.
	 * 
	 * @param swfPathNewService
	 *            待設置的 路徑接口的值
	 */
	public void setSwfPathNewService(SwfPathNewService swfPathNewService) {
		this.swfPathNewService = swfPathNewService;
	}

	/**
	 * 獲取屬性機構接口.
	 * 
	 * @return 屬性機構接口的值
	 */
	public PrpDcompanyService getPrpDcompanyService() {
		return prpDcompanyService;
	}

	/**
	 * 設置屬性機構接口.
	 * 
	 * @param prpDcompanyService
	 *            待設置的機構接口的值
	 */
	public void setPrpDcompanyService(PrpDcompanyService prpDcompanyService) {
		this.prpDcompanyService = prpDcompanyService;
	}

	/**
	 * 獲取屬性工作流路徑定義接口.
	 * 
	 * @return 屬性工作流路徑定義接口的值
	 */
	public SwfPathService getSwfPathService() {
		return swfPathService;
	}

	/**
	 * 設置屬性工作流路徑定義接口.
	 * 
	 * @param swfPathService
	 *            待設置的工作流路徑定義接口的值
	 */
	public void setSwfPathService(SwfPathService swfPathService) {
		this.swfPathService = swfPathService;
	}
	
	/**
	 * 獲取屬性核保系統提交任務服務接口.
	 * 
	 * @return 屬性核保系統提交任務服務接口的值
	 */
	public CommonDealTaskService getCommonDealTaskService() {
		return commonDealTaskService;
	}

	/**
	 * 設置屬性核保系統提交任務服務接口.
	 * 
	 * @param commonDealTaskService
	 *            待設置的核保系統提交任務服務接口的值
	 */
	public void setCommonDealTaskService(CommonDealTaskService commonDealTaskService) {
		this.commonDealTaskService = commonDealTaskService;
	}

	/**
	 * 獲取屬性標題.
	 * 
	 * @return 屬性標題的值
	 */
	public String getHandTitle() {
		return handTitle;
	}

	/**
	 * 設置屬性標題.
	 * 
	 * @param handTitle
	 *            待設置的標題的值
	 */
	public void setHandTitle(String handTitle) {
		this.handTitle = handTitle;
	}

	/**
	 * 獲取屬性編輯標題.
	 * 
	 * @return 屬性編輯標題的值
	 */
	public String getEditTitle() {
		return editTitle;
	}

	/**
	 * 設置屬性編輯標題.
	 * 
	 * @param editTitle
	 *            待設置的編輯標題的值
	 */
	public void setEditTitle(String editTitle) {
		this.editTitle = editTitle;
	}

	/**
	 * 獲取屬性提交路徑.
	 * 
	 * @return 屬性提交路徑的值
	 */
	public List getColSubmitList() {
		return colSubmitList;
	}

	/**
	 * 設置屬性提交路徑.
	 * 
	 * @param colSubmitList
	 *            待設置的提交路徑的值
	 */
	public void setColSubmitList(List colSubmitList) {
		this.colSubmitList = colSubmitList;
	}

	/**
	 * 獲取屬性回退路徑.
	 * 
	 * @return 屬性回退路徑的值
	 */
	public List getColBackList() {
		return colBackList;
	}

	/**
	 * 設置屬性回退路徑.
	 * 
	 * @param colBackList
	 *            待設置的回退路徑的值
	 */
	public void setColBackList(List colBackList) {
		this.colBackList = colBackList;
	}

	/**
	 * 獲取屬性處理人員代碼.
	 * 
	 * @return 屬性處理人員代碼的值
	 */
	public String getOperatorCode() {
		return OperatorCode;
	}

	/**
	 * 設置屬性處理人員代碼.
	 * 
	 * @param operatorCode
	 *            待設置的處理人員代碼的值
	 */
	public void setOperatorCode(String operatorCode) {
		this.OperatorCode = operatorCode;
	}

	/**
	 * 獲取屬性合約號.
	 * 
	 * @return 屬性合約號的值
	 */
	public String getContractNo() {
		return ContractNo;
	}

	/**
	 * 設置屬性合約號.
	 * 
	 * @param contractNo
	 *            待設置的合約號的值
	 */
	public void setContractNo(String contractNo) {
		this.ContractNo = contractNo;
	}

	/**
	 * 獲取屬性包號.
	 * 
	 * @return 屬性包號的值
	 */
	public String getiPackageID() {
		return iPackageID;
	}

	/**
	 * 設置屬性包號.
	 * 
	 * @param iPackageID
	 *            待設置的包號的值
	 */
	public void setiPackageID(String iPackageID) {
		this.iPackageID = iPackageID;
	}

	/**
	 * 獲取屬性工作流狀態.
	 * 
	 * @return 屬性工作流狀態的值
	 */
	public String getiFlowStatus() {
		return iFlowStatus;
	}

	/**
	 * 設置屬性工作流狀態.
	 * 
	 * @param iFlowStatus
	 *            待設置的工作流狀態的值
	 */
	public void setiFlowStatus(String iFlowStatus) {
		this.iFlowStatus = iFlowStatus;
	}

	/**
	 * 獲取屬性處理部門代碼.
	 * 
	 * @return 屬性處理部門代碼的值
	 */
	public String getiDeptCode() {
		return iDeptCode;
	}

	/**
	 * 設置屬性處理部門代碼.
	 * 
	 * @param iDeptCode
	 *            待設置的處理部門代碼的值
	 */
	public void setiDeptCode(String iDeptCode) {
		this.iDeptCode = iDeptCode;
	}

	/**
	 * 獲取屬性流入時間.
	 * 
	 * @return 屬性流入時間的值
	 */
	public String getiFlowInTime() {
		return iFlowInTime;
	}

	/**
	 * 設置屬性流入時間.
	 * 
	 * @param iFlowInTime
	 *            待設置的流入時間的值
	 */
	public void setiFlowInTime(String iFlowInTime) {
		this.iFlowInTime = iFlowInTime;
	}

	/**
	 * 獲取屬性節點狀態.
	 * 
	 * @return 屬性節點狀態的值
	 */
	public String getiNodeStatus() {
		return iNodeStatus;
	}

	/**
	 * 設置屬性節點狀態.
	 * 
	 * @param iNodeStatus
	 *            待設置的節點狀態的值
	 */
	public void setiNodeStatus(String iNodeStatus) {
		this.iNodeStatus = iNodeStatus;
	}

	/**
	 * 獲取屬性節點名稱.
	 * 
	 * @return 屬性節點名稱的值
	 */
	public String getiNodeName() {
		return iNodeName;
	}

	/**
	 * 設置屬性節點名稱.
	 * 
	 * @param iNodeName
	 *            待設置的節點名稱的值
	 */
	public void setiNodeName(String iNodeName) {
		this.iNodeName = iNodeName;
	}

	/**
	 * 獲取屬性險類代碼.
	 * 
	 * @return 屬性險類代碼的值
	 */
	public String getClassCode() {
		return classCode;
	}

	/**
	 * 設置屬性險類代碼.
	 * 
	 * @param classCode
	 *            待設置的險類代碼的值
	 */
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	/**
	 * 獲取屬性選中的節點號.
	 * 
	 * @return 屬性選中的節點號的值
	 */
	public String getSelectNodeNo() {
		return selectNodeNo;
	}

	/**
	 * 設置屬性選中的節點號.
	 * 
	 * @param selectNodeNo
	 *            待設置的選中的節點號的值
	 */
	public void setSelectNodeNo(String selectNodeNo) {
		this.selectNodeNo = selectNodeNo;
	}

	/**
	 * 獲取屬性危險單位信息服務接口.
	 * 
	 * @return 屬性危險單位信息服務接口的值
	 */
	public CommonDangerInfoService getCommonDangerInfoService() {
		return commonDangerInfoService;
	}

	/**
	 * 設置屬性危險單位信息服務接口.
	 * 
	 * @param commonDangerInfoService
	 *            待設置的危險單位信息服務接口的值
	 */
	public void setCommonDangerInfoService(
			CommonDangerInfoService commonDangerInfoService) {
		this.commonDangerInfoService = commonDangerInfoService;
	}

	/**
	 * 獲取屬性危險單位劃分接口.
	 * 
	 * @return 屬性危險單位劃分接口的值
	 */
	public PDangerUnitService getpDangerUnitService() {
		return pDangerUnitService;
	}

	/**
	 * 設置屬性危險單位劃分接口.
	 * 
	 * @param pDangerUnitService
	 *            待設置的危險單位劃分接口的值
	 */
	public void setpDangerUnitService(PDangerUnitService pDangerUnitService) {
		this.pDangerUnitService = pDangerUnitService;
	}

	/**
	 * 獲取屬性危險單位信息接口.
	 * 
	 * @return 屬性危險單位信息接口的值
	 */
	public BLDangerService getBlDangerService() {
		return blDangerService;
	}

	/**
	 * 設置屬性危險單位信息接口.
	 * 
	 * @param blDangerService
	 *            待設置的危險單位信息接口的值
	 */
	public void setBlDangerService(BLDangerService blDangerService) {
		this.blDangerService = blDangerService;
	}
    
	/**
	 * 獲取核保級別設定接口.
	 * 
	 * @return the 核保級別設定接口
	 */
	public UtiUwLevelService getUtiUwLevelService() {
		return utiUwLevelService;
	}

	/**
	 * 設置核保級別設定接口.
	 * 
	 * @param utiUwLevelService
	 *            the new 核保級別設定接口
	 */
	public void setUtiUwLevelService(UtiUwLevelService utiUwLevelService) {
		this.utiUwLevelService = utiUwLevelService;
	}

	public PrpFeedBackService getPrpFeedBackService() {
		return prpFeedBackService;
	}

	public void setPrpFeedBackService(PrpFeedBackService prpFeedBackService) {
		this.prpFeedBackService = prpFeedBackService;
	}
	/**
	 * 獲取屬性危險單位號.
	 * 
	 * @return 屬性危險單位號
	 */

	public String getDangerNo() {
		return dangerNo;
	}
	/**
	 * 設置屬性危險單位號.
	 * 
	 * @param dangerNo
	 *            待設置的危險單位號
	 */
	public void setDangerNo(String dangerNo) {
		this.dangerNo = dangerNo;
	}
	

	public PrpDuserService getPrpDuserService() {
		return prpDuserService;
	}

	public void setPrpDuserService(PrpDuserService prpDuserService) {
		this.prpDuserService = prpDuserService;
	}

	public List<SendTaskVo> getSendTaskVoList() {
		return sendTaskVoList;
	}

	public void setSendTaskVoList(List<SendTaskVo> sendTaskVoList) {
		this.sendTaskVoList = sendTaskVoList;
	}
}
