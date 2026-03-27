package com.sinosoft.undwrt.undwrtDeal.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import ins.framework.common.QueryRule;
import ins.framework.web.Struts2Action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sinosoft.platform.dto.domain.PrpDuserDto;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.undwrt.common.service.facade.PrpDcodeService;
import com.sinosoft.undwrt.pub.InternationalizationUtil;
import com.sinosoft.undwrt.undwrtBase.model.UwNotion;
import com.sinosoft.undwrt.undwrtBase.model.WfLog;
import com.sinosoft.undwrt.undwrtBase.service.facade.UwNotionService;
import com.sinosoft.undwrt.undwrtBase.service.facade.WfLogService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.WfLogHelperService;

/**
 * 批量核保處理類.
 */
public class CommonBatchTaskAction extends Struts2Action {

	/** 屬性序號. */
	private String iLogNo;

	/** 屬性工作流號. */
	private String iFlowID;

	/** 屬性編輯類型. */
	private String editType;

	/** 屬性工作流日誌接口. */
	private WfLogService wfLogService;

	/** 屬性核保系統幫助服務接口. */
	private WfLogHelperService wfLogHelperService;

	/** 屬性核保處理意見接口. */
	private UwNotionService uwNotionService;

	/** 屬性基礎代碼表接口. */
	private PrpDcodeService prpDcodeService;

	/** 屬性跳轉頁面返回結果. */
	private String content;

	/** 屬性審批片語. */
	private List notionCodeList;

	/** 屬性核保處理意見. */
	private List<UwNotion> notionContent;

	/** 屬性回退路徑. */
	private List colBackList;

	/** 屬性批量核保任務列表. */
	private List batchTaskList;

	/**
	 * 批量核保處理.
	 * 
	 * @return 跳轉頁面結果
	 * @throws UserException
	 *             自定義異常
	 * @throws Exception
	 *             異常
	 */
	public String CommonBatchTask() throws UserException, Exception {
		HttpSession session = this.getSession(false);
		HttpServletRequest req = this.getRequest();
		WfLog wfLog = null;
		String forward = "success";
		String userCode = (String) session.getAttribute("myUserCode");
		String editType = (String) req.getSession(false).getAttribute(
				"EditType");
		String nodeStatus = "";
		String[] nodeStatusVal = req.getParameterValues("nodeStatus");
		int logNo = 0;
		try {
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.flowId", iFlowID.trim());
			queryRule.addEqual("id.logNo", Integer.parseInt(iLogNo.trim()));
			wfLog = wfLogService.findByPrimaryKey(queryRule);
			// 获取节点状态进行判断 add by luyang 2005-11-3 11:05下午
			nodeStatus = wfLog.getNodeStatus();
			if (forward.equals("success")) {
				forward = this.setBatchTaskDtoToView(req);
			}
		} catch (UserException usee) {
			forward = "failure";
			session.setAttribute("userException", usee);
		}

		return forward;
	}

	/**
	 * 獲取批量核保列表.
	 * 
	 * @param req
	 *            請求對象
	 * @return 跳轉結果頁面
	 * @throws Exception
	 *             異常
	 */
	public String setBatchTaskDtoToView(HttpServletRequest req)
			throws Exception {
		InternationalizationUtil internal = new InternationalizationUtil();
		HttpSession session = req.getSession(true);
		String forward = "success";
		try {
			String contractNo = req.getParameter("iContractNo");
			String flowID = req.getParameter("iFlowID");
			String logNo = req.getParameter("iLogNo");
			String modelNo = req.getParameter("iModelNo");
			String nodeNo = req.getParameter("iNodeNo");
			String sqlPart = "";
			String handType = req.getParameter("HandType");
			if (handType == null || handType.equals("")) {
				handType = (String) session.getAttribute("HandType");
			}
			sqlPart = " SELECT DISTINCT Wflog.* From Wflog Where Wflog.LogNo <> 1 AND Wflog.NodeNo <> 1";
			if (handType.equals("11")) {
				sqlPart += " AND WfLog.BusinessType NOT IN('C','Y')";
			} else if (handType.equals("22")) {
				sqlPart += " AND WfLog.BusinessType IN('C','Y')";
			}
			sqlPart = sqlPart
					+ wfLogHelperService.getQueryConditionStatement(req);
			// 修改查询条件，加入当前模板号和节点的条件限制 add by luyang 2005-11-7 09:38
			sqlPart += " AND ContractNo = '" + contractNo + "'"
					+ " AND Wflog.modelNo ='" + modelNo + "'"
					+ " AND Wflog.nodeNo ='" + nodeNo + "'";
			System.out.println("sqlPart===" + sqlPart);
			/************* 获取批量核保任务列表 ************/
			batchTaskList = (List) wfLogService.findByConditions(sqlPart);

			// 查询业务下发的路径
			colBackList = wfLogHelperService
					.getBackPathList(req, batchTaskList);

			// 更新标志位，当前核保员占住当前业务
			PrpDuserDto prpDuserDto = new PrpDuserDto();
			prpDuserDto.setUserCode((String) req.getSession(false)
					.getAttribute("myUserCode"));
			prpDuserDto.setUserName((String) req.getSession(false)
					.getAttribute("myUserName"));
			prpDuserDto.setComCode((String) req.getSession(false).getAttribute(
					"myComCode"));
			DateTime dateTime = new DateTime(new java.util.Date());

			Iterator it = batchTaskList.iterator();
			while (it.hasNext()) {
				WfLog wfLog = (WfLog) it.next();
				if (wfLog.getNodeStatus().equals("2")) { // 正在处理或已处理未提交，如果是他人再处理，进行提示
					if (!prpDuserDto.getUserCode().equals(
							wfLog.getOperatorCode())) {
						forward = "failure";
						content = wfLog.getOperatorName()
								+ internal
										.getText("undwrt.action.batchTask.taskDealing");
					}
				} else if (wfLog.getNodeStatus().equals("4")) { // 已提交或已关闭
					forward = "failure";
					content = internal
							.getText("undwrt.action.batchTask.flowDealed");
				} else if (wfLog.getNodeStatus().equals("0")) {
					forward = "failure";
					content = internal
							.getText("undwrt.action.batchTask.workFlowDealed");
				}
				break;
			}
			if ("success".equals(forward)) {
				while (it.hasNext()) {
					WfLog wfLog = (WfLog) it.next();
					wfLog.setDeptCode(prpDuserDto.getComCode());
					wfLog.setOperatorCode(prpDuserDto.getUserCode());
					wfLog.setOperatorName(prpDuserDto.getUserName());
					wfLog.setHandleTime(dateTime.current().toString()
							.substring(0, 19));
					wfLog.setNodeStatus("2");
					wfLogService.update(wfLog);
				}
			}
			session.setAttribute("batchTaskList", batchTaskList);

			/************* 获取审批意见 ******************/
			sqlPart = " FlowId='" + flowID + "'" + " AND LogNo='" + logNo + "'";
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addSql(sqlPart);
			notionContent = uwNotionService.findByConditions(queryRule);

			/************* 获取审批片语 ******************/

			queryRule.getRuleList().clear();
			queryRule.getQueryRuleList().clear();
			queryRule.addEqual("id.codeType", "HbNotionCode");
			queryRule.addIn("id.codeCode", "001", "005");
			notionCodeList = prpDcodeService.findPrpDcodeList(queryRule);
		} catch (UserException usee) {
			throw usee;
		}
		return forward;

	}

