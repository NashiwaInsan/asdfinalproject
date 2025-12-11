package org.fpasd.menu;

import org.fpasd.helpers.Helper;
import org.fpasd.tree.RadixTree;
import com.github.lalyos.jfiglet.FigletFont;
import java.net.URL;
import java.sql.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import org.jline.reader.Candidate;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.ParsedLine;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.terminal.*;
import org.jline.utils.InfoCmp.Capability;

public class Menu {

	static void printBanner(Terminal terminal) {
		terminal.puts(Capability.clear_screen);
		String welcomeMsg = FigletFont.convertOneLine("welcome");
		terminal.writer().println(welcomeMsg);
	}

	public static void printWelcome(Terminal terminal) {
		printBanner(terminal);
		printHelp(terminal);
	}

	public static void startREPL(Terminal terminal) {
		List<String> options = Arrays.asList(
				"help",
				"play",
				"dictionary",
				"exit");
		LineReader reader = LineReaderBuilder.builder()
				.terminal(terminal)
				.completer(new StringsCompleter(options))
				.build();

		while (true) {
			String input = reader.readLine("menu> ");
			if (input.equalsIgnoreCase("exit")) {
				break;
			} else {
				handleInput(input, terminal);
			}
		}
	}

	static void printHelp(Terminal terminal) {
		String helpMsg = """
				Available Commands:
				clear (clears the screen)
				help (display help message)
				play (play a game with two player to see which one has the best guess on the least popular word)
				dictionary (search a word based on a dictionary dataset)
				exit (exit prompt)

				NOTE: By default, nothing will be executed if provided an invalid argument
				    """;
		terminal.writer().println(helpMsg);
	}

	static void playAGame(Terminal terminal) {
		terminal.writer().println("hello world");
		terminal.writer().flush();
	}

	static void startDictionarySearch(Terminal terminal) {
		// read dataset file
		Scanner sc = new Scanner(
				Menu.class.getResourceAsStream(
						"/word_frequency_with_definitions.csv"));

		// initialize radix tree
		RadixTree tree = new RadixTree();

		// feed dataset to radix tree
		int rank = 0;
		while (sc.hasNextLine()) {
			String word = sc.nextLine().split(",")[0];
			tree.insertWord(word, rank);
			rank += 1;
		}

		sc.close();

		// the reason why a custom completer is needed is because using a
		// `StringCompleter` and reassign it every time an input is changed is not
		// efficient.
		Completer completer = new Completer() {
			@Override
			public void complete(
					LineReader reader,
					ParsedLine line,
					List<Candidate> candidates) {
				String query = line.word();
				Collection<String> suggestions = tree.search(query);

				for (String suggestion : suggestions) {
					candidates.add(new Candidate(suggestion));
				}
			}
		};

		LineReader reader = LineReaderBuilder.builder()
				.terminal(terminal)
				.completer(completer)
				.build();

		terminal.puts(Capability.clear_screen);

		String dictionaryWelcome = FigletFont.convertOneLine(
				"Dictionary Search");
		terminal.writer().println(dictionaryWelcome);
		terminal.writer().println("Search words, get its meaning!");
		terminal.writer().flush();
		while (true) {
			String query = reader.readLine("dictionary> ");

			if (query.equals("q()")) {
				break;
			}

			String figletQuery = FigletFont.convertOneLine(query);
			terminal.writer().println(figletQuery);

			showDBQuery(query.strip(), terminal);
		}
	}

	static void showDBQuery(String word, Terminal terminal) {
		try {
			String sql = "SELECT * FROM word WHERE word = ?";
			Connection connection = DriverManager.getConnection(
					"jdbc:sqlite:dictionary.db");
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, word);
			ResultSet queryResult = stmt.executeQuery();

			int rank = queryResult.getInt("rank");
			int frequency = queryResult.getInt("frequency");

			terminal
					.writer()
					.println(
							"This word ranks at " +
									rank +
									" and has a frequency of " +
									Helper.getOrdinal(frequency));
			terminal.writer().flush();

			queryResult.close();
			stmt.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static void handleInput(String input, Terminal terminal) {
		switch (input.strip()) {
			case "clear" -> terminal.puts(Capability.clear_screen);
			case "help" -> printHelp(terminal);
			case "play" -> playAGame(terminal);
			case "dictionary" -> startDictionarySearch(terminal);
			default -> {
			}
		}
	}
}
