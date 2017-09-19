package com.urt.Ability.esim;



import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.sun.xml.wss.XWSSecurityException;
import com.urt.Ability.collect.UserQueryCardStatus;
import com.urt.Ability.esim.esimUnicom.EditNetworkAccessConfigService;
import com.urt.Ability.esim.esimUnicom.EsimGetTerminalDetailsService;
import com.urt.Ability.esim.esimUnicom.EsimTransferGlobalSimService;
import com.urt.Ability.esim.esimUnicom.GetTerminalRatingService;
import com.urt.Ability.http.util.ToolsUtil;
import com.urt.Ability.unicom.service.EditTerminalService;
import com.urt.Ability.unicom.vo.AccessConfig;
import com.urt.Ability.unicom.vo.EditNetworkAccessConfigResponse;
import com.urt.Ability.unicom.vo.GetTerminalDetailsResponse;
import com.urt.Ability.unicom.vo.TerminalDetail;
import com.urt.Ability.unicom.vo.TransferGlobalSimResponse;
import com.urt.dto.CardStatusDto;
import com.urt.dto.Goods.ServiceStatusDto;
import com.urt.dto.esim.EsimAccountInfo;
import com.urt.dto.esim.EsimDeviceInfo;
import com.urt.dto.esim.EsimFlowInfo;
import com.urt.dto.esim.EsimInfo;
import com.urt.interfaces.Goods.ServiceStatusService;
import com.urt.interfaces.esim.ESIMService;
import com.urt.mapper.EsimLenovoGoodsIdMapper;
import com.urt.mapper.LaoEsimDetailMapper;
import com.urt.mapper.LaoEsimFlowGivenDetailMapper;
import com.urt.mapper.LaoEsimFlowInfoMapper;
import com.urt.mapper.LaoEsimLenovoImeiMapper;
import com.urt.mapper.LaoEsimOperatorPropertyMapper;
import com.urt.mapper.ext.LaoEsimDetailExtMapper;
import com.urt.mapper.ext.LaoEsimFlowInfoExtMapper;
import com.urt.mapper.ext.LaoEsimGoodsPlanExtMapper;
import com.urt.mapper.ext.LaoEsimLenovoImeiExtMapper;
import com.urt.mapper.ext.LaoEsimStateExtMapper;
import com.urt.po.EsimLenovoGoodsId;
import com.urt.po.LaoEsimDetail;
import com.urt.po.LaoEsimFlowGivenDetail;
import com.urt.po.LaoEsimFlowInfo;
import com.urt.po.LaoEsimGoodsPlan;
import com.urt.po.LaoEsimLenovoImei;
import com.urt.po.LaoEsimOperatorProperty;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;


@Service("esimService")
public class ESIMServiceImpl implements ESIMService{
	
	private static final Logger log=Logger.getLogger(ESIMServiceImpl.class);
	
	@Autowired
	private LaoEsimFlowInfoExtMapper flowInfoDao;
	@Autowired
	private LaoEsimGoodsPlanExtMapper goodsPlanDao;
	@Autowired
	private LaoEsimLenovoImeiExtMapper lenovoImeiDao;
	@Autowired
	private LaoEsimLenovoImeiMapper lenovoImeiMapper;
	@Autowired
	private LaoEsimDetailMapper esimDetailMapper;
	@Autowired
	private LaoEsimDetailExtMapper esimDetailDao;
	@Autowired
	private LaoEsimFlowInfoMapper flowInfoMapper;
	@Autowired
	GetTerminalRatingService getTerminalRatingService;
	@Autowired
	private UserQueryCardStatus  userQueryCardStatusCUCC;
	@Autowired
	private ServiceStatusService  serviceStatusService;
	@Autowired
	private EditNetworkAccessConfigService editNetworkAccessConfigService;
	@Autowired
	private LaoEsimStateExtMapper esimStateDao;
	@Autowired
	private EsimLenovoGoodsIdMapper esimLenovoGoodsIdMapper;
	@Autowired
	private LaoEsimFlowGivenDetailMapper laoEsimFlowGivenDetailMapper;
	@Autowired
	private LaoEsimOperatorPropertyMapper laoEsimOperatorPropertyMapper;
	@Autowired
	private EsimTransferGlobalSimService transferGlobalSimService;
	@Autowired
	private EsimGetTerminalDetailsService getTerminalDetailsService;
	@Value("${esim.country}")
	private String country;
	
