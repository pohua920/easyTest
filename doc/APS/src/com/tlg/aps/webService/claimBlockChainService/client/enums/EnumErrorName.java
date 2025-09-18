package com.tlg.aps.webService.claimBlockChainService.client.enums;


/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 */
public enum EnumErrorName {
	UNAUTHORIZEDERROR("UnauthorizedError"), PARAMSERROR("ParamsError"), NOTFOUNDERROR(
			"NotFoundError"), FORBIDDENERROR("ForbiddenError"), EXTERNALAPIERROR(
			"ExternalAPIError"), FABRICNETWORKERROR("FabricNetworkError"), UNKNOWNERROR(
			"UnknownError"), CHAINCODEERROR("ChaincodeError"), DBERROR(
			"DbError");

	private String value;

	EnumErrorName(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	public static EnumErrorName fromValue(String text) {
		for (EnumErrorName b : EnumErrorName.values()) {
			if (String.valueOf(b.value).equals(text)) {
				return b;
			}
		}
		return null;
	}
}
