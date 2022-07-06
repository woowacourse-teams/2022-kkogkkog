import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { IconNames } from '@/@components/@shared/Icon/Icons';

import Icons from './Icons';

interface IconPropsType {
  iconName: IconNames;
  size?: string;
  color?: string;
}

const Icon = ({ iconName, size = '24', color = '#ffffff', ...props }: IconPropsType) => {
  const IconComponent = Icons[iconName];

  return (
    <StyledWrapper size={size} color={color} {...props}>
      <IconComponent width='100%' height='100%' />
    </StyledWrapper>
  );
};

export default Icon;

const StyledWrapper = styled.div`
  & * {
    fill: currentColor;
  }

  ${({ size, color }: Pick<IconPropsType, 'size' | 'color'>) => css`
    color: ${color};
    width: ${size}px;
    height: ${size}px;
  `}
`;
