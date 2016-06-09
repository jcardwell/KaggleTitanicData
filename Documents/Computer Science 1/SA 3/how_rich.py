
#this program will solve for the total money, accounting for compound interest after 2014

#represents the interest rate
INTEREST_RATE=1.05

# represents the initial amount of money
PRINCIPAL=1.

# represents the year, starting with year 0
CURRENT_YEAR=0.

#use a while statement to define a loop, as to calculate the interest for 2014 years
while CURRENT_YEAR<2014.:
    PRINCIPAL=(PRINCIPAL)*((INTEREST_RATE))
    CURRENT_YEAR=CURRENT_YEAR+1

#prints the balance of Brutus' money in 2014
print "The balance in " + str(CURRENT_YEAR) + " is " + str(PRINCIPAL) + " dollars."
    
    
    