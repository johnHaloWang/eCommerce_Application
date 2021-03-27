Jenkinsfile (Declarative Pipeline)
pipeline {
//  agent { docker { image 'maven:3.6.3' } }
 agent any
 stages {
     stage('Compile Stage') {
         steps {
             withMaven(maven: 'maven:3.6.3'){
                sh 'mvn clean compile'
             }

         }
     }
     stage('Testing Stage') {
              steps {
                  withMaven(maven: 'maven:3.6.3'){
                     sh 'mvn test'
                  }

              }
          }
    }
     stage('Deployment Stage') {
              steps {
                  withMaven(maven: 'maven:3.6.3'){
                     sh 'mvn deploy'
                  }

              }
          }
    }
}