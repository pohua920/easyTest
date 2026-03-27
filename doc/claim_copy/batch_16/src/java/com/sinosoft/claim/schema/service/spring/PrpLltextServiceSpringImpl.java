package com.sinosoft.claim.schema.service.spring;

/**
 * 立案文字接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLltext;
import com.sinosoft.claim.schema.model.PrpLltextId;
import com.sinosoft.claim.schema.service.facade.PrpLltextService;

public class PrpLltextServiceSpringImpl extends
		GenericDaoHibernate<PrpLltext, PrpLltextId> implements
		PrpLltextService {
	
	/**
	 * 保存立案文字信息
	 * @param prpLltext ：传入的立案文字
	 */
	@Override
	public void save(PrpLltext prpLltext) throws Exception {
		logger.info("保存立案文字信息");
		super.save(prpLltext);
	}
	
	/**
	 * 保存立案文字信息
	 * @param list:保存立案文字信息
	 */
	@Override
	public void save(List<PrpLltext> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}
	/**
	 * @param claimNo
	 * @throws Exception
	 * 根据立案号删除表
	 */
	public void deleteByclaimNo(String claimNo)throws Exception{
		String hql = "delete from PrpLltext where claimNo=?";
		super.getSession().createSQLQuery(hql).setString(0, claimNo).executeUpdate();
	}
	/**
	 * @param claimNo
	 * @throws Exception
	 * 根据立案号删除表
	 */
	public void deleteByclaimNo(String claimNo,String textType)throws Exception{
		String sql = "delete from PrpLltext where claimNo=? and textType=?";
		super.getSession().createSQLQuery(sql).setString(0, claimNo).setString(1, textType).executeUpdate();
	}
	/**
	 * @param prpLltext
	 * @throws Exception
	 */
	public void saveOrUpdate(PrpLltext prpLltext) throws Exception{
		super.getSession().saveOrUpdate(prpLltext);
	}
	/**
	 * @param list
	 * @throws Exception
	 * 保存或者删除
	 */
	public void saveOrUpdate(List<PrpLltext> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			//mantis：CLM0209，處理人員：DP0713，需求單編號：新核心-立案節點同步備案人傷訊息更新區塊鏈資料 START
			PrpLltext old = findPrpLltext(list.get(i).getId());
			if(old != null){
				delete(old.getId()); 
			}
			//mantis：CLM0209，處理人員：DP0713，需求單編號：新核心-立案節點同步備案人傷訊息更新區塊鏈資料 END
			super.getSession().saveOrUpdate(list.get(i));
		}
	}

	/**
	 * 删除立案文字信息
	 * @param prpLltextId ：传入的立案文字编号
	 */
	@Override
	public void delete(PrpLltextId prpLltextId) throws Exception{
		super.deleteByPK(prpLltextId);
		logger.info("删除立案文字编号为" + prpLltextId + "的立案文字信息");
	}
	
	/**
	 * @description: 立案文字修改
	 * @param PrpLltext prpLltext
	 * @throws Exception 
	 */
	@Override
	public void update(PrpLltext prpLltext){
		logger.info("修改立案文字信息开始");
		super.update(prpLltext);
		logger.info("修改立案文字信息结束");
	}
	
	/**
	 * 根据立案文字编号查询出立案文字信息
	 * @param prpLltextId ：传入的立案文字编号
	 * @return 返回立案文字
	 */
	@Override
	public PrpLltext findPrpLltext(PrpLltextId prpLltextId) throws Exception{
		logger.info("查询立案文字编号为" + prpLltextId + "的立案文字信息");
		return super.get(PrpLltext.class,prpLltextId);
	}
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的立案文字页面信息
	 */
	@Override
	public Page findPrpLltext(QueryRule queryRule, int pageNo, int pageSize) throws Exception{
		logger.info("获取立案文字列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLltext> findPrpLltext(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}
}