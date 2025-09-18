package com.tlg.xchg.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import com.tlg.aps.vo.MobileInsUnsendSmsVo;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.xchg.dao.MobileInsSmsDao;
import com.tlg.xchg.entity.MobileInsSms;

public class MobileInsSmsDaoImpl extends IBatisBaseDaoImpl<MobileInsSms, BigDecimal> implements MobileInsSmsDao {
	
	@Override
	public String getNameSpace() {
		return "MobileInsSms";
	}

	@Override
	public List<MobileInsUnsendSmsVo> selectUnsendCancelNotice() throws Exception {
		List<MobileInsUnsendSmsVo> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectUnsendCancelNotice");
		return queryForList;
	}

	@Override
	public List<MobileInsUnsendSmsVo> selectUnsendUnpaidNotice() throws Exception {
		List<MobileInsUnsendSmsVo> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectUnsendUnpaidNotice");
		return queryForList;
	}
	
}