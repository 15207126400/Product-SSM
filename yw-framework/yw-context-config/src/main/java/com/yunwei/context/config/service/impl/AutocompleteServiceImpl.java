package com.yunwei.context.config.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwei.context.config.dao.AutocompleteMapper;
import com.yunwei.context.config.model.Autocomplete;
import com.yunwei.context.config.service.IAutocompleteService;



@Service
public class AutocompleteServiceImpl implements IAutocompleteService {

	@Autowired
	private AutocompleteMapper autocompleteMapper;
	
	@Override
	public List<Autocomplete> getResultList(String query, String size, String tableName, String column) {
		if (StringUtils.isNotBlank(query)) {
			try {
				query = new String(query.getBytes("iso8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		if (StringUtils.isBlank(tableName) || StringUtils.isBlank(column)) {
			return null;
		}
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("query", query);
		map.put("size", size);
		map.put("tableName", tableName);
		map.put("column", column);
		return autocompleteMapper.getResultList(map);
	}

}
