package com.yunwei.context.config.configure.formfield.restriction;

import com.yunwei.context.config.configure.element.FormFieldRestriction;



public class BasicValidator extends FormFieldRestriction {
    
    protected String type;
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    @Override
    protected String getRelativeVmPath() {
        return "formfield/restriction/basicvalidator.vm";
    }
    
    @Override
    public String toString() {
        return "BasicValidator [type=" + type + ", value=" + value + ", id=" + id + ", name=" + name + ", label="
                + label + ", define=" + define + "]";
    }
    
}
