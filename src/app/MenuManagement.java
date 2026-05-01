package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Menu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import com.formdev.flatlaf.FlatClientProperties;

import dao.Mon_DAO;
import entity.Mon;

@SuppressWarnings("serial")
public class MenuManagement extends JFrame {
	private DefaultTableModel model;
	private Mon_DAO monDao = new Mon_DAO();
    public MenuManagement() {
        setTitle("Espresso Logic - Menu Management");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        add(createSidebar(), BorderLayout.WEST);
        add(createMainContent(), BorderLayout.CENTER);

        loadDataToTable();
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

	        // Highlight mục đang chọn ( Menu mangement )
	        if (data[0].equals("Menu Management")) {
	            btn.setBackground(new Color(230, 230, 210));
	            btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
	            btn.putClientProperty(FlatClientProperties.BUTTON_TYPE, 10);
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
        btnNewOrder.putClientProperty(FlatClientProperties.STYLE, "arc: 20");
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
   private JPanel createMainContent() {
	    JPanel main = new JPanel(new BorderLayout());
	    main.setBackground(new Color(251, 251, 240));
	    main.setBorder(new EmptyBorder(30, 40, 30, 40));


	    JPanel header = new JPanel(new BorderLayout());
	    header.setOpaque(false);
	    header.setBorder(new EmptyBorder(0, 0, 20, 0));

	    JLabel lblTitle = new JLabel("Quản lý thực đơn");
	    lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
	    lblTitle.setForeground(new Color(51, 51, 51));

	    JButton btnAdd = new JButton("+ Thêm món mới");
	    btnAdd.setBackground(new Color(85, 55, 34));
	    btnAdd.setForeground(Color.WHITE);
	    btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 13));
	    btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
	    btnAdd.putClientProperty(FlatClientProperties.STYLE, "arc: 10");
	    btnAdd.setBorder(new EmptyBorder(8, 15, 8, 15));

	    btnAdd.addActionListener(e -> {
	    	FoodForm foodForm = new FoodForm(this);
	        foodForm.setVisible(true);
	        loadDataToTable();
	    });

	    header.add(lblTitle, BorderLayout.WEST);
	    header.add(btnAdd, BorderLayout.EAST);
	    main.add(header, BorderLayout.NORTH);

	    String[] columns = {"Hình ảnh","Mã", "Tên món", "Loại", "Giá Mua","Giá Bán", "Trạng thái", "Hành động"};
	    Object[][] data = {};

	    model = new DefaultTableModel(data, columns) {
	        @Override
	        public Class<?> getColumnClass(int columnIndex) {

	            if (columnIndex == 0) {
					return Icon.class;
				}
	            return super.getColumnClass(columnIndex);
	        }

	        @Override
	        public boolean isCellEditable(int row, int column) {
	            return column == 7;
	        }
	    };

	    JTable table = new JTable(model);
	    table.setRowHeight(60);
	    setupTableAppearance(table);

	    JScrollPane scroll = new JScrollPane(table);
	    scroll.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 210)));
	    scroll.getViewport().setBackground(Color.WHITE);

	    main.add(scroll, BorderLayout.CENTER);

	    return main;
	}

   private void setupTableAppearance(JTable table) {
	    table.setRowHeight(45);
	    table.setShowVerticalLines(false);
	    table.setGridColor(new Color(240, 240, 230));
	    table.getTableHeader().setReorderingAllowed(false);
	    table.getTableHeader().setBackground(new Color(251, 248, 230));
	    table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

	    // Căn chỉnh độ rộng cột
	    table.getColumnModel().getColumn(0).setPreferredWidth(60);
	    table.getColumnModel().getColumn(1).setPreferredWidth(50);
	    table.getColumnModel().getColumn(2).setPreferredWidth(250);

	    // Render column trạng thái
	    table.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
	        @Override
	        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	            label.setHorizontalAlignment(SwingConstants.CENTER);
	            String status = value.toString();

	            if (status.equals("Còn hàng")) {
	                label.setForeground(new Color(40, 120, 40));
	                label.setText("<html><div style='background: #e6f4ea; padding: 2px 8px; border-radius: 10px;'>Còn hàng</div></html>");
	            } else {
	                label.setForeground(new Color(200, 50, 50));
	                label.setText("<html><div style='background: #fdeaea; padding: 2px 8px; border-radius: 10px;'>Hết hàng</div></html>");
	            }
	            return label;
	        }
	    });
	    
	    TableActionButtonCallBack callback = new TableActionButtonCallBack() {
	        @Override
	        public void onEdit(int row) {
	        	
	            String maMon = table.getValueAt(row, 1).toString();
	            
	         
	            Mon mon = monDao.getMonById(maMon);
	            
	            FoodForm foodForm = new FoodForm(MenuManagement.this, mon);
	            foodForm.fillData(mon);
	            foodForm.setVisible(true);
	            
	            loadDataToTable();
	        }

	        @Override
	        public void onDelete(int row) {
	        	
	            if (table.isEditing()) {
	                table.getCellEditor().stopCellEditing();
	            }
	            int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa?", "Xác nhận", JOptionPane.YES_NO_OPTION);
	            if (confirm == JOptionPane.YES_OPTION) {
	                String maMon = table.getValueAt(row, 1).toString();
	                monDao.deleteMonById(maMon);
	                loadDataToTable();
	                JOptionPane.showMessageDialog(MenuManagement.this,"Đã xóa sản phẩm thành công !");
	            }
	        }
	    };

	    table.getColumnModel().getColumn(7).setCellRenderer(new TableActionRenderer());
	    table.getColumnModel().getColumn(7).setCellEditor(new TableActionEditor(callback));
	}
   public void loadDataToTable() {

	   List<Mon> dsMon = monDao.getAll();
	   
	    // Xóa dữ liệu cũ 
	    model.setRowCount(0);
	    
	    for (Mon m : dsMon) {
	    	String trangThaiMonAn = m.isTrangThai() ?  "Còn hàng" : "Hết hàng";
	    	try {
	    	    java.net.URL url = new java.net.URL(m.getDuongDanAnh());
	    	    Image img = javax.imageio.ImageIO.read(url);
	    	    if (img != null) {
	    	        ImageIcon foodIcon = new ImageIcon(img.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
	    		    model.addRow(new Object[] {
	    		        foodIcon,m.getMaMon(), m.getTenMon(),m.getPhanLoaiMonAn(),m.getDonGiaMua(), m.getDonGiaBan(),  trangThaiMonAn 
	    		    });
	    	    }
	    	} catch (Exception e) {
	    		// default image
	    		ImageIcon foodIcon = new ImageIcon(new ImageIcon("img/flatwhite.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
	    	    model.addRow(new Object[] {
	    	            foodIcon,m.getMaMon(), m.getTenMon(),m.getPhanLoaiMonAn(),m.getDonGiaMua(), m.getDonGiaBan(), trangThaiMonAn
	    	    });

	    	}
	    }
	}
}

interface TableActionButtonCallBack {
    public void onEdit(int row);
    public void onDelete(int row);
}

@SuppressWarnings("serial")
class ActionPanel extends JPanel{
	private JButton btnEdit;
	private JButton btnDel;
 public ActionPanel(TableActionButtonCallBack callback, int row) {
     setLayout(new FlowLayout(FlowLayout.CENTER, 5, 8));
     setOpaque(false);
     btnEdit = new JButton("Sửa");
     btnDel = new JButton("Xóa");

     btnEdit.setFont(new Font("Segoe UI", Font.PLAIN, 12));
     btnEdit.putClientProperty(FlatClientProperties.STYLE, "arc: 5; margin: 2,5,2,5");

     btnDel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
     btnDel.setForeground(new Color(180, 50, 50));
     btnDel.putClientProperty(FlatClientProperties.STYLE, "arc: 5; margin: 2,5,2,5");
     
     btnEdit.addActionListener(e -> callback.onEdit(row)); 
     btnDel.addActionListener(e -> callback.onDelete(row));

     add(btnEdit);
     add(btnDel);
 }
}

@SuppressWarnings("serial")
class TableActionRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    	
        ActionPanel panel = new ActionPanel(new TableActionButtonCallBack() {
            @Override public void onEdit(int row) {} 
            @Override public void onDelete(int row) {}
        }, row);
        
        if (isSelected) panel.setBackground(table.getSelectionBackground());
        else panel.setBackground(table.getBackground());
        
        return panel;
    }
}
@SuppressWarnings("serial")
class TableActionEditor extends AbstractCellEditor implements TableCellEditor {
    private TableActionButtonCallBack callback;

    public TableActionEditor(TableActionButtonCallBack callback) {
        this.callback = callback;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        ActionPanel panel = new ActionPanel(callback, row);
        panel.setBackground(table.getSelectionBackground());
        return panel;
    }

    @Override
    public Object getCellEditorValue() { 
    	return null; 
    }
}