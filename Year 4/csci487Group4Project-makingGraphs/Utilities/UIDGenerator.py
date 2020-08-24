#UIDGenerator.py
#Created 4/5/20 by Jasper Heist
#Contains NumberGenerator Singleton class

from threading import Lock

lock = Lock()

#singleton number generator class that generates our UIDs for all intersections, connections, vehicles, etc.
class NumberGenerator(object):
    _instance = None

    def __new__(self):
        lock.acquire()
        if not self._instance:
            self._instance = super(NumberGenerator, self).__new__(self)
            self.__next_vehicle_uid = 0
            self.__next_connection_uid = 0
            self.__next_intersection_uid = 0
        lock.release()
        return self._instance

    #gets next vehicle UID
    def vehicle_uid(self):
        lock.acquire()
        uidToReturn=self.__next_vehicle_uid
        # print("VehicleUID: ", uidToReturn)
        self.__next_vehicle_uid += 1
        lock.release()
        return uidToReturn

    #gets next connection UID
    def connection_uid(self):
        lock.acquire()
        uidToReturn=self.__next_connection_uid
        # print("ConnectionUID: ", uidToReturn)
        self.__next_connection_uid += 1
        lock.release()
        return uidToReturn

    #gets next intersection UID
    def intersection_uid(self):
        lock.acquire()
        uidToReturn=self.__next_intersection_uid
        # print("IntersectionUID: ", uidToReturn)
        self.__next_intersection_uid += 1
        lock.release()
        return uidToReturn

