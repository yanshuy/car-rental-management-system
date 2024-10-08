import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class ReturnCarPanel extends JPanel {
    private List<Car> cars;
    private JComboBox<String> carCombo;
    private JTextArea returnDetailsArea;

    public ReturnCarPanel(List<Car> cars) {
        this.cars = cars;
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
        updateCarCombo();

        UIUtils.addFormField(formPanel, "Select Car to Return", carCombo, gbc, 0);

        return formPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(CarRentalManagementSystem.SECONDARY_COLOR);

        JButton returnBtn = UIUtils.createStyledButton("Return Car", CarRentalManagementSystem.PRIMARY_COLOR);
        JButton clearBtn = UIUtils.createStyledButton("Clear", Color.GRAY);

        returnBtn.addActionListener(e -> returnCar());
        clearBtn.addActionListener(e -> clearFields());

        buttonPanel.add(returnBtn);
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

        returnDetailsArea = new JTextArea(10, 30);
        returnDetailsArea.setEditable(false);
        returnDetailsArea.setFont(new Font("Arial", Font.PLAIN, 14));

        detailsPanel.add(new JScrollPane(returnDetailsArea), BorderLayout.CENTER);

        return detailsPanel;
    }

    private void returnCar() {
        String selectedCar = (String) carCombo.getSelectedItem();

        if (selectedCar == null) {
            UIUtils.showWarningDialog(this, "Please select a car to return.");
            return;
        }

        Car car = getCar(selectedCar);

        if (car != null) {
            car.setStatus("Available");

            String returnDetails = String.format("Car Return Details:\n\n" +
                "Car: %s %s\n" +
                "Car ID: %s\n" +
                "Status: Available\n",
                car.getBrand(), car.getModel(), car.getCarId());

            returnDetailsArea.setText(returnDetails);
            updateCarCombo();
        }
    }

    private void clearFields() {
        carCombo.setSelectedIndex(0);
        returnDetailsArea.setText("");
    }

    private void updateCarCombo() {
        carCombo.removeAllItems();

        for (Car car : cars) {
            if ("Rented".equals(car.getStatus())) {
                carCombo.addItem(car.getBrand() + " " + car.getModel() + " (" + car.getCarId() + ")");
            }
        }
    }

    private Car getCar(String selectedCar) {
        String carId = selectedCar.substring(selectedCar.lastIndexOf("(") + 1, selectedCar.lastIndexOf(")"));
        return cars.stream()
            .filter(car -> car.getCarId().equals(carId))
            .findFirst()
            .orElse(null);
    }
}