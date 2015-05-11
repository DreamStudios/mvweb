package com.blueshit.neweast.repository;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

/**
 * 数据库交互基基本接口
 * @author 9527
 * @version 1.0
 * @email zhaohuaan0925@163.com
 * @created 2015/5/11 13:38
 * @description
 */
@NoRepositoryBean
public interface MvWebBaseRepository<T, ID extends Serializable>
        extends JpaRepository<T, ID> {

    /**
     * 通过HQL查询数据列表
     * @param hql 脚本
     * @return
     */
    public List<T> whFindByList(String hql);

    /**
     * 通过HQL查询数据列表对象
     * @param hql 脚本
     * @return
     */
    public Object findByList(String hql);

    /**
     * 根据hql查询分页
     * @param hql 脚本
     * @param pageSize 每页显示数量
     * @param pageNo 页码
     * @return
     */
    public Page<T> findByHql(String hql, int pageSize, int pageNo);
    
    /**
     * 数据更新
     * @param hql 更新脚本
     * @return
     */
	public void updateByHql(String hql);
	
	/**
	 * 数据删除
	 * @param hql 删除脚本
	 */
	public void deleteByHql(String hql);
	
	/**
	 * 根据一组主键查询实体集合
	 * @param pks
	 * @return
	 */
	public List<T> findListByPKs(ID[] pks);

	/**
	 * 缓存查询
	 * @param hql
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	@Cacheable("cacheMvweb")
	public Page<T> cacheFindByHql(String hql, int pageSize, int pageNo);
	
	/**
	 * 缓存清理
	 * @param hql
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	@CacheEvict(value = "cacheMvweb")
	public Page<T> flash(String hql, int pageSize, int pageNo);
}

