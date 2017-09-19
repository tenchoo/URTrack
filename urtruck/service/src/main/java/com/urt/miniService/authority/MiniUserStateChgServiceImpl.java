package com.urt.miniService.authority;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.common.util.ConversionUtil;
import com.urt.common.util.Page;
import com.urt.dto.Goods.LaoUserDto;
import com.urt.mapper.ext.LaoUserExtMapper;
import com.urt.po.LaoUser;

@Service("miniUserStateChgServiceImpl")
public class MiniUserStateChgServiceImpl {
    
    private static Logger logger = LoggerFactory.getLogger(MiniUserStateChgServiceImpl.class);
    @Autowired
    private LaoUserExtMapper laoUserExtMapper;
    
    /**
     * 功能描述：查询
     * @param dto
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Map<String, Object> queryPageUser(Long chCustId, int pageNo, int pageSize) {
        Page<LaoUser> page = new Page<LaoUser>();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("chCustId", chCustId);
        page.setParams(params);   
        List<LaoUser> laoUserList = laoUserExtMapper.queryPageUser(page);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("data", ConversionUtil.poList2dtoList(laoUserList, LaoUserDto.class));
        resultMap.put("iTotalRecords", pageSize);//当前页包含的记录数
        resultMap.put("iTotalDisplayRecords", page.getTotalRecord());//总记录数 
        return resultMap;
    }
}
