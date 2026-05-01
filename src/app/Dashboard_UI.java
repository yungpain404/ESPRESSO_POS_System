package app;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

import dao.HoaDon_DAO;
import entity.HoaDon;
import entity.ChiTietHoaDon;
import entity.PhuongThucThanhToan;

@SuppressWarnings("serial")
public class Dashboard_UI extends JFrame { 
    
    private static final Color BG = new Color(251, 251, 240);
    private static final Color BG_CARD = Color.WHITE;
    private static final Color BG_CARD2 = Color.WHITE;
    private static final Color ACCENT = new Color(85, 55, 34);
    private static final Color BORDER_MAIN = new Color(235, 235, 230);
    private static final Color BORDER_LIGHT = new Color(235, 235, 230);
    private static final Color BORDER_SECTION = new Color(235, 235, 230);
    private static final Color TEXT_DARK = new Color(46, 31, 18);
    private static final Color TEXT_MID = new Color(85, 55, 34);
    private static final Color TEXT_DIM = Color.GRAY;
    
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
    
    private HoaDon_DAO hoaDonDao = new HoaDon_DAO();
    
    public Dashboard_UI() {
        setTitle("Espresso Logic - Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BG);
        
        JPanel pnlSidebar = createSidebar();
        JPanel pnlTop = createTopPanel();
        JPanel pnlContent = createContentPanel();
        
        JScrollPane scrollPane = new JScrollPane(pnlContent);
        scrollPane.setBackground(BG);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(BG);
        
        JPanel pnlCenter = new JPanel(new BorderLayout());
        pnlCenter.setBackground(BG);
        pnlCenter.add(pnlTop, BorderLayout.NORTH);
        pnlCenter.add(scrollPane, BorderLayout.CENTER);
        
        add(pnlSidebar, BorderLayout.WEST);
        add(pnlCenter, BorderLayout.CENTER);
        
        loadDashboardData();
    }
    
    /**
     * Load dữ liệu Dashboard
     * - Tính tổng hóa đơn, doanh thu, tiền mặt, chuyển khoản
     * - Load 5 hóa đơn gần nhất
     * - Load top 5 sản phẩm bán chạy
     */
    /**
     * Load dữ liệu Dashboard
     */
    private void loadDashboardData() {
        LocalDate testDate = LocalDate.of(2026, 4, 30);
        List<HoaDon> hoaDonHom = hoaDonDao.getByDate(testDate);
        
        if (hoaDonHom == null || hoaDonHom.isEmpty()) {
            System.out.println("Không có hóa đơn hôm nay");
            // Set giá trị mặc định để không bị NullPointerException
            lblTotalInvoices.setText("0");
            lblTotalRevenue.setText("$0.00");
            lblCashAmount.setText("$0.00");
            lblBankAmount.setText("$0.00");
            return;
        }
        
        calculateStatistics(hoaDonHom);
        loadRecentInvoices(hoaDonHom);
        loadTopItems(hoaDonHom);
    }
    
    /**
     * Tính toán thống kê
     */
    private void calculateStatistics(List<HoaDon> hoaDonList) {
        int totalInvoices = hoaDonList.size();
        double totalRevenue = 0;
        double cashRevenue = 0;
        double transferRevenue = 0;
        
        for (HoaDon hd : hoaDonList) {
            totalRevenue += hd.getTongTien();
            
            if (hd.getPhuongThucTT() == PhuongThucThanhToan.TIENMAT) {
                cashRevenue += hd.getTongTien();
            } else {
                transferRevenue += hd.getTongTien();
            }
        }
        
        lblTotalInvoices.setText(String.valueOf(totalInvoices));
        lblTotalRevenue.setText(formatCurrency(totalRevenue));
        lblCashAmount.setText(formatCurrency(cashRevenue));
        lblBankAmount.setText(formatCurrency(transferRevenue));
        
        lblInvoicesSubtitle.setText("+0 vs yesterday");
        lblRevenueSubtitle.setText("+0% vs yesterday");
        lblCashSubtitle.setText(totalInvoices + " invoices · 65%");
        lblBankSubtitle.setText("35% revenue");
    }
    
    /**
     * Load 5 hóa đơn gần nhất
     */
    private void loadRecentInvoices(List<HoaDon> hoaDonList) {
        modelRecentInvoices.setRowCount(0);
        
        int start = Math.max(0, hoaDonList.size() - 5);
        for (int i = hoaDonList.size() - 1; i >= start; i--) {
            HoaDon hd = hoaDonList.get(i);
            String method = hd.getPhuongThucTT() == PhuongThucThanhToan.TIENMAT ? "Cash" : "Transfer";
            
            modelRecentInvoices.addRow(new Object[]{
                hd.getMaHD(),
                hd.getNgayGioLap().toString(),
                formatCurrency(hd.getTongTien()),
                method
            });
        }
    }
    
    /**
     * Load top 5 sản phẩm bán chạy
     */
    private void loadTopItems(List<HoaDon> hoaDonList) {
        modelTopItems.setRowCount(0);
        
        Map<String, Integer> mapSoLuong = new HashMap<>();
        
        for (HoaDon hd : hoaDonList) {
            List<ChiTietHoaDon> dsChiTiet = hd.getDsChiTiet();
            if (dsChiTiet != null) {
                for (ChiTietHoaDon ct : dsChiTiet) {
                    String tenMon = ct.getMon().getTenMon();
                    mapSoLuong.put(tenMon, mapSoLuong.getOrDefault(tenMon, 0) + ct.getSoLuongMon());
                }
            }
        }
        
        List<Map.Entry<String, Integer>> sortedList = new ArrayList<>(mapSoLuong.entrySet());
        sortedList.sort((a, b) -> b.getValue().compareTo(a.getValue()));
        
        int rank = 1;
        for (int i = 0; i < Math.min(5, sortedList.size()); i++) {
            String tenMon = sortedList.get(i).getKey();
            int soLuong = sortedList.get(i).getValue();
            
            modelTopItems.addRow(new Object[]{
                rank++,
                tenMon,
                soLuong + " ly"
            });
        }
    }
    
    private String formatCurrency(double amount) {
        return String.format("$%.2f", amount);
    }
    
    private JPanel createTopPanel() {
        JPanel pnlTop = new JPanel(new BorderLayout());
        pnlTop.setBackground(BG_CARD);
        pnlTop.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_MAIN));
        pnlTop.setPreferredSize(new Dimension(0, 44));
        
