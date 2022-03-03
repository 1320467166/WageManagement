package com.yuanlrc.base.dao.admin;

import com.yuanlrc.base.entity.admin.Position;
import com.yuanlrc.base.entity.admin.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 岗位数据库处理层
 * @author Administrator
 *
 */
@Repository
public interface PositionDao extends JpaRepository<Position, Long>,JpaSpecificationExecutor<Position> {

	/**
	 * 按照岗位名称查找岗位信息
	 * @param name
	 * @return
	 */
	public Position findByName(String name);

	/**
	 * 根据岗位id查询
	 * @param id
	 * @return
	 */
	@Query("select p from Position p where id = :id")
	public Position find(@Param("id") Long id);
	
}
