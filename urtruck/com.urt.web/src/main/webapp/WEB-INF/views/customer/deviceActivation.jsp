<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><fmt:message bundle='${pageScope.bundle}'  key='Device.activation' /></title>
<link href="${ctx}/static/toWeb/css/common.css" rel="stylesheet" />
<link href="${ctx}/static/toWeb/css/dateRange.css" rel="stylesheet" />
<link href="${ctx}/static/ui/css/base.css" rel="stylesheet">
<link href="${ctx}/static/css/H-ui.css" rel="stylesheet">
<link href="${ctx}/static/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap-theme.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />

<script type="text/javascript" src="${ctx}/static/toWeb/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${ctx}/static/toWeb/js/jquery.cycle.all.js"></script>
<script type="text/javascript" src="${ctx}/static/toWeb/js/dateRange.js"></script>
<script type="text/javascript" src="${ctx}/static/toWeb/js/common.js"></script>
</head>
<body>

    <div class="header bg02">
        <div class="centerBox">
            <a href="javascript:;" class="logo"></a>
            <dl class="nav">
                <dt class=""><a href="${ctx}/customerQuery/loginSuccessIndex"><fmt:message bundle='${pageScope.bundle}'  key='home.page' /></a></dt>
                <dt class="navPro"><a href="javascript:;"><fmt:message bundle='${pageScope.bundle}'  key='equipment.introduction' /></a></dt>
                <dt class="navFlow current"><a href="javascript:;"><fmt:message bundle='${pageScope.bundle}'  key='phone.data.service' /></a></dt>
                <dt><a href="javascript:;"><fmt:message bundle='${pageScope.bundle}'  key='Intelligent.connection.platform' /></a></dt>
                <dt><a href="${ctx}/customerQuery/toBrandIntroduction"><fmt:message bundle='${pageScope.bundle}'  key='brand.Introduction' /></a></dt>
                <dt><a href="javascript:;"><fmt:message bundle='${pageScope.bundle}'  key='contact.us' /></a></dt>
                <dt class="account_1">
                    <span class="photo"><img src="${pageContext.request.contextPath}/static/toWeb/images/photo.png" width="42" height="42" /></span>
                    <span class="acc">${loginName }</span>
                    &nbsp;|&nbsp;
                    <a href="${ctx}/customerQuery/toExit" class="topLink"><fmt:message bundle='${pageScope.bundle}'  key='quit' /></a>
                </dt>
                <dd class="pro hide">
                    <div class="proInner">
                        <ul class="proSlide">
                            <li>
                                <a class="elem" href="javascript:;">
                                    <img src="${pageContext.request.contextPath}/static/toWeb/images/pro_1_1.png" />
                                    <span class="title"><fmt:message bundle='${pageScope.bundle}'  key='Lenovo.Ideapad' /> / PRO6S</span>
                                </a>
                                <a class="elem" href="javascript:;">
                                    <img src="${pageContext.request.contextPath}/static/toWeb/images/pro_1_2.png" />
                                    <span class="title">Miix / MX6</span>
                                </a>
                                <a class="elem" href="javascript:;">
                                    <img src="${pageContext.request.contextPath}/static/toWeb/images/pro_1_3.png" />
                                    <span class="title">YagoPad / PRO6</span>
                                </a>
                                <a class="elem" href="javascript:;">
                                    <img src="${pageContext.request.contextPath}/static/toWeb/images/pro_1_4.png" />
                                    <span class="title">ablet3 / PRO5</span>
                                </a>
                                <a class="elem" href="javascript:;">
                                    <img src="${pageContext.request.contextPath}/static/toWeb/images/pro_1_5.png" />
                                    <span class="title"><fmt:message bundle='${pageScope.bundle}'  key='Portable.MIFI' /> / MX5</span>
                                </a>
                            </li>
                            <li>
                                <a class="elem" href="javascript:;">
                                    <img src="${pageContext.request.contextPath}/static/toWeb/images/pro_1_1.png" />
                                    <span class="title"><fmt:message bundle='${pageScope.bundle}'  key='Lenovo.Ideapad' /> / PRO6S</span>
                                </a>
                                <a class="elem" href="javascript:;">
                                    <img src="${pageContext.request.contextPath}/static/toWeb/images/pro_1_2.png" />
                                    <span class="title">Miix / MX6</span>
                                </a>
                                <a class="elem" href="javascript:;">
                                    <img src="${pageContext.request.contextPath}/static/toWeb/images/pro_1_3.png" />
                                    <span class="title">YagoPad / PRO6</span>
                                </a>
                                <a class="elem" href="javascript:;">
                                    <img src="${pageContext.request.contextPath}/static/toWeb/images/pro_1_4.png" />
                                    <span class="title">ablet3 / PRO5</span>
                                </a>
                                <a class="elem" href="javascript:;">
                                    <img src="${pageContext.request.contextPath}/static/toWeb/images/pro_1_5.png" />
                                    <span class="title"><fmt:message bundle='${pageScope.bundle}'  key='Portable.MIFI' /> / MX5</span>
                                </a>
                            </li>
                        </ul>
                    </div>
                    <a href="javascript:;" class="arrowL"></a><a href="javascript:;" class="arrowR"></a>
                </dd> 
                <dd class="flow hide">
                	<a class="" href="javascript:;"><fmt:message bundle='${pageScope.bundle}'  key='Device.activation' /></a>
                	<a href="${ctx}/customerQuery/toRateSearch2"><fmt:message bundle='${pageScope.bundle}'  key='data.inquery' /></a>
                	<a href="${ctx}/customerQuery/toRecharge"><fmt:message bundle='${pageScope.bundle}'  key='recharge' /></a>
                </dd>
            </dl>
        </div>
    </div>
    
    <div class="centerBox">
        
        <div class="jihuo">
			<div class="title01"><fmt:message bundle='${pageScope.bundle}'  key='Activate.new.device' /></div>
            <dl class="jihuoForm">
            	<dt><fmt:message bundle='${pageScope.bundle}'  key='device.number' /></dt>
                <dd>
                	<input type="text" id="iccid" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='please.enter.device.number' />" />
                    <div style="color:  #e2231a; display:none;" class="iccidError"> </div>
                </dd>
            	<dt><fmt:message bundle='${pageScope.bundle}'  key='activation.code' /></dt>
                <dd>
                	<input type="text" id="privatekey" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='please.enter.activation.code' />" />
                	<div style="color:  #e2231a;display:none;" class="privatekeyError"></div>
                </dd>
            	<dt><fmt:message bundle='${pageScope.bundle}'  key='name' /></dt>
                <dd>
                	<input type="text" id="realname" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='please.enter.name' />" />
                	<div style="color:  #e2231a;display:none;" class="userameError"> </div>
                </dd>
            	<dt></dt>
                <dd>
                	<input type="text" id="idcard" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='please.enter.ID.card ' />" />
                    <div style="color:  #e2231a;display:none;" class="idcardError"> </div>
                </dd>
            	<dt><fmt:message bundle='${pageScope.bundle}'  key='Type' /></dt>
                <dd>
                	<select id="deviceType">
        	            <option style="border: none"value="0">-<fmt:message bundle='${pageScope.bundle}'  key='please.select.activation.devices' />-</option>
                        <option style="border: none"value="mifi"><fmt:message bundle='${pageScope.bundle}'  key='pc.partners' /></option>
                       <!--  <option style="border: none"value="pad">平板电脑</option> -->
                    </select>
                    <div style="color:  #e2231a;display:none;" class="deviceTypeError"> </div>
                </dd>
                <dt class="agree">
                	<a href="javascript:;" class="checkBox"><input  type="checkbox" id="checkbox" value="checked"/></a>
                    <fmt:message bundle='${pageScope.bundle}'  key='I.have.read.and.agreed.to.the.access.agreement' /><!-- <a href="javascript:;" class="link">《入网协议》</a> -->
                </dt>
                <dt class="btnBox">
                	<a href="###" id="activeBtn" class="btn">激活</a>
                </dt>
            </dl>
            
            <div class="tipsPanel">
            	<div class="tipsBox">
                	说明：<br />
                    1. 设备号及激活码位于设备后盖内，请正确填写；<br />
                    2. 上网卡一旦激活将不支持退卡操作；<br />
                    3. 如设备终端存在问题请直接到销售点进行检测处理；<br />
                    4. 根据国际通信管理局相关政策规定，同时也保障用户自身的权益，懂得通信上网卡严格按照国家规定执行入网实名制，请务必正确填写使用人真实的身份证信息；<br />
                    5. 对于上网卡无法激活或使用过程中存在的异常，可直接致电懂的通信客服热线 －4004610041
                </div>
            </div>
            
        </div>
    
    </div>

	<div class="footer">
    	<div class="logoBox">
        	<div class="footLogo"></div>
            <div class="share">
            	<a href="javascript:;" class="sina">点击关注</a>
                <span class="qrcode01">微信公众号</span>
                <!--<span class="qrcode02">APP下载</span>-->
            </div>
        </div>
        <div class="copyright">©2017 Lenovo Connect all right reserved</div>
    </div>
    
    <dl class="dialog agreeDialog" style="display:none;">
    	<dt>
        	联想懂的通信客户入网服务协议
            <a href="javascript:;" class="close"></a>
        </dt>
        <dd>
        	<p><strong>甲方</strong>：联想客户（移动电话号码- 170           ）</p>
        	<p><strong>乙方</strong>：北京联想调频科技有限公司</p>
        	<p>
            	<strong>甲方或甲方的委托代理人、法定代理人的签署表示</strong>:<br />
                ◆        甲方具有完全民事行为能力。甲方为无民事行为能力或限制民事行为能力的,已经其法定代理人同意并签名确认。<br />
                ◆        本协议签署前，甲方或其代理人已仔细阅读本协议各条款，经向乙方咨询,并无疑问。<br />
                ◆        甲方或其代理人，知悉乙方经营移动通信转售业务，完全理解并接受本协议各条款。<br />
                ◆        甲方根据乙方营业厅、网站公告及相关业务说明，已充分了解其所需的业务、须办理的手续及资费标准。
            </p>
        	<p>根据《中华人民共和国合同法》、《中华人民共和国电信条例》及其他有关法律、法规的规定，甲乙双方在平等、自愿、公平、诚实、信用的基础上，就联想移动通信服务有关事宜，达成协议如下：</p>
        	<p>
            	<strong>一、 入网及业务办理实名制要求</strong><br />
                1.1个人客户：甲方办理入网手续时，应当出示个人有效证件（居民身份证、户口薄;中国人民解放军军人、武装警察身份证件；外国公民护照；港澳居民往来内地通行证；台湾居民往来大陆通行证）、提供真实身份信息。甲方委托他人办理入网及业务变更手续的，乙方应当要求受托人出示甲方和受托人的有效证件，并提供甲方和受托人的真实身份信息。<br />
                1.2集团客户：甲方办理入网手续时，应当出示单位有效证件（营业执照、组织机构代码证、事业单位法人证书或者社会团体法人登记证书）。单位办理登记的，除出示以上证件之一外，还应当出示经办人的有效证件和单位的授权书,以及填写集团客户委托书和集团登记表。<br />
                1.3如甲方入网登记资料发生变更或办理过户、销户业务时，应到乙方营业厅办理资料变更完善手续。因甲方或其代理人提供的资料不详、不实或变更后未及时办理资料完善手续等原因所造成的后果由甲方自行承担。<br />
                1.4甲方因SIM卡丢失或损坏等原因需办理补卡手续或换卡手续时，甲方应凭有效身份证件原件到乙方营业厅办理，甲方委托他人代办需同时提供甲方及代理人真实有效身份证件原件及委托书。乙方可收取相应费用。
            </p>
        	<p>
            	<strong>二、服务内容和资费标准</strong><br />
                2.1乙方在现有技术条件下对应的基础移动通信网络（以下简称网络，包括但不限于GSM、CDMA、WCDMA、TD-SCDMA、TDD-LTE或FDD-LTE等，由基础运营商提供）覆盖范围内，为甲方提供有偿移动通信服务。甲方使用的网络制式由甲方所选号码对应的基础运营商提供的网络制式决定。<br />
                2.2乙方在与乙方签订漫游协议的基础运营商的网络覆盖范围内的国家和地区为甲方提供国际漫游服务，甲方在出访前需申请开通国际漫游业务，在漫游地将自动连接网络漫游。国际漫游资费参照对应网络的基础运营商国际漫游资费标准执行。<br />
                2.3甲方可自主选择乙方提供的各项移动通信业务。<br />
                2.4乙方根据政府主管部门批准或备案的资费标准向甲方收取相关费用，包括月基本费、通话费（含本地、长途、漫游）、短信（含彩信）费、流量费以及甲方申请的其他业务的费用。乙方通过乙方网站或营业厅等渠道公布相关资费标准和审批备案号（或文件号）。<br />
                2.5计费周期为自然月，即每月第一日至当月最后一日。由于网络设备生成费用记录及相关处理行为延时，可能发生每月部分费用计入下月费用的情况，届时乙方将在费用详单中列明。
            </p>
        	<p>
            	<strong>三、乙方的义务</strong><br />
                3.1乙方通过营业厅、网站等方式向甲方公布并提示服务项目、服务时限、服务范围及资费标准等内容。<br />
                3.2乙方应采取一种或数种方式向甲方提供方便的业务办理、业务咨询和话费信息查询渠道，包括营业厅、服务热线、服务网站及其他方式等。<br />
                3.3乙方有义务采取公布监督电话等形式受理甲方投诉，并在接到投诉之日起15日内答复甲方。<br />
                3.4乙方对移动电话原始话费数据及信息服务计费原始数据保留期限为5个月(系统产生用户话单当月起后5个月，不含当月)，对客户提出异议的话单，应保存至异议解决为止。<br />
                3.5若甲方对乙方收取的话费存有异议，乙方有责任进行调查、解释，并告知甲方核实处理的结果。<br />
                3.6乙方不得侵害甲方的通信自由和通信秘密，对甲方的客户资料负有保密义务。但根据法律法规规定，乙方有义务配合有权机关对客户通信、资料、数据的调查取证等工作。<br />
                3.7乙方免费在中国（限大陆地区，不含香港、澳门、台湾）向甲方提供火警、匪警、医疗急救、交通事故报警等公益性电话接入服务。<br />
                3.8甲方欠费时，如在90天内补齐费用并申请开机的，乙方在收到费用时起24小时内为甲方恢复移动通信服务。<br />
                3.9乙方对甲方暂停移动通信服务 (以下简称停机) 时，对使用“先使用，后付费”缴费方式的甲方应提前通知；对使用“先预存话费，后使用” 缴费方式的甲方应采取短信、电话等方式进行余额提示，通知方式包括但不限于短信、电话、信函、互联网、电子邮件等。<br />
                3.10乙方因基础运营商检修线路、设备搬迁、工程割接、网络及软件升级等可预见的原因可能影响甲方使用的，应提前通知甲方，通知方式包括但不限于短信、电话、广播、电视、信函、公告、报纸、互联网、电子邮件等。<br />
                3.11甲方向乙方申告移动电话通信障碍（指交换设备或传输线路原因造成通信中断等现象，不包括网络覆盖和终端设备故障），乙方应在接到申告后及时尽速告知对应的基础运营商，基础运营商修复或调通时间按照电信条例规定执行。<br />
                3.12乙方应在承诺时限内为甲方开通其申请的服务(双方约定超出此时限的除外)，乙方未及时开通的，应免除甲方自申请之日至服务开通期间的该项服务的月功能费。
            </p>
        	<p>
            	<strong>四、甲方的义务</strong><br />
                4.1甲方办理入网手续时，需向乙方提供真实有效的身份证件原件，个人客户的有效证件包括：居民身份证、临时居民身份证或户口簿、中国人民解放军军人身份证件、中国人民武装警察身份证件、港澳居民来往内地通行证、台湾居民来往大陆通行证或者其他有效旅行证件、外国公民护照、法律、行政法规和国家规定的其他有效身份证件；单位客户有效证件包括：组织机构代码证、营业执照、事业单位法人证书或社会团体法人登记证书、法律、行政法规和国家规定的其他有效证件或证明文件。单位办理登记的，除出示以上证件之一外，还应当出示经办人的有效证件和单位的授权书。如甲方入网登记资料发生变更，应及时通知乙方。<br />
                4.2甲方应按约定的时间、方式及时、足额向乙方交纳相关业务费用（如甲方使用“先使用、后付费”的结算方式，则甲方应于当月的5日至20日及时、足额向乙方交纳上月的费用）；甲方逾期不交纳的，乙方有权要求甲方补交或暂停甲方的服务，并自付费日期满后的次月起，每日按照所欠费用的3‰收取违约金。<br />
                4.3甲方要求终止提供移动通信服务的，应在结清所有费用后办理退网手续。<br />
                4.4甲方承诺不利用移动通信网络发送违法信息或对其他客户的骚扰信息，甲方向第三方发送广告等信息，需经该第三方同意认可。<br />
                4.5 笔记本设备内配送之SIM卡，用户不得私自取出挪作他用，否则赠送的流量全部作废，并将回收号码。
            </p>
        	<p>
            	<strong>五、特殊情况的责任承担</strong><br />
                5.1在下列情况下，乙方有权暂停或限制甲方的移动通信服务，由此给甲方造成损失的，乙方不承担责任：<br />
                （1）甲方银行账户被查封、冻结或余额不足等非乙方原因造成乙方结算时划扣不成功的；<br />
                （2）甲方预付费账户余额为0元而未及时补交款项或预付费账户余额不足以划扣下一笔预付费用的；<br />
                （3）甲方使用“先使用，后付费”的费用结算方式时，超过话费缴纳期限未缴纳话费的，或移动电话费用超过可透支额度的；<br />
                （4）甲方发送带有非法内容信息的。<br />
                5.2甲方如发送违法类信息，或未经接收客户同意大量发送商业广告等信息给他人造成骚扰或引起客户举报的，乙方有权依据举报关闭客户信息发送功能，待甲方完善真实客户资料并签署不再滥发信息的承诺书后恢复相应功能。因此给甲方造成的损失，乙方不承担责任。<br />
                5.3甲方名下的移动电话号码逾期未交清费用的，乙方有权拒绝甲方以自己名义提出的其他移动通信业务申请，缴费、话费查询除外。<br />
                5.4甲方欠费时，乙方可采取信函、委托第三方等形式追缴欠费。<br />
                5.5甲方入网后即获取客户服务密码，甲方应妥善保管。服务密码是业务办理的凭证。凡使用服务密码定制、变更业务的行为均被视为甲方或甲方授权的行为。为方便办理业务，乙方也可根据甲方申请以短信、互联网等方式提供随机服务密码，该密码可作为办理业务的临时凭证。<br />
                5.6因甲方保管不善等原因导致其移动电话被他人非法使用，甲方应及时办理停机、补卡手续，可向公安部门报案。乙方应在技术上协助公安部门进行调查，除乙方有过错外，乙方不承担相应责任。<br />
                5.7根据本协议3.1条款的要求，因甲方提供的客户资料不详、不实或变更后未通知乙方等原因，使乙方无法将服务（如话费单据等）提供给甲方，乙方不承担由此对甲方所造成的不良后果。<br />
                5.8因不可抗力不能履行合同的，根据不可抗力的影响，部分或全部免除责任，但法律另有规定的除外。当事人延迟履行后发生不可抗力的，不能免除责任。<br />
                5.9乙方作为移动通信转售企业，网络通信质量取决于基础运营商的通信保障。乙方如存在与基础运营商转售协议终止的情况，甲方的通信保障和后续服务按照国家相关规定执行并做好善后工作。<br />
                5.10除本协议另有约定外，一方违约给对方造成损失的，应当依法承担赔偿责任。
            </p>
        	<p>
            	<strong>六、协议的变更、转让与终止</strong><br />
                6.1甲方办理各类业务所签署的表单、乙方以公告等书面形式公开做出的服务承诺均为本协议的补充协议，与本协议冲突部分以补充协议为准，补充协议中未约定部分以本协议为准。但补充协议不得规避和降低乙方的法定义务和服务标准。同时，不一致条款应向客户明示（如字体加黑、划线或变换字体等）。<br />
                6.2乙方承诺资费方案有效期为一年（双方特殊约定的除外）。乙方可在有效期截止后修改资费方案。如需修改，乙方应在有效期届满前两个月通知甲方。如无需修改，则原资费方案顺延一年，顺延次数不限，如甲方不接受修改，则甲方可到期取消该资费方案。<br />
                6.3甲方如将本协议的移动电话号码及附属权利义务转让给第三人（过户），应与该第三人到乙方营业厅与乙方协商，达成一致后，三方就权利义务转移等问题签署过户协议，本协议同时自动终止。因甲方私自转让而造成的欠费、停机等后果，由甲方自行承担。<br />
                6.4下列情况下乙方有权解除协议，收回号码，终止提供服务。由此给甲方造成的损失，乙方不承担责任，并有权向甲方追讨欠费：<br />
                （1）甲方（包括代理人）未完全履行本协议约定义务，所提供客户资料不真实、不准确、不完整或无效的；<br />
                （2）移动电话被用于违法犯罪活动，或甲方严重违约致使乙方遭受较大损失；<br />
                （3）乙方收到有权机关要求停止为甲方提供通信服务；<br />
                （4）甲方欠费停机超过90天。<br />
                6.6在法定或约定终止条件具备时，本协议终止。乙方继续保留向甲方追缴所欠费用的权利，有权收回号码。为保护客户权限，乙方对收回的号码冻结90天后，方可重新投入使用。
            </p>
        	<p>
            	<strong>七、其他约定</strong><br />
                7.1信息服务提供商通过基础运营商相关的移动网络平台提供的增值电信应用服务，甲方可自愿订购或退订。<br />
                7.2如甲方通过基础运营商提供的通信网络定制、收发与乙方有合作关系的信息服务提供商所提供的各类信息，甲方同意乙方根据信息服务提供商的授权，向甲方代为收取信息费。<br />
                7.3甲方使用信息服务提供商提供的增值电信应用服务的资费标准由信息服务提供商公布。若甲方对收取的服务费用有异议，乙方与信息服务提供商应遵循“首问负责”的原则，共同协商处理。<br />
                7.4本协议项下发生的争议，双方可协商解决，协商不成的，双方可选择向电信管理部门申诉，或向消费者协会投诉，或按下列第2种方式解决：<br />
                （1）将争议提交北京联想调频科技有限公司所在地的仲裁委员会。<br />
                （2）向北京联想调频科技有限公司所在地的人民法院提起诉讼。<br />
                7.5本协议壹式贰份，双方各执壹份，签字或盖章后生效，具有同等法律效力。本协议未尽事宜，双方协商解决。
            </p>
        </dd>
    </dl>

	<script type="text/javascript">
    $(function(){    

		$('.agree .link').click(function(){
			$('.dialog').show();
		});
		$('.dialog .close').click(function(){
			$(this).parents('.dialog').hide();
		});

    });
    </script> 
