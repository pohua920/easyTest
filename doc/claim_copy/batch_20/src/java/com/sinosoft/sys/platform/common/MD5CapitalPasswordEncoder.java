package com.sinosoft.sys.platform.common;


import org.springframework.dao.DataAccessException;
import org.springframework.security.providers.encoding.Md5PasswordEncoder;

public class MD5CapitalPasswordEncoder extends Md5PasswordEncoder {
	public String encodePassword(String s, Object obj)
			throws DataAccessException {
		return (super.encodePassword(s, obj)).toUpperCase();
	}

	public boolean isPasswordValid(String encPass, String rawPass, Object salt)
			throws DataAccessException {
		return (super.isPasswordValid(encPass, rawPass, salt));
	}
}

