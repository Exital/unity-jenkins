import com.utils.CommonSteps
import com.utils.Constants

CommonSteps.createPathFolders(this, Constants.PORTFOLIO_PATH)
def JOB_NAME = Constants.PORTFOLIO_PATH + "/Portfolio-CI"
//test webhook 1
pipelineJob(JOB_NAME){
  properties {
    pipelineTriggers {
        triggers {
          genericTrigger {
              genericVariables {
                  genericVariable {
                      key("portfolio_branch")
                      value("\$.ref")
                  }
              }
              // regexpFilterText("\$portfolio_branch")
              // regexpFilterExpression("^(refs\\/heads\\/(master|develop))*?\$")
              printContributedVariables(true)
              printPostContent(true)
              tokenCredentialId('tal_webhook')
          }
      }
    }
  }
  parameters {
      stringParam('docker_registry', 'harbor.talrozen.com', 'Docker Registry')
  }

  CommonSteps.applyCpsScm(delegate, Constants.SCM_URL_DSL_REPO, Constants.EXITAL_GITHUB_CREDENTIALS, "main", "jenkins/pipelines/portfolio/Jenkinsfile.PortfolioCI")
}
