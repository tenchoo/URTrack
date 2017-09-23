//对Date的扩展，将 Date 转化为指定格式的String 
//月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
//年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
//例子： 
//(new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
//(new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
Date.prototype.format = function(fmt){ 
var o = { 
 "M+" : this.getMonth()+1,                 //月份 
 "d+" : this.getDate(),                    //日 
 "h+" : this.getHours(),                   //小时 
 "m+" : this.getMinutes(),                 //分 
 "s+" : this.getSeconds(),                 //秒 
 "q+" : Math.floor((this.getMonth()+3)/3), //季度 
 "S"  : this.getMilliseconds()             //毫秒 
}; 
if(/(y+)/.test(fmt))
	fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
	for(var k in o)
		if(new RegExp("("+ k +")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length))); 
return fmt; 
}

var DateFormat = {
		 
		// private property
		//_keyStr : "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",
		_isInt:function(str){
			var patrn=/^[-\+]?\d+$/;
			if (!patrn.exec(str)) return false
			return true
		},
		_dateDiff : function(d1,d2){
			return d1.getTime()-d2.getTime();
		},
		//接收毫秒用于创建时间
		tostring : function (d){
			return new Date(d).format('yyyy-M-d h:m:s');
		},
		//接收毫秒用于创建时间
		todate : function (d){
			return new Date(d).format('yyyy-MM-dd');
		},
		//时间相差的间隔
		format : function (d) {
			var dd=null;
			if(this._isInt(d)){
				dd = new Date(parseInt(d));
			}else if( typeof(d) == 'Date'){
				dd = d;
			}else if( typeof(d) == 'string'){
				var n = new Date();
				dd = new Date(Date.parse(d.replace(/-/g,"/")));
			}else{
				return d;
			}
			var n = new Date();
			var c = DateFormat._dateDiff(n,dd);
			
			c = Math.ceil(c/60000);
			if(c<2) return "刚刚";
			if(c<60){
				if(c<1){
					c=1;
				}
				return Math.ceil(c)+"分钟前";
			}
			if(c>60 && c<1440){
				return Math.ceil(c/60)+"小时前";
			}else{
				var m = dd.getMonth()+1;
				if(m<10) m = '0'+m;
				var d = dd.getDate();
				if(d<10) d = '0'+d;
				var h = dd.getHours();
				if(h<10) h = '0'+h;
				var mm = dd.getMinutes();
				if(mm<10) mm = '0'+mm;
				return m+'-'+d+' '+h+':'+mm;
			}
			return "";
		}, 
		//求两个时间的天数差 日期格式为 YYYY-MM-dd  
		daysBetween : function(DateOne,DateTwo)   
		{    
		    var OneMonth = DateOne.substring(5,DateOne.lastIndexOf ('-'));   
		    var OneDay = DateOne.substring(DateOne.length,DateOne.lastIndexOf ('-')+1);   
		    var OneYear = DateOne.substring(0,DateOne.indexOf ('-'));   
		   
		    var TwoMonth = DateTwo.substring(5,DateTwo.lastIndexOf ('-'));   
		    var TwoDay = DateTwo.substring(DateTwo.length,DateTwo.lastIndexOf ('-')+1);   
		    var TwoYear = DateTwo.substring(0,DateTwo.indexOf ('-'));   
		   
		    var cha=((Date.parse(OneMonth+'/'+OneDay+'/'+OneYear)- Date.parse(TwoMonth+'/'+TwoDay+'/'+TwoYear))/86400000);    
		    return Math.abs(cha);   
		},  
		//接收毫秒用于创建时间  格式为：yyyy-MM-dd hh:mm
		messagetime : function (d){
			var date1 = new Date();
			var newdays = new Date(date1.getTime()).format('yyyy-MM-dd')
			var msgdays = new Date(d).format('yyyy-MM-dd')
			var betwentDay = DateFormat.daysBetween(msgdays,newdays);
			if(betwentDay == 0){
				return new Date(d).format('hh:mm');
			}
			return new Date(d).format('yyyy-MM-dd hh:mm');
		},  
		//接收毫秒用于创建时间 格式为： MM-dd hh:mm
		yuefentime : function (d){
			var date1 = new Date();
			var newdays = new Date(date1.getTime()).format('yyyy-MM-dd')
			var msgdays = new Date(d).format('yyyy-MM-dd')
			var betwentDay = DateFormat.daysBetween(msgdays,newdays);
			if(betwentDay == 0){
				return new Date(d).format('hh:mm');
			}
			return new Date(d).format('MM-dd hh:mm');
		},//接收毫秒用于创建时间
		tolongdata : function (d){
			var dd = null;
			if( typeof(d) == 'string'){
				dd = new Date(Date.parse(d.replace(/-/g,"/")));
			}else{
				dd= d;
			}
			return new Date(dd).format('yyyy年MM月dd日');
		}
	 
	 
	}

