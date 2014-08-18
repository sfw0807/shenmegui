package com.dc.esb.servicegov.runtime;

import java.io.File;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Vincent Fan
 * Date: 14-7-18
 * Time: 上午9:33
 */
public interface PackerParserGenerator <T> {
    public List<File> generate(T identify) throws Exception;
}
