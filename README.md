# OsSyncProblems

## Operating System Sync Problems And Solutions.

This project shows 3 main problems in operating system synchronizations of process.

I implement the problems and the solutions to those problems and implement simple GUI to this problems.

I implement the problems and the solutions over threads and not over process to keep this more simple.

##### Please feel free to send me your notes and thoughts.

##### meron100@gmail.com

## Dining Philosopher
There is five philosophers.

Every philosopher have 3 state:
1) think (blue in GUI).
2) hungry (red in GUI).
3) eat (green in GUI).

Between every two philosopher there is chopstick.

When philosopher want to eat he go to state hungry try to get two chopsticks (one from his left size and one from his right side) to eat from his plate.

If he cant get two chopsticks he wait in 'hungry' state until he can get the two chopsticks and eat.

There is no way that two neighbor philosophers eat at the same time because there is one shared chopstick between them.

**_Note :  that there is few GUI problem with this, will be fixed._**

##### To run this GUI please run the **DinningTable** class.  

## Producer Consumer
The problem is that the Producer produce an item and the Consumer consume it.

When the producer produce an item he add it to the buffer and when the consumer consume an item he remove the item from the buffer 

The problem is that the producer and the consumer cannot reach the buffer at the same time.

So in the solution we try to synchronize between the producer process and the consumer process.

We want in our solution few things : 
1) The producer will not produce more item if the buffer is full and will continue to produce if there is space int he buffer.
2) The consumer will not try to consume item if the buffer is empty.
3) The producer and the consumer will not access the buffer at the same time. 
 
you can set in the GUI few parameters :
1) `buffer size:`  the max buffer size (Default is 10).
2) `time to produce:` we random time in second's between 1 to input parameter, time to produce the item (Default is 2).
3) `time to consume:` we random time in second's between 1 to input parameter, time to consume the item (Default is 5).

I set the default consume time greater then the default produce time  so the buffer will increase. 

#### To run this GUI please run **ProducerConsumerGUI** class. 

## Reader Writer
The problem is that we **can't** have two or more writers inside the file at the same time, only one writer can be inside the file at the same time.

We can have two or **more** reader's inside the file at the same file at the same time.

But we **can't** have reader and writer inside the file at the same time.

you can set in the GUI few parameters :
1) `time to read:` we random time in second's between 1 to input parameter, time to read the file (Default is 2. 
2) `time interval (Reader):` we random time in second's between 1 to input parameter, time to generate new Reader (Default is 2).
3) `time to write:` we random time in second's between 1 to input parameter, time to write inside the file (Default is 2).
4) `time interval (Writer):` we random time in second's between 1 to input parameter, time to generate new Writer (Default is 2).

#### To run this GUI please run **ReaderWriter** class.

