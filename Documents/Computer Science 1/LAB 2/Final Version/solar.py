#Jack Cardwell
#10/22/14
#Professor Farid

#import all of the necessary classes and functions
from cs1lib import *
from system import System
from body import Body

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
    
    #store the bodies in a list to feed into the system
    solar=System([sun,mercury,venus,earth, mars])
    
    #set up the graphics window
    set_clear_color(0, 0, 0)    
    enable_smoothing()
    
    #keep the simulation running until the user closes the window
    while not window_closed(): 
        clear()
        
        #draw and update the simulation during each pass through the while loop
        solar.draw(WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2, PIXELS_PER_METER)
    
        solar.update(TIMESTEP*TIME_SCALE)
        
        
        request_redraw()
        sleep(TIMESTEP)
        
start_graphics(solar_system, "Solar System Simulator", WINDOW_WIDTH, WINDOW_HEIGHT)
        
    