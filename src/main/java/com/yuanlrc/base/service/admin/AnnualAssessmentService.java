package com.yuanlrc.base.service.admin;

import com.yuanlrc.base.bean.GradeEnum;
import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.dao.admin.AnnualAssessmentDao;
import com.yuanlrc.base.dao.admin.AttendanceDao;
import com.yuanlrc.base.dao.admin.PeranceAssmentDao;
import com.yuanlrc.base.entity.admin.AnnualAssessment;
import com.yuanlrc.base.entity.admin.Staff;
import com.yuanlrc.base.util.SessionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * 年度考核Service
 */
@Service
public class AnnualAssessmentService {

    @Autowired
    private AnnualAssessmentDao annualAssessmentDao;

    @Autowired
    private AttendanceDao attendanceDao;

    @Autowired
    private PeranceAssmentDao peranceAssmentDao;

    /**
     * 根据id查找
     * @param id
     * @return
     */
    public AnnualAssessment find(Long id){
        return annualAssessmentDao.find(id);
    }

    //根据id和年份查询
    public AnnualAssessment findByStaffIdAndYear(Long staffId,Integer year){
        return annualAssessmentDao.findByStaffIdAndYear(staffId,year);
    }
    /**
     * 保存
     * @param annualAssessment
     * @return
     */
    public AnnualAssessment save(AnnualAssessment annualAssessment,Staff staff,Integer year){

        //请假天数
        Double leaveDays = attendanceDao.sumLeaveDays(staff.getJobNumber(), year);
        //加班小时
        Double overTimeHours = attendanceDao.sumOverTimeHours(staff.getJobNumber(), year);
        //缺勤天数
        Double absenceDays = attendanceDao.sumAbsenceDays(staff.getJobNumber(), year);

        //总数量
        Integer count = peranceAssmentDao.countByStaffIdAndYear(staff.getId(), year);
        BigDecimal performanceScore = BigDecimal.ZERO;
        if(count != 0){
            performanceScore = peranceAssmentDao.sumPercentage(staff.getId(), year);
            performanceScore = performanceScore.divide(new BigDecimal(count));
            performanceScore.setScale(2,BigDecimal.ROUND_HALF_UP);
        }

        annualAssessment.setStaff(staff);
        annualAssessment.setYear(year);
        annualAssessment.setPerformanceScore(performanceScore);
        annualAssessment.setLeaveDays(leaveDays);
        annualAssessment.setAbsenceDays(absenceDays);
        annualAssessment.setOvertimeHours(overTimeHours);

        GradeEnum gradeEnum = GradeEnum.countGrade(annualAssessment.getGrade().doubleValue());
        annualAssessment.setGradeEnum(gradeEnum);

        return annualAssessmentDao.save(annualAssessment);
    }

    /**
     * 部门经理查看列表
     * @param pageBean
     * @param jobNumber
     * @param year
     * @return
     */
    public PageBean<AnnualAssessment> findList(PageBean<AnnualAssessment> pageBean,String jobNumber,String year){
        Staff loginedStaff = SessionUtil.getLoginedStaff();
        Specification<AnnualAssessment> specification = new Specification<AnnualAssessment>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.like(root.get("staff").get("jobNumber"), "%" + (jobNumber == null ? "" : jobNumber) + "%");
                Predicate equal1 = criteriaBuilder.equal(root.get("staff").get("department"), loginedStaff.getDepartment().getId());
                predicate = criteriaBuilder.and(predicate,equal1);
                if(!StringUtils.isEmpty(year)){
                    Predicate equal = criteriaBuilder.equal(root.get("year"), Integer.parseInt(year));
                    predicate = criteriaBuilder.and(predicate,equal);
                }
                return predicate;
            }
        };
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        PageBean<AnnualAssessment> list = list(sort,pageBean, specification);
        return list;
    }

    /**
     * 个人年度考核
     * @param pageBean
     * @param year
     * @return
     */
    public PageBean<AnnualAssessment> findPersonageList(PageBean<AnnualAssessment> pageBean,String year){
        Staff loginedStaff = SessionUtil.getLoginedStaff();
        Specification<AnnualAssessment> specification = new Specification<AnnualAssessment>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.equal(root.get("staff").get("jobNumber"), loginedStaff.getJobNumber());
                if(!StringUtils.isEmpty(year)){
                    Predicate equal = criteriaBuilder.equal(root.get("year"), Integer.parseInt(year));
                    predicate = criteriaBuilder.and(predicate,equal);
                }
                return predicate;
            }
        };
        Sort sort = Sort.by(Sort.Direction.DESC, "year");
        PageBean<AnnualAssessment> list = list(sort,pageBean, specification);
        return list;
    }

    public PageBean<AnnualAssessment> list(Sort sort,PageBean<AnnualAssessment> pageBean,Specification<AnnualAssessment> specification){
        Pageable pageable = PageRequest.of(pageBean.getCurrentPage()-1, pageBean.getPageSize(),sort);
        Page<AnnualAssessment> findAll = annualAssessmentDao.findAll(specification, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());
        return pageBean;
    }

    /**
     * 根据id删除
     * @param id
     */
    public void delete(Long id){
        annualAssessmentDao.deleteById(id);
    }
}
