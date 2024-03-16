/* Name: NG
Date: 3/15/24
Description: coding the Deck class functions-- deck, shuffle, draw, replenish, and size functions were all modified from previous war file
 * Source:
 * 1. I tried to use the pop method or find a similar method. After, I looked up how to remove an element from an array.
 * In the method I used, I stored a copy of the deck array without the last element as the original deck variable. I used this source to find the .copyOf() function. 
 * https://stackoverflow.com/questions/22332199/deleting-the-last-element-from-an-array
 * 
 * 2. I used the .toArray() command in this source because I needed to convert my discard pile (Array list) into the array for my new deck and shuffle it.  
 * https://www.geeksforgeeks.org/arraylist-in-java/#operations-performed-in-arraylist
 */

package Project_1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Deck {
	//making an array of objects
	private Card[] deck;    
	
	public Deck()
	{
		//making an array for all the color values for deck creation
		String[] colors = {"Blue", "Yellow", "Red", "Green"};
		
		//creating an empty/null array of 108 card values which is the deck
		deck = new Card[108];
		
		//index keeps track of the place in the 108 card array to insert cards. 
		int index = 0;

		//the uno deck has a weird arrangement, it starts with four 0 cards of each color
		for (int j = 0; j < 4; j++) {
			//creates new card object with rank and color specified by list and for loop
			Card new_card = new Card(0, colors[j]);
			
			//adds the new card at the correct index in the deck array
			deck[index] = new_card;
			index ++;
		}

		//13 = wild card, 14 = +4 wild card -- there is 4 of each card 
		for (int i = 13; i < 15; i ++) {
			for (int j = 0; j < 4; j++) {
				//card is given the color black (color set later with player choice)
				Card new_card = new Card(i, "Black");

				deck[index] = new_card;
				index ++;
			}
		}

		//The following cards are added to the deck twice
        for (int a = 0; a < 2; a++) {
			//numbered cards 1-9 are added of each color
			for (int j = 0; j < 4; j++) {
                for (int i = 1; i < 10; i++) {
                
                    Card new_card = new Card(i, colors[j]);
                    
                    deck[index] = new_card;
                    index ++;
                }
            }

			//10= skip, 11= reverse, 12= draw two, each color of these cards are added
            for (int i = 10; i < 13; i ++) {
                for (int j = 0; j < 4; j++) {
                    Card new_card = new Card(i, colors[j]);

                    deck[index] = new_card;
                    index ++;
                }
            }
        }
    }

	//swaps each card in the deck with a randomly generated other card index
	//the result of the deck size function is placed in the parameter because the discard pile
	//must be reshuffled when the player can no longer draw cards. This deck can have a varying number of cards. 
	public void shuffle(int card_number)
	{
		// creates random number generator and number variable
		Random randomNumbers = new Random();
		int num;

		//goes through each card in deck, generates random number to swap with, and uses swap function to replace them
		for (int i =0; i<card_number; i++) {
			num = randomNumbers.nextInt(0, card_number -1);
			swap(i,num);
		}
	}
	
	public Card draw()
	{
		//creates a card variable for the last card in the deck
		Card drawn_card = deck[deck.length-1];

		//replaces the deck with a copy of the deck that is one card shorter
		//Mistake found: I was accidentally cutting off two cards rather than one by subtracting 2 when creating the deck copy. 
		//this made the deck half the size when trying to print out all cards.
		deck = Arrays.copyOf(deck, deck.length-1); //Source #1  

		//returns the drawn card
		return drawn_card; 
	}
	
	public boolean isEmpty()
	{
		//Checks if their are card objects in the deck with length.
		//if there aren't any cards, it will return true. If there are, it will return false
		return deck.length == 0;
	}
	
	private void swap(int i, int j)
	{
		//given the index of the old card (i) and the new card to swap with (j)
		//creates temp variable for original card
		Card temp = deck[i];

		//replaces original card with new card
		deck[i] = deck[j];
		
		//new card replaced by old card
		deck[j] = temp;
	}

	//convert discard pile into an array and store as the new deck
	public void replenish( ArrayList<Card> cards) {
		//used source #2
		deck = cards.toArray(new Card[cards.size()]);
	}
	
	//needed a deck length for the for loop in the shuffle function because the discard pile needed to be reshuffled
	//but it varied in size.
	//a new method was necessary to access the length of the deck array. 
	public int size() {
		return deck.length;
	}
}
