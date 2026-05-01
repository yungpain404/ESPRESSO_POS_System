package app;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLightLaf;

import dao.HoaDon_DAO;
import dao.Mon_DAO;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.Mon;
import entity.PhanLoaiMonAn;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class CreateFormOrders_UI extends JFrame implements ActionListener {
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
    private JButton btnCancel;
    private JTextField txtSearch;
	private JPanel pnlProductGrid;
	private JPanel pnlCartItems;
	private JLabel lblTotalPrice;
	private JTextArea txaInvoiceNote;
	
	private double totalAmount = 0.0;
	private ArrayList<ChiTietHoaDon> dsChiTiet = new ArrayList<>();

    private java.util.List<entity.Mon> originalList;
    private JButton activeTab; 


	private Mon_DAO mon_dao = new Mon_DAO();
	private HoaDon_DAO hoaDon_dao = new HoaDon_DAO();
	
	
	
    public CreateFormOrders_UI() {
        setTitle("Espresso Menu POS - Pure Java Optimized");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
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
        
        JButton btnAll = createTabButton("All", true);
        activeTab = btnAll;
        btnAll.addActionListener(e -> filterMenu(null, btnAll));
        pnlTabs.add(btnAll);
       
        for (PhanLoaiMonAn loai : PhanLoaiMonAn.values()) {
            String tabName = loai.name().substring(0, 1).toUpperCase() + 
                             loai.name().substring(1).toLowerCase();
            
            JButton btnTab = createTabButton(tabName, false);
            
            btnTab.addActionListener(e -> filterMenu(loai, btnTab));
            
            pnlTabs.add(btnTab);
        }
        pnlMenuHeader.add(lblTitle, BorderLayout.WEST);
        pnlMenuHeader.add(pnlTabs, BorderLayout.SOUTH);
        pnlLeft.add(pnlMenuHeader, BorderLayout.NORTH);

        pnlProductGrid = new JPanel(new GridLayout(0, 3, 15, 15));
        pnlProductGrid.setOpaque(false);
        pnlProductGrid.setBorder(new EmptyBorder(15, 0, 0, 0));

        
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        wrapper.setOpaque(false);
        wrapper.add(pnlProductGrid);

        JScrollPane scrPane = new JScrollPane(wrapper);
        scrPane.setBorder(null);
        scrPane.setOpaque(false);
        scrPane.getViewport().setOpaque(false);
        scrPane.getVerticalScrollBar().setUnitIncrement(15);

        pnlLeft.add(scrPane, BorderLayout.CENTER);
        pnlBody.add(pnlLeft, BorderLayout.CENTER);

        JPanel pnlCart = new JPanel(new BorderLayout());
        pnlCart.setPreferredSize(new Dimension(330, 0));
        pnlCart.setBackground(bgSidebar);
        pnlCart.setBorder(new EmptyBorder(25, 20, 25, 20));

        JLabel lblCart = new JLabel("Current Cart");
        lblCart.setForeground(accentBrown);
        lblCart.setFont(new Font("Inter", Font.BOLD, 20));
        pnlCart.add(lblCart, BorderLayout.NORTH);
        
        pnlCartItems = new JPanel();
        pnlCartItems.setLayout(new BoxLayout(pnlCartItems, BoxLayout.Y_AXIS));
        pnlCartItems.setOpaque(false);

        JPanel pnlWrapper = new JPanel(new BorderLayout());
        pnlWrapper.setOpaque(false);
        pnlWrapper.add(pnlCartItems, BorderLayout.NORTH); 
        
        JScrollPane scrCart = new JScrollPane(pnlWrapper);
        scrCart.setBorder(null);
        scrCart.setOpaque(false);
        scrCart.getViewport().setOpaque(false);
        pnlCart.add(scrCart, BorderLayout.CENTER); 


        JPanel pnlCartFooter = new JPanel();
        pnlCartFooter.setLayout(new BoxLayout(pnlCartFooter, BoxLayout.Y_AXIS));
        pnlCartFooter.setOpaque(false);
        
     // Thêm ghi chú
        JLabel lblNoteInvoce = new JLabel("Order Note:");
        lblNoteInvoce.setForeground(textGray);
        lblNoteInvoce.setFont(new Font("Inter", Font.BOLD, 12));
        lblNoteInvoce.setAlignmentX(Component.CENTER_ALIGNMENT);

        txaInvoiceNote = new JTextArea(3, 20);
        txaInvoiceNote.setLineWrap(true);
        txaInvoiceNote.setWrapStyleWord(true);
        txaInvoiceNote.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "E.g. Table 5, less ice...");
        txaInvoiceNote.setFont(new Font("Inter", Font.PLAIN, 13));

        JScrollPane scrNote = new JScrollPane(txaInvoiceNote);
        scrNote.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80)); 
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
        
       
        JPanel pnlActionButtons = new JPanel(new GridLayout(1, 2, 10, 0)); 
        pnlActionButtons.setOpaque(false);
        pnlActionButtons.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        
     // Nút Hủy (Cancel)
        btnCancel = new JButton("Cancel");
        btnCancel.setFont(new Font("Inter", Font.BOLD, 14));
        btnCancel.setForeground(accentBrown);
        btnCancel.setBackground(Color.WHITE); 
        btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnComplete = new JButton("Complete Order →");
        btnComplete.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
        btnComplete.setBackground(accentBrown);
        btnComplete.setForeground(Color.WHITE);
        btnComplete.setFont(new Font("Inter", Font.BOLD, 15));
        btnComplete.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnComplete.putClientProperty("JComponent.outlineWidth", 0);
        
        pnlActionButtons.add(btnCancel);
        pnlActionButtons.add(btnComplete);

        pnlCartFooter.add(lblTotalLabel);
        pnlCartFooter.add(Box.createVerticalStrut(5));
        pnlCartFooter.add(lblTotalPrice);
        pnlCartFooter.add(Box.createVerticalStrut(20));
        pnlCartFooter.add(pnlActionButtons);

        pnlCart.add(pnlCartFooter, BorderLayout.SOUTH);
        pnlBody.add(pnlCart, BorderLayout.EAST);
        
        renderMenu1();

        btnComplete.addActionListener(this);
        btnSearch.addActionListener(this);
        btnCancel.addActionListener(this);
    }

    private JPanel createProductCard(String maMon, String name, String desc, String price, Color bg, Color brown, Color gray, String fileImage) {
        JPanel pnlCard = new JPanel(new BorderLayout());
        pnlCard.setPreferredSize(new Dimension(205, 245));
        pnlCard.setMaximumSize(new Dimension(205, 245));
        pnlCard.setBackground(bg);
        
        pnlCard.setName(maMon);
//        Phần icon
        JLabel lblImgPlaceholder = new JLabel("", SwingConstants.CENTER);
        lblImgPlaceholder.setPreferredSize(new Dimension(0, 130));
        lblImgPlaceholder.setOpaque(true);
        lblImgPlaceholder.setBackground(Color.decode("#EEEEEE"));
        lblImgPlaceholder.putClientProperty("JComponent.outlineWidth", 1);
        try {
            ImageIcon icon = new ImageIcon(fileImage);
            
            if (icon.getIconWidth() > 0) {
                Image img = icon.getImage();
                Image scaledImg = img.getScaledInstance(200, -1, Image.SCALE_SMOOTH);
                lblImgPlaceholder.setIcon(new ImageIcon(scaledImg));
            } else {
                lblImgPlaceholder.setText("No Image");
                lblImgPlaceholder.setForeground(Color.LIGHT_GRAY);
            }
        } catch (Exception e) {
            lblImgPlaceholder.setText("Error");
            e.printStackTrace();
        }

        // phần thông tin (Tên, Mô tả, Giá, Nút thêm)
        JPanel pnlDetails = new JPanel();
        pnlDetails.setLayout(new BoxLayout(pnlDetails, BoxLayout.Y_AXIS));
        pnlDetails.setOpaque(false);
        pnlDetails.setBorder(new EmptyBorder(10, 12, 12, 12));

        // Tên món
        JLabel lblName = new JLabel(name);
        lblName.setFont(new Font("Inter", Font.BOLD, 15));
        lblName.setForeground(Color.BLACK);
        lblName.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Mô tả món
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
        pnlDetails.add(Box.createVerticalGlue());

        //Phần đáy của Info (Giá và Nút thêm)
        JPanel pnlBottomInfo = new JPanel(new BorderLayout());
        pnlBottomInfo.setOpaque(false);
        pnlBottomInfo.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Nhãn giá
        JLabel lblPrice = new JLabel(price);
        lblPrice.setForeground(brown);
        lblPrice.setFont(new Font("Inter", Font.BOLD, 16));
        pnlBottomInfo.add(lblPrice, BorderLayout.WEST);

        // Nút dấu cộng (+)
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
                double priceValue = Double.parseDouble(price.replace("$", ""));
                addToCart(name, priceValue,  maMon); 
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }); 
        btnAdd.putClientProperty("JButton.buttonType", "roundRect");

        btnAdd.addActionListener(e -> {
            System.out.println("Thêm món: " + name + " (Mã: " + maMon + ") vào giỏ hàng");
           
        });

        pnlBottomInfo.add(btnAdd, BorderLayout.EAST); 

        pnlDetails.add(pnlBottomInfo);

        pnlCard.add(lblImgPlaceholder, BorderLayout.NORTH);
        pnlCard.add(pnlDetails, BorderLayout.CENTER);

        return pnlCard;
    }

    private void addToCart(String name, double price, String maMon) {
    	for (Component comp : pnlCartItems.getComponents()) {
            if (comp instanceof JPanel && maMon.equals(comp.getName())) {
                JPanel existingItem = (JPanel) comp;
                updateQuantityInExistingItem(existingItem, price);
                return;
            }
        }
        //Panel chính cho mỗi dòng
    	JPanel pnlItem = new JPanel();
    	pnlItem.setLayout(new BoxLayout(pnlItem, BoxLayout.X_AXIS)); 
    	pnlItem.setOpaque(false);
    	pnlItem.setBorder(new EmptyBorder(10, 0, 15, 0));
    	pnlItem.setName(maMon);

        JLabel lblIcon = new JLabel();
        lblIcon.setPreferredSize(new Dimension(60, 60));
        lblIcon.setMinimumSize(new Dimension(60, 60));
        lblIcon.setMaximumSize(new Dimension(60, 60));
        lblIcon.setAlignmentY(Component.TOP_ALIGNMENT);
        lblIcon.setBackground(Color.decode("#e9e9d4")); 
        lblIcon.setOpaque(true);
        lblIcon.setHorizontalAlignment(SwingConstants.CENTER);
        lblIcon.setText("☕"); 
        lblIcon.putClientProperty("JComponent.outlineWidth", 1);

        //Tên, Ghi chú và Bộ điều chỉnh số lượng
        JPanel pnlCenter = new JPanel();
        pnlCenter.setLayout(new BoxLayout(pnlCenter, BoxLayout.Y_AXIS));
        pnlCenter.setOpaque(false);
        pnlCenter.setAlignmentY(Component.TOP_ALIGNMENT);

        JLabel lblItemName = new JLabel(name);
        lblItemName.setFont(new Font("Inter", Font.BOLD, 15));
        lblItemName.setForeground(Color.BLACK);

        JLabel lblNote = new JLabel("+ Standard Serving");
        lblNote.setFont(new Font("Inter", Font.ITALIC, 11));
        lblNote.setForeground(Color.GRAY);

        // Điều chỉnh số lượng: [- 1 +] Remove
        JPanel pnlQtyAction = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        pnlQtyAction.setOpaque(false);

        // Panel nhỏ chứa nút [- 1 +]
        JPanel pnlQtyStepper = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 2));
        pnlQtyStepper.setBackground(Color.decode("#eef3f3"));
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
            btn.setBackground(Color.decode("#b8e2f2"));
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

        // Giá tiền
        JLabel lblItemPrice = new JLabel(String.format("$%.2f", price));
        lblItemPrice.setFont(new Font("Inter", Font.BOLD, 16));
        lblItemPrice.setForeground(Color.decode("#573824"));
        lblItemPrice.setAlignmentY(Component.TOP_ALIGNMENT);

        btnPlus.addActionListener(e -> {
            int q = Integer.parseInt(lblQty.getText()) + 1;
            lblQty.setText(String.valueOf(q));
            updateTotal(price); 
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

        pnlItem.add(lblIcon);
        pnlItem.add(Box.createHorizontalStrut(15));
        pnlItem.add(pnlCenter);
        pnlItem.add(Box.createHorizontalGlue()); 
        pnlItem.add(lblItemPrice);

        pnlCartItems.add(pnlItem);
        updateTotal(price);
        
        pnlCartItems.revalidate();
        pnlCartItems.repaint();
    }
    
    private void updateTotal(double deltaPrice) {
        this.totalAmount += deltaPrice;
        if (this.totalAmount < 0) this.totalAmount = 0; 
        if (lblTotalPrice != null) {
            lblTotalPrice.setText(String.format("$%.2f", this.totalAmount));
        }
    }
    private void updateQuantityInExistingItem(JPanel itemPanel, double unitPrice) {
        try {
            JPanel pnlCenter = (JPanel) itemPanel.getComponent(2); 
            JPanel pnlQtyAction = (JPanel) pnlCenter.getComponent(3); 
            JPanel pnlQtyStepper = (JPanel) pnlQtyAction.getComponent(0);
            JLabel lblQty = (JLabel) pnlQtyStepper.getComponent(1);

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
            }else {
            	btn.setContentAreaFilled(false);
            }
            
            btn.addActionListener(e -> {
                if (pnlCartItems.getComponentCount() > 0) {
                    JOptionPane.showMessageDialog(this, 
                        "Bạn đang có đơn hàng chưa hoàn tất! Vui lòng thanh toán hoặc hủy đơn trước khi rời đi.", 
                        "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (data[0].equals("Menu Management")) {
                        MenuManagement nextFrame = new MenuManagement();
                        nextFrame.setBounds(this.getBounds());
                        nextFrame.setExtendedState(this.getExtendedState());
                        nextFrame.setVisible(true);
                        this.dispose();
                } else if (data[0].equals("Analytics")) {
                        Dashboard_UI nextFrame = new Dashboard_UI();
                        nextFrame.setBounds(this.getBounds());
                        nextFrame.setExtendedState(this.getExtendedState());
                        nextFrame.setVisible(true);
                        this.dispose();
                }else if (data[0].equals("Menu")){
                		MenuList_UI nextFrame = new MenuList_UI();
	                    nextFrame.setBounds(this.getBounds());
	                    nextFrame.setExtendedState(this.getExtendedState());
	                    nextFrame.setVisible(true);
	                    this.dispose();
                }
            });

            
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
        btnNewOrder.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, 
                "Bạn đang ở trong trang tạo đơn hàng mới rồi!", 
                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        });
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

            //Check giỏ hàng rỗng
            if (pnlCartItems.getComponentCount() == 0) {
                JOptionPane.showMessageDialog(this, "Giỏ hàng đang trống!");
                return;
            }

            //Tạo hóa đơn
            HoaDon hd = new HoaDon();
            String maHD = "HD" + System.currentTimeMillis();
            hd.setMaHD(maHD);
            hd.setNgayGioLap(LocalDate.now());
            hd.setTrangThaiTT(true);
            
            String ghiChuHoaDon = txaInvoiceNote.getText().trim();

            ArrayList<ChiTietHoaDon> dsChiTiet = new ArrayList<>();

            for (Component comp : pnlCartItems.getComponents()) {
                if (!(comp instanceof JPanel)) continue;

                JPanel pnlItem = (JPanel) comp;

                String maMon = pnlItem.getName();
                if (maMon == null) continue;

                try {
                    JLabel lblQty = (JLabel) pnlItem.getClientProperty("qtyLabel");
                    if (lblQty == null) continue;

                    int soLuong = Integer.parseInt(lblQty.getText());

                    Mon mon = mon_dao.getAll().stream()
                            .filter(m -> m.getMaMon().equals(maMon))
                            .findFirst().orElse(null);

                    if (mon == null) {
                        System.out.println("Không tìm thấy món: " + maMon);
                        continue;
                    }

                    ChiTietHoaDon ct = new ChiTietHoaDon();
                    ct.setMaCTHD(maHD + "-" + System.nanoTime());
                    ct.setMon(mon);
                    ct.setSoLuongMon(soLuong);
                    ct.setGhiChuKhachHang(ghiChuHoaDon);
                    ct.setThanhTien(); 

                    dsChiTiet.add(ct);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            //Check nếu không có chi tiết
            if (dsChiTiet.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Lỗi: Không có sản phẩm hợp lệ!");
                return;
            }

            //Gán vào hóa đơn
            hd.getDsChiTiet().clear();
            hd.getDsChiTiet().addAll(dsChiTiet);

            //Tính tổng tiền
            hd.setTongTien();

            //Lưu file
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
            
        }else if(o.equals(btnCancel)) {
        	if (pnlCartItems.getComponentCount() > 0) {
                int opt = JOptionPane.showConfirmDialog(this, 
                    "Bạn có chắc muốn hủy toàn bộ giỏ hàng?", "Xác nhận hủy", 
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (opt == JOptionPane.YES_OPTION) {
                    pnlCartItems.removeAll();
                    totalAmount = 0;
                    lblTotalPrice.setText("$0.00");
                    txaInvoiceNote.setText("");
                    pnlCartItems.revalidate();
                    pnlCartItems.repaint();
                }
            }
        }
    }
    public void renderMenu() {
        pnlProductGrid.removeAll();
        java.util.List<entity.Mon> listMon = mon_dao.getAll();
        for (entity.Mon m : listMon) {
            JPanel card = createProductCard(
                m.getMaMon(), m.getTenMon(), m.getMoTaMon(),
                String.format("$%.2f", m.getDonGiaBan()), 
                bgCard, accentBrown, textGray, m.getDuongDanAnh()
            );
            pnlProductGrid.add(card);
        }
        pnlProductGrid.revalidate();
        pnlProductGrid.repaint();
    }
    
    public void renderMenu1() {
        pnlProductGrid.removeAll();
        
        originalList = mon_dao.getAll(); 

        if (originalList != null) {
            for (entity.Mon m : originalList) {
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
    
    private JButton createTabButton(String text, boolean isSelected) {
        JButton btn = new JButton(text);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        if (isSelected) {
            btn.setBackground(Color.decode("#e6e6cc")); 
            btn.setForeground(accentBrown);
            btn.setFont(new Font("Inter", Font.BOLD, 13));
        } else {
            btn.setBackground(Color.decode("#f5f5db")); 
            btn.setForeground(Color.decode("#7d7862"));
            btn.setFont(new Font("Inter", Font.PLAIN, 13));
        }
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (!btn.getBackground().equals(Color.decode("#e6e6cc"))) {
                    btn.setBackground(Color.decode("#eeeecc"));
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (!btn.getBackground().equals(Color.decode("#e6e6cc"))) {
                    btn.setBackground(Color.decode("#f5f5db"));
                }
            }
        });
        
        return btn;
    }

    private void filterMenu(entity.PhanLoaiMonAn loai, JButton clickedTab) {
        if (activeTab != null) {
            updateTabStyle(activeTab, false);
        }
        updateTabStyle(clickedTab, true);
        activeTab = clickedTab;

        if (loai == null) {
            displayFilteredList(originalList);
        } else {
            java.util.List<entity.Mon> filtered = originalList.stream()
                    .filter(m -> m.getPhanLoaiMonAn().equals(loai)) 
                    .toList();
            displayFilteredList(filtered);
        }
    }
    private void displayFilteredList(java.util.List<entity.Mon> list) {
        pnlProductGrid.removeAll();
        for (entity.Mon m : list) {
            JPanel card = createProductCard(
                m.getMaMon(), m.getTenMon(), m.getMoTaMon(),
                String.format("$%.2f", m.getDonGiaBan()), 
                bgCard, accentBrown, textGray, m.getDuongDanAnh()
            );
            pnlProductGrid.add(card);
        }
        pnlProductGrid.revalidate();
        pnlProductGrid.repaint();
    }
    
    private void updateTabStyle(JButton btn, boolean isSelected) {
        if (isSelected) {
            btn.setBackground(Color.decode("#e6e6cc"));
            btn.setForeground(accentBrown);
            btn.setFont(new Font("Inter", Font.BOLD, 13));
        } else {
            btn.setBackground(Color.decode("#f5f5db"));
            btn.setForeground(Color.decode("#7d7862"));
            btn.setFont(new Font("Inter", Font.PLAIN, 13));
        }
    }

    
}