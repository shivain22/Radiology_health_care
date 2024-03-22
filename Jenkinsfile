pipeline {
    agent any
    stages {
        stage {
            steps {
                script {
                        sh 'docker-compose -f ./src/main/docker/app.yml up'
                }
            }
        }
    }
}
