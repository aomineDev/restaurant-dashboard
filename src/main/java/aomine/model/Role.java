package aomine.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "role_id")
  private Long roleId;

  @Column(name = "name", unique = true, nullable = false, length = 50)
  private String name;

  public Role(String name) {
    this.name = name;
  }

  public Role() {
  }

  public Long getRoleId() {
    return roleId;
  }

  public void setRoleId(Long roleId) {
    this.roleId = roleId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }

  public static enum Types {
    ADMIN("Administrador"),
    WAITER("Mesero"),
    CHEF("Cocinero"),
    CASHIER("Cajero");

    private String name;

    Types(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }
  }
}