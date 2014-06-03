/**
 * Created with IntelliJ IDEA.
 * User: Vincent Fan
 * Date: 14-2-23
 * Time: 下午4:55
 * To change this template use File | Settings | File Templates.
 */

var companyTableLayout = [
    {
        "sTitle": "编号",
        "mData": "id"
    },
    {
        "sTitle": "公司名称",
        "mData": "companyName"
    }

];

var projectTableLayout = [
    {
        "sTitle": "编号",
        "mData": "id"
    },
    {
        "sTitle": "项目名称",
        "mData": "projectName"
    },
    {
        "sTitle": "当前电费(元/度)",
        "mData": "electricityCharge"
    },
    {
        "sTitle": "节能分享比例(%)",
        "mData": "partsRatio"
    },
    {
        "sTitle": "项目开始日期",
        "mData": "startDate"
    },
    {
        "sTitle": "项目结束日期",
        "mData": "endDate"
    }
];

var gprsTableLayout = [
    {
        "sTitle": "编号",
        "mData": "id"
    },
    {
        "sTitle": "GPRS识别码",
        "mData": "identifier"
    },
    {
        "sTitle": "GPRS名称",
        "mData": "name"
    }
];

var ammeterTableLayout = [
    {
        "sTitle": "编号",
        "mData": "id"
    },
    {
        "sTitle": "电表名称",
        "mData": "pumpName"
    },
    {
        "sTitle": "电表标识",
        "mData": "name"
    },
    {
        "sTitle": "互感器倍率",
        "mData": "sensorRate"
    },
    {
        "sTitle": "技改前能耗",
        "mData": "formerCost"
    },
    {
        "sTitle": "报警上限",
        "mData": "upperLimit"
    },
    {
        "sTitle": "报警下限",
        "mData": "lowerLimit"
    }
];

var ammeterRecordTableLayout  = [
    {
        "sTitle": "编号",
        "mData": "id"
    },
    {
        "sTitle": "电表标识",
        "mData": "ammeterName"
    },
    {
        "sTitle": "电表电量读数（kWh）",
        "mData": "ammeterValue"
    },
    {
        "sTitle": "累时器读数（h）",
        "mData": "timeSum"
    },
    {
        "sTitle": "抄表时间",
        "mData": "recordDate"
    },
    {
        "sTitle": "瞬时功率（h）",
        "mData": "ammeterValue"
    }
];
