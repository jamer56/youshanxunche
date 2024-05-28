pipeline {
    agent any

    stages {
        stage('pull code') {
            steps {
                git changelog: false, credentialsId: '0b75c3ca-c113-4cdc-85bf-1a8148dea06c', poll: false, url: 'git@github.com:jamer56/youshanxunche.git'
            }
        }
        stage('build project') {
            steps {
                sh 'mvn clean package -P prod'
                sh 'mv ./target/youshanxunche-*.war ./target/youshanxunche_latest.war'
            }
        }
        stage('publish project') {
            steps {
                sh 'scp ./target/youshanxunche_latest.war  jamer56@192.168.1.203:/opt/tomcat/webapps/'
            }
        }
    }
}
