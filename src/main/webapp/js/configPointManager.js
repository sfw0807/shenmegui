/**
 * Created by Administrator on 2015/5/13.
 */
$(function() {
    var tables = {};
    var asInitVals = new Array();
    $('#tabs').tabs();
    $("#tab0").click(function(e) {
        if(tables["configExportManagerTable"]){
            tables["configExportManagerTable"].fnAdjustColumnSizing();
        }
    });
    /**
     * init service interface relate table
     * @param {Object} result
     *
     */
    var initconfigExportManagerTable = function initconfigExportManagerTable(result) {
        //初始化对Grid的操作事件
        var columnClickEventInit = function columnClickEventInit() {
            $("#configExportManagerTable tbody tr").unbind("click");
            $("#configExportManagerTable tbody tr").click(function(e) {
                $(this).toggleClass("row_selected");
            });
        };
        //初始化configExportTable
        tables["configExportManagerTable"] = $("#configExportManagerTable").dataTable( {
            "aaData" : result,
            "aoColumns" : configExportManagerTableLayout,
            "aoColumnDefs" : [
                {
                    "sClass" : "center",
                    "aTargets" : [ 0, 1, 2, 3]
                }
            ],
            "bJQueryUI": true,
            "bAutoWidth" : true,
            "bScrollCollapse" : "full_numbers",
            "bPaginate" : true,
            "bSort" : true,
            "oLanguage" : oLanguage,
            "fnDrawCallback" : function() {
                columnClickEventInit();
            }
        });
        tables["configExportManagerTable"].fnAdjustColumnSizing();
    };
    conFilePathRelateManager.getAllConFilePathRelateInfo(initconfigExportManagerTable);

    //初始化操作Grid的搜索框
    var initconfigExportManagerTableFooter = function initconfigExportManagerTableFooter() {
        $("#configExportManagerTable tfoot input").keyup(
            function() {
                tables["configExportManagerTable"].fnFilter(this.value, $(
                    "#configExportManagerTable tfoot input").index(this),null,null,null,false);
            });
        $("#configExportManagerTable tfoot input").each(function(i) {
            asInitVals[i] = this.value;
        });
        $("#configExportManagerTable tfoot input").focus(function() {
            if (this.className == "search_init") {
                this.className = "";
                this.value = "";
            }
        });
        $("#configExportManagerTable tfoot input")
            .blur(
            function(i) {
                if (this.value == "") {
                    this.className = "search_init";
                    this.value = asInitVals[$(
                        "#configExportManagerTable tfoot input")
                        .index(this)];
                }
            });
    };
    initconfigExportManagerTableFooter();

    $('#exportConfig').button().click(function () {
        var table = tables["configExportTable"];
        // 选择的行数
        var rowsSelected = table.$("tr.row_selected");
        if(rowsSelected.length == 0){
            alert('请选择要导出配置的接口!');
            return false;
        }
        var params = '';
        var ecodeArr = '';
        for(var i=0;i<rowsSelected.length;i++){
            var selectedDatas = table.fnGetData(table.$("tr.row_selected")[i]);
            var ecode = selectedDatas["interfaceInfo"];
            ecode = ecode.substring(0,ecode.indexOf("/"));
            var serviceId = selectedDatas["serviceInfo"];
            serviceId = serviceId.substring(0,serviceId.indexOf("/"));
            var operationId = selectedDatas["operationInfo"];
            operationId = operationId.substring(0,operationId.indexOf("/"));
            var consumeMsgType = selectedDatas["consumeMsgType"];
            var provideMsgType = selectedDatas["provideMsgType"];
            var through = selectedDatas["through"];
            var prdSysId = selectedDatas["provideSysInfo"];
            var consumeSysId = selectedDatas["consumeSysInfo"];
            var direction = selectedDatas["direction"];
            if(direction == '提供方'){
                direction = '1';
            }
            else if(direction == '调用方'){
                direction = '0';
            }
            if(through == '是'){
                through = '0';
            }
            else{
                through = '1';
            }
            prdSysId = prdSysId.substring(0,prdSysId.indexOf("/"));
            consumeSysId = consumeSysId.substring(0,consumeSysId.indexOf("/"));
            var info = ecode+","+consumeMsgType+","+provideMsgType+","+through+","+serviceId+","+operationId+","+prdSysId+","+direction+","+consumeSysId;
            console.log(info);
            params = params + info + ":";
            ecodeArr += ecode + ",";
        }
        params = params.substring(0,params.length-1);
        ecodeArr = ecodeArr.substring(0,ecodeArr.length-1);
        function callBack(result){
            if(result == null || result == ''){
                $.fileDownload("../export/config/" + params, {});
            }
            else{
                alert('服务或操作 [' + result + "] 未审核通过，请先审核通过!");
            }
            rowsSelected.toggleClass("row_selected");
        }
        svcAsmRelateManager.checkEcodeAudit(ecodeArr, callBack);
    });

    $('#exportMdt').button().click(function () {
        $.fileDownload("../export/exportMetadata", {});
    });

    $('#checkAll').button().click(function () {
        $("#configExportManagerTable tbody tr").addClass("row_selected");
    });
    $('#toggleAll').button().click(function () {
        $("#configExportManagerTable tbody tr").toggleClass("row_selected");
    });

    $('#publish').button().click(function () {
        var table = tables["configExportManagerTable"];
        // 选择的行数
        var rowsSelected = table.$("tr.row_selected");
        if(rowsSelected.length == 0){
            alert('请选择要发布到的服务器地址!');
            return false;
        }
        var params = getUrlRequest();
        var metadatas = "";
        for(var i=0;i<rowsSelected.length;i++){
            var selectedDatas = table.fnGetData(table.$("tr.row_selected")[i]);
            var name = selectedDatas["name"];

            metadatas = name;
        }

        function callBack(result){
            if(result == null || result == ''){
                $.fileDownload("../export/config/" + params, {});
            }
            else{
                alert('服务或操作 [' + result + "] 未审核通过，请先审核通过!");
            }
            rowsSelected.toggleClass("row_selected");
        }
        conFilePathRelateManager.publishConFilePathRelateInfo(params,metadatas, callBack);
    });

});


function getUrlRequest() {
    var url = location.search; //获取url中"?"符后的字串
    var str = "";
    if (url.indexOf("?") != -1) {
         str = url.substr(1);
        if (str.indexOf("=") != -1) {
             str = str.substr(7);
        }
    }
    return str;
}
