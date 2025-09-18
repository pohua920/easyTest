package com.tlg.msSqlMob.dao.impl;

import com.tlg.aps.vo.mob.fetPolicy.TerminationNoticeVo;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.msSqlMob.dao.TerminationNoticeDao;
import com.tlg.msSqlMob.entity.TerminationNotice;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/** mantis：MOB0024，處理人員：BJ016，需求單編號：MOB0024 產生終止通知書 */
public class TerminationNoticeDaoImpl extends IBatisBaseDaoImpl<TerminationNotice, BigDecimal> implements TerminationNoticeDao {
	@Override
	public String getNameSpace() {
		return "TerminationNotice";
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<TerminationNoticeVo> selectForCancel(Map params) throws Exception {
		return getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForCancel",params);
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<TerminationNoticeVo> selectForUnpaid1(Map params) throws Exception {
		return getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForUnpaid1",params);
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<TerminationNoticeVo> selectForUnpaid2(Map params) throws Exception {
		return getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForUnpaid2",params);
	}
}