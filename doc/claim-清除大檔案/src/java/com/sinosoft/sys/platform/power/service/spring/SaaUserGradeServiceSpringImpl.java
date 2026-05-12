package com.sinosoft.sys.platform.power.service.spring;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sinosoft.sys.platform.power.model.SaaCompany;
import com.sinosoft.sys.platform.power.model.SaaAuthProduct;
import com.sinosoft.sys.platform.power.model.SaaBusinessline;
import com.sinosoft.sys.platform.power.model.SaaClass;
import com.sinosoft.sys.platform.power.model.SaaExceptCompany;
import com.sinosoft.sys.platform.power.model.SaaGrade;
import com.sinosoft.sys.platform.power.model.SaaPermitCompany;
import com.sinosoft.sys.platform.power.model.SaaPermitProduct;
import com.sinosoft.sys.platform.power.model.SaaRisk;
import com.sinosoft.sys.platform.power.model.SaaUserGrade;
import com.sinosoft.sys.platform.power.service.facade.SaaGradeService;
import com.sinosoft.sys.platform.power.service.facade.SaaInsuranceCategoryService;
import com.sinosoft.sys.platform.power.service.facade.SaaInsuranceService;
import com.sinosoft.sys.platform.power.service.facade.SaaPowerHelpService;
import com.sinosoft.sys.platform.power.service.facade.SaaProductLineService;
import com.sinosoft.sys.platform.power.service.facade.SaaUserGradeService;
import com.sinosoft.sys.platform.power.vo.SaaRiskObjectVO;
import com.sinosoft.sys.platform.power.vo.SaaUserGradeVO;

public class SaaUserGradeServiceSpringImpl extends GenericDaoHibernate<SaaUserGrade, Long> implements SaaUserGradeService {
	private SaaGradeService saaGradeService;
	private SaaProductLineService saaProductLineService;
	private SaaInsuranceService saaInsuranceService;
	private SaaInsuranceCategoryService saaInsuranceCategoryService;
	private SaaPowerHelpService saaPowerHelpService;

	public List<SaaUserGradeVO> getUserGradeVOList(String userCode, String userCodeOperate) {
		List<SaaUserGradeVO> userGradeVOs = new ArrayList<SaaUserGradeVO>(0);
		List<SaaUserGrade> userGrades = this.getUserGradeList(userCode);
		List<SaaGrade> grades = saaGradeService.initSaaGradeList(userCodeOperate);
		Map<Long, SaaUserGrade> userGradeMap = new HashMap<Long, SaaUserGrade>();
		for (SaaUserGrade saaUserGrade : userGrades) {
			userGradeMap.put(saaUserGrade.getSaaGrade().getId(), saaUserGrade);
		}
		for (SaaGrade grade : grades) {
			SaaUserGradeVO userGradeVO = new SaaUserGradeVO();
			userGradeVO.setGradeCode(grade.getId().toString());
			userGradeVO.setGradeName(grade.getGradeCName());
			if (userGradeMap.get(grade.getId()) != null) {
				userGradeVO.setChecked(true);
				userGradeVO.setEndDate(userGradeMap.get(grade.getId()).getInvalidDate());
			} else {
				userGradeVO.setChecked(false);
			}
			userGradeVOs.add(userGradeVO);
		}
		return userGradeVOs;
	}

	public List<SaaUserGradeVO> getUserGradeVOListSysCode(String userCode, String userCodeOperate, String sysCode) {
		List<SaaUserGradeVO> userGradeVOs = new ArrayList<SaaUserGradeVO>(0);
		List<SaaUserGrade> userGrades = this.getUserGradeListSysCode(userCode, sysCode);
		List<SaaGrade> grades = saaGradeService.initSaaGradeListSysCode(userCodeOperate, sysCode);
		Map<Long, SaaUserGrade> userGradeMap = new HashMap<Long, SaaUserGrade>();
		for (SaaUserGrade saaUserGrade : userGrades) {
			userGradeMap.put(saaUserGrade.getSaaGrade().getId(), saaUserGrade);
		}
		for (SaaGrade grade : grades) {
			SaaUserGradeVO userGradeVO = new SaaUserGradeVO();
			userGradeVO.setGradeCode(grade.getId().toString());
			userGradeVO.setGradeName(grade.getGradeCName());
			if (userGradeMap.get(grade.getId()) != null) {
				userGradeVO.setChecked(true);
				userGradeVO.setEndDate(userGradeMap.get(grade.getId()).getInvalidDate());
			} else {
				userGradeVO.setChecked(false);
			}
			userGradeVOs.add(userGradeVO);
		}
		return userGradeVOs;
	}

