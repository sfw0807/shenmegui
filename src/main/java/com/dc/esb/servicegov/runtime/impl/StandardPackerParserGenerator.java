package com.dc.esb.servicegov.runtime.impl;

import com.dc.esb.servicegov.entity.Interface;
import com.dc.esb.servicegov.entity.Service;
import com.dc.esb.servicegov.exception.DataException;
import com.dc.esb.servicegov.runtime.PackerParserGenerator;
import com.dc.esb.servicegov.runtime.suppert.Context;
import com.dc.esb.servicegov.service.impl.ServiceManagerImpl;
import com.dc.esb.servicegov.vo.SDA;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

import static com.dc.esb.servicegov.runtime.suppert.Constants.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Vincent Fan
 * Date: 14-7-18
 * Time: 上午9:36
 * <p/>
 * generator who generate standard packer and parser configs for esb
 */
@Component
public class StandardPackerParserGenerator implements PackerParserGenerator<Context> {

    private Log log = LogFactory.getLog(getClass());
    @Autowired
    private ServiceManagerImpl serviceManager;

    /**
     * generate configs by operationId
     *
     * @param context
     * @return
     * @throws Exception
     */
    @Override
    public List<File> generate(Context context) throws Exception {
        //检查上下文
        validateContext(context);
        String operationId = context.get(OPERATION_ID);
        List<Service> operations = serviceManager.getServiceById(operationId);
        if (null == operations) {
            String errorMsg = "操作[" + operationId + "]不存在！";
            log.error(errorMsg);
            throw new DataException(errorMsg);
        }
        if (operations.size() != 1) {
            String errorMsg = "操作[" + operationId + "]存在重名，暂时不支持重名操作。";
            log.error(errorMsg);
            throw new DataException(errorMsg);
        }
        Service operation = operations.get(0);
        String serviceId = context.get(SERVICE_ID);
        List<Service> services = serviceManager.getServiceById(serviceId);
        if (null == services) {
            String errorMsg = "服务[" + serviceId + "]不存在！";
            log.error(errorMsg);
            throw new DataException(errorMsg);
        }
        if (services.size() != 1) {
            String errorMsg = "服务[" + serviceId + "]存在重名，暂时不支持重名操作。";
            log.error(errorMsg);
            throw new DataException(errorMsg);
        }
        Service service = services.get(0);
        Service sHeaderService = getSHeader(service);
        SDA operationSDA = serviceManager.getSDAofService(operation);
        SDA sHeaderSDA = serviceManager.getSDAofService(service);
        String workSpace = getWorkSpace();
        return null;
    }

    private List<File> paintIn(SDA sHeaderService, SDA operationService, Context context, String workspace){
        String serviceId = context.get(SERVICE_ID);
        String operationId = context.get(OPERATION_ID);
        String inDirPath = workspace + File.separator + serviceId + operationId + File.separator + "in_config";
        File inDir = new File(inDirPath);
        inDir.mkdirs();
        //in端的拆包不需要命名空间
        String inConfigPath = inDirPath + File.separator + "channel_soap_service_"+ serviceId + operationId;
        //in端的组包需要命名空间
        return null;
    }

    private Document paintInParser(){
        return null;
    }




    /**
     * 检查导入导出上下文的完整性
     * @param context
     * @throws DataException
     */
    private void validateContext(Context context) throws DataException {
        String operationId = context.get(OPERATION_ID);
        String providerId = context.get(PROVIDER_ID);
        String serviceId = context.get(SERVICE_ID);
        StringBuilder errorMsgBuilder = new StringBuilder();
        if(null == operationId){
            errorMsgBuilder.append("导出标准配置上下文中缺少[操作ID]！\n");
        }
        if(null == providerId){
            errorMsgBuilder.append("导出标准配置上下文中缺少[提供方系统ID]！\n");
        }
        if(null == serviceId){
            errorMsgBuilder.append("导出标准配置上下文中缺少["+serviceId+"]");
        }
        String errorMsg = errorMsgBuilder.toString();
        if(!"".equals(errorMsg)){
            log.error(errorMsg);
            throw new DataException(errorMsg);
        }
    }

    /**
     * 获取具有业务头的父服务
     * @param service
     * @return
     * @throws DataException
     */
    private Service getSHeader(Service service) throws DataException{
        Service sHeaderService = null;
        List<Service> superServices = serviceManager.getParentService(service);
        if(null == superServices){
            String errorMsg = "服务[" + service.getServiceId() + "]的抽象业务头父服务不存在！";
            log.error(errorMsg);
            throw new DataException(errorMsg);
        }
        if (superServices.size() != 1) {
            String errorMsg = "服务[" + service.getServiceId() + "]存在多个父服务，暂时不支持多个业务父服务。";
            log.error(errorMsg);
            throw new DataException(errorMsg);
        }
        sHeaderService = superServices.get(0);
        return sHeaderService;
    }

    /**
     * TODO
     * Soap 头暂时不变
     * 获取具有技术头的接口
     * @param operationId
     * @return
     */
    private Interface getTechHeader(String operationId) {
        return null;
    }

    private String getWorkSpace(){
        StringBuilder sb = new StringBuilder("ESB_Config_");
        long threadId = Thread.currentThread().getId();
        long current = System.currentTimeMillis();
        sb.append(threadId);
        sb.append("_");
        sb.append(current);
        return sb.toString();
    }
}
