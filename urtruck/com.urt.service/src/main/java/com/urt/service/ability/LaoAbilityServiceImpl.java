package com.urt.service.ability;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urt.common.util.ConversionUtil;
import com.urt.common.util.Page;
import com.urt.dto.LaoProvideServerDto;
import com.urt.dto.LaoUserIpManagerDto;
import com.urt.interfaces.ability.LaoAbilityService;
import com.urt.mapper.LaoProvideServerMapper;
import com.urt.mapper.LaoSsRoleCustPoMapper;
import com.urt.mapper.ext.LaoProvideServerExtMapper;
import com.urt.mapper.ext.LaoSsRoleCustPoExtMapper;
import com.urt.modules.mapper.BeanMapper;
import com.urt.po.LaoProvideServer;
import com.urt.po.LaoSsRoleCustPo;
import com.urt.po.LaoUserIpManager;

@Service("laoAbilityService")
public class LaoAbilityServiceImpl implements LaoAbilityService {

	@Autowired
	private LaoSsRoleCustPoExtMapper laoSsRoleCustPoExtMapper;

	@Autowired
	private LaoProvideServerExtMapper laoProvideServerExtMapper;
	@Autowired
	private LaoSsRoleCustPoMapper laossRoleMapper;

	@Override
	public Map<String, Object> queryPage(Integer pageNo, Integer pageSize, String custName) {

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("pageNo", pageNo);
		param.put("pageSize", pageSize);
		param.put("custName", custName);
		Integer countTotal = laoSsRoleCustPoExtMapper.countTotal(custName);
		List<Map<String, Object>> queryPage1 = laoSsRoleCustPoExtMapper.queryPage(param);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data", queryPage1);
		resultMap.put("iTotalRecords", pageSize);// 当前页包含的记录数
		resultMap.put("iTotalDisplayRecords", countTotal);
		return resultMap;
	}

	@Override
	public List<LaoProvideServerDto> queryPrivateServer(Long id) {
		List<LaoProvideServer> list = laoProvideServerExtMapper.queryPrivateServerByCustId(id);
		if (list != null && list.size() > 0) {
			List<LaoProvideServerDto> dtos = new ArrayList<LaoProvideServerDto>();
			for (LaoProvideServer laoProvideServer : list) {
				LaoProvideServerDto dto = new LaoProvideServerDto();
				BeanMapper.copy(laoProvideServer, dto);
				dtos.add(dto);
			}
			return dtos;
		}
		return null;

	}

	@Override
	public List<LaoProvideServerDto> getPrivideServer() {
		List<LaoProvideServer> list = laoProvideServerExtMapper.queryList();
		List<LaoProvideServerDto> dtos = new ArrayList<LaoProvideServerDto>();
		if (list != null && list.size() > 0) {
			for (LaoProvideServer laoProvideServer : list) {
				LaoProvideServerDto dto = new LaoProvideServerDto();
				BeanMapper.copy(laoProvideServer, dto);
				dtos.add(dto);
			}
		}
		return dtos;

	}

	@Override
	public Boolean updateServer(String serverIds, String custId) {
		Boolean falg = true;
		try {
			LaoSsRoleCustPo queryByCustId = laoSsRoleCustPoExtMapper.queryByCustId(Long.valueOf(custId));
			// 先删除
			int conn = laoSsRoleCustPoExtMapper.deleteLaoRoleServerConn(queryByCustId.getRoleId());
			if (conn > 0) {
				falg = true;
			}
			if (null == serverIds && "" == serverIds) {
				return falg;
			}
			String[] sp = serverIds.split(",");
			for (int i = 0; i < sp.length; i++) {

				laoSsRoleCustPoExtMapper.insertLaoRoleServerConn(queryByCustId.getRoleId(), Long.valueOf(sp[i]));

			}
		} catch (NumberFormatException e) {
			falg = false;
			e.printStackTrace();
		}
		return falg;
	}

	@Override
	public Boolean inSertIntoServer(String custId, String serverIds) {
		Boolean falg = false;
		try {
			String[] sp = serverIds.split(",");
			LaoSsRoleCustPo queryByCustId = laoSsRoleCustPoExtMapper.queryByCustId(Long.valueOf(custId));
			if (queryByCustId == null) {
				// 先查询最大值
				Long queryRoleMax = laoSsRoleCustPoExtMapper.queryRoleMax();
				LaoSsRoleCustPo laoSsRoleCustPo = new LaoSsRoleCustPo();
				laoSsRoleCustPo.setCustId(Long.valueOf(custId));
				laoSsRoleCustPo.setRoleId(queryRoleMax + 1);
				int insert = laossRoleMapper.insert(laoSsRoleCustPo);
				if (insert > 0) {
					falg = true;
				}
				for (int i = 0; i < sp.length; i++) {
					laoSsRoleCustPoExtMapper.insertLaoRoleServerConn(queryRoleMax + 1, Long.valueOf(sp[i]));
				}
			} else {
				int deleteLaoRoleServerConn = laoSsRoleCustPoExtMapper
						.deleteLaoRoleServerConn(queryByCustId.getRoleId());
				if (deleteLaoRoleServerConn > 0) {
					falg = true;
				}
				for (int i = 0; i < sp.length; i++) {
					laoSsRoleCustPoExtMapper.insertLaoRoleServerConn(queryByCustId.getRoleId(), Long.valueOf(sp[i]));
				}
			}
		} catch (NumberFormatException e) {
			falg = false;
			e.printStackTrace();
		}
		return falg;
	}
}
