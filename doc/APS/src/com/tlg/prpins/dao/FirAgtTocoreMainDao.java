package com.tlg.prpins.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.aps.enums.EnumYCBFile;
import com.tlg.aps.vo.Aps055BotGenFileVo;
import com.tlg.aps.vo.Aps060YcbGenFileVo;
import com.tlg.aps.vo.Aps060YcbGenPolicyFileVo;
import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.prpins.entity.FirAgtTocoreMain;

/* mantis：FIR0294，處理人員：BJ085，需求單編號：FIR0294 外銀板信續件移回新核心-資料接收排程 start */
public interface FirAgtTocoreMainDao extends IBatisBaseDao<FirAgtTocoreMain, BigDecimal> {
	
	/* mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業 start */
	public List<Aps055BotGenFileVo> selectBotReFileData(Map params) throws Exception;
	
	public List<Aps055BotGenFileVo> selectBotEnFileData(Map params) throws Exception;
	
	public List<FirAgtTocoreMain> selectDistinctExtracomcodeByParams(Map params) throws Exception;
	/* mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業 end */
	
	/* mantis：FIR0668，處理人員：DP0706，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 start */
	public List<Aps060YcbGenFileVo> selectYcbRnFileData(Map params) throws Exception;
	public List<Aps060YcbGenFileVo> selectYcbEnFileData(Map params) throws Exception;
	/* mantis：FIR0668，處理人員：DP0706，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 end */
	
	//mantis：FIR0676，處理人員：DP0706，需求單編號：FIR0676_住火_元大續保作業_N+1比對擔保品檔案
	public boolean updateByOid(FirAgtTocoreMain firAgtTocoreMain) throws Exception;

	/**
	 * FIR0690、FIR0693，處理人員：CF048，需求單編號：FIR0690、FIR0693 元大續保作業PhaseII_匯出保批檔、保單副本檔
	 * EnumFileType 元大產出檔案清單
	 * @param ycbType
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Aps060YcbGenPolicyFileVo> selectYcbFileData(EnumYCBFile ycbType, Map params) throws Exception;
}