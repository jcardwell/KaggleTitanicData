#Jack Cardwell
#11/14/14
#Professor Farid

#import all of the necessary functions
from cs1lib import *
from load_graph_ec import *
from bfs_ec import *
from vertex_ec import *

#set up the constant variables
TIME=.02

#load the information for the graph from the load graph function
list_places=load_graph("dartmouth_graph.txt")

#create the drawing function
def main():
    start_x=1
    start_y=1
    start_vertex=None
    goal_vertex=None
    
    #load the image
    img = load_image ("dartmouth_map.png")
    
    #keep updating the window, so long as the window isn't closed
    while not window_closed():
    
        draw_image(img, 0, 0)
        
        #go through the whole list and draw the vertices and edges
        for place in list_places:
            list_places[place].draw_vertex(1,0,1)
            list_places[place].connect_all(0,1,0)
            list_places[place].write_name()
            
            if list_places[place].check_location(mouse_x(), mouse_y()):
                goal_vertex=list_places[place] 
            
        #store the mouse-x and mouse-y values if the mouse is pressed
        if mouse_down():
            start_x=mouse_x()
            start_y=mouse_y()
            
            #check all of the vertices in list_places to see if the mouse is over a vertex
            for vertex in list_places:
                
                if list_places[vertex].check_location(start_x, start_y):
                    start_vertex=list_places[vertex]
            
        #only call on the backpointer function if there is a start_vertex and goal_vertex
        if start_vertex!=None and goal_vertex!=None:
            
            #run the backpointer function and store the list of backpointers to backpointer
            backpointer=bfs(start_vertex, goal_vertex)
            
            current_vertex=goal_vertex
            
            #continue to connect vertices so long as a vertex has a backpointer as its value
            while backpointer[current_vertex]!=None:
                
                current_vertex.connect_edge(backpointer[current_vertex], 1, 0, 0)
                
                #increment the loop by changing current_vertex to the backpointer
                current_vertex=backpointer[current_vertex]
            
        #request redraw and sleep the window
        request_redraw()
        sleep(TIME)
        
start_graphics(main,"Dartmouth Map", 1012, 811)