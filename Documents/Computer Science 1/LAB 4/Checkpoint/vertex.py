#Jack Cardwell
#11/10/14
#Professor Farid

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
    
    