package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.Aps024ExportVo;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirAddrCkdataDao;
import com.tlg.prpins.entity.FirAddrCkdata;

public class FirAddrCkdataDaoImpl extends IBatisBaseDaoImpl<FirAddrCkdata, BigDecimal> implements FirAddrCkdataDao{
	/* mantis：FIR0183，處理人員：BJ085，需求單編號：FIR0183 火險地址資料匯入 start */

	@Override
	public String getNameSpace() {
		return "FirAddrCkdata";
	}
	
	@Override
	public void truncate() throws Exception {
		getSqlMapClientTemplate().update(getNameSpace()+".truncate");
	}

	@SuppressWarnings("unchecked")
	private int deleteAllDatas(String nameSpace) {
		return getSqlMapClientTemplate().delete(nameSpace);
	}
	
	/* mantis：FIR0521，處理人員：CC009，需求單編號：FIR0521 火險地址維護作業增加處理正規化功能 start */
	@Override
	public List<Aps024ExportVo> aps024Export(Map<String, String> params) throws Exception {
		return getSqlMapClientTemplate().queryForList(getNameSpace()+".aps024Export",params);
	}

	@Override
	public int aps024ExportCount(Map<String, Object> params) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject(getNameSpace() + ".aps024ExportCount", params);
	}
	/* mantis：FIR0521，處理人員：CC009，需求單編號：FIR0521 火險地址維護作業增加處理正規化功能 end */
	
}