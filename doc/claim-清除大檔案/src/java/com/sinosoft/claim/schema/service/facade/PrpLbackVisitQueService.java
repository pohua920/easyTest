package com.sinosoft.claim.schema.service.facade;
/**
 * 回访问询表的数据传输对象接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLbackVisitQue;
import com.sinosoft.claim.schema.model.PrpLbackVisitQueId;

public interface PrpLbackVisitQueService {
	
	/**
	 * 保存回访问询表的数据传输对象信息
	 * @param prpLbackVisitQue ：传入的回访问询表的数据传输对象
	 */
	public void save(PrpLbackVisitQue prpLbackVisitQue) throws Exception;
	
	/**
	 * 回访问询表的数据传输对象信息
	 * @param list  :传入的回访问询表的数据传输对象信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLbackVisitQue> list) throws Exception;
	
	/**
	 * 删除回访问询表的数据传输对象信息
	 * @param prpLbackVisitQueId ：传入的回访问询表的数据传输对象编号
	 */
	public void delete(PrpLbackVisitQueId prpLbackVisitQueId) throws Exception;

	/**
	 * 更新回访问询表的数据传输对象信息
	 * @param prpLbackVisitQue :传入需要更新的回访问询表的数据传输对象
	 */
	public void update(PrpLbackVisitQue prpLbackVisitQue) throws Exception;

	/**
	 * 根据回访问询表的数据传输对象编号查询出回访问询表的数据传输对象信息
	 * @param prpLbackVisitQueId ：传入的回访问询表的数据传输对象编号
	 * @return 返回回访问询表的数据传输对象
	 */
	public PrpLbackVisitQue findPrpLbackVisitQue(PrpLbackVisitQueId prpLbackVisitQueId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的回访问询表的数据传输对象页面信息
	 */
	public Page findPrpLbackVisitQue(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取 回访问询表的数据传输对象的列表
	 * @param queryRule 查询对象
	 * @return 包含的 回访问询表的数据传输对象 的列表
	 */
	public List<PrpLbackVisitQue> findPrpLbackVisitQue(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据回访问询表的数据传输对象编号查询出回访问询表的数据传输对象信息
	 * @param certiNo ：传入的回访问询表的数据传输对象编号
	 * @return 返回回访问询表的数据传输对象
	 */
	public PrpLbackVisitQue findPrpLbackVisitQue(String certiNo) throws Exception;
}
