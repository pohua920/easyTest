package com.tlg.prpins.service;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.CarAddrImportlist;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：CAR0504，處理人員：CC009，需求單編號：CAR0504 微型電動二輪車【已領牌&未領牌】資料交換作業 */
public interface CarAddrImportlistService {
	public Result removeCarAddrImportlist(CarAddrImportlist entity) throws SystemException, Exception;
	
	public Result insertAddrImportlist(CarAddrImportlist entity) throws SystemException, Exception;
	
	public Result findCarAddrImportlistByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
}
