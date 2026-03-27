package com.sinosoft.claim.undwrt.service.facade;

import java.util.Map;

import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLprepay;

/***
 * 核赔业务数据组织处理接口
 * @author 陈杰
 *
 */
public interface BusinessDataService {
	/***
	 * 理算实赔
	 * @param prpLcompensate
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getBusinessDataMap(PrpLcompensate prpLcompensate) throws Exception;

	/***
	 * 预赔
	 * @param prpLprepay
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getBusinessDataMap(PrpLprepay prpLprepay) throws Exception;
}
