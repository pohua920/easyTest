package com.sinosoft.claim.common.service.spring;

import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import com.sinosoft.claim.common.service.facade.UtiCodeTransferService;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.UtiCodeTransfer;
import com.sinosoft.sysframework.exceptionlog.UserException;

public class UtiCodeTransferServiceSpringImpl extends GenericDaoHibernate<UtiCodeTransfer, String> implements UtiCodeTransferService {
	 /**
     * 更具险类代码删除
     * @param configCode 险类代码
     * @throws Exception
     */
	@Override
	public void delete(String configCode) throws Exception {
		super.deleteByPK(configCode);
	}
	/**
     * 根据条件删除
     * @param conditions 查询条件
     * @return
     * @throws Exception
     */
	@Override
	public int deleteByConditions(String conditions) throws Exception {
		return 0;
	}
	/**
     * 查询险类代码表
     * @param conditions 查询条件
     * @param pageNo 开始页数
     * @param rowsPerPage 没有显示条数
     * @return
     * @throws Exception
     */
	@Override
	public Collection<?> findByConditions(String conditions, int pageNo, int rowsPerPage) throws Exception {
		return null;
	}

	/**
     * 查询险类代码表
     * @param conditions 查询条件
     * @return
     * @throws Exception
     */
	@Override
	public List<UtiCodeTransfer> findByConditions(String conditions) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		return super.find(queryRule);
	}
	 /**
     * 更具险类代码查询
     * @param configCode 险类代码
     * @return
     * @throws Exception
     */
	@Override
	public UtiCodeTransfer findByPrimaryKey(String configCode) throws Exception {
		 StringBuffer buffer = new StringBuffer(200);
	        //拼SQL语句
	        buffer.append("SELECT ");
	        buffer.append("configCode,");
	        buffer.append("outerCode,");
	        buffer.append("innerCode,");
	        buffer.append("codeType,");
	        buffer.append("validStatus,");
	        buffer.append("riskType ");
	        buffer.append("FROM UtiCodeTransfer ");
	        if(logger.isDebugEnabled()){
	            StringBuffer debugBuffer =  new StringBuffer(buffer.length()*4);
	            debugBuffer.append(buffer.toString());
	            debugBuffer.append("WHERE ");
	            debugBuffer.append("configCode=").append("'").append(configCode).append("'");
	            logger.debug(debugBuffer.toString());
	        }

	        buffer.append("WHERE ");
	        String statement=buffer.toString();
	        statement=statement+"configCode = '"+configCode+"'";
			Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
			List<?> tempList = HibernateUtils.findbySql(session, statement, 1, 10);
			UtiCodeTransfer utiCodeTransfer = null;
			for (int i = 0; i < tempList.size(); i++) {
				Object[] object = (Object[]) tempList.get(i);
				utiCodeTransfer=new UtiCodeTransfer();
	            utiCodeTransfer.setConfigCode((String) object[0]);
	            utiCodeTransfer.setOuterCode((String) object[1]);
	            utiCodeTransfer.setInnerCode((String) object[2]);
	            utiCodeTransfer.setCodeType((String) object[3]);
	            utiCodeTransfer.setValidStatus((String) object[4]);
	            utiCodeTransfer.setRiskType((String) object[5]);
			}
		return utiCodeTransfer;
	}
	 /**
     * 根据条件查询条数
     * @param conditions 查询条件
     * @return
     * @throws Exception
     */
	/**更具险别，查询险种险类代码对照表
	 * @param riskCode 险别
	 * @return 返回险别对象
	 * @throws Exception
	 */
	public UtiCodeTransfer findUtiCodeTransfer(String riskCode)throws Exception{
		QueryRule queryRule = QueryRule.getInstance().addEqual("outerCode", riskCode);
		List<UtiCodeTransfer> list = super.find(queryRule);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}else{
			throw new UserException(-1003,-98,"險別代碼配置","險別代碼未配置，請與管理員聯繫。");
		}
	}
	@Override
	public int getCount(String conditions) throws Exception {
		return 0;
	}
	  /**
     * 插入数据
     * @param utiCodeTransfer  险种险类代码对照表
     * @throws Exception
     */
	@Override
	public void insert(UtiCodeTransfer utiCodeTransfer) throws Exception {
		super.save(utiCodeTransfer);
	}
	 /**
     * 批量插入数据
     * @param collection 险种险类代码对照表集合
     * @throws Exception
     */
	@Override
	public void insertAll(Collection<?> collection) throws Exception {
	}
	 /**
     * 更新代码
     * @param utiCodeTransfer 险种险类代码
     * @throws Exception
     */
	@Override
	public void update(UtiCodeTransfer utiCodeTransfer) {
		super.update(utiCodeTransfer);
	}
	/**
	 * 查询险类类表
	 * @return
	 * @throws Exception
	 */
	public Map<String,String> findRiskType() throws Exception{
		String sql = "select distinct u.riskType,c.classname from UtiCodeTransfer u, prpdclass c where codetype='1' and u.validstatus='1' and u.outercode=c.classcode order by u.risktype";
		List <Object[]> list = this.getSession().createSQLQuery(sql).list();
		Map<String,String> map = new LinkedHashMap<String,String>();
		if(list!=null&&list.size()>0){
			Object [] obj = null;
			for(int i=0;i<list.size();i++){
				obj = list.get(i);
				if(obj[0]==null){
					continue;
				}
				if("D".equals(obj[0])){
					map.put(String.valueOf(obj[0]), "車險");
				}else{
					map.put(String.valueOf(obj[0]),String.valueOf(obj[1]));
				}
			}
		}
		return map;
	}

}
