package com.tlg.prpins.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.Aps060MainDtlVo;
import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.prpins.entity.FirAgtrnYcbUpload;

/** mantis：FIR0676，處理人員：DP0706，需求單編號：FIR0676_住火_元大續保作業_N+1比對擔保品檔案 **/
public interface FirAgtrnYcbUploadDao extends IBatisBaseDao<FirAgtrnYcbUpload, BigDecimal>{
	
	public List<Aps060MainDtlVo> selectAgtMainAndDtlForImport(Map params) throws Exception;
    
}