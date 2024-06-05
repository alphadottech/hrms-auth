package com.adt.authservice.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.adt.authservice.model.ApiDetails;
import com.adt.authservice.model.Role;
import com.adt.authservice.model.payload.ApiNameResponse;
import com.adt.authservice.repository.ApiDetailsRepository;
import com.adt.authservice.util.TableDataExtractor;

@Service
public class ApiDetailsService {

	@Autowired
	private ApiDetailsRepository apiDetailsRepository;

	@Autowired
	TableDataExtractor tableDataExtractor;
	
	    private static final int MAX_PAGE_SIZE = 50;
	    private static final int DEFAULT_PAGE_SIZE = 10;

	public String saveApiDetails(ApiDetails apiDetails) {
		Optional<ApiDetails> apidetailsdata = apiDetailsRepository.findByApiName(apiDetails.getApiName());
		if (!apidetailsdata.isPresent()) {
			apiDetails.setApiName(apiDetails.getApiName().toUpperCase());
			apiDetailsRepository.save(apiDetails);
			return "Data saved successfuly";
		} else {
			return "API name is alredy present ";

		}

	}

	public String updateApiDetails(ApiDetails apiDetails) {
		Optional<ApiDetails> apidetailsdata = apiDetailsRepository.findByApiId(apiDetails.getApiId());
		if (apidetailsdata.isPresent()) {
			ApiDetails apiData = apidetailsdata.get();
			apiData.setApiName(apiDetails.getApiName().toUpperCase());
			apiData.setMethodType(apiDetails.getMethodType());
			apiData.setServiceName(apiDetails.getServiceName());
			apiDetailsRepository.save(apiData);
			return "API data updated successfully";

		}
		return "API data not present";

	}

	public String deleteApiDetails(String apiName) {
		Optional<ApiDetails> apidetailsdata = apiDetailsRepository.findByApiName(apiName);
		if (apidetailsdata.isPresent()) {
			apiDetailsRepository.deleteByApiName(apiName);

			return "Data deleted successfully";
		}
		return "API data not present";
	}

	public ResponseEntity<?> getApiDetailsByName(String apiName) {
		Optional<ApiDetails> apidetailsdata = apiDetailsRepository.findByApiName(apiName);

		if (apidetailsdata.isPresent()) {
			ApiDetails apiDetail = apidetailsdata.get();
			return new ResponseEntity<>(apiDetail, HttpStatus.OK);
		}
		return new ResponseEntity<>("Data not present", HttpStatus.OK);

	}

	public Page<ApiDetails> getAllApiDetails(int page, int size) {
		  if (size <= 0 || size > MAX_PAGE_SIZE) {
              size = DEFAULT_PAGE_SIZE;
          }
		Pageable pageable = PageRequest.of(page, size);

		return apiDetailsRepository.findAll(pageable);
	}

	public String addAndUpdateRoleMapping(List<String> apiNameList, String roleName) {
		String sql = "DELETE FROM av_schema.api_mapping am USING user_schema.role r WHERE am.ROLE_ID = r.role_id AND r.role_name ="
				+ "'" + roleName + "'";
		tableDataExtractor.insertDataFromTable(sql);
		for (String apiName : apiNameList) {
			String sql1 = "INSERT INTO av_schema.api_mapping (api_id, role_id) SELECT ad.api_id, r.role_id FROM av_schema.api_details ad JOIN user_schema.role r ON r.role_name ="
					+ "'" + roleName + "'" + " WHERE ad.api_name =" + "'" + apiName + "'";
			tableDataExtractor.insertDataFromTable(sql1);

		}
		return "Data update successfully";

	}
	
	
	public ResponseEntity<?> getListOfApiNameByRole(String roleName) {
		String sql = "SELECT ad.api_name FROM av_schema.api_details ad JOIN av_schema.api_mapping am ON ad.api_id = am.api_id JOIN user_schema.role r ON am.role_id = r.role_id WHERE r.role_name ="
				+ "'" + roleName + "'";
		List<Map<String, Object>> apiDate = tableDataExtractor.extractDataFromTable(sql);
		return new ResponseEntity<>(apiDate, HttpStatus.OK);
	}
	
	public List<ApiNameResponse> getListOfApiName(Set<Role> roles) {
		if (roles == null || roles.isEmpty())
			return Collections.emptyList();

		String roleNamesInSql = roles.stream().map(Role::getRole).map(roleName -> "'" + roleName + "'")
				.collect(Collectors.joining(", "));

		String sql = "SELECT r.role_name, ad.api_name FROM av_schema.api_details ad "
				+ "JOIN av_schema.api_mapping am ON ad.api_id = am.api_id "
				+ "JOIN user_schema.role r ON am.role_id = r.role_id " + "WHERE r.role_name IN (" + roleNamesInSql
				+ ")";

		List<Map<String, Object>> apiData = tableDataExtractor.extractDataFromTable(sql);

		Map<String, Set<String>> roleApiMap = apiData.stream()
				.collect(Collectors.groupingBy(row -> String.valueOf(row.get("role_name")),
						Collectors.mapping(row -> String.valueOf(row.get("api_name")), Collectors.toSet())));

		return roles.stream().map(role -> {
			ApiNameResponse response = new ApiNameResponse();
			response.setRoleName(role.getRole());
			response.setPermission(roleApiMap.getOrDefault(role.getRole(), Collections.emptySet()));
			return response;
		}).collect(Collectors.toList());
	}

}