	@Override
	public EsimAccountInfo getAccountInfo(EsimAccountInfo esimFlow) {
		String lenovoId = esimFlow.getLenovoId();
		List<LaoEsimFlowInfo> flowInfoList=flowInfoDao.getAccountInfo(Long.parseLong(lenovoId));
		if(flowInfoList==null||flowInfoList.size()<=0)
			return esimFlow;
		List<EsimFlowInfo> esimFlowInfos=new ArrayList<EsimFlowInfo>();//璐︽埛鍓╀綑娴侀噺 --涓浗 瑗跨彮鐗�
		List<EsimDeviceInfo> imeiFlowInfos=new ArrayList<EsimDeviceInfo>(); //璁惧娴侀噺淇℃伅
		List<EsimInfo>  imeiInfos=new ArrayList<EsimInfo>(); //璁惧淇℃伅
		//
		for(LaoEsimFlowInfo info:flowInfoList){
			EsimFlowInfo esimFlowInfo=new EsimFlowInfo();
			String operatorState = info.getOperators();
			esimFlowInfo.setOperator(operatorState);
			esimFlowInfo.setTotalFlow(info.getTotalflow());
			esimFlowInfo.setSurplusFlow(info.getSurplusflow());
			String goodsId = info.getGoodsid();
			//濡傛灉goodsId涓嶄负绌烘煡璇骇鍝佸悕绉帮紝涓浗娴侀噺/瑗跨彮鐗欐祦閲�
			if(!StringUtils.isBlank(goodsId)){
				LaoEsimGoodsPlan goodsPlan=goodsPlanDao.getGoodsPlanByGoodsId(goodsId);
				if(goodsPlan!=null){
					esimFlowInfo.setGoodsName(goodsPlan.getGoodname());
				}
			}
			esimFlowInfos.add(esimFlowInfo);
		}
		esimFlow.setInfoFlow(esimFlowInfos);
		//姣忓彴璁惧鐨勪娇鐢ㄨ褰�
		List<Map<String,Object>> imeiFlowInfoList=lenovoImeiDao.getImeiFlowInfo(lenovoId);
		if (imeiFlowInfoList != null && imeiFlowInfoList.size() > 0) {
			for (Map<String, Object> map : imeiFlowInfoList) {
				EsimDeviceInfo deviceInfo = new EsimDeviceInfo();
				String operatorState = (String) map.get("OPERATORS");
				deviceInfo.setOperator(operatorState);
				deviceInfo.setDeviceType((String) map.get("DEVICETYPE"));
				BigDecimal useflow = (BigDecimal) map.get("USEFLOW");
				if (useflow == null) {
					deviceInfo.setUseFlow("0");
				} else {

					deviceInfo.setUseFlow(useflow.toString());
				}
				imeiFlowInfos.add(deviceInfo);
			}
		}
		esimFlow.setImeiFlowInfo(imeiFlowInfos);
		//
		List<Map<String,String>> imeiInfoList=lenovoImeiDao.getImeiInfo(lenovoId);
		if(imeiInfoList!=null&&imeiInfoList.size()>0){
			for(Map<String,String> imei:imeiInfoList){
				if(imei==null)
					break;
				EsimInfo esimInfo=new EsimInfo();
				esimInfo.setDeviceName(imei.get("DEVICENAME"));
				esimInfo.setDeviceType(imei.get("DEVICETYPE"));
				imeiInfos.add(esimInfo);
			}
		}
		esimFlow.setImeiInfo(imeiInfos);
		return esimFlow;
	}	
	
