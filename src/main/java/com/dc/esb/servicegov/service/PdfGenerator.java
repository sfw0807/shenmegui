package com.dc.esb.servicegov.service;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-5-28
 * Time: 上午9:05
 */
public interface PdfGenerator<T> {

    public File generate(T t) throws Exception;

}

