package com.sinosoft.claim.undwrt.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;

import com.sinosoft.claim.common.service.facade.PrpDriskService;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.SwfNode;
import com.sinosoft.claim.schema.model.UtiUwLevel;
import com.sinosoft.claim.schema.service.facade.PrpDcodeService;
import com.sinosoft.claim.schema.service.facade.SwfNodeService;
import com.sinosoft.claim.schema.service.facade.UtiUwLevelService;
import com.sinosoft.claim.schema.service.facade.WfLogService;
import com.sinosoft.claim.undwrt.vo.NodeListDto;
import com.sinosoft.platform.dto.domain.PrpDuserDto;
import com.sinosoft.sysframework.common.datatype.PageRecord;
import com.sinosoft.sysframework.common.util.ParamUtils;
import com.sinosoft.undwrt.bl.facade.BLTaskDealFacade;
import com.sinosoft.undwrt.dto.domain.UwNotionDto;
import com.sinosoft.undwrt.dto.domain.WfLogDto;
import com.sinosoft.undwrt.ui.control.action.UIWflogQueryAction;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description
 */
public class UndwrtTaskDealViewHelper {
	/** 任务处理viewHelper */
	private TaskDealViewHelper taskDealViewHelper;
	/** 通用代码数据服务 */
	private PrpDcodeService prpDcodeService;
	/** 人员级别设置信息服务 */
	private UtiUwLevelService utiUwLevelService;
	/** 工作流节点信息服务 */
	private SwfNodeService swfNodeService; 
	/** 险种信息服务 */
	private PrpDriskService prpDriskService;
	/** 工作流日志服务 */
	private WfLogService wfLogService;

	/**
	 * 进入查询页面的数据收集
	 * @param request
	 * @param responses
	 * @throws Exception
	 */
	public void prepareQuery(HttpServletRequest request, HttpServletResponse responses) throws Exception {
		// 得到所有险种送入List
		List<?> riskCategoryList = this.getPrpDriskService().findRiskCodeByRiskCategory();
		//初始化当前操作员核赔权限
		HttpSession session = request.getSession();
		String strUserCode = "";
		strUserCode = ((UserDto) session.getAttribute("user")).getUserCode();
		String conditions = "usercode ='" + strUserCode + "' AND (uwtype='C' or uwtype='Y') AND VALIDSTATUS  = '1' order by nodeNo desc ";
		List<UtiUwLevel> col = this.getUtiUwLevelService().findByConditions(conditions);
		int maxNodeNo = 0;//最大的级别
		if(col!=null && !col.isEmpty()){
			maxNodeNo = col.get(0).getId().getNodeNo();
		}
		List<NodeListDto> inodeList = new ArrayList<NodeListDto>();
		inodeList.add(new NodeListDto("A","不包含"));
		//可包含处理的下级节点
		List<SwfNode> coll = this.getSwfNodeService().findByConditions("modelno='31' and nodeno <>1 and nodeno < " + maxNodeNo);
		String EditType = request.getParameter("EditType");
		//mantis： CLM0026 ，處理人員： David ，需求單編號： CLM0026 核賠權限處理下階條件變更
		if("query".equals(EditType)||maxNodeNo<7){
			//mantis： CLM0104 ，處理人員： BK007  蘇哲 ，需求單編號： CLM0104.新核心-核賠查詢時預設選擇包含下階
			inodeList.add(0,new NodeListDto("B","包含全部"));
			if(coll!=null && !coll.isEmpty()){
				for(SwfNode sWfNode : coll){
					inodeList.add(new NodeListDto(String.valueOf(sWfNode.getId().getNodeNo()),sWfNode.getNodeName()));
				}
			}
		}
		request.setAttribute("nodeCollection", inodeList);
		Calendar date = Calendar.getInstance();
		date.add(Calendar.MONTH,-1);//提交日期控制在1个月内的
		request.setAttribute("startDate", date.getTime());
		request.setAttribute("endDate", Calendar.getInstance().getTime());
//		date = Calendar.getInstance();
//		date.add(Calendar.MONTH,-6);//事故日期控制在6个月内的
		request.setAttribute("damageStartDate","");
		request.setAttribute("damageEndDate","");
		request.setAttribute("riskCodeCollection", JSONArray.fromCollection(riskCategoryList).toString());
	}
	
