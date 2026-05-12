package com.sinosoft.claim.claim.service.facade;

import com.sinosoft.sysframework.common.datatype.PageRecord;

public interface ExcludeClaimService {

	/**
	 * 立案除外历史查询
	 * @param conditions 查询条件
	 * @param intPageNo 起始页
	 * @param intRecordPerPage 每页显示条数
	 * @return 查询结果集
	 * @throws Exception
	 */
	public PageRecord historyQuery(String conditions, int intPageNo,int intRecordPerPage)throws Exception;

}
