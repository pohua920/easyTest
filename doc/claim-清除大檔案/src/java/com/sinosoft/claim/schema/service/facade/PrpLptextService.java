package com.sinosoft.claim.schema.service.facade;
/**
 * 预赔文字接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLptext;
import com.sinosoft.claim.schema.model.PrpLptextId;

public interface PrpLptextService {
	
	/**
	 * 保存预赔文字信息
	 * @param prpLptext ：传入的预赔文字
	 */
	public void save(PrpLptext prpLptext) throws Exception;
	
	/**
	 * 预赔文字信息
	 * @param list  :传入的预赔文字信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLptext> list) throws Exception;
	
	/**
	 * 删除预赔文字信息
	 * @param prpLptextId ：传入的预赔文字编号
	 */
	public void delete(PrpLptextId prpLptextId) throws Exception;

	/**
	 * 更新预赔文字信息
	 * @param prpLptext :传入需要更新的预赔文字
	 */
	public void update(PrpLptext prpLptext) throws Exception;

	/**
	 * 根据预赔文字编号查询出预赔文字信息
	 * @param prpLptextId ：传入的预赔文字编号
	 * @return 返回预赔文字
	 */
	public PrpLptext findPrpLptext(PrpLptextId prpLptextId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的预赔文字页面信息
	 */
	public Page findPrpLptext(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取 预赔文字信息 的集合
	 * @param queryRule 查询对象
	 * @return 包含的  预赔文字信息的集合
	 */
	public List<PrpLptext> findPrpLptext(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据预赔文字编号查询出预赔文字信息
	 * @param certiNo ：传入的预赔文字编号
	 * @return 返回预赔文字
	 */
	public PrpLptext findPrpLptext(String certiNo) throws Exception;
}
