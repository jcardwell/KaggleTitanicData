#Jack Cardwell
#Computer Science 1
#Professor Farid

#writes a program that will solve and arithmetic series the "long" way and by an equation and then compare the two answers
def arithmetic_series(n):
    total=0
    for number in range(1,n+1):
        total=number+total
    return total == ((n*(n+1))/2)

#create an infinite loop to keep asking the user for more data to try
while True:
    #create a variable assignment to store the number that the user enters
    data=int(raw_input("Please enter a number."))
    
    #filter the numbers so that only positive numbers get run through the program
    if data<=0:
        print "Error: your input must be greater than 0!"
    
    else:
        #prints if the number works out OK in the function and the long way
        if arithmetic_series(data):
            print str(data)+" works out OK!"
        else:
            print str(data)+ "does not work OK!"

        
    
