Feature: Hot Apps feature in onecognizant Webapp 

Background: 
	Given  user is on becognizant homepage 
	When user navigates to onecognizant app using quicklink
	And user click on View All Apps button under Hot Apps
	
Scenario: Verify A to Z alphabets displayed
	Then show A to Z alphabets
	
Scenario: Verify clicks not working on disabled alphabets
	And some alphabets are disabled
	Then disabled alphabets should not be clickable
	
Scenario: Display all apps name under a random active alphabet
	Then user randomly clicks on any one active alphabet
	And all app names starting with that alphabet should be displayed
