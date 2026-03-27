package cn.com.sinosoft.ims.log.service.spring;

import ins.framework.common.Page;
import ins.framework.dao.GenericDaoHibernate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.com.sinosoft.ims.log.model.UtiIExceptionLog;
import cn.com.sinosoft.ims.log.service.facade.UtiIExceptionLogService;
import cn.com.sinosoft.ims.util.IConstants;
import cn.com.sinosoft.ims.util.ReadProperties;
import cn.com.sinosoft.saa.util.HqlRulesUtil;

public class UtiIExceptionLogServiceSpringImpl extends
		GenericDaoHibernate<UtiIExceptionLog, String> implements UtiIExceptionLogService {
	

//	public Page getLogList(UtiIExceptionLog log,String userName, int pageNo, int pageSize) {
	public Page getLogList(UtiIExceptionLog log,int pageNo,int pageSize){
		
		StringBuffer hql = new StringBuffer("from UtiIExceptionLog log where 1=1");
		if (log.getUserCode() == null || "".equals(log.getUserCode())) {
		}else{			
			hql.append("and log.userCode = '" + log.getUserCode()+ "'");
		}
		if (log.getOccurTime() != null) {
			String database = ReadProperties.getString("database");
			if (IConstants.DB_ORACLE.equals(database)) {
				String str = new SimpleDateFormat("yyyy-MM-dd").format(log
						.getOccurTime());
				hql.append(" and to_char(log.occurTime,\'yyyy-MM-dd\') = '"
						+ str + "'" );
			} else {
				SimpleDateFormat f2=null;
				Date date = log.getOccurTime();
				Calendar nextDay = Calendar.getInstance();
				nextDay.setTime(date);
				nextDay.add(Calendar.DAY_OF_YEAR,1);
				date=nextDay.getTime();
				f2 = new SimpleDateFormat("yyyy-MM-dd");
				String login = f2.format(date);
				String str = new SimpleDateFormat("yyyy-MM-dd").format(log
						.getOccurTime());
				hql.append(" and log.occurTime between date('"+ str +"') and  date('"+login+"') order by serialNo desc");
			}
		}
		Page page = super.findByHql(hql.toString(), pageNo, pageSize);
		return page;
		
//		StringBuffer hql = new StringBuffer();
//		List<UtiIExceptionLog> logs = new ArrayList<UtiIExceptionLog>();
//		hql.append("from UtiIExceptionLog utiIExceptionLog where 1=1");
//		List<String> userCodes = getUserCodeByName(userName);
//		HqlRulesUtil hqlRulesUtil = new HqlRulesUtil();
//		if (userCodes==null) {
//			hql.append(" and where 1=2 ");
//		} else {
//			for(int i=0;i<userCodes.size();i++){
//				String userCode = userCodes.get(i);
//				if(userCode.equals("")){
//				}else{
//					hql.append(" and utiIExceptionLog.userCode = '"+userCode+"'");
//				}
//				if (log.getOccurTime() != null) {
//					String database = ReadProperties.getString("database");
//					if (IConstants.DB_ORACLE.equals(database)) {
//						String str = new SimpleDateFormat("yyyy-MM-dd").format(log
//								.getOccurTime());
//						hql.append(" and to_char(utiIExceptionLog.occurTime,\'yyyy-MM-dd\') = '"
//								+ str + "'" + "order by serialNo desc");
//					} else {
//						String str = new SimpleDateFormat("yyyy-MM-dd").format(log
//								.getOccurTime());
//						hql.append(" and utiIExceptionLog.occurTime > date('"+ str +"')order by serialNo desc");
//					}
//				} else {}
//				System.out.println("hql:"+hql.toString());
//				List list = new ArrayList();
//				list = super.findByHql(hql.toString());
//				for(int j=0;j<list.size();j++){
//					logs.add((UtiIExceptionLog) list.get(j));
//				}
//			}
//		}
//		//Page page = findByHql(hql.toString(), pageNo, pageSize);
//		return logs;
//		StringBuffer hql = new StringBuffer();
//		hql.append("from UtiIExceptionLog utiIExceptionLog where 1=1");
//		String userCode =  getUserCodeByName(userName);
//		HqlRulesUtil hqlRulesUtil = new HqlRulesUtil();
//		if(userCode.equals("")){
//			hqlRulesUtil.addLike("utiIExceptionLog.userCode","");
//		}else{
//			hqlRulesUtil.addLike("utiIExceptionLog.userCode",userCode);
//		}
//		if(log.getOccurTime()!=null){
//			String str = new SimpleDateFormat("yyyy-MM-dd").format(log.getOccurTime());   
//			hql.append("and to_char(utiIExceptionLog.occurTime,\'yyyy-MM-dd\') = '"+str+"')");
//		}else{
//		}
//	//	if(userCode==null){
//
//	//  }else{
//	//		hqlRulesUtil.addLike("utiIExceptionLog.userCode",userCode);
//	//	}
//	//	hqlRulesUtil.addLike("utiIExceptionLog.occurTime", log.getOccurTime().toString());
//		if (hqlRulesUtil.getHql().trim().length() != 0) {
//			hql.append(" and ").append(hqlRulesUtil.getHql());
//		}
//		Page page = findByHql(hql.toString(), pageNo, pageSize);
//		return page;
	}

	public void deleteMethod(UtiIExceptionLog utiIExceptionLog) {
		// TODO Auto-generated method stub
		
	}

	public void insertMethod(UtiIExceptionLog utiIExceptionLog) {
		// TODO Auto-generated method stub
		super.save(utiIExceptionLog);
	}
	
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





}
