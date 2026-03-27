package com.sinosoft.undwrt.undwrtDeal.web;

import ins.framework.common.QueryRule;
import ins.framework.web.Struts2Action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.sinosoft.platform.dto.domain.PrpDuserDto;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.undwrt.common.service.facade.PrpDcodeService;
import com.sinosoft.undwrt.common.service.facade.PrpDcompanyService;
import com.sinosoft.undwrt.undwrtBase.model.SwfPathNew;
import com.sinosoft.undwrt.undwrtBase.model.UwNotion;
import com.sinosoft.undwrt.undwrtBase.model.WfLog;
import com.sinosoft.undwrt.undwrtBase.service.facade.SwfPathNewService;
import com.sinosoft.undwrt.undwrtBase.service.facade.SwfPathService;
import com.sinosoft.undwrt.undwrtBase.service.facade.UwNotionService;
import com.sinosoft.undwrt.undwrtBase.service.facade.WfLogService;

/**
 * 下發修改處理類.
 */
public class BatchTaskAction extends Struts2Action {

	/** 屬性處理類型. */
	private String handType;

	/** 屬性選中的任務數組. */
	private String[] checkboxSelect;

	/** 屬性工作流號. */
	private String[] FlowID;

	/** 屬性序號. */
	private String[] LogNo;

	/** 屬性編輯類型. */
	private String editType;

	/** 屬性跳轉頁面返回結果. */
	private String content;

	/** 屬性核保處理意見. */
	private List<UwNotion> notionContent;

	/** 屬性審批片語. */
	private List notionCodeList = new ArrayList();

	// private List<WfLog> wfLogList;

	/** 屬性核保處理意見接口. */
	private UwNotionService uwNotionService;

	/** 屬性工作流日誌接口. */
	private WfLogService wfLogService;

	/** 屬性機構接口. */
	private PrpDcompanyService prpDcompanyService;

	/** 屬性工作流路徑定義接口. */
	private SwfPathService swfPathService;

	/** 屬性基礎代碼表接口. */
	private PrpDcodeService prpDcodeService;

	/** 屬性 路徑接口. */
	private SwfPathNewService swfPathNewService;

	/** 屬性下發工作流日誌列表. */
	private Collection batchTaskList = new ArrayList();

	/** 屬性回退路徑. */
	private Collection colBackList = new ArrayList();
	
	private String operateType;

