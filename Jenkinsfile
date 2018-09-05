pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh './gradlew build'
      }
    }
    stage('Generate Code Coverage Report') {
      steps {
        sh './gradlew jacocoTestReport'
      }
    }
    stage('Generate Sonarqube') {
      steps {
        sh './gradlew sonarqube'
      }
    }
  }
}