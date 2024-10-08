import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SidebarPanel extends JPanel {
    private JButton manageCarBtn, manageCustomerBtn, rentCarBtn, returnCarBtn;

    public SidebarPanel(JFrame parent) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(CarRentalManagementSystem.PRIMARY_COLOR);
        setPreferredSize(new Dimension(200, parent.getHeight()));
        setBorder(new EmptyBorder(20, 0, 0, 0));
        
        initComponents();
    }
    
    private void initComponents() {
        manageCarBtn = createSidebarButton("Manage Cars");
        manageCustomerBtn = createSidebarButton("Manage Customers");
        rentCarBtn = createSidebarButton("Rent Car");
        returnCarBtn = createSidebarButton("Return Car");
        
        add(manageCarBtn);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(manageCustomerBtn);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(rentCarBtn);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(returnCarBtn);
    }
    
    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(180, 40));
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(CarRentalManagementSystem.PRIMARY_COLOR);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(CarRentalManagementSystem.PRIMARY_COLOR.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(CarRentalManagementSystem.PRIMARY_COLOR);
            }
        });
        return button;
    }
    
    public JButton getManageCarBtn() {
        return manageCarBtn;
    }
    
    public JButton getManageCustomerBtn() {
        return manageCustomerBtn;
    }
    
    public JButton getRentCarBtn() {
        return rentCarBtn;
    }

    public JButton getReturnCarBtn() {
        return returnCarBtn;
    }

}