	public List<SaaUserGradeVO> getInseadUserGradeVOList(String userCode, String userCodeOperate) {
		List<SaaUserGradeVO> userGradeVOs = new ArrayList<SaaUserGradeVO>(0);
		List<SaaUserGrade> userGrades = this.getUserGradeList(userCode);
		List<SaaGrade> grades = saaGradeService.initSaaGradeList(userCodeOperate);
		Map<Long, SaaUserGrade> userGradeMap = new HashMap<Long, SaaUserGrade>();
		for (SaaUserGrade saaUserGrade : userGrades) {
			userGradeMap.put(saaUserGrade.getSaaGrade().getId(), saaUserGrade);
		}
		// for (SaaGrade grade : grades) {
		// SaaUserGradeVO userGradeVO = new SaaUserGradeVO();
		// userGradeVO.setGradeCode(grade.getId().toString());
		// userGradeVO.setGradeName(grade.getGradeCName());
		// if (userGradeMap.get(grade.getId()) != null) {
		// userGradeVO.setChecked(true);
		// userGradeVO.setEndDate(userGradeMap.get(grade.getId()).getInvalidDate());
		// } else {
		// userGradeVO.setChecked(false);
		// }
		// userGradeVOs.add(userGradeVO);
		// }
		// return userGradeVOs;
		// }
		// for (SaaGrade grade : grades) {
		for (int i = 0; i < userGrades.size(); i++) {
			if (grades.contains(userGrades.get(i).getSaaGrade())) {

				SaaUserGradeVO userGradeVO = new SaaUserGradeVO();
				userGradeVO.setGradeCode(userGrades.get(i).getSaaGrade().getId().toString());
				userGradeVO.setGradeName(userGrades.get(i).getSaaGrade().getGradeCName());
				if (userGradeMap.get(userGrades.get(i).getSaaGrade().getId()) != null) {
					userGradeVO.setChecked(true);
					userGradeVO.setEndDate(userGradeMap.get(userGrades.get(i).getSaaGrade().getId()).getInvalidDate());
				} else {
					userGradeVO.setChecked(false);
				}
				userGradeVOs.add(userGradeVO);
			}
		}
		// }
		return userGradeVOs;
	}

	public List<SaaUserGrade> getUserGradeList(String userCode) {
		String hql = "select userGrade from SaaUserGrade userGrade where userGrade.userCode=? and (userGrade.invalidDate>sysdate or userGrade.invalidDate is null)";
		return super.findByHql(hql, userCode);
	}

	public List<SaaUserGrade> getUserGradeListSysCode(String userCode, String systemCode) {
		String hql = "select userGrade from SaaUserGrade userGrade where userGrade.userCode=? and (userGrade.invalidDate>sysdate or userGrade.invalidDate is null) and userGrade.saaGrade.systemTypeName=?";
		return super.findByHql(hql, userCode, systemCode);
	}

	public List<SaaUserGrade> convertVoToDto(List<SaaUserGradeVO> userGradeVos, String userCode, String operUserCode) {
		List<SaaUserGrade> saaUserGrades = new ArrayList<SaaUserGrade>(0);
		for (SaaUserGradeVO userGradeVo : userGradeVos) {
			if (userGradeVo.isChecked()) {
				SaaGrade grade = saaGradeService.findSaaGradeByGradeID(userGradeVo.getGradeCode());
				SaaUserGrade userGrade = this.getSaaUserGrade(userCode, grade.getId().toString());
				if (userGrade == null) {
					SaaUserGrade saaUserGrade = new SaaUserGrade();
					saaUserGrade.setSaaGrade(grade);
					saaUserGrade.setUserCode(userCode);
					saaUserGrade.setCreatorCode(operUserCode);
					saaUserGrade.setInvalidDate(userGradeVo.getEndDate());
					saaUserGrade.setValidStatus("1");
					saaUserGrades.add(saaUserGrade);
				} else {
					userGrade.setUpdaterCode(operUserCode);
					userGrade.setInvalidDate(userGradeVo.getEndDate());
					userGrade.setValidStatus("1");
					saaUserGrades.add(userGrade);
				}

			}
		}
		return saaUserGrades;
	}

