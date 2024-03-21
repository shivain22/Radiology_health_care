pipeline {
    agent any
    
    stages {
        stage('Build and Deploy Docker Containers') {
            steps {
                // Change directory to the Docker folder
                script {
                    dir('./src/main/docker') {
                        // Run docker compose to bring up the containers defined in app.yml
                        sh 'docker-compose -f app.yml up -d'
                    }
                }
            }
        }
    }
}
