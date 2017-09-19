package com.urt.service.laoIccidassignbatch;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.urt.common.util.ConversionUtil;
import com.urt.common.util.Page;
import com.urt.dto.IccidBatchdataDto;
import com.urt.dto.IccidLibDto;
import com.urt.dto.LaoIccidAssignBatchDto;
import com.urt.dto.LaoIccidAssignLibDto;
import com.urt.interfaces.laoIccidassignbatch.LaoIccidAssignBatchService;
import com.urt.mapper.ext.IccidLibExtMapper;
import com.urt.mapper.ext.LaoIccidAssignBatchExtMapper;
import com.urt.mapper.ext.LaoIccidAssignLibExtMapper;
import com.urt.modules.mapper.BeanMapper;
import com.urt.po.IccidLib;
import com.urt.po.LaoIccidAssignBatch;
import com.urt.po.LaoIccidAssignLibPo;
import com.urt.utils.SeqID;
import com.urt.utils.ZkGenerateSeq;

@Service("laoIccidAssignBatchService")
@Transactional(propagation = Propagation.REQUIRED)
public class LaoIccidAssignBatchServiceImpl implements LaoIccidAssignBatchService {

	@Autowired
	private LaoIccidAssignBatchExtMapper laoIccidAssignBatchExtMapper;
	@Autowired
	private IccidLibExtMapper iccidLibExtMapper;
	@Autowired
	private IccidLibExtMapper iccidLibExtDAO;
	@Autowired
	private LaoIccidAssignLibExtMapper laoIccidAssignLibExtMapper;
	
	@Override
	public Map<String, Object> selectByPage(LaoIccidAssignBatchDto dto, int pageNo, int pageSize) {
		Page<LaoIccidAssignBatch> deviceRelExtpage = new Page<LaoIccidAssignBatch>();
		deviceRelExtpage.setPageNo(pageNo);
		deviceRelExtpage.setPageSize(pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("param", (LaoIccidAssignBatch) ConversionUtil.dto2po(dto, LaoIccidAssignBatch.class));
		deviceRelExtpage.setParams(params);
		List<LaoIccidAssignBatch> deviceRelList = laoIccidAssignBatchExtMapper.selectByPage(deviceRelExtpage);
		@SuppressWarnings("unchecked")
		List<IccidBatchdataDto> deviceRelDtoList = ConversionUtil.poList2dtoList(deviceRelList,
				LaoIccidAssignBatchDto.class);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data", deviceRelDtoList);
		resultMap.put("iTotalRecords", pageSize);// 当前页包含的记录数
		resultMap.put("iTotalDisplayRecords", deviceRelExtpage.getTotalRecord());// 总记录数
		return resultMap;
	}

	@Override
	public LaoIccidAssignBatchDto selectByBatchId(Long batchId) {
		LaoIccidAssignBatchDto dto = null;
		LaoIccidAssignBatch po = laoIccidAssignBatchExtMapper.selectByPrimaryKey(batchId);
		if (po != null) {
			dto = new LaoIccidAssignBatchDto();
			BeanMapper.copy(po, dto);
		}
		return dto;
	}

	@Override
	public Map<String, String> selectOneDetailByBatchId(String batchId) {
		return iccidLibExtMapper.selectOneDetailByBatchId(batchId);
	}
	
	@Override
	public int saveBatchAssign(LaoIccidAssignBatchDto dto) {
		// TODO Auto-generated method stub
		LaoIccidAssignBatch po = new LaoIccidAssignBatch();
		BeanMapper.copy(dto, po);
		return laoIccidAssignBatchExtMapper.insertSelective(po);
	}

	@Override
	public List<Map<String, Object>> selectDetailByBatchId(String batchId) {
		return iccidLibExtDAO.selectDetailByBatchId(batchId);
	}

	@Override
	public int insertAssignCard(LaoIccidAssignBatchDto dto) {
		long batchId = Long.parseLong(ZkGenerateSeq.getIdSeq(SeqID.BATCHID));
		dto.setBatchId(batchId);
		dto.setSumNum(Long.valueOf(dto.getIccidStrArry().length));
		dto.setRecvTime(new Timestamp(new Date().getTime()));
		
		List<String> list = Arrays.asList(dto.getIccidStrArry());
		List<IccidLib> iccidLibrs = iccidLibExtDAO.selectByListIccid(list);
		for(IccidLib iccidLib:iccidLibrs){
			iccidLib.setBatchId(String.valueOf(batchId));
		}
		@SuppressWarnings("unchecked")
		List<LaoIccidAssignLibPo> iccids = ConversionUtil.dtoList2poList(iccidLibrs, LaoIccidAssignLibPo.class);
		laoIccidAssignLibExtMapper.insertSelective(iccids);
		
		LaoIccidAssignBatch po =(LaoIccidAssignBatch) ConversionUtil.dto2po(dto, LaoIccidAssignBatch.class);
		
		int i = laoIccidAssignBatchExtMapper.insertAssignCard(po);
		
		return i;
	}

	@Override
	public int toAssignCard(LaoIccidAssignLibDto dto) {
		//查询划拨明细表中iccid
		List<String> iccids = laoIccidAssignLibExtMapper.selectIccidsByBatchId(dto.getBatchId());
		
		IccidLib po =(IccidLib) ConversionUtil.dto2po(dto, IccidLib.class);
		
		return 0;
	}

	@Override
	public Map<String, Object> selectDetailByPage(LaoIccidAssignLibDto dto, int pageNo, int pageSize) {
		 Page<LaoIccidAssignLibPo> deviceRelExtpage = new Page<LaoIccidAssignLibPo>();
			deviceRelExtpage.setPageNo(pageNo);
			deviceRelExtpage.setPageSize(pageSize);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("param", (LaoIccidAssignLibPo) ConversionUtil.dto2po(dto, LaoIccidAssignLibPo.class));
			deviceRelExtpage.setParams(params);
			List<LaoIccidAssignLibPo> deviceRelList = laoIccidAssignLibExtMapper.selectDetailByPage(deviceRelExtpage);
			@SuppressWarnings("unchecked")
			List<IccidLibDto> deviceRelDtoList = ConversionUtil.poList2dtoList(deviceRelList, LaoIccidAssignLibDto.class);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("data", deviceRelDtoList);
			resultMap.put("iTotalRecords", pageSize);//当前页包含的记录数
			resultMap.put("iTotalDisplayRecords", deviceRelExtpage.getTotalRecord());//总记录数 
			return resultMap;
	}
}