	public void updateUserGrade(List<SaaUserGradeVO> userGrades, String userCode, String operUserCode) {
		List<SaaUserGrade> saaUserGradeOlds = new ArrayList<SaaUserGrade>(0);
		List<SaaUserGrade> saaUserGradeNews = new ArrayList<SaaUserGrade>(0);
		saaUserGradeOlds = this.getUserGradeList(userCode);
		saaUserGradeNews = this.convertVoToDto(userGrades, userCode, operUserCode);
		Map<Long, SaaGrade> saaUserGradeNewMap = new HashMap<Long, SaaGrade>();
		List<SaaUserGrade> saaUserGradeNeedDelete = new ArrayList<SaaUserGrade>(0);

		for (SaaUserGrade grade : saaUserGradeNews) {
			saaUserGradeNewMap.put(grade.getSaaGrade().getId(), grade.getSaaGrade());
		}

		for (SaaUserGrade userGrade : saaUserGradeOlds) {
			SaaGrade grade = saaUserGradeNewMap.get(userGrade.getSaaGrade().getId());
			if (grade == null) {
				saaUserGradeNeedDelete.add(userGrade);
			}
		}
		super.saveAll(saaUserGradeNews);
		super.deleteAll(saaUserGradeNeedDelete);
	}

	public List<SaaCompany> findSpareCompanyList(String userCode) {
		if ("00000000".equals(userCode)) {
			StringBuffer hql = new StringBuffer();
			hql.append("select company from SaaCompany company where company.validStatus='1' order by company.comCode");
			return super.findByHql(hql.toString());
		}
		List<String> perComCodeList = new ArrayList<String>(0);
		List<String> exceptComCodeList = new ArrayList<String>(0);
		perComCodeList.addAll(saaPowerHelpService.getAuthPermitCom(userCode, null));
		exceptComCodeList.addAll(saaPowerHelpService.getAuthExceCom(userCode, null));

		StringBuilder comBuilder = new StringBuilder();
		comBuilder.append("select company from SaaCompany company where 1=1 ");
		if (!perComCodeList.contains("00")) {
			comBuilder.append(" and company.comCode in (" + saaPowerHelpService.removeDuplicateWithOrder(perComCodeList) + ")");
		}
		if (exceptComCodeList.size() > 0) {
			// 00是最大的机构，如果被禁止，则没有任何机构的操作权限
			if (exceptComCodeList.contains("00")) {
				comBuilder.append(" and 2=1 ");
			} else {
				comBuilder.append(" and company.comCode not in ("
						+ saaPowerHelpService.removeDuplicateWithOrder(exceptComCodeList) + ")");
			}
		}
		comBuilder.append(" order by company.comCode");
		return super.findByHql(comBuilder.toString());
	}

	public List<SaaCompany> findAgentSpareCompanyList(String userCode) {
		if ("00000000".equals(userCode)) {
			String hql = "select company from SaaCompany company where company.validStatus='1' and company.agentInscompany is not null order by company.comCode";
			return super.findByHql(hql);
		}
		List<String> perComCodeList = new ArrayList<String>(0);
		List<String> exceptComCodeList = new ArrayList<String>(0);
		perComCodeList.addAll(saaPowerHelpService.getAuthPermitCom(userCode, null));
		exceptComCodeList.addAll(saaPowerHelpService.getAuthExceCom(userCode, null));

		StringBuilder comBuilder = new StringBuilder();
		comBuilder.append("select company from SaaCompany company where 1=1 and company.agentInscompany is not null ");
		comBuilder.append(" and company.comCode in (" + saaPowerHelpService.removeDuplicateWithOrder(perComCodeList) + ")");
		if (exceptComCodeList.size() > 0) {

			comBuilder.append(" and company.comCode not in (" + saaPowerHelpService.removeDuplicateWithOrder(exceptComCodeList)
					+ ")");
		}
		comBuilder.append(" order by company.comCode");
		return super.findByHql(comBuilder.toString());
	}

	public List<SaaCompany> findSaaExceptCompanyList(String userCode, String saaGradeID) {
		String hql = "select company from SaaCompany company where company.comCode in (select exceptCompany.comCode from SaaExceptCompany exceptCompany where exceptCompany.saaUserGrade.userCode=? and exceptCompany.saaUserGrade.saaGrade.id=?)";
		return super.findByHql(hql, userCode, new Long(saaGradeID));
	}

