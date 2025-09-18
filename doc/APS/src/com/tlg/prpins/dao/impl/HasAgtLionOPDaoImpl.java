package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.HasAgtLionOPDao;
import com.tlg.prpins.entity.HasAgtLionOP;
/** mantis：HAS0215，處理人員：CD094，需求單編號：HAS0215 TA雄獅回饋檔*/
public class HasAgtLionOPDaoImpl extends IBatisBaseDaoImpl<HasAgtLionOP, BigDecimal> implements HasAgtLionOPDao {
	
	@Override
	public String getNameSpace() {
		return "HasAgtLionOP";
	}
	
	@Override
	public List<HasAgtLionOP> selectForGenFile(Map<String, String> params) throws Exception {
		return  getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForGenFile",params);
	}
	
}