package com.yunwei.context.config.configure.element;



public interface HTMLFragment<S extends HTMLFragment<?, ?>, C extends HTMLFragment<?, ?>> extends Cloneable {
    
    public boolean isDefine();
    
    public void addFragment(C fragment);
    
    public String toHTML();
    
    public S clone();
    
}