	/**
	 * 獲取屬性序號.
	 * 
	 * @return 屬性序號的值
	 */
	public String getiLogNo() {
		return iLogNo;
	}

	/**
	 * 設置屬性序號.
	 * 
	 * @param iLogNo
	 *            待設置的序號的值
	 */
	public void setiLogNo(String iLogNo) {
		this.iLogNo = iLogNo;
	}

	/**
	 * 獲取屬性工作流號.
	 * 
	 * @return 屬性工作流號的值
	 */
	public String getiFlowID() {
		return iFlowID;
	}

	/**
	 * 設置屬性工作流號.
	 * 
	 * @param iFlowID
	 *            待設置的工作流號的值
	 */
	public void setiFlowID(String iFlowID) {
		this.iFlowID = iFlowID;
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
	 * 獲取屬性核保系統幫助服務接口.
	 * 
	 * @return 屬性核保系統幫助服務接口的值
	 */
	public WfLogHelperService getWfLogHelperService() {
		return wfLogHelperService;
	}

	/**
	 * 設置屬性核保系統幫助服務接口.
	 * 
	 * @param wfLogHelperService
	 *            待設置的核保系統幫助服務接口的值
	 */
	public void setWfLogHelperService(WfLogHelperService wfLogHelperService) {
		this.wfLogHelperService = wfLogHelperService;
	}

	/**
	 * 獲取屬性核保處理意見接口.
	 * 
	 * @return 屬性核保處理意見接口的值
	 */
	public UwNotionService getUwNotionService() {
		return uwNotionService;
	}

	/**
	 * 設置屬性核保處理意見接口.
	 * 
	 * @param uwNotionService
	 *            待設置的核保處理意見接口的值
	 */
	public void setUwNotionService(UwNotionService uwNotionService) {
		this.uwNotionService = uwNotionService;
	}

	/**
	 * 獲取屬性基礎代碼表接口.
	 * 
	 * @return 屬性基礎代碼表接口的值
	 */
	public PrpDcodeService getPrpDcodeService() {
		return prpDcodeService;
	}

	/**
	 * 設置屬性基礎代碼表接口.
	 * 
	 * @param prpDcodeService
	 *            待設置的基礎代碼表接口的值
	 */
	public void setPrpDcodeService(PrpDcodeService prpDcodeService) {
		this.prpDcodeService = prpDcodeService;
	}

	/**
	 * 獲取屬性審批片語.
	 * 
	 * @return 屬性審批片語的值
	 */
	public List getNotionCodeList() {
		return notionCodeList;
	}

	/**
	 * 設置屬性審批片語.
	 * 
	 * @param notionCodeList
	 *            待設置的審批片語的值
	 */
	public void setNotionCodeList(List notionCodeList) {
		this.notionCodeList = notionCodeList;
	}

	/**
	 * 獲取屬性核保處理意見.
	 * 
	 * @return 屬性核保處理意見的值
	 */
	public List<UwNotion> getNotionContent() {
		return notionContent;
	}

	/**
	 * 設置屬性核保處理意見.
	 * 
	 * @param notionContent
	 *            待設置的核保處理意見的值
	 */
	public void setNotionContent(List<UwNotion> notionContent) {
		this.notionContent = notionContent;
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
	 * 獲取屬性批量核保任務列表.
	 * 
	 * @return 屬性批量核保任務列表的值
	 */
	public List getBatchTaskList() {
		return batchTaskList;
	}

	/**
	 * 設置屬性批量核保任務列表.
	 * 
	 * @param batchTaskList
	 *            待設置的批量核保任務列表的值
	 */
	public void setBatchTaskList(List batchTaskList) {
		this.batchTaskList = batchTaskList;
	}

}
