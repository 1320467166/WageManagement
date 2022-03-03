package com.yuanlrc.base.dao.admin;

import com.yuanlrc.base.entity.admin.AnnualAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 年度考核Dao
 */
@Repository
public interface AnnualAssessmentDao extends JpaRepository<AnnualAssessment,Long>, JpaSpecificationExecutor<AnnualAssessment> {

    /**
     * 根据id查找
     * @param id
     * @return
     */
    @Query("select a from AnnualAssessment a where id = :id")
    AnnualAssessment find(@Param("id")Long id);

    /**
     * 根据员工和年份查询
     * @param staffId
     * @param year
     * @return
     */
    AnnualAssessment findByStaffIdAndYear(Long staffId,Integer year);
}
