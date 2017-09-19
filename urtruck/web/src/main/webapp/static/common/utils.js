var CmpUtil = {
	// 中文
    isChinese : function(s) {
        var p = /^[\u4e00-\u9fa5]+$/;
        return this.test(s, p);
    },
    // 英文
    isEnglish : function(s) {
        var p = /^[A-Za-z]+$/;
        return this.test(s, p);
    },
    // 邮箱
    isEmail : function(s) {
        var p = "^[-!#$%&\'*+\\./0-9=?A-Z^_`a-z{|}~]+@[-!#$%&\'*+\\/0-9=?A-Z^_`a-z{|}~]+\.[-!#$%&\'*+\\./0-9=?A-Z^_`a-z{|}~]+$";
        return this.test(s, p);
    },
    // 手机号码
    isMobile : function(s) {
        return this.test(s, /^(1[3-8])\d{9}$/);
    },
    // 电话号码
    isPhone : function(s) {
        return this.test(s, /^[0-9]{3,4}\-[0-9]{7,8}$/);
    },
    // 邮编
    isPostCode : function(s) {
        return this.test(s, /^[1-9][0-9]{5}$/);
    },
    // 数字
    isNumber : function(s, d) {
        return !isNaN(s.nodeType == 1 ? s.value : s) && (!d || !this.test(s, '^-?[0-9]*\\.[0-9]*$'));
    },
    // 判断是否为空
    isEmpty : function(s) {
        return !jQuery.isEmptyObject(s);
    },
    // 判断是否为传真
    isFax : function(s) {
        return this.test(s, /^((\+?[0-9]{2,4}\-[0-9]{3,4}\-)|([0-9]{3,4}\-))?([0-9]{7,8})(\-[0-9]+)?$/);
    },
    //验证是否为图片
    isImage : function(s) {
        return this.test(s, /\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/);
    },
    //验证是否excel
    isExcel : function(s) {
        return this.test(s, /\.(xlsx|XLSX|xls|XLS)$/);
    },
    // 正则匹配
    test : function(s, p) {
        s = s.nodeType == 1 ? s.value : s;
        return new RegExp(p).test(s);
    }
};