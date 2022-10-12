import Airplane from '@/@components/@shared/Icon/Icons/Airplane';
import Arrow from '@/@components/@shared/Icon/Icons/Arrow';
import Close from '@/@components/@shared/Icon/Icons/Close';
import Hand from '@/@components/@shared/Icon/Icons/Hand';
import Notification from '@/@components/@shared/Icon/Icons/Notification';
import Plus from '@/@components/@shared/Icon/Icons/Plus';
import Profile from '@/@components/@shared/Icon/Icons/Profile';
import Reload from '@/@components/@shared/Icon/Icons/Reload';
import Slack from '@/@components/@shared/Icon/Icons/Slack';

import Copy from './Copy';
import Google from './Google';

const iconName = {
  profile: Profile,
  close: Close,
  airplane: Airplane,
  plus: Plus,
  arrow: Arrow,
  slack: Slack,
  notification: Notification,
  hand: Hand,
  reload: Reload,
  google: Google,
  copy: Copy,
};

export type IconNames = keyof typeof iconName;

export default iconName;
