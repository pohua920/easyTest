package com.sinosoft.claim.schema.service.facade;
/**
 * 追偿损余文字说明接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLrtext;
import com.sinosoft.claim.schema.model.PrpLrtextId;

public interface PrpLrtextService {
	
	/**
	 * 保存追偿损余文字说明信息
	 * @param prpLrtext ：传入的追偿损余文字说明
	 */
	public void save(PrpLrtext prpLrtext) throws Exception;
	
	/**
	 * 追偿损余文字说明信息
	 * @param list  :传入的追偿损余文字说明信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLrtext> list) throws Exception;
	
	/**
	 * 删除追偿损余文字说明信息
	 * @param prpLrtextId ：传入的追偿损余文字说明编号
	 */
	public void delete(PrpLrtextId prpLrtextId) throws Exception;

	/**
	 * 更新追偿损余文字说明信息
	 * @param prpLrtext :传入需要更新的追偿损余文字说明
	 */
	public void update(PrpLrtext prpLrtext) throws Exception;

	/**
	 * 根据追偿损余文字说明编号查询出追偿损余文字说明信息
	 * @param prpLrtextId ：传入的追偿损余文字说明编号
	 * @return 返回追偿损余文字说明
	 */
	public PrpLrtext findPrpLrtext(PrpLrtextId prpLrtextId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的追偿损余文字说明页面信息
	 */
	public Page findPrpLrtext(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取追偿损余文字说明信息  的集合
	 * @param queryRule 查询对象
	 * @return 包含的  追偿损余文字说明信息的集合
	 */
	public List<PrpLrtext> findPrpLrtext(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据追偿损余文字说明编号查询出追偿损余文字说明信息
	 * @param certiNo ：传入的追偿损余文字说明编号
	 * @return 返回追偿损余文字说明
	 */
	public PrpLrtext findPrpLrtext(String certiNo) throws Exception;
}
