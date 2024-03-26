pipeline {
    agent any

    stages {
        stage('SonarQube Analysis') {
            steps {
                bat './mvnw -Pprod clean verify sonar:sonar'
            }
        }
    }
}
