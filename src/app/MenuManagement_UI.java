package app;

import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

@SuppressWarnings("serial")
public class MenuManagement_UI extends JFrame{

    public MenuManagement_UI() {
        setTitle("Espresso Logic - Menu Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);
        // Layout chính: Sidebar bên trái và Main Content bên phải
        setLayout(new BorderLayout());

        add(createSidebar(), BorderLayout.WEST);
        add(createMainContent(), BorderLayout.CENTER);
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
        btnNewOrder.putClientProperty(FlatClientProperties.STYLE, "arc: 20");
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

    private JPanel createMainContent() {
    	JPanel main = new JPanel(new BorderLayout());
        main.setBackground(new Color(251, 251, 240));
        main.setBorder(new EmptyBorder(30, 40, 30, 40));

        // --- Header Area ---
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        // Bên trái: Title & Subtitle
        JPanel titlePanel = new JPanel(new GridLayout(2, 1));
        titlePanel.setOpaque(false);
        JLabel lblTitle = new JLabel("Menu Management");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        JLabel lblSub = new JLabel("Curate your daily artisanal offerings.");
        lblSub.setForeground(Color.GRAY);
        titlePanel.add(lblTitle);
        titlePanel.add(lblSub);

        // Bên phải: Nút Add Item
        @SuppressWarnings("unused")
		JPanel pnlWrapperBtnAdd = new JPanel();
        JButton btnAdd = new JButton("Add Item");
        btnAdd.setBackground(new Color(85, 55, 34));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAdd.setIcon(new ImageIcon(new ImageIcon("img/additem.png").getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH)));
        btnAdd.putClientProperty(FlatClientProperties.STYLE, "arc: 15");
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Gộp Title và Button
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        topBar.add(titlePanel, BorderLayout.WEST);
        topBar.add(btnAdd, BorderLayout.EAST);
        
        header.add(topBar, BorderLayout.CENTER);
        
        // Search bar (FlatLaf style)
        JTextField txtSearch = new JTextField(20);
        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search menu items...");
        txtSearch.putClientProperty(FlatClientProperties.STYLE, "arc: 25");
        
        header.add(txtSearch, BorderLayout.NORTH); // Thu nhỏ lại theo ý bạn
        main.add(header, BorderLayout.NORTH);

        // Grid Menu Items
        JPanel grid = new JPanel(new GridLayout(0, 3, 25, 25));
        grid.setOpaque(false);
        grid.setBorder(new EmptyBorder(30, 0, 0, 0));

        // Thêm các thẻ món ăn mẫu
        grid.add(createFoodCard("Artisanal Cortado", "$4.50","Description", "img/cortado.png","Drink", true));
        grid.add(createFoodCard("Flat White", "$4.25","Description", "img/flatwhite.png","Drink", false));
        grid.add(createFoodCard("Almond Croissant","$5.50", "Description", "img/almondcroissant.png","Drink", false));
        grid.add(createFoodCard("Almond Croissant","$5.50", "Description", "img/almondcroissant.png","Drink", false));
        grid.add(createFoodCard("Almond Croissant","$5.50", "Description", "img/almondcroissant.png","Drink", false));
        grid.add(createFoodCard("Almond Croissant","$5.50", "Description", "img/almondcroissant.png","Drink", false));
        grid.add(createFoodCard("Almond Croissant","$5.50", "Description", "img/almondcroissant.png","Drink", false));
        grid.add(createFoodCard("Almond Croissant","$5.50", "Description", "img/almondcroissant.png","Drink", false));
        grid.add(createFoodCard("Almond Croissant","$5.50", "Description", "img/almondcroissant.png","Drink", false));

        JScrollPane scroll = new JScrollPane(grid);
        scroll.setBorder(null);
        main.add(scroll, BorderLayout.CENTER);

        return main;
    }

	private JPanel createFoodCard(String name, String price, String description, String imgPath, String type, boolean isSignature) {
	    // Kích thước cố định cho Card để không bị dãn
	    Dimension cardSize = new Dimension(230, 380);
	    JPanel card = new JPanel(new BorderLayout());
	    card.setPreferredSize(cardSize);
	    card.setMaximumSize(cardSize);
	    card.setBackground(Color.WHITE);
	    card.putClientProperty(FlatClientProperties.STYLE, "arc: 20");
	    card.setBorder(BorderFactory.createLineBorder(new Color(235, 235, 230)));
	
	    // 1. Ảnh minh họa (Bo góc phía trên)
	    JLabel lblImg = new JLabel();
	    int labelW = 280;
	    int labelH = 180;
	    lblImg.setPreferredSize(new Dimension(labelW, labelH));

	    try {
	        ImageIcon icon = new ImageIcon(imgPath);
	        if (icon.getIconWidth() > 0) {
	            int imgW = icon.getIconWidth();
	            int imgH = icon.getIconHeight();

	            // TÍNH TỈ LỆ ĐỂ ĐẠT HIỆU ỨNG COVER
	            double scaleW = (double) labelW / imgW;
	            double scaleH = (double) labelH / imgH;
	            
	            // Dùng Math.max để ảnh luôn phủ kín khung (giống object-fit: cover)
	            double scale = Math.max(scaleW, scaleH);

	            int targetW = (int) (imgW * scale);
	            int targetH = (int) (imgH * scale);

	            // Scale ảnh theo tỉ lệ mới
	            Image img = icon.getImage().getScaledInstance(targetW, targetH, Image.SCALE_SMOOTH);
	            lblImg.setIcon(new ImageIcon(img));
	            
	            // CĂN GIỮA (Cực kỳ quan trọng để crop đều các cạnh)
	            lblImg.setHorizontalAlignment(SwingConstants.CENTER);
	            lblImg.setVerticalAlignment(SwingConstants.CENTER);
	            
	            // Đảm bảo label không hiển thị phần ảnh tràn ra ngoài khung 280x180
	            lblImg.setBounds(0, 0, labelW, labelH);
	        }
	    } catch (Exception e) {
	        System.err.println("Lỗi nạp ảnh: " + e.getMessage());
	    }
	
	    // 2. Phần Info Wrapper (Sử dụng để tránh BorderLayout bị dãn CENTER)
	    JPanel infoWrapper = new JPanel(new BorderLayout());
	    infoWrapper.setOpaque(false);
	    
	    JPanel info = new JPanel();
	    info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
	    info.setOpaque(false);
	    info.setBorder(new EmptyBorder(15, 15, 10, 15));
	
	    JLabel lblName = new JLabel(name + "  " + price);
	    lblName.setFont(new Font("Segoe UI", Font.BOLD, 15));
	    lblName.setForeground(new Color(85, 55, 34));
	
	    JLabel lblDesc = new JLabel("<html><p style='width: 180px'>" + description + "</p></html>");
	    lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 12));
	    lblDesc.setForeground(Color.GRAY);
	
