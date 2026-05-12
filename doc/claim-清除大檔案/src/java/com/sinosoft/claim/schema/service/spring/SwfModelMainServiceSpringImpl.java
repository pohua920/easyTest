package com.sinosoft.claim.schema.service.spring;
/**
 * SwfModelMain信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.SwfModelMain;
import com.sinosoft.claim.schema.service.facade.SwfModelMainService;

public class SwfModelMainServiceSpringImpl extends
GenericDaoHibernate<SwfModelMain, Integer> implements SwfModelMainService{

	/**
	 * 保存单条信息
	 * @param SwfModelMain
	 */
	public void save(SwfModelMain swfModelMain) throws Exception {
		logger.info("保存SwfModelMain信息");
		super.save(swfModelMain);
		
	}

	/**
	 * 保存多条条信息
	 * @param List<SwfModelMain> list
	 */
	public void save(List<SwfModelMain> list) throws Exception {
		logger.info("保存SwfModelMain信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	/**
	 * 根据主键删除信息
	 * @param swfModelMainId
	 */
	public void delete(Integer modelNo) throws Exception {
		logger.info("删除SwfModelMain信息编号为" + modelNo + "的SwfModelMain信息");
		super.deleteByPK(SwfModelMain.class, modelNo);
	}

	/**
	 * 根据主键查询信息
	 * @param swfModelMainId
	 */
	public SwfModelMain findSwfModelMain(Integer modelNo) throws Exception {
		logger.info("查询SwfModelMain信息编号为" + modelNo + "的SwfModelMain信息");
		return super.get(SwfModelMain.class, modelNo);
	}

	/**
	 * 根据查询条件queryRule 查询分页信息
	 * pageNo 开始的页数
	 * pageSize每条显示的页数
	 * @param queryRule，pageNo，pageSize
	 */
	public Page findSwfModelMain(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取SwfModelMain信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	/**
	 * 根据查询条件queryRule查询所有的信息
	 * @param queryRule
	 */
	public List<SwfModelMain> findSwfModelMain(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}

}
