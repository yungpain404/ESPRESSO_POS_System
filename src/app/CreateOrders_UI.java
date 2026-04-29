package app;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
@SuppressWarnings("serial")
public class CreateOrders_UI extends JFrame implements ActionListener {
	static {
	    try {
	        FlatLightLaf.setup();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

		private JButton btnComplete;
    public CreateOrders_UI() {
        setTitle("Espresso Menu POS - Pure Java Optimized");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);
        // Định nghĩa màu sắc
        Color bgMain = Color.decode("#fbfbe1");
        Color bgCard = Color.decode("#FFFFFF");
        Color accentBrown = Color.decode("#573824");
        Color textGray = Color.decode("#8E8E8E");
        Color bgSidebar = Color.decode("#f4f4da");
        
        JPanel pnlMain = new JPanel(new BorderLayout());
        pnlMain.setOpaque(false);
     // add vào mainPanel thay vì JFrame
        
        getContentPane().setBackground(bgMain);
        setLayout(new BorderLayout());
        add(createSidebar(), BorderLayout.WEST);
        add(pnlMain, BorderLayout.CENTER);

        //Header
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setOpaque(false);
        pnlHeader.setBorder(new EmptyBorder(15, 25, 10, 25));
        
        
        JTextField txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(400, 40));
        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search menu items...");
	    txtSearch.putClientProperty("JComponent.outlineWidth", 0);
	    txtSearch.putClientProperty("JSeparator.height", 0);
        
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
        pnlMain.add(pnlHeader, BorderLayout.NORTH);

        // Body
        JPanel pnlBody = new JPanel(new BorderLayout());
        pnlBody.setOpaque(false);
        pnlMain.add(pnlBody, BorderLayout.CENTER);

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
            btnTab.setBorderPainted(false);
            btnTab.setFocusPainted(false);
            
            if (cat.equals("All")) {
                btnTab.setBackground(Color.decode("#e6e6cc"));
                btnTab.setForeground(Color.decode("#573824"));
            } else {
                btnTab.setBackground(Color.decode("#f5f5db"));
                btnTab.setForeground(Color.decode("#7d7862"));
            }
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

        btnComplete = new JButton("Complete Order →");
        btnComplete.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
        btnComplete.setBackground(accentBrown);
        btnComplete.setForeground(Color.WHITE);
        btnComplete.setFont(new Font("Inter", Font.BOLD, 15));
        btnComplete.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnComplete.putClientProperty("JComponent.outlineWidth", 0);

        pnlCartFooter.add(lblTotalLabel);
        pnlCartFooter.add(Box.createVerticalStrut(5));
        pnlCartFooter.add(lblTotalPrice);
        pnlCartFooter.add(Box.createVerticalStrut(20));
        pnlCartFooter.add(btnComplete);
        
        pnlCart.add(pnlCartFooter, BorderLayout.SOUTH);
        pnlBody.add(pnlCart, BorderLayout.EAST);
        
        btnComplete.addActionListener(this);
    }

    // Hàm helper tạo Card sản phẩm nhỏ gọn
    private JPanel createProductCard(String name, String desc, String price, Color bg, Color brown, Color gray) {
        JPanel pnlCard = new JPanel(new BorderLayout());
        pnlCard.setBackground(bg);
        
        // Ảnh
        JLabel lblImgPlaceholder = new JLabel("IMAGE", SwingConstants.CENTER);
        lblImgPlaceholder.setPreferredSize(new Dimension(0, 110));
        lblImgPlaceholder.setOpaque(true);
        lblImgPlaceholder.setBackground(Color.decode("#EEEEEE"));
        lblImgPlaceholder.setForeground(Color.LIGHT_GRAY);

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
    
    private JPanel createSidebar() {
	    JPanel sidebar = new JPanel();
	    sidebar.setPreferredSize(new Dimension(250, 0));
	    sidebar.setBackground(new Color(245, 245, 230));
	    sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
	    sidebar.setBorder(new EmptyBorder(30, 20, 30, 20)); // Margin cho menu
	
	    // Logo
	    JLabel lblLogo = new JLabel("ESPRESSO LOGIC");
	    lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 20));
	    lblLogo.setForeground(new Color(85, 55, 34));
	    lblLogo.setAlignmentX(Component.LEFT_ALIGNMENT); // Căn trái label
	    sidebar.add(lblLogo);
	    sidebar.add(Box.createRigidArea(new Dimension(0, 40)));
	
	    
	    String[][] menuData = {
	        {"Menu", "img/menu.png"},
	        {"Menu Management", "img/menumanagement.png"}, 
	        {"Analytics", "img/analytics.png"}
	    };
	
