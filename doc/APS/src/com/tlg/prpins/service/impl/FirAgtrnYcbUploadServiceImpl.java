package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.vo.Aps060MainDtlVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirAgtrnYcbUploadDao;
import com.tlg.prpins.entity.FirAgtrnYcbUpload;
import com.tlg.prpins.service.FirAgtrnYcbUploadService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;

/** mantis：FIR0676，處理人員：DP0706，需求單編號：FIR0676_住火_元大續保作業_N+1比對擔保品檔案  **/
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirAgtrnYcbUploadServiceImpl implements FirAgtrnYcbUploadService{
	
	private FirAgtrnYcbUploadDao firAgtrnYcbUploadDao;


	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirAgtrnYcbUploadByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirAgtrnYcbUpload> searchResult = firAgtrnYcbUploadDao.findByParams(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public int countFirAgtrnYcbUpload(Map params) throws SystemException, Exception {
		return firAgtrnYcbUploadDao.count(params);
	}

	@Override
	public Result insertFirAgtrnYcbUpload(FirAgtrnYcbUpload firAgtrnYcbUpload) throws SystemException, Exception {

		if (firAgtrnYcbUpload == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		firAgtrnYcbUploadDao.insert(firAgtrnYcbUpload);

		if(firAgtrnYcbUploadDao.isUnique(firAgtrnYcbUpload)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firAgtrnYcbUpload);
		return result;
	}
	
	@Override
	public Result updateFirAgtrnYcbUpload(FirAgtrnYcbUpload firAgtrnYcbUpload) throws SystemException, Exception {

		if (firAgtrnYcbUpload == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = false;
		try{
		 status = firAgtrnYcbUploadDao.update(firAgtrnYcbUpload);
		
		}catch(Exception ex){
			ex.printStackTrace();
		}
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firAgtrnYcbUpload);
		return result;
	}
	
	@Override
	public Result removeFirAgtrnYcbUpload(FirAgtrnYcbUpload firAgtrnYcbUpload) throws SystemException, Exception {
		if (firAgtrnYcbUpload == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firAgtrnYcbUploadDao.remove(firAgtrnYcbUpload);
//		if (!status) {
//			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
//		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}
	
	@Override
	public Result selectAgtMainAndDtlForImport(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<Aps060MainDtlVo> searchResult = firAgtrnYcbUploadDao.selectAgtMainAndDtlForImport(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	public FirAgtrnYcbUploadDao getFirAgtrnYcbUploadDao() {
		return firAgtrnYcbUploadDao;
	}

	public void setFirAgtrnYcbUploadDao(FirAgtrnYcbUploadDao firAgtrnYcbUploadDao) {
		this.firAgtrnYcbUploadDao = firAgtrnYcbUploadDao;
	}

}
