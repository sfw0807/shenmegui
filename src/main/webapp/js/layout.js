/**
 * Created with IntelliJ IDEA.
 * User: Vincent Fan
 * Date: 14-2-23
 * Time: 下午4:55
 * To change this template use File | Settings | File Templates.
 */

var oLanguage = {
    "sLengthMenu": "显示 _MENU_ 条记录每页",
    "sSearch": "搜索 _INPUT_",
    "oPaginate": {
        "sFirst": "第一页",
        "sLast": "最后一页",
        "sNext": "下一页",
        "sPrevious": "上一页"
    },
    sInfo: "第 _START_ 到 _END_ 条记录 共 _TOTAL_ 条"
};
var sisTableLayout = [
	{
        "sTitle": "系统简称",
        "mData": "systemAB"
    },
  	{
        "sTitle": "系统名称",
        "mData": "systemName"
    },
    {
        "sTitle": "提供服务数",
        "mData": "provideServiceNum"
    },
    {
        "sTitle": "提供操作数",
        "mData": "provideOperationNum"
    },
    {
        "sTitle": "调用服务数",
        "mData": "consumeServiceNum"
    },
    {
        "sTitle": "调用操作数",
        "mData": "consumeOperationNum"
    }
];

var serviceTableLayout = [
    {
        "sTitle": "服务ID",
        "mData": "serviceId"
    },
    {
        "sTitle": "服务名称",
        "mData": "serviceName"
    },
    {
        "sTitle": "服务状态",
        "mData": "versionState"
    },
    {
        "sTitle": "服务备注",
        "mData": "serviceRemark"
    }
];

var serviceSlaTableLayout = [
    {
        "sTitle": "协议ID",
        "mData": "protocolId"
    },
    {
        "sTitle": "协议值",
        "mData": "protocolValue"
    },
    {
        "sTitle": "协议描述",
        "mData": "description"
    }
];

var extendsTableLayout = [
    {
        "sTitle": "继承序列号",
        "mData": "extendSeq"
    },
    {
        "sTitle": "父接口ID",
        "mData": "superInterfaceId"
    },
    {
        "sTitle": "父接口名称",
        "mData": "superInterfaceName"
    }
];

var serviceExtendsTableLayout = [
    {
        "sTitle": "继承顺序号",
        "mData": "extendSeq"
    },
    {
        "sTitle": "继承服务ID",
        "mData": "superServiceId"
    },
    {
        "sTitle": "继承服务名称",
        "mData": "superServiceName"
    }
];

var interfaceChildTableLayout = [
	{
        "sTitle": "序号",
        "mData": "structIndex"
    },
    {
        "sTitle": "英文名称",
        "mData": "structName"
    },
    {
        "sTitle": "元数据ID",
        "mData": "metadataId"
    },
    {
        "sTitle": "中文名称",
        "mData": "structAlias"
    },
    {
        "sTitle": "类型",
        "mData": "type"
    },
    {
        "sTitle": "长度",
        "mData": "length"
    },
    {
        "sTitle": "精度",
        "mData": "scale"
    },
    {
        "sTitle": "是否必输",
        "mData": "required"
    },
    {
        "sTitle": "备注",
        "mData": "remark"
    }
];

var serviceChildTableLayout = [
	{
        "sTitle": "序号",
        "mData": "structIndex"
    },
    {
        "sTitle": "英文名称",
        "mData": "structName"
    },
    {
        "sTitle": "元数据ID",
        "mData": "metadataId"
    },
    {
        "sTitle": "中文名称",
        "mData": "structAlias"
    },
    {
        "sTitle": "类型",
        "mData": "type"
    },
    {
        "sTitle": "是否必输",
        "mData": "required"
    },
    {
        "sTitle": "备注",
        "mData": "remark"
    }
];

var operationTableLayout = [
    {
        "sTitle": "操作ID",
        "mData": "serviceId"
    },
    {
        "sTitle": "操作名称",
        "mData": "serviceName"
    },
    {
        "sTitle": "操作状态",
        "mData": "versionState"
    },
    {
        "sTitle": "操作备注",
        "mData": "serviceRemark"
    }
];

var interfaceTableLayout = [
    {
        "sTitle": "接口ID",
        "mData": "interfaceId"
    },
    {
        "sTitle": "接口名称",
        "mData": "interfaceName"
    },
    {
        "sTitle": "对应操作",
        "mData": "serviceId"
    },
    {
        "sTitle": "接口系统",
        "mData": "systemName"
    },
    {
        "sTitle": "接口报文类型",
        "mData": "interfaceMsgType"
    },
    {
        "sTitle": "接口类型",
        "mData": "interfaceType"
    }
];

var exportExcelLayout = [
    {
        "sTitle": "接口ID",
        "mData": "interfaceId"
    },
    {
        "sTitle": "接口名称",
        "mData": "interfaceName"
    },
    {
        "sTitle": "服务ID",
        "mData": "superServiceId"
    },
    {
        "sTitle": "服务名称",
        "mData": "superServiceName"
    },
    {
        "sTitle": "操作ID",
        "mData": "serviceId"
    },
    {
        "sTitle": "操作名称",
        "mData": "serviceName"
    },
    {
        "sTitle": "系统ID",
        "mData": "systemId"
    },
    {
        "sTitle": "系统英文名",
        "mData": "systemEngName"
    },
    {
        "sTitle": "接口类型",
        "mData": "interfaceType"
    },
    {
        "sTitle": "状态",
        "mData": "versionState"
    },
    {
        "sTitle": "上线版本",
        "mData": "productNo"
    },
    {
        "sTitle": "开发版本",
        "mData": "versionNo"
    }
    ,
    {
        "sTitle": "上线日期",
        "mData": "onlineDate"
    }
    
];

var serviceInvokeTableLayout = [
    {
        "sTitle": "服务ID",
        "mData": "serviceId"
    },
    {
        "sTitle": "操作ID",
        "mData": "operationId"
    },
    {
        "sTitle": "接口ID",
        "mData": "interfaceId"
    },
    {
        "sTitle": "调用方系统",
        "mData": "consumerName"
    },
    {
        "sTitle": "提供方系统",
        "mData": "providerName"
    },
    {
        "sTitle": "经由系统",
        "mData": "passBySysName"
    },
    {
        "sTitle": "调用关系",
        "mData": "messageType"
    }
];

var sirTableLayout = [
    {
        "sTitle": "服务ID",
        "mData": "serviceId"
    },
    {
        "sTitle": "操作ID",
        "mData": "operationId"
    },
    {
        "sTitle": "接口ID",
        "mData": "interfaceId"
    },
    {
        "sTitle": "调用方系统",
        "mData": "consumerSystemAb"
    },
    {
        "sTitle": "提供方系统",
        "mData": "providerSystemId"
    },
    {
        "sTitle": "经由系统",
        "mData": "passbySys"
    },
    {
        "sTitle": "调用关系",
        "mData": "type"
    }
];

var systemTableLayout = [
    {
        "sTitle": "系统ID",
        "mData": "systemId"
    },
    {
        "sTitle": "系统简称",
        "mData": "systemAbbreviation"
    },
    {
        "sTitle": "系统中文名",
        "mData": "systemName"
    },
    {
        "sTitle": "系统描述",
        "mData": "systemRemark"
    }
];

