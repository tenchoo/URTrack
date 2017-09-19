package com.urt.service.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.LaoSsResourceDto;

/**
* 类说明：与页面进行交互的工具类
* @author cuichao
* @date 2016年5月13日 下午1:43:04
*/
public class ActionUtil {

	public static void PrintWriter(String message, HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter printWriter = null;
        try {
            printWriter = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        printWriter.print(message);
        printWriter.close();
    }
	
	
	public static boolean ifSuperUser( HttpServletRequest request){
		LaoSsAccountDto user=(LaoSsAccountDto)request.getSession().getAttribute("sessionUser");
		if(user != null) {
			if(user.getResources() != null && user.getResources().size()> 0){
				for (LaoSsResourceDto laoSsResourceDto : user.getResources()) {
					if(("1").equals(laoSsResourceDto.getResCode())){
						return true;
					}
				}
			}
		}
		return false;
	}
	public static boolean checkResource(LaoSsAccountDto user,String code){
		/*LaoSsAccountDto user=(LaoSsAccountDto)request.getSession().getAttribute("sessionUser");*/
		if(user != null) {
			if(user.getResources() != null && user.getResources().size()> 0){
				for (LaoSsResourceDto laoSsResourceDto : user.getResources()) {
					if(code.equals(laoSsResourceDto.getResCode())){
						return true;
					}
				}
			}
		}
		return false;
	}
}
