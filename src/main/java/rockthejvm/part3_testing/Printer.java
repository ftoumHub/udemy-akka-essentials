package rockthejvm.part3_testing;

import akka.actor.AbstractActor;
import akka.actor.AbstractLoggingActor;
import akka.actor.Props;

public class Printer extends AbstractLoggingActor {

    static public Props props() {
        return Props.create(Printer.class, () -> new Printer());
    }

    static public class Greeting {
        public final String message;

        public Greeting(String message) {
            this.message = message;
        }
    }


    public Printer() {
    }

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(Greeting.class, greeting -> log().info(greeting.message))
                .build();
    }

}
