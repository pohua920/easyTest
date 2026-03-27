package com.sinosoft.claim.schema.service.facade;
/**
 * 联系人接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLrelatePerson;
import com.sinosoft.claim.schema.model.PrpLrelatePersonId;

public interface PrpLrelatePersonService {
	
	/**
	 * 保存联系人信息
	 * @param prpLrelatePerson ：传入的联系人
	 */
	public void save(PrpLrelatePerson prpLrelatePerson) throws Exception;
	
	/**
	 * 保存联系人信息
	 * @param list:保存联系人信息
	 */
	public void save(List<PrpLrelatePerson> list) throws Exception;
	
	/**
	 * 删除联系人信息
	 * @param prpLrelatePersonId ：传入的联系人编号
	 */
	public void delete(PrpLrelatePersonId prpLrelatePersonId) throws Exception;

	/**
	 * 更新联系人信息
	 * @param prpLrelatePerson :传入需要更新的联系人
	 */
	public void update(PrpLrelatePerson prpLrelatePerson) throws Exception;

	/**
	 * 根据联系人编号查询出联系人信息
	 * @param prpLrelatePersonId ：传入的联系人编号
	 * @return 返回联系人
	 */
	public PrpLrelatePerson findPrpLrelatePerson(PrpLrelatePersonId prpLrelatePersonId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的联系人页面信息
	 */
	public Page findPrpLrelatePerson(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取联系人  的集合
	 * @param queryRule 查询对象
	 * @return 包含的 联系人 的集合
	 */
	public List<PrpLrelatePerson> findPrpLrelatePerson(QueryRule queryRule) throws Exception;
	/**
	 * @param registNo
	 * @throws Exception
	 * 根据报案号删除信息
	 */
	public void deleteByRegistNo(String registNo) throws Exception;
	/**
	 * 保存联系人信息
	 * @param list:保存联系人信息
	 */
	public void saveOrUpdate(List<PrpLrelatePerson> list) throws Exception;
	/**
	 * 保存联系人信息
	 * @param list:保存联系人信息
	 */
	public void saveOrUpdate(PrpLrelatePerson prpLrelatePerson) throws Exception;
}
