package com.sinosoft.claim.common.service.spring;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;
import ins.framework.utils.DataUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sinosoft.claim.common.service.facade.PrpDuserService;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpDuser;

public class PrpDuserServiceSpringImpl extends GenericDaoHibernate<PrpDuser,String> implements PrpDuserService{

	/**
	 * 查询用户
	 * @param userCode 用户代码
	 * @return 用户对象
	 */
	public PrpDuser getUser(String userCode){
		return super.get(userCode);
	}

	/**
	 * 用户基本信息
	 * @param Prpduser ：传入的用户名
	 */
	@Override
	public void save(PrpDuser prpDuser){
		logger.info("保存立案基本信息");
		super.save(prpDuser);
		
	}

	/**
	 * 保存用户基本信息
	 * @param list  :传入的用户基本信息集合
	 * @throws Exceptionuan
	 */
	@Override
	public void save(List<PrpDuser> list){
		logger.info("保存用户信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	/**
	 * 删除用户名信息
	 * @param userCode ：传入的用户名编号
	 */
	@Override
	public void delete(String userCode){
		logger.info("删除用户信息编号为" + userCode + "的用户信息");
		super.deleteByPK(PrpDuser.class, userCode);
	}

	/**
	 * @description: 用户修改
	 * @param PrpDuser prpDuser
	 * @throws Exception 
	 */
	@Override
	public void update(PrpDuser prpDuser){
		logger.info("修改用户信息开始");
		super.update(prpDuser);
		logger.info("修改用户信息结束");
	}
	
	/**
	 * 根据用户名编号查询出用户名信息
	 * @param userCode ：传入的用户名编号
	 * @return 返回用户名
	 */
	@Override
	public PrpDuser findPrpDuser(String userCode){
		logger.info("查询用户信息编号为" + userCode + "的用户信息");
		return super.get(PrpDuser.class,userCode);
	}
    
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的用户名页面信息
	 */
	@Override
	public Page findPrpDuser(QueryRule queryRule, int pageNo, int pageSize){
		logger.info("获取用户信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
		
	}

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @return 包含的用户名页面信息
	 */
	@Override
	public List<PrpDuser> findPrpDuser(QueryRule queryRule){
		return super.find(queryRule);
	}
	/**
	 * @param userCode 用户编码
	 * @param isChinese true 是获取中午名称，false 获取英文名称
	 * @return 返回用户的中文名称，或者英文名称
	 * @throws Exception
	 */
	public String getUserName(String userCode,boolean isChinese)throws Exception{
		String userName = "";
		if(userCode!=null||!"".equals(userCode)){
			PrpDuser prpDuser = super.get(userCode);
			if(prpDuser!=null){
				if(isChinese){
					userName = prpDuser.getUserName();
				}else{
					userName = prpDuser.getUserEName();
				}
			}
		}
		return userName;
	}
	/**
	 * 查询用户名称
	 * @param userCode 用户编码
	 * @return 返回用户的中文名称
	 * @throws Exception
	 */
	public String getUserName(String userCode)throws Exception{
		return this.getUserName(userCode,true);
	}
	/**
	 * 
	 * 查询能够处理某一机构下拥有某项权限的操作员
	 * @author 中科软
	 * @param statement 查询条件
	 * @param pageNo 开始页
	 * @param rowsPerPage 每页显示的条数
	 * @return 返回用户对象
	 * @throws Exception
	 */
	@Override
	public Page queryUserHaveRights(String statement, int pageNo, int rowsPerPage) throws Exception {
		List<?> list = HibernateUtils.findbySql(super.getSession(), statement, pageNo, rowsPerPage);
		List<PrpDuser> resultList = new ArrayList<PrpDuser>();
		if(list!=null && !list.isEmpty()){
			PrpDuser prpDuser = null;
			Object[] object = null;
			for(Iterator<?> it = list.iterator();it.hasNext();){
				object = (Object[])it.next();
				prpDuser = new PrpDuser();
				prpDuser.setUserCode((String)object[0]);
				prpDuser.setUserName((String)object[1]);
				resultList.add(prpDuser);
			}
		}
		return new Page((pageNo-1)*rowsPerPage, HibernateUtils.getCountbySql(super.getSession(), statement), rowsPerPage, resultList);
	}
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param conditions 查询条件
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的用户名页面信息
	 */
	@Override
	public Page findByPage(String conditions, int pageNo, int pageSize) {
		if(DataUtils.emptyToNull(conditions)==null){
			conditions = " 1=1 ";
		}
		String sql = "select * from PrpDuser where " + conditions;
		return HibernateUtils.findPagebySql(super.getSession(), sql, pageNo, pageSize,PrpDuser.class);
	}
	 /**
     * 翻译用户代码
     * @param userCode 用户名称
     * @param isChinese 中午还是英文
     * @throws Exception
     * @return String 用户名称
     */
	@Override
	public String translateCode(String userCode, boolean isChinese) throws Exception {
		String userName = "";
		if (userCode != null && !"".equals(userCode)) {
			PrpDuser prpDuser = super.get(PrpDuser.class, userCode);
			if (prpDuser != null) {
				if (isChinese) {
					userName = prpDuser.getUserName();
				} else {
					userName = prpDuser.getUserEName();
				}
			}
		}
		return userName;
	}
	 /**
     * 根据sql语句条件查询
	 * @param conditions 查询条件
	 * @return
	 * @throws Exception 
	 */
	@Override
	public List<PrpDuser> findByConditions(String conditions) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		return super.find(queryRule);
	}
	/**
	 * 更具条件查询用户对象
	 * @param conditions 查询条件
	 * @param pageNo 页面起始页
	 * @param pageSize 每页显示条数
	 * @return
	 * @throws Exception
	 */
	public List<PrpDuser> findByConditions(String conditions,int pageNo,int pageSize) throws Exception {
		List<PrpDuser> list = new ArrayList<PrpDuser>();
		String sql = "select *  From PrpDuser Where "+ conditions;
		if(pageSize<1){
			List<?> listTemp = HibernateUtils.findbySql(super.getSession(), sql, PrpDuser.class);
			Iterator<?> it = listTemp.iterator();
			while (it.hasNext()) {
				PrpDuser prpDuser = (PrpDuser) it.next();
				list.add(prpDuser);
			}
		}else{
			List<?> listTemp2 = HibernateUtils.findbySql(super.getSession(), sql,pageNo,pageSize, PrpDuser.class);
			Iterator<?> it = listTemp2.iterator();
			while (it.hasNext()) {
				PrpDuser prpDuser = (PrpDuser)it.next();
				list.add(prpDuser);
			}
		}
		return list;
	}
	/**
	 * 更具条件查询用户对象
	 * @param conditions  查询条件
	 * @param pageNo 页面起始页
	 * @param pageSize 每页显示条数
	 * @return
	 * @throws Exception
	 */
	public List<PrpDuser> findByStatement(String statement,int pageNo,int pageSize) throws Exception {
		List<PrpDuser> list = new ArrayList<PrpDuser>();
		if(pageSize<1){
			List<?> listTemp = HibernateUtils.findbySql(super.getSession(), statement, PrpDuser.class);
			Iterator<?> it = listTemp.iterator();
			while (it.hasNext()) {
				PrpDuser prpDuser = (PrpDuser)it.next();
				list.add(prpDuser);
			}
		}else{
			List<?> listTemp2 = HibernateUtils.findbySql(super.getSession(), statement,pageNo,pageSize, PrpDuser.class);
			Iterator<?> it = listTemp2.iterator();
			while (it.hasNext()) {
				PrpDuser prpDuser = (PrpDuser)it.next();
				list.add(prpDuser);
			}
		}
		return list;
	}
	
}
