package com.sinosoft.undwrt.common.service.spring;

import java.util.Collection;
import java.util.List;

import org.hibernate.FlushMode;

import com.sinosoft.sysframework.reference.DBManager;
import com.sinosoft.undwrt.common.service.facade.WfMessageService;
import com.sinosoft.undwrt.undwrtBase.model.WfLog;
import com.sinosoft.undwrt.undwrtBase.model.WfMessage;
import com.sinosoft.undwrt.undwrtBase.model.WfMessageId;

import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

/**
 * 備注訊息實現類.
 */
public class WfMessageSpringImpl extends GenericDaoHibernate<WfMessage,WfMessageId> implements WfMessageService{

	/**
	 * 根據條件查詢備註信息.
	 *
	 * @param conditions 查詢條件
	 * @return 滿足條件的集合
	 * @throws Exception 異常
	 * @see com.sinosoft.undwrt.common.service.facade.WfMessageService#findByConditions(java.lang.String)
	 */
	@Override
	public List<WfMessage> findByConditions(String conditions) throws Exception {
		
		return (List<WfMessage> )super.getSession().createSQLQuery(conditions).addEntity(WfMessage.class).list();
	}

	/**
	 * 得到某條記錄最大的留言序號.
	 *
	 * @param queryRule 查詢規則
	 * @return 某條記錄的所有留言集合
	 * @throws Exception 異常
	 * @see com.sinosoft.undwrt.common.service.facade.WfMessageService#getMaxSerialNo(ins.framework.common.QueryRule)
	 */
	@Override
	public Collection getMaxSerialNo(QueryRule queryRule) throws Exception {
        		
		return super.find(queryRule);
	}

	/**
	 * 保存備註訊息.
 	 *
 	 * @param wfMessageDto 備註訊息類
 	 * @param dbManager 數據管理對象
 	 * @throws Exception 異常
	 * @see com.sinosoft.undwrt.common.service.facade.WfMessageService#saveMessage(com.sinosoft.undwrt.undwrtBase.model.WfMessage,
	 *      com.sinosoft.sysframework.reference.DBManager)
	 */
	@Override
    public void saveMessage(WfMessage wfMessageDto,DBManager dbManager) throws Exception{
        String mainStatement = " Insert Into WfMessage (" +
                           " MessageID," +
                           " BusinessNo," +
                           " SerialNo," +
                           " LineNo," +
                           " Context," +
                           " OperateTime," +
                           " OperatorCode," +
                           " OperatorName," +
                           " Flag)";
        String statement = mainStatement + " values(?,?,?,?,?,?,?,?,?)";
        if(logger.isDebugEnabled()){
            String debugStatement = mainStatement + " values(" +
                           "'" + wfMessageDto.getWfMessageId().getMessageId() + "'," +
                           "'" + wfMessageDto.getBusinessNo() + "'," +
                           "" + wfMessageDto.getWfMessageId().getSerialNo() + "," +
                           "" + wfMessageDto.getWfMessageId().getLineNo()+ "," +
                           "'" + wfMessageDto.getContext() + "'," +
                           "'" + wfMessageDto.getOperateTime() + "'," +
                           "'" + wfMessageDto.getOperatorCode() + "'," +
                           "'" + wfMessageDto.getOperatorName() + "'," +
                           "'" + wfMessageDto.getFlag() + "')";
            logger.debug(debugStatement);
        }

        dbManager.prepareStatement(statement);
        dbManager.setString(1,wfMessageDto.getWfMessageId().getMessageId());
        dbManager.setString(2,wfMessageDto.getBusinessNo());
        dbManager.setInt(3,wfMessageDto.getWfMessageId().getSerialNo());
        dbManager.setInt(4,wfMessageDto.getWfMessageId().getLineNo());
        dbManager.setString(5,wfMessageDto.getContext());
        dbManager.setString(6,wfMessageDto.getOperateTime());
        dbManager.setString(7,wfMessageDto.getOperatorCode());
        dbManager.setString(8,wfMessageDto.getOperatorName());
        dbManager.setString(9,wfMessageDto.getFlag());
        dbManager.executePreparedUpdate();

        logger.info("DBWfMessageBase.insert() success!");
    }
	
	@Override
	public WfMessage getUniqueMessage(QueryRule queryRule) throws Exception
	{
		return super.findUnique(queryRule);
	};

}
