package co.socibo

import org.apache.camel.CamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

class Main {

    static void main(String[] args) {
        def cli = new CliBuilder()
        // cli.with {
        //     a longOpt: 'account', args: 2, required: true, 'twitter account'
        //     p longOpt: 'password', args: 2, required: true, 'twitter password'
        // }
        // def options = cli.parse(args)
	ApplicationContext ctx = new ClassPathXmlApplicationContext("camelContext.xml")
        def camelContext = ctx.getBean("mainCamelContext")
        camelContext.start()
        while (!camelContext.isStopped()) {
            Thread.yield();
        }
    }
}
