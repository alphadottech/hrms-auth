package com.adt.authservice.model;




import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity

@Table(catalog = "EmployeeDB", schema = "payroll_schema", name = "leave_balance")
public class LeaveModel {

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

    @Override
    public String toString() {
        return "LeaveModel{" +
                "empId=" + empId +
                ", name='" + name + '\'' +
                ", leaveBalance=" + leaveBalance +
                '}';
    }

    public LeaveModel() {
    }

    public LeaveModel(int empId, String name, int leaveBalance) {
        this.empId = empId;
        this.name = name;
        this.leaveBalance = leaveBalance;
    }

    public int getLeaveBalance() {
        return leaveBalance;
    }

    public void setLeaveBalance(int leaveBalance) {
        this.leaveBalance = leaveBalance;
    }

    @Id
    @Column(name = "empid")
    private int empId;

    @Column(name = "name")
    private String name;


    @Column(name = "leave_balance")
    private int leaveBalance;


}