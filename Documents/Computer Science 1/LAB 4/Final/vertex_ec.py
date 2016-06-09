#Jack Cardwell
#11/14/14
#Professor Farid

#import the necessary functions from cs1lib
from cs1lib import *

#define constants 
RADIUS=5    #radius of the vertices
WIDTH=3 #width of each line
TEXT_WIDTH=2 #define the width of the text
OFFSET=5    #define the offset of text from each vertex

class Vertex:
    
    
    #create the constructor
    def __init__(self, name, x, y):
        
        #store the instance variables
        self.name=str(name)
        self.x=int(x)
        self.y=int(y)
        self.adjacency_list=[]
        
    #create a string function that the user can call upon to receive certain information
    def __str__(self):
        
        #define the partial string to include everything but the components of the adjacency list
        partial_string=self.name + "; Location: "+ str(self.x) + ", " + str(self.y) + "; Adjacent Vertices: " 
        
        #create a counter to cycle through the adjacency list
        i=0
        
        #go through the list up until the second to last object with a comma at the end
        while i<=len(self.adjacency_list)-2:
            partial_string+=str(self.adjacency_list[i].name) + ", "
            
            #increment the counter
            i+=1
            
        #concatenate the last object name in the adjacency list without a comma
        partial_string+=str(self.adjacency_list[len(self.adjacency_list)-1].name)
        
        return partial_string
    
    #create a function to draw a circle at a vertex
    def draw_vertex(self,r,g,b):
        
        #set fill color to r, g, b, disable stroke, and draw the circle in the constant radius
        enable_smoothing()
        disable_stroke()
        set_fill_color(r, g, b)
        
        draw_circle(self.x, self.y, RADIUS)
    
    #write a function to write the name
    def write_name(self):
        
        #set up the draw text function
        enable_stroke()
        set_stroke_width(TEXT_WIDTH)
        set_stroke_color(0,0,1)
        
        #draw the name above the vertex
        draw_text(self.name,self.x, self.y-OFFSET)
        
    #create a function to draw an edge between two objects, given both, one as self, one as a vertex
    def connect_edge(self, vertex, r, g, b):
        
        #set up the color, enable stroke, set width, and then draw the line
        enable_stroke()
        set_stroke_width(WIDTH)
        set_stroke_color(r, g, b)
        
        draw_line(self.x, self.y, vertex.x, vertex.y)
        
    #create a function to draw an edge between the vertex and all objects within it's adjacency list
    def connect_all(self, r, g, b):
        
        #enable stroke, set stroke color, and draw the line
        enable_stroke()
        set_stroke_width(WIDTH)
        set_stroke_color(r, g, b)
        
        #create a for loop to cycle through all objects within the for loop
        for vertex in self.adjacency_list:
            #draw a line between the self vertex and all items in the adjacency list
            draw_line(self.x, self.y, vertex.x, vertex.y)
            
    #create a function that returns a boolean whether or not the mouse is over an object
    def check_location(self, x, y):
        
        #check to see if the coordinates are within the smallest square of the vertex
        if self.x-RADIUS<=x<=self.x+RADIUS and self.y-RADIUS<=y<=self.y+RADIUS:
            return True
        
        else:
            return False
    
    