package src

class CommonSteps {

  static void applyCpsScm(job, String scmUrl, String credentialsId, String branchName, String scriptFilePath) {
    job.definition {
      cpsScm {
        scm {
          git {
            branch(branchName)
            remote {
              url(scmUrl)
              if (credentialsId) {
                credentials(credentialsId)
              }
            }
            extensions {
              wipeOutWorkspace()
            }
          }
        }
        scriptPath(scriptFilePath)
      }
    }
  }

  static void createPathFolders(dslFactory, path) {
    String[] folders = path.split('/')
    String currentPath = ""

    folders.each { folder ->
      currentPath = (currentPath == "") ? folder : "$currentPath/$folder"
      dslFactory.folder(currentPath) {}
    }
  }
}
