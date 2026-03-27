package com.sinosoft.claim.schema.service.facade;

/**
 * PRPCITEMKIND接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.sinosoft.claim.schema.model.PrpCitemKind;
import com.sinosoft.claim.schema.model.PrpCitemKindId;

public interface PrpCitemKindService {

	/**
	 * 保存标的子险信息
	 * @param prpCitemKind ：传入的标的子险
	 */
	public void save(PrpCitemKind prpCitemKind) throws Exception;

	/**
	 * 标的子险信息
	 * @param list :传入的标的子险信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpCitemKind> list) throws Exception;

	/**
	 * 删除标的子险信息
	 * @param prpCitemKindId ：传入的标的子险编号
	 */
	public void delete(PrpCitemKindId prpCitemKindId) throws Exception;

	/**
	 * 更新标的子险信息
	 * @param prpCitemKind :传入需要更新的标的子险
	 */
	public void update(PrpCitemKind prpCitemKind) throws Exception;

	/**
	 * 根据标的子险编号查询出标的子险信息
	 * @param prpCitemKindId ：传入的标的子险编号
	 * @return 返回PRPCITEMKIND
	 */
	public PrpCitemKind findPrpCitemKind(PrpCitemKindId prpCitemKindId) throws Exception;

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的标的子险页面信息
	 * @deprecated 实现类调super.find会不稳定BUG不建议使用，用findByPage代替
	 */
	public Page findPrpCitemKind(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取标的子险的列表
	 * @param queryRule 查询对象
	 * @return 包含的  标的子险的列表
	 */
	public List<PrpCitemKind> findPrpCitemKind(QueryRule queryRule) throws Exception;

	/**
	 * 根据PRPCITEMKIND编号查询出PRPCITEMKIND信息
	 * @param certiNo ：传入的PRPCITEMKIND编号
	 * @return 返回PRPCITEMKIND
	 */
	public PrpCitemKind findPrpCitemKind(String certiNo) throws Exception;

	/**
	 * 按条件查询多条数据
	 * @param conditions 查询条件
	 * @param pageNo 页号
	 * @param rowsPerPage 每页的行数
	 * @return Collection
	 * @throws Exception
	 */
	public List<PrpCitemKind> findByConditionsDistinct(String conditions, int pageNo, int rowsPerPage) throws Exception;
	
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
	 * 代替findPrpCitemKind分页查PrpCitemKind
	 * @author chenjie
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
	public List<PrpCitemKind> findByConditions(String conditions);
	/**
	 * 根据当前险别产生对应虚拟险别
	 * @param prpCitemKind
	 * @return
	 * @throws Exception
	 */
	public List<PrpCitemKind> generateVirtualKind(PrpCitemKind prpCitemKind) throws Exception;

}
