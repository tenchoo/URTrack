package com.urt.Ability.http.mno;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.dto.http.mno.LaoMnoAccessLogDto;
import com.urt.interfaces.http.mno.LaoMnoAccessLogService;
import com.urt.mapper.mno.LaoMnoAccessLogMapper;

import java.util.List;
import java.util.Map;

@Service("laoMnoAccessLogService")
public class LaoMnoAccessLogServiceImpl implements LaoMnoAccessLogService {

    protected static final Logger logger = Logger.getLogger(LaoMnoAccessLogServiceImpl.class);

    @Autowired
    LaoMnoAccessLogMapper laoMnoAccessLogMapper;

    @Override
    public int insert(LaoMnoAccessLogDto accessLogDto) {
        return 0;
    }

    @Override
    public List<LaoMnoAccessLogDto> queryAccessLog(Map<String, String> param) {
        return null;
    }
}
