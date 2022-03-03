package com.yuanlrc.base.dao.admin;

import com.yuanlrc.base.entity.admin.JobTitle;
import com.yuanlrc.base.entity.admin.JobTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 职称数据库处理层
 * @author Administrator
 *
 */
@Repository
public interface JobTitleDao extends JpaRepository<JobTitle, Long>,JpaSpecificationExecutor<JobTitle> {

	/**
	 * 按照职称名称查找职称信息
	 * @param name
	 * @return
	 */
	public JobTitle findByName(String name);

	/**
	 * 根据职称id查询
	 * @param id
	 * @return
	 */
	@Query("select j from JobTitle j where id = :id")
	public JobTitle find(@Param("id") Long id);
	
}
