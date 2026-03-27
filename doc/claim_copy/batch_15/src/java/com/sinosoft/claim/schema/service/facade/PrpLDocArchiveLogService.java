package com.sinosoft.claim.schema.service.facade;
/**
 * 资料归档调阅日志表接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLDocArchiveLog;
import com.sinosoft.claim.schema.model.PrpLDocArchiveLogId;

public interface PrpLDocArchiveLogService {
	/**
	 * 保存资料归档调阅日志表的数据传输对象类
	 * @param prpLDocArchiveLog ：传入的资料归档调阅日志表的数据传输对象类
	 */
	public void save(PrpLDocArchiveLog prpLDocArchiveLog) throws Exception;
	
	/**
	 * 保存资料归档调阅日志表的数据传输对象类
	 * @param list:保存资料归档调阅日志表的数据传输对象类
	 */
	public void save(List<PrpLDocArchiveLog> list) throws Exception;
	
	/**
	 * 删除资料归档调阅日志表的数据传输对象类
	 * @param prpLDocArchiveLogId ：传入的资料归档调阅日志表的数据传输对象类编号
	 */
	public void delete(PrpLDocArchiveLogId prpLDocArchiveLogId) throws Exception;

	/**
	 * 更新资料归档调阅日志表的数据传输对象类
	 * @param prpLDocArchiveLog :传入需要更新的资料归档调阅日志表的数据传输对象类
	 */
	public void update(PrpLDocArchiveLog prpLDocArchiveLog) throws Exception;

	/**
	 * 根据损失部位编号查询出资料归档调阅日志表的数据传输对象类
	 * @param prpLDocArchiveLogId ：传入的资料归档调阅日志表的数据传输对象类编号
	 * @return 返回资料归档调阅日志表的数据传输对象类
	 */
	public PrpLDocArchiveLog findPrpLDocArchiveLog(PrpLDocArchiveLogId prpLDocArchiveLogId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的资料归档调阅日志表的数据传输对象类页面信息
	 */
	public Page findPrpLDocArchiveLog(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param sql 完整sql语句
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的资料归档调阅日志表的数据传输对象类页面信息
	 */
	public Page findPrpLDocArchiveLog(String sql, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取资料归档调阅日志表的数据传输对象类页面信息  的集合
	 * @param queryRule 查询对象
	 * @return 包含的资料归档调阅日志表的数据传输对象类页面信息  的集合
	 */
	public List<PrpLDocArchiveLog> findPrpLDocArchiveLog(QueryRule queryRule) throws Exception;

	/**
	 * 保存资料归档调阅日志表的数据传输对象类
	 * @param list:保存资料归档调阅日志表的数据传输对象类
	 */
	public void saveOrUpdate(List<PrpLDocArchiveLog> list) throws Exception;
	/**
	 * 保存资料归档调阅日志表的数据传输对象类
	 * @param list:保存资料归档调阅日志表的数据传输对象类
	 */
	public void saveOrUpdate(PrpLDocArchiveLog prpLDocArchiveLog) throws Exception;
	public List<PrpLDocArchiveLog> findByconditions(String conditions) throws Exception;
}
