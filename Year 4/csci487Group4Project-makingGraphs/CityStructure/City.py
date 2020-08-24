#City.py
#Created 4/5/20 by Jasper Heist
#File containing class definition of a city.

from CityStructure.Intersection import Intersection, TrafficLight
from Utilities.Definitions import Orientation, Colors, print_c, print_e, temp_format_clear
from typing import Union, List

class City(object):
    """City that contains a graph of intersections and connections"""

    def __init__(self, name):
        object.__init__(self)
        self.__name = name
        #single intersection in the graph so the city has reference to the whole thing. this is a list that must be inserted at thier uid. this is so we can look them up by uid (which we can store as an int in a connection)
        self.__intersections = dict()
        #intersection that we know we have. first one added to out town, since we cannot rely on the 0th intersection always being a part of our city
        self.__main_intersection = None

        self.__mapping_of_intersections = [[None, None, None], [None, None, None],[None, None, None]]

    def assign_connection_default(self, intersection, orientation_to_other_intersection:Orientation, other_intersection:Intersection = None):
        """takes two takes two intersections and links them

        Parameters
        ----------
            intersection:Intersection
                main intersection we are binding to
            
            orientation_to_other_intersection:Orientation
                relation of main intersection to other intersection 
                (if the main intersection is above the other intersection, this will be Orientation.Top)

            other_intersection:Intersection
                secondary intersection that we are linking with the main one

        """
        #if int was passed in, get intersection
        if isinstance(intersection, int):
            intersection = self.__get_intersection_by_id(intersection)
            if(intersection == None):
                return
        #if int was passed in, get intersection
        if isinstance(other_intersection, int):
            other_intersection = self.__get_intersection_by_id(other_intersection)
            if(other_intersection == None):
                return

        #do not attach to intersection that already has connection here
        if not intersection.get_adjacent_intersection(orientation_to_other_intersection) == None:
            #if there is an intersection that already exists, just link the connections
            if intersection.get_adjacent_intersection(orientation_to_other_intersection) == self.__get_intersection_by_coordinates(intersection, orientation_to_other_intersection):
                intersection.link_intersection(other_intersection, orientation_to_other_intersection, True)
                other_intersection.link_intersection(intersection, orientation_to_other_intersection.other_side(), True)
            else:
                print_e("Intersection Already attached to {} side of intersection {}".format(orientation_to_other_intersection.desc(), intersection.uid))
            return

        #if other intersection has not been supplied, default to a traffic light
        if other_intersection == None:
            other_intersection = TrafficLight()
            
       
                
        #first intersection assignment
        if(self.__main_intersection == None):
            self.__main_intersection = intersection

        if(intersection.coordinates == None):
            if(self.__main_intersection == intersection):
                intersection.coordinates = [0,0]
            else:
                print_e("{}Invalid. First Intersection argument must already exist in city OR be the first intersection (main intersection) you are adding to the city{}".format(Colors.RED, Colors.RESET))
                return

        #add each of these to list of intersectionss
        if (not self.__intersections.__contains__(intersection)):
            self.__intersections[intersection.uid] = intersection
        if (not self.__intersections.__contains__(other_intersection)):
            self.__intersections[other_intersection.uid] = other_intersection

        current_coordinates = intersection.coordinates
        new_coordinates = [current_coordinates[0], current_coordinates[1]]

        #change coordinates based on orientation
        if (orientation_to_other_intersection == Orientation.Top):
            new_coordinates[0] -= 1
        elif (orientation_to_other_intersection == Orientation.Right):
            new_coordinates[1] += 1
        elif (orientation_to_other_intersection == Orientation.Bottom):
            new_coordinates[0] += 1
        elif (orientation_to_other_intersection == Orientation.Left):
            new_coordinates[1] -= 1

        intersection.link_intersection(other_intersection, orientation_to_other_intersection)

        return other_intersection.uid

    def __get_intersection_by_id(self, id):
        intersection = self.__intersections.get(id, None)
        if intersection == None:
            print_e("No intersection exists with supplied ID")
        return intersection

    def __get_intersection_by_coordinates(self, intersection, orientation):
        new_coordinates = [intersection.coordinates[0], intersection.coordinates[1]]

        #change coordinates based on orientation
        if (orientation == Orientation.Top):
            new_coordinates[0] -= 1
        elif (orientation == Orientation.Right):
            new_coordinates[1] += 1
        elif (orientation == Orientation.Bottom):
            new_coordinates[0] += 1
        elif (orientation == Orientation.Left):
            new_coordinates[1] -= 1

        x = new_coordinates[0]
        y = new_coordinates[1]

        for intersection in self.__intersections.values():
            if (intersection.coordinates[0] == x) and (intersection.coordinates[1] == y):
                return intersection
        return None

    def print_info(self):
        print("City: " + self.__name)
        print("+++++++++++++++++++++++++++++++")
        for intersection in self.__intersections:
            intersection.print_info()

    def print_city_map(self):
        "Assumes added in the order they are in main. No smart way of figuring out location based on coordinates yet, but this is on the way there."
        print_c("City: {}".format(self.__name), Colors.WHITE)
        print_c("+++++++++++++++++++++++++++++++", Colors.WHITE)
 
        westmost_index = 0
        rightmost_index = 0
        x_location_to_intersections = dict()
        for intersection in self.__intersections.values():
            x_coord = intersection.coordinates[0]
            y_coord = intersection.coordinates[1]
            if not x_location_to_intersections.__contains__(y_coord):
                x_location_to_intersections[y_coord] = list()

            x_location_to_intersections[y_coord].append(intersection)
            #check that y coordinate is not further left (need to know for printing)
            if x_coord < westmost_index:
                westmost_index = x_coord
            #gather bounds or right hand side
            if x_coord > rightmost_index:
                rightmost_index = x_coord

        #go through each row (sort the rows first)
        for row in sorted(x_location_to_intersections.keys()):
            sorted_row = sorted(x_location_to_intersections[row], key=lambda x: x.coordinates[0], reverse=False)
            self.__print_row_of_intersections(sorted_row, westmost_index)

    def __print_row_of_intersections(self, row_of_intersections, westmost_column):
        """given a list of intersections to print, this prints the intersections next to each other in a row"""
        images = dict()
        last_index = len(row_of_intersections)
        #gather all ascii art of intersection
        for square in row_of_intersections:
            if isinstance(square, Intersection):
                images[square] = square.print_visual()
        
        #iterate and print them all together
        i = 0
        while i < 13:
            next_collumn = westmost_column
            line = ""
            collumn_index = 0
            while collumn_index < last_index:
                square = row_of_intersections[collumn_index]
                if square.coordinates[0] > next_collumn:
                    line += self.__empty_square_line()
                else:
                    line += images[square][i]
                    collumn_index += 1

                next_collumn += 1
            print_c(line, Colors.WHITE)
            i += 1

    def __empty_square_line(self):
        """returns an empty line for printing off empty spaces in the city"""
        #clear all formatting for empty field
        return temp_format_clear("                         ", Colors.WHITE)
