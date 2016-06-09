#Jack Cardwell
#10/22/14
#Professor Farid

# binary_search.py
# Code provided for CS 1 Short Assignment 7.
# Performs binary search on a sorted list of random numbers.

from random import randint

# Perform binary search for key on the sublist of the_list
# starting at index left and going up to and including index right.
# If key appears in the_list, return the index where it appears.
# Otherwise, return None.
# Requires the_list to be sorted.
def binary_search(the_list, key, left = None, right = None):
   
    # If using the default parameters, then search the entire list.
    if left == None and right == None:
        left = 0
        right = len(the_list) - 1
    
    #find the midpoint of the list in order to use in to check the index
    midpoint=(left+right)/2
    
    #if there aren't any values in the list, return None because there is nothing to check, happens when left and right index pass each other
    if left>right:
        return None
    
    #if the value at the midpoint is equal to the value desired return that index
    elif the_list[midpoint]==key:
        return midpoint
    
    #if the value at the midpoint is less than the value desired, run binary_search again from just right of the midpoint on
    elif the_list[midpoint]<key:
        return binary_search(the_list, key, midpoint+1, right)
    
    #if the value at the midpoint is greater than the value desired, run binary_search again to just left of the midpoint
    elif the_list[midpoint]>key:
        return binary_search(the_list, key, left, midpoint-1)
         
        
# Driver code for binary search.
n = int(raw_input("How many items in the list? "))

# Make a list of n random ints.
i = 0
the_list = []
while i < n:
    the_list.append(randint(0, 1000))
    i += 1
    
# Binary search wants a sorted list.
the_list = sorted(the_list)
print "The list: " + str(the_list)

while True:
    key = raw_input("What value to search for? ('?' to see the list, 'q' to quit): ")
    
    if key == "?":
        print "The list: " + str(the_list)
    elif key == "q":
        break
    else:
        key = int(key)    
        index = binary_search(the_list, key)
        if index == None:
            print str(key) + " not found"
        else:
            print str(key) + " found at index " + str(index)
