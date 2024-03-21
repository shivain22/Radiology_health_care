pipeline {
    agent any
    stages {
        stage('Prepare') {
            steps {
                script {
                    
                        // Execute docker compose command
                        sh 'docker compose -f ./src/main/docker/app.yml up'
                }
            }
        }
    }
}
