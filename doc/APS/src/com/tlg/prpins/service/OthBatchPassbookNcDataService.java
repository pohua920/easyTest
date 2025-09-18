package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.OthBatchPassbookNcData;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：OTH0131，處理人員：BJ085，需求單編號：OTH0131 保發中心-保單存摺各險寫入中介Table作業 */
public interface OthBatchPassbookNcDataService {

	public Result findOthBatchPassbookNcDataByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findOthBatchPassbookNcDataByParams(Map params) throws SystemException, Exception;

	public Result findOthBatchPassbookNcDataByUK(Map params) throws SystemException, Exception;

	public Result insertOthBatchPassbookNcData(OthBatchPassbookNcData othBatchPassbookNcData) throws SystemException, Exception;

	public Result updateOthBatchPassbookNcData(OthBatchPassbookNcData othBatchPassbookNcData) throws SystemException, Exception;

	public Result removeOthBatchPassbookNcData(OthBatchPassbookNcData othBatchPassbookNcData) throws SystemException, Exception;
	

}