	    info.add(lblName);
	    info.add(Box.createRigidArea(new Dimension(0, 5)));
	    info.add(lblDesc);
	
	    // 3. Footer (Chứa Loại món & Dấu 3 chấm)
	    JPanel footer = new JPanel(new BorderLayout());
	    footer.setOpaque(false);
	    footer.setBorder(new EmptyBorder(0, 15, 15, 15));
	
	    // Label Type (Giống cái tag PASTRY trong ảnh)
	    JLabel lblType = new JLabel(type.toUpperCase());
	    lblType.setOpaque(true);
	    lblType.setBackground(new Color(210, 235, 235)); // Màu xanh nhạt
	    lblType.setForeground(new Color(60, 100, 100));
	    lblType.setFont(new Font("Segoe UI", Font.BOLD, 10));
	    lblType.setBorder(new EmptyBorder(3, 8, 3, 8));
	    lblType.putClientProperty(FlatClientProperties.COMPONENT_ROUND_RECT, "arc: 10");
	
	    // Dấu 3 chấm (Options)
	    JButton btnMore = new JButton("");
	    btnMore.setBorderPainted(false);
	    btnMore.setContentAreaFilled(false);
	    btnMore.setFont(new Font("Segoe UI", Font.BOLD, 18));
	    btnMore.setCursor(new Cursor(Cursor.HAND_CURSOR));
	    
	    
	    try {
	        // Bạn có thể tìm một file icon 3 chấm đặt tên là more.png trong thư mục img
	        ImageIcon icon = new ImageIcon("img/more.png"); 
	        if (icon.getIconWidth() != -1) {
	            Image img = icon.getImage().getScaledInstance(12, 12, Image.SCALE_SMOOTH);
	            btnMore.setIcon(new ImageIcon(img));
	        } else {
	            // Nếu không có file ảnh, dùng chữ chuẩn và ép font hỗ trợ Unicode
	            btnMore.setText("..."); 
	            btnMore.setFont(new Font("Arial", Font.BOLD, 18));
	        }
	    } catch (Exception e) {
	        btnMore.setText("...");
	    }
	
	    // Menu chuột phải (Popup)
	    JPopupMenu popupMenu = new JPopupMenu();
	    JMenuItem itemEdit = new JMenuItem("Sửa món");
	    JMenuItem itemDelete = new JMenuItem("Xóa món");
	    itemDelete.setForeground(Color.RED);
	    popupMenu.add(itemEdit);
	    popupMenu.add(itemDelete);
	
	    btnMore.addActionListener(e -> popupMenu.show(btnMore, 0, btnMore.getHeight()));
	
	    footer.add(lblType, BorderLayout.WEST);
	    footer.add(btnMore, BorderLayout.EAST);
	
	    infoWrapper.add(info, BorderLayout.NORTH);
	    infoWrapper.add(footer, BorderLayout.SOUTH);
	
	    card.add(lblImg, BorderLayout.NORTH);
	    card.add(infoWrapper, BorderLayout.CENTER);
	
	    return card;
	}

}