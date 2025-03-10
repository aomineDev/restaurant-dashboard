package aomine.view.admin.employee;

import java.awt.Dimension;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import aomine.components.layout.view.AdminView;
import aomine.controller.admin.EmployeeController;
import aomine.utils.FormAction;
import raven.popup.DefaultOption;
import raven.popup.GlassPanePopup;
import raven.popup.component.PopupCallbackAction;
import raven.popup.component.SimplePopupBorder;
import raven.popup.component.SimplePopupBorderOption;

public class EmployeeView extends AdminView {
  private EmployeeController controller;

  public EmployeeView() {
    super("employee.png");
  }

  @Override
  protected void setController() {
    controller = new EmployeeController(this);
  }

  @Override
  public void applyEvents() {
    super.applyEvents();

    btnAdd.addActionListener(evt -> showFormPopup(FormAction.ADD));

    btnEdit.addActionListener(e -> showFormPopup(FormAction.EDIT));

    // btn delete
    btnDelete.addActionListener(e -> controller.handleDeleteEmployee(rowSelected));
  }

  @Override
  protected void setTableModel() {
    Object[] columns = { "ID", "P. Nombre", "S. Nombre", "Apellido P.", "Apellido M.", "DNI", "Fecha de nacimiento",
        "Celular", "Dirección", "Email", "Usuario", "Rol" };

    DefaultTableModel model = new DefaultTableModel(null, columns) {
      boolean[] canEdit = { false, false, false, false, false, false, false, false, false, false, false, false };

      @Override
      public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit[columnIndex];
      }
    };

    table.setModel(model);

    // Ocultar la columna ID
    TableColumnModel tcm = table.getColumnModel();
    tcm.removeColumn(tcm.getColumn(0));
  }

  @Override
  public void setTableData() {
    DefaultTableModel model = (DefaultTableModel) table.getModel();

    model.setRowCount(0);

    controller.getEmployeeList().forEach(employee -> {
      Object[] row = {
          employee.getPersonId(),
          employee.getFirstName(),
          employee.getSecondName(),
          employee.getPaternalLastname(),
          employee.getMaternalLastname(),
          employee.getDni(),
          employee.getBirthdateFomramtted(),
          employee.getPhoneNumber(),
          employee.getAddress(),
          employee.getEmail(),
          employee.getUsername(),
          employee.getRole().getName()
      };

      model.addRow(row);
    });
  }

  private void showFormPopup(FormAction action) {
    String actionBtn = switch (action) {
      case ADD -> "Agregar";
      case EDIT -> "Editar";
    };

    String title = switch (action) {
      case ADD -> "Agregar Empleado";
      case EDIT -> "Editar Empleado";
    };

    int formWidth = 800;
    int height = 550;
    int scrollWidth = 10;
    int popupWidht = formWidth + scrollWidth;

    SimplePopupBorderOption borderOption = new SimplePopupBorderOption();

    borderOption.useScroll();
    borderOption.setWidth(popupWidht);

    String[] actions = switch (action) {
      case ADD -> new String[] { "Cancelar", actionBtn };
      case EDIT -> new String[] { "Cancelar", "Cambiar contraseña", actionBtn };
    };

    PopupCallbackAction callbackAction = switch (action) {
      case ADD -> (ctrl, act) -> {
        if (act == 0) {
          GlassPanePopup.closePopup("employeeForm");
        } else if (act == 1) {
          controller.setAction(action);

          controller.handleFormAction();
        }
      };
      case EDIT -> (ctrl, act) -> {
        if (act == 0) {
          GlassPanePopup.closePopup("employeeForm");
        } else if (act == 1) {
          pushResetFormPopup();
        } else if (act == 2) {
          controller.setAction(action);

          controller.handleFormAction();
        }
      };
    };

    EmployeeForm form = new EmployeeForm(formWidth, action, controller);
    controller.setForm(form);

    if (action == FormAction.EDIT) {
      controller.setSelectedEmployee(rowSelected);
      form.fillForm(controller.getSelectedEmployee());
    }

    SimplePopupBorder popup = new SimplePopupBorder(form, title, borderOption, actions, callbackAction);

    popup.setMaximumSize(new Dimension(popupWidht, height));

    DefaultOption popupOption = new DefaultOption();

    GlassPanePopup.showPopup(
        popup,
        popupOption,
        "employeeForm");
  }

  private void pushResetFormPopup() {
    EmployeeResetForm resetForm = new EmployeeResetForm();
    controller.setResetForm(resetForm);

    String[] actions = { "Cancelar", "Cambiar" };

    PopupCallbackAction callbackAction = (ctrl, act) -> {
      if (act == 0) {
        GlassPanePopup.pop("employeeForm");
      } else if (act == 1) {
        controller.handleResetPassword();
      }
    };

    SimplePopupBorder popup = new SimplePopupBorder(resetForm, "Cambiar contraseña", actions, callbackAction);

    GlassPanePopup.push(popup, "employeeForm");
  }
}
