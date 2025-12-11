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
import java.util.Map;
import java.util.HashMap;


public class Menu {
    
    // mapping word -> rank / frequency / id, tersedia untuk seluruh method class
    static Map<String, Integer> wordToRank = new HashMap<>();
    static Map<String, Integer> wordToFrequency = new HashMap<>();
    static Map<String, Integer> wordToId = new HashMap<>();

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
                "1", "dictionary",
                "2", "play",
                "3", "clear",
                "4", "exit");
        LineReader reader = LineReaderBuilder.builder()
                .terminal(terminal)
                .completer(new StringsCompleter(options))
                .build();
    
        while (true) {
            String input = reader.readLine("menu> ");
            String cmd = input.strip();
            
            // Check exit condition (support "exit" atau "4")
            if (cmd.equalsIgnoreCase("exit") || cmd.equals("4")) {
                break;
            } else {
                handleInput(input, terminal);
            }
        }
	}

	static void printHelp(Terminal terminal) {
        String helpMsg = """
            Available Commands:
            1. dictionary (search a word based on a dictionary dataset)
            2. play (play a frequency guessing game! Two players compete to guess word frequencies. Closest guess wins. Best of 3 rounds!)
            3. clear (clears the screen)
            4. exit (exit prompt)

            TIP: You can type the command name OR its number (e.g., '2' or 'play')
            NOTE: By default, nothing will be executed if provided an invalid argument
                """;
        terminal.writer().println(helpMsg);
	}

    static void playAGame(Terminal terminal) {
        terminal.puts(Capability.clear_screen);
        
        // Banner game
        String gameBanner = FigletFont.convertOneLine("Frequency Game");
        terminal.writer().println(gameBanner);
        terminal.writer().println("=".repeat(60));
        terminal.writer().println("Guess the word frequency! Closest guess wins!");
        terminal.writer().println("Best of 3 rounds");
        terminal.writer().println("=".repeat(60));
        terminal.writer().println();
        
        // Load dataset terlebih dahulu
        wordToRank.clear();
        wordToFrequency.clear();
        wordToId.clear();
        
        Scanner dataScanner = new Scanner(
            Menu.class.getResourceAsStream("/word_frequency_with_definitions.csv"));
        
        java.util.List<String> wordList = new java.util.ArrayList<>();
        int rank = 0;
        
        while (dataScanner.hasNextLine()) {
            String line = dataScanner.nextLine().trim();
            if (line.isEmpty()) continue;
            
            String[] parts = line.split(",");
            String word = parts[0].replaceAll("^\"|\"$", "").trim();
            int frequency = 0;
            
            if (parts.length > 1) {
                try {
                    frequency = Integer.parseInt(parts[1].trim());
                } catch (NumberFormatException ex) {
                    frequency = 0;
                }
            }
            
            wordToRank.put(word, rank);
            wordToFrequency.put(word, frequency);
            wordToId.put(word, rank);
            wordList.add(word);
            
            rank++;
        }
        dataScanner.close();
        
        if (wordList.isEmpty()) {
            terminal.writer().println("Error: No words loaded from dataset!");
            terminal.writer().flush();
            return;
        }
        
        // Input nama player
        LineReader reader = LineReaderBuilder.builder()
            .terminal(terminal)
            .build();
        
        String player1Name = reader.readLine("Player 1, enter your name: ").trim();
        if (player1Name.isEmpty()) player1Name = "Player 1";
        
        String player2Name = reader.readLine("Player 2, enter your name: ").trim();
        if (player2Name.isEmpty()) player2Name = "Player 2";
        
        terminal.writer().println();
        terminal.writer().println("Welcome " + player1Name + " and " + player2Name + "!");
        terminal.writer().println("Let the game begin!");
        terminal.writer().println();
        
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Game loop - best of 3
        int player1Score = 0;
        int player2Score = 0;
        java.util.Random random = new java.util.Random();
        
        for (int round = 1; round <= 3; round++) {
            terminal.puts(Capability.clear_screen);
            terminal.writer().println("=".repeat(60));
            terminal.writer().println("                      ROUND " + round + " / 3");
            terminal.writer().println("=".repeat(60));
            terminal.writer().println("Score: " + player1Name + " [" + player1Score + "] - [" + player2Score + "] " + player2Name);
            terminal.writer().println("=".repeat(60));
            terminal.writer().println();
            
            // Pilih kata random
            String selectedWord = wordList.get(random.nextInt(wordList.size()));
            int actualFrequency = wordToFrequency.get(selectedWord);
            
            // Tampilkan kata
            String wordDisplay = FigletFont.convertOneLine(selectedWord);
            terminal.writer().println(wordDisplay);
            terminal.writer().println("Word: " + selectedWord.toUpperCase());
            terminal.writer().println("Guess the frequency of this word!");
            terminal.writer().println();
            
            // Player 1 input
            int player1Guess = 0;
            boolean validInput = false;
            while (!validInput) {
                try {
                    String input = reader.readLine(player1Name + ", enter your guess: ");
                    player1Guess = Integer.parseInt(input.trim());
                    if (player1Guess < 0) {
                        terminal.writer().println("Please enter a positive number!");
                    } else {
                        validInput = true;
                    }
                } catch (NumberFormatException e) {
                    terminal.writer().println("Invalid input! Please enter a number.");
                }
            }
            
            terminal.writer().println(player1Name + " guessed: " + player1Guess);
            terminal.writer().println();
            
            // Player 2 input
            int player2Guess = 0;
            validInput = false;
            while (!validInput) {
                try {
                    String input = reader.readLine(player2Name + ", enter your guess: ");
                    player2Guess = Integer.parseInt(input.trim());
                    if (player2Guess < 0) {
                        terminal.writer().println("Please enter a positive number!");
                    } else {
                        validInput = true;
                    }
                } catch (NumberFormatException e) {
                    terminal.writer().println("Invalid input! Please enter a number.");
                }
            }
            
            terminal.writer().println(player2Name + " guessed: " + player2Guess);
            terminal.writer().println();
            
            // Loading animation
            terminal.writer().print("Calculating");
            terminal.writer().flush();
            
            for (int i = 0; i < 6; i++) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                terminal.writer().print(".");
                terminal.writer().flush();
            }
            
            terminal.writer().println();
            terminal.writer().println();
            
            // Hitung selisih
            int player1Diff = Math.abs(player1Guess - actualFrequency);
            int player2Diff = Math.abs(player2Guess - actualFrequency);
            
            // Tampilkan hasil
            terminal.writer().println("=".repeat(60));
            terminal.writer().println("                    ROUND " + round + " RESULTS");
            terminal.writer().println("=".repeat(60));
            terminal.writer().println("Actual frequency: " + actualFrequency);
            terminal.writer().println();
            terminal.writer().println(player1Name + "'s guess: " + player1Guess + " (difference: " + player1Diff + ")");
            terminal.writer().println(player2Name + "'s guess: " + player2Guess + " (difference: " + player2Diff + ")");
            terminal.writer().println();
            
            // Tentukan pemenang ronde
            if (player1Diff < player2Diff) {
                player1Score++;
                terminal.writer().println("🎉 " + player1Name + " wins this round!");
            } else if (player2Diff < player1Diff) {
                player2Score++;
                terminal.writer().println("🎉 " + player2Name + " wins this round!");
            } else {
                terminal.writer().println("🤝 It's a tie! No points awarded.");
            }
            
            terminal.writer().println();
            terminal.writer().println("Current Score: " + player1Name + " [" + player1Score + "] - [" + player2Score + "] " + player2Name);
            terminal.writer().println("=".repeat(60));
            
            // Cek apakah sudah ada yang menang 2 ronde
            if (player1Score == 2 || player2Score == 2) {
                terminal.writer().println();
                terminal.writer().println("Press ENTER to see final results...");
                terminal.writer().flush();
                reader.readLine();
                break;
            }
            
            // Jeda sebelum ronde berikutnya
            if (round < 3) {
                terminal.writer().println();
                terminal.writer().println("Press ENTER to continue to next round...");
                terminal.writer().flush();
                reader.readLine();
            }
        }
        
        // Final results
        terminal.puts(Capability.clear_screen);
        terminal.writer().println();
        terminal.writer().println("=".repeat(60));
        terminal.writer().println("                    FINAL RESULTS");
        terminal.writer().println("=".repeat(60));
        terminal.writer().println();
        
        String winnerName;
        if (player1Score > player2Score) {
            winnerName = player1Name;
            String winnerBanner = FigletFont.convertOneLine(player1Name + " Wins!");
            terminal.writer().println(winnerBanner);
        } else if (player2Score > player1Score) {
            winnerName = player2Name;
            String winnerBanner = FigletFont.convertOneLine(player2Name + " Wins!");
            terminal.writer().println(winnerBanner);
        } else {
            terminal.writer().println("It's a TIE!");
        }
        
        terminal.writer().println();
        terminal.writer().println("Final Score: " + player1Name + " [" + player1Score + "] - [" + player2Score + "] " + player2Name);
        terminal.writer().println("=".repeat(60));
        terminal.writer().println();
        terminal.writer().println("Thanks for playing!");
        terminal.writer().println("Press ENTER to return to main menu...");
        terminal.writer().flush();
        
        reader.readLine();
        printWelcome(terminal);
    }

	static void startDictionarySearch(Terminal terminal) {
    	// clear previous maps (jika user buka dictionary berkali-kali)
        wordToRank.clear();
        wordToFrequency.clear();
        wordToId.clear();
    
        // read dataset file
        Scanner sc = new Scanner(
                Menu.class.getResourceAsStream
                    ("/word_frequency_with_definitions.csv"));
    
        // initialize radix tree
        RadixTree tree = new RadixTree();
    
        int rank = 0;
        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.isEmpty()) continue;
    
            // asumsi CSV: word,frequency,...  (jika ada header, skip bila perlu)
            String[] parts = line.split(",");
            String word = parts[0].replaceAll("^\"|\"$", "").trim(); // hapus quotes jika ada
            int frequency = 0;
            if (parts.length > 1) {
                try {
                    frequency = Integer.parseInt(parts[1].trim());
                } catch (NumberFormatException ex) {
                    frequency = 0;
                }
            }
    
            tree.insertWord(word, rank);
    
            // simpan mapping
            wordToRank.put(word, rank);
            wordToFrequency.put(word, frequency);
            wordToId.put(word, rank); // gunakan rank sebagai word_id (sesuaikan bila DB berbeda)
    
            rank++;
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
        terminal.writer().println("Type '/q' to return to main menu");
        terminal.writer().println();
        terminal.writer().flush();
        
        while (true) {
            String query = reader.readLine("dictionary> ");
    
            if (query.equals("/q")) {
                // Kembali ke menu utama
                printWelcome(terminal);
                break;
            }
    
            String figletQuery = FigletFont.convertOneLine(query);
            terminal.writer().println(figletQuery);
    
            showDBQuery(query.strip(), terminal);
        }
	}
	
	static void showDBQuery(String word, Terminal terminal) {
    	try {
            // cek apakah kata terdaftar di map
            Integer id = wordToId.get(word);
            Integer rank = wordToRank.get(word);
            Integer freq = wordToFrequency.getOrDefault(word, 0);
    
            if (id == null) {
                terminal.writer().println("Word not found in local dataset!");
                terminal.writer().flush();
                return;
            }
    
            // query definisi dari DB berdasarkan word_id
            String sql = "SELECT definition FROM definitions WHERE word_id = ?";
            Connection connection = DriverManager.getConnection("jdbc:sqlite:latest_dictionary.db");
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
    
            // tampilkan rank & frequency dulu
            terminal.writer().println("Rank: " + Helper.toOrdinal(rank));
            terminal.writer().println("Frequency: " + freq);
            terminal.writer().println("Definition:");
            boolean any = false;
            while (rs.next()) {
                any = true;
                String def = rs.getString("definition");
                terminal.writer().println("- " + def);
                terminal.writer().println(); // Tambahkan jarak setelah setiap definisi
            }
            if (!any) {
                terminal.writer().println("No definition found in database for this word_id.");
            }
            terminal.writer().flush();
    
            rs.close();
            stmt.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	static void handleInput(String input, Terminal terminal) {
    	String cmd = input.strip();
        
        // Support nomor atau nama command
        switch (cmd) {
            case "1", "dictionary" -> startDictionarySearch(terminal);
            case "2", "play" -> playAGame(terminal);
            case "3", "clear" -> {
                // Clear screen dan scroll buffer
                terminal.puts(Capability.clear_screen);
                terminal.writer().print("\033[H\033[2J\033[3J");
                terminal.writer().flush();
                printWelcome(terminal);
            }
            case "4", "exit" -> {
                // Ini tidak akan dipanggil karena exit di-handle di startREPL
                // Tapi kita tambahkan untuk konsistensi
            }
            default -> {
                // Command tidak valid
            }
        }
	}
}
