package com.urt.web.common.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Created by root on 16-5-20.
 *
 *
 */

public class JsonConverter {

   /* public static List<Device> jsonToList(Object json){
        JSONArray jsonArray=JSONArray.fromObject(json);
        List<Device> deviceList=new ArrayList<Device>();
        Device device=null;
        JSONObject jsonObject=null;
        if(jsonArray.isArray()){
            int size=jsonArray.size();
            for(int i=0;i<size;i++){
                jsonObject=JSONObject.fromObject(jsonArray.get(i));
                if(jsonObject!=null){
                    device= (Device) JSONObject.toBean(jsonObject,Device.class);
                    deviceList.add(device);
                }
            }
        }
        return deviceList;
    }*/

    public static Object[] jsonToArray(Object json){
        JSONArray jsonArray=JSONArray.fromObject(json);
        Object[] iccidArray=null;
        JSONObject jsonObject=null;
        if(jsonArray.isArray()){
            int size=jsonArray.size();
            iccidArray=new Object[size];

            for(int i=0;i<size;i++){
                jsonObject=JSONObject.fromObject(jsonArray.get(i));
                iccidArray[i]=jsonObject.get("iccid");
            }

        }
        return iccidArray;
    }

}
