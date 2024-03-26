pipeline {
    agent any

    stages {
        stage('SonarQube Analysis') {
            steps {
                bat 'npm run webapp:test'
            }
        }
    }
}
