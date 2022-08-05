import { css } from '@emotion/react';
import { Link, useLocation, useNavigate } from 'react-router-dom';

import Icon from '@/@components/@shared/Icon';
import Position from '@/@components/@shared/Position';
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

  const navigate = useNavigate();
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
          <Icon
            iconName='arrow'
            size='20'
            color={theme.colors.primary_400}
            onClick={() => navigate(-1)}
          />
        )}
      </Styled.Logo>
      <Styled.Title>{title}</Styled.Title>
      <Styled.Profile>
        <Position
          css={css`
            display: flex;
            align-items: center;
          `}
        >
          <Link to={PATH.USER_HISTORY}>
            <Icon iconName='notification' size='26' color={'transparent'} />
          </Link>
          {me?.unReadCount !== 0 && (
            <Position position='absolute' top='0' right='0'>
              <Styled.Bell />
            </Position>
          )}
        </Position>
        <Link
          to={PATH.PROFILE}
          css={css`
            display: flex;
            align-items: center;
          `}
        >
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
