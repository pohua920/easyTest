package com.sinosoft.undwrt.undwrtBase.service.spring;

import ins.framework.dao.GenericDaoHibernate;

import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.undwrt.pub.InternationalizationUtil;
import com.sinosoft.undwrt.undwrtBase.model.WfPackage;
import com.sinosoft.undwrt.undwrtBase.model.WfPackageId;
import com.sinosoft.undwrt.undwrtBase.service.facade.WfPackageService;

/**
 * 工作流包信息實現類.
 */
public class WfPackageServiceSpringImpl extends GenericDaoHibernate implements WfPackageService {

	/** 屬性機構代碼. */
	private String comCode = "";

	/** 屬性工作流包信息ID. */
	private String wfPackageId = "";

	/**
	 * 保存工作流包信息.
	 * 
	 * @param WfPackage
	 *            工作流包信息類
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.WfPackageService#save(com.sinosoft.undwrt.undwrtBase.model.WfPackage)
	 */
	@Override
	public void save(WfPackage WfPackage) {
		super.save(WfPackage);
	}

	/**
	 * 創建工作流包信息表.
	 * 
	 * @param iModelNo
	 *            模板號
	 * @param iCertiType
	 *            業務類型
	 * @param iBusinessNo
	 *            業務號
	 * @param iComCode
	 *            機構代碼
	 * @return 工作流包信息ID
	 * @throws UserException
	 *             自定義異常
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.WfPackageService#create(int,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String create(int iModelNo, String iCertiType, String iBusinessNo, String iComCode) throws UserException, Exception {
		InternationalizationUtil internal = new InternationalizationUtil();
		this.comCode = iComCode;
		WfPackage wfPackageDto = new WfPackage();
		WfPackageId wfPackageId = new WfPackageId();
		wfPackageDto.setId(wfPackageId);
		try {
			this.wfPackageId = this.getSolePackageID(iBusinessNo);
			if (this.wfPackageId == null || this.wfPackageId.length() == 0) {
				throw new UserException(-98, -1128, "WfPackage.CompensateQ()", internal.getText("undwrt.service.wfPackage.obtainPackageID"));
			}
			wfPackageDto.getId().setPackageId(this.wfPackageId);
			wfPackageDto.getId().setDetailNo(1);
			wfPackageDto.setDetailContent(internal.getText("undwrt.service.wfPackage.leaveOut") + iBusinessNo
					+ internal.getText("undwrt.service.wfPackage.summaryInfor"));
			//this.save(wfPackageDto);
		} catch (UserException ue) {
			throw ue;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return this.wfPackageId;

	}

	/**
	 * 根據部門和時間生成信息包號.
	 * 
	 * @param comCode
	 *            歸屬機構代碼
	 * @return 屬性包號的值
	 * @throws UserException
	 *             自定義異常
	 * @throws Exception
	 *             異常
	 */
	public String getSolePackageID(String comCode) throws UserException, Exception {
		String wfPackageID = "";
		String currentTime = new DateTime().current().toString();
		String currentYear = currentTime.substring(0, 4);
		String currentMonth = currentTime.substring(5, 7);
		String currentDay = currentTime.substring(8, 10);
		String currentHour = currentTime.substring(11, 13);
		String currentMinute = currentTime.substring(14, 16);
		String currentSecond = currentTime.substring(17, 19);
		String currentMM = currentTime.substring(20, 23);
		wfPackageID = comCode.substring(8) + currentYear + currentMonth + currentDay + 
				currentHour + currentMinute + currentSecond + currentMM + 
				(int)(Math.random()*100) + (int)(Math.random()*100);
		return wfPackageID;

	}

	/**
	 * 獲取屬性機構代碼.
	 * 
	 * @return 屬性機構代碼的值
	 */
	public String getComCode() {
		return comCode;
	}

	/**
	 * 設置屬性機構代碼.
	 * 
	 * @param comCode
	 *            待設置的機構代碼的值
	 */
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	/**
	 * 獲取屬性工作流包信息ID.
	 * 
	 * @return 屬性工作流包信息ID的值
	 */
	public String getWfPackageIDd() {
		return wfPackageId;
	}

	/**
	 * 設置屬性工作流包信息ID.
	 * 
	 * @param wfPackageID
	 *            待設置的工作流包信息ID的值
	 */
	public void setWfPackageId(String wfPackageID) {
		this.wfPackageId = wfPackageID;
	}

}
