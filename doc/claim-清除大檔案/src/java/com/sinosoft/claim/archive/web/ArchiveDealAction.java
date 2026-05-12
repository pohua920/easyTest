package com.sinosoft.claim.archive.web;

import ins.framework.web.Struts2Action;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.archive.service.facade.ArchiveService;
import com.sinosoft.claim.archive.util.ArchiveViewHelper;
import com.sinosoft.claim.archive.vo.ArchiveDto; //import com.sinosoft.claim.dto.custom.ArchiveDto;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpLDocArchive;
import com.sinosoft.claim.schema.model.PrpLDocArchiveLog;
import com.sinosoft.claim.schema.model.PrpLDocArchiveLogId;
import com.sinosoft.sysframework.common.datatype.DateTime;
/**
 * 理赔实体资料归档
 * @author 中科软
 *
 */
public class ArchiveDealAction extends Struts2Action {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6589730218889386334L;
	/** 资料归档调阅接口service */
	private ArchiveService archiveService;
	/** 资料归档viewHelper */
	private ArchiveViewHelper archiveViewHelper;

	/**
	 * 归档处理
	 * @return
	 * @throws Exception
	 */
	public String archiveDeal() throws Exception {
		HttpServletRequest request = getRequest();
		String editType = request.getParameter("editType");

		UserDto userDto = (UserDto) request.getSession().getAttribute("user");
		String forward = ""; // 向前
		if ("archive".equals(editType)) {// 资料归档操作
			String[] claimNos = request.getParameterValues("checkboxSelect");
			for (int i = 0; i < claimNos.length; i++) {
				ArchiveDto archiveDto = archiveViewHelper.viewToDto(request, claimNos[i]);
				this.archiveService.toArchive(archiveDto.getPrpLDocArchive(), archiveDto.getPrpLDocArchiveLog());
			}
			request.setAttribute("content", "任務提交成功！");
			forward = "success";
		} else if ("applySave".equals(editType)) {// 调阅申请
			int serialNo = Integer.parseInt(request.getParameter("serialNo"));// 序号
			String status = request.getParameter("status");// 资料状态
			String claimNo = request.getParameter("claimNo");// 赔案号
			String applyReason = request.getParameter("applyReason");// 调阅理由
			String remark = request.getParameter("remark");// 备注
			double sumDutyPaid = Double.parseDouble(request.getParameter("sumDutyPaid"));// 标的赔款金额
			DateTime nowdate = new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY);
			DateTime estimateReturnDate = null;// 预计归档日期
			String estimatePeriod = request.getParameter("estimatePeriod");// 预计归档周期
			if ("1".equals(estimatePeriod)) {
				estimateReturnDate = nowdate.addDay(7);
			} else if ("2".equals(estimatePeriod)) {
				estimateReturnDate = nowdate.addMonth(1);
			} else if ("3".equals(estimatePeriod)) {
				estimateReturnDate = nowdate.addMonth(3);
			}

			if (!"1".equals(status)) {
				PrpLDocArchive prpLDocArchiveDto = archiveViewHelper.viewToDto(request);
				String applicantName = prpLDocArchiveDto.getApplicantName();
				String content = "申請失敗！賠案已經被其他理賠人員調閱中，調閱人員：" + applicantName;
				request.setAttribute("content", content);
				request.setAttribute("message", "faile");
				forward = "faile";
				return forward;
			}

			// 更新资料归档调阅主表信息
			PrpLDocArchive prpLDocArchive = this.archiveService.findByPrimaryKey(claimNo);
			prpLDocArchive.setClaimNo(claimNo);// 赔案号
			prpLDocArchive.setSumDutyPaid(sumDutyPaid);// 标的赔款金额
			prpLDocArchive.setStatus("2");// 资料状态标志 0 未归档、1 已归档、2 调阅审核中、3
												// 调阅中、4 理赔处理中
			prpLDocArchive.setApplicantCode(userDto.getUserCode());// 调阅申请人代码
			prpLDocArchive.setApplicantName(userDto.getUserName());// 调阅申请人名称
			prpLDocArchive.setApplyDate(nowdate);// 申请调阅日期
			prpLDocArchive.setEstimatePeriod(request.getParameter("estimatePeriod"));// 预计归档周期：1-一周，2-一月，3-一季
			prpLDocArchive.setEstimateReturnDate(estimateReturnDate);// 预计归档日期

			// 向资料归档调阅日志表新增一条信息
			PrpLDocArchiveLogId prpLDocArchiveLogId = new PrpLDocArchiveLogId();
			prpLDocArchiveLogId.setClaimNo(claimNo);
			prpLDocArchiveLogId.setSerialNo(serialNo);
			PrpLDocArchiveLog prpLDocArchiveLog2 = this.archiveService.findByPrimaryKey(prpLDocArchiveLogId);// 获得资料归档调阅日志表中数据，获取老数据。
			PrpLDocArchiveLog prpLDocArchiveLog = new PrpLDocArchiveLog();// 重新new一个现在要插入的对象。
			String nodeNoAndName = this.archiveService.getPower(request, userDto);
			String[] nodeNoAndNames = nodeNoAndName.split(",");
			prpLDocArchiveLog.getId().setClaimNo(prpLDocArchiveLog2.getId().getClaimNo());
			prpLDocArchiveLog.getId().setSerialNo(serialNo + 1);
			prpLDocArchiveLog.setStatus("2");
			prpLDocArchiveLog.setApplyReason(applyReason);
			prpLDocArchiveLog.setEstimatePeriod(estimatePeriod);
			prpLDocArchiveLog.setEstimateReturnDate(estimateReturnDate);
			prpLDocArchiveLog.setRemark(remark);
			prpLDocArchiveLog.setOperatorCode(userDto.getUserCode());
			prpLDocArchiveLog.setOperatorName(userDto.getUserName());
			prpLDocArchiveLog.setOperatorDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
			prpLDocArchiveLog.setModelNo(Integer.parseInt(nodeNoAndNames[0]));
			prpLDocArchiveLog.setNodeNo(Integer.parseInt(nodeNoAndNames[1]));
			prpLDocArchiveLog.setNodeName(nodeNoAndNames[2]);
			prpLDocArchiveLog.setApplyDeferno(0);
			prpLDocArchiveLog.setApplyDeferPeriod("");
			prpLDocArchiveLog.setReturnDate(new DateTime());
			prpLDocArchiveLog.setUndwrtFlag("");
			prpLDocArchiveLog.setRegistNo(prpLDocArchiveLog2.getRegistNo());
			prpLDocArchiveLog.setPolicyNo(prpLDocArchiveLog2.getPolicyNo());
			prpLDocArchiveLog.setComcode(prpLDocArchiveLog2.getComcode());
			prpLDocArchiveLog.setInsuredCode(prpLDocArchiveLog2.getInsuredCode());
			prpLDocArchiveLog.setInsuredName(prpLDocArchiveLog2.getInsuredName());
			prpLDocArchiveLog.setEndCaseDate(prpLDocArchiveLog2.getEndCaseDate());
			prpLDocArchiveLog.setSumDutyPaid(prpLDocArchiveLog2.getSumDutyPaid());

			try {
				this.archiveService.update(prpLDocArchive, prpLDocArchiveLog);
			} catch (Exception e) {
				e.printStackTrace();
				PrpLDocArchive prpLDocArchiveDto2 = archiveViewHelper.viewToDto(request);
				String applicantName = prpLDocArchiveDto2.getApplicantName();
				String content = "申請失敗！賠案已經被其他理賠人員調閱中，調閱人員：" + applicantName;
				request.setAttribute("content", content);
				request.setAttribute("message", "faile");
				forward = "faile";
				return forward;
			}

			forward = "success";
		} else if ("retrival".equals(editType)) {// 资料调阅
			int serialNo = Integer.parseInt(request.getParameter("serialNo"));// 序号
			String claimNo = request.getParameter("claimNo");// 赔案号
			String estimatePeriod = request.getParameter("estimatePeriod");// 预计归档周期
			DateTime nowdate = new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY);
			DateTime estimateReturnDate = null;// 预计归档日期
			if ("1".equals(estimatePeriod)) {
				estimateReturnDate = nowdate.addDay(7);
			} else if ("2".equals(estimatePeriod)) {
				estimateReturnDate = nowdate.addMonth(1);
			} else if ("3".equals(estimatePeriod)) {
				estimateReturnDate = nowdate.addMonth(3);
			}

