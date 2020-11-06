# Tic Tac Toe Client-Server (GUI)

This is a GUI based version of tic tac toe using socket programming and a client-server architecture and MVC modelling.
The program is designed to have multiple tic tac toe games running or your computer using a threadpool to handle the multiple requests

## Requirements

- [Java SDK](https://www.oracle.com/java/technologies/javase-downloads.html) minimum Java 8.x.x

## How to Run

1. Start the server
In your terminal run the following:

```shell
javac server/control/TicTacToeServer.java
java server/control/TicTacToeServer
```
**IMPORTANT:** DO NOT CLOSE THIS TERMINAL!

2. Open up two other terminals from either your machine or other computers

3. In the other terminals run the following commands:

```shell
javac client/control/GameListener.java
java client/control/GameListener
```

And follow the instructions on the screen.

Enjoy! :smile:

**NOTE:** If you are running the client code on the same machine as the server, you can enter `localhost` as the IP address. Otherwise, enter the IP address of the server computer

See [the sample output](SampleOutput.pdf) if you require any clarification