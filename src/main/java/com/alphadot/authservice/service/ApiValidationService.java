package com.alphadot.authservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alphadot.authservice.model.ApiInfo;
import com.alphadot.authservice.model.Role;
import com.alphadot.authservice.model.UriValidation;
import com.alphadot.authservice.repository.ApiInfoRepository;
import com.alphadot.authservice.util.WildCardEnum;

@Service
public class ApiValidationService {
	@Autowired
	private ApiInfoRepository apiInfoRepository;

	public ApiInfo getApiInfo(String url, String methodType) {
		return apiInfoRepository.findByUriAndMethodType(url, methodType);
	}

	public boolean chechAccessablity(UriValidation uriValidation, Set<Role> roles, long userID) {

		String url = uriValidation.getUri();
		List<String> requestUrlTokens = tokenize(url);
		String query = "";
		for (int i = 0; i < 6; i++) {
			query = query + requestUrlTokens.get(i);
		}

		String finalQuery = query;
		List<ApiInfo> listOfApi = roles.stream().flatMap(r -> r.getApiInfoSet().stream()).distinct().filter(A -> {
			String dbUrl = A.getUri();
			String methodName = A.getMethodType();
			if (dbUrl.contains(finalQuery) && uriValidation.getMethedType().equalsIgnoreCase(methodName)) {
				return true;
			}
			return false;
		}).collect(Collectors.toList());

		Map<String, Long> commingWildcards = new HashedMap<String, Long>();
		for (int i = 0; i < listOfApi.size(); i++) {
			List<String> dburlTokens = tokenize(listOfApi.get(i).getUri());
			if (compare(requestUrlTokens, dburlTokens, commingWildcards)) {
				if(!listOfApi.get(i).getIsValidationRequired()) {
					return true;
				} 
				if (commingWildcards.containsKey(WildCardEnum.LOGGEDIN_USER_ID.name())) {
					if (commingWildcards.get(WildCardEnum.LOGGEDIN_USER_ID.name()) == userID) {
						return true;
					}
					return false;
				}
				return true;
			} else
				continue;
		}
		return false;
	}

	private List<String> tokenize(String url) {
		String[] tokens = url.split("/");
		String[] tokens2 = new String[10];
		List<String> list = new ArrayList<>();
		boolean flag = true;
		for (String s : tokens) {
			s = s != null ? s.trim() : "";
			if (s.contains("?")) {
				flag = false;
				int index = s.indexOf('?');
				tokens2[0] = s.substring(0, index);
				tokens2[1] = s.substring(index, index + 1);
				tokens2[2] = s.substring(index + 1);
				list.add("/");
				list.add(tokens2[0]);
				list.add(tokens2[1]);
				String[] slt = tokens2[2].split("&");
				boolean f = false;
				for (String ss : slt) {
					String arr[] = ss.split("=");
					if (f)
						list.add("&");
					f = true;
					list.add(arr[0]);
					list.add("=");
					list.add(arr[1]);
				}
			}
			if (flag & !s.isEmpty()) {
				list.add("/");
				list.add(s);
				flag = true;
			}
		}
		return list;
	}

	private boolean compare(List<String> list1, List<String> list2, Map<String, Long> commingVildcards) {
		if (list1 == null || list2 == null) {
			return false;
		}
		if (list1.size() == list2.size()) {
			for (int i = 0; i < list1.size(); i++) {
				if (!list1.get(i).equals(list2.get(i))) {
					if (isVaildCard(list2.get(i))) {
						String s = getValue(list2.get(i));
						if (!s.equals("*")) {
							if (WildCardEnum.isAvalableEnum(s)) {
								commingVildcards.put(s, Long.parseLong(list1.get(i)));
								continue;
							}
							return false;
						}
						continue;
					}
					return false;
				}
			}
		}
		return true;
	}

	private boolean isVaildCard(String s) {
		if (s.charAt(0) == '{')
			return true;
		return false;
	}

	private String getValue(String s) {
		return s.substring(1, s.length() - 1);
	}
}
