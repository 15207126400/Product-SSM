package com.yunwei.context.config.configure.parse.formfield;

import com.yunwei.context.config.configure.formfield.SelectFormField;
import com.yunwei.context.config.configure.parser.ConfigureParser;



public class SelectFormFieldParser extends SingleValidatorFormFieldParser<SelectFormField> {
    
    public SelectFormFieldParser(ConfigureParser configureParser) {
        super(configureParser);
    }
    
    @Override
    public String parseFor() {
        return "select";
    }
    
    @Override
    protected SelectFormField createFormField() {
        return new SelectFormField();
    }
    
}
