package com.sinosoft.claim.archive.service.spring;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.archive.service.facade.ArchiveService;
import com.sinosoft.claim.archive.vo.ArchiveDto;
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.PrpDcompanyService;
import com.sinosoft.claim.common.service.facade.PrpDuserService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpDcompany;
import com.sinosoft.claim.schema.model.PrpDuser;
import com.sinosoft.claim.schema.model.PrpLDocArchive;
import com.sinosoft.claim.schema.model.PrpLDocArchiveLog;
import com.sinosoft.claim.schema.model.PrpLDocArchiveLogId;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.SwfNode;
import com.sinosoft.claim.schema.model.SwfNodeId;
import com.sinosoft.claim.schema.model.SwfPath;
import com.sinosoft.claim.schema.model.UtiUserGrade;
import com.sinosoft.claim.schema.model.UtiUwLevel;
import com.sinosoft.claim.schema.service.facade.PrpLDocArchiveLogService;
import com.sinosoft.claim.schema.service.facade.PrpLDocArchiveService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.SwfNodeService;
import com.sinosoft.claim.schema.service.facade.SwfPathService;
import com.sinosoft.claim.schema.service.facade.UtiUserGradeService;
import com.sinosoft.claim.schema.service.facade.UtiUwLevelService;
import com.sinosoft.sysframework.common.util.StringUtils;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * 资料归档调阅数据库管理对象
 * <p>
 * Title: 车险理赔资料归档调阅数据管理
 * </p>
 * <p>
 * Description: 车险理赔资料归档调阅数据管理
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * @author 中科软
 * @version 1.0
 */
public class ArchiveServiceSpringImpl extends GenericDaoHibernate<ArchiveDto, String> implements ArchiveService {
	/** 资料归档调阅服務 */
	private PrpLDocArchiveService prpLDocArchiveService;
	/** 资料归档调阅日誌服務 */
	private PrpLDocArchiveLogService prpLDocArchiveLogService;
	/** 立案基礎對象服務 */
	private PrpLclaimService prpLclaimService;
	/** 核賠等級服務 */
	private UtiUwLevelService utiUwLevelService;
	/** 機構服務 */
	private PrpDcompanyService prpDcompanyService;
	/** 用戶服務 */
	private PrpDuserService prpDuserService;
	/** 工作流節點服務 */
	private SwfNodeService swfNodeService;
	/** 用戶等級服務 */
	private UtiUserGradeService utiUserGradeService;
	/** 工作流流轉路徑服務 */
	private SwfPathService swfPathService;

	/**
	 * 插入一条数据
	 * @param PrpLDocArchive prpLDocArchive
	 * @throws Exception
	 */
	public void insert(PrpLDocArchive prpLDocArchive) throws Exception {
		this.prpLDocArchiveService.save(prpLDocArchive);
	}

	/**
	 * 按主键更新一条数据(主键本身无法变更)
	 * @param prpLDocArchive prpLDocArchive
	 * @throws Exception
	 */
	public void update(PrpLDocArchive prpLDocArchive, PrpLDocArchiveLog prpLDocArchiveLog) throws Exception {
		// 插入归档调阅操作日志信息
		if (prpLDocArchive != null) {
			// 更新资料归档调阅主表信息
			this.prpLDocArchiveService.update(prpLDocArchive);
		}
		this.prpLDocArchiveLogService.save(prpLDocArchiveLog);
	}

	/**
	 * 根據條件查詢
	 * @param conditions
	 * @return
	 * @throws Exception
	 */
	public List<PrpLDocArchive> findByConditions(String conditions) throws Exception {
		return findByConditions(conditions, 0, 0);
	}

	/**
	 * 按条件从PrpLDocArchive表中查询多条数据
	 * @param conditions 查询条件
	 * @param pageNo 页码
	 * @param rowsPerPage 每页显示的行数
	 * @throws Exception
	 * @return Collection 实体资料对象
	 */
	@SuppressWarnings("unchecked")
	public List<PrpLDocArchive> findByConditions(String conditions, int pageNo, int rowsPerPage) throws Exception {
		return this.findPageByConditions(conditions, pageNo, rowsPerPage).getResult();
	}

	/**
	 * 按主键查找一条数据
	 * @param claimNo 赔案号
	 * @throws Exception
	 * @return PrpLDocArchiveDto 实体资料对象
	 */
	public PrpLDocArchive findByPrimaryKey(String claimNo) throws Exception {
		return this.prpLDocArchiveService.findPrpLDocArchive(claimNo);
	}

