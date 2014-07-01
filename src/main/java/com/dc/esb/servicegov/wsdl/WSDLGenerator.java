package com.dc.esb.servicegov.wsdl;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-6-16
 * Time: 下午3:38
 */
public interface WSDLGenerator<T> {
    public boolean generate(T t) throws Exception;
}