	/**
	 * 进入批次查询页面的数据收集
	 * mantis：CLM0241，處理人員： DP0713 ，需求單編號：強制任意批次核賠功能新增
	 * @param request
	 * @param responses
	 * @throws Exception
	 */
	public void prepareHeapQuery(HttpServletRequest request, HttpServletResponse responses) throws Exception {
		// 得到所有险种送入List
		List<?> riskCategoryList = this.getPrpDriskService().findRiskCodeByRiskCategory();
		//初始化当前操作员核赔权限
		HttpSession session = request.getSession();
		String strUserCode = "";
		strUserCode = ((UserDto) session.getAttribute("user")).getUserCode();
		String conditions = "usercode ='" + strUserCode + "' AND (uwtype='C' or uwtype='Y') AND VALIDSTATUS  = '1' order by nodeNo desc ";
		List<UtiUwLevel> col = this.getUtiUwLevelService().findByConditions(conditions);
		int maxNodeNo = 0;//最大的级别
		if(col!=null && !col.isEmpty()){
			maxNodeNo = col.get(0).getId().getNodeNo();
		}
		List<NodeListDto> inodeList = new ArrayList<NodeListDto>();
		inodeList.add(new NodeListDto("A","不包含"));
		//可包含处理的下级节点
		List<SwfNode> coll = this.getSwfNodeService().findByConditions("modelno='31' and nodeno <>1 and nodeno < " + maxNodeNo);
		String EditType = request.getParameter("EditType");
		if("query".equals(EditType)||maxNodeNo<7){
			inodeList.add(0,new NodeListDto("B","包含全部"));
			if(coll!=null && !coll.isEmpty()){
				for(SwfNode sWfNode : coll){
					inodeList.add(new NodeListDto(String.valueOf(sWfNode.getId().getNodeNo()),sWfNode.getNodeName()));
				}
			}
		}
		request.setAttribute("nodeCollection", inodeList);
		Calendar date = Calendar.getInstance();
		date.add(Calendar.MONTH,-2);//提交日期控制在2个月内的
		request.setAttribute("startDate", date.getTime());
		request.setAttribute("endDate", Calendar.getInstance().getTime());
//		date = Calendar.getInstance();
//		date.add(Calendar.MONTH,-6);//事故日期控制在6个月内的
		request.setAttribute("damageStartDate","");
		request.setAttribute("damageEndDate","");
		request.setAttribute("riskCodeCollection", JSONArray.fromCollection(riskCategoryList).toString());
	}

