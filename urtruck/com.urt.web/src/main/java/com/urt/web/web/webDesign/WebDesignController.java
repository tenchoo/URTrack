package com.urt.web.web.webDesign;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.urt.dto.LaoSsAccountDto;
import com.urt.dto.LaoWebDesignDto;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.webDesign.WebDesignService;
import com.urt.web.common.util.ActionUtil;
import com.urt.web.common.util.StringUtil;
import com.urt.web.common.util.QRCode.TwoDimensionCode;

/**
 * 类说明：主界面
 * 
 * @author xuzhipeng
 * @date 2017年8月29日15:00:00
 */
@Controller
@RequestMapping("/webDesign")
public class WebDesignController {

	Logger log = Logger.getLogger(getClass());
	
	@Autowired
	WebDesignService webDesignService;
	
	@Autowired
	UserService userService;
	
	/**
	 * 显示页面
	 */
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request, String str) {
		ModelAndView mv = new ModelAndView("/webDesign/designIndex");
		LaoSsAccountDto user = (LaoSsAccountDto) request.getSession().getAttribute("sessionUser");
		String custId = null;
		List<Map<String, Object>> designs = null;
		if(ActionUtil.ifSuperUser(request)){
			List<Map<String, Object>> lists = webDesignService.queryAllCustId();
			log.info("+++++++++++++++++++++++++++webDesignController index lists: " + lists.size());
			if(lists != null){
				mv.addObject("custIds", lists);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("begin", 1);
			map.put("end", 8);
			designs = webDesignService.queryPage(map);
			int countTmp = Integer.parseInt(webDesignService.getAllCount().toString());
			int countToT = 0;
			if(countTmp % 8 > 0){
				countToT = countTmp / 8 + 1;
			}else{
				countToT = countTmp / 8;
			}
			mv.addObject("count", countToT);
		}else if(user != null && user.getCustId() != null && !"".equals(user.getCustId())){
			custId = String.valueOf(user.getCustId());
			designs = webDesignService.selectAllByCustId(custId);
			mv.addObject("oneCustId", custId);
			mv.addObject("oneCustName", webDesignService.getCustNameByCustId(Long.parseLong(custId)).toString());
		}
		mv.addObject("designs", designs);
		return mv;
	}
	
	@RequestMapping("/detail")
	public ModelAndView detail(HttpServletRequest request, String custId, String designId) {
		ModelAndView mv = new ModelAndView("/webDesign/designDetail");
		LaoSsAccountDto user = (LaoSsAccountDto) request.getSession().getAttribute("sessionUser");
		LaoWebDesignDto webDesign = null;
		String fileName = null;
		if(StringUtil.isBlank(designId) && StringUtil.isNotBlank(custId)){
			List<Map<String, Object>> lists = webDesignService.selectAllByCustId(custId);
			if(lists != null && lists.size() > 0){
				mv.addObject("webDesign", lists.get(0));
			}else{
				webDesign = new LaoWebDesignDto();
				webDesign.setCustId(Long.parseLong(custId));
				String custName = webDesignService.getCustNameByCustId(Long.parseLong(custId));
				webDesign.setCustName(custName);
				mv.addObject("webDesign",webDesign);
			}
		}else if(StringUtil.isNotBlank(designId)){
			webDesign = webDesignService.getWebDesignDtoByDesignId(Long.parseLong(designId));
			mv.addObject("webDesign", webDesign);
			custId = webDesign.getCustId().toString();
		}else if(user != null && user.getCustId() != null && !"".equals(user.getCustId())){
			custId = user.getCustId().toString();
			List<Map<String, Object>> webDesigns = webDesignService.selectAllByCustId(custId);
			if(webDesigns != null && webDesigns.size() > 0){
				webDesign = new LaoWebDesignDto();
				designId = webDesigns.get(0).get("designId").toString();
				webDesign.setDesignId(Long.parseLong(designId));
				webDesign.setCustId(Long.parseLong(webDesigns.get(0).get("custId").toString()));
				webDesign.setColor(webDesigns.get(0).get("color").toString());
				webDesign.setImage(webDesigns.get(0).get("image").toString());
				webDesign.setStatusCode(webDesigns.get(0).get("statusCode").toString());
				webDesign.setDesignDate(webDesigns.get(0).get("designDate").toString());
				webDesign.setWebUrl(webDesigns.get(0).get("webUrl").toString());
				webDesign.setWebContent(webDesigns.get(0).get("webContent").toString());
				webDesign.setValue1(webDesigns.get(0).get("value1").toString());
				webDesign.setValue2(webDesigns.get(0).get("value2").toString());
			}
			mv.addObject("webDesign", webDesign);
		}
		if(webDesign != null && StringUtil.isNotBlank(webDesign.getValue1())){
			fileName = webDesign.getValue1();
		}else{
			fileName = custId + ".png";
		}
		String imgPath = request.getServletContext().getRealPath("/")+"/static/webDesign/img/" + fileName;
		log.info("+++++++++++++++++++++++++++webDesignController detail QRcode encode successful! imgPath: " + imgPath);
		File file = new File(imgPath);
		if(!file.exists()){
			String sign = webDesignService.getSign(custId);
			String encoderContent = "http://gla.lenovo.com/glaH5AppPay/toChargeView?custId=" + custId + "&sign=" + sign + "&logo=2";
			log.info("+++++++++++++++++++++++++++webDesignController detail encoderContent: " + encoderContent);
			TwoDimensionCode handler = new TwoDimensionCode();  
			handler.encoderQRCode(encoderContent, imgPath, "png");
			String decoderContent = handler.decoderQRCode(imgPath); 
			if(decoderContent != null){
				log.info("+++++++++++++++++++++++++++webDesignController detail QRcode encode successful! decoderContent: " + decoderContent);
			}
		}
		mv.addObject("imgPath", fileName);
		return mv;
	}
	
	@ResponseBody
	@RequestMapping("/queryPage")
	public List<Map<String, Object>> queryPage(HttpServletRequest request, String begin, String end) {
		Map<String, Object> param = new HashMap<String, Object>();
		if(!ActionUtil.ifSuperUser(request)){
			LaoSsAccountDto user = (LaoSsAccountDto) request.getSession().getAttribute("sessionUser");
			param.put("custId", user.getCustId());
		}
		param.put("begin", Integer.parseInt(begin));
		param.put("end", Integer.parseInt(end));
		List<Map<String, Object>> lists = webDesignService.queryPage(param);
		for(Map<String,Object> maps : lists){
			System.out.println("lists information: custName: " + maps.get("custName") + ",color: " + maps.get("color") + ",image: " + maps.get("image"));
			System.out.println("lists information: designDate: " + maps.get("designDate") + ",custId: " + maps.get("custId") + ",webUrl: " + maps.get("webUrl"));
		}
		return lists;
	}
	
	@ResponseBody
	@RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
	public String uploadImg(HttpServletRequest request, MultipartFile upload, String designId) {
		if(upload == null || designId == null){
			return "false";
		}
		String fileType = upload.getContentType();
		String fileName = designId + new SimpleDateFormat("MMddHHmmssSSS").format(new Date()) + "." +fileType.split("/")[1];
		byte[] bs = new byte[1024];
		String path = request.getServletContext().getRealPath("/")+"/static/webDesign/img/" + fileName;
		log.info("++++++++++++++++++++++++++++++++++++path: " + path);
		File file = new File(path);
		if(file.exists()){
			boolean b = file.delete();
		}
		int len = 0;
		InputStream is = null;
		OutputStream os = null;
		try {
			is = upload.getInputStream();
			os = new FileOutputStream(file);
			while((len = is.read(bs)) != -1){
				os.write(bs);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		} finally {
            // 完毕，关闭所有链接
            try {
                os.close();
                is.close();
                return fileName;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
		return "false";
	}
	
	@ResponseBody
	@RequestMapping("/saveDesign")
	public String saveDesign(Long designId, String custId, String color, String image, String webUrl, String webContent, String value1, String value2) {
		LaoWebDesignDto webDesign = null;
		if(designId != null && !"".equals(designId)){
			webDesign = webDesignService.getWebDesignDtoByDesignId(designId);
			if(webDesign != null){
				if(StringUtil.isNotBlank(custId)){
					String custName = webDesignService.getCustNameByCustId(Long.parseLong(custId));
					if(StringUtil.isNotBlank(custName)){
						webDesign.setCustName(custName);
					}
				}
				webDesign.setColor(color);
				webDesign.setImage(image);
				String designDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
				webDesign.setDesignDate(designDate);
				webDesign.setWebUrl(webUrl);
				webDesign.setWebContent(webContent);
				webDesign.setValue1(value1);
				webDesign.setValue2(value2);
				int a = webDesignService.updateByDesignId(webDesign);
				log.info("+++++++++++++++++++++++++++保存结果: " + a);
				return "saved";
			}
		}
		if(StringUtil.isBlank(custId)){
			return "failed";
		}
		String tmpDesignId = webDesignService.insert(Long.parseLong(custId), color, image, "1", webUrl, webContent, value1, value2);
		if(StringUtil.isNotBlank(tmpDesignId)){
			return "saved";
		}
		return "failed";
	}	
	
	@ResponseBody
	@RequestMapping("/buildQRWithLogo")
	public String buildQRWithLogo(HttpServletRequest request, MultipartFile logoFile, String custId) {
		if(logoFile != null && StringUtil.isNotBlank(custId)){
			String path = request.getServletContext().getRealPath("/") + "/static/webDesign/img/" + custId + ".png";
			String qrName = custId + new SimpleDateFormat("MMddHHmmssSSS").format(new Date()) + ".png";
			System.out.println(">>>>>>>>>>>>>>qrName: " + qrName);
			path = path.replaceAll("\\\\", "/");
			File qrFile = new File(path);
			System.out.println(">>>>>>>>>>>>>>>>path: " + path);
			if(!qrFile.exists()){
				String sign = webDesignService.getSign(custId);
				String encoderContent = "http://gla.lenovo.com/glaH5AppPay/toChargeView?custId=" + custId + "&sign=" + sign + "&logo=2";
				log.info("+++++++++++++++++++++++++++webDesignController buildQRWithLogo encoderContent: " + encoderContent);
				TwoDimensionCode handler = new TwoDimensionCode();  
				handler.encoderQRCode(encoderContent, path, "png");
				String decoderContent = handler.decoderQRCode(path); 
				if(decoderContent != null){
					log.info("+++++++++++++++++++++++++++webDesignController buildQRWithLogo QRcode encode successful! decoderContent: " + decoderContent);
				}
			}
			try {
				BufferedImage biq = ImageIO.read(qrFile);
				BufferedImage bi2 = ImageIO.read(logoFile.getInputStream());
				Graphics2D g = biq.createGraphics();
				int x = biq.getWidth() / 4;
				int y = biq.getHeight() / 4;
				int widthLogo = biq.getWidth()/2;
				int heightLogo = biq.getHeight()/2;
				g.drawImage(bi2, x, y, widthLogo, widthLogo, null);
				g.drawRoundRect(x, y, widthLogo, heightLogo, 10, 10);
				g.setColor(Color.white);
				g.drawRect(x, y, widthLogo, heightLogo);
				g.dispose();
				ImageIO.write(biq, "jpg", new File(request.getServletContext().getRealPath("/") + "/static/webDesign/img/" +qrName));
				return qrName;
			} catch (IOException e) {
				e.printStackTrace();
			}
			return "failed";
		}
		return "failed";
	}	
	
	@ResponseBody
	@RequestMapping("/downLoadQR")
	public String downLoadQR(HttpServletRequest request, String fileName, HttpServletResponse response){
		String filepath = request.getServletContext().getRealPath("/") + "/static/webDesign/img/" +fileName;
		filepath = filepath.replaceAll("\\\\", "/");
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>filePath: " + filepath);
		File file = new File(filepath);  
        InputStream inputStream = null;  
        OutputStream outputStream = null;  
        byte[] b= new byte[1024];
        int len = 0;
        try {
			inputStream = new FileInputStream(file);
			response.setContentType("application/force-download");
			outputStream = response.getOutputStream();
			response.addHeader("Content-Disposition","attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));  
			response.setContentLength((int) file.length());
            while((len = inputStream.read(b)) != -1){  
                outputStream.write(b, 0, len);  
            }
            return null;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(inputStream != null){  
                try {  
                    inputStream.close();  
                    inputStream = null;  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
            if(outputStream != null){  
                try {  
                	outputStream.flush();
                    outputStream.close();  
                    outputStream = null;  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
		}
        
		return "failed";
	}
	
}
