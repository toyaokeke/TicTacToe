# Tic Tac Toe Client-Server

This is a console based version of tic tac toe using socket programming and a client-server architecture.
The program is designed to have multiple tic tac toe games running or your computer using a threadpool to handle the multiple requests

## Requirements

- [Java SDK](https://www.oracle.com/java/technologies/javase-downloads.html) minimum Java 8.x.x

## How to Run

1. Start the server
In your terminal run the following:

```shell
javac ticTacToe_Server/TicTacToeServer.java
java ticTacToe_Server/TicTacToeServer
```
**IMPORTANT:** DO NOT CLOSE THIS TERMINAL!

2. Open up two other terminals

3. In the other terminals run the following commands:

```shell
javac ticTacToe_Client/TicTacToeClient.java
java ticTacToe_Client/TicTacToeClient
```
And follow the instructions on the screen.

Enjoy! :smile:

See [the sample output](SampleOutput.pdf) if you require any clarification