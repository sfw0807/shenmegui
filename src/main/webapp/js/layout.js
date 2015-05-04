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
    "sInfo": "第 _START_ 到 _END_ 条记录 共 _TOTAL_ 条",
	"sProcessing" : "正在加载中...",
    "sZeroRecords" : "对不起,查询不到任何相关数据"
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
        "sTitle": "首次上线日期(提供方)",
        "mData": "firstPublishDate"
    },
    {
        "sTitle": "首次上线日期(调用方)",
        "mData": "secondPublishDate"
    },
    {
        "sTitle": "提供服务数",
        "mData": "provideServiceNum"
    },
    {
        "sTitle": "调用服务数",
        "mData": "consumeServiceNum"
    },
    {
        "sTitle": "提供操作数",
        "mData": "provideOperationNum"
    },
    {
        "sTitle": "调用操作数",
        "mData": "consumeOperationNum"
    }
	
];

var logTableLayout = [
	
    {
        "sTitle": "用户名称",
        "mData": "user.name"
    },
    {
        "sTitle": "组织ID",
        "mData": "user.orgId"
    },
    {
        "sTitle": "操作",
        "mData": "function.name"
    },
    {
        "sTitle": "时间",
        "mData": "time"
    },
    {
        "sTitle": "日志详情",
        "mData": "detail"
    }
];

var publishInfoTableLayout = [
	{
        "sTitle": "上线日期",
        "mData": "onlinedate"
    },
  	{
        "sTitle": "上线服务总数",
        "mData": "countofservices"
    },
    {
        "sTitle": "新增服务数",
        "mData": "countofaddservices"
    },
    {
        "sTitle": "修改服务数",
        "mData": "counofmodifyservices"
    },
    {
        "sTitle": "上线操作总数",
        "mData": "countofoperations"
    },
    {
        "sTitle": "新增操作数",
        "mData": "countofaddoperations"
    },
	{
        "sTitle": "修改操作数",
        "mData": "countofmodifyoperations"
    }
];


var serviceDevProgressTableLayout = [

	{
        "sTitle": "系统英文名称",
        "mData": "systemAb"
    },
	{
        "sTitle": "系统名称",
        "mData": "systemName"
    },
    {
        "sTitle": "服务定义",
        "mData": "underDefine"
    },
    {
        "sTitle": "开发",
        "mData": "dev"
    },
    {
        "sTitle": "联调测试",
        "mData": "unitTest"
    },
    {
        "sTitle": "sit测试",
        "mData": "sitTest"
    },
    {
        "sTitle": "uat测试",
        "mData": "uatTest"
    },
    {
        "sTitle": "投产测试",
        "mData": "productTest"
    },
    {
        "sTitle": "总计",
        "mData": "totalNum"
    }
];

var interfaceManagementTableLayout = [

	{
        "sTitle": "接口ID",
        "mData": "ecode"
    },
    {
        "sTitle": "接口名称",
        "mData": "interface_NAME"
    },
    {
        "sTitle": "操作ID",
        "mData": "operation_ID"
    },
    {
        "sTitle": "服务ID",
        "mData": "service_ID"
    },
    {
        "sTitle": "调用系统",
        "mData": "consumer_SYSAB"
    },
    {
        "sTitle": "调用系统名称",
        "mData": "consumer_SYSNAME"
    },
    {
        "sTitle": "提供系统",
        "mData": "provider_SYSAB"
    },
    {
        "sTitle": "提供系统名称",
        "mData": "provider_SYSNAME"
    }
];

var svcAsmTableLayout = [
	{
        "sTitle": "服务ID/名称",
        "mData": "serviceInfo"
    },
    {
        "sTitle": "操作ID/名称",
        "mData": "operationInfo"
    },
    {
        "sTitle": "交易代码/名称",
        "mData": "interfaceInfo"
    },
    {
        "sTitle": "源系统简称/名称",
        "mData": "consumeSysInfo"
    },
    {
        "sTitle": "调用方系统简称/名称",
        "mData": "passbySysInfo"
    },
    {
        "sTitle": "提供方系统简称/名称",
        "mData": "provideSysInfo"
    },
    {
        "sTitle": "调用报文类型",
        "mData": "consumeMsgType"
    },
    {
        "sTitle": "提供报文类型",
        "mData": "provideMsgType"
    },
    {
        "sTitle": "是否穿透",
        "mData": "through"
    },
    {
        "sTitle": "交易状态",
        "mData": "state"
    },
    {
        "sTitle": "修订次数",
        "mData": "modifyTimes"
    },
    {
        "sTitle": "上线日期",
        "mData": "onlineDate"
    },
    {
        "sTitle": "上线版本",
        "mData": "onlineVersion"
    },
    {
        "sTitle": "备注",
        "mData": "field"
    }
];

