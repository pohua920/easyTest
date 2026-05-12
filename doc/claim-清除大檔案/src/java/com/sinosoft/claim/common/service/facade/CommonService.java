package com.sinosoft.claim.common.service.facade;

import java.util.List;
import java.util.Map;

import ins.framework.common.Page;

/***
 * 通用查询入口
 * @author 理赔组
 *
 */
public interface CommonService {

	/***
	 * 完整sql分页查询
	 * @param statements 完整的sql
	 * @param pageNo 
	 * @param pageSize
	 * @return
	 */
	public Page findPage(String statements,int pageNo,int pageSize);
	
	/***
	 * 查询指定页数据
	 * @param statements 完整的sql
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<?> find(String statements,int pageNo,int pageSize);
	
	/***
	 * 执行完整的SQL并返回结果
	 * @param statements
	 * @return
	 */
	public List<?> findByStatements(String statements);
	/***
	 * 执行完整的SQL并返回结果
	 * @param statements
	 * @return
	 */
	public <T> List<T> findByStatements(String statements , Class<T> clz);
	
	
	public List<?> findBySQL(String statements, String[] params, String[] paramtypes, Map<String, Object> flowParamMap , Class<?> cls);

}
