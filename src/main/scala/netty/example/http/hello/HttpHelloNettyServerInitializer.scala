package netty.example.http.hello


import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel
import io.netty.handler.codec.http.HttpServerCodec

/**
  * Created by zhaolei on 2018/2/10
  */
class HttpHelloNettyServerInitializer extends ChannelInitializer[SocketChannel] {
  override def initChannel(ch: SocketChannel): Unit = {
    val p = ch.pipeline()
    p.addLast("code", new HttpServerCodec())
    p.addLast("handler", new HttpHelloNettyServerHandler())
  }
}
