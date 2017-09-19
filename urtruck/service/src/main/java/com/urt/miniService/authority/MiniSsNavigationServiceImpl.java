package com.urt.miniService.authority;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.urt.common.util.ConversionUtil;
import com.urt.dto.LaoSsNavigationDto;
import com.urt.mapper.LaoSsNavigationPoMapper;
import com.urt.mapper.TfFSsNavigationPoMapper;
import com.urt.mapper.ext.LaoSsNavigationPoExtMapper;
import com.urt.po.LaoSsNavigationPo;
import com.urt.po.TfFSsNavigationPo;

/**
 * 类说明：菜单导航微服务
 * @author zhangbt3
 * @date 2016年6月20日 下午6:55:16
 */
@Service("miniNavigationService")
public class MiniSsNavigationServiceImpl {

	@Autowired
	private LaoSsNavigationPoMapper laoSsNavigationPoMapper;
	@Autowired
	private LaoSsNavigationPoExtMapper laoSsNavigationPoExtMapper;
	/**
	 * 功能描述：菜单导航新增微服务
	 * @author zhangbt3
	 * @date 2016年6月20日 下午7:00:43
	 * @param dto
	 * @return int
	 */
	public int saveSsNavigation(LaoSsNavigationDto dto) {
		LaoSsNavigationPo po = (LaoSsNavigationPo)ConversionUtil.dto2po(dto, LaoSsNavigationPo.class);
		return laoSsNavigationPoMapper.insertSelective(po);
	}
	
	/**
	 * 功能描述：查询菜单导航
	 * @author zhangbt3
	 * @date 2016年6月20日  下午7:03:17
	 * @param userId
	 * @return TfFSsNavigationDto
	 */
	public LaoSsNavigationDto selectSsNavigationById(Long navigationId) {
		LaoSsNavigationPo po=laoSsNavigationPoMapper.selectByPrimaryKey(navigationId);
		return (LaoSsNavigationDto) ConversionUtil.po2dto(po, LaoSsNavigationDto.class);
	}
	
	/**
	 * 功能描述：更新菜单导航
	 * @author zhangbt3
	 * @date 2016年6月20日  下午7:06:40
	 * @param dto
	 * @return
	 */
	public int updateSsNavigation(LaoSsNavigationDto dto){
		LaoSsNavigationPo record = (LaoSsNavigationPo) ConversionUtil.dto2po(dto, LaoSsNavigationPo.class);
		return laoSsNavigationPoMapper.updateByPrimaryKeySelective(record);
	}
	
	/**
	 * 功能描述：删除菜单导航
	 * @author zhangbt3
	 * @date 2016年6月20日  下午7:10:35
	 * @param navigationId
	 * @return
	 */
	public int deleteSsNavigation(Long navigationId){
		return laoSsNavigationPoMapper.deleteByPrimaryKey(navigationId);
	}
	
	/**
	 * 功能描述：根据用户id查询用户菜单集
	 * @author cuichao
	 * @date 2016年6月21日 上午10:39:06
	 * @param @param userId
	 * @param @return 
	 * @return List<TfFSsNavigationDto>
	 */
	@SuppressWarnings("unchecked")
	public List<LaoSsNavigationDto> getListByUserId(Long userId){
		List<LaoSsNavigationPo> poList = laoSsNavigationPoExtMapper.queryListByUserId(userId);
		if(CollectionUtils.isNotEmpty(poList)){
			List<LaoSsNavigationDto> dtoList = ConversionUtil.poList2dtoList(poList, LaoSsNavigationDto.class);
			return dtoList;
		}
		return Collections.emptyList();
	}
	/**
	 * 功能描述：查询所有菜单
	 * @author zhaoxy
	 * @date 2016年6月22日 下午5:41:25
	 * @param @return 
	 * @return List<TfFSsNavigationDto>
	 */
	@SuppressWarnings("unchecked")
	public List<LaoSsNavigationDto> queryList() {
		List<LaoSsNavigationPo> navigationPos=laoSsNavigationPoExtMapper.queryList();
		return (List<LaoSsNavigationDto>)ConversionUtil.poList2dtoList(navigationPos, LaoSsNavigationDto.class);
	}
}
