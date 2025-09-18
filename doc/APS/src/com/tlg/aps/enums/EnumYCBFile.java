package com.tlg.aps.enums;

/**
 * 元大續保產檔清單
 * 
 * FIR0690、FIR0693	 CF048住火	住火_APS_元大續保作業Ph2_N+1_產生保單副本&N+1產生出單明細檔 
 * @author CF048
 */
public enum EnumYCBFile {	
	RENEW("RN", "txt", "selectYcbRnFileData"),			//續保明細檔
	ENDNOTICE("EN", "xlsx", "selectYcbEnFileData"),		//到期通知
	POLICY("PO", "xlsx", "selectYcbPoFileData"),		//出單明細檔 xlsx 
	POLICYCOPY( "CO", "xlsx", "selectYcbCoFileData");	//保單副本檔 xlsx --> user產xlsx後另存text

    private final String fileType;		//檔案類別 PO、CO 
    private final String extension;		//副檔名
    private final String selectId;		//FirAgtTocoreMain.selectId (sql content)

    /**
     * constructor
     * @param fileType
     * @param extension
     * @param selectId
     */
    private EnumYCBFile(String fileType, String extension, String selectId ) {
    	this.fileType = fileType;
    	this.extension = extension;
    	this.selectId = selectId; 
 	}
    
    /**
     * getExtension
     * @return String extension 
     */
	public String getExtension() {
		return extension;
	}
	
	/**
	 * getFileType
	 * @return String  fileType 
	 */
	public String getFileType() {
		return fileType;
	}

	/**
	 * getSelectId
	 * @return String  selectId 
	 */
	public String getSelectId() {
		return selectId;
	}
	 
}
