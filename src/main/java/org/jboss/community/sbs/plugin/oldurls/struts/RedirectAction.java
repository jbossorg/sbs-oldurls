/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 */
package org.jboss.community.sbs.plugin.oldurls.struts;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jboss.community.sbs.plugin.oldurls.OldUrlManager;

import com.jivesoftware.community.JiveObject;
import com.jivesoftware.community.action.JiveActionSupport;
import com.jivesoftware.community.web.JiveResourceResolver;

/**
 * @author Libor Krzyzanek (lkrzyzan)
 */
public class RedirectAction extends JiveActionSupport {

	private static final Logger log = LogManager.getLogger(RedirectAction.class);

	private static final long serialVersionUID = -7637305739596569258L;

	private String urlToRedirect;

	private Integer sid;

	private Integer urlType;

	private String p1;

	private String p2;

	private String p3;

	private OldUrlManager oldUrlManager;

	@Override
	public String execute() {
		if (sid == null || p1 == null) {
			log.debug("sid and p1 is not filled");
			return NOTFOUND;
		}

		OldUrlManager.SYSTEM system;
		if (sid.intValue() == OldUrlManager.SYSTEM.SFWK.getId()) {
			system = OldUrlManager.SYSTEM.SFWK;
		} else {
			log.debug("unknown system id");
			return NOTFOUND;
		}

		JiveObject jiveObject = oldUrlManager.getTargetObject(system, urlType, p1, p2, p3);

		if (jiveObject == null) {
			return NOTFOUND;
		}

		urlToRedirect = JiveResourceResolver.getJiveObjectURL(jiveObject, true);

		return SUCCESS;
	}

	public String getUrlToRedirect() {
		return urlToRedirect;
	}

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public Integer getUrlType() {
		return urlType;
	}

	public void setUrlType(Integer urlType) {
		this.urlType = urlType;
	}

	public String getP1() {
		return p1;
	}

	public void setP1(String p1) {
		this.p1 = p1;
	}

	public String getP2() {
		return p2;
	}

	public void setP2(String p2) {
		this.p2 = p2;
	}

	public String getP3() {
		return p3;
	}

	public void setP3(String p3) {
		this.p3 = p3;
	}

	public void setUrlToRedirect(String urlToRedirect) {
		this.urlToRedirect = urlToRedirect;
	}

	public void setOldUrlManager(OldUrlManager oldUrlManager) {
		this.oldUrlManager = oldUrlManager;
	}

	public OldUrlManager getOldUrlManager() {
		return oldUrlManager;
	}

}
