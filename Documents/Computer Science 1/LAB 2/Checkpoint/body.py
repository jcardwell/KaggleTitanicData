from cs1lib import *

#create a class named "Body" that will be responsible for each of the individual planets
class Body:
    
    #create the construction definition
    def __init__(self, mass, x, y, vx, vy, pixel_radius, r, g, b):
        self.mass=mass
        self.x=x
        self.y=y
        self.vx=vx
        self.vy=vy
        self.pixel_radius=pixel_radius
        self.r=r
        self.g=g
        self.b=b
        
    #create functions to return the instance variables to other parts of the program
    def get_mass(self):
        return self.mass
    
    def get_x(self):
        return self.x
    
    def get_y(self):
        return self.y
    
    #create a draw function in order to draw all of the objects within this class
    def draw(self, cx, cy, pixels_per_meter):
        self.cx=cx
        self.cy=cy
        
        #disable stroke and set the fill color
        disable_stroke()
        
        #convert from meters to pixels
        x=(self.x*pixels_per_meter)+cx
        y=(self.y*pixels_per_meter)+cy
        
        #draw a circle at the adjusted x and y, with a radius of pixel_radius
        set_fill_color(self.r, self.g, self.b)
        draw_circle(x,y,self.pixel_radius)
        
    #update the x and y coordinates based on the current velocity       
    def update_position(self, timestep):
        
        self.x=self.x + (timestep*self.vx)
        
        self.y=self.y + (timestep*self.vy)
        
        return self.x, self.y
    
    #update the velocity based on the net acceleration in each direction, which will be passed in by the system class
    def update_velocity(self, ax, ay, timestep):
        
        self.vx=self.vx + (ax*timestep)
        
        self.vy=self.vy + (ay*timestep)
        
        return self.vx, self.vy
        
        
    
        
    
        
        