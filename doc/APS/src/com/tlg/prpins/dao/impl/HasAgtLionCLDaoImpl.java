package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.HasAgtLionCLDao;
import com.tlg.prpins.entity.HasAgtLionCL;
/** mantis：HAS0215，處理人員：CD094，需求單編號：HAS0215 TA雄獅回饋檔*/
public class HasAgtLionCLDaoImpl extends IBatisBaseDaoImpl<HasAgtLionCL, BigDecimal> implements HasAgtLionCLDao {
	
	@Override
	public String getNameSpace() {
		return "HasAgtLionCL";
	}
	
	@Override
	public List<HasAgtLionCL> selectForGenFile(Map<String, String> params) throws Exception {
		return  getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForGenFile",params);
	}
	
}