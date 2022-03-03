package com.yuanlrc.base.dao.admin;

import com.yuanlrc.base.entity.admin.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 部门数据库处理层
 * @author Administrator
 *
 */
@Repository
public interface DepartmentDao extends JpaRepository<Department, Long>,JpaSpecificationExecutor<Department> {

	/**
	 * 按照部门名称查找部门信息
	 * @param name
	 * @return
	 */
	public Department findByName(String name);

	/**
	 * 根据部门id查询
	 * @param id
	 * @return
	 */
	@Query("select d from Department d where id = :id")
	public Department find(@Param("id") Long id);

}
