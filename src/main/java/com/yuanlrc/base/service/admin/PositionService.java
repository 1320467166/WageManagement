package com.yuanlrc.base.service.admin;

import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.dao.admin.PositionDao;
import com.yuanlrc.base.entity.admin.Department;
import com.yuanlrc.base.entity.admin.Position;
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
 * 岗位管理service
 * @author Administrator
 *
 */
@Service
public class PositionService {

	@Autowired
	private PositionDao positionDao;
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public Position find(Long id){
		return positionDao.find(id);
	}
	
	/**
	 * 按照岗位名查找岗位
	 * @param name
	 * @return
	 */
	public Position findByName(String name){
		return positionDao.findByName(name);
	}
	
	/**
	 * 岗位添加/编辑操作
	 * @param position
	 * @return
	 */
	public Position save(Position position){
		return positionDao.save(position);
	}

    /**
     * 查找所有的岗位
	 * @return
     */
	public List<Position> findAll(){
		return positionDao.findAll();
	}
	
	/**
	 * 分页查询岗位列表
	 * @param position
	 * @param pageBean
	 * @return
	 */
	public PageBean<Position> findList(Position position,PageBean<Position> pageBean){
		Specification<Position> specification = new Specification<Position>() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				Predicate predicate = criteriaBuilder.like(root.get("name"), "%" + (position.getName() == null ? "" : position.getName()) + "%");
				return predicate;
			}
		};
		Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
		Pageable pageable = PageRequest.of(pageBean.getCurrentPage()-1, pageBean.getPageSize(),sort);
		Page<Position> findAll = positionDao.findAll(specification, pageable);
		pageBean.setContent(findAll.getContent());
		pageBean.setTotal(findAll.getTotalElements());
		pageBean.setTotalPage(findAll.getTotalPages());
		return pageBean;
	}

	/**
	 * 按照岗位id删除
	 * @param id
	 */
	public void delete(Long id){
		positionDao.deleteById(id);
	}
	
	/**
	 * 返回岗位总数
	 * @return
	 */
	public long total(){
		return positionDao.count();
	}

	/**
	 * 判断名称是否已存在
	 * @param name
	 * @param id
	 * @return
	 */
	public Boolean isExistName(String name,Long id){
		Position byName = positionDao.findByName(name);
		if(byName != null){
			if(byName.getId().longValue() != id.longValue()){
				return true;
			}
		}
		return false;
	}

}
