package com.alphadot.authservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(catalog = "EmployeeDB", schema = "user_schema", name = "API_INFO ")
@Entity(name = "API_INFO")
public class ApiInfo {
	@Id
	@Column(name = "API_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "METHOD_TYPE")
	private String methodType;

	@Column(name = "URI")
	private String uri;

	@Column(name = "ISVALIDATIONREQUIRED")
	private Boolean isValidationRequired;

	public ApiInfo() {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ApiInfo other = (ApiInfo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMethodType() {
		return methodType;
	}

	public void setMethodType(String methodType) {
		this.methodType = methodType;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Boolean getIsValidationRequired() {
		return isValidationRequired;
	}

	public void setIsValidationRequired(Boolean isValidationRequired) {
		this.isValidationRequired = isValidationRequired;
	}

	public ApiInfo(Integer id, String methodType, String uri, Boolean isValidationRequired) {
		super();
		this.id = id;
		this.methodType = methodType;
		this.uri = uri;
		this.isValidationRequired = isValidationRequired;
	}

}
