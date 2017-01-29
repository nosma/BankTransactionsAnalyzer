[![Code Climate](https://codeclimate.com/github/nosma/BankTransactionsAnalyzer/badges/gpa.svg)](https://codeclimate.com/github/nosma/BankTransactionsAnalyzer)
[![Issue Count](https://codeclimate.com/github/nosma/BankTransactionsAnalyzer/badges/issue_count.svg)](https://codeclimate.com/github/nosma/BankTransactionsAnalyzer)
[![Build Status](https://travis-ci.org/nosma/BankTransactionsAnalyzer.svg?branch=master)](https://travis-ci.org/nosma/BankTransactionsAnalyzer)

# Overview
Manage your bank transactions by importing 

1. Midata files
2. Transactions in csv format

and get statistics for them. 


# Setup
The preparation for the execution of the application needs a small setup of the properties file. 
This file is the **application.properties**
  
The values that 
* initial.balance: Amount in decimal format (ex: 00.000)
* transactions.directory: Directory where uploaded files will stored (ex: my/upload/directory)
* transaction.tags: Comma separated values for tags (ex: Income,Expenses,Commute,Home etc)
* server.port: Change if the default value creates conflict with another web app, else leav it as it is.

# Execution
Open you terminal and navigate to the directory where you have unzipped the app.

1. Execute the following command ./start.sh
2. Go to your browser and type [http://localhost:9999](http://localhost:9999) 

The web application will start on the **server.port** that you have specified in the application.properties file.

# Contribution

Here’s how we suggest you go about proposing a change to this project:

1. [Fork this project](https://help.github.com/articles/fork-a-repo/) to your account.
2. [Create a branch](https://help.github.com/articles/creating-and-deleting-branches-within-your-repository/) for the change you intend to make.
3. Make your changes to your fork.
4. [Send a pull request](https://help.github.com/articles/about-pull-requests/) from your fork’s branch to our **master** branch.