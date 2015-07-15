<%@ page contentType="text/html;charset=utf-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>字段映射导入</title>

</head>
<body>
    <div class="container">
        <h3>导入Excel</h3>
        <form id="uploadimg-form"  action="/excelHelper/fieldimport" method="post" enctype="multipart/form-data">
            <input type="file" title="选择文件" name="file" id="file"/><br /><br />
            <select name="select">
                <option value="Y">覆盖</option>
                <option value="N">不覆盖</option>
            </select><br /><br />
            <input id="fileBtn" type="submit" class="btn" value="文件上传"/><br /><br />
        </form>

    </div>

   
</body>
</html>
