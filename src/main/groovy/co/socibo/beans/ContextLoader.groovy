package co.socibo.beans

import org.apache.camel.CamelContext
import org.apache.camel.Exchange
import org.apache.camel.spring.SpringCamelContext
import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext
import org.springframework.context.support.FileSystemXmlApplicationContext

class ContextLoader {
  public void load(Exchange ex){

      ApplicationContext parentCtx = ex.context.applicationContext

      ApplicationContext newCtx = new FileSystemXmlApplicationContext([ex.in.headers.CamelFilePath] as String[], parentCtx)
      newCtx.refresh();
      newCtx.getBeansOfType(CamelContext.class)*.getValue().each { ctx ->
          if(ctx.isAutoStartup() == false) {
              ctx.start()
              //ctx.getRouteDefinitions()*.start(); // should also start routes
          }
      }
  }
}
