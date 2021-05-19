package ru.megains.orangems.network

import java.net.InetAddress

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.channel.{ChannelFuture, EventLoopGroup}
import ru.megains.orangem.network.NetworkManager
import ru.megains.orangems.OrangeMServer
import ru.megains.orangems.network.protocol.OrangeMServerChannelInitializer

import scala.collection.mutable.ArrayBuffer

class NetworkSystem(server: OrangeMServer) {



    var networkServer: ServerBootstrap = _
    var channelFuture: ChannelFuture  = _
    var bossExec: EventLoopGroup = _

    def startLan(address: InetAddress, port: Int): Unit = {
        bossExec = new NioEventLoopGroup(0)

        networkServer = new ServerBootstrap()
                .group(bossExec)
                .localAddress(address, port)
                .channel(classOf[NioServerSocketChannel])
                .childHandler(new OrangeMServerChannelInitializer(server))
        channelFuture = networkServer.bind.syncUninterruptibly()
        //channelFuture = networkServer.bind.sync().channel().closeFuture().syncUninterruptibly()
    }

    def networkTick():Unit = {

        NetworkSystem.networkManagers = NetworkSystem.networkManagers.flatMap(
            networkManager => {

                if (!networkManager.hasNoChannel) {
                    if (networkManager.isChannelOpen) {

                        try {
                            networkManager.processReceivedPackets()
                        } catch {
                            case exception: Exception =>
                                exception.printStackTrace()
                        }
                        Some(networkManager)
                    } else {
                        networkManager.checkDisconnected()
                        None
                    }
                } else {
                    Some(networkManager)
                }

            }

        )

    }
    def stop(): Unit = {
       bossExec.shutdownGracefully()
    }

}

object NetworkSystem {
    var networkManagers: ArrayBuffer[NetworkManager] = new ArrayBuffer[NetworkManager]
}
