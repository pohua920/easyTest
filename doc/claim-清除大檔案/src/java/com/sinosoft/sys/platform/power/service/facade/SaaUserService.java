package com.sinosoft.sys.platform.power.service.facade;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.sys.platform.power.model.SaaUser;


public interface SaaUserService {
	public Page getUserList(SaaUser saaUser, int pageNo, int pageSize,
			String userCodeOperate);

	public void queryUserJSP(String userCode, String comCode,
			String saaGradeCode, String userCodeOperate);

	public void queryUserJSPByUserCode(String userCode, String userCodeOperate);

	public List<SaaUser> findSaaUserSameComList(String userCode,
			String userCodeOperate);

	public SaaUser findSaaUserByUserCode(String userCode, String operUserCode);

	public SaaUser findSaaUserByUserCode(String userCode);

	public Page getAgengUserList(SaaUser saaUser, int pageNo, int pageSize,
			String userCodeOperate);

	/**
	 * @author 中科软 
	 * @param queryRule
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page queryUserTranslateCode(QueryRule queryRule, int pageNo,
			int pageSize) throws Exception;
	/**
	 * @author 中科软
	 * @param userCodeArray 
	 * @return List<SaaUser>
	 */
	public List<SaaUser> findSaaUserListByCodeArray(String[] userCodeArray);
	/**
	 * @author 中科软
	 * @param userCodeArray 
	 * @return List<SaaUser>
	 */
	public List<SaaUser> findSaaUserByComcode(String comCode);
	/**
	 * @author 中科软
	 * @param userCodeList 
	 * @return List<SaaUser>
	 */
	public List<SaaUser> findSaaUserListByCodeList(List<String> userCodeList);

	public List<SaaUser> findByRule(QueryRule rule);
}
