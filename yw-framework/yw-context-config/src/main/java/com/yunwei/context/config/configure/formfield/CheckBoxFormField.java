package com.yunwei.context.config.configure.formfield;

import com.yunwei.context.config.configure.element.FormField;
import com.yunwei.context.config.configure.formfield.restriction.RadioOption;



public class CheckBoxFormField extends FormField<CheckBoxFormField, RadioOption> {
    
    @Override
    public String getType() {
        return "checkbox";
    }
    
    @Override
    public void addFragment(RadioOption fragment) {
        if (2 == fragments.size()) {
            throw new IllegalArgumentException("options of checkbox can not be more then 2");
        }
        super.addFragment(fragment);
    }
    
    public boolean isChecked() {
        return !fragments.isEmpty() && fragments.get(0).getValue().equals(getProperty().getValue());
    }
    
    @Override
    protected String getRelativeVmPath() {
        return "formfield/checkbox.vm";
    }
    
    @Override
    public String toString() {
        return "CheckBoxFormField [display=" + display + ", fragments=" + fragments + ", id=" + id + ", name="
                + name + ", label=" + label + ", define=" + define + "]";
    }
    
}
