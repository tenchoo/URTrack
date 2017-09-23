package com.urt.service.User;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.urt.dto.Goods.LaoUserHisDto;
import com.urt.interfaces.User.UserHisService;
import com.urt.mapper.LaoUserHisPoMapper;
import com.urt.mapper.ext.LaoUserHisPoExtMapper;
import com.urt.modules.mapper.BeanMapper;
import com.urt.po.LaoUserHisPo;

@Service("userHisService")
@Transactional(propagation = Propagation.REQUIRED)
public class UserHisServiceImpl implements UserHisService {
    @Autowired
    private LaoUserHisPoMapper laoUserHisDao;
    @Autowired
    private LaoUserHisPoExtMapper laoUserHisExtDao;

    @Override
    public int insertSelective(LaoUserHisDto record) {
        if(record==null){
            return -1;
        }       
        LaoUserHisPo recordNew = new LaoUserHisPo();
        BeanMapper.copy(record, recordNew);
        return laoUserHisDao.insertSelective(recordNew);      
    }

    @Override
    public int insertBatch(List<LaoUserHisDto> record) {
        List<LaoUserHisPo> recordNew =new ArrayList<LaoUserHisPo>();
        for(LaoUserHisDto po:record){
            LaoUserHisPo recordTemp = new LaoUserHisPo();
            BeanMapper.copy(po, recordTemp);
            recordNew.add(recordTemp);
        }       
        return laoUserHisExtDao.insertBatch(recordNew);
    }

}
