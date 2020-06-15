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
				sh 'mvn -f ./Auth/pom.xml -B -DskipTests package'
			}
		}

		stage('Docker Build') {
			steps {
			    sh 'WEB_IMAGE_NAME=${ACR_LOGINSERVER}/jkingcontainterregistry01/jking-auth:kube${BUILD_NUMBER}'
				sh 'docker build -t $WEB_IMAGE_NAME ./Auth'
			}
		}

		stage('Docker publish') {
			steps {
				sh '${ACR_LOGINSERVER} -u ${ACR_ID} -p ${ACR_PASSWORD}'
				sh 'docker push $WEB_IMAGE_NAME'
			}
		}
		stage('kubetcl set') {
        			steps {
        				sh 'WEB_IMAGE_NAME="${ACR_LOGINSERVER}/jkingcontainterregistry01/jking-auth:kube${BUILD_NUMBER}'
        				sh 'kubectl set image deployment/jking-auth jking-auth=$WEB_IMAGE_NAME --kubeconfig /var/lib/jenkins/config'
        			}
        		}
    }
}
