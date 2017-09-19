package com.urt.service.dmp;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.urt.cache.DMPCacheUtil;
import com.urt.common.util.ConversionUtil;
import com.urt.dto.dmp.LaoDMPGroupDto;
import com.urt.dto.dmp.LaoDMPOperationDto;
import com.urt.dto.dmp.LaoDMPStrategyDto;
import com.urt.dto.dmp.LaoDMPStrategyEditDto;
import com.urt.interfaces.dmp.DMPStrategyEditService;
import com.urt.mapper.LaoDMPSchemeGroupPoMapper;
import com.urt.mapper.LaoDMPSchemeStrategyPoMapper;
import com.urt.mapper.LaoDMPStrategyEditPoMapper;
import com.urt.mapper.LaoDMPStrategyOperationPoMapper;
import com.urt.mapper.LaoDMPStrategyRelationPoMapper;
import com.urt.mapper.ext.LaoDMPGroupPoExtMapper;
import com.urt.mapper.ext.LaoDMPOperationPoExtMapper;
import com.urt.mapper.ext.LaoDMPSchemeGroupPoExtMapper;
import com.urt.mapper.ext.LaoDMPStrategyEditPoExtMapper;
import com.urt.mapper.ext.LaoDMPStrategyPoExtMapper;
import com.urt.mapper.ext.LaoDMPStrategyRelationPoExtMapper;
import com.urt.miniService.dmp.MiniDMPStrategyEditServiceImpl;
import com.urt.po.LaoDMPGroupPo;
import com.urt.po.LaoDMPOperationPo;
import com.urt.po.LaoDMPSchemeGroupPo;
import com.urt.po.LaoDMPStrategyEditPo;
import com.urt.po.LaoDMPStrategyPo;
import com.urt.po.LaoDMPStrategyRelationPo;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;


@Service("dmpStrategyEditService")
@Transactional(propagation = Propagation.REQUIRED)
public class DMPStrategyEditServiceImpl implements DMPStrategyEditService {
	
	Logger log = Logger.getLogger(DMPStrategyEditServiceImpl.class);
	@Autowired
	private DMPCacheUtil dmpCacheUtil;
	@Autowired
	MiniDMPStrategyEditServiceImpl miniDMPStrategyEditServiceImpl;
	@Autowired
	LaoDMPStrategyEditPoExtMapper schemeExtMapper;
	@Autowired
	LaoDMPStrategyEditPoMapper schemeMapper;
	@Autowired
	LaoDMPGroupPoExtMapper groupPoExtMapper;
	@Autowired
	LaoDMPStrategyPoExtMapper strategyPoExtMapper;
	@Autowired
	LaoDMPOperationPoExtMapper operationPoExtMapper;
	@Autowired
	LaoDMPSchemeGroupPoMapper schemeGroupMapper;
	@Autowired
	LaoDMPSchemeStrategyPoMapper schemeStrategyMapper;
	@Autowired
	LaoDMPStrategyOperationPoMapper strategyOperationMapper;
	@Autowired
	LaoDMPStrategyRelationPoMapper relationMapper;
	@Autowired
	LaoDMPStrategyRelationPoExtMapper relationExtMapper;
	@Autowired
	LaoDMPSchemeGroupPoExtMapper groupSchemeExtMapper;
	@Override
	public Map<String, Object> queryPage(LaoDMPStrategyEditDto schemeDto,int pageNo, int pageSize) {
		Map<String, Object> map = miniDMPStrategyEditServiceImpl.queryPage(schemeDto, pageNo,pageSize);
		return map;
	}

	@Override
	public int delStrategyEdit(Long id) {
		
		return schemeExtMapper.delStrategyEdit(id);
	}

	@Override
	public int blockUp(Long id) {
		return schemeExtMapper.blockUp(id);
	}

