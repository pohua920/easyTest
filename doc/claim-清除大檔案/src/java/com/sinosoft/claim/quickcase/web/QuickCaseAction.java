package com.sinosoft.claim.quickcase.web;

import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import com.sinosoft.claim.bl.facade.BLQuickCaseFacade;
import com.sinosoft.claim.dto.custom.QuickCaseDto;
import com.sinosoft.claim.dto.custom.WorkFlowDto;
import com.sinosoft.claim.dto.domain.PrpLquickCaseDto;
import com.sinosoft.sysframework.common.datatype.PageRecord;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * 简易赔案对象QuickCase
 * <p>
 * Title: 车险理赔样本简易赔案action
 * </p>
 * <p>
 * Description: 车险理赔样本简易赔案action
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
public class QuickCaseAction {
	/**
	 * 保存简易赔案
	 * @param QuickCaseDto：简易赔案对象DTO
	 * @throws Exception
	 */
	public void save(QuickCaseDto verifyLossDto) throws SQLException, Exception {
		BLQuickCaseFacade bLQuickCaseFacade = new BLQuickCaseFacade();
		bLQuickCaseFacade.save(verifyLossDto);
	}

	/**
	 * 保存简易赔案带工作流
	 * @param QuickCaseDto：简易赔案对象DTO
	 * @throws Exception
	 */
	public void save(QuickCaseDto quickCaseDto, WorkFlowDto workFlowDto) throws SQLException, Exception {
		BLQuickCaseFacade bLQuickCaseFacade = new BLQuickCaseFacade();
		bLQuickCaseFacade.save(quickCaseDto, workFlowDto);
	}

	/**
	 * 保存简易赔案带工作流
	 * @param QuickCaseDto：简易赔案对象DTO
	 * @throws Exception
	 */
	public void saveUwder(QuickCaseDto quickCaseDto) throws SQLException, Exception {
		BLQuickCaseFacade bLQuickCaseFacade = new BLQuickCaseFacade();
		bLQuickCaseFacade.saveUwder(quickCaseDto);
	}

	/**
	 * 保存简易赔案带工作流
	 * @param QuickCaseDto：简易赔案对象DTO
	 * @throws Exception
	 */
	public void saveCompelUwder(QuickCaseDto quickCaseDto) throws SQLException, Exception {
		BLQuickCaseFacade bLQuickCaseFacade = new BLQuickCaseFacade();

		bLQuickCaseFacade.saveCompelUwder(quickCaseDto);
	}

	/**
	 * 删除简易赔案
	 * @param registNo：简易赔案号
	 * @throws Exception
	 */
	public void delete(String registNo, String nodeType) throws SQLException, Exception {
		BLQuickCaseFacade bLQuickCaseFacade = new BLQuickCaseFacade();
		bLQuickCaseFacade.delete(registNo, nodeType);
	}

	/**
	 * 获得简易赔案信息
	 * @param registNo：简易赔案号
	 * @return 简易赔案对象
	 * @throws Exception
	 */
	public QuickCaseDto findByPrimaryKey(String registNo) throws SQLException, UserException, Exception {
		BLQuickCaseFacade bLQuickCaseFacade = new BLQuickCaseFacade();
		QuickCaseDto verifyLossDto = (QuickCaseDto) bLQuickCaseFacade.findByPrimaryKey(registNo, null);

		if (verifyLossDto == null) {
			throw new UserException(-98, -1000, this.getClass().getName() + ".findByPrimaryKey(" + registNo + ")");
		}
		return verifyLossDto;
	}

	/**
	 * 判断简易赔案号是否存在
	 * @param registNo:简易赔案号
	 * @return 是/否
	 * @throws Exception
	 */
	public boolean isExist(String registNo) throws SQLException, Exception {
		BLQuickCaseFacade bLQuickCaseFacade = new BLQuickCaseFacade();
		return bLQuickCaseFacade.isExist(registNo);
	}

	/**
	 * 获得简易赔案信息
	 * @param conditions：查询条件
	 * @return 简易赔案对象
	 * @throws Exception
	 */

	public Collection<?> findByConditions(String conditions) throws SQLException, Exception {
		BLQuickCaseFacade bLQuickCaseFacade = new BLQuickCaseFacade();
		return bLQuickCaseFacade.findByConditions(conditions);
	}

	/**
	 * 获得简易赔案查询信息
	 * @param conditions：查询条件
	 * @param nodeType：节点类型
	 * @return 报案对象
	 * @throws Exception Add By sunhao 2004-08-24 Reason:增加新的查询条件
	 */

	public Collection<?> findByQueryConditions(String conditions, String nodeType) throws SQLException, Exception {
		BLQuickCaseFacade bLQuickCaseFacade = new BLQuickCaseFacade();
		return bLQuickCaseFacade.findByQueryConditions(conditions, nodeType);
	}

