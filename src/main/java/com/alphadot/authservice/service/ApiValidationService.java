package com.alphadot.authservice.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.log4j.Logger;
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

    private static final Logger logger = Logger.getLogger(ApiValidationService.class);

    public ApiInfo getApiInfo(String url, String methodType) {
        return apiInfoRepository.findByUriAndMethodType(url, methodType);
    }

    public boolean chechAccessablity(UriValidation uriValidation, Set<Role> roles, long userID) {
        logger.info("chechAccessablity started.");

        String url = getPartialUrl(uriValidation.getUri());
        logger.info("Requested url: " + url);
        List<String> requestUrlTokens = tokenize(url);
        String query = getUrlWithoutQueryParam(requestUrlTokens);
        logger.info("Query for URL: " + query);
        String finalQuery = query;
        List<ApiInfo> listOfApi = roles.stream().flatMap(r -> r.getApiInfoSet().stream()).distinct().filter(A -> {
            String dbUrl = A.getUri();
            String methodName = A.getMethodType();
            if (dbUrl.contains(finalQuery) && uriValidation.getMethedType().equalsIgnoreCase(methodName)) {
                return true;
            }
            return false;
        }).collect(Collectors.toList());

        logger.info("selected API from DB: " + listOfApi);

        boolean trigger = false;
        Map<String, Long> commingWildcards = new HashedMap<String, Long>();
        for (int i = 0; i < listOfApi.size(); i++) {
            List<String> dburlTokens = tokenize(listOfApi.get(i).getUri());
            if (compare(requestUrlTokens, dburlTokens, commingWildcards)) {
                if (!listOfApi.get(i).getIsValidationRequired()) {
                    logger.info("Validation is not required.");
                    trigger = true;
                    break;
                }
                if (commingWildcards.containsKey(WildCardEnum.LOGGEDIN_USER_ID.name())) {
                    logger.info("Wildcard found.");
                    if (commingWildcards.get(WildCardEnum.LOGGEDIN_USER_ID.name()) == userID) {
                        logger.info("Wildcard condition matched succesfully.");
                        trigger = true;
                        break;
                    }
                    logger.info("Wildcard condition not matched.");
                    trigger = false;
                    break;
                }
                logger.info("API matched with wildcard.");
                trigger = true;
                break;
            } else
                continue;
        }
        logger.info("chechAccessablity ends with result: " + trigger);
        return trigger;
    }

    private String getPartialUrl(String urlString) {
        try {
            URL url = new URL(urlString);
            urlString = url.getFile().toString();
           
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return urlString;
    }
    
    private String getUrlWithoutQueryParam(List<String> requestUrlTokens) {
        String query = "";
        int maxSize=requestUrlTokens.size();
        if(requestUrlTokens.size()>=6) {
        	maxSize=6;
        }
        for (int i = 0; i < maxSize; i++) {
        		query = query + requestUrlTokens.get(i);
        }
		return query;
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
        logger.info("compare starts");
        if (list1 == null || list2 == null) {
            logger.error("url not found.");
            return false;
        }
        boolean trigger = true;
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
                            trigger = false;
                            break;
                        }
                        continue;
                    }
                    trigger = false;
                    break;
                }
            }
        }
        logger.info("compare ends with result " + trigger);
        return trigger;
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
