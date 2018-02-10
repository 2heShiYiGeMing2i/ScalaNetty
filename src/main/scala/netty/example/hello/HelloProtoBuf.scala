package netty.example.hello

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

import netty.example.message.messageOne.{MyMessage, MyMessageBodayInfo}

/**
  * Created by zhaolei on 2018/2/10
  */
object HelloProtoBuf {
  def main(args: Array[String]): Unit = {
    System.err.println("start  ...............")

    // 构造
    def messageId = 1

    def messageName = "name"

    def longId: Long = 10L

    def body: String = "body"

    def messageInfo = new MyMessageBodayInfo(longId, body)

    val message: MyMessage = new MyMessage(messageId, messageName, messageInfo)

    // 序列化
    val outPut = new ByteArrayOutputStream()
    System.err.println(s"序列化之前的数据:\n${message.toString}")
    message.writeTo(outPut)
    outPut.toByteArray.foreach(System.err.print)
    System.err.println()
    System.err.println(s"序列化的结果: \n${outPut.toByteArray}")

    val byteArray = outPut.toByteArray //序列化的数据 二进制数组
    System.err.println(s"byte array :\n$byteArray")
    // 反序列化
    val input = new ByteArrayInputStream(byteArray)
    val message1 = MyMessage.parseFrom(input)
    val message2 = MyMessage.parseFrom(byteArray)

    // 打印对象
    System.err.println("print object")
    System.err.println(message)
    System.err.println(message == message2)


  }
}
