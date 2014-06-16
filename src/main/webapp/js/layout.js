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