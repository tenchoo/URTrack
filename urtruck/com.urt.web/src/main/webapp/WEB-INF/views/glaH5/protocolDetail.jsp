<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="<%=basePath%>" />
<title><fmt:message bundle='${pageScope.bundle}'  key='access.agreement' /></title>
<!-- 新 Bootstrap 核心 CSS 文件 -->
<!--<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">-->
<link rel="stylesheet" href="static/h5/css/bootstrap.min.css"
	type="text/css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
<!-- 可选的Bootstrap主题文件（一般不用引入） -->
<!--<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">-->
<link rel="stylesheet" href="static/h5/css/bootstrap-theme.min.css"
	type="text/css">
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<!--<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>-->
<script src="static/h5/js/jquery-1.12.4.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<!--<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>-->
<script src="static/h5/js/bootstrap.min.js"></script>

<style>
* {
	font-family: "微软雅黑", "Microsoft Yahei", Arial, Helvetica, sans-serif,
		"宋体";
}
</style>




<script src="js/user_account.js"></script>

<style>
p {
	font-size: 3.3vw
}

button {
	font-size: 3.8vw
}
</style>

</head>
<body>
	<div style="font-size: 3.3vw;overflow:hidden;">
		<div style="background-color: #ffffff; border-bottom: 1px solid #dfe4e1; margin-bottom: 2vw;overflow:hidden;" >

			<div style="padding: 1rem 0;margin: 0 1rem;" class="col-xs-1">
				<a href="javascript:history.go(-1)"> <img
					src="static/h5/images/goback.png" class="img-responsive"
					alt="Responsive image">
				</a>
			</div>
			<div style="margin: 1.45rem 0rem; font-size: 4.2vw" class="col-xs-9 ">联想懂的通信客户入网服务协议<fmt:message bundle='${pageScope.bundle}'  key='' /></div>
		</div>

		<div class="row">
			<div
				style="padding-bottom: 10rem; background-color: #eef0f0; text-align: center"
				class="col-xs-12">
				<div style="font-size: 12px; margin: 1rem auto 0 auto;"
					class="col-xs-12">

					<div style="margin-top: 1rem; margin-bottom: 2rem; text-align: left">
						<p>甲方：联想客户</p>
						<p>乙方：北京联想调频科技有限公司</p>

					</div>
					<div style="text-align: left">
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;甲方在使用乙方服务前，务必审慎阅读、充分理解并遵守《联想懂的通信客户入网服务协议》，如甲方对本协议有任何疑问的，应联系乙方客服进行咨询。当甲方阅读并勾选“我已阅读并同意《入网服务协议》”且提交订单后，或甲方以其他乙方允许的方式实际使用乙方的移动通信服务时，即表示甲方已充分阅读、理解并同意接受本协议的全部内容的约束，本协议将立即生效。甲方承诺接受并遵守本协议的约定，届时甲方不应以未阅读本协议的内容等理由，主张本协议无效，或要求撤销本协议。</p>
						<p style="font-weight: 550">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;甲方或甲方的委托代理人、法定代理人的签署表示:</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(1)甲方具有完全民事行为能力。甲方为无民事行为能力或限制民事行为能力的,已经其法定代理人同意并签名确认。</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(2)本协议签署前，甲方或其代理人已仔细阅读本协议各条款，经向乙方咨询,并无疑问。</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(3)甲方或其代理人，知悉乙方经营移动通信转售业务，完全理解并接受本协议各条款。</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(4)甲方根据乙方营业厅、网站公告及相关业务说明，已充分了解其所需的业务、须办理的手续及资费标准。

						</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;根据《中华人民共和国合同法》、《中华人民共和国电信条例》及其他有关法律、法规的规定，甲乙双方在平等、自愿、公平、诚实、信用的基础上，就联想移动通信服务有关事宜，达成协议如下：</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
						</br>
						<p>一、入网及业务办理实名制要求</p>
						</br>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.1个人客户：甲方办理入网手续时，应当出示个人有效证件（居民身份证、户口薄;中国人民解放军军人、武装警察身份证件；外国公民护照；港澳居民往来内地通行证；台湾居民往来大陆通行证）、提供真实身份信息。甲方委托他人办理入网及业务变更手续的，乙方应当要求受托人出示甲方和受托人的有效证件，并提供甲方和受托人的真实身份信息。</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.2集团客户：甲方办理入网手续时，应当出示单位有效证件（营业执照、组织机构代码证、事业单位法人证书或者社会团体法人登记证书）。单位办理登记的，除出示以上证件之一外，还应当出示经办人的有效证件和单位的授权书,以及填写集团客户委托书和集团登记表。</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.3如甲方入网登记资料发生变更或办理过户、销户业务时，应到乙方营业厅办理资料变更完善手续。因甲方或其代理人提供的资料不详、不实或变更后未及时办理资料完善手续等原因所造成的后果由甲方自行承担。</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.4甲方因终端SIM卡丢失或损坏等需要办理补换卡业务时需按乙方业务要求提供相应资料通过乙方指定渠道办理，期间产生的相关费用应由甲方承担。</p>
						</br>
						<p>二、服务内容和资费标准</p>
						</br>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.1乙方在现有技术条件下对应的基础移动通信网络（以下简称网络，包括但不限于GSM、CDMA、WCDMA、TD-SCDMA、TDD-LTE或FDD-LTE等，由基础运营商提供）覆盖范围内，为甲方提供有偿移动通信服务。甲方使用的网络制式由甲方所选号码对应的基础运营商提供的网络制式决定。</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.2乙方在与乙方签订漫游协议的基础运营商的网络覆盖范围内的国家和地区为甲方提供国际漫游服务，甲方在出访前需申请开通国际漫游业务，在漫游地将自动连接网络漫游。国际漫游资费参照对应网络的基础运营商国际漫游资费标准执行。</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.3甲方可自主选择乙方提供的各项移动通信业务。</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.4乙方根据政府主管部门批准或备案的资费标准向甲方收取相关费用，包括月基本费、通话费（含本地、长途、漫游）、短信（含彩信）费、流量费以及甲方申请的其他业务的费用。乙方通过乙方网站或营业厅等渠道公布相关资费标准和审批备案号（或文件号）。</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.5计费周期为自然月，即每月第一日至当月最后一日。由于网络设备生成费用记录及相关处理行为延时，可能发生每月部分费用计入下月费用的情况，届时乙方将在费用详单中列明。</p>
						</br>
						<p>三、乙方的义务</p>
						</br>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.1乙方通过营业厅、网站等方式向甲方公布并提示服务项目、服务时限、服务范围及资费标准等内容。</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.2乙方应采取一种或数种方式向甲方提供方便的业务办理、业务咨询和话费信息查询渠道，包括营业厅、服务热线、服务网站及其他方式等。</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							3.3乙方有义务采取公布监督电话等形式受理甲方投诉，并在接到投诉之日起15日内答复甲方。</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							3.4乙方对移动电话原始话费数据及信息服务计费原始数据保留期限为5个月(系统产生用户话单当月起后5个月，不含当月)，对客户提出异议的话单，应保存至异议解决为止。</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							3.5若甲方对乙方收取的话费存有异议，乙方有责任进行调查、解释，并告知甲方核实处理的结果。</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							3.6乙方不得侵害甲方的通信自由和通信秘密，对甲方的客户资料负有保密义务。但根据法律法规规定，乙方有义务配合有权机关对客户通信、资料、数据的调查取证等工作。</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							3.7乙方免费在中国（限大陆地区，不含香港、澳门、台湾）向甲方提供火警、匪警、医疗急救、交通事故报警等公益性电话接入服务。</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							3.8甲方欠费时，如在90天内补齐费用并申请开机的，乙方在收到费用时起24小时内为甲方恢复移动通信服务。</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 3.9乙方对甲方暂停移动通信服务
							(以下简称停机) 时，对使用“先使用，后付费”缴费方式的甲方应提前通知；对使用“先预存话费，后使用”
							缴费方式的甲方应采取短信、电话等方式进行余额提示，通知方式包括但不限于短信、电话、信函、互联网、电子邮件等。</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							3.10乙方因基础运营商检修线路、设备搬迁、工程割接、网络及软件升级等可预见的原因可能影响甲方使用的，应提前通知甲方，通知方式包括但不限于短信、电话、广播、电视、信函、公告、报纸、互联网、电子邮件等。</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							3.11甲方向乙方申告移动电话通信障碍（指交换设备或传输线路原因造成通信中断等现象，不包括网络覆盖和终端设备故障），乙方应在接到申告后及时尽速告知对应的基础运营商，基础运营商修复或调通时间按照电信条例规定执行。</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							3.12乙方应在承诺时限内为甲方开通其申请的服务(双方约定超出此时限的除外)，乙方未及时开通的，应免除甲方自申请之日至服务开通期间的该项服务的月功能费。</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 四、甲方的义务</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							4.1甲方办理入网手续时，需向乙方提供真实有效的身份证件原件，个人客户的有效证件包括：居民身份证、临时居民身份证或户口簿、中国人民解放军军人身份证件、中国人民武装警察身份证件、港澳居民来往内地通行证、台湾居民来往大陆通行证或者其他有效旅行证件、外国公民护照、法律、行政法规和国家规定的其他有效身份证件；单位客户有效证件包括：组织机构代码证、营业执照、事业单位法人证书或社会团体法人登记证书、法律、行政法规和国家规定的其他有效证件或证明文件。单位办理登记的，除出示以上证件之一外，还应当出示经办人的有效证件和单位的授权书。如甲方入网登记资料发生变更，应及时通知乙方。</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							4.2甲方应按约定的时间、方式及时、足额向乙方交纳相关业务费用（如甲方使用“先使用、后付费”的结算方式，则甲方应于当月的5日至20日及时、足额向乙方交纳上月的费用）；甲方逾期不交纳的，乙方有权要求甲方补交或暂停甲方的服务，并自付费日期满后的次月起，每日按照所欠费用的3‰收取违约金。</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							4.3甲方要求终止提供移动通信服务的，应在结清所有费用后办理退网手续。</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							4.4甲方承诺不利用移动通信网络发送违法信息或对其他客户的骚扰信息，甲方向第三方发送广告等信息，需经该第三方同意认可。</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 五、特殊情况的责任承担</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							5.1在下列情况下，乙方有权暂停或限制甲方的移动通信服务，由此给甲方造成损失的，乙方不承担责任：</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							（1）甲方银行账户被查封、冻结或余额不足等非乙方原因造成乙方结算时划扣不成功的；</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							（2）甲方预付费账户余额为0元而未及时补交款项或预付费账户余额不足以划扣下一笔预付费用的；</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							（3）甲方使用“先使用，后付费”的费用结算方式时，超过话费缴纳期限未缴纳话费的，或移动电话费用超过可透支额度的；</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; （4）甲方发送带有非法内容信息的。</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							5.2甲方发送违法类信息，或未经接收客户同意大量发送商业广告信息给他人造成骚扰的，乙方有权依据举报关闭客户信息发送功能，待甲方完善真实客户资料并签署不再滥发信息的承诺书后恢复相应功能。因此给甲方造成的损失，乙方不承担责任。</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							5.3甲方名下的移动电话号码逾期未交清费用的，乙方有权拒绝甲方以自己名义提出的其他移动通信业务申请，缴费、话费查询除外。</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							5.4甲方欠费时，乙方可采取信函、委托第三方等形式追缴欠费。</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							5.5甲方入网后即获取客户服务密码，甲方应妥善保管。服务密码是业务办理的凭证。凡使用服务密码定制、变更业务的行为均被视为甲方或甲方授权的行为。为方便办理业务，乙方也可根据甲方申请以短信、互联网等方式提供随机服务密码，该密码可作为办理业务的临时凭证。</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							5.6因甲方保管不善等原因导致其移动电话被他人非法使用，甲方应及时办理停机、补卡手续，可向公安部门报案。乙方应在技术上协助公安部门进行调查，除乙方有过错外，乙方不承担相应责任。</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							5.7根据本协议3.1条款的要求，因甲方提供的客户资料不详、不实或变更后未通知乙方等原因，使乙方无法将服务（如话费单据等）提供给甲方，乙方不承担由此对甲方所造成的不良后果。</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							5.8因不可抗力不能履行合同的，根据不可抗力的影响，部分或全部免除责任，但法律另有规定的除外。当事人延迟履行后发生不可抗力的，不能免除责任。</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							5.9乙方作为移动通信转售企业，网络通信质量取决于基础运营商的通信保障。乙方如存在与基础运营商转售协议终止的情况，甲方的通信保障和后续服务按照国家相关规定执行并做好善后工作。</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							5.10除本协议另有约定外，一方违约给对方造成损失的，应当依法承担赔偿责任。</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 六、协议的变更、转让与终止</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							6.1甲方办理各类业务所签署的表单、乙方以公告等书面形式公开做出的服务承诺均为本协议的补充协议，与本协议冲突部分以补充协议为准，补充协议中未约定部分以本协议为准。但补充协议不得规避和降低乙方的法定义务和服务标准。同时，不一致条款应向客户明示（如字体加黑、划线或变换字体等）。</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							6.2乙方承诺资费方案有效期为一年（双方特殊约定的除外）。乙方可在有效期截止后修改资费方案。如需修改，乙方应在有效期届满前两个月通知甲方。如无需修改，则原资费方案顺延一年，顺延次数不限，如甲方不接受修改，则甲方可到期取消该资费方案。</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							6.3甲方如将本协议的移动电话号码及附属权利义务转让给第三人（过户），应与该第三人到乙方营业厅与乙方协商，达成一致后，三方就权利义务转移等问题签署过户协议，本协议同时自动终止。因甲方私自转让而造成的欠费、停机等后果，由甲方自行承担。</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							6.4下列情况下乙方有权解除协议，收回号码，终止提供服务。由此给甲方造成的损失，乙方不承担责任，并有权向甲方追讨欠费：</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							（1）甲方（包括代理人）未完全履行本协议约定义务，所提供客户资料不真实、不准确、不完整或无效的；</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							（2）移动电话被用于违法犯罪活动，或甲方严重违约致使乙方遭受较大损失；</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							（3）乙方收到有权机关要求停止为甲方提供通信服务；</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; （4）甲方欠费停机超过90天。</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							6.6在法定或约定终止条件具备时，本协议终止。乙方继续保留向甲方追缴所欠费用的权利，有权收回号码。为保护客户权限，乙方对收回的号码冻结90天后，方可重新投入使用。</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 七、其他约定</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							7.1信息服务提供商通过基础运营商相关的移动网络平台提供的增值电信应用服务，甲方可自愿订购或退订。</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							7.2如甲方通过基础运营商提供的通信网络定制、收发与乙方有合作关系的信息服务提供商所提供的各类信息，甲方同意乙方根据信息服务提供商的授权，向甲方代为收取信息费。</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							7.3甲方使用信息服务提供商提供的增值电信应用服务的资费标准由信息服务提供商公布。若甲方对收取的服务费用有异议，乙方与信息服务提供商应遵循“首问负责”的原则，共同协商处理。</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							7.4本协议项下发生的争议，双方可协商解决，协商不成的，双方可选择向电信管理部门申诉，或向消费者协会投诉，或按下列第 2
							种方式解决：</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							（1）将争议提交北京联想调频科技有限公司所在地的仲裁委员会。</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							（2）向北京联想调频科技有限公司所在地的人民法院提起诉讼。</p>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							7.5甲方承诺：本人已经充分、完整阅读并理解本协议所述全部条款及条件。</p>

					</div>
				</div>





			</div>
		</div>
	</div>


	<nav class="navbar navbar-default navbar-fixed-bottom">
	<div style="text-align: center; padding:; width: 100%"
		class="container">
		<button
			style="font-size: 4vw; width: 70vw; margin: 0.8rem auto; color: #ffffff; border-radius: 10px; text-align: center; background-color: #fa9148;"
			type="button" class="btn " onclick="javascript:history.go(-1)"><fmt:message bundle='${pageScope.bundle}'  key='agree' /></button>
	</div>


	</nav>
	<div>
</body>
</html>