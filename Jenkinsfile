pipeline {
    agent {
        docker {
            image 'docker:stable'
            args '-v /var/run/docker.sock:/var/run/docker.sock'
        }
    }
    stages {
        stage('Prepare') {
            steps {
                script {
                    // Change directory to ./src/main/docker
                    dir('./src/main/docker') {
                        // Execute docker compose command
                        sh 'docker compose -f app.yml up'
                    }
                }
            }
        }
    }
}
