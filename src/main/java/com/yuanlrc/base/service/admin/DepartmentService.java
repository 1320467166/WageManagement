package com.yuanlrc.base.service.admin;

import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.dao.admin.DepartmentDao;
import com.yuanlrc.base.dao.admin.DepartmentDao;
import com.yuanlrc.base.entity.admin.Department;
import com.yuanlrc.base.entity.admin.Department;
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
import java.util.List;

/**
 * 部门管理service
 * @author Administrator
 *
 */
@Service
public class DepartmentService {

	@Autowired
	private DepartmentDao departmentDao;
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public Department find(Long id){
		return departmentDao.find(id);
	}
	
	/**
	 * 按照部门名查找部门
	 * @param name
	 * @return
	 */
	public Department findByName(String name){
		return departmentDao.findByName(name);
	}
	
	/**
	 * 部门添加/编辑操作
	 * @param department
	 * @return
	 */
	public Department save(Department department){
		return departmentDao.save(department);
	}

    /**
     * 查找所有的部门
	 * @return
     */
	public List<Department> findAll(){
		return departmentDao.findAll();
	}
	
	/**
	 * 分页查询部门列表
	 * @param department
	 * @param pageBean
	 * @return
	 */
	public PageBean<Department> findList(Department department,PageBean<Department> pageBean){
		Specification<Department> specification = new Specification<Department>() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				Predicate predicate = criteriaBuilder.like(root.get("name"), "%" + (department.getName() == null ? "" : department.getName()) + "%");
				return predicate;
			}
		};
		Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
		Pageable pageable = PageRequest.of(pageBean.getCurrentPage()-1, pageBean.getPageSize(),sort);
		Page<Department> findAll = departmentDao.findAll(specification, pageable);
		pageBean.setContent(findAll.getContent());
		pageBean.setTotal(findAll.getTotalElements());
		pageBean.setTotalPage(findAll.getTotalPages());
		return pageBean;
	}

	/**
	 * 按照部门id删除
	 * @param id
	 */
	public void delete(Long id){
		departmentDao.deleteById(id);
	}
	
	/**
	 * 返回部门总数
	 * @return
	 */
	public long total(){
		return departmentDao.count();
	}

	/**
	 * 判断部门名称是否已存在
	 * @param name
	 * @param id
	 * @return
	 */
	public Boolean isExistName(String name,Long id){
		Department byName = departmentDao.findByName(name);
		if(byName != null){
			if(byName.getId().longValue() != id.longValue()){
				return true;
			}
		}
		return false;
	}
}
