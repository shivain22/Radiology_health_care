pipeline {
    agent any
    stages {
        stage('Prepare') {
            steps {
                script {
                    // Change directory to ./src/main/docker
                    dir('./src/main/docker') {
                        // Execute docker compose command
                        sh '''
                    sudo -s <<EOF
                    docker compose -f /path/to/your/app.yml up
                    EOF
                    '''
                    }
                }
            }
        }
    }
}
