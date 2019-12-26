package com.yunwei.context.config.dao;

import java.util.Map;

public class AutocompleteProvider {

	public String getResultList(Map<String, String> map) {
		return new StringBuilder("SELECT a.VALUE FROM (SELECT DISTINCT ").append(map.get("column")).append(" AS VALUE,INSTR(").append(map.get("column")).append(",'").append(map.get("query"))
				.append("') AS IDX FROM ").append(map.get("tableName")).append(" WHERE ").append(map.get("column")).append(" LIKE '%").append(map.get("query"))
				.append("%' ORDER BY IDX) a limit ").append(map.get("size")).toString();
	}
}
