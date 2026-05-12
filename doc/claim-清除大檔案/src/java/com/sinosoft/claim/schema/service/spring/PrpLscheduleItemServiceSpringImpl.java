package com.sinosoft.claim.schema.service.spring;

/**
 * 调度任务标的接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

import com.sinosoft.claim.schema.model.PrpLscheduleItem;
import com.sinosoft.claim.schema.model.PrpLscheduleItemId;
import com.sinosoft.claim.schema.service.facade.PrpLscheduleItemService;

public class PrpLscheduleItemServiceSpringImpl extends GenericDaoHibernate<PrpLscheduleItem, PrpLscheduleItemId> implements PrpLscheduleItemService {

	/**
	 * 保存调度任务标的信息
	 * @param prpLscheduleItem ：传入的调度任务标的
	 */
	@Override
	public void save(PrpLscheduleItem prpLscheduleItem) throws Exception {
		logger.info("保存调度任务标的信息");
		super.save(prpLscheduleItem);
	}

	/**
	 * 先删除后插入，放在一个方法中
	 * @param list
	 * @throws Exception
	 */
	public void saveAndDelete(List<PrpLscheduleItem> list)throws Exception{
		if(list==null||list.size()<=0){
			return;
		}
		PrpLscheduleItem prpLscheduleItem = list.get(0);
		PrpLscheduleItem prpLscheduleItemOld = null;
		PrpLscheduleItemId prpLscheduleItemId = prpLscheduleItem.getId();
		PrpLscheduleItemId prpLscheduleItemOldId = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.registNo",prpLscheduleItemId.getRegistNo());
		queryRule.addAscOrder("id.scheduleID");
		List<PrpLscheduleItem> prpLscheduleItemList = this.findPrpLscheduleItem(queryRule);
		if (prpLscheduleItemList != null && prpLscheduleItemList.size() > 0) {
			this.deleteAll(prpLscheduleItemList);
			for (int i=0;i<list.size();i++) {
				prpLscheduleItem = list.get(i);
				prpLscheduleItemId = prpLscheduleItem.getId();
				for(int j = 0;j<prpLscheduleItemList.size();j++){
					prpLscheduleItemOld = prpLscheduleItemList.get(j);
					prpLscheduleItemOldId = prpLscheduleItemOld.getId();
					if(prpLscheduleItemOldId.getItemNo().equals(prpLscheduleItemId.getItemNo())&&prpLscheduleItemOldId.getScheduleID().equals(prpLscheduleItemId.getScheduleID())){
						break;
					}else{
						prpLscheduleItemOld = null;
					}
				}
				if(prpLscheduleItemOld==null){
					super.save(prpLscheduleItem);
				}else{
					PropertyUtils.copyProperties(prpLscheduleItemOld, prpLscheduleItem);
					super.save(prpLscheduleItemOld);
				}
			}
		}else{
			this.insertAll(list);
		}
	}

	/**
	 * 更具报案号码删除定损调度信息
	 * @param registNo
	 * @throws Exception
	 */
	public void deleteByRegistNo(String registNo) throws Exception{
		QueryRule queryRule = QueryRule.getInstance().addEqual("id.registNo",registNo);
		List<PrpLscheduleItem> prpLscheduleItemList = this.findPrpLscheduleItem(queryRule);
		if (prpLscheduleItemList != null && prpLscheduleItemList.size() > 0) {
			this.deleteAll(prpLscheduleItemList);
		}
	}
	/**
	 * 删除调度任务标的信息
	 * @param prpLscheduleItemId ：传入的调度任务标的编号
	 */
	@Override
	public void delete(PrpLscheduleItemId prpLscheduleItemId) throws Exception {
		super.deleteByPK(prpLscheduleItemId);
		logger.info("删除调度任务标的编号为" + prpLscheduleItemId + "的调度任务标的信息");
	}

	/**
	 * 删除调度任务标的信息
	 * @param prpLscheduleItemList ：所有的调度任务
	 */
	public void deleteAll(List prpLscheduleItemList) {
		super.deleteAll(prpLscheduleItemList);
	}

	/**
	 * @description: 调度任务标的修改
	 * @param PrpLscheduleItem prpLscheduleItem
	 * @throws Exception
	 */
	@Override
	public void update(PrpLscheduleItem prpLscheduleItem) {
		logger.info("修改调度任务标的信息开始");
		super.update(prpLscheduleItem);
		logger.info("修改调度任务标的信息结束");
	}

	/**
	 * 根据调度任务标的编号查询出调度任务标的信息
	 * @param prpLscheduleItemId ：传入的调度任务标的编号
	 * @return 返回调度任务标的
	 */
	@Override
	public PrpLscheduleItem findPrpLscheduleItem(PrpLscheduleItemId prpLscheduleItemId) throws Exception {
		logger.info("查询调度任务标的编号为" + prpLscheduleItemId + "的调度任务标的信息");
		return super.get(PrpLscheduleItem.class, prpLscheduleItemId);
	}

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的调度任务标的页面信息
	 */
	@Override
	public Page findPrpLscheduleItem(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取调度任务标的列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLscheduleItem> findPrpLscheduleItem(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}
	/**
	 * 保存调度任务标的信息
	 * @param list:保存调度任务标的信息集合
	 */
	@Override
	public void insertAll(List<PrpLscheduleItem> list) throws Exception {
		if (list != null && list.size() > 0) {
			super.saveAll(list);
		}
	}

	@Override
	public void saveOrUpdate(PrpLscheduleItem prpLscheduleItem) throws Exception {
		super.getSession().merge(prpLscheduleItem);
	}
}