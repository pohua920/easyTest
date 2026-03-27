/*
 * @(#)GeneralClaimSpringService.java	Mar 5, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.specailCase.service.spring;

import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.common.service.facade.PrpDcompanyService;
import com.sinosoft.claim.generalClaim.vo.GeneralClaimDto;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLgeneralClaimTask;
import com.sinosoft.claim.schema.model.PrpLgeneralClaimTaskLog;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.claim.schema.service.facade.PrpLgeneralClaimTaskLogService;
import com.sinosoft.claim.schema.service.facade.PrpLgeneralClaimTaskService;
import com.sinosoft.claim.schema.service.facade.PrpLprepayService;
import com.sinosoft.claim.schema.service.facade.PrpLrecaseService;
import com.sinosoft.claim.schema.service.facade.PrpLregistService;
import com.sinosoft.claim.schema.service.facade.SwfLogService;
import com.sinosoft.claim.specailCase.service.facade.GeneralClaimService;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description
 */
public class GeneralClaimServiceSpringImpl extends GenericDaoHibernate<String,String> implements GeneralClaimService {
	/**报案信息service*/
	private PrpLregistService prpLregistService;
	/**立案基本信息service*/
	private PrpLclaimService prpLclaimService;
	/**赔款计算书信息service*/
	private PrpLcompensateService prpLcompensateService;
	/**预赔登记信息service*/
	private PrpLprepayService prpLprepayService;
	/**重开赔案service*/
	private PrpLrecaseService prpLrecaseService;
	/**通赔任务信息service*/
	private PrpLgeneralClaimTaskService prpLgeneralClaimTaskService;
	/**代查勘service*/
	private PrpLgeneralClaimTaskLogService prpLgeneralClaimTaskLogService;
	/**机构信息service*/
	private PrpDcompanyService prpDcompanyService;
	private SwfLogService swfLogService;

