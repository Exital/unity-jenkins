import com.utils.CommonSteps
import com.utils.Constants

CommonSteps.createPathFolders(this, Constants.PORTFOLIO_PATH)
def JOB_NAME = Constants.PORTFOLIO_PATH + "/Portfolio-CI"

pipelineJob(JOB_NAME){
  properties {
    pipelineTriggers {
        triggers {
          genericTrigger {
              genericVariables {
                  genericVariable {
                      key("portfolio_branch")
                      value("\$.ref")
                      valueFilterRegex("^(refs\\/heads\\/)")
                  }
              }
              regexpFilterText("\$portfolio_branch")
              regexpFilterExpression("^(refs\\/heads\\/(master|staging))\$")
              printContributedVariables(true)
              printPostContent(true)
              tokenCredentialId('github-webhook-portfolio-ci')
          }
      }
    }
  }
  parameters {
      stringParam('docker_registry', 'harbor.talrozen.com', 'Docker Registry')
      stringParam('portfolio_branch', 'master', 'Portfolio Branch')
  }

  CommonSteps.applyCpsScm(delegate, Constants.SCM_URL_DSL_REPO, Constants.EXITAL_GITHUB_CREDENTIALS, "main", "jenkins/pipelines/portfolio/Jenkinsfile.PortfolioCI")
}
