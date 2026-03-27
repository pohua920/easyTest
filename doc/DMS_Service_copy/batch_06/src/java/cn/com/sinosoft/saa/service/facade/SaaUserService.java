package cn.com.sinosoft.saa.service.facade;

import ins.framework.common.Page;

import java.util.List;

import cn.com.sinosoft.saa.model.SaaUser;

public interface SaaUserService {
	public Page getUserList(SaaUser saaUser, int pageNo, int pageSize,
			String userCodeOperate, String taskCode,String comName);

	public void queryUserJSP(String userCode, String comCode,
			String saaGradeCode, String userCodeOperate);

	public void queryUserJSPByUserCode(String userCode, String userCodeOperate);

	public List<SaaUser> findSaaUserSameComList(String userCode,
			String userCodeOperate);

	public SaaUser findSaaUserByUserCode(String userCode, String operUserCode);

	public SaaUser findSaaUserByUserCode(String userCode);

	public Page getAgengUserList(SaaUser saaUser, int pageNo, int pageSize,
			String userCodeOperate);
}
