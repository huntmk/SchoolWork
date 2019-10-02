
      
#Define Object Player
class Player:
    #create intializer
    def __init__(self,name=None, totalPoints=0,fgMade=0,fgAttempt=0,totalAssists=0, offenseReb=0, defReb=0, totalSteals=0,totalBlocks=0,freeMade=0,freeAttempt=0,totalTurnovers=0,gamesPlayed=0):

        self.name = name
        self.totalPoints = totalPoints
        self.fgMade = fgMade
        self.fgAttempt = fgAttempt
        self.totalAssists = totalAssists
        self.offenseReb = offenseReb
        self.defReb = defReb
        self.totalSteals = totalSteals
        self.totalBlocks = totalBlocks
        self.freeMade = freeMade
        self.freeAttempt = freeAttempt
        self.totalTurnovers = totalTurnovers
        self.gamesPlayed = gamesPlayed
        
    #add while loop for error when entering stat not 0 or greater
    #add while for fg and freethrows to check if less than 0
    def setName(self,playerName):
        self.name = str(playerName) 
    def setTotalPoints(self,points):
        self.totalPoints = int(points)
        
        #points = input("Enter total points: ")
        while int(points)<0:
            print("Total points must be 0 or greater than. ")
            points = input("Enter points again: ")
        self.totalPoints = int(points)
            
        
        #if int(points) >= 0:
            #self.totalPoints = int(points)
        #else:
            #print("Total points must be 0 or greater than. ")
            #points = input("Enter total points: ")
    def setFg(self,fgMade,fgAttempt):
        self.fgAttempt = int(fgAttempt)
        self.fgMade = int(fgMade)
        while int(fgMade) > int(fgAttempt):
            print("Field goals made must be more or the same as attempts.")
            fgMade = input("Enter field goals made: ")
            fgAttempt = input("Enter field goals attempted: ")
        #self.fgAttempt = int(fgAttempt)
        #self.fgMade = int(fgMade)
            while int(fgMade)<0 or int(fgAttempt)<0:
                print("Error, re-enter field goals made and attempted")
                fgMade = input("Enter field goals made: ")
                fgAttempt = input("Enter field goals attempted: ")
        self.fgAttempt = int(fgAttempt)
        self.fgMade = int(fgMade)

                               
    def setTotalAssists(self,assists):
        while int(assists) < 0:
            print("Total assits must be 0 or greater ")
            assists = input("Enter total assists: ")
        self.totalAssists = int(assists)
    def setOffenseReb(self,offRebounds):
        while int(offRebounds) < 0:
            print("Offensive rebounds must be 0 or greater ")
            offRebounds = input("Enter offensive rebounds: ")
            self.offenseReb = int(offRebounds)
    def setDefReb(self,defenseReb):
        while int(defenseReb) < 0:
            print("Defense rebouunds must be 0 or greater ")
            defenseReb = input("Enter defensive rebounds: ")
        self.defReb = int(defenseReb)
    def setTotalSteals(self,steals):
        while int(steals) < 0:
            print("Steals must be 0 or greater ")
            steals = input("Enter steals: ")
        self.totalSteals = int(steals)
    def setTotalBlocks(self,blocks):
        while int(blocks) < 0:
            print("Blocks must be 0 or greater ")
            blocks = input("Enter blocks: ")
        self.totalBlocks = int(blocks)
    def setFreeThrows(self,freeMade,freeAttempt):
        self.freeAttempt = int(freeAttempt)
        self.freeMade = int(freeMade)
        while int(freeMade) > int(freeAttempt):
            print("Free throws made must be less than or the same as attempt")
            freeMade = input("Enter free throws made: ")
            freeAttempt = input("Enter free throws attempted: ")
        #self.fgAttempt = int(fgAttempt)
        #self.fgMade = int(fgMade)
            while int(freeMade)<0 or int(freeAttempt)<0:
                print("Error. re-enter free throws made and attempted: ")
                freeMade = input("Enter free throws made: ")
                freeAttempt = input("Enter free throws attempted: ")
        self.freeMade = int(freeMade)
        self.freeAttempt = int(freeAttempt)
        
    def setTotalTurnovers(self,turnovers):
        while int(turnovers) < 0:
            print("Turnovers must be 0 or greater")
            turnovers = input("Enter turnovers: ")
        self.totalTurnovers = int(turnovers)
    def setGamesPlayed(self,games):
        while int(games) <= 0:
            print("Games played must be greater than 0 ")
            games = input("Enter games played: ")
        self.gamesPlayed = int(games)
        return self.gamesPlayed
    def getPointsPer(self):
        pointsPer = (self.totalPoints/self.gamesPlayed)
        #print(pointsPer)
        return "{0:.1f}".format(pointsPer)
    def getFieldPercent(self):
        fieldPercent = (self.fgMade/self.fgAttempt) *100
        
        return "{0:.1f}".format(fieldPercent)
    def getFieldMadePer(self):
        fgMadePer = self.fgMade/self.gamesPlayed
        return "{0:.1f}".format(fgMadePer)
    def getFieldAttemptPer(self):
        fgAttemptPer = self.fgAttempt/self.gamesPlayed
        return "{0:.1f}".format(fgAttemptPer)
    def getAssistPer(self):
        assistPer = self.totalAssists/self.gamesPlayed
        return "{0:.1f}".format(assistPer)
    def getOffRebPer(self):
        offRebPer = self.offenseReb/self.gamesPlayed
        return "{0:.1f}".format(offRebPer)
    def getDefRebPer(self):
        defRebPer = self.defReb/self.gamesPlayed
        return "{0:.1f}".format(defRebPer)
    def getStealsPer(self):
        stealsPer = self.totalSteals/self.gamesPlayed
        return "{0:.1f}".format(stealsPer)
    def getBlocksPer(self):
        blocksPer = self.totalBlocks/self.gamesPlayed
        return "{0:.1f}".format(blocksPer)
    def getTurnoversPer(self):
        turnoversPer = self.totalTurnovers/self.gamesPlayed
        return "{0:.1f}".format(turnoversPer)
    def getFreePercent(self):
        freePercent = (self.freeMade/self.freeAttempt)*100
        return "{0:.1f}".format(freePercent)
    def getFreeMadePer(self):
        freeMadePer = self.freeMade/self.gamesPlayed
        return "{0:.1f}".format(freeMadePer)
    def getFreeAttemptPer(self):
        freeAttemptPer = self.freeAttempt/self.gamesPlayed
        return "{0:.1f}".format(freeAttemptPer)
    def getStats(self):
        string = "\n{}'s Stats \nPoints Per Game: {} \nField Goal Percentage: {} \nField Goals Made Per Game: {} \nField Goals Attempted Per Game: {} \nAssists Per Game: {} \nOffensive Rebounds Per Game: {} \nDefensive Rebounds Per Game: {} \nSteals Per Game: {} \nBlocks Per Game: {} \nTurnovers Per Game: {} \nFree Throw Percentage: {} \nFree Throws Made: {} \nFree Throws Attempted: {} ".format(self.name,self.getPointsPer(),self.getFieldPercent(),self.getFieldMadePer(),self.getFieldAttemptPer(),self.getAssistPer(),self.getOffRebPer(),self.getDefRebPer(),self.getStealsPer(),self.getBlocksPer(),self.getTurnoversPer(),self.getFreePercent(),self.getFreeMadePer(),self.getFreeAttemptPer(), "\n")
        return string
    #Save stats to be able to review them
    def saveStats(self):
        with open('Stats.txt', 'a')as f:
            f.write(self.getStats())
        
  
    
