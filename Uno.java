/*Name: Nico Gonnella
Date: 3/15/24
Description: Card game assignment: Uno.
Sources Cited:
For the player's card hand and computer's card hand, I had to use an Array list because there was so much adding and removing. 
This website gave me all of the functions that the array list could use.  
https://www.geeksforgeeks.org/arraylist-in-java/#operations-performed-in-arraylist
*/

//importing packages used
package Project_1;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

//Uno game class
public class Uno {
    public static void main(String[] args) {
        //these variables are used throughout the entire game, so they were placed at the start
        //creates new deck
        Deck d = new Deck();
        //array list to hold the player's cards
        ArrayList<Card> player_hand = new ArrayList<Card>();
        //array list for computer's cards
        ArrayList<Card> computer_hand = new ArrayList<Card>();
        //card that will be matched when player or computer is discarding
        Card top_card;
        //array list for discard pile, is reshuffled and made back into deck.
        ArrayList<Card> discard_pile = new ArrayList<Card>();
        //when no card in the hand matches the top card, this card is drawn
        Card no_match_card;
        //determines if skip card was played on either player or computer
        Boolean skip_player = false;
        Boolean skip_computer = false;
        //array of colors used to verify if user entered correct color, and for computer to randomly pick a color during a wild card. 
        String[] colors = {"Red", "Blue", "Green", "Yellow"};
        //initialize random number generator when computer chooses random color after playing a wild card.
        Random randomNumbers = new Random();
        //number variable for random number
        int num;
        //indicates if there was a card found in the player/computer hand to be discarded
        Boolean found = false;
        //stores card value to be added to player/computer hand when a +2 or +4 card is played
        Card addedCard;
        //variable used to count rounds
        int round_counter = 1;
        //variable used to cound the number of cards picked up when no matches are found in hand to top card. 
        int card_drawn_counter = 1;
        //takes player input to choose what color they would like during a wild card. 
        Scanner keyboard = new Scanner(System.in);
        String input;
        //the following two booleans created to verify if user entered correct color.Try Catch didn't work because no error is thrown. It accepts any string. 
        //used to repeat while loop if correct format of color during wild card choice is not given
        Boolean input_loop = true;
        //triggers invalid color statement if correct color is not given
        Boolean value_present = false;
        
        //game instructions
        System.out.println("Uno Rules:\nEach player is dealt 7 cards. One card is flipped face up at the start creating the discard pile, and the deck is placed face down."
        + " A player may discard if their card matches the flipped card in color or number. If none of the cards in the player's hand"
        + "matches the flipped card, the player must draw cards from the deck until a matching card is found. Along with the numbered cards, there are trick cards placed in the deck. " 
        + "On the skip, 2+, and 4+ wild card the opponents turn is skipped.\nThis program is a game simulation where the user is required to "
        + "input values for the wild card color when the player plays it. Similar to the real life game, you can see all the cards, but the computer's hand.");
        
        //shuffles deck given deck size
        d.shuffle(d.size());

        //player and computer start with 7 cards in their hands. 
        for (int i = 0; i < 7; i++){
            player_hand.add(d.draw());
        }
        for (int i = 0; i < 7; i++){
            computer_hand.add(d.draw());
        }

        //the starting card is drawn from the deck and flipped for the first turn. This starts the discard pile. 
        top_card = d.draw();
        discard_pile.add(top_card);

        //The game ends when either the computer or the player has no more cards
        while (!(player_hand.isEmpty()) && !(computer_hand.isEmpty())) {
            //format properly and prints round number
            System.out.println("\n");
            System.out.println("Round: " + round_counter);
            round_counter++;

            //must check if a skip card was played
            if (skip_player == false) {
                //displayes players cards
                System.out.println("Player Cards: ");
                for (Card i : player_hand) {
                    System.out.print("(" + i.getRank() + ", " + i.getColor() + ") ");
                }
                //display top card
                System.out.println("\n");
                System.out.println("Top Card: " + "(" + top_card.getRank() + ", " + top_card.getColor() + ") ");

                //looks through each card in player hand for match to top card
                for (Card player_card : player_hand) {
                    //card must match in color, number, or be a wild card (black)
                    if ((player_card.getRank() == top_card.getRank()) || (player_card.getColor().equals(top_card.getColor())) || (player_card.getColor().equals("Black"))){
                        //card to match is now the card that the player discarded
                        top_card = player_card;
                        //display discarded card
                        System.out.println("Player discarded: " + "(" + top_card.getRank() + ", " + top_card.getColor() + ") ");
                        //add card to discard pile array list
                        discard_pile.add(top_card);
                        //remove played card from player's hand
                        player_hand.remove(player_card);
                        //if the player now has one card they must say Uno
                        if (player_hand.size() == 1) {
                            System.out.println("Player: UNO!");
                        }
                        //the card was matched, so they do not need to draw cards
                        found = true;
                        //break for loop when card is found in player hand
                        break;
                    }
                }
                //if the player did not have a card in their hand that matched the top card, they must draw cards
                if (!found) {
                    //check if the deck is empty before drawing
                    if (!(d.isEmpty())) {
                        //draw card from deck to match top card
                        no_match_card = d.draw();
                    }
                    else {
                        //deck is empty, must reshuffle
                        System.out.println("Deck Reshuffling...");
                        //stores discard pile as an array that is the new deck
                        d.replenish(discard_pile);
                        //shuffle discard pile by it's size
                        d.shuffle(d.size());
                        //draw card from deck to match top card
                        no_match_card = d.draw();
                    }
                    //add card drawn to hand
                    player_hand.add(no_match_card);

                    //repeat loop if card drawn doesn't match top card's rank or color and is not a wild card
                    while ((no_match_card.getRank() != top_card.getRank()) && (!(no_match_card.getColor().equals(top_card.getColor()))) && (!(no_match_card.getColor().equals("Black")))) {
                        //must check if deck is empty before drawing cards
                        if (!(d.isEmpty())) {
                            //draws card from deck and adds to hand until match found
                            no_match_card = d.draw();
                            player_hand.add(no_match_card);
                            //variable to keep track of drawn cards
                            card_drawn_counter++;
                        }
                        else {
                            //reshuffles discard pile, then goes to drawing cards and adding to hand until match found
                            System.out.println("Deck Reshuffling...");
                            d.replenish(discard_pile);
                            d.shuffle(d.size());
                        }
                    }

                    //black cards must be added differently to ensure color matching after choosing color
                    if (!(no_match_card.getColor().equals("Black"))) {
                        //after finding matching card by drawing from deck. The card must become the top card, be added to the discard pile, and be removed from the players hand. 
                        top_card = no_match_card;
                        discard_pile.add(top_card);
                        player_hand.remove(no_match_card);
                        //displays card discarded
                        System.out.println("Player discarded: " + "(" + top_card.getRank() + ", " + top_card.getColor() + ") ");
                    }
                    //displays number of cards picked up, resets card drawn counter
                    System.out.println("Cards Drawn: " + card_drawn_counter);
                    card_drawn_counter = 1;
                }
                //resets found boolean for next turn
                found = false;

                //must check if card discarded was a trick card
                if (top_card.getRank() == 10){
                    //skip card turns on the computer's skip boolean
                    skip_computer = true;
                    System.out.println("Computer Turn Skipped");
                }
                else if (top_card.getRank() == 11) {
                    //with two players, the reverse and skip card function the same, but different text is displayed. 
                    //uses skip boolean to skip computer's turn
                    skip_computer = true;
                    System.out.println("Reverse Card!");
                }
                else if (top_card.getRank() == 12) {
                    //draw two card is played
                    System.out.println("Computer Draw's Two!");
                    //Two times, if the deck is not empty, a card is drawn and added to the computers hand, 
                    //otherwise the deck is reshuffled and then cards are drawn and added to computer hand. 
                    for (int i = 0; i < 2; i++) {
                        if (!(d.isEmpty())) {
                            addedCard = d.draw();
                            computer_hand.add(addedCard);
                        }
                        else {
                            System.out.println("Deck Reshuffling...");
                            d.replenish(discard_pile);
                            d.shuffle(d.size());
                            addedCard = d.draw();
                            computer_hand.add(addedCard);
                        }
                    }
                    //computer turn is skipped with skip boolean
                    skip_computer = true;
                }
                else if (top_card.getRank() == 13) {
                    //wild card
                    System.out.println("Wild Card!");
                    //doesn't return an error so try catch doesn't work, but correct color needs to be written
                    //input_loop boolean is used to turn off loop when a valid color value is entered
                    while (input_loop) {
                        //asks user for color and scans keyboard for their input
                        System.out.println("Input your desired color (Red, Blue, Green, or Yellow): ");
                        input = keyboard.nextLine();

                        //searches color array to check if user entered correct format/color
                        for (String i : colors) {
                            //if input equals one of the given values in the color array
                            if (input.equals(i)){
                                //stops loop
                                input_loop = false;
                                //stops invalid statement from being displayed
                                value_present = true;
                                //creates card that is the wild card with the inputted color
                                Card black_card_color = new Card(13, input);
                                //stored as top card so the computer can now match with the correct color
                                top_card = black_card_color;
                            }
                        }    
                        //if input is not specified in color array, display invalid input and repeats to ask for input
                        if (!(value_present)) {
                            System.out.println("Invalid input!");
                        }    
                        
                    }
                    //reset loop and invalid input statement variables for next turn
                    input_loop = true;
                    value_present = false;

                    //when reshuffled, the deck must store a black card rather than user inputted.
                    //this creates a new black card value
                    Card black_card = new Card(13, "Black");
                    
                    //add black card to discard pile, and displays discard. 
                    discard_pile.add(black_card);
                    System.out.println("Player discarded: " + "(" + top_card.getRank() + ", " + top_card.getColor() + ") ");
                    
                }
                else if (top_card.getRank() == 14) {
                    //+4 Wild card
                    System.out.println("4+ Wild Card");
                    
                    //same loop as draw 2, but repeats 4 times to add cards to player hand or reshuffle then add cards
                    for (int i = 0; i < 4; i++) {
                        if (!(d.isEmpty())) {
                            addedCard = d.draw();
                            computer_hand.add(addedCard);
                        }
                        else {
                            System.out.println("Deck Reshuffling...");
                            d.replenish(discard_pile);
                            d.shuffle(d.size());
                            addedCard = d.draw();
                            computer_hand.add(addedCard);
                        }
                    }
                    //same loop as wild card to check if the correct color was inputted
                    while (input_loop) {
                        System.out.println("Input your desired color (Red, Blue, Green, or Yellow): ");
                        input = keyboard.nextLine();

                        for (String i : colors) {
                            if (input.equals(i)){
                                input_loop = false;
                                value_present = true;
                                //creates new 14 card with input
                                Card black_card_color = new Card(14, input);
                                top_card = black_card_color;
                            }
                        }    
                        if (!(value_present)) {
                            System.out.println("Invalid input!");
                        }    
                        
                    }
                    input_loop = true;
                    value_present = false;

                    //creates a 14 black card for the discard pile
                    Card black_card = new Card(14, "Black");
                    
                    discard_pile.add(black_card);
                    System.out.println("Player discarded: " + "(" + top_card.getRank() + ", " + top_card.getColor() + ") ");

                    //the +4 card also skips the opponents turn... yikes
                    System.out.println("Computer turn Skipped");
                    skip_computer = true;
                }
            }
            //if the above code is skipped due to the computer playing a skip card, it skips to this statement to display player skipped. 
            else {
                System.out.println("Player Skipped");
            }
            //reset player skipped variable
            skip_player = false;

            //computer's turn-- Mostly the same except computer variables are affected during discarding and player variables affected by trick cards.
            //Also, less text is outputted to terminal, because you aren't supposed to see the computer's cards other than saying uno, and computer 
            //discard is just the players top card. 

            //checks if skip card was used against computer. also skips if the player already won. 
            if ((skip_computer == false) && (!(player_hand.isEmpty()))){
                //goes through computer hand to check for matches to top card,stores new card as top card, removes from computer hand, and adds to discard pile
                for (Card computer_card : computer_hand) {
                    if ((computer_card.getRank() == top_card.getRank()) || (computer_card.getColor().equals(top_card.getColor())) || (computer_card.getColor().equals("Black"))){
                            top_card = computer_card;
                            discard_pile.add(top_card);
                            computer_hand.remove(computer_card);
                            if (computer_hand.size() == 1) {
                                System.out.println("Computer: UNO!");
                            }
                            found = true;
                            break;
                    }
                }
                //if card not found in computer hand, this loop draws cards from the deck and adds them to the computer's hand until a matching card is found.
                //this card is then stored as top card, removed from computer hand, and added to discard pile if it is not a black card
                if (!found) {
                    if (!(d.isEmpty())) {
                        no_match_card = d.draw();
                    }
                    else {
                        System.out.println("Deck Reshuffling...");
                        d.replenish(discard_pile);
                        d.shuffle(d.size());
                        no_match_card = d.draw();
                    }

                    computer_hand.add(no_match_card);

                    while ((no_match_card.getRank() != top_card.getRank()) && (!(no_match_card.getColor().equals(top_card.getColor()))) && (!(no_match_card.getColor().equals("Black")))) {
                        if (!(d.isEmpty())) {
                            no_match_card = d.draw();
                            computer_hand.add(no_match_card);
                        }
                        else {
                            System.out.println("Deck Reshuffling...");
                            d.replenish(discard_pile);
                            d.shuffle(d.size());
                        }
                    }
                    
                    if (!(no_match_card.getColor().equals("Black"))) {
                        top_card = no_match_card;
                        discard_pile.add(top_card);
                        computer_hand.remove(no_match_card);
                    }
                    
                }
                
                found = false;

                //checks if computer played a trick card. This is the same format as the trick card checking for the player. The only differences are that it negatively affects
                //the player (because card played by computer), and the color is chosen differently for the wild cards (explained in cards 13 and 14). 
                
                //skip card and reverse card are the same in skipping the player, text is displayed that player is skipped in the player skip if else loop
                if ((top_card.getRank() == 10) || (top_card.getRank() == 11)){
                    skip_player = true;
                }
                //player draws 2
                else if (top_card.getRank() == 12) {
                    for (int i = 0; i < 2; i++) {
                        if (!(d.isEmpty())) {
                            addedCard = d.draw();
                            player_hand.add(addedCard);
                        }
                        else {
                            System.out.println("Deck Reshuffling...");
                            d.replenish(discard_pile);
                            d.shuffle(d.size());
                            addedCard = d.draw();
                            player_hand.add(addedCard);
                        }
                    }
                    skip_player = true;
                }
                //wild card
                else if (top_card.getRank() == 13) {
                    //random number is generated
                    num = randomNumbers.nextInt(0, 3);
                    //number is an index for the color list to choose a random color for the next cards to be
                    Card black_card_color = new Card(13, colors[num]);
                    //Black card is still made to store in the discard pile when reshuffled
                    Card black_card = new Card(13, "Black");
                    
                    top_card = black_card_color;
                    discard_pile.add(black_card);
                }
                //player draw 4 wild card
                else if (top_card.getRank() == 14) {
                    
                    for (int i = 0; i < 4; i++) {
                        if (!(d.isEmpty())) {
                            addedCard = d.draw();
                            player_hand.add(addedCard);
                        }
                        else {
                            System.out.println("Deck Reshuffling...");
                            d.replenish(discard_pile);
                            d.shuffle(d.size());
                            addedCard = d.draw();
                            player_hand.add(addedCard);
                        }
                    }
                    
                    //uses the same random number generating code to choose an index for a color for the wild card.
                    num = randomNumbers.nextInt(0, 3);

                    //once again, stores the card with the color as top card and a card with the color black for the discard pile. 
                    Card black_card_color = new Card(14, colors[num]);
                    Card black_card = new Card(14, "Black");
                            
                    top_card = black_card_color;
                    discard_pile.add(black_card);

                    skip_player = true;
                }
            }
            //resets skip computer value
            skip_computer = false;
        }
        
        //The while loop stops when one of the hands is empty. This statement determines if the player or computer's hand is empty and displays who wins. 
        if (player_hand.isEmpty()) {
            System.out.println("Player Wins... Humanity is Saved!");
        }
        else if (computer_hand.isEmpty()) {
            System.out.println("Computer Wins... Earth is doomed!");
        }
    //I was receiving errors with the scanner value because it was used throughout the file multiple times or not at all.
    //This was solved by intializing the scanner at the top of the code and closing it at the bottom. 
    keyboard.close();  
    }
}