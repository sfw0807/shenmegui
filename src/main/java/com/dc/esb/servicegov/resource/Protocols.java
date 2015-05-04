package com.dc.esb.servicegov.resource;

public class Protocols {
	public static String IbmQServiceConnector = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><protocol.ibmQ ioDirection=\"DataIn/DataOut\" side=\"client\" mode=\"synchronous\" protocolName=\"IbmQServiceConnector\" id=\"sg_ibmqserviceconnector\"><common content-encoding=\"UTF-8\" idle=\"5000\" ccsid=\"1208\"/><request threadCount=\"10\" waitTime=\"30000\" queueName=\"qname\" connectionFactory=\"mname\" providerUrl=\"http://127.0.0.1\"/><response correlation=\"true\" threadCount=\"10\" waitTime=\"30000\" queueName=\"qname\" connectionFactory=\"mname\" providerUrl=\"http://127.0.0.1\"/><advanced/><reconn count=\"5\"><errorCode>2009</errorCode><errorCode>2019</errorCode><errorCode>2059</errorCode></reconn></protocol.ibmQ>";
	public static String IbmQChannelConnector = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><protocol.ibmQ ioDirection=\"DataIn/DataOut\" side=\"server\" mode=\"synchronous\" protocolName=\"IbmQChannelConnector\" id=\"sg_ibmqchannelconnector\"><common content-encoding=\"UTF-8\" idle=\"5000\" ccsid=\"1208\"/><request threadCount=\"10\" waitTime=\"30000\" queueName=\"qname\" connectionFactory=\"mname\" providerUrl=\"http://127.0.0.1\"/><response correlation=\"true\" threadCount=\"10\" waitTime=\"30000\" queueName=\"qname\" connectionFactory=\"mname\" providerUrl=\"http://127.0.0.1\"/><advanced/><reconn count=\"5\"><errorCode>2009</errorCode><errorCode>2019</errorCode><errorCode>2059</errorCode></reconn></protocol.ibmQ>";
	public static String UDPServiceConnector = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><protocol.udp workMode=\"UNICAST\" side=\"client\" protocolName=\"UDPServiceConnector\" mode=\"synchronous\" ioDirection=\"DataIn/DataOut\" id=\"sg_udpserviceconnector\"><common threadsCount=\"5\"/><request bufferSize=\"1024\" port=\"0\" action=\"toBytes\" connectTimeout=\"10000\" readTimeout=\"0\" encoding=\"UTF-8\" address=\"127.0.0.1\" threadsCount=\"5\"/><response bufferSize=\"1024\" port=\"0\" action=\"toBytes\" connectTimeout=\"10000\" readTimeout=\"0\" encoding=\"UTF-8\" threadsCount=\"5\"/><advanced/></protocol.udp>";
	public static String UDPChannelConnector = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><protocol.udp workMode=\"UNICAST\" side=\"server\" protocolName=\"UDPChannelConnector\" mode=\"synchronous\" ioDirection=\"DataIn/DataOut\" id=\"sg_udpchannelconnector\"><common threadsCount=\"5\"/><request bufferSize=\"1024\" port=\"0\" action=\"toBytes\" connectTimeout=\"10000\" readTimeout=\"0\" encoding=\"UTF-8\" address=\"127.0.0.1\" threadsCount=\"5\"/><response bufferSize=\"1024\" port=\"0\" action=\"toBytes\" connectTimeout=\"10000\" readTimeout=\"0\" encoding=\"UTF-8\" threadsCount=\"5\"/><advanced/></protocol.udp>";
	public static String CICSConnector = "<protocol.common protocolName=\"CICSConnector\" id=\"sg_cicsconnector\" mode=\"synchronous\" side=\"client\"><common ip=\"1.1.1.1\" port=\"11111\" properties=\"user=CICSUSER@servername=hrbrun@password=@beginIndex=49@endIndex=57@separateIndex=58\" /></protocol.common>";
	public static String IDPWSServiceConnector = "<protocol.common protocolName=\"IDPWSServiceConnector\" id=\"sg_idpwsserviceconnector\" mode=\"synchronous\" side=\"client\"><common wsdlURI=\"http://10.112.19.49:7000/AZService/basic\" operation=\"QueryUserInfo\" namespace=\"http://www.spdb.com/uaas/2007/07\" methodName=\"ExecuteWithEnc\" useSOAPAction=\"true\" soapActionURI=\"http://www.spdb.com/uaas/2007/07/ISecureService/ExecuteWithEnc\" timeout=\"30000\" /></protocol.common>";
	public static String EJBChannelConnector = "<protocol.ejb protocolName=\"EJBChannelConnector\" id=\"sg_eibchannelconnector\" mode=\"synchronous\" side=\"server\" ioDirection=\"DataIn/DataOut\"><common uri=\"t3://127.0.0.1:22221\" jndi=\"EJBAdapter\" remoteInterface=\"com.dc.esb.container.protocol.ejb.server.EJBServerHome\" methodName=\"sendSynF2T(java.lang.String, java.lang.String, java.lang.Object[]) throws java.rmi.RemoteException\" username=\"\" password=\"\" /></protocol.ejb>";
	public static String EJBServiceConnector = "<protocol.ejb protocolName=\"EJBServiceConnector\" id=\"sg_ejbserviceconnector\" mode=\"synchronous\" side=\"client\" ioDirection=\"DataIn/DataOut\"><common uri=\"http://127.0.0.1:8080\" jndi=\"jndi\" remoteInterface=\"interface\" methodName=\"method\" username=\"\" password=\"\" /></protocol.ejb>";
	public static String HTTPChannelConnector = "<protocol.http protocolName=\"HTTPChannelConnector\" id=\"sg_httpchannelconnector\" ioDirection=\"DataIn/DataOut\" mode=\"synchronous\" side=\"server\"><common uri=\"http://127.0.0.1:8080/*\" /><request serverType=\"jetty\" method=\"post\" encoding=\"UTF-8\" action=\"toString\" /><response contentType=\"text/html\" encoding=\"UTF-8\" /><security protocol=\"SSL\" bidirectional=\"false\" /><advanced connPerHostCount=\"200\" threadCount=\"50\" readTimeout=\"30000\" maxConnCount=\"2000\" soLinger=\"0\" writeBufferSize=\"2048\" reuseAddress=\"true\" readBufferSize=\"2048\" connectionTimeout=\"30000\" tcpNoDelay=\"true\" /></protocol.http>";
	public static String HTTPServiceConnector = "<protocol.http protocolName=\"HTTPServiceConnector\" id=\"sg_httpserviceconnector\" ioDirection=\"DataIn/DataOut\" mode=\"synchronous\" side=\"client\"><common uri=\"http://127.0.0.1:8080/*\" /><request method=\"post\" encoding=\"UTF-8\" contenttype=\"text/html\" /><response encoding=\"UTF-8\" action=\"toString\" /><security protocol=\"SSL\" bidirectional=\"false\" /><advanced connPerHostCount=\"200\" threadCount=\"50\" readTimeout=\"30000\" maxConnCount=\"2000\" soLinger=\"0\" writeBufferSize=\"2048\" reuseAddress=\"true\" readBufferSize=\"2048\" connectionTimeout=\"30000\" tcpNoDelay=\"true\" /></protocol.http>";
	public static String JMSChannelConnector = "<protocol.jms protocolName=\"JMSChannelConnector\" id=\"sg_jmschannelconnector\" mode=\"synchronous\" ioDirection=\"DataIn/DataOut\" side=\"server\"><common /><request JNDIContextFactory=\"weblogic.jndi.WLInitialContextFactory\" ProviderURL=\"http://127.0.0.1/\" ConnectionFactory=\"mname\" QueueName=\"qname\" SessionCount=\"1\" /><response JNDIContextFactory=\"weblogic.jndi.WLInitialContextFactory\" ProviderURL=\"http://127.0.0.1/\" ConnectionFactory=\"mname\" QueueName=\"qname\" SessionCount=\"1\" /><advanced /></protocol.jms>";
	public static String JMSServiceConnector = "<protocol.jms protocolName=\"JMSServiceConnector\" id=\"sg_jmsserviceconnector\" mode=\"synchronous\" ioDirection=\"DataIn/DataOut\" side=\"client\"><common /><request JNDIContextFactory=\"weblogic.jndi.WLInitialContextFactory\" ProviderURL=\"http://127.0.0.1/\" ConnectionFactory=\"mname\" QueueName=\"qname\" SessionCount=\"1\" locationDepend=\"true\" /><response JNDIContextFactory=\"weblogic.jndi.WLInitialContextFactory\" ProviderURL=\"http://127.0.0.1/\" ConnectionFactory=\"mname\" QueueName=\"qname\" SessionCount=\"1\" readTimeout=\"0\" locationDepend=\"true\" /><advanced /></protocol.jms>";
	public static String RESTChannelConnector = "<protocol.rest protocolName=\"RESTChannelConnector\" id=\"sg_restchannelconnector\" mode=\"synchronous\" ioDirection=\"DataIn/DataOut\" side=\"server\"><common uri=\"http://127.0.0.1\" /><request serverType=\"jetty\" method=\"post\" transMode=\"byForm\" encoding=\"UTF-8\" action=\"toString\" /><response contentType=\"text/html\" encoding=\"UTF-8\" /><advanced /></protocol.rest>";
	public static String RESTServiceConnector = "<protocol.rest protocolName=\"RESTServiceConnector\" id=\"sg_restserviceconnector\" mode=\"synchronous\" ioDirection=\"DataIn/DataOut\" side=\"client\"><common uri=\"http://127.0.0.1/\" /><request method=\"post\" transMode=\"byForm\" encoding=\"UTF-8\" connectTimeout=\"10000\" /><response encoding=\"UTF-8\" readTimeout=\"10000\" action=\"toString\" /><advanced /></protocol.rest>";
	public static String TCPChannelConnector = "<protocol.tcp protocolName=\"TCPChannelConnector\" id=\"sg_tcpchannelconnector\" mode=\"synchronous\" ioDirection=\"DataIn/DataOut\" side=\"server\"><common connectMode=\"short\" threadsCount=\"20\" port=\"0\" /><request policy=\"length:unknown\" encoding=\"UTF-8\" readTimeout=\"0\" action=\"toBytes\" /><response policy=\"length:unknown\" encoding=\"UTF-8\" /><advanced /></protocol.tcp>";
	public static String TCPServiceConnector = "<protocol.tcp protocolName=\"TCPServiceConnector\" id=\"sg_tcpserviceconnector\" mode=\"synchronous\" ioDirection=\"DataIn/DataOut\" side=\"client\"><common connectMode=\"short\" threadsCount=\"20\" host=\"127.0.0.1\" port=\"0\" /><request policy=\"length:unknown\" encoding=\"GBK\" connectTimeout=\"10000\" /><response policy=\"length:hl=2,dl=2\" encoding=\"GBK\" readTimeout=\"0\" action=\"toBytes\" /><advanced /></protocol.tcp>";
	public static String WSChannelConnector = "<protocol.ws protocolName=\"WSChannelConnector\" id=\"sg_wschannelconnector\" mode=\"synchronous\" ioDirection=\"DataIn/DataOut\" side=\"server\"><common GroundProtocol=\"http\" ServerType=\"jetty\" wsSecurity=\"\" deployedUDDI=\"false\" serviceids=\"metadata\" wsType=\"axis\" wsdlURI=\"http://localhost:9000/esb_launcher/esbwebservice/\" isSOAP12=\"false\" autoAssemble=\"false\" /><request Encoding=\"UTF-8\" /><response Encoding=\"UTF-8\" /><advanced connPerHostCount=\"200\" threadCount=\"50\" readTimeout=\"30000\" maxConnCount=\"2000\" soLinger=\"0\" writeBufferSize=\"2048\" reuseAddress=\"true\" readBufferSize=\"2048\" connectionTimeout=\"30000\" tcpNoDelay=\"true\" /></protocol.ws>";
	public static String WSServiceConnector = "<protocol.ws protocolName=\"WSServiceConnector\" id=\"sg_wsserviceconnector\" mode=\"synchronous\" ioDirection=\"DataIn/DataOut\" side=\"client\"><common GroundProtocol=\"http\" wsType=\"axis\" wsdlURI=\"http://127.0.0.1\" isSOAP12=\"false\" autoAssemble=\"false\" /><request Encoding=\"UTF-8\" /><response Encoding=\"UTF-8\" /><advanced connPerHostCount=\"200\" threadCount=\"50\" readTimeout=\"30000\" maxConnCount=\"2000\" soLinger=\"0\" writeBufferSize=\"2048\" reuseAddress=\"true\" readBufferSize=\"2048\" connectionTimeout=\"30000\" tcpNoDelay=\"true\" /></protocol.ws>";
	
