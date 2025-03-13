package aomine.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "natural_people")
@OnDelete(action = OnDeleteAction.CASCADE)
public abstract class NaturalPerson extends Person {
  @Column(name = "dni", length = 8, nullable = false, unique = true)
  protected String dni;

  @Column(name = "first_name", nullable = false, length = 50)
  protected String firstName;

  @Column(name = "second_name", length = 50)
  protected String secondName;

  @Column(name = "paternal_lastname", nullable = false, length = 50)
  protected String paternalLastname;

  @Column(name = "maternal_lastname", nullable = false, length = 50)
  protected String maternalLastname;

  @Column(name = "birthdate")
  protected LocalDate birthdate;

  @Formula("first_name || ' ' || COALESCE(second_name, '') || ' ' || paternal_lastname || ' ' || maternal_lastname")
  protected String fullName;

  public NaturalPerson() {
  }

  public String getDni() {
    return dni;
  }

  public void setDni(String dni) {
    this.dni = dni;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getSecondName() {
    return secondName;
  }

  public void setSecondName(String secondName) {
    this.secondName = secondName;
  }

  public String getPaternalLastname() {
    return paternalLastname;
  }

  public void setPaternalLastname(String paternalLastname) {
    this.paternalLastname = paternalLastname;
  }

  public String getMaternalLastname() {
    return maternalLastname;
  }

  public void setMaternalLastname(String maternalLastname) {
    this.maternalLastname = maternalLastname;
  }

  public LocalDate getBirthdate() {
    return birthdate;
  }

  public String getBirthdateFomramtted() {
    if (birthdate == null)
      return "";

    DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    return birthdate.format(df);
  }

  public void setBirthdate(LocalDate birthdate) {
    this.birthdate = birthdate;
  }

  public String getFullName() {
    return fullName;
  }
}