	/**
	 * 取得该报案的相关信息
	 * @param dbManager
	 * @param UserDto：操作员信息
	 * @param registNo：报案号
	 * @throws Exception
	 * @return ArrayList
	 */
//	public List<Object> getClaimStatus(UserDto userDto, String registNo) throws Exception {
//		List<Object> claimStatusList = new ArrayList<Object>();
//		// 是否已报案注销
//		PrpLregist prpLregist = prpLregistService.findPrpLregist(registNo);
//		if (prpLregist == null) {
//			throw new Exception("查詢不到相關信息，請確認您輸入的報案號無誤！");
//		}
//		claimStatusList.add(prpLregist);// 放入报案信息，用於页面展示
//		if (prpLregist != null && prpLregist.getCancelDate() != null) {
//			claimStatusList.add("本案已於" + prpLregist.getCancelDate() + "註銷，不能再進行代查勘！");
//		}
//		// 是否已立案注销或结案
//		PrpLclaim prpLclaim = new PrpLclaim();
//		String claimNo = null;
//		List<PrpLclaim> prpLclaimList = prpLclaimService.findByRegistNo(registNo);
//		if (prpLclaimList != null && prpLclaimList.size() > 0) {
//			prpLclaim = (PrpLclaim) prpLclaimList.get(0);
//			claimNo = prpLclaim.getClaimNo();
//			if (prpLclaim.getCancelDate() != null) {
//				claimStatusList.add("本案已於" + prpLclaim.getCancelDate() + "註銷，不能再進行代查勘！");
//			} else if (prpLclaim.getEndCaseDate() != null) {
//				claimStatusList.add("本案已於" + prpLclaim.getEndCaseDate() + "結案，不能再進行代查勘！");
//			}
//		}
//
//		// 是否存在核赔通过的计算书
//		if (claimNo != null) {
//			PrpLcompensate prpLcompensate = null;
//			PrpLprepay prplprepay = null;
//			List<PrpLcompensate> prpLcompensateList = prpLcompensateService.findByClaimNo(claimNo);
//			List<PrpLprepay> prplprepayList = prpLprepayService.findByClaimNo(claimNo);
//			if (prpLcompensateList != null && prpLcompensateList.size() > 0) {
//				for (int i = 0; i < prpLcompensateList.size(); i++) {
//					prpLcompensate = prpLcompensateList.get(i);
//					if ("1".equals(prpLcompensate.getUnderWriteFlag()) || "3".equals(prpLcompensate.getUnderWriteFlag())) {
//						claimStatusList.add("本案存在核賠通過的計算書（" + prpLcompensate.getCompensateNo() + "），不能再進行代查勘！");
//					}
//				}
//			}
//			if (prplprepayList != null && prplprepayList.size() > 0) {
//				for (int i = 0; i < prplprepayList.size(); i++) {
//					prplprepay = prplprepayList.get(i);
//					if ("1".equals(prplprepay.getUnderWriteFlag()) || "3".equals(prplprepay.getUnderWriteFlag())) {
//						claimStatusList.add("本案存在核賠通過的計算書（" + prplprepay.getPreCompensateNo() + "），不能再進行代查勘！");
//					}
//				}
//			}
//		}
//		// 是否重开赔案
//		if (claimNo != null) {
//			QueryRule queryRule = QueryRule.getInstance();
//			queryRule.addEqual("id.claimNo", claimNo);
//			List<PrpLrecase> prpLrecaseList = prpLrecaseService.findPrpLrecase(queryRule);
//			if (prpLrecaseList != null && prpLrecaseList.size() > 0) {
//				claimStatusList.add("本案屬於重開賠案，不能再進行代查勘！");
//			}
//		}
//		// 是否存在活动节点（本省）
//		String comcode = userDto.getComCode();
//		String comcodeTemp1 = comcode.substring(0, 2);
//		String comcodeTemp2 = comcode.substring(0, 4);
//		String conditions = "";
//		if ("00".equals(comcode.substring(0, 2))) {// 总公司能操作全国案件
//			conditions = " REGISTNO = '" + registNo + "' AND NODESTATUS IN ('0','2','3') ORDER BY NODENO";
//		} else if ("3302".equals(comcodeTemp2) || "3502".equals(comcodeTemp2) || "4403".equals(comcodeTemp2) || "2102".equals(comcodeTemp2) || "3702".equals(comcodeTemp2)) {// 计划单列市特殊处理
//			conditions = " REGISTNO = '" + registNo + "' AND COMCODE LIKE '" + comcodeTemp2 + "%'" + " AND NODESTATUS IN ('0','2','3') ORDER BY NODENO";
//		} else if ("33".equals(comcodeTemp1) || "35".equals(comcodeTemp1) || "21".equals(comcodeTemp1) || "37".equals(comcodeTemp1)) {// 计划单列市所在省特殊处理
//			conditions = " REGISTNO = '" + registNo + "' AND COMCODE LIKE '" + comcodeTemp1 + "%'" + " AND COMCODE NOT LIKE '" + comcodeTemp1 + "02%' AND NODESTATUS IN ('0','2','3') ORDER BY NODENO";
//		} else if ("44".equals(comcodeTemp1)) {// 深圳特殊处理
//			conditions = " REGISTNO = '" + registNo + "' AND COMCODE LIKE '" + comcodeTemp1 + "%'" + " AND COMCODE NOT LIKE '4403%' AND NODESTATUS IN ('0','2','3') ORDER BY NODENO";
//		} else {// 其他省
//			conditions = " REGISTNO = '" + registNo + "' AND COMCODE LIKE '" + comcode.substring(0, 2) + "%'" + " AND NODESTATUS IN ('0','2','3') ORDER BY NODENO";
//		}
//		// ArrayList swflogDtoList = new ArrayList();
////		BLSwfLogFacade blSwfLogFacade = new BLSwfLogFacade();
//		List<SwfLog> swflogDtoList = swfLogService.findByConditions(conditions);
//		SwfLog swflogDto = null;
//		if (swflogDtoList == null || swflogDtoList.size() < 1) {
//			claimStatusList.add("本案目前沒有處於本省的可操作的活動節點，不能進行代查勘！");
//		} else if (swflogDtoList.size() > 0) {
//			Iterator<?> it = swflogDtoList.iterator();
//			while (it.hasNext()) {
//				swflogDto = (SwfLog)it.next();
//				if ("核賠".equals(swflogDto.getNodeName())) {
//					claimStatusList.add("本案已提交核賠，不能進行代查勘！");
//					break;
//				}
//			}
//			claimStatusList.add(swflogDtoList);// 放入活动节点信息，用於页面展示
//		}
//		return claimStatusList;
//	}

