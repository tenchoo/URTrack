package com.urt.cmpp.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.cmpp.domain.MsgActiveTestResp;
import com.urt.cmpp.domain.MsgCommand;
import com.urt.cmpp.domain.MsgConnectResp;
import com.urt.cmpp.domain.MsgDeliver;
import com.urt.cmpp.domain.MsgDeliverResp;
import com.urt.cmpp.domain.MsgHead;
import com.urt.cmpp.domain.MsgSubmitResp;
import com.urt.interfaces.device.RedisService;

/**
 * 启动一个线程去接收和发送数据，如果队列处理完毕就关闭线程
 */
@Service("cmppSender")
public class CmppSender  {
	
	private static Logger logger=Logger.getLogger(CmppSender.class);
	private List<byte[]> sendData=new ArrayList<byte[]>();//需要发出的二进制数据队列
	private List<byte[]> getData=new ArrayList<byte[]>();//需要接受的二进制队列
	private List<byte[]> getDatas=new ArrayList<byte[]>();//需要发出的二进制数据队列
	@Autowired
	RedisService redisServiceImpl;
	private DataOutputStream out;
	private DataInputStream in;
	public CmppSender(DataOutputStream out,DataInputStream in,List<byte[]> sendData) {
		super();
		this.sendData=sendData;
		this.out=out;
		this.in=in;
	}
	public void start()throws Exception {
		if(out!=null&&null!=sendData){
			for(byte[] data:sendData){
			    System.out.println("data [] :"+Arrays.toString(data));
				sendMsg(data);
				byte[] returnData=getInData();
				if(null!=returnData){
					getData.add(returnData);
				}
			}
		}
		//[0, 0, 0, 8, 5, -11, -32, -1]
		if(in!=null&&null!=getData){
		   
			for(byte[] data:getData){
				if(data.length>=8){
					MsgHead head=new MsgHead(data);
					switch(head.getCommandId()){
						case MsgCommand.CMPP_CONNECT_RESP:
							MsgConnectResp connectResp=new MsgConnectResp(data);
							logger.info("懂得通信于"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"链接短信网关,状态:"+connectResp.getStatusStr()+" 序列号："+connectResp.getSequenceId());
							break;
						case MsgCommand.CMPP_ACTIVE_TEST_RESP:
							MsgActiveTestResp activeResp=new MsgActiveTestResp(data);
							logger.info("懂的通信于"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"短信网关与短信网关进行连接检查"+" 序列号："+activeResp.getSequenceId());
							break;
						case MsgCommand.CMPP_SUBMIT_RESP:
							MsgSubmitResp submitResp=new MsgSubmitResp(data);
							logger.info("懂的通信于"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"向用户下发短信，状态码:"+submitResp.getResult()+" 序列号："+submitResp.getSequenceId());
							break;
						case MsgCommand.CMPP_TERMINATE_RESP:
							logger.info("懂的通信于"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"拆除与ISMG的链接"+" 序列号："+head.getSequenceId());
							break;
						case MsgCommand.CMPP_CANCEL_RESP:
							logger.info("CMPP_CANCEL_RESP 序列号："+head.getSequenceId());
							break;
						case MsgCommand.CMPP_CANCEL:
							logger.info("CMPP_CANCEL 序列号："+head.getSequenceId());
							break;
						case MsgCommand.CMPP_DELIVER:
						    MsgDeliver msgDeliver = new MsgDeliver(data);
							MsgDeliverResp msgDeliverResp=new MsgDeliverResp();
							msgDeliverResp.setTotalLength(12+8+4);
							msgDeliverResp.setCommandId(MsgCommand.CMPP_DELIVER_RESP);
							msgDeliverResp.setSequenceId(msgDeliverResp.getSequenceId());
							msgDeliverResp.setMsg_Id(msgDeliver.getMsg_Id());
							msgDeliverResp.setResult(msgDeliver.getResult());
							sendMsg(msgDeliverResp.toByteArry());//进行回复
							
							
							if(msgDeliver.getResult()==0){
								logger.info("CMPP_DELIVER 序列号："+head.getSequenceId()+"，是否消息回复"+(msgDeliver.getRegistered_Delivery()==0?"不是,消息内容："+msgDeliver.getMsg_Content():"是，源手机号："+msgDeliver.getSrc_terminal_Id()));
								//接收短信
								if(msgDeliver.getRegistered_Delivery() == 0){
								  //正常短信
		                            if(5 !=data[85] && data[86] != 0 && 3 != data[87]){
		                                
		                                MsgDeliver msgDeliver1 = new MsgDeliver(data);
		                                CmppDeliver deliver = new CmppDeliver();
	                                    deliver.deliverMsg(msgDeliver1,null,null);
		                            }else if(5==data[85] && data[86] == 0 && 3 == data[87]){
//		                                byte[] data2 =new byte[13];//截取源手机号
//		                                System.arraycopy(data, 50, data2, 0, 13);
//		                                String telNum = new String(data2);
		                                byte[] data3 = new byte[134];
		                                Byte b = data[84];
		                                int lengEle =  getLength(b.toString());
		                                System.arraycopy(data, 91, data3, 0, lengEle-6);
	                                    CmppDeliver deliver = new CmppDeliver();
	                                    msgDeliver.setMsg_Content(new String(data3,"UTF-16BE"));
	                                    //89 总数 90 当前数量
	                                    Byte total = data[89];
	                                    Byte current = data[90];
                                        deliver.deliverMsg(msgDeliver,total.toString(),current.toString());
								}
							}else{
								logger.info("CMPP_DELIVER 序列号："+head.getSequenceId());
							}
							}
							break;
						case MsgCommand.CMPP_DELIVER_RESP:
							logger.info("CMPP_DELIVER_RESP 序列号："+head.getSequenceId());
							break;
						case MsgCommand.CMPP_QUERY:
							logger.info("CMPP_QUERY 序列号："+head.getSequenceId());
							break;
						case MsgCommand.CMPP_QUERY_RESP:
							logger.info("CMPP_QUERY_RESP 序列号："+head.getSequenceId());
							break;
						case MsgCommand.CMPP_TERMINATE:
							logger.info("CMPP_TERMINATE 序列号："+head.getSequenceId());
							break;
						case MsgCommand.CMPP_CONNECT:
							logger.info("CMPP_CONNECT 序列号："+head.getSequenceId());
							break;
						case MsgCommand.CMPP_ACTIVE_TEST:
							logger.info("CMPP_ACTIVE_TEST 序列号："+head.getSequenceId());
							break;
						case MsgCommand.CMPP_SUBMIT:
							logger.info("CMPP_SUBMIT 序列号："+head.getSequenceId());
							break;
						default:
							logger.error("无法解析IMSP返回的包结构：包长度为"+head.getTotalLength());
							break;
					}
				}
			}
		}
	}
	public List<byte[]> getGetData() {
		return getData;
	}
	/**
	   * 在本连结上发送已打包后的消息的字节
	   * @param data:要发送消息的字节
	   */
	private  boolean sendMsg(byte[] data)throws Exception {
	   try{
		  out.write(data);
		  out.flush();
		  return true;
	   }catch(NullPointerException ef){
		  logger.error("在本连结上发送已打包后的消息的字节:无字节输入");
	   }
	   return false;
	}
	private  byte[] getInData() throws IOException{
		 try{
			 int len=in.readInt();
			 if(null!=in&&0!=len){
			   byte[] data=new byte[len-4];
		   	   in.read(data);
		   	   return data;
			 }else{
				 return null;
			 }
		 }catch(NullPointerException ef){
			logger.error("在本连结上接受字节消息:无流输入");			
			return null;
		 }catch(EOFException eof){
			 logger.error("在本连结上接受字节消息:"+eof.getMessage());			
			 return null;
		 }
	}
	public static int getLength(String str){
        int res = Integer.valueOf(str);
        if (str.charAt(0) == '-') {
             res = 127+(129-Math.abs(Integer.valueOf(str)));
        }
        return res;
    }
	
}
