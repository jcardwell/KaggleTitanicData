#Jack Cardwell
#10/14/14
#Professor Farid

#import all of the necessary functions from both the class and cs1lib
from cs1lib import *
from timer import Timer

#use the initialization function
timer1=Timer(1,30,0)

#creates a while loop to continue counting down while the timer has not struck zero
while not timer1.is_zero():
    
    #print the current time to the console
    print str(timer1)
    
    #count down by one second and then eventually one minute and then one hour
    timer1.tick()
    
    #run a function to determine if the timer is at 0
    timer1.is_zero()
    
    #sleep for a second so that you decrement as a timer would, by every second
    sleep(1)
    
#print the ending time 00:00:00
print str(timer1)
    