	    for (String[] data : menuData) {
	        JButton btn = createMenuButton(data[0], data[1]);
	        
	        // Highlight mục đang chọn ( Menu mangement )
	        if (data[0].equals("Menu Management")) {
	            btn.setBackground(new Color(230, 230, 210));
	            btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
	            btn.putClientProperty(FlatClientProperties.BUTTON_TYPE, 10);
	        } else {
	            btn.setContentAreaFilled(false); // Làm các nút khác trong suốt
	        }
	        if (data[0].equals("Menu")) {
                btn.addActionListener(e -> {
                    CreateOrders_UI nextFrame = new CreateOrders_UI();
                    nextFrame.setBounds(this.getBounds()); 
                    nextFrame.setExtendedState(this.getExtendedState());
                    nextFrame.setVisible(true);
                    this.dispose();
                });
            } else if (data[0].equals("Analytics")) {
                btn.addActionListener(e -> {
                    Dashboard_UI nextFrame = new Dashboard_UI();
                    nextFrame.setBounds(this.getBounds());
                    nextFrame.setExtendedState(this.getExtendedState());
                    nextFrame.setVisible(true);
                    this.dispose();
                });
            }
	        sidebar.add(btn);
	        sidebar.add(Box.createRigidArea(new Dimension(0, 10))); // Khoảng cách giữa các item
	    }
	
	    sidebar.add(Box.createVerticalGlue());
	    
	    JButton btnNewOrder = new JButton(" + New Order ");
        btnNewOrder.setBackground(new Color(85, 55, 34));
        btnNewOrder.setForeground(Color.WHITE);
        btnNewOrder.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnNewOrder.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        btnNewOrder.setFocusPainted(false);
        btnNewOrder.setBorderPainted(false);
        // 5. Thay đổi font chữ to hơn một chút cho cân đối với nút lớn
        btnNewOrder.setFont(new Font("Segoe UI", Font.BOLD, 14));
        sidebar.add(btnNewOrder);
        
	    return sidebar;
   }
   
   private JButton createMenuButton(String text, String iconPath) {
	    // Tạo Icon và scale nhỏ lại cho vừa dòng
	    ImageIcon icon = new ImageIcon(iconPath);
	    Image scaled = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
	    
	    JButton btn = new JButton(text, new ImageIcon(scaled));
	    
	    // THUẬT TOÁN CĂN LỀ QUAN TRỌNG:
	    btn.setHorizontalAlignment(SwingConstants.LEFT); // Chữ và icon dồn sang trái
	    btn.setIconTextGap(15); // Khoảng cách giữa icon và chữ
	    btn.setAlignmentX(Component.LEFT_ALIGNMENT); // Căn nút thẳng hàng trong BoxLayout
	    btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45)); // Nút dài hết cỡ sidebar
	    
	    // Xóa bỏ các hiệu ứng thừa của Swing cũ
	    btn.setFocusPainted(false);
	    btn.setBorder(new EmptyBorder(10, 15, 10, 15)); // Padding trong của nút
	    btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
	    btn.setForeground(new Color(85, 55, 34));
	    btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
	    
	    return btn;
	}

 @Override
 public void actionPerformed(ActionEvent e) {
	Object o = e.getSource();
	if (o.equals(btnComplete)) {
		Invoice_UI nextFrame = new Invoice_UI();

	    // Giữ nguyên kích thước + trạng thái
	    nextFrame.setBounds(this.getBounds());
	    nextFrame.setExtendedState(this.getExtendedState());

	    nextFrame.setVisible(true);

	    // Đóng màn hiện tại
	    this.dispose();
	}
	
 }
}