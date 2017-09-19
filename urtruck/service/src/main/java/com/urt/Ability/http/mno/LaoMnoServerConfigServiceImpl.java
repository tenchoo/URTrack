package com.urt.Ability.http.mno;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.dto.http.mno.LaoMnoServerConfigDto;
import com.urt.interfaces.http.mno.LaoMnoServerConfigService;
import com.urt.mapper.mno.LaoMnoServerConfigMapper;
import com.urt.modules.mapper.BeanMapper;
import com.urt.po.mno.LaoMnoServerConfig;
import com.urt.po.mno.LaoMnoServerConfigExample;

import java.util.List;

@Service("laoMnoServerConfigService")
public class LaoMnoServerConfigServiceImpl implements LaoMnoServerConfigService {

    protected static final Logger logger = Logger.getLogger(LaoMnoServerConfigServiceImpl.class);

    @Autowired
    LaoMnoServerConfigMapper laoMnoServerConfigMapper;

    @Override
    public LaoMnoServerConfigDto selectBySystemIdAndServerId(String systemId, Long serverId) {
        LaoMnoServerConfigExample example = new LaoMnoServerConfigExample();
        example.createCriteria().andSystemIdEqualTo(systemId).andServerIdEqualTo(serverId);
        List<LaoMnoServerConfig> list = laoMnoServerConfigMapper.selectByExample(example);
        if (list.size() > 0) {
            LaoMnoServerConfigDto dto = new LaoMnoServerConfigDto();
            BeanMapper.copy(list.get(0), dto);
            return dto;
        }
        return null;
    }
}
