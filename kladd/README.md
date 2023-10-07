Task:
Java/Backend take-home assignment
Context
UUIDs version 1 are unique identifiers, encoded on 128 bits, and based on 3 quantities:
● A date-time on 60 bits
● A node id on 48 bits
● An uniquifying clock sequence on 14 bits
Task
Question 1: implement a simple generator that produces “unique” ids
Question 2: for auditing purposes, we need to be able to list all the ids generated on a given
day.
Design a system to save and retrieve these ids. Consider that writes are very frequent and in
the critical path, while reads are infrequent. (don’t implement the whole thing, just describe it)
Implement persisting the ids produced by your generator, using a dummy / abstract datastore
Expectations
● You’re expected to spend about 90 minutes on the assignment
● You can follow the UUID v1 specification, or use your own strategy to generate ids
● Don’t try to impress us with overly clever solutions. We're evaluating your ability to take a
set of requirements and produce clean, working, and thoughtful code
● Ideally your solution should run locally and have some unit tests
● Your deliverable is a zip file with your Java project, and a README file (any format) with
an explanation of your solution and answer to question 2



Explanation of solution:
You will find the entrypoint to this system in the UUIDService interface and implementation.
Hopefully the code should be quite self-explanatory with the tests and notes provided :).

Question 2: See UUIDService.getAllIdsForDate(), alternatively depending on persistence model used query the underlying storage directly.

Note: In general I dislike adding code comments in favor of method/variable naming, clean code etc.
However for the purpose of this task I felt like it would be preferable to couple the comments with the relevant code.

Regarding the described use case of frequent writes, for scalability purposes see the comments on UUIDServiceImpl,
the options can vary a lot depending on auditing and possibly other use cases for reading the persisted UUID values.