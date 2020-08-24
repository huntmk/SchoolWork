#Vehicles.py
#Created 4/5/20 by Jasper Heist
#Contains Vehicles Parent Class and subclassed types of vehicle definitions

from Utilities.Definitions import AverageCarLength, AverageSemiLength
from Utilities.UIDGenerator import NumberGenerator

class Vehicle(object):
    """class that all vehicles inherit from"""
    def __init__(self, length=AverageCarLength):
        object.__init__(self)
        #length of the car. Idea is to track the space the car takes up on the road, since roads have alimited amount of space cars can be bumper to bumper
        self.__length = length
        #UID for this vehicle on the road to track them
        self.__uid = NumberGenerator().vehicle_uid()

    def __get_length(self):
        """returns lenght of this vehicle"""
        return self.__length
    
    def __get_id(self):
        """return unique ID for this vehicle"""
        return self.__uid

    length = property(fget=__get_length)
    uid = property(fget=__get_id)


class Car(Vehicle):
    """class representing an average car"""

    def __init__(self, length=AverageCarLength):
        Vehicle.__init__(Vehicle, length)


class Semi(Vehicle):
    """class representing a semi truck (probably wont use this, but mostly for abstraction practice with python)"""

    def __init__(self, length=AverageSemiLength):
        Vehicle.__init__(Vehicle, length)
        