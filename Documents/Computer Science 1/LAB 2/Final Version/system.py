#Jack Cardwell
#10/22/14
#Professor Farid

#import necessary functions from math
from math import *
GRAVITATIONAL_CONSTANT=6.67384e-11
#create the system class in order to keep track of all of the body objects
class System:    
    #create the system constructor
    def __init__(self, body_list):
        self.body_list=body_list
    
    #create the draw function in order to draw each of the parts of the body
    def draw(self, cx, cy, pixels_per_meter):
        for body in self.body_list:
            body.draw(cx, cy, pixels_per_meter)
            
    
    #create the update function that passes in the components of acceleration and updates position and velocity
    def update(self,timestep):
        for n in range(len(self.body_list)):
            
            #update the acceleration to pass into the velocity function
            (ax, ay)=self.compute_acceleration(n)
            
            #update velocity in order to use in position function
            self.body_list[n].update_velocity(ax, ay, timestep)
            
            #update position
            self.body_list[n].update_position(timestep)
            

    #create a function to compute the acceleration
    def compute_acceleration(self, n):
        
        #initialize the total acceleration components to 0, so that they can store value within the loop later
        total_acceleration_x = 0
        total_acceleration_y = 0
        
        #create a for loop to cycle through everything but the object at position n
        for number in range (len(self.body_list)) :
            
            #compare all objects except the same object to itself
            if number!= n :
                
                #find the distance between the two objects compared and store them
                dx=self.body_list[number].get_x()-self.body_list[n].get_x()
                dy=self.body_list[number].get_y()-self.body_list[n].get_y()
            
                #store the radius and radius squared for usage in the acceleration function
                radius = sqrt((dx*dx)+(dy*dy))
                #self.body_list[n].RADIUS_SQUARED=(self.body_list[n].RADIUS)**2
            
                #compute the total acceleration on self.body_list[n] by self.body_list(object)
                new_acceleration = (((GRAVITATIONAL_CONSTANT)*(self.body_list[number].get_mass()/(radius**2))))
            
                #break up acceleration into x and y components
                total_acceleration_x += new_acceleration * (dx/radius)
                total_acceleration_y += new_acceleration * (dy/radius)
            
        return total_acceleration_x, total_acceleration_y
    
    
    
        
        
            
            
        
    