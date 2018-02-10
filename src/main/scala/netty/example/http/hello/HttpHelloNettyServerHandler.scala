package netty.example.http.hello

import io.netty.buffer.Unpooled
import io.netty.channel.{ChannelFuture, ChannelFutureListener, ChannelHandlerContext, ChannelInboundHandlerAdapter}
import io.netty.handler.codec.http.HttpHeaders.Names._
import io.netty.handler.codec.http.HttpHeaders._
import io.netty.handler.codec.http.HttpResponseStatus._
import io.netty.handler.codec.http.HttpVersion._
import io.netty.handler.codec.http.{DefaultFullHttpResponse, HttpRequest}
import io.netty.util.CharsetUtil

/**
  * Created by zhaolei on 2018/2/10
  */
class HttpHelloNettyServerHandler extends ChannelInboundHandlerAdapter {

  import HttpHelloNettyServerHandler.CONTENT

  override def channelRead(ctx: ChannelHandlerContext, msg: scala.Any): Unit = {
    msg match {
      case req: HttpRequest =>
        if (is100ContinueExpected(req)) {
          ctx.write(new DefaultFullHttpResponse(HTTP_1_1, CONTINUE))
        }

        val keepAlive = isKeepAlive(req)
        val response = new DefaultFullHttpResponse(HTTP_1_1, OK, CONTENT)
        response.headers().set(CONTENT_TYPE, "text/plain")
        response.headers().set(CONTENT_LENGTH, response.content().readableBytes())
        if (keepAlive) response.headers().set(CONNECTION, Values.KEEP_ALIVE)

        val future: ChannelFuture = ctx write response
        if (!keepAlive) future.addListener(ChannelFutureListener.CLOSE)
      case _                =>
    }
  }

  override def channelReadComplete(ctx: ChannelHandlerContext): Unit = {
    ctx.flush()
  }

  override def exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable): Unit = {
    cause.printStackTrace()
    ctx.close()
  }

}


object HttpHelloNettyServerHandler {
  private final val CONTENT = {
    Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("hello world", CharsetUtil.US_ASCII))
    //    Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("hello world", CharsetUtil.UTF_8))
  }
}