	/**
	 * 查询核赔任务
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void query(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		String editType = StringUtils.trimToEmpty(paramUtils.getParameter("EditType"));
		int pageNo = paramUtils.getIntParameter("pageNo", 1);
		int rowsPerPage = paramUtils.getIntParameter("rowsPerPage", 15);
		// 组合查询SQL 设置默认从wflog表中查询
		// 得到查询案件状态，如果有已处理完毕，则从视图View_wfLogAll中查询，否则则从wflog表中查询
		String[] nodeStatusVal = request.getParameterValues("nodeStatus");
		String tableName = "wflog";
		if(nodeStatusVal!=null && "0".equals(nodeStatusVal[0])){
			tableName = "View_wfLogAll";
		}
		String statement = taskDealViewHelper.getHepeiTaskQueryStatement(request, tableName);
		PageRecord pageRecord = null;
		if("wflog".equals(tableName)){
			pageRecord = this.getWfLogService().findByStatementFromWflog(statement, pageNo, rowsPerPage);
		}else {
			pageRecord = this.getWfLogService().findView_wfLogAll(statement, pageNo, rowsPerPage);
		}
		String riskCategory = StringUtils.trimToEmpty(paramUtils.getParameter("riskCategory"));
		String[] nodeStatus = paramUtils.getParameterValues("nodeStatus");
		if (nodeStatus == null) {
			nodeStatus = new String[] { "" };
		}
		String[] enabled = taskDealViewHelper.getBatchButtonEnabledByNodeStatus(StringUtils.trimToEmpty(nodeStatus[0]));
		request.setAttribute("riskCategory", riskCategory);
		request.setAttribute("nodeStatus", nodeStatus[0]);
		request.setAttribute("batchSuperiorButton", enabled[0]);
		request.setAttribute("batchJuniorButton", enabled[1]);
		request.setAttribute("batchUndoButton", enabled[2]);
		request.setAttribute("pageRecord", pageRecord);
		request.setAttribute("UndwrtTaskList", pageRecord.getResult());
		request.getSession().setAttribute("EditType", editType);
		request.getSession().setAttribute("nodeStatusVal", nodeStatusVal);
		String riskCodeVal[] = request.getParameterValues("riskCode");
		request.getSession().setAttribute("riskCodeVal", riskCodeVal);
		String relateContractNoYesNoVal[] = request.getParameterValues("relateContractNoYesNo");
		request.getSession().setAttribute("relateContractNoYesNoVal", relateContractNoYesNoVal);
	}

	/**
	 * 查询核赔任务
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void queryContinue(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParamUtils paramUtils = new ParamUtils(request);
		String editType = StringUtils.trimToEmpty(paramUtils.getParameter("EditType"));
		int pageNo = paramUtils.getIntParameter("pageNo", 1);
		int rowsPerPage = paramUtils.getIntParameter("rowsPerPage", 15);
		String nodeStatusVal[] = (String[]) request.getSession().getAttribute("nodeStatusVal");
		String tableName = "wflog";
		if(nodeStatusVal!=null && "0".equals(nodeStatusVal[0])){
			tableName = "View_wfLogAll";
		}
		String statement = taskDealViewHelper.getHepeiTaskQueryStatement(request, tableName);
		PageRecord pageRecord = null;
		if("wflog".equals(tableName)){
			pageRecord = this.getWfLogService().findByStatementFromWflog(statement, pageNo, rowsPerPage);
		}else {
			pageRecord = this.getWfLogService().findView_wfLogAll(statement, pageNo, rowsPerPage);
		}
		String riskCategory = StringUtils.trimToEmpty(paramUtils.getParameter("riskCategory"));
		String nodeStatus[] = paramUtils.getParameterValues("nodeStatus");
		if (nodeStatus == null) {
			nodeStatus = (new String[] { "" });
		}
		String enabled[] = taskDealViewHelper.getBatchButtonEnabledByNodeStatus(StringUtils.trimToEmpty(nodeStatus[0]));

		request.setAttribute("riskCategory", riskCategory);
		request.setAttribute("nodeStatus", nodeStatus[0]);
		request.setAttribute("batchSuperiorButton", enabled[0]);
		request.setAttribute("batchJuniorButton", enabled[1]);
		request.setAttribute("batchUndoButton", enabled[2]);
		request.setAttribute("pageRecord", pageRecord);
		request.setAttribute("UndwrtTaskList", pageRecord.getResult());
		request.getSession().setAttribute("EditType", editType);
	}

	/**
	 * 查询上级
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String prepareBatchSubmitSuperior(HttpServletRequest request, HttpServletResponse response) throws Exception {

		WfLogDto wfLogDto = null;
		String flowId = request.getParameter("iFlowID");
		UIWflogQueryAction uiWflogQueryAction = new UIWflogQueryAction();
		wfLogDto = uiWflogQueryAction.findByPrimaryKey(flowId, 1);
		System.out.println(flowId + "-----------------------");
		if (wfLogDto.getNodeStatus().equals("0")) { // 已提交或已关闭
			return "failure";
		} else {
			List<WfLogDto> checkboxSelectCollection = taskDealViewHelper.getCheckboxSelectTaskCollection(request);
			BLTaskDealFacade blTaskDealFacade = new BLTaskDealFacade();
			List<?>[] submitList = blTaskDealFacade.prepareBatchSubmitSuperior(checkboxSelectCollection);
			List<?> notionList = null;

			notionList = (List<?>) prpDcodeService.findByConditions(" codetype='HpNotionCode'");

			request.setAttribute("notionList", notionList);
			if (submitList[0].size() > 0) {
				request.setAttribute("yesToSubmitList", submitList[0]);
			}
			if (submitList[1].size() > 0) {
				request.setAttribute("noToSubmitList", submitList[1]);
			}
			return "noFailure";
		}
	}

	public void setPage(HttpServletRequest request, PageRecord pageRecord) throws Exception {
		if (pageRecord == null) {
			pageRecord = (PageRecord) request.getAttribute("pageRecord");
		}
		int rowsCount = pageRecord.getCount();
		int firstRow = 0;
		int lastRow = 0;
		int currentPage = 0;
		int pagesCount = 0;
		if (rowsCount > 0) {
			currentPage = pageRecord.getPageNo();
			if (currentPage == 0) {
				currentPage = 1;
			}
		}
		pagesCount = pageRecord.getTotalPageCount();
		if (currentPage > 0) {
			firstRow = pageRecord.getRowsPerPage() * (currentPage - 1) + 1;
		}
		if (currentPage < pagesCount) {
			lastRow = firstRow + pageRecord.getRowsPerPage() - 1;
		} else {
			lastRow = pageRecord.getCount();
		}
	}

	/**
	 * 提交上级
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void batchSubmitSuperior(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrpDuserDto prpDuserDto = new PrpDuserDto();
		List<WfLogDto> batchTaskCollection = taskDealViewHelper.getBatchTaskCollection(request);
		List<UwNotionDto> batchNotionCollection = taskDealViewHelper.getBatchNotionCollection(request);
		BLTaskDealFacade blTaskDealFacade = new BLTaskDealFacade();
		blTaskDealFacade.batchSubmitSuperior(batchTaskCollection, batchNotionCollection, prpDuserDto);

	}

	public TaskDealViewHelper getTaskDealViewHelper() {
		return taskDealViewHelper;
	}

	public void setTaskDealViewHelper(TaskDealViewHelper taskDealViewHelper) {
		this.taskDealViewHelper = taskDealViewHelper;
	}

	public PrpDcodeService getPrpDcodeService() {
		return prpDcodeService;
	}

	public void setPrpDcodeService(PrpDcodeService prpDcodeService) {
		this.prpDcodeService = prpDcodeService;
	}

	public UtiUwLevelService getUtiUwLevelService() {
		return utiUwLevelService;
	}

	public void setUtiUwLevelService(UtiUwLevelService utiUwLevelService) {
		this.utiUwLevelService = utiUwLevelService;
	}

	public SwfNodeService getSwfNodeService() {
		return swfNodeService;
	}

	public void setSwfNodeService(SwfNodeService swfNodeService) {
		this.swfNodeService = swfNodeService;
	}

	public PrpDriskService getPrpDriskService() {
		return prpDriskService;
	}

	public void setPrpDriskService(PrpDriskService prpDriskService) {
		this.prpDriskService = prpDriskService;
	}

	public WfLogService getWfLogService() {
		return wfLogService;
	}

	public void setWfLogService(WfLogService wfLogService) {
		this.wfLogService = wfLogService;
	}
	
}
