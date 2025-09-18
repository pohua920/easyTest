package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.PbCklistGroup;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface PbCklistGroupService {

	public int countPbCklistGroup(Map params) throws SystemException, Exception;

	public Result findPbCklistGroupByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findPbCklistGroupByParams(Map params) throws SystemException, Exception;

	public Result findPbCklistGroupByOid(BigDecimal oid) throws SystemException, Exception;

	public Result updatePbCklistGroup(PbCklistGroup pbCklistGroup) throws SystemException, Exception;

	public Result insertPbCklistGroup(PbCklistGroup pbCklistGroup) throws SystemException, Exception;

	public Result removePbCklistGroup(BigDecimal oid) throws SystemException, Exception;
	

}
