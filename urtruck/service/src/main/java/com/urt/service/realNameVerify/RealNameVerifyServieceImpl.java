package com.urt.service.realNameVerify;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.Ability.unicom.util.Base64;
import com.urt.Ability.unicom.util.HttpClientUtil;
import com.urt.Ability.unicom.util.ThreedesUtils;
import com.urt.common.util.ConversionUtil;
import com.urt.common.util.ResultMsg;
import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.LaoSsAccountRoleDto;
import com.urt.dto.LaoSsRealNameVerifyDto;
import com.urt.interfaces.realNameVerify.RealNameVerifyServiece;
import com.urt.mapper.LaoCustPersonPoMapper;
import com.urt.mapper.LaoCustomerPoMapper;
import com.urt.mapper.LaoCustomerVerifyPoMapper;
import com.urt.mapper.LaoSsAccountPoMapper;
import com.urt.mapper.ext.LaoCustomerVerifyPoExtMapper;
import com.urt.mapper.ext.LaoSsAccountPoExtMapper;
import com.urt.miniService.MiniSsVerifyServiceImpl;
import com.urt.miniService.authority.MiniSsUserRoleServiceImpl;
import com.urt.po.LaoCustPersonPo;
import com.urt.po.LaoCustomerPo;
import com.urt.po.LaoCustomerVerifyPo;
import com.urt.po.LaoSsAccountPo;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;