	/**
	 * 處理任務.
	 * 
	 * @return 跳轉頁面的結果
	 * @throws Exception
	 *             異常
	 */
	public String handleTask() throws Exception {
		String forward = "success";
		HttpSession session = this.getSession(false);

		// 报价单审核，暂无用，预留
		String[] HandType = handType.split(",");// editTppe的值为CancelDeal,deal
		handType = HandType[0];
		if ("12".equals(handType)) {
			// try {
			// UICommonDealSubmitAction uiCommonDealSubmitAction = new
			// UICommonDealSubmitAction();
			// uiCommonDealSubmitAction.submitBatchTaskQta(req);
			// } catch (UserException usee) {
			// forward = "failure";
			// session = req.getSession();
			// session.setAttribute("userException", usee);
			// }catch(InvocationTargetException inEx){
			// forward = "failure";
			// if(inEx.getTargetException() instanceof UserException){
			// UserException ue = (UserException)inEx.getTargetException();
			// session = req.getSession();
			// session.setAttribute("userException", ue);
			// }
			// }catch (Exception e) {
			// forward = "failure";
			// throw e;
			// }
			// return actionMapping.findForward(forward);
		}

		WfLog wfLogDto = null;

		// 统一指定第一条记录节点

		String[] Edittype = editType.split(",");// editTppe的值为CancelDeal,deal
		editType = Edittype[0];
		// 更新标志位，当前核保员占住当前业务
		PrpDuserDto prpDuserDto = new PrpDuserDto();
		prpDuserDto.setUserCode((String) this.getSession(false).getAttribute(
				"myUserCode"));
		prpDuserDto.setUserName((String) this.getSession(false).getAttribute(
				"myUserName"));
		prpDuserDto.setComCode((String) this.getSession(false).getAttribute(
				"myComCode"));
		DateTime dateTime = new DateTime(new java.util.Date());

		if (editType.equals("CancelDeal")) {
			for (int i = 0; i < checkboxSelect.length; i++) {
				wfLogDto = this.findByPrimaryKey(FlowID[Integer
						.parseInt(checkboxSelect[i])], Integer
						.parseInt(LogNo[Integer.parseInt(checkboxSelect[i])]));
				String nodeStatus = wfLogDto.getNodeStatus();
				if (nodeStatus.equals("2")) { // 正在处理或已处理未提交，如果是他人再处理，进行提示
					if (!prpDuserDto.getUserCode().equals(
							wfLogDto.getOperatorCode())) {
						forward = "failure";
						content = wfLogDto.getOperatorName()
								+ getText("undwrt.action.batchTask.taskDealing");
					}
				} else if (nodeStatus.equals("4")) { // 已提交或已关闭
					forward = "failure";
					content = getText("undwrt.action.batchTask.flowDealed");
				} else if (nodeStatus.equals("0")) {
					forward = "failure";
					content = getText("undwrt.action.batchTask.workFlowDealed");
				}

				wfLogDto.setDeptCode(prpDuserDto.getComCode());
				wfLogDto.setOperatorCode(prpDuserDto.getUserCode());
				wfLogDto.setOperatorName(prpDuserDto.getUserName());
				wfLogDto.setHandleTime(dateTime.current().toString()
						.substring(0, 19));
				wfLogDto.setNodeStatus("2");
				if ("success".equals(forward)) {
					wfLogService.update(wfLogDto);
				}
				batchTaskList.add(wfLogDto);

				// 下发路径
				WfLog wfPathDto = new WfLog();
				if (checkboxSelect.length == 1) {
					if ("2".equals(wfLogDto.getResultCode())) {
						// 走规则引擎时，提交上级和下发修改的路径
						String[] arrNodeNo;
						String[] arrNodeName;
						String riskcode = wfLogDto.getRiskCode();
						String comcode = wfLogDto.getComCode();
						int nodeno = wfLogDto.getNodeNo();
						SwfPathNew swfPathNewDto = new SwfPathNew();

						// 查询下发路径
						if ("".equals(wfPathDto.getNodeName())) {
							boolean isFind = false;
							while (!isFind) {
								QueryRule queryRule = QueryRule.getInstance();
								queryRule.addEqual("riskCode", riskcode);
								queryRule.addEqual("comCode", comcode);
								swfPathNewDto = swfPathNewService
										.findByPrimaryKey(queryRule);
								// 如果没有找到就查找上级
								if (swfPathNewDto != null) {
									isFind = true;
								} else {
									com.sinosoft.undwrt.common.model.PrpDcompany prpDcompanyDto = new com.sinosoft.undwrt.common.model.PrpDcompany();
									queryRule = QueryRule.getInstance();
									queryRule.addEqual("comCode", comcode);

									prpDcompanyDto = prpDcompanyService
											.findByPrimaryKey(queryRule);
									// 查到总公司还没有数据就抛出
									if (!prpDcompanyDto.getComCode().equals(
											prpDcompanyDto
													.getUpperClaimComCode())) {
										comcode = prpDcompanyDto
												.getUpperClaimComCode();
									} else {
										throw new UserException(
												-98,
												-9999,
												this.getClass().getName()
														+ ".getNodeNo()",
												getText("undwrt.action.batchTask.noRoute"));
									}
								}
							}
							arrNodeNo = swfPathNewDto.getPath().split(",");
							arrNodeName = swfPathNewDto.getPathDesc()
									.split(",");
							// 回退路径
							for (int j = (arrNodeNo.length - 1); j >= 0; j--) {
								if (nodeno > Integer.parseInt(arrNodeNo[j])) {
									WfLog wfLogDto1 = new WfLog();
									wfLogDto1.setNodeNo(Integer
											.parseInt(arrNodeNo[j]));
									wfLogDto1.setNodeName(arrNodeName[j]);
									colBackList.add(wfLogDto1);
								}
							}
						}
					} else {
						// 没有走规则引擎的回退列表
						try {
							colBackList = wfLogService.getBackList(wfLogDto
									.getId().getFlowId(), wfLogDto.getId()
									.getLogNo(), wfLogDto.getNodeNo());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				QueryRule queryRule = QueryRule.getInstance();
				queryRule.addEqual("id.flowId", FlowID[i]);
				queryRule.addEqual("id.logNo", Integer.parseInt(LogNo[i]));
				try {
					notionContent = uwNotionService.findByConditions(queryRule);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// 如果选择多个任务下发，则只支持下发到出单员
			if (checkboxSelect.length > 1) {
				WfLog wfLogDto1 = new WfLog();
				wfLogDto1.setNodeNo(1);
				wfLogDto1.setNodeName("出单员");
				colBackList.add(wfLogDto1);
			}
		}

		/************* 获取审批片语 ******************/
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.codeType", "HbNotionCode");
		queryRule.addNotEqual("id.codeCode", "001");
		queryRule.addNotEqual("id.codeCode", "005");
		notionCodeList = prpDcodeService.findByConditions(queryRule);

		// session.setAttribute("NotionCode",
		// prpDcodeService.findByConditions(queryRule));
		// session.setAttribute("batchTaskList", batchTaskList);
		// session.setAttribute("submitBackList", colBackList);
		return forward;
	}

	/**
	 * 根據主鍵查找一條工作流日誌記錄.
	 * 
	 * @param FlowID
	 *            工作流號
	 * @param LogNo
	 *            序號
	 * @return 工作流日誌對象
	 * @throws Exception
	 *             異常
	 */
	public WfLog findByPrimaryKey(String FlowID, int LogNo) throws Exception {
		WfLog wflog = new WfLog();
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.flowId", FlowID);
		queryRule.addEqual("id.logNo", LogNo);
		wflog = wfLogService.findByPrimaryKey(queryRule);
		return wflog;
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
	 * 獲取屬性選中的任務數組.
	 * 
	 * @return 屬性選中的任務數組的值
	 */
	public String[] getCheckboxSelect() {
		return checkboxSelect;
	}

	/**
	 * 設置屬性選中的任務數組.
	 * 
	 * @param checkboxSelect
	 *            待設置的選中的任務數組的值
	 */
	public void setCheckboxSelect(String[] checkboxSelect) {
		this.checkboxSelect = checkboxSelect;
	}

	/**
	 * 獲取屬性工作流號.
	 * 
	 * @return 屬性工作流號的值
	 */
	public String[] getFlowID() {
		return FlowID;
	}

	/**
	 * 設置屬性工作流號.
	 * 
	 * @param flowID
	 *            待設置的工作流號的值
	 */
	public void setFlowID(String[] flowID) {
		this.FlowID = flowID;
	}

	/**
	 * 獲取屬性序號.
	 * 
	 * @return 屬性序號的值
	 */
	public String[] getLogNo() {
		return LogNo;
	}

	/**
	 * 設置屬性序號.
	 * 
	 * @param logNo
	 *            待設置的序號的值
	 */
	public void setLogNo(String[] logNo) {
		this.LogNo = logNo;
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
	 * 獲取屬性批量核保任務列表.
	 * 
	 * @return 屬性批量核保任務列表的值
	 */
	public Collection getBatchTaskList() {
		return batchTaskList;
	}

	/**
	 * 設置屬性批量核保任務列表.
	 * 
	 * @param batchTaskList
	 *            待設置的批量核保任務列表的值
	 */
	public void setBatchTaskList(Collection batchTaskList) {
		this.batchTaskList = batchTaskList;
	}

	/**
	 * 獲取屬性回退路徑.
	 * 
	 * @return 屬性回退路徑的值
	 */
	public Collection getColBackList() {
		return colBackList;
	}

	/**
	 * 設置屬性回退路徑.
	 * 
	 * @param colBackList
	 *            待設置的回退路徑的值
	 */
	public void setColBackList(Collection colBackList) {
		this.colBackList = colBackList;
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

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

}
