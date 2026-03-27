package com.sinosoft.app.perf.hr.web;

import ins.framework.common.QueryRule;
import ins.framework.web.Struts2Action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.sinosoft.app.common.model.PerfCodeTransfer;
import com.sinosoft.app.common.model.PerfCodeTransferId;
import com.sinosoft.app.common.service.facade.PerfCodeTransferService;
import com.sinosoft.app.perf.hr.model.HrCompany;
import com.sinosoft.app.perf.hr.model.HrDepartment;
import com.sinosoft.app.perf.hr.model.HrUser;
import com.sinosoft.app.perf.hr.service.facade.PerfHrService;
import com.sinosoft.sys.platform.common.MD5CapitalPasswordEncoder;
import com.sinosoft.sys.platform.company.service.facade.CompanyService;
import com.sinosoft.sys.platform.power.model.SaaCompany;
import com.sinosoft.sys.platform.power.model.SaaUser;
import com.sinosoft.sys.platform.user.service.facade.UserService;

public class PerfHrAction extends Struts2Action {
	private static final long serialVersionUID = 1L;
	private PerfHrService perfHrService;
	private CompanyService companyService;
	private UserService userService;
	private PerfCodeTransferService perfCodeTransferService;
	private String syncComCode;
	private String syncComCName;

	public void setSyncComCName(String syncComCName) {
		this.syncComCName = syncComCName;
	}

	public void setSyncComCode(String syncComCode) {
		this.syncComCode = syncComCode;
	}

	/**
	 * 初始化同步人力资源组织架构数据页面
	 * @author 中科软
	 * @return
	 * @throws Exception
	 */
	public String initHRComSync() throws Exception {
		return SUCCESS;
	}

