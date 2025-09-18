package com.tlg.aps.bs.miTerminationNoticeService;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;
import com.tlg.xchg.entity.MobileInsSms;

public interface MobileInsSmsNewTransService {

	public Result updateMobileInsSms(MobileInsSms mobileInsSms) throws SystemException, Exception;
}
