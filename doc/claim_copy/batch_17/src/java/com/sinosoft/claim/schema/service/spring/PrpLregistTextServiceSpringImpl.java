package com.sinosoft.claim.schema.service.spring;

/**
 * 报案文字接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLregistText;
import com.sinosoft.claim.schema.model.PrpLregistTextId;
import com.sinosoft.claim.schema.service.facade.PrpLregistTextService;

public class PrpLregistTextServiceSpringImpl extends
		GenericDaoHibernate<PrpLregistText, PrpLregistTextId> implements
		PrpLregistTextService {
	
	/**
	 * 保存报案文字信息
	 * @param prpLregistText ：传入的报案文字
	 */
	@Override
	public void save(PrpLregistText prpLregistText) throws Exception {
		logger.info("保存报案文字信息");
		super.save(prpLregistText);
	}
	
	/**
	 * 保存报案文字信息
	 * @param list:保存报案文字信息
	 */
	@Override
	public void save(List<PrpLregistText> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}
	/**
	 * @param list
	 * @throws Exception
	 * 保存或者修改的方法
	 */
	public void saveOrUpdate(PrpLregistText prpLregistText) throws Exception {
		logger.info("保存报案文字信息");
		PrpLregistText prpLregistTextTemp = (PrpLregistText) super.getSession().load(PrpLregistText.class, prpLregistText.getId());
		//如果hibernate的session中存在这个对象，就先移除对象後，在保存，
		//		如果不存在这个对象，直接保存
		//注意是存在先删除，後保存的问题
		//如果saveOrUpdate也一样会有哪个问题，两个对象共用一个主键
//		使用merr方法，数据库中没有这条数据，会insert这个条数据，
//		如果数据库中存在这条数据，先删除，对象是删除状态，merge方法只更新session对象的内容，不更新session对象的状态
//		事务提交後，对象就会被删除
//		super.getSession().merge(arg0)
		if(prpLregistTextTemp!=null){
			//对象从session中异常
			super.getSession().evict(prpLregistTextTemp);
		}
		this.save(prpLregistText);
//		System.out.println(prpLregistTextTemp);
//		super.getSession().evict(prpLregistTextTemp);
//		super.getSession().flush();
//		super.getSession().saveOrUpdate(arg0, arg1)(prpLregistText);
	}
	/**
	 * @param list
	 * @throws Exception
	 * 保存或者修改的方法
	 */
	public void saveOrUpdate(List<PrpLregistText> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			this.saveOrUpdate(list.get(i));
		}
	}
	/**
	 * 删除报案文字信息
	 * @param prpLregistTextId ：传入的报案文字编号
	 */
	@Override
	public void delete(PrpLregistTextId prpLregistTextId) throws Exception{
		super.deleteByPK(prpLregistTextId);
		logger.info("删除报案文字编号为" + prpLregistTextId + "的报案文字信息");
	}
	/**
	 * @param registNo
	 * @throws Exception
	 * 根据报案号删除附加信息
	 */
	public void deleteByRegistNo(String registNo) throws Exception{
			String statement = "delete from prpLregistText Where registNo ='" + registNo+"'";
			super.getSession().createSQLQuery(statement).executeUpdate();
			logger.info("删除报案文字编号为" + registNo + "的报案文字信息");
	}
	/**
	 * @description: 报案文字修改
	 * @param PrpLregistText prpLregistText
	 * @throws Exception 
	 */
	@Override
	public void update(PrpLregistText prpLregistText){
		logger.info("修改报案文字信息开始");
		super.update(prpLregistText);
		logger.info("修改报案文字信息结束");
	}
	
	/**
	 * 根据报案文字编号查询出报案文字信息
	 * @param prpLregistTextId ：传入的报案文字编号
	 * @return 返回报案文字
	 */
	@Override
	public PrpLregistText findPrpLregistText(PrpLregistTextId prpLregistTextId) throws Exception{
		logger.info("查询报案文字编号为" + prpLregistTextId + "的报案文字信息");
		return super.get(PrpLregistText.class,prpLregistTextId);
	}
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的报案文字页面信息
	 */
	@Override
	public Page findPrpLregistText(QueryRule queryRule, int pageNo, int pageSize) throws Exception{
		logger.info("获取报案文字列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLregistText> findPrpLregistText(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	/**
	 * @param registNo
	 * @param textType
	 * @return
	 * @throws Exception
	 * 根据报案号和类型查询附加信息
	 */
	public List<PrpLregistText> findByRegistNo(String registNo,String textType)throws Exception{
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.registNo", registNo);
		queryRule.addEqual("id.textType", textType);
		return super.find(queryRule);
	}
}