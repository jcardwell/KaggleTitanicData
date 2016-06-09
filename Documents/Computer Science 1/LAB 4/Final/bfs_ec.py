#Jack Cardwell
#11/14/14
#Professor Farid

#import all of the necessary functions
from collections import deque
from vertex_ec import *

#create the function
def bfs(start_vertex, goal_vertex):
    
    #initialize an empty dictionary to keep track of all of the back pointers, making sure the start_vertex points to none
    back_pointers={}
    
    back_pointers[start_vertex]=None
    
    #initialize the queue to only contain the start_vertex
    q=deque()
    q.append(start_vertex)
    
    #continue cycling through the queue so long as there are vertices within the queue
    while len(q)!=0:
    
        
        #delete the start_vertex from the queue and then add in the adjacent vertices
        x=q.popleft()
        
        #go through all of the vertices in the vertices' adjacency list
        for vertex in x.adjacency_list:
            
            #only progress if the vertex is not in the list of back pointers
            if vertex not in back_pointers:
                
                #add the adjacent vertex to the backpointers list and then add it to the queue
                back_pointers[vertex]=x
                
                q.append(vertex)
    
    #return the list of backpointers
    return back_pointers
    
        
        
    