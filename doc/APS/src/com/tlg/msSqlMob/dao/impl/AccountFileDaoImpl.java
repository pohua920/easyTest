package com.tlg.msSqlMob.dao.impl;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.msSqlMob.dao.AccountFileDao;
import com.tlg.msSqlMob.entity.AccountFile;

import java.math.BigDecimal;

/** mantis：MOB0023，處理人員：BJ016，需求單編號：MOB0023 將安達提供的財務用銷帳回饋檔記錄到ACCOUNT_FILE資料表 */
public class AccountFileDaoImpl extends IBatisBaseDaoImpl<AccountFile, BigDecimal> implements AccountFileDao {
	@Override
	public String getNameSpace() {
		return "AccountFile";
	}
}