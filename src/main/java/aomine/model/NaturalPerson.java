package aomine.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "natural_people")
public abstract class NaturalPerson extends Person {
  @Column(name = "dni", nullable = false, unique = true)
  protected int dni;

  @Column(name = "first_name", nullable = false, length = 50)
  protected String firstName;

  @Column(name = "second_name", length = 50)
  protected String secondName;

  @Column(name = "paternal_lastname", nullable = false, length = 50)
  protected String paternalLastname;

  @Column(name = "maternal_lastname", nullable = false, length = 50)
  protected String MaternalLastname;

  @Column(name = "birthdate")
  protected LocalDate birthdate;

  public NaturalPerson() {
  }

  public int getDni() {
    return dni;
  }

  public void setDni(int dni) {
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
    return MaternalLastname;
  }

  public void setMaternalLastname(String maternalLastname) {
    MaternalLastname = maternalLastname;
  }

  public LocalDate getBirthdate() {
    return birthdate;
  }

  public void setBirthdate(LocalDate birthdate) {
    this.birthdate = birthdate;
  }
}
