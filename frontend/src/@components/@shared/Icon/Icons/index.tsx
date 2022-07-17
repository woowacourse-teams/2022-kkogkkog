import Close from '@/@components/@shared/Icon/Icons/Close';
import Profile from '@/@components/@shared/Icon/Icons/Profile';

const iconName = {
  profile: Profile,
  close: Close,
};

export type IconNames = keyof typeof iconName;

export default iconName;
