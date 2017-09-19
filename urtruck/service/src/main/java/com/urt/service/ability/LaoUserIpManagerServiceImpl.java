package com.urt.service.ability;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.common.util.ConversionUtil;
import com.urt.common.util.Page;
import com.urt.dto.LaoUserIpManagerDto;
import com.urt.interfaces.ability.LaoUserIpManagerService;
import com.urt.mapper.LaoUserIpManagerMapper;
import com.urt.mapper.ext.LaoUserIpManagerExtMapper;
import com.urt.modules.mapper.BeanMapper;
import com.urt.po.LaoAlmRulePo;
import com.urt.po.LaoUserIpManager;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;

@Service("laoUserIpManagerService")
public class LaoUserIpManagerServiceImpl implements LaoUserIpManagerService {

	@Autowired
	private LaoUserIpManagerExtMapper laoUserIpManagerExtMapper;
	@Autowired
	private LaoUserIpManagerMapper userIP;

	@Override
	public List<LaoUserIpManagerDto> getUserIpByCustId(String custId) {
		List<LaoUserIpManager> laoUserIp = laoUserIpManagerExtMapper.getLaoUserIpByCustId(Long.valueOf(custId));
		if (null != laoUserIp && laoUserIp.size() > 0) {
			List<LaoUserIpManagerDto> dtos = new ArrayList<LaoUserIpManagerDto>();
			for (LaoUserIpManager laoUserIpManager : laoUserIp) {
				LaoUserIpManagerDto dto = new LaoUserIpManagerDto();
				BeanMapper.copy(laoUserIpManager, dto);
				dtos.add(dto);
			}
			return dtos;
		}

		return null;
	}

	@Override
	public Map<String, Object> queryPage(LaoUserIpManagerDto dto, int pageNo, int pageSize) {
		// 查询数据
		Page<LaoUserIpManager> page = new Page<LaoUserIpManager>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("param", (LaoUserIpManager) ConversionUtil.dto2po(dto, LaoUserIpManager.class));
		page.setParams(params);
		List<Map<String, Object>> laoUserIp = laoUserIpManagerExtMapper.queryPage(page);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data", laoUserIp);
		resultMap.put("iTotalRecords", pageSize);// 当前页包含的记录数
		resultMap.put("iTotalDisplayRecords", page.getTotalRecord());// 总记录数
		return resultMap;
	}

	@Override
	public Boolean delUserIP(String id) {
		int deleteUserIP = userIP.deleteUserIP(Long.valueOf(id));
		if (deleteUserIP>0) {
			return  true;
		}
		return false;
	}

	@Override
	public Boolean stopIP(String id) {
		int updateISDis = userIP.updateStop(Long.valueOf(id));
		if (updateISDis>0) {
			return true;
		}
		return false;
	}

	@Override
	public Boolean openIP(String Id) {
		int updateISDis = userIP.updateOpen(Long.valueOf(Id));
		if (updateISDis>0) {
			return true;
		}
		return false;
	}

	@Override
	public Boolean saveIp(Long custId, String ip) {
		LaoUserIpManager laoUserIpManager = new LaoUserIpManager();
		String idSeq = ZkGenerateSeq.getIdSeq(SeqID.DEVICEREL_ID);
		laoUserIpManager.setId(Long.valueOf(idSeq));
		laoUserIpManager.setCustId(custId);
		laoUserIpManager.setCreatDate(new Date());
		laoUserIpManager.setUpdatDate(new Date());
		laoUserIpManager.setIpAddress(ip);
		laoUserIpManager.setIsdisabled(Short.valueOf("0"));
		int insert = userIP.insert(laoUserIpManager);
		if (insert>0) {
			
			return true;
		}
		return false;
	}

}
