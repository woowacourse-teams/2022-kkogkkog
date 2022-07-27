import Airplane from '@/@components/@shared/Icon/Icons/Airplane';
import Close from '@/@components/@shared/Icon/Icons/Close';
import Plus from '@/@components/@shared/Icon/Icons/Plus';
import Profile from '@/@components/@shared/Icon/Icons/Profile';

const iconName = {
  profile: Profile,
  close: Close,
  airplane: Airplane,
  plus: Plus,
};

export type IconNames = keyof typeof iconName;

export default iconName;
