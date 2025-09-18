package com.tlg.msSqlMob.dao;

import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.msSqlMob.entity.AccountFile;

import java.math.BigDecimal;

/** mantis：MOB0023，處理人員：BJ016，需求單編號：MOB0023 將安達提供的財務用銷帳回饋檔記錄到ACCOUNT_FILE資料表 */
public interface AccountFileDao extends IBatisBaseDao<AccountFile, BigDecimal>{
	
}