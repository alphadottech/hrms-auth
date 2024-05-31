package com.adt.authservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.Data;

@Entity(name = "api_details")
@Table(catalog = "hrms_sit", schema = "av_schema", name = "api_details")
@Data
public class ApiDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "api_details_seq")
	@SequenceGenerator(name = "api_details_seq", allocationSize = 1, schema = "av_schema")
	@Column(name = "api_id")
	private Integer apiId;
	@Column(name = "api_name")
	private String apiName;
	@Column(name = "method_type")
	private String methodType;
	@Column(name = "service_name")
	private String serviceName;

}
