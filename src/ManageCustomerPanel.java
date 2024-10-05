import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ManageCustomerPanel extends JPanel {
    private List<Customer> customers;
    private JTextField customerIdField, nameField, emailField, phoneField;
    private DefaultTableModel tableModel;
    
    public ManageCustomerPanel(List<Customer> customers) {
        this.customers = customers;
        setLayout(new BorderLayout(20, 20));
        setBackground(CarRentalManagementSystem.SECONDARY_COLOR);
        setBorder(new EmptyBorder(30, 30, 30, 30));
        
        initComponents();
    }
    
    private void initComponents() {
        add(createFormPanel(), BorderLayout.NORTH);
        add(createButtonPanel(), BorderLayout.CENTER);
        add(createTablePanel(), BorderLayout.SOUTH);
    }
    
    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(CarRentalManagementSystem.PRIMARY_COLOR, 2),
            new EmptyBorder(20, 20, 20, 20)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        customerIdField = UIUtils.createStyledTextField();
        nameField = UIUtils.createStyledTextField();
        emailField = UIUtils.createStyledTextField();
        phoneField = UIUtils.createStyledTextField();
        
        UIUtils.addFormField(formPanel, "Customer ID", customerIdField, gbc, 0);
        UIUtils.addFormField(formPanel, "Name", nameField, gbc, 1);
        UIUtils.addFormField(formPanel, "Email", emailField, gbc, 2);
        UIUtils.addFormField(formPanel, "Phone", phoneField, gbc, 3);
        
        return formPanel;
    }
    
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(CarRentalManagementSystem.SECONDARY_COLOR);
        
        JButton addBtn = UIUtils.createStyledButton("Add Customer", CarRentalManagementSystem.PRIMARY_COLOR);
        JButton updateBtn = UIUtils.createStyledButton("Update Customer", CarRentalManagementSystem.PRIMARY_COLOR);
        JButton removeBtn = UIUtils.createStyledButton("Remove Customer", CarRentalManagementSystem.ACCENT_COLOR);
        JButton clearBtn = UIUtils.createStyledButton("Clear Fields", Color.DARK_GRAY);
        
        addBtn.addActionListener(e -> addCustomer());
        updateBtn.addActionListener(e -> updateCustomer());
        removeBtn.addActionListener(e -> removeCustomer());
        clearBtn.addActionListener(e -> clearFields());
        
        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(removeBtn);
        buttonPanel.add(clearBtn);
        
        return buttonPanel;
    }
    
    private JScrollPane createTablePanel() {
        String[] columnNames = {"Customer ID", "Name", "Email", "Phone"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable customerTable = new JTable(tableModel);
        customerTable.setFont(new Font("Arial", Font.PLAIN, 14));
        customerTable.setRowHeight(25);
        customerTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        customerTable.getTableHeader().setBackground(CarRentalManagementSystem.PRIMARY_COLOR);
        customerTable.getTableHeader().setForeground(Color.DARK_GRAY);
        
        customerTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = customerTable.getSelectedRow();
            if (selectedRow != -1) {
                Customer selectedCustomer = customers.get(selectedRow);
                populateFields(selectedCustomer);
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(customerTable);
        scrollPane.setPreferredSize(new Dimension(getWidth(), 300));
        return scrollPane;
    }
    
    private void addCustomer() {
        Customer newCustomer = createCustomerFromFields();
        customers.add(newCustomer);
        updateCustomerTable();
        clearFields();
    }
    
    private void updateCustomer() {
        int selectedRow = ((JTable) ((JScrollPane) getComponent(2)).getViewport().getView()).getSelectedRow();
        if (selectedRow != -1) {
            Customer updatedCustomer = createCustomerFromFields();
            customers.set(selectedRow, updatedCustomer);
            updateCustomerTable();
        } else {
            UIUtils.showWarningDialog(this, "Please select a customer to update.");
        }
    }
    
    private void removeCustomer() {
        int selectedRow = ((JTable) ((JScrollPane) getComponent(2)).getViewport().getView()).getSelectedRow();
        if (selectedRow != -1) {
            customers.remove(selectedRow);
            updateCustomerTable();
            clearFields();
        } else {
            UIUtils.showWarningDialog(this, "Please select a customer to remove.");
        }
    }
    
    private Customer createCustomerFromFields() {
        String customerId = customerIdField.getText();
        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        return new Customer(customerId, name, email, phone);
    }
    
    private void updateCustomerTable() {
        tableModel.setRowCount(0);
        for (Customer customer : customers) {
            tableModel.addRow(new Object[]{
                customer.getCustomerId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhone()
            });
        }
    }
    
    private void clearFields() {
        customerIdField.setText("");
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
    }
    
    private void populateFields(Customer customer) {
        customerIdField.setText(customer.getCustomerId());
        nameField.setText(customer.getName());
        emailField.setText(customer.getEmail());
        phoneField.setText(customer.getPhone());
    }
}

