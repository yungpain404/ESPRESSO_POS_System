package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
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
    private JPanel pnlCartItems;
    private JPanel pnlTabs;
    private JLabel lblTotalPrice;
    private JTextArea txaInvoiceNote;

    private double totalAmount = 0.0;
    private Mon_DAO mon_dao = new Mon_DAO();
    private HoaDon_DAO hoaDon_dao = new HoaDon_DAO();

    public CreateOrders_UI() {
        setTitle("Espresso Menu POS");
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
        txtSearch.addActionListener(e -> searchMenu(txtSearch.getText().trim()));

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

        pnlTabs = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        pnlTabs.setOpaque(false);
        String[] category = {"All", "Coffee", "Tea", "Pastries"};
        for (String cat : category) {
            JButton btnTab = new JButton(cat);
            btnTab.setBorderPainted(false);
            btnTab.setFocusPainted(false);
            btnTab.setCursor(new Cursor(Cursor.HAND_CURSOR));

            if (cat.equals("All")) {
                btnTab.setBackground(Color.decode("#e6e6cc"));
                btnTab.setForeground(Color.decode("#573824"));
            } else {
                btnTab.setBackground(Color.decode("#f5f5db"));
                btnTab.setForeground(Color.decode("#7d7862"));
            }
            btnTab.addActionListener(e -> filterMenu(cat, btnTab));
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

        btnComplete = new JButton("Complete Order →");
        btnComplete.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
        btnComplete.setBackground(accentBrown);
        btnComplete.setForeground(Color.WHITE);
        btnComplete.setFont(new Font("Inter", Font.BOLD, 15));
        btnComplete.setCursor(new Cursor(Cursor.HAND_CURSOR));

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
        try {
            java.net.URL url = new java.net.URL(fileImage);
            Image img = javax.imageio.ImageIO.read(url);
            if (img != null) {
                Image scaledImg = img.getScaledInstance(350, -1, Image.SCALE_SMOOTH);
                lblImgPlaceholder.setIcon(new ImageIcon(scaledImg));
            } else {
                lblImgPlaceholder.setText("No Image");
            }
        } catch (Exception e) {
            lblImgPlaceholder.setText("Error Load");
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
        txaDesc.setOpaque(false);
        txaDesc.setFont(new Font("Inter", Font.PLAIN, 11));
        txaDesc.setForeground(gray);
        txaDesc.setAlignmentX(Component.LEFT_ALIGNMENT);
        txaDesc.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));

        pnlDetails.add(lblName);
        pnlDetails.add(Box.createVerticalStrut(4));
        pnlDetails.add(txaDesc);
        pnlDetails.add(Box.createVerticalGlue());

        JPanel pnlBottomInfo = new JPanel(new BorderLayout());
        pnlBottomInfo.setOpaque(false);
        pnlBottomInfo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblPrice = new JLabel(price);
        lblPrice.setForeground(brown);
        lblPrice.setFont(new Font("Inter", Font.BOLD, 16));
        pnlBottomInfo.add(lblPrice, BorderLayout.WEST);

        JPanel pnlWrapperBtnAdd = new JPanel(new GridBagLayout());
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
                double priceValue = Double.parseDouble(price.replace("$", "").replace(",", "."));
                addToCart(name, priceValue, maMon);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        pnlWrapperBtnAdd.add(btnAdd);
        pnlBottomInfo.add(pnlWrapperBtnAdd, BorderLayout.EAST);
        pnlDetails.add(pnlBottomInfo);

        pnlCard.add(lblImgPlaceholder, BorderLayout.NORTH);
        pnlCard.add(pnlDetails, BorderLayout.CENTER);

        return pnlCard;
    }

    private void addToCart(String name, double price, String maMon) {
        for (Component comp : pnlCartItems.getComponents()) {
            if (comp instanceof JPanel && maMon.equals(comp.getName())) {
                updateQuantityInExistingItem((JPanel) comp, price);
                return;
            }
        }
        JPanel pnlItem = new JPanel();
        pnlItem.setLayout(new BoxLayout(pnlItem, BoxLayout.X_AXIS));
        pnlItem.setOpaque(false);
        pnlItem.setBorder(new EmptyBorder(10, 0, 15, 0));
        pnlItem.setName(maMon);

        JLabel lblIcon = new JLabel("☕");
        lblIcon.setPreferredSize(new Dimension(60, 60));
        lblIcon.setMinimumSize(new Dimension(60, 60));
        lblIcon.setMaximumSize(new Dimension(60, 60));
        lblIcon.setAlignmentY(Component.TOP_ALIGNMENT);
        lblIcon.setBackground(Color.decode("#e9e9d4"));
        lblIcon.setOpaque(true);
        lblIcon.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel pnlCenter = new JPanel();
        pnlCenter.setLayout(new BoxLayout(pnlCenter, BoxLayout.Y_AXIS));
        pnlCenter.setOpaque(false);
        pnlCenter.setAlignmentY(Component.TOP_ALIGNMENT);

        JLabel lblItemName = new JLabel(name);
        lblItemName.setFont(new Font("Inter", Font.BOLD, 15));

        JLabel lblNote = new JLabel("+ Standard Serving");
        lblNote.setFont(new Font("Inter", Font.ITALIC, 11));
        lblNote.setForeground(Color.GRAY);

        JPanel pnlQtyAction = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        pnlQtyAction.setOpaque(false);

        JPanel pnlQtyStepper = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 2));
        pnlQtyStepper.setBackground(Color.decode("#eef3f3"));

        JButton btnMinus = new JButton("–");
        JLabel lblQty = new JLabel("1");
        pnlItem.putClientProperty("qtyLabel", lblQty);
        JButton btnPlus = new JButton("+");

        for (JButton btn : new JButton[]{btnMinus, btnPlus}) {
            btn.setPreferredSize(new Dimension(24, 24));
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setBackground(Color.decode("#b8e2f2"));
        }

        pnlQtyStepper.add(btnMinus);
        pnlQtyStepper.add(lblQty);
        pnlQtyStepper.add(btnPlus);

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
        pnlCenter.add(pnlQtyAction);

        JLabel lblItemPrice = new JLabel(String.format("$%.2f", price));
        lblItemPrice.setFont(new Font("Inter", Font.BOLD, 16));
        lblItemPrice.setForeground(accentBrown);
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
            JPanel pnlQtyAction = (JPanel) pnlCenter.getComponent(2);
            JPanel pnlQtyStepper = (JPanel) pnlQtyAction.getComponent(0);
            JLabel lblQty = (JLabel) pnlQtyStepper.getComponent(1);

            int newQty = Integer.parseInt(lblQty.getText()) + 1;
            lblQty.setText(String.valueOf(newQty));
            updateTotal(unitPrice);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void filterMenu(String category, JButton selectedButton) {
        for (Component comp : pnlTabs.getComponents()) {
            if (comp instanceof JButton) {
                JButton btn = (JButton) comp;
                btn.setBackground(Color.decode("#f5f5db"));
                btn.setForeground(Color.decode("#7d7862"));
            }
        }
        selectedButton.setBackground(Color.decode("#e6e6cc"));
        selectedButton.setForeground(Color.decode("#573824"));

        pnlProductGrid.removeAll();
        List<Mon> allMon = mon_dao.getAll();
        
        String filterValue = ""; 
        switch (category.toLowerCase()) {
            case "coffee": filterValue = "COFFEE"; break;
            case "tea": filterValue = "TEA"; break;
            case "pastries": filterValue = "PASTRY"; break;
            default: filterValue = "ALL"; break;
        }

        for (Mon m : allMon) {
            String dataCategory = m.getPhanLoaiMonAn().toString();
            if (filterValue.equals("ALL") || 
               (dataCategory != null && dataCategory.trim().equalsIgnoreCase(filterValue))) {
                
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

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(250, 0));
        sidebar.setBackground(new Color(245, 245, 230));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new EmptyBorder(30, 20, 30, 20));

        JLabel lblLogo = new JLabel("ESPRESSO LOGIC");
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblLogo.setForeground(new Color(85, 55, 34));
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
        return sidebar;
    }

    private JButton createMenuButton(String text, String iconPath) {
        JButton btn = new JButton(text);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
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
            if (pnlCartItems.getComponentCount() == 0) {
                JOptionPane.showMessageDialog(this, "Cart is empty!");
                return;
            }
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
                try {
                    JLabel lblQty = (JLabel) pnlItem.getClientProperty("qtyLabel");
                    int soLuong = Integer.parseInt(lblQty.getText());
                    Mon mon = mon_dao.getAll().stream().filter(m -> m.getMaMon().equals(maMon)).findFirst().orElse(null);
                    if (mon == null) continue;
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
            hd.getDsChiTiet().addAll(dsChiTiet);
            hd.setTongTien();

            if (hoaDon_dao.addHoaDon(hd)) {
                JOptionPane.showMessageDialog(this, "Payment successful!");
                pnlCartItems.removeAll();
                totalAmount = 0;
                lblTotalPrice.setText("$0.00");
                pnlCartItems.revalidate();
                pnlCartItems.repaint();
            } else {
                JOptionPane.showMessageDialog(this, "Error saving invoice!");
            }
        } else if (o.equals(btnSearch)) {
            searchMenu(txtSearch.getText().trim());
        }
    }

    public void renderMenu() {
        pnlProductGrid.removeAll();
        List<Mon> listMon = mon_dao.getAll();
        for (Mon m : listMon) {
            JPanel card = createProductCard(
                m.getMaMon(), m.getTenMon(), m.getMoTaMon(),
                String.format("$%.2f", m.getDonGiaBan()), bgCard, accentBrown, textGray, m.getDuongDanAnh()
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
            if (m.getMaMon().equalsIgnoreCase(keyword) || m.getTenMon().toLowerCase().contains(keyword.toLowerCase())) {
                JPanel card = createProductCard(
                    m.getMaMon(), m.getTenMon(), m.getMoTaMon(),
                    String.format("$%.2f", m.getDonGiaBan()), bgCard, accentBrown, textGray, m.getDuongDanAnh()
                );
                pnlProductGrid.add(card);
            }
        }
        pnlProductGrid.revalidate();
        pnlProductGrid.repaint();
    }
}