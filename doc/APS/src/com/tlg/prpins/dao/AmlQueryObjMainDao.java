package com.tlg.prpins.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.prpins.entity.AmlQueryObjMain;
import com.tlg.util.PageInfo;

/* mantis：OTH0065，處理人員：BJ085，需求單編號：OTH0065 建置AML洗錢查詢畫面  */
public interface AmlQueryObjMainDao extends IBatisBaseDao<AmlQueryObjMain, BigDecimal> {
	
	public List<AmlQueryObjMain> findAmlQueryObjMainMaxOid(PageInfo pageInfo)throws Exception;
	
	public int countMaxOid(Map params);
}