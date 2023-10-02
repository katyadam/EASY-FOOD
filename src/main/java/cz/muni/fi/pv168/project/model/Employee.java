package cz.muni.fi.pv168.project.model;

import java.time.LocalDate;
import java.util.Objects;

public class Employee {

    private String firstName;
    private String lastName;
    private cz.muni.fi.pv168.project.model.Gender gender;
    private LocalDate birthDate;
    private Department department;

    public Employee(String firstName, String lastName, cz.muni.fi.pv168.project.model.Gender gender, LocalDate birthDate, Department department) {
        setFirstName(firstName);
        setLastName(lastName);
        setGender(gender);
        setBirthDate(birthDate);
        setDepartment(department);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = Objects.requireNonNull(firstName, "firstName must not be null");
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = Objects.requireNonNull(lastName, "lastName must not be null");
    }

    public cz.muni.fi.pv168.project.model.Gender getGender() {
        return gender;
    }

    public void setGender(cz.muni.fi.pv168.project.model.Gender gender) {
        this.gender = Objects.requireNonNull(gender, "gender must not be null");
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = Objects.requireNonNull(birthDate, "birthDate must not be null");
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = Objects.requireNonNull(department, "department must not be null");
    }

    @Override
    public String toString() {
        return firstName + ' ' + lastName;
    }
}
