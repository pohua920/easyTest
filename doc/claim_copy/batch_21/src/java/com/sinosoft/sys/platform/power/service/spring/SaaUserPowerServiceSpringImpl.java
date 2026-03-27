package com.sinosoft.sys.platform.power.service.spring;

import ins.framework.dao.GenericDaoHibernate;
import ins.framework.exception.BusinessException;
import ins.framework.utils.DataUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sinosoft.app.common.util.OpenExcel;
import com.sinosoft.sys.platform.company.service.facade.CompanyService;
import com.sinosoft.sys.platform.power.model.SaaCompany;
import com.sinosoft.sys.platform.power.model.SaaAuthCompany;
import com.sinosoft.sys.platform.power.model.SaaAuthExceptCompany;
import com.sinosoft.sys.platform.power.model.SaaAuthProduct;
import com.sinosoft.sys.platform.power.model.SaaAuthTask;
import com.sinosoft.sys.platform.power.model.SaaBusinessline;
import com.sinosoft.sys.platform.power.model.SaaClass;
import com.sinosoft.sys.platform.power.model.SaaExceptCompany;
import com.sinosoft.sys.platform.power.model.SaaGrade;
import com.sinosoft.sys.platform.power.model.SaaPermitCompany;
import com.sinosoft.sys.platform.power.model.SaaPermitProduct;
import com.sinosoft.sys.platform.power.model.SaaRisk;
import com.sinosoft.sys.platform.power.model.SaaTask;
import com.sinosoft.sys.platform.power.model.SaaUser;
import com.sinosoft.sys.platform.power.model.SaaUserGrade;
import com.sinosoft.sys.platform.power.service.facade.SaaGradeService;
import com.sinosoft.sys.platform.power.service.facade.SaaInsuranceCategoryService;
import com.sinosoft.sys.platform.power.service.facade.SaaInsuranceService;
import com.sinosoft.sys.platform.power.service.facade.SaaPowerHelpService;
import com.sinosoft.sys.platform.power.service.facade.SaaPowerService;
import com.sinosoft.sys.platform.power.service.facade.SaaProductLineService;
import com.sinosoft.sys.platform.power.service.facade.SaaTaskService;
import com.sinosoft.sys.platform.power.service.facade.SaaUserGradeService;
import com.sinosoft.sys.platform.power.service.facade.SaaUserPowerService;
import com.sinosoft.sys.platform.power.service.facade.SaaUserService;
import com.sinosoft.sys.platform.power.util.IConstants;
import com.sinosoft.sys.platform.power.util.WriteExcel;
import com.sinosoft.sys.platform.power.vo.SaaAuthTaskVO;
import com.sinosoft.sys.platform.power.vo.SaaRiskObjectVO;
import com.sinosoft.sys.platform.power.vo.SaaUserPowerVO;

public class SaaUserPowerServiceSpringImpl extends GenericDaoHibernate implements SaaUserPowerService {
	private SaaUserGradeService saaUserGradeService;
	private SaaUserService saaUserService;
	private SaaPowerHelpService saaPowerHelpService;
	private SaaPowerService saaPowerService;
	private CompanyService companyService;
	private SaaTaskService saaTaskService;
	private SaaGradeService saaGradeService;
	private SaaProductLineService saaProductLineService;
	private SaaInsuranceService saaInsuranceService;
	private SaaInsuranceCategoryService saaInsuranceCategoryService;

	public String findSubCompanySql(String userCode) {
		// 找出这个人可以管理的所有机构,权限导出页面 可以通过当前的操作人找出机构列表
		List<String> companyCodeList = new ArrayList<String>(0);
		List<String> subCompanyCodeList = new ArrayList<String>(0);
		String hql = "select perCom.comCode from SaaPermitCompany perCom where perCom.saaUserGrade.id in (select userGrade.id from SaaUserGrade userGrade where userGrade.userCode=?)";
		companyCodeList = super.findByHql(hql, userCode);
		subCompanyCodeList = saaPowerHelpService.getSubCompanyCodeList(companyCodeList, null);
		StringBuilder perBuilder = new StringBuilder();
		perBuilder.append(saaPowerHelpService.removeDuplicateWithOrder(subCompanyCodeList));
		String comPerSql = saaPowerHelpService.getComPerRange(perBuilder.toString());
		return comPerSql;
	}

	public String findAuthCompanySql(String userCode) {
		StringBuilder perBuilder = new StringBuilder();
		List permitComList = new ArrayList<Object>();
		permitComList.addAll(saaPowerHelpService.getAuthPermitCom(userCode, null));
		perBuilder.append(saaPowerHelpService.removeDuplicateWithOrder(permitComList));
		String comPerSql = saaPowerHelpService.getComPerRange(perBuilder.toString());
		return comPerSql;
	}

	public String findAuthExceptCompanySql(String userCode) {
		StringBuilder authExceptBuilder = new StringBuilder();
		List exceptComList = new ArrayList<Object>();
		exceptComList.addAll(saaPowerHelpService.getAuthExceCom(userCode, null));
		authExceptBuilder.append(saaPowerHelpService.removeDuplicateWithOrder(exceptComList));
		String comPerSql = saaPowerHelpService.getComExcRange(authExceptBuilder.toString());
		if (comPerSql.equals("nohave")) {
			return "not in('************')";
		} else
			return comPerSql;
	}

