package ru.megains.orangems.network.protocol

import io.netty.channel.{ChannelInitializer, ChannelOption}
import io.netty.channel.socket.nio.NioSocketChannel
import ru.megains.orangem.network.NetworkManager
import ru.megains.orangem.network.protocol.{OrangeMCodec, OrangeMMessageCodec}
import ru.megains.orangems.OrangeMServer
import ru.megains.orangems.network.NetworkSystem
import ru.megains.orangems.network.handler.NetHandlerHandshake


class OrangeMServerChannelInitializer(server:OrangeMServer) extends ChannelInitializer[NioSocketChannel]{


    override def initChannel(ch: NioSocketChannel): Unit = {
        val networkManager = new NetworkManager(server)
        ch.pipeline()
                .addLast("serverCodec",new OrangeMCodec)
                .addLast("messageCodec",new OrangeMMessageCodec)
                .addLast("packetHandler", networkManager)
        ch.config.setOption(ChannelOption.TCP_NODELAY, Boolean.box(true))



        networkManager.setNetHandler(new NetHandlerHandshake(server, networkManager))
        NetworkSystem.networkManagers += networkManager
    }
}
