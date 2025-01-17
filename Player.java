public class Player {

    //
    protected Card[] hand;
    private int score;
    private String name;

    //creates a new player object with an array of 4 cards in its hand (Cards[])
    public static Player createHumanPlayer(Deck d, String name) {
        Player player = new Player();
        player.hand = new Card[4];
        for (int i = 0; i < 4; i++) {
            player.hand[i] = d.deal();
        }
        player.setName(name);
        return player;
    }

    //deal the top card from the deck
    public static Player createComputerPlayer(Deck d) {
        Player computer = new Player();
        computer.hand = new Card[1];
        computer.hand[0] = d.deal();
        return computer;
    }

    public void printHand() {
        for (int i = 0; i < hand.length; i++) {
            System.out.println(hand[i].getValue() + " of " + hand[i].getSuit());
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public Card[] getPlayerHand() {
        return hand;
    }

    public Card[] getComputerHand() {
        return hand;
    }

    public int addScore() {
        score++;
        return score;
    }

    public int getScore() {
        return score;
    }
}