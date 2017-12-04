# Test methodology

        
## Test principles 
1. Don't write code that you think you might need in future, but don't need yet.
1. Start with the test. The test should explain and guide the implementation.
1. The third time you write the same piece of code is the right time to extract it into a general-purpose helper (and write tests for it).
1. Tests don't need testing. Infrastructure, frameworks, and libraries for testing need tests.


## Package Structure for tests
Unit        tests:  All unit tests should follow the package structure of the code under the 
                    personal.bank.transaction.analyzer package.
                    
Integration tests:  The integration tests should get implemented under a specific package
                    personal.bank.transaction.analyzer.it