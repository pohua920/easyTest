package com.tlg.prpins.service;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.TiiTvmetp;
import com.tlg.util.Result;

/** mantis：CAR0504，處理人員：CC009，需求單編號：CAR0504 微型電動二輪車【已領牌&未領牌】資料交換作業 */
public interface TiiTvmetpService {
	public Result removeTiiTvmetp(TiiTvmetp entity) throws SystemException, Exception;
	
	public Result insertTiiTvmetp(TiiTvmetp entity) throws SystemException, Exception;
}
