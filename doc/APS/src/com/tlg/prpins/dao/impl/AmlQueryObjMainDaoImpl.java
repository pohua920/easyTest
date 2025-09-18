package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.AmlQueryObjMainDao;
import com.tlg.prpins.entity.AmlQueryObjMain;
import com.tlg.util.PageInfo;

/* mantis：OTH0065，處理人員：BJ085，需求單編號：OTH0065 建置AML洗錢查詢畫面 start */
public class AmlQueryObjMainDaoImpl extends IBatisBaseDaoImpl<AmlQueryObjMain, BigDecimal> implements AmlQueryObjMainDao {
	
	@Override
	public String getNameSpace() {
		return "AmlQueryObjMain";
	}
	
	@Override
	public List<AmlQueryObjMain> findAmlQueryObjMainMaxOid(PageInfo pageInfo)throws Exception {
		List<AmlQueryObjMain> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectMaxOid",pageInfo.getFilter());
		return queryForList;
	}
	
	@Override
	public int countMaxOid(Map params) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(getNameSpace() + ".countMaxOid", params);
		return count;
	}

}