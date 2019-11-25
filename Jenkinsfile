pipeline {
	agent {
		node {
			label 'master'
		}
	}
	
	options{
		timestamps()
	}
	
	
	stages{
		stage("Checkout, Test & Publish") {
			steps{
				checkout scm
				
				script{
					bat(/mvn clean  test /)
				}
				
				step([$class : 'Publisher', reportFilenamePattern : '**/testng-results.xml'])
			}
		}
		
		stage ('Cucumber Reports') {

            steps {
                cucumber buildStatus: "UNSTABLE",
                    fileIncludePattern: "**/cucumber.json",
                    jsonReportDirectory: 'target'

            }

        }
		stage("Email"){
			steps{
				emailext (to: 'sample@gmail.com', replyTo: 'sample@gmail.com', subject: "Email Report from - '${env.JOB_NAME}' ", body: readFile("target/surefire-reports/emailable-report.html"), mimeType: 'text/html');
			}
		}
	}
	

}