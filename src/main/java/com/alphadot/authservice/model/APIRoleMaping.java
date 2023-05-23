package com.alphadot.authservice.model;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(catalog = "EmployeeDB", schema = "user_schema", name = "API_USER_MAP ")
@Entity(name = "API_USER_MAP")
public class APIRoleMaping {
	@Id
	@Column(name = "API_ID")
	private Integer apiId;
	@Column(name = "ROLE_ID")
	private Long roleId;
	public Integer getApiId() {
		return apiId;
	}
	public void setApiId(Integer apiId) {
		this.apiId = apiId;
	}
	public	Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	@Override
	public String toString() {
		return "APIRoleMaping [apiId=" + apiId + ", roleId=" + roleId + "]";
	}
	
}
