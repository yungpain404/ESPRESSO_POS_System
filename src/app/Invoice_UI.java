package app;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

@SuppressWarnings("serial")
public class Invoice_UI extends JFrame {
	static {
	    FlatLightLaf.setup();
	    UIManager.put("Button.arc", 20);
	    UIManager.put("Component.arc", 20);
	}
    public Invoice_UI() {
        setTitle("Order Details - Pure Flat Java");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);
        // Bảng màu
        Color bgMain = Color.decode("#F9F8E6");
        Color bgWhite = Color.decode("#FFFFFF");
        Color textDark = Color.decode("#3C2A21");
        Color textGray = Color.decode("#7D7D7D");
        Color accentMint = Color.decode("#D1E7E5");
        Color btnBrown = Color.decode("#4E342E");
        Color bgStatus = Color.decode("#F1F0D5");
        
        JPanel pnlRoot = new JPanel(new BorderLayout(30, 20));
        pnlRoot.setBackground(bgMain);
        
        setContentPane(pnlRoot);
        pnlRoot.add(createSidebar(), BorderLayout.WEST);
        
        JPanel pnlContent = new JPanel(new BorderLayout(30, 0));
        pnlContent.setOpaque(false);
        pnlContent.setBorder(new EmptyBorder(40, 40, 40, 40));
        // cột bên trái
        JPanel pnlSidebar = new JPanel();
        pnlSidebar.setLayout(new BoxLayout(pnlSidebar, BoxLayout.Y_AXIS));
        pnlSidebar.setOpaque(false);
        pnlSidebar.setPreferredSize(new Dimension(240, 0));

        JLabel lblQuickActions = new JLabel("QUICK ACTIONS");
        lblQuickActions.setFont(new Font("Inter", Font.BOLD, 12));
        lblQuickActions.setForeground(textGray);
        pnlSidebar.add(lblQuickActions);
        pnlSidebar.add(Box.createRigidArea(new Dimension(0, 15)));

        // Nút Print
        JButton btnPrint = new JButton("Print Receipt");
        btnPrint.setBackground(btnBrown);
        btnPrint.setForeground(Color.WHITE);
        btnPrint.setFont(new Font("Inter", Font.BOLD, 13));
        btnPrint.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btnPrint.putClientProperty(FlatClientProperties.STYLE, "arc: 15; borderWidth: 0; focusWidth: 0");
        pnlSidebar.add(btnPrint);
        pnlSidebar.add(Box.createRigidArea(new Dimension(0, 10)));

        // Nút Email
        JButton btnEmail = new JButton("Email Invoice");
        btnEmail.setBackground(accentMint);
        btnEmail.setForeground(textDark);
        btnEmail.setFont(new Font("Inter", Font.BOLD, 13));
        btnEmail.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btnEmail.putClientProperty(FlatClientProperties.STYLE, "arc: 15; borderWidth: 0; focusWidth: 0");
        pnlSidebar.add(btnEmail);
        pnlSidebar.add(Box.createRigidArea(new Dimension(0, 10)));

        // Nút Export
        JButton btnExport = new JButton("Export PDF");
        btnExport.setBackground(bgWhite);
        btnExport.setForeground(textDark);
        btnExport.setFont(new Font("Inter", Font.BOLD, 13));
        btnExport.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btnExport.putClientProperty(FlatClientProperties.STYLE, "arc: 15; outlineColor: #E0E0E0; borderWidth: 1; focusWidth: 0");
        pnlSidebar.add(btnExport);

        // cột bên phải
        JPanel pnlInvoiceCard = new JPanel(new BorderLayout());
        pnlInvoiceCard.setBackground(bgWhite);
        pnlInvoiceCard.putClientProperty(FlatClientProperties.STYLE, "arc: 45");
        pnlInvoiceCard.setBorder(new EmptyBorder(50, 50, 50, 50));

        // Header của Invoice
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setOpaque(false);
        
        JLabel lblBrandName = new JLabel("ESPRESSO LOGIC");
        lblBrandName.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblBrandName.setForeground(textDark);
        pnlHeader.add(lblBrandName, BorderLayout.WEST);
        
        JPanel pnlInfoRight = new JPanel();
        pnlInfoRight.setLayout(new BoxLayout(pnlInfoRight, BoxLayout.Y_AXIS));
        pnlInfoRight.setOpaque(false);
        
        JLabel lblIdTitle = new JLabel("TRANSACTION ID");
        lblIdTitle.setForeground(textGray);
        lblIdTitle.setAlignmentX(Component.RIGHT_ALIGNMENT);
        
        JLabel lblIdValue = new JLabel("#ORD-2024-0088242");
        lblIdValue.setFont(new Font("Inter", Font.BOLD, 14));
        lblIdValue.setAlignmentX(Component.RIGHT_ALIGNMENT);
        
        JLabel lblDate = new JLabel("October 24, 2024 — 10:42 AM");
        lblDate.setForeground(textGray);
        lblDate.setAlignmentX(Component.RIGHT_ALIGNMENT);
        
        pnlInfoRight.add(lblIdTitle);
        pnlInfoRight.add(lblIdValue);
        pnlInfoRight.add(lblDate);
        pnlHeader.add(pnlInfoRight, BorderLayout.EAST);

        // Bảng dữ liệu sản phẩm
        String[] columns = {"DESCRIPTION", "QTY", "UNIT", "AMOUNT"};
        Object[][] data = {
            {new String[]{"Single Origin Ethiopia Yirgacheffe", "Pour-over, light roast, floral notes"}, "01", "$6.50", "$6.50"},
            {new String[]{"Oat Milk Lavender Latte", "Double shot, house-made syrup"}, "02", "$5.75", "$11.50"},
            {new String[]{"Artisan Sourdough Croissant", "Twice baked, almond filling"}, "01", "$4.50", "$4.50"}
        };
       