	/**
	 * 当前案件是否通赔过，不在承保地。如果是异地，返回true,不是异地，返回false
	 * @param businessNo
	 * @throws Exception
	 * @return boolean
	 */
//	public boolean isGeneral(String businessNo, UserDto userDto) throws Exception {
//		boolean generalFlag = false;
//		String userComCode = userDto.getComCode();
//		String PolicyComCode = "";
//		if ("6".equals(businessNo.substring(0, 1))) {// 报案号
//			PrpLregist prpLregist = null;
//			prpLregist = prpLregistService.findPrpLregist(businessNo);
//			if (prpLregist != null) {
//				PolicyComCode = prpLregist.getComCode();
//			}
//		} else if ("5".equals(businessNo.substring(0, 1))) {// 立案号
//			PrpLclaim prpLclaim = null;
//			prpLclaim = prpLclaimService.findPrpLclaim(businessNo);
//			if (prpLclaim != null) {
//				PolicyComCode = prpLclaim.getComCode();
//			}
//		} else if ("8".equals(businessNo.substring(0, 1))) {// 保单号
//			PrpCmainDto prpCmainDto = null;
//			BLPrpCmainFacade blPrpCmainFacade = new BLPrpCmainFacade();
//			prpCmainDto = blPrpCmainFacade.findByPrimaryKey(businessNo);
//			if (prpCmainDto != null) {
//				PolicyComCode = prpCmainDto.getComCode();
//			}
//		}
//		if (!"".equals(PolicyComCode)) {
//			if (("3302".equals(PolicyComCode) || "3502".equals(PolicyComCode) || "4403".equals(PolicyComCode) || "2102".equals(PolicyComCode) || "3702".equals(PolicyComCode))
//					|| ("3302".equals(userComCode) || "3502".equals(userComCode) || "4403".equals(userComCode) || "2102".equals(userComCode) || "3702".equals(userComCode))) {// 计划单列市单独处理
//				if (!PolicyComCode.substring(0, 4).equals(userComCode.substring(0, 4))) {
//					generalFlag = true;
//				} else {
//					generalFlag = false;
//				}
//
//			} else if (!PolicyComCode.substring(0, 2).equals(userComCode.substring(0, 2))) {
//				generalFlag = true;
//			} else {
//				generalFlag = false;
//			}
//
//		}
//		return generalFlag;
//	}

