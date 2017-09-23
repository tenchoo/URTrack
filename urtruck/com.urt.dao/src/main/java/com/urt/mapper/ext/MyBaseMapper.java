package com.urt.mapper.ext;




import java.util.List;
import java.util.Map;

/**
* @描述
* @author 
* @date 2015-05-11 16:26:27
*/
public interface MyBaseMapper {

    public List<Map> executeSql2Map(Map params);

    public List<String> executeSql2Str(Map params);

    public void executeSql(Map<String, String> params);


}
