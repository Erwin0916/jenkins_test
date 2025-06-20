pipeline {
    agent any

    stages {
        stage('add properties') {
            steps {
                withCredentials([file(credentialsId: 'application', variable: 'application'),
                                file(credentialsId: 'application-email', variable: 'application-email'),
                                file(credentialsId: 'application-dev', variable: 'application-dev'),
                                file(credentialsId: 'application-prod', variable: 'application-prod')]) {
                    script {
                        sh 'chmod 755 /var/jenkins_home/workspace/jenkins-emoji-cms-api/jenkins_test/src/main/resources'
                        sh 'cp ${application}       /var/jenkins_home/workspace/jenkins-emoji-cms-api/jenkins_test/src/main/resources/application.yml'
                        sh 'cp ${application-email}   /var/jenkins_home/workspace/jenkins-emoji-cms-api/jenkins_test/src/main/resources/application-email.properties'
                        sh 'cp ${application-dev}   /var/jenkins_home/workspace/jenkins-emoji-cms-api/jenkins_test/src/main/resources/application-dev.properties'
                        sh 'cp ${application-prod}  /var/jenkins_home/workspace/jenkins-emoji-cms-api/jenkins_test/src/main/resources/application-prod.properties'
                    }
                }
            }
        }

        stage('build') {
            steps {
                dir('/var/jenkins_home/workspace/jenkins-emoji-cms-api/jenkins_test') {
                   sh '''
                    echo 'start bootJar'
                    chmod +x gradlew
                    ./gradlew clean bootJar
                    '''
                }
            }
        }
    }
}