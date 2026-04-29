package app;

import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

@SuppressWarnings("serial")
public class Invoice_UI extends JFrame {

    public Invoice_UI() {
        setTitle("Order Details - Pure Flat Java");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 850);
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
        pnlRoot.setBorder(new EmptyBorder(40, 40, 40, 40));
        setContentPane(pnlRoot);

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

        
        pnlRoot.add(pnlSidebar, BorderLayout.WEST);
        pnlRoot.add(pnlInvoiceCard, BorderLayout.CENTER);
        pnlRoot.add(pnlBottomBar, BorderLayout.SOUTH);
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
}