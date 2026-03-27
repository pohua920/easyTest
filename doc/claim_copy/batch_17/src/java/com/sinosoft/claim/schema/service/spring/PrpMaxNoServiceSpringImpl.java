package com.sinosoft.claim.schema.service.spring;

/**
 * 单证号接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import com.sinosoft.claim.schema.model.PrpMaxNo;
import com.sinosoft.claim.schema.model.PrpMaxNoId;
import com.sinosoft.claim.schema.service.facade.PrpMaxNoService;
import com.sinosoft.sysframework.reference.AppConfig;
import com.sinosoft.sysframework.reference.DBManager;

public class PrpMaxNoServiceSpringImpl extends GenericDaoHibernate<PrpMaxNo, PrpMaxNoId> implements PrpMaxNoService {
	@Override
	public void save(PrpMaxNo prpMaxNo) throws Exception {
		logger.info("保存单证号信息");
		super.save(prpMaxNo);

	}
	/**
	 * 保存单证号信息
	 * @param prpMaxNo ：传入的单证号
	 */
	//CLM0116，處理人員：Sam，需求單編號：CLM0116..新核心-傷害險修改理賠序號 start*/
	public boolean saveByNewTransaction(String oldNo , PrpMaxNo prpMaxNo) throws Exception {
		logger.info("保存单证号信息");
		DBManager dbManager = new DBManager();
		try {
			dbManager.open(AppConfig.get("sysconst.DBJNDI"));
			String statement = " SELECT COUNT(*) FROM PrpMaxNo WHERE groupNo =? AND tableName =?";
			dbManager.prepareStatement(statement);
			dbManager.setString(1, prpMaxNo.getId().getGroupNo());
			dbManager.setString(2, prpMaxNo.getId().getTableName());
			ResultSet resultSet = dbManager.executePreparedQuery();
			boolean insert = true;
			while (resultSet.next()) {
				int nowNumber = dbManager.getInt(resultSet, 1);
				if(nowNumber >0 ){
					insert = false;	
				}
				break;
			}
			if(insert){
				String sql = "insert into PrpMaxNo(groupNo,tableName,maxNo,flag) values (?,?,?,?)";
				dbManager.prepareStatement(sql);
				dbManager.setString(1, prpMaxNo.getId().getGroupNo());
				dbManager.setString(2, prpMaxNo.getId().getTableName());
				dbManager.setString(3, prpMaxNo.getId().getMaxNo());
				dbManager.setString(4, prpMaxNo.getFlag());
				dbManager.executePreparedUpdate();
				return true;
			}else{
				String sql = "update PrpMaxNo set maxNo = ? where groupNo = ? and tableName = ? and maxNo = ? ";
				dbManager.prepareStatement(sql);
				dbManager.setString(1, prpMaxNo.getId().getMaxNo());
				dbManager.setString(2, prpMaxNo.getId().getGroupNo());
				dbManager.setString(3, prpMaxNo.getId().getTableName());
				dbManager.setString(4, oldNo);
				int result = dbManager.executePreparedUpdate();
				if(result == 1){
					return true;	
				}else{
					return false;
				}
			}	
		} finally {
			dbManager.close();
		}
	}
	//CLM0116，處理人員：Sam，需求單編號：CLM0116..新核心-傷害險修改理賠序號 End*/
	
	/**
	 * 删除单证号信息
	 * @param prpMaxNoId ：传入的单证号编号
	 */
	public void deleteByNewTransaction(PrpMaxNoId prpMaxNoId) throws Exception {
		logger.info("删除单证号编号为" + prpMaxNoId + "的单证号");
		DBManager dbManager = new DBManager();
		try {
			dbManager.open(AppConfig.get("sysconst.DBJNDI"));
			String statement = " Delete From PrpMaxNo Where  GroupNo = ? And  MaxNo = ? And  TableName = ?";
			dbManager.prepareStatement(statement);
			dbManager.setString(1, prpMaxNoId.getGroupNo());
			dbManager.setString(2, prpMaxNoId.getMaxNo());
			dbManager.setString(3, prpMaxNoId.getTableName());
			dbManager.executePreparedUpdate();
		} finally {
			dbManager.close();
		}
	}
	@Override
	public void save(List<PrpMaxNo> list) throws Exception {
		logger.info("保存单证号");
		Iterator<PrpMaxNo> iterator = list.iterator();
		PrpMaxNo prpMaxNo = null;
		while (iterator.hasNext()) {
			prpMaxNo = new PrpMaxNo();
			prpMaxNo = (PrpMaxNo) iterator.next();
			PrpMaxNoId prpMaxNoId = new PrpMaxNoId();
			prpMaxNoId.setGroupNo(prpMaxNo.getId().getGroupNo());
			prpMaxNoId.setTableName(prpMaxNo.getId().getTableName());
			prpMaxNoId.setMaxNo(prpMaxNo.getId().getMaxNo());
			this.delete(prpMaxNoId);
			this.save(prpMaxNo);
		}
	}

	@Override
	public void delete(PrpMaxNoId prpMaxNoId) throws Exception {
		logger.info("删除单证号编号为" + prpMaxNoId + "的单证号");
		super.deleteByPK(PrpMaxNo.class, prpMaxNoId);
	}

	@Override
	public PrpMaxNo findPrpMaxNo(PrpMaxNoId prpMaxNoId) throws Exception {
		logger.info("查询单证号编号为" + prpMaxNoId + "的单证号");
		return super.get(PrpMaxNo.class, prpMaxNoId);
	}

	@Override
	public Page findPrpMaxNo(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取单证号列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpMaxNo> findPrpMaxNo(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}
	/**
	 * 从单号表PrpMaxNo获取最大和最小单号
	 * @param iGroupNo
	 * @param iTableName
	 * @return String[] String[1]:最大号MaxNo,String[2]:最小号MinNo,String[3]:记录数Count
	 * @throws Exception
	 * @throws SQLException
	 */
	public String[] findByNewTransaction(String groupNo, String tableName) throws Exception, SQLException {
		String[] strMaxMinNo = new String[] { "", "", "0" };
		DBManager dbManager = new DBManager();
		try {
			dbManager.open(AppConfig.get("sysconst.DBJNDI"));
			String statement = " SELECT MAX(MaxNo),MIN(MaxNo),COUNT(*) FROM PrpMaxNo WHERE groupNo =? AND tableName =?";
			dbManager.prepareStatement(statement);
			dbManager.setString(1, groupNo);
			dbManager.setString(2, tableName);
			ResultSet resultSet = dbManager.executePreparedQuery();
			strMaxMinNo[2] = "0";
			while (resultSet.next()) {
				strMaxMinNo[0] = dbManager.getString(resultSet, 1);
				strMaxMinNo[1] = dbManager.getString(resultSet, 2);
				strMaxMinNo[2] = dbManager.getString(resultSet, 3);
			}
		} finally {
			dbManager.close();
		}
		return strMaxMinNo;
	}
	

	/**
	 * 从单号表PrpMaxNo获取最大和最小单号
	 * @param iGroupNo
	 * @param iTableName
	 * @return String[] String[1]:最大号MaxNo,String[2]:最小号MinNo,String[3]:记录数Count
	 * @throws Exception
	 * @throws SQLException
	 */
	public String[] getMaxMinNo(String groupNo, String tableName) throws Exception, SQLException {
		String[] strMaxMinNo = new String[]{"","","0"};
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.groupNo", groupNo);
		queryRule.addEqual("id.tableName", tableName);
		queryRule.addAscOrder("id.maxNo");
		List<PrpMaxNo> list = this.find(queryRule);
		if(list!=null&&list.size()>0){
			strMaxMinNo[0] = list.get(list.size()-1).getId().getMaxNo();
			strMaxMinNo[1] = list.get(list.size()-1).getId().getMaxNo();
			strMaxMinNo[2] = String.valueOf(list.size());
		}
		return strMaxMinNo;
	}
}
