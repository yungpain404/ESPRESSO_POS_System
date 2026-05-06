package app;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLightLaf;
import dao.Mon_DAO;
import entity.Mon;
import entity.PhanLoaiMonAn;
import entity.TaiKhoan;
import util.SessionManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@SuppressWarnings("serial")
public class MenuList_UI extends JFrame implements ActionListener {
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
    
    private JButton btnSearch;
    private JTextField txtSearch;
    private JPanel pnlProductGrid;
    private List<entity.Mon> originalList;
    private JButton activeTab; 
    private JButton btnAll;

    private Mon_DAO mon_dao = new Mon_DAO();
    @SuppressWarnings("deprecation")
	private final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

    public MenuList_UI() {
        setTitle("Espresso Menu Browser - Pure Java Optimized");
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

        TaiKhoan user = SessionManager.getCurrentUser();
        
        JLabel lblUserTittle = new JLabel("Account", SwingConstants.RIGHT);
        lblUserTittle.setFont(new Font("Inter", Font.BOLD, 14));
        lblUserTittle.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JLabel lblUserName = new JLabel(user.getTenTaiKhoan());
        lblUserName.setForeground(textGray);
        lblUserName.setFont(new Font("Inter", Font.PLAIN, 12));
        lblUserName.setAlignmentX(Component.RIGHT_ALIGNMENT);

        pnlUser.add(lblUserTittle);
        pnlUser.add(lblUserName);
        pnlProfile.add(pnlUser);

        pnlHeader.add(pnlSearchBox, BorderLayout.WEST);
        pnlHeader.add(pnlProfile, BorderLayout.EAST);
        pnlMain.add(pnlHeader, BorderLayout.NORTH);

        JPanel pnlBody = new JPanel(new BorderLayout());
        pnlBody.setOpaque(false);
        pnlMain.add(pnlBody, BorderLayout.CENTER);

        JPanel pnlLeft = new JPanel(new BorderLayout());
        pnlLeft.setOpaque(false);
        pnlLeft.setBorder(new EmptyBorder(10, 25, 20, 25));

        JPanel pnlMenuHeader = new JPanel(new BorderLayout());
        pnlMenuHeader.setOpaque(false);
        JLabel lblTitle = new JLabel("Espresso Menu");
        lblTitle.setFont(new Font("Inter", Font.BOLD, 26));
        lblTitle.setForeground(accentBrown);

        JPanel pnlTabs = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        pnlTabs.setOpaque(false);
        
        btnAll = createTabButton("All", true);
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

        pnlProductGrid = new JPanel(new GridLayout(0, 4, 15, 15));
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

        renderMenu();

        btnSearch.addActionListener(this);
    }
    private JPanel createProductCard(String maMon, String name, String desc, String price, Color bg, Color brown, Color gray, String imagePath) {

        JPanel pnlCard = new JPanel(new BorderLayout());
        pnlCard.setPreferredSize(new Dimension(230, 255));
        pnlCard.setMaximumSize(new Dimension(230, 255));
        pnlCard.setBackground(bg);
        pnlCard.setName(maMon);

        JLabel lblImgPlaceholder = new JLabel("", SwingConstants.CENTER);
        lblImgPlaceholder.setPreferredSize(new Dimension(0, 130));
        lblImgPlaceholder.setOpaque(true);
        lblImgPlaceholder.setBackground(Color.decode("#EEEEEE"));
        try {
            String urlString = imagePath; 
            if (urlString != null && !urlString.isEmpty()) {
            	java.net.URL url = new java.net.URI(imagePath).toURL(); 
            	Image img = javax.imageio.ImageIO.read(url);
                if (img != null) {
                    Image scaledImg = img.getScaledInstance(250, -1, Image.SCALE_SMOOTH);
                    lblImgPlaceholder.setIcon(new ImageIcon(scaledImg));
                    lblImgPlaceholder.setText(""); 
                } else {
                  lblImgPlaceholder.setText("No Image");
                }
            } else {
                lblImgPlaceholder.setText("No URL");
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                ImageIcon errorIcon = new ImageIcon(new ImageIcon("img/flatwhite.png")
                                        .getImage().getScaledInstance(250, -1, Image.SCALE_SMOOTH));
                lblImgPlaceholder.setIcon(errorIcon);
                lblImgPlaceholder.setText("Lỗi tải ảnh");
            } catch (Exception ex) {
                lblImgPlaceholder.setText("Error");
            }
        }

        JPanel pnlDetails = new JPanel();
        pnlDetails.setLayout(new BoxLayout(pnlDetails, BoxLayout.Y_AXIS));
        pnlDetails.setOpaque(false);
        pnlDetails.setBorder(new EmptyBorder(10, 12, 12, 12));

       JPanel pnlName = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
       pnlName.setBackground(Color.white);
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

        pnlName.add(lblName);
        pnlDetails.add(pnlName);
        pnlDetails.add(Box.createVerticalStrut(4));
        pnlDetails.add(txaDesc);
        pnlDetails.add(Box.createVerticalGlue());

        JPanel pnlBottomInfo = new JPanel(new BorderLayout());
        pnlBottomInfo.setOpaque(false);

        JLabel lblPrice = new JLabel(price);
        lblPrice.setForeground(brown);
        lblPrice.setFont(new Font("Inter", Font.BOLD, 16));
        pnlBottomInfo.add(lblPrice, BorderLayout.WEST);

        pnlDetails.add(pnlBottomInfo);

        pnlCard.add(lblImgPlaceholder, BorderLayout.NORTH);
        pnlCard.add(pnlDetails, BorderLayout.CENTER);

        return pnlCard;
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
        btnNewOrder.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnNewOrder.addActionListener(e -> {
            new CreateFormOrders_UI().setVisible(true);
            this.dispose();
        });
        sidebar.add(btnNewOrder);

        return sidebar;
}
    
    public void renderMenu() {
        originalList = mon_dao.getAll(); 
        displayFilteredList(originalList); 
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
            List<entity.Mon> filtered = originalList.stream()
                    .filter(m -> m.getPhanLoaiMonAn().equals(loai)) 
                    .toList();
            displayFilteredList(filtered);
        }
    }
    private void displayFilteredList(List<entity.Mon> list) {
        pnlProductGrid.removeAll();
        for (entity.Mon m : list) {
            JPanel card = createProductCard(
                m.getMaMon(), m.getTenMon(), m.getMoTaMon(),
                currencyFormatter.format(m.getDonGiaBan()), 
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

    @Override
    public void actionPerformed(ActionEvent e) {
    	Object o = e.getSource();
    	if (o.equals(btnSearch)) {
    		String keyWord = txtSearch.getText().trim();
    		searchMenu(keyWord);
    	}
    }
    private void searchMenu(String keyword) {
        if (activeTab != btnAll) {
            updateTabStyle(activeTab, false);
            activeTab = btnAll;
            updateTabStyle(activeTab, true);
        }

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
                    currencyFormatter.format(m.getDonGiaBan()), bgCard, accentBrown, textGray, m.getDuongDanAnh()
                );
                pnlProductGrid.add(card);
            }
        }
        pnlProductGrid.revalidate();
        pnlProductGrid.repaint();
    }
}