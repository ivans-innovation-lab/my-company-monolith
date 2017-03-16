pipeline {
    agent any
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
                sh 'git config user.email "idugalic@gmail.com"'
                sh 'git config user.name "Jenkins"'
                sh 'mvn -B gitflow:release-start'
                sh 'mvn deploy'
                sh 'git commit -a -m "Releasing"'
                sh 'mvn -B gitflow:release-finish'
            }
            post {
                success {
                    junit 'target/surefire-reports/**/*.xml' 
                }
            }
        }
    }
}
