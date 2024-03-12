package com.adt.authservice.model;




import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(catalog = "EmployeeDB", schema = "payroll_schema", name = "leave_balance")
public class LeaveModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "serial_id")
	private int serialNo;

	@Column(name = "employee_id")
	private int empId;

	@Column(name = "name")
	private String name;

	@Column(name = "leave_balance")
	private int leaveBalance;

	public int getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLeaveBalance() {
		return leaveBalance;
	}

	public void setLeaveBalance(int leaveBalance) {
		this.leaveBalance = leaveBalance;
	}

	@Override
	public String toString() {
		return "LeaveModel [serialNo=" + serialNo + ", empId=" + empId + ", name=" + name + ", leaveBalance="
				+ leaveBalance + "]";
	}

	public LeaveModel() {
		
	}

	public LeaveModel(int serialNo, int empId, String name, int leaveBalance) {
		super();
		this.serialNo = serialNo;
		this.empId = empId;
		this.name = name;
		this.leaveBalance = leaveBalance;
	}
	
	


}