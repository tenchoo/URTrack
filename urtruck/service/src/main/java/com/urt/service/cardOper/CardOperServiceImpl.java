package com.urt.service.cardOper;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.urt.common.enumeration.OperationType;
import com.urt.dto.Goods.LaoUserDto;
import com.urt.dto.Goods.LaoUserHisDto;
import com.urt.interfaces.User.UserHisService;
import com.urt.interfaces.cardOper.CardOperService;
import com.urt.mapper.ext.IccidLibExtMapper;
import com.urt.mapper.ext.LaoCustomerPoExtMapper;
import com.urt.mapper.ext.LaoUserExtMapper;
import com.urt.mapper.ext.LaoUserGoodsExtMapper;
import com.urt.mapper.ext.LaoUserOperatorPlanExtMapper;
import com.urt.miniService.authority.MiniUserStateChgServiceImpl;
import com.urt.modules.mapper.BeanMapper;
import com.urt.po.IccidLib;
import com.urt.po.LaoUser;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;


@Service("cardOperService")
@Transactional(propagation = Propagation.REQUIRED)
public class CardOperServiceImpl implements CardOperService {
    
    private static Logger logger = LoggerFactory.getLogger(CardOperServiceImpl.class);
    @Autowired
    private IccidLibExtMapper libExtDao;
    @Autowired
    private LaoUserExtMapper laoUserExt;
    @Autowired
    private LaoUserGoodsExtMapper  laoUserGoodsExt;
    @Autowired
    private LaoUserOperatorPlanExtMapper laoUserOperatorPlanExt;
    @Autowired
    private LaoCustomerPoExtMapper laoCustomerPoExt;
    @Autowired
    private UserHisService userHisService;
    @Autowired
    private MiniUserStateChgServiceImpl miniUserStateChgServiceImpl;
    
    
    @Override
    public int singleStateChg(String iccId,String msisonId,String psptId,String psptTypeCode,Long chCustId,Long accountId) {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("iccid", iccId);
        param.put("msisdn", msisonId);   
        param.put("psptId", psptId);  
        param.put("chCustId", chCustId); 
        param.put("psptTypeCode", psptTypeCode);
        //更新lao_iccid_lib
        //0否    1激活
        IccidLib iccidLib = libExtDao.selectByMap(param);
        if(iccidLib != null && iccidLib.getActived().equals("0")){
            iccidLib.setActived("1");  //0成功1失败
            iccidLib.setInDate(new Date());
           // libExtDao.updateByIccid(iccidLib);
            logger.info("iccidLib != null iccid:"+iccId+" msisdn:"+msisonId);
        }
        
        //更新lao_user  REMOVE_TAG生命周期   state_code卡状态  in_date,open_date
        LaoUser laoUser=laoUserExt.selectByMap(param);
        Long custId=laoCustomerPoExt.queryPerCustId(param);
        logger.info("custId:"+custId+" psptTypeCode:"+param.get("psptTypeCode")+" psptId:"+param.get("psptId"));
        if(laoUser!=null && custId!=null){          
            laoUser.setFirstCallTime(new Date());
            laoUser.setRemoveTag("0");
            laoUser.setCustId(custId);
            laoUserExt.updateByIccidOrMsi(laoUser);
            //记录卡生命周期变化轨迹
            LaoUserDto laoUserDto = new LaoUserDto();
            BeanMapper.copy(laoUser, laoUserDto);
            //修改Dto写一个带参构造方法，将laoUser的参数赋值给reacord
            LaoUserHisDto record =new LaoUserHisDto(laoUserDto,OperationType.UPDATE.getCode(), "",new Date(),accountId);              
            record.setSeqId(Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.USER_HIS_ID)));
            logger.info("CardOperService.singleStateChg  iccid:"+iccId+" msisdn:"+msisonId+" SeqID:"+record.getSeqId());
            userHisService.insertSelective(record);     
            List<Long> goodList =laoUserGoodsExt.getGoodsListByUserId(laoUser.getUserId());//测试期套餐
            List<Long> goodListUse =laoUserGoodsExt.getGoodsList(laoUser.getUserId());//正式期套餐
            //param.clear();
            List< Map<String, Object>> list =new ArrayList<Map<String, Object>>();
            List< Map<String, Object>> listUseDate =new ArrayList<Map<String, Object>>();
            for(Long lo:goodList){     
                Map<String, Object> par =new HashMap<String, Object>();
                par.put("goodsId", lo);
                par.put("endDate", new Date());   
                par.put("userId", laoUser.getUserId());
                list.add(par);
            }
            for(Long lo:goodListUse){     
                Map<String, Object> par =new HashMap<String, Object>();
                par.put("goodsId", lo);
                par.put("useDate", new Date());   
                par.put("userId", laoUser.getUserId());
                listUseDate.add(par);
            }
            //根据用户id和goodType=7 更新lao_user_goods end_date=sysdate 
            if(list.size()==0 || listUseDate.size()==0 ){
                logger.info("CardOperServiceImpl.singleStateChg find goodInfo is empty! UserId:"+laoUser.getUserId());
                return -1;
            }           

