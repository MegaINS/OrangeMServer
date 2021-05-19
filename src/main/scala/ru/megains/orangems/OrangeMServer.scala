package ru.megains.orangems

import org.lwjgl.glfw.GLFW.glfwPollEvents
import ru.megains.orangem.{PacketProcess, ServerStatusResponse}
import ru.megains.orangem.utils.Logger
import ru.megains.orangems.network.NetworkSystem
import ru.megains.orangems.world.WorldServer

import scala.collection.mutable
import scala.reflect.io.Directory


class OrangeMServer(serverDir: Directory)  extends Runnable with Logger[OrangeMServer] with PacketProcess{

    val networkSystem:NetworkSystem = new NetworkSystem(this)
    val statusResponse: ServerStatusResponse = new ServerStatusResponse

   // var saveLoader: AnvilSaveFormat = _
    var world: WorldServer = _
    var serverThread: Thread = Thread.currentThread

    var playerList: PlayerList = _

    var timeOfLastWarning: Long = 0
    val futureTaskQueue: mutable.Queue[()=>Unit] = new mutable.Queue[()=>Unit]



    var running = true
    //val timer = new Timer(20)

    def startServer(): Boolean = {
        log.info("Starting OrangeM server  version 0.0.1")
        Bootstrap.init()


        networkSystem.startLan(null, 20000)
       // networkSystem.startLocal()


       // saveLoader = new AnvilSaveFormat(serverDir)
        loadAllWorlds()

        true
    }



    def loadAllWorlds(): Unit = {
       // val saveHandler = saveLoader.getSaveLoader("world")
       // world = new WorldServer(this, saveHandler)
       // world.addEventListener(new ServerWorldEventHandler(this, world))
        playerList = new PlayerList(this)

        initialWorldChunkLoad()
    }

    def initialWorldChunkLoad(): Unit = {

    }


    override def run(): Unit = {

        if (startServer()) {

            var var1: Long = OrangeMServer.getCurrentTimeMillis
            var var50: Long = 0L


            while (running) {

                val var5: Long = OrangeMServer.getCurrentTimeMillis
                var var7: Long = var5 - var1

//
//                if (var7 > 2000L && var1 - timeOfLastWarning >= 15000L) {
//                    log.warn("Can\'t keep up! Did the system time change, or is the server overloaded? Running {}ms behind, skipping {} tick(s)", var7, var7 / 50L)
//                    var7 = 2000L
//                    timeOfLastWarning = var1
//                }

                if (var7 < 0L) {
                    log.warn("Time ran backwards! Did the system time change?")
                    var7 = 0L
                }



                var50 += var7
                var1 = var5


                while (var50 > 50L) {
                    var50 -= 50L
                    tick()

                }


                Thread.sleep(Math.max(1L, 50L - var50))

            }



            stopServer()


        }
    }
    def stopServer(): Unit = {
        log.info("Stopping server")
        networkSystem.stop()
//        if (this.playerList != null) {
//            log.info("Saving players")
//            // playerList.saveAllPlayerData()
//            //  playerList.removeAllPlayers()
//        }

//        if (world != null) {
//            log.info("Saving worlds")
//            //saveAllWorlds(false)
//
//
//            /*
//                          for (worldserver <- this.worldServers) {
//                            if (worldserver != null) worldserver.disableLevelSaving = false
//                        }
//            //todo  world.flush()
//                        for (worldserver1 <- this.worldServers) {
//                            if (worldserver1 != null) {
//                                net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new WorldEvent.Unload(worldserver1))
//                                worldserver1.flush()
//                            }
//                        }
//                        val tmp: Array[WorldServer] = worldServers
//                        for (world <- tmp) {
//                            net.minecraftforge.common.DimensionManager.setWorld(world.provider.getDimension, null, this)
//                        }
//            */
//        }
    }

    def tick(): Unit = {
        futureTaskQueue synchronized {
            while (futureTaskQueue.nonEmpty){
                val a =  futureTaskQueue.dequeue()
                if(a!= null){
                    a()
                }
            }

        }



        networkSystem.networkTick()
       // world.update()

    }

    override def addPacket(packet:()=>Unit): Unit = {
        futureTaskQueue += packet
    }

    def getServerStatusResponse: ServerStatusResponse = statusResponse
}

object OrangeMServer {
    def getCurrentTimeMillis: Long = System.currentTimeMillis
}