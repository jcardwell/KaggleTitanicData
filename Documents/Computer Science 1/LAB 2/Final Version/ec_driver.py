#Jack Cardwell
#10/22/14
#Professor Farid

#import all of the necessary classes and functions
from cs1lib import *
from system import System
from body import Body
from random import *

#define all of the necessary constants for the program
WINDOW_WIDTH=700
WINDOW_HEIGHT=700

TIME_SCALE=2000000   #real seconds per simulation seconds
PIXELS_PER_METER=1.35/1e9 #computer pixels per meters

FRAME_RATE=30   #frames per second
TIMESTEP=1.0/FRAME_RATE #represents how many seconds pass for each step of the simulation

AU=1.49598e11   #meters/AU
EM=5.97219e24   #kg/earth mass
    
#create the program to feed into the graphics window
def solar_system():

    #initialize all of the body objects
    sun=Body(1.98892e30, 0, 0, 0, 0, 30, 1, 1, 0)
    mercury=Body(.06*EM, .3871*AU, 0, 0, 47890, 4, .5, .5, .5 )
    venus=Body(.82*EM, .733*AU, 0, 0, 35040, 6, 1, .5, 0)
    earth=Body(EM, AU, 0, 0, 29790, 8, 0, 0, 1)
    mars=Body(.11*EM, 1.524*AU, 0, 0, 24140, 10, 0, .75, 0)
   
    #create a variable for the list
    bodies=[sun,mercury,venus,earth, mars]
    
    #store the bodies in a list to feed into the system
    solar=System(bodies)
    
    #set up the graphics window
    set_clear_color(0, 0, 0)    
    enable_smoothing()
    
    #keep the simulation running until the user closes the window
    while not window_closed(): 
        
        #create an if statement to create a new object and show how gravity affects it
        if mouse_down():
            #compute the x and y distances from the Sun
            dx=mouse_x()-WINDOW_WIDTH/2
            dy=mouse_y()-WINDOW_HEIGHT/2
            
            #convert to meters 
            dx_meters=dx/PIXELS_PER_METER
            dy_meters=dy/PIXELS_PER_METER
            
            new_object=Body(uniform(.1,.8)*EM, dx_meters, dy_meters, 0, randint(-45000,0), 10,  uniform(0,1), uniform(0,1), uniform(0,1))
            
            bodies.append(new_object)
        
        #write a series of if statements to move the sun around 
        elif is_key_pressed("a"):
            sun.x=sun.x-(.02*AU)
        elif is_key_pressed("d"):
            sun.x=sun.x+(.02*AU)
        elif is_key_pressed("w"):
            sun.y=sun.y-(.02*AU)
        elif is_key_pressed("s"):
            sun.y=sun.y+(.02*AU)
    
        #write a series of if statements to change the sun's mass
        elif is_key_pressed("l"):
            sun.mass=sun.mass-9e28
        elif is_key_pressed("m"):
            sun.mass=sun.mass+9e28
            
        
        clear()
        
        #draw and update the simulation during each pass through the while loop
        solar.draw(WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2, PIXELS_PER_METER)
    
        solar.update(TIMESTEP*TIME_SCALE)
        
        
        request_redraw()
        sleep(TIMESTEP)
        
        
            
        
start_graphics(solar_system, "Solar System Simulator", WINDOW_WIDTH, WINDOW_HEIGHT)
        
    