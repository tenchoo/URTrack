package com.urt.web.web.handlePic;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.urt.dto.handlePic.LaoPicturesDto;
import com.urt.interfaces.handlePic.HandlePicService;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;

@Controller
@RequestMapping("/picController")
public class HandlePicController {
	
	@Autowired
	private HandlePicService handlePicService;
	
	//上传图片
	@RequestMapping(value = "/uploadPic")
	public void uploadPic(MultipartFile handpic,HttpServletResponse response)throws Exception{
		//扩展名
		String ext ="";
		byte[] betys={};
		LaoPicturesDto dto = new LaoPicturesDto();
		if(handpic!=null){
			ext=FilenameUtils.getExtension(handpic.getOriginalFilename());	
			betys=handpic.getBytes();
			dto.setPicName(handpic.getOriginalFilename());
			dto.setRemark(ext);
			dto.setPicId(Long.parseLong(ZkGenerateSeq.getIdSeq(SeqID.PICTURE_ID)));
			dto.setPicContent(betys);
			handlePicService.insert(dto);
		}
		
	}
}
