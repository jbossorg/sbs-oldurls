/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 */
package org.jboss.community.sbs.plugin.oldurls;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jboss.community.sbs.plugin.oldurls.OldUrlManager.SYSTEM;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.jivesoftware.base.database.dao.JiveJdbcDaoSupport;
import com.jivesoftware.base.database.sequence.SequenceManager;
import com.jivesoftware.community.JiveObject;

/**
 * DB Implementation of {@link OldUrlDAO}
 *
 * @author Libor Krzyzanek (lkrzyzan)
 */
public class DbOldUrlDAO extends JiveJdbcDaoSupport implements OldUrlDAO {

	public static final int OLD_URL_SEQ = 5020;

	@Override
	public void addOldUrl(SYSTEM system, Integer urlType, String param1, String param2, String param3, JiveObject target) {
		long id = SequenceManager.nextID(OLD_URL_SEQ);

		getSimpleJdbcTemplate()
				.update(
						"INSERT INTO jbossOldUrl (id, systemid, urltype, param1, param2, param3, objectType, objectId) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
						id, system.getId(), urlType, param1, param2, param3, target.getObjectType(), target.getID());
	}

	@Override
	public JiveObject getTarget(SYSTEM system, Integer urlType, String param1, String param2, String param3) {
		return this.getSimpleJdbcTemplate().queryForObject(
				"SELECT objectType, objectId FROM jbossOldUrl "
						+ "WHERE systemid = ? AND urltype <=> ? AND param1 = ? AND param2 <=> ? AND param3 <=> ?",
				new DummyJiveObjectMapper(), system.getId(), urlType, param1, param2, param3);
	}

	private class DummyJiveObject implements JiveObject {
		private long id;
		private int objectType;

		public DummyJiveObject(long id, int objectType) {
			this.id = id;
			this.objectType = objectType;
		}

		@Override
		public long getID() {
			return id;
		}

		@Override
		public int getObjectType() {
			return objectType;
		}
	}

	private class DummyJiveObjectMapper implements ParameterizedRowMapper<JiveObject> {

		@Override
		public JiveObject mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new DummyJiveObject(rs.getLong("objectId"), rs.getInt("objectType"));
		}

	}

}
