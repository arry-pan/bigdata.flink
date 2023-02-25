package operators

import org.apache.flink.streaming.api.functions.source.SourceFunction

import java.util.Calendar
import scala.util.Random

class ClickSource extends SourceFunction[UserEvent]{
  //标志
  var runing = true

  override def run(ctx: SourceFunction.SourceContext[UserEvent]): Unit = {
    val random = new Random

    var users = Array("Aisha","Bob","Lily","anna")
    var urls = Array("/home","/cart","/product?id=1","/product?id=2","/product?id=3")

    while (runing){
      ctx.collect(UserEvent(users(random.nextInt(users.length)),
        urls(random.nextInt(urls.length)),
        Calendar.getInstance().getTimeInMillis))
      Thread.sleep(1000)
    }
  }

  override def cancel(): Unit = {
    runing =false
  }
}
