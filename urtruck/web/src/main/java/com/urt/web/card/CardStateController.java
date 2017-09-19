package com.urt.web.card;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.urt.common.enumeration.ConstantEnum;
import com.urt.dto.IccidLibDto;
import com.urt.dto.LaoBatchDataDto;
import com.urt.dto.LaoCustGroupDto;
import com.urt.dto.LaoSsAccountDto;
import com.urt.interfaces.User.UserService;
import com.urt.interfaces.batch.BatchService;
import com.urt.interfaces.cardOper.CardOperService;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;
import com.urt.web.common.util.ActionUtil;
import com.urt.web.common.util.ImportExcelUtils;


@Controller
@RequestMapping(value = "/cardState")
public class CardStateController {
    private static Logger logger = LoggerFactory.getLogger(CardStateController.class);

    @Autowired
    private CardOperService cardOperService;
    @Autowired
    private UserService userService;
    @Autowired
    private BatchService batchService;

    // 跳转到单卡操作页面
    @RequestMapping("/testChgNormal")
    public ModelAndView singleCardOperation(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("cardState/testChgNormal");
        LaoSsAccountDto user = (LaoSsAccountDto) request.getSession().getAttribute("sessionUser");
        mv.addObject("custId", user.getCustId());
        return mv;
    }

    // 单卡测试期转正常期方法 singleStateChg
    @ResponseBody
    @RequestMapping(value = "/singleTestState")
    public int singleTestState(HttpServletRequest request, String iccId, String msisdn, Long custId)
            throws IOException {
        // 根据ICCID或者MSSION更新卡所处的时期
        try {
            LaoSsAccountDto user = (LaoSsAccountDto) request.getSession().getAttribute("sessionUser");
            logger.info("iccId:" + iccId + " msisonId:" + msisdn + " custId:" + custId + " AcconutId:"
                    + user.getAcconutId());
            if (userService.userTestCycle(iccId,msisdn) < 0)
                return -1;
            else
                return 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;

    }
    // 单卡测试期转正常期方法 singleStateChg
    @ResponseBody
    @RequestMapping(value = "/mutilTestState")
    public int mutilTestState(HttpServletRequest request, String iccId, String msisdn, Long custId)
            throws IOException {
        int fail = 0;
        // 根据ICCID或者MSSION更新卡所处的时期
        try {           
            LaoSsAccountDto user = (LaoSsAccountDto) request.getSession().getAttribute("sessionUser");
            String[] iccIds = request.getParameter("iccId").split(";");
            String[] msisonIds = request.getParameter("msisdn").split(";");
            /*
             * if(!ActionUtil.ifSuperUser(request)){ }
             */
            if(iccIds.length!=msisonIds.length){
                return iccIds.length;
            }
            int length=iccIds.length;
            for(int i=0;i<length;i++){
                logger.info("iccId:" + iccId + " msisdn:" + msisdn + " custId:" + custId + " AcconutId:"
                        + user.getAcconutId());
                if (userService.userTestCycle(iccIds[i],msisonIds[i]) < 0)
                    fail+=1;          
            }
            LaoBatchDataDto dto=new LaoBatchDataDto();
            Long id = Long.valueOf(ZkGenerateSeq.getIdSeq(SeqID.SYSUSER_ID));
            dto.setBatchId(id);
            dto.setRecvTime(new Date());
            dto.setSumNum(Long.valueOf(length));
            dto.setFailNum(Long.valueOf(fail));
            dto.setSuccNum(Long.valueOf(length)-Long.valueOf(fail));
            dto.setOperId(String.valueOf(user.getAcconutId()));
            batchService.saveBatchData(dto); 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fail;
    }
    // 单卡测试期转正常期方法 singleStateChg
    @ResponseBody
    @RequestMapping(value = "/singleStateChg")
    public int singleStateChg(HttpServletRequest request, String iccId, String msisdn, String psptId,
            String psptTypeCode, Long chCustId) throws IOException {
        // 根据ICCID或者MSSION更新卡所处的时期
        try {
            LaoSsAccountDto user = (LaoSsAccountDto) request.getSession().getAttribute("sessionUser");
            logger.info("iccId:" + iccId + " msisonId:" + msisdn + " psptId:" + psptId + " psptTypeCode:"
                    + psptTypeCode + " AcconutId:" + user.getAcconutId());
            return cardOperService.singleStateChg(iccId, msisdn, psptId, psptTypeCode, chCustId, user.getAcconutId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;

    }

    // 批量卡测试期转正常期方法 multiStateChg
    @RequestMapping(value = "/multiStateChg")
    public int multiStateChg(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request)
            throws IOException {

        try {
            LaoSsAccountDto user = (LaoSsAccountDto) request.getSession().getAttribute("sessionUser");
            Map<String, List<String>> tMap = null;
            Long beginTime = System.currentTimeMillis();
            logger.info(".........CardStateController.multiStateChg:.......批量导入开始...................time:" + beginTime);
            // 判断上传文件，如果不为空，将之转换成对象
            if (!file.isEmpty()) {
                ImportExcelUtils<IccidLibDto> utils = new ImportExcelUtils<IccidLibDto>();
                if (file.getOriginalFilename().endsWith("xlsx")) {
                    tMap = utils.readInfoContent(file.getInputStream(), true);
                } else {
                    tMap = utils.readInfoContent(file.getInputStream(), false);
                }
                // 将对象插入数据库
                if (tMap != null) {
                    logger.info("**********CardStateController.multiStateChg: tMap.size:" + tMap.size());
                    cardOperService.multiStateChg(tMap, user.getAcconutId());
                    return 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

}
