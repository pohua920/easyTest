package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.Aps017EditVo;
import com.tlg.aps.vo.Aps017ResultVo;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirAgtSalesMappingDao;
import com.tlg.prpins.entity.FirAgtSalesMapping;
import com.tlg.util.PageInfo;

public class FirAgtSalesMappingDaoImpl extends IBatisBaseDaoImpl<FirAgtSalesMapping, BigDecimal> implements FirAgtSalesMappingDao {
	/* mantis：FIR0294，處理人員：BJ085，需求單編號：FIR0294 外銀板信續件移回新核心-資料接收排程 start */
	@Override
	public String getNameSpace() {
		return "FirAgtSalesMapping";
	}
	
	@Override
	public FirAgtSalesMapping selectByBranchNo(Map params) throws Exception {
		FirAgtSalesMapping firAgtSalesMapping = (FirAgtSalesMapping) getSqlMapClientTemplate().queryForObject(getNameSpace() + ".selectByBranchNo",params);
		return firAgtSalesMapping;
	}

	/*mantis：FIR0310，處理人員：BJ016，需求單編號：FIR0310 住火保經代分行服務人員對照表 START*/
	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<Aps017ResultVo> selectForAps017(PageInfo pageInfo) throws Exception {
		List<Aps017ResultVo> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForAps017",pageInfo.getFilter());
		return queryForList;
	}

	@SuppressWarnings("deprecation")
	@Override
	public int countForAps017(Map<String, String> params) throws Exception {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(getNameSpace() + ".countForAps017", params);
		return count;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Aps017EditVo selectByOidForAps017(BigDecimal oid) throws Exception {
		Aps017EditVo aps017EditVo = (Aps017EditVo)getSqlMapClientTemplate().queryForObject(getNameSpace() + ".selectByOidForAps017", oid);
		return aps017EditVo;
	}
	/*mantis：FIR0310，處理人員：BJ016，需求單編號：FIR0310 住火保經代分行服務人員對照表 END*/
}