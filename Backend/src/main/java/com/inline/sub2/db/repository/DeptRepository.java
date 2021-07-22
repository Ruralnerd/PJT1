package com.inline.sub2.db.repository;

import com.inline.sub2.db.entity.DeptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeptRepository extends JpaRepository<DeptEntity, String> {
    public DeptEntity findByDeptName(String deptName);
}
