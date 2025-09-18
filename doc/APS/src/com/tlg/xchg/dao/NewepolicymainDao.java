package com.tlg.xchg.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.xchg.entity.Newepolicymain;
import com.tlg.iBatis.IBatisBaseDao;

public interface NewepolicymainDao extends IBatisBaseDao<Newepolicymain, BigDecimal> {
	public List<Newepolicymain> selectForFirBatchSendmail(Map params)throws Exception;
}