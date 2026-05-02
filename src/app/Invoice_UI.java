package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLightLaf;

@SuppressWarnings("serial")
public class Invoice_UI extends JFrame {
	static {
	    try {
	        FlatLightLaf.setup();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	private JLabel lblIdValue;
	private JLabel lblDate;
	private JLabel lblTotal;
	private DefaultTableModel model;
	private JTextArea txtNotes;
		
	public Invoice_UI(entity.HoaDon hd) {
	    this(); 
	    setData(hd); 
	}
	
    public Invoice_UI() {
        setTitle("Order Details - Pure Flat Java");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(Frame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);
        // Bảng màu
        Color bgMain = Color.decode("#F9F8E6");
        Color bgWhite = Color.decode("#FFFFFF");
        Color textDark = Color.decode("#3C2A21");
        Color textGray = Color.decode("#7D7D7D");
        Color accentMint = Color.decode("#D1E7E5");
        Color btnBrown = Color.decode("#4E342E");

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
        pnlSidebar.add(btnPrint);
        pnlSidebar.add(Box.createRigidArea(new Dimension(0, 10)));

        // Nút Email
        JButton btnEmail = new JButton("Email Invoice");
        btnEmail.setBackground(accentMint);
        btnEmail.setForeground(textDark);
        btnEmail.setFont(new Font("Inter", Font.BOLD, 13));
        btnEmail.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        pnlSidebar.add(btnEmail);
        pnlSidebar.add(Box.createRigidArea(new Dimension(0, 10)));

        // Nút Export
        JButton btnExport = new JButton("Export PDF");
        btnExport.setBackground(bgWhite);
        btnExport.setForeground(textDark);
        btnExport.setFont(new Font("Inter", Font.BOLD, 13));
        btnExport.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        pnlSidebar.add(btnExport);

        // cột bên phải
        JPanel pnlInvoiceCard = new JPanel(new BorderLayout());
        pnlInvoiceCard.setBackground(bgWhite);
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
        
        lblIdValue = new JLabel("#ORD-2024-0088242");
        lblIdValue.setFont(new Font("Inter", Font.BOLD, 14));
        lblIdValue.setAlignmentX(Component.RIGHT_ALIGNMENT);
        
        lblDate = new JLabel("October 24, 2024 — 10:42 AM");
        lblDate.setForeground(textGray);
        lblDate.setAlignmentX(Component.RIGHT_ALIGNMENT);

        pnlInfoRight.add(lblIdTitle);
        pnlInfoRight.add(lblIdValue);
        pnlInfoRight.add(lblDate);
        pnlHeader.add(pnlInfoRight, BorderLayout.EAST);

        // Bảng dữ liệu sản phẩm
        String[] columns = {"DESCRIPTION", "QTY", "UNIT", "AMOUNT"};
        model = new DefaultTableModel(null, columns) {
			@Override public boolean isCellEditable(int r, int c) { return false; }
		};
		JTable tblTable = new JTable(model);
        tblTable.setRowHeight(80);
        tblTable.setShowGrid(false);
        tblTable.setIntercellSpacing(new Dimension(0, 0));
        tblTable.getTableHeader().setBackground(bgWhite);
        tblTable.getTableHeader().setFont(new Font("Inter", Font.BOLD, 11));
        tblTable.getTableHeader().setForeground(textGray);
        tblTable.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        tblTable.getColumnModel().getColumn(0).setCellRenderer(new DescriptionCellRenderer(textDark, textGray));
        tblTable.getColumnModel().getColumn(0).setPreferredWidth(400);

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        for(int i=1; i<4; i++) {
			tblTable.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
		}

        JScrollPane scrPane = new JScrollPane(tblTable);
        scrPane.setBorder(new EmptyBorder(30, 0, 30, 0));
        scrPane.getViewport().setBackground(bgWhite);

        // Footer tính tiền
        JPanel pnlFooterInvoice = new JPanel(new GridBagLayout());
        pnlFooterInvoice.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        
//       Dòng note
        JPanel pnlNotes = new JPanel();
        pnlNotes.setBackground(new Color(245, 245, 225));
        pnlNotes.setLayout(new BorderLayout(10, 10));
        pnlNotes.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblNoteTitle = new JLabel("NOTES & OBSERVATIONS");
        lblNoteTitle.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblNoteTitle.setForeground(new Color(100, 90, 70));
        pnlNotes.add(lblNoteTitle, BorderLayout.NORTH);

        // Nội dung ghi chú
        txtNotes = new JTextArea();
        txtNotes.setText("Guest requested extra hot on the lavender latte...");
        txtNotes.setLineWrap(true);
        txtNotes.setWrapStyleWord(true);
        txtNotes.setEditable(false); 
        txtNotes.setBackground(new Color(245, 245, 225)); 
        txtNotes.setFont(new Font("Serif", Font.ITALIC, 14));
        txtNotes.setForeground(new Color(80, 80, 80));
        pnlNotes.add(txtNotes, BorderLayout.CENTER);
        
        gbc.gridx = 0; gbc.gridy = 0;
		gbc.weightx = 0.55; 
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 40);
		pnlFooterInvoice.add(pnlNotes, gbc);

        // Dòng Total
		JPanel pnlPrices = new JPanel();
		pnlPrices.setLayout(new BoxLayout(pnlPrices, BoxLayout.Y_AXIS));
		pnlPrices.setOpaque(false);
		
        lblTotal = new JLabel("TOTAL   $0.0");
        lblTotal.setFont(new Font("Inter", Font.BOLD, 32));
        lblTotal.setForeground(textDark);
        lblTotal.setAlignmentX(Component.RIGHT_ALIGNMENT);
        pnlPrices.add(Box.createVerticalGlue());
		pnlPrices.add(lblTotal);
		gbc.gridx = 1; 
		gbc.weightx = 0.45;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.SOUTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		pnlFooterInvoice.add(pnlPrices, gbc);

        pnlInvoiceCard.add(pnlHeader, BorderLayout.NORTH);
        pnlInvoiceCard.add(scrPane, BorderLayout.CENTER);
        pnlInvoiceCard.add(pnlFooterInvoice, BorderLayout.SOUTH);

        pnlContent.add(pnlSidebar, BorderLayout.WEST);
        pnlContent.add(pnlInvoiceCard, BorderLayout.CENTER);
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
	
	public void setData(entity.HoaDon hd) {
	    lblIdValue.setText("#" + hd.getMaHD());
	    lblDate.setText(hd.getNgayGioLap().toString());

	    model.setRowCount(0);
	    for (entity.ChiTietHoaDon ct : hd.getDsChiTiet()) {
	        model.addRow(new Object[]{
	            
	            new String[]{ 
	                ct.getMon().getTenMon(), 
	                ct.getMon().getMoTaMon() 
	            },
	            String.format("%02d", ct.getSoLuongMon()),
	            String.format("$%.2f", ct.getMon().getDonGiaBan()),
	            String.format("$%.2f", ct.getThanhTien())
	        });
	    }

	    if (!hd.getDsChiTiet().isEmpty()) {
	        txtNotes.setText(hd.getDsChiTiet().get(0).getGhiChuKhachHang());
	    }

	    lblTotal.setText(String.format("TOTAL   $%.2f", hd.getTongTien()));
	}
    private JPanel createSidebar() {
	    JPanel sidebar = new JPanel();
	    sidebar.setPreferredSize(new Dimension(250, 0));
	    sidebar.setBackground(new Color(245, 245, 230));
	    sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
	    sidebar.setBorder(new EmptyBorder(30, 20, 30, 20)); 
	    // Logo
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
	        if (data[0].equals("Menu")) {
                btn.addActionListener(e -> {
                    MenuList_UI nextFrame = new MenuList_UI();
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
        btnNewOrder.addActionListener(e -> {
            new CreateFormOrders_UI().setVisible(true);
            this.dispose();
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
}