</body>
<script type="text/javascript">
	$(function(){
        var iccidError=$(".iccidError");
        var privateKeyError=$(".privatekeyError");
        var usernameError=$(".userameError");
        var idcardError=$(".idcardError");
        var deviceTypeErrorInput=$(".deviceTypeError");
        var reg=/[\u4e00-\u9fa5]/g;
        
        $("#activeBtn").click(function(){
        	var ss=$("#checkbox").attr("checked");
	        if(!ss){
	            alert("请同意入网协议");
	            return false;
	        }
        	//iccid验证
	        var iccid=$("#iccid").val();
	        var mache=/^[0-9]{19}$/;
	        if(!mache.test(iccid)){
	            iccidError.css({
	                "display":"inline"
	            })
	            iccidError.html("请输入正确的设备号");
	            return false;
	        }
	        //卡密验证
            var privatekey=$("#privatekey").val();
            var mache=/^[0-9a-zA-Z]{5,}[0-9a-zA-Z]+$/;
            if(!mache.test(privatekey)){
                privateKeyError.css({
                    "display":"inline"
                })
                privateKeyError.html("请输入正确的卡密");
                return false;
            }
            //姓名验证
            var username=$("#realname").val();
            if(!(/^[\u4e00-\u9fa5]{2,5}$/).test(username)){
                usernameError.html("姓名必须是汉字且在2-5之间");
                usernameError.css({
                    "display":"inline"
                })
                return false;
            }
            //身份证验证
            var idcardValue=$("#idcard").val();
            var idcardmach = /^[0-9]{17}([0-9]|X)$/;
            if (idcardValue==''||!idcardmach.test(idcardValue)){
                idcardError.html("请输入正确身份证号码");
                idcardError.css({
                    "display":"inline"
                })
                return false;
            }
            //设备类型验证
            var deviceType=$("#deviceType").val();
            if(deviceType=='0'){
                deviceTypeErrorInput.html("请选择激活设备类型");
                deviceTypeErrorInput.css({
                    "display":"inline",
                })
                return false;
            }
            //按钮变成灰色 不可点击
//          $("#activeBtn").disabled=true; 
            $(this).css({
        	   'pointer-events': 'none'
            })
            $(this).css({
               'background-color':'#8D8D8D'
            })
            $.ajax({
                type:"post",
                url:"/customerQuery/doDeviceActivation",
                data:{
                	'iccid':$("#iccid").val(),
                	'privatekey':$("#privatekey").val(),
                	'realname':$("#realname").val(),
                	'idnum':$("#idcard").val(),
                	'deviceType':$("#deviceType").val()
                	},
                cache: false,
                success:function(data){
                	$("#activeBtn").css({
                 	   'pointer-events': 'auto',
		               'background-color':'#d4251f'
                     })
                	alert(data.msg);
                }
            })
        })

        
        $("#iccid").focus(function(){
        	iccidError.show();
        	iccidError.hide();
        });
        $("#privatekey").focus(function(){
        	privateKeyError.show();
        	privateKeyError.hide();
        });
        $("#userame").focus(function(){
        	usernameError.show();
        	usernameError.hide();
        });
        $("#idcard").focus(function(){
        	idcardError.show();
        	idcardError.hide();
        }); 
        $("#deviceType").focus(function(){
        	deviceTypeErrorInput.show();
        	deviceTypeErrorInput.hide();
        }); 
        /* //input焦点离开事件
        iccidInput.blur(function(){
        	errorFlag=false;
        	var mache=/^[0-9]{19}$/;
            if(!mache.test($(this).charValidation())){
            iccidError.css({
            "display":"inline"
            })
            iccidError.html("请输入正确的iccid");
            }
        })
        privatekeyInput.blur(function(){
            errorFlag=false;
            var mache=/^[0-9a-zA-Z]{5,}[0-9a-zA-Z]+$/;
            if(!mache.test($(this).charValidation())){
            privateKeyError.css({
            "display":"inline"
            })
            privateKeyError.html("请输入正确的卡密");
            }
        })
        usernameInput.blur(function(){
            errorFlag=false;
            var mache=/^[\u4e00-\u9fa5]{2,5}$/;
            if(!mache.test($(this).charValidation())){
            usernameError.css({
            "display":"inline"
            })
            usernameError.html("姓名必须是汉字且在2-5之间");
            }
        })
        idcardInput.blur(function(){
            errorFlag=false;
            if(!idCardNoUtil.checkIdCardNo($(this).charValidation())){
            idcardError.html("请检查身份证号码");
            idcardError.css({
            "display":"inline"
            })
            }
        })
        deviceTypeInput.change(function(){
            errorFlag=false;
            deviceTypeErrorInput.show();
            deviceTypeErrorInput.hide();
        }) */
	})
</script>
</html>