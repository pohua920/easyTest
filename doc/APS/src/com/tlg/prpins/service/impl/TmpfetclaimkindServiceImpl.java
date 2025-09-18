package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.TmpfetclaimkindDao;
import com.tlg.prpins.entity.Tmpfetclaimkind;
import com.tlg.prpins.service.TmpfetclaimkindService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：MOB0018，處理人員：BJ085，需求單編號：MOB0018 理賠資料提交及列印作業 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class TmpfetclaimkindServiceImpl implements TmpfetclaimkindService{

	private TmpfetclaimkindDao tmpfetclaimkindDao;

	@Override
	public int countTmpfetclaimkind(Map params) throws SystemException, Exception {
		return tmpfetclaimkindDao.count(params);
	}

	@Override
	public Result findTmpfetclaimkindByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = tmpfetclaimkindDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<Tmpfetclaimkind> searchResult = tmpfetclaimkindDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findTmpfetclaimkindByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<Tmpfetclaimkind> searchResult = tmpfetclaimkindDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result updateTmpfetclaimkind(Tmpfetclaimkind tmpfetclaimkind) throws SystemException, Exception {
		
		if (tmpfetclaimkind == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		boolean status = tmpfetclaimkindDao.update(tmpfetclaimkind);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(tmpfetclaimkind);
		return result;
	}

	@Override
	public Result insertTmpfetclaimkind(Tmpfetclaimkind tmpfetclaimkind) throws SystemException, Exception {

		if (tmpfetclaimkind == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		BigDecimal oid = tmpfetclaimkindDao.insert(tmpfetclaimkind);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(tmpfetclaimkind);
		return result;
	}

	@Override
	public Result removeTmpfetclaimkind(BigDecimal oid) throws SystemException, Exception {
		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Tmpfetclaimkind persisted = tmpfetclaimkindDao.findByOid(oid);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = tmpfetclaimkindDao.remove(oid);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}
	
	/** mantis：MOB0017，處理人員：BI086，需求單編號：MOB0017 將安達提供中介檔上傳資料庫 */
	@Override
	public Result removeTmpfetclaimkindAll() throws SystemException, Exception {
		boolean status = tmpfetclaimkindDao.removeAll();
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}
	@Override
	public void batchInsertTmpfetclaimkind(List<Tmpfetclaimkind> listTmpfetclaimkind) throws SystemException,
			Exception {
		this.tmpfetclaimkindDao.processInBatch(listTmpfetclaimkind);
	}
	/** mantis：MOB0017，處理人員：BI086，需求單編號：MOB0017 將安達提供中介檔上傳資料庫 end */

	public TmpfetclaimkindDao getTmpfetclaimkindDao() {
		return tmpfetclaimkindDao;
	}

	public void setTmpfetclaimkindDao(TmpfetclaimkindDao tmpfetclaimkindDao) {
		this.tmpfetclaimkindDao = tmpfetclaimkindDao;
	}
}
