package com.sinosoft.sys.platform.power.service.spring;

import ins.framework.common.EncryptUtils;
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;
import ins.framework.exception.BusinessException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.util.Assert;

import com.sinosoft.sys.platform.company.service.facade.CompanyService;
import com.sinosoft.sys.platform.power.model.SaaCompany;
import com.sinosoft.sys.platform.power.model.SaaGrade;
import com.sinosoft.sys.platform.power.model.SaaSystem;
import com.sinosoft.sys.platform.power.model.SaaUserGrade;
import com.sinosoft.sys.platform.power.model.SaaUserInstead;
import com.sinosoft.sys.platform.power.service.facade.SaaCompanyService;
import com.sinosoft.sys.platform.power.service.facade.SaaPowerHelpService;
import com.sinosoft.sys.platform.power.service.facade.SaaPowerService;



@SuppressWarnings({ "unchecked", "rawtypes" })
public class SaaPowerServiceSpringImpl extends GenericDaoHibernate implements SaaPowerService {
	
	private SaaPowerHelpService saaPowerHelpService;

	public SaaPowerHelpService getSaaPowerHelpService() {
		return saaPowerHelpService;
	}

	public void setSaaPowerHelpService(SaaPowerHelpService saaPowerHelpService) {
		this.saaPowerHelpService = saaPowerHelpService;
	}
	
	private SaaCompanyService saaCompanyService;
	private CompanyService companyService;

