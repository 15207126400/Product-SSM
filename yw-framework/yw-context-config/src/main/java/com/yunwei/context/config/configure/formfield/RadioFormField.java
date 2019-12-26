package com.yunwei.context.config.configure.formfield;

import com.yunwei.context.config.configure.element.FormField;
import com.yunwei.context.config.configure.formfield.restriction.RadioOption;


public class RadioFormField extends FormField<RadioFormField, RadioOption> {
    
    @Override
    public String getType() {
        return "radio";
    }
    
    @Override
    protected String getRelativeVmPath() {
        return "formfield/radio.vm";
    }
    
    @Override
    public String toString() {
        return "RadioFormField [display=" + display + ", fragments=" + fragments + ", id=" + id + ", name=" + name
                + ", label=" + label + ", define=" + define + "]";
    }
    
}