	public List<SaaCompany> findSaaPermitCompanyList(String userCode, String saaGradeID) {
		String hql = "select company from SaaCompany company where company.comCode in (select permitCompany.comCode from SaaPermitCompany permitCompany where permitCompany.saaUserGrade.userCode=? and permitCompany.saaUserGrade.saaGrade.id=?)";
		return super.findByHql(hql, userCode, new Long(saaGradeID));
	}

	// public List<SaaCompany> findSaaPermitCompanyList(String userCode,String
	// saaGradeID) {
	// List<String> SaaPermitComCodeList = new ArrayList<String>(0);
	// SaaPermitComCodeList.addAll(saaPowerHelpService.getPermitCom(new
	// Long(saaUserGradeID)));
	// StringBuilder perBuilder = new StringBuilder();
	// perBuilder.append("select company from SaaCompany company where 1=1");
	// perBuilder.append(" and company.comCode in (");
	// perBuilder.append(saaPowerHelpService.removeDuplicateWithOrder(SaaPermitComCodeList));
	// perBuilder.append(")");
	// return super.findByHql(perBuilder.toString());
	// }
	public String getUserGradeID(String userCode, String saaGradeID) {
		String hql = "select saaUserGrade from SaaUserGrade saaUserGrade where saaUserGrade.userCode=? and saaUserGrade.saaGrade.id=?";
		SaaUserGrade saaUserGrade = (SaaUserGrade) super.findByHql(hql, userCode, new Long(saaGradeID)).get(0);
		return saaUserGrade.getId().toString();
	}

	public List<SaaCompany> findSaaCompanyList() {
		String hql = "select saaCompany from SaaCompany saaCompany";
		return super.findByHql(hql);
	}

	public SaaGradeService getSaaGradeService() {
		return saaGradeService;
	}

	public void setSaaGradeService(SaaGradeService saaGradeService) {
		this.saaGradeService = saaGradeService;
	}

