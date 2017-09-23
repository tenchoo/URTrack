/**
 * 验证专用
 */
$(function(){
	
	/**
	 * 去除所有空格:   
		str   =   str.replace(/\s+/g,"");       
		去除两头空格:   
		str   =   str.replace(/^\s+|\s+$/g,"");
		去除左空格：
		去除右空格：
		str=str.replace(/(\s*$)/g, "");
	 */
	//str=str.replace( /^\s*/, '');
	$.extend({
		replase:function(str){
			return str.replace(/\s+/g,'');
		},
		bitValidation:function(str,bit){
			return str.length==bit?true:false;
		}
	})
	$.fn.extend({
		charValidation:function(){
			var str=$.replase($.trim($(this).val()));
			return str;
		},
		styleHide:function(obj){
			 $(this).removeAttr("style");
			 obj.css({
				 "display":"none"
			 })
		},
		//身份证验证
		idcardCheck:function(){
			
		}
	
	})
	
})

