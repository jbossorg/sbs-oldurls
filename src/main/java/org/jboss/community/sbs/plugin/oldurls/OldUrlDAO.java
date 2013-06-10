/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 */
package org.jboss.community.sbs.plugin.oldurls;

import org.jboss.community.sbs.plugin.oldurls.OldUrlManager.SYSTEM;
import org.springframework.dao.EmptyResultDataAccessException;

import com.jivesoftware.community.JiveObject;

/**
 * DAO for Old URL mappings
 *
 * @author Libor Krzyzanek (lkrzyzan)
 */
public interface OldUrlDAO {

	/**
	 * Add Old URL mapping
	 *
	 * @param system  system for which URL belongs
	 * @param urlType url type - can be null
	 * @param param1  URL parameter 1
	 * @param param2  URL parameter 2
	 * @param param3  URL parameter 3
	 * @param target  jive object
	 */
	public void addOldUrl(SYSTEM system, Integer urlType, String param1, String param2, String param3, JiveObject target);

	/**
	 * Get targeted object
	 *
	 * @param system
	 * @param urlType
	 * @param param1
	 * @param param2
	 * @param param3
	 * @return
	 * @throws EmptyResultDataAccessException in case that no entry found
	 */
	public JiveObject getTarget(SYSTEM system, Integer urlType, String param1, String param2, String param3)
			throws EmptyResultDataAccessException;

}
