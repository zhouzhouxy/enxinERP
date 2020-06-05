<!DOCTYPE html>
<html>
<head>
    <meta http-equiv=”Content-Type” content=”text/html; charset=utf-8″>
    <meta content="webkit" name="renderer"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate"/>
    <meta content="no-cache" http-equiv="Pragma"/>
    <meta http-equiv="Expires" content="0"/>
    <meta content="width=device-width, initial-scale=1, user-scalable=1" name="viewport"/>
    <title>恩信ERP</title>
    <meta name="keywords" content="ERP,ERP系统,进销存,进销存系统" />
    <meta name="description" content="ERP系统,进销存系统" />
    <link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon"/>
    <script src="js/jquery/jquery-1.12.4.min.js"></script>
    <!--[if lt IE 9]>
    <script src="js/common/h5fix.min.js"></script><![endif]-->
    <link rel="stylesheet" href="css/fonts/font-icons.min.css">
    <link rel="stylesheet" href="js/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="js/select2/4.0/select2.css">
    <link rel="stylesheet" href="js/icheck/1.0/minimal/grey.css">
    <link rel="stylesheet" href="js/adminlte/css/AdminLTE.min.css">
    <link rel="stylesheet" href="css/jsherp.css">
    <link rel="stylesheet" href="js/adminlte/css/skins/skin-blue-light2.css">
</head>
<body class="hold-transition login-page">
<div class="wrapper">
    <!--[if lte IE 9]><a style="position:absolute;top:0;left:0;z-index:100000;display:block;width:100%;font-size:14px;
    color:#F00;text-decoration:none;background-color:#faffb3;text-align:center;" href="js/upbw/index.html"
                         target="_blank">
    您的浏览器版本过低或在兼容模式下，导致打开速度过慢，提升速度您可以切换到极速模式或升级为最新版，点击此处查看详情。</a>
    <![endif]-->
    <!--[if lte IE 8]>
    <script>window.location.href = 'js/static/upbw/index.html';</script><![endif]-->
    <link rel="stylesheet" href="js/modules/sys/sysLogin.css">
    <div class="login-box">
        <div class="login-logo">
            <a href="/">
                <b>华夏ERP</b>
                <small>V2.1</small>
            </a>
        </div>
        <div class="login-box-body">
            <div class="form-group has-feedback">
                <span class="glyphicon glyphicon-user form-control-feedback" title="登录账号"></span>
                <input type="text" id="loginName" name="loginName" class="form-control required"
                       data-msg-required="请填写登录账号." placeholder="登录账号"/>
            </div>
            <div class="form-group has-feedback">
                <span class="glyphicon glyphicon-lock form-control-feedback"
                      title="登录密码，鼠标按下显示密码"
                      onmousedown="$('#password').attr('type','text')"
                      onmouseup="$('#password').attr('type','password')">
                </span>
                <input type="password" id="password" name="password" class="form-control required"
                       data-msg-required="请填写登录密码." placeholder="登录密码" autocomplete="off"/>
            </div>
            <div class="form-group">
                <div class="mt5 icheck">
                    <label title="公共场所慎用,下次不需要再填写帐号"><input type="checkbox"
                                                            id="rememberUserCode" class="form-control" data-style="minimal-grey">记住账号</label> &nbsp;
                    <label title="公共场所慎用,下次不需要再填写帐号和密码"><input type="checkbox"
                                                               id="rememberMe" class="form-control" data-style="minimal-grey"> 自动登录</label>
                </div>
            </div>
            <div class="form-group">
                <input type="hidden" name="__url" value="">
                <button type="submit" class="btn btn-primary btn-block btn-flat"
                        id="btnSubmit" data-loading="登录验证成功，正在进入..."
                        data-login-valid="正在验证登录，请稍候...">立即登录
                </button>
            </div>
            <div class="row">
                <div class="col-xs-12">
                    <a href="register.html" class="pull-left"><b>[ 立即注册 ]</b></a>
                    <div class="dropdown pull-right">
                        <a href="http://www.huaxiaerp.com/" class="dropdown-toggle" target="_blank">
                            <b><i class="fa icon-globe"></i> 官方网站</b>
                        </a>
                    </div>
                </div>
            </div>
        </div>
        <div class="login-copyright">
            © 2015-2030 华夏ERP - Powered By
            <a style="color:#00458a;" href="http://www.huaxiaerp.com/" target="_blank">官方网站</a>
        </div>
    </div>
</div>

<a id="scroll-up" href="#" class="btn btn-sm"><i class="fa fa-angle-double-up"></i></a>
<script src="js/bootstrap/js/bootstrap.min.js"></script>
<script src="js/select2/4.0/select2.js"></script>
<script src="js/select2/4.0/i18n/zh_CN.js"></script>
<script src="js/layer/3.1/layer.js"></script>
<script src="js/jquery-validation/1.16/jquery.validate.js"></script>
<script src="js/jquery-validation/1.16/localization/messages_zh_CN.js"></script>
<script src="js/jquery-validation/1.16/jquery.validate.extend.js"></script>
<script src="js/common/jsherp.js"></script>
<script src="js/common/i18n/jsherp_zh_CN.js"></script>
<script src="js/common/common.js"></script>
<script src="js/md5/md5.js"></script>
<script src="js/modules/sys/sysLogin.js"></script>