package com.tlg.msSqlRdm.service;

import java.util.List;
import java.util.Map;

import com.tlg.msSqlRdm.entity.Inrcfil;
//mantis：REI00021，處理人員：DP0706，需求單編號：REI00021 再保帳單介接400
public interface InrcfilService{
	
	public List<Inrcfil> findByParams(Map<String, Object> params) throws Exception;

	public void updateInrcfil(Inrcfil inrcfil) throws Exception;
}
