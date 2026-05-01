package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.formdev.flatlaf.FlatClientProperties;

import dao.Cloudinary_DAO;
import dao.Mon_DAO;
import entity.Mon;
import entity.PhanLoaiMonAn;

@SuppressWarnings("serial")
public class FoodForm extends JDialog implements ActionListener{
    private JTextField txtMa;
    private JTextField txtTen;
    private JTextField txtGiaMua;
    private JTextField txtGiaBan;
    private JComboBox<PhanLoaiMonAn> cbLoai;
    private JRadioButton radConHang;
    private JRadioButton radHetHang;
    private JLabel lblImagePreview;
    private String selectedImagePath = "";
    private JButton btnChooseImage;
    private JButton btnSave;
    private JButton btnCancel;
    private boolean isEditMode;
    private Mon_DAO monDao = new Mon_DAO();
    private Cloudinary_DAO cloudDao = new Cloudinary_DAO();

    public FoodForm(Frame parent) {
        super(parent, "Thêm món mới", true);
        this.isEditMode = false;
        initUI();
    }

    public FoodForm(Frame parent, Mon mon) {
        super(parent, "Chỉnh sửa món ăn", true);
        this.isEditMode = true;
        initUI();
        fillData(mon);
    }

    private void initUI() {
        setSize(500, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- Header ---
        JPanel pnlHeader = new JPanel();
        pnlHeader.setBackground(new Color(85, 55, 34));
        JLabel lblHeader = new JLabel(isEditMode ? "CẬP NHẬT MÓN ĂN" : "THÊM MÓN MỚI");
        lblHeader.setForeground(Color.WHITE);
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 18));
        pnlHeader.setBorder(new EmptyBorder(15, 0, 15, 0));
        pnlHeader.add(lblHeader);
        add(pnlHeader, BorderLayout.NORTH);

        // --- Form Content ---
        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBackground(new Color(251, 251, 240));
        pnlForm.setBorder(new EmptyBorder(10, 30, 10, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);

        String fieldStyle = "arc: 10; focusColor: #553722";

        txtMa = new JTextField();
        txtMa.putClientProperty(FlatClientProperties.STYLE, fieldStyle);
        if (isEditMode) {
			txtMa.setEditable(false);
		}

        txtTen = new JTextField();
        txtTen.putClientProperty(FlatClientProperties.STYLE, fieldStyle);

        cbLoai = new JComboBox<>(PhanLoaiMonAn.values());
        cbLoai.putClientProperty(FlatClientProperties.STYLE, "arc: 10");

        txtGiaMua = new JTextField();
        txtGiaMua.putClientProperty(FlatClientProperties.STYLE, fieldStyle);
        txtGiaBan = new JTextField();
        txtGiaBan.putClientProperty(FlatClientProperties.STYLE, fieldStyle);

        JPanel pnlStatus = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        pnlStatus.setOpaque(false);
        radConHang = new JRadioButton("Còn hàng", true);
        radHetHang = new JRadioButton("Hết hàng");
        ButtonGroup groupStatus = new ButtonGroup();
        groupStatus.add(radConHang);
        groupStatus.add(radHetHang);
        pnlStatus.add(radConHang);
        pnlStatus.add(radHetHang);

        btnChooseImage = new JButton("Chọn ảnh");
        btnChooseImage.putClientProperty(FlatClientProperties.STYLE, "arc: 10");
        lblImagePreview = new JLabel("Chưa có ảnh", SwingConstants.CENTER);
        lblImagePreview.setPreferredSize(new Dimension(100, 100));
        lblImagePreview.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        addLabelAndField(pnlForm, "Mã món:", txtMa, gbc, 0);
        addLabelAndField(pnlForm, "Tên món:", txtTen, gbc, 1);
        addLabelAndField(pnlForm, "Loại:", cbLoai, gbc, 2);
        addLabelAndField(pnlForm, "Giá mua:", txtGiaMua, gbc, 3);
        addLabelAndField(pnlForm, "Giá bán:", txtGiaBan, gbc, 4);
        addLabelAndField(pnlForm, "Trạng thái:", pnlStatus, gbc, 5);

        gbc.gridy = 6; gbc.gridx = 0; gbc.weightx = 0;
        pnlForm.add(new JLabel("Hình ảnh:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; gbc.insets = new Insets(5, 15, 5, 0);
        JPanel pnlImgAction = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlImgAction.setOpaque(false);
        pnlImgAction.add(btnChooseImage);
        pnlImgAction.add(lblImagePreview);
        pnlForm.add(pnlImgAction, gbc);

        add(pnlForm, BorderLayout.CENTER);

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 20));
        pnlButtons.setBackground(new Color(251, 251, 240));
        btnCancel = new JButton("Hủy bỏ");
        btnSave = new JButton(isEditMode ? "Lưu thay đổi" : "Thêm ngay");
        btnSave.setBackground(new Color(85, 55, 34));
        btnSave.setForeground(Color.WHITE);
        btnSave.putClientProperty(FlatClientProperties.STYLE, "arc: 10");

        pnlButtons.add(btnCancel);
        pnlButtons.add(btnSave);
        add(pnlButtons, BorderLayout.SOUTH);


        btnSave.addActionListener(this);
        btnCancel.addActionListener(this);
        btnChooseImage.addActionListener(this);
    }

