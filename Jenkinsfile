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
                    echo "GIT URL: ${env.GIT_REPO}"
                    git url: "${env.GIT_REPO}"
                    sh "git clean -f && git reset --hard origin/master"
                    def pom = readMavenPom file: 'pom.xml'
                    def version = pom.version.replace("-SNAPSHOT", ".${currentBuild.number}")
                    sh "mvn -DreleaseVersion=${version} -DdevelopmentVersion=${pom.version} -DpushChanges=false -DlocalCheckout=true -DpreparationGoals=initialize release:prepare release:perform -B"
                    sh "git push origin ${pom.artifactId}-${version}"
                }
            }
        }
    }
}