	public String findSaaUserAuthComCode(String userCode) {
		List<String> authComCodeList = this.findByHql("select distinct comCode from SaaAuthCompany authCompany where " + "authCompany.userCode =?", userCode);
		String authComCode = "";
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < authComCodeList.size(); i++) {
			builder.append(",'" + authComCodeList.get(i) + "'");
		}
		if (!(authComCodeList.size() > 0)) {
			builder.append(",''");
		}
		if (builder.toString().length() > 0) {
			authComCode = builder.toString().substring(1);
		} else {
			authComCode = "''";
		}
		StringBuffer hql = new StringBuffer();
		hql.append("select a from SaaCompany a where 1=1");
		hql.append(" and a.validStatus='1'");
		// hql.append(" and a.comCode " + this.findAuthCompanySql(userCode));
		hql.append(" and a.comCode in(" + authComCode + ")");
		List<SaaCompany> saaCompanyForAuthList = new ArrayList<SaaCompany>(0);
		saaCompanyForAuthList = super.findByHql(hql.toString());
		StringBuilder authComCodeBuilder = new StringBuilder();
		for (SaaCompany com : saaCompanyForAuthList) {
			authComCodeBuilder.append(com.getComCode());
			authComCodeBuilder.append(',');
		}
		int index = authComCodeBuilder.lastIndexOf(",");
		if (index != -1) {
			authComCodeBuilder.deleteCharAt(index);
		}
		return authComCodeBuilder.toString();

	}

	public String findSaaUserAuthExceptComCode(String userCode) {
		List<String> authExceptComCodeList = this.findByHql("select distinct comCode from SaaAuthExceptCompany authExceptCompany where " + "authExceptCompany.userCode =?",
				userCode);
		String authExceptComCode = "";
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < authExceptComCodeList.size(); i++) {
			builder.append(",'" + authExceptComCodeList.get(i) + "'");
		}
		if (!(authExceptComCodeList.size() > 0)) {
			builder.append(",''");
		}
		if (builder.toString().length() > 0) {
			authExceptComCode = builder.toString().substring(1);
		} else {
			authExceptComCode = "''";
		}
		StringBuffer hql = new StringBuffer();
		hql.append("select a from SaaCompany a where 1=1");
		hql.append(" and a.validStatus='1'");
		// hql.append(" and a.comCode " +
		// this.findAuthExceptCompanySql(userCode));
		hql.append(" and a.comCode in(" + authExceptComCode + ")");
		List<SaaCompany> saaCompanyForAuthExceptList = new ArrayList<SaaCompany>(0);
		saaCompanyForAuthExceptList = super.findByHql(hql.toString());
		StringBuilder authComCodeBuilder = new StringBuilder();
		for (SaaCompany com : saaCompanyForAuthExceptList) {
			authComCodeBuilder.append(com.getComCode());
			authComCodeBuilder.append(',');
		}
		int index = authComCodeBuilder.lastIndexOf(",");
		if (index != -1) {
			authComCodeBuilder.deleteCharAt(index);
		}
		return authComCodeBuilder.toString();
	}

	public String findSaaUserAuthComName(String userCode) {
		List<String> authComCodeList = this.findByHql("select distinct comCode from SaaAuthCompany authCompany where " + "authCompany.userCode =?", userCode);
		String authComCode = "";
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < authComCodeList.size(); i++) {
			builder.append(",'" + authComCodeList.get(i) + "'");
		}
		if (!(authComCodeList.size() > 0)) {
			builder.append(",''");
		}
		if (builder.toString().length() > 0) {
			authComCode = builder.toString().substring(1);
		} else {
			authComCode = "''";
		}
		StringBuffer hql = new StringBuffer();
		hql.append("select a from SaaCompany a where 1=1");
		// hql.append(" and a.comCode " + this.findAuthCompanySql(userCode));
		hql.append(" and a.comCode in(" + authComCode + ")");
		List<SaaCompany> saaCompanyForAuthList = new ArrayList<SaaCompany>(0);
		saaCompanyForAuthList = super.findByHql(hql.toString());
		StringBuilder authComCodeBuilder = new StringBuilder();
		for (SaaCompany com : saaCompanyForAuthList) {
			authComCodeBuilder.append(com.getComCName());
			authComCodeBuilder.append(',');
		}
		int index = authComCodeBuilder.lastIndexOf(",");
		if (index != -1) {
			authComCodeBuilder.deleteCharAt(index);
		}
		return authComCodeBuilder.toString();
	}

	public String findSaaUserAuthExceptComName(String useCode) {
		List<String> authExceptComCodeList = this
				.findByHql("select distinct comCode from SaaAuthExceptCompany authExceptCompany where " + "authExceptCompany.userCode =?", useCode);
		String authExceptComCode = "";
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < authExceptComCodeList.size(); i++) {
			builder.append(",'" + authExceptComCodeList.get(i) + "'");
		}
		if (!(authExceptComCodeList.size() > 0)) {
			builder.append(",''");
		}
		if (builder.toString().length() > 0) {
			authExceptComCode = builder.toString().substring(1);
		} else {
			authExceptComCode = "''";
		}
		StringBuffer hql = new StringBuffer();
		hql.append("select a from SaaCompany a where 1=1");
		// hql.append(" and a.comCode " +
		// this.findAuthExceptCompanySql(useCode));
		hql.append(" and a.comCode in(" + authExceptComCode + ")");
		List<SaaCompany> saaCompanyForAuthExceptList = new ArrayList<SaaCompany>(0);
		saaCompanyForAuthExceptList = super.findByHql(hql.toString());
		StringBuilder authComCodeBuilder = new StringBuilder();
		for (SaaCompany com : saaCompanyForAuthExceptList) {
			authComCodeBuilder.append(com.getComCName());
			authComCodeBuilder.append(',');
		}
		int index = authComCodeBuilder.lastIndexOf(",");
		if (index != -1) {
			authComCodeBuilder.deleteCharAt(index);
		}
		return authComCodeBuilder.toString();
	}

	public List<SaaUser> findSaaUserList(String userCode) {
		if (userCode.equals("00000000")) {
			String hql = "select user from SaaUser user where user.validStatus='1'";
			return super.findByHql(hql);
		} else {
			List<String> perComCodeList = new ArrayList<String>(0);
			perComCodeList = (List<String>) saaPowerHelpService.getAuthPermitCom(userCode, null);
			StringBuilder perBuilder = new StringBuilder();
			perBuilder.append("select saaUser from SaaUser saaUser where saaUser.userCode!=?");
			perBuilder.append(" and saaUser.userCode in (select grade.userCode from SaaUserGrade grade)");
			perBuilder.append(" and saaUser.validStatus='1'");
			perBuilder.append(" and saaUser.comCode in (" + saaPowerHelpService.removeDuplicateWithOrder(perComCodeList) + ")");
			return super.findByHql(perBuilder.toString(), userCode);
		}
	}

	public void updateTaskPower(String[] taskCodes, String userCode) {
		String hql = "select task from SaaAuthTask task where task.userCode=?";
		List<SaaAuthTask> saaAuthTaskListOld = new ArrayList<SaaAuthTask>(0);
		saaAuthTaskListOld = super.findByHql(hql, userCode);
		super.deleteAll(saaAuthTaskListOld);
		List<SaaAuthTask> saaAuthTaskList = this.convertVoToDto(taskCodes, userCode);
		super.saveAll(saaAuthTaskList);
	}

	public void updateProductPower(String[] productCodes, String userCode) {
		String hql = "select product from SaaAuthProduct product where product.userCode=?";
		List<SaaAuthProduct> saaAuthProductListOld = new ArrayList<SaaAuthProduct>(0);
		saaAuthProductListOld = super.findByHql(hql, userCode);
		super.deleteAll(saaAuthProductListOld);
		List<SaaAuthProduct> saaAuthProductListNew = new ArrayList<SaaAuthProduct>(0);

		if (productCodes != null) {
			List<String> saaAuthProductList = new ArrayList<String>(0);
			List<String> saaAuthProductListLine = new ArrayList<String>(0);
			List<String> saaAuthProductListClass = new ArrayList<String>(0);
			List<String> saaAuthProductListRisk = new ArrayList<String>(0);
			for (String str : productCodes) {
				if (str.indexOf('.') == -1) {
					saaAuthProductListLine.add(str);

				} else {
					if (str.indexOf('.') == str.lastIndexOf('.')) {
						saaAuthProductListClass.add(str);

					} else {
						saaAuthProductListRisk.add(str);

					}

				}
			}
			if (saaAuthProductListLine.size() > 0) {
				for (String str : saaAuthProductListLine) {
					saaAuthProductList.add(str);
				}
			}
			if (saaAuthProductListClass.size() > 0) {
				for (String str : saaAuthProductListClass) {
					if (!saaAuthProductList.contains(str.substring(0, str.indexOf('.')))) {
						saaAuthProductList.add(str);
					}
				}
			}
			if (saaAuthProductListRisk.size() > 0) {
				for (String str : saaAuthProductListRisk) {
					if (!(saaAuthProductList.contains(str.substring(0, str.indexOf('.'))) || saaAuthProductList.contains(str.substring(0, str.lastIndexOf('.'))))) {
						saaAuthProductList.add(str);
					}
				}
			}
			if (saaAuthProductList.size() > 0) {
				for (int i = 0; i < saaAuthProductList.size(); i++) {
					SaaAuthProduct saaAuthProduct = new SaaAuthProduct();
					saaAuthProduct.setUserCode(userCode);
					saaAuthProduct.setProductCode(saaAuthProductList.get(i));
					saaAuthProductListNew.add(saaAuthProduct);
				}
				super.saveAll(saaAuthProductListNew);
			}
		}
	}

	public void updateComPower(String authComCode, String authExceptComCode, String userCode) {
		List<SaaAuthCompany> saaAuthCompanyListOld = new ArrayList<SaaAuthCompany>(0);
		List<SaaAuthExceptCompany> saaAuthExceptCompanyListOld = new ArrayList<SaaAuthExceptCompany>(0);
		String hqlAuth = "select company from SaaAuthCompany company where company.userCode=?";
		String hqlAuthExcept = "select company from SaaAuthExceptCompany company where company.userCode=?";
		saaAuthCompanyListOld = super.findByHql(hqlAuth, userCode);
		saaAuthExceptCompanyListOld = super.findByHql(hqlAuthExcept, userCode);
		super.deleteAll(saaAuthCompanyListOld);
		super.deleteAll(saaAuthExceptCompanyListOld);

		List<SaaAuthCompany> saaAuthCompanyList = new ArrayList<SaaAuthCompany>(0);
		List<SaaAuthExceptCompany> saaAuthExceptCompanyList = new ArrayList<SaaAuthExceptCompany>(0);
		String[] authComCodeArray = authComCode.split(",");
		String[] authExceptComCodeArray = authExceptComCode.split(",");
		List<String> authComCodes = new ArrayList<String>(0);
		List<String> authExceptComCodes = new ArrayList<String>(0);
		List<String> comCodeListOne = new ArrayList<String>(0);
		List<String> subCompanyCodeListForOne = new ArrayList<String>(0);
		if (null != authComCodeArray) {
			String comCode = saaUserService.findSaaUserByUserCode(userCode).getComCode();
			List<String> comCodes = companyService.getAllUpperCompanyCode(comCode); //
			comCodes.addAll(companyService.getAllSubComCode("", comCode).getResult()); //
			for (String temp : authComCodeArray) {
				if (!"".equals(temp)) {
					if (comCodes.contains(temp)) {
						authComCodes.add(temp);
					}
				}
			}
		}
		if (null != authExceptComCodeArray) {
			for (String temp : authExceptComCodeArray) {
				if (!"".equals(temp)) {
					authExceptComCodes.add(temp);
				}

			}
		}
		List<String> authComTemp = new ArrayList<String>(0);
		List<String> authExceptComTemp = new ArrayList<String>(0);
		for (String temp : authComCodes) {
			comCodeListOne.clear();
			comCodeListOne.add(temp.trim());
			subCompanyCodeListForOne = saaPowerHelpService.getSubCompanyCodeList(comCodeListOne, null);
			subCompanyCodeListForOne.remove(temp);
			authComTemp.addAll(subCompanyCodeListForOne);
		}
		authComCodes.removeAll(authComTemp);

		for (String temp : authExceptComCodes) {
			comCodeListOne.clear();
			comCodeListOne.add(temp);
			subCompanyCodeListForOne = saaPowerHelpService.getSubCompanyCodeList(comCodeListOne, null);
			subCompanyCodeListForOne.remove(temp);
			authExceptComTemp.addAll(subCompanyCodeListForOne);
		}
		authExceptComCodes.removeAll(authExceptComTemp);
		for (String temp : authComCodes) {
			SaaAuthCompany saaAuthCompany = new SaaAuthCompany();
			saaAuthCompany.setComCode(temp);
			saaAuthCompany.setUserCode(userCode);
			saaAuthCompanyList.add(saaAuthCompany);
		}
		for (String temp : authExceptComCodes) {
			SaaAuthExceptCompany saaAuthExceptCompany = new SaaAuthExceptCompany();
			saaAuthExceptCompany.setComCode(temp);
			saaAuthExceptCompany.setUserCode(userCode);
			saaAuthExceptCompanyList.add(saaAuthExceptCompany);
		}
		super.saveAll(saaAuthCompanyList);
		super.saveAll(saaAuthExceptCompanyList);

	}

	public List<SaaAuthTask> convertVoToDto(String[] taskCodes, String userCode) {
		List<SaaAuthTask> saaAuthTaskList = new ArrayList<SaaAuthTask>(0);
		List<SaaTask> saaTaskList = new ArrayList<SaaTask>(0);
		if (taskCodes != null) {
			saaTaskList = saaTaskService.findTask(taskCodes);
			for (SaaTask task : saaTaskList) {
				SaaAuthTask saaAuthTask = new SaaAuthTask();
				saaAuthTask.setUserCode(userCode);
				saaAuthTask.setSaaTask(task);
				saaAuthTaskList.add(saaAuthTask);
			}
		}
		return saaAuthTaskList;
	}

	// public Page findSaaUserList(SaaUser saaUser, int pageNo, int pageSize,
	// String userCodeOperate) {
	// HqlRulesUtil hqlRulesUtil = new HqlRulesUtil();
	// hqlRulesUtil.addLike("saaUser.userCode", saaUser.getUserCode());
	// hqlRulesUtil.addLike("saaUser.comCode", saaUser.getComCode());
	// hqlRulesUtil.addLike("saaUser.userName", saaUser.getUserName());
	// StringBuffer hql = new StringBuffer();
	// hql.append(" from SaaUser saaUser where 1=1");
	// hql.append(" and saaUser.validStatus='1'");
	// if (hqlRulesUtil.getHql().trim().length() != 0) {
	// hql.append(" and ").append(hqlRulesUtil.getHql());
	// }
	// if(!userCodeOperate.equals("0000000000")){
	// hql.append(" and saaUser.comCode "
	// + this.findAuthCompanySql(userCodeOperate));
	// }
	// Page page = findByHql(hql.toString(), pageNo, pageSize);
	// return page;
	// }

	public void copyUserPower(String userCodeFrom, String userCodeTo, String operUserCode, Date date) {
		if (logger.isDebugEnabled())
			logger.debug("Copying user power from " + userCodeFrom + " to " + userCodeTo);

		List<SaaUserGrade> saaUserGradeOld = saaUserGradeService.getUserGradeList(userCodeTo);
		List<SaaUserGrade> saaUserGradeFrom = saaUserGradeService.getUserGradeList(userCodeFrom);
		List<SaaUserGrade> saaUserGradeNew = new ArrayList<SaaUserGrade>();
		for (SaaUserGrade userGrade : saaUserGradeFrom) {
			SaaUserGrade newUserGrade = new SaaUserGrade();
			saaUserGradeNew.add(newUserGrade);

			DataUtils.copySimpleObject(userGrade, newUserGrade, true);

			newUserGrade.setId(null);
			newUserGrade.setUserCode(userCodeTo);
			newUserGrade.setSaaGrade(userGrade.getSaaGrade());
			newUserGrade.setCreatorCode(operUserCode);
			newUserGrade.setCreateTime(date);
			newUserGrade.setUpdaterCode(operUserCode);
			newUserGrade.setUpdateTime(date);
		}
		saaUserGradeService.deleteAllUserGrade(saaUserGradeOld);
		saaUserGradeService.saveAllUserGrade(saaUserGradeNew);
		if (logger.isDebugEnabled())
			logger.debug("Delete " + saaUserGradeOld.size() + " old records of ssa_usergrade for user " + userCodeTo + ", and add " + saaUserGradeNew.size() + " new records.");

		List<SaaPermitCompany> saaPermitCompanyNew = new ArrayList<SaaPermitCompany>(0);
		List<SaaExceptCompany> saaExceptCompanyNew = new ArrayList<SaaExceptCompany>(0);
		List<SaaPermitProduct> saaPermitProductNew = new ArrayList<SaaPermitProduct>(0);
		List<SaaPermitCompany> saaPermitCompanyOld = this.getSaaPermitCompanyList(userCodeTo);
		List<SaaExceptCompany> saaExceptCompanyOld = this.getSaaExceptCompanyList(userCodeTo);
		List<SaaPermitProduct> saaPermitProductOld = this.getSaaPermitProductList(userCodeTo);

		List<SaaPermitCompany> saaPermitCompanyList = this.getSaaPermitCompanyList(userCodeFrom);
		for (SaaPermitCompany permitCompany : saaPermitCompanyList) {
			SaaPermitCompany saaPermitCompany = new SaaPermitCompany();
			saaPermitCompany.setComCode(permitCompany.getComCode());
			saaPermitCompany.setSaaUserGrade(saaUserGradeService.getUserGradeList(userCodeTo).get(0));
			saaPermitCompanyNew.add(saaPermitCompany);
		}

		List<SaaExceptCompany> saaExceptCompanyList = this.getSaaExceptCompanyList(userCodeFrom);
		for (SaaExceptCompany exceptCompany : saaExceptCompanyList) {
			SaaExceptCompany saaExceptCompany = new SaaExceptCompany();
			saaExceptCompany.setComCode(exceptCompany.getComCode());
			saaExceptCompany.setSaaUserGrade(saaUserGradeService.getUserGradeList(userCodeTo).get(0));
			saaExceptCompanyNew.add(saaExceptCompany);
		}

		List<SaaPermitProduct> saaPermitProductList = this.getSaaPermitProductList(userCodeFrom);
		for (SaaPermitProduct permitProduct : saaPermitProductList) {
			SaaPermitProduct saaPermitProduct = new SaaPermitProduct();
			saaPermitProduct.setProductCode(permitProduct.getProductCode());
			saaPermitProduct.setSaaUserGrade(saaUserGradeService.getUserGradeList(userCodeTo).get(0));
			saaPermitProductNew.add(saaPermitProduct);
		}

		super.deleteAll(saaPermitCompanyOld);
		super.deleteAll(saaExceptCompanyOld);
		super.deleteAll(saaPermitProductOld);
		super.saveAll(saaPermitCompanyNew);
		super.saveAll(saaExceptCompanyNew);
		super.saveAll(saaPermitProductNew);

	}

	public List<SaaAuthTaskVO> findSaaAuthTaskVOListByUserCode(String userCodeOperate, String userCode) {
		List<SaaAuthTaskVO> saaAuthTaskVOList = new ArrayList<SaaAuthTaskVO>(0);
		List<SaaTask> saaTaskList = saaGradeService.findSaaTaskList();
		List<SaaAuthTask> saaAuthTaskList;
		List<SaaAuthTask> saaAuthTaskOperateList;
		String hql = "select saaAuthTask from SaaAuthTask saaAuthTask where saaAuthTask.userCode=?";
		if ("".equals(userCodeOperate) || userCodeOperate == null) {
			saaAuthTaskOperateList = new ArrayList<SaaAuthTask>(0);
		} else {
			saaAuthTaskOperateList = super.findByHql(hql, userCodeOperate);
		}
		if ("".equals(userCode) || userCode == null) {
			saaAuthTaskList = new ArrayList<SaaAuthTask>(0);
		} else {
			saaAuthTaskList = super.findByHql(hql, userCode);
		}
		Map<Long, SaaAuthTask> saaAuthTaskMap = new HashMap<Long, SaaAuthTask>();
		for (SaaAuthTask task : saaAuthTaskList) {
			saaAuthTaskMap.put(task.getSaaTask().getId(), task);
		}
		Map<Long, SaaAuthTask> saaAuthTaskOperateMap = new HashMap<Long, SaaAuthTask>();
		for (SaaAuthTask task : saaAuthTaskOperateList) {
			saaAuthTaskOperateMap.put(task.getSaaTask().getId(), task);
		}
		for (SaaTask task : saaTaskList) {
			SaaAuthTaskVO saaAuthTaskVO = new SaaAuthTaskVO();
			saaAuthTaskVO.setTaskCode(task.getTaskCode());
			saaAuthTaskVO.setTaskParentCode(task.getParentCode());
			saaAuthTaskVO.setTaskCName(task.getTaskCName());
			SaaAuthTask saaAuthTask = saaAuthTaskMap.get(task.getId());
			if (saaAuthTask == null) {
				saaAuthTaskVO.setChecked("0");
			} else {
				saaAuthTaskVO.setChecked("1");
			}
			if (userCodeOperate.equals("00000000")) {
				saaAuthTaskVO.setHasPower("0");
			} else {
				SaaAuthTask saaAuthTaskOperate = saaAuthTaskOperateMap.get(task.getId());
				if (saaAuthTaskOperate == null) {
					saaAuthTaskVO.setHasPower("1");
				} else {
					saaAuthTaskVO.setHasPower("0");
				}
			}

			if (saaAuthTaskVO.getTaskParentCode().equals(saaAuthTaskVO.getTaskCode())) {
				saaAuthTaskVO.setTaskParentCode("0");
			}

			saaAuthTaskVOList.add(saaAuthTaskVO);
		}
		return saaAuthTaskVOList;
	}

	public List<SaaAuthTaskVO> findSaaAuthTaskVOListByUserCodeRootTask(String userCodeOperate, String userCode, String rootTaskCode) {
		List<SaaAuthTaskVO> saaAuthTaskVOList = new ArrayList<SaaAuthTaskVO>(0);
		List<SaaTask> saaTaskList = saaGradeService.findSaaTaskListByRootTask(rootTaskCode);
		List<SaaAuthTask> saaAuthTaskList;
		List<SaaAuthTask> saaAuthTaskOperateList;
		String hql = "select saaAuthTask from SaaAuthTask saaAuthTask where saaAuthTask.userCode=?";
		if ("".equals(userCodeOperate) || userCodeOperate == null) {
			saaAuthTaskOperateList = new ArrayList<SaaAuthTask>(0);
		} else {
			saaAuthTaskOperateList = super.findByHql(hql, userCodeOperate);
		}
		if ("".equals(userCode) || userCode == null) {
			saaAuthTaskList = new ArrayList<SaaAuthTask>(0);
		} else {
			saaAuthTaskList = super.findByHql(hql, userCode);
		}
		Map<Long, SaaAuthTask> saaAuthTaskMap = new HashMap<Long, SaaAuthTask>();
		for (SaaAuthTask task : saaAuthTaskList) {
			saaAuthTaskMap.put(task.getSaaTask().getId(), task);
		}
		Map<Long, SaaAuthTask> saaAuthTaskOperateMap = new HashMap<Long, SaaAuthTask>();
		for (SaaAuthTask task : saaAuthTaskOperateList) {
			saaAuthTaskOperateMap.put(task.getSaaTask().getId(), task);
		}
		for (SaaTask task : saaTaskList) {
			SaaAuthTaskVO saaAuthTaskVO = new SaaAuthTaskVO();
			saaAuthTaskVO.setTaskCode(task.getTaskCode());
			saaAuthTaskVO.setTaskParentCode(task.getParentCode());
			saaAuthTaskVO.setTaskCName(task.getTaskCName());
			SaaAuthTask saaAuthTask = saaAuthTaskMap.get(task.getId());
			if (saaAuthTask == null) {
				saaAuthTaskVO.setChecked("0");
			} else {
				saaAuthTaskVO.setChecked("1");
			}
			if (userCodeOperate.equals("00000000")) {
				saaAuthTaskVO.setHasPower("0");
			} else {
				SaaAuthTask saaAuthTaskOperate = saaAuthTaskOperateMap.get(task.getId());
				if (saaAuthTaskOperate == null) {
					saaAuthTaskVO.setHasPower("1");
					saaAuthTaskVO.setChecked("0");//
				} else {
					saaAuthTaskVO.setHasPower("0");
				}
			}

			if (saaAuthTaskVO.getTaskParentCode().equals(saaAuthTaskVO.getTaskCode())) {
				saaAuthTaskVO.setTaskParentCode("0");
			}

			saaAuthTaskVOList.add(saaAuthTaskVO);
		}
		return saaAuthTaskVOList;
	}

	public List<SaaAuthTaskVO> findRootSaaAuthTaskVOList(String userCodeOperate) {
		List<SaaAuthTaskVO> saaAuthTaskVOList = new ArrayList<SaaAuthTaskVO>(0);
		List<SaaTask> saaTaskList = this.findSaaRootTasks();
		for (SaaTask task : saaTaskList) {
			SaaAuthTaskVO saaAuthTaskVO = new SaaAuthTaskVO();
			saaAuthTaskVO.setTaskCode(task.getTaskCode());
			saaAuthTaskVO.setTaskParentCode(task.getParentCode());
			saaAuthTaskVO.setTaskCName(task.getTaskCName());
			saaAuthTaskVOList.add(saaAuthTaskVO);
		}
		return saaAuthTaskVOList;
	}

	private List<SaaTask> findSaaRootTasks() {
		String hql = "select task from SaaTask task where task.parentCode='0' and task.validStatus='1' order by task.parentCode asc";
		return super.findByHql(hql, null);
	}

	private List<SaaAuthProduct> findSaaAuthProductAllList(List<SaaAuthProduct> productList, String userCode) {
		List<SaaAuthProduct> saaAuthProductAllList = new ArrayList<SaaAuthProduct>(0);
		saaAuthProductAllList.addAll(productList);
		String classHql = "Select cla from SaaClass cla where cla.businessLineCode=? and cla.validStatus='1'";
		String riskHql = "select risk from SaaRisk risk where risk.classCode in (select cla.classCode from SaaClass cla where cla.businessLineCode=?) and risk.validStatus='1'";

		for (SaaAuthProduct pro : productList) {
			if (pro.getProductCode().indexOf(".") == -1) {
				if (super.get(SaaBusinessline.class, pro.getProductCode()).getValidStatus().equals("1")) {
					List<SaaClass> saaClassList = super.findByHql(classHql, pro.getProductCode());
					List<SaaRisk> saaRiskList = super.findByHql(riskHql, pro.getProductCode());
					for (SaaClass cla : saaClassList) {
						SaaAuthProduct product = new SaaAuthProduct();
						product.setUserCode(userCode);
						product.setProductCode(cla.getBusinessLineCode() + "." + cla.getClassCode());
						saaAuthProductAllList.add(product);
					}
					for (SaaRisk risk : saaRiskList) {
						SaaAuthProduct product = new SaaAuthProduct();
						product.setUserCode(userCode);
						product.setProductCode(saaInsuranceCategoryService.findSaaClassByRiskCode(risk.getRiskCode()).getBusinessLineCode() + "." + risk.getClassCode() + "."
								+ risk.getRiskCode());
						saaAuthProductAllList.add(product);
					}
				} else {
				}

			} else if (pro.getProductCode().indexOf(".") == pro.getProductCode().lastIndexOf(".")) {
				if (super.get(SaaClass.class, pro.getProductCode().substring(pro.getProductCode().indexOf(".") + 1)).getValidStatus().equals("1")) {
					String hql = "select risk from SaaRisk risk where risk.classCode =? and risk.validStatus='1'";
					List<SaaRisk> saaRiskList = super.findByHql(hql, pro.getProductCode().substring(pro.getProductCode().indexOf(".") + 1));
					for (SaaRisk risk : saaRiskList) {
						SaaAuthProduct product = new SaaAuthProduct();
						product.setUserCode(userCode);
						product.setProductCode(saaInsuranceCategoryService.findSaaClassByRiskCode(risk.getRiskCode()).getBusinessLineCode() + "." + risk.getClassCode() + "."
								+ risk.getRiskCode());
						saaAuthProductAllList.add(product);
					}
				}

			} else {
			}

		}
		return saaAuthProductAllList;
	}

	public List<SaaRiskObjectVO> findSaaAuthProductVOListByUserCode(String userCodeOperate, String userCode) {
		List<SaaRiskObjectVO> saaRiskObjectVOList = new ArrayList<SaaRiskObjectVO>(0);
		String hql = "select product from SaaAuthProduct product where product.userCode=?";
		List<SaaAuthProduct> saaAuthProductList = super.findByHql(hql, userCode);
		List<SaaAuthProduct> saaAuthProductOperateList = super.findByHql(hql, userCodeOperate);

		List<SaaAuthProduct> saaAuthProductAllList = this.findSaaAuthProductAllList(saaAuthProductList, userCode);
		List<SaaAuthProduct> saaAuthProductOperateAllList = this.findSaaAuthProductAllList(saaAuthProductOperateList, userCodeOperate);
		Map<String, SaaAuthProduct> saaAuthProductMap = new HashMap<String, SaaAuthProduct>();
		Map<String, SaaAuthProduct> saaAuthProductOperateMap = new HashMap<String, SaaAuthProduct>();
		for (SaaAuthProduct product : saaAuthProductAllList) {
			saaAuthProductMap.put(product.getProductCode(), product);
		}
		for (SaaAuthProduct product : saaAuthProductOperateAllList) {
			saaAuthProductOperateMap.put(product.getProductCode(), product);
		}
		List<SaaBusinessline> saaProductLines = saaProductLineService.findSaaProductLineList();
		List<SaaRisk> saaInsurances = saaInsuranceService.findSaaInsuranceList();
		List<SaaClass> saaInsuranceCategories = saaInsuranceCategoryService.findSaaInsuranceCategoryList();
		for (SaaBusinessline saaBusinessline : saaProductLines) {
			SaaRiskObjectVO saaRiskObjectVO = new SaaRiskObjectVO();
			saaRiskObjectVO.setRiskObjectName(saaBusinessline.getBusinessLineName());
			saaRiskObjectVO.setRiskObjecCode(saaBusinessline.getBusinessLineCode());
			saaRiskObjectVO.setRiskObjectParentCode("0");
			SaaAuthProduct pro = saaAuthProductOperateMap.get(saaBusinessline.getBusinessLineCode());
			SaaAuthProduct authProduct = saaAuthProductMap.get(saaBusinessline.getBusinessLineCode());
			if (userCodeOperate.equals("00000000")) {
				// if(saaBusinessline.getValidStatus().equals("1")){
				saaRiskObjectVO.setHasPower("0");
				// }else{
				// saaRiskObjectVO.setHasPower("1");
				// }
			} else {
				if (pro != null && saaBusinessline.getValidStatus().equals("1")) {
					saaRiskObjectVO.setHasPower("0");
				} else {
					saaRiskObjectVO.setHasPower("1");
				}
			}

			if (authProduct != null) {
				saaRiskObjectVO.setValue("1");
			} else {
				saaRiskObjectVO.setValue("0");
			}
			saaRiskObjectVOList.add(saaRiskObjectVO);
		}

		for (SaaClass saaClass : saaInsuranceCategories) {
			SaaRiskObjectVO saaRiskObjectVO = new SaaRiskObjectVO();
			saaRiskObjectVO.setRiskObjectName(saaClass.getClassName());
			saaRiskObjectVO.setRiskObjectParentCode(saaClass.getBusinessLineCode());
			saaRiskObjectVO.setRiskObjecCode(saaClass.getBusinessLineCode() + "." + saaClass.getClassCode());
			SaaAuthProduct authProduct = saaAuthProductMap.get(saaClass.getBusinessLineCode() + "." + saaClass.getClassCode());

			if (authProduct != null) {
				saaRiskObjectVO.setValue("1");
			} else {
				saaRiskObjectVO.setValue("0");
			}
			SaaAuthProduct pro = saaAuthProductOperateMap.get(saaClass.getBusinessLineCode() + "." + saaClass.getClassCode());
			if (userCodeOperate.equals("00000000")) {
				// if(saaClass.getValidStatus().equals("1")){
				saaRiskObjectVO.setHasPower("0");
				// }else{
				// saaRiskObjectVO.setHasPower("1");
				// }
			} else {
				if (pro != null && saaClass.getValidStatus().equals("1")) {
					saaRiskObjectVO.setHasPower("0");
				} else {
					saaRiskObjectVO.setHasPower("1");
				}
			}
			saaRiskObjectVOList.add(saaRiskObjectVO);
		}

		for (SaaRisk saaRisk : saaInsurances) {
			SaaRiskObjectVO saaRiskObjectVO = new SaaRiskObjectVO();
			saaRiskObjectVO.setRiskObjectName(saaRisk.getRiskcname());
			saaRiskObjectVO.setRiskObjectParentCode(saaInsuranceCategoryService.findSaaClassByRiskCode(saaRisk.getRiskCode()).getBusinessLineCode() + "." + saaRisk.getClassCode());
			saaRiskObjectVO.setRiskObjecCode(saaInsuranceCategoryService.findSaaClassByRiskCode(saaRisk.getRiskCode()).getBusinessLineCode() + "." + saaRisk.getClassCode() + "."
					+ saaRisk.getRiskCode());
			SaaAuthProduct authProduct = saaAuthProductMap.get(saaInsuranceCategoryService.findSaaClassByRiskCode(saaRisk.getRiskCode()).getBusinessLineCode() + "."
					+ saaRisk.getClassCode() + "." + saaRisk.getRiskCode());
			if (authProduct != null) {
				saaRiskObjectVO.setValue("1");
			} else {
				saaRiskObjectVO.setValue("0");
			}
			if (userCodeOperate.equals("00000000")) {
				// if(saaRisk.getValidStatus().equals("1")){
				saaRiskObjectVO.setHasPower("0");
				// }else{
				// saaRiskObjectVO.setHasPower("1");
				// }
			} else {
				SaaAuthProduct pro = saaAuthProductOperateMap.get(saaInsuranceCategoryService.findSaaClassByRiskCode(saaRisk.getRiskCode()).getBusinessLineCode() + "."
						+ saaRisk.getClassCode() + "." + saaRisk.getRiskCode());
				if (pro != null && saaRisk.getValidStatus().equals("1")) {
					saaRiskObjectVO.setHasPower("0");
				} else {
					saaRiskObjectVO.setHasPower("1");
				}
			}
			saaRiskObjectVOList.add(saaRiskObjectVO);
		}
		return saaRiskObjectVOList;
	}

	public List<SaaPermitCompany> getSaaPermitCompanyList(String userCode) {
		String hql = "select company from SaaPermitCompany company where company.saaUserGrade.userCode=?";
		return super.findByHql(hql, userCode);
	}

	public List<SaaExceptCompany> getSaaExceptCompanyList(String userCode) {
		String hql = "select company from SaaExceptCompany company where company.saaUserGrade.userCode=?";
		return super.findByHql(hql, userCode);
	}

	public List<SaaPermitProduct> getSaaPermitProductList(String userCode) {
		String hql = "select product from SaaPermitProduct product where product.saaUserGrade.userCode=?";
		return super.findByHql(hql, userCode);
	}

	public void exportUserPowerToExcel(String comCodes) {
		List<String> comCodesList = new ArrayList<String>(0);
		List<String> comCodesSubList = new ArrayList<String>(0);
		List<String> userCodeList = new ArrayList<String>(0);
		List<String> userCodeOnlyList = new ArrayList<String>(0);
		for (String s : comCodes.split(",")) {
			comCodesList.add(s);
		}
		comCodesSubList = saaPowerHelpService.getSubCompanyCodeList(comCodesList, null);
		String userCodeHql = "";
		if (comCodesList.indexOf("00") == -1) {
			userCodeHql = "select user.userCode from SaaUser user where user.comCode"
					+ saaPowerHelpService.getComPerRange(saaPowerHelpService.removeDuplicateWithOrder(comCodesSubList));
		} else {
			userCodeHql = "select user.userCode from SaaUser user";
		}

		userCodeList = super.findByHql(userCodeHql);
		for (String userCode : userCodeList) {
			Set userCodeSet = new HashSet();
			if (userCodeSet.add(userCode)) {
				userCodeOnlyList.add(userCode);
			}
		}
		List<SaaUserGrade> saaUserGradeList = new ArrayList<SaaUserGrade>(0);
		List<SaaUserPowerVO> saaUserPowerVOList = new ArrayList<SaaUserPowerVO>(0);
		List<String> perProductCodeList = new ArrayList<String>(0);
		List<String> perComCodeList = new ArrayList<String>(0);
		List<String> exceptComCodeList = new ArrayList<String>(0);
		List<String> userNameList = new ArrayList<String>(0);
		List<String> userComList = new ArrayList<String>(0);
		String userNameHql = "select puser.userName from SaaUser puser where puser.userCode=?";
		String userComHql = "select '(' || puser.comCode || ')' || com.comCName from SaaUser puser,SaaCompany com where puser.userCode=? and puser.comCode=com.comCode";
		String userGradeHql = "select userGrade from SaaUserGrade userGrade where userGrade.userCode=?";
		String permitProductHql = "select perProduct.productCode from SaaPermitProduct perProduct where perProduct.saaUserGrade=?";
		String permitCompanyHql = "select '(' || perCom.comCode || ')' || com.comCName from SaaPermitCompany perCom,SaaCompany com where perCom.comCode =  com.comCode and perCom.saaUserGrade=?";
		String exceptCompanyHql = "select '(' || exceptCom.comCode || ')' || com.comCName from SaaExceptCompany exceptCom,SaaCompany com where exceptCom.comCode =  com.comCode and exceptCom.saaUserGrade=?";
		for (String userCode : userCodeOnlyList) {
			saaUserGradeList = super.findByHql(userGradeHql, userCode);
			userNameList = super.findByHql(userNameHql, userCode);
			userComList = super.findByHql(userComHql, userCode);
			String userName = "";
			String comName = "";
			if (userNameList.size() > 0) {
				userName = userNameList.get(0);
			}
			if (userComList.size() > 0) {
				comName = userComList.get(0);
			}
			if (saaUserGradeList.size() > 0) {
				for (SaaUserGrade userGrade : saaUserGradeList) {
					SaaUserPowerVO saaUserPowerVO = new SaaUserPowerVO();
					perProductCodeList = super.findByHql(permitProductHql, userGrade);
					perComCodeList = super.findByHql(permitCompanyHql, userGrade);
					exceptComCodeList = super.findByHql(exceptCompanyHql, userGrade);
					String perProductCodeString = "";
					String perComCodeString = "";
					String exceptComCodeString = "";
					if (perComCodeList.size() > 0) {
						perComCodeString = this.convertListToString(perComCodeList);
					}
					if (exceptComCodeList.size() > 0) {
						exceptComCodeString = this.convertListToString(exceptComCodeList);
					}
					if (perProductCodeList.size() > 0) {
						perProductCodeString = this.convertListToString(perProductCodeList);
					}
					saaUserPowerVO.setUserCode(userCode);
					saaUserPowerVO.setUserName(userName);
					saaUserPowerVO.setUserCom(comName);
					saaUserPowerVO.setGradeId(userGrade.getSaaGrade().getId().toString() + "-" + userGrade.getSaaGrade().getGradeCName());
					saaUserPowerVO.setPermitComCodes(perComCodeString);
					saaUserPowerVO.setExceptComCodes(exceptComCodeString);
					saaUserPowerVO.setPermitProductCodes(perProductCodeString);

					saaUserPowerVOList.add(saaUserPowerVO);
				}
			}

		}

		String[] excelHeader = { "員工代碼", "員工名稱", "歸屬機構代碼和名稱", "崗位ID和名稱", "允許機構代碼和名稱", "禁止機構代碼和名稱" };
		WriteExcel writeExcel = new WriteExcel();
		String path = this.getClass().getResource("").getPath().toString();
		// String fileName =
		// path.replace("WEB-INF/classes/cn/com/sinosoft/saa/service/spring/",
		// "downloadFiles/")+"UserPowers.xls";
		String fileName = path.substring(0, path.indexOf("WEB-INF")) + "downloadFiles/UserPowers.xls";
		FileOutputStream fos = null;
		writeExcel.createExcelSheeet(excelHeader, saaUserPowerVOList);
		try {
			writeExcel.createExcelSheeet(excelHeader, saaUserPowerVOList);
			fos = new FileOutputStream(fileName);
			writeExcel.exportExcel(fos);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private String convertListToString(List<String> list) {
		StringBuffer strBuffer = new StringBuffer();
		for (String s : list) {
			strBuffer.append(s);
			strBuffer.append(", ");
		}
		int index = strBuffer.lastIndexOf(", ");
		if (index != -1) {
			strBuffer.deleteCharAt(index);
		}
		return strBuffer.toString();
	}

	public void updateUserPowerByExcel(FileInputStream file, String curUserCode) {
		OpenExcel openExcel = new OpenExcel();
		List<Object[]> excelList = new ArrayList<Object[]>(0);
		try {
			excelList = openExcel.readExcel(file, 1, 2, 1);
		} catch (FileNotFoundException e) {
			throw new BusinessException("沒找到相應的文件，請核實再次導入！", false);
		} catch (IOException e) {
			throw new BusinessException("文件讀寫失敗，請核實再次導入！", false);
		} catch (Exception e) {
			throw new BusinessException("文件格式有誤，請核實再次導入！", false);
		}
		for (Object[] row : excelList) {
			String userCode = (String) row[0];
			List<String> userCodes = this.findByHql("select user.userCode from SaaUser user where user.userCode=?", userCode);
			if (null != userCode && !"".equals(userCode)) {
				String gradeId = (String) row[1];
				if (gradeId == null || "".equals(gradeId.trim())) {
					throw new BusinessException("沒有為人員" + userCode + "選擇崗位代碼,請核實後再次導入", false);
				}
				String permitComCode = (String) row[2];
				String exceptComCode = (String) row[3];
				// String exceptComCode ="";
				String productCode = (String) row[4];
				if (gradeId.indexOf("-") > -1) {
					gradeId = (gradeId.split("-"))[0];
				}
				this.updateUserGradeAndUserPower(curUserCode, userCode, gradeId, permitComCode, exceptComCode, productCode);
			}
		}
	}

	public void updateUserGradeAndUserPower(String curUserCode, String userCodes, String gradeIds, String permitComCodes, String exceptComCodes, String productCodes) {
		if (userCodes == null) {
			throw new BusinessException("未選擇員工代碼！", false);
		}
		if (gradeIds == null) {
			throw new BusinessException("未選擇崗位！", false);
		}
		this.checkUserCode(curUserCode, userCodes);
		// 检查是否是有效的机构代码
		if (!checkPermitComCodes(curUserCode, permitComCodes)) {
			throw new BusinessException("未知的機構代碼 :'" + permitComCodes.trim() + "' 存在於允許機構代碼串中 '" + permitComCodes + "'", false);
		}
		if (!checkPermitComCodes(curUserCode, exceptComCodes)) {
			throw new BusinessException("未知的機構代碼 :'" + exceptComCodes.trim() + "' 存在於除外機構代碼串中 '" + exceptComCodes + "'", false);
		}
		// this.checkPermitComCodes(curUserCode, permitComCodes);
		// this.checkExceptComCodes(curUserCode, exceptComCodes);
		this.checkProductCodes(curUserCode, productCodes);

		String[] userCodeArray = userCodes.split(",");
		String[] gradeIdArray = gradeIds.split(",");
		for (String userCode : userCodeArray) {
			for (String gradeId : gradeIdArray) {
				updateOneUserGradeAndUserPower(curUserCode, userCode.trim(), gradeId.trim(), permitComCodes, exceptComCodes, productCodes);
			}
		}
	}

	protected void updateOneUserGradeAndUserPower(String curUserCode, String userCode, String gradeId, String permitComCodes, String exceptComCodes, String productCodes) {
		if (hasThisGrade(curUserCode, gradeId)) {
			SaaUserGrade saaUserGrade = null;
			if (gradeId == null) {
				throw new BusinessException("數據異常，沒能取到gradeId！", false);
			}
			List<SaaUserGrade> userGrades = this.findByHql("select userGrade from SaaUserGrade userGrade where userGrade.userCode=? and userGrade.saaGrade.id=?", userCode,
					new Long(gradeId.trim()));
			if (userGrades.size() > 0) {
				saaUserGrade = userGrades.get(0);
				saaUserGrade.setUpdaterCode(curUserCode);
				saaUserGrade.setUpdateTime(new Date());
			} else {
				saaUserGrade = new SaaUserGrade();
				SaaGrade saaGrade = this.get(SaaGrade.class, new Long(gradeId));
				if (saaGrade == null) {
					throw new BusinessException("數據異常，沒能取到gradeId！", false);
				}
				saaUserGrade.setUserCode(userCode);
				saaUserGrade.setSaaGrade(saaGrade);
				saaUserGrade.setCreatorCode(curUserCode);
				saaUserGrade.setCreateTime(new Date());
			}
			saaUserGrade.setValidStatus("1");
			super.save(saaUserGrade);

			Set<String> oldSaaPermitCompanySet = new HashSet<String>();
			Set<String> oldSaaExceptCompanySet = new HashSet<String>();
			Set<String> oldSaaPermitProductSet = new HashSet<String>();
			String userGradeHql = "select userGrade from SaaUserGrade userGrade where userGrade.saaGrade.id=? and userGrade.userCode=?";
			String perComHql = "select com from SaaPermitCompany com where com.saaUserGrade.saaGrade.id=? and com.saaUserGrade.userCode=?";
			String excComHql = "select excom from SaaExceptCompany excom where excom.saaUserGrade.saaGrade.id=? and excom.saaUserGrade.userCode=?";
			String perProduct = "select pro from SaaPermitProduct pro where pro.saaUserGrade.saaGrade.id=? and pro.saaUserGrade.userCode=?";
			List<SaaUserGrade> oldSaaUserGradeList = super.findByHql(userGradeHql, new Long(gradeId), userCode);
			if (oldSaaUserGradeList.size() > 0) {
				List<SaaPermitCompany> oldSaaPermitCompanyList = super.findByHql(perComHql, new Long(gradeId), userCode);
				List<SaaExceptCompany> oldSaaExceptCompanyList = super.findByHql(excComHql, new Long(gradeId), userCode);
				List<SaaPermitProduct> oldSaaPermitProductList = super.findByHql(perProduct, new Long(gradeId), userCode);

				for (SaaPermitCompany com : oldSaaPermitCompanyList) {
					oldSaaPermitCompanySet.add(com.getComCode());
				}
				for (SaaExceptCompany com : oldSaaExceptCompanyList) {
					oldSaaExceptCompanySet.add(com.getComCode());
				}
				for (SaaPermitProduct pro : oldSaaPermitProductList) {
					oldSaaPermitProductSet.add(pro.getProductCode());
				}
			}

			List<SaaPermitCompany> newSaaPermitCompanyList = new ArrayList<SaaPermitCompany>(0);
			List<SaaExceptCompany> newSaaExceptCompanyList = new ArrayList<SaaExceptCompany>(0);
			List<SaaPermitProduct> newSaaPermitProductList = new ArrayList<SaaPermitProduct>(0);

			for (String permitComCode : permitComCodes.split(",")) {
				permitComCode = permitComCode.trim();
				if (!oldSaaPermitCompanySet.contains(permitComCode)) {
					if (null != permitComCode && !"".equals(permitComCode)) {
						SaaPermitCompany saaPermitCompany = new SaaPermitCompany();
						saaPermitCompany.setSaaUserGrade(saaUserGrade);
						saaPermitCompany.setComCode(permitComCode);
						newSaaPermitCompanyList.add(saaPermitCompany);
					}
				}
			}
			for (String exceptComCode : exceptComCodes.split(",")) {
				exceptComCode = exceptComCode.trim();
				if (!oldSaaExceptCompanySet.contains(exceptComCode)) {
					if (null != exceptComCode && !"".equals(exceptComCode)) {
						SaaExceptCompany saaExceptCompany = new SaaExceptCompany();
						saaExceptCompany.setSaaUserGrade(saaUserGrade);
						saaExceptCompany.setComCode(exceptComCode);
						newSaaExceptCompanyList.add(saaExceptCompany);
					}
				}
			}
			for (String permitProCode : productCodes.split(",")) {
				permitProCode = permitProCode.trim();
				if (!oldSaaPermitProductSet.contains(permitProCode)) {
					if (null != permitProCode && !"".equals(permitProCode)) {
						SaaPermitProduct saaPermitProduct = new SaaPermitProduct();
						saaPermitProduct.setSaaUserGrade(saaUserGrade);
						saaPermitProduct.setProductCode(permitProCode);
						newSaaPermitProductList.add(saaPermitProduct);
					}
				}
			}
			super.saveAll(newSaaPermitCompanyList);
			super.saveAll(newSaaExceptCompanyList);
			super.saveAll(newSaaPermitProductList);
		}
	}

	private boolean hasThisGrade(String curUserCode, String gradeId) {
		if (!"00000000".equals(curUserCode)) {
			return true;
		}
		List<SaaGrade> grades = saaGradeService.initSaaGradeList(curUserCode);
		Map<Long, SaaGrade> gradeMap = new HashMap<Long, SaaGrade>();
		for (SaaGrade saaGrade : grades) {
			gradeMap.put(saaGrade.getId(), saaGrade);
		}
		if (null != gradeMap.get(Long.parseLong(gradeId))) {
			return true;
		} else {
			return false;
		}

		// List<Object> objs = this
		// .findByHql(
		// "select a from SaaUserGrade a where a.validStatus='1' and a.userCode=? and a.saaGrade.id=?",
		// curUserCode, new Long(gradeId));
		// if (objs.size() > 0) {
		// return true;
		// }
		// return false;
	}

	private void checkUserCode(String curUserCode, String userCode) {
		if (!"00000000".equals(curUserCode)) {

			List<String> saaCurUserComCodeList = new ArrayList<String>(0);
			List<String> saaUserComCodeList = new ArrayList<String>(0);
			List<String> saaUserComCodeAllList = new ArrayList<String>(0);
			String hql = "select saaUser.comCode from SaaUser saaUser " + "where ? and "
					+ saaPowerService.addPower(curUserCode, IConstants.SAA_USERPOWER_POWERDATAIMP, "", "saaUser.comCode", "", "", null);
			saaUserComCodeAllList = super.findByHql(hql, "1=1");
			String hqluser = "select saaUser.comCode from SaaUser saaUser where saaUser.userCode=?";
			// saaUserComCodeAllList = saaPowerHelpService
			// .getSubCompanyCodeList(saaCurUserComCodeList);
			for (String code : userCode.split(",")) {
				saaUserComCodeList = super.findByHql(hqluser, code);
				if (!saaUserComCodeAllList.containsAll(saaUserComCodeList)) {
					throw new BusinessException("未知的人員代碼 :'" + code.trim() + "' 存在於人員代碼串中 '" + userCode + "'", false);
				}
			}
		}
	}

	private boolean checkPermitComCodes(String curUserCode, String comCodes) {
		if (!"00000000".equals(curUserCode)) {
			List<String> saaAuthCompanyCodeList = new ArrayList<String>(0);
			List<String> saaAuthCompanyCodeAllList = new ArrayList<String>(0);
			String hql = "select com.comCode from SaaAuthCompany com where com.userCode=?";

			List<String> saaAuthExceptCompanyCodeList = new ArrayList<String>(0);
			List<String> saaAuthExceptCompanyCodeAllList = new ArrayList<String>(0);
			String hqlex = "select excom.comCode from SaaAuthExceptCompany excom where excom.userCode=?";

			saaAuthCompanyCodeList = super.findByHql(hql, curUserCode);
			saaAuthCompanyCodeAllList = saaPowerHelpService.getSubCompanyCodeList(saaAuthCompanyCodeList, null);

			saaAuthExceptCompanyCodeList = super.findByHql(hqlex, curUserCode);
			saaAuthExceptCompanyCodeAllList = saaPowerHelpService.getSubCompanyCodeList(saaAuthExceptCompanyCodeList, null);

			saaAuthCompanyCodeAllList.removeAll(saaAuthExceptCompanyCodeAllList);
			for (String comCode : comCodes.split(",")) {
				if (saaAuthCompanyCodeAllList.contains(comCode)) {
					return true;
				}
				return false;
			}
		}
		return true;
	}

	private void checkExceptComCodes(String curUserCode, String comCodes) {
		if (!"00000000".equals(curUserCode)) {

			List<String> saaAuthExceptCompanyCodeList = new ArrayList<String>(0);
			List<String> saaAuthExceptCompanyCodeAllList = new ArrayList<String>(0);
			String hql = "select com.comCode from SaaAuthExceptCompany com where com.userCode=?";
			saaAuthExceptCompanyCodeList = super.findByHql(hql, curUserCode);
			saaAuthExceptCompanyCodeAllList = saaPowerHelpService.getSubCompanyCodeList(saaAuthExceptCompanyCodeList, null);
			for (String comCode : comCodes.split(",")) {
				if (!saaAuthExceptCompanyCodeAllList.contains(comCode)) {
					throw new BusinessException("未知的機構代碼 :'" + comCode.trim() + "' 存在於除外機構代碼串中 '" + comCodes + "'", false);
				}
			}
		}
	}

	private void checkProductCodes(String curUserCode, String productCodes) {
		if (!"00000000".equals(curUserCode)) {
			String hql = "select pro.productCode from SaaAuthProduct pro where pro.userCode=?";
			List<String> saaAuthProductCodeList = new ArrayList<String>(0);
			saaAuthProductCodeList = super.findByHql(hql, curUserCode);
			for (String code : productCodes.split(",")) {
				if (code.indexOf(".") == -1) {
					if (!saaAuthProductCodeList.contains(code)) {
						throw new BusinessException("未知的產品代碼 :'" + code.trim() + "' 存在於允許產品代碼串中 '" + productCodes + "'", false);
					}
				} else if (!saaAuthProductCodeList.contains(code.substring(0, code.indexOf("."))) || !saaAuthProductCodeList.contains(code.substring(0, code.lastIndexOf(".")))) {
					throw new BusinessException("未知的產品代碼 :'" + code.trim() + "' 存在於允許產品代碼串中 '" + productCodes + "'", false);
				}
			}
		}
	}

	public String getUserOperateComCodeStr(String userCode, String comLevels) {
		String comCodeSql = null;
		// 根据usercode取得usergrade
		List<SaaUserGrade> saaUserGrades = saaUserGradeService.getUserGradeList(userCode);
		if (saaUserGrades == null || saaUserGrades.size() < 1) {
			throw new BusinessException(userCode + " 没有可操作的岗位或您的岗位已过期，请核实！", false);
		} else {
			// 查询出允许机构和除外机构
			List<String> permitComList = new ArrayList<String>();
			List<String> exceComList = new ArrayList<String>();
			for (SaaUserGrade grade : saaUserGrades) {
				permitComList.addAll(saaPowerHelpService.getPermitComWithOut(grade.getId(), comLevels));
				exceComList.addAll(saaPowerHelpService.getExceComList(grade.getId(), comLevels));
			}
			for (Iterator iter = permitComList.iterator(); iter.hasNext();) {
				String permitCom = (String) iter.next();
				// 过滤掉除外机构
				if (exceComList.contains(permitCom)) {
					iter.remove();
				}
			}
			String comCodeStr = saaPowerHelpService.removeDuplicateWithOrder(permitComList);
			comCodeSql = saaPowerHelpService.getComPerRange(comCodeStr);
		}
		return comCodeSql;
	}

	public List<SaaUserGrade> findUserByPermitCompany(String comCode, String saaGradeID) {
		String hql = "from SaaUserGrade saaUserGrade where saaUserGrade.id in (select permitCompany.saaUserGrade.id "
				+ "from SaaPermitCompany permitCompany where permitCompany.comCode=?) and saaUserGrade.saaGrade.id = ? ";
		return super.findByHql(hql, comCode, Long.parseLong(saaGradeID));
	}

	public SaaUserGradeService getSaaUserGradeService() {
		return saaUserGradeService;
	}

	public void setSaaUserGradeService(SaaUserGradeService saaUserGradeService) {
		this.saaUserGradeService = saaUserGradeService;
	}

	public SaaPowerHelpService getSaaPowerHelpService() {
		return saaPowerHelpService;
	}

	public void setSaaPowerHelpService(SaaPowerHelpService saaPowerHelpService) {
		this.saaPowerHelpService = saaPowerHelpService;
	}

	public SaaTaskService getSaaTaskService() {
		return saaTaskService;
	}

	public void setSaaTaskService(SaaTaskService saaTaskService) {
		this.saaTaskService = saaTaskService;
	}

	public SaaGradeService getSaaGradeService() {
		return saaGradeService;
	}

	public void setSaaGradeService(SaaGradeService saaGradeService) {
		this.saaGradeService = saaGradeService;
	}

	public SaaProductLineService getSaaProductLineService() {
		return saaProductLineService;
	}

	public void setSaaProductLineService(SaaProductLineService saaProductLineService) {
		this.saaProductLineService = saaProductLineService;
	}

	public SaaInsuranceService getSaaInsuranceService() {
		return saaInsuranceService;
	}

	public void setSaaInsuranceService(SaaInsuranceService saaInsuranceService) {
		this.saaInsuranceService = saaInsuranceService;
	}

	public SaaInsuranceCategoryService getSaaInsuranceCategoryService() {
		return saaInsuranceCategoryService;
	}

	public void setSaaInsuranceCategoryService(SaaInsuranceCategoryService saaInsuranceCategoryService) {
		this.saaInsuranceCategoryService = saaInsuranceCategoryService;
	}

	public void setSaaUserService(SaaUserService saaUserService) {
		this.saaUserService = saaUserService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

}