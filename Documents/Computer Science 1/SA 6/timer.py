#Jack Cardwell
#10/14/14
#Professor Farid

#import the counter class in order to count down in timer
from counter import Counter

#create the class Timer 
class Timer:
    
    def __init__(self, hours, minutes, seconds):
        #create self variables for all of the input parameters
        self.hours=hours
        self.minutes=minutes
        self.seconds=seconds
        
        #create a counter in order to count down the hours input from the initialization function
        self.hour_counter=Counter(24, self.hours, 2)
        
        #create a counter in order to count down the minutes input from the initialization function
        self.minute_counter=Counter(60, self.minutes, 2)
        
        #create a counter in order to count down the minutes input from the initialization function        global seconds_counter
        self.seconds_counter=Counter(60,self.seconds, 2)
        
    #create a function to return a string of the given Timer object
    def __str__(self):
    
        #return a string of the current time in the format hh:mm:ss
        return str(self.hour_counter)+ ":" + str(self.minute_counter)+ ":" + str(self.seconds_counter)
    
    #create a function to count down the time
    def tick(self):
        
        if self.seconds_counter.tick():
            if self.minute_counter.tick():
                self.hour_counter.tick()
            
    #create a function that returns a boolean if the timer struck zero
    def is_zero(self):
        
        if self.seconds_counter.get_value()==0 and self.minute_counter.get_value()==0 and self.hour_counter.get_value()==0:
            return True
        
        else:
            return False
        
        
    
        