package com.dc.esb.servicegov.refactoring.wsdl.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.wsdl.Binding;
import javax.wsdl.BindingInput;
import javax.wsdl.BindingOperation;
import javax.wsdl.BindingOutput;
import javax.wsdl.Definition;
import javax.wsdl.Input;
import javax.wsdl.Message;
import javax.wsdl.OperationType;
import javax.wsdl.Output;
import javax.wsdl.Part;
import javax.wsdl.Port;
import javax.wsdl.PortType;
import javax.wsdl.Types;
import javax.wsdl.extensions.ExtensionRegistry;
import javax.wsdl.extensions.schema.Schema;
import javax.wsdl.extensions.schema.SchemaImport;
import javax.wsdl.extensions.soap.SOAPAddress;
import javax.wsdl.extensions.soap.SOAPBinding;
import javax.wsdl.extensions.soap.SOAPBody;
import javax.wsdl.extensions.soap.SOAPHeader;
import javax.wsdl.extensions.soap.SOAPOperation;
import javax.wsdl.factory.WSDLFactory;
import javax.wsdl.xml.WSDLWriter;
import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dc.esb.servicegov.refactoring.service.impl.ServiceManagerImpl;
import com.dc.esb.servicegov.refactoring.util.OperationComparator;
import com.dc.esb.servicegov.refactoring.util.ZIPUtils;
import com.dc.esb.servicegov.refactoring.wsdl.WSDLGenerator;
import com.dc.esb.servicegov.refactoring.wsdl.extensions.soap.SOAPAddressImpl;
import com.dc.esb.servicegov.refactoring.wsdl.extensions.soap.SOAPBindingImpl;
import com.dc.esb.servicegov.refactoring.wsdl.extensions.soap.SOAPBodyImpl;
import com.dc.esb.servicegov.refactoring.wsdl.extensions.soap.SOAPHeaderImpl;
import com.dc.esb.servicegov.refactoring.wsdl.extensions.soap.SOAPOperationImpl;
import com.dc.esb.servicegov.refactoring.entity.Operation;
import com.dc.esb.servicegov.refactoring.entity.RemainingService;
import com.dc.esb.servicegov.refactoring.entity.Service;
import com.dc.esb.servicegov.refactoring.service.impl.WSDLConstants;
/**
 * Created with IntelliJ IDEA.
 * User: Vincent
 * Date: 14-6-19
 * Time: 下午10:40
 */
@Component
public class SpdbWSDLGenerator implements WSDLGenerator<List<Service>> {
    private static final Log log = LogFactory.getLog(SpdbWSDLGenerator.class);
    @Autowired
    private ServiceManagerImpl serviceManager;
    @Autowired
    private SpdbServiceSchemaGenerator serviceSchemaGenerator;
    @Autowired
    private MetadataSchemaGenerator metadataSchemaGenerator;
    @Autowired
    private ESBServiceDescriptorGenerator esbServiceDescriptorGenerator;


    private String getWorkSpace(){

        long threadId = Thread.currentThread().getId();

        return threadId + "-" + System.currentTimeMillis();
    }

    @Override
    public boolean generate(List<Service> services) throws Exception {
        if (services != null) {
            for(Service serviceDO : services){
                generate(serviceDO);
            }
        }

        return true;
    }

    public File generate(String id){
        List<RemainingService> remainingServices = serviceManager.getRemainingServiceByServiceId(id);
        if(null != remainingServices && remainingServices.size() > 0){
            log.info("Service["+id+"] is a old service");
        }else{
            Service service = serviceManager.getServiceById(id);
            try{
                return generate(service);
            }catch(Exception e){
                log.error(e,e);
            }
        }
        return null;
    }


