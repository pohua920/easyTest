package com.tlg.msSqlRdm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.msSqlRdm.dao.InrcfilDao;
import com.tlg.msSqlRdm.entity.Inrcfil;
import com.tlg.msSqlRdm.service.InrcfilService;
//mantis：REI00021，處理人員：DP0706，需求單編號：REI00021 再保帳單介接400
@Transactional(value = "msSqlRdmTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class InrcfilServiceImpl implements InrcfilService{
	
	private InrcfilDao inrcfilDao;

	@Override
	public List<Inrcfil> findByParams(Map<String, Object> params) throws Exception{
		return inrcfilDao.findByParams(params);
	}
	

	@Override
	public void updateInrcfil(Inrcfil inrcfil) throws Exception {
		inrcfilDao.update(inrcfil);
	}

	public InrcfilDao getInrcfilDao() {
		return inrcfilDao;
	}

	public void setInrcfilDao(InrcfilDao inrcfilDao) {
		this.inrcfilDao = inrcfilDao;
	}


}
