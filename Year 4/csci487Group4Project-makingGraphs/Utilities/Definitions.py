#Definitions.py
#Created 4/5/20 by Jasper Heist
#Contains constants and other definitions needed for our program.

from enum import Enum, IntEnum
import sys

#average length of car used as default (https://mechanicbase.com/cars/average-car-length/)
AverageCarLength = 14.5
#average length of semi varies, we will call it 80 (https://www.quora.com/What-is-the-length-of-a-normal-semi-truck?share=1)
AverageSemiLength = 80

class Colors(Enum):
    '''enumeration that can supply color info for output'''

    RESET = 0
    GREEN = 1
    YELLOW = 2
    PURPLE = 3
    CYAN = 4
    RED = 5
    WHITE = 6

    def color(self):
        if self == Colors.GREEN:
            return "\033[1;32m"
        elif self == Colors.YELLOW:
            return "\033[1;33m"
        elif self == Colors.PURPLE:
            return "\033[1;35m"
        elif self == Colors.CYAN:
            return "\033[1;36m"
        elif self == Colors.RED:
            return "\033[1;37;41m"
        elif self == Colors.WHITE:
            return "\033[0;30;47m"
        else:
            return "\033[0m"

def print_c(string:str, c:Colors):
    """prints supplied string in specified color"""
    if sys.platform == "win32":
        print(string)
        return
    print("{}{}{}".format(c.color(), string, c.RESET.color()))

def print_e(string:str):
    """prints specified string in red Error format"""
    print_c(string, Colors.RED)

def temp_format_clear(string, c:Colors):
    if sys.platform == "win32":
        return string
    else:
        return "{}{}{}".format(Colors.RESET.color(), string, c.color())



#enumeration for types of intersection
class IntersectionType(Enum):
    TwoWayStop=1
    FourWayStop=2
    TrafficLight=3

#defines all possible directions for a road
class Direction(Enum):
    NorthboundOut = 0
    NorthboundIn = 1
    EastboundOut = 2
    EastboundIn = 3
    SouthboundOut = 4
    SouthboundIn = 5
    WestboundOut = 6
    WestboundIn = 7
    
class Orientation(IntEnum):
    Top = 0
    Right = 1
    Bottom = 2
    Left = 3


    def other_side(self):
        """performs math to get the opposite side of an intersection. 
        It is useful when going across an intersection (straight) and connecting two intersections
        """
        return (self+2)%4
    

    def to_the_right(self):
        """return enum as if turning to the right"""
        return (self-1)%4


    def to_the_left(self):
        """return enum as if turning left"""
        return (self+1)%4

    def desc(self):
        if self == Orientation.Top:
            return "TOP"
        elif self == Orientation.Right:
            return "RIGHT"
        elif self == Orientation.Bottom:
            return "BOTTOM"
        elif self == Orientation.Left:
            return "LEFT"
