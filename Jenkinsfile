pipeline {
    agent any

    stages {
        stage('Compile') {
            steps {
                echo 'Compile'
                bat 'mvn compile'
            }
        }
        stage('Test') {
            steps {
                echo 'Test'
                bat 'mvn test'
            }
        }
        stage('Package') {
            steps {
                echo 'Package'
                bat 'mvn package'
            }
            post {
                 always {
                junit 'target/surefire-reports/*.xml'
              }
                success {
                          archiveArtifacts artifacts: 'target/*.jar', followSymlinks: false
                }

             }
        }
        stage('Depoly') {
            steps {
                echo 'Deploy'
            withEnv(['JENKINS_NODE_COOKIE=dontKillMe']){
               bat 'start/min java -jar target/spring-boot-ForexService-0.0.1-SNAPSHOT.jar'
                }

            }
        }

    }
}
