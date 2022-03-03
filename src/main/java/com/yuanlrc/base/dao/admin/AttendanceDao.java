package com.yuanlrc.base.dao.admin;

import com.yuanlrc.base.entity.admin.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 考勤Dao
 */
@Repository
public interface AttendanceDao extends JpaRepository<Attendance,Long>, JpaSpecificationExecutor<Attendance> {

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Query("select a from Attendance a where id = :id")
    Attendance find(@Param("id")Long id);

    @Query("select a from Attendance a")
    List<Attendance> query();
    /**
     * 根据工号 年月查询
     * @param jobNumber
     * @param year
     * @param monthOfDay
     * @return
     */
    Attendance findByJobNumberAndYearAndMonthOfDay(String jobNumber,Integer year,Integer monthOfDay);

    /**
     * 计算员工一年请假天数总和
     * @param jobNumber
     * @param year
     * @return
     */
    @Query(value = "select coalesce(sum(y.leave_days),0) from ylrc_attendance y where y.job_number = :jobNumber and y.year = :year",nativeQuery = true)
    double sumLeaveDays(@Param("jobNumber")String jobNumber,@Param("year")Integer year);

    /**
     * 计算员工一年加班总时长
     * @param jobNumber
     * @param year
     * @return
     */
    @Query(value = "select coalesce(sum(y.overtime_hours),0)+coalesce(sum(y.overtime_on_weekends),0)+coalesce(sum(y.holiday_overtime_hours),0)" +
            "from ylrc_attendance y where y.job_number = :jobNumber and y.year = :year",nativeQuery = true)
    double sumOverTimeHours(@Param("jobNumber")String jobNumber,@Param("year")Integer year);

    /**
     * 计算员工一年缺勤天数总和
     * @param jobNumber
     * @param year
     * @return
     */
    @Query(value = "select coalesce(sum(y.absence_days),0) from ylrc_attendance y where y.job_number = :jobNumber and y.year = :year",nativeQuery = true)
    double sumAbsenceDays(@Param("jobNumber")String jobNumber,@Param("year")Integer year);

}
