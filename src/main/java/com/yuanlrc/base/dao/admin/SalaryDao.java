package com.yuanlrc.base.dao.admin;

import com.yuanlrc.base.bean.IsStatus;
import com.yuanlrc.base.entity.admin.Salary;
import com.yuanlrc.base.entity.admin.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 工资Dao
 */
@Repository
public interface SalaryDao extends JpaRepository<Salary,Long>, JpaSpecificationExecutor<Salary> {

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Query("select s from Salary s where id = :id")
    public Salary find(@Param("id")Long id);

    /**
     * 根据员工id年月查询是否有该工资
     * @param staffId
     * @param year
     * @param monthOfDay
     * @return
     */
    public Salary findByStaffIdAndYearAndMonthOfDay(Long staffId,Integer year,Integer monthOfDay);

    /**
     * 根据年份和月份查询部门平均工资
     * @return
     */
    @Query(value = "select COALESCE(avg(sy.net_payroll),0)as avg,dt.name from ylrc_salary sy,ylrc_department dt where sy.department_id=dt.id and sy.year=:years and sy.month_of_day=:months  group by dt.id",nativeQuery = true)
    public List<Object> sumByDepartmentByYearAndMonth(@Param("years")Integer years,@Param("months")Integer months);


    /**
     * 根据年份查找12个月 每个部门每个月的总实发工资
     * @param years
     * @return
     */
    @Query(value = "select month_of_day as month,sum(net_payroll)as payroll from ylrc_salary sy where year=:years group by month_of_day",nativeQuery = true)
    public List<Object> countPayRollDepartmentByYear(@Param("years")Integer years);

    /**
     * 根据年月查询已有的工资记录
     * @param year
     * @param monthOfDay
     * @return
     */
    public List<Salary> findByYearAndMonthOfDay(Integer year,Integer monthOfDay);

    /**
     * 更新发放状态
     * @param isStatus
     * @param id
     * @return
     */
    @Transactional
    @Modifying
    @Query( "update Salary s set s.isStatus =?1 where s.id = ?2")
    public int updateStatus(@Param("isStatus") IsStatus isStatus, @Param("id")Long id);
}
