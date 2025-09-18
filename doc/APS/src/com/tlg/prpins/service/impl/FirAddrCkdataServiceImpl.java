package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.vo.Aps024ExportVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirAddrCkdataDao;
import com.tlg.prpins.entity.FirAddrCkdata;
import com.tlg.prpins.service.FirAddrCkdataService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirAddrCkdataServiceImpl implements FirAddrCkdataService{
	/* mantis：FIR0183，處理人員：BJ085，需求單編號：FIR0183 火險地址資料匯入 start */

	private FirAddrCkdataDao firAddrCkdataDao;

	@Override
	public int countFirAddrCkdata(Map params) throws SystemException, Exception {
		return firAddrCkdataDao.count(params);
	}
	
	@Override
	public Result findFirAddrCkdataByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firAddrCkdataDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<FirAddrCkdata> searchResult = firAddrCkdataDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Result findFirAddrCkdataByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirAddrCkdata> searchResult = firAddrCkdataDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findFirAddrCkdataByOid(BigDecimal oid) throws SystemException,Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		FirAddrCkdata persisted = firAddrCkdataDao.findByOid(oid);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateFirAddrCkdata(FirAddrCkdata firAddrCkdata) throws SystemException, Exception {

		if (firAddrCkdata == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firAddrCkdataDao.update(firAddrCkdata);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firAddrCkdata);
		return result;
	}

	@Override
	public Result insertFirAddrCkdata(FirAddrCkdata firAddrCkdata) throws SystemException, Exception {

		if (firAddrCkdata == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		BigDecimal oid = firAddrCkdataDao.insert(firAddrCkdata);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		firAddrCkdata.setOid(oid);
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firAddrCkdata);
		return result;
	}

	@Override
	public Result removeFirAddrCkdata(BigDecimal oid) throws SystemException, Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		FirAddrCkdata persisted = firAddrCkdataDao.findByOid(oid);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firAddrCkdataDao.remove(oid);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}

	@Override
	public void truncate() throws SystemException, Exception {
		firAddrCkdataDao.truncate();
	}
	
	/* mantis：FIR0521，處理人員：CC009，需求單編號：FIR0521 火險地址維護作業增加處理正規化功能 start */
	@Override
	public Result findAps024ExportData(Map params) throws SystemException, Exception {
		Result result = new Result();
		List<Aps024ExportVo> searchResult = firAddrCkdataDao.aps024Export(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public int findAps024ExportDataCount(Map params) throws SystemException, Exception {
		return firAddrCkdataDao.aps024ExportCount(params);
	}
	/* mantis：FIR0521，處理人員：CC009，需求單編號：FIR0521 火險地址維護作業增加處理正規化功能 end */

	public FirAddrCkdataDao getFirAddrCkdataDao() {
		return firAddrCkdataDao;
	}

	public void setFirAddrCkdataDao(FirAddrCkdataDao firAddrCkdataDao) {
		this.firAddrCkdataDao = firAddrCkdataDao;
	}

}
