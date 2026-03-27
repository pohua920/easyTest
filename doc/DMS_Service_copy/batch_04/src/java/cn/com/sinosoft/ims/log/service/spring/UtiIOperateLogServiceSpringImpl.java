package cn.com.sinosoft.ims.log.service.spring;

import ins.framework.common.Page;
import ins.framework.dao.GenericDaoHibernate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.com.sinosoft.ims.log.model.UtiIExceptionLog;
import cn.com.sinosoft.ims.log.model.UtiIOperateLog;
import cn.com.sinosoft.ims.log.service.facade.UtiIOperateLogService;
import cn.com.sinosoft.ims.util.IConstants;
import cn.com.sinosoft.ims.util.ReadProperties;
import cn.com.sinosoft.ims.util.UtiITaskActionConstant;
import cn.com.sinosoft.ims.util.UtiITaskActionConstantId;
import cn.com.sinosoft.saa.util.HqlRulesUtil;

public class UtiIOperateLogServiceSpringImpl extends
		GenericDaoHibernate<UtiITaskActionConstant, UtiITaskActionConstantId> implements UtiIOperateLogService {


//	public Page getLogList(UtiIOperateLog log,String userName, int pageNo, int pageSize) {
	public Page getLogList(UtiIOperateLog log,int pageNo,int pageSize){
		StringBuffer hql = new StringBuffer("from UtiIOperateLog log where 1=1");
		if (log.getUserCode() == null || "".equals(log.getUserCode())) {
		}else{			
			hql.append("and log.userCode = '" + log.getUserCode()+ "'");
		}
		if (log.getLoginTime() != null) {
			String database = ReadProperties.getString("database");
			if (IConstants.DB_ORACLE.equals(database)) {
				String str = new SimpleDateFormat("yyyy-MM-dd").format(log
						.getLoginTime());
				hql.append(" and to_char(log.loginTime,\'yyyy-MM-dd\') = '"
						+ str + "'" );
			} else {
				SimpleDateFormat f2=null;
				Date date = log.getLoginTime();
				Calendar nextDay = Calendar.getInstance();
				nextDay.setTime(date);
				nextDay.add(Calendar.DAY_OF_YEAR,1);
				date=nextDay.getTime();
				f2 = new SimpleDateFormat("yyyy-MM-dd");
				String login = f2.format(date);
				String str = new SimpleDateFormat("yyyy-MM-dd").format(log
						.getLoginTime());
				hql.append(" and log.loginTime between date('"+ str +"') and date('"+login+"')order by serialNo desc");
			}
		}
		Page page = super.findByHql(hql.toString(), pageNo, pageSize);
		return page;
		
		
		
		
		
		
		
		
		
//		StringBuffer hql = new StringBuffer();
//		Page page = new Page();
//		List<UtiIOperateLog> logs = new ArrayList<UtiIOperateLog>();
//		hql.append("from UtiIOperateLog utiIOperateLog where 1=1");
//		List<String> userCodes = getUserCodeByName(userName);
//		HqlRulesUtil hqlRulesUtil = new HqlRulesUtil();
//		if (userCodes==null) {
//			hql.append(" and where 1=2 ");
//		} else {
//			for(int i=0;i<userCodes.size();i++){
//				String userCode = userCodes.get(i);
//				if(userCode.equals("")){
//				}else{
//					hql.append(" and utiIOperateLog.userCode = '"+userCode+"'");
//				}
//				if (log.getLoginTime() != null) {
//					String database = ReadProperties.getString("database");
//					if (IConstants.DB_ORACLE.equals(database)) {
//						String str = new SimpleDateFormat("yyyy-MM-dd").format(log
//								.getLoginTime());
//						hql.append(" and to_char(utiIOperateLog.loginTime,\'yyyy-MM-dd\') = '"
//								+ str + "'" + "order by serialNo desc");
//					} else {
//						String str = new SimpleDateFormat("yyyy-MM-dd").format(log
//								.getLoginTime());
//						hql.append(" and utiIOperateLog.loginTime > date('"+ str +"')order by serialNo desc");
//					}
//				} else {}
//				System.out.println("hql:"+hql.toString());
//				
//				page = super.findByHql(hql.toString(),pageNo,pageSize);
//			}
//		}
//		//Page page = findByHql(hql.toString(), pageNo, pageSize);
//		return page;
//		StringBuffer hql = new StringBuffer();
//		hql.append("from UtiIOperateLog utiIOperateLog where 1=1");
//		String userCode = getUserCodeByName(userName);
//		HqlRulesUtil hqlRulesUtil = new HqlRulesUtil();
//		if (userCode.equals("")) {
//			hqlRulesUtil.addLike("utiIOperateLog.userCode", "");
//		} else {
//			hqlRulesUtil.addLike("utiIOperateLog.userCode", userCode);
//		}
//		if (log.getLoginTime() != null) {
//			String str = new SimpleDateFormat("yyyy-MM-dd").format(log
//					.getLoginTime());
//			hql.append("and to_char(utiIOperateLog.loginTime,\'yyyy-MM-dd\') = '"
//					+ str + "')");
//		} else {
//		}
//		if (hqlRulesUtil.getHql().trim().length() != 0) {
//			hql.append(" and ").append(hqlRulesUtil.getHql());
//		}
//		Page page = findByHql(hql.toString(), pageNo, pageSize);
//		return page;
	}
	public void deleteMethod(UtiIExceptionLog utiIExceptionLog) {
		// TODO Auto-generated method stub

	}


	public void insertMethod(UtiIOperateLog utiIOperateLog) {
		// TODO Auto-generated method stub
		super.save(utiIOperateLog);
	}

//	public String getUserCodeByName(String userName){
//		QueryRule queryRule = QueryRule.getInstance();
//		queryRule.addEqual("userName", userName);
//		UtiIUser utiIUser = super.findUnique(UtiIUser.class,queryRule);
//		if(utiIUser!=null){
//			return utiIUser.getUserCode();
//		}else{
//			return "";
//		}
//	}
	public List<String> getUserCodeByName(String userName) {
		HqlRulesUtil hqlRulesUtil = new HqlRulesUtil();
		StringBuffer hql = new StringBuffer();
		hql.append("select utiIUser.userCode from UtiIUser utiIUser where utiIUser.userName = ?");
		List<String> list = new ArrayList<String>();
		list = super.findByHql(hql.toString(), userName);
		if(list.size()==0){
			if(userName.equals("")){
				list.add("");
				return list;
			}else{
				return null;
			}
		}else{
			return list;
		}
	}
	public String getCName(String actionType,String taskCode){
		UtiITaskActionConstantId utiITaskActionConstantId = new UtiITaskActionConstantId();
		utiITaskActionConstantId.setActionType(actionType);
		utiITaskActionConstantId.setTaskCode(taskCode);
		UtiITaskActionConstant utiITaskActionConstant = super.get(utiITaskActionConstantId);
		if(utiITaskActionConstant==null){
			return "isLogin";
		}else{
			return utiITaskActionConstant.getTaskName();
		}
	}
}
