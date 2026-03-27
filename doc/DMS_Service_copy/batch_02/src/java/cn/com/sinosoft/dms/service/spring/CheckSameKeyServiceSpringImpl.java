package cn.com.sinosoft.dms.service.spring;

import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.sinosoft.dms.service.facade.CheckSameKeyService;

public class CheckSameKeyServiceSpringImpl extends GenericDaoHibernate
			implements CheckSameKeyService {
	private static Log logger= LogFactory.getLog(CheckSameKeyServiceSpringImpl.class);
	/**
	 * 如果有相同主键则返回TRUE 否则返回false
	 * keys 的形式是 字段名=值；
	 * 如果复合主键则是：id.字段名=值
	 * 中间以#号隔开
	 * */
	public boolean isSameKey(String tableName, String keys) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from " + tableName + " o where 1=1 ");
		String[] keysandValues = keys.split("#");//获得key=value类似的string 数组
		for (int i = 0; i < keysandValues.length; i++) {
			String keyandvalue = keysandValues[i];
			hql.append(" and o."+keyandvalue);
		}
		List list = super.findByHql(hql.toString());
		if(list==null||list.size()==0){
			return false;
		}else{
			return true;
		}
	}
	public boolean isSameKeys(String tableName, String keys) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from " + tableName + " o where 1=1 ");
		String[] keysandValues = keys.split("\\^");//获得key=value类似的string 数组
		for (int i = 0; i < keysandValues.length; i++) {
			String keyandvalue = keysandValues[i];
			hql.append(" and o."+keyandvalue);
		}
		List list = super.findByHql(hql.toString());
		if(list==null||list.size()==0){
			return false;
		}else{
			return true;
		}
	}
	
	public synchronized Long getMaxId(String className,String key){
		String sql = "select max(temp." + key + ") from " + className + " temp " ;
		List list = super.findByHql(sql);
		Long  maxId = null;
		if (list != null && list.size() > 0) {			
			maxId = (Long)list.get(0);
			if (maxId == null) {
				maxId = 0L;
			}
			maxId = maxId + 1;
		} 
		logger.info("取到的" + className + "表的最大主键值为" + maxId);
		return maxId;
	}
}
