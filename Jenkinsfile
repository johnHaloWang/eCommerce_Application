pipeline {
    agent any
    tools {
        maven 'Apache Maven 3.6.3'
        jdk 'JDK1.9'
    }
    stages {

        stage ('Compile Stage') {
            steps {
                sh 'mvn clean compile' 
            }
        }
        stage ('Testing Stage') {
            steps {
                sh 'mvn test'               
            }
        }
        stage('Clean Install Stage') {
            steps {
                sh 'mvn clean install'
            }
        }
    }
}
