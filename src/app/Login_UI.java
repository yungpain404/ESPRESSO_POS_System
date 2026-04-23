package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.*;

public class Login_UI extends JFrame {

    private static final Color CLR_BROWN_DARK   = new Color(0x3B1F10);
    private static final Color CLR_BROWN_BTN    = new Color(0x4A2318);
    private static final Color CLR_BROWN_HOVER  = new Color(0x6B3020);
    private static final Color CLR_CREAM_BG     = new Color(0xF0EBE0);
    private static final Color CLR_TEXT_DARK    = new Color(0x2C1A0E);
    private static final Color CLR_TEXT_MUTED   = new Color(0x9A8A7A);
    private static final Color CLR_ICON_CIRCLE  = new Color(0x5C3320);
    private static final Color CLR_INPUT_BG     = new Color(0xFAF7F2);
    private static final Color CLR_INPUT_BORDER = new Color(0xD8CFBF);
    private static final Color CLR_WHITE        = Color.WHITE;
    private static final Color CLR_CREAM_TEXT   = new Color(0xC4A882);
    private static final Color CLR_LOGO_TEXT    = new Color(0xE8D5C0);
    private static final Color CLR_ICON_MUTED   = new Color(0xE8C9A0);
    private static final Color CLR_BADGE        = new Color(0xC0B8AC);
    private static final Color CLR_BADGE_TEXT   = new Color(0x6A6055);

    public Login_UI() {
        setTitle("Espresso Logic — Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);

        JPanel pnlRoot = new JPanel(new GridBagLayout());
        pnlRoot.setBackground(CLR_CREAM_BG);

        GridBagConstraints gbcLeft = new GridBagConstraints();
        gbcLeft.gridx      = 0;
        gbcLeft.gridy      = 0;
        gbcLeft.weightx    = 0.5;
        gbcLeft.weighty    = 1.0;
        gbcLeft.fill       = GridBagConstraints.BOTH;
        pnlRoot.add(buildLeftPanel(), gbcLeft);

        GridBagConstraints gbcRight = new GridBagConstraints();
        gbcRight.gridx     = 1;
        gbcRight.gridy     = 0;
        gbcRight.weightx   = 0.5;
        gbcRight.weighty   = 1.0;
        gbcRight.fill      = GridBagConstraints.BOTH;
        pnlRoot.add(buildRightPanel(), gbcRight);

        setContentPane(pnlRoot);
    }

    private JPanel buildLeftPanel() {
        JPanel pnlLeft = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                RadialGradientPaint rp = new RadialGradientPaint(
                        new Point2D.Float(0, 0),
                        Math.max(getWidth(), getHeight()) * 0.85f,
                        new float[]{0f, 1f},
                        new Color[]{new Color(0x5C3320), CLR_BROWN_DARK}
                );
                g2.setPaint(rp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        pnlLeft.setOpaque(false);


        JPanel pnlInner = new JPanel(new GridBagLayout());
        pnlInner.setOpaque(false);

        int row = 0;

        JPanel pnlLogo = new JPanel(new GridBagLayout());
        pnlLogo.setOpaque(false);

        GridBagConstraints gbcLogo = new GridBagConstraints();
        gbcLogo.gridx = 0; gbcLogo.gridy = 0;
        gbcLogo.insets = new Insets(0, 0, 0, 10);
        pnlLogo.add(makeIconLabel(makeCoffeeIcon(), 22, 22), gbcLogo);

        JLabel lblLogoText = new JLabel("ESPRESSO LOGIC");
        lblLogoText.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblLogoText.setForeground(CLR_LOGO_TEXT);
        GridBagConstraints gbcLogoTxt = new GridBagConstraints();
        gbcLogoTxt.gridx = 1; gbcLogoTxt.gridy = 0;
        pnlLogo.add(lblLogoText, gbcLogoTxt);

        addToGrid(pnlInner, pnlLogo, 0, row++,
                GridBagConstraints.REMAINDER, 1, 1.0, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 36, 0));

        JLabel lblHeadline = new JLabel(
                "<html><b>The Digital Sommelier<br>for Your Coffee Craft.</html>");
        lblHeadline.setFont(new Font("SansSerif", Font.BOLD, 36));
        lblHeadline.setForeground(CLR_WHITE);
        addToGrid(pnlInner, lblHeadline, 0, row++,
                GridBagConstraints.REMAINDER, 1, 1.0, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 18, 0));

        JLabel lblSubtext = new JLabel(
                "<html>Crafting the perfect brew requires precision.<br>"
                        + "Managing your shop should be just as artisanal.</html>");
        lblSubtext.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblSubtext.setForeground(CLR_CREAM_TEXT);
        addToGrid(pnlInner, lblSubtext, 0, row++,
                GridBagConstraints.REMAINDER, 1, 1.0, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 30, 0));

