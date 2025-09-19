package com.tlg.util.cxfAdapter;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class MapAdapter extends XmlAdapter<MapConvertor, Map<String, Object>> {

	@Override
	public MapConvertor marshal(Map<String, Object> map) throws Exception {

		MapConvertor convertor = new MapConvertor();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			MapConvertor.MapEntry e = new MapConvertor.MapEntry(entry);
			convertor.addEntry(e);
		}
		return convertor;
	}

	@Override
	public Map<String, Object> unmarshal(MapConvertor map) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		for (MapConvertor.MapEntry e : map.getEntries()) {
			result.put(e.getKey(), e.getValue());
		}
		return result;
	}

}

//@SuppressWarnings("unchecked")
//public class MapAdapter extends XmlAdapter<String, HashMap> {
//	
//	@Override
//	public String marshal(HashMap map) throws Exception {
//		XStream xs = new XStream(new DomDriver());
//		return xs.toXML(map);
//	}
//
//	@Override
//	public HashMap unmarshal(String xmlData) throws Exception {
//		XStream xs = new XStream(new DomDriver());
//		return (HashMap) xs.fromXML(xmlData);
//	}
//}


