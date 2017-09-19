package com.urt.mapper.ext;

import java.util.List;

import com.urt.po.LaoDownloadfileConfig;

public interface LaoDownloadfileConfigExtMapper {
  
	public List<LaoDownloadfileConfig> selectListFile();

	public List<LaoDownloadfileConfig> selectListFileByofo();
}
