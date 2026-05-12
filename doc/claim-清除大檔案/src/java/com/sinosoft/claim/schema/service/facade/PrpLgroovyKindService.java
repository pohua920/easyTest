package com.sinosoft.claim.schema.service.facade;
/**
 * 任務定義接口
 * @author 理赔组
 */
import java.util.List;
import java.util.Map;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.common.vo.PolicyDto;
import com.sinosoft.claim.schema.model.PrpLgroovyKind;

public interface PrpLgroovyKindService {
	
	/**
	 * 清空缓存
	 */
	public void clearAllCacheManager(Object...keys);
	
	/**
	 * 险别配置
	 * @param prpLgroovyKind ：险别配置
	 */
	public void save(PrpLgroovyKind prpLgroovyKind) throws Exception;
	
	/**
	 * 保存险别配置
	 * @param list  :险别配置集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLgroovyKind> list) throws Exception;
	
	/**
	 * 删除险别配置
	 * @param policyNo ：险别配置
	 */
	public void delete(String id) throws Exception;

	/**
	 * 更新险别配置
	 * @param PrpLgroovyKind :险别配置
	 */
	public void update(PrpLgroovyKind prpLgroovyKind) throws Exception;

	/**
	 * 根据id查询险别配置
	 * @param policyNo ：险别配置id
	 * @return 返回险别配置
	 */
	public PrpLgroovyKind findPrpLgroovyKind(String id) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的任務定義页面信息
	 */
	public Page findPrpLgroovyKind(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	
	/**据查询对象获取List对象的列表
	 * @param queryRule
	 * @return
	 * @throws Exception
	 */
	public List<PrpLgroovyKind> findPrpLgroovyKind(QueryRule queryRule) throws Exception;
	/**
	 * 查询险总的配置
	 * @param prpLgroovyKind
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public Page findPrpLgroovyKind(PrpLgroovyKind prpLgroovyKind, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据险别和险种判断是否存在
	 * @param prpLgroovyKind
	 * @return
	 * @throws Exception
	 */
	public PrpLgroovyKind isKindExist(PrpLgroovyKind prpLgroovyKind)throws Exception;
	/**
	 * 根据主键修改内容
	 * @param prpLgroovyKind
	 * @throws Exception
	 */
	public void updateKind(PrpLgroovyKind prpLgroovyKind)throws Exception;
	/**
	 * 查询赔付险别,多个使用，号分隔
	 * @param compensateTypes
	 * @param riskCode
	 * @return
	 * @throws Exception
	 */
	public List<String> findCompensateType(String compensateTypes,String riskCode)throws Exception;
	/**
	 * 查询限额控制险别
	 * @param compensateTypes
	 * @param riskCode
	 * @return
	 * @throws Exception
	 */
	public List<PrpLgroovyKind> findLimitType(String limitType,String riskCode)throws Exception;
	/**
	 * 查询赔付主车险别
	 * @return
	 * @throws Exception
	 */
	public List<String> getMainCarLoss(String riskCode);
	/**
	 * 查询赔付被保險人/駕駛人
	 * @return
	 * @throws Exception
	 */
	public List<String> getInsAnddriver(String riskCode);
	/**
	 * 查询赔付主车人 伤
	 * @return
	 * @throws Exception
	 */
	public List<String> getMainPersonLoss(String riskCode);
	/**
	 * 查询赔付主车财损
	 * @return
	 * @throws Exception
	 */
	public List<String> getMainPropLoss(String riskCode);
	/**
	 * 查询赔付三者車
	 * @return
	 * @throws Exception
	 */
	public List<String> getThirdCarLoss(String riskCode);
	/**
	 * 查询赔付三者物 
	 * @return
	 * @throws Exception
	 */
	public List<String> getThirdPropLoss(String riskCode);
	/**
	 * 查询赔付三者人
	 * @return
	 * @throws Exception
	 */
	public List<String> getThirdPersonLoss(String riskCode);
	/**
	 * 查询赔付可赔人伤部分InsAnddriver、MainPersonLoss、ThirdPersonLoss之并集
	 * @return
	 * @throws Exception
	 */
	public List<String> getKindCodeForPerson(String riskCode);
	/**
	 * 可赔财损部分 MainPropLoss、ThirdPropLoss 之并集
	 * @return
	 * @throws Exception
	 */
	public List<String> getKindCodeForProp(String riskCode);
	/**
	 * 可赔车损部分 MainCarLoss、ThirdCarLoss 之并集
	 * @return
	 * @throws Exception
	 */
	public List<String> getKindCodeForCar(String riskCode);
	/**
	 * 所有险别 目前为车、财、人之合集之并集
	 * @return
	 * @throws Exception
	 */
	public List<String> getKindCodeForAll(String riskCode);
	/**
	 * 限额取值类型（计次）
	 * @param riskCode
	 * @return
	 * @throws Exception
	 */
	public Map<String,Integer> getLimitForMeterType(String riskCode);
	/**
	 * 限额取值类型（保險期間累計）
	 * @param riskCode
	 * @return
	 * @throws Exception
	 */
	public List<String> getLimitForCumulativeType(String riskCode);
	/**
	 * 限额取值类型 (每一人/每次事故)
	 * 此项若增加需严格校验prpCitemKind的model值
	 * @param riskCode
	 * @return
	 * @throws Exception
	 */
	public List<String> getLimitForPerPersonType(String riskCode);
	
	/**
	 * 保额依赖其主险的
	 * @param riskCode
	 * @return
	 * @throws Exception
	 */
	public  Map<String,String[]> getAmountReferMainKind(String riskCode);
	/**
	 * 限额依赖其他险别的，需要在所有计算完成后进行处理
	 * KEY:险别；value：赔付时限额依赖的险别
	 * @param riskCode
	 * @return
	 * @throws Exception
	 */
	public  Map<String,String[]> getLimitReferOtherKind(String riskCode);
	/**
	 * 不限额控制的险别
	 * @param riskCode
	 * @return
	 * @throws Exception
	 */
	public  List<String> getKindCodeForNoLimit(String riskCode);
	/**
	 * 查询赔付主车险别
	 * @return
	 * @throws Exception
	 */
	public List<String> getMainCarLoss();
	/**
	 * 查询赔付被保險人/駕駛人
	 * @return
	 * @throws Exception
	 */
	public List<String> getInsAnddriver();
	/**
	 * 查询赔付主车人 伤
	 * @return
	 * @throws Exception
	 */
	public List<String> getMainPersonLoss();
	/**
	 * 查询赔付主车财损
	 * @return
	 * @throws Exception
	 */
	public List<String> getMainPropLoss();
	/**
	 * 查询赔付三者車
	 * @return
	 * @throws Exception
	 */
	public List<String> getThirdCarLoss();
	/**
	 * 查询赔付三者物 
	 * @return
	 * @throws Exception
	 */
	public List<String> getThirdPropLoss();
	/**
	 * 查询赔付三者人
	 * @return
	 * @throws Exception
	 */
	public List<String> getThirdPersonLoss();
	/**
	 * 查询赔付可赔人伤部分InsAnddriver、MainPersonLoss、ThirdPersonLoss之并集
	 * @return
	 * @throws Exception
	 */
	public List<String> getKindCodeForPerson();
	/**
	 * 可赔财损部分 MainPropLoss、ThirdPropLoss 之并集
	 * @return
	 * @throws Exception
	 */
	public List<String> getKindCodeForProp();
	/**
	 * 可赔车损部分 MainCarLoss、ThirdCarLoss 之并集
	 * @return
	 * @throws Exception
	 */
	public List<String> getKindCodeForCar();
	/**
	 * 所有险别 目前为车、财、人之合集之并集
	 * @return
	 * @throws Exception
	 */
	public List<String> getKindCodeForAll();
	/**
	 * 限额取值类型（计次）
	 * @param riskCode
	 * @return
	 * @throws Exception
	 */
	public Map<String,Integer> getLimitForMeterType();
	/**
	 * 限额取值类型（保險期間累計）
	 * @param riskCode
	 * @return
	 * @throws Exception
	 */
	public List<String> getLimitForCumulativeType();
	/**
	 * 限额取值类型 (每一人/每次事故)
	 * 此项若增加需严格校验prpCitemKind的model值
	 * @param riskCode
	 * @return
	 * @throws Exception
	 */
	public List<String> getLimitForPerPersonType();
	
	/**
	 * 保额依赖其主险的
	 * @param riskCode
	 * @return
	 * @throws Exception
	 */
	public  Map<String,String[]> getAmountReferMainKind();
	/**
	 * 限额依赖其他险别的，需要在所有计算完成后进行处理
	 * KEY:险别；value：赔付时限额依赖的险别
	 * @param riskCode
	 * @return
	 * @throws Exception
	 */
	public  Map<String,String[]> getLimitReferOtherKind();
	/**
	 * 不限额控制的险别
	 * @param riskCode
	 * @return
	 * @throws Exception
	 */
	public  List<String> getKindCodeForNoLimit();
	/**
	 * 根据险别和限额类型，查询配置
	 * @param riskCode
	 * @param kindCode
	 * @param limitType
	 * @return
	 * @throws Exception
	 */
	public List<PrpLgroovyKind> findLimitGroovyKind(String riskCode,String kindCode,String limitType)throws Exception;
	/**
	 * 获取险别的限额
	 * @param policyDto
	 * @param kindCode
	 * @param limitType
	 * @param data
	 * @return
	 */
	public Double getKindAmount(PolicyDto policyDto,String kindCode,String limitType,Map<String,Object> data);
}
