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
                                disableAllJobs: env.JOB_NAME.contains('/'),
                                default_ci_repo: params.default_ci_repo
                            ]
                    jobDsl(additionalClasspath: 'src/',
                        lookupStrategy: 'SEED_JOB',                        
                        targets: ['infrastructure/dsl/pipeline_job/DSL*.groovy'].join('\n'),
                        sandbox: true,           
                        removedJobAction: 'DELETE', // Action to take on removed jobs
                        additionalParameters: additionalParams)
                    println "Done processing DSL scripts"
                }
            }
        }
    }
}
