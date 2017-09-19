$(document).ready(function(){

    //登录注册切换
    loginChange();

    //二维码
    qrLogin();

    //欢迎登录弹窗
    ejectModal();

    //登录提交为空判断
    checkTrim('#btnLogin','.required','请填写登录表单信息');

    //注册信息验证
    $('.btnReg').on('click',function(){

    });

    //手机验证
    $("#phone").blur(function(){
        var regTL=/^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$|(^(1[3,8,7][0-9]|15[0|3|6|7|8|9]|18[8|9])\d{8}$)/;
        regTest(regTL,"#phone","账号输入有误 ( 账号为11位手机号 )");
    });

});
//未写完20170613
function regTest(regEx,intID,msgInfo){
    var reg=regEx;
    var intVal=$(intID).val();
    if(reg.test(intVal)){
        $('.popup span').html(msgInfo);
        $('.popup').show();//提示信息显示
    }
}

//登录注册为空判断
function checkTrim(btnId,intClass,msgInfo){
//btnId=登录按钮,intClass=登录表单,msgInfo=表单为空时的提示信息
    $(btnId).on('click',function(){
        $(intClass).each(function(){
            if($(this).val().length==0){
                $('.popup span').html(msgInfo);
                $('.popup').show();//提示信息显示
            }
        })
        //点击提示信息后返回
        $('.popup').on('click',function(){
            $('.popup').hide();//提示信息隐藏
        })
    })
}








// //登录验证
//  function complete(className){
//     var flag=true;
//     $("."+className).each(function (){
//         if ($(this).val().length==0){
//             flag=false;
//              alert('不能为空');
//         }
//     });
//     return flag;
// }

// function checkTrimAjax(){
//     $("#btnLogin").on("click",function(){
//         if (complete("required")){
//             $("#user_from").ajaxSubmit({
//                 type: 'post',
//                 url: "XXXXX" ,
//                 dataType:"json",
//                 success: function(data){
//                     if(data.status){
//                         window.location.href="XXXX";
//                     } else{
//                         alert( '账号不能为空' );
//                     }
//                 }
//             });
//         }
//     });
// }




//登录注册切换状态
function loginChange(){
    $('.loginMenu').on('click',function(){
        $('.loginMenu').removeClass('active');
        $(this).addClass('active');
        $('.tabForm').hide();
        $('.tabForm').eq($(this).index()).show();
    })
}

/**
    * 二维码切换登录
**/
function qrLogin(){
    $('#iconQrCode').on('click',function(){
        $('.loginMenu').hide();//登录注册菜单隐藏
        $('.qrCode').show();//二维码菜单显示
        $('.qrCode').addClass('active');//二维码登录菜单加当前状态
        $('.tab_main').hide();//登录注册项隐藏
        $('.qrBox').show();//二维码图片
        $('#iconQrCode').hide();//右上角二维码登录图标消失
        $('#iconComp').show();//右上角电脑登录显示
    });
    $('#iconComp').on('click',function(){
        $('#iconQrCode').show();//右上角二维码登录图标消失
        $('#iconComp').hide();//右上角电脑登录隐藏
        $('.loginMenu').show();//登录注册菜单显示
        $('.qrCode').hide();//二维码菜单隐藏
        $('.qrBox').hide();//二维码图片
        $('.tab_main').show();//登录注册项显示

    });
}
/**
 * 欢迎登录弹窗
 */
function ejectModal(){
    $('.qrImgBox').on('click',function(){
        $('.modalBox').show();
        $('.form_info').hide();
    });
    $('.closeBtn').on('click',function(){
        $('.modalBox').hide();
        $('.form_info').show();
    })
}