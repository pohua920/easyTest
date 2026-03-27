package com.sinosoft.claim.schema.service.facade;
/**
 * 回访备注表的数据传输对象接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLbackVisitText;
import com.sinosoft.claim.schema.model.PrpLbackVisitTextId;

public interface PrpLbackVisitTextService {
	
	/**
	 * 保存回访备注表的数据传输对象信息
	 * @param prpLbackVisitText ：传入的回访备注表的数据传输对象
	 */
	public void save(PrpLbackVisitText prpLbackVisitText) throws Exception;
	
	/**
	 * 回访备注表的数据传输对象信息
	 * @param list  :传入的回访备注表的数据传输对象信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLbackVisitText> list) throws Exception;
	
	/**
	 * 删除回访备注表的数据传输对象信息
	 * @param prpLbackVisitTextId ：传入的回访备注表的数据传输对象编号
	 */
	public void delete(PrpLbackVisitTextId prpLbackVisitTextId) throws Exception;

	/**
	 * 更新回访备注表的数据传输对象信息
	 * @param prpLbackVisitText :传入需要更新的回访备注表的数据传输对象
	 */
	public void update(PrpLbackVisitText prpLbackVisitText) throws Exception;

	/**
	 * 根据回访备注表的数据传输对象编号查询出回访备注表的数据传输对象信息
	 * @param prpLbackVisitTextId ：传入的回访备注表的数据传输对象编号
	 * @return 返回回访备注表的数据传输对象
	 */
	public PrpLbackVisitText findPrpLbackVisitText(PrpLbackVisitTextId prpLbackVisitTextId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的回访备注表的数据传输对象页面信息
	 */
	public Page findPrpLbackVisitText(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取 回访备注表的数据传输对象 的列表
	 * @param queryRule 查询对象
	 * @return 包含的回访备注表的数据传输对象  的列表
	 */
	public List<PrpLbackVisitText> findPrpLbackVisitText(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据回访备注表的数据传输对象编号查询出回访备注表的数据传输对象信息
	 * @param certiNo ：传入的回访备注表的数据传输对象编号
	 * @return 返回回访备注表的数据传输对象
	 */
	public PrpLbackVisitText findPrpLbackVisitText(String certiNo) throws Exception;
}
