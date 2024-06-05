package com.adt.authservice.model.payload;

import java.util.Set;

import lombok.Data;

@Data
public class ApiNameResponse {

	private String roleName;

	private Set<String> permission;

}