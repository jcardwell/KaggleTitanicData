#Jack Cardwell
#11/4/2014
#Professor Farid

#import the necessary functions
from army import Army

#get the raw input and plug it into the army functions in order to run the program
x=raw_input("How many soldiers are there?")
y=raw_input("How many soldiers are skipped between executions?")

army=Army(x,y)

army.kill_all()

