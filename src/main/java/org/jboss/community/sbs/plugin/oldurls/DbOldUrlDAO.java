/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 */
package org.jboss.community.sbs.plugin.oldurls;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    /**
     *
     * @param system required
     * @param urlType not required
     * @param param1 required
     * @param param2 not required
     * @param param3 not required
     * @return
     */
	@Override
	public JiveObject getTarget(SYSTEM system, Integer urlType, String param1, String param2, String param3) {

        String query = "SELECT objectType, objectId FROM jbossOldUrl WHERE systemid = ? AND param1 = ?";

        List<Object> args = new ArrayList<Object>();
        args.add(system.getId());
        args.add(param1);

        if(urlType != null) {
            query +=  " AND urltype = ?";
            args.add(urlType);
        }

        if(param2 != null) {
            query +=  " AND param2 = ?";
            args.add(param2);
        }

        if(param3 != null) {
            query +=  " AND param3 = ?";
            args.add(param3);
        }

		return this.getSimpleJdbcTemplate().queryForObject(query, new DummyJiveObjectMapper(), args.toArray());
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
