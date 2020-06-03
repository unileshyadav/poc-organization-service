/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cymmetri.organization.service.impl;

import java.util.Date;
import java.util.List;

import com.cymmetri.organization.dto.Response;
import com.cymmetri.organization.entity.Organization;
import com.cymmetri.organization.repository.OrganizationRepository;
import com.cymmetri.organization.service.OrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrganizationServiceImpl implements OrganizationService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${user.service.url}")
	private String userServiceUrl;

	@Autowired
	private OrganizationRepository organizationRepository;

	@Override
	public List<Organization> findAll() {
		return this.organizationRepository.findAll();
	}

	@Override
	public Response findUser(String orgnization) {
		RestTemplate restTemplate = new RestTemplate();
		Response response = restTemplate
				.exchange(this.userServiceUrl, HttpMethod.GET, null, new ParameterizedTypeReference<Response>() {
				}, orgnization).getBody();

		this.logger.info("Response Received as " + response + " -  " + new Date());
		return response;
	}

}
