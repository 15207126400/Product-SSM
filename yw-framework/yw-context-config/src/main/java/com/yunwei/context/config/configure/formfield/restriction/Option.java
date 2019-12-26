package com.yunwei.context.config.configure.formfield.restriction;

import org.apache.commons.lang3.StringUtils;

import com.yunwei.context.config.configure.element.FormFieldRestriction;

public class Option extends FormFieldRestriction {

	protected String description;

	protected String related;

	protected boolean checked;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRelated() {
		return related;
	}

	public void setRelated(String related) {
		this.related = related;
	}

	public boolean getChecked() {
		return isChecked();
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isChecked() {
		if(StringUtils.isEmpty(value)){
			return false;
		}
		if (StringUtils.isEmpty(getProperty().getValue())) {
			return value.equals(getProperty().getDefaultValue());
		} else {
			return value.equals(getProperty().getValue());
		}
	}

	@Override
	protected String getRelativeVmPath() {
		return "formfield/restriction/option.vm";
	}

	@Override
	public String toString() {
		return "Option [description=" + description + ", related=" + related + ", value=" + value + ", id=" + id + ", name=" + name + ", label=" + label + ", define=" + define + "]";
	}

}
