pipeline {
    agent any

    stages {

        stage('connect to EC2 and run docker compose') {
            steps {
                
              sshagent(['13.232.213.112']) {
                    sh 'sudo docker compose -f ./src/main/docker/app.yml up'
                }
            }
        }

    }
}
