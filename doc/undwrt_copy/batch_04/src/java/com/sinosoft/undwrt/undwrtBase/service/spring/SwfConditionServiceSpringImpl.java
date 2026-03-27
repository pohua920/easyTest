package com.sinosoft.undwrt.undwrtBase.service.spring;

import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.sinosoft.sysframework.common.datatype.PageRecord;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.undwrt.common.model.PrpDcompany;
import com.sinosoft.undwrt.common.service.facade.PrpDcompanyService;
import com.sinosoft.undwrt.common.util.ConfigAction;
import com.sinosoft.undwrt.undwrtBase.model.SwfCondition;
import com.sinosoft.undwrt.undwrtBase.model.SwfConditionId;
import com.sinosoft.undwrt.undwrtBase.model.SwfModelUse;
import com.sinosoft.undwrt.undwrtBase.service.facade.SwfConditionService;

/**
 * 工作流條件描述實現類
 */
public class SwfConditionServiceSpringImpl extends
		GenericDaoHibernate<SwfCondition, SwfConditionId> implements
		SwfConditionService {

	/** 屬性核保規則接口類. */
	private ConfigAction configAction;

	/**
	 * 獲取屬性核保規則接口類.
	 * 
	 * @return 屬性核保規則接口類的值
	 */
	public ConfigAction getConfigAction() {
		return configAction;
	}

	/**
	 * 設置屬性核保規則接口類.
	 * 
	 * @param configAction
	 *            待設置的核保規則接口類的值
	 */
	public void setConfigAction(ConfigAction configAction) {
		this.configAction = configAction;
	}

	/**
	 * 插入一條記錄.
	 * 
	 * @param swfCondition
	 *            工作流條件描述類
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.SwfConditionService#insert(com.sinosoft.undwrt.undwrtBase.model.SwfCondition)
	 */
	@Override
	public void insert(SwfCondition swfCondition) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * 按主鍵刪除一條數據.
	 * 
	 * @param modelNo
	 *            模板號
	 * @param pathNo
	 *            路徑號
	 * @param conditionNo
	 *            條件編號
	 * @param serialNo
	 *            序號
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.SwfConditionService#delete(int,
	 *      int, int, int)
	 */
	@Override
	public void delete(int modelNo, int pathNo, int conditionNo, int serialNo)
			throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * 按條件刪除數據.
	 * 
	 * @param conditions
	 *            刪除條件
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.SwfConditionService#deleteByConditions(java.lang.String)
	 */
	@Override
	public void deleteByConditions(String conditions) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * 按主鍵更新一條數據(主鍵本身無法變更).
	 * 
	 * @param swfCondition
	 *            工作流條件類
	 * @throws Exception
	 *             異常
	 * @see ins.framework.dao.GenericDaoHibernate#update(java.io.Serializable)
	 */
	@Override
	public void update(SwfCondition swfCondition) {
		// TODO Auto-generated method stub

	}

	/**
	 * 按主鍵查找一條數據..
	 * 
	 * @param modelNo
	 *            模板號
	 * @param pathNo
	 *            路徑號
	 * @param conditionNo
	 *            條件編號
	 * @param serialNo
	 *            序號
	 * @return sWfCondition 工作流條件類
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.SwfConditionService#findByPrimaryKey(int,
	 *      int, int, int)
	 */
	@Override
	public SwfCondition findByPrimaryKey(int modelNo, int pathNo,
			int conditionNo, int serialNo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 按條件查詢多條數據.
	 * 
	 * @param conditions
	 *            查詢條件
	 * @param pageNo
	 *            頁碼
	 * @param rowsPerPage
	 *            每頁的行數
	 * @return PageRecord 查詢的一頁的結果
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.SwfConditionService#findByConditions(java.lang.String,
	 *      int, int)
	 */
	@Override
	public PageRecord findByConditions(String conditions, int pageNo,
			int rowsPerPage) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 按條件查詢多條數據.
	 * 
	 * @param queryRule
	 *            查詢條件
	 * @return Collection 符合條件的集合
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.SwfConditionService#findByConditions(ins.framework.common.QueryRule)
	 */
	@Override
	public List<SwfCondition> findByConditions(QueryRule queryRule)
			throws Exception {
		// TODO Auto-generated method stub
		return super.find(queryRule);
	}

	/**
	 * 查詢滿足模糊查詢條件的記錄數.
	 * 
	 * @param queryRule
	 *            查詢條件
	 * @return 满足模糊查询条件的记录数
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.SwfConditionService#getCount(ins.framework.common.QueryRule)
	 */
	@Override
	public int getCount(QueryRule queryRule) throws Exception {
		// TODO Auto-generated method stub
		return super.find(queryRule).size();
	}

	/**
	 * 執行工作流系統發出的sql語句(針對簡單描述和SQL描述).
	 * 
	 * @param businessNo
	 *            業務號碼
	 * @param comcode
	 *            機構代碼
	 * @param modelno
	 *            模板號
	 * @param nodeNo
	 *            節點號
	 * @param swfCondition
	 *            工作流條件類
	 * @return boolean 成功返回true，失敗返回false
	 * @throws UserException
	 *             用戶自定義異常
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.SwfConditionService#execute(java.lang.String,
	 *      java.lang.String, int, int,
	 *      com.sinosoft.undwrt.undwrtBase.model.SwfCondition)
	 */
	@Override
	public boolean execute(String businessNo, String comcode, int modelno,
			int nodeNo, SwfCondition swfCondition) throws UserException,
			Exception {
		String strWhere = "";
		String strConfig = "";
		boolean blnResult = false;
		try {
			if (swfCondition.getConfigType().equals("0")
					|| swfCondition.getConfigType().equals("1")) {
				strWhere = swfCondition.getBusinessKey().trim() + "='"
						+ businessNo.trim() + "' AND "
						+ swfCondition.getConfigText().trim();
				// System.out.println("ConfigText>>>>>>"+sWfConditionDto.getConfigText());
				strConfig = "SELECT COUNT(*) FROM "
						+ swfCondition.getTableName().trim() + " WHERE "
						+ strWhere.trim();
				// System.out.println("----------Condition execute SQL-----------\n"
				// +strConfig);
				blnResult = configAction.executeSql(businessNo, strConfig);
			}
			if (swfCondition.getConfigType().equals("2")) {
				strConfig = swfCondition.getConfigText().trim();
				blnResult = configAction.executeFunc(businessNo, comcode,
						modelno, nodeNo, strConfig);
			}
		} catch (Exception e) {
			throw e;
		}
		return blnResult;
	}

	/** 屬性機構接口. */
	private PrpDcompanyService prpDcompanyService;

	/**
	 * 獲得模板號.
	 * 
	 * @param modelType
	 *            模版類型
	 * @param riskCode
	 *            險種代碼
	 * @param comCode
	 *            機構代碼
	 * @return 模板號
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.SwfConditionService#getModelNo(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public int getModelNo(String modelType, String riskCode, String comCode)
			throws SQLException, Exception {
		boolean blnIfExist = false;
		int modelNo = 0;
		String statementStr = "";
		String strComCodeTemp = comCode;
		int intResult = 0;
		List<SwfModelUse> list = null;
		PrpDcompany prpDcompany = null;

		while (!blnIfExist) {
			statementStr = " SELECT ModelNo FROM SwfModelUse "
					+ " WHERE ModelType = '" + modelType + "'"
					+ " AND RiskCode ='" + riskCode + "'" + " AND ComCode='"
					+ strComCodeTemp + "'" + " AND ModelStatus = '1'";

			QueryRule queryRule = QueryRule.getInstance();
			queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.modelType", modelType);
			queryRule.addEqual("id.riskCode", riskCode);
			queryRule.addEqual("id.comCode", strComCodeTemp);
			queryRule.addEqual("id.modelStatus", "1");
			list = (List) super.find(queryRule);

			if (null != list && list.size() > 0) {
				try {
					modelNo = list.get(0).getId().getModelNo();
					blnIfExist = true;
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				queryRule = QueryRule.getInstance();
				queryRule.addEqual("comCode", strComCodeTemp);
				prpDcompany = prpDcompanyService.findByPrimaryKey(queryRule);
				if (null != prpDcompany) {
					intResult = 0;
				} else {
					intResult = 100;
				}

				// 若是comcode＝uppercomcode则不在循环
				if (intResult == 100
						|| prpDcompany.getComCode().equals(
								prpDcompany.getPrpDcompany().getComCode()))
					blnIfExist = true;
				else
					strComCodeTemp = prpDcompany.getPrpDcompany().getComCode();
			}

		}
		return modelNo;
	}

	/**
	 * 獲取屬性機構接口.
	 * 
	 * @return 屬性機構接口的值
	 */
	public PrpDcompanyService getPrpDcompanyService() {
		return prpDcompanyService;
	}

	/**
	 * 設置屬性機構接口.
	 * 
	 * @param prpDcompanyService
	 *            待設置的機構接口的值
	 */
	public void setPrpDcompanyService(PrpDcompanyService prpDcompanyService) {
		this.prpDcompanyService = prpDcompanyService;
	}

}
