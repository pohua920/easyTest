package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.TmpfetclaimpayDao;
import com.tlg.prpins.entity.Tmpfetclaimpay;
import com.tlg.prpins.service.TmpfetclaimpayService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：MOB0018，處理人員：BJ085，需求單編號：MOB0018 理賠資料提交及列印作業 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class TmpfetclaimpayServiceImpl implements TmpfetclaimpayService{

	private TmpfetclaimpayDao tmpfetclaimpayDao;

	@Override
	public int countTmpfetclaimpay(Map params) throws SystemException, Exception {
		return tmpfetclaimpayDao.count(params);
	}

	@Override
	public Result findTmpfetclaimpayByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = tmpfetclaimpayDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<Tmpfetclaimpay> searchResult = tmpfetclaimpayDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findTmpfetclaimpayByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<Tmpfetclaimpay> searchResult = tmpfetclaimpayDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result updateTmpfetclaimpay(Tmpfetclaimpay tmpfetclaimpay) throws SystemException, Exception {
		
		if (tmpfetclaimpay == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		boolean status = tmpfetclaimpayDao.update(tmpfetclaimpay);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(tmpfetclaimpay);
		return result;
	}

	@Override
	public Result insertTmpfetclaimpay(Tmpfetclaimpay tmpfetclaimpay) throws SystemException, Exception {

		if (tmpfetclaimpay == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		BigDecimal oid = tmpfetclaimpayDao.insert(tmpfetclaimpay);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(tmpfetclaimpay);
		return result;
	}

	@Override
	public Result removeTmpfetclaimpay(BigDecimal oid) throws SystemException, Exception {
		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Tmpfetclaimpay persisted = tmpfetclaimpayDao.findByOid(oid);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = tmpfetclaimpayDao.remove(oid);
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
	public Result removeTmpfetclaimpayAll() throws SystemException, Exception {
		boolean status = tmpfetclaimpayDao.removeAll();
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}
	@Override
	public void batchInsertTmpfetclaimpay(List<Tmpfetclaimpay> listTmpfetclaimpay) throws SystemException,
			Exception {
		this.tmpfetclaimpayDao.processInBatch(listTmpfetclaimpay);
	}
	/** mantis：MOB0017，處理人員：BI086，需求單編號：MOB0017 將安達提供中介檔上傳資料庫 end */

	public TmpfetclaimpayDao getTmpfetclaimpayDao() {
		return tmpfetclaimpayDao;
	}

	public void setTmpfetclaimpayDao(TmpfetclaimpayDao tmpfetclaimpayDao) {
		this.tmpfetclaimpayDao = tmpfetclaimpayDao;
	}


}
