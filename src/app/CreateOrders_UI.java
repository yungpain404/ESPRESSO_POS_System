package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLightLaf;
import dao.HoaDon_DAO;
import dao.Mon_DAO;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.Mon;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class CreateOrders_UI extends JFrame implements ActionListener {
    static {
        try {
            FlatLightLaf.setup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private Color bgMain = Color.decode("#fbfbe1");
    private Color bgCard = Color.decode("#FFFFFF");
    private Color accentBrown = Color.decode("#573824");
    private Color textGray = Color.decode("#8E8E8E");
    private Color bgSidebar = Color.decode("#f4f4da");
    
    private JButton btnComplete;
    private JButton btnSearch;
    private JTextField txtSearch;
	private JPanel pnlProductGrid;
	private JPanel pnlCartItems; // Panel chứa danh sách món trong giỏ
	private JLabel lblTotalPrice; // Nhãn hiển thị tổng tiền
	private JTextArea txaInvoiceNote;
	
	private double totalAmount = 0.0;
	private ArrayList<ChiTietHoaDon> dsChiTiet = new ArrayList<>();

	private Mon_DAO mon_dao = new Mon_DAO();
	private HoaDon_DAO hoaDon_dao = new HoaDon_DAO();
	
	
	
    public CreateOrders_UI() {
        setTitle("Espresso Menu POS - Pure Java Optimized");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(Frame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);



        JPanel pnlMain = new JPanel(new BorderLayout());
        pnlMain.setOpaque(false);

        getContentPane().setBackground(bgMain);
        setLayout(new BorderLayout());
        add(createSidebar(), BorderLayout.WEST);
        add(pnlMain, BorderLayout.CENTER);

        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setOpaque(false);
        pnlHeader.setBorder(new EmptyBorder(15, 25, 10, 25));
        JPanel pnlSearchBox = new JPanel(new BorderLayout(5, 0));
        pnlSearchBox.setOpaque(false);

        txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(320, 40));
        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search menu items...");
        txtSearch.putClientProperty("JComponent.outlineWidth", 0);
        txtSearch.putClientProperty("JSeparator.height", 0);
        txtSearch.addActionListener(e-> searchMenu(txtSearch.getText().trim()));

        btnSearch = new JButton("Search");
        btnSearch.setBackground(accentBrown);
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFont(new Font("Inter", Font.BOLD, 13));
        btnSearch.setFocusPainted(false);
        btnSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSearch.setPreferredSize(new Dimension(80, 40));

        pnlSearchBox.add(txtSearch, BorderLayout.CENTER);
        pnlSearchBox.add(btnSearch, BorderLayout.EAST);

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

        pnlHeader.add(pnlSearchBox, BorderLayout.WEST);
        pnlHeader.add(pnlProfile, BorderLayout.EAST);
        pnlMain.add(pnlHeader, BorderLayout.NORTH);

        JPanel pnlBody = new JPanel(new BorderLayout());
        pnlBody.setOpaque(false);
        pnlMain.add(pnlBody, BorderLayout.CENTER);

        JPanel pnlLeft = new JPanel(new BorderLayout());
        pnlLeft.setOpaque(false);
        pnlLeft.setBorder(new EmptyBorder(10, 25, 20, 10));
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

        pnlProductGrid = new JPanel(new GridLayout(0, 3, 15, 15));
        pnlProductGrid.setOpaque(false);
        pnlProductGrid.setBorder(new EmptyBorder(15, 0, 0, 0));

        

        JScrollPane scrPane = new JScrollPane(pnlProductGrid);
        scrPane.setBorder(null);
        scrPane.setOpaque(false);
        scrPane.getViewport().setOpaque(false);
        scrPane.getVerticalScrollBar().setUnitIncrement(15);

        pnlLeft.add(scrPane, BorderLayout.CENTER);
        pnlBody.add(pnlLeft, BorderLayout.CENTER);

        JPanel pnlCart = new JPanel(new BorderLayout());
        pnlCart.setPreferredSize(new Dimension(340, 0));
        pnlCart.setBackground(bgSidebar);
        pnlCart.setBorder(new EmptyBorder(25, 20, 25, 20));

        JLabel lblCart = new JLabel("Current Cart");
        lblCart.setForeground(accentBrown);
        lblCart.setFont(new Font("Inter", Font.BOLD, 20));
        pnlCart.add(lblCart, BorderLayout.NORTH);
        
        pnlCartItems = new JPanel();
        pnlCartItems.setLayout(new BoxLayout(pnlCartItems, BoxLayout.Y_AXIS));
        pnlCartItems.setOpaque(false);

        JScrollPane scrCart = new JScrollPane(pnlCartItems);
        scrCart.setBorder(null);
        scrCart.setOpaque(false);
        scrCart.getViewport().setOpaque(false);
        pnlCart.add(scrCart, BorderLayout.CENTER); 

        JPanel pnlCartFooter = new JPanel();
        pnlCartFooter.setLayout(new BoxLayout(pnlCartFooter, BoxLayout.Y_AXIS));
        pnlCartFooter.setOpaque(false);
        
        JLabel lblNoteInvoce = new JLabel("Order Note:");
        lblNoteInvoce.setForeground(textGray);
        lblNoteInvoce.setFont(new Font("Inter", Font.BOLD, 12));
        lblNoteInvoce.setAlignmentX(Component.CENTER_ALIGNMENT);

        txaInvoiceNote = new JTextArea(3, 20); // 3 dòng
        txaInvoiceNote.setLineWrap(true);
        txaInvoiceNote.setWrapStyleWord(true);
        txaInvoiceNote.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "E.g. Table 5, less ice...");
        txaInvoiceNote.setFont(new Font("Inter", Font.PLAIN, 13));

        JScrollPane scrNote = new JScrollPane(txaInvoiceNote);
        scrNote.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80)); // Giới hạn chiều cao
        scrNote.setBorder(BorderFactory.createLineBorder(Color.decode("#E0E0E0")));

        pnlCartFooter.add(lblNoteInvoce);
        pnlCartFooter.add(Box.createVerticalStrut(5));
        pnlCartFooter.add(scrNote);
        pnlCartFooter.add(Box.createVerticalStrut(15));

        JLabel lblTotalLabel = new JLabel("Total Amount");
        lblTotalLabel.setForeground(textGray);
        lblTotalLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblTotalPrice = new JLabel("$0.00");
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
        
        renderMenu();

        btnComplete.addActionListener(this);
        btnSearch.addActionListener(this);
        
    }

    private JPanel createProductCard(String maMon, String name, String desc, String price, Color bg, Color brown, Color gray, String fileImage) {
        JPanel pnlCard = new JPanel(new BorderLayout());
        pnlCard.setBackground(bg);
        
        pnlCard.setName(maMon);

        JLabel lblImgPlaceholder = new JLabel("", SwingConstants.CENTER); 
        lblImgPlaceholder.setPreferredSize(new Dimension(0, 130));
        lblImgPlaceholder.setOpaque(true);
        lblImgPlaceholder.setBackground(Color.decode("#EEEEEE"));
        lblImgPlaceholder.putClientProperty("JComponent.outlineWidth", 1);
        try {
            java.net.URL url = new java.net.URL(fileImage);
            
            Image img = javax.imageio.ImageIO.read(url);
            
            if (img != null) {
                // 3. Co giãn ảnh: Rộng 200, Cao tự động (-1) để giữ tỉ lệ, dùng SCALE_SMOOTH để nét
                Image scaledImg = img.getScaledInstance(350, -1, Image.SCALE_SMOOTH);
                
                // 4. Gán ảnh vào Label
                lblImgPlaceholder.setIcon(new ImageIcon(scaledImg));
                lblImgPlaceholder.setText(""); // Xóa text nếu trước đó có chữ "No Image"
            } else {
                // Trường hợp URL hợp lệ nhưng không đọc được dữ liệu ảnh
                lblImgPlaceholder.setText("No Image");
                lblImgPlaceholder.setIcon(null);
                lblImgPlaceholder.setForeground(Color.LIGHT_GRAY);
            }
        } catch (Exception e) {
            lblImgPlaceholder.setText("Error Load");
            lblImgPlaceholder.setIcon(null);
            e.printStackTrace();
        }

        JPanel pnlDetails = new JPanel();
        pnlDetails.setLayout(new BoxLayout(pnlDetails, BoxLayout.Y_AXIS));
        pnlDetails.setOpaque(false);
        pnlDetails.setBorder(new EmptyBorder(10, 12, 12, 12));

        JLabel lblName = new JLabel(name);
        lblName.setFont(new Font("Inter", Font.BOLD, 15));
        lblName.setForeground(Color.BLACK);
        lblName.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextArea txaDesc = new JTextArea(desc);
        txaDesc.setLineWrap(true);
        txaDesc.setWrapStyleWord(true);
        txaDesc.setEditable(false);
        txaDesc.setFocusable(false);
        txaDesc.setOpaque(false);
        txaDesc.setFont(new Font("Inter", Font.PLAIN, 11));
        txaDesc.setForeground(gray);
        txaDesc.setAlignmentX(Component.LEFT_ALIGNMENT);
        txaDesc.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));

        pnlDetails.add(lblName);
        pnlDetails.add(Box.createVerticalStrut(4));
        pnlDetails.add(txaDesc);
        pnlDetails.add(Box.createVerticalGlue()); // Đẩy phần giá và nút xuống đáy

        // -- 3. Phần đáy của Info (Giá và Nút thêm) --
        JPanel pnlBottomInfo = new JPanel(new BorderLayout());
        pnlBottomInfo.setOpaque(false);
        pnlBottomInfo.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Nhãn giá
        JLabel lblPrice = new JLabel(price);
        lblPrice.setForeground(brown);
        lblPrice.setFont(new Font("Inter", Font.BOLD, 16));
        pnlBottomInfo.add(lblPrice, BorderLayout.WEST);

        JPanel pnlWrapperBtnAdd = new JPanel();
        pnlWrapperBtnAdd.setLayout(new GridBagLayout());
        pnlWrapperBtnAdd.setBackground(Color.WHITE);
        JButton btnAdd = new JButton("+");
        btnAdd.setFont(new Font("Inter", Font.BOLD, 14));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setBackground(brown);
        btnAdd.setFocusPainted(false);
        btnAdd.setBorderPainted(false);
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAdd.setPreferredSize(new Dimension(35, 35));
        btnAdd.addActionListener(e -> {
            try {
                double priceValue = Double.parseDouble(price.replace("$", "").replace(",","."));
                addToCart(name, priceValue,  maMon); 
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }); 
        btnAdd.putClientProperty("JButton.buttonType", "roundRect");

        pnlWrapperBtnAdd.add(btnAdd);
        
        pnlBottomInfo.add(pnlWrapperBtnAdd, BorderLayout.EAST); 

        pnlDetails.add(pnlBottomInfo);

        // Lắp ráp card
        pnlCard.add(lblImgPlaceholder, BorderLayout.NORTH);
        pnlCard.add(pnlDetails, BorderLayout.CENTER	);

        return pnlCard;

    }

    private void addToCart(String name, double price, String maMon) {
    	System.out.println("Hàm này chạy");
    	for (Component comp : pnlCartItems.getComponents()) {
            if (comp instanceof JPanel && maMon.equals(comp.getName())) {
                JPanel existingItem = (JPanel) comp;
                updateQuantityInExistingItem(existingItem, price);
                return; 
            }
        }
    	JPanel pnlItem = new JPanel();
    	pnlItem.setLayout(new BoxLayout(pnlItem, BoxLayout.X_AXIS)); // ngang thay vì BorderLayout
    	pnlItem.setOpaque(false);
    	pnlItem.setBorder(new EmptyBorder(10, 0, 15, 0));
    	pnlItem.setName(maMon); // ĐẶT TÊN ĐỂ KIỂM TRA TRÙNG LẶP

        JLabel lblIcon = new JLabel();
        lblIcon.setPreferredSize(new Dimension(60, 60));
        lblIcon.setMinimumSize(new Dimension(60, 60));
        lblIcon.setMaximumSize(new Dimension(60, 60));
        lblIcon.setAlignmentY(Component.TOP_ALIGNMENT); // QUAN TRỌNG
        lblIcon.setBackground(Color.decode("#e9e9d4")); // Màu nền kem nhạt
        lblIcon.setOpaque(true);
        lblIcon.setHorizontalAlignment(SwingConstants.CENTER);
        lblIcon.setText("☕"); 
        lblIcon.putClientProperty("JComponent.outlineWidth", 1); // Bo góc nhẹ nếu dùng FlatLaf

        // --- PHẦN GIỮA: Tên, Ghi chú và Bộ điều chỉnh số lượng ---
        JPanel pnlCenter = new JPanel();
        pnlCenter.setLayout(new BoxLayout(pnlCenter, BoxLayout.Y_AXIS));
        pnlCenter.setOpaque(false);
        pnlCenter.setAlignmentY(Component.TOP_ALIGNMENT);

        JLabel lblItemName = new JLabel(name);
        lblItemName.setFont(new Font("Inter", Font.BOLD, 15));
        lblItemName.setForeground(Color.BLACK);

        JLabel lblNote = new JLabel("+ Standard Serving"); // Ghi chú mặc định
        lblNote.setFont(new Font("Inter", Font.ITALIC, 11));
        lblNote.setForeground(Color.GRAY);

        // Bộ điều chỉnh số lượng: [- 1 +] Remove
        JPanel pnlQtyAction = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        pnlQtyAction.setOpaque(false);

        // Panel nhỏ chứa nút [- 1 +]
        JPanel pnlQtyStepper = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 2));
        pnlQtyStepper.setBackground(Color.decode("#eef3f3")); // Màu nền xanh nhạt
        pnlQtyStepper.putClientProperty("JComponent.outlineWidth", 0);
        
        JButton btnMinus = new JButton("–");
        JLabel lblQty = new JLabel("1");
        pnlItem.putClientProperty("qtyLabel", lblQty);
        JButton btnPlus = new JButton("+");
        
        // Định dạng nút bấm nhỏ
        for (JButton btn : new JButton[]{btnMinus, btnPlus}) {
            btn.setPreferredSize(new Dimension(24, 24));
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setBackground(Color.decode("#b8e2f2")); // Màu xanh nút bấm
            btn.setFont(new Font("Arial", Font.BOLD, 12));
        }

        pnlQtyStepper.add(btnMinus);
        pnlQtyStepper.add(lblQty);
        pnlQtyStepper.add(btnPlus);

        // Nút Remove màu đỏ
        JButton btnRemove = new JButton("Remove");
        btnRemove.setFont(new Font("Inter", Font.PLAIN, 12));
        btnRemove.setForeground(Color.decode("#d9534f"));
        btnRemove.setBorderPainted(false);
        btnRemove.setContentAreaFilled(false);
        btnRemove.setCursor(new Cursor(Cursor.HAND_CURSOR));

        pnlQtyAction.add(pnlQtyStepper);
        pnlQtyAction.add(btnRemove);

        pnlCenter.add(lblItemName);
        pnlCenter.add(lblNote);
        pnlCenter.add(Box.createVerticalStrut(8));
        pnlCenter.add(pnlQtyAction);

        // --- PHẦN BÊN PHẢI: Giá tiền ---
        JLabel lblItemPrice = new JLabel(String.format("$%.2f", price));
        lblItemPrice.setFont(new Font("Inter", Font.BOLD, 16));
        lblItemPrice.setForeground(Color.decode("#573824"));
        lblItemPrice.setAlignmentY(Component.TOP_ALIGNMENT);

        // --- LOGIC XỬ LÝ SỰ KIỆN ---
        btnPlus.addActionListener(e -> {
            int q = Integer.parseInt(lblQty.getText()) + 1;
            lblQty.setText(String.valueOf(q));
            updateTotal(price); // Hàm cập nhật tổng tiền (viết bên dưới)
        });

        btnMinus.addActionListener(e -> {
            int q = Integer.parseInt(lblQty.getText());
            if (q > 1) {
                lblQty.setText(String.valueOf(q - 1));
                updateTotal(-price);
            }
        });

        btnRemove.addActionListener(e -> {
            int q = Integer.parseInt(lblQty.getText());
            pnlCartItems.remove(pnlItem);
            updateTotal(-(price * q));
            pnlCartItems.revalidate();
            pnlCartItems.repaint();
        });

        // Ráp các phần vào dòng item
        pnlItem.add(lblIcon);
        pnlItem.add(Box.createHorizontalStrut(15));
        pnlItem.add(pnlCenter);
        pnlItem.add(Box.createHorizontalGlue()); // đẩy giá sang phải
        pnlItem.add(lblItemPrice);

        // Thêm vào giỏ hàng và cập nhật tổng
        pnlCartItems.add(pnlItem);
        updateTotal(price);
        
        pnlCartItems.revalidate();
        pnlCartItems.repaint();
    }
    
    private void updateTotal(double deltaPrice) {
        this.totalAmount += deltaPrice;
        if (this.totalAmount < 0) this.totalAmount = 0; // Tránh sai số âm
        if (lblTotalPrice != null) {
            lblTotalPrice.setText(String.format("$%.2f", this.totalAmount));
        }
    }
    private void updateQuantityInExistingItem(JPanel itemPanel, double unitPrice) {
        // Duyệt cây component để tìm lblQty (là JLabel có nội dung là con số)
        // Cấu trúc: pnlItem -> pnlCenter -> pnlQtyAction -> pnlQtyStepper -> lblQty
        try {
            JPanel pnlCenter = (JPanel) itemPanel.getComponent(2); // Component thứ 2 sau Icon và Strut
            JPanel pnlQtyAction = (JPanel) pnlCenter.getComponent(3); // Sau Name, Note và Strut
            JPanel pnlQtyStepper = (JPanel) pnlQtyAction.getComponent(0);
            JLabel lblQty = (JLabel) pnlQtyStepper.getComponent(1); // Nút Minus là 0, Qty là 1

            int newQty = Integer.parseInt(lblQty.getText()) + 1;
            lblQty.setText(String.valueOf(newQty));
            updateTotal(unitPrice);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    Tạo thanh điều khiển
    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(250, 0));
        sidebar.setBackground(new Color(245, 245, 230));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new EmptyBorder(30, 20, 30, 20));

        JLabel lblLogo = new JLabel("ESPRESSO LOGIC");
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblLogo.setForeground(new Color(85, 55, 34));
        lblLogo.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(lblLogo);
        sidebar.add(Box.createRigidArea(new Dimension(0, 40)));

        String[][] menuData = {
            {"Menu", "img/menu.png"},
            {"Menu Management", "img/menumanagement.png"},
            {"Analytics", "img/analytics.png"}
        };

        for (String[] data : menuData) {
            JButton btn = createMenuButton(data[0], data[1]);

            if (data[0].equals("Menu")) {
                btn.setBackground(new Color(230, 230, 210));
                btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
                btn.putClientProperty(FlatClientProperties.BUTTON_TYPE, 10);
            } else {
                btn.setContentAreaFilled(false);
            }
            if (data[0].equals("Menu Management")) {
                btn.addActionListener(e -> {
                    MenuManagement nextFrame = new MenuManagement();
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
            sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        sidebar.add(Box.createVerticalGlue());

        JButton btnNewOrder = new JButton(" + New Order ");
        btnNewOrder.setBackground(new Color(85, 55, 34));
        btnNewOrder.setForeground(Color.WHITE);
        btnNewOrder.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnNewOrder.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        btnNewOrder.setFocusPainted(false);
        btnNewOrder.setBorderPainted(false);
        btnNewOrder.setFont(new Font("Segoe UI", Font.BOLD, 14));
        sidebar.add(btnNewOrder);
        return sidebar;
    }

    private JButton createMenuButton(String text, String iconPath) {
        ImageIcon icon = new ImageIcon(iconPath);
        Image scaled = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);

        JButton btn = new JButton(text, new ImageIcon(scaled));

        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setIconTextGap(15);
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

        btn.setFocusPainted(false);	
        btn.setBorder(new EmptyBorder(10, 15, 10, 15));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setForeground(new Color(85, 55, 34));
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnComplete)) {

            // 1. Check giỏ hàng rỗng
            if (pnlCartItems.getComponentCount() == 0) {
                JOptionPane.showMessageDialog(this, "Giỏ hàng đang trống!");
                return;
            }

            // 2. Tạo hóa đơn
            HoaDon hd = new HoaDon();
            String maHD = "HD" + System.currentTimeMillis();
            hd.setMaHD(maHD);
            hd.setNgayGioLap(LocalDate.now());
            hd.setTrangThaiTT(true);
            
         // --- LẤY GHI CHÚ TỪ TEXT AREA ---
            String ghiChuHoaDon = txaInvoiceNote.getText().trim();

            ArrayList<ChiTietHoaDon> dsChiTiet = new ArrayList<>();

            // 3. Duyệt từng item trong cart
            for (Component comp : pnlCartItems.getComponents()) {
                if (!(comp instanceof JPanel)) continue;

                JPanel pnlItem = (JPanel) comp;

                String maMon = pnlItem.getName();
                if (maMon == null) continue;

                try {
                    JLabel lblQty = (JLabel) pnlItem.getClientProperty("qtyLabel");
                    if (lblQty == null) continue;

                    int soLuong = Integer.parseInt(lblQty.getText());

                    //LẤY MON
                    Mon mon = mon_dao.getAll().stream()
                            .filter(m -> m.getMaMon().equals(maMon))
                            .findFirst().orElse(null);

                    if (mon == null) {
                        System.out.println("Không tìm thấy món: " + maMon);
                        continue;
                    }

                    // TẠO CHI TIẾT
                    ChiTietHoaDon ct = new ChiTietHoaDon();
                    ct.setMaCTHD(maHD + "-" + System.nanoTime());
                    ct.setMon(mon);
                    ct.setSoLuongMon(soLuong);
                    ct.setGhiChuKhachHang(ghiChuHoaDon);
                    ct.setThanhTien(); // tính tiền

                    dsChiTiet.add(ct);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            // 4. Check nếu không có chi tiết
            if (dsChiTiet.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Lỗi: Không có sản phẩm hợp lệ!");
                return;
            }

            // 5. Gán vào hóa đơn
            hd.getDsChiTiet().clear();
            hd.getDsChiTiet().addAll(dsChiTiet);

            // 6. Tính tổng tiền
            hd.setTongTien();

            // DEBUG
            System.out.println("Số CT: " + dsChiTiet.size());
            System.out.println("Total: " + hd.getTongTien());

            // 7. Lưu file
            if (hoaDon_dao.addHoaDon(hd)) {

                JOptionPane.showMessageDialog(this,
                        "Thanh toán thành công!\nTổng tiền: $" + hd.getTongTien());

                // Reset UI
                pnlCartItems.removeAll();
                totalAmount = 0;
                lblTotalPrice.setText("$0.00");

                pnlCartItems.revalidate();
                pnlCartItems.repaint();

                // Chuyển trang
                Invoice_UI next = new Invoice_UI(hd);
                next.setVisible(true);
                this.dispose();

            } else {
                JOptionPane.showMessageDialog(this, "Lỗi lưu hóa đơn!");
            }
        } else if (o.equals(btnSearch)) {
            String keyword = txtSearch.getText().trim();
            searchMenu(keyword);
        }
    }
    
    public void renderMenu() {
        pnlProductGrid.removeAll();
        List<Mon> listMon = mon_dao.getAll();

        for (Mon m : listMon) {
            JPanel card = createProductCard(
                m.getMaMon(), 
                m.getTenMon(), 
                m.getMoTaMon(),
                String.format("$%.2f", m.getDonGiaBan()), 
                bgCard, 
                accentBrown, 
                textGray,
                m.getDuongDanAnh()
            );
            pnlProductGrid.add(card);
        }
        
        pnlProductGrid.revalidate();
        pnlProductGrid.repaint();
    }
    private void searchMenu(String keyword) {
        pnlProductGrid.removeAll();
        List<Mon> allMon = mon_dao.getAll();

        if (keyword.isEmpty()) {
            renderMenu();
            return;
        }

        for (Mon m : allMon) {
            boolean matchMa = m.getMaMon().equalsIgnoreCase(keyword);
            boolean matchTen = m.getTenMon().toLowerCase().contains(keyword.toLowerCase());

            if (matchMa || matchTen) {
                JPanel card = createProductCard(
                    m.getMaMon(), 
                    m.getTenMon(), 
                    m.getMoTaMon(),
                    String.format("$%.2f", m.getDonGiaBan()), 
                    bgCard, 
                    accentBrown, 
                    textGray,
                    m.getDuongDanAnh()
                );
                pnlProductGrid.add(card);
            }
        }

        pnlProductGrid.revalidate();
        pnlProductGrid.repaint();
    }
}