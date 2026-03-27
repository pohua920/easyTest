package com.sinosoft.undwrt.undwrtBase.service.facade;

import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.undwrt.undwrtBase.model.WfPackage;

// TODO: Auto-generated Javadoc
/**
 * 工作流包信息接口類.
 */
public interface WfPackageService {

	/**
	 * 保存工作流包信息.
	 * 
	 * @param WfPackage
	 *            工作流包信息類
	 */
	public void save(WfPackage WfPackage);

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
	 * @return 工作流包信息類的packageid
	 * @throws UserException
	 *             自定義異常
	 * @throws Exception
	 *             異常
	 */
	public String create(int iModelNo, String iCertiType, String iBusinessNo,
			String iComCode) throws UserException, Exception;
}