	/*
	 * 浜у搧鏌ヨ
	 * 灏嗚〃LAO_ESIM_GOODS_PLAN 涓殑鏁版嵁鏄剧ず鍑烘潵
	 */
	public List<Map<String,String>> queryGoods(){ 
		List<LaoEsimGoodsPlan> esimGoodsPlans=goodsPlanDao.selectGoodsPlans();
//		List<LaoEsimGoodsPlanDto> dtos=new ArrayList<LaoEsimGoodsPlanDto>();
		List<Map<String,String>> esimGoodsList=new ArrayList<Map<String,String>>();
		
		if(esimGoodsPlans!=null&&esimGoodsPlans.size()>0){
			for(LaoEsimGoodsPlan esimGoodsPlan:esimGoodsPlans){
//				LaoEsimGoodsPlanDto dto=(LaoEsimGoodsPlanDto)ConversionUtil.po2dto(esimGoodsPlan, LaoEsimGoodsPlanDto.class);
				Map<String,String> goodsMap=new HashMap<String, String>();
				goodsMap.put("goodInfo", esimGoodsPlan.getPlandesc());
				goodsMap.put("goodsID", esimGoodsPlan.getGoodsid());
				goodsMap.put("goodsName", esimGoodsPlan.getGoodname());
				goodsMap.put("goodsPrice", esimGoodsPlan.getGoodsprice());
				goodsMap.put("toalFlow", esimGoodsPlan.getPlansize());
				goodsMap.put("operator", esimGoodsPlan.getOperators());
				goodsMap.put("goodsUrl", esimGoodsPlan.getGoodsurl());
				esimGoodsList.add(goodsMap);
			}
		}
		return esimGoodsList;
		
	}	
	/*
	 * 鍔犲叆鍏变韩缁�
	 * 鍦ㄨ〃LAO_ESIM_DETAIL 涓彃鍏ヤ竴鏉¤褰曞氨ok浜�
	 */
	@Override
	public boolean addGroup(String lenovoId,String imei){
		String lenovoImeiId=ZkGenerateSeq.getIdSeq(SeqID.LENOVOIMEI_ID);
		//鎻掑叆鍒嗙粍琛�
		//鏌ヨ鏄惁宸茬粡鍔犲叆鍏变韩缁�
		int count=lenovoImeiDao.getCount(lenovoId,imei);
		if(count>0)
			return true;
		LaoEsimLenovoImei esimLenovoImei=new LaoEsimLenovoImei();
		esimLenovoImei.setId(Long.parseLong(lenovoImeiId));
		esimLenovoImei.setImei(imei);
		esimLenovoImei.setLenovoid(lenovoId);
		esimLenovoImei.setFlag("1");
		lenovoImeiMapper.insertSelective(esimLenovoImei);
		return true;
	}
	/*
	 * 閫�嚭鍏变韩缁�
	 * 璋冪敤鑱旈�鐨勬煡璇㈡祦閲忔帴鍙�鏉ユ洿鏂拌〃LAO_ESIM_DETAIL 鍜�LAO_ESIM_FLOWINFO
	 */
	@Override
	public boolean outGroup(String lenovoId,String imei){
		List<LaoEsimDetail> esimDetails = esimDetailDao.getEsimDetails(lenovoId,imei);
		if(esimDetails.size()==0){
			int a = lenovoImeiDao.deletesLenovoId(lenovoId, imei);
			return true;
		}
		LaoEsimDetail esimDetail = esimDetails.get(0);
		String iccid=esimDetail.getIccid();
		//鑾峰彇娴侀噺鍗″綋鍓嶅墿浣欐祦閲�
		String remainData = getRemainFlowOfIccid(iccid,esimDetail.getOperators());
		//鏇存柊LAO_ESIM_DETAIL琛ㄦ鍦ㄧ敤鐨勬祦閲忓崱璁板綍
		esimDetail.setEndsurplusflow(remainData);
		String startData = esimDetail.getStartsurplusflow();
		BigDecimal decimal1 = new BigDecimal(startData);
		BigDecimal decimal2 = new BigDecimal(remainData);
		Double number = Math.abs(decimal1.subtract(decimal2).doubleValue());
		esimDetail.setCurrentuseflow(number.toString());
		esimDetail.setCurrentisusetag("1");
		esimDetail.setEnddate(new Date());
		esimDetailMapper.updateByPrimaryKeySelective(esimDetail);
		//鏇存柊LAO_ESIM_FLOWINFO琛�
		flowInfoDao.updateSurplusData(esimDetail);
		
		/*if("1".equals(quitTag)){
			LaoEsimLenovoImei esimLenovoImei=new LaoEsimLenovoImei();
			esimLenovoImei.setImei(imei);
			esimLenovoImei.setLenovoid(lenovoId);
			esimLenovoImei.setEnddate(new Date());
		}*/
		log.info(lenovoId+"-----"+imei);
		//瑙ｇ粦鍏变韩缁�
		int i = lenovoImeiDao.deletesLenovoId(lenovoId, imei);
		log.info("***********"+i);
		/*String messageId = ZkGenerateSeq.getIdSeq(SeqID.USER_ID);
		boolean close = closeInternet(iccid,messageId, esimDetail.getOperators());
		log.info("****outGroup*apn is success******"+close);*/
		return true;
	}
	
