package com.yuanlrc.base.service.admin;

import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.dao.admin.JobTitleDao;
import com.yuanlrc.base.dao.admin.StaffDao;
import com.yuanlrc.base.entity.admin.Department;
import com.yuanlrc.base.entity.admin.JobTitle;
import com.yuanlrc.base.entity.admin.Staff;
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
 * 职称管理service
 * @author Administrator
 *
 */
@Service
public class JobTitleService {

	@Autowired
	private JobTitleDao jobTitleDao;
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public JobTitle find(Long id){
		return jobTitleDao.find(id);
	}
	
	/**
	 * 按照职称名查找职称
	 * @param name
	 * @return
	 */
	public JobTitle findByName(String name){
		return jobTitleDao.findByName(name);
	}
	
	/**
	 * 职称添加/编辑操作
	 * @param jobTitle
	 * @return
	 */
	public JobTitle save(JobTitle jobTitle){
		return jobTitleDao.save(jobTitle);
	}

	/**
	 * 查找所有的职称
	 * @return
	 */
	public List<JobTitle> findAll(){
		return jobTitleDao.findAll();
	}
	
	/**
	 * 分页查询职称列表
	 * @param jobTitle
	 * @param pageBean
	 * @return
	 */
	public PageBean<JobTitle> findList(JobTitle jobTitle,PageBean<JobTitle> pageBean){
		Specification<JobTitle> specification = new Specification<JobTitle>() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				Predicate predicate = criteriaBuilder.like(root.get("name"), "%" + (jobTitle.getName() == null ? "" : jobTitle.getName()) + "%");
				return predicate;
			}
		};
		Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
		Pageable pageable = PageRequest.of(pageBean.getCurrentPage()-1, pageBean.getPageSize(),sort);
		Page<JobTitle> findAll = jobTitleDao.findAll(specification, pageable);
		pageBean.setContent(findAll.getContent());
		pageBean.setTotal(findAll.getTotalElements());
		pageBean.setTotalPage(findAll.getTotalPages());
		return pageBean;
	}

	/**
	 * 按照职称id删除
	 * @param id
	 */
	public void delete(Long id){
		jobTitleDao.deleteById(id);
	}
	
	/**
	 * 返回职称总数
	 * @return
	 */
	public long total(){
		return jobTitleDao.count();
	}

	/**
	 * 判断职称名字是否已存在
	 * @param name
	 * @param id
	 * @return
	 */
	public Boolean isExistName(String name,Long id){
		JobTitle byName = jobTitleDao.findByName(name);
		if(byName != null){
			if(byName.getId().longValue() != id.longValue()){
				return true;
			}
		}
		return false;
	}

}