	/**
	 * 查询满足模糊查询条件的记录数
	 * @param conditions 查询条件
	 * @return 满足模糊查询条件的记录数
	 * @throws Exception
	 */
	public int getCount(String claimNo) throws Exception {
		int count = -1;
		String statement = "SELECT count(*) FROM PrpLDocArchiveLog WHERE ClaimNo='" + claimNo + "'";
		count = (int) HibernateUtils.getCountbyCountSql(super.getSession(), statement);
		return count;
	}

	/**
	 * 资料归档操作
	 * @param prpLDocArchiveDto 资料归档调阅主表
	 * @param prpLDocArchiveLogDto 资料归档调阅日志表
	 * @throws Exception
	 * @throws Exception
	 */
	@Override
	public void toArchive(PrpLDocArchive prpLDocArchive, PrpLDocArchiveLog prpLDocArchiveLog) throws Exception {
		prpLDocArchive.setStatus("1");// 资料状态 设置为已归档
		this.prpLDocArchiveService.update(prpLDocArchive);
		this.prpLDocArchiveLogService.save(prpLDocArchiveLog);
	}

	/**
	 * 按主键查找一条数据
	 * @param claimNo 赔案号
	 * @throws Exception
	 * @return PrpLDocArchiveDto 实体资料对象
	 */
	@Override
	public PrpLDocArchiveLog findByPrimaryKey(PrpLDocArchiveLogId prpLDocArchiveLogId) throws Exception {
		PrpLDocArchiveLog prpLDocArchiveLog = null;
		prpLDocArchiveLog = this.prpLDocArchiveLogService.findPrpLDocArchiveLog(prpLDocArchiveLogId);
		return prpLDocArchiveLog;
	}

