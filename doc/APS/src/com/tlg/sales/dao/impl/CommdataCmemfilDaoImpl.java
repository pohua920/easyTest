package com.tlg.sales.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.sales.dao.CommdataCmemfilDao;
import com.tlg.sales.entity.CommdataCmemfil;

/*mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業-新增印刷廠產檔作業規格 */
public class CommdataCmemfilDaoImpl extends IBatisBaseDaoImpl<CommdataCmemfil, BigDecimal> implements CommdataCmemfilDao {
	@Override
	public String getNameSpace() {
		return "CommdataCmemfil";
	}
	
	@Override
	public List<CommdataCmemfil> selectForFirChangeHandler1code(Map params) throws Exception {
		return  getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForFirChangeHandler1code",params);
	}
}