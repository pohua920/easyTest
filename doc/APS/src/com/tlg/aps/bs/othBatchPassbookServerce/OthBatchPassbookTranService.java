package com.tlg.aps.bs.othBatchPassbookServerce;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.OthBatchPassbookKind;
import com.tlg.prpins.entity.OthBatchPassbookList;
import com.tlg.util.Result;

public interface OthBatchPassbookTranService {

	public Result insertOthBatchPassbookList(OthBatchPassbookList othBatchPassbookList) throws SystemException, Exception;
	
	public Result insertOthBatchPassbookKind(OthBatchPassbookKind othBatchPassbookKind) throws SystemException, Exception;
}
