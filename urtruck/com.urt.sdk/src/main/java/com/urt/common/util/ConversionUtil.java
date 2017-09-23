package com.urt.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;

import com.urt.common.util.Page;

public class ConversionUtil {
	public ConversionUtil() {

	}

	public static Object conversion(Object from, Object to) {
		if (null == from)
			return null;
		try {
			BeanUtils.copyProperties(to, from);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return to;
	}

	public static Object map2po(Map map, Class clazz) {
		try {
			Class classType = Class.forName(clazz.getName());
			Object po = classType.newInstance();
			return conversion(map, po);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object map2dto(Map map, Class clazz) {
		try {
			Class classType = Class.forName(clazz.getName());
			Object dto = classType.newInstance();
			return conversion(map, dto);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object dto2po(Object dto, Class clazz) {
		try {
			Class classType = Class.forName(clazz.getName());
			Object po = classType.newInstance();
			return conversion(dto, po);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object po2dto(Object po, Class clazz) {
		try {
			Class classType = Class.forName(clazz.getName());
			Object dto = classType.newInstance();
			return conversion(po, dto);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object vo2dto(Object vo, Class clazz) {
		try {
			Class classType = Class.forName(clazz.getName());
			Object dto = classType.newInstance();
			return conversion(vo, dto);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object dto2vo(Object dto, Class clazz) {
		try {
			Class classType = Class.forName(clazz.getName());
			Object vo = classType.newInstance();
			return conversion(dto, vo);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List poList2dtoList(List poList, Class clazz) {
		List dtoList = new ArrayList();
		if (CollectionUtils.isEmpty(poList))
			return dtoList;
		Iterator i$ = poList.iterator();
		do {
			if (!i$.hasNext())
				break;
			Object po = i$.next();
			Object dto = po2dto(po, clazz);
			if (null != dto)
				dtoList.add(dto);
		} while (true);
		return dtoList;
	}

	public static List dtoList2poList(List dtoList, Class clazz) {
		List poList = new ArrayList();
		if (CollectionUtils.isEmpty(dtoList))
			return poList;
		Iterator i$ = dtoList.iterator();
		do {
			if (!i$.hasNext())
				break;
			Object dto = i$.next();
			Object po = dto2po(dto, clazz);
			if (null != po)
				poList.add(po);
		} while (true);
		return poList;
	}

	public static List mapList2poList(List mapList, Class clazz) {
		List poList = new ArrayList();
		if (CollectionUtils.isEmpty(mapList))
			return poList;
		Iterator i$ = mapList.iterator();
		do {
			if (!i$.hasNext())
				break;
			Map map = (Map) i$.next();
			Object po = map2po(map, clazz);
			if (null != po)
				poList.add(po);
		} while (true);
		return poList;
	}

	public static List mapList2dtoList(List mapList, Class clazz) {
		List dtoList = new ArrayList();
		if (CollectionUtils.isEmpty(mapList))
			return dtoList;
		Iterator i$ = mapList.iterator();
		do {
			if (!i$.hasNext())
				break;
			Map map = (Map) i$.next();
			Object dto = map2dto(map, clazz);
			if (null != dto)
				dtoList.add(dto);
		} while (true);
		return dtoList;
	}

	public static List voList2dtoList(List voList, Class clazz) {
		List dtoList = new ArrayList();
		if (CollectionUtils.isEmpty(voList))
			return dtoList;
		Iterator i$ = voList.iterator();
		do {
			if (!i$.hasNext())
				break;
			Object vo = i$.next();
			Object dto = vo2dto(vo, clazz);
			if (null != dto)
				dtoList.add(dto);
		} while (true);
		return dtoList;
	}

	public static List dtoList2voList(List dtoList, Class clazz) {
		List voList = new ArrayList();
		if (CollectionUtils.isEmpty(dtoList))
			return voList;
		Iterator i$ = dtoList.iterator();
		do {
			if (!i$.hasNext())
				break;
			Object dto = i$.next();
			Object vo = dto2vo(dto, clazz);
			if (null != vo)
				voList.add(vo);
		} while (true);
		return voList;
	}
    public static Page dtoPage2poPage(Page dtoPage, Class clazz)
    {
        Page poPage = (Page)conversion(dtoPage, new Page());
        if(CollectionUtils.isNotEmpty(dtoPage.getList()))
            poPage.setList(dtoList2poList(dtoPage.getList(), clazz));
        if(!dtoPage.getParams().isEmpty()){
        	Iterator i=dtoPage.getParams().entrySet().iterator();
        	Map map = new HashMap();
	        while(i.hasNext()){//只遍历一次,速度快
		        Map.Entry e=(Map.Entry)i.next();
		        map.put(e.getKey(), dto2po(e.getValue(), clazz));
	        }
            poPage.setParams(map);
        }
        return poPage;
    }

    public static Page poPage2dtoPage(Page poPage, Class clazz)
    {
        Page dtoPage = (Page)conversion(poPage, new Page());
        if(CollectionUtils.isNotEmpty(poPage.getList()))
            dtoPage.setList(poList2dtoList(poPage.getList(), clazz));
        if(!dtoPage.getParams().isEmpty()){
        	Iterator i=dtoPage.getParams().entrySet().iterator();
        	Map map = new HashMap();
	        while(i.hasNext()){//只遍历一次,速度快
		        Map.Entry e=(Map.Entry)i.next();
		        map.put(e.getKey(), po2dto(e.getValue(), clazz));
	        }
            poPage.setParams(map);
        }
        return dtoPage;
    }
}
