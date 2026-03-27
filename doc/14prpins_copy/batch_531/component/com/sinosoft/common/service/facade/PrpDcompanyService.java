package com.sinosoft.common.service.facade;


import java.util.List;

import com.sinosoft.common.schema.model.PrpDcompany;
import com.sinosoft.prpins.policy.schema.vo.UtiIUserVO;
/**
 * 機構服務
 * @author Sinosoft
 */
public interface PrpDcompanyService {
	/**
	 * 查詢機構對象
	 * @param ComCode 機構代碼
	 * @return PrpDcompany 機構對象
	 * @throws Exception
	 */
	public PrpDcompany findByComCode(String ComCode) throws Exception;
	/**
	 * 校驗用戶允許機構權限
	 * @param powerSQL SQL查詢條件
	 * @param comCode 機構代碼
	 * @return boolean true/false
	 * @throws Exception
	 */
	public boolean findByWhereStrAndComCode(String powerSQL, String comCode) throws Exception;
	/**
	 * 查詢機構對象集合
	 * @param conditions SQL查詢條件
	 * @return List 機構對象集合
	 * @throws Exception
	 */
	public List<PrpDcompany> getDatasByConditions(String conditions) throws Exception;
	/**
	 * 查詢當前機構所屬省的機構代碼
	 * @param comCode 機構代碼
	 * @return String 機構代碼
	 * @throws Exception
	 */
	public String findProvinceComCode(String comCode);
	/**
	 * 查詢機構對象集合
	 * @param parentID 父級標識
	 * @return List 機構對象集合
	 * @throws Exception
	 */
	public List<UtiIUserVO>  getSubSystemListByParentId(String parentID);
	/**
	 * 查詢當前機構的上級機構
	 * @param comCode 機構代碼
	 * @return String 上級機構
	 * @throws Exception
	 */
	public String findProvinceCom(String comCode);
}
