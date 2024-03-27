# final-project
1. tokenizer for Paths and phone numbers
a) there is a tokenizer which can be used for the paths so is not necessary
   to implement a new one.
b) It can recognize numbers I did the following, it takes an input string str and returns a string containing the phone numbers found in the input string, with the hyphens removed.  
   

2. Review which matching algorithm is implemented.
   
a) Are there any other matchings that can be implemented?

- One of these would be, Matching based on edit distances, or Common Substring Matching.


b) If so, what will be the idea, and what will be its use?
- Matching based on edit distances would basically be to measure the similarity between two strings, analyzing 
the minimum amount to convert one string into another, this 
could be achieved by insertion, deletion or substitution of 
characters. This can be useful to evaluate the correct 
spelling of a word or to see if two strings can be written 
slightly differently but can mean the same thing.   

Matching based on Common Substring Matching would involve finding the longest common 
substring between two strings and calculating a score based on the length of that substring. 
This method would be useful for identifying similarities between strings where a significant 
portion is identical or similar. It could be applied in various scenarios such as spell checking, 
identifying shared elements in text, or detecting similarities in sequences of characters.


c) If we need to add another matching algorithm, review and explain how that can be
   done.

- If we would like to add our implementation of edit distance based matching or 
Common Substring Matching, it would be as follows: once we have the code with the implementation 
done, we would have to modify the MatchService class to include the new method and allow 
users to choose between different matching algorithms, including edit distance based matching 
or Common Substring Matching.
different matching algorithms, including edit distance based matching.
