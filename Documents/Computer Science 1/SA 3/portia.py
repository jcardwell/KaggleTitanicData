#this program will determine the year in which Brutus' balance first exceeds Portia's

#define the interest rates for Brutus and Portia
BRUTUS_RATE=1.05
PORTIA_RATE=1.04

#define the starting amount for each person
BRUTUS_MONEY=1.00
PORTIA_MONEY=100000.00

#define the current year, 0 AD
CURRENT_YEAR=0.

#define a while loop that will continue to compound the savings until Brutus has more money than Portia
while BRUTUS_MONEY<=PORTIA_MONEY:
    
    #defines an expression for the amount of money that Brutus has
    BRUTUS_MONEY=(BRUTUS_MONEY)*(BRUTUS_RATE) 
    
    #defines an expression for the amount of money that Portia has
    PORTIA_MONEY=(PORTIA_MONEY)*(PORTIA_RATE)
    
    #defines the current year
    CURRENT_YEAR=CURRENT_YEAR+1
   
#creates a print statement that will print the year that Brutus surpasses Portia, as well as the two balances
print "The year is "+ str(CURRENT_YEAR) +" and Brutus has "+ str(BRUTUS_MONEY)+" dollars, while Portia has "+ str(PORTIA_MONEY)+" dollars."
    