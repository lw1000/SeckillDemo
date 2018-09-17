/**
 * Created by Administrator on 2018/9/11 0011.
 */
//存放主要交互逻辑代码
//JavaScript模块化
    //seckill.detail.init(params);
var seckill={
    //封装秒杀相关ajax URL
    URL:{
        now:function () {
            return '/SeckillDemo/seckill/time/now';
        },
        exposer:function (seckillId) {
            return '/SeckillDemo/seckill/'+seckillId+'/exposer';
        },
        execution:function (seckillId,md5) {
            return '/SeckillDemo/seckill/'+seckillId+'/'+md5+'/execution';
        }


    },



    //验证手机号
    validataPhone:function (killPhone) {
       //直接判断对象会看对象是否为空,空就是undefine就是false; isNaN 非数字返回true
        if(killPhone && killPhone.length==11 && !isNaN(killPhone) ){
            return true;
        }else {
            return false;
        }
    },

    //时间验证，计时交互
    countDown:function (seckillId,nowTime,startTime,endTime) {
        var seckillbox=$('#seckill-box');
        if(nowTime>endTime){
            //秒杀结束
            seckillbox.html('秒杀结束！')
        }else if(nowTime<startTime){
            //秒杀未开始,计时时间绑定
            var killTime=new Date(startTime+1000);
            //jQuery提供的countdon计时插件
            seckillbox.countdown(killTime,function (event) {
                //时间格式
                var format=event.strftime('秒杀倒计时： %D天  %H时  %M分  %S秒');
                //填充进盒子
                seckillbox.html(format).show();
                //秒杀完成后回调事件
            }).on('finish.countdown',function () {
                // 获取秒杀地址，控制显示逻辑，执行秒杀
                seckll.handleSeckill(seckillId,seckillbox);
            });
        }else {
            //秒杀开始，获取秒杀地址，控制显示逻辑，执行秒杀
            seckill.handleSeckill(seckillId,seckillbox);
        }
    },

     //获取秒杀地址，控制显示逻辑，执行秒杀
    handleSeckill:function (seckillId,seckillbox) {
        seckillbox.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">执行秒杀</button>');
        $.post(seckill.URL.exposer(seckillId),{},function (result) {
            //在回调函数中要执行的的交互流程
            if(result!=null && result['success']){
                var exposer=result['data'];
                if(exposer['exporsed']){
                    //秒杀开启
                    //获取秒杀地址
                    var md5=exposer['md5'];
                    var killUrl=seckill.URL.execution(seckillId,md5);
                    console.log('获取秒杀地址:'+killUrl);
                    //绑定一次点击事件
                    $('#killBtn').one('click',function () {
                        //执行秒杀
                        //1.点击一次后禁用按钮
                        $(this).addClass('disabled');
                        //2.发送秒杀请求执行秒杀
                        $.post(killUrl,{},function (result) {
                            if(result && result['success']){
                                //拿到秒杀结果集
                                var killResult= result['data'];
                                //拿到秒杀状态
                                var state= killResult['state'];
                                //拿到秒杀状态描述
                                var stateInfo=killResult['stateInfo'];
                                //显示秒杀结果
                                seckillbox.html('<span class="label label-success">'+stateInfo+'</span>');
                            }else {
                                console.log('result:'+result['data']);
                            }
                        });
                    });
                   seckillbox.show();
                }else {
                    //秒杀未开启（由于浏览器计时偏差，以为时间到了，结果时间并没到，需要重新计时）
                  var now= exposer['now'];
                  var start=exposer['start'];
                  var end= exposer['end'];
                  //重新计算计时逻辑
                    console.log('秒杀未开启：'+exposer);
                 seckill.countDown(seckillId,now,start,end);
                }
            }else {
                console.log('result:'+result);
            }

        });

    },


    //详情页秒杀逻辑
    detail: {
        //详情页初始化
        init: function (params) {
            //手机验证和登陆，计时交互
            //规划我们的交互流程
            //在cookie中查找手机号
            var killPhone = $.cookie('killPhone');
            var seckillId = params['seckillId'];
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            //验证手机号
            if (!seckill.validataPhone(killPhone)) {
                //绑定killPhone
                //控制输出
                var killPhoneModal = $('#killPhoneModal');
                killPhoneModal.modal({
                    show: true,//显示弹出层
                    backdrop: 'static', //禁止位置关闭
                    keyboard: false //关闭键盘时间
                });

                //点击提交按钮时
                $('#killPhoneBtn').click(function () {
                    //获取输入的手机号
                    var inputPhone=$('#killPhoneKey').val();
                    console.log("inputPhone: " + inputPhone);
                    //再次判断是否符合手机号合法性、
                    if(seckill.validataPhone(inputPhone)){
                        //手机号合法则写入cookie,有效期7天，并且在“/seckill下才生效”
                        $.cookie('killPhone',inputPhone,{expiers:7,path:'/SeckillDemo'});
                        //刷新界面
                        window.location.reload();
                    }else {
                        //错误文案信息抽取到前端字典里
                        //先隐藏起来（.hide()）再往HTML中填充数据，显示时间为300毫秒（.show(300)）
                        $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误!</label>').show(300);
                    }
                });
            }
            //验证手机号通过，用户已登录
            //计时交互
            $.get(seckill.URL.now(),{},function (result) {
                if(result&&result['success']){
                    var nowTime=result['data'];
                    //时间判断，计时交互
                    seckill.countDown(seckillId,nowTime,startTime,endTime);

                }else {
                    console.log('result'+result);
                }
            });

        }

    }

}