	/*
	 * 閫�嚭鎴栨柇寮�繛鎺�
	 * 璋冪敤鑱旈�鐨勬煡璇㈡祦閲忔帴鍙�鏉ユ洿鏂拌〃LAO_ESIM_DETAIL 鍜�LAO_ESIM_FLOWINFO
	 */
	@Override
	public boolean loginOut(String lenovoId,String imei){
		log.info(lenovoId+"-----"+imei);
		List<LaoEsimDetail> esimDetails = esimDetailDao.getEsimDetails(lenovoId,imei);
		if(esimDetails.size()==0)
			return true;
		LaoEsimDetail esimDetail = esimDetails.get(0);
		String iccid=esimDetail.getIccid();
		//鑾峰彇娴侀噺鍗″綋鍓嶅墿浣欐祦閲�
		String remainData = getRemainFlowOfIccid(iccid,esimDetail.getOperators());
		//鏇存柊LAO_ESIM_DETAIL琛ㄦ鍦ㄧ敤鐨勬祦閲忓崱璁板綍
		esimDetail.setEndsurplusflow(remainData);
		String startData = esimDetail.getStartsurplusflow();
		BigDecimal decimal1 = new BigDecimal(startData);
		BigDecimal decimal2 = new BigDecimal(remainData);
		Double number = Math.abs(decimal1.subtract(decimal2).doubleValue());
		esimDetail.setCurrentuseflow(number.toString());
		esimDetail.setCurrentisusetag("1");
		esimDetail.setEnddate(new Date());
		esimDetailMapper.updateByPrimaryKeySelective(esimDetail);
		//鏇存柊LAO_ESIM_FLOWINFO琛�
		flowInfoDao.updateSurplusData(esimDetail);
		/*String messageId = ZkGenerateSeq.getIdSeq(SeqID.USER_ID);
		boolean close = closeInternet(iccid,messageId, esimDetail.getOperators());
		log.info("****loginOut*apn is success******"+close);*/
		return true;
	}
	
	/**
	 * 
	 * @param lenovoId
	 * @param imei
	 * @return
	 * 瀹炴椂娴侀噺鏌ヨ
	 */
	public boolean getReshFlow(String lenovoId,String imei){
		//鏌ヨLenovoID涓嬫墍鏈夌殑IMEI
		List<String> imeis=lenovoImeiDao.getImeisByLenovoId(lenovoId);
		if(imeis==null||imeis.size()<=0)
			return true;
		for(String item:imeis){//item灏辨槸IMEI
			 List<LaoEsimDetail> esimDetails = esimDetailDao.getEsimDetails(lenovoId,item);
			if(esimDetails.size()==0)
				continue;
			LaoEsimDetail esimDetail = esimDetails.get(0);
			String iccid=esimDetail.getIccid();
			//鑾峰彇娴侀噺鍗″綋鍓嶅墿浣欐祦閲�
			String remainData = getRemainFlowOfIccid(iccid,esimDetail.getOperators());
			//鏇存柊LAO_ESIM_DETAIL琛ㄦ鍦ㄧ敤鐨勬祦閲忓崱璁板綍
			esimDetail.setEndsurplusflow(remainData);
			String startData = esimDetail.getStartsurplusflow();
			BigDecimal decimal1 = new BigDecimal(startData);
			BigDecimal decimal2 = new BigDecimal(remainData);
			Double number = Math.abs(decimal1.subtract(decimal2).doubleValue());
			esimDetail.setCurrentuseflow(number.toString());
			esimDetail.setCurrentisusetag("1");
			esimDetail.setEnddate(new Date());
			//鏇存柊LAO_ESIM_DETAIL
			esimDetailMapper.updateByPrimaryKeySelective(esimDetail);
			//鏇存柊LAO_ESIM_FLOWINFO琛�
			flowInfoDao.updateSurplusData(esimDetail);
			
			LaoEsimDetail detail = new LaoEsimDetail();		
			String detailId = ZkGenerateSeq.getIdSeq(SeqID.ESIMDETAIL_ID);
			detail.setId(Long.valueOf(detailId));
			detail.setLenovoid(lenovoId);
			detail.setImei(item);
			detail.setIccid(iccid);
			detail.setStartdate(new Date());
			detail.setStartsurplusflow(remainData);
			detail.setOperators(esimDetail.getOperators());
			detail.setCurrentisusetag("0");
			esimDetailMapper.insertSelective(detail);
		}
		return true;
	}
	public static void main(String[] args) {
		//ESIMServiceImpl e = new ESIMServiceImpl();
		//System.out.println(e.getRemainFlowOfIccid("89860116770002311843",""));//89860116770002311843 89860116770002311835
		BigDecimal decimal1 = new BigDecimal("124.4");
		BigDecimal decimal2 = new BigDecimal("100.0");
		Double number = Math.abs(decimal2.subtract(decimal1).doubleValue());
		System.out.println(number);
	}
	/**
	 * 鑾峰彇娴侀噺鍗＄殑浣跨敤閲�
	 * @param iccid
	 * @return
	 */
	private String getRemainFlowOfIccid(String iccid,String operator) {
		String remainData="0";
		String messageId = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()).toString();
		Map<String,String>config = getConfig(operator);
		getTerminalDetailsService.setLicenseKey(config.get("licenseKey"));
		getTerminalDetailsService.setPasswd(config.get("password"));
		getTerminalDetailsService.setUrl(config.get("url"));
		getTerminalDetailsService.setUsername(config.get("userName"));
		getTerminalDetailsService.setVersion(config.get("version"));
		
