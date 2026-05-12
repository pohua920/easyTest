package com.sinosoft.claim.schema.service.facade;
/**
 * 骨折程度和骨折部位配置表
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLfracture;
import com.sinosoft.claim.schema.model.PrpLfractureId;

public interface PrpLfractureService {
	
	/**
	 * 保存骨折程度和骨折部位信息
	 * @param prpLfracture ：骨折程度和骨折部位信息
	 */
	public void save(PrpLfracture prpLfracture) throws Exception;
	
	/**
	 * 保存骨折程度和骨折部位信息
	 * @param list  :骨折程度和骨折部位信息集合
	 * @throws Exception
	 */
	public void save(List<PrpLfracture> list) throws Exception;
	
	/**
	 * 删除骨折程度和骨折部位
	 * @param prpLfractureId ：骨折程度和骨折部位信息主键
	 */
	public void delete(PrpLfractureId prpLfractureId) throws Exception;

	/**
	 * 更新骨折程度和骨折部位信息
	 * @param prpLfracture :骨折程度和骨折部位信息
	 */
	public void update(PrpLfracture prpLfracture) throws Exception;

	/**
	 * 根据主键查询骨折程度和骨折部位信息
	 * @param prpLfractureId ：骨折程度和骨折部位信息ID
	 * @return 骨折程度和骨折部位信息
	 */
	public PrpLfracture findPrpLfracture(PrpLfractureId prpLfractureId) throws Exception;
	
	/**
	 * 根据查询对象获取骨折程度和骨折部位信息
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的骨折程度和骨折部位信息集合
	 */
	public Page findPrpLfracture(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取 骨折程度和骨折部位信息
	 * @param queryRule 查询对象
	 * @return 包含的 骨折程度和骨折部位信息 的集合
	 */
	public List<PrpLfracture> findPrpLfracture(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据骨折类型查询
	 * @param fractureType 骨折程度，骨折部位
	 * @return
	 * @throws Exception
	 */
	public List<PrpLfracture> findPrpLfracture(String fractureType) throws Exception;
}
