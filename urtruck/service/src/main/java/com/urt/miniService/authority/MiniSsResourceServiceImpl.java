package com.urt.miniService.authority;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.urt.common.util.ConversionUtil;
import com.urt.common.util.Page;
import com.urt.dto.LaoSsResourceDto;
import com.urt.mapper.LaoSsResourcePoMapper;
import com.urt.mapper.TfFSsResourcePoMapper;
import com.urt.mapper.ext.LaoSsResourcePoExtMapper;
import com.urt.po.LaoSsResourcePo;
import com.urt.po.TfFSsResourcePo;

/**
 * 功能描述：
 * @author zhaoxy9
 * @date 2016年6月21日
 */
@Service("miniResourceService")
public class MiniSsResourceServiceImpl {

	@Autowired
	private LaoSsResourcePoExtMapper laoSsResourcePoExtMapper;
	@Autowired
	private LaoSsResourcePoMapper laoSsResourcePoMapper;
	/**
	 * 功能描述：系统用户新增微服务
	 * @author zhaoxy9
	 * @date 2016年6月20日 下午2:27:43
	 * @param dto
	 * @return int
	 */
	public int saveSsRole(LaoSsResourceDto dto) {
		LaoSsResourcePo po = (LaoSsResourcePo)ConversionUtil.dto2po(dto, LaoSsResourcePo.class);
		return laoSsResourcePoMapper.insertSelective(po);
	}
	
	/**
	 * 功能描述：查询系统用户
	 * @author zhaoxy9
	 * @date 2016年6月20日 下午2:28:20
	 * @param  userId
	 * @return TfFSsRoleDto
	 */
	public LaoSsResourceDto selectSsUserById(Long userId) {
		LaoSsResourcePo dto = laoSsResourcePoMapper.selectByPrimaryKey(userId);
		return (LaoSsResourceDto) ConversionUtil.po2dto(dto, LaoSsResourceDto.class);
	}
	
	/**
	 * 功能描述：更新系统用户
	 * @author zhaoxy9
	 * @date 2016年6月20日 下午2:38:11
	 * @param dto
	 * @return
	 */
	public int updateSsUser(LaoSsResourceDto dto){
		LaoSsResourcePo record = (LaoSsResourcePo) ConversionUtil.dto2po(dto, LaoSsResourcePo.class);
		return laoSsResourcePoMapper.updateByPrimaryKeySelective(record);
	}
	
	/**
	 * 功能描述：删除系统用户
	 * @author zhaoxy9
	 * @date 2016年6月20日 下午2:29:01
	 * @param userId
	 * @return
	 */
	public int deleteSsRole(Long userId){
		return laoSsResourcePoMapper.deleteByPrimaryKey(userId);
	}

	/**
	 * 功能描述：分页查询联系人列表
	 * @author zhaoxy9
	 * @date 2016年6月20日 下午3:48:13
	 * @param dto
	 * @param pageNo
	 * @param pageSize
	 * @return Map<String, Object>
	 */
	public Map<String, Object> selectTfFSsResourceByPage(LaoSsResourceDto dto, int pageNo, int pageSize) {
		Page<LaoSsResourcePo> resourcePage = new Page<LaoSsResourcePo>();
		resourcePage.setPageNo(pageNo);
		resourcePage.setPageSize(pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("param", (LaoSsResourcePo) ConversionUtil.dto2po(dto, LaoSsResourcePo.class));
		resourcePage.setParams(params);
		List<LaoSsResourcePo> resourceList = laoSsResourcePoExtMapper.queryResourceByPage(resourcePage);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data", ConversionUtil.poList2dtoList(resourceList, LaoSsResourceDto.class));
		resultMap.put("iTotalRecords", pageSize);//当前页包含的记录数
		resultMap.put("iTotalDisplayRecords", resourcePage.getTotalRecord());//总记录数 
		return resultMap;
	}
	
	/**
	 * 功能描述：根据用户id查询用户资源集
	 * @author cuichao
	 * @date 2016年6月21日 下午1:19:40
	 * @param @param userId
	 * @param @return 
	 * @return List<TfFSsResourceDto>
	 */
	@SuppressWarnings("unchecked")
	public List<LaoSsResourceDto> getListByUserId(Long userId){
		List<LaoSsResourcePo> poList = laoSsResourcePoExtMapper.queryListByUserId(userId);
		if(CollectionUtils.isNotEmpty(poList)){
			List<LaoSsResourceDto> dtoList = ConversionUtil.poList2dtoList(poList, LaoSsResourceDto.class);
			return dtoList;
		}
		return Collections.emptyList();
	}
	
}
