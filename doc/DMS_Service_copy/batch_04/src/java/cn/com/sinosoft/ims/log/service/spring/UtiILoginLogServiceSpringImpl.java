package cn.com.sinosoft.ims.log.service.spring;

import ins.framework.common.Page;
import ins.framework.dao.GenericDaoHibernate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.com.sinosoft.ims.log.model.UtiILoginLog;
import cn.com.sinosoft.ims.log.service.facade.UtiILoginLogService;
import cn.com.sinosoft.ims.util.IConstants;
import cn.com.sinosoft.ims.util.ReadProperties;
import cn.com.sinosoft.saa.util.HqlRulesUtil;

public class UtiILoginLogServiceSpringImpl extends
		GenericDaoHibernate<Integer, String> implements UtiILoginLogService {

//	public List<UtiILoginLog> getLogList(UtiILoginLog log, String userName, int pageNo,
//			int pageSize) {
	public Page getLogList(UtiILoginLog log ,int pageNo,int pageSize){
		StringBuffer hql = new StringBuffer("from UtiILoginLog log where 1=1");
		if (log.getUserCode() == null || "".equals(log.getUserCode())) {
			
		}else {
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
				hql.append(" and log.loginTime between date('"+ str +"') and  date('"+login+"') order by serialNo desc");
				
			}
		}
		Page page = super.findByHql(hql.toString(), pageNo, pageSize);
		return page;
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/** 下面得代码是按照用户名称进行查询*/
//		StringBuffer hql = new StringBuffer();
//		List<UtiILoginLog> logs = new ArrayList<UtiILoginLog>();
//		hql.append("from UtiILoginLog utiILoginLog where 1=1");
//		List<String> userCodes = getUserCodeByName(userName);
//		HqlRulesUtil hqlRulesUtil = new HqlRulesUtil();
//		if (userCodes==null) {
//			hql.append(" and where 1=2 ");
//		} else {
//			for(int i=0;i<userCodes.size();i++){
//				String userCode = userCodes.get(i);
//				if(userCode.equals("")){
//				}else{
//					hql.append(" and utiILoginLog.userCode = '"+userCode+"'");
//				}
//				if (log.getLoginTime() != null) {
//					String database = ReadProperties.getString("database");
//					if (IConstants.DB_ORACLE.equals(database)) {
//						String str = new SimpleDateFormat("yyyy-MM-dd").format(log
//								.getLoginTime());
//						hql.append(" and to_char(utiILoginLog.loginTime,\'yyyy-MM-dd\') = '"
//								+ str + "'" );
//					} else {
//						String str = new SimpleDateFormat("yyyy-MM-dd").format(log
//								.getLoginTime());
//						hql.append(" and utiILoginLog.loginTime > date('"+ str +"')order by serialNo desc");
//					}
//				} else {}
//				
//				hql.append(" order by utiILoginLog.loginTime desc");
//				System.out.println("hql:"+hql.toString());
//				List list = new ArrayList();
////				list = super.findByHql(hql.toString(),pageNo,pageSize);
//				for(int j=0;j<list.size();j++){
//					logs.add((UtiILoginLog) list.get(j));
//				}
//			}
//		}
//		//Page page = findByHql(hql.toString(), pageNo, pageSize);
//		return logs;
	}

	public void deleteMethod() {
		// TODO Auto-generated method stub

	}

	public void insertMethod(UtiILoginLog utiILoginLog) {
		// TODO Auto-generated method stub
		super.save(utiILoginLog);

	}

	public void updateMethod(UtiILoginLog utiILoginLog) {

		super.update(utiILoginLog);
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
//		QueryRule queryRule = QueryRule.getInstance();
//		queryRule.addEqual("userName", userName);
//		UtiIUser utiIUser = super.findUnique(UtiIUser.class, queryRule);
//		if (utiIUser != null) {
//			return utiIUser.getUserCode();
//		} else {
//			return "";
//		}
	}

	
}
