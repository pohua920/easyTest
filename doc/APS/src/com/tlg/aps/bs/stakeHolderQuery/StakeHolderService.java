package com.tlg.aps.bs.stakeHolderQuery;

import com.tlg.aps.vo.StakeHolderVo;
import com.tlg.exception.SystemException;

public interface StakeHolderService {

	public StakeHolderVo queryStakeHolder(StakeHolderVo stakeHolderVo) throws SystemException,Exception;
}
