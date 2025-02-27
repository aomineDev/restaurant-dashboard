package aomine.controller.admin;

import java.util.ArrayList;

import aomine.dao.EmployeeDAO;
import aomine.dao.RoleDAO;
import aomine.model.Role;
import aomine.view.admin.EmployeeView;

public class EmployeeController {
  private EmployeeView view;
  private EmployeeDAO employeeDAO;
  private RoleDAO roleDAO;

  public EmployeeController(EmployeeView view) {
    this.view = view;
    this.employeeDAO = new EmployeeDAO();
    this.roleDAO = new RoleDAO();
  }

  public void setAllRoles() {
    ArrayList<Role> roleList = this.roleDAO.getAll();

    roleList.forEach(role -> view.getCbRole().getInput().addItem(role));
  }

}
