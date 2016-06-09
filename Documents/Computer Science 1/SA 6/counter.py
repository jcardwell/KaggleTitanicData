#Jack Cardwell
#10/14/14
#Professor Farid

#create the Counter class
class Counter:
    def __init__(self , limit, initial=0, min_digits=1):
       
        #define the variables that the class holds, except for the initial value which is defined below
        self.limit=limit-1
        self.min_digits=min_digits
        
        #define the initial values based on two qualifications
        if initial>=0 and initial<=limit-1:
            self.initial=initial
        else:
            print "Error: initial value must be greater than 0 and at least one less than the limit"
            self.initial=self.limit-1
       
        #define the initial current value to be the starting point
        self.current_value=self.initial
        
    #return the current value as an integer        
    def get_value(self):
        return self.current_value
    
    #return the counter value as a string
    def __str__(self):
        
        #determine how many zeroes to print to the left of the value
        self.necessary_zeroes=self.min_digits-len(str(self.current_value))
    
        return (self.necessary_zeroes*("0"))+str(self.current_value)
        
    #define a function to count down from the initial value
    def tick(self):
            if self.current_value>0:
                self.current_value=self.current_value-1
                return False
            
            elif self.current_value<=0:
                self.current_value=self.limit
                return True
        