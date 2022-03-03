package com.yuanlrc.base.dao.admin;

import com.yuanlrc.base.entity.admin.Department;
import com.yuanlrc.base.entity.admin.PerformanceAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * 绩效考核数据库处理层
 * @author Administrator
 *
 */
@Repository
public interface PeranceAssmentDao extends JpaRepository<PerformanceAssessment, Long>,JpaSpecificationExecutor<PerformanceAssessment> {


	/**
	 * 根据绩效考核id查询
	 * @param id
	 * @return
	 */
	@Query("select pa from PerformanceAssessment pa where id = :id")
	public PerformanceAssessment find(@Param("id") Long id);

	/**
	 * 根据年份和月份判断是否已考核过
	 * @param year
	 * @param month
	 * @return
	 */
	public PerformanceAssessment findByYearAndMonthAndStaffId(@Param("year")Integer year,@Param("month")Integer month,@Param("staffId")Long staffId);

	/**
	 * 根据员工号和年份查询数量
	 * @param staffId
	 * @param year
	 * @return
	 */
	int countByStaffIdAndYear(@Param("staffId")Long staffId,@Param("year")Integer year);

	@Query(value = "select coalesce(sum(y.percentage),0) from ylrc_performance_assessment y " +
			"where y.staff_id = :staffId and y.year = :year",nativeQuery = true)
	BigDecimal sumPercentage(@Param("staffId")Long staffId,@Param("year")Integer year);
}
