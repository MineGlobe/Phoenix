pipeline {
    agent any

    tools {
        maven 'Maven 3.8.6'
        jdk 'jdk-17'
    }

    stages {
        stage('Clone') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh "mvn clean install -U"
            }
        }

        stage('Archive') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
    }
}
