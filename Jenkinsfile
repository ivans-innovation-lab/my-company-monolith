pipeline {
    agent any
    tools { 
        maven 'maven-3' 
        org.jenkinsci.plugins.docker.commons.tools.DockerTool 'docker'
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
        stage('Create Docker Image') {
              steps {
                sh 'docker info'
              }
        }
    }
}
