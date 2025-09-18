package com.tlg.aps.webService.claimBlockChainService.client.enums;


/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 * Gets or Sets AppErrorCode
 */
public enum EnumAppErrorCode {
	AUTH_0000("AUTH_0000"), AUTH_0001("AUTH_0001"), AUTH_0002("AUTH_0002"), AUTH_0003(
			"AUTH_0003"), AUTH_0004("AUTH_0004"), AUTH_0005("AUTH_0005"), PARAM_0000(
			"PARAM_0000"), PARAM_0001("PARAM_0001"), PARAM_INVALID_FIELD_CASE_0001(
			"PARAM_INVALID_FIELD_CASE_0001"), PARAM_INVALID_FIELD_CASE_0002(
			"PARAM_INVALID_FIELD_CASE_0002"), NOTFOUND_0000("NOTFOUND_0000"), NOTFOUND_0001(
			"NOTFOUND_0001"), FORBID_0000("FORBID_0000"), FORBID_CASE_0001(
			"FORBID_CASE_0001"), FORBID_0001("FORBID_0001"), EXTERNAL_0000(
			"EXTERNAL_0000"), EXTERNAL_0001("EXTERNAL_0001"), EXTERNAL_0002(
			"EXTERNAL_0002"), CHAINCODE_0000("CHAINCODE_0000"), CHAINCODE_0001(
			"CHAINCODE_0001"), CHAINCODE_0002("CHAINCODE_0002"), CHAINCODE_0003(
			"CHAINCODE_0003"), CHAINCODE_0004("CHAINCODE_0004"), CHAIN_0000(
			"CHAIN_0000"), CHAIN_0001("CHAIN_0001"), CHAIN_0002("CHAIN_0002"), CHAIN_0003(
			"CHAIN_0003") , DB_0000("DB_0000"), DB_0001("DB_0001"), DB_0002(
			"DB_0002"), DB_0003("DB_0003"), DB_0004("DB_0004");

	private String value;

	EnumAppErrorCode(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	public static EnumAppErrorCode fromValue(String text) {
		for (EnumAppErrorCode b : EnumAppErrorCode.values()) {
			if (String.valueOf(b.value).equals(text)) {
				return b;
			}
		}
		return null;
	}

//	public static class Adapter extends TypeAdapter<AppErrorCode> {
//		@Override
//		public void write(final JsonWriter jsonWriter,
//				final AppErrorCode enumeration) throws IOException {
//			jsonWriter.value(enumeration.getValue());
//		}
//
//		@Override
//		public AppErrorCode read(final JsonReader jsonReader)
//				throws IOException {
//			String value = jsonReader.nextString();
//			return AppErrorCode.fromValue(String.valueOf(value));
//		}
//	}
}
