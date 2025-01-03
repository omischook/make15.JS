import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ReplayManager {

    public static void refreshFile () {
        try {
            FileWriter replayFile = new FileWriter("replay.txt");
        }
        catch (IOException e) {
            System.out.println("Error occurred while refreshing the replay file");
        }
    }

    public static void writeStringToReplay(String str) {
        try {
            FileWriter replayFile = new FileWriter("replay.txt", true);
            replayFile.write(str);
            replayFile.close();
        }
        catch (IOException e) {
            System.out.println("Error occurred while writing to the replay file");
        }
    }

    public static void writePlayerDetail(Player player) {
        try {
            FileWriter replayFile = new FileWriter("replay.txt", true);
            replayFile.write("Player's hand:\n");
            for (Card card : player.getPlayerHand()) {
                replayFile.write(card.getValue() + " of " + card.getSuit() + "\n");
            }
            replayFile.close();
        } catch (IOException e) {
            System.out.println("Error occurred while writing the player's hand to the replay file");
        }
    }

    public static void writeChosenCard(Player player, int selectedCard) {
        try {
            FileWriter replayFile = new FileWriter("replay.txt", true);
            replayFile.write("Player's selected card: " + player.hand[selectedCard].getValue() + " of " + player.hand[selectedCard].getSuit() + "\n");
            replayFile.close();
        } catch (IOException e) {
            System.out.println("Error occurred while writing the player's selected card to the replay file");
        }
    }

    public static void writeRoundOutcome(String result){
        try {
            FileWriter replayFile = new FileWriter("replay.txt", true);
            replayFile.write(result + "\n");
            replayFile.close();
        } catch (IOException e) {
            System.out.println("Error occurred while writing the round outcome to the replay file");
        }
    }

    public static void writeScore(String name, int score){
        try {
            FileWriter replayFile = new FileWriter("replay.txt", true);
            replayFile.write(name + " scored " + score + " points\n");
            replayFile.close();
        } catch (IOException e) {
            System.out.println("Error occurred while writing the score to the replay file");
        }
    }

    public static void showReplay(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader("replay.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error occurred while reading the replay file");
        }
    }
}
