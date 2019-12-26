package com.yunwei.context.config.service;

import java.util.List;

import com.yunwei.context.config.model.Autocomplete;



public interface IAutocompleteService {
	public List<Autocomplete> getResultList(String query,String size,String tableName,String column);
}
