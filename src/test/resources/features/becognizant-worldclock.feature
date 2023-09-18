Feature: World Clock feature on becognizant homepage 

Background:
	Given user is on becognizant homepage 
	When user scrolls down to world clock section 
	
Scenario: Display World Clock to user 
	Then world clock should be displayed 
	
Scenario: Verify times for all timezones 
	Then the Bangalore Time should match the System Time 
	And every other displayed timezone time should match the time as per google data
	
Scenario: Verify Bangalore date, day and week 
	Then the Bangalore date should match the System Date 
	And the Bangalore Day should match the System Day 
	And the Bangalore week should match the System week 
	
Scenario: Verify time differences displayed 
	Then show correct time gap between Bangalore and every other timezone time displayed
	
	
	
