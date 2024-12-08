package aomine.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "natural_people")
public abstract class NaturalPerson extends Person {
  @Column(name = "dni", nullable = false, unique = true)
  protected int dni;

  @Column(name = "name", nullable = false, length = 50)
  protected String name;

  @Column(name = "paternal_lastname", nullable = false, length = 50)
  protected String paternalLastname;

  @Column(name = "maternal_lastname", nullable = false, length = 50)
  protected String MaternalLastname;

  @Column(name = "birtdate")
  protected LocalDate birthdate;

  public NaturalPerson() {}

  public int getDni() {
    return dni;
  }

  public void setDni(int dni) {
    this.dni = dni;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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
