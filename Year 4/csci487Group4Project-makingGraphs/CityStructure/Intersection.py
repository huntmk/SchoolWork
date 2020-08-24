#Intersection.py
#Created 4/5/20 by Jasper Heist
#Intersection class definition

from Utilities.Definitions import IntersectionType, Orientation, Direction
from CityStructure.Connection import Connection
from Utilities.UIDGenerator import NumberGenerator

#class that represents an intersection. handles passing cars through the intersection(not implemented)-Essentially a node in our City/Graph
class Intersection(object):
    def __init__(self, intersection_type):
        object.__init__(self)
        self.__intersection_type = intersection_type
        self.__uid = NumberGenerator().intersection_uid()

        #lane definitions
        self.__connections={
            #southbound lane entering intersection on top
            Direction.SouthboundIn:None,
            #northbound lane leaving intersection on top 
            Direction.NorthboundOut:None, 
            #westbound lane entering intersection on right
            Direction.WestboundIn:None, 
            #eastbound lane leaving intersection on right
            Direction.EastboundOut:None, 
            #northbound lane entering intersection on bottom
            Direction.NorthboundIn:None,
            #southbound lane leaving intersection on bottom
            Direction.SouthboundOut:None,
            #eastbound lane entering intersection on left
            Direction.EastboundIn:None,
            #westbound lane leaving intersection on left
            Direction.WestboundOut:None
            }

        #coordinates relative to main intersection in city(first one added), used for mapping
        self.__coordinates_in_city = [None, None]

    def print_info(self):
        """prints textual information about this intersection (prints all connection info this intersection has)"""
        print("    Intersection " + str(self.__uid) + "(" + str(self.__intersection_type) + ")")
        print("    --------------------")
        for connection in self.__connections:
            if(self.__connections[connection] == None):
                continue
            else:
                print("    " + str(connection))
                self.__connections[connection].print_info()

        print()

    def print_visual(self):
        """gathers visual representation of intersection and return array of strings"""
        intersection_visuals = list()
        #build ascii art
        intersection_visuals.append("       |  |   ↑  ↑       ")
        intersection_visuals.append("       |  |   |  |       ")
        intersection_visuals.append("       |{}|   |{}|       ".format(self.get_uid_for_connection(Direction.SouthboundIn), self.get_uid_for_connection(Direction.NorthboundOut)))
        intersection_visuals.append("       ↓  ↓   |  |       ")
        intersection_visuals.append("←{}-             ←{}-".format(self.get_uid_for_connection(Direction.WestboundOut), self.get_uid_for_connection(Direction.WestboundIn)))
        intersection_visuals.append("←{}-             ←{}-".format(self.get_uid_for_connection(Direction.WestboundOut), self.get_uid_for_connection(Direction.WestboundIn)))
        intersection_visuals.append("        ({})[{}]      ".format(self.get_uid_string(), self.get_coordinates_string_for_intersection()))
        intersection_visuals.append("-{}→             -{}→".format(self.get_uid_for_connection(Direction.EastboundIn), self.get_uid_for_connection(Direction.EastboundOut)))
        intersection_visuals.append("-{}→             -{}→".format(self.get_uid_for_connection(Direction.EastboundIn), self.get_uid_for_connection(Direction.EastboundOut)))
        intersection_visuals.append("       |  |   ↑  ↑       ")
        intersection_visuals.append("       |{}|   |{}|       ".format(self.get_uid_for_connection(Direction.SouthboundOut), self.get_uid_for_connection(Direction.NorthboundIn)))
        intersection_visuals.append("       |  |   |  |       ")
        intersection_visuals.append("       ↓  ↓   |  |       ")

        return intersection_visuals

    def get_coordinates_string_for_intersection(self):
        x = ""
        y = ""

        if self.coordinates[0] >= 0:
            x += " "
        if self.coordinates[1] >= 0:
            y += " "
        
        x += str(self.coordinates[0])
        y += str(self.coordinates[1])

        return "{},{}".format(x, y)

    def get_uid_string(self):
        if self.uid > 9:
            return str(self.uid)
        else:
            return " {}".format(self.uid)

    def get_uid_for_connection(self, direction:Direction):
        """return formatted string from the get_formatting_for_direction(int, direction) instance method after doing a NULL check"""
        if(self.__connections[direction] == None):
            if(direction == Direction.NorthboundIn) or (direction == Direction.NorthboundOut) or (direction == Direction.SouthboundIn) or (direction == Direction.SouthboundOut):
                return("XX")
            else:
                return("-XX-")
        else:
            connection:Connection = self.__connections[direction]
            uid = connection.uid
            return self.get_formatting_for_direction(uid, direction)



    def get_formatting_for_direction(self, uid:int, direction:Direction):
        """return proper formatting for uid based on if this is a verticle or horizontal road"""
        if(direction == Direction.NorthboundIn) or (direction == Direction.NorthboundOut) or (direction == Direction.SouthboundIn) or (direction == Direction.SouthboundOut):
            #check size of uid for formatting
            if(uid < 10):
                return(" " + str(uid))
            else:
                return str(uid)
        else:
            if(uid < 10):
                return("-{}--".format(uid))
            elif(uid < 100):
                return("-{}-".format(uid))
            else:
                return()


    def link_connections(self, connection_coming_in:Connection, connection_coming_out:Connection, orientation:Orientation):
        """takes two connections (one coming in and one coming out and links them to this intersection at the supplied orientation)

        Parameters
        ----------
            connection_coming_in:Connection
                connection that will feed into this intersection
            
            connection_coming_out:Connection
                connection that will be feeding cars out of this intersection

            orientation:Orientation Enum type
                where to attach these connection in relation to the intersection

        """

        #determine correct connection to make
        if(orientation == Orientation.Top):
            self.__connections[Direction.NorthboundOut] = connection_coming_out
            self.__connections[Direction.SouthboundIn]= connection_coming_in
        elif(orientation == Orientation.Right):
            self.__connections[Direction.EastboundOut] = connection_coming_out
            self.__connections[Direction.WestboundIn]= connection_coming_in
        elif(orientation == Orientation.Bottom):
            self.__connections[Direction.SouthboundOut] = connection_coming_out
            self.__connections[Direction.NorthboundIn]= connection_coming_in
        elif(orientation == Orientation.Left):
            self.__connections[Direction.WestboundOut] = connection_coming_out
            self.__connections[Direction.EastboundIn]= connection_coming_in
        else:
            pass

        connection_coming_in.output_intersection = self.uid
        connection_coming_out.output_intersection = self.uid

    def link_intersection(self, other_intersection, orientation, skipCoordinateAssignment = False):
        """links this intersection with the supplied intersection.
        
        Parameters
        ----------
            other_intersection: Intersection
                intersection to connect to this intersection

            orientation: Orientation Enum Type
                where this connection should be in relation to the intersection this is called on

            in_connection: Connection
                connection that feeds in to this intersection and out of the other one (will create a default one if not supplied)

            out_connection: Connection
                connection that feeds out of this intersection and into the other one (will create a default one if not supplied)
        """


        in_connection = Connection()
        out_connection = Connection()

        #link intersections with connections
        self.link_connections(in_connection, out_connection, orientation)
        #link other intersection to other end
        other_intersection.link_connections(out_connection, in_connection, Orientation.other_side(orientation))
        if skipCoordinateAssignment:
            return


        if(orientation == Orientation.Top):
            other_intersection.coordinates = [self.coordinates[0], self.coordinates[1]-1]
        elif(orientation == Orientation.Right):
            other_intersection.coordinates = [self.coordinates[0]+1, self.coordinates[1]]
        elif(orientation == Orientation.Bottom):
            other_intersection.coordinates = [self.coordinates[0], self.coordinates[1]+1]
        if(orientation == Orientation.Left):
            other_intersection.coordinates = [self.coordinates[0]-1, self.coordinates[1]]

    def get_adjacent_intersection(self, relation:Orientation):
        connection_to_follow:Connection = None
        if relation == Orientation.Top:
            connection_to_follow = self.__connections[Direction.NorthboundOut]
        elif relation == Orientation.Right:
            connection_to_follow = self.__connections[Direction.EastboundOut]
        elif relation == Orientation.Bottom:
            connection_to_follow = self.__connections[Direction.SouthboundOut]
        elif relation == relation.Left:
            connection_to_follow = self.__connections[Direction.WestboundOut]

        if connection_to_follow == None:
            return None
        else:
            return connection_to_follow.output_intersection        


    #returns enum value of the type of intersection this is
    def __get_intersection_type(self):
        return self.__intersection_type

    def __get_uid(self):
        """gets uid for intersection"""
        return self.__uid

    def __set_coordinates(self, coordinates):
        self.__coordinates_in_city[0] = coordinates[0]
        self.x = coordinates[0]
        self.__coordinates_in_city[1] = coordinates[1]
        self.y = coordinates[1]

    def __get_coordinates(self):
        if(self.__coordinates_in_city[0] == None or self.__coordinates_in_city[1] == None):
            return None
        else:
            return self.__coordinates_in_city

    #Property Definitions

    intersectionType = property(fget=__get_intersection_type)

    #uid
    uid = property(fget=__get_uid)

    coordinates = property(fset=__set_coordinates, fget=__get_coordinates)

class TrafficLight(Intersection):
    """class representing a four way traffic light"""

    def __init__(self):
        Intersection.__init__(self, IntersectionType.TrafficLight)

class FourWayStop(Intersection):
    """class that represents a two way stop"""

    def __init__(self):
        Intersection.__init__(self, IntersectionType.FourWayStop)
        
class TwoWayStop(Intersection):
    """class that represents a road that"""

    def __init__(self):
        Intersection.__init__(self, IntersectionType.TwoWayStop)
        