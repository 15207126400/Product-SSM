package com.yunwei.context.sys.cache;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yunwei.common.cache.ICache;
import com.yunwei.context.sys.model.YwDictionary;
import com.yunwei.context.sys.service.YwDictionaryService;

@Component("ywDictionaryCache")
public class YwDictionaryCache implements ICache<YwDictionary> {

	private static Map<String,YwDictionary> configData= new LinkedHashMap<String, YwDictionary>();
	
	@Autowired
	private YwDictionaryService ywDictionaryService;
	
	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public void refresh() throws Exception {
		List<YwDictionary> dictionaries = ywDictionaryService.queryList(new YwDictionary());
		if(!CollectionUtils.isEmpty(dictionaries)){
			configData.clear();
			for(YwDictionary dictionary : dictionaries){
				configData.put(dictionary.getDic_key()+"-"+dictionary.getDic_subkey(), dictionary);
			}
		}
	}

	@Override
	public String getId() {
		return "ywDictionaryCache";
	}

	@Override
	public Map<String, YwDictionary> getConfigData() {
		return configData;
	}

	/**
	 * 获取字典子项集合
	 * @param dic_key
	 * @return
	 */
	public List<YwDictionary> getDictionaryList(String dic_key){
		List<YwDictionary> dictionaries = new ArrayList<YwDictionary>();
		if(!configData.isEmpty()){
			for(Entry<String, YwDictionary> entry : configData.entrySet()){
				YwDictionary dictionary = entry.getValue();
				if(StringUtils.equals(dictionary.getDic_key(), dic_key)){
					dictionaries.add(dictionary);
				}
			}
		}
		return dictionaries;
		
	}
	
	/**
	 * 根据字典项和字典子项获取值
	 * @param dic_key
	 * @param dic_subkey
	 * @return
	 */
	public YwDictionary getDictionary(String dic_key,String dic_subkey){
		return configData.get(dic_key+"-"+dic_subkey);
	}
	
	/**
	 * 根据字典项和字典子项值获取值
	 * @param dic_key
	 * @param dic_subvalue
	 * @return
	 */
	public YwDictionary getDictionaryBySubvalue(String dic_key,String dic_subvalue){
		List<YwDictionary> dictionaries = this.getDictionaryList(dic_key);
		YwDictionary dictionary = null;
		if(CollectionUtils.isNotEmpty(dictionaries)){
			for(YwDictionary ywDictionary : dictionaries){
				if(StringUtils.equals(ywDictionary.getDic_subvalue(), dic_subvalue)){
					dictionary = ywDictionary;
					break;
				}
			}
		}
		return dictionary;
	}
	
	
}