	/**
	 * 同步人力资源组织架构数据
	 * @author 中科软
	 * @return
	 * @throws Exception
	 */
	public String syncHRCom() throws Exception {
		String userCode = (String) getSession().getAttribute("UserCode");
		String syncComInfo = "";
		// 机构同步
		long synComCount = 0;
		long errComCount = 0;
		QueryRule queryRule = QueryRule.getInstance();
		if (syncComCode != null && !"".equals(syncComCode)) {
			queryRule.addSql(" 1=1 start with CompId= '" + syncComCode + "' connect by prior CompId = AdminId");
			syncComInfo = syncComCName + "(" + syncComCode + ")";
		} else {
			syncComInfo = "全部";
		}
		List<HrCompany> companyList = perfHrService.findHrCompany(queryRule);
		for (HrCompany hrCompany : companyList) {
			try {
				SaaCompany prpDcompany = new SaaCompany();
				prpDcompany.setComCode(hrCompany.getCompId());
				prpDcompany.setComCName(hrCompany.getCompCName());
				prpDcompany.setUpperComCode(hrCompany.getAdminId());
				prpDcompany.setComLevel(hrCompany.getOrgGrade());
				prpDcompany.setComType("01");// 机构类型：01为机构
				prpDcompany.setValidStatus("1");
				prpDcompany.setUpdateDate(new Date());
				prpDcompany.setUpdateCode(userCode);
				prpDcompany.setCreateTime(new Date());
				prpDcompany.setCreateCode(userCode);
				prpDcompany.setVirtualFlag("1");
				companyService.synchroPrpDcompany(prpDcompany);
				synComCount++;
			} catch (Exception e) {
				errComCount++;
				e.printStackTrace();
				logger.error("同步人力机构資料异常！机构代码：" + hrCompany.getCompId());
			}
		}

		// 部门同步
		long synDepCount = 0;
		long errDepCount = 0;
		long unnecessaryDepCount = 0;
		QueryRule queryRule1 = QueryRule.getInstance();
		if (syncComCode != null && !"".equals(syncComCode)) {
			queryRule1.addSql(" COMPID in (select compid from jx_company start with CompId= '" + syncComCode + "' connect by prior CompId = AdminId)");
		}
		List<HrDepartment> depList = perfHrService.findHrDepartment(queryRule1);
		PerfCodeTransferId perfCodeTransferId = new PerfCodeTransferId();
		perfCodeTransferId.setCodeType("ComType");
		perfCodeTransferId.setTransferId("HRTransfer");
		for (HrDepartment hrDepartment : depList) {
			try {
				perfCodeTransferId.setCodeCode(hrDepartment.getDepType());
				PerfCodeTransfer perfCodeTransfers = perfCodeTransferService.findPerfCodeTransferById(perfCodeTransferId);
				if (perfCodeTransfers == null) {
					unnecessaryDepCount++;
					continue;
				}
				SaaCompany prpDcompany = new SaaCompany();
				prpDcompany.setComCode(hrDepartment.getDepId());
				prpDcompany.setComCName(hrDepartment.getDepCName());

				prpDcompany.setComType(perfCodeTransfers.getToCode());
				if ((hrDepartment.getDepId()).equals(hrDepartment.getAdminId()) || hrDepartment.getAdminId() == null) {// hrDepartment.getDepId()主键
					prpDcompany.setUpperComCode(hrDepartment.getCompId());
				} else {
					prpDcompany.setUpperComCode(hrDepartment.getAdminId());
				}

				if ("1".equals(hrDepartment.getIsDisable())) {
					prpDcompany.setValidStatus("0");
				} else {
					prpDcompany.setValidStatus("1");
				}
				prpDcompany.setUpdateDate(new Date());
				prpDcompany.setUpdateCode(userCode);
				prpDcompany.setCreateTime(new Date());
				prpDcompany.setCreateCode(userCode);
				prpDcompany.setVirtualFlag("1");
				companyService.synchroPrpDcompany(prpDcompany);
				synDepCount++;
			} catch (Exception e) {
				errDepCount++;
				e.printStackTrace();
				logger.error("同步人力部门資料异常！部门代码：" + hrDepartment.getDepId());
			}
		}
		// 机构逆向同步
		long synComCountBack = 0;
		long errComCountBack = 0;
		QueryRule queryRule01 = QueryRule.getInstance();
		if (syncComCode != null && !"".equals(syncComCode)) {
			queryRule01.addSql(" 1=1 start with comCode= '" + syncComCode + "' connect by prior comCode = upperComCode");
			syncComInfo = syncComCName + "(" + syncComCode + ")";
		} else {
			syncComInfo = "全部";
		}
		List<SaaCompany> saaCompanyList = companyService.getCompany(queryRule01);
		for (SaaCompany itemCompany : saaCompanyList) {
			try {
				String virtual = itemCompany.getVirtualFlag();
				if ("1".equals(virtual)) {
					continue;
				}
				companyService.synReverseSaaCompany(itemCompany);
				synComCountBack++;
			} catch (Exception e) {
				errComCountBack++;
				e.printStackTrace();
				logger.error("同步人力机构資料异常！机构代码：" + itemCompany.getComCode());
			}
		}
		getRequest().setAttribute(
				"message",
				"<font size=4>同步組織架構已完成！ </font><br><br>" + "<font size=3>您選擇同步的機構為：" + syncComInfo + "</font><br><br>" + "<font color='blue '>成功數量：機構" + synComCount + "個、部門" + synDepCount + "個；</font><br><br>" + "<font color='red'>失敗數量：機構"
						+ errComCount + "個、部門" + errDepCount + "個；</font><br><br>" + "無需同步的部門處室" + unnecessaryDepCount + "個。");
		return SUCCESS;
	}

	/**
	 * 初始化同步人力资源员工数据页面
	 * @author 中科软
	 * @return
	 * @throws Exception
	 */
	public String initHRUserSync() throws Exception {
		return SUCCESS;
	}

