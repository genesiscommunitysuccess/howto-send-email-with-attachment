import { ColDef } from '@ag-grid-community/core';
import { getNumberFormatter, getDateFormatter } from '@genesislcap/foundation-utils';

export const columnDefs: ColDef[] = [
  {
    field: "FILE_NAME",
  },
  {
    field: "SUBJECT",
  },
  {
    field: "BODY",
  },
  {
    field: "RECIPIENT",
    headerName: "Recipient",
  },
  {
    field: "RECIPIENT_TYPE",
    headerName: "Recipient Type",
  }
]
