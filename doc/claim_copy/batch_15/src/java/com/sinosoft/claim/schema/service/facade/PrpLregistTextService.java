package com.sinosoft.claim.schema.service.facade;
/**
 * 报案文字接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLregistText;
import com.sinosoft.claim.schema.model.PrpLregistTextId;

public interface PrpLregistTextService {
	
	/**
	 * 保存报案文字信息
	 * @param prpLregistText ：传入的报案文字
	 */
	public void save(PrpLregistText prpLregistText) throws Exception;
	
	/**
	 * 保存报案文字信息
	 * @param list:保存报案文字信息
	 */
	public void save(List<PrpLregistText> list) throws Exception;
	
	/**
	 * 删除报案文字信息
	 * @param prpLregistTextId ：传入的报案文字编号
	 */
	public void delete(PrpLregistTextId prpLregistTextId) throws Exception;

	/**
	 * 更新报案文字信息
	 * @param prpLregistText :传入需要更新的报案文字
	 */
	public void update(PrpLregistText prpLregistText) throws Exception;

	/**
	 * 根据报案文字编号查询出报案文字信息
	 * @param prpLregistTextId ：传入的报案文字编号
	 * @return 返回报案文字
	 */
	public PrpLregistText findPrpLregistText(PrpLregistTextId prpLregistTextId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的报案文字页面信息
	 */
	public Page findPrpLregistText(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取报案文字  的集合
	 * @param queryRule 查询对象
	 * @return 包含的 报案文字 的集合
	 */
	public List<PrpLregistText> findPrpLregistText(QueryRule queryRule) throws Exception;
	/**
	 * 根据报案号和类型查询附加信息
	 * @param registNo
	 * @param textType
	 * @return
	 * @throws Exception
	 */
	public List<PrpLregistText> findByRegistNo(String registNo,String textType)throws Exception;
	/**
	 * 根据报案号删除附加信息
	 * @param registNo
	 * @throws Exception
	 */
	public void deleteByRegistNo(String registNo) throws Exception;
	/**
	 * 保存或者修改的方法
	 * @param list
	 * @throws Exception
	 */
	public void saveOrUpdate(List<PrpLregistText> list) throws Exception;
	/**
	 * 保存或者修改的方法
	 * @param list
	 * @throws Exception
	 */
	public void saveOrUpdate(PrpLregistText prpLregistText) throws Exception;
}