	public void updateUserServicePower(String[] allowSelect, String[] forbidSelect, String userCode, String saaGradeID,
			String[] treeCheckBox) {
		String hql = "select permitCompany from SaaPermitCompany permitCompany where permitCompany.saaUserGrade.userCode=? and permitCompany.saaUserGrade.saaGrade.id=?";
		String hqlTemp = "select exceptCompany from SaaExceptCompany exceptCompany where exceptCompany.saaUserGrade.userCode=? and exceptCompany.saaUserGrade.saaGrade.id=?";
		List<SaaPermitCompany> saaPermitCompanyListTemp = super.findByHql(hql, userCode, new Long(saaGradeID));
		List<SaaExceptCompany> saaExceptCompanyListTemp = super.findByHql(hqlTemp, userCode, new Long(saaGradeID));
		super.deleteAll(saaPermitCompanyListTemp);
		super.deleteAll(saaExceptCompanyListTemp);

		List<SaaPermitCompany> saaPermitCompanyList = new ArrayList<SaaPermitCompany>(0);
		List<SaaExceptCompany> saaExceptCompanyList = new ArrayList<SaaExceptCompany>(0);
		if (allowSelect != null) {
			SaaPermitCompany saaPermitCompany;
			SaaUserGrade saaUserGrade;
			for (String companyCode : allowSelect) {
				saaPermitCompany = new SaaPermitCompany();
				saaUserGrade = this.getSaaUserGrade(userCode, saaGradeID);
				saaPermitCompany.setComCode(companyCode);
				saaPermitCompany.setSaaUserGrade(saaUserGrade);
				saaPermitCompanyList.add(saaPermitCompany);
			}
		}
		if (forbidSelect != null) {
			SaaExceptCompany saaExceptCompany;
			SaaUserGrade saaUserGrade;
			for (String companyCode : forbidSelect) {
				saaExceptCompany = new SaaExceptCompany();
				saaUserGrade = this.getSaaUserGrade(userCode, saaGradeID);
				saaExceptCompany.setComCode(companyCode);
				saaExceptCompany.setSaaUserGrade(saaUserGrade);
				saaExceptCompanyList.add(saaExceptCompany);
			}
		}
		super.saveAll(saaPermitCompanyList);
		super.saveAll(saaExceptCompanyList);

		SaaGrade saaGrade = super.get(SaaGrade.class, new Long(saaGradeID));
		QueryRule queryRuleUserGrade = QueryRule.getInstance();
		queryRuleUserGrade.addEqual("userCode", userCode);
		queryRuleUserGrade.addEqual("saaGrade", saaGrade);
		List<SaaUserGrade> saaUserGrade = super.find(SaaUserGrade.class, queryRuleUserGrade);
		QueryRule queryRulePermitProduct = QueryRule.getInstance();
		queryRulePermitProduct.addEqual("saaUserGrade", saaUserGrade.get(0));

		// String hqlProduct = "select permitProduct from SaaPermitProduct
		// permitProduct where permitProduct.saaUserGrade.userCode=? and
		// permitProduct.saaUserGrade.id=?";
		List<SaaPermitProduct> saaPermitProductListOld = super.find(SaaPermitProduct.class, queryRulePermitProduct);
		// super.findByHql(
		// hqlProduct, userCode, new Long(saaGradeID));
		List<SaaPermitProduct> saaPermitProductListNew = new ArrayList<SaaPermitProduct>(0);
		super.deleteAll(saaPermitProductListOld);
		if (treeCheckBox != null) {
			List<String> treeCheckBoxList = new ArrayList<String>(0);
			// 鍒嗗紑淇濆瓨
			List<String> treeCheckBoxListLine = new ArrayList<String>(0);
			List<String> treeCheckBoxListClass = new ArrayList<String>(0);
			List<String> treeCheckBoxListRisk = new ArrayList<String>(0);
			for (String str : treeCheckBox) {
				if (str.indexOf('.') == -1) {
					treeCheckBoxListLine.add(str);

				} else {
					if (str.indexOf('.') == str.lastIndexOf('.')) {
						treeCheckBoxListClass.add(str);

					} else {
						treeCheckBoxListRisk.add(str);

					}

				}
			}
			if (null != treeCheckBoxListLine && !treeCheckBoxListLine.isEmpty()) {
				for (String str : treeCheckBoxListLine) {
					treeCheckBoxList.add(str);
				}
			}
			if (null != treeCheckBoxListClass && !treeCheckBoxListClass.isEmpty()) {
				for (String str : treeCheckBoxListClass) {
					if (!treeCheckBoxList.contains(str.substring(0, str.indexOf('.')))) {
						treeCheckBoxList.add(str);
					}
				}
			}
			if (null != treeCheckBoxListRisk && !treeCheckBoxListRisk.isEmpty()) {
				for (String str : treeCheckBoxListRisk) {
					if (!(treeCheckBoxList.contains(str.substring(0, str.indexOf('.'))) || treeCheckBoxList.contains(str
							.substring(0, str.lastIndexOf('.'))))) {
						treeCheckBoxList.add(str);
					}
				}
			}
			if (null != treeCheckBoxList && !treeCheckBoxList.isEmpty()) {
				for (int i = 0; i < treeCheckBoxList.size(); i++) {
					SaaPermitProduct saaPermitProduct = new SaaPermitProduct();
					saaPermitProduct.setSaaUserGrade(saaUserGrade.get(0));
					saaPermitProduct.setProductCode(treeCheckBoxList.get(i));
					saaPermitProductListNew.add(saaPermitProduct);
				}
				super.saveAll(saaPermitProductListNew);
			}
			// List<String> treeCheckBoxList = new ArrayList<String>(0);
			// List<String> treeCheckBoxListLine = new ArrayList<String>(0);
			// List<String> treeCheckBoxListClass = new ArrayList<String>(0);
			// List<String> treeCheckBoxListRisk = new ArrayList<String>(0);
			// for (String str : treeCheckBox) {
			// if (str.indexOf('.') == -1) {
			// treeCheckBoxListLine.add(str);
			//
			// } else {
			// if (str.indexOf('.') == str.lastIndexOf('.')) {
			// treeCheckBoxListClass.add(str);
			//
			// } else {
			// treeCheckBoxListRisk.add(str);
			//
			// }
			//
			// }
			// }
			// if (treeCheckBoxListLine.size() > 0) {
			// for (String str : treeCheckBoxListLine) {
			// treeCheckBoxList.add(str);
			// }
			// }
			// if (treeCheckBoxListClass.size() > 0) {
			// for (String str : treeCheckBoxListClass) {
			// if (!treeCheckBoxList.contains(str.substring(0, str
			// .indexOf('.')))) {
			// treeCheckBoxList.add(str);
			// }
			// }
			// }
			// if (treeCheckBoxListRisk.size() > 0) {
			// for (String str : treeCheckBoxListRisk) {
			// if (!(treeCheckBoxList.contains(str.substring(0, str
			// .indexOf('.'))) || treeCheckBoxList.contains(str
			// .substring(0, str.lastIndexOf('.'))))) {
			// treeCheckBoxList.add(str);
			// }
			// }
			// }
			// if (treeCheckBoxList.size() > 0) {
			// for (int i = 0; i < treeCheckBoxList.size(); i++) {
			//
			// SaaPermitProduct saaPermitProduct = new SaaPermitProduct();
			// saaPermitProduct.setProductCode(treeCheckBoxList.get(i));
			// saaPermitProduct.setSaaUserGrade(this.getSaaUserGrade(
			// userCode, saaGradeID));
			// saaPermitProductListNew.add(saaPermitProduct);
			// }
			// super.saveAll(saaPermitProductListNew);
			// }

		}
	}

