<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/9/10 0010
  Time: 下午 10:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>秒杀详情页面</title>
    <%@include file="common/header.jsp"%>


</head>
<body>
    <div class="container">
        <div class="panel panel-default">
            <div class="panel-heading text-center" >
               <h1>${seckill.name}</h1>
            </div>

            <div class="panel-body text-center">
                <h2 text-danger>
                <%--显示time图标--%>
                    <span class="glyphicon glyphicon-time"></span>
                <%--展示倒计时--%>
                    <span class="glyphicon" id="seckill-box"></span>
                </h2>
            </div>

        </div>
    </div>
  <%--  <!-- 按钮触发模态框 -->
    <div class="text-center">
        <button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#killPhoneModal">
        开始秒杀
        </button>
    </div>--%>
    <%--登陆弹出层 输入电话--%>
    <!-- 模态框（Modal） -->
    <div class="modal fade" id="killPhoneModal" >
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                   <%-- <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>--%>
                    <%--<h4 class="modal-title" id="myModalLabel">--%>
                        <h3 class="modal-title text-center">
                            <span class="glyphicon glyphicon-phone"> </span>秒杀电话
                        </h3>
                   <%-- </h4>--%>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-xs-8 col-xs-offset-2">
                            <input type="text" name="killPhone" id="killPhoneKey" placeholder="填写手机号" class="form-control">
                        </div>
                    </div>
                </div>


                <div class="modal-footer">
                    <%--验证信息--%>
                    <%--前端字典--%>
                    <span id="killPhoneMessage" class="glyphicon"> </span>
                    <button type="button" id="killPhoneBtn" class="btn btn-success">
                        <span class="glyphicon glyphicon-phone"> </span>
                        提交更改
                    </button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>



    <!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
   <%-- <script src="https://cdn.bootcss.com/jquery/2.0.0/jquery.min.js"></script>--%>
    <script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
    <!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>


    <%--使用CDN 获取公共js http://www.bootcdn.cn/--%>
    <%--jQuery Cookie操作插件--%>
    <script src="http://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
    <%--jQuery countDown倒计时插件--%>
    <script src="http://cdn.bootcss.com/jquery.countdown/2.1.0/jquery.countdown.min.js"></script>
    <%--base64加密--%>
    <script src="https://cdn.bootcss.com/Base64/1.0.1/base64.min.js"></script>
    <%--交互逻辑--%>
    <script src="${pageContext.request.contextPath}/resources/script/seckill.js" type="text/javascript"></script>

    <script type="text/javascript">

        $(function () {
            seckill.detail.init({
                //使用EL表达式传入参数
              seckillId:${seckill.seckillId},
              startTime:${seckill.startTime.time},
              endTime:${seckill.endTime.time}
            });
        })
    </script>
</body>
</html>
