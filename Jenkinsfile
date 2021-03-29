pipeline {
    agent any
    tools {
        maven 'Apache Maven 3.6.3'
        jdk 'JDK1.8'
    }
    stages {

        stage ('Compile Stage') {
            steps {
                sh 'mvn clean compile' 
            }
        }
        stage ('Testing Stage') {
            steps {
                sh 'mvn clean install'               
            }
        }
        //stage('Deployment Stage') {
        //    steps {
        //        sh 'mvn deploy'
        //    }
        //}
    }
}