	/**
	 * 同步人力资源组织架构数据
	 * @author 中科软
	 * @return
	 * @throws Exception
	 */
	public String syncHRUser() throws Exception {
		String syncComInfo = "";
		// 机构同步
		long synUserCount = 0;
		long errUserCount = 0;
		QueryRule queryRule = QueryRule.getInstance();
		if (syncComCode != null && !"".equals(syncComCode)) {
			queryRule.addSql(" COMPID in (select compid from jx_company start with CompId= '" + syncComCode + "' connect by prior CompId = AdminId)");
			syncComInfo = syncComCName + "(" + syncComCode + ")";
		} else {
			syncComInfo = "全部";
		}
		String password = new MD5CapitalPasswordEncoder().encodePassword("0000", null);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date passwdExpireDate = sdf.parse("2099-01-01");
		List<HrUser> hrUserList = perfHrService.findHrUser(queryRule);
		for (HrUser hrUsers : hrUserList) {
			try {
				SaaUser prpDuser = new SaaUser();
				prpDuser.setUserCode(hrUsers.getBadge());
				prpDuser.setUserName(hrUsers.getName());
				prpDuser.setEmail(hrUsers.getEmail());
				prpDuser.setPhone(hrUsers.getExtension());
				prpDuser.setMobile(hrUsers.getMobile());
				prpDuser.setPassword(password);
				prpDuser.setPasswdExpireDate(passwdExpireDate);
				/**** modify by linsiming 20120508 begin ****/
				prpDuser.setComCode(hrUsers.getDepid());
				prpDuser.setMakeCom(hrUsers.getDepid());
				/**** modify by linsiming 20120508 end ****/
				prpDuser.setNewUserCode(hrUsers.getBadge());
				prpDuser.setValidStatus("1");
				userService.synchroPrpDuser(prpDuser);
				synUserCount++;
			} catch (Exception e) {
				errUserCount++;
				e.printStackTrace();
				logger.error("同步人力员工資料异常！员工：" + hrUsers.getName() + "(" + hrUsers.getBadge() + ")");
			}
		}

		long synUserCountBack = 0;
		long errUserCountBack = 0;
		QueryRule queryRule01 = QueryRule.getInstance();
		if (syncComCode != null && !"".equals(syncComCode)) {
			queryRule01.addSql(" ComCode in (select comCode from Saa_Company start with comCode= '" + syncComCode + "' connect by prior comCode = upperComCode)");
			syncComInfo = syncComCName + "(" + syncComCode + ")";
		} else {
			syncComInfo = "全部";
		}
		List<SaaUser> saaUserList = userService.getSaaUser(queryRule01);
		for (SaaUser itemUser : saaUserList) {
			if (!"00000000".equals(itemUser.getUserCode())) {
				try {
					userService.synReverseSaaUser(itemUser);
					synUserCountBack++;
				} catch (Exception e) {
					errUserCountBack++;
					e.printStackTrace();
					logger.error("同步人力机构資料异常！机构代码：" + itemUser.getUserCode());
				}
			}
		}
		getRequest().setAttribute(
				"message",
				"<font size=4>同步員工信息已完成！ </font><br><br>" + "<font size=3>您選擇進行員工同步的機構為：" + syncComInfo + "</font><br><br>" + "<font color= 'blue'>成功數量：" + synUserCount + "個；</font><br><br>" + "<font color='red'>失敗數量：" + errUserCount
						+ "個；</font><br><br>");
		return SUCCESS;
	}

	/**
	 * 同步sun数据库里的内容到perfdb里
	 * @author 中科软
	 * @return
	 * @throws Exception
	 */

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setPerfHrService(PerfHrService perfHrService) {
		this.perfHrService = perfHrService;
	}

	public PerfHrService getPerfHrService() {
		return perfHrService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setPerfCodeTransferService(PerfCodeTransferService perfCodeTransferService) {
		this.perfCodeTransferService = perfCodeTransferService;
	}

	public PerfCodeTransferService getPerfCodeTransferService() {
		return perfCodeTransferService;
	}
}
