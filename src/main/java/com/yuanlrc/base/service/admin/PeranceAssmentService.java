package com.yuanlrc.base.service.admin;

import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.dao.admin.DepartmentDao;
import com.yuanlrc.base.dao.admin.PeranceAssmentDao;
import com.yuanlrc.base.entity.admin.Department;
import com.yuanlrc.base.entity.admin.PerformanceAssessment;
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
import java.util.List;

/**
 * 绩效考核管理service
 * @author Administrator
 *
 */
@Service
public class PeranceAssmentService {

	@Autowired
	private PeranceAssmentDao peranceAssmentDao;
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public PerformanceAssessment find(Long id){
		return peranceAssmentDao.find(id);
	}

	
	/**
	 * 绩效考核添加/编辑操作
	 * @param performanceAssessment
	 * @return
	 */
	public PerformanceAssessment save(PerformanceAssessment performanceAssessment){
		return peranceAssmentDao.save(performanceAssessment);
	}

    /**
     * 查找所有的绩效考核
	 * @return
     */
	public List<PerformanceAssessment> findAll(){
		return peranceAssmentDao.findAll();
	}
	
	/**
	 * 分页查询绩效考核列表
	 * @param performanceAssessment
	 * @param pageBean
	 * @return
	 */
	public PageBean<PerformanceAssessment> findList(PerformanceAssessment performanceAssessment,PageBean<PerformanceAssessment> pageBean){
		Specification<PerformanceAssessment> specification = new Specification<PerformanceAssessment>() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				Predicate predicate =criteriaBuilder.like(root.get("staff").get("jobNumber"), "%" + (performanceAssessment.getStaff()==null?"":performanceAssessment.getStaff().getJobNumber()) + "%");
				if(performanceAssessment.getYear()!=null){
					Predicate equal = criteriaBuilder.equal(root.get("year"), performanceAssessment.getYear());
					predicate = criteriaBuilder.and(predicate, equal);
				}
				if(performanceAssessment.getMonth()!=null){
					Predicate equa2 = criteriaBuilder.equal(root.get("month"), performanceAssessment.getMonth());
					predicate = criteriaBuilder.and(predicate, equa2);
				}
				return predicate;
			}
		};
		Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
		Pageable pageable = PageRequest.of(pageBean.getCurrentPage()-1, pageBean.getPageSize(),sort);
		Page<PerformanceAssessment> findAll = peranceAssmentDao.findAll(specification, pageable);
		pageBean.setContent(findAll.getContent());
		pageBean.setTotal(findAll.getTotalElements());
		pageBean.setTotalPage(findAll.getTotalPages());
		return pageBean;
	}

	/**
	 * 按照绩效考核id删除
	 * @param id
	 */
	public void delete(Long id){
		peranceAssmentDao.deleteById(id);
	}
	
	/**
	 * 返回绩效考核总数
	 * @return
	 */
	public long total(){
		return peranceAssmentDao.count();
	}

	/**
	 * 根据年份和月份判断是否已考核过
	 * @param year
	 * @param month
	 * @return
	 */
	public PerformanceAssessment findByYearAndMonthAndStaffId(Integer year,Integer month,Long staffId){
		return  peranceAssmentDao.findByYearAndMonthAndStaffId(year,month,staffId);
	}

	/**
	 * 根据员工号和年份查询数量
	 * @param staffId
	 * @param year
	 * @return
	 */
	public Integer count(Long staffId,Integer year){
		return peranceAssmentDao.countByStaffIdAndYear(staffId,year);
	}

	/**
	 * 根据年份和工号计算总分数
	 * @param staffId
	 * @param year
	 * @return
	 */
	public BigDecimal sumPercentage(Long staffId,Integer year){
		return peranceAssmentDao.sumPercentage(staffId, year);
	}
}
