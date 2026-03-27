package com.sinosoft.claim.schema.service.spring;

import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.common.vo.ExceptDeductibleRateDto;
import com.sinosoft.claim.schema.model.PrpDdeductCond;
import com.sinosoft.claim.schema.model.PrpDdeductCondId;
import com.sinosoft.claim.schema.model.PrpLdeductCond;
import com.sinosoft.claim.schema.service.facade.PrpDdeductCondService;
import com.sinosoft.claim.util.StringConvert;
/**
 * 
 * @Description 免赔条件表的数据访问接口实现
 * @author 中科软
 */
public class PrpDdeductCondServiceSpringImpl extends GenericDaoHibernate<PrpDdeductCond,PrpDdeductCondId> implements PrpDdeductCondService {

	@Override
	public void delete(String riskCode, String clauseType, String kindCode, String deductCondCode, String deductPeriod) throws Exception {
		PrpDdeductCondId prpDdeductCondId = new PrpDdeductCondId(riskCode,clauseType,kindCode,deductCondCode,deductPeriod);
		super.delete(prpDdeductCondId);
	}

	@Override
	public void deleteByConditions(String conditions) throws Exception {
		String statement = "DELETE FROM PrpDdeductCond WHERE " + conditions;
		HibernateUtils.executeSql(super.getSession(), statement);
	}

	@Override
	public PrpDdeductCond findPrpDdeductCond(String riskCode, String clauseType, String kindCode, String deductCondCode, String deductPeriod) throws Exception {
		PrpDdeductCondId prpDdeductCondId = new PrpDdeductCondId(riskCode,clauseType,kindCode,deductCondCode,deductPeriod);
		return super.get(prpDdeductCondId);
	}

	@Override
	public List<PrpDdeductCond> findPrpDdeductCond(String conditions) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		return super.find(queryRule);
	}

	@Override
	public void save(PrpDdeductCond prpDdeductCond) throws Exception {
		super.save(prpDdeductCond);
	}

	@Override
	public void save(List<PrpDdeductCond> list) throws Exception {
		super.saveAll(list);

	}

	@Override
	public void update(PrpDdeductCond prpDdeductCond){
		super.getSession().saveOrUpdate(prpDdeductCond);
	}

	@Override
	public ExceptDeductibleRateDto findDeductibleRateOfAbsolute(String clauseType, String kindCode, List<PrpLdeductCond> prpLdeductCondList, String riskCode, String validDate) throws Exception {
        double dblDeductibleRate = 0d;
        double afterDeductibleRate = 0d;
        String conditions = " kindCode = '" + kindCode +"'";        
        conditions = conditions + StringConvert.convertDate("validDate",validDate,"<=");
        //由於国寿0501是三个条款，所以必须区分，所以条件中需要加入条款的查询条件
        conditions = conditions + StringConvert.convertString("clauseType",clauseType,"=");
        conditions = conditions + " order by deductPeriod desc ";
        
        List<PrpDdeductCond> resultList = this.findPrpDdeductCond(conditions);
        Map<String,PrpDdeductCond> deductMap = new HashMap<String,PrpDdeductCond>();
        Map<String,PrpDdeductCond> commonDeductMap = new HashMap<String,PrpDdeductCond>(); 
        if(resultList!=null && !resultList.isEmpty()){
        	for(PrpDdeductCond prpDdeductCond : resultList){
        		if(prpDdeductCond.getId().getRiskCode().equals(riskCode)){
                    deductMap.put(prpDdeductCond.getId().getDeductCondCode(), prpDdeductCond);
                }else if("0000".equals(prpDdeductCond.getId().getRiskCode())){
                    commonDeductMap.put(prpDdeductCond.getId().getDeductCondCode(), prpDdeductCond);
                }
        	}
        }
        Map<String,PrpDdeductCond> deductTempMap = new HashMap<String,PrpDdeductCond>();
        if(!deductMap.isEmpty()){
            deductTempMap = deductMap;
        }else if(!commonDeductMap.isEmpty()){
            deductTempMap.putAll(commonDeductMap);
        }
        if(!deductTempMap.isEmpty()){
            PrpDdeductCond prpDdeductCondDtoTemp = (PrpDdeductCond)deductTempMap.get("000");
            if(prpDdeductCondDtoTemp != null){
                dblDeductibleRate = prpDdeductCondDtoTemp.getDeductRate();
            }
            if(prpLdeductCondList!=null && !prpLdeductCondList.isEmpty()){
            for (PrpLdeductCond prpLdeductCond : prpLdeductCondList) {
                String deductCode = prpLdeductCond.getId().getDeductCondCode();
                int times = prpLdeductCond.getTimes();
                int baseTimes = 1;
                PrpDdeductCond prpDdeductCondDto = (PrpDdeductCond)deductTempMap.get(deductCode);
                if(prpDdeductCondDto != null){
                    if("190".equals(deductCode))
                    {
                        int times1 = times-2;
                        if(times1<0){
                        	times1 = 0;
                        }
                        times = times1;
                    }
                    dblDeductibleRate += prpDdeductCondDto.getDeductRate()*(times-baseTimes+1.00);               
                    if(!"120".equals(deductCode) && !"121".equals(deductCode) && !"150".equals(deductCode) && !"170".equals(deductCode) && !"190".equals(deductCode) && !"180".equals(deductCode)
                           && !"291".equals(deductCode) && !"292".equals(deductCode) && !"293".equals(deductCode) && !"294".equals(deductCode)){
                        afterDeductibleRate += prpDdeductCondDto.getDeductRate()*(times-baseTimes+1.00);
                    }
                }
            }
            } 
        }
        ExceptDeductibleRateDto exceptDeductibleRateDto = new ExceptDeductibleRateDto();
        exceptDeductibleRateDto.setKindCode(kindCode);//险别代码
        exceptDeductibleRateDto.setDeductibleRate(dblDeductibleRate);//绝对免赔率
        exceptDeductibleRateDto.setAfterDeductibleRate(afterDeductibleRate);//责任免除後的不计免赔率
        return exceptDeductibleRateDto;
	}

}
