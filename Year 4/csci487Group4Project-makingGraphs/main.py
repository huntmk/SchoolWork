from CityStructure.Intersection import Intersection, TrafficLight
from CityStructure.Connection import Connection
from Agents.Vehicles import Car
from Utilities.Definitions import Orientation
from CityStructure.City import City

def main():
    # while True:
    #     action = input("Enter one character: ").upper()
    #     if len(action) != 1:
    #         print("Not a single character...")
    #         continue
    #     else:
    #         print("Input received")
    #         continue
    # testObjects()
    createFourStar()
    createOrangetown()
    createGreentown()
    

def createFourStar():
    fourstar = City("Four Star")
    mainStreet = TrafficLight()
    fourstar.assign_connection_default(mainStreet, Orientation.Top)
    fourstar.assign_connection_default(mainStreet, Orientation.Right)
    fourstar.assign_connection_default(mainStreet, Orientation.Bottom)
    fourstar.assign_connection_default(mainStreet, Orientation.Left)


    fourstar.print_city_map()

def createGreentown():
    green = City("Greentown")
    mainStreet = TrafficLight()
    t = green.assign_connection_default(mainStreet, Orientation.Top)
    r = green.assign_connection_default(mainStreet, Orientation.Right)
    b = green.assign_connection_default(mainStreet, Orientation.Bottom)
    l = green.assign_connection_default(mainStreet, Orientation.Left)
    green.assign_connection_default(t, Orientation.Left)
    i = green.assign_connection_default(t, Orientation.Right)
    i = green.assign_connection_default(i, Orientation.Right)
    s = green.assign_connection_default(i, Orientation.Right)
    r = green.assign_connection_default(s, Orientation.Bottom)
    p = green.assign_connection_default(r, Orientation.Bottom)
    l = green.assign_connection_default(p, Orientation.Right)
    l = green.assign_connection_default(l, Orientation.Right)
    l = green.assign_connection_default(l, Orientation.Top)
    s = green.assign_connection_default(s, Orientation.Right)
    s = green.assign_connection_default(s, Orientation.Right)
    green.assign_connection_default(s, Orientation.Top)
    s = green.assign_connection_default(s, Orientation.Bottom)


    green.print_city_map()

def createOrangetown():
    city = City("Orangetown")
    tl1 = TrafficLight()
    tl2 = TrafficLight()
    tl3 = TrafficLight()
    tl4 = TrafficLight()
    tl5 = TrafficLight()
    city.assign_connection_default(tl1, Orientation.Top, tl2)
    city.assign_connection_default(tl1, Orientation.Bottom, tl3)
    city.assign_connection_default(tl1, Orientation.Left, tl4)
    city.assign_connection_default(tl1, Orientation.Right, tl5)
    city.assign_connection_default(tl4, Orientation.Left)
    city.assign_connection_default(tl4, Orientation.Top)
    city.assign_connection_default(tl4, Orientation.Bottom)
    city.assign_connection_default(22, Orientation.Right, TrafficLight())
    city.assign_connection_default(0, Orientation.Right)
    city.assign_connection_default(4, Orientation.Right)
    city.assign_connection_default(9, Orientation.Top)
    city.assign_connection_default(6, Orientation.Right, 1)



    # tl2.print_visual()
    # tl1.print_visual()
    # tl3.print_visual()
    
    city.print_city_map()

    # city.print_info()
    
    



#tests Intersection,connection, and car creation. Also this tests the NumberGenerator singleton that gives us our unique ID's
def testObjects():
    intersection = TrafficLight()
    print(intersection.intersectionType)

    connection1 = Connection("connection1", 50)
    print(connection1.uid)

    i = 0
    #connection ids should start at 1 at this point because one has already been created
    while(i < 10):
        connection = Connection("connection"+str(i))
        print(connection.uid)
        i += 1

    #vehicle id's should start at 0
    while(i < 20):
        car = Car()
        print(car.uid)
        i += 1

    #intersection ID's should start at 0
    while(i < 30):
        intersection = TrafficLight()
        intersection.print_info()
        i += 1

    #vehicle id's should pick back up at 10
    while(i < 40):
        car = Car()
        print(car.uid)
        i += 1
    


if __name__ == '__main__':
    main()
