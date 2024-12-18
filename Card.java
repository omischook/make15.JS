public class Card {
    private char suit;
    private String value;

    public Card(char suit, String value) {
        this.suit = suit;
        this.value = value;
    }

    public char getSuit() {
        return suit;
    }

    public String getValue() {
        return value;
    }

    public char setSuit(char suit) {
        this.suit = suit;
        return suit;
    }
    public String setValue(String value) {
        this.value = value;
        return value;
    }
    public int convertValue() {
        if (value.equals("A")) {
            return 12;
        } else if (value.equals("J") || value.equals("Q") || value.equals("K")) {
            return 11;
        } else {
            return Integer.parseInt(value);
        }
    }
}
