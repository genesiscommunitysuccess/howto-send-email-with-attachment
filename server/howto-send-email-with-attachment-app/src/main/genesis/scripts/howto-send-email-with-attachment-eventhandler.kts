import global.genesis.file.api.FileStorageClient
import global.genesis.file.message.request.FileContentRequest
import global.genesis.notify.api.notifications.EmailRecipient
import global.genesis.gen.dao.enums.howto_send_email_with_attachment.email.RecipientType
import global.genesis.notify.api.notifications.EmailNotification
import global.genesis.notify.api.notifications.EmailRecipientType
import global.genesis.notify.api.service.NotificationService

/**
 * This file defines the event handler APIs. These APIs (modeled after CQRS
 * commands) allow callers to manipulate the data in the database. By default,
 * insert, update and delete event handlers (or commands) have been created.
 * These result in the data being written to the database and messages to be
 * published for the rest of the platform to by notified.
 *
 * Custom event handlers may be added to extend the functionality of the
 * application as well as custom logic added to existing event handlers.
 *
 * The following objects are visible in each eventhandler
 * `event.details` which holds the entity that this event handler is acting upon
 * `entityDb` which is database object used to perform INSERT, MODIFY and UPDATE the records
 * Full documentation on event handler may be found here >> https://docs.genesis.global/docs/develop/server-capabilities/core-business-logic-event-handler/

 */

eventHandler {
  //  Below we inject the NotificationService and FileStorageClient.
  //  The NotificationService is part of the Notify API and allows you to send notifications manually. You can do this via the sendNotification() methods.
  //  The FileStorageClient is part of the Document Management API. We use it to fetch the file uploaded by the user based on the file name provided

  val notifyService = inject<NotificationService>()
  val client = inject<FileStorageClient>()

  eventHandler<Email>("EMAIL_INSERT", transactional = true) {
    // onException can be used to specify what the behaviour should be if an error is thrown at any point during the event
    // we have access to the details that were used to call the event as well as the exception itself that was thrown
    onException { event, throwable ->
      LOG.error("EVENT_EMAIL_INSERT FAILED FOR: ${event.details}", throwable)
      nack("EVENT_EMAIL_INSERT FAILED FOR: ${event.details}")
    }
    // onValidate can be used in order to fail-early without having to interact with the database or email client etc
    // here we confirm that there indeed exists a file in the document management component with the given name before proceeding with the main portion of the event
    onValidate { event ->
      val fileContents = client.getFileContents(
        request = FileContentRequest(
          fileNames = setOf(event.details.fileName)
        ),
        userName = "admin"
      )
      require(fileContents.isNotEmpty()) { "The file name used for the attachment can't be found!" }
      ack()
    }
    onCommit { event ->
      // we are using the details sent through the event to construct our email
      // these can also be set as constants in the system definition file and called on like so:
      // val recipient = systemDefinition.getItem("EMAIL_RECIPIENT").toString()
      // where systemDefinition is a service accessible in all GPAL scripts to call on system definitions
      // see https://docs.genesis.global/docs/develop/server-capabilities/runtime-configuration/system-definition/ for more details on defining system definitions

      // Here we fetch the file for the file name provided by the user when calling this event
      // we know that this file exists as this was checked in the onValidate portion of this event handler
      val fileContents = client.getFileContents(
        request = FileContentRequest(
          fileNames = setOf(event.details.fileName)
        ),
        userName = "admin"
      )

      // Here we create the email notification. The sendNotification() method takes a notification object which it handles depending on
      // which implementation of Notification it is. In this case it's an EmailNotification which we set using the values
      // provided by the user.

      val notification = EmailNotification(
        messageBody = event.details.body,
        messageHeader = event.details.subject,

        // The getRecipientType() method is used to convert the enum type to , should be one of TO, CC or BCC.
        recipients = listOf(EmailRecipient(event.details.recipient, getRecipientType(event.details.recipientType))),

        // As fileContents is a list of all files that match the provided file name, here we fetch the first file from the list and get the FILE_STORAGE_ID.
        // This allows the NotificationService to fetch and attach it to the email being sent.
        // Here attachments is also a list so you're not limited to only attaching one file, all you need is the FILE_STORAGE_ID of any file you want to attach and you can add it to this list
        attachments = listOf(fileContents[0].fileStorageId)
      )

      // Finally, we send the email. The notification service will use the mail server set up in genesis-notify.kts
      notifyService.sendNotification(notification)

      // This inserts the record to the table
      val details = event.details
      entityDb.insert(details)
      ack()
    }
  }
}

// simple enum conversion function to go from the type created in the EMAIL table to what the NotificationService requires
fun getRecipientType(recipientType: RecipientType): EmailRecipientType =
  when (recipientType) {
    RecipientType.TO -> EmailRecipientType.TO
    RecipientType.CC -> EmailRecipientType.CC
    RecipientType.BCC -> EmailRecipientType.BCC
  }
