package com.example.demo.controller;

import com.example.demo.models.Employee;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

@RestController
public class EmployeeController {

    ArrayList<Employee> list = new ArrayList<>();

    @GetMapping(path = "/employees")
    public ArrayList<Employee> getEmployee() {
        Connection c = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        ArrayList<Employee> list = new ArrayList<>();

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://35.197.145.177:5432/employee", "postgres", "postgres");
            c.setAutoCommit(false);

            StringBuffer sql = new StringBuffer("SELECT * FROM employee");
            stmt = c.prepareStatement(sql.toString());
            rs = stmt.executeQuery();
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getString("id"));
                employee.setEmployeeId(rs.getString("employee_id"));
                employee.setFisrtName(rs.getString("firstname"));
                employee.setLastname(rs.getString("lastname"));
                employee.setEmail(rs.getString("email"));
                employee.setAge(rs.getString("age"));
                employee.setSex(rs.getString("sex"));

                list.add(employee);
            }

            stmt.close();
            c.commit();
            c.close();
            System.out.println("Close database");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return list;
    }

    @PostMapping(path = "/employees")
    public String addEmployee(@RequestBody Employee request) {
        Connection c = null;
        PreparedStatement stmt = null;   //ตัวจัดการ
        boolean flag = false;
        System.out.println("Error");
        try {
            Class.forName("org.postgresql.Driver");         //อ้างอิง database
            System.out.println("Fails");
            c = DriverManager
                    .getConnection("jdbc:postgresql://35.197.145.177:5432/employee",
                            "postgres", "postgres");         //เชื่อมต่อ database
            c.setAutoCommit(false);

            System.out.println("Opened database successfully");

            //คำสั่ง sql
            StringBuffer sql = new StringBuffer("INSERT INTO employee(employee_id, firstname, lastname, email, sex, age) VALUES(?,?,?,?,?,?)");
            stmt = c.prepareStatement(sql.toString());
            stmt.setString(1, request.getEmployeeId());
            stmt.setString(2, request.getFisrtName());
            stmt.setString(3, request.getLastname());
            stmt.setString(4, request.getEmail());
            stmt.setString(5, request.getSex());
            stmt.setString(6, request.getAge());

            System.out.println("After set stmt");

            //ทำงานและเชื่อมต่อ
            int result = stmt.executeUpdate();
            if (result > 0) {
                flag = true;
                System.out.println("flag = true");
            } else {
                flag = false;
                System.out.println("flag = false");
            }
            stmt.close();           //ปิด database
            c.commit();
            c.close();
            System.out.println("Close database");

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return e.getMessage();
        }

        // เช็คว่าเอัพเดท สำเร็จแล้วหรือยัง
        if (flag) {
            System.out.println("Record created successfully");
            return "Record created successfully";
        } else {
            System.out.println("Record created failed");
            return "Records created failed";
        }
    }

    @DeleteMapping(path = "/employees/{employeeId}")
    public String deletEmployee(@PathVariable String employeeId) {

        Connection c = null;
        PreparedStatement stmt = null;
        boolean flag = false;
        ArrayList<Employee> list = new ArrayList<>();

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://35.197.145.177:5432/employee", "postgres", "postgres");
            c.setAutoCommit(false);

            StringBuffer sql = new StringBuffer("DELETE FROM employee WHERE employee_id =?");
            stmt = c.prepareStatement(sql.toString());
            stmt.setString(1, employeeId);


            int result = stmt.executeUpdate();

            if (result > 0) {
                flag = true;
            } else {
                flag = false;
            }

            stmt.close();
            c.commit();
            c.close();
            System.out.println("Close database");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        if (flag) {
            System.out.println("Delete successfully");
            return "Delete successfully";
        } else {
            System.out.println("Delete failed");
            return "Delete failed";
        }
    }


    @PutMapping(path = "/employees")
    public String updateEmployee(@RequestBody Employee request) {
        Connection c = null;
        PreparedStatement stmt = null;
        boolean flag = false;
        ArrayList<Employee> list = new ArrayList<>();

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://35.197.145.177:5432/employee", "postgres", "postgres");
            c.setAutoCommit(false);

            StringBuffer sql = new StringBuffer("UPDATE employee SET firstname = ?, lastnaeme = ?, emil = ?, age = ?, sex =? WHERE employee_id = ?");
            stmt = c.prepareStatement(sql.toString());
            stmt.setString(1,request.getFisrtName());
            stmt.setString(2,request.getLastname());
            stmt.setString(3,request.getEmail());
            stmt.setString(4,request.getAge());
            stmt.setString(5,request.getSex());
            stmt.setString(6,request.getEmployeeId());

            int result = stmt.executeUpdate();

            if (result > 0){
                flag = true;
            }else {
                flag = false;
            }

            stmt.close();
            c.commit();
            c.close();
            System.out.println("Close database");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        if (flag) {
            System.out.println("Update successfully");
            return "Update successfully";
        } else {
            System.out.println("Update failed");
            return "Update failed";
        }
    }



    @GetMapping(path = "/employees/{employeeId}")
    public ArrayList<Employee> getEmployeeByid(@PathVariable String employeeId){
        Connection c = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        ArrayList<Employee> list = new ArrayList<>();

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://35.197.145.177:5432/employee", "postgres", "postgres");
            c.setAutoCommit(false);

            StringBuffer sql = new StringBuffer("SELECT * FROM employee WHERE employee_id =?");
            stmt = c.prepareStatement(sql.toString());
            stmt.setString(1,employeeId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getString("id"));
                employee.setEmployeeId(rs.getString("employee_id"));
                employee.setFisrtName(rs.getString("firstname"));
                employee.setLastname(rs.getString("lastname"));
                employee.setEmail(rs.getString("email"));
                employee.setAge(rs.getString("age"));
                employee.setSex(rs.getString("sex"));

                list.add(employee);
            }

            stmt.close();
            c.commit();
            c.close();
            System.out.println("Close database");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return list;
    }
}