var serviceDetailTableLayout = [
	{
        "sTitle": "服务ID/名称",
        "mData": "serviceInfo"
    },
    {
        "sTitle": "操作ID/名称",
        "mData": "operationInfo"
    },
    {
        "sTitle": "交易代码/名称",
        "mData": "interfaceInfo"
    },
    {
        "sTitle": "经由系统简称/名称",
        "mData": "passbySysInfo"
    },
    {
        "sTitle": "提供方系统简称/名称",
        "mData": "provideSysInfo"
    },
    {
        "sTitle": "提供报文类型",
        "mData": "provideMsgType"
    },
    {
        "sTitle": "是否穿透",
        "mData": "through"
    },
    {
        "sTitle": "交易状态",
        "mData": "state"
    },
    {
        "sTitle": "修订次数",
        "mData": "modifyTimes"
    },
    {
        "sTitle": "上线日期",
        "mData": "onlineDate"
    },
    {
        "sTitle": "上线版本",
        "mData": "onlineVersion"
    },
    {
        "sTitle": "备注",
        "mData": "field"
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

var operationSlaTableLayout = [
    {
        "sTitle": "属性ID",
        "mData": "slaName"
    },
    {
        "sTitle": "属性值",
        "mData": "slaValue"
    },
    {
        "sTitle": "备注",
        "mData": "slaRemark"
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

var operationSDATableLayout = [
	{
        "sTitle": "序号",
        "mData": "seq"
    },
    {
        "sTitle": "英文名称",
        "mData": "structId"
    },
    {
        "sTitle": "元数据ID",
        "mData": "metadataId"
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
    },
    {
        "sTitle": "id",
        "mData": "id"
    },
    {
        "sTitle": "parentId",
        "mData": "parentId"
    }
];

var idaTableLayout = [
	{
        "sTitle": "序号",
        "mData": "seq"
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
//var operationTableLayout = [
//    {
//        "sTitle": "操作ID",
//        "mData": "serviceId"
//    },
//    {
//        "sTitle": "操作名称",
//        "mData": "serviceName"
//    },
//    {
//        "sTitle": "操作状态",
//        "mData": "versionState"
//    },
//    {
//        "sTitle": "操作备注",
//        "mData": "serviceRemark"
//    }
//];

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

var exportExcelLayout1 = [
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

var exportExcelLayout = [
    {
        "sTitle": "接口ID",
        "mData": "ecode"
    },
    {
        "sTitle": "接口名称",
        "mData": "interfaceName"
    },
    {
        "sTitle": "服务ID",
        "mData": "serviceId"
    },
    {
        "sTitle": "服务名称",
        "mData": "serviceName"
    },
    {
        "sTitle": "操作ID",
        "mData": "operationId"
    },
    {
        "sTitle": "操作名称",
        "mData": "operationName"
    },
    {
        "sTitle": "源系统",
        "mData": "sourceSys"
    },
    {
        "sTitle": "调用系统",
        "mData": "consumerSys"
    },
    {
        "sTitle": "提供系统",
        "mData": "providerSysAb"
    },
    {
        "sTitle": "状态",
        "mData": "versionSt"
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
        "mData": "onLineDate"
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
var slaTableLayout = [
	{
        "sTitle": "服务ID/名称",
        "mData": "serviceInfo"
    },
    {
        "sTitle": "操作ID/名称",
        "mData": "operationInfo"
    },
    {
        "sTitle": "交易代码/名称",
        "mData": "interfaceInfo"
    },
    {
        "sTitle": "提供方系统简称/名称",
        "mData": "provideSysInfo"
    },
    {
        "sTitle": "业务成功率",
        "mData": "successRate"
    },
    {
        "sTitle": "超时时间",
        "mData": "timeOut"
    },
    {
        "sTitle": "并发数",
        "mData": "currentCount"
    },
    {
        "sTitle": "平均响应时间",
        "mData": "averageTime"
    },
    {
        "sTitle": "备注",
        "mData": "remark"
    }
];
var operationTableLayout = [
	{
        "sTitle": "操作ID",
        "mData": "operationId"
	},
	{
        "sTitle": "操作名称",
        "mData": "operationName"
	},
	{
        "sTitle": "服务ID",
        "mData": "serviceId"
	},
	{
        "sTitle": "服务名称",
        "mData": "serviceName"
	},
	{
        "sTitle": "操作状态",
        "mData": "state"
	},
	{
        "sTitle": "审核状态",
        "mData": "audit.state"
    },
	{
        "sTitle": "开发版本",
        "mData": "version"
	},
	{
        "sTitle": "上线版本",
        "mData": "publishVersion"
	},
	{
        "sTitle": "上线日期",
        "mData": "publishDate"
	},
	{
        "sTitle": "动作",
        "mData": "operationId"
	},
	{
        "sTitle": "历史",
        "mData": "operationId"
	},
	{
        "sTitle": "调用情况",
        "mData": "operationId"
	}
];

var operationOlaTableLayout = [
    {
        "sTitle": "属性ID",
        "mData": "olaName"
    },
    {
        "sTitle": "属性值",
        "mData": "olaValue"
    },
    {
        "sTitle": "备注",
        "mData": "olaRemark"
    }
];

var metadataTableLayout = [
    {
        "sTitle": "元数据ID",
        "mData": "metadataId"
    },
  	{
        "sTitle": "元数据名称",
        "mData": "name"
    },
    {
        "sTitle": "元数据描述",
        "mData": "remark"
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
        "sTitle": "操作用户",
        "mData": "modifyUser"
    },
    {
        "sTitle": "更新时间",
        "mData": "updateTime"
    }
    //{
    //    "sTitle": "操作",
    //    "mData": ""
    //}
];
var mdtUsedTableLayout = [
    {
        "sTitle": "元数据ID",
        "mData": "metadataId"
    },
  	{
        "sTitle": "操作ID",
        "mData": "operationId"
    },
    {
        "sTitle": "操作名称",
        "mData": "operationName"
    },
    {
        "sTitle": "服务ID",
        "mData": "serviceId"
    },
    {
        "sTitle": "服务名称",
        "mData": "serviceName"
    },
    {
        "sTitle": "提供系统简称",
        "mData": "prdSysAB"
    },
    {
        "sTitle": "提供系统名称",
        "mData": "prdSysName"
    }
];
var serviceInfoTableLayout = [
    {
        "sTitle": "服务ID",
        "mData": "serviceId"
    },
  	{
        "sTitle": "服务名称",
        "mData": "serviceName"
    },
    {
        "sTitle": "服务描述",
        "mData": "serviceRemark"
    },
    {
        "sTitle": "分组Id",
        "mData": "categoryId"
    },
    {
        "sTitle": "分组名称",
        "mData": "serviceCategory.categoryName"
    },
    {
        "sTitle": "版本号",
        "mData": "version"
    },
    {
        "sTitle": "版本状态",
        "mData": "state"
    },
    {
        "sTitle": "审核状态",
        "mData": "audit.state"
    },
//    {
//        "sTitle": "操作用户",
//        "mData": "modifyUser"
//    },
    {
        "sTitle": "更新时间",
        "mData": "updateTime"
    }
];
var operationsByServiceIdTableLayout = [
    {
        "sTitle": "操作ID",
        "mData": "operationId"
    },
  	{
        "sTitle": "操作名称",
        "mData": "operationName"
    },
    {
        "sTitle": "操作描述",
        "mData": "remark"
    },
    {
        "sTitle": "版本号",
        "mData": "version"
    },
    {
        "sTitle": "版本状态",
        "mData": "state"
    },
    {
        "sTitle": "操作用户",
        "mData": "modifyUser"
    },
    {
        "sTitle": "更新时间",
        "mData": "updateTime"
    }
];
var systemInfoTableLayout = [
    {
        "sTitle": "系统Id",
        "mData": "systemId"
    },
  	{
        "sTitle": "系统简称",
        "mData": "systemAb"
    },
    {
        "sTitle": "系统名称",
        "mData": "systemName"
    },
    {
        "sTitle": "系统描述",
        "mData": "remark"
    },
    {
        "sTitle": "首次上线日期(提供方)",
        "mData": "firstPublishDate"
    },
    {
        "sTitle": "首次上线日期(调用方)",
        "mData": "secondPublishDate"
    }
];
var serviceCategoryTableLayout = [
    {
        "sTitle": "分组Id",
        "mData": "categoryId"
    },
  	{
        "sTitle": "分组名称",
        "mData": "categoryName"
    },
    {
        "sTitle": "上级分组",
        "mData": "parentId"
    }
];
var metadataStructsTableLayout = [
    {
        "sTitle": "元数据结构Id",
        "mData": "structId"
    },
  	{
        "sTitle": "元数据结构名称",
        "mData": "structName"
    },
    {
        "sTitle": "描述",
        "mData": "remark"
    }
];
var metadataStructsAttrTableLayout = [
    {
        "sTitle": "元数据结构Id",
        "mData": "structId"
    },
  	{
        "sTitle": "原始元数据Id",
        "mData": "elementId"
    },
    {
        "sTitle": "原始元数据名称",
        "mData": "elementName"
    },
    {
        "sTitle": "描述",
        "mData": "remark"
    },
    {
        "sTitle": "是否必输",
        "mData": "isRequired"
    },
    {
        "sTitle": "元数据ID",
        "mData": "metadataId"
    }
];
var operationHistoryTableLayout =[
	{
        "sTitle": "操作ID",
        "mData": "operationId"
	},
	{
        "sTitle": "操作名称",
        "mData": "operationName"
	},
	{
        "sTitle": "操作版本",
        "mData": "operationVersion"
	},
	{
        "sTitle": "操作状态",
        "mData": "operationState"
	},
	{
        "sTitle": "服务ID",
        "mData": "serviceId"
	},
	{
        "sTitle": "服务版本",
        "mData": "serviceVersion"
	},
	{
        "sTitle": "备注",
        "mData": "remark"
	},
	{
        "sTitle": "详细信息",
        "mData": "operationId"
	},
	{
        "sTitle": "操作用户",
        "mData": "modifyUser"
    },
    {
        "sTitle": "更新时间",
        "mData": "updateTime"
    }
];
var protocolInfoTableLayout =[
	{
        "sTitle": "系统Id",
        "mData": "sysId"
	},
	{
        "sTitle": "协议类型",
        "mData": "connectMode"
	},
	{
        "sTitle": "访问地址",
        "mData": "sysAddr"
	},
	{
        "sTitle": "应用场景",
        "mData": "appScene"
	},
	{
        "sTitle": "报文类型",
        "mData": "msgType"
	},
	{
        "sTitle": "系统类型",
        "mData": "sysType"
	},
	{
        "sTitle": "编码格式",
        "mData": "codeType"
	},
	{
        "sTitle": "mac校验",
        "mData": "macFlag"
	}
];
var invokeTableLayout = [
    {
        "sTitle": "操作ID/操作名称",
        "mData": "operationInfo"
    },
    {
        "sTitle": "服务ID/服务名称",
        "mData": "serviceInfo"
    },
    {
        "sTitle": "接口ID/接口名称",
        "mData": "interfaceInfo"
    },
    {
        "sTitle": "源系统简称/名称",
        "mData": "consumeSysInfo"
    },
    {
        "sTitle": "调用方系统简称/名称",
        "mData": "passbySysInfo"
    },
    {
        "sTitle": "提供方系统简称/名称",
        "mData": "provideSysInfo"
    },
    {
        "sTitle": "调用方报文类型",
        "mData": "consumeMsgType"
    },
    {
        "sTitle": "提供方报文类型",
        "mData": "provideMsgType"
    },
    {
        "sTitle": "接口方向",
        "mData": "direction"
    },
    {
        "sTitle": "是否穿透",
        "mData": "through"
    },
    {
        "sTitle": "版本状态",
        "mData": "state"
    },
    {
        "sTitle": "上线版本号",
        "mData": "onlineVersion"
    },
    {
        "sTitle": "上线日期",
        "mData": "onlineDate"
    },
    {
        "sTitle": "备注",
        "mData": "field"
    }
];
var consumerTableLayout = [
	{
        "sTitle": "系统ID",
        "mData": "systemId"
	},
	{
        "sTitle": "系统英文名称",
        "mData": "systemAb"
	},
	{
        "sTitle": "系统中文名称",
        "mData": "systemName"
	},
	{
        "sTitle": "备注",
        "mData": "remark"
	}
];

var configExportTableLayout = [
	{
        "sTitle": "服务ID/名称",
        "mData": "serviceInfo"
    },
    {
        "sTitle": "操作ID/名称",
        "mData": "operationInfo"
    },
    {
        "sTitle": "交易代码/名称",
        "mData": "interfaceInfo"
    },
    {
        "sTitle": "源系统",
        "mData": "consumeSysInfo"
    },
    {
        "sTitle": "调用方系统",
        "mData": "passbySysInfo"
    },
    {
        "sTitle": "提供方系统简称/名称",
        "mData": "provideSysInfo"
    },
    {
        "sTitle": "接口方向",
        "mData": "direction"
    },
    {
        "sTitle": "调用报文类型",
        "mData": "consumeMsgType"
    },
    {
        "sTitle": "提供报文类型",
        "mData": "provideMsgType"
    },
    {
        "sTitle": "是否穿透",
        "mData": "through"
    },
    {
        "sTitle": "交易状态",
        "mData": "state"
    },
    {
        "sTitle": "修订次数",
        "mData": "modifyTimes"
    },
    {
        "sTitle": "上线日期",
        "mData": "onlineDate"
    },
    {
        "sTitle": "上线版本",
        "mData": "onlineVersion"
    },
    {
        "sTitle": "备注",
        "mData": "field"
    }
];
var userTableLayout = [
	{
        "sTitle": "用户ID",
        "mData": "id"
    },
    {
        "sTitle": "用户名",
        "mData": "name"
    },
	{
        "sTitle": "角色",
        "mData": "role"
    },
    {
        "sTitle": "所属机构",
        "mData": "org"
    },
	{
        "sTitle": "上次登录时间",
        "mData": "lastdate"
    },
    {
        "sTitle": "用户状态",
        "mData": "status"
    },
	{
        "sTitle": "备注",
        "mData": "remark"
    },
    {
        "sTitle": "操作",
        "mData": "id"
    }
];
var roleTableLayout = [
	{
        "sTitle": "角色ID",
        "mData": "id"
    },
    {
        "sTitle": "角色名称",
        "mData": "name"
    },
	{
        "sTitle": "备注",
        "mData": "remark"
    }
];
var roleTableLayout1 = [
	{
        "sTitle": "角色ID",
        "mData": "id"
    },
    {
        "sTitle": "角色名称",
        "mData": "name"
    },
	{
        "sTitle": "备注",
        "mData": "remark"
    },
    {
    	"sTitle": "操作",
        "mData": "id"
    }
];
var functionTableLayout = [
	{
        "sTitle": "权限ID",
        "mData": "id"
    },
    {
        "sTitle": "权限名称",
        "mData": "name"
    },
	{
        "sTitle": "访问路径",
        "mData": "url"
    },	{
        "sTitle": "父权限",
        "mData": "parent"
    },
    {
        "sTitle": "备注",
        "mData": "remark"
    }
];
var OrgTableLayout = [
	{
        "sTitle": "机构ID",
        "mData": "orgId"
    },
    {
        "sTitle": "机构中文名称",
        "mData": "orgName"
    },
	{
        "sTitle": "机构英文名称",
        "mData": "orgAB"
    },	{
        "sTitle": "机构状态",
        "mData": "orgStatus"
    }
];
var auditOperationTableLayout = [
	{
        "sTitle": "操作ID",
        "mData": "operationId"
	},
	{
        "sTitle": "操作名称",
        "mData": "operationName"
	},
	{
        "sTitle": "服务ID",
        "mData": "serviceId"
	},
	{
        "sTitle": "服务名称",
        "mData": "service.serviceName"
	},
	{
        "sTitle": "开发版本",
        "mData": "version"
	},
	{
        "sTitle": "审核状态",
        "mData": "audit.state"
    },
	{
        "sTitle": "动作",
        "mData": "operationId"
	}
];
var auditServiceInfoTableLayout = [
    {
        "sTitle": "服务ID",
        "mData": "serviceId"
    },
  	{
        "sTitle": "服务名称",
        "mData": "serviceName"
    },
    {
        "sTitle": "开发版本",
        "mData": "version"
    },
    {
        "sTitle": "审核状态",
        "mData": "audit.state"
    },
    {
        "sTitle": "操作用户",
        "mData": "modifyUser"
    }
    //{
    //    "sTitle": "动作",
    //    "mData": "operationId"
    //}
];
var passwordTableLayout = [
    {
        "sTitle": "用户名",
        "mData": "name"
    },
  	{
        "sTitle": "所属角色",
        "mData": "role"
    },
    {
        "sTitle": "所属机构",
        "mData": "org"
    },
    {
        "sTitle": "最后更新时间",
        "mData": "lastdate"
    },
    {
        "sTitle": "用户状态",
        "mData": "status"
    }
];
var publishTotalViewTableLayout = [
    {
        "sTitle": "投产次数",
        "mData": "countOfPublishTimes"
    },
  	{
        "sTitle": "提供方数量",
        "mData": "countOfProviderSys"
    },
    {
        "sTitle": "调用方数量",
        "mData": "countOfConsumerSys"
    },
    {
        "sTitle": "投产服务数量",
        "mData": "countOfService"
    },
    {
        "sTitle": "投产操作数量",
        "mData": "countOfOperation"
    },
    {
        "sTitle": "服务修订次数",
        "mData": "countOfModifyTimes"
    },
    {
        "sTitle": "服务下线数量",
        "mData": "countOfOffLine"
    },
    {
        "sTitle": "服务删除数量",
        "mData": "countOfDeletedService"
    }
];
var auditIdaTableLayout = [
	{
        "sTitle": "序号",
        "mData": "seq"
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
        "sTitle": "规模",
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
var batchExportTableLayout = [
	{
        "sTitle": "服务Id",
        "mData": "serviceId"
    },
    {
        "sTitle": "操作Id",
        "mData": "operationId"
    },
    {
        "sTitle": "交易代码",
        "mData": "interfaceId"
    },
    {
        "sTitle": "提供方",
        "mData": "provideSys"
    },
    {
        "sTitle": "提供报文类型",
        "mData": "provideMsgType"
    },
    {
        "sTitle": "调用报文类型",
        "mData": "consumeMsgType"
    },
    {
        "sTitle": "是否穿透",
        "mData": "through"
    }
];
var tranLinkTableLayout = [
    {
        "sTitle": "交易代码",
        "mData": "TRANCODE"
    },
    {
        "sTitle": "交易名称",
        "mData": "TRANNANE"
    },
    {
        "sTitle": "提供方",
        "mData": "PROVIDER"
    },
    {
        "sTitle": "调用方",
        "mData": "CONSUMER"
    },
    {
        "sTitle": "调用方报文格式",
        "mData": "CONSUMERMSGTYPE"
    },
    {
        "sTitle": "交易链路",
        "mData": "TRANCODE"
    }
];
var tranCountTableLayout =[
	{
        "sTitle": "系统",
        "mData": "name"
    },
    {
        "sTitle": "提供交易数量",
        "mData": "ptrancount"
    },
    {
        "sTitle": "调用交易数量",
        "mData": "ctrancount"
    }
];
var AllTranProviderTableLayout = [
    {
        "sTitle": "交易代码",
        "mData": "TRANCODE"
    },
    {
        "sTitle": "交易名称",
        "mData": "TRANNANE"
    },
    {
        "sTitle": "提供方",
        "mData": "PROVIDER"
    },
    {
        "sTitle": "提供方报文格式",
        "mData": "PROVIDERMSGTYPE"
    },
    {
        "sTitle": "负责人",
        "mData": "CHARGER"
    },
    {
        "sTitle": "备注",
        "mData": "REMARK"
    }
];
var AllTranConsumerTableLayout = [
    {
        "sTitle": "交易代码",
        "mData": "TRANCODE"
    },
    {
        "sTitle": "交易名称",
        "mData": "TRANNANE"
    },
    {
        "sTitle": "调用方",
        "mData": "CONSUMER"
    },
    {
        "sTitle": "经由系统",
        "mData": "PASSEDSYS"
    },
    {
        "sTitle": "提供方",
        "mData": "PROVIDER"
    },
    {
        "sTitle": "调用方报文格式",
        "mData": "CONSUMERMSGTYPE"
    },
    {
        "sTitle": "前台交易码",
        "mData": "FRONTTRANCODE"
    },
    {
        "sTitle": "负责人",
        "mData": "CHARGER"
    },
    {
        "sTitle": "备注",
        "mData": "REMARK"
    }
];
var publishOperationsTableLayout = [
	{
        "sTitle": "操作ID",
        "mData": "operationId"
	},
	{
        "sTitle": "操作名称",
        "mData": "operationName"
	},
	{
        "sTitle": "服务ID",
        "mData": "serviceId"
	},
	{
        "sTitle": "服务名称",
        "mData": "service.serviceName"
	}
];
var publishServicesTableLayout = [
	{
        "sTitle": "服务ID",
        "mData": "serviceId"
	},
	{
        "sTitle": "服务名称",
        "mData": "serviceName"
	}
];