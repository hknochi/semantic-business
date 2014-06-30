package com.hknochi.gae.util;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.appengine.api.memcache.MemcacheService;

@Repository
public class RDFRepository {

	@Autowired
	private MemcacheService memcacheService;

	@PersistenceContext
	private EntityManager entityManager;

	public MemcacheService getMemcacheService() {
		return memcacheService;
	}

	public void setMemcacheService(MemcacheService memcacheService) {
		this.memcacheService = memcacheService;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	
}