    private void addLabelAndField(JPanel panel, String labelText, JComponent field, GridBagConstraints gbc, int row) {
        gbc.gridy = row; gbc.gridx = 0; gbc.weightx = 0;
        panel.add(new JLabel(labelText), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; gbc.insets = new Insets(5, 15, 5, 0);
        panel.add(field, gbc);
        gbc.insets = new Insets(5, 0, 5, 0);
    }
    
    private ImageIcon createImageFromLocalPath(String imgPath) {
    	ImageIcon icon = new ImageIcon(new ImageIcon(imgPath).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
    	return icon;
    }

    private void chooseImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Hình ảnh (jpg, png, gif)", "jpg", "png", "gif"));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            selectedImagePath = selectedFile.getAbsolutePath();

            ImageIcon icon = createImageFromLocalPath(selectedImagePath);
            lblImagePreview.setIcon(icon);
            lblImagePreview.setText("");
        }
    }
    
    private void displayDefaultImagePreview() {
    	ImageIcon icon = new ImageIcon(new ImageIcon("img/flatwhite.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
    	lblImagePreview.setIcon(icon);
        lblImagePreview.setText(""); 
    }

    public void fillData(Mon mon) {
        txtMa.setText(mon.getMaMon());
        txtTen.setText(mon.getTenMon());
        cbLoai.setSelectedItem(mon.getPhanLoaiMonAn());
        txtGiaMua.setText(String.valueOf(mon.getDonGiaMua()));
        txtGiaBan.setText(String.valueOf(mon.getDonGiaBan()));
        this.selectedImagePath = mon.getDuongDanAnh(); 
        
        if(mon.isTrangThai()) 
        	radConHang.setSelected(true);
        else
        	radHetHang.setSelected(true);
        
        if (selectedImagePath != null && !selectedImagePath.isEmpty()) {
            new Thread(() -> {
                try {
                    java.net.URL url = new java.net.URL(selectedImagePath);
                    Image img = javax.imageio.ImageIO.read(url);
                    if (img != null) {
                        ImageIcon icon = new ImageIcon(img.getScaledInstance(
                            lblImagePreview.getWidth(), 
                            lblImagePreview.getHeight(), 
                            Image.SCALE_SMOOTH)
                        );
                        
                        // update UI 
                        javax.swing.SwingUtilities.invokeLater(() -> {
                            lblImagePreview.setIcon(icon);
                            lblImagePreview.setText(""); 
                        });
                    }
                } catch (Exception e) {
                    System.err.println("Lỗi load ảnh: " + e.getMessage());
                    displayDefaultImagePreview();
                }
            }).start();
        } else {
        	displayDefaultImagePreview();
        }
    }

    private void handleSave() {
    	String maMon = txtMa.getText();
    	String tenMon = txtTen.getText();
    	double giaMua = Double.parseDouble(txtGiaMua.getText());
    	double giaBan = Double.parseDouble(txtGiaBan.getText());
    	PhanLoaiMonAn loaiMon = (PhanLoaiMonAn) cbLoai.getSelectedItem();
    	boolean trangThai = radConHang.isSelected();
	
    	if(!isEditMode) {
    		String finalImageUrl = "";
    	
    		if (selectedImagePath != null && !selectedImagePath.isEmpty()) 
                finalImageUrl = cloudDao.uploadImage(selectedImagePath);
            
        	
        	if (finalImageUrl != null) {
            	Mon monMoi = new Mon(maMon, tenMon, giaMua, giaBan, trangThai, loaiMon, "description",finalImageUrl);
            	monDao.addMon(monMoi);
            	JOptionPane.showMessageDialog(this, "Thêm món thành công!");
                dispose();
            } 
        	else 
                JOptionPane.showMessageDialog(this, "Lỗi khi tải ảnh lên server!");
            
    	}
    	else {
    	    String urlToSave = "";
    	    
    	    if (selectedImagePath != null && !selectedImagePath.startsWith("http") && !selectedImagePath.isEmpty()) {
    	        btnSave.setText("Đang tải ảnh mới...");
    	        urlToSave = cloudDao.uploadImage(selectedImagePath);
    	        
    	        if (urlToSave == null) {
    	            JOptionPane.showMessageDialog(this, "Lỗi khi tải ảnh mới lên server!");
    	            return; 
    	        }
    	    } 
    	    else 
    	        urlToSave = selectedImagePath;
    	    

    	    Mon monCapNhat = new Mon(maMon, tenMon, giaMua, giaBan, trangThai, loaiMon, "description", urlToSave);
    	    
    	    boolean isUpdated = monDao.updateMon(monCapNhat);

    	    if (isUpdated) {
    	        JOptionPane.showMessageDialog(this, "Cập nhật món thành công!");
    	        dispose();
    	    } 
    	    else 
    	        JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật dữ liệu!");
    	    
    	}
    }

    private boolean isValidated() {
    	boolean isSuccess = true;
    	return isSuccess;
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		Object event = e.getSource();
		if(event.equals(btnSave)) {
			if(isValidated()) {
				handleSave();
			}
		}
		else if(event.equals(btnCancel)) {
			dispose();
		}
		else if(event.equals(btnChooseImage)) {
			chooseImage();
		}
	}
}