package com.urt.Ability.http.mno;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.dto.http.mno.LaoMnoProvideServerDto;
import com.urt.interfaces.http.mno.LaoMnoProvideServerService;
import com.urt.mapper.mno.LaoMnoProvideServerMapper;
import com.urt.modules.mapper.BeanMapper;
import com.urt.po.mno.LaoMnoProvideServer;
import com.urt.po.mno.LaoMnoProvideServerExample;

import java.util.List;
import java.util.Map;

@Service("laoMnoProvideServerService")
public class LaoMnoProvideServerServiceImpl implements LaoMnoProvideServerService {

    protected static final Logger logger = Logger.getLogger(LaoMnoProvideServerServiceImpl.class);

    @Autowired
    LaoMnoProvideServerMapper laoMnoProvideServerMapper;


    @Override
    public LaoMnoProvideServerDto selectByServerId(String serverId) {
        return null;
    }

    @Override
    public LaoMnoProvideServerDto selectByServerName(String serverName) {
        LaoMnoProvideServerExample example = new LaoMnoProvideServerExample();
        example.createCriteria().andServerNameEqualTo(serverName);
        List<LaoMnoProvideServer> list = laoMnoProvideServerMapper.selectByExample(example);
        if(list.size()>0){
            LaoMnoProvideServerDto dto = new LaoMnoProvideServerDto();
            BeanMapper.copy(list.get(0), dto);
            return dto;
        }
        return null;
    }

    @Override
    public List<LaoMnoProvideServerDto> queryServerList(Map<String, String> params) {
        return null;
    }
}
