import Airplane from '@/@components/@shared/Icon/Icons/Airplane';
import Arrow from '@/@components/@shared/Icon/Icons/Arrow';
import Close from '@/@components/@shared/Icon/Icons/Close';
import Notification from '@/@components/@shared/Icon/Icons/Notification';
import Plus from '@/@components/@shared/Icon/Icons/Plus';
import Profile from '@/@components/@shared/Icon/Icons/Profile';
import Slack from '@/@components/@shared/Icon/Icons/Slack';

const iconName = {
  profile: Profile,
  close: Close,
  airplane: Airplane,
  plus: Plus,
  arrow: Arrow,
  slack: Slack,
  notification: Notification,
};

export type IconNames = keyof typeof iconName;

export default iconName;
