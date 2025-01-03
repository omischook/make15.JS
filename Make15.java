import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Make15 {
    public static void main(String[] args) {
        startGame();
    }

    // Creates a new deck with 2 new player objects with hands
    public static void startGame() {
        Deck d = new Deck();
        d.shuffle();
        //refresh the file's contents so that the contents of the previous replay are not included in the new replay
        ReplayManager.refreshFile();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Make 15");
        System.out.println("Please input your name: ");
        String name = scanner.nextLine();
        name = name.replaceAll("\\s+", "");
        //Here we write the player name to our replay file
        ReplayManager.writeStringToReplay(name);
        //
        System.out.println("Before we start the game do you want to see the leaderboard? (yes/no)");
        String answer = "";

        while (true) {
            try {
                answer = scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Please input a valid answer");
                scanner.next(); // Clear the invalid input
            }
        }

        // Check if the answer is "yes"
        if (answer.equalsIgnoreCase("yes")) {
            // Display the scoreboard
            FileManager.printScoreBoard();
        } else {
            // Move on
            System.out.println("Starting the game...");
            // Add code to start the game here
        }



        Player player = Player.createHumanPlayer(d, name);
        Player computer = Player.createComputerPlayer(d);

        System.out.println("Player's hand:");
        player.printHand();
        System.out.println("Computer's hand:");
        computer.printHand();
        ReplayManager.writePlayerDetail(player);
        ReplayManager.writePlayerDetail(computer);
        roundOne(player, computer, d);
    }

    public static boolean suitCheck(Player player, Player computer, Deck d) {
        System.out.println("You must play a card of the same suit in order to continue the game");
        //cannot be defined upon use because it will be within a try statement therefore local to that try statement
        int selectedCard;
        Scanner scanner = new Scanner(System.in);
        while(true){
            try {
                //variables within try statements are local to that try statement
                selectedCard = scanner.nextInt();
                selectedCard = selectedCard - 1;
                System.out.println("You selected: " + player.hand[selectedCard].getValue() + " of " + player.hand[selectedCard].getSuit());
                break;
            }
            catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("You must select a card from your hand");
                return false;
            }
            catch (InputMismatchException e) {
                System.out.println("Please input a valid number that represents a card in your hand");
                return false;
            }
        }


        if (player.hand[selectedCard].getSuit() == computer.hand[0].getSuit()) {
            System.out.println("You have played a card of the same suit");
            replaceCard(player, selectedCard, d);
            return true;
        } else {
            System.out.println("You did not play a card of the same suit");
            return false;
        }
    }

    public static void replaceCard(Player player, int card, Deck d) {
        try {
            Card newCard = d.deal();
            player.hand[card] = newCard;
        } catch (NoSuchElementException e) {
            System.out.println("No more cards in the deck to replace");
            gameOver(player, d);
        }
    }

    public static void discardCard(Player player, int card, Deck d) {
        if (player.hand[card].convertValue() == 11) {
            try {
                Card newCard = d.deal();
                player.hand[card] = newCard;
            } catch (NoSuchElementException e) {
                System.out.println("No more cards in the deck to discard");
                gameOver(player, d);
            }
        } else {
            System.out.println("You cannot discard a card that is not a face card");
        }
    }

    public static void gameOver(Player player, Deck d) {
        System.out.println("This was your final score");
        scoreBoardManager(player);
        System.out.println("Scoreboard:");
        FileManager.printScoreBoard();
        System.out.println("Game Over");
        System.out.println("Player's score: " + player.getScore());


        System.out.println("Would you like to see a replay of the game? (yes/no)");
        Scanner scanner = new Scanner(System.in);
        String answer = "";

        try {
            answer = scanner.nextLine().toLowerCase();
            if (answer.equals("yes")) {
                ReplayManager.showReplay();
            }
        }catch (InputMismatchException e) {
            System.out.println("Please input a valid answer");
        }



        System.out.println("Would you like to play again? (yes/no)");
        String playAgain; //cannot be defined upon use because it will be within a try statement therefore local to that try statement
        while(true){
            try{
                playAgain = scanner.nextLine();
                break;
            }
            catch (InputMismatchException e){
                System.out.println("Please input a valid answer");
            }
        }
        if (playAgain.equals("yes")) {
            startGame();
        } else {
            System.out.println("Goodbye");
        }
    }

    public static void scoreBoardManager(Player player){
        FileManager.updateScoreBoard(player.getName(), player.getScore());
    }

    public static void roundOne(Player player, Player computer, Deck d) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Round 1");
        System.out.println("Player's turn, select a card from your hand that combines with the computer's card to make 15");
        System.out.println("If you cannot make 15, type 'DISCARD' & select a card to discard");
        System.out.println("Select a card by typing its displayed number, for example card 1 is" + player.hand[0].getValue() + " of " + player.hand[0].getSuit());
        System.out.println("Face cards are worth 11 points and an ace is worth 12 points");
        System.out.print("Enter the number of the card you want to effect: ");
        // Currently gives card numbers 0-3 will need a -1 to get the correct card
        int card;
        //this is the sanitization of the card input above checking that it is both an input and also within the bounds of the array
        while (true){
            try{
                card = scanner.nextInt();
                scanner.nextLine();
                break;
            }
            catch (ArrayIndexOutOfBoundsException e){
                System.out.println(("You must select a card from your hand"));
                scanner.next(); // Clear the invalid input
            }
            catch (InputMismatchException e){
                System.out.println("You must select a card from your hand");
                scanner.next(); // Clear the invalid input
            }
        }
        card = card - 1;
        System.out.println("You selected: " + player.hand[card].getValue() + " of " + player.hand[card].getSuit());
        //adding more information to our replay file
        ReplayManager.writeChosenCard(player, card);

        // gets player to discard a card if they choose to
        //The player is checked if they have made 15
        if (player.hand[card].convertValue() + computer.hand[0].convertValue() == 15) {
            System.out.println("You have made 15 with: " + player.hand[card].getValue() + " of " + player.hand[card].getSuit() + " and " + computer.hand[0].getValue() + " of " + computer.hand[0].getSuit());
            player.addScore();
            System.out.println("Player's score: " + player.getScore());
            ReplayManager.writeRoundOutcome("Win");
            replaceCard(player, card, d);
            // checks if the player has played a card of the same suit to see if the game will continue
        } else {
            System.out.println("You did not make 15");
            if (suitCheck(player, computer, d)) {
                System.out.println("You have played a card of the same suit you won't gain a point but will move on to the next round");
                ReplayManager.writeRoundOutcome("Continue");
                nextRound(player, computer, d);
            } else {
                System.out.println("You did not play a card of the same suit game over");
                ReplayManager.writeRoundOutcome("Lose");
                gameOver(player, d);
            }
        }
        //This is the continuation of the code assuming that the player has won the game as the if statement above will move to here assuming the player has won
        System.out.println("before the next round would you like to discard any face cards? if so type 'DISCARD' otherwise type 'NO'");
        String answer = "";
        //This is the sanitization of the answer input above checking that it is a string
        while (true){
            try {
                answer = scanner.nextLine();
                break;
            }
            catch (InputMismatchException e){
                System.out.println("Please input a valid answer");
            }
        }
        while (answer.equals("DISCARD")) {
            System.out.println("Enter the number of the card you want to effect: ");
            // Currently gives card numbers 0-3 will need a -1 to get the correct card
            card = scanner.nextInt();
            //this is the sanitization of the card input above checking that it is both an input and also within the bounds of the array
            while (true){
                try{
                    scanner.nextLine();
                    break;
                }
                catch (ArrayIndexOutOfBoundsException e){
                    System.out.println(("You must select a card from your hand"));
                }
                catch (InputMismatchException e){
                    System.out.println("You must select a card from your hand");
                }
            }
            card = card - 1;
            System.out.println("You selected: " + player.hand[card].getValue() + " of " + player.hand[card].getSuit());
            discardCard(player, card, d);
            ReplayManager.writeStringToReplay("Player discard the following card");
            ReplayManager.writeChosenCard(player, card);
            System.out.println("before the next round would you like to discard any face cards? if so type 'DISCARD' otherwise type 'NO'");
            //This is the sanitization of the answer input above checking that it is a string
            while (true){
                try {
                    answer = scanner.nextLine();
                    break;
                }
                catch (InputMismatchException e){
                    System.out.println("Please input a valid answer");
                }
            }
        }
        nextRound(player, computer, d);
    }








    //-------------------------------------------------------------------------------------------------in between space -------------------------------------------------------------------------------------------------







    public static void nextRound(Player player, Player computer, Deck d) {
        replaceCard(computer, 0, d);
        System.out.println("Next Round");
        System.out.println("Player's hand:");
        player.printHand();
        System.out.println("Computer's hand:");
        computer.printHand();
        ReplayManager.writePlayerDetail(player);
        ReplayManager.writePlayerDetail(computer);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Player's turn, select a card from your hand that combines with the computer's card to make 15");
        System.out.print("Enter the number of the card you want to effect: ");

        int card;
        //this is the sanitization of the card input above checking that it is both an input and also within the bounds of the array
        while (true){
            try{
                card = scanner.nextInt();
                scanner.nextLine();
                break;
            }
            catch (ArrayIndexOutOfBoundsException e){
                System.out.println(("You must select a card from your hand"));
                scanner.next(); // Clear the invalid input
            }
            catch (InputMismatchException e){
                System.out.println("You must select a card from your hand");
                scanner.next(); // Clear the invalid input
            }
        }
        card = card - 1;
        System.out.println("You selected: " + player.hand[card].getValue() + " of " + player.hand[card].getSuit());
        //writing the selected card to the replay for this game state
        ReplayManager.writeChosenCard(player, card);
        // gets player to discard a card if they choose to
        //The player is checked if they have made 15
        if (player.hand[card].convertValue() + computer.hand[0].convertValue() == 15) {
            System.out.println("You have made 15 with: " + player.hand[card].getValue() + " of " + player.hand[card].getSuit() + " and " + computer.hand[0].getValue() + " of " + computer.hand[0].getSuit());
            player.addScore();
            System.out.println("Player's score: " + player.getScore());
            ReplayManager.writeRoundOutcome("Win");
            replaceCard(player, card, d);
            // checks if the player has played a card of the same suit to see if the game will continue
        } else {
            System.out.println("You did not make 15");
            if (suitCheck(player, computer, d)) {
                System.out.println("You have played a card of the same suit you won't gain a point but will move on to the next round");
                ReplayManager.writeRoundOutcome("Continue");
                nextRound(player, computer, d);
            } else {
                System.out.println("You did not play a card of the same suit game over");
                ReplayManager.writeRoundOutcome("Lose");
                gameOver(player, d);
            }
        }
        //This is the continuation of the code assuming that the player has won the game as the if statement above will move to here assuming the player has won
        System.out.println("before the next round would you like to discard any face cards? if so type 'DISCARD' otherwise type 'NO'");
        String answer = "";
        //This is the sanitization of the answer input above checking that it is a string
        while (true){
            try {
                answer = scanner.nextLine();
                break;
            }
            catch (InputMismatchException e){
                System.out.println("Please input a valid answer");
            }
        }

        //we are now within the loop that checks whether the player wants to discarda a card this is also where the player decides what is discarded
        while (answer.equals("DISCARD")) {
            System.out.println("Enter the number of the card you want to effect: ");
            // Currently gives card numbers 0-3 will need a -1 to get the correct card
            card = scanner.nextInt();
            //this is the sanitization of the card input above checking that it is both an input and also within the bounds of the array
            while (true){
                try{
                    scanner.nextLine();
                    break;
                }
                catch (ArrayIndexOutOfBoundsException e){
                    System.out.println(("You must select a card from your hand"));
                }
                catch (InputMismatchException e){
                    System.out.println("You must select a card from your hand");
                }
            }
            card = card - 1;
            System.out.println("You selected: " + player.hand[card].getValue() + " of " + player.hand[card].getSuit());
            ReplayManager.writeStringToReplay("Player discard the following card");
            ReplayManager.writeChosenCard(player, card);
            discardCard(player, card, d);
            System.out.println("before the next round would you like to discard any face cards? if so type 'DISCARD' otherwise type 'NO'");
            //This is the sanitization of the answer input above checking that it is a string
            while (true){
                try {
                    answer = scanner.nextLine();
                    break;
                }
                catch (InputMismatchException e){
                    System.out.println("Please input a valid answer");
                }
            }
        }
        nextRound(player, computer, d);
    }
}