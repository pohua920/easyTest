package com.sinosoft.claim.schema.service.spring;

/**
 * PRPCopyLimit信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.sinosoft.claim.schema.model.PrpCopyLimit;
import com.sinosoft.claim.schema.model.PrpCopyLimitId;
import com.sinosoft.claim.schema.service.facade.PrpCopyLimitService;
import com.sinosoft.sysframework.common.datatype.DateTime;

public class PrpCopyLimitServiceSpringImpl extends GenericDaoHibernate<PrpCopyLimit, PrpCopyLimitId> implements PrpCopyLimitService {

	@Override
	public void save(PrpCopyLimit prpCopyLimit) throws Exception {
		logger.info("保存PRPCopyLimit信息");
		super.save(prpCopyLimit);

	}

	@Override
	public void save(List<PrpCopyLimit> list) throws Exception {
		logger.info("保存PRPCopyLimit信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpCopyLimitId prpCopyLimitId) throws Exception {
		logger.info("删除PRPCopyLimit信息编号为" + prpCopyLimitId + "的PRPCopyLimit信息");
		super.deleteByPK(PrpCopyLimit.class, prpCopyLimitId);
	}

	@Override
	public PrpCopyLimit findPrpCopyLimit(PrpCopyLimitId prpCopyLimitId) throws Exception {
		logger.info("查询PRPCopyLimit信息编号为" + prpCopyLimitId + "的PRPCopyLimit信息");
		return super.get(PrpCopyLimit.class, prpCopyLimitId);
	}

	@Override
	public Page findPrpCopyLimit(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取PRPCopyLimit信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpCopyLimit> findPrpCopyLimit(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	/**
	 * 根据PRPCopyLimit编号查询出PRPCopyLimit信息
	 * @param certiNo ：传入的PRPCopyLimit编号
	 * @return 返回PRPCopyLimit
	 */
	public PrpCopyLimit findPrpCopyLimit(String certiNo) throws Exception {
		PrpCopyLimit prpCopyLimit = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpCopyLimit> resultList = super.find(queryRule);
		if (resultList != null && resultList.size() > 0) {
			prpCopyLimit = resultList.get(0);
		}
		return prpCopyLimit;
	}

	@Override
	public List<PrpCopyLimit> findPrpCopyLimit(String conditions, String damageDate, String startDate) throws Exception {
		//临时处理，交强险调整方案
		if(damageDate!=null && !damageDate.equals("") && startDate!=null && !startDate.equals("")){
			return this.dealCompelLimitFee(conditions, damageDate, startDate);
		}else{
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addSql(conditions);
		    return this.find(queryRule);
		}
	}
	
	public List<PrpCopyLimit> dealCompelLimitFee(String conditions, String damageDate, String startDate) throws Exception {
		DateTime damageDateFormate = new DateTime(damageDate,DateTime.YEAR_TO_DAY);
		DateTime startDateFormate = new DateTime(startDate,DateTime.YEAR_TO_DAY); 
		DateTime currentDate = new DateTime("2008-02-01",DateTime.YEAR_TO_DAY); 
		List<PrpCopyLimit> prpCopyLimitList = null;
		//出险时间在08-02-01後，起保日期在08-02-01前，交强险限额暂时按照下边方法处理.
		if(damageDateFormate.compareTo(currentDate)>=0  && startDateFormate.compareTo(currentDate)<0){
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("90", "110000");
			map.put("91", "10000");
			map.put("92", "2000");
			map.put("93", "11000");
			map.put("94", "1000");
			map.put("95", "100");
			prpCopyLimitList = new ArrayList<PrpCopyLimit>();
			PrpCopyLimit prpCopyLimit = null;
			for(int serialNo=90;serialNo<=95;serialNo++){
			    prpCopyLimit = new PrpCopyLimit();
			    prpCopyLimit.getId().setLimitType(Integer.toString(serialNo));
			    prpCopyLimit.setLimitFee(Double.parseDouble((String)map.get(Integer.toString(serialNo))));
			    prpCopyLimitList.add(prpCopyLimit);
			}
		}else{
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addSql(conditions);
			prpCopyLimitList = this.find(queryRule);
		}
		return prpCopyLimitList;
	}	

}
