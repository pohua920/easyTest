package com.tlg.msSqlMob.dao.impl;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.msSqlMob.dao.ClFileDao;
import com.tlg.msSqlMob.entity.ClFile;

import java.math.BigDecimal;

/** mantis：MOB0008，處理人員：BJ016，需求單編號：MOB0008 將安達提供的理賠回饋檔記錄到CL_FILE資料表 */
public class ClFileDaoImpl extends IBatisBaseDaoImpl<ClFile, BigDecimal> implements ClFileDao {
	@Override
	public String getNameSpace() {
		return "ClFile";
	}
}