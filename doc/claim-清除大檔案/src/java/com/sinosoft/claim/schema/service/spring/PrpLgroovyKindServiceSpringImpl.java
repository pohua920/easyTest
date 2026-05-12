package com.sinosoft.claim.schema.service.spring;

/**
 * 键值信息接口实现类
 * @author 中科软
 */
import ins.framework.cache.CacheManager;
import ins.framework.cache.CacheService;
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;


import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.DataUtils;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.common.vo.PolicyDto;
import com.sinosoft.claim.schema.model.PrpCitemKind;
import com.sinosoft.claim.schema.model.PrpLgroovyKind;
import com.sinosoft.claim.schema.service.facade.PrpLgroovyKindService;
@SuppressWarnings("unchecked")
public class PrpLgroovyKindServiceSpringImpl extends GenericDaoHibernate<PrpLgroovyKind, String> implements PrpLgroovyKindService {
	
	private static CacheService cacheManager = CacheManager.getInstance("PrpLgroovyKindService");
	
	/**
	 * 清空缓存
	 */
	public void clearAllCacheManager(Object...keys){
		if(keys!=null){
			String key = cacheManager.generateCacheKey(keys);
			cacheManager.remove(key);
		}else{
			cacheManager.clearAllCacheManager();
		}
	}
	/**
	 * 险别配置
	 * @param prpLgroovyKind ：险别配置
	 */
	public void save(PrpLgroovyKind prpLgroovyKind) throws Exception {
		logger.info("保存键值信息信息");
		prpLgroovyKind.setId(null);
		super.save(prpLgroovyKind);
	}

	/**
	 * 保存险别配置
	 * @param list  :险别配置集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLgroovyKind> list) throws Exception {
		logger.info("保存键值信息");
		for (int i = 0; i < list.size(); i++) {
			this.save(list.get(i));
		}
	}

	/**
	 * 删除险别配置
	 * @param policyNo ：险别配置
	 */
	public void delete(String id) throws Exception {
		logger.info("删除键值信息编号为" + id + "的键值信息");
		super.deleteByPK(PrpLgroovyKind.class, id);
	}

	/**
	 * 根据id查询险别配置
	 * @param policyNo ：险别配置id
	 * @return 返回险别配置
	 */
	public PrpLgroovyKind findPrpLgroovyKind(String id) throws Exception {
		logger.info("查询键值信息编号为" + id + "的键值信息");
		return super.get(PrpLgroovyKind.class, id);
	}

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的任務定義页面信息
	 */
	public Page findPrpLgroovyKind(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取键值信息列表信息");
		return super.find(queryRule, pageNo, pageSize);

	}

