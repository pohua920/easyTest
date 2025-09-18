package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.HasAgtLionACDao;
import com.tlg.prpins.entity.HasAgtLionAC;
/** mantis：HAS0215，處理人員：CD094，需求單編號：HAS0215 TA雄獅回饋檔*/
public class HasAgtLionACDaoImpl extends IBatisBaseDaoImpl<HasAgtLionAC, BigDecimal> implements HasAgtLionACDao {
	
	@Override
	public String getNameSpace() {
		return "HasAgtLionAC";
	}
	
	@Override
	public List<HasAgtLionAC> selectForGenFile(Map<String, String> params) throws Exception {
		return  getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForGenFile",params);
	}
	
}