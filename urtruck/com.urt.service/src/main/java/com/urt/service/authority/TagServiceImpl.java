package com.urt.service.authority;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.apache.commons.io.CopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.urt.cache.RedisCacheUtil;
import com.urt.cache.RedisKey;
import com.urt.common.util.JsonUtil;
import com.urt.dto.IccidLibDto;
import com.urt.dto.LaoSsStaticDto;
import com.urt.interfaces.authority.TagService;
import com.urt.mapper.IccidLibMapper;
import com.urt.mapper.LaoSsStaticPoMapper;
import com.urt.mapper.ext.LaoSsStaticPoExtMapper;
import com.urt.mapper.ext.MyBaseMapper;
import com.urt.modules.mapper.BeanMapper;
import com.urt.po.IccidLib;
import com.urt.po.LaoSsStaticPo;
import com.google.gson.reflect.TypeToken;


@Service("tagService")
@Transactional(propagation=Propagation.REQUIRED)
public class TagServiceImpl  implements TagService {

	Logger log = Logger.getLogger(getClass());
    @Autowired
    private MyBaseMapper myBaseMapper;
    @Autowired
    private RedisCacheUtil redisCacheUtil;
    @Autowired
    private LaoSsStaticPoExtMapper staticDao;
    @Autowired
    private IccidLibMapper libDao;
    public String translateByTypeId(String typeId, String dataId) {
        String trans = "";
        if (typeId != null && !"".equals(typeId) && dataId != null && !"".equals(dataId)) {
            String sql = "SELECT * FROM CODE_LIST T WHERE T.SYS_CODE = '1' AND T.TYPE_CODE = '"+typeId+"' AND T.DATA_ID = '"+dataId+"' ORDER BY ORDER_LIST";
            trans = getTranslate(sql, dataId);
        }
        return trans;
    }

    public String translateBySql(String bindSql, String translateValue) {
        String trans = "";
        if(bindSql != null && !"".equals(bindSql) && translateValue != null && !"".equals(translateValue)){
            String sql = bindSql + " = '" + translateValue + "' AND ROWNUM = 1";
            trans = getTranslate(sql, translateValue);
        }
        return trans;
    }

    public String getTranslate(String sql, String value) {
        String trans = "";
        if(sql != null && !"".equals(sql)){
            Map params = new HashMap<>();
            params.put("sql",sql);
            List<?> list = myBaseMapper.executeSql2Str(params);
            if (list != null && list.size() > 0) {
                trans = ((String) list.get(0));
            } else {
                trans = value;
            }
        }
        return trans;
    }
    public List<Map> getOptionsByTypeId(String typeId,String data,String custId) {
    	List<Map> list = new ArrayList<Map>();
    	LaoSsStaticPo po=new LaoSsStaticPo();
    	if(typeId!=null && typeId.equals("col")){
    		po.setColName(data);
    		po.setCustId(custId);
    		/*list=staticDao.queryStaticByClo(po);*/
    	}else if(typeId.equals("code")){
    		po.setStaticCode(data);
    		po.setCustId(custId);
    		/*list=staticDao.queryStaticByCode(po);*/
    	}else if(typeId.equals("pid")){
    		po.setPid(Long.valueOf(data));
    		po.setCustId(custId);
    	}
    	list=staticDao.queryStatic(po);
    	return list;
	}
    /*public List<Map> getOptionsByTypeId(String typeId, String dataId) {
        List<Map> list = new ArrayList<Map>();
        if (typeId != null && !"".equals(typeId)) {
        	String trans="";
           	try {
    			trans=redisCacheUtil.getStr(RedisKey.SELECT_TYPE_CODE,typeId);
    			if(trans!=null&&!trans.isEmpty()&&!"[]".equals(trans)){
    				//转化为集合
    			    List<Map> rtn = JsonUtil.fromJson(trans, new TypeToken<List<Map>>(){}.getType());
    				return  rtn;
    			}else{
    			    String sql = "SELECT * FROM CODE_LIST T WHERE  T.SYS_CODE = '1' AND  T.TYPE_CODE = '"+typeId+"'AND T.REMOVE_TAG = '0' ORDER BY T.SEQ_NO";
    		        list = getOptions(sql);
    		        redisCacheUtil.save(RedisKey.SELECT_TYPE_CODE,typeId,JsonUtil.toJson(list));
    			}
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
          
        }
        return list;
    }*/

