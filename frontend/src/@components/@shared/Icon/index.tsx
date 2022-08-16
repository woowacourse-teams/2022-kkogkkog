import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { HTMLAttributes } from 'react';

import { IconNames } from '@/@components/@shared/Icon/Icons';

import Icons from './Icons';

interface IconProps extends HTMLAttributes<HTMLDivElement> {
  iconName: IconNames;
  hasCursor?: boolean;
  size?: string;
  color?: string;
  className?: string;
}

const Icon = (props: IconProps) => {
  const { iconName, hasCursor = true, size = '24', color = '#ffffff', ...rest } = props;

  const IconComponent = Icons[iconName];

  return (
    <Styled.Wrapper size={size} color={color} {...rest}>
      <IconComponent width='100%' height='100%' />
    </Styled.Wrapper>
  );
};

export default Icon;

const Styled = {
  Wrapper: styled.div<Pick<IconProps, 'size' | 'color' | 'hasCursor'>>`
    & * {
      fill: currentColor;
    }

    ${({ size, color, hasCursor }) => css`
      color: ${color};
      width: ${size}px;
      height: ${size}px;
      cursor: ${hasCursor && 'pointer'};
    `}
  `,
};
