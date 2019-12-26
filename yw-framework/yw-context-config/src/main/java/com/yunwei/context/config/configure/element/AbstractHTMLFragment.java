package com.yunwei.context.config.configure.element;


import java.util.ArrayList;
import java.util.List;


public abstract class AbstractHTMLFragment<S extends AbstractHTMLFragment<?, ?>, C extends HTMLFragment<?, ?>>
        extends AbstractLeafHTMLFragment<S, C> {
    
    protected List<C> fragments = new ArrayList<C>();
    
    @Override
    public void addFragment(C fragment) {
        fragments.add(fragment);
    }
    
    public List<C> getFragments() {
        return fragments;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public S clone() {
        AbstractHTMLFragment<S, C> newInstance = (AbstractHTMLFragment<S, C>) super.clone();
        newInstance.fragments = new ArrayList(fragments.size());
        for (HTMLFragment fragment : fragments) {
            C fragmentClone = (C) fragment.clone();
            newInstance.addFragment(fragmentClone);
        }
        return (S) newInstance;
    }
    
}
