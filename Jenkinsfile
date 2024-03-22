pipeline {
    agent any
    stages {
        stage('Build and Deploy') { // Added stage name
            steps {
                script {
                    bat 'docker compose -f src/main/docker/app.yml up'
                }
            }
        }
    }
}
