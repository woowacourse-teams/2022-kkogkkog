import { Link } from 'react-router-dom';

import Icon from '@/@components/@shared/Icon';
import useMe from '@/@hooks/user/useMe';
import { PATH } from '@/Router';
import theme from '@/styles/theme';

import * as Styled from './style';

interface HeaderProps {
  title: string;
}

const Header = (props: HeaderProps) => {
  const { title } = props;

  const { me } = useMe();

  return (
    <Styled.Root>
      <Styled.Logo>
        <Link to={PATH.LANDING}>
          <img src='/assets/images/logo.png' alt='로고' width='36' />
        </Link>
      </Styled.Logo>
      <Styled.Title>{title}</Styled.Title>
      <Styled.Profile>
        {me ? (
          <Link to={PATH.PROFILE}>{me.nickname}</Link>
        ) : (
          <Link to={PATH.LOGIN}>
            <Icon iconName='profile' size='30' color={theme.colors.primary_400} />
          </Link>
        )}
      </Styled.Profile>
    </Styled.Root>
  );
};

export default Header;
