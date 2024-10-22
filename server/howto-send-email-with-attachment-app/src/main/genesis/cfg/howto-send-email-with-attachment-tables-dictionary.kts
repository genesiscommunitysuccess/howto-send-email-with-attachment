/**
  * This file defines the entities (or tables) for the application.  
  * Entities aggregation a selection of the universe of fields defined in 
  * {app-name}-fields-dictionary.kts file into a business entity.  
  *
  * Note: indices defined here control the APIs available to the developer.
  * For example, if an entity requires lookup APIs by one or more of its attributes, 
  * be sure to define either a unique or non-unique index.

  * Full documentation on tables may be found here >> https://docs.genesis.global/docs/develop/server-capabilities/data-model/

 */

tables {
  // EMAIL table used to store details about emails that have been sent
  table(name = "EMAIL", id = 11_000, audit = details(id = 11_500, sequence = "EA")) {
    field("BODY", STRING).notNull()
    field("FILE_NAME", STRING).notNull()
    field("RECIPIENT", STRING).notNull()
    field("RECIPIENT_TYPE", ENUM("CC","BCC","TO")).default("TO").notNull()
    field("SUBJECT", STRING).notNull()

    primaryKey("SUBJECT")

  }
}
