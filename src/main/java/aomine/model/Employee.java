package aomine.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "employees")
@OnDelete(action = OnDeleteAction.CASCADE)
public class Employee extends NaturalPerson {
  @Column(name = "username", unique = true, nullable = false, length = 50)
  private String username;

  @Column(name = "password", nullable = false, length = 100)
  private String password;

  @ManyToOne
  @JoinColumn(name = "role_id")
  @OnDelete(action = OnDeleteAction.SET_NULL)
  private Role role;

  public Employee() {
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  @Override
  public String toString() {
    return firstName + " " + paternalLastname;
  }

  public static enum EmployeeColumn implements EntityColumn {
    FULL_NAME("fullName", "nombre completo"),
    FIRST_NAME("firstName", "primer nombre"),
    SECOND_NAME("secondName", "segundo nombre"),
    PATERNAL_LASTNAME("paternalLastname", "apellido paterno"),
    MATERNAL_LASTNAME("maternalLastname", "apellido materno"),
    DNI("dni", "dni"),
    BIRTHDATE("birthdate", "fecha de nacimiento"),
    PHONE_NUMBER("phoneNumber", "numero de telefono"),
    ADDRESS("address", "direccion"),
    EMAIL("email", "email"),
    USERNAME("username", "usuario"),
    PASSWORD("password", "contraseña"),
    ROLE("role", "rol");

    private String columnName;
    private String value;

    private EmployeeColumn(String columnName, String value) {
      this.columnName = columnName;
      this.value = value;
    }

    @Override
    public String getColumnName() {
      return columnName;
    }

    @Override
    public String toString() {
      return value;
    }
  }
}
