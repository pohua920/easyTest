package com.sinosoft.claim.schema.service.facade;
/**
 * 立案文字接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLltext;
import com.sinosoft.claim.schema.model.PrpLltextId;

public interface PrpLltextService {
	
	/**
	 * 保存立案文字信息
	 * @param prpLltext ：传入的立案文字
	 */
	public void save(PrpLltext prpLltext) throws Exception;
	
	/**
	 * 保存立案文字信息
	 * @param list:保存立案文字信息
	 */
	public void save(List<PrpLltext> list) throws Exception;
	
	/**
	 * 删除立案文字信息
	 * @param prpLltextId ：传入的立案文字编号
	 */
	public void delete(PrpLltextId prpLltextId) throws Exception;

	/**
	 * 更新立案文字信息
	 * @param prpLltext :传入需要更新的立案文字
	 */
	public void update(PrpLltext prpLltext) throws Exception;

	/**
	 * 根据立案文字编号查询出立案文字信息
	 * @param prpLltextId ：传入的立案文字编号
	 * @return 返回立案文字
	 */
	public PrpLltext findPrpLltext(PrpLltextId prpLltextId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的立案文字页面信息
	 */
	public Page findPrpLltext(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取立案文字  的集合
	 * @param queryRule 查询对象
	 * @return 包含的立案文字  的集合
	 */
	public List<PrpLltext> findPrpLltext(QueryRule queryRule) throws Exception;
	/**
	 * @param claimNo
	 * @throws Exception
	 * 根据立案号删除表
	 */
	public void deleteByclaimNo(String claimNo)throws Exception;
	/**
	 * @param list
	 * @throws Exception
	 * 保存或者删除
	 */
	public void saveOrUpdate(List<PrpLltext> list) throws Exception;
	/**
	 * @param list
	 * @throws Exception
	 * 保存或者删除
	 */
	public void saveOrUpdate(PrpLltext prpLltext) throws Exception;
	/**
	 * @param claimNo
	 * @throws Exception
	 * 根据立案号删除表
	 */
	public void deleteByclaimNo(String claimNo,String textType)throws Exception;
	
}
