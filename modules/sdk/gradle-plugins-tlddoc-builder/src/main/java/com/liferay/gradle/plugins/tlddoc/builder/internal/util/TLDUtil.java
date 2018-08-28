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

package com.liferay.gradle.plugins.tlddoc.builder.internal.util;

import java.io.File;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Peter Shin
 */
public class TLDUtil {

	public static Map<String, File> getDTDProperties(File file)
		throws Exception {

		String fileName = file.getName();

		if (!fileName.endsWith(".tld")) {
			return Collections.emptyMap();
		}

		Document document = _getDocument(file);

		if (document == null) {
			return Collections.emptyMap();
		}

		DocumentType documentType = document.getDoctype();

		if (documentType == null) {
			return Collections.emptyMap();
		}

		String publicId = documentType.getPublicId();

		if (publicId == null) {
			return Collections.emptyMap();
		}

		String definitionFileName = _getFileName(documentType.getSystemId());

		if (definitionFileName == null) {
			return Collections.emptyMap();
		}

		if (!_portalDefinitions.containsKey(definitionFileName)) {
			return Collections.emptyMap();
		}

		Map<String, File> dtdProperties = new HashMap<>();

		dtdProperties.put(publicId, _portalDefinitions.get(definitionFileName));

		return dtdProperties;
	}

	public static Map<String, File> getSchemaProperties(File file)
		throws Exception {

		String fileName = file.getName();

		if (!fileName.endsWith(".tld")) {
			return Collections.emptyMap();
		}

		Document document = _getDocument(file);

		if (document == null) {
			return Collections.emptyMap();
		}

		Map<String, File> schemaProperties = new HashMap<>();

		NodeList nodeList = document.getElementsByTagName("taglib");

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node taglibNode = nodeList.item(i);

			NamedNodeMap namedNodeMap = taglibNode.getAttributes();

			Node schemLocationNode = namedNodeMap.getNamedItem(
				"xsi:schemaLocation");

			if (schemLocationNode == null) {
				continue;
			}

			String schemLocation = schemLocationNode.getNodeValue();

			if (schemLocation == null) {
				continue;
			}

			String[] values = schemLocation.split("\\s+");

			if (values.length != 2) {
				continue;
			}

			String definitionFileName = _getFileName(values[1]);

			if (definitionFileName == null) {
				continue;
			}

			if (!_portalDefinitions.containsKey(definitionFileName)) {
				continue;
			}

			File definitionFile = _portalDefinitions.get(definitionFileName);

			schemaProperties.put(values[0].trim(), definitionFile);

			_populateSchemaProperties(
				schemaProperties, _portalDefinitions, definitionFile);
		}

		return schemaProperties;
	}

	private static Document _getDocument(File file) throws Exception {
		DocumentBuilderFactory documentBuilderFactory =
			DocumentBuilderFactory.newInstance();

		documentBuilderFactory.setFeature(_LOAD_EXTERNAL_DTD, false);

		DocumentBuilder documentBuilder =
			documentBuilderFactory.newDocumentBuilder();

		return documentBuilder.parse(file);
	}

	private static String _getFileName(String s) {
		if (s == null) {
			return null;
		}

		String trimmedString = s.trim();

		int index = trimmedString.lastIndexOf('/');

		if (index == -1) {
			return null;
		}

		return trimmedString.substring(index + 1);
	}

	private static void _populateSchemaProperties(
			Map<String, File> schemaProperties,
			Map<String, File> portalDefinitions, File definitionFile)
		throws Exception {

		Document document = _getDocument(definitionFile);

		if (document == null) {
			return;
		}

		NodeList importNodeList = document.getElementsByTagName("xsd:import");

		for (int i = 0; i < importNodeList.getLength(); i++) {
			Node importNode = importNodeList.item(i);

			NamedNodeMap namedNodeMap = importNode.getAttributes();

			Node namespaceNode = namedNodeMap.getNamedItem("namespace");

			if (namespaceNode == null) {
				continue;
			}

			Node schemLocationNode = namedNodeMap.getNamedItem(
				"schemaLocation");

			if (schemLocationNode == null) {
				continue;
			}

			String namespace = namespaceNode.getNodeValue();
			String schemaLocation = schemLocationNode.getNodeValue();

			if ((namespace == null) || (schemaLocation == null)) {
				continue;
			}

			String fileName = _getFileName(schemaLocation);

			if (fileName == null) {
				continue;
			}

			if (!portalDefinitions.containsKey(fileName)) {
				continue;
			}

			File curDefinitionFile = portalDefinitions.get(fileName);

			schemaProperties.put(namespace, curDefinitionFile);

			_populateSchemaProperties(
				schemaProperties, portalDefinitions, curDefinitionFile);
		}

		NodeList includeNodeList = document.getElementsByTagName("xsd:include");

		for (int i = 0; i < includeNodeList.getLength(); i++) {
			Node taglibNode = includeNodeList.item(i);

			NamedNodeMap namedNodeMap = taglibNode.getAttributes();

			Node schemLocationNode = namedNodeMap.getNamedItem(
				"schemaLocation");

			if (schemLocationNode == null) {
				continue;
			}

			String schemaLocation = schemLocationNode.getNodeValue();

			if (schemaLocation == null) {
				continue;
			}

			if (!portalDefinitions.containsKey(schemaLocation)) {
				continue;
			}

			File curDefinitionFile = portalDefinitions.get(schemaLocation);

			_populateSchemaProperties(
				schemaProperties, portalDefinitions, curDefinitionFile);
		}
	}

	private static final String _LOAD_EXTERNAL_DTD =
		"http://apache.org/xml/features/nonvalidating/load-external-dtd";

	private static final Map<String, File> _portalDefinitions = new HashMap<>();

	static {
		if (System.getProperty("gradle.user.home") != null) {
			File definitionsDir = new File(
				System.getProperty("gradle.user.home") + "/../definitions");

			if (definitionsDir.exists()) {
				for (File definitionFile : definitionsDir.listFiles()) {
					String definitionFileName = definitionFile.getName();

					_portalDefinitions.put(definitionFileName, definitionFile);
				}
			}
		}
	}

}