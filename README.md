Group 2:
   Juan Carlos Hidalgo
   Anghelo Leonardo Zambrana Quinteros
   Gaston Gutierrez Condori
   Hugo Andres Oropeza Villalpando

1. User Data: (Person)
   Name: Complete name, only 1 name and 1 lastname
   IC(Identity Card Number): 8 digits
   Phone Number: With country prefix and 8 dígits
   Age (in years): 18 <= age <=100
   Height (in meters): 1,10 m <= height <= 2,10 m
   Civil Status (Single, Married, Divorced, Widowed)
   Number of couples:(>= 0)
   Example: Paco Perez, 75253041, +552 57976777, 34, 1,65 m, Divorced, 5

2. Item Data: (Melee and throwable weapons)
   Name: 2 words maximum
   Material: 1 word
   Range (Large, Median o Short)
   Damage points (over 100)
   have handle(Boolean): it means that has a place to hold the weapon
   is throwable (Boolean): it means that in the middle of a fight it is profitable or not to throw it
   is sharp (Boolean): It means that cuts the skin easily when using it
   Example: Knife, Iron, Short, 20/100, True, True, True

Loading Type
We will receive our data from .csv files

3. Recommendation;
   Given a user X, return the top 5 similar users based on their information.
   Age, Civil Status and Number of Couples(using Nearest Neighbor matching)
   Generate recommendations for a user based on their preferences or similarity with other users or the items acquired/consumed by the user.
   We will make the recommendation based on the Long Common Sequence similarity between user’s name and weapon’s name, the maximum similarity found will be be the weapon recommended to the user, example:

User name = Axel
Weapon name = Axe
Weapon name2 = Sword
Weapon name3 = Katana
Long Common Sequence = 3
then: recommended weapon for Axel is Axe

4 About the implementation
1. Use the pre-processing and tokenization that already exists in the library and make sure to implement new ones. (Use at least two that already exist and two new ones.)
   As the new tokenization we will implement:
   For Enums: SINGLE = 2 (Int value for Enum SINGLE)
   For Booleans: True = 1 (Int value for Boolean SINGLE)
   As the new preprocessing we will implement one for boolean and the Enums of the new Item and User attributes.
   For Enums: we will use edit distance to solve misspellings on the csv rounded by 75% and give every Enum Value an Int value, example:
   Civil status = “Singles” => equality % = 85.71% => Civil Status = SINGLE
   For Boolean: pass the String to a Boolean Value example:
   isSharp = “True” => isSharp = True

Matching Criteria For Enums: we will use the exact equal of the Int value for
every Enum Value, example:
Enum value = Single => Int Value single = 2, Enum value = Married => Int Value married= 4
if Int Value single != Int Value married => score = 0
if Int Value single == Int Value single => score = 1

Matching Criteria for Boolean: we will use the exact equal of the Int value for
every Boolean Value, example:
Boolean value = True => Int Value True = 1, Boolean Value = False => Int Value False = 0
if Int Value True != Int Value False => score = 0
if Int Value False == Int Value False => score = 1


2. Implement one of the matching algorithms you mentioned in the previous task.(Compare the algorithm implemented with the one already in the library.)
   a) Compare score and time complexity.
   We implemented the Long Common Sequence Algorithm with  complexity O(n^2) because of the Dynamic programming, compared to equalizer match  complexity of O(n).

