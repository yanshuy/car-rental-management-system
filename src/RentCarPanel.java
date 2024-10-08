import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class RentCarPanel extends JPanel {
    private List<Car> cars;
    private List<Customer> customers;
    private JComboBox<String> carCombo, customerCombo;
    private JTextField rentDaysField;
    private JTextArea rentDetailsArea;

    public RentCarPanel(List<Car> cars, List<Customer> customers) {
        this.cars = cars;
        this.customers = customers;
        setLayout(new BorderLayout(20, 20));
        setBackground(CarRentalManagementSystem.SECONDARY_COLOR);
        setBorder(new EmptyBorder(30, 30, 30, 30));

        initComponents();
    }

    private void initComponents() {
        add(createFormPanel(), BorderLayout.NORTH);
        add(createButtonPanel(), BorderLayout.CENTER);
        add(createDetailsPanel(), BorderLayout.SOUTH);
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

        carCombo = new JComboBox<>();
        customerCombo = new JComboBox<>();
        rentDaysField = UIUtils.createStyledTextField();

        updateCombos();

        UIUtils.addFormField(formPanel, "Select Car", carCombo, gbc, 0);
        UIUtils.addFormField(formPanel, "Select Customer", customerCombo, gbc, 1);
        UIUtils.addFormField(formPanel, "Rent Days", rentDaysField, gbc, 2);

        return formPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(CarRentalManagementSystem.SECONDARY_COLOR);

        JButton rentBtn = UIUtils.createStyledButton("Rent Car", CarRentalManagementSystem.PRIMARY_COLOR);
        JButton clearBtn = UIUtils.createStyledButton("Clear Fields", Color.GRAY);

        rentBtn.addActionListener(e -> rentCar());
        clearBtn.addActionListener(e -> clearFields());

        buttonPanel.add(rentBtn);
        buttonPanel.add(clearBtn);

        return buttonPanel;
    }

    private JPanel createDetailsPanel() {
        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.setBackground(Color.WHITE);
        detailsPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(CarRentalManagementSystem.PRIMARY_COLOR, 2),
            new EmptyBorder(10, 10, 10, 10)
        ));

        rentDetailsArea = new JTextArea(10, 30);
        rentDetailsArea.setEditable(false);
        rentDetailsArea.setFont(new Font("Arial", Font.PLAIN, 14));

        detailsPanel.add(new JScrollPane(rentDetailsArea), BorderLayout.CENTER);

        return detailsPanel;
    }

    private void rentCar() {
        String selectedCar = (String) carCombo.getSelectedItem();
        String selectedCustomer = (String) customerCombo.getSelectedItem();
        String rentDays = rentDaysField.getText();

        if (selectedCar == null || selectedCustomer == null || rentDays.isEmpty()) {
            UIUtils.showWarningDialog(this, "Please fill all fields.");
            return;
        }

        try {
            int days = Integer.parseInt(rentDays);
            Car car = getCar(selectedCar);
            Customer customer = getCustomer(selectedCustomer);

            if (car != null && customer != null) {
                double totalPrice = car.getPrice() * days;
                car.setStatus("Rented");

                String rentDetails = String.format("Rental Details:\n\n" +
                    "Customer: %s\n" +
                    "Car: %s %s\n" +
                    "Rental Period: %d days\n" +
                    "Total Price: ₹%.2f\n",
                    customer.getName(), car.getBrand(), car.getModel(), days, totalPrice);

                rentDetailsArea.setText(rentDetails);
                updateCombos();
                clearFields();
            }
        } catch (NumberFormatException ex) {
            UIUtils.showErrorDialog(this, "Invalid number of days.");
        }
    }

    private void clearFields() {
        carCombo.setSelectedIndex(0);
        customerCombo.setSelectedIndex(0);
        rentDaysField.setText("");
        rentDetailsArea.setText("");
    }

    private void updateCombos() {
        carCombo.removeAllItems();
        customerCombo.removeAllItems();

        for (Car car : cars) {
            if ("Available".equals(car.getStatus())) {
                carCombo.addItem(car.getBrand() + " " + car.getModel() + " (" + car.getCarId() + ")");
            }
        }

        for (Customer customer : customers) {
            customerCombo.addItem(customer.getName() + " (" + customer.getCustomerId() + ")");
        }
    }

    private Car getCar(String selectedCar) {
        String carId = selectedCar.substring(selectedCar.lastIndexOf("(") + 1, selectedCar.lastIndexOf(")"));
        return cars.stream()
            .filter(car -> car.getCarId().equals(carId))
            .findFirst()
            .orElse(null);
    }

    private Customer getCustomer(String selectedCustomer) {
        String customerId = selectedCustomer.substring(selectedCustomer.lastIndexOf("(") + 1, selectedCustomer.lastIndexOf(")"));
        return customers.stream()
            .filter(customer -> customer.getCustomerId().equals(customerId))
            .findFirst()
            .orElse(null);
    }
}