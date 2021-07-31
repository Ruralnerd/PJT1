package com.inline.sub2.api.service;

import com.inline.sub2.db.entity.DeptEntity;
import org.springframework.transaction.annotation.Transactional;

public interface DeptService {
    @Transactional(rollbackFor = Exception.class)
    DeptEntity getDeptId(String DeptName);
    DeptEntity getDeptId(String deptName,Long officeId);
}
