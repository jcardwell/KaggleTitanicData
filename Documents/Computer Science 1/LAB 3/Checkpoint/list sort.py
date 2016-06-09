#Jack Cardwell
#11/3/2014
#Professor Farid

#import the City class to store objects
from city import City

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
    object=City(city_information[0],city_information[1],city_information[2],int(city_information[3]), float(city_information[4]),float(city_information[5]))
    
    #append each object to a list of the objects
    object_list.append(object)

#close the input file
in_file.close()


#name the file to which you will output the text
output="cities_out.txt"

#open the file as a writing file in order to export lines to the file
export=open(output, "w")

#create a for loop to write the string of each object within the object_list to the file "cities_out.txt"
for object in object_list:
    
    #print the string with a new line at the end of each object
     export.write(str(object)+"\n")

#close the output list
export.close()

