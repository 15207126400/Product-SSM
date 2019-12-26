package com.yunwei.context.config.configure.formfield.restriction;



public class RadioOption extends Option {
    
    @Override
    protected String getRelativeVmPath() {
        return "formfield/restriction/radiooption.vm";
    }
    
    @Override
    public String toString() {
        return "RadioOption [description=" + description + ", related=" + related + ", value=" + value + ", id="
                + id + ", name=" + name + ", label=" + label + ", define=" + define + "]";
    }
    
}
