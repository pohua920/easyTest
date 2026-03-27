package com.sinosoft.undwrt.common.util;

import ins.framework.dao.GenericDaoHibernate;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.sysframework.reference.DBManager;
import com.sinosoft.undwrt.common.vo.HeBaoConditionVo;

/**
 * The Class ConfigAction.
 */
public class ConfigAction  extends GenericDaoHibernate{

    /** 屬性The sinosoft standard action. */
    private StandardAction standardAction;
    
    /** 屬性The sinosoft standard check action. */
    private StandardCheckAction standardCheckAction;
    
    
    /**
	 * 獲取屬性the sinosoft standard action.
	 * 
	 * @return 屬性the sinosoft standard action的值
	 */
    public StandardAction getStandardAction() {
        return standardAction;
    }

    /**
	 * 設置屬性the sinosoft standard action.
	 * 
	 * @param standardAction
	 *            待設置的the sinosoft standard action的值
	 */
    public void setStandardAction(StandardAction standardAction) {
        this.standardAction = standardAction;
    }
    
    /**
	 * 獲取屬性the sinosoft standard check action.
	 * 
	 * @return 屬性the sinosoft standard check action的值
	 */
    public StandardCheckAction getStandardCheckAction() {
		return standardCheckAction;
	}

	/**
	 * 設置屬性the sinosoft standard check action.
	 * 
	 * @param standardCheckAction
	 *            待設置的the sinosoft standard check action的值
	 */
	public void setStandardCheckAction(StandardCheckAction standardCheckAction) {
		this.standardCheckAction = standardCheckAction;
	}

	/**
	 * 执行工作流系统发出的sql语句(针对简单描述和SQL描述).
	 * 
	 * @param iBusinessNo
	 *            业务号码
	 * @param iStrSQL
	 *            路径条件拼成的SQL语句 数据库连接
	 * @return 执行结果(TRUE:成功/FALSE:失败)
	 * @throws Exception
	 *             the exception
	 */
    public boolean executeSql(String iBusinessNo, String iStrSQL) throws Exception {
        boolean blnReturn = false;
        int intCount = 0;
        try {
            List list = super.getSession().createSQLQuery(iStrSQL).list();
            Iterator it = list.iterator();
            while (it.hasNext()) 
            {
            	BigDecimal obj = (BigDecimal) it.next();
            	intCount = obj.intValue();
            }
//            Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
//            List<?> resultList = new ArrayList();
//            //获取connection,执行静态SQL
//            Query query = session.createSQLQuery(iStrSQL);
//            intCount = query.list().size();
            if (intCount == 0)
                blnReturn = false;
            if (intCount > 0)
                blnReturn = true;
        } catch (Exception e) {
            throw e;
        }
        return blnReturn;
    }
    
    /**
	 * 执行工作流系统发出的高级条件消息语句(针对高级条件) 为了简化，目前的高级条件设置没有弄成反射的方式，而是沿袭了以前的方式，采用直接写方法名.
	 * 
	 * @param iBusinessNo
	 *            业务号码
	 * @param iComcode
	 *            the i comcode
	 * @param iModelno
	 *            the i modelno
	 * @param iNodeno
	 *            the i nodeno
	 * @param iFuncNameAndBusinessType
	 *            the i func name and business type
	 * @return 执行结果(TRUE:成功/FALSE:失败)
	 * @throws Exception
	 *             the exception
	 */
    public boolean executeFunc(String iBusinessNo, String iComcode,
            long iModelno, long iNodeno, String iFuncNameAndBusinessType) throws Exception {
        
        DBManager dbManager = new DBManager();
        dbManager.open("undwrtDataSource");
        boolean blnReturn = false;
        String iFuncName = "";
        String iBusinessType = "";
        int startNode = iFuncNameAndBusinessType.indexOf("(");
        int endNode = iFuncNameAndBusinessType.indexOf(")");
        iFuncName = iFuncNameAndBusinessType.substring(0, startNode);
        iBusinessType = iFuncNameAndBusinessType.substring(startNode + 1,
                endNode);
        //System.out.println("---------------------------进入高级条件判断-----------------------------------");
        //System.out.println("----方法名:" + iFuncName + " 业务号:" + iBusinessNo             + " 业务类型:" + iBusinessType);

        // 获取核保因子值的类
        // 获取业务数据并且和核保条件进行对比以判断是否有权限
        try {
            // 初始核保因子的基础数据
            if (standardAction.initStandard(iBusinessNo, dbManager,iBusinessType, iComcode, (int)iModelno, (int)iNodeno)) {
                // **********************************************车险核保模快条件判断>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                if (iFuncName.equals("Hebao")) {
                    //System.out.println("333333");
                    HeBaoConditionVo hebaoConditionDto = standardAction.getHebaoDto(dbManager);
                    blnReturn = standardCheckAction.checkHebao(iBusinessNo,hebaoConditionDto, dbManager, iBusinessType);
                    //checkHebao方法还没有完善
                }
                else {
                    // 传入的高级条件有误，没有找到相应的支持方法
                    blnReturn = false;
                }
            }
            else {
                // 传入的高级条件有误，没有找到相应的支持方法
                blnReturn = false;
            }
        } catch (Exception e) {
            throw e;
        }
        finally
        {
        	dbManager.close();
        }
        return blnReturn;

    }
    
}
    


