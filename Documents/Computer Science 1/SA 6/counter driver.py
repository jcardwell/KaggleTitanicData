#Jack Cardwell
#10/14/14
#Professor Farid

#import all of the necessary functions from the Counter class
from counter import Counter

#define two objects from the class, Counter
try1=Counter(60,6,5)
try2=Counter(100,99,3)

#print the initial values of these objects
print try1.get_value()
print try2.get_value()

#tick down each of these objects once and then print their values
try1.tick()
try2.tick()

#print the string of the tries with the correct amount of "0" placeholders
print str(try1)
print str(try2)

