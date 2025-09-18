package com.tlg.xchg.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.xchg.entity.AssocAnnAssuw;

public interface AssocAnnAssuwService {

	public int countAssocAnnAssuw(Map params) throws SystemException, Exception;

	public Result findAssocAnnAssuwByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findAssocAnnAssuwByParams(Map params) throws SystemException, Exception;

	public Result findAssocAnnAssuwByOid(BigDecimal oid) throws SystemException, Exception;

	public Result updateAssocAnnAssuw(AssocAnnAssuw assocAnnAssuw) throws SystemException, Exception;

	public Result insertAssocAnnAssuw(AssocAnnAssuw assocAnnAssuw) throws SystemException, Exception;

	public Result removeAssocAnnAssuw(BigDecimal oid) throws SystemException, Exception;
	
	public Result findUnsendAssocAnnAssuwData() throws SystemException, Exception;

}
