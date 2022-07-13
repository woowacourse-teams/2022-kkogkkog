import { Link } from 'react-router-dom';

import Icon from '@/@components/@shared/Icon';
import { PATH } from '@/Router';
import theme from '@/styles/theme';

import * as Styled from './style';

const Header = ({ title }) => {
  return (
    <Styled.Root>
      <Styled.Logo>
        <Link to={PATH.KKOGKKOG_LIST}>
          <img src='/assets/images/logo.png' alt='로고' width='36' />
        </Link>
      </Styled.Logo>
      <Styled.Title>{title}</Styled.Title>
      <Styled.Profile>
        <Icon iconName='profile' size='30' color={theme.colors.primary_400} />
      </Styled.Profile>
    </Styled.Root>
  );
};

export default Header;