	/**
	 * 获取审核人员信息
	 * @param request
	 * @param userDto
	 * @throws Exception
	 */
	@Override
	public String getPower(HttpServletRequest request, UserDto userDto) throws Exception {
		String claimNo = StringUtils.rightTrim(request.getParameter("claimNo"));
		PrpLclaim prpLclaimDto = null;
		prpLclaimDto = this.prpLclaimService.findPrpLclaim(claimNo);
		String classCode = prpLclaimDto.getClassCode();
		String comCode = userDto.getComCode();
		String userCode = userDto.getUserCode();
		String comLevel = userDto.getComLevel();
		// 查询该登录用户是否是核赔员
		String conditions = "comCode='" + comCode + "' AND userCode='" + userCode + "' AND VALIDSTATUS='1' AND (uwtype='C' or uwtype='Y') ORDER BY NODENO";
		List<UtiUwLevel> list = this.utiUwLevelService.findByConditions(conditions);
		boolean flag = false;
		if (CommonUtils.isEmpty(list)) {// 是核赔员
			flag = true;
		}

		int nodeNo = 0;
		if (flag) {
			// 申请人员是核赔员，审核任务提交给当前机构上一级核赔人员
			String conditions2 = "comCode='" + comCode + "' AND userCode='" + userCode + "' AND VALIDSTATUS='1' AND (uwtype='C' or uwtype='Y') ORDER BY NODENO";
			List<UtiUwLevel> list2 = this.utiUwLevelService.findByConditions(conditions2);
			Iterator<UtiUwLevel> it = list2.iterator();
			while (it.hasNext()) {
				UtiUwLevel utiUwLevel = (UtiUwLevel) it.next();
				if (utiUwLevel.getId().getNodeNo() > nodeNo) {
					nodeNo = utiUwLevel.getId().getNodeNo();
				}
			}
			nodeNo = nodeNo + 1;
		} else {
			// 申请人员不是核赔员，审核任务提交给当前机构最低级核赔人员
			nodeNo = 2;
		}

		Set<String> set = new HashSet<String>();
		int maxNodeNo = 0;
		int modelNo = 0;
		int count = 0;
		while (true) {
			count++;
			if (count > 100) {
				// 增加这个判断，防止数据库配置错误，导致系统进入死循环
				throw new UserException(-98, -1003, "系統配置錯誤", "系統配置錯誤，請聯繫系統管理員。");
			}
			if ("D".equals(ConstantCodes.carClassMap.get(classCode))) {// 车险
				maxNodeNo = 12;
				modelNo = 31;
			} else {// 非车险
				maxNodeNo = 13;
				modelNo = 40;
			}
			if (nodeNo >= maxNodeNo) {// 核赔员是当前机构最高级别
				if ("1".equals(comLevel)) {// 当前结构为最高级别
					nodeNo = maxNodeNo;
					String conditions3 = "COMCODE='" + comCode + "' AND VALIDSTATUS='1' AND (UWTYPE='C' OR UWTYPE='Y') AND NODENO='" + nodeNo + "' AND (CLASSCODE LIKE '%" + classCode + "%' or CLASSCODE='*')";
					List<UtiUwLevel> list3 = this.utiUwLevelService.findByConditions(conditions3);
					Iterator<UtiUwLevel> it = list3.iterator();
					while (it.hasNext()) {
						UtiUwLevel utiUwLevel = (UtiUwLevel) it.next();
						set.add(utiUwLevel.getId().getUserCode());
					}
					break;
				} else {// 当前机构不是最高级别，将审核任务提交到上一级最低核赔员处
					nodeNo = 2;// 最低核赔级别
					PrpDcompany prpDcompanyDto = this.prpDcompanyService.findByPrimaryKey(comCode);
					comCode = prpDcompanyDto.getPrpDcompany().getComCode();// 上级结构代码
					String conditions3 = "COMCODE='" + comCode + "' AND VALIDSTATUS='1' AND (UWTYPE='C' OR UWTYPE='Y') AND NODENO='" + nodeNo + "' AND (CLASSCODE LIKE '%" + classCode + "%' or CLASSCODE='*')";
					List<UtiUwLevel> list3 = this.utiUwLevelService.findByConditions(conditions3);
					if (list3 == null || list3.size() == 0) {
						PrpDcompany prpDcompanyDto2 = this.prpDcompanyService.findByPrimaryKey(comCode);
						comLevel = prpDcompanyDto2.getComLevel();
						nodeNo++;
						continue;
					} else {
						Iterator<UtiUwLevel> it = list3.iterator();
						while (it.hasNext()) {
							UtiUwLevel utiUwLevel = (UtiUwLevel) it.next();
							set.add(utiUwLevel.getId().getUserCode());
						}
						break;
					}
				}
			} else {// 核赔员不是当前机构最高级别
				String conditions3 = "COMCODE='" + comCode + "' AND VALIDSTATUS='1' AND (UWTYPE='C' OR UWTYPE='Y') AND NODENO='" + nodeNo + "' AND (CLASSCODE LIKE '%" + classCode + "%' or CLASSCODE='*')";
				List<UtiUwLevel> list3 = this.utiUwLevelService.findByConditions(conditions3);
				if (list3 == null || list3.size() == 0) {
					nodeNo++;
					continue;
				} else {
					Iterator<UtiUwLevel> it = list3.iterator();
					while (it.hasNext()) {
						UtiUwLevel utiUwLevel = (UtiUwLevel) it.next();
						set.add(utiUwLevel.getId().getUserCode());
					}
					break;
				}
			}
		}

		Iterator<String> it = set.iterator();
		String content = "申請成功！審核人員：";
		while (it.hasNext()) {
			PrpDuser prpDuser = null;
			try {
				prpDuser = this.prpDuserService.findPrpDuser(it.next());
			} catch (Exception e) {
				e.printStackTrace();
			}
			content = content + prpDuser.getUserName() + ",";
		}
		request.setAttribute("content", content.substring(0, content.length() - 1));
		SwfNodeId swfNodeId = new SwfNodeId();
		swfNodeId.setModelNo(modelNo);
		swfNodeId.setNodeNo(nodeNo);
		SwfNode swNodeDto = null;
		swNodeDto = this.swfNodeService.findSwfNode(swfNodeId);
		String nodeName = swNodeDto.getNodeName();
		return modelNo + "," + nodeNo + "," + nodeName;
	}

