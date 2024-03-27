# final-project
1. tokenizer for Paths and phone numbers
a) there is a tokenizer which can be used for the paths so is not necessary
   to implement a new one.
   

2. Review which matching algorithm is implemented.
   
a) Are there any other matchings that can be implemented?

- One of these would be,Matching based on edit distances,

b) If so, what will be the idea, and what will be its use?
- Matching based on edit distances would basically be to measure the similarity between two strings, analyzing 
the minimum amount to convert one string into another, this 
could be achieved by insertion, deletion or substitution of 
characters. This can be useful to evaluate the correct 
spelling of a word or to see if two strings can be written 
slightly differently but can mean the same thing.   

c) If we need to add another matching algorithm, review and explain how that can be
   done.

- If we would like to add our implementation of edit distance based matching, it would be as 
follows, once we have the code with the implementation done we would have to modify 
the MatchService class to include the new method and allow users to choose between 
different matching algorithms, including edit distance based matching.