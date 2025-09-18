package com.tlg.xchg.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.vo.MobileInsUnsendSmsVo;
import com.tlg.exception.SystemException;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;
import com.tlg.xchg.dao.MobileInsSmsDao;
import com.tlg.xchg.entity.MobileInsSms;
import com.tlg.xchg.service.MobileInsSmsService;

@Transactional(value="xchgTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class MobileInsSmsServiceImpl implements MobileInsSmsService{

	private MobileInsSmsDao mobileInsSmsDao;

	@Override
	public int countMobileInsSms(Map params) throws SystemException, Exception {
		return mobileInsSmsDao.count(params);
	}

	@Override
	public Result findMobileInsSmsByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = mobileInsSmsDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<MobileInsSms> searchResult = mobileInsSmsDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findMobileInsSmsByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<MobileInsSms> searchResult = mobileInsSmsDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findMobileInsSmsByOid(String oid) throws SystemException,Exception {

		if (StringUtil.isSpace(oid)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("oid", oid);
		Result result = new Result();
		List<MobileInsSms> list = mobileInsSmsDao.findByParams(params);
		if (null == list) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			if(list.size() == 0 || list.size() > 1){
				Message m = new Message();
				m.setMessageString("資料比數錯誤！");
				result.setMessage(m);
				return result;
			}
			MobileInsSms mobileInsSms = list.get(0);
			result.setResObject(mobileInsSms);
		}
		return result;
	}

	@Override
	public Result updateMobileInsSms(MobileInsSms mobileInsSms) throws SystemException, Exception {

		if (mobileInsSms == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = mobileInsSmsDao.update(mobileInsSms);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(mobileInsSms);
		return result;
	}

	@Override
	public Result insertMobileInsSms(MobileInsSms mobileInsSms) throws SystemException, Exception {

		if (mobileInsSms == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		BigDecimal oid = mobileInsSmsDao.insert(mobileInsSms);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(mobileInsSms);
		return result;
	}
	
	@Override
	public Result findUnsendCancelNotice() throws SystemException, Exception {
		Result result = new Result();
		List<MobileInsUnsendSmsVo> searchResult = mobileInsSmsDao.selectUnsendCancelNotice();
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findUnsendUnpaidNotice() throws SystemException, Exception {
		Result result = new Result();
		List<MobileInsUnsendSmsVo> searchResult = mobileInsSmsDao.selectUnsendUnpaidNotice();
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	public MobileInsSmsDao getMobileInsSmsDao() {
		return mobileInsSmsDao;
	}

	public void setMobileInsSmsDao(MobileInsSmsDao mobileInsSmsDao) {
		this.mobileInsSmsDao = mobileInsSmsDao;
	}


}