	public SaaUserGrade getSaaUserGrade(String userCode, String saaGradeID) {
		String hql = "select userGrade from SaaUserGrade userGrade where userGrade.userCode=? and userGrade.saaGrade.id=?";
		List<SaaUserGrade> saaUserGradeList = new ArrayList<SaaUserGrade>(0);
		saaUserGradeList = super.findByHql(hql, userCode, new Long(saaGradeID));
		if (null != saaUserGradeList && !saaUserGradeList.isEmpty()) {
			return saaUserGradeList.get(0);
		} else {
			return null;
		}
	}

	private List<SaaAuthProduct> findSaaAuthProductAllList(List<SaaAuthProduct> productList, String userCode) {
		List<SaaAuthProduct> saaAuthProductAllList = new ArrayList<SaaAuthProduct>(0);
		saaAuthProductAllList.addAll(productList);
		String classHql = "Select cla from SaaClass cla where cla.businessLineCode=? and cla.validStatus='1'";
		String riskHql = "select risk from SaaRisk risk where risk.classCode in (select cla.classCode from SaaClass cla where cla.businessLineCode=?) and risk.validStatus='1'";

		for (SaaAuthProduct pro : productList) {
			if (pro.getProductCode().indexOf(".") == -1) {
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
					product.setProductCode(saaInsuranceCategoryService.findSaaClassByRiskCode(risk.getRiskCode())
							.getBusinessLineCode()
							+ "." + risk.getClassCode() + "." + risk.getRiskCode());
					saaAuthProductAllList.add(product);
				}
			} else if (pro.getProductCode().indexOf(".") == pro.getProductCode().lastIndexOf(".")) {
				String hql = "select risk from SaaRisk risk where risk.classCode =? and risk.validStatus='1'";
				List<SaaRisk> saaRiskList = super.findByHql(hql, pro.getProductCode().substring(
						pro.getProductCode().indexOf(".") + 1));
				for (SaaRisk risk : saaRiskList) {
					SaaAuthProduct product = new SaaAuthProduct();
					product.setUserCode(userCode);
					product.setProductCode(saaInsuranceCategoryService.findSaaClassByRiskCode(risk.getRiskCode())
							.getBusinessLineCode()
							+ "." + risk.getClassCode() + "." + risk.getRiskCode());
					saaAuthProductAllList.add(product);
				}
			}

		}
		return saaAuthProductAllList;
	}

	private List<SaaPermitProduct> findSaaPermitProductAllList(List<SaaPermitProduct> productList, SaaUserGrade saaUserGrade) {
		List<SaaPermitProduct> saaPermitProductAllList = new ArrayList<SaaPermitProduct>(0);
		saaPermitProductAllList.addAll(productList);
		String classHql = "Select cla from SaaClass cla where cla.businessLineCode=? and cla.validStatus='1'";
		String riskHql = "select risk from SaaRisk risk where risk.classCode in (select cla.classCode from SaaClass cla where cla.businessLineCode=?) and risk.validStatus='1'";

		for (SaaPermitProduct pro : productList) {
			if (pro.getProductCode().indexOf(".") == -1) {
				List<SaaClass> saaClassList = super.findByHql(classHql, pro.getProductCode());
				List<SaaRisk> saaRiskList = super.findByHql(riskHql, pro.getProductCode());
				for (SaaClass cla : saaClassList) {
					SaaPermitProduct product = new SaaPermitProduct();
					product.setSaaUserGrade(saaUserGrade);
					product.setProductCode(cla.getBusinessLineCode() + "." + cla.getClassCode());
					saaPermitProductAllList.add(product);
				}
				for (SaaRisk risk : saaRiskList) {
					SaaPermitProduct product = new SaaPermitProduct();
					product.setSaaUserGrade(saaUserGrade);
					product.setProductCode(saaInsuranceCategoryService.findSaaClassByRiskCode(risk.getRiskCode())
							.getBusinessLineCode()
							+ "." + risk.getClassCode() + "." + risk.getRiskCode());
					saaPermitProductAllList.add(product);
				}
			} else if (pro.getProductCode().indexOf(".") == pro.getProductCode().lastIndexOf(".")) {
				String hql = "select risk from SaaRisk risk where risk.classCode =? and risk.validStatus='1'";
				List<SaaRisk> saaRiskList = super.findByHql(hql, pro.getProductCode().substring(
						pro.getProductCode().indexOf(".") + 1));
				for (SaaRisk risk : saaRiskList) {
					SaaPermitProduct product = new SaaPermitProduct();
					product.setSaaUserGrade(saaUserGrade);
					product.setProductCode(saaInsuranceCategoryService.findSaaClassByRiskCode(risk.getRiskCode())
							.getBusinessLineCode()
							+ "." + risk.getClassCode() + "." + risk.getRiskCode());
					saaPermitProductAllList.add(product);
				}
			}

		}
		return saaPermitProductAllList;
	}

	public List<SaaRiskObjectVO> findSaaRiskObjectVOList(String userCode, String saaGradeID, String userCodeOperate) {
		List<SaaRiskObjectVO> saaRiskObjectVOList = new ArrayList<SaaRiskObjectVO>(0);
		String hql = "select saaPermitProduct from SaaPermitProduct saaPermitProduct where saaPermitProduct.saaUserGrade.userCode=? and saaPermitProduct.saaUserGrade.saaGrade.id=?";
		List<SaaPermitProduct> saaPermitProductList = super.findByHql(hql, userCode, new Long(saaGradeID));
		SaaUserGrade saaUserGrade = this.getSaaUserGrade(userCode, saaGradeID);
		List<SaaPermitProduct> saaPermitProductAllList = this.findSaaPermitProductAllList(saaPermitProductList, saaUserGrade);
		Map<String, SaaPermitProduct> saaPermitProductMap = new HashMap<String, SaaPermitProduct>();
		for (SaaPermitProduct product : saaPermitProductAllList) {
			saaPermitProductMap.put(product.getProductCode(), product);
		}
		String hqlTemp = "select product from SaaAuthProduct product where product.userCode=?";
		List<SaaAuthProduct> saaSpareProductList = super.findByHql(hqlTemp, userCodeOperate);
		List<SaaAuthProduct> saaSpareProductAllList = this.findSaaAuthProductAllList(saaSpareProductList, userCode);
		Map<String, SaaAuthProduct> saaSpareProductMap = new HashMap<String, SaaAuthProduct>();
		for (SaaAuthProduct pro : saaSpareProductAllList) {
			saaSpareProductMap.put(pro.getProductCode(), pro);
		}
		List<SaaBusinessline> saaProductLines = saaProductLineService.findSaaProductLineList();
		List<SaaRisk> saaInsurances = saaInsuranceService.findSaaInsuranceList();
		List<SaaClass> saaInsuranceCategories = saaInsuranceCategoryService.findSaaInsuranceCategoryList();

		for (SaaBusinessline saaBusinessline : saaProductLines) {
			SaaRiskObjectVO saaRiskObjectVO = new SaaRiskObjectVO();
			saaRiskObjectVO.setRiskObjectName(saaBusinessline.getBusinessLineName());
			saaRiskObjectVO.setRiskObjectParentCode("0");
			saaRiskObjectVO.setRiskObjecCode(saaBusinessline.getBusinessLineCode());
			SaaPermitProduct permitProduct = saaPermitProductMap.get(saaBusinessline.getBusinessLineCode());
			if (permitProduct == null) {
				saaRiskObjectVO.setValue("0");
			} else {
				saaRiskObjectVO.setValue("1");
			}
			SaaAuthProduct authProduct = saaSpareProductMap.get(saaBusinessline.getBusinessLineCode());
			if ("00000000".equals(userCodeOperate)) {
				saaRiskObjectVO.setHasPower("0");
			} else {
				if (authProduct == null) {
					saaRiskObjectVO.setHasPower("1");
				} else {
					saaRiskObjectVO.setHasPower("0");
				}
			}
			saaRiskObjectVOList.add(saaRiskObjectVO);
		}

		for (SaaClass saaClass : saaInsuranceCategories) {
			SaaRiskObjectVO saaRiskObjectVO = new SaaRiskObjectVO();
			saaRiskObjectVO.setRiskObjectName(saaClass.getClassName());
			saaRiskObjectVO.setRiskObjectParentCode(saaClass.getBusinessLineCode());
			saaRiskObjectVO.setRiskObjecCode(saaClass.getBusinessLineCode() + "." + saaClass.getClassCode());
			SaaPermitProduct permitProduct = saaPermitProductMap.get(saaClass.getBusinessLineCode() + "."
					+ saaClass.getClassCode());
			if (permitProduct == null) {
				saaRiskObjectVO.setValue("0");
			} else {
				saaRiskObjectVO.setValue("1");
			}
			SaaAuthProduct authProduct = saaSpareProductMap.get(saaClass.getBusinessLineCode() + "." + saaClass.getClassCode());
			if ("00000000".equals(userCodeOperate)) {
				saaRiskObjectVO.setHasPower("0");
			} else {
				if (authProduct == null) {
					saaRiskObjectVO.setHasPower("1");
				} else {
					saaRiskObjectVO.setHasPower("0");
				}
			}

			saaRiskObjectVOList.add(saaRiskObjectVO);
		}

		for (SaaRisk saaRisk : saaInsurances) {
			SaaRiskObjectVO saaRiskObjectVO = new SaaRiskObjectVO();
			saaRiskObjectVO.setRiskObjectName(saaRisk.getRiskcname());
			saaRiskObjectVO.setRiskObjectParentCode(saaInsuranceCategoryService.findSaaClassByRiskCode(saaRisk.getRiskCode())
					.getBusinessLineCode()
					+ "." + saaRisk.getClassCode());
			saaRiskObjectVO.setRiskObjecCode(saaInsuranceCategoryService.findSaaClassByRiskCode(saaRisk.getRiskCode())
					.getBusinessLineCode()
					+ "." + saaRisk.getClassCode() + "." + saaRisk.getRiskCode());
			SaaPermitProduct permitProduct = saaPermitProductMap.get(saaInsuranceCategoryService.findSaaClassByRiskCode(
					saaRisk.getRiskCode()).getBusinessLineCode()
					+ "." + saaRisk.getClassCode() + "." + saaRisk.getRiskCode());
			if (permitProduct == null) {
				saaRiskObjectVO.setValue("0");
			} else {
				saaRiskObjectVO.setValue("1");
			}
			SaaAuthProduct authProduct = saaSpareProductMap.get(saaInsuranceCategoryService.findSaaClassByRiskCode(
					saaRisk.getRiskCode()).getBusinessLineCode()
					+ "." + saaRisk.getClassCode() + "." + saaRisk.getRiskCode());
			if ("00000000".equals(userCodeOperate)) {
				saaRiskObjectVO.setHasPower("0");
			} else {
				if (authProduct == null) {
					saaRiskObjectVO.setHasPower("1");
				} else {
					saaRiskObjectVO.setHasPower("0");
				}
			}
			saaRiskObjectVOList.add(saaRiskObjectVO);
		}
		return saaRiskObjectVOList;
	}

	public void deleteAllUserGrade(List<SaaUserGrade> list) {
		if (null != list && !list.isEmpty()) {

			super.deleteAll(list);
		}
	}

	public void saveAllUserGrade(List<SaaUserGrade> list) {
		if (null != list && !list.isEmpty()) {

			super.saveAll(list);
		}
	}
	
	public boolean isExist(String userCode, Long gradeId){
		String hql = "from SaaUserGrade saaUserGrade where saaUserGrade.userCode = ? and saaGrade.id = ?";
		List<SaaUserGrade> saaUserGradeList = super.findByHql(hql, userCode, gradeId);
		if(saaUserGradeList.size()>0){
			return true;
		}
		return false;
	}
	/**判断是都为管理员*/
	@Override
	public boolean isSuperManager(String userCode) {
		if(userCode==null||"".equals(userCode)){
			return false;
		}else if("00000000".equals(userCode)){
			return true;	
		}else{
			return isExist(userCode,(long) 1);
		}
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

	public SaaPowerHelpService getSaaPowerHelpService() {
		return saaPowerHelpService;
	}

	public void setSaaPowerHelpService(SaaPowerHelpService saaPowerHelpService) {
		this.saaPowerHelpService = saaPowerHelpService;
	}


	// public SaaUserService getSaaUserService() {
	// return saaUserService;
	// }
	//
	// public void setSaaUserService(SaaUserService saaUserService) {
	// this.saaUserService = saaUserService;
	// }

}
