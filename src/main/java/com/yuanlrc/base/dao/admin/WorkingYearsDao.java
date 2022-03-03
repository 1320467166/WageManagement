package com.yuanlrc.base.dao.admin;

import com.yuanlrc.base.entity.admin.WorkingYears;
import com.yuanlrc.base.entity.admin.WorkingYears;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 工龄数据库处理层
 * @author Administrator
 *
 */
@Repository
public interface WorkingYearsDao extends JpaRepository<WorkingYears, Long>,JpaSpecificationExecutor<WorkingYears> {


	/**
	 * 根据工龄id查询
	 * @param id
	 * @return
	 */
	@Query("select w from WorkingYears w where id = :id")
	public WorkingYears find(@Param("id") Long id);

	/**
	 * 根据工龄查询
	 * @param years
	 * @return
	 */
	public WorkingYears findByYears(Integer years);

}
