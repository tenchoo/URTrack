package com.urt.service.report;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.urt.common.util.ConversionUtil;
import com.urt.dto.report.LaoReptInfoDto;
import com.urt.interfaces.report.ReportFormService;
import com.urt.mapper.ext.LaoReptInfoPoExtMapper;
import com.urt.po.LaoReptInfoPo;


@Service("reportFormService")
@Transactional(propagation = Propagation.REQUIRED)
public class ReportFormServiceImpl implements ReportFormService {
	
	@Autowired
	LaoReptInfoPoExtMapper laoReptInfoPoExtDao;

	@Override
	public LaoReptInfoDto selectByTradeTypeCode(Long reptId) {
		LaoReptInfoPo po = laoReptInfoPoExtDao.selectByTradeTypeCode(reptId);
		return (LaoReptInfoDto) ConversionUtil.po2dto(po, LaoReptInfoDto.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LaoReptInfoDto> selectCodeAndName() {
		List<LaoReptInfoPo> listPo = laoReptInfoPoExtDao.selectCodeAndName();
		return ConversionUtil.poList2dtoList(listPo, LaoReptInfoDto.class);
	}

	@Override
	public LaoReptInfoDto selectByType(int typeCode) {
		LaoReptInfoPo po = laoReptInfoPoExtDao.selectByType(typeCode);
		return (LaoReptInfoDto) ConversionUtil.po2dto(po, LaoReptInfoDto.class);
	}

	
}