		/*UnicomWsService client = new GetTerminalDetailsService();
    	try {
			client.init();
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SOAPException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (XWSSecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//    	client.setUrl("https://api.10646.cn/ws/service/terminal");
    	client.setUrl("https://api.10646.cn/ws/service/networkaccess");
    	client.setUsername("fuhp3AA");
    	client.setPasswd("abc123456");
    	client.setVersion("1.0");
    	client.setLicenseKey("5e546edf-2016-400b-89a1-e1e630048140");*/
    	List<String> iccidList = new ArrayList<String>();
    	iccidList.add(iccid);
		try {
			SOAPMessage soapMessage = getTerminalDetailsService.call(iccidList,messageId);
			GetTerminalDetailsResponse response = (GetTerminalDetailsResponse)getTerminalDetailsService.parseMessage(soapMessage);
	
			if (soapMessage != null) {
				List<TerminalDetail> list = response.getList();
				if (list != null && list.size() > 0) {
					remainData = list.get(0).getMonthToDateUsage();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return remainData;
	}
	/*
	 * 鍒囨崲apn
	 * 闇�璋冪敤鑱旈�鐨勫叏鐞僥sim鎺ュ彛鏉ュ疄鐜�
	 */
	@Override
	public boolean changeApn(String lenovoId,String imei,String operator) throws Exception{
		
		log.info("======================"+operator);
		/*if(!country.equals(operator)){
			log.info("======================"+operator);
			return false;
		}*/
		List<LaoEsimDetail> esimDetails = esimDetailDao.getEsimDetails(lenovoId,imei);
		if(esimDetails!=null&&esimDetails.size()>0){
			for(LaoEsimDetail esimDetail:esimDetails){
				esimDetail.setCurrentisusetag("1");
				esimDetailMapper.updateByPrimaryKeySelective(esimDetail);
			}
		}
		String iccid = esimStateDao.selectIccid(imei, operator);
		String globalSimTransferTyep = "STANDARD";
		if("1".equals(operator)){
			globalSimTransferTyep = "REVERT";
		}else{
			globalSimTransferTyep = "STANDARD";
		}
		
		if(StringUtils.isBlank(iccid))
			return false;
		boolean isOk = true;
		Map<String,String> ot = getConfig(operator);
		if("1".equals(ot.get("param1"))){
			 //isOk=openInternet(iccid1, messageId,operator);
		}else{
			TransferGlobalSimResponse resp = getGlobalChange(iccid,globalSimTransferTyep,operator);
			if(null != resp && !ToolsUtil.checkStringIsNull(resp.getPrimaryIccid())){
				isOk=true;
			}else{
				isOk =false;
			}
		}
		try {
			if(isOk){
				LaoEsimDetail detail=new LaoEsimDetail();
				String detailId = ZkGenerateSeq.getIdSeq(SeqID.ESIMDETAIL_ID);
				detail.setId(Long.valueOf(detailId));
				detail.setLenovoid(lenovoId);
				detail.setImei(imei);
				detail.setIccid(iccid);
				detail.setStartdate(new Date());
				String remainData = getRemainFlowOfIccid(iccid,operator);
				detail.setStartsurplusflow(remainData);
				detail.setOperators(operator);
				detail.setCurrentisusetag("0");
				esimDetailMapper.insertSelective(detail);
			}
		} catch (Exception e) {
			isOk=false;
			e.printStackTrace();
		}
		
		return  isOk;
	}	
	/*
	 * 璐拱娴侀噺鍖呬骇鍝�
	 */
	@Override
	public boolean buyGoods(String lenovoId,String imei,String goodsId) throws Exception, SOAPException{
		LaoEsimGoodsPlan goodsPlan = goodsPlanDao.getGoodsPlanByGoodsId(goodsId);
		if(goodsPlan==null)
			return false;
		
		//鎻掑叆琛‥simLenovoGoodsId
		String lenovoGoodsId = ZkGenerateSeq.getIdSeq(SeqID.LENOVOGOODS_ID);
		EsimLenovoGoodsId esimLenovoGoodsId=new EsimLenovoGoodsId();
		esimLenovoGoodsId.setId(Long.parseLong(lenovoGoodsId));
		esimLenovoGoodsId.setLenovoid(lenovoId);
		esimLenovoGoodsId.setGoodsid(goodsId);
		esimLenovoGoodsIdMapper.insertSelective(esimLenovoGoodsId);
		
		//鏇存柊琛↙aoEsimFlowInfo
		LaoEsimFlowInfo flowInfo=flowInfoDao.getFlowInfo(lenovoId,goodsPlan.getOperators());
		if(flowInfo!=null){
			String preTotolflow = flowInfo.getTotalflow();//鍒濆鎬绘祦閲�
			String preRemainFlow = flowInfo.getSurplusflow();//鍒濆鍓╀綑娴侀噺
			String goodsSize = goodsPlan.getPlansize();//鏂板娴侀噺
			
			long originTotalflow = Long.parseLong(preTotolflow);
			double remainFlow = Double.parseDouble(preRemainFlow);
			long flowSize = Long.parseLong(goodsSize);
			long newTotalFlow = originTotalflow+flowSize;//鏂版�娴侀噺
			double nowRemainFlow = remainFlow+flowSize;//鏂板墿浣欐祦閲�
			flowInfo.setTotalflow(String.valueOf(newTotalFlow));
			flowInfo.setSurplusflow(String.valueOf(nowRemainFlow));
			flowInfoMapper.updateByPrimaryKeySelective(flowInfo);
		}else{
			flowInfo=new LaoEsimFlowInfo();
			String messageId = ZkGenerateSeq.getIdSeq(SeqID.FLOWINFO_ID);
			flowInfo.setId(Long.valueOf(messageId));
			flowInfo.setLenovoid(lenovoId);
			flowInfo.setTotalflow(goodsPlan.getPlansize());
			flowInfo.setSurplusflow(goodsPlan.getPlansize());
			flowInfo.setOperators(goodsPlan.getOperators());
			flowInfoMapper.insertSelective(flowInfo);
		}
		//璐拱瀹屽晢鍝佸悗鎻掑叆鍏变韩缁勮〃LaoEsimLenovoImei
		int count = lenovoImeiDao.getCount(lenovoId, imei);
		if(count>0)
			return true;
		String lenovoImeiId=ZkGenerateSeq.getIdSeq(SeqID.LENOVOIMEI_ID);
		LaoEsimLenovoImei esimLenovoImei=new LaoEsimLenovoImei();
		esimLenovoImei.setId(Long.parseLong(lenovoImeiId));
		esimLenovoImei.setImei(imei);
		esimLenovoImei.setLenovoid(lenovoId);
		esimLenovoImei.setFlag("1");
		lenovoImeiMapper.insertSelective(esimLenovoImei);
		return true;
	}
	/**
	 * 鏌ヨ鏃х姸鎬佹柟娉�
	 */
	public String getOldStatus(String iccid, Integer serviceId){
		CardStatusDto cardStatusDto = userQueryCardStatusCUCC.queryCardStatus(iccid);
		String oldStateCode = null;
		if(cardStatusDto != null){
			oldStateCode = cardStatusDto.getCardStatus();
		}else{
			log.info("................鑱旈�璁惧鐘舵�鏌ヨ閿欒..................."+oldStateCode);
		}
		
		ServiceStatusDto queryState = null;
		if(serviceId != -1 && !StringUtils.isBlank(oldStateCode)){
			queryState= serviceStatusService.queryState(serviceId, oldStateCode);
		}
		if(queryState != null){
			oldStateCode = queryState.getStatechangeId();
		}else{
			log.info("................鑱旈�璁惧鏌ヨ灏嗚杞崲鐨勬棫鐨勫崱鐘舵� 閿欒..................."+oldStateCode);
		}
		return oldStateCode;
	}
	/**
	 * 淇敼閫氫俊璁″垝锛屼笂绾夸笂缃戠殑寮�叧锛岃繖閲岃皟鐢ㄨ仈閫�
	 * 
	 * @param nowApn
	 * @param targetApn
	 * @param iccid
	 * @return
	 */
	private boolean changeAPNType(String targetApn, String iccid, String userId,String operator) {
		String targetNacid = AccessConfig.getNacidByapnname(targetApn);
		log.info("changeApn-----targetNacid:   " + targetNacid);
		if (targetNacid == null)
			return false;
		Map<String,String>config = getConfig(operator);
		editNetworkAccessConfigService.setLicenseKey(config.get("licenseKey"));
		editNetworkAccessConfigService.setPasswd(config.get("password"));
		editNetworkAccessConfigService.setUrl(config.get("url"));
		editNetworkAccessConfigService.setUsername(config.get("userName"));
		editNetworkAccessConfigService.setVersion(config.get("version"));
		try {
			Long beginTime = System.currentTimeMillis();
			SOAPMessage soapMessage = editNetworkAccessConfigService.call(
					iccid, userId, targetNacid);
			log.info("changeAPN cost: "
					+ (System.currentTimeMillis() - beginTime) / 1000.0);
			EditNetworkAccessConfigResponse response = (EditNetworkAccessConfigResponse) editNetworkAccessConfigService
					.parseMessage(soapMessage);
			if (response != null)
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 鎵撳紑涓婄綉寮�叧
	 * 
	 * @param iccid
	 * @param userId
	 * @return
	 */
	public boolean openInternet(String iccid, String userId,String operator) {
		boolean isChangeOk = changeAPNType("apn2", iccid, userId,operator);
		return isChangeOk;
	}

	/**
	 * 鍏抽棴涓婄綉鏈嶅姟
	 * 
	 * @param iccid
	 * @param userId
	 * @return
	 */
	public boolean closeInternet(String iccid, String userId,String operator) {
		boolean isChangeOk = changeAPNType("apn1", iccid, userId,operator);
		return isChangeOk;
	}


	@SuppressWarnings("unused")
	private  Map<String,String> getConfig(String operator){
		Map<String,String> configMap = new HashMap<String,String>();
		LaoEsimOperatorProperty config = laoEsimOperatorPropertyMapper.selectByOperator(operator);
		configMap.put("licenseKey", config.getLicensekey());
		configMap.put("userName", config.getUsername());
		configMap.put("password", config.getPassword());
		configMap.put("url", config.getUrl());
		configMap.put("version", config.getVersion());
		configMap.put("param1", config.getParam1());
		return configMap;	
	}
	
	/**
	 * 
	 * @param iccid
	 * @param operator
	 * @return
	 * 鍏ㄧ悆杩愯惀鍟嗗垏鎹㈡帴鍙�
	 */
	private TransferGlobalSimResponse getGlobalChange(String iccid,String globalSimTransferTyep,String operator){
		TransferGlobalSimResponse tr=null;
		Map<String,String>config = getConfig(operator);
		transferGlobalSimService.setLicenseKey(config.get("licenseKey"));
		transferGlobalSimService.setPasswd(config.get("password"));
		transferGlobalSimService.setUrl(config.get("url"));
		transferGlobalSimService.setUsername(config.get("userName"));
		transferGlobalSimService.setVersion(config.get("version"));
		try {
			SOAPMessage soapMessage  =	transferGlobalSimService.call("lmh-test-TransferGlobalSim",iccid,"CU Guangdong","CU Test alliance",globalSimTransferTyep,"");
			 tr = (TransferGlobalSimResponse)transferGlobalSimService.parseMessage(soapMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XWSSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SOAPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return tr;
	}

	/**
	 * 
	 * @param givenLenovoId
	 * @param bGivenLenovoId
	 * @param operator
	 * @param flowSize
	 * @return
	 * 娴侀噺杞瀹炵幇
	 */
	@Override
	@Transactional
	public String givenFlow(String givenLenovoId, String bGivenLenovoId, String operator, String flowSize) {
		log.info("givenLenovoId:"+givenLenovoId+"---"+"bGivenLenovoId:"+bGivenLenovoId+"operator:"+operator+"flowSize:"+flowSize);
		
		//1銆佸垽鏂璪GivenLenovoId鏄惁瀛樺湪
		List<Map<String,String>> lenovo = lenovoImeiDao.getlenovoInfo(bGivenLenovoId);
		if(lenovo.size() == 0){
			return "-6";
		}
		//2銆佸垽鏂�givenLenovoId operator 鏄惁澶т簬flowSize
		LaoEsimFlowInfo flowInfo = flowInfoDao.getFlowInfo(givenLenovoId,operator);
		if(null != flowInfo){
			Double surp = Double.parseDouble(flowInfo.getSurplusflow());
			Double givenFlowSize = Double.parseDouble(flowSize);
			if(surp < givenFlowSize){
				return "-5";
			}else{
				String preTotolflow = flowInfo.getTotalflow();//鍒濆鎬绘祦閲�
				String preRemainFlow = flowInfo.getSurplusflow();//鍒濆鍓╀綑娴侀噺
				long originTotalflow = Long.parseLong(preTotolflow);
				double remainFlow = Double.parseDouble(preRemainFlow);
				long flowSizeGiven = Long.parseLong(flowSize);
				long newTotalFlow = originTotalflow - flowSizeGiven;//鏂版�娴侀噺
				double nowRemainFlow = remainFlow - flowSizeGiven;//鏂板墿浣欐祦閲�
				flowInfo.setTotalflow(String.valueOf(newTotalFlow));
				flowInfo.setSurplusflow(String.valueOf(nowRemainFlow));
				flowInfoMapper.updateByPrimaryKeySelective(flowInfo);
			}
		}	
		//3銆佸湪琛╨ao_esim_flow_given_detail涓彃鍏ヤ竴鏉℃槑缁�
		LaoEsimFlowGivenDetail detail = new LaoEsimFlowGivenDetail();
			String messageId = ZkGenerateSeq.getIdSeq(SeqID.GIVINFLOW_ID);
			detail.setId(Long.parseLong(messageId));
			detail.setGivenlenovoid(givenLenovoId);
			detail.setBgivenlenovoid(bGivenLenovoId);
			detail.setFlowsize(flowSize);
			detail.setCreatedate(new Date());	
			laoEsimFlowGivenDetailMapper.insertSelective(detail);	
		//4銆佸湪琛╨ao_esim_flowinfo 涓彃鍏ユ垨鑰呮洿鏂拌褰�濡傛灉琛ㄤ腑鏈夎褰曞氨鏇存柊琛�娌℃湁璁板綍灏辨彃鍏ヤ竴鏉¤褰�
		LaoEsimFlowInfo bGivenflowInfo = flowInfoDao.getFlowInfo(bGivenLenovoId,operator);
		if(null != bGivenflowInfo){
			String preTotolflow = bGivenflowInfo.getTotalflow();//鍒濆鎬绘祦閲�
			String preRemainFlow = bGivenflowInfo.getSurplusflow();//鍒濆鍓╀綑娴侀噺
			long originTotalflow = Long.parseLong(preTotolflow);
			double remainFlow = Double.parseDouble(preRemainFlow);
			long flowSizeGiven = Long.parseLong(flowSize);
			long newTotalFlow = originTotalflow+flowSizeGiven;//鏂版�娴侀噺
			double nowRemainFlow = remainFlow+flowSizeGiven;//鏂板墿浣欐祦閲�
			bGivenflowInfo.setTotalflow(String.valueOf(newTotalFlow));
			bGivenflowInfo.setSurplusflow(String.valueOf(nowRemainFlow));
			flowInfoMapper.updateByPrimaryKeySelective(bGivenflowInfo);
		}else{
			flowInfo=new LaoEsimFlowInfo();
			String messageId1 = ZkGenerateSeq.getIdSeq(SeqID.FLOWINFO_ID);
			flowInfo.setId(Long.valueOf(messageId1));
			flowInfo.setLenovoid(bGivenLenovoId);
			flowInfo.setTotalflow(flowSize);
			flowInfo.setSurplusflow(flowSize);
			flowInfo.setOperators(operator);
			flowInfoMapper.insertSelective(flowInfo);
		}
		
		return "success";
	}
}
