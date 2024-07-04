package com.example.webs.Card_Related;
public class PlayingCard {
    // Define constants for suits
    public static final String SPADES = "Spades";
    public static final String HEARTS = "Hearts";
    public static final String DIAMONDS = "Diamonds";
    public static final String CLUBS = "Clubs";

    // Instance variables
    private String suit;
    private int value;

    // Constructor
    public PlayingCard(String suit, int value) {
        this.suit = suit;
        this.value = value;
    }

    // Getter methods
    public String getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }

    // Method to get a String representation of the card
    public String toString() {
        String cardValue;
        switch (value) {
            case 1:
                cardValue = "Ace";
                break;
            case 11:
                cardValue = "Jack";
                break;
            case 12:
                cardValue = "Queen";
                break;
            case 13:
                cardValue = "King";
                break;
            default:
                cardValue = String.valueOf(value);
        }
        return cardValue + " of " + suit;
    }
    public String getImgPath()
    {
        String path = suit.substring(0, 1).toLowerCase();
        String val1;
        if(value == 11)
        {
            val1 = "j";
        }
        else if(value == 12)
        {
            val1 = "q";
        }
        else if(value == 13)
        {
            val1 = "k";
        }
        else if(value == 1)
        {
            val1 = "a";
        }
        else
        {
            val1 = String.valueOf(value);
        }
        return "/"+path+val1+".JPG";
    }

    // Example usage in a main method
    public static void main(String[] args) {
        PlayingCard card1 = new PlayingCard(PlayingCard.SPADES, 13);
        PlayingCard card2 = new PlayingCard(PlayingCard.HEARTS, 3);

        System.out.println("Card 1: " + card1.toString());
        System.out.println("Card 2: " + card2.toString());
    }
}

