package com.tlg.prpins.service.impl;

import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.UwblacklistDao;
import com.tlg.prpins.service.UwblacklistService;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class UwblacklistServiceImpl implements UwblacklistService{

	private UwblacklistDao uwblacklistDao;

	@SuppressWarnings("rawtypes")
	@Override
	public int countUwblacklist(Map params) throws SystemException, Exception {
		return uwblacklistDao.count(params);
	}

	public UwblacklistDao getUwblacklistDao() {
		return uwblacklistDao;
	}

	public void setUwblacklistDao(UwblacklistDao uwblacklistDao) {
		this.uwblacklistDao = uwblacklistDao;
	}
}
