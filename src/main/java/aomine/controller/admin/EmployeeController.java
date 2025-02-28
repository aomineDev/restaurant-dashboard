package aomine.controller.admin;

import java.awt.event.ActionEvent;
import java.time.LocalDate;

import aomine.controller.Controller;
import aomine.dao.EmployeeDAO;
import aomine.dao.RoleDAO;
import aomine.model.Employee;
import aomine.model.Role;
import aomine.utils.GoatList;
import aomine.utils.validate.ValError;
import aomine.utils.validate.Validate;
import aomine.view.admin.EmployeeView;

public class EmployeeController implements Controller {
  private EmployeeView view;
  private EmployeeDAO employeeDAO;
  private RoleDAO roleDAO;
  private Validate validate;

  // Form inputs
  private String firstName;
  private String secondName;
  private String paternalLastname;
  private String maternalLastname;
  private int dni;
  private LocalDate birthdate;
  private int phoneNumber;
  private String email;
  private String address;
  private String username;
  private String password;
  private Role role;

  public EmployeeController(EmployeeView view) {
    this.view = view;
    this.employeeDAO = new EmployeeDAO();
    this.roleDAO = new RoleDAO();
    this.validate = new Validate();
  }

  public void setAllRoles() {
    GoatList<Role> roleList = this.roleDAO.getAll();

    roleList.forEach(role -> view.getCbRole().getInput().addItem(role));
  }

  public void handleAddEmployee(ActionEvent evt) {
    if (!validateFields())
      return;

    firstName = view.getTiFirstName().getText();
    secondName = view.getTiSecondtName().getText();
    paternalLastname = view.getTiPaternalLastName().getText();
    maternalLastname = view.getTiMaternalLastName().getText();
    dni = Integer.parseInt(view.getMiDni().getText());
    birthdate = view.getDatePicker().getSelectedDate();
    phoneNumber = Integer.parseInt(view.getMiPhoneNumber().getText());
    email = view.getTiEmail().getText();
    username = view.getTiUsername().getText();
    password = view.getPiPassword().getText();
    role = view.getCbRole().getSelectedItem();

    Employee employee = new Employee();

    employee.setFirstName(firstName);
    employee.setSecondName(secondName);
    employee.setPaternalLastname(paternalLastname);
    employee.setMaternalLastname(maternalLastname);
    employee.setDni(dni);
    employee.setBirthdate(birthdate);
    employee.setPhoneNumber(phoneNumber);
    employee.setEmail(email);
    employee.setUsername(username);
    employee.setPassword(password);
    employee.setRole(role);

    employeeDAO.add(employee);
  }

  @Override
  public boolean validateFields() {
    validate.reset();
    validate.setElement(view.getTiFirstName())
        .isRequired("Campo requerido");

    validate.setElement(view.getTiPaternalLastName())
        .isRequired("Campo requerido");

    validate.setElement(view.getTiMaternalLastName())
        .isRequired("Campo requerido");

    validate.setElement(view.getMiDni())
        .isRequired("Campo requerido")
        .minLength("minimo 8 caracteres", 8);

    validate.setElement(view.getMiPhoneNumber())
        .minLength("minimo 9 caracteres", 9);

    validate.setElement(view.getTiEmail());

    validate.setElement(view.getTiUsername())
        .isRequired("campo requerido");

    validate.setElement(view.getPiPassword())
        .isRequired("campo requerido")
        .minLength("minimo 8 caracteres", 8);

    if (!validate.getIsValid()) {
      for (ValError error : validate.getValErrorList()) {
        error.getInput().setErrorHint(true);
        error.getInput().setLabelErrorText(error.getMessage());
      }
    }

    return validate.getIsValid();
  }
}
