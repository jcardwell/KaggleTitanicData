#Jack Cardwell
#11/5/14
#Professor Farid

#create the soldier class
class Soldier:
    
    #define the constructor to take as input a number that represents its position within the circle
    def __init__(self, number):
        #create the instance variables, including the ones that keep track of the "links"
        self.number=number
        self.next=None
        self.prev=None
        
    #define a kill function to print a statement about which soldier died. The two parameters are strings
    def kill(self, prefix, suffix):
        
        print prefix + " " + str(self.number) + " " + suffix
        
        #kill the soldier by getting rid of all references to the particular soldier
        self.next.prev=self.prev
        self.prev.next=self.next
        
       
        
        