/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.adt.authservice.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.adt.authservice.model.Role;
import com.adt.authservice.repository.RoleRepository;
import com.adt.authservice.util.TableDataExtractor;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    @Autowired
    TableDataExtractor tableDataExtractor;
    
    private static final int MAX_PAGE_SIZE = 50;
    private static final int DEFAULT_PAGE_SIZE = 10;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getAllRole(){
    	return roleRepository.findAll();	
    }
    
    
    public Page<Role> findAll(int page ,int size) {
    	  if (size <= 0 || size > MAX_PAGE_SIZE) {
              size = DEFAULT_PAGE_SIZE;
          }
    	Pageable pageable = PageRequest.of(page, size);
    	return roleRepository.findAll(pageable);
    }
    
    public Set<Role> getRole(boolean defaultRole) {
    	
		return roleRepository.findByDefaultRole(defaultRole);
	}
   
	public String savaRole(Role role) {
		role.setRole(role.getRole().toUpperCase());
		if (!role.getRole().contains("ROLE_")) {
			String role1 = role.getRole();
			role.setRole("ROLE_" + role1);
		}
		String sql="select * from user_schema.role  where role_name="+"'"+role.getRole()+"'";
		List<Map<String, Object>> roleDate = tableDataExtractor.extractDataFromTable(sql);
		if(roleDate==null||roleDate.isEmpty()) {
		roleRepository.save(role);
		return "Role saved successfully";
		}
		return "This role is already present";
	}

	
	public String updateRole(Role role) {
		role.setRole(role.getRole().toUpperCase());
		if (!role.getRole().contains("ROLE_")) {
			String role1 = role.getRole();
			role.setRole("ROLE_" + role1);
		}
		try {
			Optional<Role> updateRole = roleRepository.findById(role.getId());
			Optional<Role> roleinfo = roleRepository.findByRoleName(role.getRole());
			if (updateRole.isPresent()) {
				if (roleinfo.isPresent() && !roleinfo.get().getRole().equalsIgnoreCase(updateRole.get().getRole())) {
					return "This role name already present";
				}
				Role roles = updateRole.get();
				roles.setRole(role.getRole());
				roles.setDefaultRole(role.isDefaultRole());
				roleRepository.save(roles);
				return "Role updated successfully";
			}

			return "Data not present";

		} catch (Exception e) {
			return e.getMessage();

		}
		
	}

	public String deleteRole(Long roleId) {	
		Optional<Role> role = roleRepository.findById(roleId);
		if (role.isPresent()) {
			String sql = "select * from user_schema.user_authority where role_id=" + roleId;
			List<Map<String, Object>> tableData = tableDataExtractor.extractDataFromTable(sql);
			if (tableData == null || tableData.isEmpty()) {
				roleRepository.deleteById(roleId);
			} else {
				return "You cannot delete this role, it is already mapped";
			}
		} else {
			return "Role id does not exist";
		}
		return "Role deleted successfully";

	}
      
	public Optional<Role> getRole(Long roleId) {
		return roleRepository.findById(roleId);
	}
	
	public ResponseEntity<?> getRoleAssosiateEmployee(Long employeeId) {
		String sql = "SELECT  r.role_name  FROM user_schema.role r JOIN user_schema.user_authority ua ON r.role_id = ua.role_id WHERE ua.employee_id ="+ employeeId;
		List<Map<String, Object>> tableData = tableDataExtractor.extractDataFromTable(sql);
		return new ResponseEntity<>(tableData,HttpStatus.OK) ;
		
		
	}
	
	public String updateRoleOfemployee(Long employeeId, List<String> listOfRoleName) {
		String sql3 = " SELECT r.role_name FROM user_schema.role r JOIN user_schema.user_authority ua ON r.role_id = ua.role_id WHERE r.default_role = false AND ua.employee_id = "
				+ employeeId + "";
		List<Map<String, Object>> tableData1 = tableDataExtractor.extractDataFromTable(sql3);
		for (Map<String, Object> roleData : tableData1) {
			String roleName = String.valueOf(roleData.get("role_name"));
			String sql1 = "DELETE FROM user_schema.user_authority ua USING user_schema.role r WHERE ua.role_id = r.role_id  AND ua.employee_id ="
					+ employeeId + " AND r.role_name =" + "'" + roleName + "'";
			tableDataExtractor.insertDataFromTable(sql1);
		}
		String roleName=null;
		String sql4 = " SELECT r.role_name FROM user_schema.role r JOIN user_schema.user_authority ua ON r.role_id = ua.role_id WHERE r.default_role = true AND ua.employee_id = "
				+ employeeId + "";
		List<String> listOfDefaultRole=new ArrayList<>();
		List<Map<String, Object>> tableData2 = tableDataExtractor.extractDataFromTable(sql4);
		for (Map<String, Object> roleData : tableData2) {
			 roleName = String.valueOf(roleData.get("role_name"));
			 roleName= roleName.toUpperCase();
			 listOfDefaultRole.add(roleName);
		}
		for (String roleN : listOfRoleName) {
			if (!listOfDefaultRole.contains(roleN)) {
				String sql1 = "INSERT INTO user_schema.user_authority (employee_id, role_id) SELECT " + employeeId
						+ ", r.role_id FROM user_schema.role r WHERE r.role_name =" + "'" + roleN + "'";
				tableDataExtractor.insertDataFromTable(sql1);
			}

		}
		
		return "Role data is added of user";

	}
	
	public String deleteRoleAssociatedByEmployee(Long employeeId,String roleName) {
		String sql = "SELECT ua.* FROM user_schema.user_authority ua JOIN user_schema.role r ON ua.role_id = r.role_id WHERE ua.employee_id ="
				+ employeeId +" AND r.role_name =" + "'" + roleName + "'";
		List<Map<String, Object>> tableData = tableDataExtractor.extractDataFromTable(sql);
		if(tableData!=null||!tableData.isEmpty()) {
			String sql1 ="DELETE FROM user_schema.user_authority ua USING user_schema.role r WHERE ua.role_id = r.role_id  AND ua.employee_id = 18 AND r.role_name ="+"'"+ roleName+"'";
			tableDataExtractor.insertDataFromTable(sql1);
			return "Deteled mapped role";
		}
		return "Mapped role not prsent";
	}
	
	
}
