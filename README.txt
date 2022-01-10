General Problem - Create a graphical user interface(GUI) for the investment handler program created for Assignment 1 and 2.
The interface should be able to buy, sell , search and update the investments.

Assumptions - None ( Program performs all functions as per required and mentioned in the assiment discription)
Limitations - None( Met all the condtitions as per the requirment of the program for the assignment)

User Guide-
Main program run file: Main.java
Supporting java files: Window.java Investment.java Stock.java MutualFund.java
Compiling: javac Main.java Window.java Stock.java MutualFund.java
Run: java Main

Test Plan

Initially, opened all the menu items from commands menu, all the panels looked fine with no values. Clicked add in commands menu. Added 100 Stocks of APPL at 150 price. Clicked buy,
Stock added succesfully message in the message Area. Added a mutual fund FD at price 50. Again the investment added succesfully. Tried adding a Stock with no symbol
and negative value, Invalid input error in the message area. 
Opened the sell panel. Tested the inputs with null or negative values. Finally, sold 150 stocks of APPL at 200. Went to update panel. The next and previous buttons
work fine. Updated the price of FD to 35. 
The gain panel shows a total gain of 3425.2988$ with individual gains in the message area. Finally tested the search panel. Showed correct results with all inputs,
only symbol FD, only low range, only high range, both low and high range

Possible Improvments -  Could have made the interface look better with proper spacing