			// 更新资料归档调阅主表信息
			PrpLDocArchive prpLDocArchive = this.archiveService.findByPrimaryKey(claimNo);
			prpLDocArchive.setStatus("4");// 资料状态：0 未归档、1 已归档、2 调阅审核中、3 调阅中、4
												// 理赔处理中
			prpLDocArchive.setStartReviewDate(nowdate);// 调阅时间
			prpLDocArchive.setEstimateReturnDate(estimateReturnDate);// 预计归档日期

			// 向资料归档调阅日志表新增一条信息
			PrpLDocArchiveLogId prpLDocArchiveLogId = new PrpLDocArchiveLogId();
			prpLDocArchiveLogId.setClaimNo(claimNo);
			prpLDocArchiveLogId.setSerialNo(serialNo);
			PrpLDocArchiveLog prpLDocArchiveLogDtoOld = this.archiveService.findByPrimaryKey(prpLDocArchiveLogId);// 获得资料归档调阅日志表中数据
			PrpLDocArchiveLog prpLDocArchiveLogDto = new PrpLDocArchiveLog(prpLDocArchiveLogDtoOld);
			prpLDocArchiveLogDto.getId().setSerialNo(serialNo + 1);// 序号
			prpLDocArchiveLogDto.setStatus("4");// 资料状态：0 未归档、1 已归档、2 调阅审核中、3
												// 调阅中、4 理赔处理中
			prpLDocArchiveLogDto.setEstimateReturnDate(estimateReturnDate);// 预计归档日期
			prpLDocArchiveLogDto.setOperatorCode(userDto.getUserCode());
			prpLDocArchiveLogDto.setOperatorName(userDto.getUserName());
			prpLDocArchiveLogDto.setOperatorDate(nowdate);

