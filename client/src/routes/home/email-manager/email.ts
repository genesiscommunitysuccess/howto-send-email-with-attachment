import { User } from '@genesislcap/foundation-user';
import { customElement, GenesisElement } from '@genesislcap/web-core';
import { EmailStyles as styles } from './email.styles';
import { EmailTemplate as template } from './email.template';

@customElement({
  name: 'home-email-manager',
  template,
  styles,
})
export class HomeEmailManager extends GenesisElement {
  @User user: User;
}
