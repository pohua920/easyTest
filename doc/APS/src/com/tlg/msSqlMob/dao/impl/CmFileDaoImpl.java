package com.tlg.msSqlMob.dao.impl;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.msSqlMob.dao.CmFileDao;
import com.tlg.msSqlMob.entity.CmFile;

import java.math.BigDecimal;

/** mantis：MOB0008，處理人員：BJ016，需求單編號：MOB0008 將安達提供的佣金回饋檔記錄到CM_FILE資料表 */
public class CmFileDaoImpl extends IBatisBaseDaoImpl<CmFile, BigDecimal> implements CmFileDao {
	@Override
	public String getNameSpace() {
		return "CmFile";
	}
}