package com.alphadot.authservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alphadot.authservice.model.ApiInfo;

@Repository
public interface ApiInfoRepository extends JpaRepository<ApiInfo, Integer> {

	public ApiInfo findByUriAndMethodType(String url, String methodType);

	// public List<ApiInfo> findbyUriLike(String uri);

	// public ApiInfo findByUri(String uri);

	// @Query(value="Select * from API_INFO Where uri LIKE %:uri% AND
	// method_type=:methodType", nativeQuery=true)
	@Query(value = "select * from api_info a Where a.uri LIKE %:uri% AND a.method_type=:methodType", nativeQuery = true)
	public List<ApiInfo> findUrlUsingNative(@Param("uri") String uri, @Param("methodType") String methodType);

//	public List<ApiInfo> findAllByUriLikeAndMethodType(@Param("uri") String uri,
//			@Param("methodType") String methodType);

}
