# Spell-Checker
This program finds incorrectly spelled words in a text file and offers suggested replacements. To run, there should be a dictionary text file in the same folder as the program whose name is given to the program as the first command line argument. The dictionary file should have a list of valid words that the user desires, with each word on a new line with no punctuation. 

Word Class: 
Each word object has a string data field for its word, punctuation, replacement, an arraylist of suggestions for its replacement, and a boolean ignored. When a word is created, its word is set as its key, its boolean” ignored” is set to false, and its replacement string is set to the word. 


SpellChecker Class:
The SpellChecker class works by reading in the dictionary and hashing each word in the dictionary into a hashtable. Then, the user is prompted to enter a file and then listIncorrectWords is called on that file to out write a new file with all of the misspelled words in that file (see below for how that method works). The user is then prompted to either print all words, enter a new file, or quit. If the user enters “p” to to print all the words, then printWords is called. See below for how printWords works. 


PrintWords method: 
The printWords method works by taking in the hashtable dictionary and the name of the file it wants to go through as its parameters. It creates a new hash table to store all of the wrong words. It then scans through the file line by line, and keeps a counter of the line so that it can track the correct line that each word appears on. For each line of the file, the method creates an array of elements in the line separated by “ “ using the String.split method. For each element, it finds the letters and punctuation for that element, and creates a word object with those attributes. It then checks if that word is not in the dictionary and if it is not, it checks if the word is already in the wrongWords hashtable. If both are false, it inserts the word into the wrongWords hashtable. If the user wants to ignore the word, it sets that word’s boolean to ignored. If the user wants to replace the word, it calls the replace methods (see below how they work) to generate possible replacements for the word and prompt the user to see if they want to choose a replacement. If they do, the word object’s replaced data field will be set to the replacement. If the word already is in the wrongWords hashtable, it gets the word object already in the table and then gives the user the same prompt as before. As each word is checked, it writes out the word according to the user’s choices in a new file, along with the punctuation and a space. If the word in the file does appear in the dictionary, then the code just writes out the word to the new file and continues on to the next word. 


ListIncorrectWords method:
This method writes out the file of all the misspelled words in the order they appear along with their line number. It does this similarly to how it writes the other file, except it only has to check if each word that appears in the file is in the dictionary. If it is not, then the code writes into the new file the misspelled word along with the line number.


Replacer method: 
The replacer method words by taking in the word object and hashtable dictionary. Using the word objects getter method for its key, it loops through the key letter by letter. It creates an array of chars representing the string’s contents. For each index of the array, it replaces that letter with every letter of the alphabet, checking to see if any of the replacements forms a word in the dictionary. If it does, it adds that word the word object’s arraylist of suggested replacements. 


Delete method: 
This method works by getting the word data field for a word object, and looping through that string to delete each letter and see if the resulting word appears in the dictionary. It does this by creating substrings of the word data field and leaving out the i’th index of the string. It then checks if each resulting word is contained in the dictionary, and if it is, it adds that word to the word object’s arraylist of suggested replacements.
