package com.blueshit.neweast.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * 数据库交互基类
 * @author 9527
 * @version 1.0
 * @email zhaohuaan0925@163.com
 * @created 2015/5/11 13:38
 * @description
 */
public class MvWebBaseRepositoryImpl<T, ID extends Serializable>
        extends SimpleJpaRepository<T, ID> implements MvWebBaseRepository<T, ID> {
    @PersistenceContext
    private EntityManager em;

    // There are two constructors to choose from, either can be used.
    public MvWebBaseRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);

        // This is the recommended method for accessing inherited class dependencies.
        this.em = entityManager;
    }

    //通过HQL查询数据列表
    @SuppressWarnings("unchecked")
    @Override
    public List<T> whFindByList(String hql) {
        Query query = this.em.createQuery(hql);
        return (List<T>) query.getResultList();
    }

    //通过HQL查询数据列表对象
    @Override
    public Object findByList(String hql) {
        Query query = this.em.createQuery(hql);
        return (Object) query.getResultList();
    }

    //根据hql查询分页，参数每页显示数量、页码
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public Page<T> findByHql(String hql, int pageSize, int pageNo) {
        Page<T> page = null;
        Query query = em.createQuery(hql);
        int total = emHql(hql);
        // 设置查询结果的结束记录数
        int maxResults = pageSize;
        query.setMaxResults(maxResults);
        // 设置查询结果的开始记录数（从0开始计数）
        int firstResult = (pageNo - 1) * pageSize;
        query.setFirstResult(firstResult);
        List<T> rlist = query.getResultList();
        page = new PageImpl(rlist, new PageRequest(pageNo - 1, pageSize), total);

        return page;
    }

    //通过HQL更新数据
    @Override
    public void updateByHql(String hql) {
        Query query = this.em.createQuery(hql);
        query.executeUpdate();
    }

    //通过HQL删除数据
    @Override
    public void deleteByHql(String hql) {
        Query query = this.em.createQuery(hql);
        query.executeUpdate();
    }

    //通过主键查询数据列表
    @SuppressWarnings("unchecked")
    @Override
    public List<T> findListByPKs(ID[] pks){
        Iterable<ID> pk = (Iterable<ID>) Arrays.asList(pks).iterator();
        List<T> list = findAll(pk);
        return list;
    }
    
    //查询数据条数
    public int emHql(String hql) {
        Query query = em.createQuery(hql);
        return query.getResultList().size();
    }

	@Override
	@SuppressWarnings({"rawtypes", "unchecked"})
	public Page<T> cacheFindByHql(String hql, int pageSize, int pageNo) {
	        Page<T> page = null;
	        Query query = em.createQuery(hql);
	        int total = emHql(hql);
	        // 设置查询结果的结束记录数
	        int maxResults = pageSize;
	        query.setMaxResults(maxResults);
	        // 设置查询结果的开始记录数（从0开始计数）
	        int firstResult = (pageNo - 1) * pageSize;
	        query.setFirstResult(firstResult);
	        List<T> rlist = query.getResultList();
	        page = new PageImpl(rlist, new PageRequest(pageNo - 1, pageSize), total);
	        return page;
	}
	
	@Override
	@SuppressWarnings({"rawtypes", "unchecked"})
	public Page<T> flash(String hql, int pageSize, int pageNo) {
	        Page<T> page = null;
	        Query query = em.createQuery(hql);
	        int total = emHql(hql);
	        // 设置查询结果的结束记录数
	        int maxResults = pageSize;
	        query.setMaxResults(maxResults);
	        // 设置查询结果的开始记录数（从0开始计数）
	        int firstResult = (pageNo - 1) * pageSize;
	        query.setFirstResult(firstResult);
	        List<T> rlist = query.getResultList();
	        page = new PageImpl(rlist, new PageRequest(pageNo - 1, pageSize), total);
	        return page;
	}
}






