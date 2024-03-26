pipeline {
    agent any

    stages {

        stage('SonarQube Analysis') {
            steps {
                script {

                    withSonarQubeEnv('SonarQube') {
                        sh "./mvn clean verify sonar:sonar "
                    }
                }
            }
        }
    }
}
