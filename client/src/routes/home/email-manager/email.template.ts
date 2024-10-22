import { html, whenElse, repeat } from '@genesislcap/web-core';
import { getViewUpdateRightComponent } from '../../../utils';
import type { HomeEmailManager } from './email';
import { createFormSchema } from './email.create.form.schema';
import { updateFormSchema } from './email.update.form.schema';
import { columnDefs } from './email.column.defs';


export const EmailTemplate = html<HomeEmailManager>`
    ${whenElse(
        (x) => getViewUpdateRightComponent(x.user, ''),
        html`
            <entity-management
                design-system-prefix="rapid"
                header-case-type="capitalCase"
                enable-row-flashing
                enable-cell-flashing
                resourceName="ALL_EMAILS"
                createEvent="${(x) => getViewUpdateRightComponent(x.user, '', 'EVENT_EMAIL_INSERT')}"
                :createFormUiSchema=${() => createFormSchema }
                entityLabel="Email"
                :columns=${() => columnDefs }
                modal-position="centre"
                size-columns-to-fit
                enable-search-bar
            ></entity-management>
        `,
        html`
          <not-permitted-component></not-permitted-component>
        `,
    )}`;
