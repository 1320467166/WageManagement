package com.yuanlrc.base.service.admin;

import com.yuanlrc.base.bean.IsStatus;
import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.bean.StaffStatus;
import com.yuanlrc.base.dao.admin.StaffDao;
import com.yuanlrc.base.dao.admin.UserDao;
import com.yuanlrc.base.entity.admin.Staff;
import com.yuanlrc.base.entity.admin.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * 员工管理service
 *
 * @author Administrator
 */
@Service
public class StaffService {

    @Autowired
    private StaffDao staffDao;

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    public Staff find(Long id) {
        return staffDao.find(id);
    }

    /**
     * 按照员工名查找员工
     *
     * @param name
     * @return
     */
    public Staff findByName(String name) {
        return staffDao.findByName(name);
    }

    /**
     * 员工添加/编辑操作
     *
     * @param staff
     * @return
     */
    public Staff save(Staff staff) {
        return staffDao.save(staff);
    }

    /**
     * 分页查询员工列表
     *
     * @param staff
     * @param pageBean
     * @return
     */
    public PageBean<Staff> findList(Staff staff, PageBean<Staff> pageBean) {
        Specification<Staff> specification = new Specification<Staff>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.like(root.get("jobNumber"), "%" + (staff.getJobNumber() == null ? "" : staff.getJobNumber()) + "%");
                Predicate equal = criteriaBuilder.equal(root.get("isStatus"), StaffStatus.ON_THE_JOB.getCode());
                predicate = criteriaBuilder.and(predicate, equal);
                return predicate;
            }
        };
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(pageBean.getCurrentPage() - 1, pageBean.getPageSize(), sort);
        Page<Staff> findAll = staffDao.findAll(specification, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());
        return pageBean;
    }

    /**
     * 分页查询部门员工列表
     *
     * @param staff
     * @param pageBean
     * @return
     */
    public PageBean<Staff> findDepartmentList(Staff staff, PageBean<Staff> pageBean, Staff loginStaff) {
        Specification<Staff> specification = new Specification<Staff>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.like(root.get("jobNumber"), "%" + (staff.getJobNumber() == null ? "" : staff.getJobNumber()) + "%");
                Predicate equal = criteriaBuilder.equal(root.get("isStatus"), StaffStatus.ON_THE_JOB.getCode());
                if (loginStaff.getRole().getId() == 6) {
                    //表示部门经理
                    Predicate equal1 = criteriaBuilder.equal(root.get("department").get("id"), loginStaff.getDepartment().getId());
                    predicate = criteriaBuilder.and(predicate, equal1);
                }
                predicate = criteriaBuilder.and(predicate, equal);
                return predicate;
            }
        };
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(pageBean.getCurrentPage() - 1, pageBean.getPageSize(), sort);
        Page<Staff> findAll = staffDao.findAll(specification, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());
        return pageBean;
    }

    /**
     * 按照员工id删除
     *
     * @param id
     */
    public void delete(Long id) {
        staffDao.deleteById(id);
    }

    /**
     * 返回员工总数
     *
     * @return
     */
    public long total() {
        return staffDao.count();
    }

    /**
     * 获取最大Id
     *
     * @return
     */
    public int findMaxId() {
        return staffDao.findMaxId();
    }

    /**
     * 根据员工号查找
     *
     * @param jobNumber
     * @return
     */
    public Staff findByJobNumber(String jobNumber) {
        return staffDao.findByJobNumber(jobNumber);
    }

    /**
     * 根据员工号和状态查询
     *
     * @param jobNumber
     * @return
     */
    public Staff findByJobNumberAndIsStatus(String jobNumber) {
        return staffDao.findByJobNumberAndIsStatus(jobNumber, StaffStatus.ON_THE_JOB.getCode());
    }

    /**
     * 查询全部
     *
     * @return
     */
    public List<Staff> findAll() {
        return staffDao.findAll();
    }


    /**
     * 更新离职状态
     *
     * @param status
     * @param id
     * @return
     */
    public int updateStatus(Integer status, Long id) {
        return staffDao.updateStatus(status, id);
    }

    /**
     * 根据状态查询
     *
     * @param status
     * @return
     */
    public List<Staff> findByIsStatus(Integer status) {
        return staffDao.findByIsStatus(status);
    }

    /**
     * 根据id和状态查找
     *
     * @param id
     * @param isStatus
     * @return
     */
    public Staff findByIdAndIsStatus(Long id, Integer isStatus) {
        return staffDao.findByIdAndIsStatus(id, isStatus);
    }


    /**
     * 查找部门人数
     *
     * @return
     */
    public List<Object> findCountDepartment(Integer isStatus) {
        return staffDao.findCountDepartment(isStatus);
    }

    /**
     * 各部门平均工资统计
     *
     * @return
     */
    public List<Object> findAvgDepartment(Integer isStatus) {
        return staffDao.findAvgDepartment(isStatus);
    }
}