	/**
	 * 通赔委托提交
	 * @param dbManager
	 * @param UserDto：操作员信息
	 * @param registNo：报案号
	 * @throws Exception
	 * @return ArrayList
	 */
//	public void giveInsert(String registNo, String receiveComcode, String remark, UserDto userDto) throws Exception {
//		// 数据准备
//		// 1.报案数据
//		PrpLregist prpLregist = prpLregistService.findPrpLregist(registNo);
//		// 2.立案数据
//		String claimNo = "";
//		List<PrpLclaim> prpLclaimList = prpLclaimService.findByRegistNo(registNo);
//		if (prpLclaimList != null && prpLclaimList.size() > 0) {
//			claimNo = prpLclaimList.get(0).getClaimNo();
//		}
//		// 3.工作流数据
//		String strNodeName = "";
//		SwfLog swfLogDto = null;
////		BLSwfLogFacade blSwfLogFacade = new BLSwfLogFacade();
//		List<SwfLog> swfLogDtoList = swfLogService.findByConditions(" REGISTNO = '" + registNo + "' AND NODESTATUS IN ('0','2','3') ORDER BY NODENO DESC");
//		if (swfLogDtoList != null && swfLogDtoList.size() > 0) {
//			Iterator<SwfLog> it = swfLogDtoList.iterator();
//			while (it.hasNext()) {
//				swfLogDto = it.next();
//				swfLogDto.setComCode("");
//				swfLogDto.setHandleDept("");
//				// 除计算书外，其他环节都必须放弃任务才能进行通赔，所以必然没有占号的操作员。
//				if ("2".equals(swfLogDto.getNodeStatus()) && ("计算书".equals(swfLogDto.getNodeName()) || "特殊赔案".equals(swfLogDto.getNodeName()) || "立案".equals(swfLogDto.getNodeName()))) {
//					swfLogDto.setHandlerCode("2");
//					swfLogDto.setHandlerName("立案/理算/特殊賠案被暫存");
//				} else {
//					swfLogDto.setHandlerCode("0");
//					swfLogDto.setHandlerName("");
//				}
//				swfLogDto.setNodeStatus("9");// 通赔待接收
//				swfLogService.update(swfLogDto);
//				strNodeName += swfLogDto.getNodeName() + ",";
//			}
//		}
//		if (strNodeName.length() > 0) {
//			strNodeName = strNodeName.substring(0, strNodeName.length() - 1);// 去掉最後一个逗号
//		}
////		BLPrpDcompanyFacade blPrpDcompanyFacade = new BLPrpDcompanyFacade();
//		String giveComCode = this.getLevelTwoComCode(userDto.getComCode());
//		String giveComName = prpDcompanyService.findByPrimaryKey(giveComCode).getComCName();
//		String receiveComName = prpDcompanyService.findByPrimaryKey(receiveComcode).getComCName();
//
//		PrpLgeneralClaimTask prpLgeneralClaimTask = new PrpLgeneralClaimTask();
//		prpLgeneralClaimTask.setSerialNo(prpLgeneralClaimTaskService.getSeqNextVal("PrpLgeneralClaimTask"));
//		prpLgeneralClaimTask.setRegistNo(prpLregist.getRegistNo());
//		prpLgeneralClaimTask.setClaimNo(claimNo);
//		prpLgeneralClaimTask.setPolicyNo(prpLregist.getPolicyNo());
//		prpLgeneralClaimTask.setRiskCode(prpLregist.getRiskCode());
//		prpLgeneralClaimTask.setCurrentNode(strNodeName);
//		prpLgeneralClaimTask.setGiveComCode(giveComCode);
//		prpLgeneralClaimTask.setGiveComName(giveComName);
//		prpLgeneralClaimTask.setReceiveComCode(receiveComcode);
//		prpLgeneralClaimTask.setReceiveComName(receiveComName);
//		prpLgeneralClaimTask.setGiveOperatorCode(userDto.getUserCode());
//		prpLgeneralClaimTask.setGiveOperatorName(userDto.getUserName());
//		prpLgeneralClaimTask.setReceiveOperatorCode("");
//		prpLgeneralClaimTask.setReceiveOperatorName("");
//		prpLgeneralClaimTask.setGiveTime(new DateTime(new Date(), DateTime.YEAR_TO_MINUTE));
//		prpLgeneralClaimTask.setNodeStatus("1");
//		prpLgeneralClaimTask.setComCode(prpLregist.getComCode());
//		prpLgeneralClaimTask.setComName(prpLregist.getComName());
//		prpLgeneralClaimTask.setFlag("");
//		prpLgeneralClaimTask.setRemark(remark);
//		prpLgeneralClaimTask.setExtendString1("");
//		prpLgeneralClaimTask.setExtendString2("");
//		prpLgeneralClaimTask.setExtendstring3("");
//		prpLgeneralClaimTaskService.save(prpLgeneralClaimTask);
//
//	}
	/**
	 * 通赔委托提交
	 * @param UserDto：操作员信息
	 * @param registNo：报案号
	 * @throws Exception
	 * @return ArrayList
	 * @author 中科软
	 */
	public void giveInsert(List<SwfLog> swfLogDtoList,PrpLgeneralClaimTaskLog prpLgeneralClaimTaskLog) throws Exception {
		for(SwfLog swfLog : swfLogDtoList){
			swfLogService.update(swfLog);
		}
		prpLgeneralClaimTaskLogService.save(prpLgeneralClaimTaskLog);
	}
	/**
	 * 通赔委托提交
	 * @param UserDto：操作员信息
	 * @param registNo：报案号
	 * @throws Exception
	 * @return ArrayList
	 * @author 中科软
	 */
	public void giveInsert(GeneralClaimDto generalClaimDto) throws Exception {
		if("CaseTransfer".equals(generalClaimDto.getActionType())){
			swfLogService.updateComCode(generalClaimDto.getFlowId(),generalClaimDto.getComCode());
			if(generalClaimDto.getPrpLclaimList()!=null){
				for(PrpLclaim prpLclaim : generalClaimDto.getPrpLclaimList()){
					prpLclaimService.update(prpLclaim);
				}
			}
		}
		if(generalClaimDto.getSwflogList()!=null){
			for(SwfLog swfLog : generalClaimDto.getSwflogList()){
				swfLogService.update(swfLog);
			}
		}
		if(generalClaimDto.getPrpLgeneralClaimTaskLogList()!=null){
			for(PrpLgeneralClaimTaskLog prpLgeneralClaimTaskLog :generalClaimDto.getPrpLgeneralClaimTaskLogList() ){
				prpLgeneralClaimTaskLogService.save(prpLgeneralClaimTaskLog);
			}
		}
		
	}
	/**
	 * 通赔接收查询
	 * @param dbManager
	 * @param UserDto：操作员信息
	 * @param registNo：报案号
	 * @throws Exception
	 * @return ArrayList
	 */
//	public Page receiveQuery(String conditions, UserDto userDto, int pageNo, int pageSize) throws Exception {
//		String receiveComcode = "";
//		receiveComcode = this.getLevelTwoComCode(userDto.getComCode());
//		conditions += " AND RECEIVECOMCODE = '" + receiveComcode + "'";
//		QueryRule queryRule = QueryRule.getInstance().addSql(conditions);
//		Page page = prpLgeneralClaimTaskService.findPrpLgeneralClaimTask(queryRule, pageNo, pageSize);
//		return page;
//
//	}

