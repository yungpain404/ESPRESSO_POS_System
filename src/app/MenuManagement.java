package app;

import com.formdev.flatlaf.FlatClientProperties;

import dao.Mon_DAO;
import entity.Mon;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class MenuManagement extends JFrame {
	private DefaultTableModel model;
	private Mon_DAO monDao = new Mon_DAO();
    public MenuManagement() {
        setTitle("Espresso Logic - Menu Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
	        FoodForm form = new FoodForm(this);
	        form.setVisible(true);
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
	            
	            if (columnIndex == 0) return Icon.class;
	            return super.getColumnClass(columnIndex);
	        }

	        @Override
	        public boolean isCellEditable(int row, int column) {
	            return column == 6;
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
	    
	    // Renderer cho cột "Trạng thái" (Badge style)
	    table.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
	        @Override
	        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	            label.setHorizontalAlignment(JLabel.CENTER);
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

	    // Renderer cho cột "Hành động" (Nút Sửa/Xóa)
	    table.getColumnModel().getColumn(7).setCellRenderer(new TableActionRenderer());
	    table.getColumnModel().getColumn(7).setCellEditor(new TableActionEditor(new TableActionEvent() {
	        @Override
	        public void onEdit(int row) {
	            // 1. Lấy dữ liệu từ model dựa trên dòng (row)
	            String maMon = model.getValueAt(row, 1).toString();	
	            
	            // 2. Mở Form Sửa và truyền dữ liệu vào
	            // Mon mon = monDao.getById(maMon);
	            // FormMon form = new FormMon(mon); 
	            // form.setVisible(true);
	            System.out.println("Đang sửa món: " + maMon);
	        }

	        @Override
	        public void onDelete(int row) {
	            int confirm = JOptionPane.showConfirmDialog(null, 
	                "Bạn có chắc chắn muốn xóa món này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
	            
	            if (confirm == JOptionPane.YES_OPTION) {
	                // Gọi DAO xóa và load lại bảng
	                // monDao.delete(maMon);
	                // loadDataToTable();
	            }
	        }
	    }));
	}
   public void loadDataToTable() {
	   
	   List<Mon> dsMon = monDao.getAll();
	   
	    // 2. Xóa dữ liệu cũ trên bảng (DefaultTableModel)
	    model.setRowCount(0); 
	    
	    // 3. Đổ dữ liệu mới vào
	    for (Mon m : dsMon) {
	    	ImageIcon foodIcon = new ImageIcon(new ImageIcon("img/flatwhite.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
	        model.addRow(new Object[] {
	            foodIcon,m.getMaMon(), m.getTenMon(),m.getPhanLoaiMonAn(),m.getDonGiaMua(), m.getDonGiaBan(), "Còn hàng" // Thay bằng các getter của bạn
	        });
	    }
	}
}

interface TableActionEvent {
    public void onEdit(int row);
    public void onDelete(int row);
}

@SuppressWarnings("serial")
class ActionPanel extends JPanel {
 public ActionPanel() {
     setLayout(new FlowLayout(FlowLayout.CENTER, 5, 8));
     setOpaque(false);
     JButton btnEdit = new JButton("Sửa");
     JButton btnDel = new JButton("Xóa");
     
     // Style cho nút Sửa
     btnEdit.setFont(new Font("Segoe UI", Font.PLAIN, 12));
     btnEdit.putClientProperty(FlatClientProperties.STYLE, "arc: 5; margin: 2,5,2,5");
     
     // Style cho nút Xóa
     btnDel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
     btnDel.setForeground(new Color(180, 50, 50));
     btnDel.putClientProperty(FlatClientProperties.STYLE, "arc: 5; margin: 2,5,2,5");

     add(btnEdit);
     add(btnDel);
 }
 public ActionPanel(TableActionEvent event, int row) {
     setLayout(new FlowLayout(FlowLayout.CENTER, 5, 8));
     setOpaque(false);
     JButton btnEdit = new JButton("Sửa");
     JButton btnDel = new JButton("Xóa");

     btnEdit.addActionListener(e -> event.onEdit(row));
     
     btnDel.addActionListener(e -> event.onDelete(row));

     add(btnEdit);
     add(btnDel);
 }
}

// Renderer để hiển thị ActionPanel trong Table
@SuppressWarnings("serial")
class TableActionRenderer extends DefaultTableCellRenderer {
 @Override
 public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
     return new ActionPanel();
 }
}

// Editor để có thể tương tác (click) vào nút trong Table
@SuppressWarnings("serial")
class TableActionEditor extends AbstractCellEditor implements TableCellEditor {
	private TableActionEvent event;
	
    public TableActionEditor(TableActionEvent event) {
        this.event = event;
    }
	 @Override
	 public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
	     return new ActionPanel();
	 }
	 @Override
	 public Object getCellEditorValue() { 
		 return null;
	 }
 
}