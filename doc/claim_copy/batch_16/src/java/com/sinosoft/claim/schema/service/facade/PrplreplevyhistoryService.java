package com.sinosoft.claim.schema.service.facade;
/**
 * 追偿信息历史记录接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.Prplreplevyhistory;

public interface PrplreplevyhistoryService {
	
	/**
	 * 追偿信息历史记录信息
	 * @param Prplreplevyhistory ：传入的追偿信息历史记录
	 */
	public void save(Prplreplevyhistory prplreplevyhistory) throws Exception;
	
	/**
	 * 保存追偿信息历史记录
	 * @param list  :传入的追偿信息历史记录集合
	 * @throws Exceptionuan
	 */
	public void save(List<Prplreplevyhistory> list) throws Exception;
	
	/**
	 * 删除追偿信息历史记录
	 * @param policyNo ：传入的追偿信息历史记录
	 */
	public void delete(String businessNo) throws Exception;

	/**
	 * 更新追偿信息历史记录信息信息
	 * @param Prplreplevyhistory :传入需要更新的追偿信息历史记录
	 */
	public void update(Prplreplevyhistory prplreplevyhistory) throws Exception;

	/**
	 * 根据追偿信息历史记录信息编号查询出保单追偿信息历史记录
	 * @param policyNo ：传入的追偿信息历史记录编号
	 * @return 返回追偿信息历史记录信息
	 */
	public Prplreplevyhistory findPrplreplevyhistory(String businessNo) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的追偿信息历史记录信息页面信息
	 */
	public Page findPrplreplevyhistory(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取 追偿信息历史记录信息 的集合
	 * @param queryRule 查询对象
	 * @return 包含的追偿信息历史记录信息  的集合
	 */
	public List<Prplreplevyhistory> findPrplreplevyhistory(QueryRule queryRule) throws Exception;
 	/**
	 * @param prplreplevyhistory
	 * @throws Exception
	 * 保存或者修改，如果对象在数据库中不存在就保存对象，如果存在就更新对象
	 */
	public void saveOrUpdate(Prplreplevyhistory prplreplevyhistory)throws Exception;
}
