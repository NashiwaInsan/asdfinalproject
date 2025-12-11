package org.fpasd.entry;

import org.fpasd.menu.Menu;
import org.jline.terminal.*;

public class Main {

	public static void main(String[] args) {
		try {
			Terminal terminal = TerminalBuilder.builder()
					.system(true)
					.provider("ffm")
					.dumb(false)
					.build();
			Menu.printWelcome(terminal);
			Menu.startREPL(terminal);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