	/**
	 * 查是否有
	 * @param userCode 员工代码
	 * @param taskCode 功能代码
	 * @param powerType 权限 1-内网权限 2-外网权限
	 * @param gradesIdString  岗位列表 
	 * @return true 有权 false 无权
	 */
	public boolean checkPower(String userCode, String taskCode, int powerType,
			String gradesIdString) {
		// ??查员工代码???功能代码，权限值是否存在，如果不存在则抛出异常
		Assert.hasText(userCode, "userCode must have value.");
		Assert.hasText(taskCode, "taskCode must have value.");
		Assert.hasText(powerType + "", "powerType must have value.");
		if(userCode.equals("00000000")){
			return true;
		}
		List<String> gradeList = new ArrayList<String>();
		// 如果有岗位则遍历这些岗位
		if (gradesIdString.length() > 0) {
			String[] grades = gradesIdString.split(",");
			for (int i = 0; i < grades.length; i++) {
				gradeList.add(grades[i]);
			}
		} else {// 如果没有岗位则根据员工代码查询出其所有岗位並遍历
			gradeList = this.findByHql("select saaGrade.id from SaaUserGrade userGrade where userGrade.userCode=? and (userGrade.invalidDate>sysdate or userGrade.invalidDate is null)",userCode);
		}
		StringBuilder builder = new StringBuilder();
		StringBuilder gradeIdHql = new StringBuilder();
		if (gradeList.size() > 0) {
			for (int j = 0; j < gradeList.size(); j++) {
				gradeIdHql.append(",'");
				gradeIdHql.append(String.valueOf(gradeList.get(j)));
				gradeIdHql.append("'");
			}
			builder.append("select count(*) from");
			builder.append(" SaaGradeTask gradeTask,SaaTask task");
			builder.append(" where gradeTask.saaTask.id=task.id ");
			builder.append(" and gradeTask.saaGrade.id in (");
			builder.append(gradeIdHql.substring(1)).append(")");
			switch (powerType) {
			case 1:
				builder.append(" and gradeTask.intranetValue ='1'");
				break;
			case 2:
				builder.append(" and gradeTask.internetValue ='1'");
				break;
			default:
				throw new IllegalArgumentException("powerType must be 1 or 2!");
			}
			builder.append(" and task.taskCode=?");
			long count = super.getCount(builder.toString(),taskCode);
			if (count > 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取管理权限范围
	 * 
	 * @param strings[0] userCode
	 *            员工代码
	 * @param strings[1] userCodeFields
	 *            员工代码字段??
	 * @param strings[2] companyCodeFields
	 *            机构代码字段??
	 * @param strings[3] riskCodeFields
	 *            险种代码字段??
	 * @param strings[4] adminFlag
	 * 				机构范围标志：A-返回该管理员管理机构范围??对应的所有代理机构；B-返回该管理员管理机构范围;C-返回前两者合??           
	 * @return String 查询范围的SQL查询条件
	 */
	
	public String addAuthPower(String[] strings) {
		StringBuilder buffer = new StringBuilder();
		// 查员工代码是否存在，如果不存在则抛出异常
		Assert.hasText(strings[0], "userCode must have value.");
		

			StringBuilder perBuilder = new StringBuilder();
			StringBuilder exceBuilder = new StringBuilder();
			StringBuilder riskBuilder = new StringBuilder();
			List<String> permitComList=new ArrayList<String>();
			List<String> exceComList=new ArrayList<String>();
			List<String> productCodeList=new ArrayList<String>();

			// ---------------------------------------------------机构范围begin----------------------------------
				//获取允许机构范围
				//改，将允许机构及下级机构加入到permitComList中然後去重复??
			if(strings[4].equals("A")){
				permitComList.addAll(saaPowerHelpService.getAuthPermitAgentCom(strings[0], null));
			}else if(strings[4].equals("B")){
				permitComList.addAll(saaPowerHelpService.getAuthPermitCom(strings[0], null));
				
			}else if(strings[4].equals("C")){
				permitComList.addAll(saaPowerHelpService.getAuthPermitCom(strings[0], null));
				permitComList.addAll(saaPowerHelpService.getAuthPermitAgentCom(strings[0], null));
			}
				// 除外机构范围
				exceComList.addAll(saaPowerHelpService.getAuthExceCom(strings[0], null));
				// 获取险种范围
				productCodeList.addAll(saaPowerHelpService.getAuthRisksByProductCode(strings[0]));

			perBuilder.append(saaPowerHelpService.removeDuplicateWithOrder(permitComList));
			exceBuilder.append(saaPowerHelpService.removeDuplicateWithOrder(exceComList));
			riskBuilder.append(saaPowerHelpService.removeDuplicateWithOrder(productCodeList));
			
			String comPerSql = saaPowerHelpService.getComPerRange(perBuilder.toString());
			String comExcSql = saaPowerHelpService.getComExcRange(exceBuilder.toString());
			String[] companyCodeFieldsArray = strings[2].split(",");
			if (companyCodeFieldsArray.length > 0) {
				buffer.append("(");
				for (int i = 0; i < companyCodeFieldsArray.length; i++) {
					if (i > 0) {
						buffer.append(" or ");
					}
					buffer.append(companyCodeFieldsArray[i]);
					buffer.append(comPerSql);
					
					if(comExcSql.length()>0){
						if(comExcSql.equals("nohave")){
							
						}else{
							buffer.append(" and (" + companyCodeFieldsArray[i]);
							buffer.append(comExcSql+")");
						}
						
					}
									
				}
				buffer.append(")");
			}
			// ---------------------------------------------------机构范围end--------------------------------------------
			// ---------------------------------------------------险种范围(产品、险类???险??)begin----------------------------
			if (strings[3].length() > 0) {
				if (buffer.toString().length() > 0) {
					buffer.append(" and ");
				}
				buffer.append(strings[3]);
				buffer.append(" in (");
				buffer.append(riskBuilder.toString());
				buffer.append(")");
			}
			// ---------------------------------------------------险种范围(产品、险类???险??)end----------------------------
			//----------------------------------员工部分begin----------------------------
			if(strings[1].length()>0){
				if (buffer.toString().length() > 0) {
					buffer.append(" and ");
				}
				String[] userCodeFieldsArray = strings[1].split(",");		
				buffer.append("(");
				for (int i = 0; i < userCodeFieldsArray.length; i++) {
					if (i > 0) {
						buffer.append(" or ");
					}
					buffer.append(userCodeFieldsArray[i]);
					buffer.append("=");
					buffer.append(strings[0]);				
				}
				buffer.append(")");
			}
			//----------------------------------员工部分end----------------------------
		return buffer.toString();

	}
	/**
	 * 获取业务权限范围
	 * 
	 * @param userCode  员工代码
	 * @param taskcode  功能代码
	 * @param userCodeFields
	 *            员工代码字段??
	 * @param companyCodeFields
	 *            机构代码字段??
	 * @param riskCodeFields
	 *            险种代码字段??
	 * @param gradesIdString
	 *            岗位列表
	 * @return String 查询范围的SQL查询条件
	 */
	public String addPower(String userCode, String taskCode,
			String userCodeFields, String companyCodeFields,
			String riskCodeFields, String gradesIdString, String businssComCode) {
		StringBuilder buffer = new StringBuilder();
		List<Long> gradeList = new ArrayList<Long>();
		List<String> powerList = new ArrayList<String>();
		List<SaaUserGrade> userGradeIdList = new ArrayList<SaaUserGrade>();
		// ??查员工代码???功能代码是否存在，如果不存在则抛出异常
		Assert.hasText(userCode, "userCode must have value.");
		Assert.hasText(taskCode, "taskCode must have value.");
//		Assert.hasText(companyCodeFields, "companyCodeFields must have value.");
//		Assert.hasText(companyCodeFields,"companyCodeFields must have value.");
//		String key = cacheManager.generateCacheKey("addPower", userCode,
//				taskCode, userCodeFields, companyCodeFields, riskCodeFields,
//				gradesIdString);
//		Object result = cacheManager.getCache(key);
//		if (result != null) {
//			return (String) result;
//		}
		if(userCode.equals("00000000")){
			return " (1=1)";
		}
		// 如果有岗位则遍历这些岗位
		if (gradesIdString.length() > 0) {
			String[] grades = gradesIdString.split(",");
			for (int i = 0; i < grades.length; i++) {
				gradeList.add(Long.valueOf(grades[i]));
			}
			boolean resBool = saaPowerHelpService.checkTaskByGrade(taskCode,gradeList);
			if (!resBool) {
				throw new BusinessException(taskCode + " 崗位中不存在此功能代碼，請核實後再查詢", false);
			}
		}else {	// 如果没有岗位则根据员工代码查询出其所有岗位並遍历
			gradeList = this
					.findByHql(
							"select userGrade.saaGrade.id from SaaUserGrade userGrade where userGrade.userCode=? and (userGrade.invalidDate>sysdate or userGrade.invalidDate is null) and " +
							"userGrade.saaGrade.id in(select gradeTask.saaGrade.id from SaaGradeTask gradeTask " +
							"where gradeTask.saaTask.id =(select saaTaskm.id from SaaTask saaTaskm where saaTaskm.taskCode=?))",
							userCode,taskCode);
			if(gradeList.size()<=0){
					throw new BusinessException(taskCode + " 崗位中不存在此功能代碼或您的崗位已過期，請核實後再查詢", false);
			}
		}
		
		if (gradeList.size() > 0) {
			for (int i = 0; i < gradeList.size(); i++) {
				// 根据岗位代码gradeId和人员代码usercode查找业务范围的关联主??
				QueryRule queryRule = QueryRule.getInstance();
				queryRule.addEqual("userCode", userCode);
				queryRule.addEqual("saaGrade.id", gradeList.get(i));
				userGradeIdList = super.find(SaaUserGrade.class, queryRule);
				// 建立权限记录表powerList
				if (userGradeIdList.size() > 0) {
					for (int j = 0; j < userGradeIdList.size(); j++) {
						powerList.add(String.valueOf(userGradeIdList.get(j)
								.getId()));
					}
				}
			}
		}
		
		if (powerList.size() > 0) {
			StringBuilder perBuilder = new StringBuilder();
			StringBuilder exceBuilder = new StringBuilder();
			StringBuilder riskBuilder = new StringBuilder();
			List<String> permitComList=new ArrayList<String>();
			List<String> exceComList=new ArrayList<String>();
			List<String> permitRiskList=new ArrayList<String>();
			// ---------------------------------------------------机构范围begin----------------------------------
			for (int k = 0; k < powerList.size(); k++) {
				// 获取允许机构范围
				
//				permitCom = saaPowerHelpService.getPermitCom(Long
//						.parseLong(powerList.get(k)));
				//改???，将允许机构及下级机构加入到permitComList中然後去重复??
				if(companyCodeFields.length()>0){
					permitComList.addAll(saaPowerHelpService.getPermitCom(Long
							.parseLong(powerList.get(k)), null));
					
//					if (k > 0 && permitCom.length() > 0) {
//						perBuilder.append(",");
//					}
//					perBuilder.append(permitCom);
					// 除外机构范围
					exceComList.addAll(saaPowerHelpService.getExceComList((Long.parseLong(powerList
							.get(k))), null));
//					exceCom = saaPowerHelpService.getExceCom(Long.parseLong(powerList
//							.get(k)));
//					if (k > 0 && exceCom.length() > 0) {
//						exceBuilder.append(",");
//					}
//					exceBuilder.append(exceCom);
				}
				
				
				// 获取险种范围
//				riskCodes = saaPowerHelpService.getRisksByProductCode(Long
//						.parseLong(powerList.get(k)));
//				
//				if (k > 0 && riskCodes.length() > 0) {
//					riskBuilder.append(",");
//				}
//				riskBuilder.append(riskCodes);
				permitRiskList.addAll(saaPowerHelpService.getRisksByProductCode(Long
						.parseLong(powerList.get(k))));
				
			}
			// 业务控制
			if(businssComCode != null && !"".equals(businssComCode.trim()) && !"9999999998".equals(businssComCode)){
				if(permitComList.contains("9999999998")){
					permitComList = saaPowerHelpService.getSubAllCompanyCode(businssComCode.trim(), null);
				}else{
					// 有业务控制，就合並业务和权限两种控制，取交集
					permitComList = saaPowerHelpService.getMixed(permitComList, saaPowerHelpService.getSubAllCompanyCode(businssComCode.trim(), null));
				}
			}
			// 没有业务控制，就直接取权限控制
			perBuilder.append(saaPowerHelpService.removeDuplicateWithOrder(permitComList));
			riskBuilder.append(saaPowerHelpService.removeDuplicateWithOrder(permitRiskList));
			exceBuilder.append(saaPowerHelpService.removeDuplicateWithOrder(exceComList));
			String comPerSql = saaPowerHelpService.getComPerRange(perBuilder.toString());
			String comExcSql = saaPowerHelpService.getComExcRange(exceBuilder.toString());
			
			String[] companyCodeFieldsArray = companyCodeFields.split(",");	
			if(userCodeFields.length()>0){
				buffer.append("(");
			}
			if (companyCodeFields.length() > 0) {
				
				buffer.append("(");
				for (int i = 0; i < companyCodeFieldsArray.length; i++) {
					if (i > 0) {
						buffer.append(" or ");
					}
					if (permitComList.contains("9999999998")) {
						buffer.append(" (1=1) ");
					}else{
						
						buffer.append(companyCodeFieldsArray[i]);
						buffer.append(comPerSql);
					}
//					buffer.append(")");
					if(comExcSql.length()>0&&!comExcSql.equals("nohave")){
						buffer.append(" and (" + companyCodeFieldsArray[i]);
						buffer.append(comExcSql+")");
					}
									
				}
				buffer.append(")");
				
			}
		
			// ---------------------------------------------------机构范围end--------------------------------------------
			// ---------------------------------------------------险种范围(产品、险类???险??)begin----------------------------
			if (riskCodeFields.length() > 0) {
				if (buffer.toString().length() > 0) {
					buffer.append(" and ");
				}
				buffer.append(riskCodeFields);
				buffer.append(" in (");
					buffer.append(riskBuilder.toString());
				buffer.append(")");
			}
			// ---------------------------------------------------险种范围(产品、险类???险??)end----------------------------
			//----------------------------------员工部分begin----------------------------
			if(userCodeFields.length()>0){
				if (buffer.toString().length() > 0) {
					if(taskCode.startsWith("PNC_")){
						buffer.append(" AND ");
					}else{
						buffer.append(" or ");
					}
				}
				String[] userCodeFieldsArray = userCodeFields.split(",");		
				buffer.append("(");
				for (int i = 0; i < userCodeFieldsArray.length; i++) {
					if (i > 0) {
						buffer.append(" or ");
					}
					if(userCodeFieldsArray[i].indexOf("=")>0){
						buffer.append(userCodeFieldsArray[i]);
					}else{
						buffer.append(userCodeFieldsArray[i]);
						buffer.append("='");
						buffer.append(userCode+"'");
					}
									
				}
				buffer.append("))");
			}
			//----------------------------------员工部分end----------------------------
		}
		return buffer.toString();

	}

	public String addBusinessPower(String comCode, String companyCodeFields){
		// 以下情况，不再处理
		if(comCode == null || "".equals(comCode.trim()) || "00".equals(comCode.trim()) 
				|| companyCodeFields == null || "".equals(companyCodeFields.trim())){
			return " (1=1) ";
		}
		StringBuilder hqlBuilder = new StringBuilder();
		// 找到下级机构
		List<String> comCodes = saaPowerHelpService.getSubAllCompanyCode(comCode, null);
		// 组织机构代码
		String comCodesStr = saaPowerHelpService.removeDuplicateWithOrder(comCodes);
		// 组织subSql￡￡￡in（'',''）格式
		String comPerSql = saaPowerHelpService.getComPerRange(comCodesStr);
		String[] companyCodeFieldsArray = companyCodeFields.split(",");	
		hqlBuilder.append(" (");
		for (int i = 0; i < companyCodeFieldsArray.length; i++) {
			if (i > 0) {
				hqlBuilder.append(" or ");
			}
			hqlBuilder.append(companyCodeFieldsArray[i]);
			hqlBuilder.append(comPerSql);
		}
		hqlBuilder.append(")");
		return hqlBuilder.toString();
	}
	
	/**
	 * 根据当前员工获取有效授权人员列表
	 * 
	 * @param userCode
	 *            员工代码
	 * @return list 授权员工列表
	 */
	
	public List<String> getInsteadUserList(String userCode) {
		List<String> authUserCodeList = new ArrayList<String>();
		String authUserCode = "";
		Date now = new Date();
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("userCode", userCode);
		queryRule.addGreaterEqual("authEndTime", now);
		queryRule.addLessEqual("authBeginTime", now);
		//有效值校?? Modify By yaochunyang 20080710
		queryRule.addEqual("validStatus", "1");
		//有效值校?? Modify End
		List<SaaUserInstead> userInsteadList = super.find(SaaUserInstead.class,
				queryRule);
		for (int i = 0; i < userInsteadList.size(); i++) {
			authUserCode = userInsteadList.get(i).getAuthUserCode();
			authUserCodeList.add(authUserCode);
		}
		return authUserCodeList;
	}

	/**
	 * 根据代班员工和授权员工查询授权岗位id集合
	 * 
	 * @param userCode
	 *            员工代码
	 * @param insteadUserCode
	 *            授权员工
	 * @return String 授权岗位id集合
	 */
	
	public String getInsteadGradesIdString(String userCode,
			String insteadUserCode) {
		String gradeIds = "";
		StringBuilder builder = new StringBuilder();
		String userGrades = "";
		// ??查员工代码???被代班的员工代码是否存在，如果不存在则抛出异常
		Assert.hasText(userCode, "userCode must have value.");
		Assert.hasText(insteadUserCode, "insteadUserCode must have value.");
		// 从员工代班授权表查询出传入员工和被代班的员工代码相匹配的代班授权记录，返回其岗位列表
		// 其中相匹配需满足如下条件??1、在授权时间范围之内 2、效力状态为有效
		Date now = new Date();
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("userCode", userCode);
		queryRule.addEqual("authUserCode", insteadUserCode);
		queryRule.addEqual("validStatus", "1");
		queryRule.addGreaterEqual("authEndTime", now);
		queryRule.addLessEqual("authBeginTime", now);
		List<SaaUserInstead> userInsteadList = super.find(SaaUserInstead.class,
				queryRule);
		for (int i = 0; i < userInsteadList.size(); i++) {
			userGrades = userInsteadList.get(i).getUserGrades();
			builder.append(",");
			builder.append(userGrades);
		}
		gradeIds = builder.substring(1);
		return gradeIds;
	}

	/**
	 * 登录服务
	 * 
	 * @param userCode
	 *            员工代码
	 * @param password
	 *            员工密码
	 */
	public void login(String userCode, String passWord) {
		// ??查员工代码和密码是否存在 不存在则抛出异常
		Assert.hasText(userCode, "userCode must have value.");
		Assert.hasText(passWord, "passWord must have value.");
		// 将页面明文密码转换成md5机密後密??
		passWord = EncryptUtils.sinosoftEncrypt(passWord.trim());
		// 从数据库中检查该员工代码/密码是否存在，如果不存在则抛出异??
		String hql = "select count(*) from SaaUser where userCode=? and passWord=?";
		long count = super.getCount(hql, userCode, passWord);
		if (count <= 0) {
			throw new BusinessException("員工代碼或密碼不正確??", false);
		}
		Date now = new Date();
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("authUserCode", userCode);
		queryRule.addEqual("validStatus", "1");
		queryRule.addGreaterEqual("authEndTime", now);
		queryRule.addLessEqual("authBeginTime", now);
		List<SaaUserInstead> authEndTimeList = super.find(SaaUserInstead.class,
				queryRule);
		if (authEndTimeList.size() > 0) {
			throw new BusinessException("已授權給其他員工代班!", false);
		}
	}
	/**
	 * 根据人员代码、功能代码返回可操作机构列表
	 */
	public List<String> getPowerComList(String userCode, String taskCode){
		List<Long> gradeList = new ArrayList<Long>();
		List<String> powerList = new ArrayList<String>();
		List<SaaUserGrade> userGradeIdList = new ArrayList<SaaUserGrade>();
		List<String> permitComList=new ArrayList<String>();
		// ??查员工代码???功能代码是否存在，如果不存在则抛出异常
		Assert.hasText(userCode, "userCode must have value.");
		Assert.hasText(taskCode, "taskCode must have value.");
			gradeList = this
					.findByHql(
							"select userGrade.saaGrade.id from SaaUserGrade userGrade where userGrade.userCode=? and (userGrade.invalidDate>sysdate or userGrade.invalidDate is null) and " +
							"userGrade.saaGrade.id in(select gradeTask.saaGrade.id from SaaGradeTask gradeTask " +
							"where gradeTask.saaTask.id =(select saaTaskm.id from SaaTask saaTaskm where saaTaskm.taskCode=?))",
							userCode,taskCode);
			if(gradeList.size()<=0){
					throw new BusinessException(taskCode + " 崗位中不存在此功能代碼或您的崗位已過期，請核實後在查??", false);
			}
		
		if (gradeList.size() > 0) {
			for (int i = 0; i < gradeList.size(); i++) {
				// 根据岗位代码gradeId和人员代码usercode查找业务范围的关联主??
				QueryRule queryRule = QueryRule.getInstance();
				queryRule.addEqual("userCode", userCode);
				queryRule.addEqual("saaGrade.id", gradeList.get(i));
				userGradeIdList = super.find(SaaUserGrade.class, queryRule);
				// 建立权限记录表powerList
				if (userGradeIdList.size() > 0) {
					for (int j = 0; j < userGradeIdList.size(); j++) {
						powerList.add(String.valueOf(userGradeIdList.get(j)
								.getId()));
					}
				}
			}
		}
		
		if (powerList.size() > 0) {
			
			List<String> exceCom = new ArrayList<String>();
			// ---------------------------------------------------机构范围begin----------------------------------
			for (int k = 0; k < powerList.size(); k++) {
				permitComList.addAll(saaPowerHelpService.getPermitComWithOut(Long
						.parseLong(powerList.get(k)), null));
				
				
				exceCom.addAll(saaPowerHelpService.getExceComList(Long.parseLong(powerList
						.get(k)), null));
				
			}
			if(permitComList.size()>0){
				
				Set<String> set = new HashSet<String>();
				  List<String> newList = new ArrayList<String>();
				  for (Iterator<String> iter = permitComList.iterator(); iter.hasNext();) {
				   String element = iter.next();
				   if (set.add(element))
				    newList.add(element);
				  }
				  permitComList.clear();
				  permitComList.addAll(newList);
				  
			  }				
			  else{
				  permitComList.add(null);
			  }
			if(exceCom.size()>0){
				Set set = new HashSet();
				  List newList = new ArrayList<String>();
				  for (Iterator<String> iter = exceCom.iterator(); iter.hasNext();) {
				   String element = iter.next();
				   if (set.add(element))
				    newList.add(element);
				  }
				  exceCom.clear();
				  exceCom.addAll(newList);
				  for (int cl = 0; cl < exceCom.size(); cl++) {
					  permitComList.remove(exceCom.get(cl));
				}
			}
			
		
		}
		return permitComList;
	}
	/**
	 * 根据功能代码、机构代码???险种代码返回有权限的人员列??
	 */
	public List<String> getPowerUser(String taskCode, String comCode ,
			String riskCode){
		Assert.hasText(taskCode, "taskCode must have value.");
		Assert.hasText(comCode, "comCode must have value.");
		Assert.hasText(riskCode,"riskCode must have value.");
		List<Long> userGradeList = new ArrayList<Long>();
		userGradeList = this
		.findByHql(
				"select userGrade.id from"
				+" SaaUserGrade userGrade"
				+" where saaGrade.id in"
				+"(select saaGrade.id from SaaGradeTask saaGradeTask where saaTask.id"
				+"=(select id from SaaTask saaTask where taskCode=?))"
				+" and (userGrade.invalidDate>sysdate or userGrade.invalidDate is null) and userGrade.validStatus='1'",
//				+" and userGrade.InvalidDate>sysdate",
				taskCode);
		List<String> primUserCode=new ArrayList<String>();
		List<String> pUserCode=new ArrayList<String>();
		List<String> riskCodeList=new ArrayList<String>();
		String[] riskCodesArray = riskCode.split(",");
			for (int ii = 0; ii < riskCodesArray.length; ii++) {
				riskCodeList.add(riskCodesArray[ii]);
			}
		for (int i = 0; i < userGradeList.size(); i++) {
		if((saaPowerHelpService.removeDuplicateWithOrder((List)saaPowerHelpService.getPermitComWithOut(userGradeList.get(i), null))).indexOf(comCode)>-1&&
			(saaPowerHelpService.getExceCom(userGradeList.get(i), null)).indexOf(comCode)==-1){
				if(((List)saaPowerHelpService.getRisksByProductCode(userGradeList.get(i))).containsAll(riskCodeList)){
					pUserCode=this.findByHql("select userGrade.userCode from SaaUserGrade userGrade where id=?", userGradeList.get(i));
					primUserCode.addAll(pUserCode);
				}
		}
		}
		
		  if(primUserCode.size()>0){
			  Set<String> set = new HashSet<String>();
			  List<String> newList = new ArrayList<String>();
			  for (Iterator<String> iter = primUserCode.iterator(); iter.hasNext();) {
			   String element = iter.next();
			   if (set.add(element))
			    newList.add(element);
			  }
			  primUserCode.clear();
			  primUserCode.addAll(newList);
			  
		  }				
		  else{
			  primUserCode.add("");
		  }
		  return primUserCode;
	}
	/**
	 * 根据功能代码、机构代码???险种代??(可为??)返回有权限的人员列表
	 */
	public List<String> getPowerUserCodes(String taskCode, String comCode ,
			String riskCode){
		Assert.hasText(taskCode, "taskCode must have value.");
		Assert.hasText(comCode, "comCode must have value.");
		List<Long> userGradeList = new ArrayList<Long>();
		userGradeList = this
		.findByHql(
				"select userGrade.id from"
				+" SaaUserGrade userGrade"
				+" where saaGrade.id in"
				+"(select saaGrade.id from SaaGradeTask saaGradeTask where saaTask.id"
				+"=(select id from SaaTask saaTask where taskCode=?))"
				+" and (userGrade.invalidDate>sysdate or userGrade.invalidDate is null) and userGrade.validStatus='1'",
//				+" and userGrade.InvalidDate>sysdate",
				taskCode);
		List primUserCode=new ArrayList<String>();
		List pUserCode=new ArrayList<String>();
		List riskCodeList=new ArrayList<String>();
		if (null!=riskCode&&!"".equals(riskCode)) {
			String[] riskCodesArray = riskCode.split(",");
			for (int ii = 0; ii < riskCodesArray.length; ii++) {
				riskCodeList.add(riskCodesArray[ii]);
			}
		}
		for (int i = 0; i < userGradeList.size(); i++) {
		if((saaPowerHelpService.removeDuplicateWithOrder((List)saaPowerHelpService.getPermitComWithOut(userGradeList.get(i), null))).indexOf(comCode)>-1&&
			(saaPowerHelpService.getExceCom(userGradeList.get(i), null)).indexOf(comCode)==-1){
			if (riskCodeList.size()>0) {
				if(((List)saaPowerHelpService.getRisksByProductCode(userGradeList.get(i))).containsAll(riskCodeList)){
					pUserCode=this.findByHql("select userGrade.userCode from SaaUserGrade userGrade where id=?", userGradeList.get(i));
					primUserCode.addAll(pUserCode);
					}
				}else{
					pUserCode=this.findByHql("select userGrade.userCode from SaaUserGrade userGrade where id=?", userGradeList.get(i));
					primUserCode.addAll(pUserCode);
				}
			}
		}
		
		  if(primUserCode.size()>0){
			  Set<String> set = new HashSet<String>();
			  List<String> newList = new ArrayList<String>();
			  for (Iterator<String> iter = primUserCode.iterator(); iter.hasNext();) {
			   String element = iter.next();
			   if (set.add(element))
			    newList.add(element);
			  }
			  primUserCode.clear();
			  primUserCode.addAll(newList);
			  
		  }				
		  else{
			  primUserCode=new ArrayList<String>(0);
		  }
		  return primUserCode;
	}
	/**
	 * 
	 * @param userCode
	 *            人员代码
	 * @param note
	 *            节点
	 * @return
	 * 		taskCodeList<String>
	 */
	public List<String> findUserTaskCodeByNote(String userCode, String note){
		// ??查员工代码???功能代码，权限值是否存在，如果不存在则抛出异常
		Assert.hasText(userCode, "userCode must have value.");
		Assert.hasText(note, "note must have value.");
		List<String> taskCodeList = new ArrayList<String>();
		if(userCode.equals("00000000")){
			taskCodeList.add(null);
			return taskCodeList;
		}
		List<String> gradeList = new ArrayList<String>();
			gradeList = this
					.findByHql(
							"select saaGrade.id from SaaUserGrade userGrade where userGrade.userCode=? and (userGrade.invalidDate>sysdate or userGrade.invalidDate is null)",
							userCode);
		StringBuilder builder = new StringBuilder();
		StringBuilder gradeIdHql = new StringBuilder();
		
		if (gradeList.size() > 0) {
			for (int j = 0; j < gradeList.size(); j++) {
				gradeIdHql.append(",'");
				gradeIdHql.append(String.valueOf(gradeList.get(j)));
				gradeIdHql.append("'");
			}
			builder.append("select distinct task.taskCode from");
			builder.append(" SaaGradeTask gradeTask,SaaTask task");
			builder.append(" where gradeTask.saaTask.id=task.id ");
			builder.append(" and gradeTask.saaGrade.id in (");
			builder.append(gradeIdHql.substring(1)).append(")");
			builder.append(" and task.taskCode like ?");
			
			taskCodeList = super.findByHql(builder.toString(), note);
			if(taskCodeList.size()>0){
				return taskCodeList;
			}else{
				taskCodeList.add(null);
			}
		}else{
			taskCodeList.add(null);
		}
		
		return taskCodeList;
	}
	/**
	 * 
	 * @param taskCode
	 *            功能代码
	 * @param comcode
	 *            机构代码
	 * @param taskType
	 *            任务类型 1表示查询核保??2表示查询核赔
	 * @return
	 */
	public SaaGrade getLowUndwrtGrade(String taskCode, String comCode,
			String taskType) {
		SaaGrade saaGrade = new SaaGrade();
		Assert.hasText(comCode, "comCode must have value.");
		Assert.hasText(taskType, "taskType must have value.");
		List<String> undwrtList = new ArrayList<String>();
		List<String> undwrtClaimList = new ArrayList<String>();
		//将功能代码封装到数据??
		undwrtList.add("undwrtOne");
		undwrtList.add("undwrtTwo");
		undwrtList.add("undwrtThree");
		undwrtClaimList.add("undwrtClaimOne");
		undwrtClaimList.add("undwrtClaimTwo");
		
		StringBuilder builder = new StringBuilder();
		StringBuilder temBuilder = new StringBuilder();
		StringBuilder builder1 = new StringBuilder();
		StringBuilder temBuilder1 = new StringBuilder();
		List<String> temGradeList = new ArrayList<String>();
		//根据??有的功能代码查找岗位
		builder
				.append("select gradeTask.saaGrade.id from SaaGradeTask gradeTask, SaaTask task where ");
		builder.append("gradeTask.saaTask.id =task.id and task.taskCode in (");
		if (taskType.equals("1")) {
			for (int i = 0; i < undwrtList.size(); i++) {
				temBuilder.append(",'");
				temBuilder.append(undwrtList.get(i));
				temBuilder.append("'");
			}
			builder.append(temBuilder.substring(1));
		} else if (taskType.equals("2")) {
			for (int i = 0; i < undwrtClaimList.size(); i++) {
				temBuilder.append(",'");
				temBuilder.append(undwrtClaimList.get(i));
				temBuilder.append("'");
			}
			builder.append(temBuilder.substring(1));
		}
		builder.append(")");
		List<String> gradeList = this.findByHql(builder.toString());
		//获取对应机构下存在的上述岗位
		while (true) {
			builder1
					.append("select userGrade.saaGrade.id from SaaUserGrade userGrade,SaaPermitCompany permitCom where and (userGrade.invalidDate>sysdate or userGrade.invalidDate is null)");
			builder1
					.append("permitCom.saaUserGrade.id =userGrade.id and userGrade.saaGrade.id in (");
			for (int i = 0; i < gradeList.size(); i++) {
				temBuilder1.append(",'");
				temBuilder1.append(String.valueOf(gradeList.get(i)));
				temBuilder1.append("'");
			}
			builder1.append(temBuilder1.substring(1));
			builder1.append(") and comCode = ?");
			temGradeList = this.findByHql(builder1.toString(), comCode);
			if (temGradeList.size() > 0) {
				break;
			} else {
				//如果当前机构没有获取上级机构代码
				QueryRule queryRule = QueryRule.getInstance();
				queryRule.addEqual("comCode", comCode);
				queryRule.addEqual("validStatus", "1");
				List<SaaCompany> comList = super.find(SaaCompany.class,
						queryRule);
				if (comList.size() > 0) {
					if (comCode.equals(comList.get(0).getUpperComCode())) {
						break;
					}
					comCode = comList.get(0).getUpperComCode();
					continue;
				} else {
					break;
				}
			}
		}
		// 通过员工岗位表中的岗位id查找对应的岗位对??
		List<SaaGrade> gradeIDList = new ArrayList<SaaGrade>(0);
		if (temGradeList.size() > 0) {
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addIn("id", temGradeList);
			queryRule.addEqual("validStatus", "1");
			gradeIDList = super.find(SaaGrade.class, queryRule);
		}
		// 获取??低岗位並返回岗位对象
		long mixId = 0;
		if (gradeIDList.size() > 0) {
			String gradeCName = "";
			Map gradeMap = saaPowerHelpService.builderGradeMap(taskCode,taskType);
			for (int i = 0; i < gradeIDList.size(); i++) {
				gradeCName = gradeIDList.get(i).getGradeCName();
				if (gradeMap.containsKey(gradeCName)) {
					long tempMixId = new Long((Integer)gradeMap.get(gradeCName));
					if (tempMixId > mixId) {
						mixId = tempMixId;
						saaGrade = gradeIDList.get(i);
					}
				}
			}
		}
		return saaGrade;
	}
	/**
	 * 清空??有服务器端缓??
	 */
	public void clearAllCacheManager(){
		saaPowerHelpService.clearAllServerCacheManager();
	}
	
	public boolean checkTask(String taskCode, String userCode){
		List gradeList = this.findByHql(
				"select userGrade.saaGrade.id from SaaUserGrade userGrade where userGrade.userCode=? and (userGrade.invalidDate>sysdate or userGrade.invalidDate is null) and " +
				"userGrade.saaGrade.id in(select gradeTask.saaGrade.id from SaaGradeTask gradeTask " +
				"where gradeTask.saaTask.id =(select saaTaskm.id from SaaTask saaTaskm where saaTaskm.taskCode=?))",
				userCode,taskCode);
		if(gradeList.size() > 0){
				return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 查询系统
	 * @return
	 */
	public List<SaaSystem> findSystem(){
		return super.findByHql("select saaSystem from SaaSystem saaSystem order by systemCode");
	}
	
	public SaaSystem findSystemBySystemCode(String code){
		return super.get(SaaSystem.class, code);
	}
	
	public Page findPowerUserList(String comCode,String fieldValue,String taskCode,int pageNo, int pageSize) {
//		String key = cacheManager.generateCacheKey("findPowerUserList", comCode,fieldValue,taskCode,pageNo,pageSize);
//		Object result = cacheManager.getCache(key);
//		if (result != null) {
//			return (Page) result;
//		}
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("validStatus", "1");
		queryRule.addSql("1=1 start with comCode= '" + comCode + "' connect by prior upperComCode = comCode");
		List<SaaCompany> saaCompanyList = super.find(SaaCompany.class, queryRule);
		String hql1 = "";
		for (SaaCompany itemCompany : saaCompanyList) {
			hql1 += ",'" + itemCompany.getComCode() + "'";
		}
		StringBuffer buffer = new StringBuffer(200);
		buffer.append("select distinct(saauser) from");
		buffer.append(" SaaUser saauser,SaaUserGrade userGrade");
		buffer.append(" where saauser.userCode=userGrade.userCode ");
		buffer.append(" and saauser.userCode like '"+fieldValue+"'");
		buffer.append(" and (userGrade.invalidDate>sysdate or userGrade.invalidDate is null) and userGrade.validStatus='1'");
		buffer.append(" and exists (select gradeTask from SaaGradeTask gradeTask where userGrade.saaGrade.id=gradeTask.saaGrade.id ");
		buffer.append("  and saaTask.id=(select id from SaaTask saaTask where taskCode='" + taskCode + "'))");
		buffer.append(" and exists (select permitCompany from SaaPermitCompany permitCompany where ");
		if(!"".equals(hql1)){
		  buffer.append("  permitCompany.comCode in (" + hql1.substring(1) + ") and");
		}
		buffer.append(" permitCompany.saaUserGrade.id=userGrade.id)");
		buffer.append(" and not exists (select exceptCompany from SaaExceptCompany exceptCompany where exceptCompany.comCode='" + comCode
				+ "' and exceptCompany.saaUserGrade.id=userGrade.id)");
//		Page page = super.findByHql(buffer.toString(), pageNo, pageSize);
//		cacheManager.putCache(key, page);
		return null;
	}
	
	@Override
	public Page findPowerUserUnderComCode(String comCode,String fieldValue,String taskCode,int pageNo, int pageSize) {
//		String key = cacheManager.generateCacheKey("findPowerUserUnderComCode", comCode,fieldValue,taskCode,pageNo,pageSize);
//		Object result = cacheManager.getCache(key);
//		if (result != null) {
//			return (Page) result;
//		}
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("validStatus", "1");
		String hql1 = "";
		if(!"9999999998".equals(comCode)){
			SaaCompany saaCompany = companyService.findPrpDcompanyByComCode(comCode);
			if("3".equals(saaCompany.getVirtualFlag())){
				queryRule.addSql("1=1 start with comCode= '" + saaCompany.getUpperComCode() + "' connect by prior comCode = upperComCode");
			}else{
				queryRule.addSql("1=1 start with comCode= '" + comCode + "' connect by prior comCode = upperComCode");
			}
			List<SaaCompany> saaCompanyList = super.find(SaaCompany.class, queryRule);
			for (SaaCompany itemCompany : saaCompanyList) {
				hql1 += ",'" + itemCompany.getComCode() + "'";
			}
		}
		StringBuffer buffer = new StringBuffer(200);
		if ("perf.plan.input".equals(taskCode)||"perf.mytask.plan.input".equals(taskCode)) {
			buffer.append("select distinct(saauser) from");
			buffer.append(" SaaUser saauser,SaaUserGrade userGrade");
			buffer.append(" where saauser.userCode=userGrade.userCode ");
			buffer.append(" and saauser.userCode like '"+fieldValue+"'");
			if(hql1!=""){
				buffer.append(" and saauser.comCode in (" + hql1.substring(1) + ")");
			}
			buffer.append(" and (userGrade.invalidDate>sysdate or userGrade.invalidDate is null) and userGrade.validStatus='1'");
			buffer.append(" and exists (select gradeTask from SaaGradeTask gradeTask where userGrade.saaGrade.id=gradeTask.saaGrade.id ");
			buffer.append("  and saaTask.id=(select id from SaaTask saaTask where taskCode='" + taskCode + "'))");
		}
//		Page page = super.findByHql(buffer.toString(), pageNo, pageSize);
//		cacheManager.putCache(key, page);
		return null;
	}

	public SaaCompanyService getSaaCompanyService() {
		return saaCompanyService;
	}

	public void setSaaCompanyService(SaaCompanyService saaCompanyService) {
		this.saaCompanyService = saaCompanyService;
	}

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

}
