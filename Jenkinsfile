pipeline {
    agent any
     environment {
     DOCKER_HOST = "unix:///var/run/docker.sock"
    }
    tools { 
        maven 'maven-3' 
    }
    stages {
        stage ('Build') {
            when {
                branch 'feature/*'
            }
            steps {
                sh 'mvn clean install'
            }
            post {
                success {
                    junit 'target/surefire-reports/**/*.xml' 
                }
            }
        }
        stage ('Build & Deploy artifact') {
            when {
                branch 'master'
            }
            steps {
                sh 'mvn clean deploy'
            }
            post {
                success {
                    junit 'target/surefire-reports/**/*.xml' 
                }
            }
        }
        stage ('Build & Push Docker image') {
            when {
                branch 'master'
            }
            steps {
                sh 'mvn docker:build -DpushImage'
            }
        }
    }
}
