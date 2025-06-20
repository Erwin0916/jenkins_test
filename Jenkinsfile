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
                        sh """
                            mkdir -p ${env.WORKSPACE}/Jenkins-build-deploy/src/main/resources
                            chmod -R u+rwX ${env.WORKSPACE}/Jenkins-build-deploy/src/main/resources
                            mkdir -p ${env.WORKSPACE}/jenkins-emoji-cms-api/src/main/resources
                            chmod -R u+rwX ${env.WORKSPACE}/jenkins-emoji-cms-api/src/main/resources

                            cp "$application" ${env.WORKSPACE}/Jenkins-build-deploy/src/main/resources/application.yml
                            cp "$application_email" ${env.WORKSPACE}/Jenkins-build-deploy/src/main/resources/application-email.properties
                            cp "$application_dev" ${env.WORKSPACE}/Jenkins-build-deploy/src/main/resources/application-dev.properties
                            cp "$application_prod" ${env.WORKSPACE}/Jenkins-build-deploy/src/main/resources/application-prod.properties
                            cp "$application" ${env.WORKSPACE}/jenkins-emoji-cms-api/src/main/resources/application.yml
                            cp "$application_email" ${env.WORKSPACE}/jenkins-emoji-cms-api/src/main/resources/application-email.properties
                            cp "$application_dev" ${env.WORKSPACE}/jenkins-emoji-cms-api/src/main/resources/application-dev.properties
                            cp "$application_prod" ${env.WORKSPACE}/jenkins-emoji-cms-api/src/main/resources/application-prod.properties
                        """
                    }
                }
            }
        }

        stage('build-emoji-cms-api') {
            steps {
                dir("${env.WORKSPACE}/jenkins-emoji-cms-api") {
                   sh '''
                        echo 'start bootJar'
                        chmod +x gradlew
                        ./gradlew clean bootJar
                   '''
                }
            }
        }

        stage('build-jenkins-build-deploy') {
            steps {
                dir("${env.WORKSPACE}/Jenkins-build-deploy") {
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