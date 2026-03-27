package com.sinosoft.claim.schema.service.spring;

/**
 * PRPCLIMIT信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.sinosoft.claim.schema.model.PrpClimit;
import com.sinosoft.claim.schema.model.PrpClimitId;
import com.sinosoft.claim.schema.service.facade.PrpClimitService;
import com.sinosoft.sysframework.common.datatype.DateTime;

public class PrpClimitServiceSpringImpl extends GenericDaoHibernate<PrpClimit, PrpClimitId> implements PrpClimitService {

	@Override
	public void save(PrpClimit prpClimit) throws Exception {
		logger.info("保存PRPCLIMIT信息");
		super.save(prpClimit);

	}

	@Override
	public void save(List<PrpClimit> list) throws Exception {
		logger.info("保存PRPCLIMIT信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpClimitId prpClimitId) throws Exception {
		logger.info("删除PRPCLIMIT信息编号为" + prpClimitId + "的PRPCLIMIT信息");
		super.deleteByPK(PrpClimit.class, prpClimitId);
	}

	@Override
	public PrpClimit findPrpClimit(PrpClimitId prpClimitId) throws Exception {
		logger.info("查询PRPCLIMIT信息编号为" + prpClimitId + "的PRPCLIMIT信息");
		return super.get(PrpClimit.class, prpClimitId);
	}

	@Override
	public Page findPrpClimit(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取PRPCLIMIT信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpClimit> findPrpClimit(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	/**
	 * 根据PRPCLIMIT编号查询出PRPCLIMIT信息
	 * @param certiNo ：传入的PRPCLIMIT编号
	 * @return 返回PRPCLIMIT
	 */
	public PrpClimit findPrpClimit(String certiNo) throws Exception {
		PrpClimit prpClimit = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpClimit> resultList = super.find(queryRule);
		if (resultList != null && resultList.size() > 0) {
			prpClimit = resultList.get(0);
		}
		return prpClimit;
	}

	@Override
	public List<PrpClimit> findPrpClimit(String conditions, String damageDate, String startDate) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		return this.find(queryRule);
	}
	
	public List<PrpClimit> dealCompelLimitFee(String conditions, String damageDate, String startDate) throws Exception {
		DateTime damageDateFormate = new DateTime(damageDate,DateTime.YEAR_TO_DAY);
		DateTime startDateFormate = new DateTime(startDate,DateTime.YEAR_TO_DAY); 
		DateTime currentDate = new DateTime("2008-02-01",DateTime.YEAR_TO_DAY); 
		List<PrpClimit> prpClimitList = null;
		//出险时间在08-02-01後，起保日期在08-02-01前，交强险限额暂时按照下边方法处理.
		if(damageDateFormate.compareTo(currentDate)>=0  && startDateFormate.compareTo(currentDate)<0){
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("90", "110000");
			map.put("91", "10000");
			map.put("92", "2000");
			map.put("93", "11000");
			map.put("94", "1000");
			map.put("95", "100");
			prpClimitList = new ArrayList<PrpClimit>();
			PrpClimit prpClimit = null;
			for(int serialNo=90;serialNo<=95;serialNo++){
			    prpClimit = new PrpClimit();
			    prpClimit.getId().setLimitType(Integer.toString(serialNo));
			    prpClimit.setLimitFee(Double.parseDouble((String)map.get(Integer.toString(serialNo))));
			    prpClimitList.add(prpClimit);
			}
		}else{
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addSql(conditions);
			prpClimitList = this.find(queryRule);
		}
		return prpClimitList;
	}	

}
