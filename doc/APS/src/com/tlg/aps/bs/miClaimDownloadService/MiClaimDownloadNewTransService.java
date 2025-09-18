package com.tlg.aps.bs.miClaimDownloadService;

import java.util.ArrayList;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.Tmpfetclaimcomm;
import com.tlg.prpins.entity.Tmpfetclaimkind;
import com.tlg.prpins.entity.Tmpfetclaimmain;
import com.tlg.prpins.entity.Tmpfetclaimpay;

public interface MiClaimDownloadNewTransService {

	public void deleteAllData() throws SystemException, Exception;
	
	public void batchInsertFtpData(
			ArrayList<Tmpfetclaimmain> listTmpfetclaimmain,
			ArrayList<Tmpfetclaimkind> listTmpfetclaimkind,
			ArrayList<Tmpfetclaimpay> listTmpfetclaimpay,
			ArrayList<Tmpfetclaimcomm> listTmpfetclaimcomm) throws SystemException, Exception;

}
