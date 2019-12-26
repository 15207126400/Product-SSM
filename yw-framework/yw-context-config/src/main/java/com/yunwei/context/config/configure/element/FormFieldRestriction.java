package com.yunwei.context.config.configure.element;



public abstract class FormFieldRestriction extends
        AbstractLeafHTMLFragment<FormFieldRestriction, HTMLFragment<?, ?>> {
    
    protected String value;
    
    protected Property property;
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
    public Property getProperty() {
        return property;
    }
    
    public void setProperty(Property property) {
        this.property = property;
    }
    
}
