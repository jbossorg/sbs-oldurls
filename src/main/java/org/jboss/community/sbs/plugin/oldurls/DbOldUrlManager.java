/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 */
package org.jboss.community.sbs.plugin.oldurls;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;

import com.jivesoftware.community.JiveObject;
import com.jivesoftware.community.JiveObjectLoader;
import com.jivesoftware.community.NotFoundException;

/**
 * DB Implementation of {@link OldUrlManager} interface.
 *
 * @author Libor Krzyzanek (lkrzyzan)
 */
public class DbOldUrlManager implements OldUrlManager {

	private static final Logger log = LogManager.getLogger(DbOldUrlManager.class);

	private OldUrlDAO oldUrlDAO;

	private JiveObjectLoader jiveObjectLoader;

	@Override
	public JiveObject getTargetObject(SYSTEM system, Integer urlType, String param1, String param2, String param3) {
		try {
			JiveObject dummyJiveObject = oldUrlDAO.getTarget(system, urlType, param1, param2, param3);
			try {
				return jiveObjectLoader.getJiveObject(dummyJiveObject.getObjectType(), dummyJiveObject.getID());
			} catch (NotFoundException e) {
				return null;
			}
		} catch (EmptyResultDataAccessException e) {
			log.debug("No mapping found in DB");
			return null;
		}
	}

	@Override
	public void addOldUrl(SYSTEM system, Integer urlType, String param1, String param2, String param3, JiveObject target) {
		oldUrlDAO.addOldUrl(system, urlType, param1, param2, param3, target);
	}

	public void setOldUrlDAO(OldUrlDAO oldUrlDAO) {
		this.oldUrlDAO = oldUrlDAO;
	}

	public void setJiveObjectLoader(JiveObjectLoader jiveObjectLoader) {
		this.jiveObjectLoader = jiveObjectLoader;
	}

}
