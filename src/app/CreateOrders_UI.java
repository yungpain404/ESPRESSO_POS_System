package app;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CreateOrders_UI extends JFrame {

    public CreateOrders_UI() {
        setTitle("Espresso Menu POS - Pure Java Optimized");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 850);
        setLocationRelativeTo(null);
        
        // Định nghĩa màu sắc
        Color bgMain = Color.decode("#fbfbe1");
        Color bgCard = Color.decode("#FFFFFF");
        Color accentBrown = Color.decode("#573824");
        Color textGray = Color.decode("#8E8E8E");
        Color bgSidebar = Color.decode("#f4f4da");

        getContentPane().setBackground(bgMain);
        setLayout(new BorderLayout());
        

        //Header
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setOpaque(false);
        pnlHeader.setBorder(new EmptyBorder(15, 25, 10, 25));

        JTextField txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(400, 40));
        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search menu items..."); 
        txtSearch.putClientProperty(FlatClientProperties.STYLE, "arc: 25; background: #FFFFFF; borderWidth: 0; focusWidth: 0;");
        
        // Profile bên phải
        JPanel pnlProfile = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        pnlProfile.setOpaque(false);

        
        JPanel pnlUser = new JPanel();
        pnlUser.setLayout(new BoxLayout(pnlUser, BoxLayout.Y_AXIS));
        pnlUser.setOpaque(false);
        
        JLabel lblUserName = new JLabel("Alex Reed", SwingConstants.RIGHT);
        lblUserName.setFont(new Font("Inter", Font.BOLD, 14));
        lblUserName.setAlignmentX(Component.RIGHT_ALIGNMENT);
        
        JLabel lblRole = new JLabel("Head Barista");
        lblRole.setForeground(textGray);
        lblRole.setFont(new Font("Inter", Font.PLAIN, 12));
        lblRole.setAlignmentX(Component.RIGHT_ALIGNMENT);
        
        pnlUser.add(lblUserName);
        pnlUser.add(lblRole);
        pnlProfile.add(pnlUser);

        pnlHeader.add(txtSearch, BorderLayout.WEST);
        pnlHeader.add(pnlProfile, BorderLayout.EAST);
        add(pnlHeader, BorderLayout.NORTH);

        // Body
        JPanel pnlBody = new JPanel(new BorderLayout());
        pnlBody.setOpaque(false);
        add(pnlBody, BorderLayout.CENTER);

        // cột trái: Menu
        JPanel pnlLeft = new JPanel(new BorderLayout());
        pnlLeft.setOpaque(false);
        pnlLeft.setBorder(new EmptyBorder(10, 25, 20, 10)); 
        
        // Tiêu đề & Tabs
        JPanel pnlMenuHeader = new JPanel(new BorderLayout());
        pnlMenuHeader.setOpaque(false);
        JLabel lblTitle = new JLabel("Espresso Menu");
        lblTitle.setFont(new Font("Inter", Font.BOLD, 26));
        lblTitle.setForeground(accentBrown);
        
        JPanel pnlTabs = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        pnlTabs.setOpaque(false);
        String[] category = {"All", "Coffee", "Tea", "Pastries"};
        for (String cat : category) {
            JButton btnTab = new JButton(cat);
            String btnStyle = cat.equals("All") 
                ? "arc: 20; background: #e6e6cc; foreground: #573824; borderWidth: 0;" 
                : "arc: 20; background: #f5f5db; foreground: #7d7862; borderWidth: 0;";
            btnTab.putClientProperty(FlatClientProperties.STYLE, btnStyle);
            pnlTabs.add(btnTab);
        }
        pnlMenuHeader.add(lblTitle, BorderLayout.WEST);
        pnlMenuHeader.add(pnlTabs, BorderLayout.SOUTH);
        pnlLeft.add(pnlMenuHeader, BorderLayout.NORTH);

        // Danh sách sản phẩm
        JPanel pnlProductGrid = new JPanel(new GridLayout(0, 3, 15, 15));
        pnlProductGrid.setOpaque(false);
        pnlProductGrid.setBorder(new EmptyBorder(15, 0, 0, 0));

        // Thêm 6 card sản phẩm
        for (int i = 0; i < 6; i++) {
            pnlProductGrid.add(createProductCard("Velvet Cappuccino", "Double shot, steamed silk milk with thin foam layer", "$4.50", bgCard, accentBrown, textGray));
        }

        JScrollPane scrPane = new JScrollPane(pnlProductGrid);
        scrPane.setBorder(null);
        scrPane.setOpaque(false);
        scrPane.getViewport().setOpaque(false);
        scrPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrPane.getVerticalScrollBar().setUnitIncrement(15);
        
        pnlLeft.add(scrPane, BorderLayout.CENTER);
        pnlBody.add(pnlLeft, BorderLayout.CENTER);

        //Cart
        JPanel pnlCart = new JPanel(new BorderLayout());
        pnlCart.setPreferredSize(new Dimension(340, 0));
        pnlCart.setBackground(bgSidebar); 
        pnlCart.setBorder(new EmptyBorder(25, 20, 25, 20));

        JLabel lblCart = new JLabel("Current Cart");
        lblCart.setForeground(accentBrown);
        lblCart.setFont(new Font("Inter", Font.BOLD, 20));
        pnlCart.add(lblCart, BorderLayout.NORTH);

        // Footer Cart (Thanh toán)
        JPanel pnlCartFooter = new JPanel();
        pnlCartFooter.setLayout(new BoxLayout(pnlCartFooter, BoxLayout.Y_AXIS));
        pnlCartFooter.setOpaque(false);

        JLabel lblTotalLabel = new JLabel("Total Amount");
        lblTotalLabel.setForeground(textGray);
        lblTotalLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblTotalPrice = new JLabel("$18.99");
        lblTotalPrice.setFont(new Font("Inter", Font.BOLD, 30));
        lblTotalPrice.setForeground(accentBrown);
        lblTotalPrice.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnComplete = new JButton("Complete Order →");
        btnComplete.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
        btnComplete.setBackground(accentBrown);
        btnComplete.setForeground(Color.WHITE);
        btnComplete.setFont(new Font("Inter", Font.BOLD, 15));
        btnComplete.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnComplete.putClientProperty(FlatClientProperties.STYLE, "arc: 25; focusWidth: 0;");

        pnlCartFooter.add(lblTotalLabel);
        pnlCartFooter.add(Box.createVerticalStrut(5));
        pnlCartFooter.add(lblTotalPrice);
        pnlCartFooter.add(Box.createVerticalStrut(20));
        pnlCartFooter.add(btnComplete);
        
        pnlCart.add(pnlCartFooter, BorderLayout.SOUTH);
        pnlBody.add(pnlCart, BorderLayout.EAST);
    }

    // Hàm helper tạo Card sản phẩm nhỏ gọn
    private JPanel createProductCard(String name, String desc, String price, Color bg, Color brown, Color gray) {
        JPanel pnlCard = new JPanel(new BorderLayout());
        pnlCard.setBackground(bg);
        pnlCard.putClientProperty(FlatClientProperties.STYLE, "arc: 25;");
        
        // Ảnh
        JLabel lblImgPlaceholder = new JLabel("IMAGE", SwingConstants.CENTER);
        lblImgPlaceholder.setPreferredSize(new Dimension(0, 110));
        lblImgPlaceholder.setOpaque(true);
        lblImgPlaceholder.setBackground(Color.decode("#EEEEEE"));
        lblImgPlaceholder.setForeground(Color.LIGHT_GRAY);
        lblImgPlaceholder.putClientProperty(FlatClientProperties.STYLE, "arc: 25;");

        // Panel thông tin
        JPanel pnlInfo = new JPanel();
        pnlInfo.setLayout(new BoxLayout(pnlInfo, BoxLayout.Y_AXIS));
        pnlInfo.setOpaque(false);
        pnlInfo.setBorder(new EmptyBorder(10, 12, 12, 12));

        JLabel lblName = new JLabel(name);
        lblName.setFont(new Font("Inter", Font.BOLD, 15));
        lblName.setForeground(Color.BLACK);
        lblName.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Mô tả
        JTextArea txaDesc = new JTextArea(desc);
        txaDesc.setLineWrap(true);
        txaDesc.setWrapStyleWord(true);
        txaDesc.setEditable(false);
        txaDesc.setFocusable(false);
        txaDesc.setOpaque(false);
        txaDesc.setFont(new Font("Inter", Font.PLAIN, 11));
        txaDesc.setForeground(gray);
        txaDesc.setAlignmentX(Component.LEFT_ALIGNMENT);
        txaDesc.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32)); // Giới hạn 2 dòng

        JLabel lblPrice = new JLabel(price);
        lblPrice.setForeground(brown);
        lblPrice.setFont(new Font("Inter", Font.BOLD, 16));
        lblPrice.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        pnlInfo.add(lblName);
        pnlInfo.add(Box.createVerticalStrut(4));
        pnlInfo.add(txaDesc);
        pnlInfo.add(Box.createVerticalStrut(8));
        pnlInfo.add(lblPrice);

        pnlCard.add(lblImgPlaceholder, BorderLayout.NORTH);
        pnlCard.add(pnlInfo, BorderLayout.CENTER);
        
        return pnlCard;
    }

    public static void main(String[] args) {
        FlatLightLaf.setup(); 
        UIManager.put("Button.arc", 20); 
        UIManager.put("Component.arc", 20); 
        
        new CreateOrders_UI().setVisible(true);
    }
}