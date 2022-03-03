package com.yuanlrc.base.dao.admin;

import com.yuanlrc.base.entity.admin.Department;
import com.yuanlrc.base.entity.admin.Staff;
import com.yuanlrc.base.entity.admin.WageItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *工资项管理数据库处理层
 * @author Administrator
 *
 */
@Repository
public interface WageItemDao extends JpaRepository<WageItem, Long>,JpaSpecificationExecutor<WageItem> {

    public WageItem findFirstByOrderByCreateTimeDesc();

    /**
     * 根据工龄项id查询
     * @param id
     * @return
     */
    @Query("select w from WageItem w where id = :id")
    public WageItem find(@Param("id") Long id);

}
