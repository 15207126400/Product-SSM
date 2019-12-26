package com.yunwei.context.config.configure.formfield;

import com.yunwei.context.config.configure.element.FormField;
import com.yunwei.context.config.configure.formfield.restriction.Option;


public class SelectFormField extends FormField<SelectFormField, Option> {
    
    @Override
    public String getType() {
        return "select";
    }
    
    @Override
    protected String getRelativeVmPath() {
        return "formfield/select.vm";
    }
    
    @Override
    public String toString() {
        return "SelectFormField [display=" + display + ", fragments=" + fragments + ", id=" + id + ", name=" + name
                + ", label=" + label + ", define=" + define + "]";
    }
    
}
