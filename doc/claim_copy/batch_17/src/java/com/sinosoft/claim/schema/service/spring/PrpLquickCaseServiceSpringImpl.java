package com.sinosoft.claim.schema.service.spring;

/**
 * 简易赔案接口实现类
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;
import com.sinosoft.claim.schema.model.PrpLquickCase;
import com.sinosoft.claim.schema.service.facade.PrpLquickCaseService;

public class PrpLquickCaseServiceSpringImpl extends
		GenericDaoHibernate<PrpLquickCase, String> implements
		PrpLquickCaseService {
	
	/**
	 * 保存简易赔案信息
	 * @param prpLquickCase ：传入的简易赔案
	 */
	@Override
	public void save(PrpLquickCase prpLquickCase) throws Exception {
		logger.info("保存简易赔案信息");
		super.save(prpLquickCase);
	}
	
	/**
	 * 保存简易赔案信息
	 * @param list:保存简易赔案信息
	 */
	@Override
	public void save(List<PrpLquickCase> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	/**
	 * 删除简易赔案信息
	 * @param registNo ：传入的简易赔案编号
	 */
	@Override
	public void delete(String registNo) throws Exception{
		super.deleteByPK(PrpLquickCase.class, registNo);
		logger.info("删除简易赔案编号为" + registNo + "的简易赔案信息");
	}
	
	/**
	 * @description: 简易赔案修改
	 * @param PrpLquickCase prpLquickCase
	 * @throws Exception 
	 */
	@Override
	public void update(PrpLquickCase prpLquickCase){
		logger.info("修改简易赔案信息开始");
		super.update(prpLquickCase);
		logger.info("修改简易赔案信息结束");
	}
	
	/**
	 * 根据简易赔案编号查询出简易赔案信息
	 * @param registNo ：传入的简易赔案编号
	 * @return 返回简易赔案
	 */
	@Override
	public PrpLquickCase findPrpLquickCase(String registNo) throws Exception{
		logger.info("查询简易赔案编号为" + registNo + "的简易赔案信息");
		return super.get(PrpLquickCase.class,registNo);
	}
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的简易赔案页面信息
	 */
	@Override
	public Page findPrpLquickCase(QueryRule queryRule, int pageNo, int pageSize) throws Exception{
		logger.info("获取简易赔案列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLquickCase> findPrpLquickCase(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
}