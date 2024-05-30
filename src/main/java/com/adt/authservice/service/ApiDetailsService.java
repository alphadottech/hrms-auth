package com.adt.authservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.adt.authservice.model.ApiDetails;
import com.adt.authservice.repository.ApiDetailsRepository;
import com.adt.authservice.util.TableDataExtractor;

@Service
public class ApiDetailsService {

	@Autowired
	private ApiDetailsRepository apiDetailsRepository;

	@Autowired
	TableDataExtractor tableDataExtractor;

	public String saveApiDetails(ApiDetails apiDetails) {
		Optional<ApiDetails> apidetailsdata = apiDetailsRepository.findByApiName(apiDetails.getApiName());
		if (!apidetailsdata.isPresent()) {
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
			apiData.setApiName(apiDetails.getApiName());
			apiData.setMathodType(apiDetails.getMathodType());
			apiData.setServiceName(apiDetails.getServiceName());
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

	public List<ApiDetails> getAllApiDetails() {
		List<ApiDetails> apidetailsdata = apiDetailsRepository.findAll();
		return apidetailsdata;
	}

	public String addAndUpdateRoleMapping(List<String> apiNameList, String roleName) {
		String sql = "DELETE am FROM av_schema.api_mapping am JOIN user_schema.role r ON am.ROLE_ID = r.role_id WHERE r.role_name ="
				+ "'" + roleName + "'";
		   tableDataExtractor.insertDataFromTable(sql);
		for (String apiName : apiNameList) {
			String sql1 = "INSERT INTO av_schema.api_mapping (api_id, role_id) SELECT ad.api_id, r.role_id FROM av_schema.api_details ad JOIN user_schema.role r ON r.role_name ="
					+ "'" + roleName + "'" + " WHERE ad.api_name =" + "'" + apiName + "'";
			tableDataExtractor.insertDataFromTable(sql1);
			return "Data update successfully";
		}
		return "data not present";

	}

}
