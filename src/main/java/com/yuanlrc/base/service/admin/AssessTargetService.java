package com.yuanlrc.base.service.admin;

import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.dao.admin.AssessTargetDao;
import com.yuanlrc.base.dao.admin.DepartmentDao;
import com.yuanlrc.base.entity.admin.AssessTarget;
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
 * 指标管理service
 * @author Administrator
 *
 */
@Service
public class AssessTargetService {

	@Autowired
	private AssessTargetDao assessTargetDao;
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public AssessTarget find(Long id){
		return assessTargetDao.find(id);
	}
	
	/**
	 * 按照指标名查找指标
	 * @param name
	 * @return
	 */
	public AssessTarget findByName(String name){
		return assessTargetDao.findByName(name);
	}
	
	/**
	 * 指标添加/编辑操作
	 * @param assessTarget
	 * @return
	 */
	public AssessTarget save(AssessTarget assessTarget){
		return assessTargetDao.save(assessTarget);
	}

    /**
     * 查找所有的指标
	 * @return
     */
	public List<AssessTarget> findAll(){
		return assessTargetDao.findAll();
	}
	
	/**
	 * 分页查询指标列表
	 * @param assessTarget
	 * @param pageBean
	 * @return
	 */
	public PageBean<AssessTarget> findList(AssessTarget assessTarget,PageBean<AssessTarget> pageBean){
		Specification<AssessTarget> specification = new Specification<AssessTarget>() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				Predicate predicate = criteriaBuilder.like(root.get("name"), "%" + (assessTarget.getName() == null ? "" : assessTarget.getName()) + "%");
				return predicate;
			}
		};
		Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
		Pageable pageable = PageRequest.of(pageBean.getCurrentPage()-1, pageBean.getPageSize(),sort);
		Page<AssessTarget> findAll = assessTargetDao.findAll(specification, pageable);
		pageBean.setContent(findAll.getContent());
		pageBean.setTotal(findAll.getTotalElements());
		pageBean.setTotalPage(findAll.getTotalPages());
		return pageBean;
	}

	/**
	 * 按照指标id删除
	 * @param id
	 */
	public void delete(Long id){
		assessTargetDao.deleteById(id);
	}
	
	/**
	 * 返回指标总数
	 * @return
	 */
	public long total(){
		return assessTargetDao.count();
	}

	/**
	 * 判断指标名称是否已存在
	 * @param name
	 * @param id
	 * @return
	 */
	public Boolean isExistName(String name,Long id){
		AssessTarget byName = assessTargetDao.findByName(name);
		if(byName != null){
			if(byName.getId().longValue() != id.longValue()){
				return true;
			}
		}
		return false;
	}
}
