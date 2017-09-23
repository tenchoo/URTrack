(function() {
    var lis = document.getElementById('aside').getElementsByTagName('li');
    var signal = document.getElementById('jsSignal');
    var layer = document.getElementById('jslayer');
    var findlist1 = document.getElementById('findlist1');
    var findlist2 = document.getElementById('findlist2');
    var select_table = document.getElementById('select_table');
    var url = window.location.href;
    var loc = url.substr(url.indexOf('?') + 1, 7);
    var loc1 = url.substr(url.indexOf('&') + 1);
    var state = 0;
    //左侧菜单
    switch (loc) {
        case "c=list1":
            lis[0].setAttribute('class', 'current');
            findlist1.style.display = 'table-row';
            break;
        case "c=list2":
            lis[1].setAttribute('class', 'current');
            findlist2.style.display = 'table-row';
            findlist1.style.display = 'none';
            break;
        case "c=list3":
            lis[2].setAttribute('class', 'current');
            break;
    }

    switch (loc1) {
        case "payType=1":
            select_table.innerHTML = '支付宝';
            break;
        case "payType=2":
            select_table.innerHTML = '银行卡';
            break;
        case "payType=3":
            select_table.innerHTML = '微信支付';
            break;

    }
    //选择充值方式
    signal.onclick = function() {
        if (state == 0) {
            this.className = 'arrowdown_table';
            layer.style.display = 'block';
            state = 1;
        } else {
            this.className = 'arrowup_table';
            layer.style.display = 'none';
            state = 0;
        }

    }

})()