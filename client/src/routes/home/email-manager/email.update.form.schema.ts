import { UiSchema } from '@genesislcap/foundation-forms';

export const updateFormSchema: UiSchema = {
  "type": "VerticalLayout",
  "elements": [
    {
      "type": "Control",
      "label": "File Name",
      "scope": "#/properties/FILE_NAME",
      "options": {}
    },
    {
      "type": "Control",
      "label": "Subject",
      "scope": "#/properties/SUBJECT",
      "options": {}
    },
    {
      "type": "Control",
      "label": "Body",
      "scope": "#/properties/BODY",
      "options": {}
    },
    {
      "type": "Control",
      "label": "Recipient",
      "scope": "#/properties/RECIPIENT",
      "options": {}
    },
    {
      "type": "Control",
      "label": "Recipient Type",
      "scope": "#/properties/RECIPIENT_TYPE",
      "options": {}
    }
  ]
}
