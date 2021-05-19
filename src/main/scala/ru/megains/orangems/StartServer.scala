package ru.megains.orangems

import scala.reflect.io.Path

object StartServer extends App {

    try {

        val path = Path("Z:/OrangeM/Server").toDirectory
        Thread.currentThread.setName("Server")
        val server = new OrangeMServer(path)
        val serverCommand = new ServerCommand(server)
        serverCommand.setName("serverControl")
        serverCommand.setDaemon(true)
        serverCommand.start()

        server.run()
    } catch {
        case e:Exception => e.printStackTrace()
    }
}
