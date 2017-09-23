package com.urt.mapper.ext;

import java.util.List;

import com.urt.po.LaoSmsDeliver;

public interface LaoSmsDeliverExtMapper {
    LaoSmsDeliver selectBySrcNumber(Long srcNumber);
}