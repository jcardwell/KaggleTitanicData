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
        for body in self.body_list:
            body.update_position(timestep)
            
    