	public static String getProtocol(String protocolType){
		if("IbmQServiceConnector".equals(protocolType)){
			return IbmQServiceConnector;
		}else if("IbmQChannelConnector".equals(protocolType)){
			return IbmQChannelConnector;
		}else if("UDPServiceConnector".equals(protocolType)){
			return UDPServiceConnector;
		}else if("UDPChannelConnector".equals(protocolType)){
			return UDPChannelConnector;
		}else if("CICSConnector".equals(protocolType)){
			return CICSConnector;
		}else if("IDPWSServiceConnector".equals(protocolType)){
			return IDPWSServiceConnector;
		}else if("EJBChannelConnector".equals(protocolType)){
			return EJBChannelConnector;
		}else if("EJBServiceConnector".equals(protocolType)){
			return EJBServiceConnector;
		}else if("HTTPChannelConnector".equals(protocolType)){
			return HTTPChannelConnector;
		}else if("HTTPServiceConnector".equals(protocolType)){
			return HTTPServiceConnector;
		}else if("JMSChannelConnector".equals(protocolType)){
			return JMSChannelConnector;
		}else if("JMSServiceConnector".equals(protocolType)){
			return JMSServiceConnector;
		}else if("RESTChannelConnector".equals(protocolType)){
			return RESTChannelConnector;
		}else if("RESTServiceConnector".equals(protocolType)){
			return RESTServiceConnector;
		}else if("TCPChannelConnector".equals(protocolType)){
			return TCPChannelConnector;
		}else if("TCPServiceConnector".equals(protocolType)){
			return TCPServiceConnector;
		}else if("WSChannelConnector".equals(protocolType)){
			return WSChannelConnector;
		}else if("WSServiceConnector".equals(protocolType)){
			return WSServiceConnector;
		}else{
			return "";
		}
	}
	public static void main(String[] args) {
		System.out.println(TCPServiceConnector.replaceAll("sg_", "bip"));
	}
}
