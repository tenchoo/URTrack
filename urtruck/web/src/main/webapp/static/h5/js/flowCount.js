/**
 * Created by Administrator on 2016/4/22.
 */

$.extend({
    flowShow:function($,data){
        $("#timestamp").html("");
        $("#dataRemaining").html(0);
        $("#expirationDate").html(0);
        $("#result").html(0);
        $("#useing").html(0);
        try{
            var dataRemaining=data.dataRemaining;
            //保留小数点后两位
            dataRemaining=Math.round(dataRemaining*100)/100;
            $("#timestamp").html(data.timestamp);
            $("#dataRemaining").html(dataRemaining);
            $("#result").html(dataRemaining);
           //正在使用的流量包
            var ratePlanName = data.ratePlanName;
            ratePlanName = ratePlanName.substring(ratePlanName.indexOf("_",ratePlanName.indexOf("_")+1)+1,ratePlanName.lastIndexOf("M"));
            $("#useing").html(ratePlanName);
            ratePlanName = ratePlanName.replace("M","");
            
           //正在使用的流量包
            if(ratePlanName%1024 == 0){
            	var str = ratePlanName/1024;
            	 $("#useing").html(str+"G");
            }
         
            //到期时间
            var expirationDate=data.expirationDate;
            if(expirationDate!=null&&expirationDate!=''){
                var dateValue=expirationDate.split("T")[0];
                $("#expirationDate").html(dateValue);
            /*  $("#expirationDate").html(date.pattern("yyyy.MM.dd"));*/
            }else{
                $("#expirationDate").html("");
            }
        }catch(error){
            alert("请求超时！！")
        }

    }
})