def main():
    print("Hello! This is the basketball stat machine program. The program asks for the name of the player, the totals of each stat and the number of games played. The program will calculate the averages and percentages of the players individual stats. These stats will be saved in a file in order to review and look at. It will also ask if you want to add in a second player's stats for comparison. Lets start with the first player's stats. \n")
    
    player1 = Player()
    name = input("Player name: ")
    player1.setName(name)
    totalPoints = input("Enter total points: ")
    player1.setTotalPoints(totalPoints)
    fgMade = input("Enter field goals made: ")
    fgAttempt = input("Enter field goals attempted: ")
    player1.setFg(fgMade,fgAttempt)
    totalAssists = input("Enter total assists: ")
    player1.setTotalAssists(totalAssists)
    offenseReb = input("Enter offensive rebounds: ")
    player1.setOffenseReb(offenseReb)
    defReb = input("Enter defensive rebounds: ")
    player1.setDefReb(defReb)
    totalSteals = input("Enter total steals: ")
    player1.setTotalSteals(totalSteals)
    totalBlocks = input("Enter total blocks: ")
    player1.setTotalBlocks(totalBlocks)
    freeMade = input("Enter free throws made: ")
    freeAttempt = input("Enter free throws attempted: ")
    player1.setFreeThrows(freeMade,freeAttempt)
    totalTurnovers = input("Enter total turnovers: ")
    player1.setTotalTurnovers(totalTurnovers)
    gamesPlayed = input("Enter games played: ")
    player1.setGamesPlayed(gamesPlayed)
    print(player1.getStats())
    player1.saveStats()
    response = input("Would you like to enter another player's stats for comparison: (yes or no)\n")
    if response == ("yes"):
        print("Player 2 Stats: ")
        player2 = Player()
        name = input("Player name: ")
        
        player2.setName(name)
        totalPoints = input("Enter total points: ")
        player2.setTotalPoints(totalPoints)
        fgMade = input("Enter field goals made: ")
        fgAttempt = input("Enter field goals attempted: ")
        player2.setFg(fgMade,fgAttempt)
        totalAssists = input("Enter total assists: ")
        player2.setTotalAssists(totalAssists)
        offenseReb = input("Enter offensive rebounds: ")
        player2.setOffenseReb(offenseReb)
        defReb = input("Enter defensive rebounds: ")
        player2.setDefReb(defReb)
        totalSteals = input("Enter total steals: ")
        player2.setTotalSteals(totalSteals)
        totalBlocks = input("Enter total blocks: ")
        player2.setTotalBlocks(totalBlocks)
        freeMade = input("Enter free throws made: ")
        freeAttempt = input("Enter free throws attempted: ")
        player2.setFreeThrows(freeMade,freeAttempt)
        totalTurnovers = input("Enter total turnovers: ")
        player2.setTotalTurnovers(totalTurnovers)
        gamesPlayed = input("Enter games played: ")
        player2.setGamesPlayed(gamesPlayed)
        print(player2.getStats())
        player2.saveStats()
        
    elif response== ("no"):
        print("Thanks for using the comparison stat machine")
    else:
        print("Invalid response. ")
        response = input("(yes) or (no)")
if __name__== "__main__":
    main()
        