		JTable tblTable = new JTable(new DefaultTableModel(data, columns) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        });
        tblTable.setRowHeight(80);
        tblTable.setShowGrid(false);
        tblTable.setIntercellSpacing(new Dimension(0, 0));
        tblTable.getTableHeader().setBackground(bgWhite);
        tblTable.getTableHeader().setFont(new Font("Inter", Font.BOLD, 11));
        tblTable.getTableHeader().setForeground(textGray);
        tblTable.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        // Renderer cho cột Description (Sử dụng class con ở dưới)
        tblTable.getColumnModel().getColumn(0).setCellRenderer(new DescriptionCellRenderer(textDark, textGray));
        tblTable.getColumnModel().getColumn(0).setPreferredWidth(400);

        // Căn lề phải cho cột Qty, Unit, Amount
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        for(int i=1; i<4; i++) tblTable.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);

        JScrollPane scrPane = new JScrollPane(tblTable);
        scrPane.setBorder(new EmptyBorder(30, 0, 30, 0));
        scrPane.getViewport().setBackground(bgWhite);

        // Footer tính tiền
        JPanel pnlFooterInvoice = new JPanel(new GridBagLayout());
        pnlFooterInvoice.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.weightx = 1.0; gbc.anchor = GridBagConstraints.EAST; gbc.insets = new Insets(4, 0, 4, 0);

        // Các dòng Subtotal, Fee, Tax
        String[][] priceLines = {{"Subtotal", "$22.50"}, {"Service Fee (15%)", "$3.37"}, {"Tax (8.5%)", "$1.91"}};
        for (int i = 0; i < priceLines.length; i++) {
            JPanel line = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
            line.setOpaque(false);
            JLabel t = new JLabel(priceLines[i][0]); t.setForeground(textGray);
            JLabel v = new JLabel(priceLines[i][1]); v.setFont(new Font("Inter", Font.BOLD, 13));
            line.add(t); line.add(v);
            gbc.gridy = i;
            pnlFooterInvoice.add(line, gbc);
        }
        
        // Dòng Total
        gbc.gridy = 3; gbc.insets = new Insets(25, 0, 15, 0);
        JLabel lblTotal = new JLabel("TOTAL   $27.78");
        lblTotal.setFont(new Font("Inter", Font.BOLD, 32));
        lblTotal.setForeground(textDark);
        pnlFooterInvoice.add(lblTotal, gbc);

        pnlInvoiceCard.add(pnlHeader, BorderLayout.NORTH);
        pnlInvoiceCard.add(scrPane, BorderLayout.CENTER);
        pnlInvoiceCard.add(pnlFooterInvoice, BorderLayout.SOUTH);

        // thanh trạng thái
        JPanel pnlBottomBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        pnlBottomBar.setOpaque(false);

        // Box 1: Preparation Time
        JPanel boxPrep = new JPanel(new BorderLayout(5, 2));
        boxPrep.setBackground(bgStatus);
        boxPrep.setBorder(new EmptyBorder(15, 20, 15, 20));
        boxPrep.putClientProperty(FlatClientProperties.STYLE, "arc: 20");
        JLabel t1 = new JLabel("Preparation Time"); t1.setFont(new Font("Inter", Font.BOLD, 13));
        JLabel d1 = new JLabel("Order was fulfilled in 8 minutes and 24 seconds."); d1.setForeground(Color.DARK_GRAY);
        boxPrep.add(t1, BorderLayout.NORTH);
        boxPrep.add(d1, BorderLayout.CENTER);

        // Box 2: Barista on Duty
        JPanel boxBarista = new JPanel(new BorderLayout(5, 2));
        boxBarista.setBackground(bgStatus);
        boxBarista.setBorder(new EmptyBorder(15, 20, 15, 20));
        boxBarista.putClientProperty(FlatClientProperties.STYLE, "arc: 20");
        JLabel t2 = new JLabel("Barista on Duty"); t2.setFont(new Font("Inter", Font.BOLD, 13));
        JLabel d2 = new JLabel("Prepared by Senior Sommelier: Marcus Chen"); d2.setForeground(Color.DARK_GRAY);
        boxBarista.add(t2, BorderLayout.NORTH);
        boxBarista.add(d2, BorderLayout.CENTER);

        pnlBottomBar.add(boxPrep);
        pnlBottomBar.add(boxBarista);

        
        pnlContent.add(pnlSidebar, BorderLayout.WEST);
        pnlContent.add(pnlInvoiceCard, BorderLayout.CENTER);
        pnlContent.add(pnlBottomBar, BorderLayout.SOUTH);
        pnlRoot.add(pnlContent, BorderLayout.CENTER);
    }

    // Tùy chỉnh hiển thị JTable
	class DescriptionCellRenderer extends JPanel implements TableCellRenderer {
        private JLabel lblTitle = new JLabel();
        private JLabel lblDesc = new JLabel();

        public DescriptionCellRenderer(Color dark, Color gray) {
            setLayout(new GridLayout(2, 1, 0, 0));
            setOpaque(true);
            setBorder(new EmptyBorder(10, 10, 10, 10));
            lblTitle.setFont(new Font("Inter", Font.BOLD, 14));
            lblTitle.setForeground(dark);
            lblDesc.setFont(new Font("Inter", Font.ITALIC, 12));
            lblDesc.setForeground(gray);
            add(lblTitle);
            add(lblDesc);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof String[]) {
                String[] val = (String[]) value;
                lblTitle.setText(val[0]);
                lblDesc.setText(val[1]);
            }
            setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
            return this;
        }
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
}