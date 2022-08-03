import { Link, useLocation } from 'react-router-dom';

import Icon from '@/@components/@shared/Icon';
import { useFetchMe } from '@/@hooks/@queries/user';
import { PATH } from '@/Router';
import theme from '@/styles/theme';

import * as Styled from './style';

interface HeaderProps {
  title?: string;
  className?: string;
}

const Header = (props: HeaderProps) => {
  const { title = '', className } = props;

  const isLandingPage = useLocation().pathname === PATH.LANDING;

  const { me } = useFetchMe();

  return (
    <Styled.Root className={className}>
      <Styled.Logo>
        {isLandingPage ? (
          <Link to={PATH.LANDING}>
            <img src='/assets/images/logo.png' alt='로고' width='36' />
          </Link>
        ) : (
          <Link to='-1'>
            <Icon iconName='arrow' size='20' color={theme.colors.primary_400} />
          </Link>
        )}
      </Styled.Logo>
      <Styled.Title>{title}</Styled.Title>
      <Styled.Profile>
        <Link to={PATH.NOTICATION}>
          <Icon iconName='notification' size='26' color={'transparent'} />
        </Link>
        <Link to={PATH.PROFILE}>
          {me ? (
            <Styled.ProfileImage src={me.imageUrl} alt='프사' width='30' />
          ) : (
            <Icon iconName='profile' size='30' color={theme.colors.primary_400} />
          )}
        </Link>
      </Styled.Profile>
    </Styled.Root>
  );
};

export default Header;
