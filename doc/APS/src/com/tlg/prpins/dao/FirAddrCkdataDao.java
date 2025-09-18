package com.tlg.prpins.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.Aps024ExportVo;
import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.prpins.entity.FirAddrCkdata;

public interface FirAddrCkdataDao extends IBatisBaseDao<FirAddrCkdata, BigDecimal> {
	/* mantis：FIR0183，處理人員：BJ085，需求單編號：FIR0183 火險地址資料匯入 start */
	
	public void truncate() throws Exception;
	
	/* mantis：FIR0521，處理人員：CC009，需求單編號：FIR0521 火險地址維護作業增加處理正規化功能 start */
	public List<Aps024ExportVo> aps024Export(Map<String,String> params) throws Exception;
	public int aps024ExportCount(Map<String, Object> params) throws Exception;
	/* mantis：FIR0521，處理人員：CC009，需求單編號：FIR0521 火險地址維護作業增加處理正規化功能 end */

}