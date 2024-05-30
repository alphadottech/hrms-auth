package com.adt.authservice.controller;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adt.authservice.model.ApiDetails;
import com.adt.authservice.model.Role;
import com.adt.authservice.service.ApiDetailsService;
import com.adt.authservice.service.RoleService;

@RestController
@RequestMapping("/api/role")
public class RoleController {
	
	@Autowired
	public  RoleService roleService;
	
	@Autowired
	public ApiDetailsService apiDetailsService;
	
   @PreAuthorize("@auth.allow('GET_ALL_ROLE')")
	@GetMapping("/getAllRoles")
	public ResponseEntity<Set<Role>> getAllRoles() {
		return new ResponseEntity<Set<Role>>(roleService.findAll(), HttpStatus.OK);
	}
    @PreAuthorize("@auth.allow('CREATE_ROLE')")
	@PostMapping("/createRole")
	public ResponseEntity<?> createRole(@RequestBody Role role) {
		if (role.getRole().matches("^[a-zA-Z_]*$")) {
			return new ResponseEntity<>(roleService.savaRole(role), HttpStatus.OK);
		}
		return new ResponseEntity<>("Role name should contain only characters", HttpStatus.OK);

	}
    @PreAuthorize("@auth.allow('UPDATE_ROLE')")
	@Transactional
	@PutMapping("/updateRole")
	public ResponseEntity<?> updateRole(@RequestBody Role role) {
		if (role.getRole().matches("^[a-zA-Z_]*$")) {
			return new ResponseEntity<>(roleService.updateRole(role), HttpStatus.OK);
		}
		return new ResponseEntity<>("Role name should contain only characters", HttpStatus.OK);

	}
    @PreAuthorize("@auth.allow('DELETE_ROLE')")
	@Transactional
	@DeleteMapping("/deleteRole")
	public ResponseEntity<String> deleteRole(@Param(value = "roleId") Long roleId) {
		return new ResponseEntity<>(roleService.deleteRole(roleId), HttpStatus.OK);

	}
    @PreAuthorize("@auth.allow('GET_ROLE_BY_ID')")
	@GetMapping("/getRoleById")
	public ResponseEntity<?> getRole(@Param(value = "roleId") Long roleId) {
		return new ResponseEntity<>(roleService.getRole(roleId), HttpStatus.OK);
	}
	
    @PreAuthorize("@auth.allow('GET_ROLE_ASSING_BY_EMPLOYEE')")
	@GetMapping("/getRoleAssingByEmployee")
	public ResponseEntity<?> getEmployeeAssosiateRole(@Param(value = "employeeId") Long employeeId){		
		return new ResponseEntity<>(roleService.getRoleAssosiateEmployee(employeeId), HttpStatus.OK);
	}
    @PreAuthorize("@auth.allow('UPDATE_ROLE_OF_EMPLOYEE')")
	@PutMapping("/updateRoleOfEmployee")
	public ResponseEntity<?> updateRoleOfUser(@Param(value = "employeeId") Long employeeId, @RequestBody List<String> roleName){
		return new ResponseEntity<>(roleService.updateRoleOfemployee(employeeId,roleName), HttpStatus.OK);
		
	}
    @PreAuthorize("@auth.allow('DELETE_ASSOSIATED_ROLE')")
	@DeleteMapping("/deleteAssosiatedRole")
	public ResponseEntity<String> deleteAsosiatedRole(@Param(value = "employeeId") Long employeeId,@Param(value = "roleName") String roleName) {
		return new ResponseEntity<>(roleService.deleteRoleAssosiatedByEmployee(employeeId,roleName), HttpStatus.OK);

	}
    @PreAuthorize("@auth.allow('SAVE_API_DETAILS')")
	@PostMapping("/saveApiDetails")
	public ResponseEntity<String> saveApiDetails(@RequestBody ApiDetails apidetails){
		return new ResponseEntity<>(apiDetailsService.saveApiDetails(apidetails), HttpStatus.OK);
		
	}
    @PreAuthorize("@auth.allow('UPDATE_API_DETAILS')")
	@PutMapping("/updateApiDetails")
	public ResponseEntity<String> updateApiDetails(@RequestBody ApiDetails apidetails){
		return new ResponseEntity<>(apiDetailsService.updateApiDetails(apidetails), HttpStatus.OK);	
	}
	
    @PreAuthorize("@auth.allow('DELETE_API_DETAIL_DATA')")
	@DeleteMapping("/deleteApiDetailData")
    public ResponseEntity<String> deleteApiDetails(@Param(value = "apiName") String apiName){
		return new ResponseEntity<>(apiDetailsService.deleteApiDetails(apiName), HttpStatus.OK);
		
	}
    @PreAuthorize("@auth.allow('GET_ALL_API_DETAILS')")
	@GetMapping("/getAllApiDetails")
	public ResponseEntity <List<ApiDetails>> getAllApiDetails(){
		return new ResponseEntity<>(apiDetailsService.getAllApiDetails(), HttpStatus.OK);
		
	}
	

	
}
