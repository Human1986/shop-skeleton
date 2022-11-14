# Shop (cashboxes)

The purpose of this exercise is to train you to work with queues.  
The estimated workload is *120 min*.

## Description 

A shop has **N** cashboxes. Every cashbox is numbered from 0 up to N-1 and can be in one of the following states, decsribed by the `CashBox.State` enum:  
* ENABLED - enabled cashbox can have a not empty queue of buyers, it can serve buyers, new buyers can be added to the end of its queue;  
* IS_CLOSING - cashbox is in a process of a closing, it serves existed buyers, but no new buyers can be added to its queue;  
* DISABLED - this cashbox has an empty queue and cannot serve any buyer.  


When the `tact` method of class `Shop` is invoked:  
* every non empty queue is decreased by 1 buyer (this one is removed from the head of a queue);  
* all queues are balanced by a count of buyers if necessary;  
* cashbox by state `IS_CLOSING` changes its status to `DISABLED` if a last buyer just has been served (i.e. size of its queue will become equal to 0).  

If a cashbox changes its status from `DISABLED` to `ENABLED`, then buyers from the tail of other queues go to the tail of queue of this cashbox.  

Length of queues cannot be differ in more than 1. But a queue with a state of IS_CLOSING can have less buyers than other queues.  

> Cashbox with a lower number must not have less buyers than a cashbox with a bigger number, if both are in `ENABLED` state.  

Please, proceed to the class `CashBox` and implement the following methods:  

* `public Deque<buyer> getQueue()`  
Returns a copy of queue.  

* `public buyer serveBuyer()`  
Serves a buyer if a queue is not empty, changes a state from IS_CLOSING to disable if necessary.  

* `public boolean inState(State state)`  
Checks if this cashbox has the specified state.  

* `public void add(buyer buyer)`  
Adds a bayer to the end of this cashbox queue.  

* `public Buyer removeLast()`  
Retrieves and removes the last buyer of this cashbox queue.

* `public String toString()`  
Generates a textual reperentation of this cashbox (see an example below).

* `setter/getter`  
Sets/returns a state of this cashbox.  

Please, proceed to the class `Shop` and implement the following methods:  

* `public void addBuyer(Buyer buyer)`  
Adds a buyer to the end of the shortest queue; if there are more than one shortest queue, adds to the end of the queue which belongs to cashbox with lower number.  

* `public CBox getCBox(int cboxNumber)`  
Returns a cashbox by its number; throws `IllegalArgumentException` if a number is out of range.  

* `public void setCashBoxState(int cboxNumber, State state)`  
Sets a new state for cashbox with the specified number; throws `IllegalArgumentException` if a number is out of range.   

* `public void tact()`  
Does one tact (see description above).  

> You can add your own methods if you want. Don't change any content of the `Buyer` and `Demo` classes.  

## Description of how to balance queues of cashboxes

When at least one cashbox changes its state from `DISABLED` to `ENABLED` all queues must be balanced by count of buyers. Please use the following algorithm to redistibute buyers by cashbox queues.
* Calculate minimum and maximum values of size of queues after redistribution. 
* Create a new queue `defector-buyers` of buyers who leave there queues.  

* Iterate over all queues in ascending order by its number and do the following:
    * calculate how much buyer have to leave this queue, for example it is **k** buyers (**k** >= 0);
    * move **k** buyers from the end of this queue to the end of the `defector-buyers` queue.

* Iterate over all queues in ascending order by number and do the following:
    * calculate how much buyer have to be added to this queue, for example it is **k** buyers (**k** >= 0);
    * move **k** buyers from the head of `defector-buyers` queue to the end of this queue (remember that queue with the state IS_CLOSING cannot be extended).

> Restrictions:
You may not use lambda and Streams during the implementation of this task.

### Example
If the initial state is as the following
```
#0[+] ABCDE
#1[+] 
#2[+] FGHI
#3[+] 
#4[-] 
```
then method `tact` serves two buyers A and F and balances queues.  
When A and F are removed, total number of buyers will be 7.
There are 4 not `DISABLED` queues, so after the redistribution we have minimum number of buyers in queue is 1, and maximum number of buyers in queue is 2.
```
#0[+] BC  (DE ==> defector buyers)
#1[+] 
#2[+] GH  (I ==> defector buyers)
#3[+] 
#4[-] 
```

`defector-buyers` = [E, D, I]

Buyers from `defector-buyers` are redistributed among all (enabled) queues:
```
#0[+] BC
#1[+] ED
#2[+] GH
#3[+] I
#4[-] 
```

## Output example
The `main` method of class `Demo` have to produce the following output:
```
Initial state
#0[-] 
#1[-] 
#2[-] 
#3[-] 
#4[-] 
==============
Enable cboxes 0, 2, 4
#0[+] 
#1[-] 
#2[+] 
#3[-] 
#4[+] 
==============
Add 5 buyers
#0[+] AD
#1[-] 
#2[+] BE
#3[-] 
#4[+] C
==============
Add 8 buyers (total 13 buyers)
#0[+] ADGJM
#1[-] 
#2[+] BEHK
#3[-] 
#4[+] CFIL
==============
Set cbox #2 is closing
#0[+] ADGJM
#1[-] 
#2[|] BEHK
#3[-] 
#4[+] CFIL
==============
Do 1 tact
#0[+] DGJM
#1[-] 
#2[|] EHK
#3[-] 
#4[+] FIL
==============
Enable cbox #1, add 7 buyers
#0[+] DGJMS
#1[+] NOPQT
#2[|] EHK
#3[-] 
#4[+] FILR
==============
Enable cbox #3, do 1 tact
#0[+] GJM
#1[+] OPQ
#2[|] HK
#3[+] ST
#4[+] IL
==============
Do 1 tact
#0[+] JM
#1[+] PQ
#2[|] K
#3[+] T
#4[+] L
==============
Do 1 tact
#0[+] M
#1[+] Q
#2[-] 
#3[+] 
#4[+] 
```
