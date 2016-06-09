#Jack Cardwell
#11/3/2014
#Professor Farid

#create the class City
class City:
    
    #create the constructor function for the City class
    def __init__(self, code, name, region, population, latitude, longitude):
        
        #initialize all of the instance variables
        self.code=code
        self.name=name
        self.region=region
        self.population=int(population)
        self.latitude=float(latitude)
        self.longitude=float(longitude)
        
    #create a string function to return the object's name population latitude and longitude
    def __str__(self):
        return str(self.name)+","+str(self.population)+","+str(self.latitude)+","+str(self.longitude)
    
    