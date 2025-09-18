package com.tlg.msSqlMob.dao;

import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.msSqlMob.entity.ChFile;

import java.math.BigDecimal;

/** mantis：MOB0008，處理人員：BJ016，需求單編號：MOB0008 將安達提供的保批回饋檔記錄到CH_FILE資料表 */
public interface ChFileDao extends IBatisBaseDao<ChFile, BigDecimal>{
	
}