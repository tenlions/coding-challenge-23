#!/usr/bin/groovy

properties([
        disableConcurrentBuilds(),
        pipelineTriggers([bitbucketPush()]),
        parameters([
                booleanParam(
                        defaultValue: false,
                        description: 'Should this be a release build? (remember to set the correct version in the pom before build)',
                        name: 'IS_RELEASE_BUILD')
        ])
])

node {
    jdk = tool name: 'jdk-11'
    env.JAVA_HOME = "${jdk}"
    env.DOCKER_CREDENTIALS = credentials('a86db6d4-82b7-48a5-bedb-81fa4a2b43ff')
    env.DOCKER_SERVER = "docker-registry.services.sabio.de"
    env.DOCKER_REPO = "knowledge-file-management"

    dir('knowledge-file-management') {

        stage('Build') {
            deleteDir()
            checkout scm
            try {
                mvn("package")
            } catch (e) {
                sendEmail()
                throw e
            }
        }

        stage('Integration Tests') {
            deleteDir()
            checkout scm
            try {
                mvn("verify")
            } catch (e) {
                sendEmail()
                throw e
            }
        }

        stage('Analysis') {
            //TODO
        }

        stage('Build & Publish') {
            mvn("deploy -DskipTests -DskipITs -Dmaven.deploy.skip=true")

            withDockerRegistry(credentialsId: 'DOCKER_REGISTRY_CREDENTIALS', url: 'https://docker-registry.services.sabio.de') {
                if(isMaster() || params.IS_RELEASE_BUILD){
                    DOCKER_TAG = readMavenPom().getVersion()
                } else {
                    DOCKER_TAG = env.BRANCH_NAME.take(200) + "-SNAPSHOT"
                }

                mvn("package")

                sh "docker build -t ${env.DOCKER_SERVER}/${env.DOCKER_REPO}:${DOCKER_TAG} ."
                sh "docker push ${env.DOCKER_SERVER}/${env.DOCKER_REPO}:${DOCKER_TAG}"

                if (params.IS_RELEASE_BUILD) {
                    sendReleaseMail(DOCKER_TAG)
                    releaseDeploymentNotes(DOCKER_TAG)
                    resetDeploymentNotes()
                }
            }
        }
    }
}

/**
 * Invokes a given Maven tool with a given argument list.
 */
def mvn(args) {
    sh "${tool 'maven-38'}/bin/mvn --batch-mode ${args}"
}

/**
 * Do a checkout with a shallow clone to save disk space.
 *
 * NOTE: depth=1 may lead to "reference is not a tree" errors when the commit
 * to be fetched is not the latest one. This situation may arise from new commits
 * during a long running pipeline.
 */
def checkoutRepository() {
    try {
        // clean workspace to avoid errors like 'Unable to delete .../Lietuvi��.pdf'
        if (fileExists('pom.xml')) {
            docker.image('maven:3-alpine').inside('-u root:root -v $HOME/.m2:/root/.m2:ro') {
                sh 'mvn clean'
            }
        }

        checkout([$class           : 'GitSCM',
                  branches         : scm.branches,
                  extensions       : scm.extensions + [[$class: 'CloneOption', depth: 20, shallow: true, timeout: 30, reference: '/opt/jenkins/reference-repos/sabio/']],
                  userRemoteConfigs: scm.userRemoteConfigs])
    } catch (e) {
        echo "Checkout failed, retrying checkout..."
        checkout scm
    }
}

def sendEmail() {

    // This is what is supposed to work but it is not. This might be uncommented and checked later, after updating Jenkins.
    //    emailext body: "${env.BUILD_URL} (${env.CHANGE_AUTHOR_EMAIL}", recipientProviders:
    //        [[$class: 'CulpritsRecipientProvider']], replyTo: 'noreply@sabio.de', to: "${env.CHANGE_AUTHOR_EMAIL}", subject: "Job " +
    //        "${env.BUILD_TAG} failed"

    sh "git --no-pager show -s --format='%ae' > emailAddress.txt"
    def emailAddress = readFile "emailAddress.txt"
    emailext body: "${env.BUILD_URL}", replyTo: "noreply@sabio.de", to: "${emailAddress}", subject: "[JENKINS_FAILURE] Job " +
            "${env.BUILD_TAG} failed"
}

def isMaster() {
    def branch = env.BRANCH_NAME
    ['master'].contains(branch)
}

/**
 * Computes a development version from the current branch name, if
 * the branch is a development branch.
 *
 * For a branch name like SAB-4711-make-users-happy, the resulting
 * version is 0.0-SAB-4711-make-users-happy-SNAPSHOT.
 *
 * For branch names that do not start with a JIRA-like prefix, the
 * result is null.
 */
def developmentVersion() {
    // try to match JIRA issue prefix in branch name
    def matcher = env.BRANCH_NAME =~ '^([A-z]+-\\d+)-'

    matcher ? "0.0-" + truncate(env.BRANCH_NAME, 200) + "-SNAPSHOT" : null
}

