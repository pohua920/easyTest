package com.tlg.db2.dao;

import java.util.List;

import com.tlg.db2.entity.Com910wa;
import com.tlg.iBatis.IBatisBaseDao;


public interface Com910waDao extends IBatisBaseDao<Com910wa, String> {
	
	public boolean updateForBatch(Com910wa com910wa)throws Exception;
	
	public List<Com910wa> selectByUnsend() throws Exception;
	
	
}