package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.vo.Aps017EditVo;
import com.tlg.aps.vo.Aps017ResultVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirAgtSalesMappingDao;
import com.tlg.prpins.entity.FirAgtSalesMapping;
import com.tlg.prpins.service.FirAgtSalesMappingService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirAgtSalesMappingServiceImpl implements FirAgtSalesMappingService{
	/* mantis：FIR0294，處理人員：BJ085，需求單編號：FIR0294 外銀板信續件移回新核心-資料接收排程 start */
	private FirAgtSalesMappingDao firAgtSalesMappingDao;

	@Override
	public Result findFirAgtSalesMappingByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firAgtSalesMappingDao.count(pageInfo.getFilter());
		if (rowCount == 0) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();
		
		List<FirAgtSalesMapping> searchResult = firAgtSalesMappingDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirAgtSalesMappingByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirAgtSalesMapping> searchResult = firAgtSalesMappingDao.findByParams(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public int countFirAgtSalesMapping(Map params) throws SystemException, Exception {
		return firAgtSalesMappingDao.count(params);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirAgtSalesMappingByBranchNo(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		FirAgtSalesMapping firAgtSalesMapping = firAgtSalesMappingDao.selectByBranchNo(params);
		if (null == firAgtSalesMapping) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(firAgtSalesMapping);
		}
		return result;
	}

	@Override
	public Result insertFirAgtSalesMapping(FirAgtSalesMapping firAgtSalesMapping) throws SystemException, Exception {

		if (firAgtSalesMapping == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		BigDecimal oid = firAgtSalesMappingDao.insert(firAgtSalesMapping);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}else {
			firAgtSalesMapping.setOid(oid);
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firAgtSalesMapping);
		return result;
	}
	
	@Override
	public Result updateFirAgtSalesMapping(FirAgtSalesMapping firAgtSalesMapping) throws SystemException, Exception {

		if (firAgtSalesMapping == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firAgtSalesMappingDao.update(firAgtSalesMapping);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firAgtSalesMapping);
		return result;
	}

	/* mantis：FIR0310，處理人員：BJ016，需求單編號：FIR0310 住火保經代分行服務人員對照表 START */
	@SuppressWarnings("unchecked")
	@Override
	public Result findFirAgtSalesMappingForAps017ByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firAgtSalesMappingDao.countForAps017(pageInfo.getFilter());
		if (rowCount == 0) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<Aps017ResultVo> searchResult = firAgtSalesMappingDao.selectForAps017(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result selectByOidForAps017(BigDecimal oid) throws SystemException, Exception {
		Result result = new Result();
		if (null == oid) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		Aps017EditVo searchResult = firAgtSalesMappingDao.selectByOidForAps017(oid);
		if (null == searchResult) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result findFirAgtSalesMappingByOid(BigDecimal oid) throws SystemException, Exception {
		Result result = new Result();
		if (null == oid) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		FirAgtSalesMapping searchResult = firAgtSalesMappingDao.findByOid(oid);
		if (null == searchResult) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	/* mantis：FIR0310，處理人員：BJ016，需求單編號：FIR0310 住火保經代分行服務人員對照表 END */

	public FirAgtSalesMappingDao getFirAgtSalesMappingDao() {
		return firAgtSalesMappingDao;
	}

	public void setFirAgtSalesMappingDao(FirAgtSalesMappingDao firAgtSalesMappingDao) {
		this.firAgtSalesMappingDao = firAgtSalesMappingDao;
	}
}
