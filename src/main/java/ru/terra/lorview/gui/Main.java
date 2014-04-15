package ru.terra.lorview.gui;

import org.apache.log4j.BasicConfigurator;

public class Main {

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
        BasicConfigurator.configure();
		new MainWindow().open();
	}
}
