package com.sinosoft.claim.schema.service.spring;
/**
 * SwfPackage信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.SwfPackage;
import com.sinosoft.claim.schema.model.SwfPackageId;
import com.sinosoft.claim.schema.service.facade.SwfPackageService;

public class SwfPackageServiceSpringImpl extends
GenericDaoHibernate<SwfPackage, SwfPackageId> implements SwfPackageService{
	
	/**
	 * 保存单条信息
	 * @param SwfPackage
	 */
	public void save(SwfPackage swfPackage) throws Exception {
		logger.info("保存SwfPackage信息");
		super.save(swfPackage);
		
	}

	/**
	 * 保存多条条信息
	 * @param List<SwfPackage> list
	 */
	public void save(List<SwfPackage> list) throws Exception {
		logger.info("保存SwfPackage信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	/**
	 * 根据主键删除信息
	 * @param swfPackageId
	 */
	public void delete(SwfPackageId swfPackageId) throws Exception {
		logger.info("删除SwfPackage信息编号为" + swfPackageId + "的SwfPackage信息");
		super.deleteByPK(SwfPackage.class, swfPackageId);
	}

	/**
	 * 根据主键查询信息
	 * @param swfPackageId
	 */
	public SwfPackage findSwfPackage(SwfPackageId swfPackageId) throws Exception {
		logger.info("查询SwfPackage信息编号为" + swfPackageId + "的SwfPackage信息");
		return super.get(SwfPackage.class, swfPackageId);
	}

	/**
	 * 根据查询条件queryRule 查询分页信息
	 * pageNo 开始的页数
	 * pageSize每条显示的页数
	 * @param queryRule，pageNo，pageSize
	 */
	public Page findSwfPackage(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取SwfPackage信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	/**
	 * 根据查询条件queryRule查询所有的信息
	 * @param queryRule
	 */
	public List<SwfPackage> findSwfPackage(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
}
