pipeline {
    agent { label 'Demo' }
    stages {
        stage('Compile/Test') {
            steps {
                withMaven(maven: 'Maven 3.8.6') {
                    sh "mvn clean install"
                }                
            }
        }
        stage('Report') {
            steps {
                junit allowEmptyResults: true, testResults: '**/*.xml'
            }
        }        
        stage('Source Code Check') {
            steps {
                echo "sonar scan"
            }
        }        
        stage('Deploy') {
            steps {
                withMaven(maven: 'Maven 3.8.6') {
                    sh "mvn deploy"
                }
            }
        }        
   }
   post {
       always {
           sh 'printenv'
           emailext body: '', recipientProviders: [developers()], subject: 'Build executed ', to: 'thomas@mosig-frey.de'
       }
       success {
            cleanWs()                               
       }
   }
}