/**
 * Overrides the Maven version if working on a dev branch, to avoid conflicts in Maven
 * or Docker artifacts.
 */
def overrideMavenVersionForDevBranch() {
    def devVersion = developmentVersion();
    if (devVersion) {
        env.DEV_VERSION = devVersion;
    }
}

/**
 * Sends an email to notify IT, Support, PM and DEV about the latest SABIO Knowledge release. If this build is a "dry run" then the job
 * starter will be notified instead.
 */
def sendReleaseMail(release_version) {
    def recipients = getUserEmail()
    withVault(configuration: [timeout: 60], vaultSecrets: [
        [path: 'secret/nomad/labs/jenkins/jobs/release',
        secretValues: [[envVar: 'EMAIL_RECIPIENTS', vaultKey: 'EMAIL_RECIPIENTS']]]]) {
        recipients = "${EMAIL_RECIPIENTS}"
    }

    emailext([body   : createReleaseMailMessage("${release_version}", getUserEmail(), env.BRANCH_NAME),
              to     : "${recipients}",
              replyTo: getUserEmail(),
              subject: "Release Knowledge File Management ${release_version}",
              mimeType: 'text/html'
    ])
}

/**
 * Provides the release mail message.
 */
def createReleaseMailMessage(release_version, builtBy, branch_name) {
"""Dear all,</br>
Knowledge File Management Release ${release_version} is now available in the Docker Registry.</br>
</br>
<a href="https://bitbucket.org/sabio-de/knowledge-file-management/src/${branch_name}/DEPLOYMENT_NOTES.md">Deployment Notes</a></br>
</br>
Please check compatibility: <a href="https://bitbucket.org/sabio-de/knowledge-file-management/src/master/COMPATIBILITY.md">File Management</a></br>
</br>
Greetings</br>
$builtBy, on behalf of the Dev Team"""
}

/**
 * Provides email address of the user who triggered this build.
 */
def getUserEmail() {
    def cause = currentBuild.rawBuild.getCause(Cause.UserIdCause)
    def id = cause.getUserId()
    def userMail = User.getById(id, false).getProperty(hudson.tasks.Mailer.UserProperty.class).getAddress()
    userMail
}

// this method overrides the first line in the DEPLOYMENT_NOTES.md and replaces it with the release version
void releaseDeploymentNotes(release_version) {
    checkoutBranchAndResetToOrigin()
    replaceVersionWith("$release_version")
    commitAndPushFile("DEPLOYMENT_NOTES.md", "Update deployment notes with release version: $release_version")
}

// this method resets the deployment notes template
void resetDeploymentNotes(){
    checkoutBranchAndResetToOrigin()
    // append template to top of DEPLOYMENT_NOTES.md
    sh "cat src/main/resources/release/deployment_notes_template.md | cat - DEPLOYMENT_NOTES.md > temp && mv temp DEPLOYMENT_NOTES.md"
    commitAndPushFile("DEPLOYMENT_NOTES.md", "Reset deployment notes with template")
}

/**
 * This method checks out ${env.BRANCH_NAME} from bitbucket.
 *
 * If using Jenkins method "checkout scm" a commit will be checked out, not a branch.
 * After that our HEAD doesn't have a reference to any local branch, but points directly to the commit.
 * That means it is a "detached HEAD"-state: There is no new reference, no branch created.
 * Maven release plugin doesn't like detached HEAD and want's to have a specific branch.
 *
 * Additionally, after checking out a branch we reset HEAD to remote branch and clean the working directory
 * from files that do not belong here.
 *
 * This implementation assumes to be executed inside a folder containing a git repository.
 * Note: ${env.BRANCH_NAME} is only available for "Mutlibranch pipelines"
 */
def checkoutBranchAndResetToOrigin() {

    // discard potential file modifications for current branch (that might block following git commands)
    sh "git checkout . ; " +

        // checkout specific branch
        "git fetch origin; " +
        "git checkout ${env.BRANCH_NAME}; " +

        // force local branch to use state of remote branch
        "git reset --hard origin/${env.BRANCH_NAME}; " +

        // remove files that don't belong here, e.g. tests for a feature, that is being developed on a branch
        "git clean -fdq ; "
}

private void commitAndPushFile(fileName, message){
    sh "git config push.default matching" // to avoid warning about warning: 'push.default'
    sh "git add $fileName"
    // git diff-index --quiet HEAD  is used to commit only in case we have local changes. Otherwise commit would abort with an error.
    sh "git diff-index --quiet HEAD || git commit -m \"$message\""
    sh "git push"
}

// replaces first line of DEPLOYMENT_NOTES.md with # $newVersion
private void replaceVersionWith(newVersion){
    sh "sed -i \"1s/.*/# $newVersion/\" DEPLOYMENT_NOTES.md"
}