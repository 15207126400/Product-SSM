package com.yunwei.context.config.configure;


import java.util.List;


/**
 * 配置文件分类
 */
public class ConfigureCatalog {
    private String  id;
    private String  name;
    private String  description;
    private String config;
    private List<Generate> templates;
    //配置状态  0-未配置  1-有更新  2-相同
    private int status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public List<Generate> getTemplates() {
        return templates;
    }

    public void setTemplates(List<Generate> templates) {
        this.templates = templates;
    }

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
