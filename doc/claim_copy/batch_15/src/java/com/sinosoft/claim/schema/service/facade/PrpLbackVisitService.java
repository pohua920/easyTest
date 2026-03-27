package com.sinosoft.claim.schema.service.facade;
/**
 * 回访信息主表的数据传输对象接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLbackVisit;
import com.sinosoft.claim.schema.model.PrpLbackVisitId;

public interface PrpLbackVisitService {
	
	/**
	 * 保存回访信息主表的数据传输对象信息
	 * @param prpLbackVisit ：传入的回访信息主表的数据传输对象
	 */
	public void save(PrpLbackVisit prpLbackVisit) throws Exception;
	
	/**
	 * 回访信息主表的数据传输对象信息
	 * @param list  :传入的回访信息主表的数据传输对象信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLbackVisit> list) throws Exception;
	
	/**
	 * 删除回访信息主表的数据传输对象信息
	 * @param prpLbackVisitId ：传入的回访信息主表的数据传输对象编号
	 */
	public void delete(PrpLbackVisitId prpLbackVisitId) throws Exception;

	/**
	 * 更新回访信息主表的数据传输对象信息
	 * @param prpLbackVisit :传入需要更新的回访信息主表的数据传输对象
	 */
	public void update(PrpLbackVisit prpLbackVisit) throws Exception;

	/**
	 * 根据回访信息主表的数据传输对象编号查询出回访信息主表的数据传输对象信息
	 * @param prpLbackVisitId ：传入的回访信息主表的数据传输对象编号
	 * @return 返回回访信息主表的数据传输对象
	 */
	public PrpLbackVisit findPrpLbackVisit(PrpLbackVisitId prpLbackVisitId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的回访信息主表的数据传输对象页面信息
	 */
	public Page findPrpLbackVisit(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取回访信息主表的数据传输对象的列表
	 * @param queryRule 查询对象
	 * @return 包含的回访信息主表的数据传输对象 的列表
	 */
	public List<PrpLbackVisit> findPrpLbackVisit(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据回访信息主表的数据传输对象编号查询出回访信息主表的数据传输对象信息
	 * @param certiNo ：传入的回访信息主表的数据传输对象编号
	 * @return 返回回访信息主表的数据传输对象
	 */
	public PrpLbackVisit findPrpLbackVisit(String certiNo) throws Exception;
}