	/**
	 * 进入通赔接收处理页面
	 * @param dbManager
	 * @param UserDto：操作员信息
	 * @param registNo：报案号
	 * @throws Exception
	 * @return ArrayList
	 */
//	public Map<String, Object> prepareReceiveInsert(String registNo) throws Exception {
//		Map<String, Object> generalClaimInfo = new HashMap<String, Object>();
//		PrpLregist prpLregist = prpLregistService.findPrpLregist(registNo);
//		String conditions = " REGISTNO = '" + registNo + "' AND NODESTATUS = '9' ORDER BY NODENO";
////		BLSwfLogFacade blSwfLogFacade = new BLSwfLogFacade();
//		List<SwfLog> swflogDtoList = swfLogService.findByConditions(conditions);
//		// DBPrplgeneralclaimtask dbPrplgeneralclaimtask = new
//		// DBPrplgeneralclaimtask(dbManager);
//		QueryRule queryRule = QueryRule.getInstance().addEqual("registNo", registNo);
//		List<PrpLgeneralClaimTask> prpLgeneralClaimTaskList = prpLgeneralClaimTaskService.findPrpLgeneralClaimTask(queryRule);
//		PrpLgeneralClaimTask prpLgeneralClaimTask = null;
//		if (prpLgeneralClaimTaskList != null && prpLgeneralClaimTaskList.size() > 0) {
//			prpLgeneralClaimTask = prpLgeneralClaimTaskList.get(0);
//		} else {
//			throw new Exception("該案已被其他代查勘崗接收，或者已被原處理機構收回！");
//		}
//		generalClaimInfo.put("prpLregist", prpLregist);
//		generalClaimInfo.put("swflogDtoList", swflogDtoList);
//		generalClaimInfo.put("prpLgeneralClaimTask", prpLgeneralClaimTask);
//		return generalClaimInfo;
//	}

