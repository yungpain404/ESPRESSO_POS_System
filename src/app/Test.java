package app;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatLightLaf;

public class Test{
	public static void main(String[] args) {
		try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        }catch (Exception e){
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(()->{
            new Login_UI().setVisible(true);
        });
	}
}
