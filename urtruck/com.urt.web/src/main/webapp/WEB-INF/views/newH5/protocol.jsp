<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title><fmt:message bundle='${pageScope.bundle}'  key='access.agreement' /></title>
	<base href="<%=basePath%>" />
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<link rel="stylesheet" type="text/css" href="static/newH5/css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="static/newH5/css/style.css">
	<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
	<script type="text/javascript" src="static/newH5/js/jquery.js"></script>
	<script type="text/javascript" src="static/newH5/js/bootstrap.js"></script>
	<script type="text/javascript" src="static/newH5/js/main.js"></script>
</head>
<body>
	<div class="container-fluid">
		<jsp:include page="header.jsp" flush="true"/>
		<div class="protocol">
			<h2 class="text-center">联想[网站]隐私声明</h2>
			<p>本隐私声明最近一次更新于[2016年2月25日]。</p>
			<p>关于本声明</p>
			<p>• 联想重视您的隐私。本隐私声明适用于联想拥有或运营的联想网站。本声明披露了联想网站对待信息的相关做法，包括收集和跟踪哪些类型的信息、如何使用信息、与何人分享信息及如何保护信息。</p>
			<p>• 我们保留随时更改本隐私声明的权利。若我们对本隐私声明做出变更，我们将会根据您的要求提供先前的版本，并使您了解什么时候发生的变更以及发生了哪些变更。若我们对本隐私声明做出重大变更，在变更生效之前，我们将在本网站上发布通知或通过邮件告知您（发送至您账户中所列的电子邮箱地址）。我们建议您定期访问本网页，以获取我们在隐私实践方面的最新信息。</p>
			<p>联想如何收集个人信息</p>
			<p>• 一般来说，您不必告知我们您的身份或任何个人信息即可访问联想网站。但在有些情况下，我们或我们的合作伙伴可能会需要您提供一些信息。</p>
			<p>• 在不同的情况下，您可选择向我们提供信息。例如，为使我们可以联系到您，或处理一份订单，或向您提供订阅信息，您可能会需要向我们提供比如您的姓名和地址或电子邮箱之类的信息。为从联想购买商品，您可能会需要向我们提供您的信用卡信息；如您考虑获得联想或联想下属公司有关空缺职位的信息，您可能会需要向我们提供您的教育背景和工作经历。在收集您的这些信息之前，我们将告知如何使用该等信息。若您告知我们，除满足您的要求外，不希望我们使用该信息与您进一步联络，我们将尊重您的选择。若您向我们提供了他人的个人信息，比如配偶或同事，我们将推定您已获得他们的许可。这些信息将仅用于实现您的要求或提供时告知的用途。</p>
			<p>• 若您希望订阅联想的营销资料，我们将使用您的姓名及电子邮箱地址。您可根据这些邮件中的退订说明或更改您账户中的电邮偏好来选择停止接收我们的推广邮件，或者您也可以通过privacy@lenovo.com来联系我们。</p>
			<p>联想如何使用及共享个人信息</p>
			<p>• 下列各节详细说明了联想将如何使用您的个人信息，以及我们可能与何人分享这些信息。根据您访问的联想网站的类型不同，下面的一节或多节将会适用。比如，若您从联想网站订购意向产品时，您的个人信息将依据《实现您的交易请求》及《推广应用》中所述规定加以处理。</p>
			<p>• 我们将依据本隐私声明中所述的方式与第三方分享您的个人信息。我们不会向第三方出售您的个人信息。</p>
			<p>在您请求交易时</p>
			<p>• 在您通过联想网站发起请求时，比如关于一项产品或服务、回电或特定营销材料的请求，我们将使用您提供的信息来实现您的请求。为此，我们可能会与完成这项工作所涉及的其他人共享信息，比如联想其他部门、联想的业务合作伙伴、金融机构、运输公司、邮局或政府机关（比如海关部门）。针对一项交易活动，作为客户满意度调查或开展市场研究的一部分，我们也可能联系您。</p>
			<p>推广应用</p>
			<p>• 您在某些联想网站上向联想提供的信息还可能会被联想及选定的第三方用于推广目的。但是在使用之前，您将有权选择是否允许我们以该方式使用您的信息。</p>
			<p>服务质量监督</p>
			<p>• 某些网站交易行为可能会涉及到您呼叫联想或联想呼叫您的行为。请注意，在某些情况下，我们可能记录这些通话，以进行员工培训或保证服务质量。</p>
			<p>业务合作伙伴信息</p>
			<p>• 若您是联想的业务合作伙伴，您可以访问专门面向联想业务合作伙伴的网站。我们可能会使用在该网站上提供的信息，以管理和加强我们与您本人、您所代表的业务合作伙伴及联想业务合作伙伴整体的业务关系。例如，这可能涉及到向您发送联想合作伙伴计划的详情。这些活动还可能包括与其他业务合作伙伴共享某些信息（但应履行可能存在的保密义务）或联想客户或潜在客户。针对特定的交易或计划，作为客户满意度调查或开展市场研究的一部分，我们可能会联系您。</p>
			<p>联想供应商管理</p>
			<p>• 若您是联想的供应商，您可以访问专门面向联想供应商的网站。为与您达成或执行交易，我们可能会使用在该网站上提供的信息。比如，这可能包括与参与实现过程的联想其他部门、联想业务合作伙伴、客户、运输公司、金融机构、邮局或政府机关共享信息。这些信息还可能用于管理和加强我们与您、您所代表的供应商及/或其他联想供应商的关系。</p>
			<p>法律要求的披露</p>
			<p>• 敬请知悉，在某些情况下，个人信息可能会受限于司法或其他政府部门发出的传票、令状或命令。因此，如果我们本着善意的原则认为法律有要求，我们可能会披露个人信息，以保护我们的权利、您的安全或其他人的安全，对欺诈行为进行调查，或对政府的要求做出回应。</p>
			<p>联想博客、论坛及聊天室</p>
			<p>• 若您加入了联想博客、论坛或聊天室，您应知悉您在此所提供的信息可能会被访问该博客、论坛或聊天室的任何人员获知，他们可能是联想内部或外部的人员。同时，还请您注意，个人博客、论坛及聊天室可能是由联想之外的组织主办的，他们可能会适用其他的规则和条件。每个参与人在博客、论坛或聊天室发表的意见仅为其个人意见，而不代表联想的意见。</p>
			<p>其他联想网站声明及世界范围内的做法</p>
			<p>• 在某些情况下，特定的联想网站可能包含关于信息使用及信息实践的其他声明。</p>
			<p>• 联想是一家全球性的组织，法律实体、业务流程、管理机构及技术系统均跨越了国界。我们的隐私实践旨在全世界范围内保护您的个人信息。</p>
			<p>• 我们可能会在联想内部共享您的个人信息，并将其转移到我们开展业务的世界其他国家。</p>
			<p>• 敬请注意某些国家的隐私法律可能不会等同于您所在国家的隐私法律。在这些国家，联想仍将依据我们在此所述的方式处理信息。</p>
			<p>保护个人信息的安全性及完整性</p>
			<p>• 在传输过程中及收到您的个人信息时，联想会采取适当的措施及程序加以保护并维护其质量。例如，在传输如信用卡卡号之类的敏感信息时，我们会进行加密，以保护信息的安全。若您对个人信息的安全性有任何疑问，请联系我们privacy@lenovo.com.</p>
			<p>补充信息</p>
			<p>• 对于您通过联想网站提供的信息，我们可能会不时地利用其他来源的信息加以补充，比如验证您地址的信息或关于业务的其他信息。这有助于我们维护信息的准确性，并有助于我们提供更好的服务。</p>
			<p>供应商使用信息</p>
			<p>• 在某些情况下，联想会采用供应商为联想收集、使用、分析或以其它方式处理信息。根据联想的做法，联想会要求这些供应商以与联想政策一致的方式处理信息。</p>
			<p>信息保留</p>
			<p>• 只要您的账户处于活动状态，我们就会保留您的信息，或根据需要为您提供服务。为遵守适用的法律义务、解决争议及履行协议之用，我们也将保留必要的信息。</p>
			<p>合并</p>
			<p>• 出于战略或其他业务目的，联想也许会决定购买、出售、合并或以其他方式对某些国家的业务进行重组，我们将通过电子邮件及/或在联想网站上的明显位置，将您个人信息的所有权或使用的变更情况告知您，并告知您可以做出的选择。这种交易可能会涉及到向潜在或实际购买人披露个人信息，或从卖方接收个人信息。在此类交易中，联想会采取适当的措施保护信息。</p>
			<p>联想对Cookies、网络信标及相关技术的使用</p>
			<p>• 对于大多数网站，我们均会自动收集某些信息，并存储在日志文件中。在访问我们的网站时，我们有时会收集非识别信息，以便我们提供更好的客户服务。比如，我们会跟踪人们访问的来源域名，我们还会对访问者在联想网站上的活动进行计量，但在此过程中，信息均不会识别出个人身份。这种信息有时被称作“点击流数据”。联想或代表联想的其他人士可能会使用该数据分析趋势和统计数据，以帮助我们提供更好的客户服务。</p>
			<p>• 同时，当我们在交易活动中收集您的个人信息时，我们会以非识别格式提取一些关于该交易的信息，并将其与其他非识别信息（比如点击流数据）进行合并。这种信息仅会在总体层次上进行使用和分析，以帮助我们理解趋势和模式。这种信息不会在个体层次上加以评估。若您不希望以这种方式使用您的交易信息，您可禁用您的Cookies。</p>
			<p>• 我们通过不同的技术收集上述各节中所述的信息，包括一种名为“Cookies”的技术。Cookies是网站向您的浏览器发送的数据包，它随后作为匿名标签存储在您的电脑上，可识别您的计算机而非您本人。一些联想网页会使用由联想或联想供应商发送的Cookies或其他技术（比如Java脚本和Etag），以便在您返回网站时更好地为您服务。所有浏览器均具有允许您一并关闭Cookies的设置，或在一个网站希望您保留Cookies时向您提醒的设置；敬请注意，关闭Cookies可能导致您对某些网站的访问受限。</p>
			<p>• 一些联想网站还会使用网络信标或其他技术（比如Java脚本和Etag），以更好地对网站进行定制化，提供更好的客户服务。这些技术可能会在不同联想网站的多个网页中加以使用。当访问者访问这些网页时，会产生该访问的匿名通知，由我们或我们的供应商来处理。网络信标通常会与Cookies一起运行。若您关闭Cookies，网络信标或其他技术仍会检测到对这些网页的访问，但所产生的通知不会与其他非识别Cookies信息关联起来，从而会被忽略。</p>
			<p>• 此外，我们会使用本地存储对象（Local Storage Objects，比如Flash Cookies及HTML5）存储内容信息及偏好。与我们合作的第三方，比如在我们网站上提供某些功能或基于您的浏览活动展示广告的第三方，也会使用本地存储对象（比如HTML5）收集和存储信息。不同的浏览器可能会提供各自移除HTML5本地存储对象的管理工具。</p>
			<p>在线广告</p>
			<p>• 我们会与第三方进行合作，在我们的网站上展示广告或管理我们在非联想网站上的广告。我们或我们的第三方合作伙伴可能会使用Cookies之类的技术收集关于您浏览活动的信息，以便根据您的兴趣为您提供广告。若您不希望将这些信息用于向您提供可能感兴趣的广告，您可发送邮件给我们：privacy@lenovo.com，但您仍会继续收到普通广告。</p>
			<p>个性化的URL链接</p>
			<p>• 我们有时可能会为某些访问者对网站进行个性化和定制化设计。若您访问了这些网站，您可能会发现我们基于您与联想的互动情况及您向我们提供的信息，或根据我们认为您感兴趣的产品及/或服务，进行了定制化。在您访问这些网站时，我们可能会收集关于您的访问行为的信息，以更好地对网站进行定制，使之符合您的兴趣。访问这些网站的邀请可能体现为电子邮件中个性化的URL、网站注册页面上的通知或在登录某个网站时的响应上。</p>
			<p>• 访问这些网站意味着您同意联想收集您访问的有关信息，并将其与您的其他信息及您与联想的关系联接起来。若您不希望我们以这种方式使用您的信息，请不要接收访问这些网站的邀请。</p>
			<p>儿童隐私</p>
			<p>• 联想网站仅应由成人及年龄在13岁及以上且得到父母或法定监护人允许的儿童使用。我们不会寻求收集关于年龄低于13岁的儿童的信息，且在我们发现后，将立即予以删除。我们鼓励父母积极地监督其子女使用互联网。</p>
			<p>与非联想网站的链接</p>
			<p>• 联想网站可能包含了与其他网站的链接。对于其他网站在隐私方面的做法，联想不承担任何责任。</p>
			<p>客户评价</p>
			<p>• 联想可能会在我们的网站上展示消费者的满意评价或其他支持意见。经您同意，我们可能会发布您的评价及您的姓名。若您希望更新或删除您的评价，您可联系我们privacy@lenovo.com。</p>
			<p>社交媒体功能及插件</p>
			<p>• 我们的网站包含了社交媒体功能及链接，比如带有图标并链接至联想的特定网络位置（比如联想微信）的社交工具栏。这些功能可能会收集您在我们的网站中访问的IP地址及信息，且可能会设置一项Cookies，以使该功能可正常运行。社交媒体功能及工具是由第三方或直接在我们的网站上主办的。您与这些功能的互动应受提供方隐私政策的制约。</p>
			<p>框架及“Powered-By”网站</p>
			<p>• 我们的一些网页使用了框架技术，以便在维护我们网站外观及感觉的同时提供我们合作伙伴的内容。此外，某些采用联想品牌的网站可能是由第三方运营的，并可能带有“powered-by”标志。敬请注意这些网站应遵循我们合作伙伴的隐私实践及政策，但不一定是联想或联想官网的实践及政策。</p>
			<p>联系联想隐私项目组</p>
			<p>• 若您对本声明有任何疑问，请通过privacy@lenovo.com联系我们或发函至：北京市海淀区上地创业路六号。</p>
			<p>• 若您希望获得您提供给联想的特定信息的副本，或若您知悉信息不准确并希望我们予以纠正，或若您希望要求删除您的个人信息，请联系我们：privacy@lenovo.com。</p>
			<p>• 但是，在联想向您提供信息或做出纠正之前，我们可能要求核实您的身份并提供其他信息，以帮助我们做出响应。我们将尽力在适当的期间内给予答复。</p>
			<p>• 本网站由联想或其世界范围内的关联公司提供维护。</p>
			<p>联系我们</p>
			<p>如有任何关于本声明或 Lenovo 信息处理方式的问题，可以将意见发送至：privacy@lenovo.com 或 privacy@motorola.com或 中国北京海淀区上地创业路6号 100085</p>
		</div>
	</div>
</body>
</html>