package com.sinosoft.claim.schema.service.facade;

import java.util.List;

import com.sinosoft.claim.common.vo.ExceptDeductibleRateDto;
import com.sinosoft.claim.schema.model.PrpDdeductCond;
import com.sinosoft.claim.schema.model.PrpLdeductCond;
/**
 * 免赔条件表的数据访问接口
 * @Description
 * @author 中科软
 */
public interface PrpDdeductCondService {

	/**
	 * 插入一条数据
	 * @param prpDdeductCondDto prpDdeductCondDto
	 * @throws Exception
	 */
	public void save(PrpDdeductCond prpDdeductCond) throws Exception;

	/**
	 * 采用批方式插入多条数据
	 * @param collection collection
	 * @throws Exception
	 */
	public void save(List<PrpDdeductCond> list) throws Exception;

	/**
	 * 按主键删除一条数据
	 * @param riskCode 险种
	 * @param clauseType 条款类别
	 * @param kindCode 险别代码
	 * @param deductCondCode 免赔条件代码
	 * @param dEDUCTPERIOD 期数
	 * @throws Exception
	 */
	public void delete(String riskCode, String clauseType, String kindCode, String deductCondCode, String deductPeriod) throws Exception;

	/**
	 * 按主键更新一条数据(主键本身无法变更)
	 * @param PrpDdeductCond PrpDdeductCond
	 * @throws Exception
	 */
	public void update(PrpDdeductCond prpDdeductCond) throws Exception;

	/**
	 * 按主键查找一条数据
	 * @param riskCode 险种
	 * @param clauseType 条款类别
	 * @param kindCode 险别代码
	 * @param deductCondCode 免赔条件代码
	 * @param dEDUCTPERIOD 期数
	 * @return PrpDdeductCond
	 * @throws Exception
	 */
	public PrpDdeductCond findPrpDdeductCond(String riskCode, String clauseType, String kindCode, String deductCondCode, String dEDUCTPERIOD) throws Exception;
	/**
	 * 按条件查询多条数据
	 * @param conditions 查询条件
	 * @return Collection
	 * @throws Exception
	 */
	public List<PrpDdeductCond> findPrpDdeductCond(String conditions) throws Exception;

	/**
	 * 按条件删除数据
	 * @param conditions 查询条件
	 * @return 删除的行数
	 * @throws Exception
	 */
	public void deleteByConditions(String conditions) throws Exception;
	
	/**
	 * 查找绝对免赔率
	 * @Description: 
	 * @author 中科软
	 * @date Mar 4, 2013 6:14:19 PM
	 * @param clauseType
	 * @param kindCode
	 * @param prpDdeductCondList
	 * @param riskCode
	 * @param validDate
	 * @return
	 * @throws Exception
	 */
	public ExceptDeductibleRateDto findDeductibleRateOfAbsolute(String clauseType, String kindCode, List<PrpLdeductCond> prpLdeductCondList, 
            String riskCode, String validDate) throws Exception;
	
}
