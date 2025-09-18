package com.tlg.sales.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.sales.entity.CommdataCmemfil;

/*mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業-新增印刷廠產檔作業規格*/
public interface CommdataCmemfilDao extends IBatisBaseDao<CommdataCmemfil, BigDecimal> {
	
	public List<CommdataCmemfil> selectForFirChangeHandler1code(Map params)throws Exception;
}