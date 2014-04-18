/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 */
package org.jboss.community.sbs.plugin.oldurls;

import com.jivesoftware.community.JiveObject;

/**
 * Business logic for Old URL plugin.
 *
 * @author Libor Krzyzanek (lkrzyzan)
 */
public interface OldUrlManager {

	/**
	 * Enumeration of systems
	 */
	public enum SYSTEM {
		SFWK(1),
        FUSE(2);

		private SYSTEM(int id) {
			this.id = id;
		}

		private int id;

		public int getId() {
			return id;
		}

	}

	/**
	 * Get target jive object based on parameters
	 *
	 * @param system  system from which URL comes
	 * @param urlType type of URL. can be null
	 * @param param1  URL parameter 1
	 * @param param2  URL parameter 2
	 * @param param3  URL parameter 3
	 * @return target jive object or null if nothing found
	 */
	public JiveObject getTargetObject(SYSTEM system, Integer urlType, String param1, String param2, String param3);

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

}
