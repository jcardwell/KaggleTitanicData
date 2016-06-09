#Jack Cardwell
#11/7/2014
#Professor Farid

#create a broad function to swap two items within a list
def swap(the_list, i, j):
    temp=the_list[i]
    the_list[i]=the_list[j]
    the_list[j]=temp
    

#define the partition function
def partition(the_list, p, r, compare_func):
    
    #value of the pivot
    pivot=the_list[r]
    
    #create the two pointers, j and i
    i=p-1
    j=p
    
    #create a while loop to index through the whole list
    
    while j<r:
        #create the conditionals
         
        if compare_func(the_list[j],pivot):
            i+=1
            swap(the_list,i,j)
        j+=1

    swap(the_list,r, i+1)
    
    #index of the pivot=q=i+1
    #return the index of the pivot
    return i+1

def quicksort(the_list, p, r, compare_func):
    #continue so long as there are 2 or more objects in the list
    if p<r:
        
        #use the partition function and then recursively call quick sort on each half of the list
        q=partition(the_list, p, r, compare_func)
        
        quicksort(the_list, p, q-1, compare_func)
        
        quicksort(the_list, q+1, r, compare_func)
        
#create the sort function 
def sort(the_list, compare_func):
    
    #set the initial p to 0 and r to the length of the list-1
    p=0
    r=len(the_list)-1
    
    #run the quick sort function on the whole list
    quicksort(the_list, p, r, compare_func)
 



    
    