package com.tlg.aps.webService.claimBlockChainService.client.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 * 檔案內容
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompulsoryUpdateFilesVo {

	/**
	 * 既有檔案的 id，就算再在沒有更新的時候還是會傳到後端
	 */
	@JsonProperty("id")
	private Double id;

	/**
	 * 既有檔案的 URL，就算再在沒有更新的時候還是會傳到後端
	 */
	@JsonProperty("url")
	private String url;

	/**
	 * 檔案識別碼
	 */
	@JsonProperty("uuid")
	private String uuid;

	/**
	 * 檔案名稱
	 */
	@JsonProperty("name")
	private String name;

	public Double getId() {
		return id;
	}

	public void setId(Double id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
