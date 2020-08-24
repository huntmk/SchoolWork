#Connection.py
#Created 4/5/20 by Jasper Heist
#contains definition of a connection

from Agents.Vehicles import Car, Semi
from Utilities.UIDGenerator import NumberGenerator
from Utilities.Definitions import AverageCarLength


class Connection(object):
    """class representing a ONE-DIRECTIONAL segment of a road that connects two intersections together"""

    def __init__(self, name="unamed", length=AverageCarLength*10, lanes=2, speed_limit=30):
        """initializer (set default length to 10 car lengths)"""
        object.__init__(self)
        self.__name = name
        #length of road-the idea here is to get a max amount of people that can be on a road by making sure the length of all 
        #the cars on the road does not exceed the length of this segement of road itself
        self.__length = length
        #all vehicles on this section of road
        self.__vehicles = list()
        #number of lanes in this connection
        self.__lanes = lanes
        #unique identifier for this connection
        self.__uid = NumberGenerator().connection_uid()
        #max speed cars can go
        self.__speed_limit = speed_limit
        #intersection the cars come from as they enter this connection
        self.__input_intersection =  None
        #intersection this connection feed into
        self.__output_intersection = None

    def __print_info(self):
        print("      Connection " + str(self.uid))

    def __get_name(self):
        """gets name of road"""
        return self.__name

    def __get_uid(self):
        """return unique ID for Connection"""
        return self.__uid

    def __get_input_inter(self):
        return self.__input_intersection
    
    def __set_input_inter(self, intersection:int):
        self.__input_intersection = intersection

    def __get_output_inter(self):
        return self.__output_intersection
    
    def __set_output_inter(self, intersection:int):
        self.__output_intersection = intersection

    #name of road
    name = property(fget = __get_name)
    
    uid = property(fget = __get_uid)

    input_intersection = property(fget=__get_input_inter, fset=__set_input_inter)

    output_intersection = property(fget=__get_output_inter, fset=__set_output_inter)
