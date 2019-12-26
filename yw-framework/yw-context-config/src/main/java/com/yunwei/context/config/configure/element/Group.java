package com.yunwei.context.config.configure.element;



public class Group<S extends Group<?, ?>, C extends Block<?, ?>> extends Block<S, C> {
    
    public boolean hasLabel() {
        return null != label && !label.isEmpty();
    }
    
    @Override
    protected String getRelativeVmPath() {
        return "group.vm";
    }
    
    @Override
    public String toString() {
        return "Group [display=" + display + ", fragments=" + fragments + ", id=" + id + ", name=" + name
                + ", label=" + label + ", define=" + define + "]";
    }
    
}
