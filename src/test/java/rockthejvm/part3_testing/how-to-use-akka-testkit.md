https://heikoseeberger.rocks/2017/09/13/2017-09-13-how-to-use-akka-testkit/

#How to (not) use akka-testkit

Testing actors is different from “traditional” testing of objects or functions.
* First, the only way to interact with actors is via asynchronous messaging.
This means that we can’t simply call a method or function and compare the actual result with the expected.
* Second – thanks to the baked in concurrency – actors behave in a nondeterministic fashion.

Akka comes with a neat module for facilitating testing of actors named "**akka-testkit**".
It has been created quite a while ago and we – including the authors – have learned some lessons since then.
I’m going to introduce the tools provided by "**akka-testkit**" alongside with some recommendations on which to use and how.

## TestActorRef

Akka – the core “akka-actor” module, to be more precise – prevents us from getting access to an instance of an actor.
Yet "**akka-testkit**" introduces the **TestActorRef** which gives access to the **underlyingActor** instance.


While this might sound like a great idea, it turns out the opposite, because the **TestActorRef** brings its special 
dispatcher – the **CurrentThreadDispatcher** – which turns all interactions with the actor into synchronous ones. Clearly testing within this friendly sandboxed environment is far from real conditions, namely asynchrony and nondeterminism.

Usually not getting access to an instance of an actor is not an issue, because it should always be possible to separate 
our business logic from actor logic, e.g. by defining the business logic as methods in the companion object of the actor.
That way we can still test the business logic the “traditional” way. And for testing the actor logic we can use **TestProbes** – another tool provided by “akka-testkit” and introduced below.

## TestKit



