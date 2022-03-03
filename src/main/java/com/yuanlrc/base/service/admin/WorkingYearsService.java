package com.yuanlrc.base.service.admin;

import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.dao.admin.WorkingYearsDao;
import com.yuanlrc.base.dao.admin.WorkingYearsDao;
import com.yuanlrc.base.entity.admin.User;
import com.yuanlrc.base.entity.admin.WorkingYears;
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
 * 工龄管理service
 * @author Administrator
 *
 */
@Service
public class WorkingYearsService {

	@Autowired
	private WorkingYearsDao workingYearsDao;
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public WorkingYears find(Long id){
		return workingYearsDao.find(id);
	}

	
	/**
	 * 工龄添加/编辑操作
	 * @param workingYears
	 * @return
	 */
	public WorkingYears save(WorkingYears workingYears){
		return workingYearsDao.save(workingYears);
	}

    /**
     * 查找所有的工龄
	 * @return
     */
	public List<WorkingYears> findAll(){
		return workingYearsDao.findAll();
	}
	
	/**
	 * 分页查询工龄列表
	 * @param workingYears
	 * @param pageBean
	 * @return
	 */
	public PageBean<WorkingYears> findList(WorkingYears workingYears,PageBean<WorkingYears> pageBean){
		Specification<WorkingYears> specification = new Specification<WorkingYears>() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				Predicate predicate =null;
				if(workingYears.getYears()!=null){
					predicate = criteriaBuilder.equal(root.get("years"), workingYears.getYears());
				}
				return predicate;
			}
		};
		Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
		Pageable pageable = PageRequest.of(pageBean.getCurrentPage()-1, pageBean.getPageSize(),sort);
		Page<WorkingYears> findAll = workingYearsDao.findAll(specification, pageable);
		pageBean.setContent(findAll.getContent());
		pageBean.setTotal(findAll.getTotalElements());
		pageBean.setTotalPage(findAll.getTotalPages());
		return pageBean;
	}

	/**
	 * 按照工龄id删除
	 * @param id
	 */
	public void delete(Long id){
		workingYearsDao.deleteById(id);
	}
	
	/**
	 * 返回工龄总数
	 * @return
	 */
	public long total(){
		return workingYearsDao.count();
	}

	/**
	 * 根据工龄查询
	 * @param years
	 * @return
	 */
	public WorkingYears findByYears(Integer years){
		return workingYearsDao.findByYears(years);
	}

	/**
	 * 判断用工龄是否存在，添加和编辑均可判断
	 * @param year
	 * @param id
	 * @return
	 */
	public boolean isExistYear(Integer year,Long id){
		WorkingYears byYears = workingYearsDao.findByYears(year);
		if(byYears != null){
			if(byYears.getId().longValue() != id.longValue()){
				return true;
			}
		}
		return false;
	}
}
