<%--
  Author: zzx
  Date: 2020-12-26 12:26
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}">
    <script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="layer/layer.js"></script>
    <script type="text/javascript">
        $(function () {
            /*
                发送数组请求方法一，缺陷是controller接受时参数需要写成array[]，因为jquery发送请求时的参数后面拼接了中括号
           */
            $("#btn1").click(function () {
                $.ajax({
                    url: "test/sendArray/one.html",             // 请求目标资源的url
                    type: "POST",                               // 请求方式
                    data: {                                     // 请求参数
                        array: [0, 8, 2, 7],
                    },
                    dataType: "text",                           // 如何对待服务器端返回的数据
                    success: function (data) {                  // 服务器端处理请求成功后调用的回调函数
                        alert(data);
                    },
                    error: function (data) {                    // 服务器端处理请求失败后调用的回调函数
                        alert(data);
                    }
                });
            });

            /*方案二将数组拆开来发送参数，这种方案只能当array是某一个实体类的属性时，才能被SpringMVC接收到*/
            $("#btn2").click(function () {
                $.ajax({
                    url: "test/sendArray/two.html",             // 请求目标资源的url
                    type: "POST",                               // 请求方式
                    data: {                                     // 请求参数
                        "array[0]": 0,
                        "array[1]": 8,
                        "array[2]": 2,
                        "array[3]": 7
                    },
                    dataType: "text",                           // 如何对待服务器端返回的数据
                    success: function (data) {                  // 服务器端处理请求成功后调用的回调函数
                        alert(data);
                    },
                    error: function (data) {                    // 服务器端处理请求失败后调用的回调函数
                        alert(data);
                    }
                });
            });

            $("#btn3").click(function () {
                // 准备好要发送到服务器端的数组
                var array = [0, 8, 2, 7];

                // 将JSON数组转化为JSON字符串
                var requestBody = JSON.stringify(array);

                $.ajax({
                    url: "test/sendArray/three.html",               // 请求目标资源的url
                    type: "POST",                                   // 请求方式
                    data: requestBody,                              // 请求体
                    contentType: "application/json;charset=UTF-8",  //设置请求体的内容类型，告诉服务器这次请求的请求体是JSON格式
                    dataType: "text",                               // 如何对待服务器端返回的数据
                    success: function (data) {                      // 服务器端处理请求成功后调用的回调函数
                        alert(data);
                    },
                    error: function (data) {                        // 服务器端处理请求失败后调用的回调函数
                        alert(data);
                    }
                });
            });

            $("#btn4").click(function () {
                layer.msg("弹框");
            });
        })
    </script>
</head>
<body>
<a href="test/ssm.html">测试SSM整合环境</a>

<br>
<br>

<button id="btn1">send [0,8,2,7] case one</button>

<br>
<br>

<button id="btn2">send [0,8,2,7] case two</button>

<br>
<br>

<button id="btn3">send [0,8,2,7] case Three</button>

<br>
<br>

<button id="btn4">点我弹框</button>
</body>
</html>
