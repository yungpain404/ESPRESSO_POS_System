package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class LoginForm extends JFrame implements ActionListener{

    private JPasswordField txtPass;
	private JTextField txtUser;
	private JButton btnLogin;
	private JLabel lblErrName;

	public LoginForm() {
        setTitle("Cafe POS Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);
        //Main layout
        setLayout(new BorderLayout());

        //Left Panel
        JPanel pnlLeft = new JPanel();
        pnlLeft.setPreferredSize(new Dimension(360, 0));
        pnlLeft.setBackground(new Color(85, 55, 34)); 
        pnlLeft.setLayout(new BoxLayout(pnlLeft, BoxLayout.Y_AXIS));

        pnlLeft.add(Box.createVerticalGlue());

        JPanel pnlLogoCircle = new JPanel(new BorderLayout());
        pnlLogoCircle.setMaximumSize(new Dimension(88, 88));
        pnlLogoCircle.setPreferredSize(new Dimension(88, 88));
        pnlLogoCircle.setBackground(new Color(107, 69, 48));
        pnlLogoCircle.setBorder(BorderFactory.createLineBorder(new Color(122, 80, 64), 1, true));

        ImageIcon cafeIcon = new ImageIcon("img/latte.png");
        java.awt.Image scaleLogo = cafeIcon.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
        JLabel lblLogoIcon = new JLabel(new ImageIcon(scaleLogo));
        lblLogoIcon.setHorizontalAlignment(JLabel.CENTER);
        pnlLogoCircle.add(lblLogoIcon, BorderLayout.CENTER);
        pnlLogoCircle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTitle = new JLabel("COFFEE POS");
        lblTitle.setForeground(new Color(255, 245, 236));
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSubTitle = new JLabel("Hệ thống quản lý quán cà phê");
        lblSubTitle.setForeground(new Color(160, 128, 112));
        lblSubTitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSubTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        pnlLeft.add(pnlLogoCircle);
        pnlLeft.add(Box.createRigidArea(new Dimension(0, 20)));
        pnlLeft.add(lblTitle);
        pnlLeft.add(lblSubTitle);
        pnlLeft.add(Box.createRigidArea(new Dimension(0, 100)));

        JLabel lblVersion = new JLabel("v1.0.0 2026");
        lblVersion.setForeground(new Color(160, 128, 112));
        lblVersion.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblVersion.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlLeft.add(lblVersion);

        pnlLeft.add(Box.createVerticalGlue());

        // Right panel
        JPanel pnlRight = new JPanel();
        pnlRight.setBackground(new Color(251, 251, 226));
        pnlRight.setLayout(new BoxLayout(pnlRight, BoxLayout.Y_AXIS));

        JPanel pnlCenterWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        pnlCenterWrapper.setOpaque(false);

        JPanel pnlLoginCard = new JPanel();
        pnlLoginCard.setPreferredSize(new Dimension(320, 320));
        pnlLoginCard.setBackground(new Color(255, 248, 232));
        pnlLoginCard.setLayout(new BorderLayout());
        pnlLoginCard.setBorder(BorderFactory.createLineBorder(new Color(200, 184, 154), 1, true));

        JPanel pnlHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 15));
        pnlHeader.setBackground(new Color(85, 55, 34));

        JPanel pnlHeaderIcon = new JPanel(new BorderLayout());
        pnlHeaderIcon.setPreferredSize(new Dimension(32, 32));
        pnlHeaderIcon.setBackground(new Color(107, 69, 48));
        pnlHeaderIcon.setBorder(BorderFactory.createLineBorder(new Color(122, 80, 64), 1, true));
        ImageIcon iconLock = new ImageIcon("img/lock.png");
        java.awt.Image scaleLock = iconLock.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
        JLabel lblHeaderIcon = new JLabel(new ImageIcon(scaleLock));
        lblHeaderIcon.setHorizontalAlignment(JLabel.CENTER);
        pnlHeaderIcon.add(lblHeaderIcon, BorderLayout.CENTER);

        JPanel pnlHeaderText = new JPanel();
        pnlHeaderText.setLayout(new BoxLayout(pnlHeaderText, BoxLayout.Y_AXIS));
        pnlHeaderText.setOpaque(false);

        JLabel lblHeaderTitle = new JLabel("Đăng nhập hệ thống");
        lblHeaderTitle.setForeground(new Color(255, 245, 236));
        lblHeaderTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel lblHeaderSub = new JLabel("Vui lòng xác thực tài khoản");
        lblHeaderSub.setForeground(new Color(160, 128, 112));
        lblHeaderSub.setFont(new Font("Segoe UI", Font.PLAIN, 11));

        pnlHeaderText.add(lblHeaderTitle);
        pnlHeaderText.add(lblHeaderSub);

        pnlHeader.add(pnlHeaderIcon);
        pnlHeader.add(pnlHeaderText);

        JPanel pnlBody = new JPanel();
        pnlBody.setLayout(new BoxLayout(pnlBody, BoxLayout.Y_AXIS));
        pnlBody.setBackground(new Color(255, 248, 232));
        pnlBody.setBorder(new EmptyBorder(22, 22, 22, 22));

        JLabel lblUser = new JLabel("TÊN ĐĂNG NHẬP");
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblUser.setForeground(new Color(92, 64, 48));
        lblUser.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel pnlUserField = new JPanel(new BorderLayout(5, 0));
        pnlUserField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        pnlUserField.setBackground(new Color(255, 254, 245));
        pnlUserField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 184, 154), 1, true),
                new EmptyBorder(0, 5, 0, 5)
        ));

        ImageIcon iconUser = new ImageIcon("img/user.png");
        java.awt.Image scaleUser = iconUser.getImage().getScaledInstance(22, 20, java.awt.Image.SCALE_DEFAULT);
        JLabel lblUserIcon = new JLabel(new ImageIcon(scaleUser));
        txtUser = new JTextField();
        txtUser.setBorder(null);
        txtUser.setBackground(new Color(255, 254, 245));
        pnlUserField.add(lblUserIcon, BorderLayout.WEST);
        pnlUserField.add(txtUser, BorderLayout.CENTER);
        pnlUserField.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblErrName = new JLabel();
        lblErrName.setForeground(new Color(222, 93, 75));
        lblErrName.setFont(new Font("Segoe UI", Font.ITALIC, 10));
        

        JLabel lblPass = new JLabel("MẬT KHẨU");
        lblPass.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblPass.setForeground(new Color(92, 64, 48));
        lblPass.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel pnlPassField = new JPanel(new BorderLayout(5, 0));
        pnlPassField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        pnlPassField.setBackground(new Color(255, 254, 245));
        pnlPassField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 184, 154), 1, true),
                new EmptyBorder(0, 5, 0, 5)
        ));

        ImageIcon iconPass = new ImageIcon("img/unlock.png");
        java.awt.Image scalePass = iconPass.getImage().getScaledInstance(23, 20, java.awt.Image.SCALE_DEFAULT);
        JLabel lblPassIcon = new JLabel(new ImageIcon(scalePass));
        txtPass = new JPasswordField();
        txtPass.setBorder(null);
        txtPass.setBackground(new Color(255, 254, 245));
        JLabel lblEyeIcon = new JLabel(new ImageIcon("img/eye_icon.png")); 
        
        pnlPassField.add(lblPassIcon, BorderLayout.WEST);
        pnlPassField.add(txtPass, BorderLayout.CENTER);
        pnlPassField.add(lblEyeIcon, BorderLayout.EAST);
        pnlPassField.setAlignmentX(Component.LEFT_ALIGNMENT);

        btnLogin = new JButton("Đăng nhập");
        btnLogin.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        btnLogin.setBackground(new Color(85, 55, 34));
        btnLogin.setForeground(new Color(255, 245, 236));
        btnLogin.setFocusPainted(false);
        btnLogin.setBorder(BorderFactory.createLineBorder(new Color(85, 55, 34), 1, true));
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnLogin.setAlignmentX(Component.LEFT_ALIGNMENT);

        pnlBody.add(lblUser);
        pnlBody.add(Box.createRigidArea(new Dimension(0, 5)));
        pnlBody.add(pnlUserField);
        pnlBody.add(lblErrName);
        pnlBody.add(Box.createRigidArea(new Dimension(0, 14)));
        pnlBody.add(lblPass);
        pnlBody.add(Box.createRigidArea(new Dimension(0, 5)));
        pnlBody.add(pnlPassField);
        pnlBody.add(Box.createRigidArea(new Dimension(0, 30)));
        pnlBody.add(btnLogin);

        pnlLoginCard.add(pnlHeader, BorderLayout.NORTH);
        pnlLoginCard.add(pnlBody, BorderLayout.CENTER);

        pnlCenterWrapper.add(pnlLoginCard);
        
        pnlRight.add(Box.createVerticalGlue());
        pnlRight.add(pnlCenterWrapper);
        pnlRight.add(Box.createVerticalGlue());
        //Event
        btnLogin.addActionListener(this);
        
        //Display
        add(pnlLeft, BorderLayout.WEST);
        add(pnlRight, BorderLayout.CENTER);
        //Mnemonic
        this.getRootPane().setDefaultButton(btnLogin);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnLogin)) {
			if (validInput()) {
				CreateOrders_UI nextFrame = new CreateOrders_UI();
				nextFrame.setBounds(this.getBounds());
				nextFrame.setExtendedState(this.getExtendedState());
				nextFrame.setVisible(true);
				this.dispose();
			}
		}
		
	}
	
	private boolean validInput() {
		String loginName = txtUser.getText().trim();
		String loginPass = new String(txtPass.getPassword()).trim();
		if (loginName.length() == 0 || loginPass.length() == 0) {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu.");
			if (loginName.length() == 0) {
				txtUser.requestFocus();
			}else {
				txtPass.requestFocus();
			}
			return false;
		}
		if (loginName.length() < 8) {
			lblErrName.setText("Username phải ≥ 8 ký tự.");
			txtUser.requestFocus();
			return false;
		}else {
			if (!loginName.matches("^[a-zA-Z][a-zA-Z0-9_]{7,29}$")) {
				lblErrName.setText("Usename bắt đầu bằng chữ cái, không chứa kí tự đặc biệt.");
				txtUser.requestFocus();
				return false;
			}else {
				lblErrName.setText("");
			}
		}
		return true;
	}
}