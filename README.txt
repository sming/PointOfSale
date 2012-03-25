README

CODING EXERCISE CONTEXT
===============================================================================
We are looking for 
	clean, 
	simple, 
	testable, 
	object-oriented code 
	
written in Java or Ruby. We ask that you don't use any external libraries to solve this problem; however, you may use external libraries or tools for build or testing purposes. Specifically, you may use JUnit, RSpec, and Ant to assist your development.

For the input to the problem you are free to implement any mechanism for feeding input into your solution (for example, using hard coded data within a unit test). You should provide sufficient evidence that your solution is complete by, as a minimum, indicating that it works correctly against the supplied test data.

Solutions should be zipped up and emailed back in 2-3 days.

Please let me know if you have any questions.


PROBLEM DEFINITION
===============================================================================
Consider a store where items have prices per unit but also volume prices. For example, apples may be $1.00 each or 4 for $3.00.

Implement a point-of-sale scanning API that accepts an arbitrary ordering of products (similar to what would happen at a checkout line) and then returns the correct total price for an entire shopping cart based on the per unit prices or the volume prices as applicable.

Here are the products listed by code and the prices to use (there is no sales tax):
Product Code | Price
--------------------
A            | $2.00 each or 4 for $7.00
B            | $12.00
C            | $1.25 or $6 for a six pack
D            | $0.15

For your solution, we ask that you use either Java or Ruby. There should be a top level point of sale terminal service object that looks something like the pseudo-code below. You are free to design and implement the rest of the code however you wish, including how you specify the prices in the system:

terminal.setPricing(...)
terminal.scan("A")
terminal.scan("C")
... etc.
result = terminal.total

Here are the minimal inputs you should use for your test cases. These test cases must be shown to work in your program:

Scan these items in this order: ABCDABAA; Verify the total price is $32.40.
Scan these items in this order: CCCCCCC; Verify the total price is $7.25.
Scan these items in this order: ABCD; Verify the total price is $15.40.
