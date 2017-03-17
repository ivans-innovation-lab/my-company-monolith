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
                script{
                    sh 'git config --global user.email "idugalic@gmail.com"'
                    sh 'git config --global user.name "jenkins"'
                    def pom = readMavenPom file: 'pom.xml'
                    def version = pom.version.replace("-SNAPSHOT", ".${currentBuild.number}")
                    sh "mvn -DreleaseVersion=${version} -DdevelopmentVersion=${pom.version} release:clean release:prepare release:perform -B"
                }
            }
        }
    }
}
