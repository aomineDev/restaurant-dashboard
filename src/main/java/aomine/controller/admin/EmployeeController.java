package aomine.controller.admin;

import java.time.LocalDate;

import aomine.controller.Controller;
import aomine.dao.EmployeeDAO;
import aomine.dao.RoleDAO;
import aomine.model.Employee;
import aomine.model.Role;
import aomine.utils.Form;
import aomine.utils.FormAction;
import aomine.utils.GoatList;
import aomine.utils.validate.Validate;
import aomine.view.admin.EmployeeView;
import raven.alerts.MessageAlerts;
import raven.popup.GlassPanePopup;
import raven.toast.Notifications;

public class EmployeeController implements Controller {
  private EmployeeView view;
  private EmployeeDAO employeeDAO;
  private RoleDAO roleDAO;
  private Validate validate;
  private FormAction action;
  private Employee selectedEmployee;

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

  public void handleFormAction() {
    if (!validateFields())
      return;

    firstName = view.getTiFirstName().getText();
    secondName = view.getTiSecondtName().getText();
    paternalLastname = view.getTiPaternalLastName().getText();
    maternalLastname = view.getTiMaternalLastName().getText();
    dni = Integer.parseInt(view.getMiDni().getText());
    birthdate = view.getDatePicker().getSelectedDate();
    phoneNumber = Integer.parseInt(view.getMiPhoneNumber().getText().replaceAll(" ", ""));
    email = view.getTiEmail().getText();
    address = view.getTiAddress().getText();
    username = view.getTiUsername().getText();
    password = view.getPiPassword().getText();
    role = view.getCbRole().getSelectedItem();

    String messageSuccess = switch (action) {
      case ADD -> "Empleado creado correctamente";
      case EDIT -> "Empleado editado correctamente";
    };
    String messageError = switch (action) {
      case ADD -> "Error al creado empleado";
      case EDIT -> "Error al editar empleado";
    };
    try {
      switch (action) {
        case ADD -> {

          Employee employee = new Employee();

          fillEmployee(employee);

          employeeDAO.add(employee);
        }
        case EDIT -> {

          fillEmployee(selectedEmployee);

          employeeDAO.update(selectedEmployee);
        }
      }

      GlassPanePopup.closePopup("employeeForm");

      Form.cleanInputs(view.getFormInputList());

      view.setTableData();

      Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_RIGHT, messageSuccess);
    } catch (Exception e) {
      Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, messageError);

      e.printStackTrace();
    }
  }

  public void handleDeleteEmployee(int rowSelected) {
    MessageAlerts.getInstance().showMessage("Eliminar Empleado", "¿Está seguro de eliminar este empleado?",
        MessageAlerts.MessageType.WARNING, MessageAlerts.YES_NO_OPTION, (ctr, option) -> {
          if (option == 0) {
            try {
              employeeDAO.delete(getIdFromRow(rowSelected));

              view.setTableData();

              Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_RIGHT,
                  "Empleado eliminado correctamente");
            } catch (Exception e) {
              Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT,
                  "Error al eliminar empleado");

              e.printStackTrace();
            }
          }
        });
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
        .selfValidate("Campo requerido", text -> !text.equals("--------"))
        .isInteger("DNI invalido");

    switch (action) {
      case ADD -> validate.isUnique("DNI ya registrado", Employee.class, "dni");
      case EDIT -> validate.isUnique("DNI ya registrado", Employee.class, "dni", selectedEmployee.getPersonId());
    }

    if (!view.getMiBirthdate().getText().equals("--/--/----"))
      validate.setElement(view.getMiBirthdate())
          .isDate("Fecha invalida");

    if (!view.getMiPhoneNumber().getText().equals("--- --- ---")) {
      validate.setElement(view.getMiPhoneNumber())
          .setText(text -> text.replaceAll(" ", ""))
          .isInteger("telefono invalido");

      switch (action) {
        case ADD -> validate.isUnique("telefono ya registrado", Employee.class, "phoneNumber");
        case EDIT ->
          validate.isUnique("telefono ya registrado", Employee.class, "phoneNumber", selectedEmployee.getPersonId());
      }
    }

    if (!view.getTiEmail().getText().equals("")) {
      validate.setElement(view.getTiEmail())
          .isEmail("email invalido");

      switch (action) {
        case ADD -> validate.isUnique("email ya registrado", Employee.class, "email");
        case EDIT -> validate.isUnique("email ya registrado", Employee.class, "email", selectedEmployee.getPersonId());
      }

    }

    validate.setElement(view.getTiUsername())
        .isRequired("campo requerido");

    switch (action) {
      case ADD ->
        validate.isUnique("username ya registrado", Employee.class, "username");
      case EDIT ->
        validate.isUnique("username ya registrado", Employee.class, "username", selectedEmployee.getPersonId());
    }

    if (action == FormAction.ADD)
      validate.setElement(view.getPiPassword())
          .isRequired("campo requerido")
          .minLength("minimo 8 caracteres", 8);

    if (!validate.isValid()) {
      validate.getValErrorList().forEach(error -> {
        error.getComponent().setErrorHint(true);
        error.getComponent().setLabelErrorText(error.getMessage());
        error.getComponent().setText("");
      });
    }

    return validate.isValid();
  }

  public void fillEmployee(Employee employee) {
    employee.setFirstName(firstName);
    employee.setSecondName(secondName);
    employee.setPaternalLastname(paternalLastname);
    employee.setMaternalLastname(maternalLastname);
    employee.setDni(dni);
    employee.setBirthdate(birthdate);
    employee.setPhoneNumber(phoneNumber);
    employee.setEmail(email);
    employee.setAddress(address);
    employee.setUsername(username);

    if (action == FormAction.ADD)
      employee.setPassword(password);

    employee.setRole(role);
  }

  public long getIdFromRow(int rowSelected) {
    return (long) view.getTableEmployee().getModel().getValueAt(rowSelected, 0);
  }

  public GoatList<Employee> getEmployeeList() {
    return employeeDAO.getAll();
  }

  public Employee getEmployee(int rowSelected) {
    this.selectedEmployee = employeeDAO.get(getIdFromRow(rowSelected));

    return this.selectedEmployee;
  }

  public void setAction(FormAction action) {
    this.action = action;
  }
}
