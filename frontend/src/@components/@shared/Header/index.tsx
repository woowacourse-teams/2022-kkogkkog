import { css } from '@emotion/react';
import { Link, useLocation } from 'react-router-dom';

import Icon from '@/@components/@shared/Icon';
import Position from '@/@components/@shared/Position';
import { useFetchMe } from '@/@hooks/@queries/user';
import logoImage from '@/assets/images/logo.png';
import { PATH } from '@/Router';
import theme from '@/styles/theme';

import * as Styled from './style';

interface HeaderProps {
  title?: string;
  className?: string;
}

const Header = (props: HeaderProps) => {
  const { title = '', className } = props;

  const isMainPage = useLocation().pathname === PATH.MAIN;

  const { me } = useFetchMe();

  return (
    <Styled.Root className={className}>
      <Styled.Logo>
        <Link to={PATH.MAIN}>
          <img src={logoImage} alt='로고' width={36} height={36} />
        </Link>
      </Styled.Logo>
      <Styled.Title>{!isMainPage && title}</Styled.Title>
      <Styled.Profile>
        {me && (
          <Link
            to={PATH.USER_HISTORY}
            css={css`
              position: relative;
            `}
          >
            <Icon iconName='notification' size='26' color={'transparent'} />
            {me?.unReadCount !== 0 && (
              <Position position='absolute' top='0' right='0'>
                <Styled.Bell />
              </Position>
            )}
          </Link>
        )}
        <Link
          to={PATH.PROFILE}
          css={css`
            display: flex;
            align-items: center;
          `}
        >
          {me ? (
            <Styled.ProfileImage src={me.imageUrl} alt='프사' width={30} height={30} />
          ) : (
            <Icon iconName='profile' size='30' color={theme.colors.primary_400} />
          )}
        </Link>
      </Styled.Profile>
    </Styled.Root>
  );
};

export default Header;
