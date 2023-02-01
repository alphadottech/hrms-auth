package com.alphadot.authservice.model.payload;

public class LoginResponse {
	private JwtAuthenticationResponse jwtAuthenticationResponse;

	private Long employeeId;

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

}
