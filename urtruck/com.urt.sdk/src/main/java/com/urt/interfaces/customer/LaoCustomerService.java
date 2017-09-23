package com.urt.interfaces.customer;

import java.util.List;
import java.util.Map;

import com.urt.dto.LaoCustomerDto;
import com.urt.dto.custcentre.LaoCustomerAccountDto;

public interface LaoCustomerService {
	
	public Map<String, Object> queryPage(LaoCustomerDto dto,Integer pageNo,Integer pageSize);
	
	public Map<String, Object> queryPerPage(LaoCustomerDto dto,Integer pageNo,Integer pageSize);
	public Map<String, Object> feeQueryPageList(LaoCustomerDto dto,Integer pageNo, Integer pageSize);
	
	int save(LaoCustomerDto dto);
	
	long initCustInfo(LaoCustomerDto dto);
	
	int update(LaoCustomerDto dto);
	
	LaoCustomerDto selectDtoById(Long custId);
	
	List<LaoCustomerDto> queryAgent();

	public Long selectDepositMoney(Long custId);
	
	List<Map<String, Object>> selectCustCardInfo(Long channelCustId);
	
	List<Map<String, Object>> selectCategoryCount(Long channelCustId);

	Map<String, Object> selectGoodsCorporate(Long channelCustId);

	Map<String, Object> selectCorporate(Long channelCustId);

	public List<LaoCustomerDto> queryChannelCust(String sellType);

	public boolean saveStyle(String welcomeLanguage, String style, String pic,Long custId,String custName);

	public boolean saveAccount(LaoCustomerAccountDto accountDto);
	
	/**
	 * ��ѯ�ͻ��¼��ͻ��б?
	 * �¼��ͻ�ָ�ÿͻ������ϼ��ͻ���parentId����չ���Ŀͻ���
	 * 
	 * @param custId �ͻ���ʶ
	 * @param includeSelf �����б����Ƿ�����?true�����?false��������
	 * @return �¼��ͻ�����չ�ͻ����б�
	 */
	public List<LaoCustomerDto> queryChildCustList(Long custId, boolean includeSelf);

	Map<String, Object> feeQueryPage(LaoCustomerDto dto, Integer pageNo, Integer pageSize);

	}
