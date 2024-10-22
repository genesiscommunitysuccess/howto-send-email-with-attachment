notify {
    gateways {
        screen("Screen")

        // this is where you can add the specific details for your SMTP server
        // these properties should ideally be externalised to the system definition file
        // you can find more on this in the following documentation (https://docs.genesis.global/docs/develop/business-components/notifications/notifications-server/email/#using-system-definition-in-the-notifykts-script).
        email("Email") {
            smtpHost =  "localhost"
            smtpPort = 587
            smtpUser = "*"
            smtpPw = "*"
            smtpProtocol = TransportStrategy.SMTP
            systemDefaultUserName = "*"
            systemDefaultEmail = "*"
            sendFromUserAddress = false
        }

        teams("Teams")
    }
}
