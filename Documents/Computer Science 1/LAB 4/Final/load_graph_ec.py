#Jack Cardwell
#11/14/14
#Professor Farid

#import the class function, Vertex, from vertex.py
from vertex_ec import Vertex

#define the function load graph
def load_graph(text_file):
    
    #create an empty dictionary to hold all of the vertex objects
    list_places={}
    
    #open up the text file containing the information
    dartmouth_places=open(str(text_file), "r")
    
    #for each line of dartmouth_places, store the information to a vertex object
    for line in dartmouth_places:
        
        first_split=line.split(";")
        
        #split the third part of first_split by a comma in order to return the coordinates
       
        coordinates_split=first_split[2].split(",")
        
        #add the information in the line to a vertex object and store that object to the dictionary list_places
        new_place=Vertex(first_split[0].strip(),coordinates_split[0].strip(),coordinates_split[1].strip())
        
        list_places[str(first_split[0].strip())]=new_place
    
    #close and reopen the file before looping through the file again
    dartmouth_places.close()
    dartmouth_places_2=open(str(text_file), "r")
    
    #loop through the file again   
    for line in dartmouth_places_2:
        
        #split the line up initially by semicolon and then comma

        semicolon_split=line.split(";")
        
        #split up the adjacent places part by comma
        adjacent_list=semicolon_split[1].split(",")
        
        #loop through the adjacent list created by the split, searching for each object in the dictionary and then linking it to the vertex in the line of code
        for place in adjacent_list:
            
            #append this object to the adjacency list of the vertex object in line
            list_places[semicolon_split[0]].adjacency_list.append(list_places[place.strip()])
            

    dartmouth_places.close() 

    return list_places


        