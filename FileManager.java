import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;


public class FileManager {
    public static File scoreBoard = new File("scoreBoard.txt");

    public static void resetFile(){
        File scoreBoard = new File("scoreBoard.txt");
    }

    //constructor for the Scoreentry object

    private static class ScoreEntry {
        String name;
        int score;

        public ScoreEntry(String name, int score) {
            this.name = name;
            this.score = score;
        }
    }

    //--------------------------------------------------------------------------------

    public static void updateScoreBoard(String name, int score) {
        List<ScoreEntry> scoreEntries = new ArrayList<>();

        // Read current scores from the file
        try (BufferedReader reader = new BufferedReader(new FileReader(scoreBoard))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 2) {
                    String playerName = parts[0];
                    int playerScore = Integer.parseInt(parts[1]);
                    scoreEntries.add(new ScoreEntry(playerName, playerScore));
                }
            }
        } catch (IOException e) {
            System.out.println("Error occurred while reading the scoreBoard file");
        }

        // Add the new score if it is higher than one of the top five scores
        if (scoreEntries.size() < 5 || score > scoreEntries.get(scoreEntries.size() - 1).score) {
            scoreEntries.add(new ScoreEntry(name, score));
        }

        // Sort the scores in descending order
        scoreEntries.sort((a, b) -> Integer.compare(b.score, a.score));

        // Keep only the top 5 scores
        if (scoreEntries.size() > 5) {
            scoreEntries = scoreEntries.subList(0, 5);
        }

        // Write the updated scores back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(scoreBoard))) {
            for (ScoreEntry entry : scoreEntries) {
                writer.write(entry.name + " " + entry.score);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error occurred while writing to the scoreBoard file");
        }
    }

    //--------------------------------------------------------------------------------

    /*public static void writeScoreBoard(int[] scores) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(scoreBoard));
            for (int score : scores) {
                writer.write(String.valueOf(score));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error occurred while writing to the scoreBoard file");
        }
    }

    public static int[] readScoreBoardScores() {
        int[] scores = new int[4];
        try {
            BufferedReader reader = new BufferedReader(new FileReader(scoreBoard));
            String line;
            int index = 0;
            while ((line = reader.readLine()) != null && index < scores.length) {
                try {
                    scores[index] = Integer.parseInt(line.trim());
                    index++;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid score format in line: " + line);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error occurred while reading the scoreBoard file");
        }
        return scores;
    }



    public static void removeScore(String name, int score){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(scoreBoard));
            String line = reader.readLine();
            while (line != null){
                if (line.contains(name + " " + score)){
                    line = "";
                    System.out.println("The score for " + name + " has been removed");
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error occured in fileManager class");
        }
    }*/

    public static void printScoreBoard(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(scoreBoard));
            String line = reader.readLine();
            while (line != null){
                System.out.println(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error occured in fileManager class");
        }
    }
}
