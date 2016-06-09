#Jack Cardwell
#9/19/14
#Computer Science 1
#drawing.py
#opens up a window and draws Green Eggs and Ham

#imports necessary functions from cs1lib.py
from cs1lib import set_fill_color, set_stroke_color, set_clear_color, clear,set_stroke_width, draw_triangle, draw_circle, draw_ellipse, enable_stroke, disable_stroke, draw_line, draw_text, start_graphics

#defines a function to make the fill color white
def set_fill_white():
    set_fill_color(.937, .937, .937)

#defines a function to make the fill color green
def set_fill_green():
    set_fill_color(.435, .957, 0)
    
#defines a function to make the stroke color blue
def set_stroke_blue():
    set_stroke_color(.063, .122, .878)

#defines a function to make the stroke color black
def set_stroke_black():
    set_stroke_color(0,0,0)

#defines a function that makes the background red
def make_background_red():
    set_clear_color(1, .02, .02)
    clear()

#defines the function that draws the picture of green egss and ham
def draw_green_eggs_and_ham():
   
    #call up the red background
    make_background_red()
    
    #draw a plate as a white triangle with a black border
    enable_stroke()
    set_stroke_width(2)
    set_stroke_black()
    set_fill_white()
    draw_triangle(200,25,20,325,380,325)
    
    #draw a green ham steak with a white bone, neither have an outline
    #first draw the main part of the ham
    disable_stroke()
    set_fill_green()
    draw_ellipse(235,260,80,40)
    
    #then draw the white bone as a circle in the middle
    disable_stroke()
    set_fill_white()
    draw_circle(235,260,8)
    
    #draw the first of the two eggs
    #first start with the egg white
    enable_stroke()
    set_stroke_width(2)
    set_stroke_black()
    set_fill_white()
    draw_ellipse(200,150,35,20)
    
    #then draw the green yolk
    disable_stroke()
    set_fill_green()
    draw_circle(200,150,12)
    
    #draw the second egg
    #start with the egg white
    enable_stroke()
    set_stroke_width(2)
    set_stroke_black()
    set_fill_white()
    draw_ellipse(160,205,35,20)
   
    #then draw the green yolk
    disable_stroke()
    set_fill_green()
    draw_circle(160,205,12)
    
    #draw the blue fork
    #first draw the blue handle with no outline
    enable_stroke()
    set_stroke_width(2)
    set_stroke_blue()
    draw_line(253,150,253,220)
    
    #then draw the horizontal piece 
    draw_line(243,220,263,220)
    
    #finally draw the four tines, starting with the one on the left then working towards the right
    #first one
    set_stroke_width(2)
    draw_line(243,220,243,240)
    
    #second one
    draw_line(249,220,249,240)
    
    #third one
    draw_line(256,220,256,240)
    
    #fourth and last tine
    draw_line(263,220,263,240)
    
    #sign your name in the bottom left corner
    set_stroke_black()
    draw_text("Jack Cardwell",15,380)
    
start_graphics(draw_green_eggs_and_ham)

    
    
    
    


    