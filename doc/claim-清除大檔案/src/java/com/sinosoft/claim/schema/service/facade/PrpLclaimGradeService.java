package com.sinosoft.claim.schema.service.facade;
/**
 * 理赔业务权限接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLclaimGrade;
import com.sinosoft.claim.schema.model.PrpLclaimGradeId;

public interface PrpLclaimGradeService {
	
	/**
	 * 保存理赔业务权限信息
	 * @param prpLclaimGrade ：传入的理赔业务权限
	 */
	public void save(PrpLclaimGrade prpLclaimGrade) throws Exception;
	
	/**
	 * 理赔业务权限信息
	 * @param list  :传入的理赔业务权限信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLclaimGrade> list) throws Exception;
	
	/**
	 * 删除理赔业务权限信息
	 * @param prpLclaimGradeId ：传入的理赔业务权限编号
	 */
	public void delete(PrpLclaimGradeId prpLclaimGradeId) throws Exception;

	/**
	 * 更新理赔业务权限信息
	 * @param prpLclaimGrade :传入需要更新的理赔业务权限
	 */
	public void update(PrpLclaimGrade prpLclaimGrade) throws Exception;

	/**
	 * 根据理赔业务权限编号查询出理赔业务权限信息
	 * @param prpLclaimGradeId ：传入的理赔业务权限编号
	 * @return 返回理赔业务权限
	 */
	public PrpLclaimGrade findPrpLclaimGrade(PrpLclaimGradeId prpLclaimGradeId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的理赔业务权限页面信息
	 */
	public Page findPrpLclaimGrade(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取理赔业务权限信息  的列表
	 * @param queryRule 查询对象
	 * @return 包含的理赔业务权限信息  的列表
	 */
	public List<PrpLclaimGrade> findPrpLclaimGrade(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据理赔业务权限编号查询出理赔业务权限信息
	 * @param certiNo ：传入的理赔业务权限编号
	 * @return 返回理赔业务权限
	 */
	public PrpLclaimGrade findPrpLclaimGrade(String certiNo) throws Exception;
}