            laoUserGoodsExt.updateByUserIdAndGoodsId(list);  
            laoUserOperatorPlanExt.updateByUserIdAndGoodsId(list);
            laoUserGoodsExt.updateByUseDate(listUseDate);                           
            laoUserOperatorPlanExt.updateByUserDate(listUseDate);  
            
        }else{
            logger.info("custId==null!"+" psptTypeCode:"+param.get("psptTypeCode")+" psptId:"+param.get("psptId"));
            return -2;
        }       
        return 0;
    }


    @Override
    public int multiStateChg( Map<String, List<String>> tMap, Long accountId) {
        Date nowDate=new Date();
        List<LaoUser> userList =null;
        List< Map<String, Object>> list =new ArrayList<Map<String, Object>>();
        List< Map<String, Object>> listUseDate =new ArrayList<Map<String, Object>>();
        Map<String,Object> param = new HashMap<String,Object>();
        if(tMap.get("psptId").size() >0 && tMap.get("psptTypeCode").size() >0){
            param.put("psptId", tMap.get("psptId").get(0));  
            param.put("psptTypeCode", tMap.get("psptTypeCode").get(0));
        }else{
            logger.info("CardOperServiceImpl.multiStateChg: psptTypeCode or psptId is empty!");
            return -1;
        }
       
        if(tMap.get("iccId").size() > 0){
            //根据iccid批量变更      
            List<IccidLib> iccidList = libExtDao.selectByListIccid(tMap.get("iccId"));
            for(IccidLib lib:iccidList){
                lib.setActived("1");
                lib.setInDate(nowDate);
            }
            libExtDao.updateByIccidSelective(iccidList);
             userList=laoUserExt.selectByListIccid(tMap.get("iccId"));  
             Long custId=laoCustomerPoExt.queryPerCustId(param);
            for(LaoUser us:userList){
                us.setFirstCallTime(nowDate);
                us.setRemoveTag("0"); 
                us.setCustId(custId);
                List<Long> goodList =laoUserGoodsExt.getGoodsListByUserId(us.getUserId());//测试期套餐列表
                List<Long> goodListUse =laoUserGoodsExt.getGoodsList(us.getUserId());//正式期套餐
                for(Long lo:goodList){     
                    Map<String, Object> par =new HashMap<String, Object>();
                    par.put("goodsId", lo);
                    par.put("endDate", new Date());   
                    par.put("userId", us.getUserId());
                    list.add(par);
                }
                
                for(Long lo:goodListUse){     
                    Map<String, Object> par =new HashMap<String, Object>();
                    par.put("goodsId", lo);
                    par.put("useDate", new Date());   
                    par.put("userId", us.getUserId());
                    listUseDate.add(par);
                }
                
                if(list.size()==0 || listUseDate.size()==0 ){
                    logger.info("CardOperServiceImpl.multiStateChg find goodInfo is empty! UserId:"+us.getUserId());
                    return -1;
                }  
            }
             
            
            laoUserGoodsExt.updateByUserIdAndGoodsId(list);//更新endDtae
            laoUserOperatorPlanExt.updateByUserIdAndGoodsId(list); //   
            
            laoUserOperatorPlanExt.updateByUserIdAndGoodsId(list);
            laoUserOperatorPlanExt.updateByUserDate(listUseDate);
            
            laoUserExt.updateByIccidSelective(userList);
            logger.info("CardOperService.singleStateChg  iccid.size:"+tMap.get("iccId")+" iccidList:"+iccidList.size());
        }else if(tMap.get("msisonId").size() > 0){
          //根据msison批量变更
            List<IccidLib> msisonList = libExtDao.selectByListMsison(tMap.get("msisonId"));
            for(IccidLib lib:msisonList){
                lib.setActived("1");
                lib.setInDate(nowDate);               
            }
            libExtDao.updateByMsisonSelective(msisonList);
            userList=laoUserExt.selectByListMsison(tMap.get("msisonId"));
            for(LaoUser us:userList){
                us.setFirstCallTime(nowDate);
                us.setRemoveTag("0");            
                List<Long> goodList =laoUserGoodsExt.getGoodsListByUserId(us.getUserId());
                List<Long> goodListUse =laoUserGoodsExt.getGoodsList(us.getUserId());//正式期套餐
                for(Long lo:goodList){     
                    Map<String, Object> par =new HashMap<String, Object>();
                    par.put("goodsId", lo);
                    par.put("endDate", new Date());   
                    par.put("userId", us.getUserId());
                    list.add(par);
                }
                
                for(Long lo:goodListUse){     
                    Map<String, Object> par =new HashMap<String, Object>();
                    par.put("goodsId", lo);
                    par.put("useDate", new Date());   
                    par.put("userId", us.getUserId());
                    listUseDate.add(par);
                }
                
                if(list.size()==0 || listUseDate.size()==0 ){
                    logger.info("CardOperServiceImpl.multiStateChg find goodInfo is empty! UserId:"+us.getUserId());
                    return -1;
                }  
            }
            laoUserGoodsExt.updateByUserIdAndGoodsId(list);
            laoUserOperatorPlanExt.updateByUserIdAndGoodsId(list); 
            
            laoUserOperatorPlanExt.updateByUserIdAndGoodsId(list);
            laoUserOperatorPlanExt.updateByUserDate(listUseDate);
            
            laoUserExt.updateByIccidSelective(userList);
            logger.info("CardOperService.singleStateChg  msison.size:"+tMap.get("msisonId")+" msisonList:"+msisonList.size());
        }     
        List<LaoUserHisDto> userListDto=new ArrayList<LaoUserHisDto>();
        if(userList!=null){
            for(LaoUser laouser:userList){
                LaoUserDto laoUserDto = new LaoUserDto();
                BeanMapper.copy(laouser, laoUserDto);
                //修改Dto写一个带参构造方法，将laoUser的参数赋值给reacord
                LaoUserHisDto record =new LaoUserHisDto(laoUserDto,OperationType.UPDATE.getCode(),"",new Date(),accountId);              
                record.setSeqId(Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.USER_HIS_ID)));
                userListDto.add(record);
            }
            userHisService.insertBatch(userListDto);
        }
        
        return 0;
    }
    
   //查询测试期转正常期信息
    @Override
    public Map<String, Object> queryPage(Long chCustId, Integer pageNo,
            Integer pageSize) {
        Map<String, Object> map=miniUserStateChgServiceImpl.queryPageUser(chCustId, pageNo, pageSize);
        return map;
    }
    
}
