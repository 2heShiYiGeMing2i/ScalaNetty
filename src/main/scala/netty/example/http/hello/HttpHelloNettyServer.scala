package netty.example.http.hello

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel

/**
  * Created by zhaolei on 2018/2/10
  */
object HttpHelloNettyServer extends App {
  val port = args.headOption.map(_.toInt).getOrElse(9090)
  private val bossGroup = new NioEventLoopGroup()
  private val workerGroup = new NioEventLoopGroup()
  try {
    val bootstrap = new ServerBootstrap()
    bootstrap.option(ChannelOption.SO_BACKLOG, Int.box(1024))
      .group(bossGroup, workerGroup)
      .channel(classOf[NioServerSocketChannel])
      .childHandler(new HttpHelloNettyServerInitializer)

    val ch = bootstrap.bind(port).sync().channel()
    ch.closeFuture().sync()
  } finally {
    bossGroup.shutdownGracefully()
    workerGroup.shutdownGracefully()
  }

}
