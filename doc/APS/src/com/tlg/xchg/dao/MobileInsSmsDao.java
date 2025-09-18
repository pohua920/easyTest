package com.tlg.xchg.dao;

import java.math.BigDecimal;
import java.util.List;

import com.tlg.aps.vo.MobileInsUnsendSmsVo;
import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.xchg.entity.MobileInsSms;


public interface MobileInsSmsDao extends IBatisBaseDao<MobileInsSms, BigDecimal> {
	
	public List<MobileInsUnsendSmsVo> selectUnsendCancelNotice() throws Exception;
	
	public List<MobileInsUnsendSmsVo> selectUnsendUnpaidNotice() throws Exception;
}