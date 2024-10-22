import { isDev } from '@genesislcap/foundation-utils';
import { html } from '@genesislcap/web-core';
import type { Home } from './home';
import { HomeEmailManager } from './email-manager';

HomeEmailManager;

export const HomeTemplate = html<Home>`
  <rapid-layout auto-save-key="${() => (isDev() ? null : 'HOME_1727442236272')}">
     <rapid-layout-region>
         <rapid-layout-item title="Email">
             <home-email-manager></home-email-manager>
         </rapid-layout-item>
     </rapid-layout-region>
  </rapid-layout>
`;
