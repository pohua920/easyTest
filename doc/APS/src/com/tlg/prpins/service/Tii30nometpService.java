package com.tlg.prpins.service;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.Tii30nometp;
import com.tlg.util.Result;

/** mantis：CAR0504，處理人員：CC009，需求單編號：CAR0504 微型電動二輪車【已領牌&未領牌】資料交換作業 */
public interface Tii30nometpService {
	public Result removeTii30nometp(Tii30nometp entity) throws SystemException, Exception;
	
	public Result insertTii30nometp(Tii30nometp entity) throws SystemException, Exception;
}
