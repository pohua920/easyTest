package cn.com.sinosoft.intf.image.model.vo;

import java.util.ArrayList;
import java.util.List;

public class OTHER_DATA {
	
	private WRITEBACK_META_DATA writeBack_meta_data;

	private List<String> imgTypes = new ArrayList<String>();
	
	private List<String> itemcodes = new ArrayList<String>();;

	public WRITEBACK_META_DATA getWriteBack_meta_data() {
		return writeBack_meta_data;
	}

	public void setWriteBack_meta_data(WRITEBACK_META_DATA writeBackMetaData) {
		writeBack_meta_data = writeBackMetaData;
	}

	public List<String> getImgTypes() {
		return imgTypes;
	}

	public void setImgTypes(List<String> imgTypes) {
		this.imgTypes = imgTypes;
	}

	public List<String> getItemcodes() {
		return itemcodes;
	}

	public void setItemcodes(List<String> itemcodes) {
		this.itemcodes = itemcodes;
	}
	
	
}
