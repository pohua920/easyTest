package com.sinosoft.claim.schema.service.spring;

/**
 * 单证号使用登记接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;
import java.util.List;
import com.sinosoft.claim.schema.model.PrpMaxUse;
import com.sinosoft.claim.schema.model.PrpMaxUseId;
import com.sinosoft.claim.schema.service.facade.PrpMaxUseService;
import com.sinosoft.sysframework.reference.AppConfig;
import com.sinosoft.sysframework.reference.DBManager;

public class PrpMaxUseServiceSpringImpl extends GenericDaoHibernate<PrpMaxUse, PrpMaxUseId> implements PrpMaxUseService {

	@Override
	public void save(PrpMaxUse prpMaxUse) throws Exception {
		logger.info("保存单证号使用登记信息");
		super.save(prpMaxUse);

	}
	/**
	 * 保存单证号使用登记信息
	 * @param prpMaxUse ：传入的单证号使用登记
	 */
	public void saveByNewTransaction(PrpMaxUse prpMaxUse) throws Exception {
		logger.info("保存单证号使用登记信息");
		DBManager dbManager = new DBManager();
		try {
			dbManager.open(AppConfig.get("sysconst.DBJNDI"));
			String statement = " Insert Into PrpMaxUse( GroupNo, MaxNo, TableName, TtyCode, Flag) values(?,?,?,?,?)";
			dbManager.prepareStatement(statement);
			dbManager.setString(1, prpMaxUse.getId().getGroupNo());
			dbManager.setString(2, prpMaxUse.getId().getMaxNo());
			dbManager.setString(3, prpMaxUse.getId().getTableName());
			dbManager.setString(4, prpMaxUse.getTtyCode());
			dbManager.setString(5, prpMaxUse.getFlag());
			dbManager.executePreparedUpdate();
		} finally {
			dbManager.close();
		}
	}
	/**
	 * 删除单证号使用登记信息
	 * @param prpMaxUseId ：传入的单证号使用登记编号
	 */
	public void deleteByNewTransaction(PrpMaxUseId prpMaxUseId) throws Exception {
		logger.info("删除单证号使用登记编号为" + prpMaxUseId + "的单证号使用登记");
		DBManager dbManager = new DBManager();
		try {
			dbManager.open(AppConfig.get("sysconst.DBJNDI"));
			String statement = " Delete From PrpMaxUse Where  GroupNo = ? And  MaxNo = ? And  TableName = ?";
			dbManager.prepareStatement(statement);
			dbManager.setString(1, prpMaxUseId.getGroupNo());
			dbManager.setString(2, prpMaxUseId.getMaxNo());
			dbManager.setString(3, prpMaxUseId.getTableName());
			dbManager.executePreparedUpdate();
		} finally {
			dbManager.close();
		}
	}

	@Override
	public void save(List<PrpMaxUse> list) throws Exception {
		logger.info("保存单证号使用登记");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpMaxUseId prpMaxUseId) throws Exception {
		logger.info("删除单证号使用登记编号为" + prpMaxUseId + "的单证号使用登记");
		super.deleteByPK(PrpMaxUse.class, prpMaxUseId);
	}

	@Override
	public PrpMaxUse findPrpMaxUse(PrpMaxUseId prpMaxUseId) throws Exception {
		logger.info("查询单证号使用登记编号为" + prpMaxUseId + "的单证号使用登记");
		return super.get(PrpMaxUse.class, prpMaxUseId);
	}

	@Override
	public Page findPrpMaxUse(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取单证号使用登记列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpMaxUse> findPrpMaxUse(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}
}
