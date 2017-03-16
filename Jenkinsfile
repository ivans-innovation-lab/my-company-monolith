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
                script {
                    def pom = readMavenPom file: 'pom.xml'
                    def version = pom.version.replace("-SNAPSHOT", ".${currentBuild.number}")
                }
                echo 'POM: ${pom}'
                echo 'VERSION: ${version}'
                sh 'mvn -DpushChanges=false -DlocalCheckout=true -DpreparationGoals=initialize release:prepare release:perform -B'
            }
            post {
                success {
                    junit 'target/surefire-reports/**/*.xml' 
                }
            }
        }
    }
}
