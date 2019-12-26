package com.yunwei.context.config.configure.formfield;

import com.yunwei.context.config.configure.element.FormField;
import com.yunwei.context.config.configure.formfield.restriction.BasicValidator;



public class InputFormField extends FormField<InputFormField, BasicValidator> {
	
    @Override
    public String getType() {
    	if(property.getId() != null){
    		String preName = property.getId().trim();
    		if("password".equals(preName)){
    			return "password";
    		}
    	}
        return DISPLAY_HIDE.equals(property.getDisplay()) ? "hidden" : "text";
    }
    
    @Override
    protected String getRelativeVmPath() {
        return "formfield/input.vm";
    }
    
    @Override
    public String toString() {
        return "InputFormField [display=" + display + ", fragments=" + fragments + ", id=" + id + ", name=" + name
                + ", label=" + label + ", define=" + define + "]";
    }
    
}
