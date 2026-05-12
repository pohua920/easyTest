package com.sinosoft.claim.schema.service.spring;

/**
 * 任務定義信息接口实现类
 * @author 理赔组
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.UtiTask;
import com.sinosoft.claim.schema.service.facade.UtiTaskService;

public class UtiTaskServiceSpringImpl extends GenericDaoHibernate<UtiTask, String> implements UtiTaskService {

	@Override
	public void save(UtiTask utiTask) throws Exception {
		logger.info("保存任務定義信息信息");
		super.save(utiTask);

	}

	@Override
	public void save(List<UtiTask> list) throws Exception {
		logger.info("保存任務定義信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(String taskcode) throws Exception {
		logger.info("删除任務定義信息编号为" + taskcode + "的任務定義信息");
		super.deleteByPK(UtiTask.class, taskcode);
	}

	@Override
	public UtiTask findUtiTask(String taskcode) throws Exception {
		logger.info("查询任務定義信息编号为" + taskcode + "的任務定義信息");
		return super.get(UtiTask.class, taskcode);
	}

	@Override
	public Page findUtiTask(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取任務定義信息列表信息");
		return super.find(queryRule, pageNo, pageSize);

	}

	@Override
	public List<UtiTask> findUtiTask(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

}
