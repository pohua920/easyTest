package com.sinosoft.sys.platform.power.service.spring;

import ins.framework.cache.CacheManager;
import ins.framework.cache.CacheService;
import ins.framework.dao.GenericDaoHibernate;
import ins.framework.utils.DataUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;

import com.sinosoft.sys.platform.common.TreeNode;
import com.sinosoft.sys.platform.power.model.SaaAuthTask;
import com.sinosoft.sys.platform.power.model.SaaGrade;
import com.sinosoft.sys.platform.power.model.SaaGradeTask;
import com.sinosoft.sys.platform.power.model.SaaTask;
import com.sinosoft.sys.platform.power.model.SaaUser;
import com.sinosoft.sys.platform.power.service.facade.SaaGradeService;
import com.sinosoft.sys.platform.power.service.facade.SaaPowerHelpService;
import com.sinosoft.sys.platform.power.service.facade.SaaPowerService;
import com.sinosoft.sys.platform.power.service.facade.SaaTaskService;
import com.sinosoft.sys.platform.power.service.facade.SaaUserService;
import com.sinosoft.sys.platform.power.vo.SaaGradeTaskVO;


public class SaaGradeServiceSpringImpl extends
		GenericDaoHibernate<SaaGrade, Long> implements SaaGradeService {
	private SaaTaskService saaTaskService;
	private SaaUserService saaUserService;
	private SaaPowerHelpService saaPowerHelpService;
	private SaaPowerService saaPowerService;
 
	private static CacheService cacheManager = CacheManager.getInstance("SaaGradeServiceSpringImpl");
	@SuppressWarnings("unchecked")
	public List<SaaGrade> initSaaGradeList(String userCode) {
		List<SaaGrade> saaGradeList = new ArrayList<SaaGrade>(0);
		if(userCode.equals("00000000")){
			String hql="select grade from SaaGrade grade";
			saaGradeList=super.findByHql(hql);
		}else{
			SaaUser saaUser = findSaaUserByUserCode(userCode);
			List<SaaGrade> saaGradeAllList = new ArrayList<SaaGrade>(0);
			List<SaaTask> saaAuthTaskList = new ArrayList<SaaTask>(0);
			List<SaaTask> saaGradeTaskList = new ArrayList<SaaTask>(0);
			String keysaaAuthTaskList = cacheManager.generateCacheKey("authTask",
					userCode);
			Object saaAuthTaskListresult = cacheManager.getCache(keysaaAuthTaskList);
			if (saaAuthTaskListresult != null) {
				saaAuthTaskList = (List<SaaTask>)saaAuthTaskListresult;
			}else{
				String authTaskHql = "select authTask.saaTask from SaaAuthTask authTask where authTask.userCode=?";
				saaAuthTaskList= super.findByHql(authTaskHql,userCode);
				cacheManager.putCache(keysaaAuthTaskList, saaAuthTaskList);
				
			}
			String gradeTaskHql = "select gradeTask.saaTask from SaaGradeTask gradeTask where gradeTask.saaGrade=?";
			String uppsercomcode = saaPowerHelpService.getUpperComcode(saaUser.getComCode());
			String gradeHql = "select grade from SaaGrade grade where (grade.comCode = ?  or (grade.comCode"
					+ this.getParAllCompanyCodeSQL(saaUser.getComCode())
					+ " and grade.commonGrade='1') or (grade.comCode = ? and grade.commonGrade='0')) and grade.validStatus='1' order by grade.id";
			saaGradeAllList = super.findByHql(gradeHql, saaUser.getComCode(),uppsercomcode);
			if(saaGradeAllList.size()>0){
				for(SaaGrade grade:saaGradeAllList){
					saaGradeTaskList=super.findByHql(gradeTaskHql, grade);				
					if(saaAuthTaskList.containsAll(saaGradeTaskList)){
						saaGradeList.add(grade);
					}
				}
			}
		}
		return saaGradeList;
	}
	
	public List<SaaGrade> initSaaGradeListSysCode(String userCode, String systemTypename) {
		List<SaaGrade> saaGradeList = new ArrayList<SaaGrade>(0);
		if(userCode.equals("00000000")){
			String hql="select grade from SaaGrade grade where grade.validStatus = '1' and grade.systemTypeName='"+systemTypename+"'";
			saaGradeList=super.findByHql(hql);
		}else{
			SaaUser saaUser = findSaaUserByUserCode(userCode);
			List<SaaGrade> saaGradeAllList = new ArrayList<SaaGrade>(0);
			List<SaaTask> saaAuthTaskList = new ArrayList<SaaTask>(0);
			List<SaaTask> saaGradeTaskList = new ArrayList<SaaTask>(0);
			String keysaaAuthTaskList = cacheManager.generateCacheKey("authTask",userCode);
			Object saaAuthTaskListresult = cacheManager.getCache(keysaaAuthTaskList);
			if (saaAuthTaskListresult != null) {
				saaAuthTaskList = (List<SaaTask>)saaAuthTaskListresult;
			}else{
				String authTaskHql = "select authTask.saaTask from SaaAuthTask authTask where authTask.userCode=?";
				saaAuthTaskList= super.findByHql(authTaskHql,userCode);
				cacheManager.putCache(keysaaAuthTaskList, saaAuthTaskList);
			}
			String gradeTaskHql = "select gradeTask.saaTask from SaaGradeTask gradeTask where gradeTask.saaGrade=?";
			String uppsercomcode = saaPowerHelpService.getUpperComcode(saaUser.getComCode());
			String gradeHql = "select grade from SaaGrade grade where grade.validStatus = '1' and grade.systemTypeName= ? and (grade.comCode = ?  or (grade.comCode"
					+ this.getParAllCompanyCodeSQL(saaUser.getComCode())
					+ " and grade.commonGrade='1') or (grade.comCode = ? and grade.commonGrade='0')) and grade.validStatus='1' order by grade.id";
			
			saaGradeAllList = super.findByHql(gradeHql,systemTypename, saaUser.getComCode(),uppsercomcode);
			if(saaGradeAllList.size()>0){
				List<String> perAuthCom = (List<String>) saaPowerHelpService.getAuthPermitCom(userCode, null);
				List<String> excAuthCom = (List<String>) saaPowerHelpService.getAuthExceCom(userCode, null);
				if (excAuthCom.size()>0) {
					perAuthCom.removeAll(excAuthCom);
				}
				for(SaaGrade grade:saaGradeAllList){
					saaGradeTaskList=super.findByHql(gradeTaskHql, grade);		
					boolean gradeFlag = false;
					if(saaAuthTaskList.containsAll(saaGradeTaskList)){
						gradeFlag = true;
					}
					if (gradeFlag && perAuthCom.contains(grade.getComCode())) {
						saaGradeList.add(grade);
					}
				}
			}
		}
		return saaGradeList;
	}
	
	public List<SaaGrade> initSaaGradeListForGrade(String userCode,String systemTypename) {
		List<SaaGrade> saaGradeList = new ArrayList<SaaGrade>(0);
		if(userCode.equals("00000000")){
			String hql="select grade from SaaGrade grade where grade.systemTypeName = ?";
			saaGradeList=super.findByHql(hql,systemTypename);
		}else{
			List<String> perAuthCom = new ArrayList<String>(0);
			List<String> excAuthCom = new ArrayList<String>(0);
			List<SaaTask> saaAuthTaskList = new ArrayList<SaaTask>(0);
			String keyperAuthCom = cacheManager.generateCacheKey("authCom",
					userCode);
			String keyexcAuthCom = cacheManager.generateCacheKey("excAuthCom",
					userCode);
			String keysaaAuthTaskList = cacheManager.generateCacheKey("authTask",
					userCode);
			Object perAuthComresult = cacheManager.getCache(keyperAuthCom);
			Object excAuthComresult = cacheManager.getCache(keyexcAuthCom);
			Object saaAuthTaskListresult = cacheManager.getCache(keysaaAuthTaskList);
			if (perAuthComresult != null) {
				perAuthCom = (List<String>)perAuthComresult;
			}else{
				perAuthCom = (List<String>) saaPowerHelpService.getAuthPermitCom(userCode, null);
				cacheManager.putCache(keyperAuthCom, perAuthCom);
			}
			if (excAuthComresult != null) {
				excAuthCom = (List<String>)excAuthComresult;
			}else{
				excAuthCom = (List<String>) saaPowerHelpService.getAuthExceCom(userCode, null);
				cacheManager.putCache(keyexcAuthCom, excAuthCom);
			}
			if (saaAuthTaskListresult != null) {
				saaAuthTaskList = (List<SaaTask>)saaAuthTaskListresult;
			}else{
				String authTaskHql = "select authTask.saaTask from SaaAuthTask authTask where authTask.userCode=?";
				saaAuthTaskList= super.findByHql(authTaskHql,userCode);
				cacheManager.putCache(keysaaAuthTaskList, saaAuthTaskList);
			}
			if (excAuthCom.size()>0) {
				perAuthCom.removeAll(excAuthCom);
			}
			SaaUser saaUser = findSaaUserByUserCode(userCode);
			List<SaaGrade> saaGradeAllList = new ArrayList<SaaGrade>(0);
			
			List<SaaTask> saaGradeTaskList = new ArrayList<SaaTask>(0);
			
			String gradeTaskHql = "select gradeTask.saaTask from SaaGradeTask gradeTask where gradeTask.saaGrade=?";
			String uppsercomcode = saaPowerHelpService.getUpperComcode(saaUser.getComCode());
			String gradeHql = "select grade from SaaGrade grade where grade.validStatus = '1' and (grade.comCode = ? and grade.validStatus='1' and grade.systemTypeName = '" + systemTypename +
					"') or (grade.comCode"
					+ this.getParAllCompanyCodeSQL(saaUser.getComCode())
					+ " and grade.commonGrade='1' and grade.systemTypeName = '" + systemTypename +
							"') or (grade.comCode = ? and grade.commonGrade='0' and grade.systemTypeName = '" + systemTypename +
							"') order by grade.id";
//			Map perAuthComMap = new 
			saaGradeAllList = super.findByHql(gradeHql, saaUser.getComCode(),uppsercomcode);
			if(saaGradeAllList.size()>0){
				for(SaaGrade grade:saaGradeAllList){
					saaGradeTaskList=(List<SaaTask>)super.findByHql(gradeTaskHql, grade);
					boolean gradeFlag = true;
					for (int i = 0; i < saaGradeTaskList.size(); i++) {
						if(!(saaAuthTaskList.contains(saaGradeTaskList.get(i)))){//
							gradeFlag = false;
							break;
						}
					}
					if (gradeFlag && perAuthCom.contains(grade.getComCode())) {
						saaGradeList.add(grade);
					}
				}
			}
		}
		return saaGradeList;
	}
	
	private SaaUser findSaaUserByUserCode(String userCode){
		List<SaaUser> saaUserList = new ArrayList<SaaUser>(0);
		if(userCode!=null){
			String hql = "select saaUser from SaaUser saaUser where saaUser.userCode=?";
			saaUserList = super.findByHql(hql, userCode);
		}
		if(saaUserList.size()!=0){
			return saaUserList.get(0);
		}else{
			return null;
		}
		
	}

	@SuppressWarnings("unchecked")
	public List<SaaGradeTask> findSaaGradeTaskList(String saaGradeID) {
		List<SaaGradeTask> saaGradeTaskListTemp;
		if ("".equals(saaGradeID) || saaGradeID == null) {
			saaGradeTaskListTemp = new ArrayList<SaaGradeTask>(0);
		} else {
			String hql = "select gradeTask from SaaGradeTask gradeTask where gradeTask.saaGrade.id=?";
			saaGradeTaskListTemp = super.findByHql(hql, new Long(saaGradeID));
		}

		return saaGradeTaskListTemp;

	}

	public List<SaaGradeTaskVO> findSaaGradeTaskVOList(String saaGradeID,String userCode) {
		List<SaaGradeTaskVO> saaGradeTaskVOList = new ArrayList<SaaGradeTaskVO>(
				0);
		List<SaaGradeTask> saaGradeTaskList;
		List<SaaAuthTask> saaAuthTaskList;
		if ("".equals(saaGradeID) || saaGradeID == null) {
			saaGradeTaskList = new ArrayList<SaaGradeTask>(0);
		} else {
			saaGradeTaskList = this.findSaaGradeTasks(saaGradeID);
		}
		if ("".equals(userCode) || userCode == null) {
			saaAuthTaskList = new ArrayList<SaaAuthTask>(0);
		} else {
			saaAuthTaskList = this.findSaaAuthTasks(userCode);
		}
		Map<Long, SaaGradeTask> saaGradeTaskMap = new HashMap<Long, SaaGradeTask>();
		for (SaaGradeTask gradeTask : saaGradeTaskList) {
			saaGradeTaskMap.put(gradeTask.getSaaTask().getId(), gradeTask);
		}
		Map<Long,SaaAuthTask> saaAuthTaskMap = new HashMap<Long,SaaAuthTask>();		
		for(SaaAuthTask task:saaAuthTaskList){
			saaAuthTaskMap.put(task.getSaaTask().getId(), task);
		}
		List<SaaTask> saaTaskList = this.findSaaTaskList();
		for (SaaTask task : saaTaskList) {
			SaaGradeTaskVO saaGradeTaskVO = new SaaGradeTaskVO();
			SaaAuthTask authTask = saaAuthTaskMap.get(task.getId());
			if(userCode.equals("00000000")){
				saaGradeTaskVO.setHasPower("0");
			}else{
				if(authTask==null){
					saaGradeTaskVO.setHasPower("1");
				}else{
					saaGradeTaskVO.setHasPower("0");
				}
			}
			
			SaaGradeTask gradeTask = saaGradeTaskMap.get(task.getId());
			if (gradeTask==null) {
				saaGradeTaskVO.setValue("0");
				saaGradeTaskVO.setInternetValue("0");
				saaGradeTaskVO.setIntranetValue("0");
			} else {
				saaGradeTaskVO.setValue("1");
				saaGradeTaskVO.setInternetValue(gradeTask.getInternetValue());
				saaGradeTaskVO.setIntranetValue(gradeTask.getIntranetValue());
			}
			saaGradeTaskVO.setTaskCode(task.getTaskCode());
			saaGradeTaskVO.setTaskCName(task.getTaskCName());
			saaGradeTaskVO.setTaskParentCode(task.getParentCode());
			if (saaGradeTaskVO.getTaskParentCode().equals(saaGradeTaskVO.getTaskCode())) {
				saaGradeTaskVO.setTaskParentCode("0");
			}
			saaGradeTaskVOList.add(saaGradeTaskVO);
		}
		return saaGradeTaskVOList;

	}
	
	public List<SaaGradeTaskVO> findSaaGradeTaskVOListByRootCode(String saaGradeID,String userCode, String rootTaskCode) {
		List<SaaGradeTaskVO> saaGradeTaskVOList = new ArrayList<SaaGradeTaskVO>(
				0);
		List<SaaGradeTask> saaGradeTaskList;
		List<SaaAuthTask> saaAuthTaskList;
		if ("".equals(saaGradeID) || saaGradeID == null) {
			saaGradeTaskList = new ArrayList<SaaGradeTask>(0);
		} else {
			saaGradeTaskList = this.findSaaGradeTasks(saaGradeID);
		}
		if ("".equals(userCode) || userCode == null) {
			saaAuthTaskList = new ArrayList<SaaAuthTask>(0);
		} else {
			saaAuthTaskList = this.findSaaAuthTasks(userCode);
		}
		Map<Long, SaaGradeTask> saaGradeTaskMap = new HashMap<Long, SaaGradeTask>();
		for (SaaGradeTask gradeTask : saaGradeTaskList) {
			saaGradeTaskMap.put(gradeTask.getSaaTask().getId(), gradeTask);
		}
		Map<Long,SaaAuthTask> saaAuthTaskMap = new HashMap<Long,SaaAuthTask>();		
		for(SaaAuthTask task:saaAuthTaskList){
			saaAuthTaskMap.put(task.getSaaTask().getId(), task);
		}
		List<SaaTask> saaTaskList = this.findSaaTaskListByRootTask(rootTaskCode);
		for (SaaTask task : saaTaskList) {
			SaaGradeTaskVO saaGradeTaskVO = new SaaGradeTaskVO();
			SaaAuthTask authTask = saaAuthTaskMap.get(task.getId());
			if(userCode.equals("00000000")){
				saaGradeTaskVO.setHasPower("0");
			}else{
				if(authTask==null){
					saaGradeTaskVO.setHasPower("1");
				}else{
					saaGradeTaskVO.setHasPower("0");
				}
			}
			
			SaaGradeTask gradeTask = saaGradeTaskMap.get(task.getId());
			if (gradeTask==null) {
				saaGradeTaskVO.setValue("0");
				saaGradeTaskVO.setInternetValue("0");
				saaGradeTaskVO.setIntranetValue("0");
			} else {
				saaGradeTaskVO.setValue("1");
				saaGradeTaskVO.setInternetValue(gradeTask.getInternetValue());
				saaGradeTaskVO.setIntranetValue(gradeTask.getIntranetValue());
			}
			saaGradeTaskVO.setTaskCode(task.getTaskCode());
			saaGradeTaskVO.setTaskCName(task.getTaskCName());
			saaGradeTaskVO.setTaskParentCode(task.getParentCode());
			if (saaGradeTaskVO.getTaskParentCode().equals(saaGradeTaskVO.getTaskCode())) {
				saaGradeTaskVO.setTaskParentCode("0");
			}
			saaGradeTaskVOList.add(saaGradeTaskVO);
		}
		return saaGradeTaskVOList;

	}
	
	public List<SaaGradeTaskVO> findRootSaaGradeTaskVO(String userCode) {
		List<SaaGradeTaskVO> saaGradeTaskVOList = new ArrayList<SaaGradeTaskVO>(0);
		List<SaaAuthTask> saaAuthTaskList;
		if ("".equals(userCode) || userCode == null) {
			saaAuthTaskList = new ArrayList<SaaAuthTask>(0);
		} else {
			saaAuthTaskList = this.findSaaAuthTasks(userCode);
		}
		Map<Long,SaaAuthTask> saaAuthTaskMap = new HashMap<Long,SaaAuthTask>();		
		for(SaaAuthTask task:saaAuthTaskList){
			saaAuthTaskMap.put(task.getSaaTask().getId(), task);
		}
		List<SaaTask> saaTaskList = this.findSaaRootTasks();
		for (SaaTask task : saaTaskList) {
			SaaGradeTaskVO saaGradeTaskVO = new SaaGradeTaskVO();
			SaaAuthTask authTask = saaAuthTaskMap.get(task.getId());
			if(userCode.equals("00000000")){
				saaGradeTaskVO.setTaskCode(task.getTaskCode());
				saaGradeTaskVO.setTaskCName(task.getTaskCName());
				saaGradeTaskVO.setTaskParentCode(task.getParentCode());
				saaGradeTaskVOList.add(saaGradeTaskVO);
				continue;
			}else{
				if(authTask==null){
					continue;
				}else{
					saaGradeTaskVO.setTaskCode(task.getTaskCode());
					saaGradeTaskVO.setTaskCName(task.getTaskCName());
					saaGradeTaskVO.setTaskParentCode(task.getParentCode());
					saaGradeTaskVOList.add(saaGradeTaskVO);
				}
			}
		}
		return saaGradeTaskVOList;

	}

	public List<SaaGradeTask> findSaaGradeTasks(String gradeID) {
		String hql = "select gradeTask from SaaGradeTask gradeTask where gradeTask.saaGrade.id=?";
		return super.findByHql(hql, new Long(gradeID));
	}
	public void deleteSaaGradeByGradeID(String saaGradeID){
		SaaGrade grade = this.findSaaGradeByGradeID(saaGradeID);
		super.delete(grade);
	}
	public List<SaaTask> findSaaTaskList() {
		String hql = "select task from SaaTask task where task.validStatus='1' order by task.parentCode asc";
		return super.findByHql(hql, null);
	}
	private List<SaaTask> findSaaRootTasks(){
		String hql = "select task from SaaTask task where task.parentCode='0' and task.validStatus='1' order by task.parentCode asc";
		return super.findByHql(hql, null);
	}
	public List<SaaTask> findSaaTaskListByRootTask(String rootTaskCode){
		String hql = "Select * from Saa_Task task start with task.taskCode='"+rootTaskCode+"' connect by prior task.taskCode = task.parentCode and task.validStatus='1'";
		SQLQuery query=getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(hql);
		List list=query.addEntity(SaaTask.class).list();
		return list;
	}
	public List<SaaAuthTask> findSaaAuthTasks(String userCode){
		String hql = "select task from SaaAuthTask task where task.userCode=?";
		return super.findByHql(hql,userCode);
	}
	public SaaGrade findSaaGradeByGradeID(String saaGradeID) {
		String hql = "select grade from SaaGrade grade where grade.id=?";
		return (SaaGrade) super.findByHql(hql, new Long(saaGradeID)).get(0);
	}

	public List<SaaGradeTask> convertVoToDto(String[] taskCodes,
			String[] intranetCheckBox, String[] internetCheckBox, Long gradeID) {
		List<SaaGradeTask> gradeTaskList = new ArrayList<SaaGradeTask>(0);
		SaaGrade grade = this.get(gradeID);
		List<String> intranetList = new ArrayList<String>(0);
		List<String> internetList = new ArrayList<String>(0);
		if (intranetCheckBox != null) {
			for (String str : intranetCheckBox) {
				intranetList.add(str);
			}
		}
		if (internetCheckBox != null) {
			for (String str : internetCheckBox) {
				internetList.add(str);
			}
		}
		if (taskCodes != null) {
			for (String str : taskCodes) {
				SaaGradeTask gradeTaskNew = new SaaGradeTask();
				if (intranetList.contains(str)) {
					gradeTaskNew.setIntranetValue("1");
				} else {
					gradeTaskNew.setIntranetValue("0");
				}
				if (internetList.contains(str)) {
					gradeTaskNew.setInternetValue("1");
				} else {
					gradeTaskNew.setInternetValue("0");
				}
				Map<String, Object> taskCodeMap = new HashMap<String, Object>();
				taskCodeMap.put("taskCode", str);
				gradeTaskNew.setSaaGrade(grade);
				gradeTaskNew.setSaaTask(saaTaskService.getTask(taskCodeMap));
				if (gradeTaskNew.getSaaTask() != null)
					gradeTaskList.add(gradeTaskNew);
			}
		}
		return gradeTaskList;
	}

	public void addSaaGrade(String[] taskCodes, String[] intranetCheckBox,
			String[] internetCheckBox, SaaGrade saaGrade,String userCodeOper) {
		Date date= new Date();
		saaGrade.setCreateTime(date);
		saaGrade.setCreatorCode(userCodeOper);
		String comCode = saaGrade.getComCode();
		saaGrade.setComCode(comCode.split(",")[0]);
		super.save(saaGrade);
		List<SaaGradeTask> gradeTask = new ArrayList<SaaGradeTask>(0);
		gradeTask = this.convertVoToDto(taskCodes, intranetCheckBox,
				internetCheckBox, saaGrade.getId());

		saaGrade.setSaaGradeTasks(gradeTask);

	}

	public String getSaaGradeTaskId(Long saaGradeId) {
		String taskCode = "";
		List<Long> taskCodes = this
				.findByHql(
						"select gradeTask.saaTask.id from SaaGradeTask gradeTask where gradeTask.saaGrade.id=? ",
						saaGradeId);
		if (taskCodes.size() > 0) {
			for (Long str : taskCodes) {
				taskCode = taskCode + "," + str.toString();
			}
		}
		if (taskCode.startsWith(",")) {
			taskCode = taskCode.substring(0, taskCode.length());
		}
		return taskCode;
	}

	public List<Long> getSaaGradeTaskIdList(Long saaGradeId) {
		return this
				.findByHql(
						"select distinct gradeTask.saaTask.id from SaaGradeTask gradeTask where gradeTask.saaGrade.id=?",
						saaGradeId);
	}

	public void updateSaaGrade(String[] taskCodes, String[] intranetCheckBox,
			String[] internetCheckBox, SaaGrade saaGrade,String userCodeOper) {
		Date date =new Date();
		saaGrade.setUpdaterCode(userCodeOper);
		saaGrade.setUpdateTime(date);
		SaaGrade gradeOld = this.get(saaGrade.getId());
		DataUtils.copySimpleObject(saaGrade, gradeOld, false);
		List<SaaGradeTask> gradeTaskNew = new ArrayList<SaaGradeTask>(0);
		List<SaaGradeTask> gradeTaskOld = new ArrayList<SaaGradeTask>(0);
		gradeTaskNew = this.convertVoToDto(taskCodes, intranetCheckBox,
				internetCheckBox, saaGrade.getId());
		gradeTaskOld = gradeOld.getSaaGradeTasks();
		mergeList(gradeTaskNew, gradeTaskOld, "id");

	}

	private String getParAllCompanyCodeSQL(String comCode) {
		List<String> parAllCompanyCodeList = new ArrayList<String>(0);
		parAllCompanyCodeList = this.getParAllCompanyCode(comCode);
		parAllCompanyCodeList.remove(comCode);
		StringBuilder hqlBuilder = new StringBuilder();
		StringBuilder hql = new StringBuilder();
		hql.append(" in (");
		if (parAllCompanyCodeList.size() > 0) {
			for (String code : parAllCompanyCodeList) {
				hqlBuilder.append(",'" + code + "'");
			}
		} else {
			hqlBuilder.append(" '')");
			hqlBuilder.append(" and 1=2");
		}
		hql.append(hqlBuilder.substring(1));
		hql.append(")");
		return hql.toString();
	}

	private List<String> getParAllCompanyCode(String comCode) {
		String key = cacheManager
				.generateCacheKey("parAllCompanyCode", comCode);
		Object result = cacheManager.getCache(key);
		if (result != null) {
			return (List<String>) result;
		}
		List<String> comCodeList = new ArrayList<String>(0);
		TreeNode<String> node = this.getCompanyTree().get(comCode);
		if (node != null) {
			List<TreeNode<String>> parents = node.getAllParent();
			if (parents != null && parents.size() != 0) {
				for (TreeNode<String> parent : parents) {
					comCodeList.add(parent.getValue());
				}
			}
			comCodeList.add(node.getValue());
		}
		cacheManager.putCache(key, comCodeList);
		return comCodeList;
	}

	private Map<String, TreeNode<String>> getCompanyTree() {
		Map<String, TreeNode<String>> treeNodeMap = (Map<String, TreeNode<String>>) cacheManager
				.getCache("CompanyTree");
		if (treeNodeMap == null) {
			treeNodeMap = initCompanyTreeCache();
		}
		return treeNodeMap;
	}

	private Map<String, TreeNode<String>> initCompanyTreeCache() {
		Map<String, TreeNode<String>> companyTree = new HashMap<String, TreeNode<String>>();
		List<Object[]> list = super
				.findByHql("select com.comCode, com.upperComCode from SaaCompany com where com.validStatus='1'");
		for (Object[] str : list) {
			TreeNode<String> node = new TreeNode<String>((String) str[0]);
			companyTree.put(node.getValue(), node);
		}
		for (Object[] str : list) {
			TreeNode<String> node = companyTree.get((String) str[0]);
			TreeNode<String> parent = companyTree.get((String) str[1]);
			if (parent != null && parent != node) {
				node.setParent(parent);
				parent.addChild(node);
			}
		}
		cacheManager.putCache("CompanyTree", companyTree);
		return companyTree;
	}

	public SaaTaskService getSaaTaskService() {
		return saaTaskService;
	}

	public void setSaaTaskService(SaaTaskService saaTaskService) {
		this.saaTaskService = saaTaskService;
	}

	public SaaUserService getSaaUserService() {
		return saaUserService;
	}

	public void setSaaUserService(SaaUserService saaUserService) {
		this.saaUserService = saaUserService;
	}

	public SaaPowerHelpService getSaaPowerHelpService() {
		return saaPowerHelpService;
	}

	public void setSaaPowerHelpService(SaaPowerHelpService saaPowerHelpService) {
		this.saaPowerHelpService = saaPowerHelpService;
	}

	public SaaPowerService getSaaPowerService() {
		return saaPowerService;
	}

	public void setSaaPowerService(SaaPowerService saaPowerService) {
		this.saaPowerService = saaPowerService;
	}



	

}
