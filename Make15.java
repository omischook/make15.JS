import java.util.Scanner;
public class Make15 {
    public static void main(String[] args) {
        startGame();
    }
    //creates a new deck with 2 new player objects with hands
    public static void startGame() {
        Deck d = new Deck();
        d.shuffle();
        Player player = Player.createHumanPlayer(d);
        Player computer = Player.createComputerPlayer(d);

        System.out.println("Player's hand:");
        player.printHand();
        System.out.println("Computer's hand:");
        computer.printHand();
        roundOne(player, computer);
    }

    public static void roundOne(Player player, Player computer) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Round 1");
        System.out.println("Player's turn, select a card from your hand that combines with the computers card to make 15");
        System.out.println("If you cannot make 15, type 'DISCARD' & select a card to discard");
        System.out.println("Select a card by typing its displayed number, for example card 1 is");
        System.out.print("Type 'DISCARD' to remove a card otherwise enter nothing: ");
        String discard = scanner.nextLine();
        System.out.print("Enter the number of the card you want to efffect: ");
        //currently gives card numbers 0-3 will need a -1 to get the correct card
        int card = scanner.nextInt();
        System.out.println("You selected: " + player.hand[card].getValue() + " of " + player.hand[card].getSuit());
        if (discard.equals("DISCARD")) {
            System.out.println("You have discarded: " + player.hand[card].getValue() + " of " + player.hand[card].getSuit());
            player.hand[card] = null;
        }
        else if (player.hand[card].convertValue() + computer.hand[0].convertValue() == 15) {
            System.out.println("You have made 15 with: " + player.hand[card].getValue() + " of " + player.hand[card].getSuit() + " and " + computer.hand[0].getValue() + " of " + computer.hand[0].getSuit());
            player.hand[card] = null;
            computer.hand[0] = null;
            player.addScore();
        }
        else {
            System.out.println("You did not make 15");
            if (suitCheck(player, computer)) {
                System.out.println("You have played a card of the same suit you won't gain a point but will move on to the next round");
                nextRound(player, computer);
            }
            else {
                System.out.println("You did not play a card of the same suit game over");
            }
        }
    }

    public static boolean suitCheck(Player player, Player computer){
        System.out.println("You must play a card of the same suit in order to continue the game");
        Scanner scanner = new Scanner(System.in);
        int selectedCard = scanner.nextInt();
        if (player.hand[selectedCard].getSuit() == computer.hand[0].getSuit()) {
            System.out.println("You have played a card of the same suit");
            return true;
        }
        else {
            System.out.println("You did not play a card of the same suit");
            return false;
        }
    }
    public static void nextRound(Player player, Player computer){
        System.out.println("Player's hand:");
        player.printHand();
        System.out.println("Computer's hand:");
        computer.printHand();
        System.out.print("Type 'DISCARD' to remove a card otherwise enter nothing: ");
        String discard = scanner.nextLine();
        System.out.print("Enter the number of the card you want to efffect: ");
        //currently gives card numbers 0-3 will need a -1 to get the correct card
        int card = scanner.nextInt();
        System.out.println("You selected: " + player.hand[card].getValue() + " of " + player.hand[card].getSuit());
        if (discard.equals("DISCARD")) {
            System.out.println("You have discarded: " + player.hand[card].getValue() + " of " + player.hand[card].getSuit());
            player.hand[card] = null;
        }
        else if (player.hand[card].convertValue() + computer.hand[0].convertValue() == 15) {
            System.out.println("You have made 15 with: " + player.hand[card].getValue() + " of " + player.hand[card].getSuit() + " and " + computer.hand[0].getValue() + " of " + computer.hand[0].getSuit());
            player.hand[card] = null;
            computer.hand[0] = null;
            player.addScore();
        }
        else {
            System.out.println("You did not make 15");
            if (suitCheck(player, computer)) {
                System.out.println("You have played a card of the same suit you won't gain a point but will move on to the next round");
                nextRound(player, computer);
            }
            else {
                System.out.println("You did not play a card of the same suit game over");
            }
        }
    }

}