	/**
	 * 查询能够处理某一机构下拥有某项权限的操作员
	 * @throws Exception
	 * @return PageRecord
	 * @author 中科软
	 */
//	public Page queryUserHaveRights(String conditions, int pageNo, int pageSize) throws Exception {
//		List<PrpDuserDto> list = new ArrayList<PrpDuserDto>(pageSize);
//		// boolean supportPaging = false;//数据库是否支持分页
//		List<?> resultSet = super.getSession().createSQLQuery(conditions).setFirstResult((pageNo - 1) * pageSize).setMaxResults(pageSize).list();
//		PrpDuserDto prpDuserDto = null;
//		Iterator<?> it = resultSet.iterator();
//		while (it.hasNext()) {
//			prpDuserDto = new PrpDuserDto();
//			Object[] obj = (Object[])it.next();
//			if (obj[0] != null) {
//				prpDuserDto.setUserCode(obj[0].toString());
//			}
//			if (obj[1] != null) {
//				prpDuserDto.setUserName(obj[1].toString());
//			}
//			list.add(prpDuserDto);
//		}
//		conditions = "select count(*) from (" + conditions + ")";
//		long count = HibernateUtils.getCountbySql(super.getSession(), conditions);
//		Page page = new Page((pageNo - 1) * pageSize, count, pageSize, list);
//		return page;
//	}

