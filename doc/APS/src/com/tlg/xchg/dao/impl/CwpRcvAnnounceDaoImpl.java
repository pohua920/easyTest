package com.tlg.xchg.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.CwpAnnounceVo;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.util.PageInfo;
import com.tlg.xchg.dao.CwpRcvAnnounceDao;
import com.tlg.xchg.entity.CwpRcvAnnounce;

public class CwpRcvAnnounceDaoImpl extends IBatisBaseDaoImpl<CwpRcvAnnounce, BigDecimal> implements CwpRcvAnnounceDao {
	/* mantis：OTH0093，處理人員：BJ085，需求單編號：OTH0093 傷害險通報查詢、重送介面 start */
	@Override
	public String getNameSpace() {
		return "CwpRcvAnnounce";
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<CwpAnnounceVo> selectDistinctByPageInfo(PageInfo pageInfo) throws Exception {
		List<CwpAnnounceVo> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectDistinctByPageInfo",pageInfo.getFilter());
		return queryForList;
	}

	@Override
	public int countDistinct(Map params) throws Exception {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(getNameSpace() + ".countDistinct",params);
		return count;
	}

	@Override
	public CwpAnnounceVo selectDistinctByParams(Map params) throws Exception {
		CwpAnnounceVo cwpAnnounceVo = (CwpAnnounceVo) getSqlMapClientTemplate().queryForObject(getNameSpace()+".selectDistinctByParams",params);
		return cwpAnnounceVo;
	}

	@Override
	public List<Map> selectUnsendRcvData(Map params) throws Exception {
		List<Map> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectRcvForUnsend", params);
		return queryForList;
	}
}