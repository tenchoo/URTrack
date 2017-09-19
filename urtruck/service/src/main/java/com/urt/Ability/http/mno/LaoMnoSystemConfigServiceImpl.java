package com.urt.Ability.http.mno;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.dto.http.mno.LaoMnoSystemConfigDto;
import com.urt.interfaces.http.mno.LaoMnoSystemConfigService;
import com.urt.mapper.mno.LaoMnoSystemConfigMapper;
import com.urt.modules.mapper.BeanMapper;
import com.urt.po.mno.LaoMnoSystemConfig;
import com.urt.po.mno.LaoMnoSystemConfigExample;

import java.util.List;

@Service("laoMnoSystemConfigService")
public class LaoMnoSystemConfigServiceImpl implements LaoMnoSystemConfigService {

    protected static final Logger logger = Logger.getLogger(LaoMnoSystemConfigServiceImpl.class);

    @Autowired
    LaoMnoSystemConfigMapper laoMnoSystemConfigMapper;


    @Override
    public LaoMnoSystemConfigDto querySystemConfig(String systemId) {
        LaoMnoSystemConfigExample example = new LaoMnoSystemConfigExample();
        example.createCriteria().andSystemIdEqualTo(systemId);
        List<LaoMnoSystemConfig> list = laoMnoSystemConfigMapper.selectByExample(example);
        if (list.size() > 0) {
            LaoMnoSystemConfigDto dto = new LaoMnoSystemConfigDto();
            BeanMapper.copy(list.get(0), dto);
            return dto;
        }
        return null;
    }
}
