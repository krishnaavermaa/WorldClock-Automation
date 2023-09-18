# World Clock
#### _Major Project_

This is an automation project to automate various User Behaviour driven actions and perform tests.

## Tech Stack
- **IDE :** Eclipse Enterprise Edition
- **LANGUAGES :** JAVA, Gherkin
- **FRAMEWORKS :** Selenium JAVA, junit 4
- **TOOLS :** Apache Maven, Cucumber

### Development Methodology
- Behavioural Driven Development 

### Design Pattern
- Page Object Model

## Features
### User info feature on becognizant homepage
#### **Scenario:** Display User Info
- **Given** user is on becognizant homepage
- **When** user clicks on user profile icon
- **Then** user info should be displayed

### World Clock feature on becognizant homepage
#### **Background:**  
- **Given** user is on becognizant homepage
- **When** user scrolls down to world clock section

#### **Scenario:**  Display World Clock to user
- **Then** world clock should be displayed

#### **Scenario:**  Verify times for all timezones
- **Then** the Bangalore Time should match the System Time
- **And** every other displayed timezone time should match the time as per google data

#### **Scenario:**  Verify Bangalore date, day and week
- **Then** the Bangalore date should match the System Date
- **And** the Bangalore Day should match the System Day
- **And** the Bangalore week should match the System week

#### **Scenario:**  Verify time differences displayed
- **Then** show correct time gap between Bangalore and every other timezone time displayed

### Hot Apps feature in onecognizant Webapp
#### **Background:**  
- **Given** user is on becognizant homepage
- **When** user navigates to onecognizant app using quicklink
- **And** user click on View All Apps button under Hot Apps

#### **Scenario:**  Verify A to Z alphabets displayed
- **Then** world clock should be displayed

#### **Scenario:**  Verify clicks not working on disabled alphabets
- **And** some alphabets are disabled
- **Then** disabled alphabets should not be clickable

#### **Scenario:**  Display all apps name under a random active alphabet
- **Then** user randomly clicks on any one active alphabet
- **And** all app names starting with that alphabet should be displayed

### Authors
#### Krishna Verma
##### QEA Intern
##### Cognizant
##### Noida (IND)
##### 2269552@cognizant.com
