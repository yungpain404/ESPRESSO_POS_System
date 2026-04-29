package app;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Dashboard extends JPanel {
    
    // Color Constants
    private static final Color BG = new Color(251, 251, 226);
    private static final Color BG_CARD = new Color(255, 248, 232);
    private static final Color BG_CARD2 = new Color(255, 254, 245);
    private static final Color ACCENT = new Color(85, 55, 34);
    private static final Color ACCENT_LIGHT = new Color(240, 232, 218);
    private static final Color BORDER_MAIN = new Color(200, 184, 154);
    private static final Color BORDER_LIGHT = new Color(221, 208, 184);
    private static final Color BORDER_SECTION = new Color(192, 170, 136);
    private static final Color TEXT_DARK = new Color(46, 31, 18);
    private static final Color TEXT_MID = new Color(92, 64, 48);
    private static final Color TEXT_DIM = new Color(138, 112, 96);
    
    // Component Variables
    private JLabel lblTotalInvoices;
    private JLabel lblTotalRevenue;
    private JLabel lblCashAmount;
    private JLabel lblBankAmount;
    private JLabel lblInvoicesSubtitle;
    private JLabel lblRevenueSubtitle;
    private JLabel lblCashSubtitle;
    private JLabel lblBankSubtitle;
    
    private JTable tblRecentInvoices;
    private DefaultTableModel modelRecentInvoices;
    
    private JTable tblTopItems;
    private DefaultTableModel modelTopItems;
    
    private JPanel pnlInsightsContainer;
    
    private JButton btnCancel;
    private JButton btnConfirm;
    private JButton btnEndShift;
    
    /**
     * Constructor: Khởi tạo Dashboard Panel
     * - Thiết lập layout chính (BorderLayout)
     * - Tạo top panel chứa tiêu đề và nút đóng ca
     * - Tạo content panel với các thống kê, bảng và nút hành động
     */
    /**
     * Constructor: Khởi tạo Dashboard Panel
     * - Thiết lập layout chính (BorderLayout)
     * - Tạo sidebar navigation menu bên trái
     * - Tạo top panel chứa tiêu đề và nút đóng ca
     * - Tạo content panel với các thống kê, bảng và nút hành động
     */
    public Dashboard() {
        setLayout(new BorderLayout());
        setBackground(BG);
        setPreferredSize(new Dimension(1400, 710));
        
        // Sidebar
        JPanel pnlSidebar = createSidebar();
        
        // Top Panel
        JPanel pnlTop = createTopPanel();
        
        // Main Content
        JPanel pnlContent = createContentPanel();
        
        // Scroll pane for content
        JScrollPane scrollPane = new JScrollPane(pnlContent);
        scrollPane.setBackground(BG);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(BG);
        
        // Center panel (Top + Content)
        JPanel pnlCenter = new JPanel(new BorderLayout());
        pnlCenter.setBackground(BG);
        pnlCenter.add(pnlTop, BorderLayout.NORTH);
        pnlCenter.add(scrollPane, BorderLayout.CENTER);
        
        add(pnlSidebar, BorderLayout.WEST);
        add(pnlCenter, BorderLayout.CENTER);
    }
    
    /**
     * Tạo Top Panel
     * - Hiển thị tiêu đề "Dashboard"
     * - Hiển thị thông tin ca làm việc (shift info)
     * - Nút "Close" để đóng ca
     */
    private JPanel createTopPanel() {
        JPanel pnlTop = new JPanel(new BorderLayout());
        pnlTop.setBackground(BG_CARD);
        pnlTop.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_MAIN));
        pnlTop.setPreferredSize(new Dimension(0, 44));
        
        JLabel lblTitle = new JLabel("Dashboard");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTitle.setForeground(TEXT_DARK);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(0, 18, 0, 0));
        
        JPanel pnlTopRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        pnlTopRight.setBackground(BG_CARD);
        pnlTopRight.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 18));
        
        JLabel lblShiftInfo = new JLabel();
        lblShiftInfo.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblShiftInfo.setForeground(TEXT_MID);
        lblShiftInfo.setBackground(ACCENT_LIGHT);
        lblShiftInfo.setOpaque(true);
        lblShiftInfo.setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 10));
        
        btnEndShift = new JButton("Close");
        btnEndShift.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        btnEndShift.setForeground(new Color(122, 32, 16));
        btnEndShift.setBackground(Color.WHITE);
        btnEndShift.setBorder(BorderFactory.createLineBorder(new Color(168, 80, 64)));
        btnEndShift.setFocusPainted(false);
        
        pnlTopRight.add(lblShiftInfo);
        pnlTopRight.add(btnEndShift);
        
        pnlTop.add(lblTitle, BorderLayout.WEST);
        pnlTop.add(pnlTopRight, BorderLayout.EAST);
        
        return pnlTop;
    }
    
    /**
     * Tạo Content Panel chính
     * - Chứa 4 phần: thống kê, 2 bảng, insights, nút hành động
     * - Sử dụng BoxLayout để xếp các phần theo chiều dọc
     */
    private JPanel createContentPanel() {
        JPanel pnlContent = new JPanel();
        pnlContent.setLayout(new BoxLayout(pnlContent, BoxLayout.Y_AXIS));
        pnlContent.setBackground(BG);
        
        // Statistics Section
        JPanel pnlStats = createStatisticsPanel();
        
        // Two Column Section
        JPanel pnlTwoCol = createTwoColumnPanel();
        
        // Insights Section
        JPanel pnlInsights = createInsightsPanel();
        
        // Bottom Buttons
        JPanel pnlButtons = createButtonsPanel();
        
        pnlContent.add(pnlStats);
        pnlContent.add(pnlTwoCol);
        pnlContent.add(pnlInsights);
        pnlContent.add(Box.createVerticalGlue());
        pnlContent.add(pnlButtons);
        
        return pnlContent;
    }
    
    /**
     * Tạo Statistics Panel
     * - Hiển thị 4 thẻ thống kê: tổng hóa đơn, doanh thu, tiền mặt, chuyển khoản
     * - Mỗi thẻ gồm: nhãn, giá trị chính, phụ đề (subtitle)
     * - Dữ liệu sẽ được populate từ logic sau
     */
    private JPanel createStatisticsPanel() {
        JPanel pnlStats = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 10));
        pnlStats.setBackground(BG);
        pnlStats.setPreferredSize(new Dimension(0, 130));
        pnlStats.setMaximumSize(new Dimension(Integer.MAX_VALUE, 130));
        pnlStats.setBorder(BorderFactory.createEmptyBorder(10, 12, 0, 12));
        
        pnlStats.add(createStatCard("Total Invoices", "lblTotalInvoices", "lblInvoicesSubtitle"));
        pnlStats.add(createStatCard("Total Revenue", "lblTotalRevenue", "lblRevenueSubtitle"));
        pnlStats.add(createStatCard("Cash", "lblCashAmount", "lblCashSubtitle"));
        pnlStats.add(createStatCard("Bank Transfer", "lblBankAmount", "lblBankSubtitle"));
        
        return pnlStats;
    }
    
    /**
     * Tạo một thẻ thống kê (Stat Card)
     * - Hiển thị: tiêu đề, giá trị lớn, phụ đề nhỏ
     * - Lưu reference của các JLabel để cập nhật dữ liệu từ logic
     * 
     * @param label - Tiêu đề thẻ (VD: "Total Invoices")
     * @param varValue - Tên biến để lưu JLabel giá trị
     * @param varSubtitle - Tên biến để lưu JLabel phụ đề
     */
    private JPanel createStatCard(String label, String varValue, String varSubtitle) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(BG_CARD2);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_LIGHT),
            BorderFactory.createEmptyBorder(10, 13, 10, 13)
        ));
        card.setPreferredSize(new Dimension(270, 100));
        card.setMaximumSize(new Dimension(270, 100));
        
        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblLabel.setForeground(TEXT_DIM);
        lblLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 4, 0));
        
        JLabel lblValue = new JLabel();
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblValue.setForeground(TEXT_DARK);
        lblValue.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lblSubtitle = new JLabel();
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lblSubtitle.setForeground(new Color(39, 80, 10));
        lblSubtitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblSubtitle.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
        
        // Lưu reference của các JLabel vào biến thành viên
        if (varValue.equals("lblTotalInvoices")) lblTotalInvoices = lblValue;
        else if (varValue.equals("lblTotalRevenue")) lblTotalRevenue = lblValue;
        else if (varValue.equals("lblCashAmount")) lblCashAmount = lblValue;
        else if (varValue.equals("lblBankAmount")) lblBankAmount = lblValue;
        
        if (varSubtitle.equals("lblInvoicesSubtitle")) lblInvoicesSubtitle = lblSubtitle;
        else if (varSubtitle.equals("lblRevenueSubtitle")) lblRevenueSubtitle = lblSubtitle;
        else if (varSubtitle.equals("lblCashSubtitle")) lblCashSubtitle = lblSubtitle;
        else if (varSubtitle.equals("lblBankSubtitle")) lblBankSubtitle = lblSubtitle;
        
        card.add(lblLabel);
        card.add(lblValue);
        card.add(lblSubtitle);
        
        return card;
    }
    
    /**
     * Tạo Two Column Panel
     * - Chia thành 2 cột bằng nhau (50-50)
     * - Cột trái: bảng Hóa đơn gần nhất
     * - Cột phải: bảng Top 5 sản phẩm bán chạy
     */
    private JPanel createTwoColumnPanel() {
        JPanel pnlTwoCol = new JPanel(new GridLayout(1, 2, 10, 0));
        pnlTwoCol.setBackground(BG);
        pnlTwoCol.setMaximumSize(new Dimension(Integer.MAX_VALUE, 180));
        pnlTwoCol.setBorder(BorderFactory.createEmptyBorder(10, 12, 0, 12));
        
        pnlTwoCol.add(createRecentInvoicesPanel());
        pnlTwoCol.add(createTopItemsPanel());
        
        return pnlTwoCol;
    }

    /**
     * Tạo Recent Invoices Panel
     * - Hiển thị bảng 5 hóa đơn gần nhất
     * - Cột: #, Time, Total, Method
     * - Bảng read-only, không cho chỉnh sửa
     * - Dữ liệu sẽ được populate từ logic bằng modelRecentInvoices.addRow()
     */
    private JPanel createRecentInvoicesPanel() {
        JPanel pnlBox = new JPanel(new BorderLayout());
        pnlBox.setBackground(BG_CARD2);
        pnlBox.setBorder(BorderFactory.createLineBorder(BORDER_MAIN));
        
        // Header
        JPanel pnlHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pnlHeader.setBackground(BG_CARD);
        pnlHeader.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_SECTION));
        pnlHeader.setPreferredSize(new Dimension(0, 30));
        
        JLabel lblHeader = new JLabel("RECENT INVOICES");
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblHeader.setForeground(TEXT_MID);
        lblHeader.setBorder(BorderFactory.createEmptyBorder(8, 12, 0, 0));
        pnlHeader.add(lblHeader);
        
        // Table
        String[] columns = {"#", "Time", "Total", "Method"};
        modelRecentInvoices = new DefaultTableModel(columns, 5) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Bảng read-only, không cho chỉnh sửa
            }
        };
        tblRecentInvoices = new JTable(modelRecentInvoices);
        tblRecentInvoices.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        tblRecentInvoices.setBackground(BG_CARD2);
        tblRecentInvoices.setForeground(TEXT_DARK);
        tblRecentInvoices.setRowHeight(25);
        tblRecentInvoices.setShowGrid(false);
        tblRecentInvoices.setIntercellSpacing(new Dimension(0, 1));
        tblRecentInvoices.getTableHeader().setBackground(BG_CARD);
        tblRecentInvoices.getTableHeader().setFont(new Font("Segoe UI", Font.PLAIN, 10));
        tblRecentInvoices.getTableHeader().setForeground(TEXT_DIM);
        tblRecentInvoices.getTableHeader().setPreferredSize(new Dimension(0, 25));
        tblRecentInvoices.getTableHeader().setReorderingAllowed(false);
        tblRecentInvoices.setPreferredScrollableViewportSize(new Dimension(0, 150));
        
        // Set column widths
        tblRecentInvoices.getColumnModel().getColumn(0).setPreferredWidth(50);
        tblRecentInvoices.getColumnModel().getColumn(1).setPreferredWidth(60);
        tblRecentInvoices.getColumnModel().getColumn(2).setPreferredWidth(80);
        tblRecentInvoices.getColumnModel().getColumn(3).setPreferredWidth(80);
        
        JScrollPane scrollTable = new JScrollPane(tblRecentInvoices);
        scrollTable.setBackground(BG_CARD2);
        scrollTable.setBorder(null);
        scrollTable.getViewport().setBackground(BG_CARD2);
        scrollTable.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollTable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        pnlBox.add(pnlHeader, BorderLayout.NORTH);
        pnlBox.add(scrollTable, BorderLayout.CENTER);
        
        return pnlBox;
    }

    /**
     * Tạo Top Items Panel
     * - Hiển thị bảng Top 5 sản phẩm bán chạy
     * - Cột: Rank, Item Name, Quantity
     * - Bảng read-only, không cho chỉnh sửa
     * - Dữ liệu sẽ được populate từ logic bằng modelTopItems.addRow()
     */
    private JPanel createTopItemsPanel() {
        JPanel pnlBox = new JPanel(new BorderLayout());
        pnlBox.setBackground(BG_CARD2);
        pnlBox.setBorder(BorderFactory.createLineBorder(BORDER_MAIN));
        
        // Header
        JPanel pnlHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pnlHeader.setBackground(BG_CARD);
        pnlHeader.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_SECTION));
        pnlHeader.setPreferredSize(new Dimension(0, 30));
        
        JLabel lblHeader = new JLabel("TOP SELLING ITEMS");
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblHeader.setForeground(TEXT_MID);
        lblHeader.setBorder(BorderFactory.createEmptyBorder(8, 12, 0, 0));
        pnlHeader.add(lblHeader);
        
        // Table
        String[] columns = {"Rank", "Item Name", "Quantity"};
        modelTopItems = new DefaultTableModel(columns, 5) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Bảng read-only, không cho chỉnh sửa
            }
        };
        tblTopItems = new JTable(modelTopItems);
        tblTopItems.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        tblTopItems.setBackground(BG_CARD2);
        tblTopItems.setForeground(TEXT_DARK);
        tblTopItems.setRowHeight(25);
        tblTopItems.setShowGrid(false);
        tblTopItems.setIntercellSpacing(new Dimension(0, 1));
        tblTopItems.getTableHeader().setBackground(BG_CARD);
        tblTopItems.getTableHeader().setFont(new Font("Segoe UI", Font.PLAIN, 10));
        tblTopItems.getTableHeader().setForeground(TEXT_DIM);
        tblTopItems.getTableHeader().setPreferredSize(new Dimension(0, 25));
        tblTopItems.getTableHeader().setReorderingAllowed(false);
        tblTopItems.setPreferredScrollableViewportSize(new Dimension(0, 150));
        
        // Set column widths
        tblTopItems.getColumnModel().getColumn(0).setPreferredWidth(40);
        tblTopItems.getColumnModel().getColumn(1).setPreferredWidth(150);
        tblTopItems.getColumnModel().getColumn(2).setPreferredWidth(80);
        
        JScrollPane scrollTable = new JScrollPane(tblTopItems);
        scrollTable.setBackground(BG_CARD2);
        scrollTable.setBorder(null);
        scrollTable.getViewport().setBackground(BG_CARD2);
        scrollTable.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollTable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        pnlBox.add(pnlHeader, BorderLayout.NORTH);
        pnlBox.add(scrollTable, BorderLayout.CENTER);
        
        return pnlBox;
    }
    
    /**
     * Tạo Insights Panel
     * - Chứa container để hiển thị các insight box (nhận xét từ số liệu)
     * - Mỗi insight gồm: dot màu, text nhận xét
     * - Dữ liệu sẽ được populate từ logic bằng pnlInsightsContainer.add()
     */
    private JPanel createInsightsPanel() {
        JPanel pnlInsights = new JPanel();
        pnlInsights.setLayout(new BoxLayout(pnlInsights, BoxLayout.Y_AXIS));
        pnlInsights.setBackground(BG);
        pnlInsights.setPreferredSize(new Dimension(0, 180));
        pnlInsights.setMaximumSize(new Dimension(Integer.MAX_VALUE, 180));
        pnlInsights.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));
        
        pnlInsightsContainer = new JPanel();
        pnlInsightsContainer.setLayout(new BoxLayout(pnlInsightsContainer, BoxLayout.Y_AXIS));
        pnlInsightsContainer.setBackground(BG);
        
        pnlInsights.add(pnlInsightsContainer);
        
        return pnlInsights;
    }
    
    /**
     * Tạo Buttons Panel
     * - Chứa 2 nút: Cancel, Confirm
     * - Nút Cancel: để hủy bỏ đóng ca
     * - Nút Confirm: để xác nhận đóng ca và in báo cáo
     */
    private JPanel createButtonsPanel() {
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 10));
        pnlButtons.setBackground(BG);
        pnlButtons.setPreferredSize(new Dimension(0, 50));
        pnlButtons.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        pnlButtons.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));
        
        btnCancel = new JButton("Cancel");
        btnCancel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnCancel.setForeground(TEXT_MID);
        btnCancel.setBackground(Color.WHITE);
        btnCancel.setBorder(BorderFactory.createLineBorder(BORDER_MAIN));
        btnCancel.setFocusPainted(false);
        btnCancel.setPreferredSize(new Dimension(100, 32));
        
        btnConfirm = new JButton("Confirm Close & Print Report");
        btnConfirm.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnConfirm.setForeground(new Color(255, 245, 236));
        btnConfirm.setBackground(ACCENT);
        btnConfirm.setBorder(BorderFactory.createLineBorder(ACCENT));
        btnConfirm.setFocusPainted(false);
        btnConfirm.setPreferredSize(new Dimension(250, 32));
        
        pnlButtons.add(btnCancel);
        pnlButtons.add(btnConfirm);
        
        return pnlButtons;
    }
    
    /**
     * Tạo Sidebar Panel
     * - Hiển thị logo "ESPRESSO LOGIC"
     * - Menu navigation: Menu, Menu Management, Analytics
     * - Nút "+ New Order" ở dưới cùng
     * - Các menu item căn trái, chữ to
     */
    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(280, 0));
        sidebar.setBackground(new Color(245, 245, 230));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));

        // Logo
        JLabel lblLogo = new JLabel("ESPRESSO LOGIC");
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblLogo.setForeground(new Color(85, 55, 34));
        sidebar.add(lblLogo);
        sidebar.add(Box.createRigidArea(new Dimension(0, 40)));

        // Danh sách menu (Tên - Icon Path)
        String[][] menuData = {
            {"Menu", "src/img/menu.png"},
            {"Menu Management", "src/img/menumanagement.png"}, 
            {"Analytics", "src/img/analytics.png"}
        };

        for (String[] data : menuData) {
            JButton btn = createMenuButton(data[0], data[1]);
            btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
            btn.setMinimumSize(new Dimension(200, 45));
            
            // Highlight mục đang chọn (Menu Management)
            if (data[0].equals("Menu Management")) {
                btn.setBackground(new Color(230, 220, 200));
                btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
                btn.setContentAreaFilled(true);
                btn.setOpaque(true);
                btn.setBorderPainted(false);
            } else {
                btn.setContentAreaFilled(false);
                btn.setOpaque(false);
                btn.setBorderPainted(false);
                btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            }

            sidebar.add(btn);
            sidebar.add(Box.createRigidArea(new Dimension(0, 15)));
        }

        sidebar.add(Box.createVerticalGlue());
        
        JButton btnNewOrder = new JButton("+ New Order");
        btnNewOrder.setBackground(ACCENT);
        btnNewOrder.setForeground(Color.WHITE);
        btnNewOrder.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btnNewOrder.setMinimumSize(new Dimension(200, 45));
        btnNewOrder.setFocusPainted(false);
        btnNewOrder.setBorderPainted(false);
        btnNewOrder.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnNewOrder.setOpaque(true);
        sidebar.add(btnNewOrder);
        
        return sidebar;
    }

    /**
     * Tạo Menu Button
     * - Nút menu với icon và tên
     * - Căn trái, chữ lớn
     * 
     * @param name - Tên menu item
     * @param iconPath - Đường dẫn đến icon
     * @return JButton menu button
     */
    private JButton createMenuButton(String name, String iconPath) {
        JButton btn = new JButton(name);
        btn.setForeground(new Color(100, 80, 70));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 0));
        
        // TODO: Thêm icon từ iconPath khi có hình ảnh
        // ImageIcon icon = new ImageIcon(iconPath);
        // btn.setIcon(icon);
        // btn.setIconTextGap(10);
        
        return btn;
    }
    
    // ========== GETTER METHODS ==========
    // Các getter dùng để truy cập và cập nhật dữ liệu từ logic controller
    
    /**
     * Lấy JLabel tổng số hóa đơn
     * @return JLabel lblTotalInvoices
     */
    public JLabel getLblTotalInvoices() { return lblTotalInvoices; }
    
    /**
     * Lấy JLabel tổng doanh thu
     * @return JLabel lblTotalRevenue
     */
    public JLabel getLblTotalRevenue() { return lblTotalRevenue; }
    
    /**
     * Lấy JLabel tổng tiền mặt
     * @return JLabel lblCashAmount
     */
    public JLabel getLblCashAmount() { return lblCashAmount; }
    
    /**
     * Lấy JLabel tổng tiền chuyển khoản
     * @return JLabel lblBankAmount
     */
    public JLabel getLblBankAmount() { return lblBankAmount; }
    
    /**
     * Lấy JLabel phụ đề thống kê hóa đơn
     * @return JLabel lblInvoicesSubtitle
     */
    public JLabel getLblInvoicesSubtitle() { return lblInvoicesSubtitle; }
    
    /**
     * Lấy JLabel phụ đề thống kê doanh thu
     * @return JLabel lblRevenueSubtitle
     */
    public JLabel getLblRevenueSubtitle() { return lblRevenueSubtitle; }
    
    /**
     * Lấy JLabel phụ đề thống kê tiền mặt
     * @return JLabel lblCashSubtitle
     */
    public JLabel getLblCashSubtitle() { return lblCashSubtitle; }
    
    /**
     * Lấy JLabel phụ đề thống kê tiền chuyển khoản
     * @return JLabel lblBankSubtitle
     */
    public JLabel getLblBankSubtitle() { return lblBankSubtitle; }
    
    /**
     * Lấy DefaultTableModel bảng hóa đơn gần nhất
     * Dùng để thêm dữ liệu: modelRecentInvoices.addRow(new Object[]{...})
     * @return DefaultTableModel modelRecentInvoices
     */
    public DefaultTableModel getModelRecentInvoices() { return modelRecentInvoices; }
    
    /**
     * Lấy DefaultTableModel bảng top sản phẩm bán chạy
     * Dùng để thêm dữ liệu: modelTopItems.addRow(new Object[]{...})
     * @return DefaultTableModel modelTopItems
     */
    public DefaultTableModel getModelTopItems() { return modelTopItems; }
    
    /**
     * Lấy JTable hóa đơn gần nhất
     * @return JTable tblRecentInvoices
     */
    public JTable getTblRecentInvoices() { return tblRecentInvoices; }
    
    /**
     * Lấy JTable top sản phẩm bán chạy
     * @return JTable tblTopItems
     */
    public JTable getTblTopItems() { return tblTopItems; }
    
    /**
     * Lấy JPanel container insights
     * Dùng để thêm insight box: pnlInsightsContainer.add(createInsightBox(...))
     * @return JPanel pnlInsightsContainer
     */
    public JPanel getPnlInsightsContainer() { return pnlInsightsContainer; }
    
    /**
     * Lấy nút Cancel
     * Dùng để thêm action listener
     * @return JButton btnCancel
     */
    public JButton getBtnCancel() { return btnCancel; }
    
    /**
     * Lấy nút Confirm
     * Dùng để thêm action listener
     * @return JButton btnConfirm
     */
    public JButton getBtnConfirm() { return btnConfirm; }
    
    /**
     * Lấy nút Close Shift
     * Dùng để thêm action listener
     * @return JButton btnEndShift
     */
    public JButton getBtnEndShift() { return btnEndShift; }
    
    /**
     * Main method - Chạy ứng dụng Dashboard
     * Tạo JFrame và hiển thị Dashboard Panel
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Cafe POS - Dashboard");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new Dashboard());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}