        addToGrid(pnlInner, buildFeatureCard(makeAnalyticsIcon(),
                        "Real-time Analytics", "Track every pour and sale instantly."),
                0, row++,
                GridBagConstraints.REMAINDER, 1, 1.0, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 14, 0));


        addToGrid(pnlInner, buildFeatureCard(makeInventoryIcon(),
                        "Inventory Logic", "Automated stock alerts for fresh beans."),
                0, row++,
                GridBagConstraints.REMAINDER, 1, 1.0, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0));


        GridBagConstraints gbcSpacer = new GridBagConstraints();
        gbcSpacer.gridx = 0; gbcSpacer.gridy = row;
        gbcSpacer.weighty = 1.0;
        gbcSpacer.fill = GridBagConstraints.VERTICAL;
        pnlInner.add(Box.createVerticalGlue(), gbcSpacer);


        GridBagConstraints gbcInner = new GridBagConstraints();
        gbcInner.gridx   = 0;
        gbcInner.gridy   = 0;
        gbcInner.weightx = 1.0;
        gbcInner.weighty = 1.0;
        gbcInner.fill    = GridBagConstraints.BOTH;
        gbcInner.insets  = new Insets(44, 42, 44, 42);
        gbcInner.anchor  = GridBagConstraints.NORTHWEST;
        pnlLeft.add(pnlInner, gbcInner);

        return pnlLeft;
    }

    private JPanel buildFeatureCard(Icon icon, String title, String desc) {
        JPanel pnlCard = new JPanel(new GridBagLayout());
        pnlCard.setOpaque(false);


        JLabel lblCardIcon = new JLabel(icon) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(CLR_ICON_CIRCLE);
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        lblCardIcon.setPreferredSize(new Dimension(46, 46));
        lblCardIcon.setHorizontalAlignment(SwingConstants.CENTER);
        lblCardIcon.setVerticalAlignment(SwingConstants.CENTER);

        GridBagConstraints gbcIcon = new GridBagConstraints();
        gbcIcon.gridx   = 0;
        gbcIcon.gridy   = 0;
        gbcIcon.gridheight = 2;
        gbcIcon.anchor  = GridBagConstraints.CENTER;
        gbcIcon.insets  = new Insets(0, 0, 0, 14);
        pnlCard.add(lblCardIcon, gbcIcon);


        JLabel lblCardTitle = new JLabel(title);
        lblCardTitle.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblCardTitle.setForeground(CLR_WHITE);
        GridBagConstraints gbcTitle = new GridBagConstraints();
        gbcTitle.gridx   = 1;
        gbcTitle.gridy   = 0;
        gbcTitle.weightx = 1.0;
        gbcTitle.anchor  = GridBagConstraints.SOUTHWEST;
        gbcTitle.fill    = GridBagConstraints.HORIZONTAL;
        pnlCard.add(lblCardTitle, gbcTitle);


        JLabel lblCardDesc = new JLabel(desc);
        lblCardDesc.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblCardDesc.setForeground(CLR_CREAM_TEXT);
        GridBagConstraints gbcDesc = new GridBagConstraints();
        gbcDesc.gridx   = 1;
        gbcDesc.gridy   = 1;
        gbcDesc.weightx = 1.0;
        gbcDesc.anchor  = GridBagConstraints.NORTHWEST;
        gbcDesc.fill    = GridBagConstraints.HORIZONTAL;
        pnlCard.add(lblCardDesc, gbcDesc);

        return pnlCard;
    }

    private JPanel buildRightPanel() {
        JPanel pnlRight = new JPanel(new GridBagLayout());
        pnlRight.setBackground(CLR_CREAM_BG);

        // Form panel holds all form rows, centred in pnlRight
        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setOpaque(false);

        int row = 0;


        JLabel lblWelcome = new JLabel("Welcome Back");
        lblWelcome.setFont(new Font("SansSerif", Font.BOLD, 36));
        lblWelcome.setForeground(CLR_TEXT_DARK);
        addToGrid(pnlForm, lblWelcome, 0, row++,
                2, 1, 1.0, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 8, 0));


        JLabel lblSubtitle = new JLabel(
                "<html>Please enter your credentials to manage your floor.</html>");
        lblSubtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblSubtitle.setForeground(CLR_TEXT_MUTED);
        addToGrid(pnlForm, lblSubtitle, 0, row++,
                2, 1, 1.0, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 28, 0));


        addToGrid(pnlForm, makeFieldLabel("Username"), 0, row++,
                2, 1, 1.0, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 7, 0));


        addToGrid(pnlForm, buildInputPanel(makePersonIcon(), false, "barista_id or email"),
                0, row++,
                2, 1, 1.0, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 18, 0));


        JLabel lblPasswordLabel = makeFieldLabel("Password");
        addToGrid(pnlForm, lblPasswordLabel, 0, row,
                1, 1, 1.0, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 7, 0));

        JLabel lblForgot = new JLabel("Forgot?");
        lblForgot.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblForgot.setForeground(CLR_BROWN_BTN);
        lblForgot.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        GridBagConstraints gbcForgot = new GridBagConstraints();
        gbcForgot.gridx   = 1;
        gbcForgot.gridy   = row;
        gbcForgot.anchor  = GridBagConstraints.EAST;
        gbcForgot.insets  = new Insets(0, 0, 7, 0);
        pnlForm.add(lblForgot, gbcForgot);
        row++;


        addToGrid(pnlForm, buildInputPanel(makeLockIcon(), true, "••••••••"),
                0, row++,
                2, 1, 1.0, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 18, 0));


        JCheckBox chkRemember = new JCheckBox("Stay logged in for the shift");
        chkRemember.setOpaque(false);
        chkRemember.setFont(new Font("SansSerif", Font.PLAIN, 13));
        chkRemember.setForeground(CLR_TEXT_DARK);
        addToGrid(pnlForm, chkRemember, 0, row++,
                2, 1, 1.0, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 24, 0));


        JButton btnLogin = new JButton("Login  →") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) {
                    g2.setColor(CLR_BROWN_DARK);
                } else if (getModel().isRollover()) {
                    g2.setColor(CLR_BROWN_HOVER);
                } else {
                    g2.setColor(CLR_BROWN_BTN);
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 50);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btnLogin.setFont(new Font("SansSerif", Font.BOLD, 15));
        btnLogin.setForeground(CLR_WHITE);
        btnLogin.setContentAreaFilled(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setFocusPainted(false);
        btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnLogin.setMargin(new Insets(16, 24, 16, 24));
        btnLogin.setPreferredSize(new Dimension(360, 58));

        addToGrid(pnlForm, btnLogin, 0, row++,
                2, 1, 1.0, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 36, 0));

        JLabel lblAuthorized = new JLabel("AUTHORIZED ACCESS ONLY");
        lblAuthorized.setFont(new Font("SansSerif", Font.BOLD, 10));
        lblAuthorized.setForeground(CLR_TEXT_MUTED);
        addToGrid(pnlForm, lblAuthorized, 0, row++,
                2, 1, 1.0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 0, 8, 0));

        JPanel pnlBadges = new JPanel(new GridBagLayout());
        pnlBadges.setOpaque(false);
        GridBagConstraints gbcB1 = new GridBagConstraints();
        gbcB1.gridx = 0; gbcB1.gridy = 0; gbcB1.insets = new Insets(0, 0, 0, 6);
        pnlBadges.add(makeBadge("SSL"), gbcB1);
        GridBagConstraints gbcB2 = new GridBagConstraints();
        gbcB2.gridx = 1; gbcB2.gridy = 0;
        pnlBadges.add(makeBadge("256"), gbcB2);

        addToGrid(pnlForm, pnlBadges, 0, row,
                2, 1, 1.0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0));


        pnlForm.setPreferredSize(new Dimension(380, pnlForm.getPreferredSize().height));
        GridBagConstraints gbcForm = new GridBagConstraints();
        gbcForm.gridx   = 0;
        gbcForm.gridy   = 0;
        gbcForm.anchor  = GridBagConstraints.CENTER;
        gbcForm.fill    = GridBagConstraints.NONE;
        gbcForm.ipadx   = 0;
        gbcForm.ipady   = 0;
        gbcForm.weightx = 1.0;
        gbcForm.weighty = 1.0;
        pnlRight.add(pnlForm, gbcForm);

        return pnlRight;
    }


    private JPanel buildInputPanel(Icon icon, boolean isPassword, String placeholder) {
        JPanel pnlInput = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(CLR_INPUT_BG);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
                g2.setColor(CLR_INPUT_BORDER);
                g2.setStroke(new BasicStroke(1.2f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 14, 14);
                g2.dispose();
            }

            @Override
            public boolean isOpaque() { return false; }
        };
        pnlInput.setPreferredSize(new Dimension(360, 48));
        pnlInput.setMinimumSize(new Dimension(100, 48));

        JLabel lblInputIcon = makeIconLabel(icon, 20, 20);
        lblInputIcon.setBounds(14, 14, 20, 20);
        pnlInput.add(lblInputIcon);

        JTextField txtField = isPassword ? new JPasswordField() : new JTextField();
        txtField.setOpaque(false);
        txtField.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        txtField.setFont(new Font("SansSerif", Font.PLAIN, 13));
        txtField.setForeground(CLR_TEXT_DARK);
        txtField.putClientProperty("JTextField.placeholderText", placeholder);
        if (isPassword) {
            ((JPasswordField) txtField).setEchoChar('•');
        }
        pnlInput.add(txtField);

        pnlInput.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                txtField.setBounds(44, 4, pnlInput.getWidth() - 54, 40);
            }
        });

        return pnlInput;
    }

    private void addToGrid(JPanel pnlTarget, Component comp,
                           int gridx, int gridy,
                           int gridwidth, int gridheight,
                           double weightx, double weighty,
                           int anchor, int fill,
                           Insets insets) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx      = gridx;
        gbc.gridy      = gridy;
        gbc.gridwidth  = gridwidth;
        gbc.gridheight = gridheight;
        gbc.weightx    = weightx;
        gbc.weighty    = weighty;
        gbc.anchor     = anchor;
        gbc.fill       = fill;
        gbc.insets     = insets;
        pnlTarget.add(comp, gbc);
    }

    private JLabel makeFieldLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lbl.setForeground(CLR_TEXT_DARK);
        return lbl;
    }

    private JLabel makeIconLabel(Icon icon, int w, int h) {
        JLabel lbl = new JLabel(icon);
        lbl.setPreferredSize(new Dimension(w, h));
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        lbl.setVerticalAlignment(SwingConstants.CENTER);
        return lbl;
    }

    private JLabel makeBadge(String text) {
        JLabel lbl = new JLabel(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(CLR_BADGE);
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        lbl.setFont(new Font("SansSerif", Font.BOLD, 9));
        lbl.setForeground(CLR_BADGE_TEXT);
        lbl.setPreferredSize(new Dimension(38, 38));
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        lbl.setVerticalAlignment(SwingConstants.CENTER);
        return lbl;
    }

    private Icon makeCoffeeIcon() {
        return new Icon() {
            @Override public int getIconWidth()  { return 22; }
            @Override public int getIconHeight() { return 22; }
            @Override public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(CLR_LOGO_TEXT);
                g2.setStroke(new BasicStroke(1.6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.drawRoundRect(x + 2, y + 7, 13, 11, 3, 3);
                g2.drawArc(x + 14, y + 9, 6, 6, -90, 180);
                g2.drawLine(x + 5, y + 5, x + 5, y + 2);
                g2.drawLine(x + 9, y + 5, x + 9, y + 2);
                g2.dispose();
            }
        };
    }

    private Icon makeAnalyticsIcon() {
        return new Icon() {
            @Override public int getIconWidth()  { return 20; }
            @Override public int getIconHeight() { return 20; }
            @Override public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(CLR_ICON_MUTED);
                g2.fillRect(x + 2,  y + 13, 3, 6);
                g2.fillRect(x + 7,  y + 9,  3, 10);
                g2.fillRect(x + 12, y + 5,  3, 14);
                g2.setColor(new Color(0xFFE0A0));
                g2.setStroke(new BasicStroke(1.6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.drawPolyline(
                        new int[]{x + 3, x + 8, x + 13},
                        new int[]{y + 12, y + 8, y + 4}, 3);
                g2.dispose();
            }
        };
    }

    private Icon makeInventoryIcon() {
        return new Icon() {
            @Override public int getIconWidth()  { return 20; }
            @Override public int getIconHeight() { return 20; }
            @Override public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(CLR_ICON_MUTED);
                g2.setStroke(new BasicStroke(1.6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.drawRoundRect(x + 2, y + 5, 15, 13, 3, 3);
                g2.drawLine(x + 1, y + 9, x + 17, y + 9);
                g2.drawArc(x + 7, y + 2, 5, 6, 0, 180);
                g2.dispose();
            }
        };
    }

    private Icon makePersonIcon() {
        return new Icon() {
            @Override public int getIconWidth()  { return 20; }
            @Override public int getIconHeight() { return 20; }
            @Override public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(CLR_TEXT_MUTED);
                g2.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.drawOval(x + 5, y + 1, 8, 8);
                g2.drawArc(x + 1, y + 11, 16, 8, 0, 180);
                g2.dispose();
            }
        };
    }

    private Icon makeLockIcon() {
        return new Icon() {
            @Override public int getIconWidth()  { return 20; }
            @Override public int getIconHeight() { return 20; }
            @Override public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(CLR_TEXT_MUTED);
                g2.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.drawRoundRect(x + 3, y + 8, 12, 10, 3, 3);
                g2.drawArc(x + 5, y + 2, 8, 9, 0, 180);
                g2.fillOval(x + 8, y + 12, 3, 3);
                g2.dispose();
            }
        };
    }
}