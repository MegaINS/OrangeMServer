package ru.megains.orangems.network.handler

import ru.megains.orangem.network.NetworkManager
import ru.megains.orangem.network.handler.INetHandlerLoginServer
import ru.megains.orangem.network.packet.login.client.CPacketLoginStart
import ru.megains.orangem.network.packet.login.server.SPacketLoginSuccess
import ru.megains.orangems.OrangeMServer
import ru.megains.orangems.entity.EntityPlayerMP


class NetHandlerLoginServer(server: OrangeMServer, networkManager: NetworkManager) extends INetHandlerLoginServer /*with ITickable*/ {

    var currentLoginState = LoginState.HELLO
    var name: String = _


    override def processLoginStart(packetIn: CPacketLoginStart): Unit = {
        currentLoginState = LoginState.READY_TO_ACCEPT
        name = packetIn.name
        tryAcceptPlayer()
    }


    override def onDisconnect(msg: String): Unit = {

    }

    def update() {
        if (currentLoginState eq LoginState.READY_TO_ACCEPT) tryAcceptPlayer()

        //        if ( {
        //            connectionTimer += 1; connectionTimer - 1
        //        } == 600) closeConnection("Took too long to log in")
    }

    def tryAcceptPlayer() {
        //  val s: String = server.playerList.allowUserToConnect(networkManager.getRemoteAddress, loginGameProfile)
        //  if (s != null) closeConnection(s)
        //   else {
        currentLoginState = LoginState.ACCEPTED
        networkManager.sendPacket(new SPacketLoginSuccess())

        var entityPlayer: EntityPlayerMP = server.playerList.getPlayerByName(name)
        if (entityPlayer == null) {
            entityPlayer = server.playerList.createPlayerForUser(name)
        }
        server.playerList.initializeConnectionToPlayer(networkManager, entityPlayer)
        // }
    }

    object LoginState extends Enumeration {
        type LoginState = Value
        val HELLO, KEY, AUTHENTICATING, READY_TO_ACCEPT, DELAY_ACCEPT, ACCEPTED = Value
    }

    override def disconnect(msg: String): Unit = {

    }
}


