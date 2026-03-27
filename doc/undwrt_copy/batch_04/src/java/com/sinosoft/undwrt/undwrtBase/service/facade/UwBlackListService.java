package com.sinosoft.undwrt.undwrtBase.service.facade;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.common.schema.model.PrpDBankInfo;
import com.sinosoft.sysframework.common.datatype.PageRecord;
import com.sinosoft.undwrt.undwrtBase.model.UwBlackList;

/**
 * 核保黑名單接口類
 * 
 * @author sinoSoft
 * 
 * @created 2013-12-29
 */
public interface UwBlackListService {

	
	/**
	 * 根據條件查詢核保黑名單列表.
	 * 
	 * @param queryRule
	 *            查詢條件
	 * @return 黑名單列表
	 */
	public List<UwBlackList> getUwBlackList(QueryRule queryRule);

	public void saveBlackMaintenance(UwBlackList uwBlackList);

	public String getStatement(String identifyNumber, String riskCode,boolean nodeStatusView);

	public Page findByStatement(String statement, int pageNo, int rowsPerPage,boolean nodeStatusView);

	public PageRecord findByStatementPageRecord(String statement, int pageNo,int rowsPerPage, boolean nodeStatusView);

	public void updateBlackList(UwBlackList uwBlackList);

	public void deleteBlackList(List<UwBlackList> list);

	public PrpDBankInfo queryBankInfo(String bankCode);
}
