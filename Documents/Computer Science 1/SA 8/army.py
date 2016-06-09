#Jack Cardwell
#11/5/14
#Professor Farid

#import the soldier class in order to utilize the functions
from soldier import Soldier

#create an army class in order to consolidate the functions
class Army:
    
    #create the constructor that takes as input n, the total number of soldiers, including the current victim, k
    def __init__(self, n, k):
        
        #create an instance variable to bind the number of soldiers in the army to the Army object
        self.n=int(n)
        self.k=int(k)
        
        #create a counter, starting at i=2, and going up until self.n
        i=2
        
        #create the head of the list that initially points to itself
        head=Soldier(1)
        head.next=head
        head.prev=head
        
        #use a variable to keep track of the head of the list, acts as a counter
        self.victim=head 
        
        #construct the list and tie all of the objects together
        while i<=self.n:
            
            #create a new object that ties to the object before it and the one next in line
            self.victim.next=Soldier(i)
            self.victim.next.prev=self.victim
            
            #increment which object is equal to x
            self.victim=self.victim.next
            
            #increment the driver
            i+=1
            
        #complete the list by referring back to the head of the list and showing the head of the list the end of the list
        self.victim.next=head
        head.prev=self.victim    
    
        self.advance(self.k)
    
    #create an advance function that takes a number as input and advances the kill cycle skipping that number of soldiers
    def advance(self,k):
        
        #create a counter
        i=0
        
        #create a while loop to point to the next victim using k
        while i<k:
            self.victim=self.victim.next
            
            #increment the counter
            i+=1
            
    #create a function to kill all of the soldiers
    def kill_all(self):
        
        #as long as there is a soldier, keep killing the soldiers
        while self.n>0:
            
            #advance and kill the current victim
            self.victim.kill("Soldier", "has died.")
            self.advance(self.k)
            
            
            #decrement the number of soldiers alive
            self.n-=1
    
        
                
                
                
        
        