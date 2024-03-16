/* Name: Nico Gonnella
Date: 3/15/24
Description: creating the Card Object
Source: War code-- color variable is only thing changed. 
 */

package Project_1;

//creating the card object 
public class Card {
	//initialize stored variables
	private int rank;
	private String color;
	
	//Whenever a card is called, it needs to store a rank/number and color instead of suit for uno
	Card(int r, String c)
	{
		rank = r;
		color = c;
	}

	//function returns the card number
	public int getRank()
	{
		return rank;
	}
	
	//function returns a string for card color
	public String getColor()
	{
		return color;
	}
}