@Service("realNameVerifyServiece")
public class RealNameVerifyServieceImpl implements RealNameVerifyServiece{
	private static Logger logger = LoggerFactory.getLogger(RealNameVerifyServieceImpl.class);
	private static final String REQURL = "http://www.lenauth.com/idcard-service/services";
	private static final String USERKEY = "609073FBE37F49D7B38D428934690930";
	private static final String USERSECRET = "lYxYX8mR2SdsKnCo";
	@Autowired 
	LaoCustomerVerifyPoExtMapper laoCustomerVerifyPoExtDao;
	@Autowired
	LaoCustomerVerifyPoMapper customerVerifyDao;
	@Autowired
	LaoCustomerPoMapper customerDao;
	@Autowired
	LaoCustPersonPoMapper custPsersonDao;
	@Autowired
	LaoSsAccountPoMapper accountDao;
	@Autowired 
	private MiniSsVerifyServiceImpl miniSsVerifyServiceImpl;
	@Autowired
	LaoSsAccountPoExtMapper accountExtDao;
	@Autowired MiniSsUserRoleServiceImpl miniUserRoleService;
	@Override
	public boolean checkRealName(String idnum,long accountId){
		// TODO Auto-generated method stub
		if(idnum!=null && idnum.trim().length()>0){
			int count=laoCustomerVerifyPoExtDao.getCountByIdcode(idnum);
			if(laoCustomerVerifyPoExtDao.getCountByIdcode(idnum)>0){
				return false;
			}
		}
		LaoCustomerVerifyPo po=laoCustomerVerifyPoExtDao.getFailMessage(accountId);
		if(po!=null && po.getFailtimes()>=3){
			return false;
		}
		return true;
	}
	@Override
	public ResultMsg customerVerifyServiece(LaoSsRealNameVerifyDto dto,LaoSsAccountDto currentUser) throws Exception {
		// TODO Auto-generated method stub
		
		String nameSecret="";
		String cardScert="";
		if(dto.getRealname()!=null&&!dto.getRealname().trim().equals("")){
				byte[] encrypt3desName = ThreedesUtils.encrypt3DES(dto.getRealname().getBytes("UTF-8"), USERSECRET);
				nameSecret = Base64.encode(encrypt3desName);
		}
		if(dto.getIdnum()!=null&&!dto.getIdnum().trim().equals("")){
				byte[] encrypt3desCard = ThreedesUtils.encrypt3DES(dto.getIdnum().getBytes("UTF-8"), USERSECRET);
				cardScert = Base64.encode(encrypt3desCard);
		}
		Map<String,String>  map = new HashMap<String, String>();
		String date = new SimpleDateFormat("yyyyMMddhhmm").format(new Date()).toString();
		logger.info(">>>>>>>>>>>>>>>>国民认证姓名："+nameSecret);
		map.put("name", nameSecret);
		map.put("cardNo", cardScert);
		map.put("userKey", USERKEY);
		map.put("clienttime", date);
		map.put("method", "verify");
		String  sign = HttpClientUtil.sign(map,"lYxYX8mR2SdsKnCo");
        map.put("v", "1.0");
        map.put("sign", sign);		
		Document sendHttpPostXML = HttpClientUtil.sendHttpPostXML(REQURL, map);
		logger.info("国民认证入参:"+map);
		
		
		//解析xml文件
		Element root = sendHttpPostXML.getRootElement();
		logger.info("国民认证出参:"+sendHttpPostXML);
		ResultMsg result=new ResultMsg();
		if ("0".equalsIgnoreCase(root.elementText("result"))) {
			result.setMsg("实名认证成功");
			result.setSuccess(true);
			//写入三张表 回写用户表
			Long custId=Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID));
			LaoCustomerPo customer=new LaoCustomerPo();
			customer.setCustId(custId);
			/*SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");*/
			customer.setInDate(new Date());
			customer.setCustType("0");
			customer.setCustState("0");
			customer.setPsptTypeCode(dto.getIdtype().toString());
			customer.setPsptId(dto.getIdnum());
			customer.setCustName(dto.getRealname());
			customerDao.insert(customer);
			LaoCustomerVerifyPo verifyPo=(LaoCustomerVerifyPo)ConversionUtil.dto2po(dto, LaoCustomerVerifyPo.class);
			long verfyId=Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID));
			verifyPo.setId(verfyId);
			verifyPo.setCustId(custId);
			verifyPo.setCreatetime(new Date());
			verifyPo.setUpdatetime(new Date());
			verifyPo.setVerifystatus((short)2);
			verifyPo.setAccountId(currentUser.getAcconutId());
			customerVerifyDao.insert(verifyPo);
			
			LaoCustPersonPo custPersonPo=new LaoCustPersonPo();
			Long custPersonId=Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID));
			custPersonPo.setCustId(custId);
			custPersonPo.setCustName(dto.getRealname());
			custPersonPo.setPsptTypeCode(customer.getPsptTypeCode());
			custPersonPo.setPsptId(customer.getPsptId());
			custPsersonDao.insert(custPersonPo);
			currentUser.setCustId(custId);
			LaoSsAccountPo account=(LaoSsAccountPo)ConversionUtil.dto2po(currentUser, LaoSsAccountPo.class);
			account.setCustId(custId);
			accountDao.updateByPrimaryKeySelective(account);
			
		}else{
			LaoCustomerVerifyPo po=laoCustomerVerifyPoExtDao.getFailMessage(currentUser.getAcconutId());
			if(po!=null && po.getFailtimes()>0){
				po.setUpdatetime(new Date());
				po.setFailtimes(po.getFailtimes()+1);
				laoCustomerVerifyPoExtDao.updateFailTimes(po);
			}else{
				LaoCustomerVerifyPo verifyPo=(LaoCustomerVerifyPo)ConversionUtil.dto2po(dto, LaoCustomerVerifyPo.class);
				long verfyId=Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID));
				verifyPo.setId(verfyId);
				verifyPo.setCreatetime(new Date());
				verifyPo.setUpdatetime(new Date());  
				verifyPo.setVerifystatus((short)1);
				verifyPo.setFailtimes(1l);
				verifyPo.setAccountId(currentUser.getAcconutId());
				customerVerifyDao.insert(verifyPo);
			}
			result.setMsg("实名认证失败");
			result.setSuccess(false);
		}
		return result;
	}
	@Override
	public LaoSsRealNameVerifyDto getVerifyByAccountId(Long accountId) {
		// TODO Auto-generated method stub
		LaoCustomerVerifyPo po=laoCustomerVerifyPoExtDao.getVerifyByAccountId(accountId);
		if(po!=null){
			LaoSsRealNameVerifyDto dto=(LaoSsRealNameVerifyDto)ConversionUtil.po2dto(po, LaoSsRealNameVerifyDto.class);
			return dto;
		}else{
			return null;
		}
		
	}
	@Override
	public LaoSsRealNameVerifyDto getVerifyById(Long id) {
		// TODO Auto-generated method stub
		LaoCustomerVerifyPo po=laoCustomerVerifyPoExtDao.getVerifyByAccountId(id);
		if(po!=null){
			LaoSsRealNameVerifyDto dto=(LaoSsRealNameVerifyDto)ConversionUtil.po2dto(po, LaoSsRealNameVerifyDto.class);
			return dto;
		}else{
			return null;
		}
	}
	@Override
	public Boolean approved(Long id) {
		// TODO Auto-generated method stub
		LaoCustomerVerifyPo po=laoCustomerVerifyPoExtDao.getVerifyById(id);
		if(po!=null){
			po.setUpdatetime(new Date());
			po.setVerifystatus((short)3);
			if(laoCustomerVerifyPoExtDao.updateStatus(po)>0){
				LaoSsAccountPo accountPo=accountExtDao.queryByCustId(po.getCustId()).get(0);
				miniUserRoleService.deleteSsUserRole(accountPo.getAcconutId());
				LaoSsAccountRoleDto dto=new LaoSsAccountRoleDto();
				/*Properties prop=new Properties();
				InputStream in=this.getClass().getResourceAsStream("application.properies");
				String roleId="";
				try {
					prop.load(in);
					roleId=prop.getProperty("common").trim();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				dto.setRoleId(3l);
				dto.setUserId(po.getAccountId());
				miniUserRoleService.insertTfFSsUserRole(dto);
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	@Override
	public Boolean unapproved(Long id) {
		// TODO Auto-generated method stub
		LaoCustomerVerifyPo po=laoCustomerVerifyPoExtDao.getVerifyById(id);
		if(po!=null){
			po.setUpdatetime(new Date());
			po.setVerifystatus((short)4);
			if(laoCustomerVerifyPoExtDao.updateStatus(po)>0){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	@Override
	public Map<String, Object> queryPage(LaoSsRealNameVerifyDto dto,
			Integer pageNo, Integer pageSize) {
		// TODO Auto-generated method stub
		Map<String, Object> map=miniSsVerifyServiceImpl.queryPage(dto, pageNo, pageSize);
		return map;
	}

}
