package com.sinosoft.claim.schema.service.spring;
/**
 * 追偿信息历史记录接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.Prplreplevyhistory;
import com.sinosoft.claim.schema.service.facade.PrplreplevyhistoryService;

public class PrplreplevyhistoryServiceSpringImpl extends
GenericDaoHibernate<Prplreplevyhistory, String> implements PrplreplevyhistoryService{

	@Override
	public void save(Prplreplevyhistory prplreplevyhistory) throws Exception {
		logger.info("保存追偿信息历史记录信息");
		super.save(prplreplevyhistory);
		
	}

	@Override
	public void save(List<Prplreplevyhistory> list) throws Exception {
		logger.info("保存追偿信息历史记录");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}
	public void saveOrUpdate(Prplreplevyhistory prplreplevyhistory)throws Exception{
		if(prplreplevyhistory!=null){
			super.getSession().saveOrUpdate(prplreplevyhistory);
		}
	}
	@Override
	public void delete(String businessNo) throws Exception {
		logger.info("删除追偿信息历史记录编号为" + businessNo + "的追偿信息历史记录");
		super.deleteByPK(Prplreplevyhistory.class, businessNo);
	}

	@Override
	public Prplreplevyhistory findPrplreplevyhistory(String businessNo) throws Exception {
		logger.info("查询追偿信息历史记录编号为" + businessNo + "的追偿信息历史记录");
		return super.get(Prplreplevyhistory.class,businessNo);
	}
    
	@Override
	public Page findPrplreplevyhistory(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取追偿信息历史记录列表信息");
		return super.find(queryRule, pageNo, pageSize);
		
	}

	@Override
	public List<Prplreplevyhistory> findPrplreplevyhistory(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

}
