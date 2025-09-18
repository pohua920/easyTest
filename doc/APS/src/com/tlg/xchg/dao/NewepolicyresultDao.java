package com.tlg.xchg.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.NewepolicyVo;
import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.util.PageInfo;
import com.tlg.xchg.entity.Newepolicyresult;
/** mantis：OTH0159，處理人員：CD094，需求單編號：OTH0159 電子保單系統條款檢核不通過資料通知(APS)  **/
public interface NewepolicyresultDao extends IBatisBaseDao<Newepolicyresult, BigDecimal> {
	
	public List<NewepolicyVo> selectForErr(PageInfo pageInfo)throws Exception;
	
	public List<NewepolicyVo> selectForYdayErr(Map params)throws Exception;
	
	public String selectForUrl(Map params)throws Exception;
	
}