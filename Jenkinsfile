pipeline {
    agent any
    tools { 
        maven 'maven-3' 
    }
    stages {
        stage ('Build') {
            steps {
                sh 'mvn clean verify'
            }
            post {
                success {
                    junit 'target/surefire-reports/**/*.xml' 
                }
            }
        }
        stage ('Stage') {
            when {
                branch 'master'
            }
            steps {
                sh 'mvn deploy -DskipTests'
                sh 'cf api https://api.local.pcfdev.io'
     			sh 'cf auth user pass'
      			sh 'cf target -o pcfdev-org -s pcfdev-stage'
      			sh 'cf push stage-my-company-monolith -p target/*.jar --no-start'
      			sh 'cf bind-service stage-my-company-monolith mysql-stage'
      			sh 'cf restart stage-my-company-monolith'
            }
        }
        stage ('Production') {
            when {
                branch 'production'
            }
            steps {
                sh 'mvn package -DskipTests'
                sh 'cf api https://api.local.pcfdev.io'
     			sh 'cf auth user pass'
      			sh 'cf target -o pcfdev-org -s pcfdev-prod'
      			sh 'cf push prod-my-company-monolith -p target/*.jar --no-start'
      			sh 'cf bind-service prod-my-company-monolith mysql-prod'
      			sh 'cf restart prod-my-company-monolith'
            }
          
        }
    }
}