    public File generate(Service serviceDO) {
        BufferedOutputStream wsdlOut = null;
        String serviceId = serviceDO.getServiceId();
//        String workspacePath = getWorkSpace();
        String dirPath = serviceId;
        if(log.isInfoEnabled()){
            log.info("create wsdl in" + dirPath);
        }
        File dir = new File(dirPath);
        dir.mkdirs();
        try {
            serviceSchemaGenerator.generate(serviceId, dirPath);
            List<Operation> operations = serviceManager.getOperationsByServiceId(serviceId);
            // 无SDA数据的操作，从操作列表中删除
            List<Operation> delList = new ArrayList<Operation>();
            for(int i=0;i<operations.size();i++){
            	Operation operationDO = operations.get(i);
            	if(serviceManager.checkSdaExists(operationDO.getOperationId(),serviceId) == false){
            		delList.add(operationDO);
            	}
            }
            operations.removeAll(delList);
            OperationComparator operationComparator = new OperationComparator();
            Collections.sort(operations, operationComparator);
            esbServiceDescriptorGenerator.generate(serviceDO, operations, dirPath);
            metadataSchemaGenerator.generate(dirPath, serviceId);
            String tns = "http://esb.spdbbiz.com/services/" + serviceId + "/wsdl";
            WSDLFactory wsdlFactory = WSDLFactory.newInstance();
            //Create Definition
            Definition wsdlDefinition = createDefinition(serviceId, wsdlFactory);
            Types types = createTypes(serviceId, wsdlDefinition);
            //Create PortType
            PortType portType = wsdlDefinition.createPortType();
            portType.setQName(new QName(tns, "ESBServerPortType"));
            //创建绑定（binding）
            Binding binding = wsdlDefinition.createBinding();
            binding.setQName(new QName(tns, "ESBServerSoapBinding"));
            //创建SOAP绑定（SOAP binding）
            SOAPBinding soapBinding = new SOAPBindingImpl();
            //设置 style = "document"
            soapBinding.setStyle("document");
            //设置 SOAP传输协议 为 HTTP
            soapBinding.setTransportURI("http://schemas.xmlsoap.org/soap/http");
            binding.addExtensibilityElement(soapBinding);
            //Create

            if (null != operations) {
                for (Operation operationDO : operations) {

                    String operationId = operationDO.getOperationId();
                    String tmpOperationId = handleDupOperationIdIssue(operationId);
                    //Create Rep and Rsp Parts
                    Part bodyReqPart = wsdlDefinition.createPart();
                    bodyReqPart.setName("Req" + tmpOperationId);
//                    bodyReqPart.setTypeName(new QName("http://esb.spdbbiz.com/services/" + serviceId, "Req" + operationId, "s"));
                    bodyReqPart.setElementName(new QName("http://esb.spdbbiz.com/services/" + serviceId, "Req" + tmpOperationId, "s"));
                    Part bodyRspPart = wsdlDefinition.createPart();
                    bodyRspPart.setName("Rsp" + tmpOperationId);
//                    bodyRspPart.setTypeName(new QName("http://esb.spdbbiz.com/services/" + serviceId, "Rsp" + operationId, "s"));
                    bodyRspPart.setElementName(new QName("http://esb.spdbbiz.com/services/" + serviceId, "Rsp" + tmpOperationId, "s"));

                    Part headerReqPart = wsdlDefinition.createPart();
                    headerReqPart.setName("ReqHeader");
                    headerReqPart.setElementName(new QName("http://esb.spdbbiz.com/services/" + serviceId , "ReqHeader", "s"));
                    Part headerRspPart = wsdlDefinition.createPart();
                    headerRspPart.setName("RspHeader");
                    headerRspPart.setElementName(new QName("http://esb.spdbbiz.com/services/" + serviceId , "RspHeader", "s"));


                    //Create messages
                    Message headerReqMessage = wsdlDefinition.createMessage();
                    headerReqMessage.setQName(new QName(tns, "ReqHeader"));
                    headerReqMessage.addPart(headerReqPart);
                    headerReqMessage.setUndefined(false);
                    Message headerRspMessage = wsdlDefinition.createMessage();
                    headerRspMessage.setQName(new QName(tns, "RspHeader"));
                    headerRspMessage.addPart(headerRspPart);
                    headerRspMessage.setUndefined(false);
                    wsdlDefinition.addMessage(headerReqMessage);
                    wsdlDefinition.addMessage(headerRspMessage);

                    Message bodyReqMessage = wsdlDefinition.createMessage();
                    bodyReqMessage.setQName(new QName(tns, "Req" + tmpOperationId));
                    bodyReqMessage.addPart(bodyReqPart);
                    bodyReqMessage.setUndefined(false);
                    Message bodyRspMessage = wsdlDefinition.createMessage();
                    bodyRspMessage.setQName(new QName(tns, "Rsp" + tmpOperationId));
                    bodyRspMessage.addPart(bodyRspPart);
                    bodyRspMessage.setUndefined(false);
                    wsdlDefinition.addMessage(bodyReqMessage);
                    wsdlDefinition.addMessage(bodyRspMessage);
                    //create Operation for portType
                    javax.wsdl.Operation operation = wsdlDefinition.createOperation();
                    operation.setName(tmpOperationId);
                    //创建 Input，并设置 Input 的 message
                    Input input = wsdlDefinition.createInput();
                    input.setMessage(bodyReqMessage);
                    //创建 Output，并设置 Output 的 message
                    Output output = wsdlDefinition.createOutput();
                    output.setMessage(bodyRspMessage);
                    //设置 Operation 的输入，输出，操作类型
                    operation.setInput(input);
                    operation.setOutput(output);
                    operation.setStyle(OperationType.REQUEST_RESPONSE);
                    operation.setUndefined(false);
                    portType.addOperation(operation);
                    portType.setUndefined(false);
                    //创建绑定操作（Binding Operation）
                    BindingOperation bindingOperation = wsdlDefinition.createBindingOperation();
                    //创建 bindingInput
                    BindingInput bindingInput = wsdlDefinition.createBindingInput();
                    SOAPOperation soapOperation = new SOAPOperationImpl();
                    soapOperation.setSoapActionURI("urn:/" + tmpOperationId);
                    bindingOperation.addExtensibilityElement(soapOperation);
                    //Create SOAP header use = "literal"
                    SOAPHeader inSOAPHeader = new SOAPHeaderImpl();
                    inSOAPHeader.setUse("literal");
                    inSOAPHeader.setMessage(new QName(tns,"ReqHeader"));
                    inSOAPHeader.setPart("ReqHeader");
                    bindingInput.addExtensibilityElement(inSOAPHeader);
                    //创建 SOAP body ，设置 use = "literal"
                    SOAPBody inSOAPBody = new SOAPBodyImpl();
                    inSOAPBody.setUse("literal");
                    bindingInput.addExtensibilityElement(inSOAPBody);

                    BindingOutput bindingOutput = wsdlDefinition.createBindingOutput();
                    SOAPHeader outSOAPHeader = new SOAPHeaderImpl();
                    outSOAPHeader.setUse("literal");
                    outSOAPHeader.setMessage(new QName(tns, "RspHeader"));
                    outSOAPHeader.setPart("RspHeader");
                    bindingOutput.addExtensibilityElement(outSOAPHeader);
                    SOAPBody outSOAPBody = new SOAPBodyImpl();
                    outSOAPBody.setUse("literal");
                    bindingOutput.addExtensibilityElement(outSOAPBody);
                    //设置 bindingOperation 的名称，绑定输入 和 绑定输出
                    bindingOperation.setName(tmpOperationId);
                    bindingOperation.setBindingInput(bindingInput);
                    bindingOperation.setBindingOutput(bindingOutput);
                    binding.addBindingOperation(bindingOperation);
                }
                wsdlDefinition.addPortType(portType);
                //设置绑定的端口类型
                binding.setPortType(portType);
                binding.setUndefined(false);
                wsdlDefinition.addBinding(binding);

                //创建 service
                javax.wsdl.Service service = wsdlDefinition.createService();
                service.setQName(new QName(tns, serviceId));
                //创建服务端口 port
                Port port = wsdlDefinition.createPort();
                //设置服务端口的 binding，名称，并添加SOAP地址
                port.setBinding(binding);
                port.setName("ESBServerSoapEndpoint");
                SOAPAddress soapAddress = new SOAPAddressImpl();
                soapAddress.setLocationURI("http://esb.spdbbiz.com:7701/services/" + serviceId);
                port.addExtensibilityElement(soapAddress);
                service.addPort(port);
                wsdlDefinition.addService(service);


                wsdlDefinition.setTypes(types);
                WSDLWriter writer = wsdlFactory.newWSDLWriter();

                File wsdlFile = new File(dirPath + File.separator + serviceId + ".wsdl");
                if(!wsdlFile.exists()){
                    wsdlFile.createNewFile();
                }
                System.out.println(wsdlFile.getAbsolutePath());
                wsdlOut = new BufferedOutputStream(new FileOutputStream(wsdlFile));
                writer.writeWSDL(wsdlDefinition, wsdlOut);
            }

            ZIPUtils zipUtils = new ZIPUtils();
            zipUtils.doZip(new File[]{new File(dir.getAbsolutePath())}, dirPath + ".zip");

            return new File(dirPath + ".zip");

        } catch (Exception e) {
            log.error(e, e);
        }finally{
            try {
            	if(wsdlOut != null){
                  wsdlOut.close();
            	}
            } catch (IOException e) {
                log.error(e,e);
            }
            deleteFile(dir);
        }
        return null;
    }
	public boolean deleteFile(File file){
		boolean deleteResult = true;
		if(file.isDirectory()){
			File[] subFiles = file.listFiles();
			if(null != subFiles){
				for(File subFile : subFiles){
					deleteResult = deleteFile(subFile);
				}
			}
		}
		deleteResult = file.delete();
		if(!deleteResult){
			log.error("删除临时文件["+file.getAbsolutePath()+"]失败！");
		}
		return deleteResult;
	}
    private Definition createDefinition(String serviceId, WSDLFactory wsdlFactory) {
        Definition wsdlDefinition = null;
        try {
            wsdlDefinition = wsdlFactory.newDefinition();
            String tns = "http://esb.spdbbiz.com/services/" + serviceId + "/wsdl";
            wsdlDefinition.setTargetNamespace(tns);
            wsdlDefinition.addNamespace("tns", tns);
            wsdlDefinition.addNamespace(WSDLConstants.HTTP_PREFIX, WSDLConstants.HTTP_NAMESPACE);
            wsdlDefinition.addNamespace(WSDLConstants.MIME_PREFIX, WSDLConstants.MIME_NAMEPACE);
            wsdlDefinition.addNamespace(WSDLConstants.SOAP_PREFIX, WSDLConstants.SOAP_NAMESPACE);
            wsdlDefinition.addNamespace(WSDLConstants.SOAP_NC_PREFIX, WSDLConstants.SOAP_NC_NAMESPACE);
            wsdlDefinition.addNamespace(WSDLConstants.XSD_PREFIX, WSDLConstants.XSD_NAMESPACE);
            wsdlDefinition.addNamespace("s", "http://esb.spdbbiz.com/services/" + serviceId);
        } catch (Exception e) {
            //Todo
            log.error(e, e);
        }

        return wsdlDefinition;
    }

    private Types createTypes(String serviceId, Definition wsdlDefinition) {
        Types types = null;
        try {
            types = wsdlDefinition.createTypes();
            ExtensionRegistry extReg = wsdlDefinition.getExtensionRegistry();
            Schema schema = (Schema) extReg.createExtension(Types.class, new QName(WSDLConstants.XSD_NAMESPACE, "schema", WSDLConstants.XSD_PREFIX));
            schema.setTargetNamespace("http://esb.spdbbiz.com/services/" + serviceId + "/wsdl");
            SchemaImport schemaImport = schema.createImport();
            schemaImport.setSchemaLocationURI("http://10.112.20.145:8080/Publish/WSDLfilePath/"+serviceId + ".xsd");
            schemaImport.setNamespaceURI("http://esb.spdbbiz.com/services/" + serviceId);
            schema.addImport(schemaImport);
            types.addExtensibilityElement(schema);
        } catch (Exception e) {
            //Todo
            log.error(e, e);
        }
        return types;
    }

    //Todo
    public String handleDupOperationIdIssue(String operationId){
        if(operationId.indexOf("-") > -1){
            String tmpOperationId = operationId.substring(0, operationId.indexOf("-"));
            return tmpOperationId;
        }else{
            return operationId;
        }
    }


}
