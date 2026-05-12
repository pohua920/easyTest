package com.sinosoft.claim.schema.service.facade;

/**
 * PrpCopyItemKind接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCopyItemKind;
import com.sinosoft.claim.schema.model.PrpCopyItemKindId;

public interface PrpCopyItemKindService {

	/**
	 * 保存标的子险信息
	 * @param prpCopyItemKind ：传入的标的子险
	 */
	public void save(PrpCopyItemKind prpCopyItemKind) throws Exception;

	/**
	 * 标的子险信息
	 * @param list :传入的标的子险信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpCopyItemKind> list) throws Exception;

	/**
	 * 删除标的子险信息
	 * @param prpCopyItemKindId ：传入的标的子险编号
	 */
	public void delete(PrpCopyItemKindId prpCopyItemKindId) throws Exception;

	/**
	 * 更新标的子险信息
	 * @param prpCopyItemKind :传入需要更新的标的子险
	 */
	public void update(PrpCopyItemKind prpCopyItemKind) throws Exception;

	/**
	 * 根据标的子险编号查询出标的子险信息
	 * @param prpCopyItemKindId ：传入的标的子险编号
	 * @return 返回PrpCopyItemKind
	 */
	public PrpCopyItemKind findPrpCopyItemKind(PrpCopyItemKindId prpCopyItemKindId) throws Exception;

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的标的子险页面信息
	 * @deprecated 实现类调super.find会不稳定BUG不建议使用，用findByPage代替
	 */
	public Page findPrpCopyItemKind(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取标的子险的列表
	 * @param queryRule 查询对象
	 * @return 包含的  标的子险的列表
	 */
	public List<PrpCopyItemKind> findPrpCopyItemKind(QueryRule queryRule) throws Exception;

	/**
	 * 根据PrpCopyItemKind编号查询出PrpCopyItemKind信息
	 * @param certiNo ：传入的PrpCopyItemKind编号
	 * @return 返回PrpCopyItemKind
	 */
	public PrpCopyItemKind findPrpCopyItemKind(String certiNo) throws Exception;

	/**
	 * 按条件查询多条数据
	 * @param conditions 查询条件
	 * @param pageNo 页号
	 * @param rowsPerPage 每页的行数
	 * @return Collection
	 * @throws Exception
	 */
	public List<PrpCopyItemKind> findByConditionsDistinct(String conditions, int pageNo, int rowsPerPage) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param conditions 查询条件
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的标的子险页面信息
	 * 
	 */
	public Page findKindCodeAndNameByConditionsDistinct(String conditions, int pageNo, int rowsPerPage) throws Exception;
	
	/**
	 * 
	 * 代替findPrpCopyItemKind分页查PrpCopyItemKind
	 * @author 中科软
	 * @date Mar 26, 2013 11:27:56 AM
	 * @param conditions
	 * @param pageNo
	 * @param rowsPerPage
	 * @return
	 * @throws Exception
	 */
	public Page findByPage(String conditions, int pageNo, int rowsPerPage) throws Exception;
		/**
	 * 按条件查询多条数据
	 * @param conditions 查询条件
	 * @param pageNo 页号
	 * @param rowsPerPage 每页的行数
	 * @return PageRecord 查询的一页的结果
	 * @throws Exception
	 */
	public List<PrpCopyItemKind> findByConditions(String conditions);
}
