package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.Aps060MainDtlVo;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirAgtrnYcbUploadDao;
import com.tlg.prpins.entity.FirAgtrnYcbUpload;

/** mantis：FIR0676，處理人員：DP0706，需求單編號：FIR0676_住火_元大續保作業_N+1比對擔保品檔案  **/
public class FirAgtrnYcbUploadDaoImpl extends IBatisBaseDaoImpl<FirAgtrnYcbUpload, BigDecimal>  implements FirAgtrnYcbUploadDao {

	@Override
	public String getNameSpace() {
		return "FirAgtrnYcbUpload";
	}
	
	@Override
	public  List<Aps060MainDtlVo> selectAgtMainAndDtlForImport(Map params) throws Exception {
		List<Aps060MainDtlVo> reusltList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectAgtMainAndDtlForImport", params);
		return reusltList;
	}
}