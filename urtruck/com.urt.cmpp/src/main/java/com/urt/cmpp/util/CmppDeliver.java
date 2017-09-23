package com.urt.cmpp.util;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.urt.cmpp.domain.MsgDeliver;
import com.urt.dto.LaoSmsDeliverDto;
import com.urt.dto.Goods.LaoUserDto;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.cmpp.LaoCmppService;
import com.urt.utils.SeqID;
import com.urt.utils.SpringContextUtils;
import com.urt.utils.ZkGenerateSeq;

public class CmppDeliver {
	@Autowired
	LaoCmppService laoCmppService;
	@Autowired
	UserService userService;
	
	CmppDeliver deliver;
	public CmppDeliver() {
		super();
		if(deliver == null){
			this.laoCmppService=SpringContextUtils.getBean("laoCmppService");
			this.userService=SpringContextUtils.getBean("userService");
		}
	}

	public void deliverMsg(MsgDeliver msgDeliver,String total,String currentNum){
		//入库逻辑————————————————————————————————TODO
		String destId = msgDeliver.getDest_Id();
		String srcTerminalId = msgDeliver.getSrc_terminal_Id();
		String msgContent = msgDeliver.getMsg_Content();
		System.out.println("**msgContent*********"+msgContent);
		Long msgId = msgDeliver.getMsg_Id();
		
		/*Date recvDate = MsgUtils.formatMsgId(msgId);
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String pushDate = fmt.format(recvDate);
		System.out.println(pushDate);*/
		boolean flag = false;
		String falgU ="update";
		srcTerminalId = srcTerminalId.substring(0, 13);
		destId = destId.substring(0, 13);
		System.out.println(srcTerminalId);
		LaoUserDto user = userService.getLaoUserDtoByMsisdn(srcTerminalId.substring(0, 13));
		System.out.println(user);
		String custId = "";
		if(user.getChannelCustId() != null){
			custId = user.getChannelCustId().toString();
		}
		String res = null;
		Date date = new Date();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String pushDate = fmt.format(date);
        
		if (null == total && null == currentNum) {
		    Long smsId = laoCmppService.saveMsg(msgDeliver.getMsg_Content(), user.getIccid(), custId, null);
		    flag = true;
		    falgU = "insert";
		     res = laoCmppService.pushSmstoPlatform(destId, srcTerminalId, smsId.toString(), msgContent, pushDate);
		     insert(smsId.toString(),res,msgDeliver);
        }else{
            if (total.equals(currentNum) && null != total && null != currentNum) {
                System.out.println("最终内容插入 "+currentNum);
                Map<String,String> content =  getContent(srcTerminalId, msgContent);
                System.out.println("最终内容为:"+content);
                res = laoCmppService.pushSmstoPlatform(destId, srcTerminalId, content.get("smsId"), content.get("content"), pushDate);
                JSONObject jo1 = JSONObject.fromObject(res);
                System.out.println("**转换JSON**"+jo1);
                JSONObject jay = jo1.getJSONObject("resultInfo");
                String respCode = jay.getString("respCode");
                String respDesc = jay.getString("respDesc");
                LaoSmsDeliverDto deliverDto = laoCmppService.selectBySrcNumber(Long.valueOf(srcTerminalId));
                deliverDto.setSmsContent(content.get("content"));
                deliverDto.setPushDate(new Date());
                deliverDto.setPushDesc(respDesc);
                if("0000".equals(respCode)){
                	deliverDto.setPushState(1L);
                }else{
                	deliverDto.setPushState(0L);
                }
                laoCmppService.updateSmsDeliver(deliverDto);
                //getContent(srcTerminalId, msgContent);
            }else{
                if ("1".equals(currentNum)) {
                    Long smsId = laoCmppService.saveMsg("", user.getIccid(), custId, null);
                    System.out.println("进入第一条插入 "+currentNum);
                    flag= false;
                    LaoSmsDeliverDto laoSmsDeliver = new LaoSmsDeliverDto();
                    laoSmsDeliver.setDestNumber(destId);
                    laoSmsDeliver.setPushDate(date);
                    laoSmsDeliver.setPushState(2L);
                    laoSmsDeliver.setSrcNumber(srcTerminalId);
                    laoSmsDeliver.setSmsContent(msgContent);
                    laoSmsDeliver.setSmsId(smsId);
                    String deliverId = ZkGenerateSeq.get8IdSeq(SeqID.DELIVER_ID);
                    laoSmsDeliver.setDeliverId(Long.valueOf(deliverId));
                    laoCmppService.saveDeliver(laoSmsDeliver);
                }else{
                    System.out.println("update content ");
                    //更新内容
                    getContent(srcTerminalId, msgContent);
                   
                }
            }
        }
	}
		//System.out.println("**推送平台返回**"+res);
		public void insert(String smsId,String res,MsgDeliver msgDeliver){
		    LaoSmsDeliverDto laoSmsDeliver = new LaoSmsDeliverDto();
	        laoSmsDeliver.setDestNumber(msgDeliver.getDest_Id());
	        laoSmsDeliver.setPushDate(new Date());
		    JSONObject jo1 = JSONObject.fromObject(res);
	        System.out.println("**转换JSON**"+jo1);
	        JSONObject jay = jo1.getJSONObject("resultInfo");
	        String respCode = jay.getString("respCode");
	        String respDesc = jay.getString("respDesc");
	        System.out.println(respCode);
	        System.out.println(respDesc);
	        Long pushState = 0L;
	        if(respCode.equals("0000")){
	            pushState = 1L;
	            laoCmppService.updateMsgSuccess(Long.valueOf(smsId));
	        }
	        laoSmsDeliver.setPushDesc(respDesc);
	        laoSmsDeliver.setPushState(pushState);
	        laoSmsDeliver.setSrcNumber(msgDeliver.getSrc_terminal_Id());
	        laoSmsDeliver.setSmsContent(msgDeliver.getMsg_Content());
	        laoSmsDeliver.setSmsId(Long.valueOf(smsId));
	        String deliverId = ZkGenerateSeq.get8IdSeq(SeqID.DELIVER_ID);
	        laoSmsDeliver.setDeliverId(Long.valueOf(deliverId));
	        laoCmppService.saveDeliver(laoSmsDeliver);
		}
	
	public  Map<String,String> getContent(String srcTerminalId,String msgContent){
	    LaoSmsDeliverDto deliverDto = laoCmppService.selectBySrcNumber(Long.valueOf(srcTerminalId));
        String cont = deliverDto.getSmsContent();//拿到内容
        String finalCont = cont+msgContent;
        LaoSmsDeliverDto laoSmsDeliver = new LaoSmsDeliverDto();
        laoSmsDeliver.setSmsContent(finalCont);
        laoSmsDeliver.setPushDate(new Date());
        laoCmppService.updateSmsDeliver(laoSmsDeliver);
        Map<String,String> map = new HashMap<String,String>();
        map.put("content", finalCont);
        map.put("smsId", String.valueOf(deliverDto.getSmsId()));
        return map;
	}
}
