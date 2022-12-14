pipeline{
    environment {
        dockerImage = ''
        imageName = "helloworld"
    }
    
    agent any
    stages{
        stage('pull'){
            steps{
              git poll: false, url: 'https://github.com/jredel/jenkins-helloworld.git'
            }
        }
        stage('dockerfile'){
            steps {
                // On génère le dockerfile à la volé pour le test, il faudrait qu'il soit dans le dépôt
              sh '''
                echo 'FROM eclipse-temurin:17-jdk
                COPY Main.java /app/Main.java
                WORKDIR /app
                RUN javac Main.java
                CMD ["java", "Main"]' > Dockerfile
                echo '---'
                cat Dockerfile
                echo '---'
            '''
            }
        }
        
        stage('build'){
            steps {
                script {
                    dockerImage = docker.build imageName
                }
            }
        }

        stage('push'){
            steps {
                script{
                    docker.withRegistry('http://registry:5000','') {
                        dockerImage.push("$BUILD_NUMBER")
                        dockerImage.push('latest')
                    }   
                }
            }
        }

        stage('run'){
            steps {
                sh 'docker run helloworld'
            }
        }
    }
}