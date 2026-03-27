package com.sinosoft.sys.platform.user.service.facade;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.platform.sui.ac.vo.CodeCondition;
import java.util.List;
import com.sinosoft.sys.platform.power.model.SaaUser;

public interface UserService {

	public abstract SaaUser findUserByUserCode(String s);

	public abstract SaaUser getUser(String s);

	/** *******这里两个方法暂时保留两个，因为不确定在双击域当中是否是一样的功能权限，待完善（机构同理）******** */
	public abstract Page findUser(QueryRule queryrule, int i, int j);

	public abstract Page findUser(String userCode, QueryRule queryrule, int i, int j);

	/** -------------------------------------------------------------------------------* */
	public abstract SaaUser getUserByUserCode(String s);

	public abstract void update(SaaUser prpduser);

	public abstract void updateNothing();

	public abstract void save(SaaUser prpduser);

	public abstract void delete(String s);

	public abstract void unvalidUser(String s);

	public abstract String getComCodeByUserCode(String s);

	public abstract Page findAllUser(String s, String s1);

	public abstract List listUserCodeSelect(CodeCondition codecondition);

	public abstract List listUserCodeSelect(List list);

	public abstract List<Object[]> getUser();

	/**
	 * 类似於saveorupdate方法 管理功能同步
	 * 
	 * @author 中科软
	 * @param prpDuser
	 */
	public void synchroPrpDuser(SaaUser prpDuser) throws Exception;

	/**
	 * 判断用户是否存在
	 * 
	 * @author 中科软
	 * @param userCode
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public boolean UserExist(String userCode, String password) throws Exception;

	public List<SaaUser> getSaaUser(QueryRule rule);

	public void synReverseSaaUser(SaaUser saaUser) throws Exception;

	public abstract Page findUser(String userCode,String taskCode, QueryRule rule, int pageNo,int pageSize) throws Exception;
}
