#Jack Cardwell
#9/24/14
#Computer Science 1
#Professor Farid

#this program will create a black pop-up window, in which the user can draw lines with different colors

#import all necessary functions from cs1lib
from cs1lib import *

#define the program
def create_chalkboard():
    
    #sets clear color to black and then clears the window each time the a new window opens
    set_clear_color(0,0,0)
    clear()
    
    #sets the default stroke color to white and enables smoothing to make the drawing better
    set_stroke_color(1,1,1)
    enable_smoothing()
    
    #sets stroke width to two pixels wide
    set_stroke_width(2)
    
    #defines a variable to remember where the pointer is, right as the user clicks the mouse
    old_x=mouse_x()
    old_y=mouse_y()
    
    #creates a while loop that will allow the user to draw, so long as they don't clear the window
    while not window_closed():
        
        #creates an if statement so that the mouse only draws when clicked and held
        if mouse_down():
            
            #draws the line at the mouse location defined before the while loop until the user lets go of the mouse
            draw_line(old_x,old_y,mouse_x(),mouse_y())
                   
        #stores the last mouse location before held, so that the user can draw from this point in the next part of the loop
        old_x=mouse_x()
        old_y=mouse_y()  
        
        #requests redraw in order to transfer the drawing from the buffer to the console
        request_redraw()
            
        #stops reading code for 1/50th of a second in order to give the user time to process the change
        sleep(.02)
        
        #if the user presses "r", then the color of the line will change to red
        if is_key_pressed("r"):
            set_stroke_color(1,0,0)
                
        #if the user presses "b", then the color of the line will change to blue
        elif is_key_pressed("b"):
            set_stroke_color(0,0,1)
                
        #if the user presses "g", then the color of the line will change to green
        elif is_key_pressed("g"):
            set_stroke_color(0,1,0)
                
        #if the user presses "e",  then the line will function as an eraser, because of the black line
        elif is_key_pressed("e"):
            set_stroke_color(0,0,0)
            
        #if the user presses "o", then the color of the line will change to orange
        elif is_key_pressed("o"):
            set_stroke_color(1,.5,0)
            
        #if the user presses "y", then the color of the line will change to yellow
        elif is_key_pressed("y"):
            set_stroke_color(1,1,0)
        
        #if the user presses "p", then the color of the line will change to purple
        elif is_key_pressed("p"):
            set_stroke_color(.25,0,.5)
                
        #if the user presses "E", then it will erase the screen.
        elif is_key_pressed("E"):
            clear() 
            set_stroke_color(1,1,1)
            
#opens a graphics window entitled "Chalkboard"
start_graphics(create_chalkboard,"Chalkboard")