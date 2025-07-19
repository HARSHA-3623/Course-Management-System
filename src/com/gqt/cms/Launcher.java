package com.gqt.cms;

import javax.swing.SwingUtilities;
import com.gqt.cms.gui.MainWindow;

public class Launcher {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new MainWindow();
		});
	}
}