	/**
	 * 通赔接收提交
	 * @param dbManager
	 * @param UserDto：操作员信息
	 * @param registNo：报案号
	 * @throws Exception
	 * @return ArrayList
	 */
//	public void receiveInsert(List swflogList, UserDto userDto) throws Exception {
//		String registNo = "";
//		SwfLog swfLogDto = new SwfLog();
//		SwfLog newSwfLogDto = new SwfLog();
////		BLSwfLogFacade blSwfLogFacade = new BLSwfLogFacade();
//		// 更新swflog表
//		try {
//			for (int i = 0; i < swflogList.size(); i++) {
//				swfLogDto = (SwfLog) swflogList.get(i);
//				registNo = swfLogDto.getRegistNo();
//				newSwfLogDto = swfLogService.findSwfLog(swfLogDto.getId().getFlowID(), swfLogDto.getId().getLogNo());
//				newSwfLogDto.setComCode(swfLogDto.getComCode());
//				if ("2".equals(newSwfLogDto.getHandlerCode())) {// 借用HandlerCode存储理算暂存状态
//					newSwfLogDto.setNodeStatus("2");// 如果原来是暂存状态，还保留为暂存（现在只有立案环节、理算环节和特殊赔案可能出现这种情况）
//				} else {
//					newSwfLogDto.setNodeStatus("0");
//				}
//				if ("理算".equals(newSwfLogDto.getNodeName())) {
//					// 理算节点不置操作员，因为流程中的理算环节实际对应的是计算书节点，所以理算节点无法放弃任务。
//				} else {
//					newSwfLogDto.setHandlerName(swfLogDto.getHandlerName());
//					newSwfLogDto.setHandlerCode(swfLogDto.getHandlerCode());
//				}
//				swfLogService.update(newSwfLogDto);
//			}
//			List<PrpLgeneralClaimTask> prplgeneralclaimtaskDtoList = new ArrayList<PrpLgeneralClaimTask>();
//			PrpLgeneralClaimTask prpLgeneralClaimTask = new PrpLgeneralClaimTask();
//			PrpLgeneralClaimTaskLog prpLgeneralClaimTaskLog = new PrpLgeneralClaimTaskLog();
//			QueryRule queryRule = QueryRule.getInstance();
//			queryRule.addSql("REGISTNO = '" + registNo + "'");
//			prplgeneralclaimtaskDtoList = (List<PrpLgeneralClaimTask>) prpLgeneralClaimTaskService.findPrpLgeneralClaimTask(queryRule);
//			if (prplgeneralclaimtaskDtoList != null && prplgeneralclaimtaskDtoList.size() > 0) {
//				prpLgeneralClaimTask = (PrpLgeneralClaimTask) prplgeneralclaimtaskDtoList.get(0);
//			} else {
//				throw new Exception("該案已被其他通賠崗接收，或者已被原處理機搆收迴！");
//			}
//			PropertyUtils.copyProperties(prpLgeneralClaimTaskLog, prpLgeneralClaimTask);
//			prpLgeneralClaimTaskLog.setReceiveOperatorCode(userDto.getUserCode());
//			prpLgeneralClaimTaskLog.setReceiveOperatorName(userDto.getUserName());
//			prpLgeneralClaimTaskLog.setReceiveTime(new DateTime(new Date(), DateTime.YEAR_TO_SECOND));
//			prpLgeneralClaimTaskLog.setNodeStatus("0");
//			prpLgeneralClaimTaskLogService.save(prpLgeneralClaimTaskLog);
//			prpLgeneralClaimTaskService.deleteByRegistNo(registNo);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw e;
//		}
//	}
	/**
	 * 通赔收回查询
	 * @param dbManager
	 * @param UserDto：操作员信息
	 * @param registNo：报案号
	 * @throws Exception
	 * @return ArrayList
	 */
//	public Page regainQuery(String conditions, UserDto userDto, int pageNo, int pageSize) throws Exception {
//		String giveComcode = "";
//		giveComcode = this.getLevelTwoComCode(userDto.getComCode());
//		conditions += " AND GIVECOMCODE = '" + giveComcode + "'";
//		QueryRule queryRule = QueryRule.getInstance().addSql(conditions);
//		Page page = prpLgeneralClaimTaskService.findPrpLgeneralClaimTask(queryRule, pageNo, pageSize);
//		return page;
//
//	}

	/**
	 * 通赔历史查询
	 * @param dbManager
	 * @param UserDto：操作员信息
	 * @param registNo：报案号
	 * @throws Exception
	 * @return ArrayList
	 */
	/**
	 * 通赔历史查询
	 * @param registNo：报案号
	 * @return Page
	 */
//	public Page historyQuery(UserDto userDto, String generalType, String conditions, int pageNo, int rowsPerPage) throws Exception {
//		String comCode = "";
//		comCode = this.getLevelTwoComCode(userDto.getComCode());
//		if ("all".equals(generalType)) {
//			conditions += " AND (GIVECOMCODE = '" + comCode + "' OR RECEIVECOMCODE = '" + comCode + "')";
//		} else if ("give".equals(generalType)) {
//			conditions += " AND GIVECOMCODE = '" + comCode + "'";
//		} else {
//			conditions += " AND RECEIVECOMCODE = '" + comCode + "'";
//		}
//		Page page = prpLgeneralClaimTaskLogService.findByConditions(conditions, pageNo, rowsPerPage);
//		return page;
//	}
	/**
	 * 根据当前机构取得该机构的二级机构
	 * @param workFlowDto 理赔工作流流程处理处理任务取消的对象
	 * @throws SQLException
	 * @throws Exception
	 * @return 无
	 */
//	public String getLevelTwoComCode(String comCode) throws SQLException, Exception {
//		String levelTwoComCOde = "";
//		if ("00".equals(comCode.substring(0, 2))) {
//			levelTwoComCOde = "0000990000";
//		} else {
//			StringBuffer buffer = new StringBuffer(200);
//			buffer.append("SELECT COMCODE FROM (");
//			buffer.append("SELECT COMCODE,COMLEVEL ");
//			buffer.append("FROM PRPDCOMPANY ");
//			buffer.append("WHERE 1=1 ");
//			buffer.append("START WITH COMCODE = '");
//			buffer.append(comCode);
//			buffer.append("' CONNECT BY PRIOR UPPERCOMCODE = COMCODE ");
//			buffer.append("AND PRIOR COMCODE <> UPPERCOMCODE ");
//			buffer.append(") WHERE COMLEVEL = '2'");
//			Object obj = super.getSession().createSQLQuery(buffer.toString()).uniqueResult();
//			if (obj != null) {
//				levelTwoComCOde = obj.toString();
//			}
//		}
//		return levelTwoComCOde;
//	}