	/**
	 * 获得报案查询信息
	 * @param conditions：查询条件
	 * @return 报案对象
	 * @throws Exception Add By sunhao 2004-08-24 Reason:增加新的查询条件
	 */
	public PageRecord findByQueryConditions(String conditions, int pageNo, int recordPerPage) throws SQLException, Exception {
		BLQuickCaseFacade bLQuickCaseFacade = new BLQuickCaseFacade();
		return (PageRecord) bLQuickCaseFacade.findByQueryConditions(conditions, pageNo, recordPerPage);

	}

	/**
	 * 获得简易赔案信息
	 * @param registNo：简易赔案号
	 * @return 简易赔案对象
	 * @throws Exception
	 */
	public QuickCaseDto findByPrimaryKey(String registNo, String lossItemCode, String nodeType) throws SQLException, UserException, Exception {
		BLQuickCaseFacade bLQuickCaseFacade = new BLQuickCaseFacade();
		QuickCaseDto verifyLossDto = (QuickCaseDto) bLQuickCaseFacade.findByPrimaryKey(registNo, nodeType);

		if (verifyLossDto == null) {
			throw new UserException(-98, -1000, this.getClass().getName() + ".findByPrimaryKey(" + registNo + ")");
		}
		return verifyLossDto;
	}

	/**
	 * 获得简易赔案的单表信息
	 * @param registNo：简易赔案号
	 * @return 简易赔案单表对象
	 * @throws Exception
	 */
	public PrpLquickCaseDto findPrpLquickCaseByPrimaryKey(String registNo) throws SQLException, UserException, Exception {
		BLQuickCaseFacade bLQuickCaseFacade = new BLQuickCaseFacade();
		PrpLquickCaseDto prpLquickCaseDto = (PrpLquickCaseDto) bLQuickCaseFacade.findPrpLquickCaseByPrimaryKey(registNo);

		return prpLquickCaseDto;
	}

	/**
	 * 获得简易赔案的最大简易赔案次数
	 * @param registNo：简易赔案号
	 * @return 简易赔案单表对象
	 * @throws Exception
	 */
	public int findMaxQuickCaseTimesByRegistNo(String registNo) throws SQLException, UserException, Exception {
		BLQuickCaseFacade bLQuickCaseFacade = new BLQuickCaseFacade();
		int intRet = bLQuickCaseFacade.findMaxQuickCaseTimesByRegistNo(registNo);

		return intRet;
	}

	/**
	 * 查询简易赔案信息
	 * @param conditions
	 * @param nodeType
	 * @param pageNo
	 * @param recordPerPage
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public PageRecord findByCondition(String conditions, String nodeType, int pageNo, int recordPerPage) throws SQLException, Exception {
		BLQuickCaseFacade bLQuickCaseFacade = new BLQuickCaseFacade();
		return bLQuickCaseFacade.findByCondition(conditions, nodeType, pageNo, recordPerPage);
	}

	/**
	 * 检查是否是简易赔案，若是，统一都跳转到简易赔案的查询界面，不能操作简易赔案。这里增加了统一的方法。 modify liping 080513
	 * 後续程序是否执行，赠加控制
	 * @param registNo
	 * @throws SQLException
	 * @throws Exception
	 */
	public boolean checkQuickCaseAndForwadToSHOW(String registNo, HttpServletResponse httpServletResponse) throws SQLException, Exception {
		// quickCaseStatus 00:一般赔案；01：简易赔案未处理;02:简易赔案暂存;03:简易赔案提交
		PrpLquickCaseDto prpLquickCaseDto = new QuickCaseAction().findPrpLquickCaseByPrimaryKey(registNo);
		if (prpLquickCaseDto != null && prpLquickCaseDto.getValidStatus().equals("1")) {
			String newForward = "/claim/quickCaseFinishQueryList.do" + "?nodeType=quickCase&&status=" + prpLquickCaseDto.getQuickCaseStatus() + "&editType=SHOW&registNo=" + registNo + "&riskCode=" + prpLquickCaseDto.getRiskCode();
			httpServletResponse.sendRedirect(newForward);
			return true;
		}
		return false;
	}

	// add by wangliguang20080429 begin
	// reason:解决简易赔案重开赔案後，再由核赔回退到理算後没有提交等按钮
	public void updatePrpLquickCaseValidstatus(PrpLquickCaseDto prpLquickCaseDto) throws Exception {
		BLQuickCaseFacade blQuickCaseFacade = new BLQuickCaseFacade();
		blQuickCaseFacade.updatePrpLquickCaseValidstatus(prpLquickCaseDto);

	}
	// add by wangliguang20080429 end
}
