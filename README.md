# FinanceBot

FinanceBot is an application that handles the overdraft for you to minimise the overdraft fees. 

## Installation

Clone the repository from the github repository using
```bash
git clone https://github.com/Lismera/FinanceBot.git
```
After cloned successfully, place the customer files in .csv format in the main directory (where BankingMain.java is). 
The program was developed on Java11, so it is recommended to use Java 11+ 

After the customer files are in the main directory, you need to compile the program. This is done via the following commands:

```bash
javac BankingMain.java
```
## Usage

After compiled once, the program is ready for use by just running:
```bash
java BankingMain <input filename> <output filename>
```
Filename format is X.csv - no need to put the full path in.

## Assumptions
I have made the following assumptions while making the program:
- Each customer can have only 1 account of each type.
- Customer input csv is in correct order (date-wise) for processing.
- Each csv is formatted with the same headers in the given order. 
  - AccountID
  - AccountType
  - InitiatorType
  - DateTime
  - TransactionValue
- All the data is in the correct format (for example, no letters in numerical fields)