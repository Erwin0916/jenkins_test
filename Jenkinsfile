pipeline {
    agent any

    stages {
        stage('add properties') {
            steps {
                withCredentials([
                    file(credentialsId: 'application', variable: 'application'),
                    file(credentialsId: 'application-email', variable: 'application_email'),
                    file(credentialsId: 'application-dev', variable: 'application_dev'),
                    file(credentialsId: 'application-prod', variable: 'application_prod')
                ]) {
                    script {
                        sh '''
                            mkdir -p /var/jenkins_home/workspace/jenkins-emoji-cms-api/src/main/resources
                            chmod -R u+rwX /var/jenkins_home/workspace/jenkins-emoji-cms-api/src/main/resources
                        '''
                        sh """
                            cp "$application" /var/jenkins_home/workspace/jenkins-emoji-cms-api/src/main/resources/application.yml
                            cp "$application_email" /var/jenkins_home/workspace/jenkins-emoji-cms-api/src/main/resources/application-email.properties
                            cp "$application_dev" /var/jenkins_home/workspace/jenkins-emoji-cms-api/src/main/resources/application-dev.properties
                            cp "$application_prod" /var/jenkins_home/workspace/jenkins-emoji-cms-api/src/main/resources/application-prod.properties
                        """
                    }
                }
            }
        }

        stage('build') {
            steps {
                dir('/var/jenkins_home/workspace/jenkins-emoji-cms-api') {
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