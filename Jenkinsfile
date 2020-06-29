pipeline {
    agent {
		node {
			label 'master'
		}
	}
    tools{
        maven 'apache-maven'
        jdk 'JDK 11'
    }
    options {
        skipStagesAfterUnstable()
    }

    stages {
        stage('Docker cleanup'){
            steps{
				sh '''
				docker rmi $(docker images -f 'dangling=true' -q) || true
				docker rmi $(docker images | sed 1,2d | awk '{print $3}') || true
				'''
            }
        }

		stage('SonarCloud package') {
			steps {
				sh 'mvn -f ./pom.xml verify sonar:sonar'
                sh 'mvn -f ./pom.xml clean package sonar:sonar'
			}
		}

		stage('Docker Build') {
			steps {
				sh 'docker build -t jkingcontainterregistry01.azurecr.io/jking-auth:latest ./authorization-server'
				sh 'docker build -t jkingcontainterregistry01.azurecr.io/jking-guild:latest ./guild'
			}
		}

		stage('Docker publish') {
			steps {
			withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId:'acr-credentials', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']]){
				    sh 'docker login jkingcontainterregistry01.azurecr.io -u $USERNAME -p $PASSWORD'
				    sh 'docker push jkingcontainterregistry01.azurecr.io/jking-auth:latest'
				    sh 'docker push jkingcontainterregistry01.azurecr.io/jking-guild:latest'
				}
			}
		}
		stage('kubetcl set') {
        			steps {
        				sh 'kubectl set image deployment/jking-auth jking-auth=jkingcontainterregistry01.azurecr.io/jking-auth:latest --kubeconfig /home/danny/.kube/config'
        				sh 'kubectl set image deployment/jking-guild jking-guild=jkingcontainterregistry01.azurecr.io/jking-guild:latest --kubeconfig /home/danny/.kube/config'
        			}
        		}
    }
}
