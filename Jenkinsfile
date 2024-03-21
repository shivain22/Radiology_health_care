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
    
    post {
        always {
            // Cleanup after the build
            script {
                dir('./src/main/docker') {
                    // Stop and remove the containers after the build completes
                    sh 'docker-compose down'
                }
            }
        }
    }
}
