import Airplane from '@/@components/@shared/Icon/Icons/Airplane';
import Arrow from '@/@components/@shared/Icon/Icons/Arrow';
import Close from '@/@components/@shared/Icon/Icons/Close';
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
};

export type IconNames = keyof typeof iconName;

export default iconName;
