package com.yuanlrc.base.service.admin;

import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.dao.admin.WageItemDao;
import com.yuanlrc.base.dao.admin.WageItemDao;
import com.yuanlrc.base.entity.admin.WageItem;
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
 * 工资项管理service
 *
 * @author Administrator
 */
@Service
public class WageItemService {

    @Autowired
    private WageItemDao wageItemDao;


    /**
     * 工资项添加/编辑操作
     *
     * @param wageItem
     * @return
     */
    public WageItem save(WageItem wageItem) {
        return wageItemDao.save(wageItem);
    }

    /**
     * 查找所有的工资项
     *
     * @return
     */
    public List<WageItem> findAll() {
        return wageItemDao.findAll();
    }

    /**
     * 返回工资项总数
     *
     * @return
     */
    public long total() {
        return wageItemDao.count();
    }

    /**
     * 获取第一条数据
     * @return
     */
    public WageItem findFirstByOrderByCreateTimeDesc(){
        return wageItemDao.findFirstByOrderByCreateTimeDesc();
    }


    public WageItem find(Long id){
        return wageItemDao.find(id);
    }
}
