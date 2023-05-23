/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alphadot.authservice.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The type Role. Defines the role and the list of users who are associated with
 * that role
 */
@Entity(name = "ROLE")
public class Role {

	@Id
	@Column(name = "ROLE_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "ROLE_NAME")
	@Enumerated(EnumType.STRING)
	@NaturalId
	private RoleName role;

	@ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER, cascade = { CascadeType.MERGE })
	@JsonIgnore
	private Set<User> userList = new HashSet<>();

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE })
	@JoinTable(name = "API_ROLE_MAPPING", joinColumns = {
			@JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID") }, inverseJoinColumns = {
					@JoinColumn(name = "API_ID", referencedColumnName = "API_ID") })
	private Set<ApiInfo> apiInfoSet = new HashSet<>();

	public Set<ApiInfo> getApiInfoSet() {
		return apiInfoSet;
	}

	public void setApiInfoSet(Set<ApiInfo> apiInfoSet) {
		this.apiInfoSet = apiInfoSet;
	}

	public Role(RoleName role) {
		this.role = role;
	}

	public Role() {

	}

	public boolean isAdminRole() {
		return null != this && this.role.equals(RoleName.ROLE_ADMIN);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RoleName getRole() {
		return role;
	}

	public void setRole(RoleName role) {
		this.role = role;
	}

	public Set<User> getUserList() {
		return userList;
	}

	public void setUserList(Set<User> userList) {
		this.userList = userList;
	}
}