	/**据查询对象获取List对象的列表
	 * @param queryRule
	 * @return
	 * @throws Exception
	 */
	public List<PrpLgroovyKind> findPrpLgroovyKind(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 查询险总的配置
	 * @param prpLgroovyKind
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public Page findPrpLgroovyKind(PrpLgroovyKind prpLgroovyKind, int pageNo, int pageSize) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		if(!CommonUtils.isEmpty(prpLgroovyKind.getRiskCode())){
			queryRule.addLike("riskCode", prpLgroovyKind.getRiskCode()+"%");
		}
		if(!CommonUtils.isEmpty(prpLgroovyKind.getRiskName())){
			queryRule.addLike("riskName", prpLgroovyKind.getRiskName()+"%");
		}
		if(!CommonUtils.isEmpty(prpLgroovyKind.getKindCode())){
			queryRule.addLike("kindCode", prpLgroovyKind.getKindCode()+"%");
		}
		if(!CommonUtils.isEmpty(prpLgroovyKind.getKindName())){
			queryRule.addLike("kindName", prpLgroovyKind.getKindName()+"%");
		}
		if(!CommonUtils.isEmpty(prpLgroovyKind.getIsMainKind())){
			queryRule.addEqual("isMainKind", prpLgroovyKind.getIsMainKind());
		}
		if(!CommonUtils.isEmpty(prpLgroovyKind.getCompensateType())){
			String[] types = prpLgroovyKind.getCompensateType().split(",");
			StringBuffer sb = new StringBuffer();
			sb.append("(");
			for(String s : types){
				sb.append("compensateType like '%").append(s).append("%' or ");
			}
			sb.append(" 1!=1 )");
			queryRule.addSql(sb.toString());
		}
		queryRule.addEqual("validStatus", "1");
		return this.find(queryRule, pageNo, pageSize);
	}
	/**
	 * 根据险别和险种判断是否存在
	 * @param prpLgroovyKind
	 * @return
	 * @throws Exception
	 */
	public PrpLgroovyKind isKindExist(PrpLgroovyKind prpLgroovyKind)throws Exception{
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("riskCode", prpLgroovyKind.getRiskCode());
		queryRule.addEqual("kindCode", prpLgroovyKind.getKindCode());
		List<PrpLgroovyKind> list = this.find(queryRule);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	/**
	 * 根据主键修改内容
	 * @param prpLgroovyKind
	 * @throws Exception
	 */
	public void updateKind(PrpLgroovyKind prpLgroovyKind)throws Exception{
		PrpLgroovyKind temp = this.findPrpLgroovyKind(prpLgroovyKind.getId());
		PropertyUtils.copyProperties(temp, prpLgroovyKind);
		super.update(temp);
	}
	
	/**
	 * 查询赔付险别,多个使用，号分隔
	 * @param compensateTypes
	 * @param riskCode
	 * @return
	 * @throws Exception
	 */
	public List<String> findCompensateType(String compensateTypes,String riskCode)throws Exception{
		String key = cacheManager.generateCacheKey("findCompensateType",compensateTypes,riskCode);
		List<String> values = (List<String>)cacheManager.getCache(key);
		if(values!=null){
			return values;
		}
		values = new ArrayList<String>();
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct kindCode from PrpLgroovyKind where validStatus = '1' ");
		if(riskCode!=null&&!"".equals(riskCode)){
			sql.append(" and riskCode = '"+riskCode+"'");
		}
		sql.append(" and (1!=1 ");
		for(String str : compensateTypes.split(",")){
			sql.append(" or compensateType like '%"+str+"%'");
		}
		sql.append(")");
		sql.append(" order by kindCode");
		List<Object> objs = super.getSession().createSQLQuery(sql.toString()).list();
		for(Object obj : objs){
			if(obj!=null){
				values.add(obj.toString());
			}
		}
		cacheManager.putCache(key, values);
		return values;
	}
	/**
	 * 查询限额控制险别
	 * @param compensateTypes
	 * @param riskCode
	 * @return
	 * @throws Exception
	 */
	public List<PrpLgroovyKind> findLimitType(String limitType,String riskCode)throws Exception{
		String key = cacheManager.generateCacheKey("findLimitType",limitType,riskCode);
		List<PrpLgroovyKind> values = null;
		//暂时先屏蔽掉缓存
//		values = (List<PrpLgroovyKind>)cacheManager.getCache(key);
		if(values!=null){
			return values;
		}
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("validStatus", "1");
		if(riskCode!=null&&!"".equals(riskCode)){
			queryRule.addEqual("riskCode", riskCode);
		}
		queryRule.addLike("limitType", "%"+limitType+"%");
		queryRule.addAscOrder("kindCode");
		values = this.find(queryRule);
		cacheManager.putCache(key,values);
		return values;
	}
	
	/**
	 * 查询赔付主车险别
	 * @return
	 * @throws Exception
	 */
	public List<String> getMainCarLoss(String riskCode){
		try {
			return this.findCompensateType(PrpLgroovyKind.MAINCARLOSS,riskCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<String>();
	}
	/**
	 * 查询赔付被保險人/駕駛人
	 * @return
	 * @throws Exception
	 */
	public List<String> getInsAnddriver(String riskCode){
		try {
			return this.findCompensateType(PrpLgroovyKind.INSANDDRIVER,riskCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<String>();
	}
	/**
	 * 查询赔付主车人 伤
	 * @return
	 * @throws Exception
	 */
	public List<String> getMainPersonLoss(String riskCode){
		try {
			return findCompensateType(PrpLgroovyKind.MAINPERSONLOSS,riskCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<String>();
	}
	/**
	 * 查询赔付主车财损
	 * @return
	 * @throws Exception
	 */
	public List<String> getMainPropLoss(String riskCode){
		try {
			return this.findCompensateType(PrpLgroovyKind.MAINPROPLOSS,riskCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<String>();
	}
	/**
	 * 查询赔付三者車
	 * @return
	 * @throws Exception
	 */
	public List<String> getThirdCarLoss(String riskCode){
		try {
			return this.findCompensateType(PrpLgroovyKind.THIRDCARLOSS,riskCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<String>();
	}
	/**
	 * 查询赔付三者物 
	 * @return
	 * @throws Exception
	 */
	public List<String> getThirdPropLoss(String riskCode){
		try {
			return this.findCompensateType(PrpLgroovyKind.THIRDPROPLOSS,riskCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<String>();
	}
	/**
	 * 查询赔付三者人
	 * @return
	 * @throws Exception
	 */
	public List<String> getThirdPersonLoss(String riskCode){
		try {
			return this.findCompensateType(PrpLgroovyKind.THIRDPERSONLOSS,riskCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<String>();
	}
	/**
	 * 查询赔付可赔人伤部分InsAnddriver、MainPersonLoss、ThirdPersonLoss之并集
	 * @return
	 * @throws Exception
	 */
	public List<String> getKindCodeForPerson(String riskCode){
		List<String> kindCodeForPerson = new ArrayList<String>();
		List<String> insAnddriver = this.getInsAnddriver(riskCode);
		kindCodeForPerson.addAll(insAnddriver);
		List<String> mainPersonLoss = this.getMainPersonLoss(riskCode);
		kindCodeForPerson.removeAll(mainPersonLoss);
		kindCodeForPerson.addAll(mainPersonLoss);
		List<String> thirdPersonLoss = this.getThirdPersonLoss(riskCode);
		kindCodeForPerson.removeAll(thirdPersonLoss);
		kindCodeForPerson.addAll(thirdPersonLoss);
		return kindCodeForPerson;
	}
	/**
	 * 可赔财损部分 MainPropLoss、ThirdPropLoss 之并集
	 * @return
	 * @throws Exception
	 */
	public List<String> getKindCodeForProp(String riskCode){
		List<String> kindCodeForProp = new ArrayList<String>();
		kindCodeForProp.addAll(this.getMainPropLoss(riskCode));
		List<String> thirdPropLoss = this.getThirdPropLoss(riskCode);
		kindCodeForProp.removeAll(thirdPropLoss);
		kindCodeForProp.addAll(thirdPropLoss);
		return kindCodeForProp;
	}
	/**
	 * 可赔车损部分 MainCarLoss、ThirdCarLoss 之并集
	 * @return
	 * @throws Exception
	 */
	public List<String> getKindCodeForCar(String riskCode){
		List<String> kindCodeForCar = new ArrayList<String>();
		kindCodeForCar.addAll(this.getMainCarLoss(riskCode));
		List<String>thirdCarLoss = this.getThirdCarLoss(riskCode);
		kindCodeForCar.removeAll(thirdCarLoss);
		kindCodeForCar.addAll(thirdCarLoss);
		return kindCodeForCar;
	}
	/**
	 * 所有险别 目前为车、财、人之合集之并集
	 * @return
	 * @throws Exception
	 */
	public List<String> getKindCodeForAll(String riskCode){
		List<String> kindCodeForAll = new ArrayList<String>();
		kindCodeForAll.addAll(this.getKindCodeForPerson(riskCode));
		List<String> kindCodeForCar = this.getKindCodeForCar(riskCode);
		kindCodeForAll.removeAll(kindCodeForCar);
		kindCodeForAll.addAll(kindCodeForCar);
		List<String> kindCodeForProp = this.getKindCodeForProp(riskCode);
		kindCodeForAll.removeAll(kindCodeForProp);
		kindCodeForAll.addAll(kindCodeForProp);
		return kindCodeForAll;
	}
	/**
	 * 限额取值类型（计次）
	 * @param riskCode
	 * @return
	 * @throws Exception
	 */
	public Map<String,Integer> getLimitForMeterType(String riskCode){
		Map<String, Integer> values = null;
		try {
			String key = cacheManager.generateCacheKey("getLimitForMeterType",riskCode);
			//暂时先屏蔽掉缓存
//			values = (Map<String,Integer>)cacheManager.getCache(key);
			if(values!=null){
				return values;
			}
			values = new HashMap<String,Integer>();
			List<PrpLgroovyKind> list = this.findLimitType(PrpLgroovyKind.LIMITFORMETERTYPE, riskCode);
			for(PrpLgroovyKind temp : list){
				if(temp.getMeterType()!=null){
					values.put(temp.getKindCode(),Integer.valueOf(temp.getMeterType()));
				}else{
					values.put(temp.getKindCode(),1);
				}
			}
			cacheManager.putCache(key, values);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return values;
	}
	/**
	 * 限额取值类型（保險期間累計）
	 * @param riskCode
	 * @return
	 * @throws Exception
	 */
	public List<String> getLimitForCumulativeType(String riskCode){
		List<String> values = null;
		try {
			String key = cacheManager.generateCacheKey("getLimitForCumulativeType",riskCode);
			//暂时先屏蔽掉缓存
//			values = (List<String>)cacheManager.getCache(key);
			if(values!=null){
				return values;
			}
			values = new ArrayList<String>();
			List<PrpLgroovyKind> list = this.findLimitType(PrpLgroovyKind.LIMITFORCUMULATIVETYPE, riskCode);
			for(PrpLgroovyKind temp : list){
				values.add(temp.getKindCode());
			}
			cacheManager.putCache(key,values);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return values;
	}
	/**
	 * 限额取值类型 (每一人/每次事故)
	 * 此项若增加需严格校验prpCitemKind的model值
	 * @param riskCode
	 * @return
	 * @throws Exception
	 */
	public List<String> getLimitForPerPersonType(String riskCode){
		List<String> values = null;
		try {
			String key = cacheManager.generateCacheKey("getLimitForPerPersonType",riskCode);
			//暂时先屏蔽掉缓存
//			values = (List<String>)cacheManager.getCache(key);
			if(values!=null){
				return values;
			}
			values = new ArrayList<String>();
			List<PrpLgroovyKind> list = this.findLimitType(PrpLgroovyKind.LIMITFORPERPERSONTYPE, riskCode);
			for(PrpLgroovyKind temp : list){
				values.add(temp.getKindCode());
			}
			cacheManager.putCache(key,values);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return values;
	}
	/**
	 * 保额依赖其主险的
	 * @param riskCode
	 * @return
	 * @throws Exception
	 */
	public  Map<String,String[]> getAmountReferMainKind(String riskCode){
		Map<String, String[]> values = null;
		try {
			String key = cacheManager.generateCacheKey("getAmountReferMainKind",riskCode);
			//暂时先屏蔽掉缓存
//			values = ( Map<String,String[]>)cacheManager.getCache(key);
			if(values!=null){
				return values;
			}
			values = new HashMap<String,String[]>();
			List<PrpLgroovyKind> list = this.findLimitType(PrpLgroovyKind.AMOUNTREFERMAINKIND, riskCode);
			for(PrpLgroovyKind temp : list){
				if(temp.getReferOtherKind()!=null){
					values.put(temp.getKindCode(),temp.getReferOtherKind().split(","));
				}
			}
			cacheManager.putCache(key,values);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return values;
	}
	/**
	 * 限额依赖其他险别的，需要在所有计算完成后进行处理
	 * KEY:险别；value：赔付时限额依赖的险别
	 * @param riskCode
	 * @return
	 * @throws Exception
	 */
	public  Map<String,String[]> getLimitReferOtherKind(String riskCode){
		Map<String,String[]> values = null;
		try {
			String key = cacheManager.generateCacheKey("getLimitReferOtherKind",riskCode);
			//暂时先屏蔽掉缓存
//			values = ( Map<String,String[]>)cacheManager.getCache(key);
			if(values!=null){
				return values;
			}
			values = new HashMap<String,String[]>();
			List<PrpLgroovyKind> list = this.findLimitType(PrpLgroovyKind.LIMITREFEROTHERKIND, riskCode);
			for(PrpLgroovyKind temp : list){
				if(temp.getReferOtherKind()!=null){
					values.put(temp.getKindCode(),temp.getReferOtherKind().split(","));
				}
			}
			cacheManager.putCache(key,values);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return values;
	}
	/**
	 * 不限额控制的险别
	 * @param riskCode
	 * @return
	 * @throws Exception
	 */
	public  List<String> getKindCodeForNoLimit(String riskCode){
		List<String> values = null;
		try {
			String key = cacheManager.generateCacheKey("getKindCodeForNoLimit",riskCode);
			//暂时先屏蔽掉缓存
//			values = (List<String>)cacheManager.getCache(key);
			if(values!=null){
				return values;
			}
			values = new ArrayList<String>();
			List<PrpLgroovyKind> list = this.findLimitType(PrpLgroovyKind.KINDCODEFORNOLIMIT, riskCode);
			for(PrpLgroovyKind temp : list){
				if(temp.getKindCode()!=null){
					values.add(temp.getKindCode());
				}
			}
			cacheManager.putCache(key,values);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return values;
	}
	/**
	 * 查询赔付主车险别
	 * @return
	 * @throws Exception
	 */
	public List<String> getMainCarLoss(){
		return getMainCarLoss(null);
	}
	/**
	 * 查询赔付被保險人/駕駛人
	 * @return
	 * @throws Exception
	 */
	public List<String> getInsAnddriver(){
		return getInsAnddriver(null);
	}
	/**
	 * 查询赔付主车人 伤
	 * @return
	 * @throws Exception
	 */
	public List<String> getMainPersonLoss(){
		return getMainPersonLoss(null);
	}
	/**
	 * 查询赔付主车财损
	 * @return
	 * @throws Exception
	 */
	public List<String> getMainPropLoss(){
		return getMainPropLoss(null);
	}
	/**
	 * 查询赔付三者車
	 * @return
	 * @throws Exception
	 */
	public List<String> getThirdCarLoss(){
		return getThirdCarLoss(null);
	}
	/**
	 * 查询赔付三者物 
	 * @return
	 * @throws Exception
	 */
	public List<String> getThirdPropLoss(){
		return getThirdPropLoss(null);
	}
	/**
	 * 查询赔付三者人
	 * @return
	 * @throws Exception
	 */
	public List<String> getThirdPersonLoss(){
		return getThirdPersonLoss(null);
	}
	/**
	 * 查询赔付可赔人伤部分InsAnddriver、MainPersonLoss、ThirdPersonLoss之并集
	 * @return
	 * @throws Exception
	 */
	public List<String> getKindCodeForPerson(){
		return getKindCodeForPerson(null);
	}
	/**
	 * 可赔财损部分 MainPropLoss、ThirdPropLoss 之并集
	 * @return
	 * @throws Exception
	 */
	public List<String> getKindCodeForProp(){
		return getKindCodeForProp(null);
	}
	/**
	 * 可赔车损部分 MainCarLoss、ThirdCarLoss 之并集
	 * @return
	 * @throws Exception
	 */
	public List<String> getKindCodeForCar(){
		return getKindCodeForCar(null);
	}
	/**
	 * 所有险别 目前为车、财、人之合集之并集
	 * @return
	 * @throws Exception
	 */
	public List<String> getKindCodeForAll(){
		return getKindCodeForAll(null);
	}
	/**
	 * 限额取值类型（计次）
	 * @param riskCode
	 * @return
	 * @throws Exception
	 */
	public Map<String,Integer> getLimitForMeterType(){
		return getLimitForMeterType(null);
	}
	/**
	 * 限额取值类型（保險期間累計）
	 * @param riskCode
	 * @return
	 * @throws Exception
	 */
	public List<String> getLimitForCumulativeType(){
		return getLimitForCumulativeType(null);
	}
	/**
	 * 限额取值类型 (每一人/每次事故)
	 * 此项若增加需严格校验prpCitemKind的model值
	 * @param riskCode
	 * @return
	 * @throws Exception
	 */
	public List<String> getLimitForPerPersonType(){
		return getLimitForPerPersonType(null);
	}
	/**
	 * 保额依赖其主险的
	 * @param riskCode
	 * @return
	 * @throws Exception
	 */
	public  Map<String,String[]> getAmountReferMainKind(){
		return getAmountReferMainKind(null);
	}
	/**
	 * 限额依赖其他险别的，需要在所有计算完成后进行处理
	 * KEY:险别；value：赔付时限额依赖的险别
	 * @param riskCode
	 * @return
	 * @throws Exception
	 */
	public  Map<String,String[]> getLimitReferOtherKind(){
		return getLimitReferOtherKind(null);
	}
	/**
	 * 不限额控制的险别
	 * @param riskCode
	 * @return
	 * @throws Exception
	 */
	public  List<String> getKindCodeForNoLimit(){
		return getKindCodeForNoLimit(null);
	}
	/**
	 * 根据险别和限额类型，查询配置
	 * @param riskCode
	 * @param kindCode
	 * @param limitType
	 * @return
	 * @throws Exception
	 */
	public List<PrpLgroovyKind> findLimitGroovyKind(String riskCode,String kindCode,String limitType)throws Exception{
		String key = cacheManager.generateCacheKey("findLimitGroovyKind",riskCode,kindCode,limitType);
		List<PrpLgroovyKind> values = (List<PrpLgroovyKind>)cacheManager.getCache(key);
		if(values!=null){
			return values;
		}
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("validStatus", "1");
		if(riskCode!=null&&!"".equals(riskCode)){
			queryRule.addEqual("riskCode", riskCode);
		}
		queryRule.addEqual("kindCode", kindCode);
		queryRule.addLike("limitType", limitType);
		queryRule.addAscOrder("kindCode");
		values = this.find(queryRule);
		cacheManager.putCache(key,values);
		return values;
	}
	/**
	 * 获取险别的限额
	 * @param policyDto
	 * @param kindCode
	 * @param limitType
	 * @param data
	 * @return
	 */
	public Double getKindAmount(PolicyDto policyDto,String kindCode,String limitType,Map<String,Object> data){
		Double amount = null;
		try {
			List<PrpLgroovyKind> prpLgroovyKindList = this.findLimitGroovyKind(policyDto.getPrpCmain().getRiskCode(),kindCode,limitType);
			if(prpLgroovyKindList.size()>0){
				PrpLgroovyKind prpLgroovyKind = prpLgroovyKindList.get(0);
				String kindAmount = prpLgroovyKind.getKindAmount();
				String sqlAmount = prpLgroovyKind.getSqlAmount();
				if(!CommonUtils.isEmpty(kindAmount)){
					for(PrpCitemKind prpCitemKind : policyDto.getPrpCitemKindList()){
						if(prpCitemKind.getKindCode().equals(kindCode)){
							Object obj = PropertyUtils.getProperty(prpCitemKind, kindAmount);
							amount = DataUtils.getDouble(obj);
							break;
						}
					}
				}else if(!CommonUtils.isEmpty(sqlAmount)){
					if(data==null){
						data = new HashMap<String,Object>();
					}
					data.put("policyNo", policyDto.getPrpCmain().getPolicyNo());
					data.put("riskCode", policyDto.getPrpCmain().getRiskCode());
					data.put("kindCode", kindCode);
					data.put("limitType", limitType);
					for(String key : data.keySet()){
						if(data.get(key)!=null){
							sqlAmount = sqlAmount.replaceAll("\\$\\{"+key+"\\}", String.valueOf(data.get(key)));
						}else{
							sqlAmount = sqlAmount.replaceAll("\\$\\{"+key+"\\}", "");
						}
					}
					List<Object> list = (List<Object>) HibernateUtils.findbySql(super.getSession(), sqlAmount);
					if(list.size()>0){
						Object obj = list.get(0);
						amount = DataUtils.getDouble(obj);
					}
				}
			}
		} catch (Exception e) {
			amount = null;
			e.printStackTrace();
		}
		return amount;
	}
}
