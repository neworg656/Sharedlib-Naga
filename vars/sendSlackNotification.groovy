def call(String buildStatus = 'STARTED') {
    buildStatus = buildStatus ?: 'SUCCESS'

    def colorCode
    def subject = "${buildStatus}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'"
    def summary = "${subject} (${env.BUILD_URL})"

    switch (buildStatus) {
        case 'STARTED':
            colorCode = '#FFFF00' // Yellow
            break
        case 'SUCCESS':
            colorCode = '#00FF00' // Green
            break
        default:
            colorCode = '#FF0000' // Red
    }

    //slackSend(color: colorCode, message: summary, channel: '#jio-devops')
     echo "DEBUG: buildStatus = ${buildStatus}"
    echo "DEBUG: colorCode = ${colorCode}"
    echo "DEBUG: summary = ${summary}"

    // Try-catch to prevent pipeline crash
    try {
        slackSend(color: colorCode, message: summary, channel: '#jio-devops')
    } catch (Exception e) {
        echo "Slack notification failed: ${e.message}"
    }
}
