package com.tlg.xchg.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.CwpAnnounceVo;
import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.util.PageInfo;
import com.tlg.xchg.entity.CwpRcvAnnounce;

public interface CwpRcvAnnounceDao extends IBatisBaseDao<CwpRcvAnnounce, BigDecimal> {
	/* mantis：OTH0093，處理人員：BJ085，需求單編號：OTH0093 傷害險通報查詢、重送介面 start */
	public List<CwpAnnounceVo> selectDistinctByPageInfo(PageInfo pageInfo) throws Exception;
	
	public int countDistinct(Map params) throws Exception;
	
	public CwpAnnounceVo selectDistinctByParams(Map params) throws Exception;
	
	public List<Map> selectUnsendRcvData(Map params) throws Exception;
}