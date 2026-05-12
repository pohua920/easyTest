package com.sinosoft.claim.schema.service.facade;

/**
 * 核保核赔因子设置表接口
 * @author 中科软
 */
import ins.framework.common.Page;

import java.util.List;

import com.sinosoft.claim.schema.model.UtiUwCondition;
import com.sinosoft.claim.schema.model.UtiUwFactor;

public interface UtiUwConditionService {
	/**
	 * 删除核保核赔因子设置信息
	 * @param conditionDto ：传入的核保核赔因子设置信息
	 * @param actionType ：传入的操作类型
	 */
	public void delete(UtiUwCondition conditionDto, String actionType) throws Exception;

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param conditions 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的核保核赔因子设置页面信息
	 */
	public Page findOverviewByConditions(String conditions, int pageNo, int rowsPerPage) throws Exception;

	/**
	 * @param conditionDto 传入的核保核赔因子设置信息
	 */
	public void prepareInsertValidate(UtiUwCondition conditionDto) throws Exception;

	/**
	 * 更新核保核赔因子设置信息
	 * @param prpLthirdCarLoss :传入需要更新的核保核赔因子设置
	 * @param oldRiskCode：oldRiskCode
	 */
	public void update(UtiUwCondition conditionDto, String oldRiskCode) throws Exception;

	/**
	 * @param statement :查询语句
	 * @return ：符合条件的数量
	 */
	public long getCount(String statement) throws Exception;

	/**
	 * 根据查询对象获取核保核赔因子设置信息 的集合
	 * @param statement 查询对象
	 * @return 包含的 核保核赔因子设置信息的集合
	 */
	public List<UtiUwCondition> findByConditions(String statement) throws Exception;

	/**
	 * 根据参数，查询是否有满足条件的核保核赔因子设置信息
	 */
	public boolean findCountByConditions(String paramString1, int paramInt1, int paramInt2, String paramString2, String paramString3) throws Exception;

	/**
	 * 根据查询对象获取核保核赔因子设置信息 的集合
	 * @param queryRule 查询对象
	 * @return 包含的 核保核赔因子设置信息的集合
	 */
	public List<UtiUwFactor> getSimpleFactors(UtiUwCondition conditionDto, int flag) throws Exception;

	/**
	 * 根据查询对象获取核保核赔因子设置信息 的集合
	 * @param queryRule 查询对象
	 * @return 包含的 核保核赔因子设置信息的集合
	 */
	public List<?> getEnumFactors(UtiUwCondition conditionDto, int flag) throws Exception;

	/**
	 * 根据查询对象获取核保核赔因子设置信息 的集合
	 * @param queryRule 查询对象
	 * @return 包含的 核保核赔因子设置信息的集合
	 */
	public List<?> getComboFactors(UtiUwCondition conditionDto, int flag) throws Exception;

	/**
	 * 根据查询对象获取核保核赔因子设置信息 的集合
	 * @param queryRule 查询对象
	 * @return 包含的 核保核赔因子设置信息的集合
	 */
	// public List<UtiUwFactor> getUtiUwLevel(UtiUwCondition condition)
	// throws Exception;

	/**
	 *更新核保核赔因子设置信息
	 */
	public void updateUtiUwCondition(UtiUwCondition condition, String[] simpleFactorCode, String[] simpleFactorValue, String[] enFactorCode, String[] enCheckbox, String[] comboFactorCode, String[] comboFactorCols, String[] comboCodeType,
			String[] comboCodeCode, String[] comboFactorValue, String[] comboFactorDefaultValue, int flag, String actionType) throws Exception;

	/**
	 * 主键查询
	 * @param comCode
	 * @param modelNo
	 * @param nodeNo
	 * @param riskCode
	 * @param uwType
	 * @param factorValue
	 * @param paramInt3
	 * @return
	 * @throws Exception
	 */
	public UtiUwCondition findByPrimaryKey(String comCode, int modelNo, int nodeNo, String riskCode, String uwType, String factorValue, int paramInt3) throws Exception;
}
