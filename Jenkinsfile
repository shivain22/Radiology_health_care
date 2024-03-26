pipeline {
    agent any

    stages {
        stage('SonarQube Analysis') {
            steps {
                bat 'mvn clean verify sonar:sonar'
            }
        }
    }
}
