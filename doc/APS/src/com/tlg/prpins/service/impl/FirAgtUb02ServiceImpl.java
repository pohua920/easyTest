package com.tlg.prpins.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.vo.Aps041MainVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirAgtUb02Dao;
import com.tlg.prpins.service.FirAgtUb02Service;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/* mantis：FIR0547，處理人員：CC009，需求單編號：FIR0547 聯邦新件資料交換查詢作業 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirAgtUb02ServiceImpl implements FirAgtUb02Service{
	private FirAgtUb02Dao firAgtUb02Dao;

	@SuppressWarnings("unchecked")
	@Override
	public Result findAPS041Main3ByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firAgtUb02Dao.countForAps041(pageInfo.getFilter());
		if (rowCount == 0) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<Aps041MainVo> searchResult = firAgtUb02Dao.selectForAps041(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	public FirAgtUb02Dao getFirAgtUb02Dao() {
		return firAgtUb02Dao;
	}

	public void setFirAgtUb02Dao(FirAgtUb02Dao firAgtUb02Dao) {
		this.firAgtUb02Dao = firAgtUb02Dao;
	}
	
}
