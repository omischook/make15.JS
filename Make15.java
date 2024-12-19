import java.util.Scanner;

public class Make15 {
    public static void main(String[] args) {
        startGame();
    }

    // Creates a new deck with 2 new player objects with hands
    public static void startGame() {
        Deck d = new Deck();
        d.shuffle();
        Player player = Player.createHumanPlayer(d);
        Player computer = Player.createComputerPlayer(d);

        System.out.println("Player's hand:");
        player.printHand();
        System.out.println("Computer's hand:");
        computer.printHand();
        roundOne(player, computer, d);
    }

    public static boolean suitCheck(Player player, Player computer, Deck d) {
        System.out.println("You must play a card of the same suit in order to continue the game");
        Scanner scanner = new Scanner(System.in);
        int selectedCard = scanner.nextInt();
        selectedCard = selectedCard - 1;
        System.out.println("You selected: " + player.hand[selectedCard].getValue() + " of " + player.hand[selectedCard].getSuit());
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
        // assigns a new card object with name newCard to be a card from the deck d gotten using the deal method
        Card newCard = d.deal();
        player.hand[card] = newCard;
    }

    public static void discardCard(Player player, int card, Deck d) {
        if (player.hand[card].convertValue() == 11) {
            // assigns a new card object with name newCard to be a card from the deck d gotten using the deal method
            Card newCard = d.deal();
            player.hand[card] = newCard;
        } else {
            System.out.println("You cannot discard a card that is not a face card");
        }
    }

    public static void gameOver(Player player, Player computer, Deck d) {
        System.out.println("Game Over");
        System.out.println("Player's score: " + player.getScore());
        System.out.println("Computer's score: " + computer.getScore());
        System.out.println("Would you like to play again? (yes/no)");
        Scanner scanner = new Scanner(System.in);
        String playAgain = scanner.nextLine();
        if (playAgain.equals("yes")) {
            startGame();
        } else {
            System.out.println("Goodbye");
        }
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
        int card = scanner.nextInt();
        scanner.nextLine();
        card = card - 1;
        System.out.println("You selected: " + player.hand[card].getValue() + " of " + player.hand[card].getSuit());
        // gets player to discard a card if they choose to

        if (player.hand[card].convertValue() + computer.hand[0].convertValue() == 15) {
            System.out.println("You have made 15 with: " + player.hand[card].getValue() + " of " + player.hand[card].getSuit() + " and " + computer.hand[0].getValue() + " of " + computer.hand[0].getSuit());
            replaceCard(player, card, d);
            // checks if the player has played a card of the same suit to see if the game will continue
        } else {
            System.out.println("You did not make 15");
            if (suitCheck(player, computer, d)) {
                System.out.println("You have played a card of the same suit you won't gain a point but will move on to the next round");
                nextRound(player, computer, d);
            } else {
                System.out.println("You did not play a card of the same suit game over");
                gameOver(player, computer, d);
            }
        }
        System.out.println("before the next round would you like to discard any face cards? if so type 'DISCARD' otherwise type 'NO'");
        String answer = scanner.nextLine();
        while (answer.equals("DISCARD")) {
            System.out.println("Enter the number of the card you want to effect: ");
            // Currently gives card numbers 0-3 will need a -1 to get the correct card
            card = scanner.nextInt();
            scanner.nextLine();
            card = card - 1;
            System.out.println("You selected: " + player.hand[card].getValue() + " of " + player.hand[card].getSuit());
            discardCard(player, card, d);
            System.out.println("before the next round would you like to discard any face cards? if so type 'DISCARD' otherwise type 'NO'");
            answer = scanner.nextLine();
        }
        nextRound(player, computer, d);
    }








    //-------------------------------------------------------------------------------------------------in between space -------------------------------------------------------------------------------------------------







    public static void nextRound(Player player, Player computer, Deck d) {
        System.out.println("Player's hand:");
        player.printHand();
        System.out.println("Computer's hand:");
        computer.printHand();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Type 'DISCARD' to remove a card otherwise enter nothing: ");
        String discard = scanner.nextLine();
        System.out.print("Enter the number of the card you want to effect: ");
        // Currently gives card numbers 0-3 will need a -1 to get the correct card
        int card = scanner.nextInt();
        card = card - 1;
        System.out.println("You selected: " + player.hand[card].getValue() + " of " + player.hand[card].getSuit());
        if (discard.equals("DISCARD")) {
            System.out.println("You have discarded: " + player.hand[card].getValue() + " of " + player.hand[card].getSuit());
            discardCard(player, card, d);
        } else if (player.hand[card].convertValue() + computer.hand[0].convertValue() == 15) {
            System.out.println("You have made 15 with: " + player.hand[card].getValue() + " of " + player.hand[card].getSuit() + " and " + computer.hand[0].getValue() + " of " + computer.hand[0].getSuit());
            player.hand[card] = null;
            computer.hand[0] = null;
            player.addScore();
        } else {
            System.out.println("You did not make 15");
            if (suitCheck(player, computer, d)) {
                System.out.println("You have played a card of the same suit you won't gain a point but will move on to the next round");
                nextRound(player, computer, d);
            } else {
                System.out.println("You did not play a card of the same suit game over");
            }
        }
    }
}