	/**
	 * 根据条件获取用户权限信息
	 * @param conditions 查询条件
	 * @return Collection 用户权限对象
	 * @throws SQLException Exception
	 */
	@Override
	public List<UtiUserGrade> findUtiUserGradeByConditions(String conditions) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		List<UtiUserGrade> list = this.utiUserGradeService.findUtiUserGrade(queryRule);
		return list;
	}

	/**
	 * 提交請求
	 * @param request
	 */
	@Override
	public void submit(HttpServletRequest request) {
		List<SwfPath> colSubmitList = new ArrayList<SwfPath>();
		int modelNo = Integer.parseInt(request.getParameter("modelNo"));
		int nodelNo = Integer.parseInt(request.getParameter("nodeNo"));
		UserDto userDto = (UserDto) request.getSession().getAttribute("user");
		String comCode = userDto.getComCode();
		// 提交路径列表
		try {
			colSubmitList = this.swfPathService.getPathes(modelNo, nodelNo, comCode);
		} catch (UserException e) {
			e.printStackTrace();
		}
		request.setAttribute("submitList", colSubmitList.size() > 0 ? colSubmitList : null);
	}

	/**
	 * 根据条件获得资料归档调阅日志表中数据
	 * @param conditions 查询条件
	 * @return 资料归档调阅日志对象
	 * @throws Exception
	 */
	@Override
	public PrpLDocArchiveLog findPrpLDocArchiveLogByConditions(String conditions) throws Exception {
		List<PrpLDocArchiveLog> list = this.prpLDocArchiveLogService.findByconditions(conditions);
		PrpLDocArchiveLog prpLDocArchiveLog = null;
		if (list.size() > 0) {
			prpLDocArchiveLog = list.get(0);
		}
		return prpLDocArchiveLog;
	}

	/**
	 * 根據條件查詢page
	 * @param conditions
	 * @param pageNo
	 * @param rowsPerPage
	 * @return
	 * @throws Exception
	 */
	@Override
	public Page findPageByConditions(String conditions, int pageNo, int rowsPerPage) throws Exception {
		Page page = prpLDocArchiveService.findByConditions(conditions, pageNo, rowsPerPage);
		for (int i = 0; i < page.getResult().size(); i++) {
			if (((PrpLDocArchive) page.getResult().get(i)).getApplyDate() != null) {
				Date applyDate = new Date((((PrpLDocArchive) page.getResult().get(i)).getApplyDate()).getTime());
				((PrpLDocArchive) page.getResult().get(i)).setApplyDate(applyDate);
			}
			if (((PrpLDocArchive) page.getResult().get(i)).getEndCaseDate() != null) {
				Date endCaseDate = new Date((((PrpLDocArchive) page.getResult().get(i)).getEndCaseDate()).getTime());
				((PrpLDocArchive) page.getResult().get(i)).setEndCaseDate(endCaseDate);
			}
			if (((PrpLDocArchive) page.getResult().get(i)).getReturnDate() != null) {
				Date returnDate = new Date((((PrpLDocArchive) page.getResult().get(i)).getReturnDate()).getTime());
				((PrpLDocArchive) page.getResult().get(i)).setReturnDate(returnDate);
			}
			if (((PrpLDocArchive) page.getResult().get(i)).getStartReviewDate() != null) {
				Date startReviewDate = new Date((((PrpLDocArchive) page.getResult().get(i)).getStartReviewDate()).getTime());
				((PrpLDocArchive) page.getResult().get(i)).setStartReviewDate(startReviewDate);
			}
		}
		return page;
	}

	public PrpLDocArchiveService getPrpLDocArchiveService() {
		return prpLDocArchiveService;
	}

	public void setPrpLDocArchiveService(PrpLDocArchiveService prpLDocArchiveService) {
		this.prpLDocArchiveService = prpLDocArchiveService;
	}

	public PrpLDocArchiveLogService getPrpLDocArchiveLogService() {
		return prpLDocArchiveLogService;
	}

	public void setPrpLDocArchiveLogService(PrpLDocArchiveLogService prpLDocArchiveLogService) {
		this.prpLDocArchiveLogService = prpLDocArchiveLogService;
	}

	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}

	public UtiUwLevelService getUtiUwLevelService() {
		return utiUwLevelService;
	}

	public void setUtiUwLevelService(UtiUwLevelService utiUwLevelService) {
		this.utiUwLevelService = utiUwLevelService;
	}

	public PrpDcompanyService getPrpDcompanyService() {
		return prpDcompanyService;
	}

	public void setPrpDcompanyService(PrpDcompanyService prpDcompanyService) {
		this.prpDcompanyService = prpDcompanyService;
	}

	public PrpDuserService getPrpDuserService() {
		return prpDuserService;
	}

	public void setPrpDuserService(PrpDuserService prpDuserService) {
		this.prpDuserService = prpDuserService;
	}

	public SwfNodeService getSwfNodeService() {
		return swfNodeService;
	}

	public void setSwfNodeService(SwfNodeService swfNodeService) {
		this.swfNodeService = swfNodeService;
	}

	public UtiUserGradeService getUtiUserGradeService() {
		return utiUserGradeService;
	}

	public void setUtiUserGradeService(UtiUserGradeService utiUserGradeService) {
		this.utiUserGradeService = utiUserGradeService;
	}

	public SwfPathService getSwfPathService() {
		return swfPathService;
	}

	public void setSwfPathService(SwfPathService swfPathService) {
		this.swfPathService = swfPathService;
	}

}
