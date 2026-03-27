package com.sinosoft.claim.common.service.facade;
/**
 * 批改保单信息表接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.ArrayList;
import java.util.List;

import com.sinosoft.claim.schema.model.PrpPmain;

public interface PrpPmainService {
    /**
     * 按条件查询多条数据
     * @param conditions 查询条件
     * @param pageNo 页号
     * @param rowsPerPage 每页的行数
     * @return Collection
     * @throws Exception
     */
    public ArrayList<PrpPmain> findByConditions(String conditions,int pageNo,int rowsPerPage) throws Exception;
    /**
     * 按主键查找一条数据
     * @param endorseNo 批单号码
     * @return PrpPmainDto
     * @throws Exception
     */
    public PrpPmain findByPrimaryKey(String endorseNo) throws Exception;
	/**
	 * 保存批改保单信息
	 * @param prpPmain ：传入的批改保单
	 */
	public void save(PrpPmain prpPmain) throws Exception;
	
	/**
	 * 保存批改保单信息
	 * @param list:保存批改保单信息
	 */
	public void save(List<PrpPmain> list) throws Exception;
	
	/**
	 * 删除批改保单信息
	 * @param prpPmainId ：传入的批改保单编号
	 */
	public void delete(String endorseNo) throws Exception;

	/**
	 * 更新批改保单信息
	 * @param prpPmain :传入需要更新的批改保单
	 */
	public void update(PrpPmain prpPmain) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的批改保单页面信息
	 */
	public Page findPrpPmain(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取批改保单信息  的集合
	 * @param queryRule 查询对象
	 * @return 包含的  批改保单信息的集合
	 */
	public List<PrpPmain> findPrpPmain(QueryRule queryRule) throws Exception;
    
}
