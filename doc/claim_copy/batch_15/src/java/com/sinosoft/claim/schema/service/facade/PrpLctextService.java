package com.sinosoft.claim.schema.service.facade;
/**
 * 赔款计算文字接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLctext;
import com.sinosoft.claim.schema.model.PrpLctextId;

public interface PrpLctextService {
	
	/**
	 * 保存赔款计算文字信息
	 * @param prpLctext ：传入的赔款计算文字
	 */
	public void save(PrpLctext prpLctext) throws Exception;
	
	/**
	 * 赔款计算文字信息
	 * @param list  :传入的赔款计算文字信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLctext> list) throws Exception;
	
	/**
	 * 删除赔款计算文字信息
	 * @param prpLctextId ：传入的赔款计算文字编号
	 */
	public void delete(PrpLctextId prpLctextId) throws Exception;

	/**
	 * 更新赔款计算文字信息
	 * @param prpLctext :传入需要更新的赔款计算文字
	 */
	public void update(PrpLctext prpLctext) throws Exception;

	/**
	 * 根据赔款计算文字编号查询出赔款计算文字信息
	 * @param prpLctextId ：传入的赔款计算文字编号
	 * @return 返回赔款计算文字
	 */
	public PrpLctext findPrpLctext(PrpLctextId prpLctextId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的赔款计算文字页面信息
	 */
	public Page findPrpLctext(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取赔款计算文字页面信息  的列表
	 * @param queryRule 查询对象
	 * @return 包含的赔款计算文字页面信息  的列表
	 */
	public List<PrpLctext> findPrpLctext(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据赔款计算文字编号查询出赔款计算文字信息
	 * @param certiNo ：传入的赔款计算文字编号
	 * @return 返回赔款计算文字
	 */
	public PrpLctext findPrpLctext(String certiNo) throws Exception;
	/**
	 * 
	 * 根据计算书号删除赔款计算文字信息
	 * @author 中科软
	 * @date Mar 6, 2013 7:43:30 PM
	 * @param compensateNo
	 * @throws Exception
	 */
	public void deleteByCompensateNo(String compensateNo) throws Exception;
}
