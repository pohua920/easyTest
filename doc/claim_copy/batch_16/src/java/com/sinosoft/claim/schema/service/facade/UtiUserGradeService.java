package com.sinosoft.claim.schema.service.facade;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.Collection;
import java.util.List;

import com.sinosoft.claim.schema.model.UtiUserGrade;
import com.sinosoft.claim.schema.model.UtiUserGradeId;


/**
 * 用户岗位接口
 * @author 中科软
 *
 */
public interface UtiUserGradeService {
	/**
	 * 保存用户岗位定义表信息
	 * @param prpLthirdCarLoss ：传入的用户岗位定义表
	 */
	public void save(UtiUserGrade UtiUserGrade) throws Exception;
	
	/**
	 * 保存用户岗位定义表信息
	 * @param list:保存用户岗位定义表信息
	 */
	public void save(List<UtiUserGrade> list) throws Exception;
	
	/**
	 * 删除用户岗位定义表信息
	 * @param UtiUserGradeId ：传入的用户岗位定义表编号
	 */
	public void delete(UtiUserGradeId UtiUserGradeId) throws Exception;

	/**
	 * 更新用户岗位定义表信息
	 * @param UtiUserGrade :传入需要更新的用户岗位定义表
	 */
	public void update(UtiUserGrade UtiUserGrade) throws Exception;

	/**
	 * 根据用户岗位定义表编号查询出用户岗位定义表信息
	 * @param UtiUserGradeId ：传入的用户岗位定义表编号
	 * @return 返回用户岗位定义表
	 */
	public UtiUserGrade findUtiUserGrade(UtiUserGradeId UtiUserGradeId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的用户岗位定义表页面信息
	 */
	public Page findUtiUserGrade(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	
	public List<UtiUserGrade> findUtiUserGrade(QueryRule queryRule) throws Exception;
	/**
	 * 保存用户岗位定义表信息
	 * @param list:保存用户岗位定义表信息
	 */
	public void saveOrUpdate(List<UtiUserGrade> list) throws Exception;
	/**
	 * 保存用户岗位定义表信息
	 * @param list:保存用户岗位定义表信息
	 */
	public void saveOrUpdate(UtiUserGrade UtiUserGrade) throws Exception;

	public void insertAll(List<UtiUserGrade> UtiUserGradeList);

	public Collection<UtiUserGrade> findByConditions(String conditions)throws Exception;
	/**
	 * 查询用户的角色
	 * @param userCode
	 * @return
	 */
	public List<String> findGradeCodeByUserCode(String userCode);
}
