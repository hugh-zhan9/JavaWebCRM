<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>文件上传</title>
    </head>
    <body>
        <!---二进制传输文件-->
        <form action="upload" method="POST" enctype="multipart/form-data">
            <input type="file" name="files" multiple="multiple"/> <!--多文件上传-->
            <button>上传</button>
        </form>
        
    </body>
</html>