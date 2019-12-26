package com.yunwei.context.config.configure.element;


import java.util.Map;

import com.yunwei.context.config.configure.formfield.InputFormField;



public class Property extends Block<Property, FormField<?, ?>> {
    
    private String defaultValue;
    
    private String description = "";
    
    private Map<Object, Object> dataStore;
    
    public String getValue() {
        if (null != dataStore) {
            if (dataStore.containsKey(name)) {
                Object value = dataStore.get(name);
                if (null != value) {
                    return value.toString();
                } else {
                    return "";
                }
            }
        }
        return defaultValue;
    }
    
    @Override
    public void addFragment(FormField<?, ?> fragment) {
        if (1 == fragments.size()) {
            throw new IllegalArgumentException("property can only have one form field");
        }
        super.addFragment(fragment);
        fragment.setProperty(this);
    }
    
    public String getDefaultValue() {
        return defaultValue;
    }
    
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        if (null != description) {
            this.description = description;
        }
    }
    
    public void setDataStore(Map<Object, Object> dataStore) {
        this.dataStore = dataStore;
    }
    
    public boolean isInDataStore() {
        return null != dataStore && !dataStore.containsKey(name);
    }
    
    public void fixNoFormField() {
        if (fragments.isEmpty()) {
            addFragment(new InputFormField());
        }
    }
    
    @Override
    protected String getRelativeVmPath() {
        return "property.vm";
    }
    
    @Override
    public String toString() {
        return "Property [defaultValue=" + defaultValue + ", description=" + description + ", display=" + display
                + ", fragments=" + fragments + ", id=" + id + ", name=" + name + ", label=" + label + ", define="
                + define + "]";
    }
    
}