    public List<Map> getOptionsBySql(String bindSql, String sqlCond) {
        List<Map> list = new ArrayList<Map>();
        String sql = bindSql+" where 1=1 and " + sqlCond;
        list = getOptions(sql);
        return list;
    }

    public List<Map> getOptions(String sql) {
        List<Map> list = new ArrayList<Map>();
        if(sql != null && !"".equals(sql)){
        	Map params = new HashMap<>();
            params.put("sql",sql);
            list = myBaseMapper.executeSql2Map(params);
        }
        return list;
    }

	@Override
	public List<Map> queryProductTypeByCustId(String custId) {
		// TODO Auto-generated method stub
		return staticDao.queryProductTypeByCustId(custId);
	}

	@Override
	public List<Map> queryProductVersionByPid(String pid,String custId) {
		// TODO Auto-generated method stub
		LaoSsStaticPo po=new LaoSsStaticPo();
		po.setPid(Long.valueOf(pid));
		po.setCustId(custId);
		List<Map> list=staticDao.queryProductVersionByPid(po);
		List<Map> maps=new ArrayList<Map>();
		for(Map map:list){
			Map selectMap=new HashMap();
			selectMap.put("textKey", map.get("STATIC_NAME"));
			selectMap.put("valueKey", map.get("STATIC_CODE"));
			maps.add(selectMap);
		}
		return maps;
	}


	@Override
	public LaoSsStaticDto getStaticByCustIdCode(String staticCode, String custId) {
		LaoSsStaticPo queryPo=new LaoSsStaticPo();
		queryPo.setStaticCode(staticCode);
		queryPo.setCustId(custId);
		
		LaoSsStaticPo po=staticDao.getStaticByCustIdCode(queryPo);
		LaoSsStaticDto dto=new LaoSsStaticDto();
		if(po!=null){
			BeanMapper.copy(po, dto);
		}
		return dto;
	}

	@Override
	public Map getIccidLibByIccid(String id) {
		// TODO Auto-generated method stub
		Map map=staticDao.getStaticByIccid(id);
		return map;
	}

	@Override
	public List<Map> queryPoolsByCustId(String custId) {
		// TODO Auto-generated method stub
		return staticDao.queryPoolsByCustId(custId);
	}

	@Override
	public List<Map<String,Object>> queryProductTypeVAL1() {
		List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
		List<Map> list = staticDao.queryProductTypeVAL1();
		Map<String, Object> map0 = new HashMap<String, Object>();
		map0.put("text", "请选择");
		map0.put("id", -1);
		listMap.add(map0);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				@SuppressWarnings("unchecked")
				Map<String, Object> mapList = list.get(i);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("text", mapList.get("STATIC_NAME"));
				map.put("id", mapList.get("STATIC_CODE"));
				listMap.add(map);
			}
		}
		return listMap;
	}

	@Override
	public List<Map<String,Object>> queryProductVersionVAL2(String pid) {
		List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
		List<Map> list = staticDao.queryProductVersionVAL2(pid);
		Map<String, Object> map0 = new HashMap<String, Object>();
		map0.put("text", "请选择");
		map0.put("id", -1);
		listMap.add(map0);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				@SuppressWarnings("unchecked")
				Map<String, Object> mapList = list.get(i);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("text", mapList.get("STATIC_NAME"));
				map.put("id", mapList.get("STATIC_CODE"));
				listMap.add(map);
			}
		}
		return listMap;
	}

	@Override
	public List<Map<String,Object>> queryProductTypeVAL1TSP(String custId) {
		List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
		List<Map> list = staticDao.queryProductTypeVAL1TSP(custId);
		Map<String, Object> map0 = new HashMap<String, Object>();
		map0.put("text", "请选择");
		map0.put("id", -1);
		listMap.add(map0);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> mapList = list.get(i);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("text", mapList.get("STATIC_NAME"));
				map.put("id", mapList.get("STATIC_CODE"));
				listMap.add(map);
			}
		}
		return listMap;
	}
	@Override
	public LaoSsStaticDto getStaticByCustIdCodeTSP(String staticCode, String custId) {
		LaoSsStaticPo queryPo=new LaoSsStaticPo();
		queryPo.setStaticCode(staticCode);
		queryPo.setCustId(custId);
		
		LaoSsStaticPo po=staticDao.getStaticByCustIdCodeTSP(queryPo);
		LaoSsStaticDto dto=new LaoSsStaticDto();
		if(po!=null){
			BeanMapper.copy(po, dto);
		}
		return dto;
	}
	
	
}
