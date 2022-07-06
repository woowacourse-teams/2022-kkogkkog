import Icon from '@/@components/@shared/Icon';
import theme from '@/styles/theme';

import * as Styled from './style';

const Header = ({ title }) => {
  return (
    <Styled.Root>
      <Styled.Logo>로고</Styled.Logo>
      <Styled.Title>{title}</Styled.Title>
      <Styled.Profile>
        <Icon iconName='profile' size='30' color={theme.colors.primary_400} />
      </Styled.Profile>
    </Styled.Root>
  );
};

export default Header;
