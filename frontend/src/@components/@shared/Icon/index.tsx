import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { IconNames } from '@/@components/@shared/Icon/Icons';

import Icons from './Icons';

interface IconProps {
  iconName: IconNames;
  size?: string;
  color?: string;
  className?: string;
}

const Icon = (props: IconProps) => {
  const { iconName, size = '24', color = '#ffffff', ...rest } = props;

  const IconComponent = Icons[iconName];

  return (
    <Styled.Wrapper size={size} color={color} {...rest}>
      <IconComponent width='100%' height='100%' />
    </Styled.Wrapper>
  );
};

export default Icon;

const Styled = {
  Wrapper: styled.div`
    & * {
      fill: currentColor;
    }

    ${({ size, color }: Pick<IconProps, 'size' | 'color'>) => css`
      color: ${color};
      width: ${size}px;
      height: ${size}px;
    `}
  `,
};
