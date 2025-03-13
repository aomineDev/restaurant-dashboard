package aomine.controller.admin;

import java.time.LocalDate;

import aomine.controller.Controller;
import aomine.dao.EmployeeDAO;
import aomine.dao.RoleDAO;
import aomine.model.Employee;
import aomine.model.EntityColumn;
import aomine.model.Role;
import aomine.model.Employee.EmployeeColumn;
import aomine.utils.FormAction;
import aomine.utils.GoatList;
import aomine.utils.validate.Validate;
import aomine.view.admin.employee.EmployeeView;
import aomine.view.admin.employee.EmployeeForm;
import aomine.view.admin.employee.EmployeeResetForm;
import at.favre.lib.crypto.bcrypt.BCrypt;
import raven.alerts.MessageAlerts;
import raven.popup.GlassPanePopup;
import raven.toast.Notifications;

public class EmployeeController implements Controller {
  private EmployeeView view;
  private EmployeeForm form;
  private EmployeeResetForm resetForm;
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
  private String dni;
  private LocalDate birthdate;
  private String phoneNumber;
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

  public GoatList<Role> getRoleList() {
    return this.roleDAO.getAll();
  }

  public void handleFormAction() {
    if (!validateFormFields())
      return;

    firstName = form.getTiFirstName().getText();
    secondName = form.getTiSecondtName().getText();
    paternalLastname = form.getTiPaternalLastName().getText();
    maternalLastname = form.getTiMaternalLastName().getText();
    dni = form.getMiDni().getText();
    birthdate = form.getDatePicker().getSelectedDate();
    phoneNumber = form.getMiPhoneNumber().getText();
    email = form.getTiEmail().getText();
    address = form.getTiAddress().getText();
    username = form.getTiUsername().getText();
    password = getEncryptedPassword(form.getPiPassword().getText());
    role = form.getSiRole().getSelectedItem();

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

      view.viewRefresh();

      Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.BOTTOM_LEFT, messageSuccess);
    } catch (Exception e) {
      Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.BOTTOM_LEFT, messageError);

      e.printStackTrace();
    }
  }

  public void handleDeleteEmployee(int rowSelected) {
    String fullName = (String) view.getValueFromTable(rowSelected, 0);

    String msg = String.format("¿Está seguro de eliminar a \n %s?", fullName);

    MessageAlerts.getInstance().showMessage("Eliminar Empleado", msg,
        MessageAlerts.MessageType.WARNING, MessageAlerts.YES_NO_OPTION, (ctr, option) -> {
          if (option == 0) {
            try {
              employeeDAO.delete(view.getIdFromTable(rowSelected));

              view.viewRefresh();

              Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.BOTTOM_LEFT,
                  "Empleado eliminado correctamente");
            } catch (Exception e) {
              Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.BOTTOM_LEFT,
                  "Error al eliminar empleado");

              e.printStackTrace();
            }
          }
        });
  }

  public void handleResetPassword() {
    if (!validateResetFormFields())
      return;

    password = getEncryptedPassword(resetForm.getPiNewPassword().getText());

    selectedEmployee.setPassword(password);

    try {
      employeeDAO.update(selectedEmployee);

      GlassPanePopup.closePopup("employeeForm");

      Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.BOTTOM_LEFT,
          "Contraseña actualizada");
    } catch (Exception e) {
      Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.BOTTOM_LEFT,
          "Error al actualizar la contraseña");

      e.printStackTrace();
    }

    view.setBtnEnabled(false);
  }

  public GoatList<Employee> getFilteredEmployeeList() {
    return employeeDAO.search(view.getSiColumn().getSelectedItem(), view.getTiSearch().getText());
  }

  @Override
  public boolean validateFormFields() {
    validate.reset();

    validate.setElement(form.getTiFirstName())
        .isRequired("Campo requerido");

    validate.setElement(form.getTiPaternalLastName())
        .isRequired("Campo requerido");

    validate.setElement(form.getTiMaternalLastName())
        .isRequired("Campo requerido");

    validate.setElement(form.getMiDni())
        .isRequired("Campo requerido")
        .isInteger("DNI invalido");

    switch (action) {
      case ADD -> validate.isUnique("DNI ya registrado", Employee.class, EmployeeColumn.DNI);
      case EDIT ->
        validate.isUnique("DNI ya registrado", Employee.class, EmployeeColumn.DNI, selectedEmployee.getPersonId());
    }

    if (form.getMiBirthdate().getText() != null)
      validate.setElement(form.getMiBirthdate())
          .isDate("Fecha invalida");

    if (form.getMiPhoneNumber().getText() != null) {
      validate.setElement(form.getMiPhoneNumber())
          .isInteger("telefono invalido");

      switch (action) {
        case ADD -> validate.isUnique("telefono ya registrado", Employee.class, EmployeeColumn.PHONE_NUMBER);
        case EDIT ->
          validate.isUnique("telefono ya registrado", Employee.class, EmployeeColumn.PHONE_NUMBER,
              selectedEmployee.getPersonId());
      }
    }

    if (form.getTiEmail().getText() != null) {
      validate.setElement(form.getTiEmail())
          .isEmail("email invalido");

      switch (action) {
        case ADD -> validate.isUnique("email ya registrado", Employee.class, EmployeeColumn.EMAIL);
        case EDIT -> validate.isUnique("email ya registrado", Employee.class, EmployeeColumn.EMAIL,
            selectedEmployee.getPersonId());
      }
    }

    validate.setElement(form.getTiUsername())
        .isRequired("campo requerido");

    switch (action) {
      case ADD ->
        validate.isUnique("username ya registrado", Employee.class, EmployeeColumn.USERNAME);
      case EDIT ->
        validate.isUnique("username ya registrado", Employee.class, EmployeeColumn.USERNAME,
            selectedEmployee.getPersonId());
    }

    if (action == FormAction.ADD)
      validate.setElement(form.getPiPassword())
          .isRequired("campo requerido")
          .minLength("minimo 8 caracteres", 8);

    if (!validate.isValid()) {
      validate.getValErrorList().forEach(error -> {
        error.getComponent().setError(error.getMessage());
        error.getComponent().clear();
      });
    }

    return validate.isValid();
  }

  public boolean validateResetFormFields() {
    validate.reset();

    validate.setElement(resetForm.getPiNewPassword())
        .isRequired("campo requerido")
        .minLength("minimo 8 caracteres", 8);

    if (!validate.isValid()) {
      validate.getValErrorList().forEach(error -> {
        error.getComponent().setError(error.getMessage());
        error.getComponent().clear();
      });
    }

    return validate.isValid();
  }

  private String getEncryptedPassword(String password) {
    if (password == null)
      return null;

    return BCrypt.withDefaults().hashToString(12, password.toCharArray());
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

  public GoatList<Employee> getEmployeeList() {
    return employeeDAO.getAll();
  }

  public Employee getSelectedEmployee() {
    return this.selectedEmployee;
  }

  public void setSelectedEmployee(int rowSelected) {
    this.selectedEmployee = employeeDAO.get(view.getIdFromTable(rowSelected));
  }

  public void setAction(FormAction action) {
    this.action = action;
  }

  public void setForm(EmployeeForm form) {
    this.form = form;
  }

  public void setResetForm(EmployeeResetForm resetForm) {
    this.resetForm = resetForm;
  }
}
