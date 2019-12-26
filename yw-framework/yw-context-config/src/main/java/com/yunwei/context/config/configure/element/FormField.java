package com.yunwei.context.config.configure.element;



public abstract class FormField<S extends FormField<?, ?>, C extends FormFieldRestriction> extends Block<S, C> {
    
    protected Property property;
    
    public abstract String getType();
    
    public String getFormFieldId() {
        return property.getId() + '_' + getType();
    }
    
    @Override
    public void addFragment(C fragment) {
        fragment.setProperty(property);
        super.addFragment(fragment);
    }
    
    public Property getProperty() {
        return property;
    }
    
    public void setProperty(Property property) {
        this.property = property;
        for (FormFieldRestriction restriction : fragments) {
            restriction.setProperty(property);
        }
    }
    
}
