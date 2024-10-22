import global.genesis.db.rx.entity.multi.AsyncEntityDb
import global.genesis.file.api.FileStorageClient
import global.genesis.gen.dao.Email
import global.genesis.gen.dao.enums.howto_send_email_with_attachment.email.RecipientType
import global.genesis.message.core.event.EventReply
import global.genesis.notify.api.service.NotificationService
import global.genesis.testsupport.client.eventhandler.EventClientSync
import global.genesis.testsupport.jupiter.*
import javax.inject.Inject
import kotlin.String
import kotlin.Unit
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.Ignore

/**
 * This file tests the event handlers generated for your project.
 *
 * TODO As you edit any event handler code, for example editing existing events or adding new, you
 * should try to extend this test suite to test the changes.
 *
 * Full documentation on event handler tests may be found here >>
 * https://docs.genesis.global/docs/develop/server-capabilities/core-business-logic-event-handler/#testing
 */
@ExtendWith(GenesisJunit::class)
@PackageScans(
  PackageScan(NotificationService::class),
  PackageScan(FileStorageClient::class)
)
@SysDefOverwrite("STORAGE_STRATEGY", "LOCAL")
@SysDefOverwrite("LOCAL_STORAGE_FOLDER", "build/tmp")
@PackageNameScan("global.genesis.file")
@ScriptFile("howto-send-email-with-attachment-eventhandler.kts")
class EventHandlerTest {
  @Inject
  lateinit var client: EventClientSync

  @Inject
  lateinit var entityDb: AsyncEntityDb

  private val adminUser: String = "admin"

  @Ignore
  @Test
  fun `test insert EMAIL`(): Unit = runBlocking {
    val result = client.sendEvent(
      details = Email {
        body = "1"
        fileName = "1"
        recipient = "1"
        recipientType = RecipientType.TO
        subject = "1"
      },
      messageType = "EVENT_EMAIL_INSERT",
      userName = adminUser
    )
    result.assertedCast<EventReply.EventAck>()
    val email = entityDb.getBulk<Email>().toList()
    assertTrue(email.isNotEmpty())
  }
}
