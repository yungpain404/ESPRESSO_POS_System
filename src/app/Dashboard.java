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
    
    public Dashboard() {
        setLayout(new BorderLayout());
        setBackground(BG);
        setPreferredSize(new Dimension(1200, 710));
        
        // Top Panel
        JPanel pnlTop = createTopPanel();
        
        // Main Content
        JPanel pnlContent = createContentPanel();
        
        // Scroll pane for content
        JScrollPane scrollPane = new JScrollPane(pnlContent);
        scrollPane.setBackground(BG);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(BG);
        
        add(pnlTop, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
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
        
        // Store references
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
        btnConfirm.setForeground(new Color(255, 245, 236));
        btnConfirm.setBackground(ACCENT);
        btnConfirm.setBorder(BorderFactory.createLineBorder(ACCENT));
        btnConfirm.setFocusPainted(false);
        btnConfirm.setPreferredSize(new Dimension(250, 32));
        
        pnlButtons.add(btnCancel);
        pnlButtons.add(btnConfirm);
        
        return pnlButtons;
    }
    
    // Getters for logic implementation
    public JLabel getLblTotalInvoices() { return lblTotalInvoices; }
    public JLabel getLblTotalRevenue() { return lblTotalRevenue; }
    public JLabel getLblCashAmount() { return lblCashAmount; }
    public JLabel getLblBankAmount() { return lblBankAmount; }
    public JLabel getLblInvoicesSubtitle() { return lblInvoicesSubtitle; }
    public JLabel getLblRevenueSubtitle() { return lblRevenueSubtitle; }
    public JLabel getLblCashSubtitle() { return lblCashSubtitle; }
    public JLabel getLblBankSubtitle() { return lblBankSubtitle; }
    
    public DefaultTableModel getModelRecentInvoices() { return modelRecentInvoices; }
    public DefaultTableModel getModelTopItems() { return modelTopItems; }
    public JTable getTblRecentInvoices() { return tblRecentInvoices; }
    public JTable getTblTopItems() { return tblTopItems; }
    
    public JPanel getPnlInsightsContainer() { return pnlInsightsContainer; }
    
    public JButton getBtnCancel() { return btnCancel; }
    public JButton getBtnConfirm() { return btnConfirm; }
    public JButton getBtnEndShift() { return btnEndShift; }
    
    private JButton btnEndShift;
    
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

