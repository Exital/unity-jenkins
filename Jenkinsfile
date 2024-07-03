pipeline {
    agent {
        kubernetes {
            label 'K8S-With-Docker'
        }
    }
    options {
        skipDefaultCheckout(true)
    }

    stages {
        stage('Checkout') {
            steps {
                cleanWs()
                checkout scm
            }
        }
        
        
        stage('Create or Update Jobs and Folders') {
            steps {
                script {
                    final Map additionalParams = [
                        testingEnv: env.JOB_NAME.contains('Test'),
                    ]
                    jobDsl(additionalClasspath: 'src/',
                        lookupStrategy: 'SEED_JOB',                        
                        targets: ['jenkins/dsl/jobs/*/DSL*.groovy'].join('\n'),
                        sandbox: true,           
                        removedJobAction: 'DELETE', // Action to take on removed jobs
                        additionalParameters: additionalParams)
                    println "Done processing DSL scripts"
                }
            }
        }
    }
}
