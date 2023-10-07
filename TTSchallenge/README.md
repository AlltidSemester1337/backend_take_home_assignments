# TTS Backend Take Home Challenge

Thank you for your interest in working at Taptap Send. If you are reading this then it means we are excited about you and want to know more about how you approach technical problems. This task is written in Java, but if you prefer to convert it to Kotlin (our backend language of choice) then feel free.

This should take no more than 3 hours. Furthermore, we would like to see your work within 3 business days of receiving this challenge. Please let us know if that will be a problem. When you've completed the task, please send us a link to a Github repository with your work. Someone on our team will review within 1 business day and follow up with you about next steps.

If you have any questions or need clarification, don't hesitate to ask. Good luck!

### Requirements

You are writing code for a company that builds software to track and invoice for movie rentals and are asked to add a new billing rule for classic movies:

- Renting a classic movie costs $1 per day, earns 1 reward point per day, and has no fixed rental cost.

The codebase works in its current form, but was originally written by a junior engineer. Refactor the code as you see necessary and add the new billing rule. Make reasonable assumptions and document them.

We will check that your code works, but are more interested in your approach to refactoring, code design, and the decisions you make. We will be asking you about the decisions you made in the next phase of the the interview so be prepared to speak to them.


Approach (not to be kept, only for the purpose of the challenge as requested)
- Implement regression tests at a comfortable level
- Redesign / refactor parts of the code in order to not add further tech debt upon introducing new code
-  - This includes introducing the RentalService as main point of interaction with the service (instead of it being decentralized and harder to understand)
   - Refactor / Extend Rental class and introduce Account concept for total cost and points calculation
   - Delegate some responsibilites of Customer class, break the statement method (currently violating SRP) into smaller ones, refactor and place them where appropriate (for example don't mix concept of calculating one rental cost with summing up all)
   - Refactor rental cost and reward points calculation to improve readability, maintainability and testability, introduce delegate cost and long term rate rules to MovieType
   - Implement new business requirement acceptance / unit test and code
- ???
- Profit