/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.site.navigation.admin.web.internal.portlet;

import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.site.navigation.admin.constants.SiteNavigationAdminPortletKeys;
import com.liferay.site.navigation.admin.web.internal.constants.SiteNavigationAdminWebKeys;
import com.liferay.site.navigation.admin.web.internal.display.context.SiteNavigationAdminDisplayContext;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalService;
import com.liferay.site.navigation.service.SiteNavigationMenuService;
import com.liferay.site.navigation.type.SiteNavigationMenuItemTypeRegistry;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-navigation-admin",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Navigation",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + SiteNavigationAdminPortletKeys.SITE_NAVIGATION_ADMIN,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=guest,power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class SiteNavigationAdminPortlet extends MVCPortlet {

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		LiferayPortletRequest liferayPortletRequest =
			_portal.getLiferayPortletRequest(renderRequest);
		LiferayPortletResponse liferayPortletResponse =
			_portal.getLiferayPortletResponse(renderResponse);
		HttpServletRequest request = _portal.getHttpServletRequest(
			renderRequest);

		SiteNavigationAdminDisplayContext siteNavigationAdminDisplayContext =
			new SiteNavigationAdminDisplayContext(
				liferayPortletRequest, liferayPortletResponse, request,
				_siteNavigationMenuItemTypeRegistry,
				_siteNavigationMenuLocalService, _siteNavigationMenuService);

		renderRequest.setAttribute(
			SiteNavigationAdminWebKeys.
				SITE_NAVIGATION_MENU_ADMIN_DISPLAY_CONTEXT,
			siteNavigationAdminDisplayContext);

		renderRequest.setAttribute(
			SiteNavigationAdminWebKeys.RESOLVED_MODULE_NAME,
			_npmResolver.resolveModuleName("site-navigation-admin-web"));

		super.doDispatch(renderRequest, renderResponse);
	}

	@Reference
	private NPMResolver _npmResolver;

	@Reference
	private Portal _portal;

	@Reference
	private SiteNavigationMenuItemTypeRegistry
		_siteNavigationMenuItemTypeRegistry;

	@Reference
	private SiteNavigationMenuLocalService _siteNavigationMenuLocalService;

	@Reference
	private SiteNavigationMenuService _siteNavigationMenuService;

}