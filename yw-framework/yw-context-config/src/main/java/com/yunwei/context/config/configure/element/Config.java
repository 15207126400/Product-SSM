package com.yunwei.context.config.configure.element;



public class Config extends AbstractHTMLFragment<Config, Block<?, ?>> {
    
    @Override
    protected String getRelativeVmPath() {
        return "config.vm";
    }
    
    @Override
    public String toString() {
        return "Config [display=" + display + ", fragments=" + fragments + ", id=" + id + ", name=" + name
                + ", label=" + label + ", define=" + define + "]";
    }
    
}
