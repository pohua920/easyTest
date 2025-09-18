package com.tlg.sales.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.sales.dao.CommdataCmemfilDao;
import com.tlg.sales.entity.CommdataCmemfil;
import com.tlg.sales.service.CommdataCmemfilService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;

/*mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業-新增印刷廠產檔作業規格*/
@Transactional(value="salesTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class CommdataCmemfilServiceImpl implements CommdataCmemfilService{
	
	private CommdataCmemfilDao commdataCmemfilDao;

	@Override
	public Result findCommdataCmemfilByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<CommdataCmemfil> searchResult = commdataCmemfilDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result findCommdataCmemfilByUk(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		CommdataCmemfil commdataCmemfil = commdataCmemfilDao.findByUK(params);
		if (null == commdataCmemfil) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(commdataCmemfil);
		}
		return result;
	}
	
	/*住火_取得失效服務人員接替人員代碼、姓名*/
	@Override
	public Result FindForFirChangeHandler1code(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<CommdataCmemfil> searchResult = commdataCmemfilDao.selectForFirChangeHandler1code(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	public CommdataCmemfilDao getCommdataCmemfilDao() {
		return commdataCmemfilDao;
	}

	public void setCommdataCmemfilDao(CommdataCmemfilDao commdataCmemfilDao) {
		this.commdataCmemfilDao = commdataCmemfilDao;
	}

}
