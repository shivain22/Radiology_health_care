pipeline {
    agent any
    stages {
        stage('Prepare') {
            steps {
                script {
                        // Execute docker compose command
                        sh '''
                    echo 'Asd!@#123' | sudo -S docker-compose -f ./src/main/docker/app.yml up
                    '''
                }
            }
        }
    }
}
