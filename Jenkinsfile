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
                    sh 'git config user.email "you@example.com"'
                    sh 'git config user.name "Your Name"'
                    def pom = readMavenPom file: 'pom.xml'
                    def version = pom.version.replace("-SNAPSHOT", ".${currentBuild.number}")
                    sh "mvn -DreleaseVersion=${version} -DdevelopmentVersion=${pom.version} -DpushChanges=false release:prepare release:perform -B"
                    
                    withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'git', usernameVariable: 'GIT_USERNAME', passwordVariable: 'GIT_PASSWORD']]) {
                        sh('git push https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/ivans-innovation-lab/my-company-monolith.git --tags')
                    }
                }
            }
        }
    }
}
