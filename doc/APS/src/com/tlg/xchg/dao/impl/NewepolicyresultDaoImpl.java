package com.tlg.xchg.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.NewepolicyVo;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.util.PageInfo;
import com.tlg.xchg.dao.NewepolicyresultDao;
import com.tlg.xchg.entity.Newepolicyresult;
/** mantis：OTH0159，處理人員：CD094，需求單編號：OTH0159 電子保單系統條款檢核不通過資料通知(APS)  **/
public class NewepolicyresultDaoImpl extends IBatisBaseDaoImpl<Newepolicyresult, BigDecimal> implements NewepolicyresultDao {
	
	@Override
	public String getNameSpace() {
		return "Newepolicyresult";
	}

	@Override
	public List<NewepolicyVo> selectForErr(PageInfo pageInfo) throws Exception {
		List<NewepolicyVo> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectByErr",pageInfo.getFilter());
		return queryForList;
	}
	
	@Override
	public List<NewepolicyVo> selectForYdayErr(Map params) throws Exception {
		List<NewepolicyVo> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectByYdayErr",params);
		return queryForList;
	}
	
	@Override
	public String selectForUrl(Map params) {
		String  pdfUrl= (String) getSqlMapClientTemplate().queryForObject(getNameSpace() + ".selectForUrl", params);
		return pdfUrl;
	}

	
}