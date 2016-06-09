#Jack Cardwell
#11/7/2014
#Professor Farid

#import the necessary functions 
from cs1lib import *
from city import City
from quicksort import *

#set the constants up to use later
WINDOW_WIDTH=720
WINDOW_HEIGHT=360
DOT_RADIUS=5    #radius of each of the dots on the map
WINDOW_CENTER_X=WINDOW_WIDTH/2
WINDOW_CENTER_Y=WINDOW_HEIGHT/2
PIXELS_PER_DEGREE=2     #conversion factor to convert from longitude/latitude to pixels
TIME=.75        #time to sleep between loops of the draw function
NUMBER_OF_CITIES=50     #the number of most populous cities to draw


#open the input file "world_cities.txt"
in_file= open("world_cities.txt","r")

#create a list of the city objects, initially empty, to be added to later on to represent each object
object_list=[]

#go through the data line by line and separate out each part into a list
for line in in_file:
    
    #split the object then strip it    
    information=line.strip()
    city_information=information.split(",")
    
    #create a city object using this data
    object=City(city_information[0],city_information[1],city_information[2],city_information[3],city_information[4],city_information[5])
    
    #append each object to a list of the objects
    object_list.append(object)

#close the input file
in_file.close()


#create the definition of the compare function
def compare_population(j, pivot):
    if j.population>pivot.population:
        return True
    else:
        return False

#sort the objects in object_list by population, using compare_population function defined above
sort(object_list,compare_population)

#define an update function to update the location of each object within the loop
def update_location(object):
    #store the equation for the coordinates of the dot
    dot_x=(object.longitude*PIXELS_PER_DEGREE)+WINDOW_CENTER_X
    dot_y=(object.latitude*PIXELS_PER_DEGREE)+WINDOW_CENTER_Y
    
    return dot_x, dot_y

#define a function draw the dots of each population location
def draw_dot(object, dot_x, dot_y):
    #set up the details of the draw_circle function
    disable_stroke()
    enable_smoothing()
    set_fill_color(.5, 0, .5)
    
    draw_circle(dot_x, dot_y , DOT_RADIUS)
    
    
#create the function that start graphics will call in order to open the window
def graphics():
    
    #load the image of the background
    img = load_image ("world.png")
    
    #create a while loop in order to update the graphics window every time a new item is drawn, so long as the window isn't closed
    while not window_closed():
        
        #draw the background
        draw_image(img,0,0)
        
        #create a for loop to cycle through the population list, drawing them in order of population
        for object in object_list[:NUMBER_OF_CITIES]:    
            
            #draw each dot and name
            dot_x, dot_y=update_location(object)
            draw_dot(object, dot_x, dot_y)
            
            #update the window
            request_redraw()
            sleep(TIME)
        

#start the graphics function
start_graphics(graphics, "Cities Visualization", WINDOW_WIDTH, WINDOW_HEIGHT, flipped_y=True)
