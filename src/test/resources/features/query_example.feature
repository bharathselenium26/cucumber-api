Feature: Snowtooth Highway examples

   
@smoke
Scenario: Verify Query - All Lifts
		Given Application "http://snowtooth.moonhighway.com/" is initialized
		Then fetch the details of all lifts
		And verify count "11"

    
@smoke
Scenario: Verify Query - All Lifts with payload
		Given Application "http://snowtooth.moonhighway.com/" is initialized
		Then query for all lifts 
		"""
		query lifts{
 			allLifts{
    		name,
    		status
  		} 
		}
		"""
		And verify count "11"