import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class CarRentalManagementSystem extends JFrame {
    private JPanel mainPanel, sidebarPanel, contentPanel;
    private JButton manageCarBtn, manageCustomerBtn, manageRentalBtn;
    private CardLayout cardLayout;
    private List<Car> cars;

    // Color scheme
    private Color primaryColor = new Color(41, 128, 185); // Blue
    private Color secondaryColor = new Color(236, 240, 241); // Light Gray
    private Color accentColor = new Color(231, 76, 60); // Red
    private Color textColor = new Color(52, 73, 94); // Dark Gray

    public CarRentalManagementSystem() {
        cars = new ArrayList<>();
        setTitle("Car Rental Management System");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        initComponents();
        
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void initComponents() {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(secondaryColor);
        
        // Sidebar panel
        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(primaryColor);
        sidebarPanel.setPreferredSize(new Dimension(200, getHeight()));
        sidebarPanel.setBorder(new EmptyBorder(20, 0, 0, 0));
        
        JLabel titleLabel = new JLabel("Car Rental System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebarPanel.add(titleLabel);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        
        manageCarBtn = createSidebarButton("Manage Cars");
        manageCustomerBtn = createSidebarButton("Manage Customers");
        manageRentalBtn = createSidebarButton("Manage Rentals");
        
        sidebarPanel.add(manageCarBtn);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebarPanel.add(manageCustomerBtn);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebarPanel.add(manageRentalBtn);
        
        mainPanel.add(sidebarPanel, BorderLayout.WEST);
        
        // Content panel
        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);
        contentPanel.setBackground(secondaryColor);
        
        JPanel manageCarPanel = createManageCarPanel();
        contentPanel.add(manageCarPanel, "MANAGE_CAR");
        
        // Placeholder panels for other features
        contentPanel.add(createPlaceholderPanel("Manage Customers"), "MANAGE_CUSTOMER");
        contentPanel.add(createPlaceholderPanel("Manage Rentals"), "MANAGE_RENTAL");
        
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        add(mainPanel);
        
        // Action listeners for navigation
        manageCarBtn.addActionListener(e -> cardLayout.show(contentPanel, "MANAGE_CAR"));
        manageCustomerBtn.addActionListener(e -> cardLayout.show(contentPanel, "MANAGE_CUSTOMER"));
        manageRentalBtn.addActionListener(e -> cardLayout.show(contentPanel, "MANAGE_RENTAL"));
    }
    
    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(180, 40));
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(primaryColor);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(primaryColor.darker());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(primaryColor);
            }
        });
        return button;
    }
    
    private JPanel createManageCarPanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(secondaryColor);
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));
        
        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(primaryColor, 2),
            new EmptyBorder(20, 20, 20, 20)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JTextField carIdField = createStyledTextField();
        JTextField brandField = createStyledTextField();
        JTextField modelField = createStyledTextField();
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Available", "Booked"});
        statusCombo.setFont(new Font("Arial", Font.PLAIN, 16));
        JTextField priceField = createStyledTextField();
        JComboBox<String> typeCombo = new JComboBox<>(new String[]{"Sedan", "SUV", "Hatchback", "Convertible", "Van"});
        typeCombo.setFont(new Font("Arial", Font.PLAIN, 16));
        
        addFormField(formPanel, "Car ID:", carIdField, gbc, 0);
        addFormField(formPanel, "Brand:", brandField, gbc, 1);
        addFormField(formPanel, "Model:", modelField, gbc, 2);
        addFormField(formPanel, "Status:", statusCombo, gbc, 3);
        addFormField(formPanel, "Price per Day:", priceField, gbc, 4);
        addFormField(formPanel, "Type:", typeCombo, gbc, 5);
        
        panel.add(formPanel, BorderLayout.NORTH);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(secondaryColor);
        JButton addBtn = createStyledButton("Add Car", primaryColor);
        JButton updateBtn = createStyledButton("Update Car", primaryColor);
        JButton removeBtn = createStyledButton("Remove Car", accentColor);
        JButton clearBtn = createStyledButton("Clear Fields", Color.GRAY);
        
        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(removeBtn);
        buttonPanel.add(clearBtn);
        
        panel.add(buttonPanel, BorderLayout.CENTER);
        
        // Car table
        String[] columnNames = {"Car ID", "Brand", "Model", "Status", "Price/Day", "Type"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable carTable = new JTable(tableModel);
        carTable.setFont(new Font("Arial", Font.PLAIN, 14));
        carTable.setRowHeight(25);
        carTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        carTable.getTableHeader().setBackground(primaryColor);
        carTable.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(carTable);
        scrollPane.setPreferredSize(new Dimension(panel.getWidth(), 300));
        panel.add(scrollPane, BorderLayout.SOUTH);
        
        // Action listeners
        addBtn.addActionListener(e -> {
            try {
                String carId = carIdField.getText();
                String brand = brandField.getText();
                String model = modelField.getText();
                String status = (String) statusCombo.getSelectedItem();
                double price = Double.parseDouble(priceField.getText());
                String type = (String) typeCombo.getSelectedItem();
                
                Car newCar = new Car(carId, brand, model, status, price, type);
                cars.add(newCar);
                updateCarTable(tableModel);
                clearFields(carIdField, brandField, modelField, statusCombo, priceField, typeCombo);
            } catch (NumberFormatException ex) {
                showErrorDialog("Invalid input. Please check your entries.");
            }
        });
        
        updateBtn.addActionListener(e -> {
            int selectedRow = carTable.getSelectedRow();
            if (selectedRow != -1) {
                try {
                    Car selectedCar = cars.get(selectedRow);
                    selectedCar.setCarId(carIdField.getText());
                    selectedCar.setBrand(brandField.getText());
                    selectedCar.setModel(modelField.getText());
                    selectedCar.setStatus((String) statusCombo.getSelectedItem());
                    selectedCar.setPrice(Double.parseDouble(priceField.getText()));
                    selectedCar.setType((String) typeCombo.getSelectedItem());
                    updateCarTable(tableModel);
                } catch (NumberFormatException ex) {
                    showErrorDialog("Invalid input. Please check your entries.");
                }
            } else {
                showWarningDialog("Please select a car to update.");
            }
        });
        
        removeBtn.addActionListener(e -> {
            int selectedRow = carTable.getSelectedRow();
            if (selectedRow != -1) {
                cars.remove(selectedRow);
                updateCarTable(tableModel);
                clearFields(carIdField, brandField, modelField, statusCombo, priceField, typeCombo);
            } else {
                showWarningDialog("Please select a car to remove.");
            }
        });
        
        clearBtn.addActionListener(e -> clearFields(carIdField, brandField, modelField, statusCombo, priceField, typeCombo));
        
        carTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = carTable.getSelectedRow();
            if (selectedRow != -1) {
                Car selectedCar = cars.get(selectedRow);
                carIdField.setText(selectedCar.getCarId());
                brandField.setText(selectedCar.getBrand());
                modelField.setText(selectedCar.getModel());
                statusCombo.setSelectedItem(selectedCar.getStatus());
                priceField.setText(String.valueOf(selectedCar.getPrice()));
                typeCombo.setSelectedItem(selectedCar.getType());
            }
        });
        
        return panel;
    }
    
    private JTextField createStyledTextField() {
        JTextField field = new JTextField(20);
        field.setFont(new Font("Arial", Font.PLAIN, 16));
        return field;
    }
    
    private void addFormField(JPanel panel, String label, JComponent field, GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(labelComponent, gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(field, gbc);
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(140, 40));
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
        return button;
    }
    
    private JPanel createPlaceholderPanel(String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(secondaryColor);
        JLabel label = new JLabel(title + " (Not Implemented)");
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(textColor);
        label.setHorizontalAlignment(JLabel.CENTER);
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }
    
    private void updateCarTable(DefaultTableModel model) {
        model.setRowCount(0);
        for (Car car : cars) {
            model.addRow(new Object[]{
                car.getCarId(),
                car.getBrand(),
                car.getModel(),
                car.getStatus(),
                String.format("$%.2f", car.getPrice()),
                car.getType()
            });
        }
    }
    
    private void clearFields(JTextField carIdField, JTextField brandField, JTextField modelField, JComboBox<String> statusCombo, JTextField priceField, JComboBox<String> typeCombo) {
        carIdField.setText("");
        brandField.setText("");
        modelField.setText("");
        statusCombo.setSelectedIndex(0);
        priceField.setText("");
        typeCombo.setSelectedIndex(0);
    }
    
    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    private void showWarningDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new CarRentalManagementSystem());
    }
}

class Car {
    private String carId;
    private String brand;
    private String model;
    private String status;
    private double price;
    private String type;
    
    // Constructor
    public Car(String carId, String brand, String model, String status, double price, String type) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.status = status;
        this.price = price;
        this.type = type;
    }
    
    // Getters and Setters
    public String getCarId() {
        return carId;
    }
    
    public void setCarId(String carId) {
        this.carId = carId;
    }
    
    public String getBrand() {
        return brand;
    }
    
    public void setBrand(String brand) {
        this.brand = brand;
    }
    
    public String getModel() {
        return model;
    }
    
    public void setModel(String model) {
        this.model = model;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
}
