package com.tlg.xchg.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.xchg.dao.NewepolicymainDao;
import com.tlg.xchg.entity.Newepolicymain;

public class NewepolicymainDaoImpl extends IBatisBaseDaoImpl<Newepolicymain, BigDecimal> implements NewepolicymainDao {
	
	@Override
	public String getNameSpace() {
		return "Newepolicymain";
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	@Override
	public List<Newepolicymain> selectForFirBatchSendmail(Map params) throws Exception {
		List<Newepolicymain> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForFirBatchSendmail",params);
		return queryForList;
	}
	
}