package com.yuanlrc.base.dao.admin;

import com.yuanlrc.base.entity.admin.AssessTarget;
import com.yuanlrc.base.entity.admin.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 指标数据库处理层
 * @author Administrator
 *
 */
@Repository
public interface AssessTargetDao extends JpaRepository<AssessTarget, Long>,JpaSpecificationExecutor<AssessTarget> {

	/**
	 * 按照指标名称查找指标信息
	 * @param name
	 * @return
	 */
	public AssessTarget findByName(String name);

	/**
	 * 根据指标id查询
	 * @param id
	 * @return
	 */
	@Query("select at from AssessTarget at where id = :id")
	public AssessTarget find(@Param("id") Long id);
	
}
