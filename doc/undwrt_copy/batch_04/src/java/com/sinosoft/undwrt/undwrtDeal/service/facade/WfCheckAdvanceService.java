package com.sinosoft.undwrt.undwrtDeal.service.facade;

import ins.framework.common.QueryRule;

import com.sinosoft.sysframework.exceptionlog.UserException;

// TODO: Auto-generated Javadoc
/**
 * 權限校驗接口類.
 */
public interface WfCheckAdvanceService {

	/**
	 * 權限校驗.
	 * 
	 * @param ModelNo
	 *            模板號
	 * @param StartNodeNo
	 *            開始節點號
	 * @param BusinessType
	 *            業務類型
	 * @param BusinessNo
	 *            業務號
	 * @param DefaultFlag
	 *            默認標誌位
	 * @return 有權限返回true，沒有權限返回false
	 * @throws Exception
	 *             異常
	 */
	public boolean checkAdvanceCondition(int ModelNo, int StartNodeNo,
			String BusinessType, String BusinessNo, String DefaultFlag)
			throws Exception;

	/**
	 * 取得以某節點爲起始節點的所有滿足條件且優先級最高的路徑.
	 * 
	 * @param modelNo
	 *            模板號
	 * @param startNodeNo
	 *            起始節點號
	 * @param certiType
	 *            業務類型
	 * @param businessNo
	 *            業務號
	 * @param defaultFlag
	 *            默認標誌位
	 * @return 成功返回true，失敗返回false
	 * @throws UserException
	 *             自定義異常
	 * @throws Exception
	 *             異常
	 */
	public boolean getAdvancePathes(int modelNo, int startNodeNo,
			String certiType, String businessNo, String defaultFlag)
			throws UserException, Exception;
}
