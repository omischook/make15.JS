public class Deck {

    private Card[] cards;
    private int top;

    public Deck() {
        // ♠ ︎ ♥ ︎ ♦   ♣︎
        cards = new Card[52];
        top = 0;
        char[] suits = {'♥', '♦', '♣', '♠'};
        String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        int index = 0;
        for (int i = 0; i < suits.length; i++) {
            for (int j = 0; j < values.length; j++) {
                cards[index] = new Card(suits[i], values[j]);
                index++;
            }
        }
    }

    public void shuffle() {
        for (int i = 0; i < cards.length; i++) {
            int randomIndex = (int)(Math.random() * cards.length);
            Card temp = cards[i];
            cards[i] = cards[randomIndex];
            cards[randomIndex] = temp;
        }
    }

    public Card deal() {
        if (top < cards.length) {
            return cards[top++];
        }
        return null;
    }

    public void printDeck() {
        for (int i = 0; i < cards.length; i++) {
            System.out.println(cards[i].getValue() + " of " + cards[i].getSuit());
        }
    }
}