	@Override
	public int startUsing(Long id) {
		return schemeExtMapper.startUsing(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LaoDMPGroupDto> getGroupsByCustId(Long custId) {
		List<LaoDMPGroupPo> groupPos=groupPoExtMapper.getGroupsByCustId(custId);
		return ConversionUtil.poList2dtoList(groupPos, LaoDMPGroupDto.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LaoDMPStrategyDto> getStrategys() {
		List<LaoDMPStrategyPo> strategyPos=strategyPoExtMapper.getStrategys();
		return ConversionUtil.poList2dtoList(strategyPos, LaoDMPStrategyDto.class);
	}

	@Override
	public List<LaoDMPOperationDto> getOperations() {
		List<LaoDMPOperationPo> operationPos=operationPoExtMapper.getOperations();
		return ConversionUtil.poList2dtoList(operationPos, LaoDMPOperationDto.class);
	}

	@Override
	public boolean saveScheme(LaoDMPStrategyEditDto schemeDto) {
		Gson gson=new Gson();
		Long custId = schemeDto.getChannelcustId();
		String groupIds = schemeDto.getGroupIds();
		String strategyJson = schemeDto.getStrategyJson();
		LaoDMPStrategyEditPo schemePo = (LaoDMPStrategyEditPo) ConversionUtil.dto2po(schemeDto, LaoDMPStrategyEditPo.class);
		Long schemeId=schemePo.getId();
		if(schemeId==null){
			schemeId = Long.parseLong(ZkGenerateSeq.getIdSeq(SeqID.DMPSCHEME_ID));
			schemePo.setId(schemeId);
			log.info("保存用户策略");
			schemeMapper.insertSelective(schemePo);
		}else{
			groupSchemeExtMapper.deleteBySchemeId(schemeId);
			relationExtMapper.deleteBySchemeId(schemeId);
			schemeMapper.updateByPrimaryKeySelective(schemePo);
		}
		try {
			dmpCacheUtil.hset("schemes_"+custId, String.valueOf(schemeId), gson.toJson(schemePo));
			if(groupIds!=null&&!"".equals(groupIds.trim())){
				log.info("保存scheme和组的关联关系");
				String[] groupIdArr = groupIds.split(",");
				for(String groupId:groupIdArr){
					LaoDMPSchemeGroupPo schemeGroupPo=new LaoDMPSchemeGroupPo();
					schemeGroupPo.setSchemeId(schemeId);
					schemeGroupPo.setGroupId(Long.parseLong(groupId));
					schemeGroupPo.setDelflag("1");
					schemeGroupMapper.insertSelective(schemeGroupPo);
//					dmpCacheUtil.hset("schemeIdsofgroup_"+groupId, String.valueOf(schemeId),gson.toJson(schemePo));
				}
			}
			log.info("保存策略操作关联关系");
			 LaoDMPStrategyRelationPo strategyRelation=new LaoDMPStrategyRelationPo();
	         strategyRelation.setCustId(schemeDto.getChannelcustId());
	         strategyRelation.setSchemeId(schemeId);
	         strategyRelation.setDelflag("1");
		    JSONObject jsonObject =JSONObject.fromObject(strategyJson); 
		    Iterator<String> iterator = jsonObject.keys();
		    while(iterator.hasNext()){
		          String strategyId = iterator.next();
		          LaoDMPStrategyPo strategyPo = strategyPoExtMapper.selectById(Long.parseLong(strategyId));
		          dmpCacheUtil.hset("strategiesofscheme_"+schemeId, strategyId, gson.toJson(strategyPo));    
		          String value = jsonObject.getString(strategyId);
		          JSONArray array = JSONArray.fromObject(value);
		          ArrayList<String> operationIds = (ArrayList<String>) array.toList(array);
		          strategyRelation.setStrategyId(Long.parseLong(strategyId));
		          if(operationIds!=null&&operationIds.size()>0){
		        	  for(String operationId:operationIds){
		        		  strategyRelation.setOperationId(Long.parseLong(operationId));
		        		  relationMapper.insertSelective(strategyRelation);
		        		  LaoDMPOperationPo operationPo = operationPoExtMapper.selectById(Long.parseLong(operationId));
		        		  dmpCacheUtil.hset("operationsofscheme_"+schemeId, strategyId+"_"+operationId, gson.toJson(operationPo));
//		        		  dmpCacheUtil.lpush("relation_"+custId, gson.toJson(strategyRelation));
		        	  }
		          }
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}
	@Override
	public boolean delStrategyRelative(Long custId,Long id) {
		boolean result=true;
		try {
			dmpCacheUtil.hdel("schemes_"+custId, String.valueOf(id));
			dmpCacheUtil.del("strategiesofscheme_"+id); 
			dmpCacheUtil.del("operationsofscheme_"+id);
			groupSchemeExtMapper.deleteBySchemeId(id);
			relationExtMapper.deleteBySchemeId(id);
			schemeExtMapper.delStrategyEdit(id);
		} catch (Exception e) {
			result=false;			
			e.printStackTrace();
		}
		return result;
	}
	@Override
	public boolean stopScheme(Long custId, Long id) {
		boolean result=true;
		try {
			dmpCacheUtil.hdel("schemes_"+custId, String.valueOf(id));
			dmpCacheUtil.del("strategiesofscheme_"+id); 
			dmpCacheUtil.del("operationsofscheme_"+id);
			schemeExtMapper.blockUp(id);
		} catch (Exception e) {
			result=false;
			e.printStackTrace();
		}
		return result;
	}
	@Override
	public LaoDMPStrategyEditDto getSchemeDetail(Long schemeId,Long custId) {
		Gson gson=new Gson();
		LaoDMPStrategyEditPo schemePo=null;
		try {
//			String schemeStr = dmpCacheUtil.hget("schemes_"+custId, String.valueOf(schemeId));
//			if(StringUtils.isEmpty(schemeStr)){
//				schemePo = schemeExtMapper.selectByPrimaryKey(schemeId);
//				dmpCacheUtil.hset("schemes_"+custId, String.valueOf(schemePo.getId()), gson.toJson(schemePo));
//			}else{
//				schemePo=gson.fromJson(schemeStr, LaoDMPStrategyEditPo.class);
//			}
			schemePo = schemeExtMapper.selectByPrimaryKey(schemeId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (LaoDMPStrategyEditDto) ConversionUtil.po2dto(schemePo, LaoDMPStrategyEditDto.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LaoDMPGroupDto> getGroupsByScheme(Long id) {
		List<LaoDMPGroupPo> pos=groupSchemeExtMapper.getGroupsByScheme(id);
		return ConversionUtil.poList2dtoList(pos, LaoDMPGroupDto.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LaoDMPStrategyDto> getStrategis(Long id) {
		List<LaoDMPStrategyPo> pos=strategyPoExtMapper.getStrategis(id);
		return ConversionUtil.poList2dtoList(pos, LaoDMPStrategyDto.class);
	}
    /**
     * 
     * @param id  为schemeId
     * @param strategyId
     * @return
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<LaoDMPOperationDto> getOperations(Long id,Long strategyId) {
		List<LaoDMPOperationPo> operationPos=operationPoExtMapper.getOperationsBystategy(id,strategyId);
		return  ConversionUtil.poList2dtoList(operationPos, LaoDMPOperationDto.class);
	}

}
