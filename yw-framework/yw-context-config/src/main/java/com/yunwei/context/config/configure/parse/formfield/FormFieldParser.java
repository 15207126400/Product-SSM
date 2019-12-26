package com.yunwei.context.config.configure.parse.formfield;

import com.yunwei.context.config.configure.element.FormField;
import com.yunwei.context.config.configure.parser.HTMLFragmentParser;



public interface FormFieldParser<G extends FormField<?, ?>> extends HTMLFragmentParser<G> {
    
    public static final String COMMON_FORM_FIELD_TYPE = "";// 除了select/radio/checkbox以外的validator
    
}
