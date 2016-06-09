#Jack Cardwell
#11/7/2014
#Professor Farid

#import the necessary functions 
from quicksort import *
from city import *
from string import *

#open the input file "world_cities.txt"
in_file= open("world_cities.txt","r")

#create a list of the city objects, initially empty, to be added to later on to represent each object
the_list=[]

#go through the data line by line and separate out each part into a list
for line in in_file:

    #split the object then strip it    
    information=line.strip()
    city_information=information.split(",")
    
    #create a city object using this data
    city_total=City(city_information[0],city_information[1],city_information[2],int(city_information[3]), float(city_information[4]),float(city_information[5]))
    
    #append each object to a list of the objects
    the_list.append(city_total)

#close the input file
in_file.close

def compare_name(j, pivot):
     if lower(j.name)<=lower(pivot.name):
         return True
     else:
         return False
     
def compare_population(j, pivot):
    if j.population>pivot.population:
        return True
    else:
        return False
    
def compare_latitude(j, pivot):
     if j.latitude<=pivot.latitude:
        return True
     else:
        return False
    

sort(the_list,compare_population)

output="cities_population.txt"
export=open(output, "w")
for object in the_list:
    export.write(str(object)+ "\n")
export.close

sort(the_list,compare_name)

output="cities_alpha.txt"
export=open(output, "w")
for object in the_list:
    export.write(str(object)+ "\n")
export.close

sort(the_list,compare_latitude)

output="cities_latitude.txt"
export=open(output, "w")
for object in the_list:
    export.write(str(object)+ "\n")
export.close



