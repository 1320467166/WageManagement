package com.yuanlrc.base.service.admin;

import com.yuanlrc.base.bean.IsStatus;
import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.bean.StaffStatus;
import com.yuanlrc.base.dao.admin.SalaryDao;
import com.yuanlrc.base.dao.admin.WageItemDao;
import com.yuanlrc.base.entity.admin.Salary;
import com.yuanlrc.base.entity.admin.Staff;
import com.yuanlrc.base.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.persistence.Transient;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * 工资Service
 */
@Service
public class SalaryService {

    @Autowired
    private SalaryDao salaryDao;


    /**
     * 根据id查询
     * @param id
     * @return
     */
    public Salary find(Long id){
        return salaryDao.find(id);
    }

    /**
     * 分页查询
     * @param salary
     * @param pageBean
     * @return
     */
    public PageBean<Salary> findList(Salary salary,PageBean<Salary> pageBean,String jobNumber){
        Specification<Salary> specification = new Specification<Salary>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.like(root.get("staff").get("jobNumber"), "%" + (jobNumber == null ? "" : jobNumber) + "%");
                Predicate equal = criteriaBuilder.equal(root.get("staff").get("isStatus"), StaffStatus.ON_THE_JOB.getCode());
                predicate = criteriaBuilder.and(predicate,equal);
                if(salary.getMonth() != null){
                    Predicate month = byMonth(salary.getMonth(), root, criteriaBuilder);
                    predicate = criteriaBuilder.and(predicate,month);
                }
                return predicate;
            }
        };
        Sort sort = Sort.by(Sort.Direction.DESC, "isStatus");
        PageBean<Salary> list = list(sort,pageBean, specification);
        return list;
    }

    /**
     * 个人工资列表
     * @param salary
     * @param pageBean
     * @return
     */
    public PageBean<Salary> findPersonageList(Salary salary,PageBean<Salary> pageBean){
        Specification<Salary> specification = new Specification<Salary>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Staff loginedStaff = SessionUtil.getLoginedStaff();
                Predicate predicate = criteriaBuilder.equal(root.get("staff"), loginedStaff.getId());
                if(salary.getMonth() != null){
                    Predicate month = byMonth(salary.getMonth(), root, criteriaBuilder);
                    predicate = criteriaBuilder.and(predicate,month);
                }
                return predicate;
            }
        };
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        PageBean<Salary> list = list(sort,pageBean, specification);
        return list;
    }

    public Predicate byMonth(Date date, Root root, CriteriaBuilder criteriaBuilder){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Predicate year = criteriaBuilder.equal(root.get("year"), cal.get(Calendar.YEAR));
        Predicate month = criteriaBuilder.equal(root.get("monthOfDay"), cal.get(Calendar.MONTH)+1);
        Predicate and = criteriaBuilder.and(month, year);
        return and;
    }

    public PageBean<Salary> list(Sort sort, PageBean<Salary> pageBean, Specification<Salary> specification){
        Pageable pageable = PageRequest.of(pageBean.getCurrentPage()-1, pageBean.getPageSize(),sort);
        Page<Salary> findAll = salaryDao.findAll(specification, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());
        return pageBean;
    }

    /**
     * 根据员工id年月查询是否有该工资
     * @param staffId
     * @param year
     * @param monthOfDay
     * @return
     */
    public Salary findByStaffIdAndYearAndMonthOfDay(Long staffId,Integer year,Integer monthOfDay){
        return salaryDao.findByStaffIdAndYearAndMonthOfDay(staffId, year, monthOfDay);
    }

    /**
     * 保存
     * @param salary
     * @return
     */
    public Salary save(Salary salary){
        return salaryDao.save(salary);
    }


    /**
     * 根据id删除
     * @param id
     */
    public void delete(Long id){
        salaryDao.deleteById(id);
    }

    /**
     * 根据年份和月份查询部门平均工资
     * @return
     */
    public List<Object> sumByDepartmentByYearAndMonth(Integer years,Integer months){
        return salaryDao.sumByDepartmentByYearAndMonth(years,months);
    }
    /**
     * 根据年份查找12个月 每个部门每个月的总实发工资
     * @param years
     * @return
     */
    public List<Object> countPayRollDepartmentByYear(Integer years){
        return  salaryDao.countPayRollDepartmentByYear(years);
    }
    /**
     * 查询该月已有工资记录的列表
     * @param year
     * @param monthOfDay
     * @return
     */
    public List<Salary> findByYearAndMonthOfDay(Integer year,Integer monthOfDay){
        return salaryDao.findByYearAndMonthOfDay(year,monthOfDay);
    }

    @Transient
    public void saveAll(List<Salary> salaries) {
        for (Salary salary : salaries) {
            salaryDao.save(salary);
        }
    }

    /**
     * 根据id更新状态
     * @param isStatus
     * @param id
     * @return
     */
    public int updateStatus(IsStatus isStatus,Long id){
        return salaryDao.updateStatus(isStatus,id);
    }
}
