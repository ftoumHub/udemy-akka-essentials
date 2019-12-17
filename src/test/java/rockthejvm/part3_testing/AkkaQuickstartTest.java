package rockthejvm.part3_testing;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import rockthejvm.part3_testing.Greeter;
import rockthejvm.part3_testing.Greeter.*;
import rockthejvm.part3_testing.Printer;
import rockthejvm.part3_testing.Printer.*;

import static org.junit.Assert.assertEquals;

public class AkkaQuickstartTest {
    static ActorSystem system;

    @BeforeClass
    public static void setup() {
        system = ActorSystem.create();
    }

    @AfterClass
    public static void teardown() {
        TestKit.shutdownActorSystem(system);
        system = null;
    }

    @Test
    public void testGreeterActorSendingOfGreeting() {
        final TestKit testProbe = new TestKit(system);
        final ActorRef greeter = system.actorOf(Greeter.props("Hello", testProbe.getRef()));
        // On envoi un premier message pour initialiser la variable 'greeting' de l'acteur Greeter
        greeter.tell(new WhoToGreet("Akka"), ActorRef.noSender());
        // Le deuxième message est un signal qui va envoyer un message Greeting au printerActor
        greeter.tell(new Greet(), ActorRef.noSender());
        Printer.Greeting greeting = testProbe.expectMsgClass(Greeting.class);
        assertEquals("Hello, Akka", greeting.message);
    }
}
