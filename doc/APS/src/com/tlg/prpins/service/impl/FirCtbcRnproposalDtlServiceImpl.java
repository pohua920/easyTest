package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.vo.Aps035DetailVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirCtbcRnproposalDtlDao;
import com.tlg.prpins.entity.FirCtbcRnproposalDtl;
import com.tlg.prpins.service.FirCtbcRnproposalDtlService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：FIR0459，處理人員：CC009，需求單編號：FIR0459 住火-APS中信續件要保書-排程查詢作業 
	mantis：FIR0458，處理人員：CC009，需求單編號：FIR0458 住火-APS中信續件要保書-資料接收排程 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirCtbcRnproposalDtlServiceImpl implements FirCtbcRnproposalDtlService{
	private FirCtbcRnproposalDtlDao firCtbcRnproposalDtlDao;
	
	@Override
	@SuppressWarnings("unchecked")
	public Result findFirCtbcRnproposalDtlByPageInfoJoinPc(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firCtbcRnproposalDtlDao.countJoinPc(pageInfo.getFilter());
		if (rowCount == 0) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<Aps035DetailVo> searchResult = firCtbcRnproposalDtlDao.selectJoinPc(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result insertFirCtbcRnproposalDtl(FirCtbcRnproposalDtl firCtbcRnproposalDtl)
			throws SystemException, Exception {
		if (firCtbcRnproposalDtl == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		BigDecimal oid = firCtbcRnproposalDtlDao.insert(firCtbcRnproposalDtl);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}else {
			firCtbcRnproposalDtl.setOid(oid);
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firCtbcRnproposalDtl);
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirCtbcRnproposalDtlByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirCtbcRnproposalDtl> searchResult = firCtbcRnproposalDtlDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result updateFirCtbcRnproposalDtl(FirCtbcRnproposalDtl firCtbcRnproposalDtl)
			throws SystemException, Exception {
		if (firCtbcRnproposalDtl == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firCtbcRnproposalDtlDao.update(firCtbcRnproposalDtl);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firCtbcRnproposalDtl);
		return result;
	}

	public FirCtbcRnproposalDtlDao getFirCtbcRnproposalDtlDao() {
		return firCtbcRnproposalDtlDao;
	}

	public void setFirCtbcRnproposalDtlDao(FirCtbcRnproposalDtlDao firCtbcRnproposalDtlDao) {
		this.firCtbcRnproposalDtlDao = firCtbcRnproposalDtlDao;
	}
	
}
