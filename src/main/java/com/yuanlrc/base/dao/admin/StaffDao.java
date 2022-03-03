package com.yuanlrc.base.dao.admin;

import com.yuanlrc.base.bean.StaffStatus;
import com.yuanlrc.base.bean.IsStatus;
import com.yuanlrc.base.entity.admin.Staff;
import com.yuanlrc.base.entity.admin.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.List;

/**
 * 员工数据库处理层
 * @author Administrator
 *
 */
@Repository
public interface StaffDao extends JpaRepository<Staff, Long>,JpaSpecificationExecutor<Staff> {

	/**
	 * 按照用户名查找用户信息
	 * @param name
	 * @return
	 */
	public Staff findByName(String name);

	/**
	 * 根据员工id查询
	 * @param id
	 * @return
	 */
	@Query("select s from Staff s where id = :id")
	public Staff find(@Param("id") Long id);




	/**
	 * 获取最大id
	 * @return
	 */
	@Query(value = "select max(id) from ylrc_staff",nativeQuery = true)
	public int findMaxId();

	/**
	 * 按照员工号查找
	 * @param jobNumber
	 * @return
	 */
	public Staff findByJobNumber(String jobNumber);

	/**
	 * 更新离职状态
	 * @param status
	 * @param id
	 * @return
	 */
	@Transactional
	@Modifying
	@Query( "update Staff s set s.isStatus =?1 where s.id = ?2")
	public int updateStatus(@Param("status") Integer status, @Param("id")Long id);

	/**
	 * 根据员工号和状态查询
	 * @param jobNumber
	 * @param isStatus
	 * @return
	 */
	public Staff findByJobNumberAndIsStatus(String jobNumber, Integer isStatus);

	/**
	 * 根据状态查询
	 * @param status
	 * @return
	 */
	public List<Staff> findByIsStatus(@Param("status")Integer status);

	/**
	 * 根据id和状态查找
	 * @param id
	 * @param isStatus
	 * @return
	 */
	public Staff findByIdAndIsStatus(@Param("id")Long id,@Param("status") Integer isStatus);


	/**
	 * 各部门的人数统计
	 * @return
	 */
	@Query("select count(s.id)as count,s.department from Staff s where s.isStatus = :status group by s.department")
	public List<Object> findCountDepartment(@Param("status")Integer status);


	/**
	 * 各部门平均工资统计
	 * @return
	 */
	@Query("select avg(s.salary)as avg,s.department from Staff s where s.isStatus = :status group by s.department")
	public List<Object> findAvgDepartment(@Param("status")Integer status);


}
