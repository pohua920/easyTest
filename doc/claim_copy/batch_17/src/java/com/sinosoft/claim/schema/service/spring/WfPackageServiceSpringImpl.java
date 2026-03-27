package com.sinosoft.claim.schema.service.spring;
/**
 * 工作流日志业务信息表接口
 * @author 中科软
 */
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;
import java.util.Random;

import com.sinosoft.claim.schema.model.WfPackage;
import com.sinosoft.claim.schema.model.WfPackageId;
import com.sinosoft.claim.schema.service.facade.WfPackageService;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.exceptionlog.UserException;

public class WfPackageServiceSpringImpl extends GenericDaoHibernate<WfPackage, WfPackageId> implements WfPackageService{

    private String comCode = "";
    private String wfPackageId = "";
    
    @Override
    public void save(WfPackage WfPackage) {
        super.save(WfPackage);
    }
    
    @Override
    public String create(int iModelNo, String iCertiType, String iBusinessNo, String iComCode) throws UserException, Exception {
        this.comCode = iComCode;
        WfPackage wfPackageDto = new WfPackage();
        WfPackageId  wfPackageId = new WfPackageId();
        wfPackageDto.setId(wfPackageId);
        try {
            this.wfPackageId = this.getSolePackageID(this.comCode);
            if (this.wfPackageId == null || this.wfPackageId.length() == 0) {
                throw new UserException(-98, -1128, "WfPackage.CompensateQ()", "獲取 PackageID 失敗[100]!");
            }
            wfPackageDto.getId().setPackageId(this.wfPackageId);
            wfPackageDto.getId().setDetailNo(1);
            wfPackageDto.setDetailContent("省略 " + iBusinessNo + " 的摘要信息");
            this.save(wfPackageDto);
        } catch (UserException ue) {
            throw ue;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return this.wfPackageId;
    
    }
    
    /**
     * 根据部门和时间生成信息包号
     * @param int iModelno 模版号
     * @param String iBusinessno 业务号 throws UserException,Exception
     */
    public String getSolePackageID (String comCode)
            throws UserException, Exception {
       String wfPackageID = "";
       String currentTime = DateTime.current().toString();
       String currentYear = currentTime.substring(2,4);
       String currentMonth = currentTime.substring(5,7);
       String currentDay = currentTime.substring(8,10);
       String currentHour = currentTime.substring(11,13);
       String currentMinute = currentTime.substring(14,16);
       String currentSecond = currentTime.substring(17,19);
       String currentMM = currentTime.substring(20,23);
       String random = String.valueOf(new Random().nextInt(100));
       random = com.sinosoft.sysframework.common.util.StringUtils.newString("0",2-random.length())+random;
       wfPackageID = comCode+currentYear+currentMonth+currentDay+currentHour+currentMinute+currentSecond+currentMM+random;
       return wfPackageID;

    }
    
    public String getComCode() {
        return comCode;
    }
    public void setComCode(String comCode) {
        this.comCode = comCode;
    }
    public String getWfPackageIDd() {
        return wfPackageId;
    }
    public void setWfPackageId(String wfPackageID) {
        this.wfPackageId = wfPackageID;
    }

	@Override
	public void delete(WfPackageId wfPackageId) throws Exception {
		super.deleteByPK(wfPackageId);
		logger.info("删除工作流日志业务编号为" + wfPackageId + "的工作流日志业务信息");
	}

	@Override
	public WfPackage findWfPackage(WfPackageId wfPackageId) throws Exception {
		logger.info("查询工作流日志业务编号为" + wfPackageId + "的工作流日志业务信息");
		return super.get(WfPackage.class,wfPackageId);
	}

	@Override
	public void save(List<WfPackage> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	@Override
	public void update(WfPackage wfPackage) {
		logger.info("修改工作流日志业务信息开始");
		super.update(wfPackage);
		logger.info("修改工作流日志业务信息结束");
	}

}