        JLabel lblTitle = new JLabel("Dashboard");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTitle.setForeground(TEXT_DARK);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(0, 18, 0, 0));
        
        pnlTop.add(lblTitle, BorderLayout.WEST);
        
        return pnlTop;
    }

    private JPanel createContentPanel() {
        JPanel pnlContent = new JPanel();
        pnlContent.setLayout(new BoxLayout(pnlContent, BoxLayout.Y_AXIS));
        pnlContent.setBackground(BG);
        
        pnlContent.add(createStatisticsPanel());
        pnlContent.add(createTwoColumnPanel());
        pnlContent.add(createInsightsPanel());
        pnlContent.add(Box.createVerticalGlue());
        pnlContent.add(createButtonsPanel());
        
        return pnlContent;
    }

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

    private JPanel createTwoColumnPanel() {
        JPanel pnlTwoCol = new JPanel(new GridLayout(1, 2, 10, 0));
        pnlTwoCol.setBackground(BG);
        pnlTwoCol.setMaximumSize(new Dimension(Integer.MAX_VALUE, 180));
        pnlTwoCol.setBorder(BorderFactory.createEmptyBorder(10, 12, 0, 12));
        
        pnlTwoCol.add(createRecentInvoicesPanel());
        pnlTwoCol.add(createTopItemsPanel());
        
        return pnlTwoCol;
    }

    private JPanel createRecentInvoicesPanel() {
        JPanel pnlBox = new JPanel(new BorderLayout());
        pnlBox.setBackground(BG_CARD2);
        pnlBox.setBorder(BorderFactory.createLineBorder(BORDER_MAIN));
        
        JPanel pnlHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pnlHeader.setBackground(BG_CARD);
        pnlHeader.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_SECTION));
        pnlHeader.setPreferredSize(new Dimension(0, 30));
        
        JLabel lblHeader = new JLabel("RECENT INVOICES");
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblHeader.setForeground(TEXT_MID);
        lblHeader.setBorder(BorderFactory.createEmptyBorder(8, 12, 0, 0));
        pnlHeader.add(lblHeader);
        
        String[] columns = {"#", "Time", "Total", "Method"};
        modelRecentInvoices = new DefaultTableModel(columns, 5) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
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

    private JPanel createTopItemsPanel() {
        JPanel pnlBox = new JPanel(new BorderLayout());
        pnlBox.setBackground(BG_CARD2);
        pnlBox.setBorder(BorderFactory.createLineBorder(BORDER_MAIN));
        
        JPanel pnlHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pnlHeader.setBackground(BG_CARD);
        pnlHeader.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_SECTION));
        pnlHeader.setPreferredSize(new Dimension(0, 30));
        
        JLabel lblHeader = new JLabel("TOP SELLING ITEMS");
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblHeader.setForeground(TEXT_MID);
        lblHeader.setBorder(BorderFactory.createEmptyBorder(8, 12, 0, 0));
        pnlHeader.add(lblHeader);
        
        String[] columns = {"Rank", "Item Name", "Quantity"};
        modelTopItems = new DefaultTableModel(columns, 5) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
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
        btnConfirm.setForeground(Color.WHITE);
        btnConfirm.setBackground(ACCENT);
        btnConfirm.setBorder(BorderFactory.createLineBorder(ACCENT));
        btnConfirm.setFocusPainted(false);
        btnConfirm.setPreferredSize(new Dimension(250, 32));
        
        pnlButtons.add(btnCancel);
        pnlButtons.add(btnConfirm);
        
        return pnlButtons;
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
            if (data[0].equals("Analytics")) {
                btn.setBackground(new Color(230, 230, 210));
                btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
                btn.setOpaque(true);
                btn.setContentAreaFilled(true);
            } else {
                btn.setContentAreaFilled(false);
            }
            if (data[0].equals("Menu")) {
                btn.addActionListener(e -> {
                    CreateOrders_UI nextFrame = new CreateOrders_UI();
                    nextFrame.setBounds(this.getBounds()); 
                    nextFrame.setExtendedState(this.getExtendedState());
                    nextFrame.setVisible(true);
                    this.dispose();
                });
            } else if (data[0].equals("Menu Management")) {
                btn.addActionListener(e -> {
                    MenuManagement nextFrame = new MenuManagement();
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
        JButton btn = new JButton(text);
        try {
            ImageIcon icon = new ImageIcon(iconPath);
            Image scaled = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            btn.setIcon(new ImageIcon(scaled));
        } catch (Exception ignored) {}
        
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
}