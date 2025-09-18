package com.tlg.prpins.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.prpins.entity.FirAgtSalesMapping;
import com.tlg.util.PageInfo;
import com.tlg.aps.vo.Aps017EditVo;
import com.tlg.aps.vo.Aps017ResultVo;
import com.tlg.iBatis.IBatisBaseDao;

public interface FirAgtSalesMappingDao extends IBatisBaseDao<FirAgtSalesMapping, BigDecimal> {

	@SuppressWarnings("rawtypes")
	public FirAgtSalesMapping selectByBranchNo(Map params) throws Exception;
	
	//mantis：FIR0310，處理人員：BJ016，需求單編號：FIR0310 住火保經代分行服務人員對照表 START
	public List<Aps017ResultVo> selectForAps017(PageInfo pageInfo)throws Exception;
	public int countForAps017(Map<String,String> params)throws Exception;
	public Aps017EditVo selectByOidForAps017(BigDecimal oid)throws Exception;
	//mantis：FIR0310，處理人員：BJ016，需求單編號：FIR0310 住火保經代分行服務人員對照表 END
}