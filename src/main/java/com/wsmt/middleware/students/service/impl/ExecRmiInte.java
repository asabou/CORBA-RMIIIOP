package com.wsmt.middleware.students.service.impl;

import com.wsmt.middleware.students.service.model.GradeDTO;
import com.wsmt.middleware.students.service.model.SchoolObjectDTO;
import com.wsmt.middleware.students.service.model.StudentDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ExecRmiInte extends Remote {
    void addStudent(StudentDTO student) throws RemoteException;
    void updateStudent(StudentDTO student) throws RemoteException;
    void deleteStudent(String id) throws RemoteException;
    StudentDTO[] getAllStudents() throws RemoteException;
    void addSchoolObject(SchoolObjectDTO schoolObject) throws RemoteException;
    void updateSchoolObject(SchoolObjectDTO schoolObject) throws RemoteException;
    void deleteSchoolObject(String id) throws RemoteException;
    SchoolObjectDTO[] getAllSchoolObjects() throws RemoteException;
    void addGrade(GradeDTO grade) throws RemoteException;
    void updateGrade(GradeDTO grade) throws RemoteException;
    GradeDTO[] getCatalogForStudent(String student) throws RemoteException;
    GradeDTO[] getCatalogForSchoolObject(String schoolObject) throws RemoteException;
}
