package cn.com.sinosoft.dms.service.facade;


public interface CheckSameKeyService {

	public boolean isSameKey(String tableName, String keys);
	
	public boolean isSameKeys(String tableName, String keys);
	
	public Long getMaxId(String className,String key);
}
