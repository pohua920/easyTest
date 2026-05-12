package com.sinosoft.claim.schema.service.spring;
/**
 * SwfModelUse信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.SwfModelUse;
import com.sinosoft.claim.schema.model.SwfModelUseId;
import com.sinosoft.claim.schema.service.facade.SwfModelUseService;

public class SwfModelUseServiceSpringImpl extends
GenericDaoHibernate<SwfModelUse, SwfModelUseId> implements SwfModelUseService{

	/**
	 * 保存单条信息
	 * @param SwfModelUse
	 */
	public void save(SwfModelUse swfModelUse) throws Exception {
		logger.info("保存SwfModelUse信息");
		super.save(swfModelUse);
		
	}

	/**
	 * 保存多条条信息
	 * @param List<SwfModelUse> list
	 */
	public void save(List<SwfModelUse> list) throws Exception {
		logger.info("保存SwfModelUse信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}
	
	public void saveOrUpdate(SwfModelUse swfModelUse)throws Exception{
		super.getSession().saveOrUpdate(swfModelUse);
	}

	/**
	 * 根据主键删除信息
	 * @param swfModelUseId
	 */
	public void delete(SwfModelUseId swfModelUseId) throws Exception {
		logger.info("删除SwfModelUse信息编号为" + swfModelUseId + "的SwfModelUse信息");
		super.deleteByPK(SwfModelUse.class, swfModelUseId);
	}

	/**
	 * 根据主键查询信息
	 * @param swfModelUseId
	 */
	public SwfModelUse findSwfModelUse(SwfModelUseId swfModelUseId) throws Exception {
		logger.info("查询SwfModelUse信息编号为" + swfModelUseId + "的SwfModelUse信息");
		return super.get(SwfModelUse.class, swfModelUseId);
	}

	/**
	 * 根据查询条件queryRule 查询分页信息
	 * pageNo 开始的页数
	 * pageSize每条显示的页数
	 * @param queryRule，pageNo，pageSize
	 */
	public Page findSwfModelUse(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取SwfModelUse信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	/**
	 * 根据查询条件queryRule查询所有的信息
	 * @param queryRule
	 */
	public List<SwfModelUse> findSwfModelUse(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	/**
	 * @param riskCode
	 * @param comCode
	 * @return
	 * @throws Exception
	 * 获取用户的理赔模板号
	 */
	public int getModelNo(String riskCode, String comCode) throws Exception {
		// 示例未完成
		int modelNo = -1;
		SwfModelUse swfModelUse = null;
		//这里统一取总公司的理赔模板
		String condition = " riskCode ='" + riskCode + "' and comCode='00'" + " and modelType='01'";
		//System.out.println("--sql-------" + condition);
		QueryRule queryRule = QueryRule.getInstance().addSql(condition);
		List<SwfModelUse> modelUseList = super.find(queryRule);
		if (modelUseList != null && modelUseList.size() > 0) {
			swfModelUse = modelUseList.get(0);
			modelNo = swfModelUse.getId().getModelNo();
		}
		return modelNo;

	}
	/**
	 * 查询模版号
	 * @param modelType : 模版类型
	 * @param riskCode  ：险种代码
	 * @param comCode   ：部门代码
	 * @return：void
	 * @throws Exception
	 */
	public int getModelNo(String modelType, String riskCode, String comCode) throws SQLException, Exception {
		int modelNo = 0;
		String statementStr = " SELECT a.ModelNo FROM SwfModelUse a,SwfModelMain b " + " WHERE b.ModelType = '" + modelType + "'" + "	AND b.ModelNo = a.ModelNo" + "	AND a.riskcode ='" + riskCode + "'" + "	AND a.comcode='" + comCode + "'"
				+ "	AND a.ModelStatus = '1'";
		List<?> list = HibernateUtils.findbySql(super.getSession(), statementStr);
		if (list.size() > 0) {
			Number num = (Number) list.get(0);
			if (num != null) {
				modelNo = num.intValue();
			}
		}
		return modelNo;
	}
	
	/**
     * 按条件查询多条数据
     * @param conditions 查询条件
     * @param pageNo 页号
     * @param rowsPerPage 每页的行数
     * @return Collection
     * @throws Exception
     */
    public List<SwfModelUse> findByConditions(String conditions,int pageNo,int rowsPerPage) throws Exception{
        String statement = "Select * From SwfModelUse Where " + conditions;
        List<SwfModelUse> collection = new ArrayList<SwfModelUse>();
        if(rowsPerPage<1){
        	List<?> list = HibernateUtils.findbySql(super.getSession(), statement, SwfModelUse.class);
        	for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
        		SwfModelUse swfModelUse = (SwfModelUse) iterator.next();
        		collection.add(swfModelUse);
			}
        }else{
        	List<?> list = HibernateUtils.findbySql(super.getSession(), statement,pageNo, rowsPerPage, SwfModelUse.class);
        	for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
				SwfModelUse swfModelUse = (SwfModelUse) iterator.next();
				collection.add(swfModelUse);
			}
        }
        logger.info("DBSwfModelUseBase.findByConditions() success!");
        return collection;
    }

	@Override
	public List<SwfModelUse> findByConditions(String conditions) throws Exception {
		return findByConditions(conditions,0,0);
	}
	 /**
	    * 查询模板使用情况列表
	    * @param conditions String
	    * @throws SQLException
	    * @throws Exception
	    * @return Collection
	    */
	public List<SwfModelUse> findByModelUseConditions(String conditions) throws Exception {
		return findByConditions(conditions,0,0);
	}
	

}