			this.archiveService.update(prpLDocArchive, prpLDocArchiveLogDto);
			String content = "提交成功！";
			request.setAttribute("content", content);
			forward = "success";
		} else if ("extension".equals(editType)) {// 调阅延期
			String claimNo = request.getParameter("claimNo");// 赔案号
			DateTime nowdate = new DateTime(request.getParameter("estimateReturnDate"));
			DateTime estimateReturnDate = null;// 预计归档日期
			String applyDeferPeriod = request.getParameter("applyDeferPeriod");// 延期周期
			if ("1".equals(applyDeferPeriod)) {
				estimateReturnDate = nowdate.addDay(7);
			} else if ("2".equals(applyDeferPeriod)) {
				estimateReturnDate = nowdate.addMonth(1);
			} else if ("3".equals(applyDeferPeriod)) {
				estimateReturnDate = nowdate.addMonth(3);
			}

			// 更新资料归档调阅主表信息
			PrpLDocArchive prpLDocArchive = this.archiveService.findByPrimaryKey(claimNo);
			prpLDocArchive.setApplyDeferno(1);// 申请延期次数
			prpLDocArchive.setApplyDeferPeriod(applyDeferPeriod);// 延期周期：1-一周，2-一月，3-一季
			prpLDocArchive.setEstimateReturnDate(estimateReturnDate);// 预计归档日期

			// 向资料归档调阅日志表新增一条信息
			int serialNo = this.archiveService.getCount(claimNo);
			PrpLDocArchiveLogId prpLDocArchiveLogId = new PrpLDocArchiveLogId();
			prpLDocArchiveLogId.setClaimNo(claimNo);
			prpLDocArchiveLogId.setSerialNo(serialNo);
			PrpLDocArchiveLog prpLDocArchiveLogOld = this.archiveService.findByPrimaryKey(prpLDocArchiveLogId);// 获得资料归档调阅日志表中数据
			PrpLDocArchiveLog prpLDocArchiveLog = new PrpLDocArchiveLog(prpLDocArchiveLogOld);
			prpLDocArchiveLog.getId().setSerialNo(serialNo + 1);// 序号
			prpLDocArchiveLog.setApplyDeferno(1);// 申请延期次数
			prpLDocArchiveLog.setApplyDeferPeriod(applyDeferPeriod);// 延期周期
			prpLDocArchiveLog.setEstimateReturnDate(estimateReturnDate);// 预计归档日期
			prpLDocArchiveLog.setOperatorCode(userDto.getUserCode());
			prpLDocArchiveLog.setOperatorName(userDto.getUserName());
			prpLDocArchiveLog.setOperatorDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
			this.archiveService.update(prpLDocArchive, prpLDocArchiveLog);
			String content = "申請延期成功！";
			request.setAttribute("content", content);
			forward = "success";
		} else if ("toarchive".equals(editType)) {// 调阅归档
			int serialNo = Integer.parseInt(request.getParameter("serialNo"));// 序号
			String claimNo = request.getParameter("claimNo");// 赔案号
			String returnDate = request.getParameter("returnDate");// 实际归档日期

			// 更新资料归档调阅主表信息
			PrpLDocArchive prpLDocArchive = this.archiveService.findByPrimaryKey(claimNo);
			prpLDocArchive.setStatus("1");// 资料状态：0 未归档、1 已归档、2 调阅审核中、3 调阅中、4
											// 理赔处理中
			prpLDocArchive.setApplicantCode("");// 调阅申请人代码
			prpLDocArchive.setApplicantName("");// 调阅申请人名称
			prpLDocArchive.setApplyDate(new DateTime());// 申请调阅日期
			prpLDocArchive.setStartReviewDate(new DateTime());// 调阅时间
			prpLDocArchive.setEstimatePeriod("");// 预计归档周期
			prpLDocArchive.setApplyDeferno(0);// 申请延期次数
			prpLDocArchive.setApplyDeferPeriod("");// 延期周期：1-一周，2-一月，3-一季
			prpLDocArchive.setEstimateReturnDate(new DateTime());// 预计归档日期

			// 向资料归档调阅日志表新增一条信息
			PrpLDocArchiveLogId prpLDocArchiveLogId = new PrpLDocArchiveLogId();
			prpLDocArchiveLogId.setClaimNo(claimNo);
			prpLDocArchiveLogId.setSerialNo(serialNo);
			PrpLDocArchiveLog prpLDocArchiveLogOld = this.archiveService.findByPrimaryKey(prpLDocArchiveLogId);// 获得资料归档调阅日志表中数据
			PrpLDocArchiveLog prpLDocArchiveLog = new PrpLDocArchiveLog(prpLDocArchiveLogOld);
			prpLDocArchiveLog.getId().setSerialNo(serialNo + 1);// 序号
			prpLDocArchiveLog.setStatus("1");// 资料状态：0 未归档、1 已归档、2 调阅审核中、3 调阅中、4
												// 理赔处理中
			prpLDocArchiveLog.setReturnDate(new DateTime(returnDate));
			prpLDocArchiveLog.setOperatorCode(userDto.getUserCode());
			prpLDocArchiveLog.setOperatorName(userDto.getUserName());
			prpLDocArchiveLog.setOperatorDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));

			this.archiveService.update(prpLDocArchive, prpLDocArchiveLog);
			String content = "提交成功！";
			request.setAttribute("content", content);
			forward = "success";
		} else if ("pass".equals(editType)) {// 审核通过
			int serialNo = Integer.parseInt(request.getParameter("serialNo"));// 序号
			String claimNo = request.getParameter("claimNo");// 赔案号
			String estimatePeriod = request.getParameter("estimatePeriod");// 预计归档周期
			DateTime nowdate = new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY);
			DateTime estimateReturnDate = null;// 预计归档日期
			if ("1".equals(estimatePeriod)) {
				estimateReturnDate = nowdate.addDay(7);
			} else if ("2".equals(estimatePeriod)) {
				estimateReturnDate = nowdate.addMonth(1);
			} else if ("3".equals(estimatePeriod)) {
				estimateReturnDate = nowdate.addMonth(3);
			}

			// 更新资料归档调阅主表信息
			PrpLDocArchive prpLDocArchiveDto = this.archiveService.findByPrimaryKey(claimNo);
			prpLDocArchiveDto.setStatus("3");// 资料状态：0 未归档、1 已归档、2 调阅审核中、3 调阅中、4
												// 理赔处理中
			prpLDocArchiveDto.setStartReviewDate(nowdate);// 调阅时间
			prpLDocArchiveDto.setEstimateReturnDate(estimateReturnDate);// 预计归档日期

			// 向资料归档调阅日志表新增一条信息
			PrpLDocArchiveLogId prpLDocArchiveLogId = new PrpLDocArchiveLogId();
			prpLDocArchiveLogId.setClaimNo(claimNo);
			prpLDocArchiveLogId.setSerialNo(serialNo);
			PrpLDocArchiveLog prpLDocArchiveLog2 = this.archiveService.findByPrimaryKey(prpLDocArchiveLogId);// 获得资料归档调阅日志表中数据,获取老数据
			PrpLDocArchiveLog prpLDocArchiveLog = new PrpLDocArchiveLog();// 重新new一个现在要插入的对象。
			prpLDocArchiveLog.getId().setClaimNo(prpLDocArchiveLog2.getId().getClaimNo());
			prpLDocArchiveLog.getId().setSerialNo(serialNo + 1);// 序号
			prpLDocArchiveLog.setStatus("3");// 资料状态：0 未归档、1 已归档、2 调阅审核中、3 调阅中、4
												// 理赔处理中
			prpLDocArchiveLog.setEstimateReturnDate(estimateReturnDate);// 预计归档日期
			prpLDocArchiveLog.setUndwrtFlag("通過");// 审核结论
			prpLDocArchiveLog.setOperatorCode(userDto.getUserCode());
			prpLDocArchiveLog.setOperatorName(userDto.getUserName());
			prpLDocArchiveLog.setOperatorDate(nowdate);
			prpLDocArchiveLog.setNodeNo(11);// 审核节点
			prpLDocArchiveLog.setNodeName("審核通過");// 审核节点名称
			prpLDocArchiveLog.setApplyReason(prpLDocArchiveLog2.getApplyReason());
			prpLDocArchiveLog.setEstimatePeriod(prpLDocArchiveLog2.getEstimatePeriod());
			prpLDocArchiveLog.setRemark(prpLDocArchiveLog2.getRemark());
			prpLDocArchiveLog.setModelNo(prpLDocArchiveLog2.getModelNo());
			prpLDocArchiveLog.setApplyDeferno(prpLDocArchiveLog2.getApplyDeferno());
			prpLDocArchiveLog.setApplyDeferPeriod(prpLDocArchiveLog2.getApplyDeferPeriod());
			prpLDocArchiveLog.setReturnDate(prpLDocArchiveLog2.getReturnDate());
			prpLDocArchiveLog.setRegistNo(prpLDocArchiveLog2.getRegistNo());
			prpLDocArchiveLog.setPolicyNo(prpLDocArchiveLog2.getPolicyNo());
			prpLDocArchiveLog.setComcode(prpLDocArchiveLog2.getComcode());
			prpLDocArchiveLog.setInsuredCode(prpLDocArchiveLog2.getInsuredCode());
			prpLDocArchiveLog.setInsuredName(prpLDocArchiveLog2.getInsuredName());
			prpLDocArchiveLog.setEndCaseDate(prpLDocArchiveLog2.getEndCaseDate());
			prpLDocArchiveLog.setSumDutyPaid(prpLDocArchiveLog2.getSumDutyPaid());

			this.archiveService.update(prpLDocArchiveDto, prpLDocArchiveLog);
			String content = "提交成功！";
			request.setAttribute("content", content);
			forward = "success";
		} else if ("nopass".equals(editType)) {// 审核不通过
			int serialNo = Integer.parseInt(request.getParameter("serialNo"));// 序号
			String claimNo = request.getParameter("claimNo");// 赔案号

			// 更新资料归档调阅主表信息
			PrpLDocArchive prpLDocArchive = this.archiveService.findByPrimaryKey(claimNo);
			prpLDocArchive.setStatus("1");// 资料状态：0 未归档、1 已归档、2 调阅审核中、3 调阅中、4
											// 理赔处理中
			prpLDocArchive.setApplicantCode("");// 调阅申请人代码
			prpLDocArchive.setApplicantName("");// 调阅申请人名称
			prpLDocArchive.setApplyDate(new DateTime());// 申请调阅日期
			prpLDocArchive.setStartReviewDate(new DateTime());// 调阅时间
			prpLDocArchive.setEstimatePeriod("");// 预计归档周期
			prpLDocArchive.setApplyDeferno(0);// 申请延期次数
			prpLDocArchive.setApplyDeferPeriod("");// 延期周期：1-一周，2-一月，3-一季
			prpLDocArchive.setEstimateReturnDate(new DateTime());// 预计归档日期

			// 向资料归档调阅日志表新增一条信息
			PrpLDocArchiveLogId prpLDocArchiveLogId = new PrpLDocArchiveLogId();
			prpLDocArchiveLogId.setClaimNo(claimNo);
			prpLDocArchiveLogId.setSerialNo(serialNo);
			PrpLDocArchiveLog prpLDocArchiveLogOld = this.archiveService.findByPrimaryKey(prpLDocArchiveLogId);// 獲得資料歸檔調閱日志表中數據
			PrpLDocArchiveLog prpLDocArchiveLog = new PrpLDocArchiveLog(prpLDocArchiveLogOld);
			prpLDocArchiveLog.getId().setSerialNo(serialNo + 1);// 序号
			prpLDocArchiveLog.setStatus("1");// 资料状态：0 未归档、1 已归档、2 调阅审核中、3 调阅中、4
												// 理赔处理中
			prpLDocArchiveLog.setUndwrtFlag("不通過");// 审核结论
			prpLDocArchiveLog.setOperatorCode(userDto.getUserCode());
			prpLDocArchiveLog.setOperatorName(userDto.getUserName());
			prpLDocArchiveLog.setOperatorDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
			prpLDocArchiveLog.setModelNo(0);// 模版号
			prpLDocArchiveLog.setNodeNo(0);// 审核节点
			prpLDocArchiveLog.setNodeName("");// 审核节点名称

			this.archiveService.update(prpLDocArchive, prpLDocArchiveLog);
			String content = "提交成功！";
			request.setAttribute("content", content);
			forward = "success";
		} else if ("submit".equals(editType)) {
			this.archiveService.submit(request);
			request.setAttribute("claimNo", request.getParameter("claimNo"));
			request.setAttribute("serialNo", request.getParameter("serialNo"));
			request.setAttribute("modelNo", request.getParameter("modelNo"));
			forward = editType;
		} else if ("submitDeal".equals(editType)) {
			int serialNo = Integer.parseInt(request.getParameter("serialNo"));// 序号
			int nodeNo = Integer.parseInt(request.getParameter("NodeNo"));// 节点号
			String nodeName = request.getParameter("NodeName");// 节点名称
			String claimNo = request.getParameter("claimNo");// 赔案号

			// 向资料归档调阅日志表新增一条信息
			PrpLDocArchiveLogId prpLDocArchiveLogId = new PrpLDocArchiveLogId();
			prpLDocArchiveLogId.setClaimNo(claimNo);
			prpLDocArchiveLogId.setSerialNo(serialNo);
			PrpLDocArchiveLog prpLDocArchiveLogOld = this.archiveService.findByPrimaryKey(prpLDocArchiveLogId);// 获得资料归档调阅日志表中数据
			PrpLDocArchiveLog prpLDocArchiveLog = new PrpLDocArchiveLog(prpLDocArchiveLogOld);
			prpLDocArchiveLog.getId().setSerialNo(serialNo + 1);// 序号
			prpLDocArchiveLog.setUndwrtFlag("提交高階");// 审核结论
			prpLDocArchiveLog.setOperatorCode(userDto.getUserCode());
			prpLDocArchiveLog.setOperatorName(userDto.getUserName());
			prpLDocArchiveLog.setOperatorDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
			prpLDocArchiveLog.setNodeNo(nodeNo);// 审核节点
			prpLDocArchiveLog.setNodeName(nodeName);// 审核节点名称

			this.archiveService.update(null, prpLDocArchiveLog);
			String content = "提交成功！";
			request.setAttribute("content", content);
			forward = "success";
		}
		return forward;
	}

	public ArchiveService getArchiveService() {
		return archiveService;
	}

	public void setArchiveService(ArchiveService archiveService) {
		this.archiveService = archiveService;
	}

	public ArchiveViewHelper getArchiveViewHelper() {
		return archiveViewHelper;
	}

	public void setArchiveViewHelper(ArchiveViewHelper archiveViewHelper) {
		this.archiveViewHelper = archiveViewHelper;
	}

}
