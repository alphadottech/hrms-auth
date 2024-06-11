package com.adt.authservice.model.payload;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.adt.authservice.model.Role;

public class LoginResponse {
	private JwtAuthenticationResponse jwtAuthenticationResponse;

	private Long employeeId;

	private String employeeName;

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	private Set<Role> roles = new HashSet<>();
	
	private List<ApiNameResponse> apiNameResponse;

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public JwtAuthenticationResponse getJwtAuthenticationResponse() {
		return jwtAuthenticationResponse;
	}

	public void setJwtAuthenticationResponse(JwtAuthenticationResponse jwtAuthenticationResponse) {
		this.jwtAuthenticationResponse = jwtAuthenticationResponse;
	}

	public List<ApiNameResponse> getApiNameResponse() {
		return apiNameResponse;
	}

	public void setApiNameResponse(List<ApiNameResponse> apiNameResponse) {
		this.apiNameResponse = apiNameResponse;
	}
	

}
