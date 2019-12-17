package rockthejvm.part3_testing;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import java.io.IOException;

public class AkkaQuickstart {
    public static void main(String[] args) {

        ActorSystem actorSystem = ActorSystem.create("AkkaQuickstart");
        final ActorRef printerActor = actorSystem.actorOf(Printer.props());
        final ActorRef greeterMain = actorSystem.actorOf(Greeter.props("Hello", printerActor));

        // On envoi un premier message pour initialiser la variable 'greeting' de l'acteur Greeter
        greeterMain.tell(new Greeter.WhoToGreet("Georges"), ActorRef.noSender());
        // Le deuxième message est un signal qui va envoyer un message Greeting au printerActor
        greeterMain.tell(new Greeter.Greet(), ActorRef.noSender());

        try {
            System.out.println(">>> Press ENTER to exit <<<");
            System.in.read();
        } catch (IOException ignored) {
        } finally {
            actorSystem.terminate();
        }
    }
}