	/**
	 * 根据报案号查询通赔待处理任务
	 */
	public List<PrpLgeneralClaimTask> queryByRegistNo(String registNo) throws Exception {
		return this.prpLgeneralClaimTaskService.findPrpLgeneralClaimTask(QueryRule.getInstance().addSql(" REGISTNO = '" + registNo + "'"));
	}

	public List<PrpLgeneralClaimTaskLog> queryHistoryByRegistNo(String registNo) throws Exception {
		return this.prpLgeneralClaimTaskLogService.findPrpLgeneralClaimTaskLog(QueryRule.getInstance().addSql(" REGISTNO = '" + registNo + "'"));
	}

	public PrpLregistService getPrpLregistService() {
		return prpLregistService;
	}

	public void setPrpLregistService(PrpLregistService prpLregistService) {
		this.prpLregistService = prpLregistService;
	}

	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}

	public PrpLcompensateService getPrpLcompensateService() {
		return prpLcompensateService;
	}

	public void setPrpLcompensateService(PrpLcompensateService prpLcompensateService) {
		this.prpLcompensateService = prpLcompensateService;
	}

	public PrpLprepayService getPrpLprepayService() {
		return prpLprepayService;
	}

	public void setPrpLprepayService(PrpLprepayService prpLprepayService) {
		this.prpLprepayService = prpLprepayService;
	}

	public PrpLrecaseService getPrpLrecaseService() {
		return prpLrecaseService;
	}

	public void setPrpLrecaseService(PrpLrecaseService prpLrecaseService) {
		this.prpLrecaseService = prpLrecaseService;
	}

	public PrpLgeneralClaimTaskService getPrpLgeneralClaimTaskService() {
		return prpLgeneralClaimTaskService;
	}

	public void setPrpLgeneralClaimTaskService(PrpLgeneralClaimTaskService prpLgeneralClaimTaskService) {
		this.prpLgeneralClaimTaskService = prpLgeneralClaimTaskService;
	}

	public PrpLgeneralClaimTaskLogService getPrpLgeneralClaimTaskLogService() {
		return prpLgeneralClaimTaskLogService;
	}

	public void setPrpLgeneralClaimTaskLogService(PrpLgeneralClaimTaskLogService prpLgeneralClaimTaskLogService) {
		this.prpLgeneralClaimTaskLogService = prpLgeneralClaimTaskLogService;
	}

	public PrpDcompanyService getPrpDcompanyService() {
		return prpDcompanyService;
	}

	public void setPrpDcompanyService(PrpDcompanyService prpDcompanyService) {
		this.prpDcompanyService = prpDcompanyService;
	}

	public SwfLogService getSwfLogService() {
		return swfLogService;
	}

	public void setSwfLogService(SwfLogService swfLogService) {
		this.swfLogService = swfLogService;
	}

}
