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

		stage('Package') {
			steps {
				sh 'mvn -f ./authentication/pom.xml -B -DskipTests package'
			}
		}

		stage('Docker Build') {
			steps {
				sh 'docker build -t jkingcontainterregistry01/jking-auth:kube ./authentication'
			}
		}

		stage('Docker publish') {
			steps {
			withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId:'acr-credentials', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']]){
				    sh 'docker login -u $USERNAME -p $PASSWORD'
				    sh 'docker push jkingcontainterregistry01/jking-auth:kube'
				}
			}
		}
		stage('kubetcl set') {
        			steps {
        				sh 'kubectl set image deployment/jking-auth jking-auth=jkingcontainterregistry01/jking-auth:kube --kubeconfig /var/lib/jenkins/config'
        			}
        		}
    }
}
