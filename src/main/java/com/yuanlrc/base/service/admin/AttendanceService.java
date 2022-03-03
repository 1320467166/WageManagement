package com.yuanlrc.base.service.admin;

import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.bean.StaffStatus;
import com.yuanlrc.base.dao.admin.AttendanceDao;
import com.yuanlrc.base.dao.admin.StaffDao;
import com.yuanlrc.base.entity.admin.Attendance;
import com.yuanlrc.base.entity.admin.Staff;
import com.yuanlrc.base.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * 考勤Service
 */
@Service
public class AttendanceService {

    @Autowired
    private AttendanceDao attendanceDao;

    @Autowired
    private StaffDao staffDao;

    /**
     * 根据id查询
     * @param id
     * @return
     */
    public Attendance find(Long id){
        return attendanceDao.find(id);
    }

    /**
     * 根据工号 年月查询
     * @param jobNumber
     * @param year
     * @param monthOfDay
     * @return
     */
    public Attendance findByJobNumberAndYearAndMonthOfDay(String jobNumber,Integer year,Integer monthOfDay){
        return attendanceDao.findByJobNumberAndYearAndMonthOfDay(jobNumber,year,monthOfDay);
    }

    /**
     * 计算员工一年请假天数总和
     * @param jobNumber
     * @param year
     * @return
     */
    public Double sumLeaveDays(String jobNumber,Integer year){
        return attendanceDao.sumLeaveDays(jobNumber, year);
    }

    /**
     * 计算员工一年加班总和
     * @param jobNumber
     * @param year
     * @return
     */
    public Double sumOverTimeHours(String jobNumber,Integer year){
        return attendanceDao.sumOverTimeHours(jobNumber,year);
    }

    /**
     * 计算员工一年缺勤天数总和
     * @param jobNumber
     * @param year
     * @return
     */
    public Double sumAbsenceDays(String jobNumber,Integer year){
        return attendanceDao.sumAbsenceDays(jobNumber,year);
    }

    /**
     * 分页查询
     * @param attendance
     * @param pageBean
     * @return
     */
    public PageBean<Attendance> findList(Attendance attendance,PageBean<Attendance> pageBean){
        Specification<Attendance> specification = new Specification<Attendance>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.like(root.get("jobNumber"), "%" + (attendance.getJobNumber() == null ? "" : attendance.getJobNumber()) + "%");
                Predicate equal = criteriaBuilder.equal(root.get("staff").get("isStatus"), StaffStatus.ON_THE_JOB.getCode());
                predicate = criteriaBuilder.and(predicate,equal);
                if(attendance.getMonth() != null){
                    Predicate month = byMonth(attendance.getMonth(), root, criteriaBuilder);
                    predicate = criteriaBuilder.and(predicate,month);
                }
                return predicate;
            }
        };
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        PageBean<Attendance> list = list(sort,pageBean, specification);
        return list;
    }

    /**
     * 个人考勤列表
     * @param attendance
     * @param pageBean
     * @return
     */
    public PageBean<Attendance> findPersonageList(Attendance attendance,PageBean<Attendance> pageBean){
        Specification<Attendance> specification = new Specification<Attendance>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Staff loginedStaff = SessionUtil.getLoginedStaff();
                Predicate   predicate = criteriaBuilder.equal(root.get("jobNumber"), loginedStaff.getJobNumber());
                if(attendance.getMonth() != null){
                    Predicate month = byMonth(attendance.getMonth(), root, criteriaBuilder);
                    predicate = criteriaBuilder.and(predicate,month);
                }
                return predicate;
            }
        };
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        PageBean<Attendance> list = list(sort,pageBean, specification);
        return list;
    }

    public Predicate byMonth(Date date,Root root,CriteriaBuilder criteriaBuilder){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Predicate year = criteriaBuilder.equal(root.get("year"), cal.get(Calendar.YEAR));
        Predicate month = criteriaBuilder.equal(root.get("monthOfDay"), cal.get(Calendar.MONTH)+1);
        Predicate and = criteriaBuilder.and(month, year);
        return and;
    }

    public PageBean<Attendance> list(Sort sort, PageBean<Attendance> pageBean, Specification<Attendance> specification){
        Pageable pageable = PageRequest.of(pageBean.getCurrentPage()-1, pageBean.getPageSize(),sort);
        Page<Attendance> findAll = attendanceDao.findAll(specification, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());
        return pageBean;
    }


    /**
     * 保存
     * @param attendance
     * @return
     */
    public Attendance save(Attendance attendance){
        return attendanceDao.save(attendance);
    }

    /**
     * 根据id删除
     * @param id
     */
    public void delete(Long id){
        attendanceDao.deleteById(id);
    }

    @Transactional
    public void saveAll(List<Attendance> attendanceList) {
        for (Attendance attendance : attendanceList) {
            attendanceDao.save(attendance);
        }
    }
}
