import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.List;
import java.util.ArrayList;

/**
 * War game class
 *
 * @author Mr. Jaffe
 * @version 2022-10-19
 */
public class War {
    private Deck deck, player, bot;
    private Scanner scanner = new Scanner(System.in);
    private Card playerCard, botCard;
    private List<Card> cardsPlayed = new ArrayList<Card>();

    private String ans;
    private boolean playing = true;
    private boolean answered, inWar = false;
    private int pos;

    /**
     * Constructor for the game
     * Include your initialization here -- card decks, shuffling, etc
     * Run the event loop after you've done the initializations
     */
    public War() {
        // Initializations here...
        deck = new Deck();
        deck.initializeNewDeck();
        deck.shuffle();

        Deck[] halves = deck.dealDeck();
        player = halves[0];
        bot = halves[1];

        // ...then run the event loop
        this.runEventLoop();
    }

    /**
     * This is the game's event loop. The code in here should come
     * from the War flowchart you created for this game
     */
    public void runEventLoop() {
        while (playing) {
            System.out.println(
                    "You will be playing against a bot for a game of War."
                            + "\nIf you don't know how to play, there will be instructions throughout."
                            + "\nThis game is not actually interactive and will not determine the end result."
                            + "\nTyping is just to simulate an actual game and to make it more interesting."
                            + "\nPlease type 1 to begin and 0 to exit.");

            int temp = scanner.nextInt();
            try {
                if (temp == 1) {
                    newGame();
                } else if (temp == 0) {
                    this.playing = false;
                }
            } catch (InputMismatchException error) {
                System.out.println("Please enter either 0 or 1 to exit or begin.");
                scanner.next();
            }
        }
    }

    public void newGame() {
        System.out.println("\f");
        for (int round = 1; round <= 300; round++) {
            System.out.println("Round: " + round
                    + "\n ");
            pos = 0;

            // playing cards
            System.out.println("Type 'play' to play a card."
                    + "\n ");

            playCards();

            // compares cards
            inWar = true;
            do {
                if (playerCard.getRank() == botCard.getRank()) {
                    System.out.println("\nYou are now in war."
                            + "\nType 'play' 4 times please.");
                    if (player.getDeckSize() < 3) {
                        System.out.println("\nYou don't have enough cards for a War.");
                        winMessage(bot);
                    } else if (player.getDeckSize() < 3) {
                        System.out.println("\nThe bot doesn't have enough cards for a War.");
                        winMessage(player);
                    }
                    for (int i = 0; i < 4; i++) {
                        playCards();
                    }
                } else if (playerCard.getRank() > botCard.getRank()) {
                    System.out.println("\n"
                            + "\nYou win the round!"
                            + "\nType 'take' to take your cards.");
                    ans = scanner.next();
                    do {
                        if (ans.contains("take")) {
                            answered = true;
                        } else {
                            System.out.println("Please just type 'take' next time, it will not change anything.");
                            answered = false;
                        }
                    } while (answered = false);
                    takeCards(player);
                    inWar = false;
                    cardsPlayed.clear();
                } else if (botCard.getRank() > playerCard.getRank()) {
                    System.out.println("Bot wins the round!");
                    takeCards(bot);
                    inWar = false;
                    cardsPlayed.clear();
                }
            } while (inWar);

            System.out.println("\n"
                    + "\nYour Deck: " + player.getDeckSize()
                    + "\nBot's Deck: " + bot.getDeckSize()
                    + "\n-------------------------------------------------------------------------"
                    + "\n ");
        }
        if (player.getDeckSize() > bot.getDeckSize()) {
            winMessage(player);
        } else {
            winMessage(bot);
        }
    }

    public void playCards() {
        System.out.println(" ");
        ans = scanner.next();
        do {
            if (ans.contains("play")) {
                answered = true;
            } else {
                System.out.println("Please just type 'play' next time, it will not change anything.");
            }
        } while (answered = false);

        playerCard = player.dealCardFromDeck();
        botCard = bot.dealCardFromDeck();

        cardsPlayed.add(playerCard);
        cardsPlayed.add(botCard);

        System.out.println("You played:" + playerCard.getFace() + " of " + playerCard.getSuit()
                + "\nBot played: " + botCard.getFace() + " of " + botCard.getSuit());
    }

    public void takeCards(Deck hand) {
        for (int i = 0; i < cardsPlayed.size(); i++) {
            hand.addCardToDeck(cardsPlayed.get(i));
        }
    }

    public void winMessage(Deck hand) {
        answered = false;
        do {
            System.out.println("Looks like " + hand + " won! Play again?"
                    + "\n Type 'y' for yes or 'n' for no.");
            if (ans.contains("y")) {
                answered = true;
                deck = new Deck();
                deck.initializeNewDeck();
                deck.shuffle();

                Deck[] halves = deck.dealDeck();
                player = halves[0];
                bot = halves[1];
                runEventLoop();
            } else if (ans.contains("n")) {
                answered = true;
                playing = false;
            }
        } while (answered = false);
    }

    /**
     * The main method is called when Java starts your program
     */
    public static void main(String[] args) {
        War war = new War();
    }
}
