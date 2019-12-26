package com.yunwei.context.config.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.SelectProvider;

import com.yunwei.context.config.model.Autocomplete;





public interface AutocompleteMapper {

	@SelectProvider(method = "getResultList", type = AutocompleteProvider.class)
	public List<Autocomplete> getResultList(Map<String,String> map);
}
